package com.company;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;


/**
 * The binary tree data structure
 * @author Masyukevich Nikita
 * @version 1.0
 * @see <a href="https://en.wikipedia.org/wiki/Binary_tree">See more about this data structure</a>
 */
class BinaryTree {
    /**
     * Inner class node for storing elements of tree
     */
    protected class Node {

        /**
         * The value of the node
         */
        int value;

        /**
         * The reference to the left node
         */
        Node left;

        /**
         * The reference to the right node
         */
        Node right;


        /**
         * The constructor for Node class
         * @param value the value of the node
         */
        Node(int value) {
            this.value = value;
            right = null;
            left = null;
        }

        /**
         * Used to call print method with parameters
         */
        void print() {
            if (this == null) {
                System.out.println("The tree is empty");
                return;
            }
            print("", this, false, true);
        }

        /**
         * Used to print the tree or any subtree, this method is called by renderTree method
         * @param prefix the prefix to be printed
         * @param n the root node
         * @param isLeft specify if the node is the left sub-tree
         * @param isRoot specify if the node is the tree's root
         */
        void print(String prefix, Node n, boolean isLeft, boolean isRoot) {
            if (n != null) {
                if (isRoot){
                    System.out.println("root:" + n.value + " _");
                } else
                    System.out.println (prefix + "  ╰–– " + (isLeft ? "l:": "r:") + n.value);
                print(prefix + (isLeft ? "  |   ": "      "), n.left, true, false);
                print(prefix + (isLeft ? "  |   ": "      "), n.right, false, false);
            }
        }

    }
    /**
     * Constructor to initialize the tree with an array
     * @param values the values to initialize the tree with
     */
     public BinaryTree(int[] values){
        for (int value: values){
            pushNode(value);
        }
     }

     /**
      * Constructor to initialize the tree with the values
      * @param fillWith the value to initialize the tree with
      * @param size specify haw many nodes to initialize with the value (the size of the tree)
      */
     public BinaryTree(int fillWith, int size){
        for (int i = 0; i < size; i++){
            pushNode(fillWith);
        }
     }

    /**
     * A default constructor
     */
    public BinaryTree(){
    }

    /**
     * The root node of the tree
     */
    protected Node root;

    /**
     * The size of the tree
     */
    protected int size = 0;

    /**
     * Used to disconnect a specified node from the tree if present
     * @param nodeToRemove the node to disconnect from the tree
     */
    protected void disconnectNode(Node nodeToRemove){
        if (root == null) {
            return;
        }

        // iterate through the tree using BFS
        Queue<Node> nodes = new LinkedList<>();
        nodes.add(root);

        while (!nodes.isEmpty()) {

            Node node = nodes.remove();

            // when the specified node if found, delete the
            // reference of it
            if (node.left != null) {
                if (node.left == nodeToRemove){
                    node.left = null;
                    size -- ;
                    return;
                }
                nodes.add(node.left);
            }

            if (node.right != null) {
                if (node.right == nodeToRemove){
                    node.right = null;
                    size -- ;
                    return;
                }
                nodes.add(node.right);
            }
        }
    }

    /**
     * Get the deepest right-most leaf node in the tree
     * @param root the root node
     * @return the found node
     */
    protected Node getDeepestRightLeafNode(Node root) {
        if (root == null)
            return null;

        // create a queue for level order traversal
        Queue<Node> q = new LinkedList<>();
        q.add(root);

        // the resulting node
        Node result = root;

        // traverse until the queue is empty
        while (!q.isEmpty()) {
            int size = q.size();
            for (int i = 0; i < size; i++) {
                Node node = q.remove();

                if (node.left != null) {
                    q.add(node.left);
                }

                // since we go level by level, the last
                // stored right leaf node is the deepest one
                if (node.right != null) {
                    q.add(node.right);
                }
                if (i == size - 1) {
                    result = node;
                }
            }
        }
        return result;
    }

    /**
     * Used to print the full binary tree
     */
    public void renderTree(){
        root.print();
    }

