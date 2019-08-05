package isolette;

public interface IRegulator {

	/**
	 * This method run regulator tasks
	 */
	public abstract void run(byte LDTempIn, byte UDTempIn, byte currTempIn);

	/**
	 * Return regulator current status
	 */
	public abstract EStatus getRegulatorStatus();

	/**
	 * Return display temperature
	 */
	public  byte getDisplayTemp();

	/**
	 * Return current heat control status
	 */
	public abstract EStatus getHeatControl();

}
