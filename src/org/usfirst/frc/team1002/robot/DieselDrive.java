package org.usfirst.frc.team1002.robot;

import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.RobotDrive.MotorType;

public class DieselDrive {

	private static RobotDrive dieselDrive;

	private enum Channel {
		FRONT_LEFT(9), REAR_LEFT(8), FRONT_RIGHT(5), REAR_RIGHT(2),;

		public int port;

		Channel(int port) {
			this.port = port;
		}
	}

	private static double smooth(double value) {

		if (value > 0.9)
			return 1;
		else if (value < 0.15 && value > -0.15)
			return 0;
		else if (value < -0.9) {
			return -1;
		} else
			return Math.sin(value);
	}

	public static double x;
	public static double y;
	public static double t;

	public DieselDrive() {
		dieselDrive = new RobotDrive(Channel.FRONT_LEFT.port, Channel.REAR_LEFT.port, Channel.FRONT_RIGHT.port,
				Channel.REAR_RIGHT.port);

		dieselDrive.setInvertedMotor(MotorType.kFrontLeft, true);
		dieselDrive.setInvertedMotor(MotorType.kRearLeft, true);
	}

	public void teleOp() {
		double scale = 1.0;

		if (Robot.driver.getRawButton(5)) {
			scale /= 2;
		}

		x = (smooth(Robot.driver.getX(Hand.kLeft) * -1) * scale);
		y = (smooth(Robot.driver.getY(Hand.kLeft) * scale));
		t = (smooth(Robot.driver.getX(Hand.kRight)) * scale);

		myDriveCartesian(x, y, t, 0.0);
		Robot.lastT = t;
		Robot.lastY = y;
		Robot.lastX = x;
	}

	static double prev_x = 0.0;
	static double prev_y = 0.0;
	static double prev_t = 0.0;
	static final double protectedConstant = 20;

	public void myDriveCartesian(double x, double y, double t, double angle) {

		if (Math.abs(x) > 0.25) {
			x = (prev_x + (x - prev_x) / protectedConstant);
		}
		if (Math.abs(y) > 0.25) {
			y = (prev_y + (y - prev_y) / protectedConstant);
		}
		if (Math.abs(t) > 0.25) {
			t = (prev_t + (t - prev_t) / protectedConstant);
		}

		dieselDrive.mecanumDrive_Cartesian(x, y, t, angle);
		prev_x = x;
		prev_y = y;
		prev_t = t;
	}
}
