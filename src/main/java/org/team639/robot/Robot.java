package org.team639.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import org.team639.robot.subsystems.Acquisition;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.team639.robot.subsystems.Lift;
import org.team639.robot.subsystems.Climbing;
import edu.wpi.first.wpilibj.command.Scheduler;
import org.team639.lib.commands.DriveThread;
import org.team639.robot.commands.drive.DriveLayout;
import org.team639.robot.sensors.LineFollower;
import org.team639.robot.subsystems.Drivetrain;
import org.team639.robot.subsystems.shuffleboard.LiftDisplay;

/**
 * The main loop of Redshift, Code Red Robotics' 2019 robot.
 */
public class Robot extends TimedRobot {
    public static final Acquisition acquisition = new Acquisition(
            RobotMap.lowerRollerExtension,
            RobotMap.upperRoller,
            RobotMap.lowerRoller,
            RobotMap.flowerOpen,
            RobotMap.flowerForward,
            RobotMap.cargoDetector,
            RobotMap.hatchDetector
    );

    public static final Drivetrain drivetrain = new Drivetrain(RobotMap.leftDriveMaster, RobotMap.rightDriveMaster, RobotMap.leftFollowers, RobotMap.rightFollowers, RobotMap.navx, new LineFollower(0));

    public static final Lift lift = new Lift(RobotMap.liftMainTalon, RobotMap.liftFollower, RobotMap.liftBrake);

    public static final Climbing climbing = new Climbing();

    public static DriveLayout getDriveLayout() {
        return DriveLayout.Arcade2JoystickRight;
    }

    private double liftMaxSpeed = 0;
    private LiftDisplay liftDisplay = new LiftDisplay();

    @Override
    public void robotInit() {
        DriveThread.getInstance().start();
        System.out.println("thread started");
        OI.mapButtons();
//        drivetrain.setControlMode(Drivetrain.Mode.OpenLoop);

        SmartDashboard.putNumber("trim", 0);
    }

    @Override
    public void disabledInit() {
        super.disabledInit();
    }

    @Override
    public void autonomousInit() {
        teleopInit();
    }

    @Override
    public void teleopInit() {
        super.teleopInit();
    }

    @Override
    public void testInit() {
        super.testInit();
    }

    @Override
    public void robotPeriodic() {
//        SmartDashboard.putNumber("right enc", drivetrain.getRightEncPos());
//        SmartDashboard.putNumber("left enc", drivetrain.getLeftEncPos());
//        SmartDashboard.putNumber("angle", drivetrain.getRobotAngle());
//
//        SmartDashboard.putNumber("tracked x", drivetrain.getTrackedX());
//        SmartDashboard.putNumber("tracked y", drivetrain.getTrackedY());
//
//        var left = drivetrain.getLeftEncVelocity();
//        if (left > lmax) lmax = left;
//        var right = drivetrain.getRightEncVelocity();
//        if (right > rmax) rmax = right;
//
//        SmartDashboard.putNumber("lmax", lmax);
//        SmartDashboard.putNumber("rmax", rmax);

//        SmartDashboard.putNumber("left vel", left);
//        SmartDashboard.putNumber("right vel", right);

//        System.out.println(drivetrain.encodersPresent());
        var liftSpeed = lift.getEncVelocity();

        if (liftSpeed > liftMaxSpeed) liftMaxSpeed = liftSpeed;
        SmartDashboard.putNumber("lift max speed", liftMaxSpeed);
        SmartDashboard.putNumber("lift position", lift.getPosition());

        liftDisplay.setEncoderPresent(lift.encoderPresent());
        liftDisplay.setEncoderPosition(lift.getPosition());
        liftDisplay.setLimitSwitchStatus(lift.isAtLowerLimit());
//        System.out.println(lift.isAtLowerLimit());

        SmartDashboard.putNumber("Robot pitch", drivetrain.getRobotPitch());
    }

    @Override
    public void disabledPeriodic() {
        RobotMap.bigCompressor.set(0);
    }

    @Override
    public void autonomousPeriodic() {
        teleopPeriodic();
    }

    @Override
    public void teleopPeriodic() {
//        System.out.println(RobotMap.pressureSwitch.get());
//        if (RobotMap.pressureSwitch.get()) {
            RobotMap.bigCompressor.set(0);
//        } else {
//            RobotMap.bigCompressor.set(0.5);
//        }
        Scheduler.getInstance().run();
    }

    @Override
    public void testPeriodic() {
        super.testPeriodic();
    }
}
