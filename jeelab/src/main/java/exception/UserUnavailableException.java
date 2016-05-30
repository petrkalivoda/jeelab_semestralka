package exception;

public class UserUnavailableException extends Exception {
	private static final String MSG = "exception.user.unavailable";

	@Override
	public String getMessage() {
		return MSG;
	}
}
