package jeelab.model.dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import jeelab.model.entity.Role;
import jeelab.model.entity.User;

/**
 * Logika spojena s uzivatelem
 * @author Vaclav Dobes
 *
 */
@Stateless
public class UserDao {

    @PersistenceContext(unitName = "jee")
    private EntityManager manager;

    /**
     * Seznam uzivatelu serazeny podle prijmeni
     * @return
     */
    @Produces
    public List<User> getUsers() {
        return manager
                .createQuery("select user from User user order by user.lastname", User.class)
                .getResultList();
    }
    
    public void save(User user) {
    	manager.persist(user);
    	manager.flush();
    }
    
    /**
     * Vraci uzivatele podle emailu
     * @param email
     * @return
     */
    public User getbyEmail(String email) {
    	return manager
    			.createQuery("select user from User user where user.email = ?", User.class)
    			.setParameter(1, email)
    			.getSingleResult();
    }
    
    /**
     * Vraci entitu Role podle jejiho jmena
     * @param role
     * @return
     */
    public Role getRoleByName(String role) {
    	return manager
    			.createQuery("select role from Role role where role.name = ?", Role.class)
    			.setParameter(1, role)
    			.getSingleResult();
    }
    
    public User get(long id) {
    	return manager.find(User.class, id);
    }
	
}
