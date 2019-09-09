package com.sapient.activeMq.error;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.ErrorHandler;

@Service
public class JmsErrorHandler implements ErrorHandler {

	Logger log = LoggerFactory.getLogger(JmsErrorHandler.class);
	
	@Override
	public void handleError(Throwable t) {		
		log.warn("In default Jms Listener");
		log.error("Error {} " + t.getMessage() );
	}
	
}
