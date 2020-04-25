import java.rmi.registry.Registry;
import java.rmi.registry.LocateRegistry;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;



//Server

/**
 * 
 * @author malachi.beerram
 * 
 * RML template: https://github.com/Malac-253/RMIDemo
 * 
 * This is the Business Plan Server for Malachi.Beerram of 360 
 * Keep in mind Client can be away from this Server
 *
 */

/**
 * How to run RMI
 * 1:Find where the RMIregistry.exe was on the system
 * 2:Run RMIregistry.exe from directory that your class files are in with the absolute path in gitBash
 * 	-Ex:  /c/Program\ Files\ \(x86\)/Java/jre1.8.0_251/bin/rmiregistry.exe
 * 3:Run server
 * 4:Run client
 * 
 */

public class BusinessPlanServer implements BusinessPlanInterface {
	//Variables
		//Testing Variables
			public static int AccessCounter = 0;
			protected ArrayList<Person> users = new ArrayList<Person>();
			private ArrayList<BusinessPlan> plans = new ArrayList<BusinessPlan>();
	
	//Constructor
			public BusinessPlanServer() {
				//adding master
				users.add(new Person("Master", "x108", "Support", true));
				//System.err.println(users.get(0).username);
			}
			
			
	
	//Methods
		//Testing Methods
			public String sayHelloForTesting() {
		        
				return "Hello, world!";
		    }
		    
			public String giveHighFiveForTesting(int test) {
		    	AccessCounterDisplay();
		    	return "Hello, number :" + (test * 5) ;
		    }   
		    
			public String reciveHighFiveForTesting(int test) {
		    	AccessCounterDisplay();
		    	return "Hello, number :" + (test * AccessCounter) ;
		    } 
		//Display Methods   
			public void AccessCounterDisplay() {
		    	AccessCounter = AccessCounter + 1;
		    	System.err.println(" ");
		    	System.err.println("BusinessPlanServer Usage Access Counter: " + AccessCounter);
		    	System.err.println("BusinessPlanServer --------------------------------- ");
		    }
			public void AccessByDisplay(Person p) {
				System.err.println("---------- BY: " + p.username + " - ID:" + p.userID);	
		    }
			public void AccessOnDisplay(BusinessPlan bp) {
				System.err.println("---------- ON: " + bp.department + " - Year:" + bp.year);	
		    }
			public void AccessActionDisplay(String act) {
				System.err.println("---------- ACTION: " + act);	
		    }
			public void AccessServerActionDisplay(String act) {
				System.err.println("---------- SERVER ACTION: " + act);	
		    }
			public void AccessHasPersonDisplay(Person p, String act) {
				AccessCounterDisplay();
				AccessByDisplay(p);
				AccessActionDisplay(act);
		    }
			public void AccessHasBPDisplay(BusinessPlan bp, String act) {
				AccessCounterDisplay();
				AccessOnDisplay(bp);
				AccessActionDisplay(act);
		    }
		//Methods
			public void addUser(Person p) throws RemoteException
			{
				for(Person e: users)
				{
					//Don't want to add duplicates
					if((e.getUsername().equals(p.getUsername())) && (e.getPassword().equals(p.getPassword()))){
						return;}
					
					while(e.getUserID() == p.getUserID()) {
						p.setUserID((long)(Math.random() * 899999999) + 100000000);
					}
				}
				
				users.add(p);
				
				AccessHasPersonDisplay(p, "public void addUser(Person p) throws RemoteException");
			}
			
			public void addBusinessPlan(BusinessPlan bp) throws RemoteException, NotAllowedException
			{
				for(int i = 0; i < plans.size(); i++)
				{
					BusinessPlan current = plans.get(i);
					
					//This plan already exists on the server (i.e. it is being replaced)
					if(current.getDepartment().equals(bp.getDepartment()) && current.year == bp.year)
					{
						//We don't want a plan to be replaced if it is not editable
						if(!current.isEditable)
							throw new NotAllowedException("This plan may not be edited");
						plans.set(i, bp);
						return;
					}
				}
				//This plan does not already exist on the server. Add it.
				plans.add(bp);
				
				AccessHasBPDisplay(bp,"public void addBusinessPlan(BusinessPlan bp) throws RemoteException, NotAllowedException");
				
			}

