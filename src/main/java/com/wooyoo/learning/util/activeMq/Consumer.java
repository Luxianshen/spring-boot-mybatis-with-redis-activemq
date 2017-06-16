package com.wooyoo.learning.util.activeMq;

import com.wooyoo.learning.dao.domain.Product;
import org.apache.activemq.command.ActiveMQMapMessage;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.TextMessage;

/**
 * Created by lujiasheng on 2017/6/5。
 */

@Component
public class Consumer implements ApplicationListener<ApplicationStartedEvent> {
    //使用JmsListener配置消费者监听的队列，其中text是接收到的消息
    @JmsListener(destination = "mytest.queue")
    public void receiveQueue(Message text) throws JMSException {
        MapMessage tm = (MapMessage) text;
        System.out.println(tm.getLong("Id"));
        System.out.println(tm.getLong("price"));
        System.out.println(tm.getString("name"));
        System.out.println(tm.getBoolean("up"));
        System.out.println("Consumer收到的报文为:"+text.toString());
    }

    @Override
    public void onApplicationEvent(
        ApplicationStartedEvent applicationStartedEvent) {

    }
}
