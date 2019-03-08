package org.team639.robot.subsystems;

import com.ctre.phoenix.motorcontrol.*;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.kauailabs.navx.frc.AHRS;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.team639.lib.commands.ThreadedDriveCommand;
import org.team639.lib.communication.UdpReceiver;
import org.team639.lib.subsystem.DriveSubsystem;
import org.team639.robot.Constants;
import org.team639.robot.commands.drive.DriveTracker;
import org.team639.robot.commands.drive.JoystickDrive;
import org.team639.robot.sensors.LineFollower;
import org.team639.robot.sensors.VisionTarget;
import org.team639.robot.subsystems.shuffleboard.DrivetrainDisplay;

import java.net.SocketException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Optional;
import java.util.OptionalDouble;

import static org.team639.robot.Constants.Drivetrain.*;

/**
 * The robot drivetrain hardware wrapper.
 */
public class Drivetrain extends DriveSubsystem {
    private final TalonSRX leftMaster;
    private final TalonSRX rightMaster;

    private volatile Mode controlMode = Mode.ClosedLoop;

    private LineFollower lineFollower;

    private final AHRS navx;

    private double kP = 0.1;
    private double kI = 0;
    private double kD = 0;
    private double kF = 0;

    private DriveTracker tracker;

    private UdpReceiver visionReceiver;
    private Thread visionRunner;

    public final DrivetrainDisplay display = DrivetrainDisplay.getInstance();

