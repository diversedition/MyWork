package stateStreet.auction;

import java.util.List;
import java.util.ArrayList;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;


/**
 *  Auction
 *       
 *  Some test data, with expected results
 *  Bicycle;Alice,50,80,3;Aaron,60,82,2;Amanda,55,85,5                 :: Amanda-85
 *  Scooter;Alice,700,725,2;Aaron,599,725,15;Amanda,625,725,8          :: Alice-722
 *  Boat;Alice,2500,3000,500;Aaron,2800,3100,201;Amanda,2501,3200,247  :: Aaron-3001
 *
 */
public class Auction 
{
	static int MAX_NUMBER_OF_PASSES = 100;  //Failsafe - could configure eventually via a propFile...
	
	static boolean DEBUG = false;
	static int winningBid = 0;
	static String winningName = "None";	
	static List<Bidder> bidders = new ArrayList<Bidder>();
	
	static ReadWriteLock rwl = new ReentrantReadWriteLock();
	static AuctionServer auctionServer = new AuctionServer(DEBUG);
	
	/**
	 * will read a set of auction input: <auctionItem>;bidder(n),initialBid,maxBid,increment;bidder(n+1), ...
	 * (i.e. Bicycle;Alice,50,80,3;Aaron,60,82,2;Amanda,55,85,5) 
	 * @param args
	 * @throws InterruptedException
	 */
    public static void main( String[] args ) throws InterruptedException

    {
        System.out.println( "Start Auction..." );
        
        for (int i=0; i < args.length; i++) {
        
        	resetAuction();
        	
	        String auctionData = args[i];
	        
	        String[] auctionInfo = null;
	        if (auctionData != null) {
	        	auctionInfo = auctionData.split(";");
	        }
	        
	        String auctionItem = auctionInfo[0];
	        System.out.println("\n Auctioning off the " + auctionItem + " ............................................");
	              
	        String b1BidParams[] = auctionInfo[1].split(",");
	        Bidder b1 = new Bidder(b1BidParams[0], b1BidParams[1], b1BidParams[2], b1BidParams[3]);
	        bidders.add(b1);
	                
	        String b2BidParams[] = auctionInfo[2].split(",");
	        Bidder b2 = new Bidder(b2BidParams[0], b2BidParams[1], b2BidParams[2], b2BidParams[3]);  
	        bidders.add(b2);
	        
	        String b3BidParams[] = auctionInfo[3].split(",");
	        Bidder b3 = new Bidder(b3BidParams[0], b3BidParams[1], b3BidParams[2], b3BidParams[3]);
	        bidders.add(b3);
	        
	        int passNumber = 0;
	        while (atLeastOneCouldStillBid(bidders)) {
	           passNumber++;
	           if (passNumber == 1) {
	              System.out.println("\n begin pass:" + passNumber + " >> " + showStats(bidders, true));
	           }
	           auctionServer.makeBids(bidders);          	           
	           
	           while (!auctionServer.allThreadsCompleted()) {
	     		   //wait for all the threads to finish
	     	   }  
	           
	           System.out.println(" after pass:" + passNumber + " >> " + showStats(bidders, false));
	           
	           //Failsafe, (for now...)  Could probably also set a time limit to the auction...
	           if (passNumber > MAX_NUMBER_OF_PASSES) {
	        	   System.out.println("WARNING: forced termination after max number of bid cycles !!\n");
	        	   auctionServer.shutdownAuctionServer();
	        	   System.exit(1);
	           }
	        }
	        
	        System.out.println("\n The winner of the " + auctionItem + " is " + winningName + ", with the winning bid of: " + winningBid);
	        System.out.println("\n============================================================================================\n");
        }                        
        
        auctionServer.shutdownAuctionServer();
    }  
    
    /**
     * will reset the Auction for the next set of auction data
     */
    private static void resetAuction() {
    	bidders.clear();
    	winningBid = 0;
    	winningName = "None";
    }
        
    /**
     * will print out the current auction stats at the start of an auction, and after each bidder has bid
     * @param bidders - a set of bidders for the current auction item
     * @param isInitial - true if it is the opening bid
     * @return the auction status string
     */
    private static String showStats(List<Bidder> bidders, boolean isInitial) {
    	
    	String passType = (isInitial) ? " Initial" : " Current";
    	String stats = passType + " Bid:" + winningBid + " ";
    	
    	for (Bidder b : bidders) {
    	   String name = (winningName.equals(b.getName()) ? b.getName().toUpperCase() : b.getName());
    	   stats = stats + " " + name + "-" + b.getCurrentBid() + ":" + b.getMaxBid() + ":" + b.getIncrement() + " ";	
    	}
    	
    	return stats;
    }  
    
    /**
     * true if at least on of the current bidders can still increase their bid again
     * @param bidders - a set of bidders for the current auction item
     * @return true if any of the bidders can still make bids
     */
    private static boolean atLeastOneCouldStillBid(List<Bidder> bidders) {
    	
    	for (Bidder b : bidders) {
    	   if (b.couldBidAgain()) {
    		   return true;
    	   }
    	}
    	
    	return false;
    }
    
    
    /**
     * Will coordinate the multi-threaded exector for the auction
     * @author Eugene
     *
     */
    static class AuctionServer {

