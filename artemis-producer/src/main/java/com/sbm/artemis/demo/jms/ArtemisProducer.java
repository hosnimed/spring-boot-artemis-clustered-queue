package com.sbm.artemis.demo.jms;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

@Component
@EnableJms
public class ArtemisProducer {

	@Autowired
	JmsTemplate jmsTemplate;

	@Autowired
	Environment environment;

	@Value("${jms.queue.destination}")
	String destinationQueue;

	public void send(String msg){
		if(environment.getActiveProfiles()[0].equals("clustered-queue")){
			jmsTemplate.convertAndSend(destinationQueue, msg);
		}
		if(environment.getActiveProfiles()[0].equals("local")){
			jmsTemplate.convertAndSend(destinationQueue, msg);
		}
	}

	public void send(Person p){
		if(environment.getActiveProfiles()[0].equals("clustered-queue")){
			jmsTemplate.convertAndSend(destinationQueue, p);
		}
		if(environment.getActiveProfiles()[0].equals("local")){
			jmsTemplate.convertAndSend(destinationQueue, p);
		}
	}


}