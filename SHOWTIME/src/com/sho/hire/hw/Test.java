package com.sho.hire.hw;

import java.util.*; 


//The solution should simply be named ‘solution.txt’ and contain your analysis of the problem. 
//You do not have to write code to fix the problem.


//NOTES:
//Use ArrayList so the Object array can grow dynamically, then we won't really need to check capacity.
//pop() should declare the checked exception it throws

public class Test {     
		private Object[] elements;     
		private int size = 0;
		
		public Test(int initialCapacity) {
			this.elements = new Object[initialCapacity];      
		}
		
		public void push(Object e) {          
			ensureCapacity();          
			elements[size++] = e;      
		}
		
		public Object pop() {          
			if (size==0)            
				throw new EmptyStackException();          
			Object pop = elements[--size];          
			return pop;     			        
        }

		/**       
		 * * Ensure space for at least one more element, roughly       
		 * * doubling the capacity each time the array needs to grow.       
		 * */      
		private void ensureCapacity() {          
			if (elements.length == size) {              
				Object[] oldElements = elements;              
				elements = new Object[2 * elements.length + 1];              
				System.arraycopy(oldElements, 0, elements, 0, size); 
			} 
		}
}
