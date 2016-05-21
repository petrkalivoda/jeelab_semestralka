package jeelab.ws;

import java.util.List;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import jeelab.model.builder.UserBuilder;
import jeelab.model.dao.UserDao;
import jeelab.model.entity.User;
import jeelab.view.RegistrationForm;
import jeelab.ws.response.ListWrapper;

@Path("/users")
@Produces(MediaType.APPLICATION_JSON)
public class UserWS {
	
	@Inject
	private UserDao userDao;
	@Inject
	private UserBuilder userBuilder;

	@GET()
	@Path("/")
	public Response users() {
		List<User> users = userDao.getUsers();
		return Response.ok(new ListWrapper(users), MediaType.APPLICATION_JSON).build();
	}
	
	@POST()
	@Path("/add")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response addUser(@Valid RegistrationForm form) {
		User user = userBuilder
				.firstname(form.getFirstname())
				.lastname(form.getLastname())
				.email(form.getEmail())
				.password(form.getPassword())
				.build();
		userDao.save(user);
		
		return Response.status(Status.CREATED).entity(user).build();
	}
	
}
