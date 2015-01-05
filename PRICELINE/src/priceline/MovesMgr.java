package priceline;

import java.util.List;
import java.util.Set;

/**
 * The Class MovesMgr.
 */
public class MovesMgr {
	
	/** The Constant VICTORY_TEXT. */
	private final static String VICTORY_TEXT="Victory : Game Over !!";
	
	/** The Constant LADDER_TEXT. */
	private final static String LADDER_TEXT=" - Ladder -> ";
	
	/** The Constant CHUTE_TEXT. */
	private final static String CHUTE_TEXT=" - Chute  -> ";
	
	/** The Constant INITIAL_TEXT. */
	private final static String INITIAL_TEXT="At Starting Point";
	
	/** The Constant CANT_MOVE_TEXT. */
	private final static String CANT_MOVE_TEXT="Could not advance";
	
	/** The Constant DRAW_TEXT. */
	private final static String DRAW_TEXT="\nIMPORTANT NOTICE:\nMax rounds have been used up, with no winner.  This game ends in a Draw !!";
	
	/** The moves. */
	public List<Move> moves = null;
	
	/** The game board impl. */
	GameBoardImpl gameBoardImpl = null;
	

	/**
	 * Instantiates a new moves mgr.
	 *
	 * @param mvs the mvs
	 * @param gmBoardImpl the gm board impl
	 */
	public MovesMgr(List<Move> mvs, GameBoardImpl gmBoardImpl) {
	    this.moves = mvs;
	    this.gameBoardImpl = gmBoardImpl;
	}
	
	
	/**
	 * Record move.
	 *
	 * @param player the player
	 * @param spinNumber the spin number
	 * @param startSq the start sq
	 * @param moveType the move type
	 */
	public void recordMove(Player player, int spinNumber, int startSq, GameBoard.MoveType moveType) {
		Move move = new Move(player, spinNumber, startSq, moveType);
		moves.add(move);		
	}
	
	
	/**
	 * Record victory.
	 *
	 * @param p the p
	 */
	public void recordVictory(Player p) {
		recordMove(p, 0, gameBoardImpl.getWinningValue(), GameBoard.MoveType.VICTORY_MOVE_TYPE);
	}
	
	
	/**
	 * Record draw.
	 *
	 * @param p the p
	 */
	public void recordDraw(Player p) {
		recordMove(p, 0, 0, GameBoard.MoveType.DRAW_MOVE_TYPE);
	}
	
	
	/**
	 * Record start.
	 *
	 * @param p the p
	 */
	public void recordStart(Player p) {
		recordMove(p, 0, gameBoardImpl.getInitialValue(), GameBoard.MoveType.INITIAL_MOVE_TYPE);
	}
	

	
	/**
	 * Append next square.
	 *
	 * @param startSq the start sq
	 * @param spinNumber the spin number
	 * @param buff the buff
	 */
	private void appendNextSquare(int startSq, int spinNumber, StringBuffer buff) {
		
	    int nextSq = startSq + spinNumber;
        if (nextSq < 10) {
    	    buff.append(" ");
        }
        
	    buff.append(nextSq);
    }   
	
	
	/**
	 * Append spin value.
	 *
	 * @param spinNumber the spin number
	 * @param buff the buff
	 */
	private void appendSpinValue(int spinNumber, StringBuffer buff) {
		buff.append(" SpinValue: ");
		buff.append(spinNumber);	
		buff.append(" -> ");
	}
	
	
	/**
	 * Append jump results.
	 *
	 * @param nextSq the next sq
	 * @param jumpText the jump text
	 * @param buff the buff
	 */
	private void appendJumpResults(int nextSq, String jumpText, StringBuffer buff) {
		buff.append(jumpText);
		buff.append(nextSq);
	}
	
	
	/** 
	 * Get moves.
	 * 
	 * @return - the moves List.
	 */
	public List<Move> getMoves() {
		return moves;
	}
	
	
	/**
	 * Display all moves.
	 *
	 * @param players the players
	 */
	void displayAllMoves(Set<Player> players) {
		//format is: 
        //Round-[<round #>]
        //<PlayerName> : move[#] <startSq> -> SpinValue: <spinValue> -> (<nextSq> [ -> (LADDER|CHUTE) -> endSq)]|[ -> VICTORY])
        //Initial Start points, Victories and Draws are also noted.

		//Example:
        //Round-[19]
        //      John : move[76] 94 ->  SpinValue: 1 -> 95 - Chute  -> 75
        //      Bill : move[74] 99 ->  SpinValue: 5 -> Could not advance
        //      Ann : move[75] 20 ->  SpinValue: 2 -> 22
		
		StringBuffer moveBuff = new StringBuffer();
		
		int loopCounter = 0;
		int round = 0;
				
		for (Move m : moves) {			
			GameBoard.MoveType moveType = m.getMoveType();
			
			if ((moveType != GameBoard.MoveType.INITIAL_MOVE_TYPE) && 
				(moveType != GameBoard.MoveType.VICTORY_MOVE_TYPE) && 
				(moveType != GameBoard.MoveType.DRAW_MOVE_TYPE)) {
				
				//Print the round; and skip a line between each round
				if ((loopCounter % players.size()) == 0) {
					moveBuff.append("\nRound-[");
					moveBuff.append(round++);
					moveBuff.append("]\n");
				}
				
				moveBuff.append(m.getPlayer().getNormalizedPlayerName());
			    moveBuff.append(" : move[");
			    int loop = loopCounter++;
			    String loopStr = (loop < 10) ? (" " + String.valueOf(loop)) : String.valueOf(loop); 
			    moveBuff.append(loopStr);
			    moveBuff.append("] ");
			    if (m.getStartSq() < 10) {
			    	moveBuff.append(" ");
			    }
			    moveBuff.append(m.getStartSq());
			    moveBuff.append(" -> ");
			}
			else {
				if (moveType != GameBoard.MoveType.DRAW_MOVE_TYPE) {
				    moveBuff.append(m.getPlayer().getNormalizedPlayerName());
				    moveBuff.append(" -> ");
				}
			}			
			
			switch (moveType) {
			case CANT_MOVE_MOVE_TYPE : {
				appendSpinValue(m.getSpinNumber(), moveBuff);
				moveBuff.append(CANT_MOVE_TEXT);
				break;
			}
			case LADDER_MOVE_TYPE : {
				appendSpinValue(m.getSpinNumber(), moveBuff);
				appendNextSquare(m.getStartSq(), m.getSpinNumber(), moveBuff);			
				appendJumpResults(m.getEndSq(), LADDER_TEXT, moveBuff);
				break;
			}
			case CHUTE_MOVE_TYPE : {
				appendSpinValue(m.getSpinNumber(), moveBuff);
				appendNextSquare(m.getStartSq(), m.getSpinNumber(), moveBuff);				
				appendJumpResults(m.getEndSq(), CHUTE_TEXT, moveBuff);
				break;
			}
			case INITIAL_MOVE_TYPE : {
				moveBuff.append(INITIAL_TEXT);
				break;
			}
			case VICTORY_MOVE_TYPE : {
				moveBuff.append(VICTORY_TEXT);
				break;
			}
			case DRAW_MOVE_TYPE : {
				moveBuff.append(DRAW_TEXT);
				break;
			}
			default : {
				appendSpinValue(m.getSpinNumber(), moveBuff);
				appendNextSquare(m.getStartSq(), m.getSpinNumber(), moveBuff);				
				break;
			}//default
			
		  }	//switch
			
		  moveBuff.append("\n");
			
		}//for
		
		System.out.println(moveBuff.toString());
	}
	
}
