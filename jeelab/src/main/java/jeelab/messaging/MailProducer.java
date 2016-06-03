package jeelab.messaging;

import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.Queue;
import javax.naming.NamingException;

@Stateless
public class MailProducer {
	
	@Inject
	JMSContext context;
	
	@Resource(mappedName = "java:jboss/jms/queue/exampleQueue")
    private Queue queueMail;
	
	public void sendMail(String msg) throws NamingException, JMSException {
		try {    
			context.createProducer().send(queueMail, msg);        
			System.out.println("Send: " + msg);
        } catch (Exception exc) {
        	exc.printStackTrace();
        }
	}
}
