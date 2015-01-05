package projectThree;

import static java.lang.System.out;

public class MyTest {

	public static void main(String[] args) {
		
        out.println("start test");
        
        String input = "This is a test sentence.";
        reverseWords(input);
        
        out.println("\n");
        reverseWords2(input);
        
        out.println("\n");
        Deck deck = new Deck();
        
        for (int i = 0; i < 52; i++) {
           Deck.Card card1 = deck.deal();       
           out.println("Draw a Card: card = " + card1.toString());
        }
        
        Deck.shuffle();
        Deck.Card card2 = deck.deal();       
        out.println("Draw a Card: card = " + card2.toString());
        
        out.println("end test, good-bye...");        
	}
	
	private static void reverseWords(String in) {
		out.println("initial:" + in);
		StringBuilder strBuff = new StringBuilder(in);
		String reversed = strBuff.reverse().toString();
		out.println("reversed:" + reversed);			
	}
	
	private static void reverseWords2(String in) {
		out.println("initial:" + in);
		String[] strArray = in.split("\\s");
		
		String temp = "";
		for (int i = strArray.length-1; i >= 0; i--) {
			temp += strArray[i] + " ";
		}
		
		out.println("reversed:" + temp);			
	}

}
 