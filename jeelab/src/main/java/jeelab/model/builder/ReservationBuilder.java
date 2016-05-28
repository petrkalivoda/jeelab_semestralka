package jeelab.model.builder;

import java.sql.Date;

import javax.inject.Inject;

import jeelab.model.dao.SportsCentreDao;
import jeelab.model.dao.UserDao;
import jeelab.model.entity.Reservation;
import jeelab.model.entity.SportsCentreFacility;
import jeelab.model.entity.User;

public class ReservationBuilder implements EntityBuilder<Reservation> {
	
	@Inject
	private UserDao userDao;
	@Inject
	private SportsCentreDao sportCentreDao;
	
	private Date date;
	private Float from;
	private Float to;
	private User user;
	private SportsCentreFacility sportsCentreFacility;
	
	public ReservationBuilder date(java.util.Date date) {
		if (date != null)
			this.date = new Date(date.getTime());
		return this;
	}
	
	public ReservationBuilder from(float from) {
		this.from = from;
		return this;
	}
	
	public ReservationBuilder to(float to) {
		this.to = to;
		return this;
	}
	
	public ReservationBuilder user(long id) {
		this.user = this.userDao.get(id);
		return this;
	}
	
	public ReservationBuilder sportsCentreFacility(long id) {
		this.sportsCentreFacility = this.sportCentreDao.getFacility(id);
		return this;
	}
	
	@Override
	public Reservation build(Reservation entity) {
		if (date != null)
			entity.setDate(date);
		if (from != null)
			entity.setFrom(from);
		if (to != null)
			entity.setTo(to);
		if (user != null)
			entity.setUser(user);
		if (sportsCentreFacility != null)
			entity.setSportsCentreFacility(sportsCentreFacility);
		return entity;
	}

	@Override
	public Reservation build() {
		Reservation entity = new Reservation();
		return build(entity);
	}

	@Override
	public void clear() {
		date = null;
		from = null;
		to = null;
		user = null;
		sportsCentreFacility = null;
	}

}
