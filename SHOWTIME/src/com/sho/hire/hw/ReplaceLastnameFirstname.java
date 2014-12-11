package com.sho.hire.hw;

import java.util.regex.Matcher; 
import java.util.regex.Pattern; 

/**
 * 
 *	ReplaceLastnameFirstname.java 
 *	  
 *	Please create the containing class in the following package: com.sho.hire.hw, and please name the containing class: 
 *	ReplaceLastnameFirstname and give it a default no-argument constructor.  
 *
 *	Please write a reversed string replacement method (public String ecalpeResrever( ... ) 
 *	that takes three String arguments, (haystack, needle, and replacement).   
 *
 *  The method will perform a left-to-right string replacement, followed by reversing the resultant strings *words*. 
 *  i.e. ("I like cats", "cat", "dog") >>>  "dogs like I"   
 *	
 *	The method must compile and run under JDK 1.5. 
 ** The method will be tested for speed and correctness on haystacks up to one megabyte. 
 *	
 **	There is a fast linear solution to this problem. Be aware of edge cases.
 *	 
 **	Please leave all testing code intact so that we may evaluate your testing procedure. If you wish to use JUnit, you can 
 *  create a separate class with 'Test' appended to the end of your primary class's name, and extending TestCase. 
 *
 *	
 **	Example Test Cases (you should come up with more edge cases): 
 **	ecalpeResrever(“ABC”, “A”, “a”).equals(“aBC”); 
 ** ecalpeResrever(“AAA AAB BAA”, “AA”, “a”).equals(“Ba aB aA”); 
 ** ecalpeResrever(“I Work.”, “Work”, “Play”).equals(“Play. I”); 
 ** ecalpeResrever(“Tests are the best!”,”the best!”,”just ok.”).equals(“ok. just are Tests”);
 *
 */

/**
 * 
 * @author Eugene
 *
 */
public class ReplaceLastnameFirstname {
	
	public static boolean DEBUG=false;
	
	/**
	 * main method
	 * @param args
	 */
	public static void main(String args[]) {
		System.out.println("Replace needle and reverse the words of the new String...\n");
		
		runTest("ABC",  "A",  "B",  "BBC"); 
		runTest("ABC", "A", "a", "aBC"); 
		runTest("AAA AAB BAA", "AA", "a", "Ba aB aA"); 
		runTest("I Work.", "Work", "Play", "Play. I"); 
		runTest("Tests are the best!", "the best!", "just ok.", "ok. just are Tests");
		
		runTest("ABC",  "",  "B",  "ABC"); 
		runTest("ABC", "A", "", "ABC"); 
		runTest("ABC",  "",  "",  "ABC"); 
		runTest("", "", "", ""); 
	}
	
	
    /**
     * 	no-args default constructor
     */
	public ReplaceLastnameFirstname() {
		
    }
	
	/**
	 * 
	 * @param input
	 * @param needle
	 * @param replacement
	 * @param expectedOutput
	 */
	private static void runTest(String input, String needle, String replacement, String expectedOutput) {
		System.out.println("");
		System.out.println("input=[" + input + "] needle=[" + needle + "] replacement=[" + replacement + "]");
		System.out.println("ecalpeResrever=[" + ecalpeResrever(input, needle, replacement) + "]");
		System.out.println("expectedOutput=[" + expectedOutput + "]");
	}
	
	/**
	 * 
	 * @param inputString
	 * @return
	 */
	public static char[] getCharArray(String inputString) {
		
		if (inputString != null) {
		   char[] charArray = new char[inputString.length()];
		   charArray = inputString.toCharArray();
		
		   return charArray;
		}
		
		return null;    
	}
	
	/**
	 * 
	 * @param inputCharArray
	 */
	public static void reverseChars(char[] inputCharArray) {
		
		int len = inputCharArray.length;
		
		if (len > 2) {
			int i=0;
			int l=len-1;
			while (i < l) {
				char tempChar = inputCharArray[i];
				inputCharArray[i] = inputCharArray[l];
				inputCharArray[l] = tempChar;
				
				i++;
				l--;
			}
		}
	}	
	
	
	/**
	 * 
	 * @param haystack
	 * @param needle
	 * @param replacement
	 * @return
	 */
	public static String replaceNeedle(String haystack, String needle, String replacement) {		
		
		if ((haystack == null) || (haystack.length() == 0)) {
			 return "";
		}
		
		if ((needle == null) || (needle.length() == 0)) {
			 return haystack;
		}
		
		if ((replacement == null) || (replacement.length() == 0)) {
			 return haystack;
		}
		
		Pattern pattern = Pattern.compile(needle);
		 		 
		Matcher m = pattern.matcher(haystack); 
		String newHaystack = m.replaceAll(replacement);
		  
		return newHaystack;
	}
	
	
	/**
	 * 
	 * @param str
	 * @return
	 */
	public static String reverseWords(char[] inputCharArray) {
		 if (inputCharArray == null) {
			 return null;
		 }
		 
		 String str = new String(inputCharArray);
		
		 if ((str == null) || (str.length() ==0)) {
			 return "";
		 }
		
		 StringBuilder stb=new StringBuilder();
		 StringBuffer strBuff = new StringBuffer();	 
			
		 String arr[] = str.split(" ");
		 
		 for (int i=0; i < arr.length; i++) {
			 strBuff.setLength(0);
			 
			 String s = strBuff.append(arr[i]).reverse().toString();
			 stb.append(s);
			 
			 if ((i + 1) < arr.length) {
			     stb.append(" ");
			 }
		 }
		 
		 return stb.toString();
	}
	
	
	/**
	 * The method will perform a left-to-right string replacement, followed by reversing the resultant string's 'words'. 
     * i.e. ("I like cats", "cat", "dog") >>>  "dogs like I"  
	 * 
	 * @return
	 */
	public static String ecalpeResrever(String haystack, String needle, String replacement) {
		
		String outputString = null;
		
		//Replace the needle with replacement in haystack, convert to char[]
		String newHaystack = replaceNeedle(haystack, needle, replacement);
		if (DEBUG) { System.out.println("replaced haystack=[" + newHaystack + "]"); }
		char[] haystackArray = getCharArray(newHaystack);
		
		//Go thru the updated char[] and reverse all the chars
		reverseChars(haystackArray);
		if (DEBUG) { 
			if (haystackArray != null) {
				System.out.println("reversed, replaced haystack=[" + new String(haystackArray) + "]"); 
			}
		}
		
		//Go thru the reversed updated char[] and reverse all the 'words'
		outputString = reverseWords(haystackArray);
		if (DEBUG) { System.out.println("reversed words=[" + outputString + "]"); }
		
		return outputString;		
	}

}
