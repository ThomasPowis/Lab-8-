import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;


public class AVLTree {
	Node root;

	private class Node{
		int val;
		int balanceFactor;
		int height;
		Node left;
		Node right;
		Node parent;
		private Node(int n){
			val=n;
		}
	} 
	public static ArrayList<Node> printOrder = new ArrayList<Node>();

	public static void main(String[] args) {
		AVLTree tree = new AVLTree();
		int[] list ={72, 10, 25, 63, 91, 4, 85, 50, 13, 44, 57, 88, 97, 39, 78, 30, 21, 48, 65, 99, 60, 76, 33, 55, 82};
		for(int i=0;i<list.length;i++){
			tree.add(list[i]);
		}
		//tree.traverseForDebugging(tree.root);
		System.out.println(tree.print());   

		// Print the elements of the ArrayList
		for (Node node : printOrder) {
			System.out.print(node.val + " ");
		}

	}

	//This was built with the help of chatGPT
	public void rotateLeft(Node node){
		// Store the right child of the node as a separate variable
		Node rightChild = node.right;

		// Make the right child's left child the new right child of the node
		node.right = rightChild.left;
		if (rightChild.left != null) {
			rightChild.left.parent = node;
		}

		// Make the node's parent the new parent of its right child
		rightChild.parent = node.parent;
		if (node.parent == null) {
			root = rightChild;
		} else if (node == node.parent.left) {
			node.parent.left = rightChild;
		} else {
			node.parent.right = rightChild;
		}

		// Make the node the new left child of its former right child
		rightChild.left = node;
		node.parent = rightChild;

		// Update the heights and balance factors of the nodes involved in the rotation
		node.height = Math.max(height(node.left), height(node.right)) + 1;
		rightChild.height = Math.max(height(rightChild.left), height(rightChild.right)) + 1;
		node.balanceFactor = calculateNode(node);
		rightChild.balanceFactor = calculateNode(rightChild);
	}

	//This was built with the help of chatGPT
	public void rotateRight(Node node){
		// Store the left child of the node as a separate variable
		Node leftChild = node.left;

		// Make the left child's right child the new left child of the node
		node.left = leftChild.right;
		if (leftChild.right != null) {
			leftChild.right.parent = node;
		}

		// Make the node's parent the new parent of its left child
		leftChild.parent = node.parent;
		if (node.parent == null) {
			root = leftChild;
		} else if (node == node.parent.right) {
			node.parent.right = leftChild;
		} else {
			node.parent.left = leftChild;
		}

		// Make the node the new right child of its former left child
		leftChild.right = node;
		node.parent = leftChild;

		// Update the heights and balance factors of the nodes involved in the rotation
		node.height = Math.max(height(node.left), height(node.right)) + 1;
		leftChild.height = Math.max(height(leftChild.left), height(leftChild.right)) + 1;
		node.balanceFactor = calculateNode(node);
		leftChild.balanceFactor = calculateNode(leftChild);
	}


	//calculate balance factor
	public int calculateNode(Node node){
		if(node == null) {
			return 0;
		}
		else
			return height(node.left) - height(node.right);
	}
	public boolean add(int item){
		if (root == null) {
			root = new Node(item);
			root.height++;
			return true;
		} else {
			// If the root is not null, call the addHelper method to recursively add the element to the tree
			root.height++;
			return add(root,item);
		}
	}
	private boolean add(Node node,int item){
		if (item<node.val) {
			if (node.left == null) {
				node.left = new Node(item);
				return true;
			} else {
				return add(node.left,item);
			}
		} 
		else if (item>node.val) {
			if (node.right == null) {
				node.right = new Node(item);
				return true;
			} else {
				return add(node.right,item);
			}
		} 
		else {
			return false;
		}
	}
	/* 
    public int height(Node node){
    	if(node == null) {
    		return 0;
    	}

        return node.height;
    }
	 */
	private static int height( Node node){
		if(node==null){
			return -1;
		}
		return Math.max(height(node.left), height(node.right)) + 1;
	}

	public ArrayList<Integer> print() {
		ArrayList<Integer> printOrder = new ArrayList<>();
		List<Node> levelNodes = new ArrayList<>();
		levelNodes.add(root);
		print(printOrder, levelNodes);
		return printOrder;
	}
	private void print(ArrayList<Integer> bfsOrder, List<Node> levelNodes) {
		if (levelNodes.isEmpty()) {
			return;
		}
		List<Node> nextLevelNodes = new ArrayList<>();
		for (Node node : levelNodes) {
			if (node != null) {
				bfsOrder.add(node.val);
				nextLevelNodes.add(node.left);
				nextLevelNodes.add(node.right);
			}
		}
		print(bfsOrder, nextLevelNodes);
	}

	
	public void traverseForDebugging(Node node){

		if (node == null)
			return;

		/* first recur on left child */
		traverseForDebugging(node.left);

		/* then print the data of node */
		System.out.println(calculateNode(node));

		/* now recur on right child */
		traverseForDebugging(node.right);
	}
}
