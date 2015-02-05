package stateStreet.auction;

public class Winner {
	int winningBid = 0;
	String winningName = "None";
	
	Winner() {
	    clear();
	}		
	
	Winner(int winningBid, String winningName) {
		this.winningBid = winningBid;
		this.winningName = winningName;
	}
	
	int getWinningBid() { return winningBid; }
	String getWinningName() { return winningName; }
	
	void setWinningBid(int winningBid) { this.winningBid = winningBid;	}
	void setWinningName(String winningName) { this.winningName = winningName; }
	
	void setFields(int winBid, String winName) {
		setWinningBid(winBid);
		setWinningName(winName);
	}
	
	public void clear() {
		winningBid = 0;
		winningName = "None";
	}
}
