
package jeelab.ws;

import java.security.Principal;
import java.util.List;

import javax.annotation.security.PermitAll;
import javax.inject.Inject;
import javax.jms.JMSException;
import javax.naming.NamingException;
import javax.transaction.Transactional;
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

import jeelab.exception.UserUnavailableException;
import jeelab.messaging.MailProducer;
import jeelab.model.builder.UserBuilder;
import jeelab.model.dao.ReservationDao;
import jeelab.model.dao.UserDao;
import jeelab.model.entity.Reservation;
import jeelab.model.entity.User;
import jeelab.setup.RolesInitializer;
import jeelab.view.RegistrationForm;
import jeelab.ws.response.AddressStorage;
import jeelab.ws.response.ListWrapper;
import jeelab.ws.response.UserResponse;

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
	@Inject
	private AddressStorage address;
	@Inject
	private Principal loggedUser;
	@Inject
	private MailProducer mail;
	
	/**
	 * Registruje noveho uzivatele s roli "ROLE_USER"
	 * @param form
	 * @return
	 * @throws UserUnavailableException 
	 * @throws JMSException 
	 * @throws NamingException 
	 */
	@POST()
	@Path("/")
	@Consumes(MediaType.APPLICATION_JSON)
	@Transactional
	public Response addUser(@Valid RegistrationForm form) throws UserUnavailableException, NamingException, JMSException {
		User user = userBuilder
				.firstname(form.getFirstname())
				.lastname(form.getLastname())
				.email(form.getEmail())
				.password(form.getPassword())
				.setRole(RolesInitializer.ROLE_USER)
				.build();
		userDao.save(user);
		mail.sendMail("test");
		return Response.status(Status.CREATED).entity(userResponse(user)).build();
	}
	
	@GET()
	@Path("/login")
	@PermitAll
	public Response login() {
		String email = loggedUser.getName();
		User user = userDao.getbyEmail(email);
		return Response.ok(userResponse(user)).build();
	}
	
	/**
	 * Vraci seznam rezervaci uzivatele
	 * @param form
	 * @return
	 */
	@GET()
	@Path("/{userId}/reservations")
	public Response getReservations(@PathParam("userId") Long userId) {
		List<Reservation> reservations = reservationDao.getUserReservations(userId, null, null);
		return Response.ok(new ListWrapper(reservations)).build();
	}
	
	private UserResponse userResponse(User user) {
		UserResponse response = new UserResponse()
			.id(user.getId())
			.url(address.user(user.getId()))
			.firstname(user.getFirstname())
			.lastname(user.getLastname())
			.email(user.getEmail())
			.roles(user.getRoles());
		return response;
	}
	
}
