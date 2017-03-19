/**
 * 登录页面脚本
 */


$(function () {
    var $loginForm = $("#login-form"),
        $name = $('#name'),
        $password = $('#password'),
        $code = $('#code'),
        $loginBtn = $('.btn-login'),
        $loginTips = $('.login-tips'), keys;

    //回车键绑定登录事件
    document.onkeydown = function (e) {
        if (e.keyCode == 13) {
            handlerLogin();
        }
    };

    $loginBtn.on('click', function () {
        handlerLogin()
    });


    //login处理方法
    function handlerLogin() {
        //检测是否输入完整
        var name = $.trim($name.val()),
            password = $.trim($password.val());

        if (!name) {
            showError("账号为空", $name);
            return;
        }
        if (!password) {
            showError("密码为空", $password);
            return;
        }

        if ($loginBtn.hasClass("disabled-login")) {
            return;
        }

        $loginBtn.text("登录中").addClass("disabled-login");

        //登录请求
        // $loginBtn.text("登录中").removeClass("disabled-login");
        var keys = getPublicKey();
        var userInfo = encryptData(keys,name,password);
        if(sendUserInfo(userInfo)){
            console.info("check success!");
        }else {
            console.info("check false!")
        }
    }


    function showError(msg, el) {
        $loginTips.show().find('em').text(msg);
        $('.login-form input').removeClass('error');
        if (el) {
            el.addClass('error');
        }
    }

    function clearError() {
        $loginTips.hide().find('em').text('');
        $('.login-form input').removeClass('error');
    }
});
