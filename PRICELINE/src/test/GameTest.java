package test;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Set;
import java.util.List;

import priceline.GameBoard;
import priceline.MovesMgr;
import priceline.Player;
import priceline.Game;
import priceline.GameBoardImpl;
import priceline.GameBoardMgr;
import priceline.GameBoard_10x10_A;
import priceline.Move;

/**
 * Class GameTest
 *
 */
public class GameTest {
	
	@Test
	public void testGamePlayerSetCreation() {
		Game game = new Game();
		
		try {
			//System.out.println("testGamePlayerCreation");
			game.setUpGame();
			
			Set<Player> linkedHashSet = game.getPlayers();
			Object players[] = linkedHashSet.toArray();
			assertEquals(linkedHashSet.size(), 4);
			
			//DEBUG
		    //for (int i=0; i<players.length; i++) {
		    //	System.out.println("player[" + i + "]:" + ((Player)players[i]).getPlayerName());
		    //}
			
			Player p1 = new Player("John", 1);
			Player p2 = new Player("Mary", 2);
			Player p3 = new Player("Bill", 3);
			Player p4 = new Player("Ann", 4);
			
			assertTrue(((Player)players[0]).getPlayerName().equals(p1.getPlayerName()));
			assertTrue(((Player)players[1]).getPlayerName().equals(p2.getPlayerName()));
			assertTrue(((Player)players[2]).getPlayerName().equals(p3.getPlayerName()));
			assertTrue(((Player)players[3]).getPlayerName().equals(p4.getPlayerName()));
		} 
		catch (Exception e) {
			fail(e.toString());
		}
	}
	
	
	@Test
	public void testMovesListPlayerCreation() {
		Game game = new Game();
		
		try {
			//System.out.println("testMovesListCreation");
			game.setUpGame();
			
			MovesMgr movesMgr = game.getMovesMgr();
			List<Move> moves = movesMgr.getMoves();
			assertEquals(moves.size(), 4);
			
			//DEBUG
			//System.out.println("moves.size()=" + moves.size());
			//int i=0;
			//for (Move m : moves) {
		    //   System.out.println("moves[" + (i++) + "]:" + (m.getMoveType()));
		    //}
			
			Player p1 = new Player("John", 1);
			Player p2 = new Player("Mary", 2);
			Player p3 = new Player("Bill", 3);
			Player p4 = new Player("Ann", 4);
			
			GameBoardImpl gameBoardImpl = Game.getGameBoardImpl();		   
			
			Move m1 = new Move(p1, 0, gameBoardImpl.getInitialValue(), GameBoard.MoveType.INITIAL_MOVE_TYPE);
			Move m2 = new Move(p2, 0, gameBoardImpl.getInitialValue(), GameBoard.MoveType.INITIAL_MOVE_TYPE);
			Move m3 = new Move(p3, 0, gameBoardImpl.getInitialValue(), GameBoard.MoveType.INITIAL_MOVE_TYPE);
			Move m4 = new Move(p4, 0, gameBoardImpl.getInitialValue(), GameBoard.MoveType.INITIAL_MOVE_TYPE);

			assertTrue(((Move)moves.get(0)).getPlayer().getPlayerName().equals(m1.getPlayer().getPlayerName()));
			assertTrue(((Move)moves.get(1)).getPlayer().getPlayerName().equals(m2.getPlayer().getPlayerName()));
			assertTrue(((Move)moves.get(2)).getPlayer().getPlayerName().equals(m3.getPlayer().getPlayerName()));
			assertTrue(((Move)moves.get(3)).getPlayer().getPlayerName().equals(m4.getPlayer().getPlayerName()));
		} 
		catch (Exception e) {
			fail(e.toString());
		}
	}
	
	
	@Test
	public void testMovesListInitialMoveValues() {
		Game game = new Game();
		
		try {
			//System.out.println("testMovesListInitialMovewsValues");
			game.setUpGame();
			
			MovesMgr movesMgr = game.getMovesMgr();
			List<Move> moves = movesMgr.getMoves();
			assertEquals(moves.size(), 4);
			
			//DEBUG
			//System.out.println("moves.size()=" + moves.size());
			//int i=0;
			//for (Move m : moves) {
		    //   System.out.println("moves[" + (i++) + "]:" + (m.getMoveType()));
		    //}
			
			Player p1 = new Player("John", 1);
			Player p2 = new Player("Mary", 2);
			Player p3 = new Player("Bill", 3);
			Player p4 = new Player("Ann", 4);
			
			GameBoardImpl gameBoardImpl = Game.getGameBoardImpl();		   
			
			Move m1 = new Move(p1, 0, gameBoardImpl.getInitialValue(), GameBoard.MoveType.INITIAL_MOVE_TYPE);
			Move m2 = new Move(p2, 0, gameBoardImpl.getInitialValue(), GameBoard.MoveType.INITIAL_MOVE_TYPE);
			Move m3 = new Move(p3, 0, gameBoardImpl.getInitialValue(), GameBoard.MoveType.INITIAL_MOVE_TYPE);
			Move m4 = new Move(p4, 0, gameBoardImpl.getInitialValue(), GameBoard.MoveType.INITIAL_MOVE_TYPE);

			assertTrue(((Move)moves.get(0)).getStartSq() == m1.getStartSq());
			assertTrue(((Move)moves.get(1)).getStartSq() == m2.getStartSq());
			assertTrue(((Move)moves.get(2)).getStartSq() == m3.getStartSq());
			assertTrue(((Move)moves.get(3)).getStartSq() == m4.getStartSq());			

			assertTrue(((Move)moves.get(0)).getSpinNumber() == m1.getSpinNumber());
			assertTrue(((Move)moves.get(1)).getSpinNumber() == m2.getSpinNumber());
			assertTrue(((Move)moves.get(2)).getSpinNumber() == m3.getSpinNumber());
			assertTrue(((Move)moves.get(3)).getSpinNumber() == m4.getSpinNumber());						

			assertTrue(((Move)moves.get(0)).getEndSq() == m1.getEndSq());
			assertTrue(((Move)moves.get(1)).getEndSq() == m2.getEndSq());
			assertTrue(((Move)moves.get(2)).getEndSq() == m3.getEndSq());
			assertTrue(((Move)moves.get(3)).getEndSq() == m4.getEndSq());
		} 
		catch (Exception e) {
			fail(e.toString());
		}
	}

	
	@Test
	public void testGameBoardImplCreation() {
		Game game = new Game();
		
		try {
			game.setGameBoard();
			
			GameBoardMgr gameBoardMgr = new GameBoardMgr();
			
			String gameBoardName = "priceline.GameBoard_10x10_A";
		    GameBoardImpl gameBoardImpl_fromGameBoardMgr = gameBoardMgr.getGameBoard(gameBoardName);
		    gameBoardImpl_fromGameBoardMgr.initializeGameBoard();
		    
		    GameBoardImpl gameBoardImpl_fromGame = Game.getGameBoardImpl();	
		    gameBoardImpl_fromGame.initializeGameBoard();
		    
		    GameBoardImpl gameBoardImpl_real = new GameBoard_10x10_A();
		    gameBoardImpl_real.initializeGameBoard();
		    
		    assertEquals(gameBoardImpl_real.getClass(), gameBoardImpl_fromGameBoardMgr.getClass());
		    assertEquals(gameBoardImpl_real.getClass(), gameBoardImpl_fromGame.getClass());
		} 
		catch (Exception e) {
			fail(e.toString());
		}
	}
	
}


