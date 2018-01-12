package com.sbm.artemis.demo.jms;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

import javax.jms.Session;

@Component
public class ArtemisConsumer { //  implements MessageConverter {


	@JmsListener(destination = "${jms.queue.destination:demoQueue}" )
	public void receiveQueueMessage(Message message, Session session){
		System.out.println("======================Queue Message==================================");
		receiveMessage(message, session);
	}


	private void receiveMessage(Message message, Session session) {
		if(message.getPayload() instanceof String){
			System.out.println("Received Message: " + message.getPayload().toString());
		}else if (message.getPayload() instanceof Person){
			System.out.println("Received Person: " + message.getPayload().toString());
		}else {
			System.err.println("Message Type Unkown !");
		}
	}




}