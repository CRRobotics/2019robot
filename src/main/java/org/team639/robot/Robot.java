package org.team639.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.team639.lib.commands.DriveThread;
import org.team639.lib.controls.XBoxController;
import org.team639.robot.commands.drive.DriveLayout;
import org.team639.robot.subsystems.Drivetrain;

import static org.team639.robot.Constants.Drivetrain.SPEED_RANGE;

public class Robot extends TimedRobot {
    public static final Drivetrain drivetrain = new Drivetrain(RobotMap.leftDriveMaster, RobotMap.rightDriveMaster, RobotMap.navx);;

    public static DriveLayout getDriveLayout() {
        return DriveLayout.Tank;
    }

    public double lmax = 0;
    public double rmax = 0;

    @Override
    public void robotInit() {
        DriveThread.getInstance().start();
        OI.mapButtons();
//        drivetrain.setControlMode(Drivetrain.Mode.OpenLoop);
    }

    @Override
    public void disabledInit() {
        super.disabledInit();
    }

    @Override
    public void autonomousInit() {
        super.autonomousInit();
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
//        drivetrain.track();
//
//        SmartDashboard.putNumber("right enc", drivetrain.getRightEncPos());
//        SmartDashboard.putNumber("left enc", drivetrain.getLeftEncPos());
//        SmartDashboard.putNumber("angle", drivetrain.getRobotAngle());
//
//        SmartDashboard.putNumber("tracked x", drivetrain.getTrackedX());
//        SmartDashboard.putNumber("tracked y", drivetrain.getTrackedY());
//
//        var left = drivetrain.getLeftEncVelocity();
////        if (left > lmax) lmax = left;
//        var right = drivetrain.getRightEncVelocity();
//        if (right > rmax) rmax = right;
//
//        SmartDashboard.putNumber("lmax", lmax);
//        SmartDashboard.putNumber("rmax", rmax);

//        SmartDashboard.putNumber("left vel", left);
//        SmartDashboard.putNumber("right vel", right);
    }

    @Override
    public void disabledPeriodic() {
        super.disabledPeriodic();
    }

    @Override
    public void autonomousPeriodic() {
        super.autonomousPeriodic();
    }

    @Override
    public void teleopPeriodic() {
        Scheduler.getInstance().run();
    }

    @Override
    public void testPeriodic() {
        super.testPeriodic();
    }
}
