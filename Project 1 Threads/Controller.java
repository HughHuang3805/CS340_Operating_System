import java.util.Vector;


public class Controller {

	public Controller(){
		//initialize car threads in an array
		int counter = 0;
		Vector<Car> carThreads = new Vector<Car>(3);
		for(int i = 0; i < 3; i++){
			carThreads.add(new Car(i));
		}
		Car.initializePassenger();

		try {
			while(counter < 3){
				for(int i = 0; i <3; i++){
					carThreads.elementAt(0).start();
					carThreads.elementAt(0).join();

					carThreads.elementAt(1).start();
					carThreads.elementAt(1).join();

					carThreads.elementAt(2).start();
					carThreads.elementAt(2).join();
					if(i == 3){
						System.out.println("All rides are done.");
					}
				}
				counter++;
				
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalThreadStateException ise){

		}


	}

}
