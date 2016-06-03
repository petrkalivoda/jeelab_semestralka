package jeelab.messaging;

import java.io.IOException;
import java.io.PrintWriter;

import javax.inject.Inject;
import javax.jms.JMSDestinationDefinition;
import javax.jms.JMSException;
import javax.naming.NamingException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jboss.logging.Logger;

@JMSDestinationDefinition(name = "java:jboss/jms/queue/exampleQueue",
className = "javax.jms.Queue",
destinationName="queue1234",
description="My Queue", 
interfaceName = "java:jboss/jms/queue/exampleQueue")
@WebServlet(urlPatterns = {"/jms"})
public class MailServlet extends HttpServlet {
 
	@Inject
	MailProducer sender;
 
	@Inject
	MailConsumer receiver;


	 public void doGet(HttpServletRequest request, HttpServletResponse response) {
		 response.setContentType("text/html;charset=UTF-8");
	     try (PrintWriter out = response.getWriter()) {
	         out.println("<html>");
	         out.println("<head>");
	         out.println("<title>JMS2 Send Message</title>");            
	         out.println("</head>");
	         out.println("<body>");
	         out.println("<h1>JMS2 Send/Receive Message using JMS2 " + request.getContextPath() + "</h1>");
	         String m = "Hello there";
	         sender.sendMail(m);
	         out.format("Message sent: %1$s.<br>", m);
	         out.println("Receiving message...<br>");
	         String message = receiver.startReceiver();
	         out.println("Message rx: " + message);
	         out.println("</body>");
	         out.println("</html>");
		} catch (JMSException | NamingException | IOException e) {
			Logger.getLogger(MailServlet.class).error(e);
		}
	 }
}