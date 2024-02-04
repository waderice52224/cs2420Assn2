import java.util.*;

public class Tree<E extends Comparable<? super E>> {
    private BinaryTreeNode root;  // Root of tree
    private String name;     // Name of tree

    /**
     * Create an empty tree
     *
     * @param label Name of tree
     */
    public Tree(String label) {
        name = label;
    }

    /**
     * Create BST from ArrayList
     *
     * @param arr   List of elements to be added
     * @param label Name of tree
     */
    public Tree(ArrayList<E> arr, String label) {
        name = label;
        for (E key : arr) {
            insert(key);
        }
    }

    /**
     * Create BST from Array
     *
     * @param arr   List of elements to be added
     * @param label Name of  tree
     */
    public Tree(E[] arr, String label) {
        name = label;
        for (E key : arr) {
            insert(key);
        }
    }

    /**
     * Return a string containing the tree contents as a tree with one node per line
     */
    public String toString() {
        if (this.root == null){
            return "Empty Tree";
        }
        return this.name + "\n" + makeStringRec(this.root, 0);
    }

    private String makeStringRec(BinaryTreeNode node, int depth){
        String nullParent = "no parent";
        if (node == null){
            return "";
        }
        if (node.parent != null){
            nullParent = node.parent.key + "";
        }
        String whiteSpace = "";
        for (int i = 0; i < depth; i++) {
            whiteSpace += "  ";
        }
        return makeStringRec(node.right, depth + 1) + whiteSpace + node.key + "["+ nullParent + "]\n" + makeStringRec(node.left, depth + 1);
    }

    /**
     * Return a string containing the tree contents as a single line
     */
    public String inOrderToString() {
        return this.name + ": " + findInOrderRec(root);
    }
    private String findInOrderRec(BinaryTreeNode node){
        if(node == null){
            return "";
        }
        return findInOrderRec(node.left) + node.key + " " + findInOrderRec(node.right);
    }

    /**
     * reverse left and right children recursively
     */
    public void flip() {
        flipNodeChildren(root);
    }
    private void flipNodeChildren(BinaryTreeNode node){
        BinaryTreeNode placeHold = node.right;
        node.right = node.left;
        node.left = placeHold;
        if (node.left != null) {
            flipNodeChildren(node.left);
        }
        if (node.right != null) {
            flipNodeChildren(node.right);
        }

    }

    /**
     * Returns the in-order successor of the specified node
     * @param node node from which to find the in-order successor
     */
    public BinaryTreeNode inOrderSuccessor(BinaryTreeNode node) {
        return findNextSuccessorRec(node);

    }
    private BinaryTreeNode whileNodeLessThan(BinaryTreeNode node, E key){
        if (node.key.compareTo(key) > 0){
            return node;
        }else {
            return whileNodeLessThan(node.parent, key);
        }
    }
    private BinaryTreeNode whileNodeLessThanChild(BinaryTreeNode node){
        if(node.left == null){
            return node;
        }else {
            return whileNodeLessThanChild(node.left);
        }
    }

    private BinaryTreeNode findNextSuccessorRec(BinaryTreeNode node){
        if (node == null){
            return null;
        }
        if (node.right != null){
            return whileNodeLessThanChild(node.right);
        }else{

            return whileNodeLessThan(node.parent, node.key);
        }
    }

    /**
     * Counts number of nodes in specified level
     *
     * @param level Level in tree, root is zero
     * @return count of number of nodes at specified level
     */
    public int nodesInLevel(int level) {
        return checkDepth(root, level, 0, 0);
    }
    private int checkDepth(BinaryTreeNode node, int orgLevel, int curLevel, int count){
        int totalCount = 0;
        if(node == null){
            return count;
        }else if (orgLevel == curLevel){
            return totalCount + 1;
        }else {
            totalCount += checkDepth(node.left, orgLevel, curLevel+1, count);
            totalCount += checkDepth(node.right, orgLevel, curLevel+1, count);
        }
        return totalCount;
    }

    /**
     * Print all paths from root to leaves
     */
    public void printAllPaths() {
        printNextRec(root, "");
    }
    private void printNextRec(BinaryTreeNode node, String msg) {
        msg += node.key + " ";
        if (node.left == null && node.right == null){
            System.out.println(msg);
        }else{
            if (node.left !=null){
                printNextRec(node.left, msg);
            }
            if (node.right !=null){
                printNextRec(node.right, msg);
            }

        }
    }
    /**
     * Counts all non-null binary search trees embedded in tree
     *
     * @return Count of embedded binary search trees
     */
    public int countBST() {
        return countBSTRec(root);
    }
    private int countBSTRec(BinaryTreeNode node) {
        if (node == null) {
            return 0;
        }
        int count;
        if (isBST(node, null, null)) {
            count = 1;
        } else {
            count = 0;
        }
        count += countBSTRec(node.left);
        count += countBSTRec(node.right);
        return count;
    }

