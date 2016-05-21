package jeelab.model.dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import jeelab.model.entity.User;

@Stateless
public class UserDao {

    @PersistenceContext(unitName = "jee")
    private EntityManager manager;

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
    
    public User getbyEmail(String email) {
    	return manager.createQuery("select user from User user where user.email = ?", User.class).setParameter(1, email).getSingleResult();
    }
    
    public User get(Long id) {
    	return manager.find(User.class, id);
    }
	
}
