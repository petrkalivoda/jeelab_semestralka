package jeelab;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.ElementKind;
import javax.validation.Path.Node;
import javax.validation.ValidationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.jboss.resteasy.api.validation.ResteasyViolationException;

import jeelab.ws.response.FieldError;

@Provider
public class JsonValidationProvider implements ExceptionMapper<ValidationException> {

	@Override
	public Response toResponse(ValidationException exception) {
		if (exception instanceof ResteasyViolationException) {
			ResteasyViolationException restEx = ResteasyViolationException.class.cast(exception);
            Set<ConstraintViolation<?>> violations = restEx.getConstraintViolations();
            List<FieldError> errors = new ArrayList<FieldError>();
            for (ConstraintViolation<?> v: violations) {
            	Iterator<Node> it = v.getPropertyPath().iterator();
            	String field = null;
            	while(it.hasNext()) {
            		Node p = it.next();
            		if (p.getKind().equals(ElementKind.PROPERTY))
            			field = p.getName();
            	}
            	            	
            	errors.add(new FieldError(field, v.getMessageTemplate()));
            
            }
            return Response.status(Status.BAD_REQUEST).entity(errors).type(MediaType.APPLICATION_JSON).build();
		}
		return Response.status(Status.BAD_REQUEST).entity(exception).build();
	}
	
	
}
	