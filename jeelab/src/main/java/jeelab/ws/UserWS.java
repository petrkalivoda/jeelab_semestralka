package jeelab.ws;

import java.util.List;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import jeelab.model.builder.UserBuilder;
import jeelab.model.dao.ReservationDao;
import jeelab.model.dao.UserDao;
import jeelab.model.entity.Reservation;
import jeelab.model.entity.User;
import jeelab.view.RegistrationForm;
import jeelab.ws.response.ListWrapper;

/**
 * REST rozhrani pro spravu uzivatelu
 * @author Vaclav Dobes
 *
 */
@Path("/user")
@Produces(MediaType.APPLICATION_JSON)
public class UserWS {
	
	@Inject
	private UserDao userDao;
	@Inject
	private ReservationDao reservationDao;
	@Inject
	private UserBuilder userBuilder;

	@GET()
	@Path("/")
	public Response users() {
		List<User> users = userDao.getUsers();
		for (User u : users) {
			u.setReservations(null);
		}
		return Response.ok(new ListWrapper(users), MediaType.APPLICATION_JSON).build();
	}
	
	/**
	 * Registruje noveho uzivatele
	 * @param form
	 * @return
	 */
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
	
	/**
	 * Vraci seznam rezervaci uzivatele
	 * @param form
	 * @return
	 */
	@GET()
	@Path("/{userId}/reservations")
	public Response getReservations(@PathParam("userId") Long userId) {
		List<Reservation> reservations = reservationDao.getUserReservations(userId);
		return Response.ok(new ListWrapper(reservations)).build();
	}
	
}
