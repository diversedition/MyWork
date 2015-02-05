package stateStreet.auction;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for simple App.
 */
public class AuctionImplTest 
    extends TestCase
{
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public AuctionImplTest( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( AuctionImplTest.class );
    }

    /**
     * Rigourous Test :-)
     */
    public void testAuction()
    {
        assertTrue( true );
    }

    /*  Bicycle;Alice,50,80,3;Aaron,60,82,2;Amanda,55,85,5                 :: Amanda-85
        Scooter;Alice,700,725,2;Aaron,599,725,15;Amanda,625,725,8          :: Alice-722
        Boat;Alice,2500,3000,500;Aaron,2800,3100,201;Amanda,2501,3200,247  :: Aaron-3001 */
    public void testRunSingleAuction0()
    {
    	Auction auction = new AuctionImpl(new AuctionServer(false));
    	
    	String[] auctionInfo = {"Table", "James,50,70,5", "Henry,55,80,10", "Bill,45,65,15"}; 
    	auction.runSingleAuction(auctionInfo);
    	
    	assert(AuctionImpl.getWinner().getWinningName().equals("Henry"));
    	assert(AuctionImpl.getWinner().getWinningBid() == 75);
    }   
    
    public void testRunSingleAuction1()
    {
    	Auction auction = new AuctionImpl(new AuctionServer(false));
    	
    	String[] auctionInfo = {"Bicycle", "Alice,50,80,3", "Aaron,60,82,2", "Amanda,55,85,5"}; 
    	auction.runSingleAuction(auctionInfo);
    	
    	assert(AuctionImpl.getWinner().getWinningName().equals("Amanda"));
    	assert(AuctionImpl.getWinner().getWinningBid() == 85);
    }
    
    public void testRunSingleAuction2()
    {
    	Auction auction = new AuctionImpl(new AuctionServer(false));
    	
    	String[] auctionInfo = {"Scooter", "Alice,700,725,2", "Aaron,599,725,2", "Amanda,55,85,5"}; 
    	auction.runSingleAuction(auctionInfo);
    	
    	assert(AuctionImpl.getWinner().getWinningName().equals("Henry"));
    	assert(AuctionImpl.getWinner().getWinningBid() == 75);
    }
    
    public void testGetAuctionItem()
    {
    	Auction auction = new AuctionImpl(new AuctionServer(false));
    	
    	String[] auctionInfo = {"Boat", "James,50,70,5", "Henry,55,80,10"}; 
    	auction.runSingleAuction(auctionInfo);
    	String auctionItem = auction.getAuctionItem();
    	
    	assert(auctionItem.equals("Boat"));
    }   

}
