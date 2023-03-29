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
         	AVLTree tree = new AVLTree();
            int[] list ={72, 10, 25, 63, 91, 4, 85, 50, 13, 44, 57, 88, 97, 39, 78, 30, 21, 48, 65, 99, 60, 76, 33, 55, 82};
	        for(int i=0;i<list.length;i++){
		        tree.add(list[i]);
	         }
    }
    public void rotateLeft(Node node){

    }
    public void rotateRight(Node node){
        
    }
    //calculate balance factor
    public int calculateNode(Node node){
        return 0;
    }
    public boolean add(int item){
        if (root == null) {
            root = new Node(item);
            return true;
        } else {
            // If the root is not null, call the addHelper method to recursively add the element to the tree
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
    
    public int height(Node node){
    	if(node == null) {
    		return 0;
    	}
    	
        return node.height;
    }
    public void print(AVLTree tree){
        
    }
}
