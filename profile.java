//Arnab Bhattacharjee
//Dillon Kerr
//Stephen Squire
//COM 212 Final Project

import java.util.*;
import java.lang.Thread;
/**
*	user profile class
*/
public class profile {

	private String name;
	private String email;
	private String password;
	public String classYear;
	public String status;
	public Queue<Event> events;
	public Queue<String> wall;
	private Scanner scan;
	public AVLTree friends;
	public hash hashTable;
	public PQueue pq;
	public String friendString;
	
	/**
	* constructor for profile class
	*/
	public profile(String nm, String em, String pwd, String year, String stat, Queue<Event> eventQueue, Queue<String> wallQueue, String friendString, hash hashTable, PQueue pq) {
		name = nm;
		email = em;
		password = pwd;
		classYear = year;
		status = stat;
		events = eventQueue;
		wall = wallQueue;
		friends = new AVLTree();
		this.hashTable = hashTable;
		this.pq=pq;
		this.friendString=friendString;
		scan = new Scanner(System.in);
	}//end constructor
	
	/**
	* @return returns email address of user
	*/
	public String getEm() {
		return email;
	}//end getEmail()

		/**
	* @return returns name of user
	*/
	public String getNm() {
		return name;
	}//end getName()
	
	/**
	* @return returns password of user
	*/
	public String getPwd() {
		return password;
	}//end getEmail()
	
	/**
	* reads friend string and inserts friends into tree
	*/
	public void readFriendString() {
		Scanner friendScanner = new Scanner(friendString);
		friendScanner.useDelimiter(",");
		
		while(friendScanner.hasNext()) {
			String oneFriend = friendScanner.next();
			profile p = hashTable.find(oneFriend);
			String nm = p.getNm();
			friends.insert(nm,oneFriend);
		}
	}//end readFriendString()
	
	
	/**
	* change the user's current status
	*/
	public boolean changeStatus() {
		System.out.println("\nEnter new status:\n");
		Scanner scan2 = new Scanner(System.in);		//not sure why this is needed
		String s = scan2.nextLine();			//it doesn't work otherwise
		status=s;
		System.out.println("\nYour new status is: "+status);
		return true;
	}//end changeStatus()
	
	/**
	* finds the user in the hash table
	* @return profile object of user or null if not found
	*/
	public profile findUser() {
		System.out.println("\nEnter the friend's e-mail address:\n");
		String email = scan.next();
		profile p = hashTable.find(email);
		if(p==null) {
			System.out.println("\nThe e-mail address was not found.");
			return null;
		}
		else {
			return p;
		}
	}
	
	/**
	* adds the friend to the user's friend tree
	* @return returns true if friend was found and added, false otherwise
	*/
	public void addFriend() {
		profile p = findUser();
		if(p!=null) {
			if(friends.find(p.getNm(),friends.root())!=null) {
				System.out.println("\n"+p.name+" is already your friend.");
			}//end if
			else {
				friends.insert(p.getNm(),p.getEm());
				p.friends.insert(name,email);
				System.out.println("\n"+p.name+" was added to your friends.");
			}//end else
		}//end if
	}//end addFriend()

	/**
	* removes the friend to the user's friend tree
	* @return returns true if friend was found and deleted, false otherwise
	*/
	public void delFriend() {
		if(friends.size()==0) {
			System.out.println("\nYou don't have any friends.");
		}//end if
		else {
			System.out.println("\nEnter the number next to the friend you want to delete.\n");
			try {
				String[] a = new String[200];
				a = traversePrint(friends.root(),0,a);
				System.out.println("");
				int k = scan.nextInt();
				String em = a[k];
				profile p = hashTable.find(em);
				if(friends.find(p.getNm(),friends.root())!=null) {
					friends.delete(p.getNm());
					p.friends.delete(name);
					System.out.println("\n"+p.name+" was removed from your friends.");
				}//end if
				else System.out.println("\nFriend not found.");
			}
			catch (Exception ex0) {
				System.out.println("\nIncorrect entry.");
			}
		}//end else
	}//end delFriend()

	/**
	* display profile info
	*/
	public void homeScreen() {
		checkEvents();
		System.out.println("\nName: "+name);
		System.out.println("E-mail: "+email);
		System.out.println("Class Year: "+classYear);
		System.out.println("Status: "+status);
		
		System.out.print("Wall: ");
		String wallString=wall.toString();
		System.out.println(wallString.substring(1,wallString.length()-1));
		
		System.out.print("Events: ");
		String eventsString=events.toString();
		System.out.println(eventsString.substring(1,eventsString.length()-1));

		System.out.print("Next campus event: ");
		if(pq.size()==0) System.out.println("No events are scheduled.");
		else System.out.println(pq.getMin());
	}//end home()
	
	/**
	* performs in order traversal of friends tree and prints ouput
	* @param root node friends tree
	*/
	public String[] traversePrint(AVLNode v,int count, String[] a) {
		if(v!=null) {
				if(v.lc!=null)
					traversePrint(v.lc,count,a);
					
				String em =v.email;
				profile p = hashTable.find(em);
				count++;
				a[count]=em;
				if(p!=null) {
					System.out.println(count+". "+p.getNm()+": "+p.status);
				}
				
				if(v.rc!=null) 
					traversePrint(v.rc,count,a);
			}
			return a;
	}
	
