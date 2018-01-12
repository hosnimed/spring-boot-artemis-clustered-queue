
package com.sbm.artemis.demo.config;

import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.messaging.converter.MessageConverter;
import org.springframework.messaging.converter.SimpleMessageConverter;

import javax.jms.ConnectionFactory;
import javax.jms.DeliveryMode;

@Configuration
@Profile("local")
public class MessagingLocalConfiguration {

  @Autowired
  private ConnectionFactory connectionFactory(){
    ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory();
    return factory;
  }

  @Value("${jms.queue.destination}")
  String destinationQueue;

  /*
   * Used for Sending Messages.
   */
  @Bean
  public JmsTemplate jmsTemplate(ConnectionFactory connectionFactory) {
    JmsTemplate template = new JmsTemplate();
    template.setConnectionFactory(connectionFactory());
    template.setDeliveryMode(DeliveryMode.PERSISTENT);
    template.setDeliveryPersistent(true);
    return template;
  }

  @Bean
  MessageConverter converter() {
    return new SimpleMessageConverter();
  }

}
