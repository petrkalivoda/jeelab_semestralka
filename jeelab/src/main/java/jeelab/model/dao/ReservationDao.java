package jeelab.model.dao;

import java.util.List;

import javax.annotation.security.DeclareRoles;
import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import jeelab.model.entity.Reservation;
import jeelab.model.entity.User;

/**
 * Rezervace
 * @author Vaclav Dobes
 *
 */
@Stateless
public class ReservationDao {

	@PersistenceContext(unitName = "jee")
    private EntityManager manager;
	
	//@RolesAllowed("gooduser")
	public void save(Reservation reservation) {
    	manager.persist(reservation);
    	manager.flush();
    }
	
	/**
	 * Rezervace podle uzivatele
	 * @param userId
	 * @return
	 */
    public List<Reservation> getUserReservations(Long userId) {
        return manager
                .createQuery("select reservation from Reservation reservation where reservation.user.id = ?", Reservation.class)
                .setParameter(1, userId)
                .getResultList();
//		User user = manager.find(User.class, userId);
//		return user.getReservations();
    }
	
}
