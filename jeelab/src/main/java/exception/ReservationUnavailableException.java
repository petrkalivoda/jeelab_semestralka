package exception;

public class ReservationUnavailableException extends Exception {
	private static final String MSG = "exception.reservation.unavailable";

	@Override
	public String getMessage() {
		return MSG;
	}
}
