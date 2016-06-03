package jeelab.messaging;

import javax.ejb.EJB;
import javax.jms.JMSDestinationDefinition;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@JMSDestinationDefinition(name = "java:jboss/jms/queue/exampleQueue",
className = "javax.jms.Queue",
destinationName="queue1234",
description="My Queue", 
interfaceName = "java:jboss/jms/queue/exampleQueue")
@WebServlet(urlPatterns = {"/TestServlet"})
public class MailServlet extends HttpServlet {
 
 @EJB MailProducer sender;
 
 @EJB MailConsumer receiver;


 public void doGet(HttpServletRequest request, HttpServletResponse response) {
	 //TODO: DO SHIT HERE 
 }
}