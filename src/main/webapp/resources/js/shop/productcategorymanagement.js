$(function () {
    var listUrl = "/O2O/shopadmin/getproductcategorylist";
    var addUrl = "/O2O/shopadmin/addproductcategorys";
    var deleteUrl = "/O2O/shopadmin/removeproductcategory";
    getList();
    function getList() {
        $.getJSON(listUrl,function (data) {
            if (data.success){
                var dataList = data.data;
                $(".category-wrap").html("");
                var tempHtml = "<tr>" +
                    "        <th class='col-33'>类别</th>" +
                    "        <th class='col-33'>优先级</th>" +
                    "        <th class='col-33'>操作</th>" +
                    "        </tr>";
                dataList.map(function (item,index) {
                    tempHtml += ''+ '<tr class="row-product-category ">'
                        + '<td class="product-category-name">'
                        + item.productCategoryName
                        + '</td>'
                        + '<td >'
                        + item.priority
                        + '</td>'
                        + '<td ><a href="#" class=" delete" data-id="'
                        + item.productCategoryId
                        + '">删除</a></td>'
                        + '</tr>';

                })
                $(".category-wrap").append(tempHtml);
            }
        })

    }
    $("#new").click(function () {
        var tempHtml = '<tr class="row-product-category temp">'
            + '<td class=""><input class="category-input category" type="text" placeholder="分类名"></td>'
            + '<td class=""><input class="category-input priority" type="number" placeholder="优先级"></td>'
            + '<td class=""><a href="#" class="button delete">删除</a></td>'
            + '</tr>';
        $('.category-wrap').append(tempHtml);
    });
    $("#submit").click(function () {
        var tempArr = $(".temp");
        var productCategoryList  = [];
        tempArr.map(function(index,item) {
            var tempObj = {};
            tempObj.productCategoryName = $(item).find(".category").val();
            tempObj.priority = $(item).find(".priority").val();
            if (tempObj.productCategoryName && tempObj.priority){
                productCategoryList.push(tempObj);
            }

        });
        $.ajax({
            url:addUrl,
            type: "POST",
            data : JSON.stringify(productCategoryList),
            contentType : "application/json",
                success: function(data) {
            if (data.success){
                $.toast("提交成功");
                getList();
            } else {
                $.toast("提交失败")
            }
        }
        });

    });
    $(".category-wrap").on("click",".row-product-category.temp .delete",function (e) {
        console.log($(this).parent().parent());
        $(this).parent().parent().remove();

    });
    $(".category-wrap").on("click",".row-product-category.now .delete",function (e) {
       var target = e.currentTarget;
       $.confirm("确定吗",function () {
           $.ajax({
               url:deleteUrl,
               type:"POST",
               data: {productCategoryId:target.dataset.id},
               dataType:"json",
               success:function (data) {
                   if (data.success){
                       $.toast("提交成功");
                       getList();
                   } else {
                       $.toast("删除失败");
                   }
               }
           })
       })
    });
});