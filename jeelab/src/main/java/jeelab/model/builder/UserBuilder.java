package jeelab.model.builder;

import java.util.ArrayList;
import java.util.Collection;

import javax.inject.Inject;

import org.apache.commons.codec.digest.DigestUtils;

import jeelab.model.dao.RoleDao;
import jeelab.model.entity.Role;
import jeelab.model.entity.User;

public class UserBuilder implements EntityBuilder<User> {
	
	public static final String ROLE_USER = "ROLE_USER";
	
	private @Inject RoleDao roleDao;
	
	public String firstname;
	public String lastname;
	public String email;
	public String hash;
	public Collection<Role> roles;
	
	public UserBuilder firstname(String firstname) {
		this.firstname = firstname;
		return this;
	}
	
	public UserBuilder lastname(String lastname) {
		this.lastname = lastname;
		return this;
	}
		
	public UserBuilder email(String email) {
		this.email = email;
		return this;
	}
	
	public UserBuilder password(String password) {
		this.hash = DigestUtils.sha256Hex(password);
		return this;
	}
	
	public UserBuilder setRole(String roleName) {
		Role role = roleDao.getByName(roleName);
		if (role == null)
			return this;
		if (this.roles == null)
			this.roles = new ArrayList<Role>();
		this.roles.add(role);
		return this;
	}

	@Override
	public User build(User entity) {
		if (firstname != null)
			entity.setFirstname(firstname);
		if (lastname != null)
			entity.setLastname(lastname);
		if (email != null)
			entity.setEmail(email);
		if (hash != null)
			entity.setPassword(hash);
		if (roles != null)
			entity.setRoles(roles);
		return entity;
	}

	@Override
	public User build() {
		User entity = new User();
		return build(entity);
	}

	@Override
	public void clear() {
		firstname = null;
		lastname = null;
		email = null;
		hash = null;
	}

	
	
}
