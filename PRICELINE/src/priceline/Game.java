package priceline;

import java.util.Set;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;

/**********************************************************************************************************
 * Class Game
 * 
 * A Game has a spinner, gameboard, set of players, and list of moves.  The list of moves ties the spinner, 
 * gameboard, and players together.
 * 
 *   //TODO 
 **  players will have image file names in them
 **  gameboards will have image filenames in them
 *
 *   //TODO
 **  **  the whole game will be serializable
 *   new game files can be created and validated, and written to disk
 **  games can be paused and saved to player's online portfolio?
 *
 *  SOME ASSUMPTIONS
 *  a particular spinner is selected for a game - it MUST have a one in it !!  Max can be from 5 - 10
 *  a particular game is selected for a game - this is the gameboard (i.e. 100_10x10_A, etc)
 *  players will be in their play order
 *
 *  we can assume that a game will consist of picking players/image pairs and doing a spin
 *  players continue to spin until the play order is determined, this order will be an input for the game
 *  when we have validate a gameboard, a spinner, and a set of players we can make a START button available
 *  game continues until someone wins or some very high number of rounds have been used up, (as a fail-safe...)
 *  
 *  Any square cannot have both a chute and a ladder.
 *  Jump values for chutes and ladders must be in the valid range of squares
 *  Chutes must go down and ladders must go up
 *  The top row cannot have ladders
 *  The bottom row cannot have chutes
 *  
 *  EVENTUAL CHANGES:
 *  Add ability to pause a game
 *  Add ability to save an entire game to file
 *  Perhaps just save the state of a game, via the movesList?
 *  Add token image files
 *  Add gameboard image files
 *  Add more variations of gameboards
 *  Add more testcases
 ************************************************************************************************************/
public class Game {
		
	/** The game board impl. */
	private static GameBoardImpl gameBoardImpl = null;
	
	/** The spinner impl. */
	private static SpinnerImpl spinnerImpl = null;
	
	/** The players. */
	private static Set<Player> players = null;   
	
	/** The moves. */
	private static List<Move> moves = null;
	
	/** The moves mgr. */
	private static MovesMgr movesMgr = null;
	
	/** The last active player. */
	@SuppressWarnings("unused")
	private static Player lastActivePlayer = null;
	
	/** The Constant MAX_SPIN_VALUE. */
	private final static int MAX_SPIN_VALUE=6;	
	
	/** The Constant DEFAULT_GAMEBOARD_STR. */
	private final static String DEFAULT_GAMEBOARD_STR="priceline.GameBoard_10x10_A";
	
