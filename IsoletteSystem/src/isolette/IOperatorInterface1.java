package isolette;

public interface IOperatorInterface1 {

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
	 * This method set the desired temperature
	 */
	public  void setDesiredTemp(byte lowDesiredTemp, byte upperDesiredTemp);

	/**
	 * This method set the alarm temperature
	 */
	public  void setAlarmTemp(byte lowAlarmTemp, byte upperAlarmTemp);

	/**
	 * This method displays the isolette overall info 
	 */
	public  void displaySystemInfo();
	
	/**
	 * This method displays the isolette overall info 
	 */
	public  void updateOperIntInfo();
	
	public void stopIsolette(boolean onOff);
	
	public void startIsolette(boolean onOff);

}
