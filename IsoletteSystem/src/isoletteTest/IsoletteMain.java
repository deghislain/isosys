package isoletteTest;

import java.util.concurrent.TimeUnit;

import isolette.IsoletteSystem;

public class IsoletteMain {
	
	
	
	
	public static void main(String[] args) {
		final byte lowDesiredTemp = 97;

		final byte upperDesiredTemp = 99;

		final byte lowAlarmTemp = 96;

		final byte upperAlarmTemp = 100;

		IsoletteSystem is = new IsoletteSystem();
		int i =1;
		
		while(true) {
			System.out.println("Round: " +i++);
			is.executeRound(true, lowDesiredTemp, upperDesiredTemp, lowAlarmTemp, upperAlarmTemp);
			try {
				TimeUnit.MILLISECONDS.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			 
		}
		
	}
}
