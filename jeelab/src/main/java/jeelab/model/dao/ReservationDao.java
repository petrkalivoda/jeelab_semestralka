package jeelab.model.dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import jeelab.model.entity.Reservation;

/**
 * Rezervace
 * @author Vaclav Dobes
 *
 */
@Stateless
public class ReservationDao {

	@PersistenceContext(unitName = "jee")
    private EntityManager manager;
	
	public void save(Reservation reservation) {
    	manager.persist(reservation);
    	manager.flush();
    }
	
	/**
	 * Vsechny Rezervace
	 * @param userId
	 * @return
	 */
	//TODO strankovani
    public List<Reservation> getReservations() {
        return manager
                .createQuery("select reservation from Reservation reservation", Reservation.class)
                .getResultList();
    }
	
	/**
	 * Rezervace podle uzivatele
	 * @param userId
	 * @return
	 */
	//TODO strankovani
    public List<Reservation> getUserReservations(Long userId) {
        return manager
                .createQuery("select reservation from Reservation reservation where reservation.user.id = ?", Reservation.class)
                .setParameter(1, userId)
                .getResultList();
    }
	
}
