package jeelab.model.dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TemporalType;

import org.joda.time.DateTime;

import jeelab.exception.ReservationUnavailableException;
import jeelab.model.builder.ReservationBuilder;
import jeelab.model.entity.BusinessHours;
import jeelab.model.entity.Reservation;
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
		return ((Long) manager.createQuery("select count(reservation) from Reservation reservation "
							+ "where reservation.sportsCentreFacility=:res "
							+ "and reservation.date=:date "
							+ "and reservation.startTime<:resStart " //interval check x1 < y2 && y1 < x2
							+ "and reservation.end>:resEnd")
		.setParameter("res", reservation)
		.setParameter("date", reservation.getDate(), TemporalType.DATE)
		.setParameter("resStart", reservation.getFrom())
		.setParameter("resEnd", reservation.getTo())
		.getSingleResult()
		) == 0;
	}
	
	private boolean isOutOfHours(Reservation reservation){
		DateTime dt = new DateTime(reservation.getDate().getTime());
		BusinessHours hours = businessHoursDao.getFacilityHoursForDay(reservation.getSportsCentreFacility().getId(), dt.getDayOfWeek());
		
		if(hours == null){
			return false;
		}
		
		return hours.getOpenTime() <= reservation.getFrom() && hours.getCloseTime() >= reservation.getTo();
	}
	
	public void save(Reservation reservation) throws ReservationUnavailableException {
		if(!isAvailable(reservation) || isOutOfHours(reservation)){
			throw new ReservationUnavailableException();
		}
		
		manager.persist(reservation);
    	manager.flush();
    }
	
	public void updateReservation(long id, ReservationForm form) throws ReservationUnavailableException {
		Reservation reservation = manager.find(Reservation.class, id);
		if(reservation != null){
			if(!isAvailable(reservation) || isOutOfHours(reservation)){
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
        return manager
                .createQuery("select reservation from Reservation reservation "
                			+ "order by reservation.date "
                			+ (max > 0 ? "limit :offset,:max" : ""), 
                			Reservation.class)
                .setParameter("offset", offset)
                .setParameter("max", max)
                .getResultList();
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
    	/// !!!FIXME!!! use Optional<Long> or method overloading or something!
        return manager
                .createQuery("select reservation from Reservation reservation where reservation.user.id = :userId "
                			+ "order by reservation.date "
                			+ (max > 0 ? "limit :offset,:max" : ""), 
                			Reservation.class)
                .setParameter("userId", userId)
                .setParameter("offset", offset)
                .setParameter("max", max)
                .getResultList();
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
