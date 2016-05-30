package jeelab.ws.handler;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import exception.DependentEntityException;


@Provider
public class DependentEntityExceptionHandler implements ExceptionMapper<DependentEntityException> {

	@Override
	public Response toResponse(DependentEntityException exception) {
		return Response.status(Status.BAD_REQUEST).build();
	}

}
