package priceline;


/**
 * The Class GameBoardMgr.
 */
public class GameBoardMgr {
	
	/**
	 * Instantiates a new game board mgr.
	 */
	public GameBoardMgr() {
		
	}

	
	/**
	 * Gets the game board.
	 *
	 * @param gameBoardName the game board name
	 * @return the game board
	 * @throws GameBoardInsertionException the game board insertion exception
	 */
	public GameBoardImpl getGameBoard(String gameBoardName) throws GameBoardCreationException {
		//Read the gameBoardName from some kind of persistent storage.
		
		Class<?> cl = null;
		GameBoardImpl gameBoardImpl = null;
		
		try {
			cl = Class.forName(gameBoardName);
		    gameBoardImpl = (GameBoard_10x10_A)cl.newInstance();
		    //gameBoardImpl = (GameBoard_10x10_A)cl.newInstance();
		}
		catch (Exception e) {
			System.out.println("Exception in getGameBoard e=" + e.toString());  
		}
		
		return gameBoardImpl;
	}
	
	
	/**
	 * Gets the spinner.
	 *
	 * @return the spinner
	 */
	public static SpinnerImpl getSpinner() {
		//Read the spinnerName from some kind of persistant storage.
		
		//SpinnerImpl spinnerImpl = new Spinner(maxSpinValue);
		SpinnerImpl spinnerImpl = new Spinner();
		
		return spinnerImpl;
	}
}
