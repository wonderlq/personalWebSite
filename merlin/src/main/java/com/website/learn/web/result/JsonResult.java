package com.website.learn.web.result;

/**
 * @author dell
 * @since 1.0.0
 * Created On 2017-02-20 00:46
 */
public class JsonResult {
    /**
     * 异常标识码
     */
    private int code;
    /**
     * 异常信息
     */
    private String msg;
    /**
     * 数据
     */
    private Object data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "data result[code:"+code+"; msg:"+msg+"; data:"+data+"]";
    }

    public JsonResult(int code, String msg, Object data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public JsonResult(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public JsonResult() {
    }

    public static final class ResultCode{
        public static final int SUCCESS = 0;
        public static final int FAILED = 1;
    }
}
