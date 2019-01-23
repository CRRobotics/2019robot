package org.team639.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Scheduler;
import org.team639.OI;
import org.team639.robot.commands.drive.DriveLayout;
import org.team639.robot.subsystems.Drivetrain;

public class Robot extends TimedRobot {
    public static final Drivetrain drivetrain = new Drivetrain(RobotMap.leftDriveMaster, RobotMap.rightDriveMaster, RobotMap.navx);

    public static DriveLayout getDriveLayout() {
        return DriveLayout.Arcade2JoystickRight;
    }

    @Override
    public void robotInit() {
        OI.mapButtons();
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
        super.robotPeriodic();
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
