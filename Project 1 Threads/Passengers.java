
public class Passengers extends Thread {

	int passengerID;
	int time;
	boolean isOnCar = false;
	
	
	public Passengers(int passengerID){
		this.passengerID = passengerID;
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		
		try {
			System.out.println(this.getName() + " currently sleeping");
			sleep(50000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			System.out.println(this.getName() + " I just had a fun time enjoying the ride.");
		}
	}

	public boolean isOnCar(){
		return isOnCar;
	}

	public void setOnCar(boolean flag){
		isOnCar = flag;
	}
	
}