    /**
     * Used to print items of the tree in level order
     */
    public void printLevelOrder() {
        if (root == null) {
            return;
        }

        // traverse the tree with breadth first search,
        // utilizing the queue
        Queue<Node> nodes = new LinkedList<>();
        nodes.add(root);

        // define the first level
        int level = 1;

        // while there are nodes in queue
        while (!nodes.isEmpty()) {

            System.out.println("level: " + level);

            // get the number of nodes in the queue
            int size = nodes.size();

            // iterate through the nodes in queue
            for (int i = 0; i < size; i++){

                // take away the node
                Node current = nodes.remove();

                // if the node has children, add then to the queue
                if (current.left != null) {
                    nodes.add(current.left);
                }

                if (current.right != null) {
                    nodes.add(current.right);
                }

                // print the node's value
                System.out.print(" " + current.value);
            }
            System.out.println();

            // increment the level counter
            level ++;
        }
    }

    /**
     * Used to properly create new nodes
     * @param value the value of a new node
     * @return the created node
     */
    protected Node createNode(int value){
        Node node = new Node(value);

        // increment the size of the tree
        size ++;
        return node;
    }

    /**
     * Used to finding the max depth of the tree
     * @param node the root node
     * @return number - the max depth of the tree
     */
    protected int maxDepth(Node node) {
        if (node == null)
            return -1;
        else {
            // compute the depth of each subtree
            int lDepth = maxDepth(node.left);
            int rDepth = maxDepth(node.right);

            // use the larger one
            if (lDepth > rDepth)
                return (lDepth + 1);
            else
                return (rDepth + 1);
        }
    }

    /**
     * Public method for finding the tree height
     * @return number - the height of the tree
     */
    public int treeHeight(){
        return maxDepth(root) + 1;
    }

    /**
     * Insert the node on a specified level to the specified inter-level position
     * @param value the value of the node
     * @param level the level in the tree, where node should be inserted
     * @param interLevelIndex the index inside the level, where node should be inserted
     */
    public void insertNode(int value, int level, int interLevelIndex){

        // find the tree height
        int treeHeight = treeHeight();

        // the case when attempting to add the node to the root of the
        // empty tree
        if (treeHeight == 0 && level == 1 && interLevelIndex == 1){
            pushNode(value);
            return;
        }

        // if the specified level is less than 1 or
        // is greater than the total number of level + 1, then
        // the node can't be inserted
        if (level > treeHeight+1 || level <= 0){
            System.out.println("cannot insert, level is out of range");
            return;
        }

        // if the inter-level position is less than 1 or
        // greater than the max possible position on the specified
        // level, the node can't be inserted
        if (interLevelIndex > Math.pow(2, level-1) ||
                interLevelIndex <=0){
            System.out.println("can't insert, index is out of range");
            return;
        }

        // if the specified level is 1 level bellow the last one,
        // and the inner-level position is not 1, then the node can't
        // be added because it would make the tree incomplete
        if (level == treeHeight+1 && interLevelIndex > 1){
            System.out.println("can't insert due to completeness constraint");
            return;
        }

        // traverse the tree using breadth first search utilizing
        // the queue
        Queue<Node> nodes = new LinkedList<>();

        // add the root node to the queue
        nodes.add(root);

        // set the current level counter as 1
        int currentLevel = 1;

        // while the current level is less than specified level
        while (currentLevel < level) {

            // get the size of the queue
            int size = nodes.size();

            // se the 'number of nodes on the next level' counter as 0
            int numberOfNodesOnTheNextLevel = 0;

            // iterate through the nodes in queue
            for (int i = 0; i < size; i++){

                // take away the node from the queue
                Node current = nodes.remove();

                // if the current node has children add them
                // to the queue and increment the counter of those
                if (current.left != null) {
                    nodes.add(current.left);
                    numberOfNodesOnTheNextLevel ++;
                }
                if (current.right != null) {
                    nodes.add(current.right);
                    numberOfNodesOnTheNextLevel ++;
                }

                // the case when it's specified to add the node
                // just bellow the last level
                if (currentLevel == treeHeight){
                    current.left = createNode(value);
                    return;
                }

                // the case when it's specified to add the node
                // just bellow the current level
                else if (level - currentLevel == 1) {

                    // if the specified inter-level position is less or
                    // equals than the number of child notes on the next level,
                    // it means that the specified position is already taken,
                    // and the node cannot be inserted
                    if (interLevelIndex <= numberOfNodesOnTheNextLevel){
                        System.out.println("cannot insert, specified position is taken");
                        return;
                    }

                    // if the specified position is just above the number of children
                    // on the next level, add the node to the current node:
                    // to the left if empty
                    // to the right if empty
                    // or if both children are present, go to the next node on the
                    // current level
                    if (interLevelIndex - numberOfNodesOnTheNextLevel == 1){
                        if (current.left == null){
                            current.left = createNode(value);

                        } else if (current.right == null){
                            current.right = createNode(value);

                        } else {
                            continue;
                        }
                        return;
                    }
                }
            }

            // if the case is to add a node to the next level from the
            // current one, and the index is more than 1 further
            // than number of nodes on that level, cannot insert,
            // as it would violate the completeness constraint
            if ((level - currentLevel == 1) &&(interLevelIndex - numberOfNodesOnTheNextLevel > 1)){
                System.out.println("cannot insert due to completeness constraint");
                return;
            }

            // increment the level counter
            currentLevel ++;
        }

        // in other cases, if the node have not been inserted,
        // give a message
        System.out.println("couldn't insert");
        return;
    }