	/** The Constant PLAYERS_STRING. */
	private static String PLAYERS_STRING="John,Mary,Bill,Ann";
	
	
	/**
	 * Instantiates a new game.
	 */
	public Game() {		
		spinnerImpl = null;
		gameBoardImpl = null;
		moves = new ArrayList<Move>();
		players = new LinkedHashSet<Player>();		
	}
	
	
	/**
	 * Sets the up game.
	 */
	public void setUpGame() {
		//Select Spinner & Board				
		setSpinner();
		
		try {
		    setGameBoard();
		}
		catch (Exception e) {
			System.out.println("Error setting up gameboard e=" + e);
			System.out.println("exiting now...");
			System.exit(0);
		}
		
		//Set up the movesMgr to collect & display moves
		movesMgr = new MovesMgr(moves, gameBoardImpl);
				
		//Simulate the selection of players
		String playersStr = getPlayersStr();
		//System.out.println("PlayersStr=[" + playersStr + "]");
		
		if (players.size() == 0) {
		    selectPlayers(playersStr, moves);
		} else {
			//reset players and clear moves 
			clearGame();
		}
		
		initializeGame(movesMgr);
	}
	
		
	//Returns a random number between 1 and maxSpinValue, inclusive
	/**
	 * Gets the spin value.
	 *
	 * @param maxSpinValue the max spin value
	 * @return the spin value
	 */
	private int getSpinValue(int maxSpinValue) {		
		return (spinnerImpl.nextInt(maxSpinValue) + 1);
	}
	
	
	/**
	 * Gets the spin value.
	 *
	 * @return the spin value
	 */
	private int getSpinValue() {
		return getSpinValue(MAX_SPIN_VALUE);
	}
	
	
	/**
	 * Play game.
	 *
	 * @param players the players
	 */
	private void playGame(Set<Player> players) {
		
		System.out.println("\nStart the game...\n");
		
		//Set a round limit to 10*MAX_VALUE, as a failsafe for endless loops in our gameboard 
		int round = 0;
		int MAX_ROUNDS = (10*gameBoardImpl.getMaxValue());
		
		while (round++ < MAX_ROUNDS) {
			
			//Loop through players
			Iterator<Player> iterator = players.iterator();    

	        while (iterator.hasNext())
	        {
	           Player p = iterator.next();
			   
			   int startSquare = p.getCurrentSquare();

			   //Simulate using the spinner (returns 1 - 5)
			   int moveNumber = this.getSpinValue();
			   
			   //Get moveType, and move token if possible
			   GameBoard.MoveType moveType = gameBoardImpl.advanceToken(p, moveNumber);
			   
			   //Record the move or non-move                     
			   movesMgr.recordMove(p, moveNumber, startSquare, moveType);
			   
			   lastActivePlayer = p;
			   
		       //Check for win; (if win, record win & break)						
			   if (gameBoardImpl.gameHasBeenWonByCurrentPlayer(p.getCurrentSquare())) {
				   
				   movesMgr.recordVictory(p);
				   round = MAX_ROUNDS+1;
				   break;
				   
			   }//if
			   
			}//iterator
			
		}//while
		
		//Failsafe...
		if (!gameBoardImpl.gameHasBeenWonByAnyPlayer(players)) {
			movesMgr.recordDraw(null);
		}
	}
	
	
	//TODO - may want to have this in case users want to pause the game
	/** 
	 * Save the Game to disk.
	 * 
	 * @throws IOException
	 *
	private void saveGame() {
		
	}
	**/
	
	
	//TODO - will eventually be attached to a UI
	/**
	 * Gets the players str.
	 *
	 * @return the players str
	 */
	private String getPlayersStr() {
		return PLAYERS_STRING;             
	}
	
	
	/**
	 * Gets the set of players.
	 *
	 * @return the players ser
	 */
	public Set<Player> getPlayers() {
		return players;             
	}
	
		
	/**
	 * Gets the movesMgr.
	 *
	 * @return the movesMgr
	 */
	public MovesMgr getMovesMgr() {
		return movesMgr;
	}
	
	   
	/**
	 * refreshGame
	 *
	 * @param movesMgr the MovesMgr of moves
	 */
	private void initializeGame(MovesMgr movesMgr) {
		
		System.out.println("initialize the game...");
		
		for (Player p : players) {
			 //Record a start move for each player
		     movesMgr.recordStart(p);
		}
	}
	
	
	/**
	 * clearGame
	 *
	 * @param players the Set of Players 
	 * @param moves the List of moves
	 */
	private void clearGame() {
		
		System.out.println("reset the players...");
		
		for (Player p : players) {
			p.setCurrentSquare(0);
		}
				
	    //Clear the list of moves
	    moves.clear();
	}
	
	
	/**
	 * Select players.
	 *
	 * @param playersStr the players str
	 * @param moves the moves
	 */
	private void selectPlayers(String playersStr, List<Move> moves) {
		
		System.out.println("set the players...");
		
		String[] playerNames = playersStr.split(",");
		
		for (int i=0; i < playerNames.length; i++) {
			
			String name = playerNames[i];
			
			//Only create players with non-null & non-blank names !!
			if ((name != null) && (name.length() > 0)) {
			    Player p = new Player(playerNames[i], i);
			    players.add(p);
			}
		}
	}
	
	
	/**
	 * Sets the spinner.
	 */
	private void setSpinner() {
		
		System.out.println("set the spinner...");				
		//spinnerImpl = GameBoardMgr.getSpinner(MAX_SPIN_VALUE);
		spinnerImpl = GameBoardMgr.getSpinner();
	} 
	
	
	public static GameBoardImpl getGameBoardImpl() {
		return gameBoardImpl;
	}
	
		
	/**
	 * Sets the game board.
	 *
	 * @throws GameBoardInsertionException the game board insertion exception
	 */
	public void setGameBoard() throws GameBoardCreationException, GameBoardValidationException {
		
		System.out.println("set the gameboard...");
		
		GameBoardMgr gameBoardMgr = new GameBoardMgr();
		gameBoardImpl = gameBoardMgr.getGameBoard(DEFAULT_GAMEBOARD_STR);  
		
		if (gameBoardImpl == null) {
			System.out.print("  WARNING !! - The gameBoardImpl is null !!!!\n");
		} else {
			gameBoardImpl.initializeGameBoard();
		}
	}
	
	

	/**
	 * The main method.
	 *
	 * @param args the arguments
	 */
	public static void main(String[] args) {
		
		if (args.length > 0) {
		    PLAYERS_STRING=args[0];
		}
		
		Game game = new Game();
		game.setUpGame();
		
		Set<Player> players = game.getPlayers();
		
		//TODO - This could go in a while loop where we keep playing repeatedly...
		//game.clearGame();
		
		//Play the game...
		game.playGame(players);
		
		//Print final results
		movesMgr.displayAllMoves(players);
	}
	
}
