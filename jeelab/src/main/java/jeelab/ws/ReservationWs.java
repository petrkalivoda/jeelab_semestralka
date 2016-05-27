package jeelab.ws;

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

	/**
	 * Vytvori rezervaci. Rezervaci muze vytvorit pouze aktivni uzivatel ve volnem terminu
	 * @param form
	 * @return
	 */
	@POST()
	@Consumes(MediaType.APPLICATION_JSON)
	public Response newReservation(@Valid ReservationForm form) {
		Reservation reservation = reservationBuilder
				.date(form.getDate())
				.from(form.getFrom())
				.to(form.getTo())
				.user(form.getUser())
				.build();
		// TODO existuje uzivatel? je aktivni? je termin volny?
		reservationDao.save(reservation);
		return Response.status(Status.CREATED).build();
	}
	
}
