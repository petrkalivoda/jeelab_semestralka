package exception;

public class ReservationUnavailableException extends Exception {
	private static final String MSG = "exception.unavailable";

	@Override
	public String getMessage() {
		return MSG;
	}
}
