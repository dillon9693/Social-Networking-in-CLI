import java.util.*;

/**
*Event object class 
*/
public class Event {
	public String name;
	public int month, day, year, hour, minute;
	public Calendar userCal = Calendar.getInstance(); //to compare times
	
	/**
	 *Constructor for event object
	 */
	public Event(String n, int m, int d, int y, int h, int min) {
		name = n;
		month = m;
		day = d;
		year = y;
		hour = h;
		minute = min;
		userCal.set(y, m - 1, d, h, min);
	}
	
	/**
	* @return printable string of event object
	*/
	public String toString() {
		return name+" on "+ month + "/" + day + "/" + year + " at " + hour + ":" + minute;
	}
	
	/**
	* @param event being checked 
	* @return 1 if this.e happens before e being compared, -1 if it is after, 0 if the happen at the same time
	*/
	public int compare(Event e) {
		if(e==null) return -1; 	//null event
		if(userCal.getTimeInMillis() > e.userCal.getTimeInMillis()) {//if after event e, returns 1
			return 1; }
		else if(userCal.getTimeInMillis() < e.userCal.getTimeInMillis()) { //if before event e, returns -1
			return -1; }
		else {//if the same, returns 0
			return 0; }
	}
}
/**
* event test class
*/
class EventTest {
	public static void main(String[] args) {
		Event e = new Event("Dinner", 9, 6, 1993, 7, 12); //create events
		Event a = new Event("Lunch", 9, 6, 2011, 6, 12);
		System.out.println(e.compare(a)); //compare them
	}
}
