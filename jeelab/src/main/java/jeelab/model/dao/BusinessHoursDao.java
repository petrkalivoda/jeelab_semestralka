package jeelab.model.dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

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
    	CriteriaBuilder cb = manager.getCriteriaBuilder();
    	
    	CriteriaQuery<BusinessHours> cq = cb.createQuery(BusinessHours.class);
    	Root<BusinessHours> root = cq.from(BusinessHours.class);
    	cq.orderBy(cb.asc(root.get("day")));
    	
        TypedQuery<BusinessHours> q = manager.createQuery(cq);
        
        if(max != null && !max.equals(0L)) {
        	q.setMaxResults(max.intValue());
        }
        
        if(offset != null && !offset.equals(0L)) {
        	q.setFirstResult(offset.intValue());
        }
        
        return q.getResultList();
    }
    
    /**
     * Total number of BusinessHourss
     * @return
     */
    public Long getBusinessHoursCount(){
    	return (Long) manager.createQuery("select count(bh) from BusinessHours bh").getSingleResult();
    }

	public BusinessHours getFacilityHoursForDay(Long facilityId, int dayOfWeek) {
		List<BusinessHours> hoursList =  manager.createQuery("select bh from BusinessHours bh join bh.sportsCentreFacilities scf where scf.id=:facilityId "
								+ "and bh.day=:day", 
								BusinessHours.class)
				.setParameter("facilityId", facilityId)
				.setParameter("day", dayOfWeek)
				.getResultList();
		
		return hoursList.size() == 0 ? null : hoursList.get(0);
	}
}
