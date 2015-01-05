package priceline;

import java.util.Random;
import java.io.Serializable;

/**
 * The Class Spinner.
 */
public class Spinner extends Random implements SpinnerImpl, Serializable {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	
	/**
	 * Instantiates a new spinner.
	 */
	public Spinner() {
		super();
	}

	
	/**
	 * Instantiates a new spinner.
	 *
	 * @param seed the seed
	 */
	public Spinner(long seed) {
		super(seed);
	}

}