			//Compares a username/password pair to the users list. Returns the cooresponding person if one exists
			public Person validateLogin(String username, String password) throws NothingFoundException, RemoteException
			{
				
					for(Person p: users)
					{
						if(p.getUsername().equals(username) && p.getPassword().equals(password))
							{
							AccessHasPersonDisplay(p, "public Person validateLogin(String username, String password) throws NothingFoundException, RemoteException");
							return p;
							}
					}
					AccessCounterDisplay();
					AccessActionDisplay("public Person validateLogin(String username, String password) throws NothingFoundException, RemoteException");
					throw new NothingFoundException("There is no user with these credentials");
				
			}
			
			//Finds a plan, given a person and a year. The person parameter is used for its department attribute
			public BusinessPlan findPlan(Person p, int year) throws RemoteException, NothingFoundException
			{
				String department = p.getDepartment();
				
				for(BusinessPlan bp: plans)
				{
					if(bp.getDepartment().equals(department) && bp.getYear() == year)
						{
						AccessHasBPDisplay(bp,"public void addBusinessPlan(BusinessPlan bp) throws RemoteException, NotAllowedException");
						return bp;
						}
				}
				AccessCounterDisplay();
				AccessActionDisplay("public BusinessPlan findPlan(Person p, int year) throws RemoteException, NothingFoundException");
				throw new NothingFoundException("There is no buisness plan with these details");
			}
			
			//Saves the users and plans lists to the disk
			public void encode() throws RemoteException
			{
				
				for(int i = 0; i < plans.size(); i++)
				{
					BusinessPlan current = plans.get(i);
					String fileName = "plan" + i + ".xml";
					current.encodeToXML(fileName);
				}
				
				for(int i = 0; i < users.size(); i++)
				{
					Person current = users.get(i);
					String fileName = "user" + i + ".xml";
					current.encodeToXML(fileName);
				}
				AccessServerActionDisplay("public void encode() throws RemoteException");
			}
			
			//Reads the BusinessPlan xml objects from the disk
			public ArrayList<BusinessPlan> decodePlans() throws RemoteException
			{
				ArrayList<BusinessPlan> plans = new ArrayList<BusinessPlan>();
				int i = 0;
				AccessServerActionDisplay("ArrayList<BusinessPlan> decodePlans() throws RemoteException");
				while(true)
				{
					String fileName = "plan" + i + ".xml";
					try
					{
						BusinessPlan bp = BusinessPlan.decodeFromXML(fileName);
						plans.add(bp);
					}
					catch(Exception e)
					{
						return plans;
					}
					
					i++;
				}
				
			}
			
			//Reads the Person xml objects from the disk
			public ArrayList<Person> decodeUsers() throws RemoteException
			{
				ArrayList<Person> users = new ArrayList<Person>();
				AccessServerActionDisplay("public ArrayList<Person> decodeUsers() throws RemoteException");
				int i = 0;
				while(true)
				{
					String fileName = "user" + i + ".xml";
					try
					{
						Person usr = Person.decodePersonFromXML(fileName);
						users.add(usr);
					}
					catch(Exception e)
					{
						return users;
					}
					
					i++;
				}
			}
			      
    //Main - static void main(String args[])
    public static void main(String args[]) {
        try {
        	BusinessPlanServer obj = new BusinessPlanServer();
        	BusinessPlanInterface stub = (BusinessPlanInterface) UnicastRemoteObject.exportObject(obj, 0);
        	
        	// Bind the remote object's stub in the registry
        	Registry registry = LocateRegistry.createRegistry(1098); //for testing use
            //Registry registry = LocateRegistry.getRegistry();
            //name
            registry.bind("BusinessPlanInterface", stub);
        	
        	//BusinessPlanServer service = new BusinessPlanServer();
			//Naming.rebind("BusinessPlanInterface", service);
			
            System.err.println("BusinessPlanServer ready");
            System.err.println("BusinessPlanServer AccessCounter: " + AccessCounter);
            
        } catch (Exception e) {
            System.err.println("BusinessPlanServer Server exception: " + e.toString());
            e.printStackTrace();
        }
    }
	
}
