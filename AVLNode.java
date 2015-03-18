
/**
*Class for the construction of a AVLNode
*which holds lc/right children and a char key
*/

public class AVLNode{
	public AVLNode parent;
	public AVLNode lc;
	public AVLNode rc;
	public String name;
	public String email;
	protected int height;
	
	/**
	*Constructor method
	*	@param A AVLNode lc to become the lc child.
	*	@param A AVLNode rc to become the right child.
	*	@param A char k to become the key.
	*/
	public AVLNode(String name, String email, AVLNode parent, AVLNode lc, AVLNode rc){
		this.name = name;
		this.email = email;
		this.parent = parent;
		this.lc = lc;
		this.lc = rc;
		
		height = 1;
		if (lc != null)
			height = Math.max(height, 1 + (lc.getHeight()));
			
		if (rc != null)
			height = Math.max(height, 1 + (rc.getHeight()));
	}
	
	/**
	* set height of node
	* @param Node whose height is being set
	*/
	public void setHeight(int h){
		height = h;
		}
		
	/**
	* get height of node
	* @return height of node
	*/
	public int getHeight(){
		return height;
		}
		
	/**
	*@returns name and email
	*/
	public String toString() {
		return name + " " + email;
	}
}
