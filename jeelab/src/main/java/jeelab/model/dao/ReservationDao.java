package jeelab.model.dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import jeelab.model.builder.ReservationBuilder;
import jeelab.model.entity.Reservation;
import jeelab.model.entity.SportsCentre;
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
	
	@PersistenceContext(unitName = "jee")
    private EntityManager manager;
	
	public void save(Reservation reservation) {
    	manager.persist(reservation);
    	manager.flush();
    }
	
	public void updateReservation(long id, ReservationForm form) {
		Reservation reservation = manager.find(Reservation.class, id);
		if(reservation != null){
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
                			+ "order by reservation.date"
                			+ "limit :offset,:max",
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
        return manager
                .createQuery("select reservation from Reservation reservation where reservation.user.id = :userId"
                			+ "order by reservation.date"
                			+ "limit :offset,:max", 
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
