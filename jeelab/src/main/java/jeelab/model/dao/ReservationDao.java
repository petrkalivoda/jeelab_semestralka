package jeelab.model.dao;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TemporalType;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import jeelab.exception.ReservationUnavailableException;
import jeelab.model.builder.ReservationBuilder;
import jeelab.model.entity.BusinessHours;
import jeelab.model.entity.Reservation;
import jeelab.model.entity.SportsCentreFacility;
import jeelab.view.ReservationForm;

/**
 * Rezervace
 * @author Vaclav Dobes
 *
 */
@Stateless
public class ReservationDao {

	@Inject
	private ReservationBuilder reservationBuilder;
	
	@Inject
	private BusinessHoursDao businessHoursDao;
	
	@PersistenceContext(unitName = "jee")
    private EntityManager manager;
	
	private boolean isAvailable(Reservation reservation){
		return isAvailable(reservation.getId(), reservation.getSportsCentreFacility(), reservation.getDate(), reservation.getFrom(), reservation.getTo());
	}
	
	private boolean isAvailable(Long id, SportsCentreFacility facility , Date date, Float from, Float to){
		Query query = manager.createQuery("select count(reservation) from Reservation reservation "
				+ "where reservation.sportsCentreFacility = :facility "
				+ (id != null ? "and reservation.id <> :resId " : "")
				+ "and reservation.date = :date "
				+ "and reservation.from < :resEnd " 
				+ "and reservation.to > :resStart");
		
		if(id != null){
			query.setParameter("resId", id);
		}
		
		return ((Long) query
				.setParameter("facility", facility)
				.setParameter("date", date, TemporalType.DATE)
				.setParameter("resStart", from)
				.setParameter("resEnd", to)
				.getSingleResult()
				).equals(0L);
	}
	
	private boolean isOutOfHours(Reservation reservation){
		return isOutOfHours(reservation.getId(), reservation.getSportsCentreFacility(), reservation.getDate(), reservation.getFrom(), reservation.getTo());
	}
	
	private boolean isOutOfHours(Long id, SportsCentreFacility facility , Date date, Float from, Float to){
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		
		BusinessHours hours = businessHoursDao.getFacilityHoursForDay(facility.getId(), c.get(Calendar.DAY_OF_WEEK));

		if(hours == null){
			return true;
		}
		
		return !(hours.getOpenTime() <= from && hours.getCloseTime() >= to);
	}
	
	public void save(Reservation reservation) throws ReservationUnavailableException 
		if(!isAvailable(reservation) || isOutOfHours(reservation)){
			throw new ReservationUnavailableException();
		}
		
		manager.persist(reservation);
    	manager.flush();
    }
	
	public void updateReservation(long id, ReservationForm form) throws ReservationUnavailableException {
		Reservation reservation = manager.find(Reservation.class, id);
		
		if(reservation != null){
			if(!isAvailable(reservation.getId(), reservation.getSportsCentreFacility(), form.getDate(), form.getFrom(), form.getTo()) 
					|| isOutOfHours(reservation.getId(), reservation.getSportsCentreFacility(), form.getDate(), form.getFrom(), form.getTo())){
				throw new ReservationUnavailableException();
			}
			
			reservationBuilder
				.date(form.getDate())
				.from(form.getFrom())
				.to(form.getTo())
				.user(form.getUser())
				.sportsCentreFacility(form.getCentreFacility())
				.build(reservation);
		}
	}
	
	public void deleteReservation(long id) {
		Reservation reservation = manager.find(Reservation.class, id);
		if (reservation != null){
			manager.remove(reservation);
		}
	}
	
	/**
	 * Get reservation by id
	 * @param id
	 * @return
	 */
	public Reservation getReservation(Long id){
    	return manager.find(Reservation.class, id);
    }
	
	/**
	 * Vsechny Rezervace
	 * @param userId
	 * @return
	 */
    public List<Reservation> getReservations(Long max, Long offset) {    	
    	CriteriaBuilder cb = manager.getCriteriaBuilder();
    	
    	CriteriaQuery<Reservation> cq = cb.createQuery(Reservation.class);
    	Root<Reservation> root = cq.from(Reservation.class);
    	cq.orderBy(cb.desc(root.get("date")));
    	
    	TypedQuery<Reservation> q = manager.createQuery(cq);
    	
    	if(max != null && !max.equals(0L)) {
        	q.setMaxResults(max.intValue());
        }
        
        if(offset != null && !offset.equals(0L)) {
        	q.setFirstResult(offset.intValue());
        }
        
        return q.getResultList();
    }
    
    /**
     * Total number of reservations
     * @return
     */
    public Long getReservationsCount(){
    	return (Long) manager.createQuery("select count(reservation) from Reservation reservation").getSingleResult();
    }
	
	/**
	 * Rezervace podle uzivatele
	 * @param userId
	 * @return
	 */
    public List<Reservation> getUserReservations(Long userId, Long max, Long offset) {
    	CriteriaBuilder cb = manager.getCriteriaBuilder();
    	
    	CriteriaQuery<Reservation> cq = cb.createQuery(Reservation.class);
    	Root<Reservation> root = cq.from(Reservation.class);
    	
    	cq.orderBy(cb.desc(root.get("date")));
    	cq.where(cb.equal(root.join("user").get("id"), userId));
    	
    	TypedQuery<Reservation> q = manager.createQuery(cq);
    	
    	if(max != null && !max.equals(0L)) {
        	q.setMaxResults(max.intValue());
        }
        
        if(offset != null && !offset.equals(0L)) {
        	q.setFirstResult(offset.intValue());
        }
        
        return q.getResultList();
    }
    
    /**
     * Total number of reservation for user
     * @param userId
     * @return
     */
    public Long getUserReservationsCount(Long userId){
    	return (Long) manager.createQuery("select count(reservation) from Reservation reservation where reservation.user.id = :userId")
    			.setParameter("userId", userId)
    			.getSingleResult();
    }
	
}
