/**
 * 登录页面脚本
 */


$(function () {
    var $loginForm = $("#login-form"),
        $name = $('#name'),
        $password = $('#password'),
        $code = $('#code'),
        $loginBtn = $('.btn-login'),
        $loginTips = $('.login-tips');

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

        //发送请求
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
