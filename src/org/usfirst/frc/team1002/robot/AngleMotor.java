package org.usfirst.frc.team1002.robot;

import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.TalonSRX;
import edu.wpi.first.wpilibj.VictorSP;

public class AngleMotor {
	private static final int ANGLEMOTOR = 0;
	SpeedController angleMotor;

	public AngleMotor() {
		angleMotor = new VictorSP(0);
	}

	public void teleOp() {
		while (Robot.driver.getBButton() == true) {
			angleMotor.set(1.0);
		}
		while (Robot.driver.getAButton() == true) {
			angleMotor.set(-1.0);
		}
		{
			angleMotor.set(0.0);
		}

	}
}