    /**
     * Add the node to the end of the tree, retaining the completeness
     * @param value the value to be added
     */
    public void pushNode(int value) {
        // if the tree is empty create a new node
        if (root == null) {
            root = createNode(value);
            return;
        }

        // traverse the tree using breadth first search
        // utilizing the queue
        Queue<Node> nodes = new LinkedList<>();
        // add the root node to the queue
        nodes.add(root);

        // the inter-tree index
        int i = 1;

        // the calculated position to add
        int position = (int) (size + 1) / 2;

        // while there are nodes in the queue
        while (!nodes.isEmpty()) {

            // take away the node from the queue
            Node node = nodes.remove();

            // if the current node has the position of calculated,
            // add the node
            if (i == position){
                if (node.left == null){
                    node.left = createNode(value);
                } else if (node.right == null){
                    node.right = createNode(value);
                }
                else System.out.println("the tree is incomplete, cannot create node");
                return;
            }

            // add the child notes to queue
            if (node.left != null) {
                nodes.add(node.left);
            }

            if (node.right != null) {
                nodes.add(node.right);
            }

            // increment the inter-tree index
            i++;
        }
    }

    /**
     * Remove the node by value
     * @param value the value by which to search and delete the node
     */
    public void remove(int value) {
        if (root == null) {
            System.out.println("cannot remove, the tree is empty");
            return;
        }

        // if the root node need to be removed, find the right-most
        // the deepest node, pop it and replace the root node with it
        if (root.value == value){
            Node toReplace = getDeepestRightLeafNode(root);
            disconnectNode(toReplace);
            toReplace.left = root.left;
            toReplace.right = root.right;
            root = toReplace;
            return;
        }

        // traverse the tree using breadth first search
        // utilizing the queue
        Queue<Node> nodes = new LinkedList<>();
        // add the root node to the queue
        nodes.add(root);

        // while there are nodes in the queue
        while (!nodes.isEmpty()) {

            // take away the node from the queue
            Node node = nodes.remove();

            // if the left child node is the one to be removed,
            // consider 3 cases:
            if (node.left.value == value) {
                // 1: it has no children, then remove the reference of it from
                // the parent node
                if (node.left.left== null && node.left.right == null){
                    node.left = null;
                    size --;
                // 2: it has 1 children,  then replace the node with the child
                // node
                } else if (node.left.left == null && node.left.right != null){
                    node.left = node.left.right;
                    size --;
                } else if (node.left.right == null && node.left.left != null){
                    node.left = node.left.left;
                    size --;
                // 3: it has 2 children, then find the deepest right-most node,
                // pop it and replace the node to be deleted with it
                } else {
                    Node toReplace = getDeepestRightLeafNode(node.left);
                    disconnectNode(toReplace);
                    toReplace.left = node.left.left;
                    toReplace.right = node.left.right;
                    node.left = toReplace;
                }
                return;
            }
            if (node.right.value == value) {
                if (node.right.left== null && node.right.right == null){
                    node.right = null;
                    size --;
                } else if (node.right.left == null && node.right.right != null){
                    node.right = node.right.right;
                    size --;
                } else if (node.right.right == null && node.right.left != null){
                    node.right = node.right.left;
                    size --;
                } else {
                    Node toReplace = getDeepestRightLeafNode(node.right);
                    disconnectNode(toReplace);
                    toReplace.left = node.right.left;
                    toReplace.right = node.right.right;
                    node.right = toReplace;
                }
                return;
            }

            // add the child notes to queue
            if (node.left != null) {
                nodes.add(node.left);
            }

            if (node.right != null) {
                nodes.add(node.right);
            }
        }

    }

