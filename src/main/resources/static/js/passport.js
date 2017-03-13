/**
 * 获取公钥
 */
function getPublicKey() {
    $.get('get_key?time=' + new Date().getTime(), function (result) {
        console.info(result);
    })
}
