//Arnab Bhattacharjee
//Dillon Kerr
//Stephen Squire
//COM 212 Final Project

import java.io.*;
import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;

/**
* home class - houses main program
*/
public class home {
	public hash hashTable = new hash(200);
	public PQueue eventQueue = new PQueue(200);
	
	/**
	* reads from input file
	*/
	public void readFile() {
		//this try-catch statement is needed around this file input code
		//because the FileInputStream may throw a FileNotFoundException
		//the text file MUST have at least one entry in it when reading
		try {
			Scanner lineScanner = new Scanner(new FileInputStream("userdata.txt"));
			
			while (lineScanner.hasNextLine()) { //while more of the input file is still available for reading
				
				String name = lineScanner.nextLine();  //reads an entire line of input
				
				String email = lineScanner.nextLine();
				
				String pwd = lineScanner.nextLine();
				
				String year = lineScanner.nextLine();
				
				String status = lineScanner.nextLine();

				String events = lineScanner.nextLine();
				
				Scanner eventsScanner = new Scanner(events);
				Queue<Event> eventBQueue = new ArrayBlockingQueue<Event>(200);
		
				eventsScanner.useDelimiter(",");
				
				String eventString; //will hold each individual event

				while (eventsScanner.hasNext()){
				
					eventString = eventsScanner.next();
					
					//break down each event string

					Scanner eventStringScanner = new Scanner(eventString);
					int month = eventStringScanner.nextInt();
					int day = eventStringScanner.nextInt();
					int Year = eventStringScanner.nextInt();
					int hour = eventStringScanner.nextInt();
					int minutes = eventStringScanner.nextInt();
					String desc = ""; //to hold the description of the event
					
					while (eventStringScanner.hasNext()) { //while there are words left...
						desc+=eventStringScanner.next()+" "; //reads the description one word at a time
					}//end while
					
					desc=desc.substring(0,desc.length()-1); //remove ending space
					
					Event eventObj = new Event(desc, month, day, Year, hour, minutes); //new event object
					
					eventBQueue.add(eventObj);	//push to event queue
					if(!eventQueue.find(eventObj)) eventQueue.insert(eventObj); //insert to master priority queue if it doesn't already
													//exist
				}//end while	

				String wall = lineScanner.nextLine();
				
				Scanner wallMsgScanner = new Scanner(wall);
				Queue<String> wallQueue = new ArrayBlockingQueue<String>(5);
				
				wallMsgScanner.useDelimiter(",");
				
				String message; 

				while (wallMsgScanner.hasNext()) {
					message = wallMsgScanner.next();
					wallQueue.add(message);
				} //end while
				
				String friendString = lineScanner.nextLine(); 
				
				String space = lineScanner.nextLine(); //the blank line at the end of record
				
				profile newProfile = new profile(name, email, pwd, year, status, eventBQueue, wallQueue, friendString, hashTable, eventQueue); //create profile object
				
				hashTable.insert(newProfile);				//insert into hash table
				
			}//end while
			
			Queue<Integer> keylist = hashTable.getKeylist();
			
			int first = keylist.remove();
			profile p = hashTable.a[first];
			p.readFriendString();
			keylist.add(first);
			
			while(first!=keylist.peek()) {
				int next = keylist.remove();
				p = hashTable.a[next];
				p.readFriendString();
				keylist.add(next);
			}//end while
		}//end try 
		
		catch(Exception ex0) { //in case file was not found
			System.out.println("\nInput file \"userdata.txt\" is formatted incorrectly or the file was not found.\nThe program will now exit.\n");
			System.exit(0);
		}
	}//end readFile()
	
