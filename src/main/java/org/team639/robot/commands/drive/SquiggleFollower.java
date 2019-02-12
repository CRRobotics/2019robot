package org.team639.robot.commands.drive;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.team639.lib.commands.DriveCommand;
import org.team639.lib.math.PID;
import org.team639.lib.squiggles.ArcPathGenerator;
import org.team639.lib.squiggles.PathFollower;
import org.team639.lib.squiggles.Vector;
import org.team639.robot.Robot;
import org.team639.robot.subsystems.Drivetrain;

import static org.team639.robot.Constants.Drivetrain.TRACK_WIDTH_INCHES;

public class SquiggleFollower extends DriveCommand {
    private ArcPathGenerator.ArcPath path;
    private Drivetrain drivetrain = Robot.drivetrain;
    private State state;
    private int leftEncTicks;

    private PID anglePID;

    public SquiggleFollower() {
        super("SquiggleFollower");
        requires(this.drivetrain);
    }

    @Override
    protected void initialize() {
        path = ArcPathGenerator.generatePath(new Vector(0, 0), Math.PI / 2, new Vector(5 * 12, 5 * 12), 0);
        if (path.straightFirst) {
            state = State.Straight;
            leftEncTicks = drivetrain.getLeftEncPos();
        } else {
            state = State.Arc;
        }

        drivetrain.resetTracking();
        drivetrain.zeroRobotAngle();
        System.out.println("squigglefollower");
        System.out.println("radius: " + path.radius);
        System.out.println("straight: " + path.straightDistance);
        anglePID = new PID(10, 0, 0, 0.2, 0.5, 0.5, Math.toRadians(5), 0);
    }

    @Override
    protected void execute() {
        drivetrain.track();

        SmartDashboard.putNumber("tracked x", drivetrain.getTrackedX());
        SmartDashboard.putNumber("tracked y", drivetrain.getTrackedY());

        switch (state) {
            case Straight:
                if (drivetrain.getLeftEncPos() - leftEncTicks > path.straightDistance - 500) {
                    state = path.straightFirst ? State.Arc : State.Done;
                } else {
                    drivetrain.setSpeedsPercent(0.3, 0.3);
                }
                break;
            case Arc:
                if (Math.abs(Math.toRadians(drivetrain.getRobotAngle()) - path.endAngle) < Math.toRadians(5)) {
                    state = path.straightFirst ? State.Done : State.Straight;
                } else {
                    var outer = anglePID.compute(Math.abs(Math.toRadians(drivetrain.getRobotAngle())) - path.endAngle);
                    var inner = ArcPathGenerator.innerSpeed(outer, path.radius, TRACK_WIDTH_INCHES);
                    switch (path.direction) {
                        case Left:
                            drivetrain.setSpeedsPercent(inner, outer);
                            break;
                        case Right:
                            drivetrain.setSpeedsPercent(outer, inner);
                            break;
                    }
                }
                break;
            case Done:
                break;
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
        return state == State.Done;
    }

    enum State {
        Straight,
        Arc,
        Done
    }
}
