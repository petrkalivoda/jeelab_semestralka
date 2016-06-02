package jeelab.ws.handler;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import jeelab.exception.ReservationUnavailableException;

@Provider
public class ReservationUnavailableExceptionHandler implements ExceptionMapper<ReservationUnavailableException> {

	@Override
	public Response toResponse(ReservationUnavailableException exception) {
		return Response.status(Status.CONFLICT).build();
	}

}
