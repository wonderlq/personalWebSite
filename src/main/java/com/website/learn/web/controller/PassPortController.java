package com.website.learn.web.controller;

import com.website.learn.bean.bo.KeyData;
import com.website.learn.service.SecurityService;
import com.website.learn.web.controller.base.BaseController;
import com.website.learn.web.result.JsonResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
public class PassPortController extends BaseController {

    @Autowired
    SecurityService securityService;

    private static final Logger logger = LoggerFactory.getLogger(PassPortController.class);

    @ResponseBody
    @RequestMapping("/get_key")
    public JsonResult createPassportKey(String time){
        logger.info("create Passport Key ,time:{}",time);
        JsonResult jsonResult = new JsonResult();
        jsonResult.setCode(0);
        jsonResult.setData(new KeyData(securityService.createPublicKey(time),time));
        return jsonResult;
    }
}
