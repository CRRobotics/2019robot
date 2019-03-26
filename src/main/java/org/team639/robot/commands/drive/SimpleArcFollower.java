package org.team639.robot.commands.drive;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.team639.lib.commands.DriveCommand;
import org.team639.lib.math.AngleMath;
import org.team639.lib.math.PID;
import org.team639.lib.squiggles.ArcPathGenerator;
import org.team639.lib.squiggles.Vector;
import org.team639.robot.Robot;
import org.team639.robot.subsystems.Drivetrain;

import static org.team639.robot.Constants.Drivetrain.TICKS_PER_INCH;
import static org.team639.robot.Constants.Drivetrain.TRACK_WIDTH_INCHES;
import static org.team639.robot.Constants.Drivetrain.*;

public class SimpleArcFollower extends DriveCommand {
    private ArcPathGenerator.SimpleArcPath path;
    private Drivetrain drivetrain = Robot.drivetrain;
    private State state;
    private int leftEncTicks;

    private PID anglePID;
    private PID turnPID;

    public SimpleArcFollower() {
        super("SimpleArcFollower");
        requires(this.drivetrain);
    }

    @Override
    protected void initialize() {
        path = ArcPathGenerator.generateSimplePath(new Vector(0, 0), Math.PI / 2, new Vector(15 * 12, 5 * 12), 0);
        if (path.straightFirst) {
            state = State.Straight;
            leftEncTicks = drivetrain.getLeftEncPos();
        } else {
            state = State.Arc;
        }

        drivetrain.resetTracking();
        drivetrain.zeroRobotAngle();
        System.out.println("SimpleArcFollower");
        System.out.println("radius: " + path.radius);
        System.out.println("straight: " + path.straightDistance);
        anglePID = new PID(10, 0, 0, 0.45, 0.5, 0.5, Math.toRadians(5), 0);

        turnPID = new PID(AC_P, AC_I, AC_D, AC_MIN, AC_MAX, AC_RATE, AC_TOLERANCE, AC_I_CAP);
    }

    @Override
    protected void execute() {
        drivetrain.track();

        SmartDashboard.putNumber("tracked x", drivetrain.getTrackedX());
        SmartDashboard.putNumber("tracked y", drivetrain.getTrackedY());
        SmartDashboard.putNumber("angle", drivetrain.getRobotAngle());

        switch (state) {
            case Straight:
                if (drivetrain.getLeftEncPos() - leftEncTicks > path.straightDistance * TICKS_PER_INCH - 500) {
                    state = path.straightFirst ? State.Arc : State.Done;
                } else {
                    double error = AngleMath.shortestAngle(drivetrain.getRobotAngle(), path.straightAngle);
                    double output = turnPID.compute(error);
                    drivetrain.setSpeedsPercent(0.5 - output, 0.5 + output);
                }
                break;
            case Arc:
                if (Math.abs(Math.toRadians(drivetrain.getRobotAngle()) - path.endAngle) < Math.toRadians(5)) {
                    leftEncTicks = drivetrain.getLeftEncPos();
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
