package jeelab.view;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class UserForm {
	
	@NotNull(message = "validation.user.firstName.null")
	@NotBlank(message = "validation.user.firstName.null")
	private String firstName;
	
	@NotNull(message = "validation.user.lastName.null")
	@NotBlank(message = "validation.user.lastName.null")
	private String lastName;
	
	@NotNull(message = "validation.user.email.null")
	@NotBlank(message = "validation.user.email.null")
	private String email;
	
	@NotNull(message = "validation.user.password.null")
	@NotBlank(message = "validation.user.password.null")
	private String password;

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
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
