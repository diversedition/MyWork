package priceline;


import javax.imageio.ImageIO;
import java.io.IOException;
import java.io.File;
import java.io.BufferedReader;
import java.io.Serializable;
import java.util.Map;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Collections;


/**
 * This Creates a 10x10 GameBoard.
 */
public class GameBoard_10x10_A extends GameBoard_100 implements GameBoardImpl, Serializable {		
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 3453068576834185075L;


	/**
	 * Instantiates a new game board_10x10_ a.
	 *
	 * @throws GameBoardInsertionException the game board insertion exception
	 */
	public GameBoard_10x10_A() throws GameBoardCreationException, GameBoardValidationException {
		super();
	}
	
	
	public void initializeGameBoard() throws GameBoardCreationException, GameBoardValidationException {		
		populateGameBoard();
	    validateGameBoard();
	}
	
	
	/**
	 * @see priceline.GameBoard#getImageFile(java.lang.String)
	 */
	@Override
	public void getImageFile(String imageFilename) throws IOException {
		
		try {
			File f = new File(imageFilename);
			image = ImageIO.read(f);
		}
		catch (IOException ioe) {
			
		}
		
	}
	
    
	/**
	 * Populate game board from file.
	 *
	 * @param fileName the file name
	 * @throws GameBoardInsertionException the game board insertion exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public void populateGameBoardFromFile(String fileName) throws GameBoardCreationException, IOException {
		
		if ((fileName != null) && (fileName.length() > 0)) {
			
			Map<Integer, Node> linkedHashMap = null;
			BufferedReader br = null;
			
			try {
				File f = new File(fileName);
				
				br = getBufferedReader(f); 
				linkedHashMap = new LinkedHashMap<Integer, Node>();
					
				 
				String line = null;
				while ((line = br.readLine()) != null) {
				    String[] strArray = line.split(",");
				    
				    if (strArray.length == 2) {
			    			    
				        String key = strArray[0];
				        if (key != null) {
				        	key = key.trim();
				        }
				        String nextSquare = strArray[1];
				        if (nextSquare != null) {
				        	nextSquare = nextSquare.trim();
				        }			        
				    
				        //Populate the HashMap with all the ladders/chutes for quick retrieval.	    	   
				        putSquare(linkedHashMap, Integer.valueOf(key).intValue(), Integer.valueOf(nextSquare).intValue());
				    }
				}//while
			} 
			catch(IOException ioe) {
			    throw new GameBoardCreationException("Each input line must have a key and a jumpSquare value.");
			}
			finally {
				if (br != null) {
					br.close();
				}
			}			   
	    
	        Map<Integer, Node> unmodifiableMap = Collections.unmodifiableMap(linkedHashMap);
	        this.setGameBoard(unmodifiableMap);
		} else {
			throw new GameBoardCreationException("An invalid filename was provided for gameboard initialization !");
		}
	}
	
    
	/**
	 * @see priceline.GameBoard#populateGameBoard()
	 */
	@Override
	public void populateGameBoard() throws GameBoardCreationException {		
		
	    Map<Integer, Node> linkedHashMap = new LinkedHashMap<Integer, Node>();	
	    
	    //Populate the HashMap with all the ladders/chutes for quick retrieval.	    	   
	    putSquare(linkedHashMap, 1, 38);
	    putSquare(linkedHashMap, 4, 14);
	    putSquare(linkedHashMap, 9, 31);	    
	    putSquare(linkedHashMap, 21, 42);
	    putSquare(linkedHashMap, 28, 84);
	    putSquare(linkedHashMap, 36, 44);
	    putSquare(linkedHashMap, 47, 26);
	    putSquare(linkedHashMap, 49, 11);	   
	    putSquare(linkedHashMap, 51, 67);
	    putSquare(linkedHashMap, 62, 19);
	    putSquare(linkedHashMap, 64, 60);
	    putSquare(linkedHashMap, 71, 91);
	    putSquare(linkedHashMap, 80, 100);
	    putSquare(linkedHashMap, 87, 24);
	    putSquare(linkedHashMap, 93, 73);
	    putSquare(linkedHashMap, 95, 75);
	    putSquare(linkedHashMap, 98, 78);
	    
	    Map<Integer, Node> unmodifiableMap = Collections.unmodifiableMap(linkedHashMap);
	    this.setGameBoard(unmodifiableMap);
	}
	
	
	/**
	 * @see priceline.GameBoard#validateGameBoard()
	 */
	@Override
	public void validateGameBoard() throws GameBoardValidationException {
		//Since this is a 10 x 10 matrix check to make sure we have no Chutes on bottom row, & no ladders on top row
		
		Iterator<Integer> iterator = gameBoard.keySet().iterator();
		while (iterator.hasNext()) {
			int intValue = iterator.next().intValue();
			
			//Check bottow row for chutes
			if (intValue <= 10) {
				
				if (gameBoard.containsKey(intValue)) {
					Node node = gameBoard.get(intValue);
					
					if (node.hasChute()) {
						throw new GameBoardValidationException("The bottom row cannot have chutes.");
					}
				}
			}//if bottom row
			
			//Check top row for ladders
			if (intValue >= 90) {

				if (gameBoard.containsKey(intValue)) {
					Node node = gameBoard.get(intValue);
					
					if (node.hasLadder()) {
						throw new GameBoardValidationException("The top row cannot have ladders.");
					}
				}
			}//if top row
		}//while
	}
	
	
	/**
	 * @see priceline.GameBoard_100#setGameBoard(java.util.Map)
	 */
	@Override
	public void setGameBoard(Map<Integer, Node> gmBoard) {
		super.setGameBoard(gmBoard);
	}

}
