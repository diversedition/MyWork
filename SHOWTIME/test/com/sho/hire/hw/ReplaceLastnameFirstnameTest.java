package com.sho.hire.hw;

import junit.framework.TestCase;

public class ReplaceLastnameFirstnameTest extends TestCase {

	protected void setUp() throws Exception {
		super.setUp();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}

	public void testGetCharArrayIsNull() {
		
		String inputString = null;
		char[] chArray = ReplaceLastnameFirstname.getCharArray(inputString);
		assertNull(chArray);
		
	}
	
	public void testGetCharArrayIsValid() {
		
		String inputString = "Hello";
		char[] chArray = ReplaceLastnameFirstname.getCharArray(inputString);
		
		char[] tempArray = { 'H', 'e', 'l', 'l', 'o' };
		assert(new String(chArray) == new String(tempArray));
		
	}

	public void testReverseChars() {
		char[] tempArray = { 'H', 'e', 'l', 'l', 'o' };
		ReplaceLastnameFirstname.reverseChars(tempArray);
		String s = new String(tempArray);
		
		assert(s == "Hello");
	}

	public void testReplaceNeedle() {
        String haystack = "Hello World";
        String needle = "World";
        String replacement = "Everyone";
        
		String s = ReplaceLastnameFirstname.replaceNeedle(haystack, needle, replacement);
		
		assert(s == "Hello Everyone");
	}

	public void testReverseWords() {
		char[] tempArray = { 'd', 'l', 'r', 'o', 'W', ' ', 'o', 'l', 'l', 'e', 'H' };
		ReplaceLastnameFirstname.reverseWords(tempArray);
		String s = new String(tempArray);
		
		assert(s == "World Hello");
	}

	public void testEcalpeResrever() {

        String haystack = "Hello World";
        String needle = "World";
        String replacement = "Everyone";
        
		String s = ReplaceLastnameFirstname.ecalpeResrever(haystack, needle, replacement);
		
		assert(s == "Everyone Hello");
	}

}
