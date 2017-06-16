package com.wooyoo.learning.util.activeMq;

import com.wooyoo.learning.dao.domain.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.stereotype.Service;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.Session;

/**
 * Created by lujiasheng on 2017/6/5。
 */
@Service("producer")
public class Producer {

    @Autowired
    //也可以注入JmsTemplate，JmsMessagingTemplate对JmsTemplate进行了封装
    private JmsMessagingTemplate jmsTemplate;

    //发送消息，destination是发送到的队列，message是待发送的消息
    public void sendMessage(Destination destination, Product product) throws JMSException {
        Session session = jmsTemplate.getConnectionFactory()
            .createConnection().createSession(Boolean.TRUE, Session.AUTO_ACKNOWLEDGE);
        Message message = sendData(session,product);
        jmsTemplate.convertAndSend(destination, message);
    }

    public Message sendData(Session session, Product product) throws JMSException {

        MapMessage message = session.createMapMessage();
        message.setString("name", product.getName());
        message.setLong("Id", product.getId());
        message.setLong("price", product.getPrice());
        message.setBoolean("up", true);
        return message;
    }
}
