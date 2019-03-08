package org.team639.robot.subsystems;

import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import org.team639.robot.Constants;
import edu.wpi.first.wpilibj.command.Subsystem;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.ControlMode;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Solenoid;
import org.team639.robot.commands.climbing.JoystickControlledClimb;

/**
 * Controls the functions of the climbing subsystem. This system has pneumatics that moves its
 * pivot points and motors that move it up and down.
 */
public class Climbing extends Subsystem {

    private TalonSRX climbMotor;
    private DigitalInput fullyExtended;
    private DigitalInput lowlimitswitch;
    private Solenoid pivot;
    private Solenoid clamps;
    private Solenoid brake;

    public Climbing() {
        climbMotor = new TalonSRX(8);
        climbMotor.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 0);
        fullyExtended = new DigitalInput(5);
        lowlimitswitch = new DigitalInput(8);
        pivot = new Solenoid(7);
        brake = new Solenoid(4);
        clamps = new Solenoid(6);

        setClamped(false);
    }

    @Override
    public void initDefaultCommand() {
        setDefaultCommand(new JoystickControlledClimb());
    }

    /**
     * Sets the speed of the climbing lift to a percentage between 1 and -1.
     * Values < 0 move the system down
     * Values > 0 move the system up
     * @param speed The speed at which the motors are to be run.
     */
    public void setSpeed(double speed) {
        if (isFullyExtended() && speed < 0) {
            // Prevents the motors from running past the limit switches
            climbMotor.set(ControlMode.PercentOutput, 0);
//            System.out.println("uh oh");
        } else {
            climbMotor.set(ControlMode.PercentOutput, speed);
//            System.out.println("settings: " + speed);
        }
    }

    /**
     * Releases or stores the subsystem.
     * @param released Whether the climber should be released or not.
     */
    public void setReleased(boolean released) {
        pivot.set(released); // TODO: Verify
    }

    public boolean isReleased() {
        return pivot.get(); // TODO: Verify
    }

    /**
     * Returns the position of the climbing lift in encoder ticks
     */
    public int getPosition() {
        return climbMotor.getSelectedSensorPosition(0);
    }

    /**
     * Returns the encoder position, converted to inches, of a specified motor
     * @return The encoder position, converted to inches, of a specified motor
     */
    public double getEncoderPositionInches() {
        return climbMotor.getSelectedSensorPosition(0) / Constants.Climbing.TICKS_PER_INCH_CLIMBING;
    }

    public void setClamped(boolean clamped) {
        clamps.set(!clamped); // TODO: Verify
    }

    public boolean isClamped() {
        return !clamps.get(); // TODO: Verify
    }

    /**
     * Change the quadrature reported position to 0, so changes in position are reported as relative to this.
     * see SensorCollection.setQuadraturePosition for more information
     */
    public void zeroEncoder() {
        climbMotor.getSensorCollection().setQuadraturePosition(0, 0);
    }

    public void setBrake(boolean engaged) {
        brake.set(!engaged); // TODO: Verify
    }

    public boolean isBraking() {
        return !brake.get(); // TODO: Verify
    }

    public boolean isFullyExtended() {
        return !fullyExtended.get(); // TODO: Verify
    }


}
