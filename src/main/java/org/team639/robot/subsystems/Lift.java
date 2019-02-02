package org.team639.robot.subsystems;


import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.wpi.first.wpilibj.command.Subsystem;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.StatusFrameEnhanced;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;
import org.team639.robot.Constants;
import org.team639.robot.RobotMap;

// Note - Encoder: 4096 ticks per rotation

public abstract class Lift extends Subsystem {

    private TalonSRX mainTalon;
    private TalonSRX followerTalon;

    private Solenoid brake;

    private ControlMode currentControlMode;

    private double kP;
    private double kI;
    private double kD;
    private double kF;

    public Lift() {

        mainTalon = new TalonSRX(6);
        followerTalon = new TalonSRX(7);

        followerTalon.follow(mainTalon);

        mainTalon.setInverted(true);
        followerTalon.setInverted(true);

        mainTalon.configReverseSoftLimitEnable(false, 0);

        mainTalon.configForwardSoftLimitEnable(true, 0);
        mainTalon.configForwardSoftLimitThreshold(Constants.LIFT_MAX_HEIGHT, 0);

        brake = RobotMap.getLiftBrake();

        setPID(Constants.LIFT_P, Constants.LIFT_I, Constants.LIFT_D, Constants.LIFT_F);
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


    public void setMotionMagicPosition(int tickCount) {
        mainTalon.set(ControlMode.MotionMagic, tickCount);
    }

    public int getEncPos() {
        return mainTalon.getSelectedSensorPosition(0);
    }

    public double getEncVelocity()
    {
        return mainTalon.getSelectedSensorVelocity(0);
    }

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

    public boolean isAtLowerLimit() {
        return mainTalon.getSensorCollection().isRevLimitSwitchClosed();
    }

    public void setBrake(boolean locked) {
        brake.set(locked);
    }

    public boolean isBraking() {
        return brake.get();

    }

    public ControlMode getCurrentControlMode() {
        return currentControlMode;
    }

    public void setcurrentControlMode(ControlMode controlMode) {
        this.currentControlMode = controlMode;
    }

}






