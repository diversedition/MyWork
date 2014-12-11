package test;

import java.util.LinkedHashMap;
import java.util.Map;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import priceline.GameBoard_10x10_A;
import priceline.Node;
	
/**
 * Class GameBoard_10x10_ATest  
 *
 */
public class GameBoard_10x10_ATest {
    
	/**
	 * Constructor
	 */
	public GameBoard_10x10_ATest() {
		
	}
	
	@Test
	public void testInitializeGameBoard() {
		GameBoard_10x10_A gameBoard_10x10_A = null;
		
		try {
		    gameBoard_10x10_A = new GameBoard_10x10_A();
		    assertNotNull(gameBoard_10x10_A);
		    
		    gameBoard_10x10_A.initializeGameBoard();
		    assertNotNull(gameBoard_10x10_A);
		}
		catch(Exception e) {
			System.out.println("Exception in testPopulateGameBoard e=" + e);
			fail("Exception in test e=" + e);
		}		
	}

	
	@Test
	public void testPopulateGameBoard() {
		GameBoard_10x10_A gameBoard_10x10_A = null;
		
		try {
		    gameBoard_10x10_A = new GameBoard_10x10_A();
		    assertNotNull(gameBoard_10x10_A);
		    
		    gameBoard_10x10_A.populateGameBoard();
		    assertNotNull(gameBoard_10x10_A);
		    
		    assertEquals(17, gameBoard_10x10_A.getGameBoard().size());
		    
		    //gameBoard_10x10_A.validateGameBoard();
		    //assertNotNull(gameBoard_10x10_A);
		}
		catch(Exception e) {
			System.out.println("Exception in testPopulateGameBoard e=" + e);
			fail("Exception in test e=" + e);
		}		
	}
	
	
	//TODO
	@Test
	public void testPopulateGameBoardFromFile() {
	}
	 
	
	//TODO
	@Test
	public void testGetImageFile() {
	}
	
	
	@Test
	public void testValidateGameBoard_Success() {

		GameBoard_10x10_A gameBoard_10x10_A = null;
		
		try {
		    gameBoard_10x10_A = new GameBoard_10x10_A();
		    assertNotNull(gameBoard_10x10_A);
		    
		    gameBoard_10x10_A.populateGameBoard();
		    assertNotNull(gameBoard_10x10_A);
		    
		    assertEquals(17, gameBoard_10x10_A.getGameBoard().size());
		    
		    gameBoard_10x10_A.validateGameBoard();
		    assertNotNull(gameBoard_10x10_A);
		}
		catch(Exception e) {
			System.out.println("Exception in testValidateGameBoard e=" + e);
			fail("Exception in test e=" + e);
		}		
	}
	
	
	@Test
	public void testValidateGameBoard_Duplicate_Jump_Values() {

		GameBoard_10x10_A gameBoard_10x10_A = null;
		
		try {
		    gameBoard_10x10_A = new GameBoard_10x10_A();
		    assertNotNull(gameBoard_10x10_A);		
			
		    Map<Integer, Node> linkedHashMap = new LinkedHashMap<Integer, Node>();	
		    gameBoard_10x10_A.setGameBoard(linkedHashMap);
		    
		    //gameBoard_10x10_A.populateGameBoard();
		    gameBoard_10x10_A.putSquare(gameBoard_10x10_A.getGameBoard(), 1, 20);
		    gameBoard_10x10_A.putSquare(gameBoard_10x10_A.getGameBoard(), 10, 20);
		    gameBoard_10x10_A.putSquare(gameBoard_10x10_A.getGameBoard(), 4, 2);
		    
		    assertNotNull(gameBoard_10x10_A);
		    assertEquals(3, gameBoard_10x10_A.getGameBoard().size());
		    
		    gameBoard_10x10_A.validateGameBoard();		    
		}
		catch(Exception e) {
			System.out.println("Exception in testPopulateGameBoard e=" + e);
			assertEquals("priceline.GameBoardCreationException: both square and nextSquare values must not already exist as jumpSquare values.", e.toString());
			//fail("Exception in test e=" + e);
		}		
		
	}
	
	
	@Test
	public void testValidateGameBoard_Failure_BottomRow() {

		GameBoard_10x10_A gameBoard_10x10_A = null;
		
		try {
		    gameBoard_10x10_A = new GameBoard_10x10_A();
		    assertNotNull(gameBoard_10x10_A);		
			
		    Map<Integer, Node> linkedHashMap = new LinkedHashMap<Integer, Node>();	
		    gameBoard_10x10_A.setGameBoard(linkedHashMap);
		    
		    //gameBoard_10x10_A.populateGameBoard();
		    gameBoard_10x10_A.putSquare(gameBoard_10x10_A.getGameBoard(), 1, 20);
		    gameBoard_10x10_A.putSquare(gameBoard_10x10_A.getGameBoard(), 10, 25);
		    gameBoard_10x10_A.putSquare(gameBoard_10x10_A.getGameBoard(), 4, 2);
		    
		    assertNotNull(gameBoard_10x10_A);
		    assertEquals(3, gameBoard_10x10_A.getGameBoard().size());
		    
		    gameBoard_10x10_A.validateGameBoard();		    
		}
		catch(Exception e) {
			System.out.println("Exception in testPopulateGameBoard e=" + e);
			assertEquals("priceline.GameBoardValidationException: The bottom row cannot have chutes.", e.toString());
		}		
		
	}
	
	
	@Test
	public void testValidateGameBoard_Failure_TopRow() {

		GameBoard_10x10_A gameBoard_10x10_A = null;
		
		try {
		    gameBoard_10x10_A = new GameBoard_10x10_A();
		    assertNotNull(gameBoard_10x10_A);		
			
		    Map<Integer, Node> linkedHashMap = new LinkedHashMap<Integer, Node>();	
		    gameBoard_10x10_A.setGameBoard(linkedHashMap);
		    		    
		    gameBoard_10x10_A.putSquare(gameBoard_10x10_A.getGameBoard(), 1, 20);
		    gameBoard_10x10_A.putSquare(gameBoard_10x10_A.getGameBoard(), 10, 22);
		    gameBoard_10x10_A.putSquare(gameBoard_10x10_A.getGameBoard(), 94, 99);
		    
		    assertNotNull(gameBoard_10x10_A);
		    assertEquals(3, gameBoard_10x10_A.getGameBoard().size());
		    
		    gameBoard_10x10_A.validateGameBoard();		    
		}
		catch(Exception e) {
			System.out.println("Exception in testPopulateGameBoard e=" + e);
			assertEquals("priceline.GameBoardValidationException: The top row cannot have ladders.", e.toString());
		}		
	}

}
