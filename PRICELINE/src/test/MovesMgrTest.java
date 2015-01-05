/**
 * 
 */
package test;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertNotNull;

import java.util.Map;
import java.util.List;
import java.util.ArrayList;

import priceline.GameBoard;
import priceline.GameBoardImpl;
import priceline.GameBoard_10x10_A;
import priceline.Move;
import priceline.Node;
import priceline.MovesMgr;
import priceline.Player;

/**
 * @author Eugene
 *
 */

public class MovesMgrTest {
	
	@Test
	public void testRecordStart() {
		
		List<Move> moves = new ArrayList<Move>();
		GameBoardImpl gameBoardImpl = null;
		
		try {
		    gameBoardImpl = new GameBoard_10x10_A();
		    gameBoardImpl.initializeGameBoard();
		} catch(Exception ex) {
			System.out.println("Exception getting gameboard ex=" + ex);
		}
		
		MovesMgr movesMgr = new MovesMgr(moves, gameBoardImpl);
		
		//Simulate a "start" for "Ann"
		Player p1 = new Player("Ann", 1);
		p1.setCurrentSquare(gameBoardImpl.getInitialValue());
		
		//Record the "start"
		movesMgr.recordMove(p1, 0, gameBoardImpl.getInitialValue(), GameBoard.MoveType.INITIAL_MOVE_TYPE);
		
		Move move = movesMgr.getMoves().get(0);
		assertEquals(move.getPlayer().getPlayerName(), "Ann");
		assertEquals(move.getMoveType(), GameBoard.MoveType.INITIAL_MOVE_TYPE);
		assertEquals(move.getSpinNumber(), 0);
		assertEquals(move.getStartSq(), move.getPlayer().getCurrentSquare());
		assertEquals(move.getEndSq(), gameBoardImpl.getInitialValue());
	}

	
	@Test
	public void testLadderMove() {
		
		List<Move> moves = new ArrayList<Move>();
		GameBoardImpl gameBoardImpl = null;
		
		try {
		    gameBoardImpl = new GameBoard_10x10_A();
		    gameBoardImpl.initializeGameBoard();
		} catch(Exception ex) {
			System.out.println("Exception getting gameboard ex=" + ex);
		}
		
		MovesMgr movesMgr = new MovesMgr(moves, gameBoardImpl);
		assertEquals(moves.size(), 0);
		
		//Simulate a "default" move for "Ann"
		Player p1 = new Player("Ann", 1);
		
		//Start at 31 and spin 6 to get the ladder at 36, to 44
		int startSq = 31;
		int spinValue = 5;
		
		p1.setCurrentSquare(startSq);
		int pStSq = p1.getCurrentSquare();
		
		GameBoard.MoveType moveType = gameBoardImpl.advanceToken(p1, spinValue);
		
		//Record the "ladder" move		
		movesMgr.recordMove(p1, spinValue, pStSq, moveType);
		
		Move move = movesMgr.getMoves().get(0);
		assertEquals(move.getPlayer().getPlayerName(), p1.getPlayerName());
		assertEquals(move.getMoveType(), GameBoard.MoveType.LADDER_MOVE_TYPE);
		assertEquals(move.getSpinNumber(), spinValue);
		assertEquals(move.getStartSq(), pStSq);
		assertEquals(move.getStartSq(), startSq);
		
		int tempSq = startSq + spinValue;
		Map<Integer, Node> map = gameBoardImpl.getGameBoard();
		Node node = map.get(tempSq);
		
		assertNotNull(node);
	    int nextSq = node.getNextValue();
		
		assertEquals(move.getEndSq(), nextSq);
		assertEquals(move.getEndSq(), p1.getCurrentSquare());
		assertEquals(move.getEndSq(), 44);
	}	
		
	
	@Test
	public void testChuteMove() {
		
		List<Move> moves = new ArrayList<Move>();
		GameBoardImpl gameBoardImpl = null;
		
		try {
		    gameBoardImpl = new GameBoard_10x10_A();
		    gameBoardImpl.initializeGameBoard();
		} catch(Exception ex) {
			System.out.println("Exception getting gameboard ex=" + ex);
		}
		
		MovesMgr movesMgr = new MovesMgr(moves, gameBoardImpl);
		assertEquals(moves.size(), 0);
		
		//Simulate a "default" move for "Ann"
		Player p1 = new Player("Ann", 1);
		
		//Start at 44 and spin 3 to get the chute at 47, to 26
		int startSq = 44;
		int spinValue = 3;
		
		p1.setCurrentSquare(startSq);
		int pStSq = p1.getCurrentSquare();
		
		GameBoard.MoveType moveType = gameBoardImpl.advanceToken(p1, spinValue);
		
		//Record the "chute" move		
		movesMgr.recordMove(p1, spinValue, pStSq, moveType);
		
		Move move = movesMgr.getMoves().get(0);
		assertEquals(move.getPlayer().getPlayerName(), p1.getPlayerName());
		assertEquals(move.getMoveType(), GameBoard.MoveType.CHUTE_MOVE_TYPE);
		assertEquals(move.getSpinNumber(), spinValue);
		assertEquals(move.getStartSq(), pStSq);		
		assertEquals(move.getStartSq(), 44);
		
		int tempSq = startSq + spinValue;
		Map<Integer, Node> map = gameBoardImpl.getGameBoard();
		Node node = map.get(tempSq);
		
		assertNotNull(node);
	    int nextSq = node.getNextValue();
		
	    assertEquals(nextSq, 26);
		assertEquals(move.getEndSq(), nextSq);
		assertEquals(move.getEndSq(), p1.getCurrentSquare());
		assertEquals(move.getEndSq(), 26);
	}	
		
	
	@Test
	public void testDefaultMove() {
		
		List<Move> moves = new ArrayList<Move>();
		GameBoardImpl gameBoardImpl = null;
		
		try {
		    gameBoardImpl = new GameBoard_10x10_A();
		    gameBoardImpl.initializeGameBoard();
		} catch(Exception ex) {
			System.out.println("Exception getting gameboard ex=" + ex);
		}
		
		MovesMgr movesMgr = new MovesMgr(moves, gameBoardImpl);
		
		//Simulate a "default" move for "Ann"
		Player p1 = new Player("Ann", 1);
		
		//Start at 21 and spin 3 to go to 24
		int startSq = 21;
		int spinValue = 3;
		
		p1.setCurrentSquare(startSq);
		int pStSq = p1.getCurrentSquare();
		
		GameBoard.MoveType moveType = gameBoardImpl.advanceToken(p1, spinValue);
		
		//Record the "default" move		
		movesMgr.recordMove(p1, spinValue, pStSq, moveType);
		
		Move move = movesMgr.getMoves().get(0);
		assertEquals(move.getPlayer().getPlayerName(), p1.getPlayerName());
		assertEquals(move.getMoveType(), GameBoard.MoveType.DEFAULT_MOVE_TYPE);
		assertEquals(move.getSpinNumber(), spinValue);
		assertEquals(move.getStartSq(), pStSq);
		
		int tempSq = startSq + spinValue;
		Map<Integer, Node> map = gameBoardImpl.getGameBoard();
		Node node = map.get(tempSq);
		
		assertNull(node);		
		assertEquals(move.getEndSq(), tempSq);
		assertEquals(move.getEndSq(), p1.getCurrentSquare());
		assertEquals(move.getEndSq(), 24);
	}
	
	
	@Test
	public void testRecordCantMove() {
		
		List<Move> moves = new ArrayList<Move>();
		GameBoardImpl gameBoardImpl = null;
		
		try {
		    gameBoardImpl = new GameBoard_10x10_A();
		    gameBoardImpl.initializeGameBoard();
		} catch(Exception ex) {
			System.out.println("Exception getting gameboard ex=" + ex);
		}
		
		MovesMgr movesMgr = new MovesMgr(moves, gameBoardImpl);
		
		//Simulate a "win" for "Ann"
		Player p1 = new Player("Ann", 1);
		
		//Start at 96 and spin 5, move 0, and stay at 96
		int startSq = 96;
		//int spinValue = 5;
		int moveValue = 0;
				
		p1.setCurrentSquare(startSq);
		int pStSq = p1.getCurrentSquare();
		
		//Record the "can't Move" move
		movesMgr.recordMove(p1, moveValue, pStSq, GameBoard.MoveType.CANT_MOVE_MOVE_TYPE);
		
		Move move = movesMgr.getMoves().get(0);
		assertEquals(move.getPlayer().getPlayerName(), "Ann");
		assertEquals(move.getMoveType(), GameBoard.MoveType.CANT_MOVE_MOVE_TYPE);
		assertEquals(move.getSpinNumber(), moveValue);
		assertEquals(move.getStartSq(), move.getPlayer().getCurrentSquare());
		assertEquals(move.getEndSq(), startSq);
	}
	
	
	@Test
	public void testRecordVictory() {
		
		List<Move> moves = new ArrayList<Move>();
		GameBoardImpl gameBoardImpl = null;
		
		try {
		    gameBoardImpl = new GameBoard_10x10_A();
		    gameBoardImpl.initializeGameBoard();
		} catch(Exception ex) {
			System.out.println("Exception getting gameboard ex=" + ex);
		}
		
		MovesMgr movesMgr = new MovesMgr(moves, gameBoardImpl);
		
		//Simulate a "win" for "Ann"
		Player p1 = new Player("Ann", 1);
		p1.setCurrentSquare(gameBoardImpl.getWinningValue());
		
		//Record the "win"
		movesMgr.recordMove(p1, 0, gameBoardImpl.getWinningValue(), GameBoard.MoveType.VICTORY_MOVE_TYPE);
		
		Move move = movesMgr.getMoves().get(0);
		assertEquals(move.getPlayer().getPlayerName(), "Ann");
		assertEquals(move.getMoveType(), GameBoard.MoveType.VICTORY_MOVE_TYPE);
		assertEquals(move.getSpinNumber(), 0);
		assertEquals(move.getStartSq(), move.getPlayer().getCurrentSquare());
		assertEquals(move.getEndSq(), gameBoardImpl.getWinningValue());
	}

}
