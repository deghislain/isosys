package isolette;

public class Regulator implements IRegulator {

	/**
	 * Indicates the current regulator status
	 */
	private EStatus regStatus;

	/**
	 * Indicates the current regulator mode status
	 */
	private EStatus regMode;

	/**
	 * Indicates the initialization time
	 */
	private long initTime = 0;

	/**
	 * Indicates the current temperature status
	 */
	private ETempStatus currTempStatus;

	/**
	 * Indicates if the regulator failed during initialization phase
	 */
	private boolean regIntFailure = false;

	/**
	 * Indicates the current heat source status
	 */
	private EStatus HSStatus;

	/**
	 * Indicates the current temperature
	 */
	private byte currTemp = 68;

	/**
	 * Indicates the lower desired temperature in input
	 */
	private byte LDTemp = 96;

	/**
	 * Indicates the upper desired temperature in input
	 */
	private byte UDTemp = 102;

	/**
	 * Indicates the system initialization timeout
	 */
	private float initTimeout = 1.0f;

	public Regulator() {
		this.regStatus = EStatus.INIT;
		this.regMode = EStatus.INIT;
		this.currTempStatus = ETempStatus.INVALID;
		this.HSStatus = EStatus.OFF;
	}

	/**
	 * This method run regulator tasks
	 */
	public void run(byte LDTempIn, byte UDTempIn, byte currTempIn) {
		this.startTimer();
		this.updateCurrentTemperature(LDTempIn, UDTempIn, currTempIn);
		this.updateHeatSourceStatus();
		this.checkRegulatorFailure(LDTempIn, UDTempIn, currTempIn);
		this.updateRegulatorMode();
		this.updateRegulatorStatus();
	}

	/**
	 * Start the initialization timer
	 */
	private void startTimer() {
		if (regStatus == EStatus.INIT) {
			if (initTime == 0) {
				initTime = System.nanoTime();
			}
		}

	}

	/**
	 * Update the current temperature
	 */
	private void updateCurrentTemperature(byte LDTempIn, byte UDTempIn, byte currTempIn) {
		LDTemp = LDTempIn;
		UDTemp = UDTempIn;
		currTemp = currTempIn;
		if (68 <= currTemp && currTemp <= 105) {
			currTempStatus = ETempStatus.VALID;
		} else {
			currTempStatus = ETempStatus.INVALID;
		}

	}

	/**
	 * Check the regulator failure
	 */
	private void checkRegulatorFailure(byte LDTempIn, byte UDTempIn, byte currTempIn) {
		if ((currTempStatus == ETempStatus.INVALID && regStatus == EStatus.NORMAL)
				|| (regStatus!=EStatus.INIT && currTemp < LDTempIn && HSStatus == EStatus.OFF) || (currTemp > UDTempIn && HSStatus == EStatus.ON)
				|| ((System.nanoTime() - initTime) / 1000000000 >= initTimeout)
				) {
			regIntFailure = true;
		} else {
			regIntFailure = false;
		}
	}

	/**
	 * Update the regulator mode
	 */
	private void updateRegulatorMode() {
		if (currTempStatus == ETempStatus.VALID && !regIntFailure) {
			if (regMode == EStatus.INIT || regMode == EStatus.NORMAL) {
				regMode = EStatus.NORMAL;
			} else {
				regMode = EStatus.FAILED;
			}
		}
		initTime = 0;
	}

	/**
	 * Update the heat source status
	 */
	private void updateHeatSourceStatus() {
		if (regStatus == EStatus.ON) {
			if (currTemp <= LDTemp && HSStatus == EStatus.OFF) {
				HSStatus = EStatus.ON;
			} else if (currTemp >= UDTemp && HSStatus == EStatus.ON) {
				HSStatus = EStatus.OFF;
			}
		}
	}

	/**
	 * Update the regulator status
	 */
	private void updateRegulatorStatus() {
		if (regMode == EStatus.NORMAL) {
			regStatus = EStatus.ON;
		} else {
			regStatus = EStatus.FAILED;
		}
	}

	/**
	 * Return the regulator status
	 */
	public EStatus getRegulatorStatus() {
		return regStatus;
	}

	/**
	 * Return the current display temperature
	 */
	public byte getDisplayTemp() {
		return currTemp;
	}

	/**
	 * Return current heat control status
	 */
	public EStatus getHeatControl() {
		return HSStatus;
	}

}
