package jeelab.websockets;

import java.io.IOException;

import javax.websocket.OnMessage;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

@ServerEndpoint("/websockets/reservation")
public class ReservationWebSockets {
	
	@OnMessage
	public void reservation(String message, Session session) throws IOException {
		for (Session s: session.getOpenSessions()) {
			System.out.println("Send to: " + s.getId());
			if (!session.equals(s))
				s.getBasicRemote().sendText(message);
		}
	}
	
//	@OnOpen
//	public void open(Session session) {
//		System.out.println("Session id: " + session.getId());
//	}
//	
//	@OnClose
//	public void close(CloseReason reason) {
//		System.out.println("Reason is: " + reason.getReasonPhrase());
//	}
	
}
