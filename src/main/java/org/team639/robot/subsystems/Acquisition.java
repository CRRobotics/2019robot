package org.team639.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * The acquisition subsystem.
 *
 * Responsible for manipulating hatch panels and cargo.
 * @author Sara Xin
 * @author Patrick Pfeifer
 * @author Jack Greenberg
 */
public class Acquisition extends Subsystem {

    private TalonSRX auxiliaryRollerLever;
    private TalonSRX lowerRollerExtension;

    private Spark upperRoller;
    private Spark lowerRoller;

    private Solenoid flowerOpen;
    private Solenoid flowerForward;

    /**
     * The constructor of the Acquisition class.
     * Initializes all private instance variables.
     */
    public Acquisition() {
        upperRoller = new Spark(0);
        lowerRoller = new Spark(1);

        auxiliaryRollerLever = new TalonSRX(4);
        lowerRollerExtension = new TalonSRX(3);

        flowerOpen = new Solenoid(1);
        flowerForward = new Solenoid(2);


        auxiliaryRollerLever.configFactoryDefault();
        lowerRollerExtension.configFactoryDefault();

        auxiliaryRollerLever.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 0);
        lowerRollerExtension.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 0);

        auxiliaryRollerLever.setNeutralMode(NeutralMode.Brake);
        lowerRollerExtension.setNeutralMode(NeutralMode.Brake);
    }

    @Override
    public void initDefaultCommand() {
    }

    /**
     * Sets the flower to be open.
     *
     * @param open boolean indicating whether the flower should be open or not.
     */
    public void setFlowerOpen(boolean open) {
        flowerOpen.set(open);
    }

    /**
     * Sets the flower to be forward.
     *
     * @param forward boolean indicating whether the flower should be positioned forward or not.
     */
    public void setFlowerForward(boolean forward) {
        flowerForward.set(forward);
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

//    public void setAuxiliaryRollerLeverSpeed(double speed) {
//        if (speed > 1) speed = 1;
//        else if (speed < -1) speed = -1;
//        auxiliaryRollerLever.set(ControlMode.PercentOutput, speed);
//    }

    public void setLowerRollerExtensionSpeed(double speed) {
        if (speed > 1) speed = 1;
        else if (speed < -1) speed = -1;
        auxiliaryRollerLever.set(ControlMode.PercentOutput, speed);
    }

    /**
     * Returns the position of the lower roller extension.
     * @return The position of the lower roller extension in encoder ticks.
     */
    public int getLowerRollerExtensionEncoderPosition() {
        return auxiliaryRollerLever.getSelectedSensorPosition(0);
    }

    /**
     * Zeroes the position of the lower extension encoder.
     */
    public void zeroLowerExtensionEncoder() {
        auxiliaryRollerLever.getSensorCollection().setQuadraturePosition(0, 0);
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
        return false; // TODO: actually read sensor.
    }

    /**
     * Returns whether cargo is detected within the cargo acquisition.
     * @return Whether cargo is detected within the cargo acquisition.
     */
    public boolean isCargoDetected() {
        return false; // TODO: actually read sensor.
    }
}
