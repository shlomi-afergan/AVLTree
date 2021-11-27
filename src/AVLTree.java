/**
 *
 * AVLTree
 *
 * An implementation of aמ AVL Tree with
 * distinct integer keys and info.
 *
 */

public class AVLTree {

    public IAVLNode ExLeaf = new CreateExternalLeaf();
    public IAVLNode root = ExLeaf;
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
        while (node.isRealNode()) {     // until we reach an external node // while(node.isRealNode())
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
        if (search(k) != null){
            return -1;
        }
        else {
            IAVLNode newNodeParent = getPosition(k);
            IAVLNode newNode = new AVLNode(k, i,newNodeParent);

            if (empty()){
                root = newNode;
                max = newNode;
                min = newNode;
                return 0;
            }
            else {
                if (newNodeParent.getKey() > k){
                    newNodeParent.setLeft(newNode);
                    if (newNodeParent.getKey() == min.getKey()){
                        min = newNode;
                    }
                }
                else {
                    newNodeParent.setRight(newNode);
                    if (newNodeParent.getKey() == max.getKey()){
                        max = newNode;
                    }
                }
            }
            return insertRebalance(newNodeParent);
        }
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
        if (search(k) == null){   //item with key k not found in the tree
            return -1;
        }
        else {//item with key k found in the tree
            IAVLNode node = getPosition(k);

            if (node.getKey() == min.getKey()){
                this.min = node.getSuccessor();
            }

            if (node.getKey() == max.getKey()){
                if (node.getLeft().isRealNode()){            //node have left child
                    IAVLNode max_subtree = node.getLeft();
                    while (max_subtree.getRight().isRealNode()){
                        max_subtree = max_subtree.getRight();
                    }
                    this.max = max_subtree;
                }
                else{                   //node is leaf
                    this.max = node.getParent();
                }
            }

            if (!node.getRight().isRealNode() && !node.getLeft().isRealNode()){  //node is leaf -> replace with external leaf
                replaceNodeWithAnotherNode(node, ExLeaf);
            }
            else if (!node.getRight().isRealNode()){             //has only left child -> replace with left child
                replaceNodeWithAnotherNode(node, node.getLeft());
            }
            else if (!node.getLeft().isRealNode()){            //has only right child -> replace with right child
                replaceNodeWithAnotherNode(node, node.getRight());
            }
            else{             //has two children -> replace with successor
                IAVLNode nodeSuccessor = node.getSuccessor();
                //the successor is a leaf or unary node with only right child
                replaceNodeWithAnotherNode(nodeSuccessor, nodeSuccessor.getRight());//remove the successor from the tree
                //replace the node with the successor
//                node.Key = nodeSuccessor.getKey();
//                node.value = nodeSuccessor.value;
                node = nodeSuccessor.getRight();
            }
            return deleteRebalance(node.getParent());    //
        }
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
        IAVLNode node = getPosition(x);
        AVLTree tSmaller = new AVLTree();
        tSmaller.root = node.getLeft();
        AVLTree tBigger = new AVLTree();
        tBigger.root = node.getRight();
        AVLTree[] newTrees = new AVLTree[] {tSmaller, tBigger};

        while(node.getParent() != null){
            if (node.getParent().getLeft() == node){
                if (node.getParent().getRight().isRealNode()){
                    AVLTree subTreeBigger = new AVLTree();
                    subTreeBigger.root = node.getParent().getRight();
                    tBigger.join(node.getParent(), subTreeBigger);
                }
                else {
                    tBigger.insert(node.getParent().getKey(), node.getParent().getValue());
                }
            }
            else{
                if (node.getParent().getLeft().isRealNode()){
                    AVLTree subTreeSmaller = new AVLTree();
                    subTreeSmaller.root = node.getParent().getLeft();
                    tSmaller.join(node.getParent(), subTreeSmaller);
                }
                else{
                    tSmaller.insert(node.getParent().getKey(), node.getParent().getValue());
                }
            }
            node = node.getParent();
        }

        return newTrees;
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
    public int join(IAVLNode x, AVLTree t) {
        int rank_T1 = this.root.getHeight();
        int rank_T2 = t.root.getHeight();

        if (rank_T1 <= rank_T2){
            if (this.root.getKey() < x.getKey()){   // keys(this) < x.key < keys(t)
                IAVLNode mergeNode = findLeftNode(t, rank_T1);
                //update x
                x.setRight(mergeNode);
                x.setLeft(this.root);
                x.setParent(mergeNode.getParent());
                //update nodes
                mergeNode.getParent().setLeft(x);
                this.root.setParent(x);
                mergeNode.setParent(x);
                //change max
                this.max = t.max;
            }
            else{         // keys(this) > x.key > keys(t)
                IAVLNode mergeNode = findRightNode(t, rank_T1);
                //update x
                x.setRight(this.root);
                x.setLeft(mergeNode);
                x.setParent(mergeNode.getParent());
                //update nodes
                mergeNode.getParent().setRight(x);
                this.root.setParent(x);
                mergeNode.setParent(x);
                //update min
                this.min = t.min;
            }
        }

        else{//rank_T1 > rank_T2
            if (this.root.getKey() < x.getKey()){// keys(this) < x.key < keys(t)
                IAVLNode mergeNode = findRightNode(this, rank_T2);
                //update x
                x.setRight(t.root);
                x.setLeft(mergeNode);
                x.setParent(mergeNode.getParent());
                //update nodes
                mergeNode.getParent().setRight(x);
                t.root.setParent(x);
                mergeNode.setParent(x);
                //update max
                this.max = t.max;
            }
            else{// keys(this) > x.key > keys(t)
                IAVLNode mergeNode = findLeftNode(t, rank_T2);
                //update x
                x.setRight(mergeNode);
                x.setLeft(t.root);
                x.setParent(mergeNode.getParent());
                //update nodes
                mergeNode.getParent().setLeft(x);
                t.root.setParent(x);
                mergeNode.setParent(x);
                //update min
                this.min = t.min;
            }
        }
        insertRebalance(x);
        this.root = x;
        return Math.abs(rank_T1 - rank_T2) + 1;
    }
    // ######################################################################
    // Auxiliary functions for the Tree:

    private IAVLNode getPosition(int k){
        IAVLNode node = root;
        IAVLNode parent_node = node;
        while (node.isRealNode()){
            if (node.getKey() > k){
                parent_node = node;
                node = node.getLeft();
            }
            else {
                parent_node = node;
                node = node.getRight();
            }
        }
        if (parent_node == ExLeaf) return null;
        return parent_node;
    }

    private int rotateRight(IAVLNode node){
        IAVLNode left = node.getLeft();
        IAVLNode left_right = node.getLeft().getRight();

        if (node.getParent() == null){
            this.root = left;
        }
        else if (node.getParent().getKey() > node.getKey()){
            node.getParent().setLeft(left);
        }
        else {
            node.getParent().setRight(left);
        }

        //update parents
        left.setParent(node.getParent());
        left_right.setParent(node);
        node.setParent(left);

        //update children
        node.setLeft(left_right);
        left.setRight(node);

        //update size
        return updateSize(left_right) + updateSize(node) + updateSize(left);
    }


    // _______________________________________________
    private int rotateLeft(IAVLNode node){
        IAVLNode right = node.getRight();
        IAVLNode right_left = node.getRight().getLeft();

        if (node.getParent() == null){
            this.root = right;
        }
        else if (node.getParent().getKey() > node.getKey()){
            node.getParent().setLeft(right);
        }
        else {
            node.getParent().setRight(right);
        }

        //update parents
        right.setParent(node.getParent());
        right_left.setParent(node);
        node.setParent(right);

        //update children
        node.setRight(right_left);
        right.setLeft(node);

        //update size
        return updateSize(right_left) + updateSize(node) + updateSize(right);
    }

    //________________________________________________
    private int insertRebalance(IAVLNode node){
        int counter = 0;
        while(node != null ){
            int diffRight = node.getHeight() - node.getRight().getHeight();
            int diffLeft = node.getHeight() - node.getLeft().getHeight();

            if ((diffLeft == 0 && diffRight == 1) || (diffRight == 0 && diffLeft ==1)){//0,1 or 1,0
                node.setHeight(node.getHeight()+1);   //promote
                counter++;
            }
            else {
                if (diffLeft == 0 && diffRight == 2) {
                    int childLeft_diffChildRight = node.getLeft().getHeight() - node.getLeft().getRight().getHeight();
                    int childLeft_diffChildLeft = node.getLeft().getHeight() - node.getLeft().getLeft().getHeight();

                    if (childLeft_diffChildLeft == 1 && childLeft_diffChildRight == 2) {     //single rotation right
                        counter += rotateRight(node);
                        node.setHeight(node.getHeight() - 1);   //demote
                        counter += 2;
                    } else if (childLeft_diffChildLeft == 2 && childLeft_diffChildRight == 1) {//double rotation LR
                        node.setHeight(node.getHeight() - 1);    //demote
                        node.getLeft().setHeight(node.getLeft().getHeight() - 1);   //demote
                        node.getLeft().getRight().setHeight(node.getLeft().getRight().getHeight() + 1);//promote
                        counter += rotateLeft(node.getLeft());
                        counter += rotateRight(node);
                        counter += 5;
                    } else if (childLeft_diffChildLeft == 1 && childLeft_diffChildRight == 1) {
                        node.getLeft().setHeight(node.getLeft().getHeight() + 1);
                        counter += rotateRight(node);
                        counter += 2;
                    }
                } else if (diffLeft == 2 && diffRight == 0) {
                    int childRight_diffRight = node.getRight().getHeight() - node.getRight().getRight().getHeight();
                    int childRight_diffLeft = node.getRight().getHeight() - node.getRight().getLeft().getHeight();

                    if (childRight_diffLeft == 2 && childRight_diffRight == 1) {    //single rotation left
                        counter += rotateLeft(node);
                        node.setHeight(node.getHeight() - 1);    //demote
                        counter += 2;
                    }
                    else if (childRight_diffLeft == 1 && childRight_diffRight == 2) { //double rotation RL
                        node.setHeight(node.getHeight() - 1);  //demote
                        node.getRight().setHeight(node.getRight().getHeight() - 1);//demote
                        node.getRight().getLeft().setHeight(node.getRight().getLeft().getHeight() + 1);      //promote
                        counter += rotateRight(node.getRight());
                        counter += rotateLeft(node);
                        counter += 5;
                    }
                    else if(childRight_diffLeft == 1 && childRight_diffRight == 1){
                        node.getRight().setHeight(node.getRight().getHeight() + 1);
                        counter += rotateLeft(node);
                        counter += 2;
                    }
                }
            }
            counter += updateSize(node);   //update size
            node = node.getParent();      //continue up
        }
        return counter;
    }

    //_________________________________________________
    private int deleteRebalance(IAVLNode node){
        int counter = 0;
        while (node != null){
            int diffRight = node.getHeight() - node.getRight().getHeight();
            int diffLeft = node.getHeight() - node.getLeft().getHeight();

            int childRight_diffRight = node.getRight().getHeight() - node.getRight().getRight().getHeight();
            int childRight_diffLeft = node.getRight().getHeight() - node.getRight().getLeft().getHeight();

            int childLeft_diffChildRight = node.getLeft().getHeight() - node.getLeft().getRight().getHeight();
            int childLeft_diffChildLeft = node.getLeft().getHeight() - node.getLeft().getLeft().getHeight();

            if ((diffLeft == 1 && diffRight == 2) || (diffLeft == 2 && diffRight == 1)){
                node.setHeight(node.getHeight() - 1);    //demote
                counter++;
            }
            else if (diffLeft == 3 && diffRight == 1 && childRight_diffLeft == 1 && childRight_diffRight == 1){
                node.setHeight(node.getHeight() - 1);     //demote
                node.getRight().setHeight(node.getRight().getHeight() + 1);   //promote
                counter += rotateLeft(node);
                counter += 3;
            }
            else if (diffLeft == 1 && diffRight == 3 && childLeft_diffChildLeft == 1 && childLeft_diffChildRight == 1){
                node.setHeight(node.getHeight() - 1);       //demote
                node.getLeft().setHeight(node.getLeft().getHeight() + 1); //promote
                counter += rotateRight(node);
                counter += 3;
            }
            else if (diffLeft == 3 && diffRight == 1 && childRight_diffLeft == 2 && childRight_diffRight == 1){
                node.setHeight(node.getHeight() - 2);     //demote twice
                counter += rotateLeft(node);
                counter += 2;
            }
            else if (diffLeft == 1 && diffRight == 3 && childLeft_diffChildLeft == 1 && childLeft_diffChildRight == 2){
                node.setHeight(node.getHeight() - 2); //demote twice
                counter += rotateRight(node);
                counter += 2;
            }
            else if (diffLeft == 3 && diffRight == 1 && childRight_diffLeft == 1 && childRight_diffRight == 2){
                node.setHeight(node.getHeight() - 2);   //demote twice
                node.getRight().setHeight(node.getRight().getHeight() - 1);   //demote
                node.getRight().getLeft().setHeight(node.getRight().getLeft().getHeight() + 1);      //promote
                counter += rotateRight(node.getRight());
                counter += rotateLeft(node);
                counter += 5;
            }
            else if (diffLeft == 1 && diffRight == 3 && childLeft_diffChildLeft == 2 && childLeft_diffChildRight == 1){
                node.setHeight(node.getHeight() - 2);   //demote twice
                node.getLeft().setHeight(node.getLeft().getHeight() - 1);    //demote
                node.getLeft().getRight().setHeight(node.getLeft().getRight().getHeight() + 1);    //promote
                counter += rotateLeft(node.getLeft());
                counter += rotateRight(node);
                counter += 5;
            }
            counter += updateSize(node);   //update size
            node = node.getParent();      //continue up
        }
        return counter;
    }
    // _______________________________________________
    private void replaceNodeWithAnotherNode(IAVLNode node, IAVLNode anotherNode){
        if (this.root == node){
            this.root = anotherNode;
        }
        else{
            if (node.getParent().getLeft() == node){
                node.getParent().setLeft(anotherNode);
            }
            else {
                node.getParent().setRight(anotherNode);
            }
        }
        anotherNode.setParent(node.getParent());
    }


    // _______________________________________________
    private int updateSize(IAVLNode node){
        node.setSize(node.getLeft().getSize() + node.getRight().getSize() + 1);
        if (!node.getRight().isRealNode() && !node.getLeft().isRealNode() && node.getHeight()!=0){ //is an internal leaf
            node.setHeight(0);
            return 1;
        }
        return 0;
    }

    // ________________________________________________
    private IAVLNode findRightNode (AVLTree t, int k){
        IAVLNode node = t.root;
        while (node.getHeight() > k){
            if (node.getRight().isRealNode()){
                node = node.getRight();
            }
            else{
                node = node.getLeft();
            }

        }
        return node;
    }


    //_________________________________________________
    private IAVLNode findLeftNode (AVLTree t, int k){
        IAVLNode node = t.root;
        while (node.getHeight() > k){
            if (node.getLeft().isRealNode()){
                node = node.getLeft();
            }
            else{
                node = node.getRight();
            }

        }
        return node;
    }

    // ###########################################################################
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
        public void setSize(int size);
        public int getSize(); //return the size of this tree.
        public IAVLNode getSuccessor(); // get the successor of the current node.
    }

