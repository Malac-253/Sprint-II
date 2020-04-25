import java.io.Serializable;
import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.lang.Math; 

public class Person implements Serializable
{
	//Variables
			//Serializable
			private static final long serialVersionUID = 8508528081943699422L;
			//Account Variables	
			public String username = "username";
			public long userID = ((long)(Math.random() * 899999999) + 100000000);
			public String password = "password";
			public String department = "department";
			public boolean isAdmin = false;	
			//BusinessPlan Variables	
			public BusinessPlan currBusinessPlan = null;
	
	//Constructor
		public Person(){}
		public Person(String usr, String pw, String dep, boolean adm)
		{
			this.username = usr;
			this.password = pw;
			this.department = dep;
			this.isAdmin = adm;
		}
	
	//Methods	
		//XML Stuff so that it can save
			// encode object to XML file
				public void encodeToXML(String fileName)
				{
					final String SERIALIZED_FILE_NAME = fileName;
					XMLEncoder encoder = null;
					try
					{
						encoder = new XMLEncoder(new BufferedOutputStream(new FileOutputStream(SERIALIZED_FILE_NAME)));
					} 
					catch (NullPointerException | FileNotFoundException fileNotFound)
					{
						System.out.println("ERROR: While Creating or Opening the File");
					}
					encoder.writeObject(this);
					encoder.close();
				}
			//decode the Person from a XML file
				public static Person decodePersonFromXML(String fileName)
				{
			
					final String SERIALIZED_FILE_NAME = fileName;
					XMLDecoder decoder = null;
					try
					{
						decoder = new XMLDecoder(new BufferedInputStream(new FileInputStream(SERIALIZED_FILE_NAME)));
					} 
					catch (NullPointerException | FileNotFoundException e)
					{
			
					}
					Person person = (Person) decoder.readObject();
					return person;
				}
			// decode the business plan from a XML file
				public static BusinessPlan decodeBusinessPlanFromXML(String fileName)
				{
		
					final String SERIALIZED_FILE_NAME = fileName;
					XMLDecoder decoder = null;
					try
					{
						decoder = new XMLDecoder(new BufferedInputStream(new FileInputStream(SERIALIZED_FILE_NAME)));
					} 
					catch (NullPointerException | FileNotFoundException e)
					{
		
					}
					BusinessPlan plan = (BusinessPlan) decoder.readObject();
					return plan;
				}
		
				
		//Getter and Setter
			public String getUsername()
			{
				return username;
			}
		
			public void setUsername(String username)
			{
				this.username = username;
			}
		
			public String getPassword()
			{
				return password;
			}
		
			public void setPassword(String password)
			{
				this.password = password;
			}
		
			public String getDepartment()
			{
				return department;
			}
		
			public long getUserID() {
				return userID;
			}
			public void setUserID(long userID) {
				this.userID = userID;
			}
			public BusinessPlan getCurrBusinessPlan() {
				return currBusinessPlan;
			}
			public void setCurrBusinessPlan(BusinessPlan currBusinessPlan) {
				this.currBusinessPlan = currBusinessPlan;
			}
			public void setDepartment(String department)
			{
				this.department = department;
			}
		
			public boolean isAdmin()
			{
				return isAdmin;
			}
		
			public void setAdmin(boolean isAdmin)
	{
		this.isAdmin = isAdmin;
	}
}
