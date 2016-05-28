package jeelab.view;

import javax.validation.constraints.NotNull;

public class FacilityTypeForm {

	@NotNull(message = "validation.type.name.null")
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}
