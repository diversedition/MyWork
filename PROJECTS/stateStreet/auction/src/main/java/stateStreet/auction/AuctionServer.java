package stateStreet.auction;

import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Will coordinate the multi-threaded exector for the auction
 * @author Eugene
 *
 */
class AuctionServer {

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
	void makeBids(List<Bidder> bidders, Winner winner) {
		for (Bidder b : bidders) {
	       if (b.couldBidAgain(winner)) {
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
