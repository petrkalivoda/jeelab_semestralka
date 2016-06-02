package jeelab.ws.response;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import jeelab.model.entity.SportsCentreFacility;
import jeelab.model.entity.User;

@JsonInclude(Include.NON_NULL)
public class ReservationResponse {

	private Long id;
	private String url;
	private Date date;
	private Float from;
	private Float to;
	private UserResponse user;
	private FacilityResponse facility;
	
	private AddressStorage address;
	
	public ReservationResponse(AddressStorage address) {
		this.address = address;
	}
	
	public ReservationResponse() {}
	
	public ReservationResponse id(long id) {
		this.id = id;
		return this;
	}
	
	public ReservationResponse url(String url) {
		this.url = url;
		return this;
	}
	
	public ReservationResponse date(Date date) {
		this.date = date;
		return this;
	}
	
	public ReservationResponse from(float from) {
		this.from = from;
		return this;
	}
	
	public ReservationResponse to(float to) {
		this.to = to;
		return this;
	}
	
	public ReservationResponse user(long id, String url) {
		user = new UserResponse()
				.id(id)
				.url(url);
		return this;
	}
	
	public ReservationResponse user(User user) {
		this.user = new UserResponse()
				.id(user.getId())
				.url(address.user(user.getId()))
				.firstname(user.getFirstname())
				.lastname(user.getLastname())
				.email(user.getEmail());
		return this;
	}
	
	public ReservationResponse facility(SportsCentreFacility facility) {
		this.facility = new FacilityResponse(address)
				.id(facility.getId())
				.url(address.facility(facility.getSportsCentre().getId(), facility.getId()))
				.type(facility.getFacilityType())
				.centre(facility.getSportsCentre());
		return this;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Float getFrom() {
		return from;
	}

	public void setFrom(Float from) {
		this.from = from;
	}

	public Float getTo() {
		return to;
	}

	public void setTo(Float to) {
		this.to = to;
	}

	public UserResponse getUser() {
		return user;
	}

	public void setUser(UserResponse user) {
		this.user = user;
	}

	public FacilityResponse getFacility() {
		return facility;
	}

	public void setFacility(FacilityResponse facility) {
		this.facility = facility;
	}
			
}
