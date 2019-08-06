$(function () {
    var shopid = getQueryString("shopId");
    var isEdit = shopid ? true : false;

    var initUrl='/O2O/shopadmin/getshopinitinfo';
    var registerShopUrl = '/O2O/shopadmin/registershop';
    var shopInfoUrl = "/O2O/shopadmin/getbyshopid?shopId="+shopid;
    var editShopUrl = "/O2O/shopadmin/modifyshop";
    if (!isEdit){
        getShopInitInfo();
    } else {
        getShopInfo();
    }

    function getShopInfo() {
        $.getJSON(shopInfoUrl,function (data) {
            if (data.success){
                var shop = data.shop;
                $("#shop-name").val(shop.shopName);
                $("#shop-addr").val(shop.shopAddr);
                $("#shop-phone").val(shop.phone);
                $("#shop-desc").val(shop.shopDesc);
                var tempHtml = '';
                var tempAreaHtml = '';
                    tempHtml += '<option data-id="'+shop.shopCategory.shopCategoryId+'">'
                        +shop.shopCategory.shopCategoryName+'</option>';

                data.areaList.map(function (item,index) {
                    tempAreaHtml += "<option data-id='" + item.areaId + "'>"
                        +item.areaName+"</option>";
                });
                $("#shop-category").html(tempHtml);
                $("#shop-category").attr("disabled","disabled");
                $("#area").html(tempAreaHtml);
                $("#area option[data-id='"+shop.area.areaId+"']").attr("selected","selected");
            }
        });


    }
    function getShopInitInfo() {
        $.getJSON(initUrl,function (data) {
           if (data.success){
               var tempHtml = '';
               var tempAreaHtml = '';
               data.shopCategoryList.map(function (item,index) {
                   tempHtml += '<option data-id="'+item.shopCategoryId+'">'
                       +item.shopCategoryName+'</option>';
               });
               data.areaList.map(function (item,index) {
                   tempAreaHtml += "<option data-id='" + item.areaId + "'>"
                       +item.areaName+"</option>";
               });
               $("#shop-category").html(tempHtml);
               $("#area").html(tempAreaHtml);
           }
        });

    }
    $("#submit").click(function () {
        var shop = {};
        if(isEdit){
            shop.shopId = shopid;
        }
        alert(shop.shopId);
        shop.shopName =$("#shop-name").val();
        shop.shopAddr = $("#shop-addr").val();
        shop.phone =$("#shop-phone").val();
        shop.shopDesc = $("#shop-desc").val();
        shop.shopCategory = {
            shopCategoryId:$("#shop-category").find("option").not(function () {
                return !this.selected;

            }).data("id")
        };
        shop.area = {
            areaId:$("#area").find("option").not(function () {
                return !this.selected;

            }).data("id")
        };


        var shopImg = $("#shop-img")[0].files[0];
        var formData = new FormData();
        var VerifyCodeActual = $("#j_captcha").val();
        if (!VerifyCodeActual){
            $.toast("请输入验证码");
            return;
        }
        formData.append("verifyCode",VerifyCodeActual);
        formData.append("shopImg",shopImg);
        formData.append("shopStr",JSON.stringify(shop));
        $.ajax({
            url:isEdit?editShopUrl:registerShopUrl,
            type:"POST",
            data:formData,
            contentType:false,
            processData:false,
            cache:false,
            success:function (data) {
                if (data.success){
                    $.toast("提交成功");
                } else {
                    $.toast("提交失败"+data.errorMsg);
                }
                $("#captcha_img").click();
            }
        });
    });
    
});