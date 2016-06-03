package jeelab.messaging;

import org.jboss.msc.service.ServiceActivator;
import org.jboss.msc.service.ServiceActivatorContext;
import org.jboss.msc.service.ServiceName;
import org.jboss.msc.service.ServiceRegistryException;
import org.jboss.msc.service.ServiceTarget;

public class EmailServiceActivator implements ServiceActivator {

	@Override
	public void activate(ServiceActivatorContext context) throws ServiceRegistryException {
		System.err.println("activating services");
        ServiceTarget target = context.getServiceTarget();

        target.addService(ServiceName.of("my", "service", "1"), new MailConsumer("/jms/topic/my-topic"))
                .install();
	}

}
