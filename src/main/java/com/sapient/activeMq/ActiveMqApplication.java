package com.sapient.activeMq;

import java.util.Date;

import javax.jms.ConnectionFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jms.DefaultJmsListenerContainerFactoryConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.config.JmsListenerContainerFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.jms.support.converter.MessageType;

import com.sapient.activeMq.error.JmsErrorHandler;
import com.sapient.activeMq.model.Message;

@SpringBootApplication
@EnableJms
public class ActiveMqApplication implements CommandLineRunner {

	@Autowired
	private JmsTemplate jmsTemplate;
	
	@Autowired
	private JmsErrorHandler jmsErrorHandler;
	
	public static void main(String[] args) {
		SpringApplication.run(ActiveMqApplication.class, args);
	}

	@Bean
	public JmsListenerContainerFactory<?> myFactory(ConnectionFactory connectionFactory,
			DefaultJmsListenerContainerFactoryConfigurer configurer) {
		DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
		factory.setErrorHandler(jmsErrorHandler);
		configurer.configure(factory, connectionFactory);
		factory.setPubSubDomain(true);
		return factory;
	}
	
	@Bean
	public MessageConverter jacksonJmsMessageConverter() {
		MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter();
		converter.setTargetType(MessageType.TEXT);
		converter.setTypeIdPropertyName("_type");
		return converter;
	}

	@Override
	public void run(String... args) throws Exception {
		jmsTemplate.setPubSubDomain(true);
		jmsTemplate.convertAndSend("jms.message.endpoint2", new Message(1001L, "Welcome to AtiveMq", new Date()));
		jmsTemplate.convertAndSend("jms.message.endpoint2", new Message(1002L, "Welcome to AtiveMq1", new Date()));
		jmsTemplate.convertAndSend("jms.message.endpoint1", new Message(1003L, "Welcome to AtiveMq2", new Date()));
		jmsTemplate.convertAndSend("jms.message.endpoint1", new Message(1004L, "Welcome to AtiveMq3", new Date()));
		jmsTemplate.convertAndSend("jms.message.endpoint", new Message(1005L, "Welcome to AtiveMq4", new Date()));		
	}

}
