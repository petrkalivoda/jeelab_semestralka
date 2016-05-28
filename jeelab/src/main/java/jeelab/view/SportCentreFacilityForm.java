package jeelab.view;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class SportCentreFacilityForm {

	//mame v url
//	@NotNull(message = "validation.centre.null")
//	private Long centre;
	
	@NotNull(message = "validation.type.null")
	private Long type;
	
	@Size(min = 1, max = 7, message = "validation.hours.size")
	@Valid
	private List<HoursForm> hours;

	public Long getType() {
		return type;
	}

	public void setType(Long type) {
		this.type = type;
	}

	public List<HoursForm> getHours() {
		return hours;
	}

	public void setHours(List<HoursForm> hours) {
		this.hours = hours;
	}
		
}
