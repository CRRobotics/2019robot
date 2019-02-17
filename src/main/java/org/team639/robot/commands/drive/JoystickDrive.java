package org.team639.robot.commands.drive;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.team639.lib.commands.DriveCommand;
import org.team639.robot.OI;
import org.team639.lib.controls.XBoxController;
import org.team639.robot.Robot;
import org.team639.robot.subsystems.Drivetrain;

/**
 * Sets drivetrain speeds based on joystick input.
 */
public class JoystickDrive extends DriveCommand {
    private Drivetrain drivetrain;

    public double lmax = 0;
    public double rmax = 0;

    public JoystickDrive() {
        super("JoystickDrive");
        drivetrain = Robot.drivetrain;
        requires(drivetrain);
    }

    @Override
    protected void initialize() {
        System.out.println("joystickdrive init");
    }

    @Override
    protected void execute() {
        drivetrain.track();

        if (!drivetrain.encodersPresent()) drivetrain.setControlMode(Drivetrain.Mode.OpenLoop);
//        else drivetrain.setControlMode();

        double scale = 1 - 0.8 * OI.drive.getControllerAxis(XBoxController.ControllerAxis.RightTrigger);
        if (scale < 0.2) scale = 0.2;
        switch (Robot.getDriveLayout()) {
            case Tank:
                tankDrive(OI.drive.getLeftStickY() * scale, OI.drive.getRightStickY() * scale);
                break;
            case Arcade1JoystickRight:
                arcadeDrive(OI.drive.getRightStickY() * scale, OI.drive.getRightStickX() * scale);
                break;
            case Arcade2JoystickLeft:
                arcadeDrive(OI.drive.getLeftStickY() * scale, OI.drive.getRightStickX() * scale);
                break;
            case Arcade2JoystickRight:
                arcadeDrive(OI.drive.getRightStickY() * scale, OI.drive.getLeftStickX() * scale);
                break;
        }

        var left = drivetrain.getLeftEncVelocity();
        if (left > lmax) lmax = left;
        var right = drivetrain.getRightEncVelocity();
        if (right > rmax) rmax = right;

        SmartDashboard.putNumber("left", drivetrain.getLeftEncVelocity());
        SmartDashboard.putNumber("right", drivetrain.getRightEncVelocity());


        SmartDashboard.putNumber("lmax", lmax);
        SmartDashboard.putNumber("rmax", rmax);
    }

    @Override
    protected void end() {}

    @Override
    protected void interrupted() {}

    @Override
    protected boolean isFinished() {
        return false;
    }

    /**
     * Takes two speed values from -1 to 1 and uses them for tank driving
     * @param lSpeed The value for the left side
     * @param rSpeed The value for the right side
     */
    private void tankDrive(double lSpeed, double rSpeed) {
        drivetrain.setSpeedsPercent(lSpeed, rSpeed);
    }

    /**
     * Performs arcade driving
     * @param speed The magnitude of speed from -1 to 1
     * @param turning The turning magnitude from -1 to 1
     */
    private void arcadeDrive(double speed, double turning) {
        speed = speed * 2 / 3;
        double turnMultiplier = 1 - speed;
        if (turnMultiplier < 1d / 3d) turnMultiplier = 1d / 3d;
        if (turnMultiplier > 2d / 3d) turnMultiplier = 2d / 3d;
        turning = turning * turnMultiplier;

        drivetrain.setSpeedsPercent(speed + turning, speed - turning);
    }
}
