$(function () {
    var listUrl = "/O2O/shopadmin/getproductlistbyshop?pageIndex=1&pageSize=999";
    var modifyUrl = "/O2O/shopadmin/modifyproduct";
    getList();
    function getList() {
        $.getJSON(listUrl,function (data) {
            if (data.success){
                var productList = data.productList;
                var tempHtml = "";
                productList.map(function (item,index) {
                    var textop = "下架";
                    var contrayStatus = 0;
                    if (item.enableStatus=="0"){
                        textop ="上架";
                        contrayStatus =1;
                    }else {
                        contrayStatus =0;
                    }
                    tempHtml += "<div class='row row-product'><div class='col-33'>"+item.productName +"</div> " +
                        "<div class='col-20'>"+item.priority+"</div>"
                            +"<div class='col-40'> " +
                        "<a href='#' class='first edit' data-id='"+item.productId+"' data-status='"+item.enableStatus+"'>" +
                        "编辑</a> " +
                        "<a href='#' class='first status' data-id='"+item.productId+"' data-status='"+contrayStatus+"'>" +
                        textop +"</a> " +
                        "<a href='#' class='preview' data-id='"+item.productId+"' data-status='"+item.enableStatus+"'>" +
                        "预览</a> " +
                        "</div></div>";

                });

                $(".product-wrap").html(tempHtml);
            }

        })
    }
    $(".product-wrap").on("click","a",function (e) {
        let target = $(e.currentTarget);
        if (target.hasClass("edit")){
            window.location.href="/O2O/shopadmin/productoperation?productId="+e.currentTarget.dataset.id
        }
        if (target.hasClass("status")){
            let product = {};
            product.productId = e.currentTarget.dataset.id;
            product.enableStatus = e.currentTarget.dataset.status;
            let alertInfo =  e.currentTarget.dataset.status ==="1"?  "你确定要上架吗"  :"你确定要下架吗";

                $.confirm(alertInfo,
                    function () {
                    //确定
                        $.ajax({
                            url : modifyUrl,
                            type:"POST",
                            data : {
                                productStr : JSON.stringify(product),
                                statusChange :true
                            },
                            dataType:"json",
                            success : function (data) {
                                if (data.success){
                                    $.toast("操作成功");
                                    getList();
                                }else {
                                    $.toast("操作失败")
                                }

                            }
                        }
                        )
                    }
                );

        }
        if (target.hasClass("preview")){
            //进入商品详情页面
            alert("进入商品详情页面")
        }
    })
});