    	boolean DEBUG = false;
    	private static final int THREAD_COUNT = 3;
    	ThreadPoolExecutor executor = null;
    	
    	/**
    	 * constructor
    	 * @param debug
    	 */
    	AuctionServer(boolean debug) {
    		
    		DEBUG = debug;
    		
    		int  corePoolSize  = THREAD_COUNT;
    		int  maxPoolSize   = 5;
    		long keepAliveTime = 5000;

    		executor = new ThreadPoolExecutor(corePoolSize,
								              maxPoolSize,
								              keepAliveTime,
								              TimeUnit.MILLISECONDS,
								              new LinkedBlockingQueue<Runnable>()
								              );
    	}
    	
    	/**
    	 * checks if all of the running threads have completed, and can also produce detailed debug
    	 * @return true if all the running threads have completed
    	 */
    	boolean allThreadsCompleted() {
    		if (DEBUG) {
    		   System.out.println(String.format("[monitor] [%d/%d] Active: %d, Completed: %d, Task: %d, isShutdown: %s, isTerminated: %s",
		                          executor.getPoolSize(),
		                          executor.getCorePoolSize(),
		                          executor.getActiveCount(),
		                          executor.getCompletedTaskCount(),
		                          executor.getTaskCount(),
		                          executor.isShutdown(),
		                          executor.isTerminated()));
    		}
    		return (executor.getCompletedTaskCount() == executor.getTaskCount());
    	}
    	
    	
    	/**
    	 * will send a list of bidders to the executor for a bidding cycle
    	 * @param bidders - a set of bidders for the current auction item
    	 */
    	void makeBids(List<Bidder> bidders) {
    		for (Bidder b : bidders) {
    	       if (b.couldBidAgain()) {
    	    	   executor.execute(b);
    	       }
    		}    		
    	}
    	
    	/**
    	 * will shutdown the auction server
    	 */
    	void shutdownAuctionServer() {
    		
    		System.out.println("\nThe Auction is now being shutdown...");
    		executor.shutdown();
    		
    		try {
    		   Thread.sleep(5000);
    		}
    		catch (InterruptedException ie) {
    		   if (!executor.isTerminated()) {
     		      executor.shutdownNow();
     		   }    
    		}
    		
    		System.out.println("The Auction has successfully been shutdown, Good-bye.\n");
    	}
    }
    
    
    /**
     * a Runnable instance for an auction bidder
     * @author Eugene
     *
     */
    static class Bidder implements Runnable 
    {
    	final String myName;
    	
    	int myCurrentBid = 0;
    	final int myMaxBid;
    	final int myIncrement;
    	
    	boolean couldUpdate = false;
    	
    	/**
    	 * constructor
    	 * @param name - bidder's name
    	 * @param currentBid - the bidder's current bid
    	 * @param maxBid - the bidder's max bidder
    	 * @param increment - the bidder's increment value
    	 */
    	Bidder(String name, String currentBid, String maxBid, String increment) {
    		this.myName = name;
    		this.myCurrentBid = Integer.parseInt(currentBid);		
    		this.myMaxBid = Integer.parseInt(maxBid); 
    		this.myIncrement = Integer.parseInt(increment);   
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
    	private void checkWinningBid(int currentBid, String name) {    		
    		rwl.writeLock().lock();
    		try {
    		   if (currentBid > winningBid) {
    			  if (DEBUG) {
    		         System.out.println(" Bidder:" + name + " setting winningBid to: " + currentBid);
    			  }
    		      winningBid = currentBid;
    		      winningName = name; 		       
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
    	private boolean canUpdateCurrentBid(int currentBid, String name) {
    		
    		boolean canUpdate = false;
    		
    		try {
    		   rwl.readLock().lock();
    		   
    		   //Can happen during the initial bidding
    		   if ( currentBid > winningBid ) {
    			  canUpdate = true;
    		   } 
    		   else {
    			  //Check if a bidder can increment past the current winningBid
    		      if ( ((currentBid < winningBid) || ((currentBid == winningBid) && (!name.equals(winningName)))) && ((currentBid + myIncrement) <= myMaxBid) ) {
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
    	 * Checks if a bidder can potentially increase their bid.  It is used to see if any bidders can possible place more bids, to determine if the auction can continue.
    	 * @return - true if a bidder's current bid can be increased
    	 */
    	boolean couldBidAgain() {
    		boolean couldUpdate = false;
    		
    	    try {
    	    	rwl.readLock().lock();
    	    	int myNextBidAttempt = myCurrentBid + myIncrement;
    	    	
    	    	//Check if you are NOT the current winner AND your next bid is not past your max, AND it is not already the winning bid
    	        if ( (!winningName.equals(myName)) &&     	        		
    	        	 ( ((myNextBidAttempt) < myMaxBid) ||
    	        	   (((myNextBidAttempt) == myMaxBid) && (myNextBidAttempt != winningBid)) ) 
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
    	    while (canUpdateCurrentBid(myCurrentBid, myName)) {
    	    	checkWinningBid(myCurrentBid, myName);
    	    }
    	}
    }
}
