package jeelab.model.dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.enterprise.inject.Model;
import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import jeelab.model.entity.User;

@Stateless
public class UserDao {

    @PersistenceContext
    private EntityManager manager;

    @Produces
    @Model
    public List<User> getUsers() {
        return manager
                .createQuery("select user from User user order by user.lastname", User.class)
                .getResultList();
    }
    
    public void save(User user) {
    	manager.persist(user);
    }
	
}
