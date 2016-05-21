package jeelab.ws;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import jeelab.model.dao.UserDao;
import jeelab.model.entity.User;

@Path("/")
public class HelloWs {
	
	@Inject
	private UserDao userDao;

	@GET()
	@Path("/users")
	@Produces("text/plain")
	public String users() {
		List<User> users = userDao.getUsers();
		return "Size: " + users.size();
	}
	
	@GET()
	@Path("/add")
	@Produces("text/plain")
	public String addUser() {
		User user = new User();
		user.setFirstname("John");
		user.setFirstname("Doe");
		userDao.save(user);
		return "done";
	}
	
}
