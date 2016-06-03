package jeelab.exception;

/**
 * Vyjimka pri chybe geokodovani.
 * @author Petr Kalivoda
 *
 */
public class GeocodingErrorException extends Exception {
	private static final long serialVersionUID = -6198156515229847500L;
	
	public GeocodingErrorException() {}
	public GeocodingErrorException(Throwable cause) {
		super(cause);
	}
}
