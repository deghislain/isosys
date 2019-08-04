package isolette;


public class OperatorInterface1 implements IOperatorInterface1 {

	/**
	 * Indicates isolette status
	 */
	private EStatus isoletteStatus = EStatus.OFF;

	/**
	 * Indicates thermostat status
	 */
	private EStatus thermostatStatus = EStatus.OFF;

	/**
	 * Indicates if the low desired temperature is valid or not
	 */
	private ETempStatus lowDesirableTempStatus = ETempStatus.INVALID;

	/**
	 * Indicates if the upper desired temperature is valid or not
	 */
	private ETempStatus upperDesirableTempStatus = ETempStatus.INVALID;

	/**
	 * Indicates if the low alarm temperature is valid or not
	 */
	private ETempStatus lowAlarmTempStatus = ETempStatus.INVALID;

	/**
	 * Indicates if the upper alarm temperature is valid or not
	 */
	private ETempStatus upperAlarmTempStatus = ETempStatus.INVALID;

	/**
	 * Indicates if the regulator is runing or not
	 */
	private EStatus regulatorStatus = EStatus.OFF;

	/**
	 * Tell the value of the current temperature
	 */
	private byte currentTemperature = 68;

	/**
	 * Indicates the current low desired temperature
	 */
	private byte LDTemp = 68;

	/**
	 * Indicates the current upper desired temperature
	 */
	private byte UDTemp = 68;

	/**
	 * Indicates the current low alarm temperature
	 */
	private byte LATemp = 68;

	/**
	 * Indicates the current upper alarm temperature
	 */
	private byte UATemp = 68;

	/**
	 * Indicates if the monitor is runing or not
	 */
	private EStatus monitorStatus = EStatus.OFF;
	
	/**
	 * Turn the thermostat on/off
	 */
	private boolean thermosCom = false;


	/**
	 * This method start the isolette
	 */
	public void startIsolette(boolean onOff) {
		turnOn(onOff);
	}
	
	private void turnOn(boolean onOff) {
		isoletteStatus = EStatus.ON;
		thermosCom = true;
	}

	/**
	 * This method stop the isolette
	 */
	public void stopIsolette(boolean onOff) {
		turnOff(onOff);
	}

	private void turnOff(boolean onOff) {
		isoletteStatus = EStatus.OFF;
		thermosCom = false;
	}
	/**
	 * This method set the desired temperature
	 */
	public void setDesiredTemp(byte LDTempIn, byte UDTempIn) {
			updateDesiredTemp(LDTempIn, UDTempIn);
	}

	/**
	 * This method keep the low and upper desired temperature updated
	 */
	private void updateDesiredTemp(byte LDTempIn, byte UDTempIn) {
		
		if(MINLDTEMP <= LDTempIn && LDTempIn <= MAXLDTEMP) {
			lowDesirableTempStatus = ETempStatus.VALID;
		}else {
			lowDesirableTempStatus = ETempStatus.INVALID;
		}
		
		if(MINUDTEMP <= LDTempIn && LDTempIn <= MAXUDTEMP) {
			upperDesirableTempStatus = ETempStatus.INVALID;
		}else {
			upperDesirableTempStatus = ETempStatus.INVALID;
		}
		
		LDTemp = LDTempIn;
		UDTemp = UDTempIn;

	}

	/**
	 * This method set the alarm temperature
	 */
	public void setAlarmTemp(byte LATempIn, byte UATempIn) {
		updateAlarmTemp(LATempIn, UATempIn);
	}

	/**
	 * This method keep the low and upper alarm temperature updated
	 */
	private void updateAlarmTemp(byte LATempIn, byte UATempIn) {
		if(MINLATEMP <= LATempIn && LATempIn <= MAXLATEMP) {
			lowAlarmTempStatus = ETempStatus.VALID;
		}else {
			lowAlarmTempStatus = ETempStatus.INVALID;
		}
		
		if(MINUATEMP <= UATempIn && UATempIn <= MAXUATEMP) {
			upperAlarmTempStatus = ETempStatus.INVALID;
		}else {
			upperAlarmTempStatus = ETempStatus.INVALID;
		}
		
		LATemp = LATempIn;
		UATemp = UATempIn;
	}
	
	/**
	 * This method keep the thermostat status updated
	 */
	private void updateThermostatStatus() {
		if (regulatorStatus == EStatus.ON && monitorStatus == EStatus.ON) {
			
			thermostatStatus = EStatus.NORMAL;
		}
	}

	/**
	 * This method displays the isolette overall info 
	 */
	public void displaySystemInfo() {
		display();
	}
	
private void display(){
		
	}

public void updateOperIntInfo() {
	updateInfo();
	
}

	/**
	 * This method keep the operator interface updated
	 */
	private void updateInfo() {
		updateThermostatStatus();

	}
	
	
	/**
	 * Return the low desired temperature status
	 */
	public ETempStatus getLDTempStatus() {
		return lowDesirableTempStatus;
	}
	
	/**
	 * Return the upper desired temperature status
	 */
	public ETempStatus getUDTempStatus() {
		return upperDesirableTempStatus;
	}
	
	/**
	 * Return the low alarm temperature status
	 */
	public ETempStatus getLATempStatus() {
		return lowAlarmTempStatus;
	}
	
	/**
	 * Return the upper alarm temperature status
	 */
	public ETempStatus getUATempStatus() {
		return upperAlarmTempStatus;
	}
	
	/**
	 * Return the current isolette status
	 */
	public EStatus getIsoletteStatus() {
		return isoletteStatus;
	}
	
	/**
	 * Return the current thermostat status
	 */
	public EStatus getThermostatStatus() {
		return thermostatStatus;
	}
	
	/**
	 * Provide the command to start and stop the thermos
	 */
	public boolean getThermosCom() {
		return thermosCom;
	}
	

}
