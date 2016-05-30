package jeelab.model.dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import exception.DependentEntityException;
import jeelab.model.builder.SportsCentreBuilder;
import jeelab.model.entity.FacilityType;
import jeelab.model.entity.SportsCentre;
import jeelab.model.entity.SportsCentreFacility;
import jeelab.view.SportCentreForm;

@Stateless
public class SportsCentreDao {
	
	@Inject
	private SportsCentreBuilder sportsBuilder;

	@PersistenceContext(unitName = "jee")
    private EntityManager manager;
	
	public void saveCentre(SportsCentre centre) {
		manager.persist(centre);
		manager.flush();
	}
	
	public void updateCentre(long id, SportCentreForm form) {
		SportsCentre centre = manager.find(SportsCentre.class, id);
		if (centre != null)
			sportsBuilder
				.city(form.getCity())
				.street(form.getStreet())
				.country(form.getCountry())
				.phone(form.getPhone())
				.building(form.getBuildingNumber())
				.build(centre);
	}
	
	public void deleteCentre(long id) {
		SportsCentre centre = manager.find(SportsCentre.class, id);
		if (centre != null)
			manager.remove(centre);
	}
	
	public SportsCentre getCentre(long id) {
    	return manager.find(SportsCentre.class, id);
    }
	
	public SportsCentre getCompleteCentre(long id) {
    	SportsCentre centre = manager.find(SportsCentre.class, id);
    	if (centre != null) {
    		centre.getBusinessHours().size();
    		centre.getFacilities().size();
    		for (SportsCentreFacility f : centre.getFacilities()) {
    			f.getBusinessHours().size();
    		}
    	}
		return centre;
    }
	
	public List<SportsCentre> getAllCentre() {
		return manager.createQuery("select centre from SportsCentre centre", SportsCentre.class).getResultList();
	}
	
	public List<SportsCentreFacility> getCentreFacilites(long centreId){
		return manager.createQuery("select scf from SportsCentreFacility scf where scf.sportsCentre=:centreId", 
									SportsCentreFacility.class)
				.setParameter("centreId", centreId)
				.getResultList();
	}

	public SportsCentreFacility getFacility(long id) {
    	return manager.find(SportsCentreFacility.class, id);
    }
	
	public void saveFacilityType(FacilityType type) {
		manager.persist(type);
		manager.flush();
	}
	
	public void updateFacilityTypeName(long id, String name) {
		FacilityType type = manager.find(FacilityType.class, id);
		if (type != null)
			type.setName(name);
	}
	
	public void deleteFacilityType(long id) {
		FacilityType type = manager.find(FacilityType.class, id);
		if (type.getFacilities().size() > 0)
			throw new DependentEntityException();
		if (type != null)
			manager.remove(type);
	}
	
	public FacilityType getFacilityType(long id) {
		return manager.find(FacilityType.class, id);
	}
	
	public void saveFacility(SportsCentreFacility facility) {
		manager.persist(facility);
		manager.flush();
	}
	
	/**
	 * Seznam zarizeni (bowling, tenis...)
	 * @return
	 */
	public List<FacilityType> getFacilityTypes() {
		return manager
				.createQuery("select type from FacilityType type order by type.name", FacilityType.class)
				.getResultList();
	}
	
}
