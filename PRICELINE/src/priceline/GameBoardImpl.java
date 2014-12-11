package priceline;

import java.util.Map; 
import java.util.Set;

/**
 * The Interface GameBoardImpl.
 */
public interface GameBoardImpl {
	
	/**
	 * Gets the game board.
	 *
	 * @return the game board
	 */
	public Map<Integer, Node> getGameBoard();

	
	/**
	 * Sets the game board.
	 *
	 * @param gameBoard the game board
	 */
	public void setGameBoard(Map<Integer, Node> gameBoard);  
	
	
	/**
	 * Gets the max value.
	 *
	 * @return the max value
	 */
	public int getMaxValue();
	
	
	/**
	 * Gets the min value.
	 *
	 * @return the min value
	 */
	public int getMinValue();
	
	
	/**
	 * Gets the initial value.
	 *
	 * @return the initial value
	 */
	public int getInitialValue();
	
	
	/**
	 * Gets the winning value.
	 *
	 * @return the winning value
	 */
	public int getWinningValue();
	
	
	/**
	 * Populate game board.
	 *
	 * @throws GameBoardInsertionException the game board insertion exception
	 */
	public void populateGameBoard() throws GameBoardCreationException;
	
	
	/**
	 * Validate game board.
	 *
	 * @throws GameBoardValidationException the game board validation exception
	 */
	public void validateGameBoard() throws GameBoardValidationException;
	
		
	/**
	 * Initialize game board, by populating and validating the GameBoard.
	 *
	 * @throws GameBoardInsertionException the game board insertion exception
	 */
	public void initializeGameBoard() throws GameBoardCreationException, GameBoardValidationException;
	
	
	/**
	 * Put square.
	 *
	 * @param gameBoard the game board
	 * @param square the square
	 * @param nextSquare the next square
	 * @throws GameBoardInsertionException the game board insertion exception
	 */
	public void putSquare(Map<Integer, Node> gameBoard, int square, int nextSquare) throws GameBoardCreationException;

	
	/**
	 * Advance token.
	 *
	 * @param p the p
	 * @param moves the moves
	 * @return the game board. move type
	 */
	public GameBoard.MoveType advanceToken(Player p, int moves);	
	
	
	/**
	 * Game has been won by current player.
	 *
	 * @param currentSquare the current square
	 * @return true, if successful
	 */
	public boolean gameHasBeenWonByCurrentPlayer(int currentSquare); 
	
	
	/**
	 * Game has been won by any player.
	 *
	 * @param players the players
	 * @return true, if successful
	 */
	public boolean gameHasBeenWonByAnyPlayer(Set<Player> players);
}
