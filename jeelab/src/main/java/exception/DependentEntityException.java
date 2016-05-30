package exception;

public class DependentEntityException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public DependentEntityException() {
		super();
	}
	
	public DependentEntityException(String message) {
		super(message);
	}
	
}
