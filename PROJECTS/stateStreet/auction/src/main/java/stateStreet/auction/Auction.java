package stateStreet.auction;

import java.util.List;
import java.util.ArrayList;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;


/**
 * Auction
 *
 */
public class Auction 
{
	static int MAX_NUMBER_OF_PASSES = 100;  //Failsafe - could configure eventually via a propFile...
	
	static int winningBid = 0;
	static String winningName = "None";	
	static List<Bidder> bidders = new ArrayList<Bidder>();
	
	static ReadWriteLock rwl = new ReentrantReadWriteLock();
	
    public static void main( String[] args ) throws InterruptedException

    {
        System.out.println( "Start Auction..." );
        
        //Some test data, with expected results
        //Bicycle;Alice,50,80,3;Aaron,60,82,2;Amanda,55,85,5                 :: Amanda-85
        //Scooter;Alice,700,725,2;Aaron,599,725,15;Amanda,625,725,8          :: Alice-722
        //Boat;Alice,2500,3000,500;Aaron,2800,3100,201;Amanda,2501,3200,247  :: Aaron-3001
        
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
	                        
	        
	        //DIVER ---------------------- This can go into the Server class
	        
	        Thread t1 = null;
	        Thread t2 = null;
	        Thread t3 = null;
	        
	        int passNumber = 0;
	        while (atLeastOneCouldStillBid(bidders)) {
	           passNumber++;
	           System.out.println("\nbegin pass:" + passNumber + " >> " + showStats(bidders, passNumber));
	           
	           t1 = new Thread(b1);
	           t2 = new Thread(b2);
	           t3 = new Thread(b3);	
	        	
	           t1.start();
	           t2.start();
	           t3.start();
	        
	           t1.join();
	           t2.join();
	           t3.join();
	           
	           System.out.println("after pass:" + passNumber + " >> " + showStats(bidders, passNumber));
	           
	           //Failsafe, (for now...)
	           if (passNumber > MAX_NUMBER_OF_PASSES) {
	        	   System.out.println("WARNING: forced termination after max number of bid cycles !!\n");
	        	   System.exit(1);
	           }
	        } 
	        
	        //DIVER -------------------------------------------------end of Server class stuff
	        
	        System.out.println("\nThe winner is " + winningName + " with the winning bid of: " + winningBid);
	        System.out.println("\n============================================================================================\n");
        }
                
        System.out.println("\n");
    }  
    
    private static void resetAuction() {
    	bidders.clear();
    	winningBid = 0;
    	winningName = "None";
    }
        
    private static String showStats(List<Bidder> bidders, int passNumber) {
    	
    	String passType = (passNumber > 1) ? " Current" : " Initial";
    	String stats = passType + " Bid:" + winningBid + " ";
    	
    	for (Bidder b : bidders) {
    	   stats = stats + " " + b.getName() + "-" + b.getCurrentBid() + ":" + b.getMaxBid() + ":" + b.getIncrement() + " ";	
    	}
    	
    	return stats;
    }  
    
    private static boolean atLeastOneCouldStillBid(List<Bidder> bidders) {
    	
    	for (Bidder b : bidders) {
    	   if (b.couldBidAgain()) {
    		   return true;
    	   }
    	}
    	
    	return false;
    }
    
    
    static class AuctionServer {
    	
    	List<Bidder> bidders = null;
    	
    	AuctionServer() {
    		
    	}
    }
    
    
    static class Bidder implements Runnable 
    {
    	final String myName;
    	
    	int myCurrentBid = 0;
    	final int myMaxBid;
    	final int myIncrement;
    	
    	boolean couldUpdate = false;
    	
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
    	
    	
    	private void checkWinningBid(int currentBid, String name) {    		
    		rwl.writeLock().lock();
    		try {
    		   if (myCurrentBid > winningBid) {
    		      System.out.println(" Bidder:" + name + " setting winningBid to: " + currentBid);
    		      winningBid = currentBid;
    		      winningName = name; 		       
 		       }    		    
    		} 
    		finally {
    		   rwl.writeLock().unlock();
    		}
    	}
    	
    	
    	private boolean canUpdateCurrentBid(int currentBid, String name) {
    		
    		boolean canUpdate = false;
    		
    		try {
    		   rwl.readLock().lock();
    		   
    		   //Can happen during the initial bidding
    		   if ( myCurrentBid > winningBid ) {
    			  canUpdate = true;
    		   } 
    		   else {
    			  //Check if a bidder can increment past the current winningBid
    		      if ( ((myCurrentBid < winningBid) || ((myCurrentBid == winningBid) && (!myName.equals(winningName)))) && ((myCurrentBid + myIncrement) <= myMaxBid) ) {
    			     myCurrentBid = myCurrentBid + myIncrement;
    		         canUpdate = true;
    		      }
    		   }
    		}    		   
    		finally {
    		   rwl.readLock().unlock();
    		}
    		
    		return canUpdate;
    	}
    	
    	
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
    	
    	
    	public void run() {
    	    while (canUpdateCurrentBid(myCurrentBid, myName)) {
    	    	checkWinningBid(myCurrentBid, myName);
    	    }
    	}
    }
}
