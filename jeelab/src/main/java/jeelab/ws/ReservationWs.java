package jeelab.ws;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import jeelab.model.builder.ReservationBuilder;
import jeelab.model.dao.ReservationDao;
import jeelab.model.entity.Reservation;
import jeelab.view.ReservationForm;
import jeelab.ws.response.AddressStorage;
import jeelab.ws.response.ReservationResponse;

/**
 * REST rozhrani pro spravu rezervaci
 * @author Vaclav Dobes
 *
 */
@Path("/reservation")
@Produces(MediaType.APPLICATION_JSON)
public class ReservationWs {
	
	@Inject
	private ReservationBuilder reservationBuilder;
	@Inject
	private ReservationDao reservationDao;
	@Inject
	private AddressStorage address;

	/**
	 * Vytvori rezervaci. Rezervaci muze vytvorit pouze aktivni uzivatel ve volnem terminu
	 * @param form
	 * @return
	 * @throws Exception 
	 */
	@POST()
	@Consumes(MediaType.APPLICATION_JSON)
	@RolesAllowed("ROLE_USER")
	public Response newReservation(@Valid ReservationForm form) throws Exception {
		Reservation reservation = reservationBuilder
				.date(form.getDate())
				.from(form.getFrom())
				.to(form.getTo())
				.user(form.getUser())
				.sportsCentreFacility(form.getCentreFacility())
				.build();
		// TODO je termin volny?
		reservationDao.save(reservation);
		ReservationResponse response = new ReservationResponse()
				.id(reservation.getId())
				.url(address.reservation(reservation.getId()))
				.date(reservation.getDate())
				.from(reservation.getFrom())
				.to(reservation.getTo())
				.user(form.getUser(), address.user(form.getUser()));
		return Response.status(Status.CREATED).entity(response).build();
	}
	
}
