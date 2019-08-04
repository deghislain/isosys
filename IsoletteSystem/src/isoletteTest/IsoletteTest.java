package isoletteTest;

import isolette.IsoletteSystem;

public class IsoletteTest {
	
	
	
	
	public static void main(String[] args) {
		final byte lowDesiredTemp = 97;

		final byte upperDesiredTemp = 99;

		final byte lowAlarmTemp = 93;

		final byte upperAlarmTemp = 99;

		IsoletteSystem is = new IsoletteSystem();
		int i =1;
		
		while(true) {
			System.out.println("Round: " +i++);
			is.executeRound(true, lowDesiredTemp, upperDesiredTemp, lowAlarmTemp, upperAlarmTemp);
		}
		
	}
}
