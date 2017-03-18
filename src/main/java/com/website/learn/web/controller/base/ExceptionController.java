package com.website.learn.web.controller.base;


import com.website.learn.util.CommonUtil;
import com.website.learn.web.result.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.ErrorAttributes;
import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @author dell
 * @since 1.0.0
 * Created On 2017-03-19 01:28
 */
@RestController
@RequestMapping("/error")
public class ExceptionController extends BaseController implements ErrorController {
    private final ErrorAttributes errorAttributes;

    @Autowired
    public ExceptionController(ErrorAttributes errorAttributes) {
        Assert.notNull(errorAttributes, "ErrorAttributes must not be null");
        this.errorAttributes = errorAttributes;
    }

    @Override
    public String getErrorPath() {
        return "";
    }

    /**
     * @see org.springframework.boot.autoconfigure.web.AbstractErrorController#getErrorAttributes
     * @see org.springframework.boot.autoconfigure.web.BasicErrorController#error
     */
    @ResponseBody
    @RequestMapping(produces = "application/json")
    public JsonResult error(HttpServletRequest request) {
        RequestAttributes requestAttributes = new ServletRequestAttributes(request);
        Map<String, Object> body = this.errorAttributes.getErrorAttributes(requestAttributes, false);

        JsonResult responseResult = new JsonResult();
        responseResult.setCode(Integer.valueOf(body.get("status").toString()));
        String msg = body.get("path") + ", " + CommonUtil.split(body.get("message").toString()) + ", " + body.get("error");
        responseResult.setMsg(msg);
        return responseResult;
    }

}
