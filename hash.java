import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;

public class hash {
	public profile[] a; //array holding profile objects
	private Queue<Integer> keylist; //queue for holding keys
	private int max; //capacity of hash table
	private int size; //size of hash table
	
	/**
	* constructor for hash class
	*/
	public hash(int n) {
		max=n;			//capcity of array
		a = new profile[max];
		keylist = new ArrayBlockingQueue<Integer>(200);
		size=0;
	}//end constructor
	
	/**
	*@return keylist
	*/
	public Queue<Integer> getKeylist() {
		return keylist;
	}//end hashArray()	
		
	/**
	*@param insert profile object into hash table
	*/
	public void insert(profile p) {
		String em = p.getEm();
		int key = hashString(em);
		a[key]=p;
		keylist.add(key);
		size++;
		
	}//end insert()
	
	/**
	* generate hash key
	* @param String s - string containing e-mail address of user
	* @return hash key
	*/
	public int genHash(String s) {
		long hashval = 0;

		for(int i=0; i<s.length(); i++) {	//generate key using horner hash
			hashval=(hashval*128+(int)s.charAt(i))%(max);
		}//end for

		int key = (int)hashval;		//convert hashval to int
		return key;
	}//end genHash()
	
	/**
	* insert String into hash table
	* @param String s - string containing e-mail address of user
	* @return hash key
	*/
	public int hashString(String s) {
		int key = genHash(s);

		if(a[key]==null) {	//no collision
			return key;
		}//end if	
		else {				//if there is a collision
			int start=key;
			if(key<max) key++;
			else key=0;			
			while(a[key]!=null && key!=start) {		//linear probing
				if(key<max-1) {
					key++;
				}
				else {
					key=0;				//wrap around
				}
			}
			
			if(key==start) {
				System.out.println("\nERROR: Array full\n");
				return -1;
			}//end if
			else {
				return key;
			}//end else()
		}//end else
	}//end createKey()
	
	/**
	* find profile using email
	* @param string containing email address
	* @return key associated with profile object or -1 if not found
	*/
	public profile find(String s) {
		int key = genHash(s);

		if(a[key]==null) {
			//System.out.println(a[key]);
			return null; //not found
		}
		else {
			//System.out.println(a[key]);
			String em ="";
			int start=key;
			while(true) {
				profile p = a[key];
				em = p.getEm();
				if(em.equals(s)) return p; //found
				if(key<max) key++;
				else key=0;
				if(key==start) {
					//System.out.println("Key equals start.");
					return null; //not found
				}
			}//end while
		}//end else
	}//end find()

	/**
	* display contents of hash table
	*/
	public void display() {
		for(int i=0; i<a.length; i++) {
			if(a[i]!=null) {
				profile p = a[i];
				System.out.println("Index: "+i+"\nEntry: "+p.getEm()+"\n");
			}//end if
		}//end for
	}//end display()
}//end hash class
