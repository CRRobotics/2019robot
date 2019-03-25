package org.team639.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.command.Subsystem;
import org.team639.robot.commands.acqusition.ExtendLowerRollerWithTrigger;
import org.team639.robot.sensors.HatchDistanceSensor;

import static org.team639.robot.Constants.Acquisition.HATCH_DETECT_DISTANCE;

/**
 * The acquisition subsystem.
 *
 * Responsible for manipulating hatch panels and cargo.
 * @author Sara Xin
 * @author Patrick Pfeifer
 * @author Jack Greenberg
 */
public class Acquisition extends Subsystem {

    private TalonSRX lowerRollerExtension;

    private Spark upperRoller;
    private Spark lowerRoller;

    private Solenoid flowerOpen;
    private Solenoid flowerForward;

    private DigitalInput cargoDetector;
    private HatchDistanceSensor hatchDistanceSensor;

    /**
     * Creates a new acquisition using the provided hardware.
     * @param lowerRollerExtension The talon that extends the lower roller.
     * @param upperRoller The spark controlling the upper roller.
     * @param lowerRoller The spark controlling the lower roller.
     * @param flowerOpen The solenoid that opens and closes the hatch mechanism.
     * @param flowerForward The solenoid that extends and retracts the hatch mechanism.
     * @param cargoDetector The cargo detection sensor.
     * @param hatchDistanceSensor The hatch detection sensor (taken off real robot).
     */
    public Acquisition(TalonSRX lowerRollerExtension, Spark upperRoller, Spark lowerRoller, Solenoid flowerOpen, Solenoid flowerForward, DigitalInput cargoDetector, HatchDistanceSensor hatchDistanceSensor) {
        super("Acquisition");
        this.lowerRollerExtension = lowerRollerExtension;
        this.upperRoller = upperRoller;
        this.lowerRoller = lowerRoller;
        this.flowerOpen = flowerOpen;
        this.flowerForward = flowerForward;
        this.cargoDetector = cargoDetector;
        this.hatchDistanceSensor = hatchDistanceSensor;



        lowerRollerExtension.configFactoryDefault();

        lowerRoller.setInverted(true);

        lowerRollerExtension.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 0);

        lowerRollerExtension.setNeutralMode(NeutralMode.Brake);

        setFlowerOpen(true);
        setFlowerForward(true);
    }

    @Override
    public void initDefaultCommand() {
        setDefaultCommand(new ExtendLowerRollerWithTrigger());
    }

    /**
     * Sets the flower to be open.
     *
     * @param open boolean indicating whether the flower should be open or not.
     */
    public void setFlowerOpen(boolean open) {
        flowerOpen.set(open);
    }

    public boolean isFlowerOpen() {
        return flowerOpen.get();
    }

    /**
     * Sets the flower to be forward.
     *
     * @param forward boolean indicating whether the flower should be positioned forward or not.
     */
    public void setFlowerForward(boolean forward) {
        flowerForward.set(forward);
    }

    public boolean isFlowerExtended() {
        return flowerForward.get();
    }

    /**
     * Sets the speed of the top roller.
     * @param speed The speed of the roller as a percent from -1 to 1.
     */
    public void setUpperRollerSpeed(double speed) {
        if (speed > 1) speed = 1;
        else if (speed < -1) speed = -1;
        upperRoller.set(speed);
    }

    /**
     * Sets the speed of the bottom roller.
     * @param speed The speed of the roller as a percent from -1 to 1.
     */
    public void setLowerRollerSpeed(double speed) {
        if (speed > 1) speed = 1;
        else if (speed < -1) speed = -1;
        lowerRoller.set(speed);
    }

    /**
     * Sets the speed at which the lower roller is extending (or retracting).
     * @param speed A value from -1 to 1 representing the speed of the extension winch as a percent of maximum.
     */
    public void setLowerRollerExtensionSpeed(double speed) {
        if (speed > 1) speed = 1;
        else if (speed < -1) speed = -1;
        lowerRollerExtension.set(ControlMode.PercentOutput, speed);
//        System.out.println("AAAAAAAAAAAHHHH " + speed);
    }

    /**
     * Returns the position of the lower roller extension.
     * @return The position of the lower roller extension in encoder ticks.
     */
    public int getLowerRollerExtensionEncoderPosition() {
        return lowerRollerExtension.getSelectedSensorPosition(0);
    }

    /**
     * Zeroes the position of the lower extension encoder.
     */
    public void zeroLowerExtensionEncoder() {
        lowerRollerExtension.getSensorCollection().setQuadraturePosition(0, 0);
    }

    /**
     * Returns whether the lower roller is in its storage position.
     * @return Whether the lower roller is in its storage position.
     */
    public boolean isLowerRollerStored() {
        return lowerRollerExtension.getSensorCollection().isRevLimitSwitchClosed();
    }

    /**
     * Returns whether the lower roller is in its extended position.
     * @return Whether the lower roller is in its extended position.
     */
    public boolean isLowerRollerExtended() {
        return lowerRollerExtension.getSensorCollection().isFwdLimitSwitchClosed();
    }

    /**
     * Returns whether a hatch is detected on the flower, regardless of whether the flower is actually open.
     * @return Whether a hatch is detected on the flower, regardless of whether the flower is actually open.
     */
    public boolean isHatchOnFlower() {
        return false; // hatchDistanceSensor.getDistanceInches() <= HATCH_DETECT_DISTANCE;
    }

    /**
     * Returns whether cargo is detected within the cargo acquisition.
     * @return Whether cargo is detected within the cargo acquisition.
     */
    public boolean isCargoDetected() {
        return !cargoDetector.get();
    }
}
