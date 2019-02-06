package org.team639.robot.commands.drive;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.team639.lib.commands.DriveCommand;
import org.team639.lib.squiggles.PathFollower;
import org.team639.lib.squiggles.Vector;
import org.team639.robot.Robot;
import org.team639.robot.subsystems.Drivetrain;

import java.util.List;

import static org.team639.robot.Constants.Drivetrain.*;

public class SquiggleFollower extends DriveCommand {
    private PathFollower pf;
    private Drivetrain drivetrain = Robot.drivetrain;
    private long lastTime = 0;
    private boolean done = false;

    public SquiggleFollower() {
        super("SquiggleFollower");
        requires(this.drivetrain);
    }

    @Override
    protected void initialize() {
        var path = List.of(
                new Vector(0, 0),
                new Vector(0, 10 * 12.0),
                new Vector(7 * 12, 10 * 12)
        );
        this.pf = new PathFollower(path, 20, new PathFollower.RateLimiter(2.5 * 12.0, drivetrain.averageVelocity()), MAX_VELOCITY_INCHES_PER_SECOND, K_VELOCITY, TRACK_WIDTH_INCHES);
        lastTime = System.currentTimeMillis();
        drivetrain.resetTracking();
        drivetrain.zeroRobotAngle();
        done = false;
        System.out.println("squigglefollower");
    }

    @Override
    protected void execute() {
        drivetrain.track();

        SmartDashboard.putNumber("tracked x", drivetrain.getTrackedX());
        SmartDashboard.putNumber("tracked y", drivetrain.getTrackedY());

        var time = System.currentTimeMillis();
        var robotVector = new Vector(drivetrain.getTrackedX(), drivetrain.getTrackedY());
        var signal = pf.followWithTime(robotVector, drivetrain.getRobotAngle(), time - lastTime);

        lastTime = time;

        done = signal.isEmpty() || pf.distanceToEnd(robotVector) < 20;

        SmartDashboard.putNumber("distance to end", pf.distanceToEnd(robotVector));

        SmartDashboard.putNumber("left vel", drivetrain.getLeftEncVelocity());
        SmartDashboard.putNumber("right vel", drivetrain.getRightEncVelocity());

        SmartDashboard.putNumber("gyro", drivetrain.getRobotAngle());

        if (!done) {
            var sig = signal.get();
//            System.out.println("l:" + sig.left + "r ;"+sig.right);
            SmartDashboard.putNumber("left signal", sig.left);
            SmartDashboard.putNumber("right signal", sig.right);
            drivetrain.setSpeedsFeetPerSecond(sig.left / 12.0, sig.right / 12.0);
        } else {
            System.out.println("done");
        }
    }

    @Override
    protected void end() {
        drivetrain.setSpeedsPercent(0, 0);
    }

    @Override
    protected void interrupted() {
        end();
    }

    @Override
    protected boolean isFinished() {
        return done;
    }
}
