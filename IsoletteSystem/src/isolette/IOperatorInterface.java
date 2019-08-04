package isolette;

public interface IOperatorInterface {

	/**
	 * Indicates the minimum acceptable low desired temperature value for the isolette
	 */
	public byte MINLDTEMP = 97;

	/**
	 * Indicates the maximum acceptable low desired temperature value for the isolette
	 */
	public byte MAXLDTEMP = 99;

	/**
	 * Indicates the minimum acceptable low alarm temperature value for the isolette
	 */
	public byte MINLATEMP = 93;

	/**
	 * Indicates the maximum acceptable low alarm temperature value for the isolette
	 */
	public byte MAXLATEMP = 98;

	/**
	 * Indicates the minimum acceptable upper desired temperature value for the isolette
	 */
	public byte MINUDTEMP = 98;

	/**
	 * Indicates the maximum acceptable upper desired temperature value for the isolette
	 */
	public byte MAXUDTEMP = 100;

	/**
	 * Indicates the minimum acceptable upper alarm temperature value for the isolette
	 */
	public byte MINUATEMP = 99;

	/**
	 * Indicates the maximum acceptable upper alarm temperature value for the isolette
	 */
	public byte MAXUATEMP = 103;

	/**
	 * This method executes all operator interface tasks
	 */
	public  void run(boolean isolCom, boolean thermosCom, byte LDTempIn, byte UDTempIn, byte LATempIn, byte UATempIn, byte displayTempIn, EStatus regStatus, EStatus monStatus);
	
	public boolean getThermosCom();

}
