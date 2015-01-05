package priceline;

/**
 * The Class GameBoardInsertionException.
 */
public class GameBoardCreationException extends Exception {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 6369374707163910602L;


	/**
	 * Instantiates a new game board insertion exception.
	 */
	GameBoardCreationException() {
		super();
	}
	
	
	/**
	 * Instantiates a new game board insertion exception.
	 *
	 * @param message the message
	 */
	public GameBoardCreationException(String message) {
	    super(message);
	}
	
	
	/**
	 * Instantiates a new game board insertion exception.
	 *
	 * @param message the message
	 * @param cause the cause
	 */
	public GameBoardCreationException(String message, Throwable cause) {
	    super(message, cause);
	}
	
	
	/**
	 * Instantiates a new game board insertion exception.
	 *
	 * @param cause the cause
	 */
	public GameBoardCreationException(Throwable cause) {
	    super(cause);
	}


}
