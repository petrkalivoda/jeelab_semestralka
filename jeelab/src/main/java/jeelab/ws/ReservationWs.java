package jeelab.ws;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import jeelab.exception.ReservationUnavailableException;
import jeelab.model.builder.ReservationBuilder;
import jeelab.model.dao.ReservationDao;
import jeelab.model.dao.SportsCentreDao;
import jeelab.model.entity.Reservation;
import jeelab.model.entity.SportsCentreFacility;
import jeelab.view.ReservationForm;
import jeelab.ws.response.AddressStorage;
import jeelab.ws.response.ListWrapper;
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
	private SportsCentreDao sportsDao;
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
	@Transactional
	public Response newReservation(@Valid ReservationForm form) throws ReservationUnavailableException {
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
	
	/**
	 * Smaze rezervaci
	 * @param id
	 * @return
	 */
	@DELETE()
	@Path("{id}")
	@RolesAllowed("ROLE_ADMIN")
	@Transactional
	public Response deleteReservation(@PathParam("id") Long id) {
		reservationDao.deleteReservation(id);
		return Response.status(Status.NO_CONTENT).build();
	}
	
	/**
	 * Seznam rezervaci
	 * @return
	 */
	@GET()
	@Consumes(MediaType.APPLICATION_JSON)
	@RolesAllowed("ROLE_USER")
	public Response reservationList() {
		List<ReservationResponse> reservations = new ArrayList<ReservationResponse>();
		for (Reservation r : reservationDao.getReservations(null, null)) {
			reservations.add(createReservation(r));
		}
		return Response.status(Status.OK).entity(new ListWrapper(reservations)).build();
	}
	
	/**
	 * Detail rezervace
	 * @return
	 */
	@GET()
	@Path("{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	@PermitAll
	public Response reservation(@PathParam("id") Long id) {
		Reservation reservation = reservationDao.getReservation(id);
		if (reservation == null)
			throw new EntityNotFoundException();
		return Response.status(Status.OK).entity(createReservation(reservation)).build();
	}
	
	/**
	 * Seznam rezervaci konkretniho zarizeni
	 * @return
	 */
	@GET()
	@Path("facility/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	@RolesAllowed("ROLE_USER")
	public Response reservationFacilityList(@PathParam("id") Long id) {
		List<ReservationResponse> reservations = new ArrayList<ReservationResponse>();
		SportsCentreFacility facility = sportsDao.getFacilityWithReservations(id);
		for (Reservation r : facility.getReservations()) {
			reservations.add(createBasicReservation(r));
		}
		return Response.status(Status.OK).entity(new ListWrapper(reservations)).build();
	}
	
	private ReservationResponse createReservation(Reservation r) {
		return new ReservationResponse(address)
				.id(r.getId())
				.url(address.reservation(r.getId()))
				.from(r.getFrom())
				.to(r.getTo())
				.date(r.getDate())
				.user(r.getUser())
				.facility(r.getSportsCentreFacility());
	}
	
	private ReservationResponse createBasicReservation(Reservation r) {
		return new ReservationResponse(address)
				.id(r.getId())
				.url(address.reservation(r.getId()))
				.from(r.getFrom())
				.to(r.getTo())
				.date(r.getDate());
	}
	
}
