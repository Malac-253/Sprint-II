import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThrows;

import java.net.MalformedURLException;
import java.rmi.AlreadyBoundException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import org.junit.Test;


public class test
{

	
	@Test
	public void test1() throws NotBoundException, NotAllowedException
	{
			int host = 1097;
	        try {
	        	//STEP 1: Server Creation Management
		        	//as if to create registry
		            //Registry registry = LocateRegistry.getRegistry(host);
	        		BusinessPlanServer obj = new BusinessPlanServer();
	        		BusinessPlanInterface stub = (BusinessPlanInterface) UnicastRemoteObject.exportObject(obj, 0);
	        		Registry registry = LocateRegistry.createRegistry(host); // for testing use
		            registry.bind("BusinessPlanInterface", stub);
	            
		            //Test login
		            //BusinessPlanUserClient(BusinessPlanInterface s, String username, String password, String dep, boolean adm)
		            BusinessPlanUserClient client = new BusinessPlanUserClient(stub,"Master", "x108", "Support", true);
		            
		            //client.addPerson(String username, String password, String department, boolean isAdmin)
		            client.addPerson("TestB", "D4", "Math", true);
		            client.addPerson("TestC", "D41", "Math", false);
		            client.addPerson("TestD", "D42", "Math", false);
		            client.addPerson("TestE", "D43", "Math", false);
		            client.addPerson("Testf", "D411", "Math", false);
		            client.addPerson("Testg", "D422", "Math", false);
		            client.addPerson("Testh", "D433", "Math", false);
		            client.addPerson("Testj", "4D41", "Math", false);
		            client.addPerson("Testk", "D442", "Math", false);
		            client.addPerson("Testl", "D453", "Math", false);
					//client.currentPlan.setEditable(true);
					//Replace the plan
					//client.sendPlan();
					//Save changes. Note:this kind of access is just for testing purposes
					//client.server.encode();
					
					//PART 3: Run this multiple times to see that the encoding/decoding work
					
					//Part 4: Wait two minutes (or change the source code to make it faster) to see that the auto-save works
	            
	            
	           // registry = LocateRegistry.getRegistry(host);
	            //BusinessPlanInterface stub = (BusinessPlanInterface) registry.lookup("BusinessPlanInterface");
	            
	            
	            //String response = stub.sayHelloForTesting();
	            //String response2 = stub.giveHighFiveForTesting(5);
	            //String response3 = stub.reciveHighFiveForTesting(79);
	            
	            //System.out.println("response: " + response);
	            //System.out.println("response 2: " + response2);
	            //System.out.println("response 3: " + response3);
	            
	        }catch (RemoteException e){
				e.printStackTrace();
			}
			catch (Exception e) {
	            System.err.println("Client exception: " + e.toString());
	            e.printStackTrace();
	        }
		
	}
	
}

