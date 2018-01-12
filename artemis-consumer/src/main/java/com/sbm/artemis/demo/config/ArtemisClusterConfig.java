package com.sbm.artemis.demo.config;

import org.apache.activemq.artemis.api.core.DiscoveryGroupConfiguration;
import org.apache.activemq.artemis.api.core.UDPBroadcastEndpointFactory;
import org.apache.activemq.artemis.api.jms.ActiveMQJMSClient;
import org.apache.activemq.artemis.api.jms.JMSFactoryType;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.stereotype.Component;

import javax.jms.ConnectionFactory;

@EnableJms
@Component
@Profile("!local")
public class ArtemisClusterConfig {

  @Bean
  public ConnectionFactory connectionFactory() {
    UDPBroadcastEndpointFactory udpCfg = new UDPBroadcastEndpointFactory();
    udpCfg.setGroupAddress("231.7.7.7").setGroupPort(9876);
    DiscoveryGroupConfiguration groupConfiguration = new DiscoveryGroupConfiguration();
    groupConfiguration.setBroadcastEndpointFactory(udpCfg);
    ConnectionFactory connectionFactory = ActiveMQJMSClient.createConnectionFactoryWithHA(groupConfiguration, JMSFactoryType.QUEUE_XA_CF);

    return connectionFactory;
  }

}
