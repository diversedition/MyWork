package proectFour.treeTest;

//import java.util.TreeMap;

public class TreeTest {
	
	//TreeMap tree = null;
	private static Node root = null;
	
	boolean isTopNode = true;
	
	public TreeTest() {
		//tree = new TreeMap();
		root = initializeTree();
	}
	
	Node getRoot() {
		return root;
	}
	
	/**
	 * 
	 * @param tree
	 *      
	 *       2
	 *     /    \ 
	 *    7      5  
	 *   / \      \  
	 *  2   6      9
	 *     / \     /
	 *    5  11   4
	 */
	Node initializeTree() {
		Node n5 = new Node(5);
		Node n11 = new Node(11);
		Node n4 = new Node(4);
		Node n2 = new Node(2);
		Node n6 = new Node(6);
		Node n9 = new Node(9);
		Node n7 = new Node(7);
		Node n5b = new Node(5);
		Node n2b = new Node(2);
		
		n6.setLeftNode(n5b);
		n6.setRightNode(n11);
		
		n9.setLeftNode(n4);
		
		n7.setLeftNode(n2b);
		n7.setRightNode(n6);
		
		n5.setRightNode(n9);
		
		n2.setLeftNode(n7);
		n2.setRightNode(n5);
		
		return n2;
	}

	public static void main(String[] args) {
        runIt();
	}
	
	static void runIt() {
		System.out.println("Running the TreeTest app...");
		
		TreeTest tt = new TreeTest();
		Node root = tt.getRoot();
		tt.preOrder(root);
		System.out.println("");
		
		System.out.println("Done running TreeTest app...");
	}
	
	void preOrder(Node root) {
		
		if (root == null) {
			return;
		}
		
		if (isTopNode == false) {
		   System.out.print(", ");
		} else {
		   isTopNode = false;
		}
		System.out.print(root.getData());
				
		preOrder(root.leftNode());
		preOrder(root.rightNode());				
	}

}
