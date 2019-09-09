package com.sapient.activeMq.receiver;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import com.sapient.activeMq.model.Message;

@Component
public class MessageReceiver {
	
	@JmsListener(destination= "jms.message.endpoint", containerFactory = "myFactory")
	public void messageReceiver(Message message) {
		System.out.println(message);
	}
	
	@JmsListener(destination= "jms.message.endpoint1", containerFactory = "myFactory")
	public void messageReceiver1(Message message) {
		System.out.println(message);
	}
	
	@JmsListener(destination= "jms.message.endpoint2", containerFactory = "myFactory")
	public void messageReceiver2(Message message) {
		System.out.println(message);
		System.out.println("Contents : " + message.getContent());
		System.out.println("Date of Message: " + message.getDate());
	}
}
