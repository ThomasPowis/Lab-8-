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

	public static void main(String[] args) {
		testing();
	}
	//Testing method
	public static void testing() {
		AVLTree right = new AVLTree();
		AVLTree left = new AVLTree();
		AVLTree leftright = new AVLTree();
		AVLTree rightleft = new AVLTree();
		int[] leftList = {50,40,60,30,70,80};
		int[] rightList = {20,10,25,5,15,2};
		int[] leftRightList = {30,10,35,5,20,15,25};
		int[] rightLeftList = {2,1,0};
		for(int i = 0; i < rightList.length; i++) {
			right.add(rightList[i]);
		}
		for(int i = 0; i < leftList.length; i++) {
			left.add(leftList[i]);
		}
		for(int i = 0; i < leftRightList.length; i++) {
			leftright.add(leftRightList[i]);
		}
		for(int i = 0; i < rightLeftList.length; i++) {
			rightleft.add(rightLeftList[i]);
		}
		System.out.println("Right Rotation:");
		right.printLevels();
		System.out.println();
		System.out.println();
		System.out.println("Left Rotation:");
		left.printLevels();
		System.out.println();
		System.out.println("Left Right Rotation:");
		leftright.printLevels();
		System.out.println();
		System.out.println("Right Left Rotation:");
		rightleft.printLevels();
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
				node.left.parent = node;
				return true;
			} else {
				node.left.height++;
				boolean add = add(node.left,item);
				if(add) {
					balanceTree(node.left);
				}
				return add;
			}
		}
		else if (item>node.val) {
			if (node.right == null) {
				node.right = new Node(item);
				node.right.parent = node;
				return true;
			} else {
				node.right.height++;
				boolean add = add(node.right,item);
				if(add) {
					balanceTree(node.right);
				}
				return add;
			}
		}
		else {
			return false;
		}
	}

	//Balance Factor method
	//Built with the help of chatGPT
	private void balanceTree(Node node) {
		if (node == null) {
			return;
		}
		if (node.balanceFactor < -1) { // right subtree is deeper
			if (node.right.balanceFactor > 0) { // right-left case
				rotateRight(node.right);
			}
			rotateLeft(node); // right-right or right-left case
		} else if (node.balanceFactor > 1) { // left subtree is deeper
			if (node.left.balanceFactor < 0) { // left-right case
				rotateLeft(node.left);
			}
			rotateRight(node); // left-left or left-right case
		}
		balanceTree(node.parent); // continue balancing up the tree
	}

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

	public void printLevels() {
		if (root == null) {
			return;
		}
		Queue<Node> queue = new LinkedList<>();
		queue.offer(root);
		int currentLevel = 1;
		int nextLevel = 0;
		while (!queue.isEmpty()) {
			Node node = queue.poll();
			currentLevel--;
			if (node == null) {
				System.out.print("- ");
				continue;
			}
			System.out.print(node.val + " ");
			if (node.left != null || node.right != null) {
				queue.offer(node.left);
				queue.offer(node.right);
				nextLevel += 2;
			}
			if (currentLevel == 0) {
				System.out.println();
				currentLevel = nextLevel;
				nextLevel = 0;
			}
		}
	}
}
