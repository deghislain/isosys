package isolette;

public class IsoletteSystem implements IIsoletteSystem{
	
	private Thermostat thermostat;

	private OperatorInterface operatorInterface;

	private Air air;

	private TemperatureSensor temperatureSensor;

	private HeatSource heatSource;
	
	
	
	public IsoletteSystem() {
		operatorInterface = new OperatorInterface();
		thermostat = new Thermostat();
		air = new Air();
		temperatureSensor = new TemperatureSensor();
		heatSource = new HeatSource();
	}
	
	public void executeRound(boolean isolCom, byte LDTempIn, byte UDTempIn, byte LATempIn, byte UATempIn) {
		final boolean thermosCom = operatorInterface.getThermosCom();
		final byte LDTemp = operatorInterface.getLowDesiredTemp();
		final byte UDTemp = operatorInterface.getUpperDesiredTemp();
		final byte LATemp = operatorInterface.getLowAlarmTemp();
		final byte UATemp = operatorInterface.getUpperAlarmTemp();
		final EStatus regulatorStatus = thermostat.getRegulator().getRegulatorStatus();
		final EStatus monitorStatus = thermostat.getMonitor().getMonitorStatus();
		final byte displayTemp = thermostat.getRegulator().getDisplayTemp();
		final EStatus heatControl = thermostat.getRegulator().getHeatControl();
		final float airTemp = temperatureSensor.getCurrentTemperature();
		final float heat = air.getHeat();
		final float heatDelta = heatSource.getHeatDelta();
		final EStatus thermosStatus = operatorInterface.getThermosStatus();
		
		
		this.runOperatorInterface(isolCom, thermosCom, LDTempIn, UDTempIn, LATempIn, UATempIn, displayTemp, regulatorStatus, monitorStatus);
		this.runThermostat(thermosCom, LDTemp, UDTemp, LATemp, UATemp, airTemp);
		this.runHeatSource(heatControl, thermosStatus);
		this.runAir(heatDelta, thermosStatus);
		this.runTemperatureSensor(heat);
	}

	/**
	 * This method execute the operator interface activities
	 */
	private void runOperatorInterface(boolean isolCom, boolean thermosCom, byte LDTempIn, byte UDTempIn, byte LATempIn, byte UATempIn, byte displayTempIn, EStatus regStatus, EStatus monStatus) {
		operatorInterface.run(isolCom, thermosCom, LDTempIn, UDTempIn, LATempIn, UATempIn, displayTempIn, regStatus, monStatus);
	}

	/**
	 * This method execute the thermostat activities
	 */
	private void runThermostat(boolean thermosComIn, byte LDTempIn, byte UDTempIn, byte LATempIn, byte UATempIn, float currTempIn) {
		thermostat.run(thermosComIn, LDTempIn, UDTempIn, LATempIn, UATempIn, currTempIn);
	}

	/**
	 * This method execute the heat source activities
	 */
	private void runHeatSource(EStatus heatControl, EStatus thermosStatus) {
		heatSource.run(heatControl, thermosStatus);
	}

	/**
	 * This method execute the temperature sensor activities
	 */
	private void runTemperatureSensor(float airTempIn) {
		temperatureSensor.run(airTempIn);
	}
	
	private void runAir(float heat, EStatus thermosStatus) {
		air.run(heat, thermosStatus);
	}

}
