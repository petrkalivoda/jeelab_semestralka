package jeelab.messaging;

import javax.jms.ConnectionFactory;
import javax.jms.JMSContext;
import javax.jms.Topic;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class MailProducer {
	
	public void sendMessage(String msg) throws NamingException {
		Context ctx = new InitialContext();
        ConnectionFactory factory = (ConnectionFactory) ctx.lookup("ConnectionFactory");
        Topic topic = (Topic) ctx.lookup("/jms/topic/my-topic");

        try (
                JMSContext context = factory.createContext()
        ) {

            context.createProducer().send(topic, msg);
        }
	 }
}
