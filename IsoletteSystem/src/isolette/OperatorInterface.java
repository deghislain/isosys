package isolette;

public class OperatorInterface implements IOperatorInterface {

	/**
	 * Indicates isolette status
	 */
	private EStatus isoletteStatus;

	/**
	 * Indicates if the low desired temperature is valid or not
	 */
	private ETempStatus lowDesirableTempStatus;

	/**
	 * Indicates if the upper desired temperature is valid or not
	 */
	private ETempStatus upperDesirableTempStatus;

	/**
	 * Indicates if the low alarm temperature is valid or not
	 */
	private ETempStatus lowAlarmTempStatus;

	/**
	 * Indicates if the upper alarm temperature is valid or not
	 */
	private ETempStatus upperAlarmTempStatus;

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
	 * Turn the thermostat on off
	 */
	private boolean thermosCom = false;

	/**
	 * Indicates the current regulator status
	 */
	//private EStatus regulatorStatus;

	/**
	 * Indicates the current monitor status
	 */
	//private EStatus monitorStatus;

	/**
	 * Indicates the thermostat status
	 */
	private EStatus thermosStatus;

	/**
	 * Indicates the thermostat status
	 */
	private EStatus alarmStatus;
	
	public OperatorInterface() {
		this.isoletteStatus = EStatus.OFF;
		this.lowDesirableTempStatus  = ETempStatus.INVALID;
		this.upperDesirableTempStatus  = ETempStatus.INVALID;
		this.lowAlarmTempStatus  = ETempStatus.INVALID;
		this.upperAlarmTempStatus  = ETempStatus.INVALID;
		//this.regulatorStatus  = EStatus.INIT;
		//this.monitorStatus  = EStatus.INIT;
		this.thermosStatus  = EStatus.OFF;
		this.alarmStatus  = EStatus.OFF;
	}

	/**
	 * This method executes all operator interface tasks
	 */
	public void run(boolean isolCom, boolean thermosCom, byte LDTempIn, byte UDTempIn, byte LATempIn, byte UATempIn,
			byte displayTempIn, EStatus regStatus, EStatus monStatus) {
		// Turn the isolette on
		if (isolCom && isoletteStatus == EStatus.OFF) {
			turnOn(isolCom);
		} else if (!isolCom && isoletteStatus == EStatus.ON) {
			turnOff(isolCom);
		}

		if (isoletteStatus == EStatus.ON) {
				turnThermosOn();

			if (thermosStatus == EStatus.NORMAL) {

				if (lowDesirableTempStatus == ETempStatus.INVALID && upperDesirableTempStatus == ETempStatus.INVALID) {
					setDesiredTemp(LDTempIn, UDTempIn);
				}

				if (lowAlarmTempStatus == ETempStatus.INVALID && upperAlarmTempStatus == ETempStatus.INVALID) {
					setAlarmTemp(LATempIn, UATempIn);
				}
			}
			displaySystemInfo();
			updateThermosStatus(regStatus, monStatus);
		}else {
			turnThermosOff();
		}
	}

	/**
	 * This method set the desired temperature
	 */
	private void setDesiredTemp(byte LDTempIn, byte UDTempIn) {
		LDTemp = LDTempIn;
		UDTemp = UDTempIn;
		if (MINLDTEMP <= LDTempIn && LDTempIn <= MAXLDTEMP) {
			lowDesirableTempStatus = ETempStatus.VALID;
		} else {
			lowDesirableTempStatus = ETempStatus.INVALID;
		}

		if (MINUDTEMP <= UDTemp && UDTemp <= MAXUDTEMP) {
			upperDesirableTempStatus = ETempStatus.VALID;
		} else {
			upperDesirableTempStatus = ETempStatus.INVALID;
		}

	}

	/**
	 * This method set the alarm temperature
	 */
	private void setAlarmTemp(byte LATempIn, byte UATempIn) {
		LATemp = LATempIn;
		UATemp = UATempIn;
		if (MINLATEMP <= LATempIn && LATempIn <= MAXLATEMP) {
			lowAlarmTempStatus = ETempStatus.VALID;
		} else {
			lowAlarmTempStatus = ETempStatus.INVALID;
		}

		if (MINUATEMP <= UATempIn && UATempIn <= MAXUATEMP) {
			upperAlarmTempStatus = ETempStatus.VALID;
		} else {
			upperAlarmTempStatus = ETempStatus.INVALID;
		}

	}

	/**
	 * This method displays the isolette overall info
	 */
	private void displaySystemInfo() {
		String currTemperature = "UNSPECIFIED";
		if (this.thermosStatus == EStatus.NORMAL) {
			currTemperature = Integer.toString(this.currentTemperature);
		}
		System.out.printf("%-10s %-10s %-10s %-10s %-20s %-20s %-10s %n", "LDT: " + this.LDTemp, "UDT: " + this.UDTemp,
				"LAT: " + this.LATemp, "UAT: " + this.UATemp, "Thermostat Status: " + this.thermosStatus,
				"Current Temperature: " + currTemperature, "Alarm Status: " + this.alarmStatus);
		String tempStatus = null;

		if (this.thermosStatus == EStatus.NORMAL) {
			if (this.lowDesirableTempStatus == ETempStatus.INVALID) {
				tempStatus = "The LDT is INVALID";
			}
			if (this.upperDesirableTempStatus == ETempStatus.INVALID) {
				tempStatus = tempStatus + "   The UDT is INVALID";
			}
			if (this.lowAlarmTempStatus == ETempStatus.INVALID) {
				tempStatus = tempStatus + "   The LAT is INVALID";
			}
			if (this.upperAlarmTempStatus == ETempStatus.INVALID) {
				tempStatus = tempStatus + "   The UAT is INVALID";
			}
			if (null != tempStatus)
				System.out.println(tempStatus);
		}
	}

	private void turnOn(boolean onOff) {
		isoletteStatus = EStatus.ON;
		// thermosCom = true;
	}

	private void turnOff(boolean onOff) {
		isoletteStatus = EStatus.OFF;
		// thermosCom = false;
	}

	public boolean getThermosCom() {
		return thermosCom;
	}

	public EStatus getThermosStatus() {
		return thermosStatus;
	}

	public EStatus getIsoletteStatus() {
		return isoletteStatus;
	}
	
	public byte getLowDesiredTemp() {
		return LDTemp;
	}
	public byte getUpperDesiredTemp() {
		return UDTemp;
	}
	
	public byte getLowAlarmTemp() {
		return LATemp;
	}
	public byte getUpperAlarmTemp() {
		return UATemp;
	}

	private void updateThermosStatus(EStatus regStatus, EStatus monStatus) {
		// if(regStatus == EStatus.INIT && monStatus == EStatus.INIT) {
		if (thermosCom) {
			if (regStatus == EStatus.INIT && monStatus == EStatus.INIT) {
				thermosStatus = EStatus.INIT;
				// comment for test }else if(regStatus == EStatus.ON && monStatus == EStatus.ON)
				// {

			} else if (regStatus == EStatus.ON && monStatus == EStatus.ON) {
				thermosStatus = EStatus.NORMAL;
			} else {
				thermosStatus = EStatus.FAILED;
			}
		} else {
			thermosStatus = EStatus.OFF;
		}
		
		//regulatorStatus = regStatus;

	}

	private void turnThermosOn() {
			thermosCom = true;
	}
	
	private void turnThermosOff(){
		thermosCom = false;
	}
}
