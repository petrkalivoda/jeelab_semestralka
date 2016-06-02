package jeelab.ws.response;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

/**
 * 
 * Sdruzuje veskere entry points
 * @author Vaclav Dobes
 *
 */
public class AddressStorage {
	
	public static final String REST_PREFIX = "rest";
	
	@Inject
	private HttpServletRequest request;
	
	/**
	 * Adresa serveru
	 * @return
	 */
	public String host() {
		return request.getRequestURL().toString().replace(request.getRequestURI(), request.getContextPath());
	}
	
	/**
	 * Registrace uzivatele
	 * @return
	 */
	public String user() {
		return host() + "/" + REST_PREFIX + "/user/";
	}
	
	/**
	 * Prihlaseni do aplikace
	 * @return
	 */
	public String login() {
		return host() + "/" + REST_PREFIX + "/user/login";
	}
	
	/**
	 * Odhlaseni z aplikace
	 * @return
	 */
	public String logout() {
		return host() + "/" + REST_PREFIX + "/user/logout";
	}
	
	/**
	 * Detail uzivatele, admina
	 * @param id
	 * @return
	 */
	public String user(long id) {
		return host() + "/" + REST_PREFIX + "/user/" + id;
	}
	
	/**
	 * Seznam vsech rezervaci (GET)
	 * pridani rezervace (POST)
	 * @return
	 */
	public String reservation() {
		return host() + "/" + REST_PREFIX + "/reservation/";
	}
	
	/**
	 * Detail rezervace + update a delete
	 * @param id
	 * @return
	 */
	public String reservation(long id) {
		return host() + "/" + REST_PREFIX + "/reservation/" + id;
	}
	
	public String facilityReservations(long id) {
		return host() + "/" + REST_PREFIX + "/reservation/facility/" + id;
	}
	
	/**
	 * Seznam sportovist (GET)
	 * pridani sportoviste (POST)
	 * @return
	 */
	public String centre() {
		return host() + "/" + REST_PREFIX + "/sport/centre/";
	}
	
	/**
	 * Detail sportoviste
	 * @param id
	 * @return
	 */
	public String centre(long id) {
		return host() + "/" + REST_PREFIX + "/sport/centre/" + id;
	}

	/**
	 * Vytvoreni zarizeni u sportoviste
	 * @param centreId
	 * @return
	 */
	public String facility(long centreId) {
		return host() + "/" + REST_PREFIX + "/sport/centre/" + centreId + "/facility";
	}
	
	/**
	 * Detail zarizeni + update a delete
	 * @param centreId
	 * @param facilityId
	 * @return
	 */
	public String facility(long centreId, long facilityId) {
		return host() + "/" + REST_PREFIX + "/sport/centre/" + centreId + "/facility/" + facilityId;
	}
	
	/**
	 * Seznam typu sportovist (GET)
	 * novy typ (POST)
	 * @return
	 */
	public String facilityType() {
		return host() + "/" + REST_PREFIX + "/sport/facility/type/";
	}
	
	/**
	 * Seznam zarizeni
	 * @param id
	 * @return
	 */
	public String facility() {
		return host() + "/" + REST_PREFIX + "/sport/centre/facility/";
	}
	
	/**
	 * Detail typu + update a delete
	 * @param id
	 * @return
	 */
	public String facilityType(long id) {
		return host() + "/" + REST_PREFIX + "/sport/facility/type/" + id;
	}
	
	/**
	 * Hodiny sportoviste, slouzi k pripadnemu smazani
	 * @param sportId
	 * @param hourId
	 * @return
	 */
	public String centreHours(long sportId, long hourId) {
		return host() + "/" + REST_PREFIX + "/sport/centre/" + sportId + "/hours/" + hourId;
	}
	
	/**
	 * Hodiny konkretniho zarizeni, slouzi k pripadnemu smazani
	 * @param facilityId
	 * @param hourId
	 * @return
	 */
	public String facilityHours(long facilityId, long hourId) {
		return host() + "/" + REST_PREFIX + "/sport/facility/" + facilityId + "/hours/" + hourId;
	}
	
}
