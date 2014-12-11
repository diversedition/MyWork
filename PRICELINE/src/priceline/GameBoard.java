package priceline;

import java.awt.image.BufferedImage;

import java.io.IOException;
import java.io.FileInputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.File;
import java.io.Serializable;
import java.util.Map;
import java.util.Map.Entry;
import java.util.List;
import java.util.Set;
import java.util.Iterator;


/**
 * The Class GameBoard.
 */
public abstract class GameBoard implements Serializable {	
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -499521008208429321L;
	
	/** The moves. */
	List<Move> moves = null;
	
	/** The image name. */
	String imageName = null;
	
	/** The image. */
	BufferedImage image = null;
	
	/**
	 * Constructor for the abstract class GameBoard
	 */
	public GameBoard() {
	}
	 
	/**
	 * The Enum MoveType.
	 */
	public enum MoveType {
		
		/** The not set yet move type. */
		NOT_SET_YET_MOVE_TYPE,
		
		/** The initial move type. */
		INITIAL_MOVE_TYPE,
		
		/** The cant move move type. */
		CANT_MOVE_MOVE_TYPE,
		
		/** The default move type. */
		DEFAULT_MOVE_TYPE,
		
		/** The ladder move type. */
		LADDER_MOVE_TYPE,
		
		/** The chute move type. */
		CHUTE_MOVE_TYPE,
		
		/** The victory move type. */
		VICTORY_MOVE_TYPE,
		
		/** The draw move type. */
		DRAW_MOVE_TYPE;
	}
	
	/**
	 * Gets the max value.
	 *
	 * @return the max value
	 */
	public abstract int getMaxValue();	
	
	
	/**
	 * Gets the min value.
	 *
	 * @return the min value
	 */
	public abstract int getMinValue();
	
	
	/**
	 * Gets the initial value.
	 *
	 * @return the initial value
	 */
	public abstract int getInitialValue();	
	
	
	/**
	 * Gets the winning value.
	 *
	 * @return the winning value
	 */
	public abstract int getWinningValue();
	
	
	/**
	 * Gets the game board.
	 *
	 * @return the game board
	 */
	public abstract Map<Integer, Node> getGameBoard();
	
	
	/**
	 * Sets the moves list.
	 *
	 * @param moves the new moves list
	 */
	public void setMovesList(List<Move> moves) {
		this.moves = moves;
	}
	
	
	/**
	 * Gets the moves list.
	 *
	 * @return the moves list
	 */
	public List<Move> getMovesList() {
		return moves;
	}
		
	
	/**
	 * Check for valid square value.
	 *
	 * @param sqValue the sq value
	 * @param label the label
	 * @throws GameBoardInsertionException the game board insertion exception
	 */
	private void checkForValidSquareValue(int sqValue, String label) throws GameBoardCreationException {
		if ((sqValue < getMinValue()) || (sqValue > getMaxValue())) {
    		throw new GameBoardCreationException("The " + label + " value must be within the valid range of numbers for the game board.");
    	}
	}

		
	/**
	 * Game has been won by current player.
	 *
	 * @param currentSquare the current square
	 * @return true, if successful
	 */
	public boolean gameHasBeenWonByCurrentPlayer(int currentSquare) {
		return (currentSquare == getWinningValue()) ? true : false;
	}
	
	
	/**
	 * Game has been won by any player.
	 *
	 * @param players the players
	 * @return true, if successful
	 */
	public boolean gameHasBeenWonByAnyPlayer(Set<Player> players) {
		
		boolean gameHasBeenWon = false;
		
		for (Player p : players) {
			gameHasBeenWon = gameHasBeenWonByCurrentPlayer(p.getCurrentSquare());
			if (gameHasBeenWon) {
				break;
			}
		}
		
		return gameHasBeenWon;
	}
		

