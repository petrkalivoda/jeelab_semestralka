package jeelab.messaging;

import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.jms.JMSContext;
import javax.jms.Queue;

@Stateless
public class MailConsumer {
	
	@Inject
	 private JMSContext context;
	  
	 @Resource(mappedName = "java:jboss/jms/queue/exampleQueue")
	 Queue myQueue;
	 
	 public String startReceiver() {
	   String message = context.createConsumer(myQueue).receiveBody(String.class);
	   System.out.println("Message is: " + message);
	   return message;
	 }
}
