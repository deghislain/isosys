package isolette;

public interface IOperatorInterface {

	/**
	 * Indicate the min low desirable temperature value that can be set by a nurse
	 */
	public static final byte MIN_LOW_DESIRABLE_TEMP = 97;

	/**
	 * Indicate the max low desirable temperature value that can be set by a nurse
	 */
	public static final byte MAX_LOW_DESIRABLE_TEMP = 99;

	/**
	 * Indicate the min upper desirable temperature value that can be set by a nurse
	 */
	public static final byte MIN_UPPER_DESIRABLE_TEMP = 98;

	/**
	 * Indicate the max  low desirable temperature value that can be set by a nurse
	 */
	public static final byte MAX_UPPER_DESIRABLE_TEMP = 100;

	/**
	 * Indicate the min low alarm temperature value that can be set by a nurse
	 */
	public static final byte MIN_LOW_ALARM_TEMP = 93;

	/**
	 * Indicate the max  low alarm temperature value that can be set by a nurse
	 */
	public static final byte MAX_LOW_ALARM_TEMP = 98;

	/**
	 * Indicate the min upper alarm temperature value that can be set by a nurse
	 */
	public static final byte MIN_UPPER_ALARM_TEMP = 99;

	/**
	 * Indicate the max  low alarm temperature value that can be set by a nurse
	 */
	public static final byte MAX_UPPER_ALARM_TEMP = 103;

	/**
	 * This method executes all operator interface tasks
	 */
	public  void run(boolean isolCom, boolean thermosCom, byte LDTempIn, byte UDTempIn, byte LATempIn, byte UATempIn, byte displayTempIn, EStatus alarmStatusIn, EStatus regStatus, EStatus monStatus);
	
	public boolean getThermosCom();

}