    /**
     * Check if the tree is complete
     * @param root the root node
     * @param index the index of the node
     * @param numberNodes the size of the tree
     * @return the boolean value indicating whether the tree is complete
     */
    private boolean checkComplete(Node root, int index, int numberNodes) {
        // Check if the tree is empty
        if (root == null)
            return true;

        // if the index of the node if greater than the number of nodes in the
        // tree then this tree is incomplete
        if (index >= numberNodes)
            return false;

        // check for the child nodes
        return (checkComplete(root.left, 2 * index + 1, numberNodes)
                && checkComplete(root.right, 2 * index + 2, numberNodes));
    }

    /**
     * Public method for checking completeness
     * @return the boolean value indicating whether the tree is complete
     */
    public boolean isCompleteTree(){
        return checkComplete(root, 0, size);
    }

    /**
     * Get the size of the tree or any sub-tree
     * @param node the root node
     * @return the number of nodes in the tree
     */
    protected int getSize(Node node) {
        if (node == null) return(0);
        else {
            return(getSize(node.left) + 1 + getSize(node.right));
        }
    }

    /**
     * Public method for getting size of the full tree
     * @return the number of nodes in the tree
     */
    public int size(){
        return getSize(root);
    }

    /**
     * Get the node values of the tree
     * @return the int array of all values
     */
    protected int[] getValuesOfTheTree(){
        if (root == null) {
            return new int[]{};
        }

        // traverse through the tree
        Queue<Node> nodes = new LinkedList<>();
        nodes.add(root);

        // collect the values of the tree level by level
        int[] values= new int [this.size];
        int i = 0;
        while (!nodes.isEmpty()) {

            Node node = nodes.remove();
            values[i] =  (node.value);

            if (node.left != null) {
                nodes.add(node.left);
            }

            if (node.right != null) {
                nodes.add(node.right);
            }
            i++;
        }
        return values;
    }

    /**
     * Restores the state of being a complete tree
     */
    protected void restoreCompleteness(){

        if (root == null) {
            return;
        }

        int[] values = getValuesOfTheTree();

        // replace the old tree with a new one, where each node is
        // inserted properly
        BinaryTree restored = new BinaryTree(values);
        this.root = restored.root;
    }

    /**
     * Public method to restore the tree if it's incomplete
     */
    public void restore(){
        if (!this.isCompleteTree()){
            this.restoreCompleteness();
        }
    }
}

/**
 * The heap data structure
 * @author Masyukevych Nikita
 * @version 1.0
 * @see <a href="https://en.wikipedia.org/wiki/Heap_(data_structure)">See more about this data structure</a>
 */
class Heap extends BinaryTree{
    /**
     * Constructor to initialize the heap with values
     * @param values values to add to the heap
     */
    public Heap(int[] values){
        Arrays.sort(values);
        for (int value: values){
            this.pushNode(value);
        }
    }

    // initialize the tree with the values

    /**
     * Constructor to initialize the tree with the same values
     * @param fillWith the value to add a number of times
     * @param size the number of times to add the value
     */
    public Heap(int fillWith, int size){
        for (int i = 0; i < size; i++){
            pushNode(fillWith);
        }
    }

    /**
     * Default constructor
     */
    public Heap (){
    }

    /**
     * Restore the heap
     */
    public void restore() {

        if (root == null) {
            return;
        }

        int[] values = getValuesOfTheTree();

        // replace the old tree with a new one, where each node is
        // inserted properly
        Heap restored = new Heap(values);
        this.root = restored.root;
    }
}

class Main{

    public static void main(String[] args) {

    }
}
