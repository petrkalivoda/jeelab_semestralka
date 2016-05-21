package jeelab.view;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;

public class RegistrationForm {

	@NotNull(message = "validation.user.firstname.null")
	private String firstname;
	
	@NotNull(message = "validation.user.lastname.null")
	private String lastname;

	@NotNull(message = "validation.user.email.null")
	@Email(message = "validation.user.email.pattern")
	private String email;
	
	@NotNull(message = "validation.user.password.null")
	@Size(min = 5, message = "validation.user.password.size")
	private String password;
	
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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
			
}