    /**
     * Creates a drivetrain with the given components.
     * @param leftMaster The master motor on the left side.
     * @param rightMaster The master motor on the right side.
     * @param navx The robot navx.
     */
    public Drivetrain(TalonSRX leftMaster, TalonSRX rightMaster, IMotorController[] leftFollowers, IMotorController[] rightFollowers, AHRS navx, LineFollower lineFollower) {
        this.leftMaster = leftMaster;
        this.rightMaster = rightMaster;
        this.navx = navx;
        this.lineFollower = lineFollower;

        for (IMotorController motorController : leftFollowers) motorController.follow(leftMaster);
        for (IMotorController motorController : rightFollowers) motorController.follow(rightMaster);

        this.leftMaster.configFactoryDefault();
        this.rightMaster.configFactoryDefault();

        this.leftMaster.setSensorPhase(true);
        this.rightMaster.setSensorPhase(true);

        setNeutralMode(NeutralMode.Coast);

        setPIDF(DRIVE_P, DRIVE_I, DRIVE_D, DRIVE_F);

        rightMaster.setInverted(true);
        for (IMotorController motorController : rightFollowers) motorController.setInverted(true);

        leftMaster.setStatusFramePeriod(StatusFrameEnhanced.Status_3_Quadrature, 5, 0);
        rightMaster.setStatusFramePeriod(StatusFrameEnhanced.Status_3_Quadrature, 5, 0);

        leftMaster.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 0);
        rightMaster.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 0);

        zeroRobotAngle();

        if (!encodersPresent()) controlMode = Mode.OpenLoop;

        tracker = new DriveTracker(0, 0, this);

        try {
            visionReceiver = new UdpReceiver(5810, 2);
            visionRunner = new Thread(visionReceiver);
            visionRunner.setDaemon(true);
            visionRunner.start();
        } catch (SocketException se) {
            System.err.println("Vision connection failed: " + se);
        }
    }

    /**
     * Takes two raw speed values and uses them to set the motors.
     * @param lSpeed The value for the left side
     * @param rSpeed The value for the right side
     */
    public void setSpeedsRaw(double lSpeed, double rSpeed) {
        double trim = SmartDashboard.getNumber("trim", 0);
        if (Math.abs(trim) >= 0.3) trim = Math.signum(trim) * 0.3;

        double lmod = 1, rmod = 1;
        if (trim < 0) {
            lmod = 1 + trim;
        } else {
            rmod = 1 - trim;
        }

        rightMaster.set(controlMode.ctreMode, rmod * rSpeed);
        leftMaster.set(controlMode.ctreMode, lmod * lSpeed);

        if (controlMode == Mode.ClosedLoop) {
            display.setLeftError(lSpeed - getLeftEncVelocity());
            display.setRightError(rSpeed - getRightEncVelocity());

            SmartDashboard.putNumber("left error", lSpeed - getLeftEncVelocity());
            SmartDashboard.putNumber("right error", rSpeed - getRightEncVelocity());
        }

        SmartDashboard.putNumber("left command", lSpeed);
        SmartDashboard.putNumber("right command", rSpeed);
        SmartDashboard.putNumber("side difference", lSpeed - rSpeed);

        SmartDashboard.putNumber("left actual", getLeftEncVelocity());
        SmartDashboard.putNumber("right actual", getRightEncVelocity());
        SmartDashboard.putNumber("actual side difference", getLeftEncVelocity() - getRightEncVelocity());
    }

    /**
     * Takes two speed values from -1 to 1 and uses them to set the motors
     * @param lSpeed The value for the left side
     * @param rSpeed The value for the right side
     */
    public void setSpeedsPercent(double lSpeed, double rSpeed) {
        // Limits speeds to the range [-1, 1]
        if (Math.abs(lSpeed) > 1) lSpeed = lSpeed < 0 ? -1 : 1;
        if (Math.abs(rSpeed) > 1) rSpeed = rSpeed < 0 ? -1 : 1;
        switch (controlMode) {
            case OpenLoop:
                setSpeedsRaw(lSpeed, rSpeed);
                break;
            case ClosedLoop:
                double ls = lSpeed * SPEED_RANGE;
                double rs = rSpeed * SPEED_RANGE;
                setSpeedsRaw(ls, rs);
                break;
        }
    }

    /**
     * Sets the speed of the robot in feet per second.
     * @param left The speed of the left side of the robot in feet per second.
     * @param right The speed of the right side of the robot in feet per second.
     */
    public void setSpeedsFeetPerSecond(double left, double right) {
        setControlMode(Mode.ClosedLoop);
        setSpeedsRaw(left * FPS_TO_MOTOR_UNITS, right * FPS_TO_MOTOR_UNITS);
    }

    /**
     * Sets the PIDF constants.
     * @param p The P constant.
     * @param i The I constant.
     * @param d The D constant.
     * @param f The F constant.
     */
    public void setPIDF(double p, double i, double d, double f) {
        kP = p;
        kI = i;
        kD = d;
        kF = f;
        rightMaster.config_kP(0, kP);
        rightMaster.config_kI(0, kI);
        rightMaster.config_kD(0, kD);
        rightMaster.config_kF(0, kF);
        leftMaster.config_kP(0, kP);
        leftMaster.config_kI(0, kI);
        leftMaster.config_kD(0, kD);
        leftMaster.config_kF(0, kF);

    }

    /**
     * Changes the neutral mode of the motor controllers.
     * @param mode The neutral mode.
     */
    public void setNeutralMode(NeutralMode mode) {
        leftMaster.setNeutralMode(mode);
        rightMaster.setNeutralMode(mode);
    }

    /**
     * Returns whether or not the encoders are both connected.
     * @return Whether or not the encoders are both connected.
     */
    public boolean encodersPresent() {
        // We have to assume that the encoders are present if we aren't running on the real robot.
        return !Constants.REAL || !(rightMaster.getSensorCollection().getPulseWidthRiseToRiseUs() == 0 || leftMaster.getSensorCollection().getPulseWidthRiseToRiseUs() == 0);
    }

    /**
     * Gets the current angle of the robot from 0-360 degrees, with 90 being directly downfield. This assumes that the robot starts facing downfield.
     * @return The current angle of the robot
     */
    public double getRobotAngle() {
        double angle = navx.getYaw();
        angle *= -1;
        angle += 90;
        if (angle < 0) angle = 360 + angle;
        return angle;
    }

    /**
     * Checks if the NavX is connected.
     * @return If the NavX is connected.
     */
    public boolean isNavXPresent() {
        return navx.isConnected();
    }

    /**
     * Zeroes the robot Angle.
     */
    public void zeroRobotAngle() {
        navx.zeroYaw();
    }

    /**
     * Returns the P constant of the drivetrain.
     * @return the P constant of the drivetrain.
     */
    public double getkP() {
        return kP;
    }

    /**
     * Returns the I constant of the drivetrain.
     * @return the I constant of the drivetrain.
     */
    public double getkI() {
        return kI;
    }

    /**
     * Returns the D constant of the drivetrain.
     * @return the D constant of the drivetrain.
     */
    public double getkD() {
        return kD;
    }

    /**
     * Returns the F constant of the drivetrain.
     * @return the F constant of the drivetrain.
     */
    public double getkF() {
        return kF;
    }

    /**
     * Returns the position of the left encoder
     * @return The position of the left encoder
     */
    public int getLeftEncPos() {
        return leftMaster.getSelectedSensorPosition(0);
    }

    /**
     * Reutrns the position of the right encoder
     * @return The position of the right encoder
     */
    public int getRightEncPos() {
        return rightMaster.getSelectedSensorPosition(0);
    }

    /**
     * Returns the velocity of the left encoder
     * @return The velocity of the left encoder
     */
    public int getLeftEncVelocity() {
        return leftMaster.getSelectedSensorVelocity(0);
    }

    /**
     * Returns the velocity of the right encoder
     * @return The velocity of the right encoder
     */
    public int getRightEncVelocity() {
        return rightMaster.getSelectedSensorVelocity(0);
    }

    public Mode getControlMode() {
        return controlMode;
    }

    public synchronized void setControlMode(Mode controlMode) {
        this.controlMode = controlMode;
    }

    @Override
    protected void initDefaultCommand() {
        setDefaultCommand(new ThreadedDriveCommand(new JoystickDrive(), this));
    }

    /**
     * Returns the tracked x position of the robot.
     * @return The tracked x position of the robot.
     */
    public double getTrackedX() {
        return tracker.getX();
    }

    /**
     * Returns the tracked y position of the robot.
     * @return The tracked y position of the robot.
     */
    public double getTrackedY() {
        return tracker.getY();
    }

    /**
     * Updates the drive tracker.
     */
    public void track() {
        tracker.collect();
    }

    /**
     * Resets the drive tracker to (0, 0)
     */
    public void resetTracking() {
        tracker.reset(0, 0);
    }

    @Override
    public void threadConstant() {
        track();
    }

    public OptionalDouble lineFollowerValue() {
        return lineFollower.getRawPosition();
    }

    public Optional<VisionTarget> getVisionTarget() {
        if (visionReceiver != null && visionRunner.isAlive()) {
            var buf = visionReceiver.getCurrentBuffer();
            if (buf.length < 2 || buf[0] < 0) return Optional.empty();
            ByteBuffer b = ByteBuffer.wrap(buf);
            b.order(ByteOrder.BIG_ENDIAN);
            float dst = b.getFloat(0);
            float angle = b.getFloat(1);
            return Optional.of(new VisionTarget(dst, getRobotAngle() - angle));
        } else {
            return Optional.empty();
        }
    }


    public double getRobotPitch() {
        return navx.getRoll();
    }

    /**
     * Represents the possible modes that the drivetrain can be in.
     */
    public enum Mode {
        ClosedLoop(ControlMode.Velocity),
        OpenLoop(ControlMode.PercentOutput);

        ControlMode ctreMode;

        Mode(ControlMode mode) {
            this.ctreMode = mode;
        }
    }
}
