$(function() {
    // 登录验证的controller url
    var loginUrl = '/O2O/local/userlogin';
    // 从地址栏的URL里获取usertype
    // usertype=1则为customer,其余为shopowner
    var usertype = "1";
    // 登录次数，累积登录三次失败之后自动弹出验证码要求输入
    var loginCount = 0;

    $("#username").on("change",function (e) {
        var username=$("#username").val();
        var url = "/O2O/local/getusetype?username="+username;
        $.getJSON(url,function (data) {
            if (data.success){
                usertype = data.type;
                var temphtml ="<li>" +
                    "                <div class='item-content'>" +
                    "                    <div class='item-media'><i class='icon icon-form-toggle'></i></div>" +
                    "                <div class='item-inner'>" +
                    "                    <div class='item-title label'>进入店铺管理页面</div>" +
                    "                    <div class='item-input'>" +
                    "                    <label class='label-switch'>" +
                    "                    <input type='checkbox' class='checkswitch' value='false'>" +
                    "                    <div class='checkbox'></div>" +
                    "                    </label>" +
                    "                    </div>" +
                    "                    </div>" +
                    "                    </div>" +
                    "                    </li>"
                $(".list-block > ul").append(temphtml);

            }
        });

    })
   $(".list-block > ul").on("click",".checkbox",function (e) {
        var temp = $(".checkswitch").val();
        if (temp=="false"){
            temp = true;
        }else if (temp == "true" ){
            temp = false;
        }
        $(".checkswitch").val(temp);

    });
    $('#submit').click(function() {
        // 获取输入的帐号
        var userName = $('#username').val();
        // 获取输入的密码
        var password = $('#psw').val();
        // 获取验证码信息
        var verifyCodeActual = $('#j_captcha').val();
        // 是否需要验证码验证，默认为false,即不需要
        var needVerify = false;
        var checkswitch =  $(".checkswitch").val();
        // 如果登录三次都失败
        if (loginCount >= 3) {
            // 那么就需要验证码校验了
            if (!verifyCodeActual) {
                $.toast('请输入验证码！');
                return;
            } else {
                needVerify = true;
            }
        }
        // 访问后台进行登录验证
        $.ajax({
            url : loginUrl,
            async : false,
            cache : false,
            type : "post",
            dataType : 'json',
            data : {
                userName : userName,
                password : password,
                verifyCodeActual : verifyCodeActual,
                //是否需要做验证码校验
                needVerify : needVerify,
                checkswitch :checkswitch
            },
            success : function(data) {
                if (data.success) {
                    $.toast('登录成功！');
                    if (!data.usertype) {
                        // 若用户在前端展示系统页面则自动链接到前端展示系统首页
                        window.location.href = '/O2O/index.html';
                    } else {
                        // 若用户是在店家管理系统页面则自动链接到店铺列表页中
                        window.location.href = '/O2O/shopadmin/shoplist';
                    }
                } else {
                    $.toast('登录失败！' + data.errMsg);
                    loginCount++;
                    if (loginCount >= 3) {
                        // 登录失败三次，需要做验证码校验
                        $('#verifyPart').show();
                    }
                }
            }
        });
    });
});