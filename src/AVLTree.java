/**
 *
 * AVLTree
 *
 * An implementation of aמ AVL Tree with
 * distinct integer keys and info.
 *
 */

public class AVLTree {

    public IAVLNode root = null;
    public IAVLNode ExLeaf = new CreateExternalLeaf();
    private IAVLNode min;
    private IAVLNode max;



    /**
     * public boolean empty()
     *
     * Returns true if and only if the tree is empty.
     *
     */
    public boolean empty() {
        return !root.isRealNode(); // to be replaced by student code
    }

    /**
     * public String search(int k)
     *
     * Returns the info of an item with key k if it exists in the tree.
     * otherwise, returns null.
     */
    public String search(int k)
    {
        IAVLNode node = root;
        while (node.getKey() != -1) {     // until we reach an external node // while(node.isRealNode())
            if (k == node.getKey()) return node.getValue();   //we found k
            else if (k < node.getKey()) node = node.getLeft();   //continue search in left tree.
            else node = node.getRight();
        }
        return null;  // k not found
    }


    /**
     * public int insert(int k, String i)
     *
     * Inserts an item with key k and info i to the AVL tree.
     * The tree must remain valid, i.e. keep its invariants.
     * Returns the number of re-balancing operations, or 0 if no re-balancing operations were necessary.
     * A promotion/rotation counts as one re-balance operation, double-rotation is counted as 2.
     * Returns -1 if an item with key k already exists in the tree.
     */
    public int insert(int k, String i) {
        return 0;
    }

    /**
     * public int delete(int k)
     *
     * Deletes an item with key k from the binary tree, if it is there.
     * The tree must remain valid, i.e. keep its invariants.
     * Returns the number of re-balancing operations, or 0 if no re-balancing operations were necessary.
     * A promotion/rotation counts as one re-balance operation, double-rotation is counted as 2.
     * Returns -1 if an item with key k was not found in the tree.
     */
    public int delete(int k)
    {
        return 421;	// to be replaced by student code
    }

    /**
     * public String min()
     *
     * Returns the info of the item with the smallest key in the tree,
     * or null if the tree is empty.
     */
    public String min()
    {
        if (this.empty()) return null;
        else return min.getValue();
    }

    /**
     * public String max()
     *
     * Returns the info of the item with the largest key in the tree,
     * or null if the tree is empty.
     */
    public String max()
    {
        if (this.empty()) return null;
        else return max.getValue();
    }

    /**
     * public int[] keysToArray()
     *
     * Returns a sorted array which contains all keys in the tree,
     * or an empty array if the tree is empty.
     */
    public int[] keysToArray(){
        IAVLNode node = root;
        int[] array = new int[node.getSize()];
            if (!node.isRealNode()) return array;  //size should be 0 because the tree is empty.
            else keysToArray_rec(node, array, 0);
            return array;
}

    private void keysToArray_rec(IAVLNode node, int[] array, int index) {
        if (node.isRealNode()) {
            keysToArray_rec(node.getLeft(), array, index);
            array[index] = node.getKey();
            index += 1;
            keysToArray_rec(node.getRight(), array, index);
        }
    }

    /**
     * public String[] infoToArray()
     *
     * Returns an array which contains all info in the tree,
     * sorted by their respective keys,
     * or an empty array if the tree is empty.
     */
    public String[] infoToArray()
    {
        IAVLNode node = root;
        String[] array = new String[node.getSize()];
        if (!node.isRealNode()) return array;  //size should be 0 because the tree is empty.
        else infoToArray_rec(node, array, 0);
        return array;
    }

    private void infoToArray_rec(IAVLNode node, String[] array, int index) {
        if (node.isRealNode()) {
            infoToArray_rec(node.getLeft(), array, index);
            array[index] = node.getValue();
            index += 1;
            infoToArray_rec(node.getRight(), array, index);
        }
    }

    /**
     * public int size()
     *
     * Returns the number of nodes in the tree.
     */
    public int size()
    {
        return root.getSize();
    }

