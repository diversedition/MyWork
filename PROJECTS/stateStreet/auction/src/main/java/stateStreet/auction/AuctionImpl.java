package stateStreet.auction;

import java.util.List;
import java.util.ArrayList;


/**
 *  Auction
 *       
 *  Some test data, with expected results
 *  Bicycle;Alice,50,80,3;Aaron,60,82,2;Amanda,55,85,5                 :: Amanda-85
 *  Scooter;Alice,700,725,2;Aaron,599,725,15;Amanda,625,725,8          :: Alice-722
 *  Boat;Alice,2500,3000,500;Aaron,2800,3100,201;Amanda,2501,3200,247  :: Aaron-3001
 *
 *  RUN THESE COMMANDS TO ACCESS EXTERNALLY:
 *  auction = new AuctionImpl(new AuctionServer(DEBUG));	        
 *	auction.runSingleAuction(auctionInfo);	//auctionInfo: Bicycle;Alice,50,80,3;Aaron,60,82,2;Amanda,55,85,5
 *  auction.shutdown();
 *
 */
public class AuctionImpl implements Auction
{
	static int MAX_NUMBER_OF_PASSES = 500;  //Failsafe - could configure eventually via a propFile...
	
	static boolean DEBUG = false;
		
	private static Winner winner = new Winner();	
	public static Winner getWinner() { return winner; }
	
	String auctionItem = "NotSet";
	List<Bidder> bidders = new ArrayList<Bidder>();
		
	static Auction auction = null;
	AuctionServer auctionServer = null;
    
    /**
     * setup the auction an actual auction
     */
    AuctionImpl(AuctionServer auctionSvr) {
    	auctionServer = auctionSvr;    	
        System.out.println( "StartUp Auction Session..." );
    }    
    
	/**
	 * will read a set of auction input: <auctionItem>;bidder(n),initialBid,maxBid,increment;bidder(n+1), ...
	 * (i.e. Bicycle;Alice,50,80,3;Aaron,60,82,2;Amanda,55,85,5) 
	 * @param args
	 * @throws InterruptedException
	 */
    public static void main(String[] args) throws InterruptedException
    {
    	auction = new AuctionImpl(new AuctionServer(DEBUG));
        
        for (int i=0; i < args.length; i++) {
        	
	        String auctionData = args[i];
	        
	        String[] auctionInfo = null;
	        if ((auctionData != null) && (auctionData.length() > 0)) {
	        	auctionInfo = auctionData.split(";");
	        } else {
	        	continue;
	        }	
	        
	        auction.runSingleAuction(auctionInfo);	
        }                        
        
        auction.shutdown();
    } 
    
    /**
     * will reset the Auction for the next set of auction data
     */
    private void resetAuction() {
    	bidders.clear();
    	winner.clear();
    }
    
    /**
     * Getter for auctionItem, which is a String, representing the item currently being auctioned. 
     *      
     */
    public String getAuctionItem() {
    	return auctionItem;
    }
    
    /**
     * Setter for auctionItem
     * @param item
     */
    private void setAuctionItem(String item) {
    	auctionItem = item;
    }
    
    /**
     * Initialize the bidders auctionItem for a new auction
     * @param auctionInfo
     * @return
     */
    private String initializeAuction(String[] auctionInfo) {
     
    	//Clear the auction values
    	resetAuction();
    	
        setAuctionItem(auctionInfo[0]);
                      
        int i = 1;
        while (i < auctionInfo.length) {
	        String b1BidParams[] = auctionInfo[i++].split(",");
	        bidders.add(new Bidder(b1BidParams[0], b1BidParams[1], b1BidParams[2], b1BidParams[3]));
        }
        
        return auctionItem;
    }
    
    
    /**
     * Run a single Auction, given the auction information.  Auction input will be an array of Strings.
     * The first array item will be the name of the auction item.  The remainder of the array will be a 
     * series of bidders, consisting of a comma delimited string of the following fields: (bidderName, currentBid, maxBid, bidIncrement)
     * @param auctionInfo
     */
    public void runSingleAuction(String[] auctionInfo) {    	        
        initializeAuction(auctionInfo);	        
        processAuctionBids();        	        
        processWinner(winner);
    }
    
    
    /**
     * Process the bidding part of the auction.  Bidding will continue until there is a single winner, and no other bidders are capable of bidding more.
     */
    private void processAuctionBids() {
        
        System.out.println("\n Auctioning off the " + getAuctionItem() + " ............................................");
        
        int passNumber = 0;
	    while (biddingStillPossible(bidders, winner)) {
	       passNumber++;
	       if (passNumber == 1) {
	          System.out.println("\n begin pass:" + passNumber + " >> " + showStats(bidders, true));
	       }
	       auctionServer.makeBids(bidders, winner);          	           
	       
	       while (!auctionServer.allThreadsCompleted()) {
	 		   //wait for all the threads to finish
	 	   }  
	       
	       System.out.println(" after pass:" + passNumber + " >> " + showStats(bidders, false));
	       
	       //Failsafe, (for now...)  Could probably also set a time limit to the auction...
	       if (passNumber > MAX_NUMBER_OF_PASSES) {
	    	   System.out.println("WARNING: forced termination after max number of bid cycles !!\n");
	    	   auctionServer.shutdownAuctionServer();
	    	   //System.exit(1);
	       }
	    }
    }
        
    /**
     * will print out the current auction stats at the start of an auction, and after each bidder has bid
     * @param bidders - a set of bidders for the current auction item
     * @param isInitial - true if it is the opening bid
     * @return the auction status string
     */
    private String showStats(List<Bidder> bidders, boolean isInitial) {
    	
    	String passType = (isInitial) ? " Initial" : " Current";
    	String stats = passType + " Bid:" + winner.getWinningBid() + " ";
    	
    	for (Bidder b : bidders) {
    	   String name = (winner.getWinningName().equals(b.getName()) ? b.getName().toUpperCase() : b.getName());
    	   stats = stats + " " + name + "-" + b.getCurrentBid() + ":" + b.getMaxBid() + ":" + b.getIncrement() + " ";	
    	}
    	
    	return stats;
    }  
    
    
    /**
     *  Show information on the winner.  Eventually we could expand functionality to contact the winner...
     */
    private void processWinner(Winner winner) {        
        System.out.println("\n The winner of the " + this.getAuctionItem() + " is " + winner.getWinningName() + ", with the winning bid of: " + winner.getWinningBid());
        System.out.println("\n============================================================================================\n");
    }
    
    
    /**
     * true if at least on of the current bidders can still increase their bid again
     * @param bidders - a set of bidders for the current auction item
     * @return true if any of the bidders can still make bids
     */
    private boolean biddingStillPossible(List<Bidder> bidders, Winner winner) {
    	
    	for (Bidder b : bidders) {
    	   if (b.couldBidAgain(winner)) {
    		   return true;
    	   }
    	}
    	
    	return false;
    }
    
    /**
     * shutdown the auction 
     */
    public void shutdown() {
    	auctionServer.shutdownAuctionServer();
    }           

}
