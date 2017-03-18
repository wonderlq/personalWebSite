package com.website.learn.util;


import com.alibaba.dubbo.common.utils.StringUtils;

/**
 * @author zhenfei.wang
 * @since 1.0.0
 * Created On: 2016-08-13 10:55
 */

public final class CommonUtil {

    /**
     * 截取字符串的前128个字符长度的串
     * @param originString 需要被截取的原生字符串
     * @return  返回截取后的字符串
     */
    public static String split(String originString){
        return split(originString, 128);
    }

    /**
     * 截取字符串的前targetLength个字符长度的串
     * @param originString 需要被截取的原生字符串
     * @param targetLength 需要截取的长度
     * @return  返回截取后的字符串
     */
    public static String split(String originString, int targetLength){
        if(StringUtils.isBlank(originString)){
            return originString;
        }else {
            return originString.length()>targetLength?originString.substring(0, targetLength):originString;
        }
    }

}
