package isolette;

public class Monitor implements IMonitor {
	/**
	 * Indicates the current temperature
	 */
	private int currTemp;

	/**
	 * Indicates the current monitor status
	 */
	private EStatus monStatus;

	/**
	 * Indicates the current the current monitor mode
	 */
	private EStatus monMode;

	/**
	 * Indicates the current temperature status
	 */
	private ETempStatus currTempStatus;

	/**
	 * Indicates the current alarm status
	 */
	private EStatus alarmStatus;

	/**
	 * Indicates if the monitor has failed
	 */
	private boolean monitorFailure;

	/**
	 * Indicates the initialization phase time
	 */
	private long initTime = 0;
	
	/**
	 * Indicates the lower desired temperature in input
	 */
	private byte LATemp = 96;

	/**
	 * Indicates the upper desired temperature in input
	 */
	private byte UATemp = 101;
	
	/**
	 * Indicates the system initialization timeout
	 */
	private float initTimeout = 1.0f;

	
	
	public Monitor() {
		this.monStatus = EStatus.INIT;
		this.monMode = EStatus.OFF;
		this.currTempStatus = ETempStatus.INVALID;
		this.alarmStatus = EStatus.OFF;
		this.currTemp = 68;
		this.monitorFailure = false;
		this.initTime = 0;
	}

	/**
	 * Run monitors tasks
	 */
	public void run(byte LATempIn, byte UATempIn, byte currTempIn) {
		this.startTimer();
		this.updateCurrentTemperature(LATempIn, UATempIn, currTempIn);
		//this.checkMonitorFailure(currTempIn);
		this.updateMonitorMode();
		this.updateMonitorStatus();
		this.updateAlarmStatus();
	}

	/**
	 * Initialization phase timer
	 */
	private void startTimer() {
		if (monStatus == EStatus.INIT) {
			if (initTime == 0) {
				initTime = System.nanoTime();
			}
		}

	}

	/**
	 * Update the current temperature
	 */
	private void updateCurrentTemperature(byte LATempIn, byte UATempIn, byte currTempIn) {
		LATemp = LATempIn;
		UATemp = UATempIn;
		currTemp = currTempIn;
		if (68 <= currTemp && currTemp <= 105) {
			currTempStatus = ETempStatus.VALID;
		} else {
			currTempStatus = ETempStatus.INVALID;
		}
	}

	/**
	 * Check if the monitor has failed
	 */
	private void checkMonitorFailure(byte currTempIn) {
		currTemp = currTempIn;
		if(currTempStatus == ETempStatus.INVALID || ((System.nanoTime() - initTime) / 1000000000 >= initTimeout)){
			monitorFailure = true;
		} else {
			monitorFailure = false;
		}		}


	/**
	 * Keep the monitor mode up to date
	 */
	private void updateMonitorMode() {
		if (currTempStatus == ETempStatus.VALID && !monitorFailure) {
				monMode = EStatus.NORMAL;
			}else {
				monMode = EStatus.FAILED;
			}
		initTime = 0;
		}
		

	/**
	 * Keep the alarm status up to date
	 */
	private void updateAlarmStatus() {
		if( monMode == EStatus.INIT) {
			alarmStatus = EStatus.OFF;
		}else if((currTemp < LATemp || currTemp > UATemp) && monMode == EStatus.NORMAL) {
			alarmStatus = EStatus.ON;
		}else{
			alarmStatus = EStatus.OFF;
		}
	}

	/**
	 * Keep the monitor status up to date
	 */
	private void updateMonitorStatus() {
		if (monMode == EStatus.NORMAL) {
			monStatus = EStatus.ON;
		} else {
			monStatus = EStatus.FAILED;
		}
	}

	/**
	 * Return the current alarm status
	 */
	public EStatus getAlarmStatus() {
		return alarmStatus;
	}

	/**
	 * Return the current monitor status
	 */
	public EStatus getMonitorStatus() {
		return monStatus;
	}

}
