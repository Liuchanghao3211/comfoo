package com.foo.flume.interceptor;

import org.apache.commons.lang.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LogUtils {
    private static Logger logger = LoggerFactory.getLogger(LogUtils.class);
    public static boolean validateReportLog(String log) {

        /**
         *  日志检查：正常的返回true，错误的返回false
         */
        try{
            //1577435693391|{"cm":{"ln":"-90.1","sv":"V2.9.0","os":"8.2.8","g":"90NLBW6Z@gmail.com","mid":"m107","nw":"WIFI","l":"pt","vc":"4","hw":"1080*1920","ar":"MX","uid":"u840","t":"1577414296537","la":"-14.0","md":"HTC-2","vn":"1.1.7","ba":"HTC","sr":"F"},"ap":"gmall","et":[]}
            //首先校验是总长度（2）
            if (log.split("\\|").length <2 ){
                return false;
            }
            //其次是校验的第一串是否时间戳
            if (log.split("\\|")[0].length()!=13 || !NumberUtils.isDigits(log.split("\\|")[0])){
                return false;
            }
            //再次是校验的是第二个串（之后的串）是否为正确的json串
            if (!log.split("\\|")[1].trim().startsWith("{" ) || !log.split("\\|")[1].trim().endsWith("}")){
                return false;
            }
        }catch (Exception e){
            // 错误的日志的打印，需要查考
            logger.error("error parse,message is:" + log);
            logger.error(e.getMessage());
            return  false;
        }
        return true;
    }
}
