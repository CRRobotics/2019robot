package org.team639.robot.subsystems;

import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.StatusFrameEnhanced;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import edu.wpi.first.wpilibj.command.Subsystem;
import com.ctre.phoenix.motorcontrol.ControlMode;
import edu.wpi.first.wpilibj.Solenoid;
import org.team639.robot.Constants;
import org.team639.robot.commands.lift.MonitorLift;

// Note - Encoder: 4096 ticks per rotation

/**
 * CLass for controlling the lift system
 * @author Aidan Foley
 * @author Rohit Lal
 */

public class Lift extends Subsystem {

    private TalonSRX mainTalon;
    private VictorSPX follower;

    private Solenoid brake;

    private ControlMode currentControlMode = ControlMode.Velocity;

    private double kP;
    private double kI;
    private double kD;
    private double kF;


    /**
     * Constructor, initializes the lift given the two required motors.
     * @param mainTalon The main motor.
     * @param follower The following motor.
     * @param brake The lift brake.
     */
    public Lift(TalonSRX mainTalon, VictorSPX follower, Solenoid brake) {

        this.mainTalon = mainTalon;
        this.follower = follower;
        this.brake = brake;

        follower.follow(mainTalon);

        mainTalon.configReverseSoftLimitEnable(false, 0);

//        mainTalon.configForwardSoftLimitEnable(true, 0);
//        mainTalon.configForwardSoftLimitThreshold(Constants.LIFT_MAX_HEIGHT, 0);

        mainTalon.configPeakCurrentLimit(30);
        mainTalon.configPeakCurrentDuration(10);
        mainTalon.configContinuousCurrentLimit(25);
        mainTalon.enableCurrentLimit(true);

        mainTalon.setNeutralMode(NeutralMode.Brake);

        mainTalon.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 0);
        mainTalon.setSensorPhase(false);

        mainTalon.setStatusFramePeriod(StatusFrameEnhanced.Status_3_Quadrature, 15, 0);

        mainTalon.configAllowableClosedloopError(0, 50, 0);

        setPID(Constants.LIFT_P, Constants.LIFT_I, Constants.LIFT_D, Constants.LIFT_F);

        if (encoderPresent()) setCurrentControlMode(ControlMode.Velocity);
        else setCurrentControlMode(ControlMode.PercentOutput);
    }

    /**
     * Sets the default command to motorBrake, so if nothing is done the motor will hold the lift in place.
     */
    public void initDefaultCommand() {
//        setDefaultCommand(new MonitorLift());
    }

    /**
     * Sets the speed of the lift with a percent of total speed from -1 to 1. Negative is down and positive is up.
     * @param speed The speed to move the lift.
     */
    public void setSpeedPercent(double speed) {
        if (speed > 1) speed = 1;
        else if (speed < -1) speed = -1;
        switch (currentControlMode) {
            case PercentOutput:
                mainTalon.set(currentControlMode, speed);
                break;
            case Velocity:
                mainTalon.set(currentControlMode, speed * Constants.LIFT_MAX_SPEED);
                break;
        }
    }

    /**
     * Sets the talon internal pid.
     * @param p The p constant.
     * @param i The i constant.
     * @param d The d constant.
     * @param f The f constant.
     */
    public void setPID(double p, double i, double d, double f) {
        this.kP = p;
        this.kI = i;
        this.kD = d;
        this.kF = f;

        mainTalon.config_kP(0, p, 0);
        mainTalon.config_kI(0, i, 0);
        mainTalon.config_kD(0, d, 0);
        mainTalon.config_kF(0, f, 0);
    }



    public double getKP() {
        return kP;
    }

    public double getKI() {
        return kI;
    }

    public double getKD() {
        return kD;
    }

    public double getKF() {
        return kF;
    }

    /**
     * Returns the encoder position in ticks.
     * @return The encoder position in ticks.
     */
    public double getPosition() {
//        return mainTalon.getSelectedSensorPosition(0);
        return mainTalon.getSensorCollection().getQuadraturePosition();
    }

    /**
     * Returns the encoder velocity in ticks per 100ms.
     * @return the encoder velocity in ticks per 100ms.
     */
    public double getEncVelocity()
    {
        return mainTalon.getSelectedSensorVelocity(0);
    }

    /**
     * Change the quadrature reported position to 0, so changes in position are reported as relative to this.
     * see SensorCollection.setQuadraturePosition for more information
     */
    public void zeroEncoder() {
        mainTalon.getSensorCollection().setQuadraturePosition(0, 0);
    }

    /**
     * Returns whether or not the encoder is present on the lift.
     * @return Whether or not the encoder is present.
     */
    public boolean encoderPresent() {
        return mainTalon.getSensorCollection().getPulseWidthRiseToRiseUs() != 0;
    }

    /**
     * Returns whether or not the lift is at it's lower limit.
     * @return whether or not the lift is at it's lower limit.
     */
    public boolean isAtLowerLimit() {
        // TODO: Might need to change.
        return mainTalon.getSensorCollection().isRevLimitSwitchClosed();
    }

    /**
     * Sets the pneumatic brake to param locked.
     * Hopefully we won't have to use this much if the motorBrake works.
     * @param locked whether the brake is locked or not.
     */
    public void setBrake(boolean locked) {
        brake.set(!locked);
    }

    /**
     * Returns whether the pneumatic brake is active or not.
     * @return whether the pneumatic brake is active or not.
     */
    public boolean isBraking() {
        return !brake.get();
    }

    /**
     * Returns the current control mode.
     * @return The current control mode.
     */
    public ControlMode getCurrentControlMode() {
        return currentControlMode;
    }

    /**
     * Sets the current control mode.
     * @param controlMode The control mode to set the current control mode to.
     */
    public void setCurrentControlMode(ControlMode controlMode) {
        this.currentControlMode = controlMode;
    }

}






