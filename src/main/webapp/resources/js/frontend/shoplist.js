$(function () {
    var loading = true;
    // 分页允许返回的最大条数，超过此数则禁止访问后台
    var maxItems = 999;
    // 一页返回的最大条数
    var pageSize = 3;
    // 获取店铺列表的URL
    var listUrl = '/O2O/frontend/getshops';
    // 获取店铺类别列表以及区域列表的URL
    var searchDivUrl = '/O2O/frontend//listshoppageinfo';
    // 页码
    var pageNum = 1;
    // 从地址栏URL里尝试获取parent shop category id.
    var parentId = getQueryString("parentId");
    // 是否选择了子类
    var selectedParent = false;
    if (parentId) {
        selectedParent = true;
    }
    var areaId = '';
    var shopCategoryId = '';
    var shopName = '';
    // 渲染出店铺类别列表以及区域列表以供搜索
    getSearchDivData();
    // 预先加载10条店铺信息
    addItem(pageSize, pageNum);

    /**
     * 获取店铺类别列表以及区域列表信息
     *
     * @returns
     */
    function getSearchDivData() {
        // 如果传入了parentId，则取出此一级类别下面的所有二级类别
        var url = searchDivUrl + '?' + 'parentId=' + parentId;
        $
            .getJSON( url, function (data) {
                    if (data.success) {
                        // 获取后台返回过来的店铺类别列表
                        var shopCategoryList = data.shopCategoryList;
                        var html = '';
                        html += '<a href="#" class="button" data-category-id=""> 全部类别  </a>';
                        // 遍历店铺类别列表，拼接出a标签集
                        shopCategoryList
                            .map(function (item, index) {
                                html += '<a href="#" class="button" data-category-id='
                                    + item.shopCategoryId
                                    + '>'
                                    + item.shopCategoryName
                                    + '</a>';
                            });
                        // 将拼接好的类别标签嵌入前台的html组件里
                        $('#shoplist-search-div').html(html);
                        var selectOptions = '<option value="">全部街道</option>';
                        // 获取后台返回过来的区域信息列表
                        var areaList = data.areaList;
                        // 遍历区域信息列表，拼接出option标签集
                        areaList.map(function (item, index) {
                            selectOptions += '<option value="'
                                + item.areaId + '">'
                                + item.areaName + '</option>';
                        });
                        // 将标签集添加进area列表里
                        $('#area-search').html(selectOptions);
                    }
                });
    }
    function addItem(pageSize,pageNum) {
        var url = listUrl + '?' + 'pageIndex=' + pageNum + '&pageSize='
            + pageSize + '&parentId=' + parentId + '&areaId=' + areaId
            + '&shopCategoryId=' + shopCategoryId + '&shopName=' + shopName;
        loading = true;
        $.getJSON(url,function (data) {
            if (data.success){
                // 获取当前查询条件下店铺的总数
                maxItems = data.count;
                var html = '';
                // 遍历店铺列表，拼接出卡片集合
                var shopList = data.shopList;
                shopList.map(function(item, index) {
                    html += '' + '<div class="card" data-shop-id="'
                        + item.shopId + '">' + '<div class="card-header">'
                        + item.shopName + '</div>'
                        + '<div class="card-content">'
                        + '<div class="list-block media-list">' + '<ul>'
                        + '<li class="item-content">'
                        + '<div class="item-media">' + '<img src="'
                        + item.shopImg + '" width="44">' + '</div>'
                        + '<div class="item-inner">'
                        + '<div class="item-subtitle">' + item.shopDesc
                        + '</div>' + '</div>' + '</li>' + '</ul>'
                        + '</div>' + '</div>' + '<div class="card-footer">'
                        + '<p class="color-gray">'
                        + new Date(item.lastEditTime).Format("yyyy-MM-dd")
                        + '更新</p>' + '<span>点击查看</span>' + '</div>'
                        + '</div>';
                });
                // 将卡片集合添加到目标HTML组件里
                $('.list-div').append(html);
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

        addItem(pageSize,++pageNum);
    })

    $(".shop-list").on("click",".card",function (e) {
        var shopId = e.currentTarget.dataset.shopId;
        window.location.href = "/O2O/frontend/shopdetail?shopId="+shopId;
    })
    $("#shoplist-search-div").on("click",".button",function (e) {
        if (parentId && selectedParent){
            shopCategoryId = e.target.dataset.categoryId;
            if ($(e.target).hasClass("button-fill")){
                $(e.target).removeClass("button-fill")
                shopCategoryId = "";
            }else {
                $(e.target).addClass("button-fill").siblings().removeClass("button-fill")
            }
            $(".list-div").empty();
            pageNum =1;
            addItem(pageSize,pageNum)
        }else {
            parentId = e.currentTarget.dataset.categoryId;
            if ($(e.target).hasClass("button-fill")){
                $(e.target).removeClass("button-fill")
                shopCategoryId = "";
            }else {
                $(e.target).addClass("button-fill").siblings().removeClass("button-fill")
            }
            $(".list-div").empty();
            pageNum =1;
            addItem(pageSize,pageNum)
        }
    })


    $("#area-search").on("change",function (e) {
         areaId = $('#area-search').val();;
        $(".list-div").empty();
        pageNum =1 ;
        addItem(pageSize,pageNum);
    })

    $("#search").on("change",function (e) {
        shopName = e.target.value;
        $(".list-div").empty();
        pageNum =1 ;
        addItem(pageSize,pageNum);
    })
    $("#me").click(function () {
        $.openPanel("#panel-right-demo");
    })
    $.init();
});