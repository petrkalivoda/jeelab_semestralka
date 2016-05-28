package jeelab.ws.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class UserResponse {
	
	private Long id;
	private String url;
	private String firstname;
	private String lastname;
	private String email;
	
	public UserResponse id(long id) {
		this.id = id;
		return this;
	}
	
	public UserResponse url(String url) {
		this.url = url;
		return this;
	}
	
	public UserResponse firstname(String firstname) {
		this.firstname = firstname;
		return this;
	}
	
	public UserResponse lastname(String lastname) {
		this.lastname = lastname;
		return this;
	}
	
	public UserResponse email(String email) {
		this.email = email;
		return this;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
		
}
