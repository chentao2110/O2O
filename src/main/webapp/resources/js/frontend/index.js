$(function () {
 var infoUrl = "/O2O/frontend/listmainpageinfo";
 $.getJSON(infoUrl,function (data) {
     if (data.success){
         var headLineList = data.headLineList;
         var shopCategoryList = data.shopCategoryList;
         var tempHtml = "";
         headLineList.map(function (item,index) {
             tempHtml  +=
             "<div class='swiper-slide img-wrap'>" +
             "<a href='"+item.lineLink+"' external> " +
                 "<img class='banner-img'  src='"+item.lineImg+"' alt='"+item.lineName+"' /></a>" +
             "</div>"
         });

         $(".swiper-wrapper").html(tempHtml);
         $(".swiper-container").swiper({
             autoplay : 3000,
             //用户对轮播图进行操作时，是否自动停止autoplay
             autoplayDisableOnInteraction : true
         });
         tempHtml = "";
        shopCategoryList.map(function (item,index) {
            tempHtml += "<div class='col-50  shop-classify' data-category='"+item.shopCategoryId+"'>" +
                "<div class='word'>" +
                "<p class='shop-title'>"+item.shopCategoryName+"</p>" +
                "<p class='shop-desc'>"+item.shopcategoryDesc+"</p>" +
                "</div>"  +
                "<div class ='shop-classify-img-warp'>" +
                "<img class='shop-img' src ='"+item.shopCategoryImg+"' data-id ='"+item.shopCategoryId+"' >" +
                "</div>"+
                "</div> "
        });
        $(".row").html(tempHtml);
     }

 })
    $("#me").click(function () {
        $.openPanel("#panel-right-demo");
    })
    $(".row").on("click",".shop-classify",function (e) {
        var categoryId = e.currentTarget.dataset.category;
        var newUrl = "/O2O/frontend/shoplist?parentId="+categoryId;
        window.location.href = newUrl;
    })
})