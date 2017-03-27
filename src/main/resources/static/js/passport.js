/**
 * 获取公钥
 */
function getPublicKey() {
    var keyInfo = null;
    $.ajax({
        type: "get",
        url: 'get_key?time=' + new Date().getTime(),
        async: false,
        success: function (data) {
            keyInfo = data;
        }
    });
    return keyInfo;
}

/**
 * 发起登录请求
 * @param data
 */
function sendUserInfo(data) {
    var isOk = false;
    $.ajax({
        type: "post",
        url: 'login/signIn',
        beforeSend: function (xhr) {
            xhr.setRequestHeader("Authorization", "true");
        },
        data: data,
        async: false,
        success: function (result) {
            isOk = result.data.code == 0? true:false;
        }
    });
    return isOk;
}

/**
 * 加密数据
 * @param keys
 * @returns {Object}
 */
function encryptData(keys,name,password) {
    var encrypt = new JSEncrypt();
    var userInfo = new Object();

    encrypt.setPublicKey(keys.data.publicKey);
    var encryptedName = encrypt.encrypt(name);
    var encryptedPassword = encrypt.encrypt(password);
    userInfo.userName = encryptedName;
    userInfo.password = encryptedPassword;
    userInfo.time = keys.data.fileKey;
    userInfo.publicKey = keys.data.publicKey;
    return userInfo;
}
