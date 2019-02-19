package org.team639.robot;

import com.ctre.phoenix.motorcontrol.IMotorController;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import com.kauailabs.navx.frc.AHRS;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Spark;

import static org.team639.robot.Constants.REAL;

/**
 * A central location for robot hardware.
 */
public class RobotMap {
    public static final TalonSRX leftDriveMaster = new TalonSRX(0);
    public static final TalonSRX rightDriveMaster = new TalonSRX(3);
    public static final AHRS navx = new AHRS(SPI.Port.kMXP);
    public static final TalonSRX lowerRollerExtension = new TalonSRX(6);
    public static final Spark lowerRoller = new Spark(1);
    public static final Spark upperRoller = new Spark(0);
    public static final Solenoid flowerOpen = new Solenoid(0);
    public static final Solenoid flowerForward = new Solenoid(1);

    public static final IMotorController[] leftFollowers = {
            new VictorSPX(1),
            new VictorSPX(2),
    };

    public static final IMotorController[] rightFollowers = {
            new VictorSPX(4),
            new VictorSPX(5),
    };

    public static final DigitalInput cargoDetector = new DigitalInput(0);
}
