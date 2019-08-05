package isolette;

public class Thermostat implements IThermostat {

	/**
	 * Indicates the current thermostat status
	 */
	private EStatus thermosStatus = EStatus.OFF;

	private Monitor monitor;

	private Regulator regulator;
	
	
	public Thermostat() {
		monitor = new Monitor();
		
		regulator = new Regulator();
	}

	/**
	 * This method run thermostat tasks
	 */
	public void run(boolean thermosCom, byte LDTempIn, byte UDTempIn, byte LATempIn, byte UATempIn, float airTempIn) {
		byte currTemp = (byte) Math.round(airTempIn);
			this.runRegulator(LDTempIn, UDTempIn,currTemp);
			this.runMonitor(LATempIn, UATempIn,currTemp);
	}

	/**
	 * Executes the regulator tasks
	 */
	private void runRegulator(byte LDTempIn, byte UDTempIn,byte airTempIn) {
		regulator.run(LDTempIn, UDTempIn, airTempIn);
	}
	
	/**
	 * Executes the monitor tasks
	 */
	private void runMonitor(byte LATempIn, byte UATempIn, byte airTempIn) {
		monitor.run(LATempIn, UATempIn, airTempIn);
	}
	
	/**
	 * Return the regulator
	 */
	public IRegulator getRegulator() {
		return regulator;
	}
	
	/**
	 * Return the regulator
	 */
	public IMonitor getMonitor() {
		return monitor;
	}
}
