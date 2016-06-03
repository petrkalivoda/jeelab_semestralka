package jeelab.messaging;

import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSConsumer;
import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.JMSRuntimeException;
import javax.jms.TextMessage;
import javax.naming.Context;
import javax.naming.InitialContext;

import org.jboss.msc.service.Service;
import org.jboss.msc.service.StartContext;
import org.jboss.msc.service.StartException;
import org.jboss.msc.service.StopContext;

public class MailConsumer implements Service<Void> {
	
	private final String destinationName;

    private JMSContext context;

    public MailConsumer(String destinationName) {
        this.destinationName = destinationName;
    }
	
	@Override
	public Void getValue() throws IllegalStateException, IllegalArgumentException {
		return null;
	}

	@Override
	public void start(StartContext startContext) throws StartException {
		try {
            Context ctx = new InitialContext();
            ConnectionFactory factory = (ConnectionFactory) ctx.lookup("ConnectionFactory");
            Destination destination = (Destination) ctx.lookup(destinationName);

            this.context = factory.createContext();
            JMSConsumer consumer = context.createConsumer(destination);

            startContext.complete();
            System.out.println("Starting to receive from " + destination);
            consumer.setMessageListener(message -> {
                try {
                    System.out.println("received: " + ((TextMessage) message).getText());
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            });
        } catch (Throwable t) {
            throw new StartException(t);
        }
	}

	@Override
	public void stop(StopContext stopContext) {
		try {
            this.context.close();
        } catch (JMSRuntimeException e) {
            e.printStackTrace();
        }
	}
}