	/**
	* writes program data to file
	*@param hash table
	*/
	public void writeFile(hash hashTable) {
		try {
			FileWriter fout = new FileWriter("userdata.txt");
			BufferedWriter out = new BufferedWriter(fout);
			Queue<Integer> keylist = hashTable.getKeylist();
			boolean lastItem=false; //so that it doesn't print 2 lines after the last item is written
			
			while(keylist.peek()!=null) {
				int key = keylist.remove();
				if(keylist.peek()==null) lastItem=true;
				profile prof = hashTable.a[key];
				out.write(prof.getNm()+"\n");				//write name
				out.write(prof.getEm()+"\n");				//write email address...
				out.write(prof.getPwd()+"\n");		
				out.write(prof.classYear+"\n");
				out.write(prof.status+"\n");
				
				Queue<Event> eventBQueue = prof.events;
				
				while (eventBQueue.peek()!=null) {			//write events
					Event e = eventBQueue.remove();
					String eventString = e.month+" "+e.day+" "+e.year+" "+e.hour+" "+e.minute+" "+e.name+",";
					out.write(eventString);
				}//end while
				
				out.newLine();
			
				Queue<String> wallQueue = prof.wall;
				
				while (wallQueue.peek()!=null) {		//write wall messages
					String message = wallQueue.remove();
					out.write(message+",");
				}//end while
				
				AVLTree friendTree = prof.friends;
				
				prof.friendString="";
				prof.traverseString(friendTree.root());
				String friendString=prof.friendString;
				
				out.newLine();					//write friends
				out.write(friendString);

				
				if(!lastItem) out.write("\n\n");
				else out.write("\nEND");
			} //end while
			
			out.close();
		} catch (IOException e) { //if there is an error, the file will not save correctly
			System.out.println("Output file \"userdata.txt\" did not save correctly.\nThe program will now exit.\n");
			System.exit(0);
		} //end catch
	}//end writeFile()
}//end home()

class welcome {
	/**
	* user interface for program
	*/
	public static void main(String[] args) {
		Scanner s = new Scanner(System.in);
		home homeObj = new home();
		homeObj.readFile();
		
		System.out.println("\nWelcome to our social networking program.");

		do {
			System.out.println("\nPlease select from the following:\n");
			System.out.println("Enter 1 if you are an existing user.");
			System.out.println("Enter 2 if you are a new user.");
			System.out.println("Enter 3 to quit.\n");
		
			String e = s.next();
			if(e.equals("1")) {
				System.out.println("\nEnter your e-mail address:\n");
				String em = s.next();
				profile p = homeObj.hashTable.find(em); //look up in hash table
				if(p==null) System.out.println("\nE-mail address was not found.");
				else {
					String pwd = p.getPwd();
					System.out.println("\nEnter your password:\n");
					String pass = s.next();
					if(pwd.equals(pass)) p.userint();
					else System.out.println("\nPassword is incorrect.");
				}//end else if

			}
			else if (e.equals("2")) {
				System.out.println("\nEnter your  full name:\n");
				s.nextLine(); //flush line buffer
				String nm = s.nextLine();

				System.out.println("\nEnter your e-mail address:\n");
				String em = s.next();

				System.out.println("\nEnter your class year:\n");
				String year = s.next();

				System.out.println("\nEnter a password for the account:\n");
				String pwd = s.next();	
				
				Queue<Event> eventBQueue = new ArrayBlockingQueue<Event>(200); //event and wall message queues
				Queue<String> wallQueue = new ArrayBlockingQueue<String>(5);
				String friendString="";

				profile newProfile = new profile(nm, em, pwd, year,"",eventBQueue, wallQueue, friendString, homeObj.hashTable, homeObj.eventQueue); //create profile object
				
				homeObj.hashTable.insert(newProfile); //insert into hash table
				newProfile.userint(); //call user control panel function
			}

			else if (e.equals("3")) { //exit
				System.out.println("\nGoodbye.\n");
				homeObj.writeFile(homeObj.hashTable); //write to file
				break;
			}
			else System.out.println("\nIncorrect entry.");
		} while(true);
	}//end main()
} //end welcome()
