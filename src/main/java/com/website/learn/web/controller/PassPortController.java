package com.website.learn.web.controller;

import com.website.learn.web.result.JsonResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author dell
 * @since 1.0.0
 * Created On 2017-03-14 01:04
 */
@RestController
@RequestMapping("")
public class PassPortController extends BaseController{

    private static final Logger logger = LoggerFactory.getLogger(PassPortController.class);

    @ResponseBody
    @RequestMapping("/get_key")
    public JsonResult createPassportKey(String time){
        logger.info("create Passport Key ,time:{}",time);
        JsonResult jsonResult = new JsonResult();
        jsonResult.setCode(0);
        jsonResult.setData("good,just for test");
        return jsonResult;
    }
}