	/**
	* performs in order traversal of friends tree and concatenates output to string
	* @param root node friends tree
	*/
	public void traverseString(AVLNode v) {
		if(v!=null) {
				if(v.lc!=null)
					traverseString(v.lc);
					
				friendString+=v.email+",";
				
				if(v.rc!=null) 
					traverseString(v.rc);
			}
	}
	
	/**
	* list friends and their wall messages
	*/
	public void listFriends() {
		if(friends.root()==null) System.out.println("\nYou don't have any friends.");
		else {
			System.out.print("\nFriends: \n\n");
			
			String[] a = new String[200];
			
			a = traversePrint(friends.root(),0,a);

			System.out.println("\nEnter 1 to write on a friend's wall.");
			System.out.println("Enter 2 to see a friend's profile.");
			System.out.println("Enter 3 to return to the main menu.\n");
			String e ="";
			do {
				e = scan.next();
				if(e.equals("1")) {
					System.out.println("\nEnter the number next to the friend whose wall you want to write on.\n");
					try {
						int k = scan.nextInt();
						String em = a[k];
						profile p = hashTable.find(em);
						System.out.println("\nEnter the wall message:\n");
						scan.nextLine();
						String message = scan.nextLine();
						if(p.wall.offer(message)==false) {
								p.wall.poll();
							 	p.wall.add(message);
						}
						System.out.println("\nYou wrote \""+message+"\" on "+p.name+"'s wall.");
						break;
					}
					catch (Exception ex0) {
						System.out.println("\nIncorrect entry.");
						break;
					}
				}//end if
				
				else if(e.equals("2")) {
					System.out.println("\nEnter the number next to the friend whose profile you want to see.\n");
					try {
						int k = scan.nextInt();
						String em = a[k];
						profile p = hashTable.find(em);
						p.homeScreen();
						break;
					}
					catch (Exception ex0) {
						System.out.println("\nIncorrect entry.");
						break;
					}
				}//end if

				else if(e.equals("3")) System.out.println("\nReturning to main menu...");
				
				else System.out.println("\nIncorrect entry.\n");
				
				} while(!e.equals("3"));
		}//end else
	}//end listFriends()
	
	/**
	* add a new event to profile queue
	*/
	public void addEvent() {
		System.out.print("\nUpcoming Events:\n\n");
		pq.display();
		System.out.println("\nEnter 1 to select an event from the list.\n");
		System.out.println("Enter 2 to create a new event.\n");
		String e = scan.next();
		
		if(e.equals("1")) {
			System.out.println("\nEnter the number next to the event you want to add:\n");
			int f = scan.nextInt();
			Event ev = pq.getEvent(f);
			if (ev==null) System.out.println("\nIncorrect Entry.");
			else {
				if(events.contains(ev)) System.out.println("\nYou are already attending this event.");
				else {
					events.add(ev);
					System.out.println("\nThe event "+ev.name+" was successfully added.");
				}//end if
			}//end else
		}//end if
		
		else if(e.equals("2")) {
			System.out.println("\nEnter month (MM) : \n");
			int m = scan.nextInt();
			System.out.println("\nEnter day (DD) : \n");
			int d = scan.nextInt();
			System.out.println("\nEnter year (YYYY): \n");
			int y = scan.nextInt();
			System.out.println("\nEnter hour in military time (HH): \n");
			int h = scan.nextInt();
			System.out.println("\nEnter minute (MM): \n");
			int min = scan.nextInt();
			System.out.println("\nEnter name and location of event: \n");
			scan.nextLine();
			String a = scan.nextLine();
			String ev_name = a;
			Event ev = new Event(ev_name, m, d, y, h, min);
			if(!pq.find(ev)) {
				pq.insert(ev);
				events.add(ev);
				System.out.println("\nThe event "+ev.name+" was successfully added.");
			}
			else System.out.println("\nThis event already exists.");
			
		}//end else
		
		else System.out.println("\nIncorrect entry.");
	}//end addEvent()

	/**
	* Checks if events in the priority queue have already passed and if so, removes them
	*/
	public void checkEvents() {
		Calendar cal = Calendar.getInstance();
		Event ev = pq.getMin();
		if(pq.size()!=0) {
			while(ev!=null && ev.userCal.getTimeInMillis() < cal.getTimeInMillis()) { //if the top event has passed
				pq.extractMin(); //remove it from the priority queue
				ev = pq.getMin(); //check the next event
			}//end while
		}//end if

	}//end checkEvents()
		
	
	/**
	* user control panel
	*/
	public void userint() {
		do {
			homeScreen();
			System.out.println("\nEnter 1 to change your status.\n");
			System.out.println("Enter 2 to add a friend.\n");
			System.out.println("Enter 3 to delete a friend.\n");
			System.out.println("Enter 4 to add an event.\n");
			System.out.println("Enter 5 to see a list of your friends.\n");
			System.out.println("Enter 6 to log out.\n");
			String e = scan.next();
			if(e.equals("1")) changeStatus();
			else if(e.equals("2")) addFriend();
			else if(e.equals("3")) delFriend();
			else if(e.equals("4")) addEvent();
			else if(e.equals("5")) listFriends();
			else if(e.equals("6")) break;
			else System.out.println("\nIncorrect entry.");
			
			try {	//wait 2 seconds before displaying menu again when it loops
				Thread.currentThread().sleep(2000);
			}
			catch(InterruptedException ie){
			}
			
		} while(true);
	}//end userint();
}//end profile class
