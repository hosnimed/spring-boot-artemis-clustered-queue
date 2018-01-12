package com.sbm.artemis.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.Environment;

import javax.jms.JMSException;

@SpringBootApplication
public class SpringBootArtemisConsumerApplication {

	private static Environment environment;

	@Autowired
	public  void setEnvironment(Environment environment) {
		SpringBootArtemisConsumerApplication.environment = environment;
	}

	public static void main(String[] args) throws JMSException {
		SpringApplication.run(SpringBootArtemisConsumerApplication.class, args);
		String activeProfile = SpringBootArtemisConsumerApplication.environment.getActiveProfiles()[0];
		String artemisPort = SpringBootArtemisConsumerApplication.environment.getProperty("spring.artemis.port");
		System.out.println(String.format("======================Starting Consumer on Node %s ======================", activeProfile));
		System.out.println(String.format("===============Consumer is connected to Node @ Port : %s ================", artemisPort));

	}



}
