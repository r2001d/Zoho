import java.awt.datatransfer.SystemFlavorMap;
import java.nio.file.Path;
import java.util.*;
class Passenger
{
     //Declaration of variables
	String name;
	int age;
	int num;
	String dest;
	//Constructor
	public Passenger(String name, int age, int num,String dest) {
		super();
		this.name = name;
		this.age = age;
		this.num = num ;
		this.dest=dest;
	}
      //Methods
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}
	public String getDest() {
		return dest;
	}
	public void setDest(String dest) {
		this.dest = dest;
	}
	 	
}
//To find errors
class Error
{


	public void numError() {
        System.out.println("Please enter number!");		
		
	}

	public void alphaError() {
        System.out.println("Please enter alphabet!");				
	}

	public void unRecognizedError() {
		System.out.println("Command not found");
	}

	public void cmdError() {
		System.out.println("Cannot be executed now");
	}

	public void reinitError() {
        System.out.println("Type exit and re-run");
		
	}

	public void initError() {
		System.out.println("Cannot perform operation without initializing");
	}

	public void pathNotFound() {
		  System.out.println("Cannot find connections");	
	}
	
	public void uncheckedError()
	{
		System.out.println("Unknown error!");
	}
	
}
//Class Hyper is extended using Class Error to find the input errors
public class Hyper extends Error{
      // These are declared private so that it can be accessed only within the class
	private static int node;
	private static LinkedList<Integer>adj[];
	private static List<Passenger> passengers=new LinkedList<Passenger>();
	private static boolean Visited[]; 
	private static Hyper booking;
	private  ArrayList<Integer> shortestPath=new ArrayList<Integer>();
	private static List<Passenger>printQList=new LinkedList<Passenger>();
	private static Error error;
	
	public static void main(String args[])
	{
		Scanner sc=new Scanner(System.in);
		booking=new Hyper();
		error=new Error();
            // Initialization
		int num=0;
		int a=1;
		while(a==1)
		{
		String init=sc.nextLine();
		String initArray[]=init.split(" ");//Splitting the elements of String Array
		String sourceString;
		int source=0;
            // Checking whether the given input matches the word "INIT"
		if(initArray[0].equals("INIT"))
		{
			if(!initArray[1].matches("[0-9]"))
			{
				error.numError();
			}
			else if(!initArray[2].matches("[A-Z]"))
			{
			    error.alphaError();	
			}
			else
			{
		    booking.getConnection(initArray[0],initArray[1]);//calls the getConnection method
		    booking.setConnection(sc);//setConnection method
	     	    sourceString = initArray[2];//[A-Z]
	       source=(int)sourceString.charAt(0)-65;//Converting String to int
			}
		}
		else if(initArray[0].equals("ADD_PASSENGER"))
		{
		 int count=Integer.parseInt(initArray[1]);
		 
		 booking.mainAdd(num,count,sc);
		 num+=1;
		}
		else if(initArray[0].equals("START_POD"))
		{
		  int n=Integer.parseInt(initArray[1]);
		  Visited=new boolean[node];
		  Arrays.fill(Visited, false);
		  booking.startPod(n, source);
		}
		else if(initArray[0].equals("PRINT_Q"))
		{
			booking.remainingInQueue();
		}
		else if(initArray[0].equals("EXIT"))
		{
			System.out.println("Thank you");
			a=0;
		}
		else 
		{
			error.unRecognizedError();
		}
		}
	}
	
	
	
	public LinkedList<Integer>[] getConnection(String cmd,String nodes)
	{
		 //To check whether the input string contains "INIT" or not
		 if(cmd.equals("PRINT_Q")||cmd.equals("START_POD")||cmd.equals("ADD_PASSENGER"))
		 {
			 error.cmdError();
			 return null;
		 }

		   if(passengers.size()>0)
		   {
			   error.reinitError();
			   return null;
		   }
               //String converted to int
			node=Integer.parseInt(nodes);
			adj=new LinkedList[node];
			for(int i=0;i<node;i++)
			{
				adj[i]=new LinkedList<Integer>();
			}
			return adj;
		
	}
	
	
	
