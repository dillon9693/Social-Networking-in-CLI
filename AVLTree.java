//Arnab Bhattacharjee, Dillon Kerr, Stephen Squire
/**AVLTree class
*A AVL tree class that constructs a tree
*where single digit variables are the children
*of their operands. Postfix traversion
*of the tree will convert infix to postfix.
*/
import java.util.*;
import java.lang.Math.*;
import java.io.*;   //so we can use the scanner class

public class AVLTree {
	private AVLNode root;
	private int size;
	
	/**
	*Constructor method
	*/
	public AVLTree() {
		root = null;
		size = 0;
	}

	/**
	*@return the root of the BTree
	*/
	public AVLNode root() {
		return root;
	}

	/**
	*@return size of the tree
	*/
	public int size() {
		return size;
	}
	
	/**
	*getHeight methods
	*@return the height of the AVLNode
	*calls back to node method
	*/
	public int getHeight(AVLNode t){
		if (t == null){
			return 0;
		}
		else return t.getHeight();
	}
	
	/**
	*Sets the height of an internal node (call back to AVLNode)
	*
	*/
	public void setHeight(AVLNode p){
		while (p !=null){
			if(p.lc==null && p.rc==null) {
				p.setHeight(1);
				
			}
			else if(p.lc==null) {
				p.setHeight(1+getHeight(p.rc));
				
			}
			else if(p.rc==null){
				p.setHeight(1+getHeight(p.lc));
				
			}
			else {
				p.setHeight(1+Math.max(getHeight(p.lc),getHeight(p.rc)));
			}
			p=p.parent;
		}
	}//end setHeight()
				
	/**
	*method for creation and insertion of
	*a AVLNode with name value k into the proper
	*place in a given AVLTree.
	*Uses the recursive function to find the 
	*correct position if the tree is not null.
	*	@param name value k
	*/
	public void insert(String n, String em) {
		if (root==null){
			root = new AVLNode(n, em, null,null,null); //also set initial bf
			size++;
		}
		else {
			recInsert(n, em, root);
		}
	}
	
	private void recInsert(String n, String em, AVLNode v){
		if (n.compareToIgnoreCase(v.name) > 0) {//This means that v.name comes before k	
			if (v.rc==null){
				v.rc = new AVLNode(n, em, v,null, null); //also set initial bf
				
				setHeight(v);
				
				size++;
				rebalance(v);
				
			}
			else {
				recInsert(n,em, v.rc);
			}
		}
		else if (n.compareToIgnoreCase(v.name) <= 0) {
			if (v.lc==null){
				v.lc = new AVLNode(n,em,v,null,null);
				//setHeight(v);
				
				size++;
				setHeight(v);
				rebalance(v);//also set initial bf
				
				
			}
			else {
				recInsert(n,em,v.lc);
			}
		}
		
		
		//UPDATE BFs:  add loop to walk from v all the way up to root, calling updateBF in each iteration
		//call rebalance method here
	}
	
	/**
	*This code was adapted from Lafore Data Structures Text
	*Method for user choice of traversal methods
	*/
	public void traverse(char traverseType)
		{
			switch(traverseType)
			{
				case 'p': 
					preOrder(root);
					break;
				case 'i': 
					inOrder(root);
					break;
				case 't': 
					postOrder(root);
					break;
			}
			
	}
	/**
	*methods for printing the pre, post, and inorder 
	*tree traversals of a given AVLTree rooted at v.
	*	@param AVLTree rooted at AVLNode v
	*/
	
	public void preOrder(AVLNode v){
		
		if (v.lc!=null)
			preOrder(v.lc);
		if (v.rc!=null)
			preOrder(v.rc);
	}
	
	public void inOrder(AVLNode v){
		if (v.lc!=null)
			inOrder(v.lc);
		
		if (v.rc!=null)
			inOrder(v.rc);
	}
	
	public void postOrder(AVLNode v){
		if (v.lc!=null)
			postOrder(v.lc);
		if (v.rc!=null)
			postOrder(v.rc);
		
	}

	/**
	*method for determining whether a given node
	*v is a left child or a right child.
	*	@param AVLNode of a AVLTree
	*	@return -1 for left, 0 for root, 1 for right
	*/
	public int leftOrRight(AVLNode v) {
		if (v.parent==null)           // v is root
			return 0;
		else if (v.parent.lc==v) //v is left child
			return -1;
		else				//v is right child
			return 1;
	}
	