    /**
     * public int getRoot()
     *
     * Returns the root AVL node, or null if the tree is empty
     */
    public IAVLNode getRoot()
    {
        if (root.isRealNode()) return root;
        else return null;
    }

    /**
     * public AVLTree[] split(int x)
     *
     * splits the tree into 2 trees according to the key x.
     * Returns an array [t1, t2] with two AVL trees. keys(t1) < x < keys(t2).
     *
     * precondition: search(x) != null (i.e. you can also assume that the tree is not empty)
     * postcondition: none
     */
    public AVLTree[] split(int x)
    {
        return null;
    }

    /**
     * public int join(IAVLNode x, AVLTree t)
     *
     * joins t and x with the tree.
     * Returns the complexity of the operation (|tree.rank - t.rank| + 1).
     *
     * precondition: keys(t) < x < keys() or keys(t) > x > keys(). t/tree might be empty (rank = -1).
     * postcondition: none
     */
    public int join(IAVLNode x, AVLTree t)
    {
        return -1;
    }

    /**
     * public interface IAVLNode
     * ! Do not delete or modify this - otherwise all tests will fail !
     */
    public interface IAVLNode{
        public int getKey(); // Returns node's key (for virtual node return -1).
        public String getValue(); // Returns node's value [info], for virtual node returns null.
        public void setLeft(IAVLNode node); // Sets left child.
        public IAVLNode getLeft(); // Returns left child, if there is no left child returns null.
        public void setRight(IAVLNode node); // Sets right child.
        public IAVLNode getRight(); // Returns right child, if there is no right child return null.
        public void setParent(IAVLNode node); // Sets parent.
        public IAVLNode getParent(); // Returns the parent, if there is no parent return null.
        public boolean isRealNode(); // Returns True if this is a non-virtual AVL node.
        public void setHeight(int height); // Sets the height of the node.
        public int getHeight(); // Returns the height of the node (-1 for virtual nodes).
        public int getSize(); //return the size of this tree.
    }

    /**
     * public class AVLNode
     *
     * If you wish to implement classes other than AVLTree
     * (for example AVLNode), do it in this file, not in another file.
     *
     * This class can and MUST be modified (It must implement IAVLNode).
     */


    private class AVLNode implements IAVLNode{

        int key;
        int height = 0;
        int size = 0;
        String info;
        private IAVLNode left = null;
        private IAVLNode right = null;
        private IAVLNode parent = null;

        public AVLNode(int key,String value){
            this.key = key;
            this.info = value;
            this.left = ExLeaf;  //key = -1 , info = null, height = -1, size = 0;
            this.right = ExLeaf;
        }




        public int getKey()
        {
            return this.key; // to be replaced by student code
        }
        public String getValue()
        {
            return this.info; // to be replaced by student code
        }
        public void setLeft(IAVLNode node)
        {
            this.left = node; // to be replaced by student code
        }
        public IAVLNode getLeft()
        {
            return this.left; // to be replaced by student code
        }
        public void setRight(IAVLNode node)
        {
            this.right = node; // to be replaced by student code
        }
        public IAVLNode getRight()
        {
            return this.right; // to be replaced by student code
        }
        public void setParent(IAVLNode node)
        {
            this.parent = node; // to be replaced by student code
        }
        public IAVLNode getParent()
        {
            return this.parent; // to be replaced by student code
        }
        public boolean isRealNode()
        {
            return this.height != -1; // to be replaced by student code
        }
        public void setHeight(int height)
        {
            this.height = height; // to be replaced by student code
        }
        public int getHeight()
        {
            return this.height; // to be replaced by student code
        }
        public int getSize() {return 1 + this.getRight().getSize() + getLeft().getSize();}
    }

    public class CreateExternalLeaf extends AVLNode{
        CreateExternalLeaf() {
            super(-1, null);
            this.height = -1;
            this.size = 0;
        }
    }

}