	//Appending values in the queue
	public void setConnection(Scanner sc)
	{
		if(passengers.size()<=0)
		{
		 for(int i=0;i<node;i++)
		 {
			 String coord=sc.nextLine();
			 String coordArray[]=coord.split(" ");
			 String s1=coordArray[0];
			 String s2=coordArray[1];
			 if(!s1.matches("[A-Z]") || !s2.matches("[A-Z]"))
			 {
				 error.alphaError();
				 break;
			 }
			 else
			 {
			 int u=(int)s1.charAt(0)-65;
			 int v=(int)s2.charAt(0)-65;
			 {
			 adj[u].add(v);
			 }
			 }
		  }
		}

	}
	
	
	
	
	//Giving priority according to age[from oldest to youngest]
	public void addPassenger(Passenger passenger)
	{
		
		       passengers.add(passenger);
		       printQList.add(passenger);
				if(printQList.size()==0)
				{
				  error.initError();	
				}

		       Passenger temp;
		       if(passengers.size()!=0)
		       {
		       for (int i = 0; i < passengers.size(); i++) {     
		            for (int j = i+1; j <passengers.size(); j++) {     
		               if(passengers.get(i).getAge() < passengers.get(j).getAge()) {    
		                   temp = passengers.get(i);    
		                   passengers.set(i,passengers.get(j));    
		                   passengers.set(j, temp);
		               }     
		            }     
		        }
		       }
		       else
		       {
		    	   passengers.add(passenger);
		       }
		
	}

	
	
	
	//Adding passengers according to requirements
	public void mainAdd(int id,int count,Scanner sc)
	{

		for(int i=0;i<count;i++)
		 {
			 String passenger=sc.nextLine();
			 String passengerArray[]=passenger.split(" ");
			 if(passengerArray[0].equals("PRINT_Q")||passengerArray[0].equals("START_POD")||passengerArray[0].equals("ADD_PASSENGER")||passengerArray[0].equals("INIT"))
			 {
				 error.initError();
				 break;
			 }
			 else
			 {
			 int num=id;
			 String name=passengerArray[0];
			 int age=Integer.parseInt(passengerArray[1]);
			 String dest=passengerArray[2];
			 Passenger p=new Passenger(name, age, num,dest);
			 booking.addPassenger(p);
			 }
		 }
	}
	
	
	
	//To check whether the right passenger is present or not to start the pod
	public void startPod(int n, int source)
	{
		
		if(printQList.size()==0)
		{
			error.initError();
		}
		else if(passengers.size()==0)
		{
			error.initError();
		}
		else
		{
		List<Passenger>ridePassenger=new LinkedList<Passenger>();
		for(int i=0;i<n;i++)
		{
			ridePassenger.add(passengers.get(i));
		}
		for(int i=n-1;i>=0;i--)
		{
			passengers.remove(i);
		}
		for(int j=0;j<n;j++)
		{
			Passenger passenger=ridePassenger.get(j);
			for(int k=0;k<printQList.size();k++)
			{
				if(passenger.getNum()==printQList.get(k).getNum())
				{
					printQList.remove(k);
				}
			}
			
		}
		booking.buildPath(ridePassenger,source);
		}
	}
	
	
	
	
	//To connect and build the path
	public void buildPath(List<Passenger> ridePassenger,int source)
	{
		for(int i=0;i<ridePassenger.size();i++)
		{
		String dest=ridePassenger.get(i).getDest();
		int desti=(int)dest.charAt(0)-65;
		ArrayList<Integer> pathList = new ArrayList<>(); 
		pathList.add(source);
		booking.startPathFinding(source, desti, pathList);
		String path="";
            //To check the shortest path available to reach the destination
		for(int j=0;j<shortestPath.size();j++)
		{
			int charac=shortestPath.get(j)+65;
			path=path+String.valueOf((char)(charac))+" ";
		}
		if(!path.contains(dest) || path.equals(""))	
		{
			error.pathNotFound();
			break;
		}
		else
		{
		System.out.println(ridePassenger.get(i).getName()+" "+path);
		}
		
		}
	}
	
	
	
	
	
	//To find the feasible path to reach the destination with minimum interconnections
	public void startPathFinding(int source,int dest,ArrayList<Integer> PathList)
	{
		if(source==dest) {
			int min=Integer.MAX_VALUE;
			if(PathList.size()<min && PathList.size()>1)
			{
		     min=PathList.size();		
			 shortestPath=(ArrayList<Integer>) PathList.clone();	
			}
			return;
		}
		Visited[source]=true;
		//If the path is being used then the other passenger cannot ride in the same path
		for(Integer i:adj[source])
		{   
		if(!Visited[i])
			{
				PathList.add(i);
				startPathFinding(i, dest, PathList);
				PathList.remove(i);
			}

		}
		Visited[source]=false;
	}
	
	//To know the remaining number of passengers waiting for the ride
	public void remainingInQueue()
	{
		if(printQList.size()==0)
		{
			error.initError();
		}
		else
		{
		System.out.println(printQList.size());
	   for(int i=0;i<printQList.size();i++) {	
		   System.out.println(printQList.get(i).getName()+" "+printQList.get(i).getAge());
	    }
		}
	}
	
	
	
	
	
}