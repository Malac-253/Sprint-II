import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

//Interface

/**
 * 
 * @author malachi.beerram
 * 
 * RML template: https://github.com/Malac-253/RMIDemo
 * 
 * This is the Business Plan Interface for Malachi.Beerram of 360 
 * Keep in mind Client can be away from this Server
 *
 */ 

public interface BusinessPlanInterface extends Remote {
	//Methods
		//Testing Methods
			public String sayHelloForTesting() throws RemoteException;
			
			public String giveHighFiveForTesting(int test) throws RemoteException;
			
			public String reciveHighFiveForTesting(int test) throws RemoteException;
			
		// Methods
		    public void encode() throws RemoteException;
			
			public ArrayList<BusinessPlan> decodePlans() throws RemoteException;
			
			public ArrayList<Person> decodeUsers() throws RemoteException;
			
			public void addUser(Person p) throws RemoteException;
			
			public void addBusinessPlan(BusinessPlan bp) throws RemoteException, NotAllowedException;
				
			public Person validateLogin(String username, String password) throws NothingFoundException, RemoteException;
			
			public BusinessPlan findPlan(Person p, int year) throws RemoteException, NothingFoundException;
}


