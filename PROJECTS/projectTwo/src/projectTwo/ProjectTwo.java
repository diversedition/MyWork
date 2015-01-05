package projectTwo;

import static java.lang.System.out;

public class ProjectTwo {
	
	public ProjectTwo() {
		
	}

	static public void main(String[] args) {
		// TODO Auto-generated method stub
		
		String input = "This is a test";
		
		reverseWords(input);
	}
	
	
	static private void reverseWords(String sentence) {
		out.println("initial:" + sentence);
		StringBuilder strBuff = new StringBuilder(sentence);
		String reversed = strBuff.reverse().toString();
		out.println("reversed:" + reversed);
	}

}