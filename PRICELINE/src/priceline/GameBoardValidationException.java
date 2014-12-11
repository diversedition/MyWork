package priceline;

/**
 * The Class GameBoardValidationException.
 */
public class GameBoardValidationException extends Exception {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 6369374707163910602L;


	/**
	 * Instantiates a new game board validation exception.
	 */
	GameBoardValidationException() {
		super();
	}
	
	
	/**
	 * Instantiates a new game board validation exception.
	 *
	 * @param message the message
	 */
	public GameBoardValidationException(String message) {
	    super(message);
	}
	
	
	/**
	 * Instantiates a new game board validation exception.
	 *
	 * @param message the message
	 * @param cause the cause
	 */
	public GameBoardValidationException(String message, Throwable cause) {
	    super(message, cause);
	}
	
	
	/**
	 * Instantiates a new game board validation exception.
	 *
	 * @param cause the cause
	 */
	public GameBoardValidationException(Throwable cause) {
	    super(cause);
	}


}