	/**
	*delete method for removing entries of given name values.
	*Will check for various cases and make the appropriate
	*suAVLTreeitutions with the correct successors.
	*	@param namevalue k
	*/
	public AVLNode delete(String k){
		AVLNode min = null;
		AVLNode v = null;
		v = find(k, root);

		

		AVLNode tempvparent = v.parent;
		if (v.rc != null){
			min = getMin(v.rc); //getting executed
			AVLNode tempminparent = min.parent;
			
		}
		else if (v.lc != null){
			min = getMax(v.lc);
			AVLNode tempminparent = min.parent;
		}
		else { AVLNode tempminparent = null;}

		
		
		if (v.lc==null && v.rc==null){ //no children
			
			
			
			
			if (leftOrRight(v)==-1)
			
				v.parent.lc=null;
			
			else if (leftOrRight(v)==0)
				
				root = null;
			
			else { v.parent.rc=null;}
			
			size=size-1;
			
			rebalance(v.parent);
			//starting from v.parent, walking all the way up to root (using a loop),
			//updateBF on each node
		}
		
		else if(v.lc!=null && v.rc!=null){//2children				
			
			v.name = min.name; //copy name of succesor into v
			v.email = min.email;
			replace(min, min.rc); //replace min with its right child (null)
			
			rebalance(min.parent);
			//starting from min.parent, walk all the way up to root using a loop,
			//updating BF on each node
		}	
		else {  // v has one child
			if (v.lc==null)
				replace(v, v.rc);
				
			else {
				replace(v, v.lc);
			}			
			
			
			rebalance(v.parent);
			
			//starting from v.parent, walking all the way up to root (using a loop),
			//updateBF on each node		
		}
		//after updating bf, call rebalance method here
		rebalance(v);
		return v;
	} // end delete method
	
	/**
	*method for replacing a given AVLNode v with
	*a AVLNode w in a given tree.
	*	@param AVLNode v within AVLTree t
	*	@param AVLNode w within AVLTree t
	*/
	public void replace(AVLNode b, AVLNode w) {
		
		if (b==root) { //if first node is root
			
			root=w;
			//set root to second node (this may be messing up root case)
			if (w!=null){
				w.parent=null;
				
			}
			//else {root = null;}
		}
		else {  // b is not the root, so it has a parent
			
			
			if (leftOrRight(b)==1){ //v is a right child
				
				b.parent.rc = w; //reassign v's parent's right child to w
				//w.parent = v.parent;
				//w.lc = v.lc;
				//w.rc = v.rc;
			}
			
			else { //first node is a left child
				
				b.parent.lc = w; //reassign v's parent's right child to w
				
				//w.lc = v.lc;
				//w.rc = v.rc;
			}
			if (w != null)
				w.parent = b.parent;
		}
	}

	
	/**
	*boolean for empty tree exeption
	*/
	public boolean isEmpty() {
		if (root==null)
			return true;
		return false;
	}
	
	/**
	*method for obtaining the minimum of the left subtree
	*of a given node
	*	@param AVLNode r within AVLTree t
	*	@return node with the minumum key value
	*/
	public AVLNode getMin(AVLNode r) {
		if (r == null)//
			return null;//
		if (r.lc!=null) {
			return getMin(r.lc);
		}
		else {
			
			return r;
		}
	}
	
	/**
	*method for obtaining the maximum of the right subtree
	*of a given node
	*	@param AVLNode v within AVLTree t
	*	@return node with the minumum key value
	*/
	public AVLNode getMax(AVLNode r) {
		if (r.rc!=null) {
			return getMax(r.rc);
		}
		else {
			
			return r;
		}
	}
	
	/**
	*addRoot method for inserting new root r
	*into tree T
	*/
	public void addRoot(String n, String em) {
		AVLNode r = new AVLNode(n,em,null, null, null);
		if (root==null)
			root = r;
	}
	
	
	/**
	*Rebalance method for checking inbalances within a given tree,
	*then correcting for those imbalances with the appropriate rotations
	*iteratively moving up along the parents of t to the root.
	*	@param AVLNode t within AVLTree t
	*	
	*/
	//REBALANCE METHOD (Node v) 
	//Check for imbalances:  for each node z from t up to root, checking if |bf(t)| >= 2
	public void rebalance(AVLNode t){
		
		while (t != null){
			
			if(getHeight(t.lc)-getHeight(t.rc) == 2) {//bf -2
				if(getHeight(t.lc.lc)-getHeight(t.lc.rc) == 1){ //bf of subtree -1, 
					
					t = rotateWithlc(t); //single CW rotation
					
				}	
			
				else {
					
					t = doubleWithlc(t); //rotate CCW on child, then single CW
					
				}
			}
			
			if(getHeight(t.rc)-getHeight(t.lc) == 2) {//bf +2
				if(getHeight(t.rc.rc)-getHeight(t.rc.lc) == 1) {//bf subtree +1
					t = rotateWithrc(t); 
					
				}
				
				else{
					t = doubleWithrc(t); 
					
				}
			}
			//if (leftOrRight(t) == -1)
			//	t.parent.lc = t;
			//else t.parent.rc = t;
			
		t= t.parent;
			
		}
	}
	
