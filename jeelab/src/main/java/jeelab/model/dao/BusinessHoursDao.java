package jeelab.model.dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import jeelab.model.entity.BusinessHours;
import jeelab.view.HoursForm;

/**
 * Business hours
 * @author Oscar Hernandez
 *
 */
@Stateless
public class BusinessHoursDao {
	
	@PersistenceContext(unitName = "jee")
    private EntityManager manager;
	
	public void save(BusinessHours businessHours){
    	manager.persist(businessHours);
    	manager.flush();
    }
	
	public void updateBusinessHours(long id, HoursForm form) {
		BusinessHours bh = manager.find(BusinessHours.class, id);
		if(bh != null){
			bh.setDay(form.getDay());
			bh.setOpenTime(form.getOpen());
			bh.setCloseTime(form.getClose());
		}
	}
	
	public void deleteBusinessHours(long id) {
		BusinessHours bh = manager.find(BusinessHours.class, id);
		if (bh != null){
			manager.remove(bh);
		}
	}
	
	/**
	 * Get BusinessHours by id
	 * @param id
	 * @return
	 */
	public BusinessHours getBusinessHours(Long id){
    	return manager.find(BusinessHours.class, id);
    }
	
	/**
	 * All Business hours
	 * @param max
	 * @param offset
	 * @return
	 */
    public List<BusinessHours> getBusinessHours(Long max, Long offset) {
        return manager
                .createQuery("select bh from BusinessHours bh "
                			+ "order by bh.date "
                			+ (max > 0 ? "limit :offset,:max" : ""),
                			BusinessHours.class)
                .setParameter("offset", offset)
                .setParameter("max", max)
                .getResultList();
    }
    
    /**
     * Total number of BusinessHourss
     * @return
     */
    public Long getBusinessHoursCount(){
    	return (Long) manager.createQuery("select count(bh) from BusinessHours bh").getSingleResult();
    }
	
	/**
	 * Business hours by centre
	 * @param centreId
	 * @param max
	 * @param offset
	 * @return
	 */
    public List<BusinessHours> getCentreBusinessHours(Long centreId, Long max, Long offset) {
        return manager
                .createQuery("select bh from BusinessHours bh join bh.sportsCentres sc where sc.id=:centreId "
                			+ "order by bh.date "
                			+ (max > 0 ? "limit :offset,:max" : ""),
                			BusinessHours.class)
                .setParameter("centreId", centreId)
                .setParameter("offset", offset)
                .setParameter("max", max)
                .getResultList();
    }
    
    /**
     * Total number of BusinessHours for centre
     * @param userId
     * @return
     */
    public Long getCentreBusinessHoursCount(Long centreId){
    	return (Long) manager.createQuery("select count(bh) from BusinessHours bh join bh.sportsCentres sc where sc.id=:centreId")
    			.setParameter("centreId", centreId)
    			.getSingleResult();
    }
	
    /**
	 * Business hours by facility
	 * @param facilityId
	 * @param max
	 * @param offset
	 * @return
	 */
    public List<BusinessHours> getFacilityBusinessHours(Long facilityId, Long max, Long offset) {
        return manager
                .createQuery("select bh from BusinessHours bh join bh.sportsCentreFacilities scf where scf.id=:facilityId "
                			+ "order by bh.date "
                			+ (max > 0 ? "limit :offset,:max" : ""), 
                			BusinessHours.class)
                .setParameter("facilityId", facilityId)
                .setParameter("offset", offset)
                .setParameter("max", max)
                .getResultList();
    }
    
    /**
     * Total number of BusinessHours for facility
     * @param userId
     * @return
     */
    public Long getFacilityBusinessHoursCount(Long facilityId){
    	return (Long) manager.createQuery("select count(bh) from BusinessHours bh join bh.sportsCentreFacilities scf where scf.id=:facilityId")
    			.setParameter("facilityId", facilityId)
    			.getSingleResult();
    }

	public BusinessHours getFacilityHoursForDay(Long facilityId, int dayOfWeek) {
		return manager.createQuery("select bh from BusinessHours bh join bh.sportsCentreFacilities scf where scf.id=:facilityId "
								+ "and bh.day=:day", 
								BusinessHours.class)
				.setParameter("facilityId", facilityId)
				.setParameter("day", dayOfWeek)
				.getSingleResult();
	}
}
