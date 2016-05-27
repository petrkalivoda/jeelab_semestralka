package jeelab.ws;

import java.util.List;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
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

import org.jboss.security.annotation.SecurityDomain;

import jeelab.model.builder.UserBuilder;
import jeelab.model.dao.ReservationDao;
import jeelab.model.dao.UserDao;
import jeelab.model.entity.Reservation;
import jeelab.model.entity.User;
import jeelab.view.RegistrationForm;
import jeelab.ws.response.ListWrapper;
import jeelab.ws.response.UserResponse;

/**
 * REST rozhrani pro spravu uzivatelu
 * @author Vaclav Dobes
 *
 */
@Path("/user")
@Produces(MediaType.APPLICATION_JSON)
@SecurityDomain("jeedb")
public class UserWS {
	
	@Inject
	private UserDao userDao;
	@Inject
	private ReservationDao reservationDao;
	@Inject
	private UserBuilder userBuilder;
	
	/**
	 * Registruje noveho uzivatele
	 * @param form
	 * @return
	 */
	@POST()
	@Path("/")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response addUser(@Valid RegistrationForm form) {
		User user = userBuilder
				.firstname(form.getFirstname())
				.lastname(form.getLastname())
				.email(form.getEmail())
				.password(form.getPassword())
				.build();
		userDao.save(user);
		UserResponse response = new UserResponse()
				.id(user.getId())
				.firstname(user.getFirstname())
				.lastname(user.getLastname())
				.email(user.getEmail());
		return Response.status(Status.CREATED).entity(response).build();
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
	
	@GET()
	@Path("/test1")
	@Produces(MediaType.TEXT_PLAIN)
	@PermitAll
	public Response test1() {
		return Response.ok("test 1").build();
	}
	
	@GET()
	@Path("/test2")
	@Produces(MediaType.TEXT_PLAIN)
	@RolesAllowed("ROLE_USER")
	public Response test2() {
		return Response.ok("test 2").build();
	}
	
}