	/**
	*@param root of imbalanced tree
	*@return root of rebalanced tree
	*/
	private AVLNode rotateWithlc( AVLNode k2 )
        {
		AVLNode p = k2;
		AVLNode k1 = k2.lc;  //k2 assigned
		
		if (k2.parent != null){ //k2 is Not root...
			p = k2.parent; //p holds k1s parent
			if (leftOrRight(k2) == -1){ //is left child
				p.lc = k1;
				k1.parent = p; //k1s parent is p
			}
			else if (leftOrRight(k2) == 1){//is right child
				p.rc = k1;
				k1.parent = p; //k1s parent is p
			}
		}
		else {k1.parent = null; root = k1;}
		k2.parent = k1;
		k2.lc = k1.rc;
		
		if (k1.rc != null)  //CC
			k1.rc.parent = k2; //CC
			
		k1.rc = k2;		
		setHeight(k2);//.setHeight(Math.max( getHeight( k2.lc ), getHeight( k2.rc ) + 1));
		setHeight(k1);//k1.setHeight(Math.max( getHeight( k1.lc ), getHeight( k1.rc ) + 1));
		setHeight(p);	
		return k1;
        }
        /**
         * Rotate binary tree node with right child.
         * For AVL trees, this is a single rotation for case 4.
         * Update heights, then 
	* @return root of new tree
         */
        private AVLNode rotateWithrc( AVLNode k1 ){ 
		AVLNode p = k1;
		AVLNode k2 = k1.rc;  //k2 assigned
		
		if (k1.parent != null){ //k1 is Not root...
			p = k1.parent; //p holds k1s parent
			if (leftOrRight(k1) == -1){ //is left child
				p.lc = k2;
				k2.parent = p; //k2s parent is p
			}
			else if (leftOrRight(k1) == 1){//is right child
				p.rc = k2;
				k2.parent = p; //k2s parent is p
			}
		}
		else {k2.parent = null; root = k2;}
		k1.parent = k2;
		k1.rc = k2.lc;
		if (k2.lc != null)		
			k2.lc.parent = k2; //CC
		k2.lc = k1;

		setHeight(k1); //update from the bottom node...
		setHeight(k2);
		setHeight(p);

		return k2;
		
			
		
        }

        /**
         * Double rotate binary tree node: first lc child
         * with its right child; then node k3 with new lc child.
         * For AVL trees, this is a double rotation for case 2.
         * Update heights, then return new root.
         */
        private AVLNode doubleWithlc( AVLNode k3 )
        {
            k3.lc = rotateWithrc( k3.lc );
            return rotateWithlc( k3 );
        }

        /**
         * Double rotate binary tree node: first right child
         * with its lc child; then node k1 with new right child.
         * For AVL trees, this is a double rotation for case 3.
         * Update heights, then return new root.
         */
        private AVLNode doubleWithrc( AVLNode k1 )
        {
		
            k1.rc = rotateWithlc( k1.rc );
            return rotateWithrc( k1 );
        }
	
	/**
	*Creates a new tree T rooted at v, with subtrees
	*t1 and t2
	*	@param a TNode to be the parent of the subtrees
	*	@param A BTree that will become the lc child of TNode v
	*	@param A Btree that will become the right chile of TNode v
	*/
	public void attach(AVLNode v, AVLTree t1, AVLTree t2) {
		if (v.lc==null && v.rc==null) {
			v.lc = t1.root;
			v.rc = t2.root;
		}
		else System.out.println("Cannot attach");
	}	
	
