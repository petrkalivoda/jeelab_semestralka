package jeelab.ws.response;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

public class AddressStorage {
	
	public static final String REST_PREFIX = "rest";
	
	@Inject
	private HttpServletRequest request;
	
	public String host() {
		return request.getRequestURL().toString().replace(request.getRequestURI(), request.getContextPath());
	}
	
	public String user(long id) {
		return host() + "/" + REST_PREFIX + "/user/" + id;
	}
	
	public String reservation(long id) {
		return host() + "/" + REST_PREFIX + "/reservation/" + id;
	}
	
	public String centre(long id) {
		return host() + "/" + REST_PREFIX + "/sport/centre/" + id;
	}

	public String facility(long centreId) {
		return host() + "/" + REST_PREFIX + "/sport/centre/" + centreId + "/facility";
	}
	
	public String facility(long centreId, long facilityId) {
		return host() + "/" + REST_PREFIX + "/sport/centre/" + centreId + "/facility/" + facilityId;
	}
	
	public String facilityType(long id) {
		return host() + "/" + REST_PREFIX + "/sport/facility/type/" + id;
	}
	
	public String hours(long sportId, long hourId) {
		return host() + "/" + REST_PREFIX + "/sport/centre/" + sportId + "/hours/" + hourId;
	}
	
}
