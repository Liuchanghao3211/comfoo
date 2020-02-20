package com.foo.flume.interceptor;


import org.apache.flume.Context;
import org.apache.flume.Event;
import org.apache.flume.interceptor.Interceptor;

import java.util.List;
import java.util.Map;

/**
 * Created Liuchanghao on 2020/1/1
 *
 * */
public class LogTypeInterceptor implements Interceptor {

    @Override
    public void initialize() {

    }

    @Override
    public Event intercept(Event event) {
        //获取flume的body，body为json的数组
        byte[] body = event.getBody();
        //将json的数组转化为字符串
        String jsonStr = new String(body);
        String logType = " ";
        //判断我们的数据的类型，（start为启动日志，其余的11种为事件的日志）
        if (jsonStr.contains("start")){
            logType = "start";
        }else {
            logType = "event";
        }

        //接下来获取flume的消息头
        Map<String, String> headers = event.getHeaders();
        //将我们的日志类型添加或存储到我们的消息头当中
        headers.put("logType","logtype");

        return event;
    }

    @Override
    public List<Event> intercept(List<Event> events) {
        for (Event event : events){
            intercept(event);
        }
        return events;
    }

    @Override
    public void close() {

    }

    public static  class Builder implements Interceptor.Builder{

        @Override
        public Interceptor build() {
            return new LogTypeInterceptor();
        }

        @Override
        public void configure(Context context) {

        }
    }
}
