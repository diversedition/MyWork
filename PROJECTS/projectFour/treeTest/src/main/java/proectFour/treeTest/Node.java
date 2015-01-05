package proectFour.treeTest;

public class Node {

  private Node leftNode;
  private Node rightNode;
  private int data;
	
  Node(int d) {  
	  data = d;
	  leftNode = null;
	  rightNode = null;
  }

  Node leftNode() { return leftNode; }
  Node rightNode() { return rightNode; }
  int getData() { return data; }
  
  void setLeftNode(Node n) { leftNode = n; }  
  void setRightNode(Node n) { rightNode = n; }  
  void setData(int d) { data = d; }
}
