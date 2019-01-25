package org.team639.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.TimedRobot;
import org.team639.robot.subsystems.Climbing;

public class Robot extends TimedRobot {

    //Port number may or may not need to be changed
    private static Joystick joystick = new Joystick(1);
    private static Climbing climbing = new Climbing();

    @Override
    public void robotInit() {
        super.robotInit();
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
        super.teleopPeriodic();
    }

    @Override
    public void testPeriodic() {
        super.testPeriodic();
    }

    public static Climbing getClimbing() {
        return climbing;
    }

    public static Joystick getJoystick() {
        return joystick;
    }
}
