import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

//Client

/**
* 
* @author malachi.beerram
* 
* RML template: https://github.com/Malac-253/RMIDemo
* 
* This is the Business Plan User Client for Malachi.Beerram of 360 
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

public class BusinessPlanUserClient {
	//Variables
		//Testing Variables
			//null
		//Variables
			public BusinessPlanInterface server;
			public Person loggedIn;
			public BusinessPlan currentPlan;
			
	//Constructor
		public BusinessPlanUserClient() {}
		
		public BusinessPlanUserClient(BusinessPlanInterface s, String username, String password, String dep, boolean adm)
		{
			server = s;
			loggedIn = new Person(username, password, dep, adm);
			currentPlan = new BusinessPlan();
			askForLogin(username, password);
			
			//Make sure nothing gets overwritten prematurely
			try
			{
				Thread.sleep(5000);
			}
			catch (InterruptedException e1)
			{
				e1.printStackTrace();
			}
			//Tells the server to save data to the disk every two minutes
			ScheduledExecutorService exec = Executors.newSingleThreadScheduledExecutor();
			exec.scheduleAtFixedRate(new Runnable()
			{
			  @Override
			  public void run()
			  {
				
				try
				{
					server.encode();
				}
				catch (RemoteException e)
				{
					e.printStackTrace();
				}
				
			  }
			}, 0, 1, TimeUnit.SECONDS);
		}
		
	
		
	//Methods
		//Attempt to log in
		public void askForLogin(String username, String password)
		{
			try
			{
				loggedIn = server.validateLogin(username, password);
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}
		
		//Add a user to the server's list. Only works if an admin is logged in
		public void addPerson(String username, String password, String department, boolean isAdmin) throws NotAllowedException
		{
			if(!loggedIn.isAdmin)
				throw new NotAllowedException("The current user may not perform this action");
			
			Person p = new Person(username, password, department, isAdmin);
			try
			{
				server.addUser(p);
				//System.out.println("server.addUser(p); good ");
			}
			catch (RemoteException e)
			{
				e.printStackTrace();
			}	
		}
		
		//Sends a plan to be added to the server's list. Will not work if trying to overwrite a non-editable plan
		public void sendPlan() throws NotAllowedException
		{	
			try
			{
				server.addBusinessPlan(currentPlan);
			}
			catch (RemoteException e)
			{
				e.printStackTrace();
			}
		}
		
		//Gives the user a blank plan to work with
		public void blankPlan()
		{
			currentPlan = new BusinessPlan("", 0, true);
		}
		
		//Change whether the current plan can be edited. Only available to administrators.
		public void changeEditable(boolean val) throws NotAllowedException
		{
			if(!loggedIn.isAdmin)
				throw new NotAllowedException("The current user may not perform this action");
			
			currentPlan.setEditable(val);
		}
		
		//Asks the server for the BusinessPlan from the user's department and a given year
		public void askForBP(int year)
		{
			try
			{
				BusinessPlan plan = server.findPlan(loggedIn, year);
				currentPlan = plan;
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}
		
		
	//Main - static void main(String args[])	
		public static void main(String[] args) {
		//public static void runner() {
			String host = null;
	    	host = (args.length < 1) ? null : args[0];
	        try {
	            Registry registry = LocateRegistry.getRegistry(1098);
	            //Registry registry = LocateRegistry.createRegistry(1099); for testing use
	            //start server
	            BusinessPlanInterface stub = (BusinessPlanInterface) registry.lookup("BusinessPlanInterface");
	            
	            
	            String response = stub.sayHelloForTesting();
	            String response2 = stub.giveHighFiveForTesting(2);
	            String response3 = stub.reciveHighFiveForTesting(5);
	            System.out.println("response: " + response);
	            System.out.println("response 2: " + response2);
	            System.out.println("response 3: " + response3);
	            
	        } catch (Exception e) {
	            System.err.println("Client exception: " + e.toString());
	            e.printStackTrace();
	        }
	    }
		
}
