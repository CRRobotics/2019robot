package org.team639.robot.commands.drive;

import edu.wpi.first.wpilibj.command.Command;
import org.team639.lib.squiggles.ArcPathGenerator;
import org.team639.lib.squiggles.Vector;
import org.team639.robot.Robot;
import org.team639.robot.subsystems.Drivetrain;

import java.util.List;

import static java.lang.Math.PI;
import static org.team639.robot.Constants.Drivetrain.TRACK_WIDTH_INCHES;

public class ComplexArcFollower extends Command {
    private Drivetrain drivetrain = Robot.drivetrain;

    private List<ArcPathGenerator.PathSegment> path;
    private int index = 0;

    private int leftEncTicks;

    public ComplexArcFollower() {
        requires(drivetrain);

        path = ArcPathGenerator.generateComplexPath(new Vector(0, 0), PI / 2.0, new Vector(5, 5), 0);
    }

    private void nextSegment() {
        index++;
        leftEncTicks = drivetrain.getLeftEncPos();
    }

    /**
     * The initialize method is called the first time this Command is run after being started.
     */
    @Override
    protected void initialize() {
        index = 0;
        leftEncTicks = drivetrain.getLeftEncPos();
    }

    /**
     * The execute method is called repeatedly until this Command either finishes or is canceled.
     */
    @Override
    protected void execute() {
        if (index >= path.size()) return;
        var segment = path.get(index);
        switch (segment.type) {
            case Arc:
                var arc = segment.arcSegment;
                if (Math.abs(Math.toRadians(drivetrain.getRobotAngle()) - arc.endAngle) < Math.toRadians(5)) {
                    nextSegment();
                } else {
                    var outer = 0.5;
                    var inner = ArcPathGenerator.innerSpeed(outer, arc.radius, TRACK_WIDTH_INCHES);
                    if (arc.direction == ArcPathGenerator.Direction.Left) {
                        drivetrain.setSpeedsPercent(inner, outer);
                    } else if (arc.direction == ArcPathGenerator.Direction.Right) {
                        drivetrain.setSpeedsPercent(outer, inner);
                    }
                }
                break;
            case Straight:
                var straight = segment.straightSegment;
                if (drivetrain.getLeftEncPos() - leftEncTicks > straight.distance - 500) {
                    nextSegment();
                } else {
                    drivetrain.setSpeedsPercent(0.3, 0.3);
                }
                break;
        }
    }

    /**
     * Called when the command ended peacefully. This is where you may want to wrap up loose ends,
     * like shutting off a motor that was being used in the command.
     */
    @Override
    protected void end() {
        drivetrain.setSpeedsPercent(0, 0);
    }

    /**
     * Called when the command ends because somebody called {@link Command#cancel() cancel()} or
     * another command shared the same requirements as this one, and booted it out.
     *
     * <p>This is where you may want to wrap up loose ends, like shutting off a motor that was being
     * used in the command.
     *
     * <p>Generally, it is useful to simply call the {@link Command#end() end()} method within this
     * method, as done here.
     */
    @Override
    protected void interrupted() {
        end();
    }

    /**
     * Returns whether this command is finished. If it is, then the command will be removed and {@link
     * Command#end() end()} will be called.
     *
     * <p>It may be useful for a team to reference the {@link Command#isTimedOut() isTimedOut()}
     * method for time-sensitive commands.
     *
     * @return whether this command is finished.
     * @see Command#isTimedOut() isTimedOut()
     */
    @Override
    protected boolean isFinished() {
        return index >= path.size();
    }
}
