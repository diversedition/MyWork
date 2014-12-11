package priceline;

/**
 * The Class Node.
 */
public class Node {
	
	/** The key. */
	private final int key;   //range from MIN to MAX;  
	    
	//CHUTE OR LADDER DETAILS
	/** The has ladder. */
	private final boolean hasLadder;
	
	/** The has chute. */
	private final boolean hasChute;
	
	/** The next value. */
	private final int nextValue;
	
	/**
	 * Instantiates a new node.
	 *
	 * @param key the key
	 * @param hasLadder the has ladder
	 * @param hasChute the has chute
	 * @param nextValue the next value
	 */
	Node(int key, boolean hasLadder, boolean hasChute, int nextValue) {
	   	this.key = key;
	   	this.hasLadder = hasLadder;
	   	this.hasChute = hasChute;
	   	this.nextValue = nextValue;
	}	
	
	/**
	 * Instantiates a new node.
	 *
	 * @param key the key
	 * @param nextValue the next value
	 */
	Node(int key, int nextValue) {
	   	this.key = key;
	   	this.nextValue = nextValue;
	   	
	   	if (nextValue > key) {	   		
	   		this.hasChute = false;
	   		this.hasLadder = true;
	   	} else {
	   		if (nextValue < key) {
	   		   this.hasChute = true;
	   		   this.hasLadder = false;
	   		} else {
	   		   this.hasChute = false;
	   		   this.hasLadder = false;
	   		}
	   	}
	}
	
	
	/**
	 * Gets the next value.
	 *
	 * @return the next value
	 */
	public int getNextValue() {
		return nextValue;
	}
	
	
	/**
	 * Gets the key.
	 *
	 * @return the key
	 */
	public int getKey() {
		return key;
	}
	
	
	/**
	 * Checks for ladder.
	 *
	 * @return true, if successful
	 */
	public boolean hasLadder() {
		return this.hasLadder;
	}
	
	
	/**
	 * Checks for chute.
	 *
	 * @return true, if successful
	 */
	public boolean hasChute() {
		return this.hasChute;
	}
	
}
