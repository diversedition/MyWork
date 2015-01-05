package projectThree;

import java.util.Collections;
import java.util.List;    
import java.util.ArrayList;
import java.util.Random;

public class Deck {
	
	static List<Card> cards = new ArrayList<Card>(52);
	int currentCard = 51;
	
	public Deck() {
		initialize(cards);
	    shuffle();		   
	}
	
	private void initialize(List<Card> d) {	
		
		d.clear();
		
		//Initialize the Deck
		for (int s=0; s < 4; s++) {
			for (int v=0; v < 13; v++) {
				d.add(new Card(s, v));
			}
		}
		
		currentCard = 51;
	}
	
	public static void shuffle() {
		Collections.shuffle(cards);
	}
	
	public Card deal() {
		Card c = null;
		
		if (currentCard >= 0) {
			c = cards.get(currentCard);
			currentCard--;
		}
		
		return c;
	}

	class Card {
		
		final int value;
		final int suit;
		
		Card(int s, int v) {
		   suit = s;
		   value = v;
		}
		
		int getValue() { return value; }
		int getSuit() { return suit; }
		
		public String toString() {
			return "card: " + suit + " : " + value;		
		}
	}
}
