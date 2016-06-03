package jeelab.ws.handler;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import jeelab.exception.UserUnavailableException;

@Provider
public class UserUnavailableExceptionHandler implements ExceptionMapper<UserUnavailableException> {

	@Override
	public Response toResponse(UserUnavailableException exception) {
		return Response.status(Status.CONFLICT).build();
	}

}
