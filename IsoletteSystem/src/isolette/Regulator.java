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
	private byte LDTemp = 68;

	/**
	 * Indicates the upper desired temperature in input
	 */
	private byte UDTemp = 68;

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
	public void run(EStatus thermosStatus, byte LDTempIn, byte UDTempIn, byte currTempIn) {
		if (initTime == 0) {
			startTimer(thermosStatus);
		}
		updateCurrentTemperature(LDTempIn, UDTempIn, currTempIn);
		updateHeatSourceStatus(thermosStatus);
		checkRegulatorFailure(LDTempIn, UDTempIn, currTempIn);
		updateRegulatorMode();
		updateRegulatorStatus();
	}

	/**
	 * Start the initialization timer
	 */
	private void startTimer(EStatus thermosStatus) {
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
	private void updateHeatSourceStatus(EStatus thermosStatus ) {
		if ((thermosStatus != EStatus.OFF) && (currTemp < LDTemp && HSStatus == EStatus.OFF)) {
			HSStatus = EStatus.ON;
		} else if((thermosStatus != EStatus.OFF) && (currTemp > UDTemp && HSStatus == EStatus.ON)){
			HSStatus = EStatus.OFF;
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
