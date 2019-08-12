package isolette;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;

public class IsoletteSystem implements IIsoletteSystem {

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
		final EStatus alarmStatus = thermostat.getMonitor().getAlarmStatus();

		Thread operatorInterfaceThread = new Thread(new Runnable() {

			@Override
			public void run() {
				System.out.println(
						"Run by " + Thread.currentThread().getName() + " " + convertTime(System.currentTimeMillis()));
				operatorInterface.run(isolCom, thermosCom, LDTempIn, UDTempIn, LATempIn, UATempIn, displayTemp,
						alarmStatus, regulatorStatus, monitorStatus);
			}

		});

		Thread thermostatThread = new Thread(new Runnable() {

			@Override
			public void run() {
				System.out.println(
						"Run by " + Thread.currentThread().getName() + " " + convertTime(System.currentTimeMillis()));
				thermostat.run(thermosCom, LDTemp, UDTemp, LATemp, UATemp, airTemp);

			}

		});

		Thread heatSourceThread = new Thread(new Runnable() {

			@Override
			public void run() {
				System.out.println(
						"Run by " + Thread.currentThread().getName() + " " + convertTime(System.currentTimeMillis()));
				heatSource.run(heatControl);
			}

		});

		Thread airThread = new Thread(new Runnable() {

			@Override
			public void run() {
				System.out.println(
						"Run by " + Thread.currentThread().getName() + " " + convertTime(System.currentTimeMillis()));
				air.run(heatDelta);
			}

		});

		/*
		 * Thread temperatureSensorThread = new Thread(new Runnable() {
		 * 
		 * @Override public void run() { temperatureSensor.run(heat); }
		 * 
		 * });
		 */

		operatorInterfaceThread.setName("OI");
		thermostatThread.setName("Thermostat");
		heatSourceThread.setName("HS");
		airThread.setName("air");
		// temperatureSensorThread.setName("TS");
		operatorInterfaceThread.start();
		thermostatThread.start();
		heatSourceThread.start();
		airThread.start();

		temperatureSensor.run(heat);

		try {
			System.out.println(
					"Run by " + Thread.currentThread().getName() + " " + convertTime(System.currentTimeMillis()));
			operatorInterfaceThread.join();
			thermostatThread.join();
			heatSourceThread.join();
			airThread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}

	private String convertTime(long time) {
		Date date = new Date(time);
		Format format = new SimpleDateFormat("yyyy MM dd HH:mm:ss");
		return format.format(date);
	}

}
