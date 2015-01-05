/**
 * 
 */
package test;

import org.junit.Test;

import static org.junit.Assert.fail;
import static org.junit.Assert.assertEquals;
import priceline.GameBoardImpl;
import priceline.GameBoard_10x10_A;
import priceline.GameBoardMgr;

/**
 * Class GameBoardMgrTest
 * @author Eugene
 *
 */
public class GameBoardMgrTest {
	
	/**
	 * Test getGameBoard
	 */
	@Test
	public void testGetGameBoard() {
		
		try {
			GameBoardMgr gameBoardMgr = new GameBoardMgr();
			
			String gameBoardName = "priceline.GameBoard_10x10_A";
		    GameBoardImpl gameBoardImpl_real = gameBoardMgr.getGameBoard(gameBoardName);
		    
		    GameBoardImpl gameBoardImpl_hardcoded = new GameBoard_10x10_A();
		    gameBoardImpl_hardcoded.initializeGameBoard();
		    
		    assertEquals(gameBoardImpl_real.getClass(), gameBoardImpl_hardcoded.getClass());
		} 
		catch (Exception e) {
			fail(e.toString());
		}
	}

}
