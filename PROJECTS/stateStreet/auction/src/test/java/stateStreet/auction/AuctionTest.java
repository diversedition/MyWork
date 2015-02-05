package stateStreet.auction;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for simple App.
 */
public class AuctionTest 
    extends TestCase
{
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public AuctionTest( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( AuctionTest.class );
    }

    /**
     * Rigourous Test :-)
     */
    public void testAuction()
    {
        assertTrue( true );
    }
    
    public void testResetAuction()
    {
    	Auction auction = new Auction(new AuctionServer(false));
    	
    	//auction.
    	auction.resetAuction();
    	
    	//assert(auction.getBidders().size() == 0);
    }
}
