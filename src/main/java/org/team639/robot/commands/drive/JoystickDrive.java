package org.team639.robot.commands.drive;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.team639.lib.commands.DriveCommand;
import org.team639.robot.Constants;
import org.team639.robot.OI;
import org.team639.lib.controls.XBoxController;
import org.team639.robot.Robot;
import org.team639.robot.subsystems.Drivetrain;

import static org.team639.robot.Constants.Drivetrain.MIN_DRIVE_PERCENT;

/**
 * Sets drivetrain speeds based on joystick input.
 */
public class JoystickDrive extends DriveCommand {
    private Drivetrain drivetrain;

    public double lmax = 0;
    public double rmax = 0;

    private double lastLeft = 0;
    private double lastRight = 0;
    public static final double maxDiff = 1 / 0.25 / 50;

    public JoystickDrive() {
        super("JoystickDrive");
        drivetrain = Robot.drivetrain;
        requires(drivetrain);
    }

    @Override
    protected void initialize() {
        System.out.println("joystickdrive init");

        SmartDashboard.putNumber("Drive P", Constants.Drivetrain.DRIVE_P);
        SmartDashboard.putNumber("Drive I", Constants.Drivetrain.DRIVE_I);
        SmartDashboard.putNumber("Drive D", Constants.Drivetrain.DRIVE_D);
    }

    @Override
    protected void execute() {
        drivetrain.track();

        double p = SmartDashboard.getNumber("Drive P", Constants.Drivetrain.DRIVE_P);
        double i = SmartDashboard.getNumber("Drive I", Constants.Drivetrain.DRIVE_I);
        double d = SmartDashboard.getNumber("Drive D", Constants.Drivetrain.DRIVE_D);

        drivetrain.setPIDF(p, i, d, Constants.Drivetrain.DRIVE_F);

        if (!drivetrain.encodersPresent()) drivetrain.setControlMode(Drivetrain.Mode.OpenLoop);
//        else drivetrain.setControlMode();

        double scale = 1 - (1 - MIN_DRIVE_PERCENT) * OI.drive.getControllerAxis(XBoxController.ControllerAxis.RightTrigger);
        if (scale < MIN_DRIVE_PERCENT) scale = MIN_DRIVE_PERCENT;
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
        speed = speed * 0.85;
        double turnMultiplier = 1 - speed;
        if (turnMultiplier < 1d / 2d) turnMultiplier = 1d / 2d;
        if (turnMultiplier > 4d / 5d) turnMultiplier = 4d / 5d;
        turning = turning * turnMultiplier;

        double left = speed + turning;
        double right = speed - turning;
//
        if(Math.signum(left) != Math.signum(lastLeft)) {
            lastLeft = 0;
        }

        if(Math.signum(right) != Math.signum(lastRight)) {
            lastRight = 0;
        }

        if(Math.abs(left) - Math.abs(lastLeft) > maxDiff) {
            left = (Math.abs(lastLeft) + maxDiff) * Math.signum(left);
        }

        if(Math.abs(right) - Math.abs(lastRight) > maxDiff){
            right += (Math.abs(lastRight) + maxDiff) * Math.signum(right);
        }

        lastLeft = left;
        lastRight = right;

        drivetrain.setSpeedsPercent(left, right);
    }
}
