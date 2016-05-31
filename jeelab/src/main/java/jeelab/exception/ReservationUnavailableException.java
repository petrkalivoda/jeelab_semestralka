package jeelab.exception;

public class ReservationUnavailableException extends Exception {

	private static final long serialVersionUID = -7702211149158694934L;
	private static final String MSG = "exception.reservation.unavailable";

	@Override
	public String getMessage() {
		return MSG;
	}
}
