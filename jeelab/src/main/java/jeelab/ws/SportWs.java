package jeelab.ws;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import jeelab.model.builder.SportsCentreBuilder;
import jeelab.model.builder.SportsCentreFacilityBuilder;
import jeelab.model.dao.SportsCentreDao;
import jeelab.model.entity.FacilityType;
import jeelab.model.entity.SportsCentre;
import jeelab.model.entity.SportsCentreFacility;
import jeelab.view.FacilityTypeForm;
import jeelab.view.SportCentreFacilityForm;
import jeelab.view.SportCentreForm;
import jeelab.ws.response.AddressStorage;
import jeelab.ws.response.FacilityResponse;
import jeelab.ws.response.FacilityTypeResponse;
import jeelab.ws.response.ListWrapper;
import jeelab.ws.response.SportCentreResponse;

/**
 * REST rozhrani pro spravu sportovist
 * @author Vaclav Dobes
 *
 */
@Path("sport")
@Produces(MediaType.APPLICATION_JSON)
public class SportWs {
	
	@Inject
	private SportsCentreDao sportsDao;
	@Inject
	private SportsCentreBuilder centreBuilder;
	@Inject
	private SportsCentreFacilityBuilder facilityBuilder;
	@Inject
	private AddressStorage address;
	
	/**
	 * Vraci seznam typu zarizeni
	 * @return
	 */
	@GET()
	@Path("facility/type")
	public Response facilityTypes () {
		List<FacilityType> types = sportsDao.getFacilityTypes();
		List <FacilityTypeResponse> list = new ArrayList<FacilityTypeResponse>();
		for (FacilityType t : types) {
			list.add(new FacilityTypeResponse()
					.id(t.getId())
					.url(address.facilityType(t.getId()))
					.name(t.getName()));
		}
		return Response.ok(new ListWrapper(list)).build();
	}
	
	/**
	 * Vraci dany typ zarizeni
	 * @return
	 */
	@GET()
	@Path("facility/type/{id}")
	public Response facilityType (@PathParam("id") Long id) {
		FacilityType type = sportsDao.getFacilityType(id);
		if (type == null)
			throw new EntityNotFoundException();
		FacilityTypeResponse response = new FacilityTypeResponse()
			.id(type.getId())
			.url(address.facilityType(type.getId()))
			.name(type.getName());
		return Response.ok(response).build();
	}
	
	/**
	 * Vytvori novy typ zarizeni
	 * @param form
	 * @return
	 */
	@POST()
	@Path("facility/type")
	@RolesAllowed("ROLE_ADMIN")
	public Response createFacilityType(@Valid FacilityTypeForm form) {
		FacilityType type = new FacilityType();
		type.setName(form.getName());
		sportsDao.saveFacilityType(type);
		FacilityTypeResponse response = new FacilityTypeResponse()
				.id(type.getId())
				.url(address.facilityType(type.getId()))
				.name(type.getName());
		return Response.status(Status.CREATED).entity(response).build();
	}
	
	/**
	 * Upravi typ zarizeni
	 * @param form
	 * @return
	 */
	@PUT()
	@Path("facility/type/{id}")
	@RolesAllowed("ROLE_ADMIN")
	public Response updateFacilityType(
			@PathParam("id") Long id,
			@Valid FacilityTypeForm form
			) {
		sportsDao.updateFacilityTypeName(id, form.getName());
		return Response.status(Status.OK).build();
	}
	
	/**
	 * Smaze typ zarizeni
	 * @param form
	 * @return
	 */
	@DELETE()
	@Path("facility/type/{id}")
	@RolesAllowed("ROLE_ADMIN")
	public Response deleteFacilityType(@PathParam("id") Long id) {
		sportsDao.deleteFacilityType(id);
		return Response.status(Status.NO_CONTENT).build();
	}
	
	/**
	 * Vraci sportovni centra
	 * @return
	 */
	@GET()
	@Path("centre")
	public Response sportsCentre() {
		List<SportsCentre> centres = sportsDao.getAllCentre();
		List<SportCentreResponse> list = new ArrayList<SportCentreResponse>();
		for (SportsCentre c: centres) {
			list.add(centreBuilder(c, false));
		}
		return Response.ok(new ListWrapper(list)).build();
	}
	