	/**
	*Find method for locating a given node in the tree,
	*starting at the root and searching based on the binary
	*nature of the tree.
	*	@param a String representing a persons name whose node must be found
	*	@param the root of the AVL tree
	*/
	public AVLNode find(String n, AVLNode v){
		if (v==null) return null;
		else if (n.compareToIgnoreCase(v.name) == 0) return v;
		else if (n.compareToIgnoreCase(v.name) > 0) return find(n,v.rc);
		else if (n.compareToIgnoreCase(v.name) < 0) return find(n,v.lc);
		else return null;
	}
	
	
	//This code was adapted from Lafore Data Structures Text
	public void displayTree(){
		try {
			
			Stack<AVLNode> globalStack = new Stack<AVLNode>();
			globalStack.push(root);
			int nBlanks = 32;
			boolean isRowEmpty = false;
			System.out.println("......................................................");
			while(isRowEmpty==false)
			{
				Stack<AVLNode> localStack = new Stack<AVLNode>();
				isRowEmpty = true;

				for(int j=0; j<nBlanks; j++)
				System.out.print(' ');

				while(!globalStack.isEmpty())
				{
					AVLNode temp = globalStack.pop(); //pop the whole tree
					if(temp != null)
					{
						System.out.print(temp.name);
						localStack.push(temp.lc);
						localStack.push(temp.rc);

						if(temp.lc != null || temp.rc != null)
						isRowEmpty = false;
					}
					else
					{
						System.out.print("--");
						localStack.push(null);
						localStack.push(null);
					}
					for(int j=0; j<nBlanks*3; j++)
					System.out.print(' ');
				}  // end while
				System.out.println();
				nBlanks /= 2;
				while(localStack.isEmpty()==false)
				globalStack.push( localStack.pop() );
			}  // end while isRowEmpty is false
			System.out.println(
			"......................................................");
		}  
		catch (Exception ex0) { }
	}

	
}
//end BTree class
/**
* class housing tree application
*/
class treeApp
{

	public static void main(String[] args) throws IOException
	{
		int choice;
		String name;	
		AVLTree theTree = new AVLTree();	//create AVLTree instance
		Scanner scan = new Scanner(System.in);

		theTree.insert("Dillon","dkerr@conncoll.edu");		//insert names
		theTree.insert("Arnab","abhattac@conncoll.edu");
		theTree.insert("Stephen","ssquire@conncoll.edu");
		theTree.insert("John","jdoe@conncoll.edu");
		theTree.insert("Flavor","fflavor@conncoll.edu");
		theTree.insert("Giant","gglass@conncoll.edu");

		do				//user interface
		{	
			System.out.print("\nEnter a letter: \n\n quit program (q) \n show tree (s) \n ");
			System.out.print("insert (i) \n find (f) \n delete (d) \n traverse (t) \n\t\t --> ");
			choice = getChar();
			//to see how switch statements in java work, see:
			// http://www.cafeaulait.org/course/week2/42.html
			switch(choice)
			{
				case 's':
					theTree.displayTree();
					break;
				case 'i':
					System.out.print("\nEnter email: ");
					String em = scan.nextLine();
					System.out.print("\nEnter name: ");
					name = scan.nextLine();
					theTree.insert(name,em);
					break;
				case 'f':
					System.out.print("\nEnter name to find: ");
					name = scan.nextLine();
					AVLNode found = theTree.find(name, theTree.root());
					if(found != null)
					{
						System.out.print("\nFound: ");
						System.out.println(found);
						System.out.print("\n");
					}
					else 
					{
						System.out.print("\nCould not find ");
						System.out.print("" + name + '\n');
					}
					break;
				case 'd':
					System.out.print("\nEnter name to delete: ");
					name = scan.nextLine();
					if(theTree.delete(name) != null)
						System.out.print("\nDeleted " + name + '\n');
					else 
					{
						System.out.print("\nCould not delete ");
						System.out.print("" + name + '\n');
					}
					break;
				case 't':
					System.out.print("\nEnter a letter: preorder (p), inorder (i), or postorder (t) --> ");
					char letter = getChar();
					theTree.traverse(letter);
					break;
				case 'q':
					System.out.println("\nGoodbye.");
					break;
				default:
					System.out.print("\nNot a valid entry.\n");
			}  // end switch
		} while(choice != 'q'); //loops until user quits
	}  // end main()
	// -------------------------------------------------------------
	public static String getString() throws IOException
	{
		InputStreamReader isr = new InputStreamReader(System.in);
		BufferedReader br = new BufferedReader(isr);
		String s = br.readLine();
		return s;
	}
	// -------------------------------------------------------------
	public static char getChar() throws IOException
	{
		String s = getString();
		return s.charAt(0);
	}
	//-------------------------------------------------------------
	public static int getInt() throws IOException
	{
		String s = getString();
		return Integer.parseInt(s);
	}
	// -------------------------------------------------------------
}  // end class TreeApp
