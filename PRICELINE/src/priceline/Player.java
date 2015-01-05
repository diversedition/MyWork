package priceline;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * The Class Player.
 */
public class Player {
   
   /** The Constant MAX_PLAYER_NAMESIZE. */
   private final static int MAX_PLAYER_NAMESIZE=20;
   
   /** The player id. */
   private final int playerId;
   
   /** The player name. */
   private final String playerName;
   
   /** The normalized player name. */
   private final String normalizedPlayerName;
   
   /** The current square. */
   private int currentSquare;   //must NOT be final!
   
   /** The filename. */
   private String filename;
   
   /** The image. */
   private BufferedImage image;
 
   
   /**
    * Instantiates a new player.
    *
    * @param name the name
    * @param id the id
    */
   public Player(String name, int id) {
	   this.playerId = id;
	   this.playerName = name;
	   this.normalizedPlayerName = normalizePlayerName(playerName);
	   this.currentSquare = 0;
	   this.image = null;
   }
   
   
   /**
    * Gets the player id.
    *
    * @return the player id
    */
   public int getPlayerId() {
	   return playerId;
   }
   
   
   /**
    * Gets the player name.
    *
    * @return the player name
    */
   public String getPlayerName() {
	   return playerName;
   }
   
   
   /**
    * Gets the normalized player name.
    *
    * @return the normalized player name
    */
   public String getNormalizedPlayerName() {
	   return normalizedPlayerName;
   }
   
   
   /**
    * Normalize player name.
    *
    * @param playerName the player name
    * @return the string
    */
   private String normalizePlayerName(String playerName) {
	   
	   StringBuffer tempBuff = new StringBuffer("");
	   
	   //We should never have null or blank names, but doesn't hurt check anyway...
	   if ((playerName != null) && (playerName.length() > 0)) {
	   	
		   //Pad the playerName to MAX_PLAYER_NAMESIZE
		   if (playerName.length() <= MAX_PLAYER_NAMESIZE) {
		       int ptr = 0;
		       int padding = (MAX_PLAYER_NAMESIZE - playerName.length());
		       while (ptr++ < padding) {
		          tempBuff.append(" ");
		       }
		       tempBuff.append(playerName);
		   } else {
			   //truncate the playerName to MAX_PLAYER_NAMESIZE
			   tempBuff.append(playerName.substring(0, MAX_PLAYER_NAMESIZE));
		   }
	   }
	   
	   return tempBuff.toString();
   }
   
   
   /**
    * Gets the current square.
    *
    * @return the current square
    */
   public int getCurrentSquare() {
	   return currentSquare;
   }
   
   
   /**
    * Sets the current square.
    *
    * @param newValue the new current square
    */
   public void setCurrentSquare(int newValue) {
	   this.currentSquare = newValue;
   }
   
   
   /**
    * Sets the image.
    *
    * @param fileNm the new image
    * @throws IOException Signals that an I/O exception has occurred.
    */
   public void setImage(String fileNm) throws IOException {
	   try
	    {
		  filename = fileNm;
	      //read in the image file
	      image = ImageIO.read(new File(filename));
	    } 
	    catch (IOException e)
	    {
	      // log the exception
	      // re-throw if desired
	    }
   }
   
   
   /**
    * Sets the image.
    *
    * @param fileNm the new image
    * @throws IOException Signals that an I/O exception has occurred.
    */
   public BufferedImage getImage(String fileNm) throws IOException {
       return image;
   }
   
}
