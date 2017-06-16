package com.wooyoo.learning;

import com.wooyoo.learning.dao.domain.Product;
import com.wooyoo.learning.dao.mapper.ProductMapper;
import com.wooyoo.learning.util.activeMq.Consumer;
import com.wooyoo.learning.util.activeMq.Producer;
import org.apache.activemq.ActiveMQQueueSession;
import org.apache.activemq.ActiveMQSession;
import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Session;

@RestController
@RequestMapping("/product")
public class ProductController {
    @Autowired
    private ProductMapper productMapper;
    @Autowired
    private Producer      producer;
    @Autowired
    private Consumer      consumer;

    @GetMapping("/{id}")
    public Product getProductInfo(
            @PathVariable("id")
                    Long productId) throws JMSException {
        Product product = productMapper.select(productId);
        Destination destination = new ActiveMQQueue("mytest.queue");
        producer.sendMessage(destination,product);
        return productMapper.select(productId);
    }

    @PutMapping("/{id}")
    public Product updateProductInfo(
            @PathVariable("id")
                    Long productId,
            @RequestBody
                    Product newProduct) {
        Product product = productMapper.select(productId);
        if (product == null) {
            throw new ProductNotFoundException(productId);
        }
        product.setName(newProduct.getName());
        product.setPrice(newProduct.getPrice());
        productMapper.update(product);
        return product;
    }
}
