import java.util.*;
/**
* Priority queue class
*/
public class PQueue {
	public  Event[] events; //array to implement the heap
	private int size; //integer to represent the number of events in the priority queue
	
	/**
	 *Constructor
	 */
	public PQueue(int n) {
		events = new Event[n];
		size = 0;
	}//end Constructor
	
	/**
	 *Returns the event with the earliest date (highest priority).
	 *@return Event with earliest date.
	 */
	public Event getMin() {
		return events[1];
	}//end getMin

	/** @return size of queue */
	public int size() {
		return size;
	}//end size()
	
	/**
	 *Returns true if priority queue is empty, false otherwise.
	 *@return true if empty, false otherwise.
	 */
	public boolean isEmpty() {
		if (size == 0)
			return true;
		return false;
	}//end isEmpty
	/**
	 *Returns the index of the parent of an event in the priority
	 *queue.
	 *	@param: index of event whose parent is being found.
	 *	@return: index of provided events parent.
	 */
	public int parent(int childPos) {
		return childPos / 2;
	}//end parent
	
	/**
	 *Returns the index of the left child of an event.
	 *@param index of an event.
	 *@return index of its left child.
	 */
	public int leftChild(int parentPos) {
		return (2 * parentPos);
	}//end leftChild
	
	/**
	 *Returns the index of the right child of an event.
	 *@param index of an event.
	 *@return index of its right child.
	 */
	public int rightChild(int parentPos) {
		return (2 * parentPos + 1);
	}// end rightChild
	
	/**
	 *Swaps two events at indices i and j in the queue.
	 *@param index of first event.
	 *@param index of second event.
	 */
	public void swap(int i, int j) {
		Event temp = events[i];
		events[i] = events[j];
		events[j] = temp;
	}// end swap
	
	/**
	 *Inserts an event into the priority queue in the correct position.
	 *@param event to be inserted.
	 */
	public void insert(Event e) {
		events[size + 1] = e;
		size++;
		int c = size;
		while(c > 1 && events[c].userCal.getTimeInMillis() < events[parent(c)].userCal.getTimeInMillis()) {
			swap(c, parent(c));
			c = parent(c);
		}
	}//end insert()
	
	/**
	 *Removes and returns the event with the highest priority.
	 *@return event to be removed.
	 */
	public Event extractMin() {
		if(size==0) return null; //queue is empty
		Event temp = events[1];
		swap(1, size);
		events[size] = null;
		size--;
		int p = 1;
		while((events[leftChild(p)] != null && events[p].userCal.getTimeInMillis() > events[leftChild(p)].userCal.getTimeInMillis()) 
		|| (events[rightChild(p)] != null && events[p].userCal.getTimeInMillis() > events[rightChild(p)].userCal.getTimeInMillis())) {
			if(events[leftChild(p)].compare(events[rightChild(p)]) == -1) {
				swap(p, leftChild(p));
				p = leftChild(p);
			}
			else {
				swap(p, rightChild(p));
				p = rightChild(p);
			}
		}
		return temp;
	}//end extractMin()
	
	/**
	* Displays priority queue
	*/
	public void display() {
		for(int i = 1; i <= size; i++) {
			System.out.print(i + ": "); 
			System.out.println(events[i]);
		}//end for
	}//end display()

	/**
	* Find event e in priority queue
	* @param Event being searched for
	* @return true if event was found, false otherwise
	*/
	public boolean find(Event e) {
		for(int i=1; i<=size; i++) {
			if (events[i].toString().equals(e.toString())) return true;
		}//end for
		return false; //not found
	}//end find()

	/**
	*@return Event at index i
	*/
	public Event getEvent(int i) {
		try {
			return events[i];
		}
		catch (ArrayIndexOutOfBoundsException e) { //if the index is out of bounds
			return null;
		}
	}		
}//end getEvent()

/**
* Priority queue test class
*/
class PQueueTest {
	public static void main (String[] args) {
		Event e = new Event("Dinner", 9, 6, 1993, 7, 12); //create event objects
		Event a = new Event("Lunch", 9, 6, 2011, 6, 12);
		Event b = new Event("Breakfast", 7, 4, 2005, 12, 45);
		Event c = new Event("Snack", 6, 12, 2001, 12, 45);
		Event d = new Event("Date", 7, 5, 1990, 8, 24);
		Event f = new Event("Sleep", 4, 2, 2003, 11, 45);
		Event g = new Event("Read", 3, 5, 2003, 12, 30);
		PQueue p = new PQueue(50); 						//create priority queue
		p.insert(e);									//insert into priority queue
		p.insert(a);
		p.insert(b);
		p.insert(c);
		p.insert(d);
		p.insert(f);
		p.insert(g);
		p.display();
		System.out.print("Removed: ");				
		System.out.println(p.extractMin());				//remove highest priority elements
		System.out.println("");
		System.out.print("Removed: ");
		System.out.println(p.extractMin());
		System.out.println("");
		System.out.print("Removed: ");
		System.out.println(p.extractMin());
		System.out.println("");
		System.out.print("Removed: ");
		System.out.println(p.extractMin());
		System.out.println("");
		System.out.println("AFTER:");
		p.display();								//display queue
	}//end main()
}//end PQueueTest class
