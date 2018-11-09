import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Project # 3 for COSC-311
 * The objective of this project is to implement two queues, one a priority queue which is 
 * implemented using an Ordered Singly LinkedList, the second is a circular array.
 * 
 * this has two outputs:
 * 1)
 * The screen output for the current simulation is: the percentage of the time the
 * modems were busy, and the average wait time.
 * 
 * 2)an output file "report.txt"
 * The output in the file “report.txt” consists of the set of simulation parameters, the
 *	percentage of the time the modems were busy, the average wait time, and the number of
 *  users waiting in the waiting queue when the current simulation runs out.
 * 
 * 
 * @author Syed Muhammad Mehdi Hussain
 * EID: E01472939
 *
 */

public class RunSimulation {

static double clock = 0;

static int lenOfSim;
static double lambda;
static int modem;
static int con;	
static int waitQsize;

static int numOfCust;
static int modemBusy;
static double totalWait;

static PriorityEventQueue<Event> pq;
static RegularWaitQueue<Event> wq; 
static File file;

public static void main(String [] args) throws IOException
	{
System.out.println("Welcome to the simulation implented using Priority Queue");
	
	writeFile();			
		pq=new PriorityEventQueue<>();
		run();	
		trailer();

}
/**
 * this is the method which runs the simulation. Various methods are called inside
 * this to perform the simulation for each clock time
 *
 * @throws IOException
 */
	private static void  run() throws IOException {
	modemBusy=0;
	totalWait=0;
	numOfCust=0;
	
	parameter();
	
	int numOfModems=modem;
	
			
	dial_in();

	for (clock = 1; clock<=lenOfSim; clock++){

		System.out.println("\n****************************************************");
		System.out.println("\nProcessing for time:"+clock);
	
		checkNewArrival(clock);
	}
			
		System.out.println("\nPercentage of time the Modems were Busy  :"+percentModemBusy());
		
		System.out.println("\nTotal numbers of customers  :"+numOfCust);
		
		System.out.println("\nnum of customers in waiting queue:  "+wq.size());
		
		System.out.println("\nAverage wait time:  "+averageWaitTime());
		
		nextSimulation(numOfModems);
		

	}///run method

/*
 * this asks the user if they want to run the simulation to run again
 */
	private static void nextSimulation(int numOfModems) throws IOException {
	@SuppressWarnings("resource")
	
	Scanner keyInput = new Scanner(System.in);

	try {
		System.out.println("If you want to run another simulation enter Y:");
		String input = keyInput.nextLine();
		
		if (input.equalsIgnoreCase("Y")){
			append(numOfModems);
			Event.instanceCounter=1;
			run();
			
		}
		else {
			append(numOfModems);
			System.out.println("Simulation is complete: Summary in file report.txt");
		}
	} catch (InputMismatchException e) {
		System.out.println("Input not correct:");	
	nextSimulation(numOfModems);
	}
}//end run method

/**
 * this method takes the input parameter for the simulation by calling
 * a method for each input parameter
 * 
 */
public static void parameter(){
	lengthOfSim();
	
	averageDialin();
	
	averageConnectionTime();
	
	numberOfModems();
	
	sizeOfWaitQ();

}//end parameter

/**
 * this method takes the input of the length of simulation
 */
private static void lengthOfSim() {
		try {
			@SuppressWarnings("resource")
			Scanner keyIn = new Scanner(System.in);
			
			System.out.println("Enter length of simulation:");
			lenOfSim = keyIn.nextInt();
		} catch (Exception e) {
		System.out.println("Input not correct,enter correct input:");
			lengthOfSim();
		}
}//end length of simulation


/**
 * this takes the input for the average of each dialin attempts in for a particular time
 */
private static void averageDialin() {
	try {
		@SuppressWarnings("resource")
		Scanner keyIn = new Scanner(System.in);
		System.out.print("\nEnter the average time between dial in attempt,between 0 and 1: ");
		lambda = keyIn.nextDouble();
		
		if (lambda>=1 || lambda<=0 ) 
		{System.out.print("\nInput incorrect Enter the correct input,between 0 and 1: ");
		averageDialin();}
	
	} catch (Exception e) {
		System.out.println("Input not correct,enter correct input:");
		averageDialin();
	}
}// end averageDialin 

/*
 * this takes the input for average connection time for each event or customer
 */
private static void averageConnectionTime() {
	try {
		@SuppressWarnings("resource")
		Scanner keyIn = new Scanner(System.in);
		System.out.print("Enter the connection time: ");
		con = keyIn.nextInt();
	} catch (Exception e) {
		System.out.println("Input not correct,enter correct input:");
		averageConnectionTime();
	}
}// end averageConnectionTime

/*
 * this takes the input of the number of modems for a simulation
 */
private static void numberOfModems() {
	try {
		@SuppressWarnings("resource")
		Scanner keyIn = new Scanner(System.in);
		System.out.println("Enter number of modems:");
		modem = keyIn.nextInt();
	} catch (Exception e) {
		System.out.println("Input not correct,enter correct input:");
		numberOfModems();
	}
}//end numOfModems

/*
 *this takes the input of size of the waiting queue, if the user enters -1, then it becomes an
 *infinite queue for that simulation 
 */
private static void sizeOfWaitQ() {
	try {
		@SuppressWarnings("resource")
		Scanner keyIn = new Scanner(System.in);
		System.out.println("Enter size of waiting queue:");
		System.out.print("If you want the Waiting Queue to e infinite then enter -1");
		waitQsize = keyIn.nextInt();
	} catch (Exception e) {
		System.out.println("Input not correct,enter correct input:");
		sizeOfWaitQ();
	}

	if(waitQsize==-1){
		wq = new RegularWaitQueue<>(10);
	}
	else{
		wq = new RegularWaitQueue<>(waitQsize);
	}
}//end sizeOfWaitQ

/*
 * this method initializes the priority event queue with the events which are occuring at
 * every unit of time and inserts it into the EventQueue
 */
private static void dial_in() {
	
	for (clock=1; clock<=lenOfSim; clock++){
		 Poisson randomArrivalTime;
		 randomArrivalTime = new Poisson (1/lambda);
		 
		 int numOfCustForthisTime = randomArrivalTime.nextInt();
		 int userId; 
		 
		 for(userId=numOfCust; userId <numOfCustForthisTime+numOfCust; userId++){
			 Event dialin;
			 Eventtype e = Eventtype.dialin;
			 dialin = new Event(userId,e,clock);	 
				pq.offer(dialin);
				
		 } 
		 numOfCust = numOfCustForthisTime+numOfCust;
	}
}//dial_in




/* -‐‐ For each time unit of the simulation
 * -‐‐ While there are still events to be performed for this time unit
 *     handle them
 */
private static void checkNewArrival(double clock2) {

	int modemBusyForthisTime = 0;
	
	//-----   Remove the event from the event queue 	
while (pq.element().getTime()==clock2){
	
	Event arrivalCust = pq.remove();

	//---‐‐ If it is a hang-‐‐up event then free a modem 
//	System.out.println ( "\nArrived Customer"+ arrivalCust);
	if(arrivalCust.getType()==Eventtype.hangup){
		modem++;
	}

	/*
	 * Otherwise either add the event to the waiting queue if 
	 * queue is not full or print a message
	 */
	else{ 
		if (waitQsize!= -1 && wq.size()==waitQsize){
			System.out.println("\nWait Queue is full customer discarded");
		}
		else
			{
			wq.offer(arrivalCust);
			}
}
	/*
	 *Look for available modems and connect waiting customers 
	 *Add customer’s wait time to the total wait time
  	 *Determine the connection time for this customer (Poisson)
  	*/
if (modem>0){
				
			if (wq.size()>0){
				
			Event waitingCust =	wq.remove();
			
			double waitTime= clock - waitingCust.getTime();
			totalWait += waitTime;

			Poisson randomDepartureTime;
			Eventtype e = Eventtype.hangup;
			randomDepartureTime = new Poisson(con);
			double connectionTime =randomDepartureTime.nextDouble();
			double hanguptime = connectionTime +clock;
			waitingCust.setEvent(e, hanguptime);  
			pq.offer(waitingCust);	
			modem--;
			}			
		}	
	}
	if(modem==0)	modemBusyForthisTime++;
	modemBusy=modemBusy+modemBusyForthisTime;
}//end check new arrival 


/**
 * This looks if a file with the name report.txt exists, if it does, it deletes that 
 * and creates a new file report.txt and writes the header to label the columns of the file
 * @throws IOException
 */
public static void writeFile() throws IOException {
	
	
	FileWriter fw=null; 
	
	BufferedWriter writer = null;
	
	try
	{	
		file = new File("report.txt");	
		
		if(file.exists())
			file.delete();
			file.createNewFile();
			
			fw= new FileWriter(file,true);
		
			writer = new BufferedWriter(fw);
	    
	    writer.write(String.format("%10s%10s%10s%10s%10s%10s%10s%10s",
			    "LEN", "AVTIME", "AVCON", "NoMOD", "QUESIZE",
			    "PERCBUSY","AVWAIT","NoCUSWAIT"));
	    
	    writer.newLine();
	    writer.close();
	    
	}
	catch ( IOException e)
	{
		
	}
	
	finally
	{	    try
	    {
	        if ( writer!= null)
	        writer.close();
	    }
	    catch ( IOException e)
	    {
	    	}
		}

	}//end writeFile 



/**
 * this method writes the details of each simulation to the file report.txt
 * 
 * @param numOfModems
 */

private static void append(int numOfModems) {
	try{
    	

    	FileWriter appender = new FileWriter(file,true);
        BufferedWriter bw = new BufferedWriter(appender);
        
        bw.newLine();
    	bw.append(String.format("%10s%10s%10s%10s%10s%10s%10s%10s",
			    lenOfSim, lambda,con, numOfModems, waitQsize,percentModemBusy(),
			    averageWaitTime(),wq.size()));
    	
    	bw.newLine();
    	bw.newLine();
    	//Closing BufferedWriter Stream
    	bw.close();

      }catch(IOException ioe){
         System.out.println("Exception occurred:");
    	 ioe.printStackTrace();
       }
}//end append

/**
 * this writes the trailer of the table to explain the labels to the file
 */
private static void trailer() {
try{
    	

    	FileWriter trail = new FileWriter(file,true);
        BufferedWriter bw = new BufferedWriter(trail);
        
        bw.newLine();
        	
    	bw.append("LEN=Length Of Simulation AVTIME=Average time between dial-in attempts");
    	bw.newLine();    	bw.newLine();
    	bw.append("AVCON=Average connection time	NoMOD=Number of Modems	QUESIZE=Size of Waiting Queue");
    	bw.newLine();    	bw.newLine();
    	bw.append("PERCBUSY=Percentage of times Modems were busy	AVWAIT=Average Wait Time ");
    	bw.newLine();    	bw.newLine();
    	bw.append("NoCUSWAIT=Number of customers in the waiting Queue");
    	bw.newLine();    	bw.newLine();
    	bw.append("NOTE: if the size of waitQueue is -1 then it is an infinite queue");
    	
    	//Closing BufferedWriter Stream
    	
    	bw.close();

      }catch(IOException ioe){
         System.out.println("Exception occurred:");
    	 ioe.printStackTrace();
       }
}
/**
 * this computes the percentage of times the modem was busy for each simulation
 * @return
 */
public static String percentModemBusy(){
	
	double percBusy = (modemBusy/lenOfSim*100);
	DecimalFormat dec = new DecimalFormat("#0.00");
	return dec.format(percBusy);

}

/*
 * this computes the average wait time for each simulation
 */
public static String averageWaitTime(){
	double avgWaitTime= totalWait/numOfCust;
	DecimalFormat dec = new DecimalFormat("#0.00");
	return dec.format(avgWaitTime);
	}

}// end class 
