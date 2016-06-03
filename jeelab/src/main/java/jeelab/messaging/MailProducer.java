package jeelab.messaging;

import javax.annotation.Resource;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.Queue;
import javax.naming.NamingException;

@RequestScoped
public class MailProducer {
	
	@Resource(mappedName = "java:jboss/jms/queue/exampleQueue")
    private Queue queueMail;
	
	@Inject
	private JMSContext context;
	
	public void sendMail(String msg) throws NamingException, JMSException {
		try {    
			context.createProducer().send(queueMail, msg);        
			System.out.println("Send: " + msg);
        } catch (Exception exc) {
        	exc.printStackTrace();
        }
	}
}
