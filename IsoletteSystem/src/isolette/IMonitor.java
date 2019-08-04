package isolette;

public interface IMonitor {

	/**
	 * Run monitors tasks
	 */
	public abstract void run(byte LATempIn, byte UATempIn, byte currTempIn);

	/**
	 * Return the current alarm status
	 */
	public abstract EStatus getAlarmStatus();

	/**
	 * Return the current monitor status
	 */
	public abstract EStatus getMonitorStatus();

}
