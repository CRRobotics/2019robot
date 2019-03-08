package org.team639.robot.commands.climbing;

import edu.wpi.first.wpilibj.command.Command;
import org.team639.lib.math.PID;
import org.team639.robot.Robot;
import org.team639.robot.subsystems.Climbing;
import org.team639.robot.subsystems.Lift;

public class CoordinatedClimb extends Command {
    private Climbing climbing = Robot.climbing;
    private Lift lift = Robot.lift;

    private PID pid;

    public CoordinatedClimb() {
        requires(climbing);
        requires(lift);
    }

    /**
     * The initialize method is called the first time this Command is run after being started.
     */
    @Override
    protected void initialize() {
        pid = new PID(0.01, 0, 0, 0, 0.7, 0.05, 5, 0);
        lift.setBrake(false);
        climbing.setBrake(false);
    }

    /**
     * The execute method is called repeatedly until this Command either finishes or is canceled.
     */
    @Override
    protected void execute() {
        double error = Robot.drivetrain.getRobotPitch(); // shhh
        var output = pid.compute(error);

        if (climbing.isFullyExtended()) {
            climbing.setSpeed(0);
            climbing.setBrake(true);
        } else {
            climbing.setBrake(false);
            climbing.setSpeed(-0.5);
        }

        if (lift.isAtLowerLimit()) {
            lift.setSpeedPercent(0);
            lift.setBrake(true);
        } else {
            lift.setBrake(false);
            lift.setSpeedPercent(-0.25 + output);
        }
    }

    /**
     * Called when the command ended peacefully. This is where you may want to wrap up loose ends,
     * like shutting off a motor that was being used in the command.
     */
    @Override
    protected void end() {
        lift.setSpeedPercent(0);
        lift.setBrake(true);

        climbing.setSpeed(0);
        climbing.setBrake(true);
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
     * @return whether this command is finished.
     * @see Command#isTimedOut() isTimedOut()
     */
    @Override
    protected boolean isFinished() {
        return lift.isAtLowerLimit() && climbing.isFullyExtended();
    }
}
