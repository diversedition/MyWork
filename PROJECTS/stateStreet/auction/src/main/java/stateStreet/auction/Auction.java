package stateStreet.auction;

public interface Auction {
	public void runSingleAuction(String[] auctionInfo);	
    public String getAuctionItem();
    public void shutdown();
}
