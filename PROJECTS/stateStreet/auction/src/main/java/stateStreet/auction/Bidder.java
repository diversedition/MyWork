package stateStreet.auction;

import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Creates a Runnable instance for an auction bidder
 * @author Eugene
 *
 */
class Bidder implements Runnable 
{
	boolean DEBUG=false;
	final String myName;
	
	int myCurrentBid = 0;
	final int myMaxBid;
	final int myIncrement;
	
	boolean couldUpdate = false;
	
	static ReadWriteLock rwl = new ReentrantReadWriteLock();  
	
	/**
	 * constructor
	 * @param name - bidder's name
	 * @param currentBid - the bidder's current bid
	 * @param maxBid - the bidder's max bidder
	 * @param increment - the bidder's increment value
	 * @param debug - switch for debugging
	 */
	Bidder(String name, String currentBid, String maxBid, String increment, boolean debug) {
		this.myName = name;
		this.myCurrentBid = Integer.parseInt(currentBid);		
		this.myMaxBid = Integer.parseInt(maxBid); 
		this.myIncrement = Integer.parseInt(increment);   
		this.DEBUG = debug;
	}
	
	/**
	 * helper constructor
	 * @param name - bidder's name
	 * @param currentBid - the bidder's current bid
	 * @param maxBid - the bidder's max bidder
	 * @param increment - the bidder's increment value
	 * @param debug - switch for debugging
	 */
	Bidder(String name, String currentBid, String maxBid, String increment) {
		this(name, currentBid, maxBid, increment, false);
	}
	    	
	String getName() { return myName; }
	int getIncrement() { return myIncrement; }
	int getMaxBid() { return myMaxBid; }
	int getCurrentBid() { return myCurrentBid; }
	
	/**
	 * Checks the current bid of a given thread to see if it is the new winning bid; if so, note the winning name & bid.  If a bidder's current bid is equal
	 * to the current winning bid, it will not replace the current winner.
	 * @param currentBid
	 * @param name
	 */
	private void checkWinningBid(int currentBid, String name, Winner winner) {    		
		rwl.writeLock().lock();
		try {
		   if (currentBid > winner.getWinningBid()) {
			  if (DEBUG) {
		         System.out.println(" Bidder:" + name + " setting winningBid to: " + currentBid);
			  }
		      winner.setFields(currentBid, name);
		   }    		    
		} 
		finally {
		   rwl.writeLock().unlock();
		}
	}
	
	/**
	 * Checks to see if the current bidder can update their bid, and potentially become the winning bid.  If the opening bid is greater than the current winning
	 * bid, then it will also be a be a candidate for checking if it is the current winning bid. <br> 
	 * A bid may not exceed the bidder's max bid.  If two bidders' have equal bids,
	 * the first bidder to set the respective bid, wins the tie.
	 * @param currentBid - the current bid of a bidder
	 * @param name - the bidder's name
	 * @return - true if the bidder's current bid can be increased
	 */
	private boolean canUpdateCurrentBid(int currentBid, String name, Winner winner) {       
		
		boolean canUpdate = false;
		
		try {
		   rwl.readLock().lock();
		   
		   int winningBid = winner.getWinningBid();  //We have the lock so it won't change...
		   String winningName = winner.getWinningName();
		   
		   //Can happen during the initial bidding
		   if ( currentBid > winningBid ) {
			  canUpdate = true;
		   } 
		   else {
			  //Check if a bidder can increment past the current winningBid
		      if ( ((currentBid < winningBid) || ((currentBid == winningBid) && (!winningName.equals(name))) ) && ((currentBid + myIncrement) <= myMaxBid) ) {
			     myCurrentBid = currentBid + myIncrement;
		         canUpdate = true;
		      }
		   }
		}    		   
		finally {
		   rwl.readLock().unlock();
		}
		
		return canUpdate;
	}
	
	/**
	 * Checks if a bidder, other than the currently winning bidder, can potentially increase their bid.  
	 * It is used to see if any bidders can possibly place more bids, to determine if the auction can continue.
	 * @return - true if a bidder's current bid can be increased
	 */
	boolean couldBidAgain(Winner winner) {
		boolean couldUpdate = false;
		
	    try {
	    	rwl.readLock().lock();
	    	int myNextBidAttempt = myCurrentBid + myIncrement;
	    	
	    	//Check if you are NOT the current winner AND your next bid is not past your max, AND it is not already the winning bid
	        if ( (!winner.getWinningName().equals(myName)) &&     	        		
	        	 ( ((myNextBidAttempt) < myMaxBid) ||
	        	   (((myNextBidAttempt) == myMaxBid) && (myNextBidAttempt != winner.getWinningBid())) ) 
	           ) {
	    	     couldUpdate = true;
	        }
	    }
	    finally {
	    	rwl.readLock().unlock();
	    }
	    
	    return couldUpdate;
	}
	
	
	/**
	 * As long as a bidder can update their current bid, do so; and check each time to see that the bid is the winning bid.
	 * Stop when the bid is the winning bid; or the max bid has been reached, or would be reached.
	 */
	public void run() {
	    while (canUpdateCurrentBid(myCurrentBid, myName, AuctionImpl.getWinner())) {
	    	checkWinningBid(myCurrentBid, myName, AuctionImpl.getWinner());
	    }
	}
}