package jeelab.view;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class HoursForm {
	
	@NotNull(message = "validation.hours.day.null")
	@Min(value = 1, message = "validation.hours.day.min")
	@Max(value = 7, message = "validation.hours.day.max")
	private Integer day;
	
	@NotNull(message = "validation.hours.open.null")
	@Min(value = 0, message = "validation.hours.open.min")
	@Max(value = 23, message = "validation.hours.open.max")
	private Float open;
	
	@NotNull(message = "validation.hours.close.null")
	@Min(value = 0, message = "validation.hours.close.min")
	@Max(value = 23, message = "validation.hours.close.max")
	private Float close;
	
	public Integer getDay() {
		return day;
	}
	public void setDay(Integer day) {
		this.day = day;
	}
	public Float getOpen() {
		return open;
	}
	public void setOpen(Float open) {
		this.open = open;
	}
	public Float getClose() {
		return close;
	}
	public void setClose(Float close) {
		this.close = close;
	}
	
}