    private boolean isBST(BinaryTreeNode node, E minValue, E maxValue) {
        if (node == null) {
            return true;
        }
        if ((minValue == null || node.key.compareTo(minValue) > 0) && (maxValue == null || node.key.compareTo(maxValue) < 0)) {
            return isBST(node.left, minValue, node.key) && isBST(node.right, node.key, maxValue);
        } else {
            return false;
        }
    }
    /**
     * Insert into a bst tree; duplicates are allowed
     *
     * @param x the item to insert.
     */
    public void insert(E x) {
        root = insert(x, root, null);
    }

    public BinaryTreeNode getByKey(E key) {
        return checkNode(root, key);

    }
    private BinaryTreeNode checkNode(BinaryTreeNode node, E key) {
        if (node == null) {
            return null;
        } else if (node.key == key) {
            return node;
        } else {
            if (checkNode(node.left, key) == null){
                return checkNode(node.right, key);
            }
            return checkNode(node.left, key);


        }
    }

    /**
     * Balance the tree
     */
    private ArrayList<BinaryTreeNode> findInOrderRecNode(BinaryTreeNode node){
        ArrayList<BinaryTreeNode> finalList = new ArrayList<>();
        if (node == null){

        }
        if (node.left != null){
            finalList.addAll(findInOrderRecNode(node.left));

        }
        finalList.add(node);
        if (node.right != null){
            finalList.addAll(findInOrderRecNode(node.right));
        }
        return finalList;

    }
    public void balanceTree() {
        ArrayList<BinaryTreeNode> sorted = findInOrderRecNode(root);
        root = null;
        inOrderTraversalRec(sorted);
    }
    private void inOrderTraversalRec(ArrayList<BinaryTreeNode> sortedList) {
        if (sortedList.isEmpty()) {
            return;
        }
        int midIndex = sortedList.size() / 2;
        BinaryTreeNode mid = sortedList.get(midIndex);
        insert(mid.key);

        inOrderTraversalRec(new ArrayList<>(sortedList.subList(0, midIndex)));
        inOrderTraversalRec(new ArrayList<>(sortedList.subList(midIndex + 1, sortedList.size())));
    }


    /**
     * Internal method to insert into a subtree.
     * In tree is balanced, this routine runs in O(log n)
     *
     * @param x the item to insert.
     * @param t the node that roots the subtree.
     * @return the new root of the subtree.
     */
    private BinaryTreeNode insert(E x, BinaryTreeNode t, BinaryTreeNode parent) {
        if (t == null) return new BinaryTreeNode(x, null, null, parent);

        int compareResult = x.compareTo(t.key);
        if (compareResult < 0) {
            t.left = insert(x, t.left, t);
        } else {
            t.right = insert(x, t.right, t);
        }

        return t;
    }


    /**
     * Internal method to find an item in a subtree.
     * This routine runs in O(log n) as there is only one recursive call that is executed and the work
     * associated with a single call is independent of the size of the tree: a=1, b=2, k=0
     *
     * @param x is item to search for.
     * @param t the node that roots the subtree.
     *          SIDE EFFECT: Sets local variable curr to be the node that is found
     * @return node containing the matched item.
     */
    private boolean contains(E x, BinaryTreeNode t) {
        if (t == null)
            return false;

        int compareResult = x.compareTo(t.key);

        if (compareResult < 0)
            return contains(x, t.left);
        else if (compareResult > 0)
            return contains(x, t.right);
        else {
            return true;    // Match
        }
    }

    // Basic node stored in unbalanced binary trees
    public class BinaryTreeNode {
        E key;            // The data/key for the node
        BinaryTreeNode left;   // Left child
        BinaryTreeNode right;  // Right child
        BinaryTreeNode parent; //  Parent node

        // Constructors
        BinaryTreeNode(E theElement) {
            this(theElement, null, null, null);
        }

        BinaryTreeNode(E theElement, BinaryTreeNode lt, BinaryTreeNode rt, BinaryTreeNode pt) {
            key = theElement;
            left = lt;
            right = rt;
            parent = pt;
        }

        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("Node:");
            sb.append(key);
            if (parent == null) {
                sb.append("<>");
            } else {
                sb.append("<");
                sb.append(parent.key);
                sb.append(">");
            }

            return sb.toString();
        }
    }
}
