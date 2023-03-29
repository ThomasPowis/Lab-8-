public class AVLTree {
    Node root;

    private class Node{
        int val;
        int balanceFactor;
        Node left;
        Node right;
        private Node(int n){
            val=n;
        }
    }        
}
