package jeelab.view;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class SportCentreForm {

	@NotNull(message = "validation.centre.number.null")
	private String buildingNumber;
	
	@NotNull(message = "validation.centre.city.null")
	private String city;
	
	@NotNull(message = "validation.centre.street.null")
	private String street;
	
	@NotNull(message = "validation.centre.country.null")
	private String country;
	
	@NotNull(message = "validation.centre.phone.null")
	//@Phone()
	private String phone;
	
	//@NotNull(message = "validation.centre.hours.null")
	@Size(min = 1, max = 7, message = "validation.hours.size")
	@Valid
	private List<HoursForm> hours;

	public String getBuildingNumber() {
		return buildingNumber;
	}

	public void setBuildingNumber(String buildingNumber) {
		this.buildingNumber = buildingNumber;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
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

	public List<HoursForm> getHours() {
		return hours;
	}

	public void setHours(List<HoursForm> hours) {
		this.hours = hours;
	}
		
}
