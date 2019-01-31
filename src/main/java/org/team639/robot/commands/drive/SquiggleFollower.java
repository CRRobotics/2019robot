package org.team639.robot.commands.drive;

import edu.wpi.first.wpilibj.command.Command;
import org.team639.lib.squiggles.PathFollower;
import org.team639.lib.squiggles.Vector;
import org.team639.robot.Robot;
import org.team639.robot.subsystems.Drivetrain;

import java.util.List;

import static org.team639.robot.Constants.Drivetrain.*;

public class SquiggleFollower extends Command {
    private PathFollower pf;
    private Drivetrain drivetrain = Robot.drivetrain;
    private long lastTime = 0;
    private boolean done = false;

    public SquiggleFollower() {
        requires(this.drivetrain);
    }

    @Override
    protected void initialize() {
        var path = List.of(
                new Vector(0, 0),
                new Vector(0, 5 * 12.0),
                new Vector(5, 5 * 12.0)
        );
        this.pf = new PathFollower(path, 0.5, new PathFollower.RateLimiter(2.5 * 12.0, drivetrain.averageVelocity()), MAX_VELOCITY_INCHES_PER_SECOND, K_VELOCITY, TRACK_WIDTH_INCHES);
        lastTime = System.currentTimeMillis();
        done = false;
    }

    @Override
    protected void execute() {
        var time = System.currentTimeMillis();
        var signal = pf.followWithTime(new Vector(drivetrain.getTrackedX(), drivetrain.getTrackedY()), drivetrain.getRobotAngle(), time - lastTime);

        lastTime = time;

        done = signal.isEmpty();

        if (!done) {
            var sig = signal.get();
            drivetrain.setSpeedsFeetPerSecond(sig.left / 12.0, sig.right / 12.0);
        }
    }

    @Override
    protected void end() {

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
