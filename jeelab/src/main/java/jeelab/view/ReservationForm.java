package jeelab.view;

import java.util.Date;

import javax.validation.constraints.NotNull;

public class ReservationForm {

	@NotNull(message = "validation.reservation.user.null")
	private Long user;
	
	@NotNull(message = "validation.reservation.date.null")
	private Date date;
	
	@NotNull(message = "validation.reservation.from.null")
	private Float from;
	
	@NotNull(message = "validation.reservation.to.null")
	private Float to;

	public Long getUser() {
		return user;
	}

	public void setUser(Long user) {
		this.user = user;
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
	
}
