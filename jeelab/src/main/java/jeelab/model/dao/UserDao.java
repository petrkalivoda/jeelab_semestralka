package jeelab.model.dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import exception.UserUnavailableException;
import jeelab.model.builder.UserBuilder;
import jeelab.model.entity.User;
import jeelab.view.UserForm;

/**
 * Logika spojena s uzivatelem
 * @author Vaclav Dobes
 *
 */
@Stateless
public class UserDao {
	
	@Inject
	private UserBuilder userBuilder;

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
    
    public void save(User user) throws UserUnavailableException {
    	if(getbyEmail(user.getEmail()) != null){
    		throw new UserUnavailableException();
    	}
    	
    	manager.persist(user);
    	manager.flush();
    }
    
    public void updateUser(long id, UserForm form) throws UserUnavailableException {
		User user = manager.find(User.class, id);
		if(!user.getEmail().equals(form.getEmail()) && getbyEmail(form.getEmail()) != null){
			throw new UserUnavailableException();
		}
		
		if(user != null){
			userBuilder
				.firstname(form.getFirstName())
				.lastname(form.getLastName())
				.email(form.getLastName())
				.password(form.getPassword());
		}
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
    
    public User get(long id) {
    	return manager.find(User.class, id);
    }
	
}
