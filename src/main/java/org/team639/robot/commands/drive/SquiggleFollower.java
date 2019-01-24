package org.team639.robot.commands.drive;

import edu.wpi.first.wpilibj.command.Command;
import org.team639.lib.squiggles.Squiggles;
import org.team639.robot.Robot;
import org.team639.robot.subsystems.Drivetrain;

public class SquiggleFollower extends Command {
    private Squiggles.PathFollower pf;
    private Drivetrain drivetrain = Robot.drivetrain;

    public SquiggleFollower() {
        requires(this.drivetrain);
    }

    @Override
    protected void initialize() {
        this.pf = new Squiggles.PathFollower(new Squiggles.DriveSignal(0, 0),0, 0, drivetrain.getRobotAngle(), new Squiggles.PFConfig(32.0 / 12.0, 1, 5, 2, 5));
    }

    @Override
    protected void execute() {
        var signal = pf.getDriveSignal();

        drivetrain.setSpeedsFeetPerSecond(signal.left, signal.right);
    }

    @Override
    protected void end() {
        pf.close();
    }

    @Override
    protected void interrupted() {
        end();
    }

    @Override
    protected boolean isFinished() {
        var status = pf.follow(drivetrain.getTrackedX(), drivetrain.getTrackedY(), drivetrain.getRobotAngle());
        return status == Squiggles.PathStatus.FINISHED || status == Squiggles.PathStatus.LOST;
    }
}
