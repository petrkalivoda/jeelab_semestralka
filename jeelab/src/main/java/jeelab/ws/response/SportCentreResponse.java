package jeelab.ws.response;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import jeelab.model.entity.BusinessHours;
import jeelab.model.entity.SportsCentreFacility;

@JsonInclude(Include.NON_NULL)
public class SportCentreResponse {

	private Long id;
	private String url;
	private String facilityUrl;
	private String street;
	private String city;
	private String country;
	private String phone;
	private String building;
	private List<FacilityResponse> facilities;
	private List<HoursResponse> hours;
	private Float longitude;
	private Float latitude;
	
	@JsonIgnore
	private AddressStorage address;
	
	public SportCentreResponse() {}
	
	public SportCentreResponse(AddressStorage address) {
		this.address = address;
	}
	
	public SportCentreResponse id(Long id) {
		this.id = id;
		return this;
	}
	
	public SportCentreResponse url(String url) {
		this.url = url;
		return this;
	}
	
	public SportCentreResponse facilityUrl(String url) {
		this.facilityUrl = url;
		return this;
	}
	
	public SportCentreResponse street(String street) {
		this.street = street;
		return this;
	}
	
	public SportCentreResponse city(String city) {
		this.city = city;
		return this;
	}
	
	public SportCentreResponse country(String country) {
		this.country = country;
		return this;
	}
	
	public SportCentreResponse phone(String phone) {
		this.phone = phone;
		return this;
	}
	
	public SportCentreResponse building(String building) {
		this.building = building;
		return this;
	}
	
	public SportCentreResponse longitude(Float longitude) {
		this.longitude = longitude;
		return this;
	}
	
	public SportCentreResponse latitude(Float latitude) {
		this.latitude = latitude;
		return this;
	}
	
	public SportCentreResponse hours(Collection<BusinessHours> hours) {
		if (hours == null || hours.isEmpty()) return this;
		this.hours = new ArrayList<HoursResponse>();
		for (BusinessHours h : hours) {
			this.hours.add(new HoursResponse()
					.id(h.getId())
					.url(address.centreHours(id, h.getId()))
					.day(h.getDay())
					.open(h.getOpenTime())
					.close(h.getCloseTime())
					);
		}
		return this;
	}
	
	public SportCentreResponse facilities(Collection<SportsCentreFacility> facilities) {
		if (facilities == null || facilities.isEmpty()) return this;
		this.facilities = new ArrayList<FacilityResponse>();
		for (SportsCentreFacility f : facilities) {
			this.facilities.add(new FacilityResponse(address)
					.id(f.getId())
					.url(address.facility(id, f.getId()))
					.type(f.getFacilityType())
					.hours(f.getBusinessHours())
					);
		}
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
	public String getFacilityUrl() {
		return facilityUrl;
	}
	public void setFacilityUrl(String facilityUrl) {
		this.facilityUrl = facilityUrl;
	}
	public String getStreet() {
		return street;
	}
	public void setStreet(String street) {
		this.street = street;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getBuilding() {
		return building;
	}
	public void setBuilding(String building) {
		this.building = building;
	}
	public List<HoursResponse> getHours() {
		return hours;
	}
	public void setHours(List<HoursResponse> hours) {
		this.hours = hours;
	}

	public List<FacilityResponse> getFacilities() {
		return facilities;
	}

	public void setFacilities(List<FacilityResponse> facilities) {
		this.facilities = facilities;
	}

	public Float getLongitude() {
		return longitude;
	}

	public void setLongitude(Float longitude) {
		this.longitude = longitude;
	}

	public Float getLatitude() {
		return latitude;
	}

	public void setLatitude(Float latitude) {
		this.latitude = latitude;
	}		
}
