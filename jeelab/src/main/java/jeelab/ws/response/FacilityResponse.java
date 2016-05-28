package jeelab.ws.response;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import jeelab.model.entity.BusinessHours;
import jeelab.model.entity.FacilityType;
import jeelab.model.entity.SportsCentre;

@JsonInclude(Include.NON_NULL)
public class FacilityResponse {

	private Long id;
	private String url;
	private SportCentreResponse centre;
	private FacilityTypeResponse type;
	private List<HoursResponse> hours;
	
	private AddressStorage address;
	
	public FacilityResponse(AddressStorage address) {
		this.address = address;
	}
	
	public FacilityResponse id(Long id) {
		this.id = id;
		return this;
	}
	
	public FacilityResponse url(String url) {
		this.url = url;
		return this;
	}
	
	public FacilityResponse centre(SportsCentre centre) {
		this.centre = new SportCentreResponse()
				.id(centre.getId())
				.url(address.centre(centre.getId()));
		return this;
	}
	
	public FacilityResponse type(FacilityType type) {
		this.type = new FacilityTypeResponse()
				.id(type.getId())
				.url(address.facilityType(type.getId()))
				.name(type.getName());
		return this;
	}
	
	public FacilityResponse hours(Collection<BusinessHours> hours) {
		if (hours == null || hours.isEmpty()) return this;
		this.hours = new ArrayList<HoursResponse>();
		for (BusinessHours h : hours) {
			this.hours.add(new HoursResponse()
					.id(h.getId())
					.url(address.facilityHours(id, h.getId()))
					.day(h.getDay())
					.open(h.getOpenTime())
					.close(h.getCloseTime())
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
	public SportCentreResponse getCentre() {
		return centre;
	}
	public void setCentre(SportCentreResponse centre) {
		this.centre = centre;
	}
	public FacilityTypeResponse getType() {
		return type;
	}
	public void setType(FacilityTypeResponse type) {
		this.type = type;
	}
	public List<HoursResponse> getHours() {
		return hours;
	}
	public void setHours(List<HoursResponse> hours) {
		this.hours = hours;
	}
		
}
