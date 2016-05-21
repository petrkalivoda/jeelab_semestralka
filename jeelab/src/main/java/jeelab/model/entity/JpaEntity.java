package jeelab.model.entity;

import java.io.Serializable;

public interface JpaEntity extends Serializable {

	public Long getId();
	public void setId(Long id);
	
}
