package jeelab.model.builder;

import java.util.Collection;

import jeelab.model.entity.Role;
import jeelab.model.entity.User;

/**
 * Builder for Role
 * @author Petr Kalivoda
 *
 */
public class RoleBuilder implements EntityBuilder<Role> {
	
	private Long id;
	private String name;
	private Collection<User> users;
	
	public RoleBuilder id(Long id) {
		this.id = id;
		return this;
	}
	
	public RoleBuilder name(String name) {
		this.name = name;
		return this;
	}
	
	public RoleBuilder users(Collection<User> users) {
		this.users = users;
		return this;
	}

	@Override
	public Role build(Role entity) {
		if(id != null) {
			entity.setId(id);
		}
		
		if(name != null) {
			entity.setName(name);
		}
		
		if(users != null) {
			entity.setUsers(users);
		}
		
		return entity;
	}

	@Override
	public Role build() {
		return build(new Role());
	}

	@Override
	public void clear() {
		id = null;
		name = null;
		users = null;
	}

}
