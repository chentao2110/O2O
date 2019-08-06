$(function () {
    var loading = false;
    // 分页允许返回的最大条数，超过此数则禁止访问后台
    var maxItems = 20;
    // 默认一页返回的商品数
    var pageSize = 3;
    // 列出商品列表的URL
    var listUrl = '/O2O/frontend/listproductbyshop';
    // 默认的页码
    var pageNum = 1;
    // 从地址栏里获取ShopId
    var shopId = getQueryString('shopId');
    var productCategoryId = '';
    var productName = '';
    var getUserSessionUrl = "/O2O/local/getsession";
    // 获取本店铺信息以及商品类别信息列表的URL
    var searchDivUrl = '/O2O/frontend/listshopdetailpageinfo?shopId=' + shopId;
    // 渲染出店铺基本信息以及商品类别列表以供搜索
    getSearchDivData();
    // 预先加载10条商品信息
    addItems(pageSize, pageNum);

    // $('#exchangelist').attr('href', '/o2o/frontend/awardlist?shopId=' +
    // shopId);
    // 获取本店铺信息以及商品类别信息列表
    function getSearchDivData() {
        $.getJSON(searchDivUrl,function (data) {
            if (data.success){
                var shop = data.shop;
                $('#shop-cover-pic').attr('src', shop.shopImg);
                $('#shop-update-time').html(
                    new Date(shop.lastEditTime)
                        .Format("yyyy-MM-dd"));
                $('#shop-name').html(shop.shopName);
                $('#shop-desc').html(shop.shopDesc);
                $('#shop-addr').html(shop.shopAddr);
                $('#shop-phone').html(shop.phone);
                // 获取后台返回的该店铺的商品类别列表
                var productCategoryList = data.productCategoryList;
                var html = '';
                productCategoryList.map(function (item,index) {
                    html += '<a href="#" class="button" data-product-search-id='
                        + item.productCategoryId
                        + '>'
                        + item.productCategoryName
                        + '</a>';
                });
                $("#shopdetail-button-div").html(html);
            }
        })
    }

    function addItems(pageSize,pageNum) {
        var url = listUrl + '?' + 'pageIndex=' + pageNum + '&pageSize='
            + pageSize + '&productCategoryId=' + productCategoryId
            + '&productName=' + productName + '&shopId=' + shopId;
        loading = true;
        $.getJSON(url,function (data) {
            if (data.success) {
                // 获取当前查询条件下店铺的总数
                maxItems = data.count;
                var html = '';
                data.productList.map(function(item, index) {
                    html += '' + '<div class="card" data-product-id='
                        + item.productId + '>'
                        + '<div class="card-header">' + item.productName
                        + '</div>' + '<div class="card-content">'
                        + '<div class="list-block media-list">' + '<ul>'
                        + '<li class="item-content">'
                        + '<div class="item-media">' + '<img src="'
                        + item.imgAddr + '" width="44">' + '</div>'
                        + '<div class="item-inner">'
                        + '<div class="item-subtitle">' + item.productDesc
                        + '</div>' + '</div>' + '</li>' + '</ul>'
                        + '</div>' + '</div>' + '<div class="card-footer">'
                        + '<p class="color-gray">'
                        + new Date(item.lastEditTime).Format("yyyy-MM-dd")
                        + '更新</p>' + '<span>点击查看</span>' + '</div>'
                        + '</div>';
                });
                $(".list-div").append(html);
                var total = $('.list-div .card').length;
                // 若总数达到跟按照此查询条件列出来的总数一致，则停止后台的加载
                if (total >= maxItems) {
                    // 隐藏提示符
                    $('.infinite-scroll-preloader').hide();
                } else {
                    $('.infinite-scroll-preloader').show();
                }
                // 否则页码加1，继续load出新的店铺
                pageNum += 1;
                // 加载结束，可以再次加载了
                loading = false;
                // 刷新页面，显示新加载的店铺
                $.refreshScroller();
            }
        })
    }
    $(document).on("infinite",".infinite-scroll-bottom",function (e) {
        if (loading)
            return ;

        addItems(pageSize,++pageNum);
    })
    $("#shopdetail-search-div").on("click",".button",function (e) {
        productCategoryId = e.target.dataset.categoryId;
        if (productCategoryId){

            if ($(e.target).hasClass("button-fill")){
                $(e.target).removeClass("button-fill")
                productCategoryId = "";
            }else {
                $(e.target).addClass("button-fill").siblings().removeClass("button-fill")
            }
            $(".list-div").empty();
            pageNum =1;
            addItems(pageSize,pageNum)
        }
    });
    $(".list-div").on("click",".card",function (e) {
        var productId = e.currentTarget.dataset.productId;
        window.location.href="/O2O/frontend/productdetail?productId="+productId;
    });
    $("#search").on("change",function (e) {
        areaId = e.target.value;
        $(".list-div").empty();
        pageNum =1 ;
        addItems(pageSize,pageNum);
    })
    //进入侧边栏
    $("#me").click(function () {
        $.getJSON(getUserSessionUrl,function (data) {
            if (data.success){
                $.openPanel("#panel-right-demo");
            }else {
                window.location.href="/O2O/local/login";
            }
        })
    })
    $.init();
});