    /**
     * public class AVLNode
     *
     * If you wish to implement classes other than AVLTree
     * (for example AVLNode), do it in this file, not in another file.
     *
     * This class can and MUST be modified (It must implement IAVLNode).
     */


    public class AVLNode implements IAVLNode{

        int key;
        int height = 0;
        int size = 1;
        String info;
        private IAVLNode left = null;
        private IAVLNode right = null;
        private IAVLNode parent;

        public AVLNode(int key,String value, IAVLNode parent){
            this.key = key;
            this.info = value;
            this.left = ExLeaf;  //key = -1 , info = null, height = -1, size = 0;
            this.right = ExLeaf;
            this.setParent(parent);
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
        public void setSize(int size) {this.size = size;}
//        public int getSize() {return 1 + this.getRight().getSize() + getLeft().getSize();}
        public int getSize() {return this.size;}

        public IAVLNode getSuccessor(){
            IAVLNode node;
            if (this.right.isRealNode()){    //has right child
                node = this.right;
                while (node.getLeft().isRealNode()){
                    node = node.getLeft();
                }
            }
            else{
                node = this.parent;
                while (node.getParent() != null && node == node.getParent().getRight()){
                    node = node.getParent();
                }
            }
            return node;
        }
    }

    public class CreateExternalLeaf extends AVLNode{
        CreateExternalLeaf() {
            super(-1, null, null);
            this.height = -1;
            this.size = 0;
        }
    }

}

