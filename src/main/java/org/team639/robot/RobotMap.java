package org.team639.robot;

import edu.wpi.first.wpilibj.Solenoid;
import com.ctre.phoenix.motorcontrol.IMotorController;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import com.kauailabs.navx.frc.AHRS;
import edu.wpi.first.wpilibj.*;
import org.team639.robot.sensors.HatchDistanceSensor;

import static org.team639.robot.Constants.REAL;

/**
 * A central location for robot hardware.
 */
public class RobotMap {
    public static final Spark bigCompressor = new Spark(6);
    public static final DigitalInput pressureSwitch = new DigitalInput(9);

    public static final Solenoid liftBrake = new Solenoid(3);

    public static final TalonSRX liftMainTalon = new TalonSRX(6);
    public static final VictorSPX liftFollower = new VictorSPX(7);

    public static final TalonSRX leftDriveMaster = new TalonSRX(0);
    public static final TalonSRX rightDriveMaster = new TalonSRX(3);
    public static final AHRS navx = new AHRS(SPI.Port.kMXP);
    public static final TalonSRX lowerRollerExtension = new TalonSRX(9);
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
    public static final HatchDistanceSensor hatchDetector = new HatchDistanceSensor(1);
}
