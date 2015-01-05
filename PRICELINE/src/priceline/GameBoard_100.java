package priceline;

import java.util.Map;
import java.io.Serializable;

/**
 * The Class GameBoard_100.
 */
public abstract class GameBoard_100 extends GameBoard implements Serializable {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -7669260724479768047L;
	
	/** The Constant MAX_VALUE. */
	private final static int MAX_VALUE=100;
	
	/** The Constant MIN_VALUE. */
	private final static int MIN_VALUE=1;
	
	/** The Constant INITIAL_VALUE. */
	private final static int INITIAL_VALUE=MIN_VALUE-1;
	
	/** The Constant WINNING_VALUE. */
	private final static int WINNING_VALUE=MAX_VALUE;
	
	/** The game board. */
	Map<Integer, Node> gameBoard;
	
	/**
	 * Instantiates a new game board_100.
	 */
	public GameBoard_100() {
	}
	
	/**	 
	 * @see priceline.GameBoard#getMaxValue()
	 */
	@Override
	public int getMaxValue() {
		return MAX_VALUE;
	}
	
	/*
	 * @see priceline.GameBoard#getMinValue()
	 */
	@Override
	public int getMinValue() {
		return MIN_VALUE;
	}
	
	/**
	 * @see priceline.GameBoard#getInitialValue()
	 */
	@Override
	public int getInitialValue() {
		return INITIAL_VALUE;
	}
	
	/**
	 * @see priceline.GameBoard#getWinningValue()
	 */
	@Override
	public int getWinningValue() {
		return WINNING_VALUE;
	}
	
	/**
	 * @see priceline.GameBoard#getGameBoard()
	 */
	@Override
	public final Map<Integer, Node> getGameBoard() {
		return this.gameBoard;
	}
	
	
	/**
	 * Sets the game board.
	 *
	 * @param gmBoard the gm board
	 */
	public void setGameBoard(Map<Integer, Node> gmBoard) {
		this.gameBoard = gmBoard;
	}
}
