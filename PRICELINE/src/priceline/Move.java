package priceline;


/**
 * The Class Move.
 */
public class Move {	
	
	/** The player. */
	private final Player player;
	
	/** The spin number. */
	private final int spinNumber;
	
	/** The start sq. */
	private final int startSq;
	
	/** The end sq. */
	private final int endSq;
	
	/** The move type. */
	private final GameBoard.MoveType moveType;
	
	
	/**
	 * Instantiates a new move.
	 *
	 * @param p the p
	 * @param spinNum the spin num
	 * @param stSq the st sq
	 * @param mvType the mv type
	 */
	public Move(Player p, int spinNum, int stSq, GameBoard.MoveType mvType) {
		this.player = p;
		this.spinNumber = spinNum;
		this.startSq = stSq;
		this.endSq = (p != null) ? p.getCurrentSquare() : 0;  //includes any chute/ladder changes; p is null for a draw
		this.moveType = mvType;
	}
	
	
	/**
	 * Gets the player.
	 *
	 * @return the player
	 */
	public Player getPlayer() {
		return player;
	}
	
	
	/**
	 * Gets the spin number.
	 *
	 * @return the spin number
	 */
	public int getSpinNumber() {
		return spinNumber;
	}
	
	
	/**
	 * Gets the start sq.
	 *
	 * @return the start sq
	 */
	public int getStartSq() {
		return startSq;
	}
	
	
	/**
	 * Gets the end sq.
	 *
	 * @return the end sq
	 */
	public int getEndSq() {
		return endSq;
	}
	
	
	/**
	 * Gets the move type.
	 *
	 * @return the move type
	 */
	public GameBoard.MoveType getMoveType() {
		return moveType;
	}
}
