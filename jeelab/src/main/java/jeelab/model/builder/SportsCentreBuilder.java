package jeelab.model.builder;

import java.util.ArrayList;
import java.util.List;

import jeelab.model.entity.BusinessHours;
import jeelab.model.entity.SportsCentre;
import jeelab.view.HoursForm;

public class SportsCentreBuilder implements EntityBuilder<SportsCentre> {

	private String street;
	private String city;
	private String country;
	private String building;
	private String phone;
	private List<BusinessHours> hours;
	
	public SportsCentreBuilder street(String street) {
		this.street = street;
		return this;
	}
	
	public SportsCentreBuilder city(String city) {
		this.city = city;
		return this;
	}
	
	public SportsCentreBuilder country(String country) {
		this.country = country;
		return this;
	}
	
	public SportsCentreBuilder building(String building) {
		this.building = building;
		return this;
	}
	
	public SportsCentreBuilder phone(String phone) {
		this.phone = phone;
		return this;
	}
	
	// TODO predelat - nehezky :(
	public SportsCentreBuilder hours(List<HoursForm> hours) {
		if (hours == null || hours.isEmpty()) return this;
		this.hours = new ArrayList<BusinessHours>();
		BusinessHours entityHour = null;
		for (HoursForm h : hours) {
			entityHour = new BusinessHours();
			entityHour.setDay(h.getDay());
			entityHour.setOpenTime(h.getOpen());
			entityHour.setCloseTime(h.getClose());
			this.hours.add(entityHour);
		}
		return this;
	}
	
	@Override
	public SportsCentre build(SportsCentre entity) {
		if (street != null)
			entity.setStreet(street);
		if (city != null)
			entity.setCity(city);
		if (country != null)
			entity.setCountry(country);
		if (building != null)
			entity.setBuildingNumber(building);
		if (phone != null)
			entity.setPhoneNumber(phone);
		if (hours != null)
			entity.setBusinessHours(hours);
		return entity;
	}

	@Override
	public SportsCentre build() {
		SportsCentre entity = new SportsCentre();
		return build(entity);
	}

	@Override
	public void clear() {
		street = null;
		city = null;
		country = null;
		building = null;
		phone = null;
		hours = null;
	}

	
	
}
