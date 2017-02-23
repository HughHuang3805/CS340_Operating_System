import java.util.Enumeration;
import java.util.Random;
import java.util.Vector;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Consumer;

import javax.swing.text.html.HTMLDocument.Iterator;


public class Car extends Thread {

	public static int capacity = 0;
	public int carID;
	boolean isRunning = true;
	boolean started = false;
	int counter = 0;
	//initialize passenger threads in an array
	static Vector<Passengers> passengerThreads = new Vector<Passengers>(11);
	static Vector<Passengers> passengerOnCar = new Vector<Passengers>(4);
	
	public Car(int carID){
		this.carID = carID;
	}

	public static void initializePassenger(){
		//add passenger threads to the vector
		for(int i = 0; i < 11; i++){
			passengerThreads.add(new Passengers(i));
			//passengerThreads.elementAt(i).start();
		}
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		while(counter < 20){
			try {
				emptyCar();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			putPassengers();
			System.out.println("This is car " + this.getID() + ". The car is now full, going on a ride." + this.getCapacity());
			
			/*for(Passengers passengers : passengerOnCar){
				try {
					passengers.join();
					//System.out.println("hi");
				} catch(IllegalThreadStateException | InterruptedException ise){
					//System.out.println("Already started");
				}
					
			}*/
			
			//start each passenger thread on the car only if it is not started
			for(Passengers passengers : passengerOnCar){
				try {
					passengers.start();
					//System.out.println("hi");
				} catch(IllegalThreadStateException ise){
					//System.out.println("Already started");
				}
					
			}

			Consumer<Passengers> everyone2 = (Passengers p) -> p.yield();;
			passengerOnCar.forEach(everyone2);
			
			Consumer<Passengers> everyone3 = (Passengers p) -> p.interrupt();
			passengerOnCar.forEach(everyone3);
			
			counter++;
		}
	}

	@SuppressWarnings("static-access")
	public synchronized void emptyCar() throws InterruptedException{
		//need to call interrupt
		capacity = 0;
		for(int i = 0; i < passengerOnCar.size(); i++){
			//System.out.println(passengerOnCar.size());
			passengerOnCar.elementAt(i).currentThread().setPriority(ThreadLocalRandom.current().nextInt(1, 10));//give a random priority
			//passengerOnCar.elementAt(i).join();
			System.out.println(passengerOnCar.elementAt(0).getName() + " is waiting to get off the car.");

			passengerThreads.add(passengerOnCar.remove(0)); // done passengers
			//passengerThreads.lastElement().join();
		}

	}

	public static synchronized void putPassengers(){
		while(capacity < 4){
			//System.out.println(passengerOnCar.size());

			passengerOnCar.add(passengerThreads.remove(0)); //add passengers in the queue to the car
			capacity++;
		}
		

	}
	
	public int increaseCapacity(){
		capacity++;
		return capacity;
	}

	public int decreaseCapacity(){
		capacity--;
		return capacity;
	}

	public int getCapacity(){
		return capacity;
	}


	public boolean isEmpty(){
		return capacity == 0;
	}

	public boolean isFull(){
		return capacity <= 4;
	}

	public int getID(){
		return carID;
	}
}
