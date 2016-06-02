package jeelab.ws;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import jeelab.ws.response.AddressStorage;
import jeelab.ws.response.Point;

@Path("/")
@Produces(MediaType.APPLICATION_JSON)
public class EntryPoint {

	@Inject
	private AddressStorage address;
	
	@GET()
	@Path("/")
	public Response points() {
		
		List<Point> points = new ArrayList<Point>();
		
		points.add(new Point("registration", address.user()));
		points.add(new Point("login", address.login()));
		points.add(new Point("logout", address.logout()));
		points.add(new Point("reservation", address.reservation()));
		points.add(new Point("centre", address.centre()));
		points.add(new Point("facility", address.facility()));
		points.add(new Point("facilityType", address.facilityType()));
		
		return Response.ok(points).build();
	}
	
}
