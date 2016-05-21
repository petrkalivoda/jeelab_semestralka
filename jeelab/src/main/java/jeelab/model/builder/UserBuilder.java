package jeelab.model.builder;

import jeelab.model.entity.User;

public class UserBuilder implements EntityBuilder<User> {
	
	public String firstname;
	public String lastname;
	public String email;
	public String hash;
	
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
		// TODO generate hash
		this.hash = password;
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
		return entity;
	}

	@Override
	public User build() {
		User entity = new User();
		return build(entity);
	}

	
	
}
