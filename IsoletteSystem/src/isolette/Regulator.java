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
		if (this.regStatus == EStatus.INIT) {
			if (this.initTime == 0) {
				this.initTime = System.nanoTime();
			}
		}

	}

	/**
	 * Update the current temperature
	 */
	private void updateCurrentTemperature(byte LDTempIn, byte UDTempIn, byte currTempIn) {
		this.LDTemp = LDTempIn;
		this.UDTemp = UDTempIn;
		this.currTemp = currTempIn;
		if (68 <= this.currTemp && this.currTemp <= 105) {
			this.currTempStatus = ETempStatus.VALID;
		} else {
			this.currTempStatus = ETempStatus.INVALID;
		}

	}

	/**
	 * Check the regulator failure
	 */
	private void checkRegulatorFailure(byte LDTempIn, byte UDTempIn, byte currTempIn) {
		if ((this.currTempStatus == ETempStatus.INVALID && this.regStatus == EStatus.NORMAL)
				|| (this.regStatus!=EStatus.INIT && this.currTemp < LDTempIn && this.HSStatus == EStatus.OFF) || (this.currTemp > UDTempIn && this.HSStatus == EStatus.ON)
				|| (this.regStatus == EStatus.INIT && (System.nanoTime() - this.initTime) / 1000000000 >= this.initTimeout)
				) {
			this.regIntFailure = true;
		} else {
			this.regIntFailure = false;
		}
	}

	/**
	 * Update the regulator mode
	 */
	private void updateRegulatorMode() {
		if (this.currTempStatus == ETempStatus.VALID && !this.regIntFailure) {
			if (this.regMode == EStatus.INIT || this.regMode == EStatus.NORMAL) {
				this.regMode = EStatus.NORMAL;
			} else {
				this.regMode = EStatus.FAILED;
			}
		}
	}

	/**
	 * Update the heat source status
	 */
	private void updateHeatSourceStatus() {
		if (this.regStatus == EStatus.ON) {
			if (this.currTemp <= LDTemp && this.HSStatus == EStatus.OFF) {
				this.HSStatus = EStatus.ON;
			} else if (currTemp >= this.UDTemp && this.HSStatus == EStatus.ON) {
				this.HSStatus = EStatus.OFF;
			}
		}
	}

	/**
	 * Update the regulator status
	 */
	private void updateRegulatorStatus() {
		if (this.regMode == EStatus.NORMAL) {
			this.regStatus = EStatus.ON;
		} else {
			this.regStatus = EStatus.FAILED;
		}
	}

	/**
	 * Return the regulator status
	 */
	public EStatus getRegulatorStatus() {
		return this.regStatus;
	}

	/**
	 * Return the current display temperature
	 */
	public byte getDisplayTemp() {
		return this.currTemp;
	}

	/**
	 * Return current heat control status
	 */
	public EStatus getHeatControl() {
		return this.HSStatus;
	}

}
