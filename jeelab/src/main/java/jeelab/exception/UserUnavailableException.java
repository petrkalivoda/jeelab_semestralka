package jeelab.exception;

public class UserUnavailableException extends Exception {

	private static final long serialVersionUID = 8153420811816188394L;
	private static final String MSG = "exception.user.unavailable";

	@Override
	public String getMessage() {
		return MSG;
	}
}
