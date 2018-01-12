
package com.sbm.artemis.demo.config;

import org.apache.activemq.artemis.api.core.DiscoveryGroupConfiguration;
import org.apache.activemq.artemis.api.core.UDPBroadcastEndpointFactory;
import org.apache.activemq.artemis.api.jms.ActiveMQJMSClient;
import org.apache.activemq.artemis.api.jms.JMSFactoryType;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.messaging.converter.MessageConverter;
import org.springframework.messaging.converter.SimpleMessageConverter;

import javax.jms.ConnectionFactory;

@Configuration
@Profile("clustered-queue")
public class MessagingQueueConfiguration {


  @Bean
  public ConnectionFactory connectionFactory() {
    UDPBroadcastEndpointFactory udpCfg = new UDPBroadcastEndpointFactory();
    udpCfg.setGroupAddress("231.7.7.7").setGroupPort(9876);
    DiscoveryGroupConfiguration groupConfiguration = new DiscoveryGroupConfiguration();
    groupConfiguration.setBroadcastEndpointFactory(udpCfg);

    ConnectionFactory connectionFactory = ActiveMQJMSClient.createConnectionFactoryWithoutHA(groupConfiguration, JMSFactoryType.QUEUE_XA_CF);

    return connectionFactory;
  }



  /*
   * Optionally you can use cached connection factory if performance is a big concern.
   */
/*

  @Bean
  public ConnectionFactory cachingConnectionFactory() {
    CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
    connectionFactory.setTargetConnectionFactory(connectionFactory);
    connectionFactory.setSessionCacheSize(10);
    return connectionFactory;
  }

*/

  /*
   * Used for Sending Messages.
   */
  @Bean
  public JmsTemplate jmsTemplate() {
    JmsTemplate template = new JmsTemplate();
    template.setConnectionFactory(connectionFactory());
    return template;
  }

  @Bean
  MessageConverter converter() {
    return new SimpleMessageConverter();
  }

}