	/**
	 * Detail sportovniho centra vcetne oteviracich hodina a seznamu zarizeni
	 * @param id
	 * @return
	 */
	@GET()
	@Path("centre/{id}")
	public Response sportsCentre(@PathParam("id") Long id) {
		SportsCentre centre = sportsDao.getCompleteCentre(id);
		if (centre == null)
			throw new EntityNotFoundException();
		return Response.ok(centreBuilder(centre, true)).build();
	}
	
	/**
	 * Nové sportovni centrum
	 * @param form
	 * @return
	 */
	@POST()
	@Path("centre")
	@RolesAllowed("ROLE_ADMIN")
	public Response createCentre(@Valid SportCentreForm form) {
		SportsCentre centre = centreBuilder
				.city(form.getCity())
				.street(form.getStreet())
				.country(form.getCountry())
				.building(form.getBuildingNumber())
				.phone(form.getPhone())
				.hours(form.getHours())
				.build();
		sportsDao.saveCentre(centre);
		return Response.status(Status.CREATED).entity(centreBuilder(centre, true)).build();
	}
	
	/**
	 * Uprava centra
	 * @param id
	 * @param form
	 * @return
	 */
	@PUT()
	@Path("centre/{id}")
	@RolesAllowed("ROLE_ADMIN")
	public Response updateCentre(
			@PathParam("id") Long id,
			@Valid SportCentreForm form
		) {
		sportsDao.updateCentre(id, form);
		return Response.status(Status.OK).build();
	}
	
	@DELETE()
	@Path("centre/{id}")
	@RolesAllowed("ROLE_ADMIN")
	public Response deleteCentre(@PathParam("id") Long id) {
		sportsDao.deleteCentre(id);
		return Response.status(Status.NO_CONTENT).build();
	}
	
	/**
	 * Vytvori nove zarizeni
	 * @param id
	 * @param form
	 * @return
	 */
	@POST()
	@Path("centre/{id}/facility")
	@RolesAllowed("ROLE_ADMIN")
	public Response createCentreFacility(
			@PathParam("id") Long id, 
			@Valid SportCentreFacilityForm form
			) {
		SportsCentreFacility facility = facilityBuilder
				.centre(id)
				.type(form.getType())
				.hours(form.getHours())
				.build();
		sportsDao.saveFacility(facility);
		return Response.status(Status.CREATED).entity(facilityBuilder(facility, false)).build();
	}
	
	@PUT()
	@Path("centre/{centreId}/facility/{facilityId}")
	@RolesAllowed("ROLE_ADMIN")
	public Response updateCentreFacility(
			@PathParam("centreId") Long centreId,
			@PathParam("facilityId") Long facilityId,
			@Valid SportCentreFacilityForm form
			) {
		sportsDao.updateFacility(facilityId, form);
		return Response.status(Status.OK).build();
	}
	
	/**
	 * Smaze zarizeni
	 * @param centreId
	 * @param facilityId
	 * @return
	 */
	@DELETE()
	@Path("centre/{centreId}/facility/{facilityId}")
	@RolesAllowed("ROLE_ADMIN")
	public Response deleteCentreFacility(
			@PathParam("centreId") Long centreId,
			@PathParam("facilityId") Long facilityId
			) {
		sportsDao.deleteFacility(facilityId);
		return Response.status(Status.NO_CONTENT).build();
	}
	
	private SportCentreResponse centreBuilder(SportsCentre centre, boolean extraData) {
		SportCentreResponse response = new SportCentreResponse(address)
				.id(centre.getId())
				.url(address.centre(centre.getId()))
				.facilityUrl(address.facility(centre.getId()))
				.street(centre.getStreet())
				.city(centre.getCity())
				.country(centre.getCountry())
				.phone(centre.getPhoneNumber())
				.building(centre.getBuildingNumber());
		if (extraData) {
			response.hours(centre.getBusinessHours());
			response.facilities(centre.getFacilities());
		}
		return response;
	}
	
	private FacilityResponse facilityBuilder(SportsCentreFacility facility, boolean centre) {
		FacilityResponse response = new FacilityResponse(address)
				.id(facility.getId())
				.url(address.facility(facility.getSportsCentre().getId(), facility.getFacilityType().getId()))
				.type(facility.getFacilityType())
				.hours(facility.getBusinessHours());
		if (centre)
			response.centre(facility.getSportsCentre());
		return response;
	}
}
