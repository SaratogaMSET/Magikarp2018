
package org.usfirst.frc.team649.robot.util;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.Timer;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Joystick;

public class VoltageLog implements Runnable {

	private final long defaultInterval = 100L;
	public static BufferedWriter buff;
	private static boolean nowLogging = false;
	public static final String COMMA_DELIMITER = ",";
	private static final String NEW_LINE_DELIMITER = "\n";
	private static ScheduledExecutorService executeLogging;
	private static Compressor robotCompressor;
	public static double startTime;
	private static PowerDistributionPanel robotPDP;
	
	private static Joystick horizontal;
	private static Joystick vertical;
	private static Joystick operatorJoystick;
	private static Joystick buttonboard;
	private static Joystick buttonboard2;

	public VoltageLog(PowerDistributionPanel pdp, Compressor compressor, Joystick hor, Joystick vert, Joystick op, Joystick bb1, Joystick bb2) {
		robotCompressor = compressor;
		robotPDP = pdp;
		horizontal = hor;
		vertical = vert;
		operatorJoystick = op;
		// In case Graham asks to read in buttonboard values(Not implemented yet)
		buttonboard = bb1;
		buttonboard2 = bb2;
	}

	public void startLoggingWithInterval(String fileName, long interval) {
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("YYYYMMdd-HHMMss");
		LocalDateTime now = LocalDateTime.now();

		try {
			if (DriverStation.getInstance().isFMSAttached()) {
				buff = new BufferedWriter(new FileWriter("/home/lvuser/" + "match"+DriverStation.getInstance().getMatchNumber()+ ".csv"));
			} else {
				buff = new BufferedWriter(new FileWriter("/home/lvuser/" + fileName + "-" + dtf.format(now) + ".csv"));
			}
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}

		executeLogging = Executors.newSingleThreadScheduledExecutor();
		executeLogging.scheduleAtFixedRate(this, interval, interval, TimeUnit.MILLISECONDS);
		nowLogging = true;
		startTime = Timer.getFPGATimestamp();

	}

	public void startLogging(String fileName) {
		startLoggingWithInterval(fileName, defaultInterval);
	}

	@Override
	public void run() {
		try {
			createLogEntry();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	// Added Joystick values to log entry
	public static void createLogEntry() throws IOException {
		int i;

		if (nowLogging == false) {
			return; // file is not open
		}

		buff.write(String.valueOf(Timer.getFPGATimestamp() - startTime));
		buff.write(COMMA_DELIMITER);
		buff.write(String.valueOf(robotPDP.getVoltage()));
		buff.write(COMMA_DELIMITER);
		buff.write(String.valueOf(robotPDP.getTemperature()));
		buff.write(" ");

		if (robotCompressor != null) {
			buff.write(COMMA_DELIMITER);
			buff.write(String.valueOf(robotCompressor.getCompressorCurrent()));
			buff.write(COMMA_DELIMITER);
			buff.write(String.valueOf(robotCompressor.getPressureSwitchValue()));
		} else {
			buff.write(",0,0"); // in case we have no compressor.
		}
		buff.write(" ");

		buff.write(COMMA_DELIMITER);
		buff.write(String.valueOf(robotPDP.getTotalCurrent()));

		buff.write(" ");

		for (i = 0; i < 16; i++) {
			buff.write(COMMA_DELIMITER);
			buff.write(String.valueOf(robotPDP.getCurrent(i)));
		}

		// Writing joystick values
		buff.write(COMMA_DELIMITER);
		buff.write(String.valueOf(horizontal.getX()));
		buff.write(COMMA_DELIMITER);
		buff.write(String.valueOf(horizontal.getY()));
		buff.write(COMMA_DELIMITER);
		buff.write(String.valueOf(horizontal.getZ()));
		buff.write(" ");
		
		buff.write(COMMA_DELIMITER);
		buff.write(String.valueOf(vertical.getX()));
		buff.write(COMMA_DELIMITER);
		buff.write(String.valueOf(vertical.getY()));
		buff.write(COMMA_DELIMITER);
		buff.write(String.valueOf(vertical.getZ()));
		buff.write(" ");
		
		// Gunner joystick
		buff.write(COMMA_DELIMITER);
		buff.write(String.valueOf(operatorJoystick.getX()));
		buff.write(COMMA_DELIMITER);
		buff.write(String.valueOf(operatorJoystick.getY()));
		buff.write(COMMA_DELIMITER);
		buff.write(String.valueOf(operatorJoystick.getZ()));
		buff.write(" ");
		
		buff.write(NEW_LINE_DELIMITER);
	}

	public void endLogging() {
		if (nowLogging == true) {
			executeLogging.shutdown();
			try {
				buff.close();
			} catch (IOException e) {
				// nothing to do
			}
		}
		nowLogging = false;
	}
}