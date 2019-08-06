$(function () {
    $(".submit").click(function () {
        var username = $(".name").val();
        var password = $(".password").val();
        var confirmpassword = $(".confirmpassword").val();
        var email = $(".email").val();
        var gender = $(".item-input option:selected").val();
        if (username == null || username == undefined) {
           $.toast("用户名不能为空");
           return;
        }
        if (password == null || password == undefined) {
            $.toast("密码不能为空");
            return;
        }
        if (confirmpassword != password || confirmpassword == undefined) {
            $.toast("确认密码和密码不一致！");
            return;
        }
        if (email == null || email == undefined) {
            $.toast("用户名不能为空");
            return;
        }
        var localAuth = {};
        var personInfo = {};

    })
})