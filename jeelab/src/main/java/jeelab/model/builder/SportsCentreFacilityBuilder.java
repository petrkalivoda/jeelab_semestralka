package jeelab.model.builder;

import java.util.ArrayList;
import java.util.List;

import javax.enterprise.inject.Instance;
import javax.inject.Inject;

import jeelab.model.dao.SportsCentreDao;
import jeelab.model.entity.BusinessHours;
import jeelab.model.entity.FacilityType;
import jeelab.model.entity.SportsCentre;
import jeelab.model.entity.SportsCentreFacility;
import jeelab.view.HoursForm;

public class SportsCentreFacilityBuilder implements EntityBuilder<SportsCentreFacility> {
	
	// !!! FIXME !!! next time don't create circular dependencies
	private @Inject Instance<SportsCentreDao> sportsDao;
	
	private SportsCentre centre;
	private FacilityType type;
	private List<BusinessHours> hours;

	public SportsCentreFacilityBuilder centre(Long id) {
		centre = sportsDao.get().getCentre(id);
		return this;
	}
	
	public SportsCentreFacilityBuilder type(Long id) {
		type = sportsDao.get().getFacilityType(id);
		return this;
	}
	
	// TODO predelat - nehezky :(
	public SportsCentreFacilityBuilder hours(List<HoursForm> hours) {
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
	
	public SportsCentreFacilityBuilder hoursList(List<BusinessHours> hours) {
		this.hours = hours;
		return this;
	}
	
	@Override
	public SportsCentreFacility build(SportsCentreFacility entity) {
		if (centre != null)
			entity.setSportsCentre(centre);
		if (type != null)
			entity.setFacilityType(type);
		if (hours != null)
			entity.setBusinessHours(hours);
		return entity;
	}

	@Override
	public SportsCentreFacility build() {
		SportsCentreFacility facility = new SportsCentreFacility();
		return build(facility);
	}

	@Override
	public void clear() {
		centre = null;
		type = null;
		hours = null;
	}

}
