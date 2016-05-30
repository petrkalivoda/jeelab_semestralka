package jeelab.model.dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import jeelab.model.entity.Role;

/**
 * DAO pro role
 * @author Petr Kalivoda
 *
 */
@Stateless
public class RoleDao {
	
	@PersistenceContext(unitName="jee")
	private EntityManager entityManager;
	
	/**
     * Vraci entitu Role podle jejiho jmena
     * @param role
     * @return
     */
    public Role getByName(String role) {
    	List<Role> roles = entityManager
    			.createQuery("select role from Role role where role.name = ?", Role.class)
    			.setParameter(1, role)
    			.getResultList();
    	
    	return roles.isEmpty() ? null : roles.get(0);
    }
    
    public void save(Role role) {
    	entityManager.persist(role);
    	entityManager.flush();
    }
    
    /**
     * Returns Role by id
     * @param id
     * @return
     */
    public Role get(Long id) {
    	return entityManager.find(Role.class, id);
    } 
}