	/**
	 * Gets the image file.
	 *
	 * @param imageFilename the image filename
	 * @return the image file
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public abstract void getImageFile(String imageFilename) throws IOException;
	
	
	/**
	 * Populate game board.
	 *
	 * @throws GameBoardInsertionException the game board insertion exception
	 */
	public abstract void populateGameBoard() throws GameBoardCreationException;
	
	
	/**
	 * Validate game board.
	 *
	 * @throws GameBoardValidationException the game board validation exception
	 */
	public abstract void validateGameBoard() throws GameBoardValidationException;
	
	
	/**
	 * Gets the buffered reader.
	 *
	 * @param fin the fin
	 * @return the buffered reader
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	BufferedReader getBufferedReader(File fin) throws IOException {
		FileInputStream fis = new FileInputStream(fin);
	 
		//Construct BufferedReader from InputStreamReader
		BufferedReader br = new BufferedReader(new InputStreamReader(fis));
		
		return br;
	}
	
	
    /**
     * Put square.
     *
     * @param gameBoard the game board
     * @param square the square
     * @param nextSquare the next square
     * @throws GameBoardInsertionException the game board insertion exception
     */
    public void putSquare(Map<Integer, Node> gameBoard, int square, int nextSquare) throws GameBoardCreationException {
    	
    	//Make sure the two squares are not equal
    	if (square == nextSquare) {
    		throw new GameBoardCreationException("the square and next square cannot be equal.");
    	}
    	
    	//Make sure both square values are in the valid range of squares
    	checkForValidSquareValue(square, "square");
    	checkForValidSquareValue(nextSquare, "nextSquare");
    	
    	Integer sqKey = new Integer(square);
    	Integer nextSqKey = new Integer(nextSquare);
    	
    	//Only insert a new value if both square values are not already keys AND both square values are not already the final values in a chute or ladder
    	if ((gameBoard.containsKey(sqKey) == false) && (gameBoard.containsKey(nextSqKey) == false)) {
    		
    		//Go through all the Values in the Map and make sure either square or nextSquare is not a final jump value
    		Set<Entry<Integer, Node>> entrySet = gameBoard.entrySet();
    		
    		Iterator<Entry<Integer, Node>> iterator = entrySet.iterator();
    		while (iterator.hasNext()) {
    			Entry<Integer, Node> element = iterator.next();
    			
    			Node node = element.getValue();
    			int jumpSquare = node.getNextValue();
    			
    		    if ((square == jumpSquare) || (nextSquare == jumpSquare)) {
    		    	throw new GameBoardCreationException("both square and nextSquare values must not already exist as jumpSquare values.");
    		    }
    		}    		
    		
    		//Create the valid Node for insertion into the gameBoard Map
    		Node node = new Node(square, nextSquare);
    	    gameBoard.put(sqKey, node);
    	} else {
    		throw new GameBoardCreationException("both square and nextSquare values must not already exist as key values.");
    	}
    }
	
	
	/**
	 * Advance token.
	 *
	 * @param p the p
	 * @param moves the moves
	 * @return the move type
	 */
	public MoveType advanceToken(Player p, int moves) {
				
		MoveType moveType = GameBoard.MoveType.NOT_SET_YET_MOVE_TYPE;
		int startSquare = p.getCurrentSquare();
		
		//Add the spinner value to the start square value
		int newSquare = startSquare + moves;
		
		//Check if player can move
		if (newSquare <= this.getMaxValue()) {
			
		   //At the very least we are a default move, maybe more...
		   moveType = GameBoard.MoveType.DEFAULT_MOVE_TYPE; 
			
		   //Check if the new square has a chute or ladder
		   Node node = getGameBoard().get(newSquare);
		   
		   //If a node exists, adjust for the new chute/ladder value
		   if (node != null) {
		   
		     int nextSquare = node.getNextValue();
		     if ((nextSquare > 0) && (nextSquare != newSquare)) {			 
			
		    	 //Set the moveType to chute or ladder
		    	 moveType = (nextSquare > newSquare) ? GameBoard.MoveType.LADDER_MOVE_TYPE : GameBoard.MoveType.CHUTE_MOVE_TYPE;
		    			     
			     //Update the newSquare value
			     newSquare = nextSquare;
		     }
		   }
		   
		   //Update the player's token to the new square
		   p.setCurrentSquare(newSquare);
		}
		else {
			//DO NOTHING, SINCE PLAYER CAN'T MOVE BEYOND MAX_VALUE; but mark it as such
			moveType = GameBoard.MoveType.CANT_MOVE_MOVE_TYPE;
		}
		
		return moveType;
	}
}
