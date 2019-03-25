package org.team639.robot.commands.lift;

import edu.wpi.first.wpilibj.command.Command;
import org.team639.lib.math.PID;
import org.team639.robot.Robot;
import org.team639.robot.subsystems.Lift;
import static org.team639.robot.Constants.*;

/**
 * Moves the lift to one of several preset positions defined in {@link LiftPosition}
 * @see LiftPosition
 * @see Lift
 */
public class MoveLiftToSetPosition extends Command {
    private Lift lift;
    private int position;
    private boolean done;
    private PID pid;

    public MoveLiftToSetPosition(int position) {
        this.position = position;
        lift = Robot.lift;
        requires(lift);
    }

    /**
     * Constructor, creates a new MoveLiftToSetPosition command with a LiftPosition to go to.
     * @param position The position to go to.
     */
    public MoveLiftToSetPosition(LiftPosition position) {
        this(position.getEncTicks());
    }

    /**
     * The initialize method is called the first time this Command is run after being started.
     */
    @Override
    protected void initialize() {
        done = false;
        lift.setBrake(false);
        if (!lift.encoderPresent()) done = true;
        pid = new PID(LIFT_POS_P, LIFT_POS_I, LIFT_POS_D, LIFT_MIN, LIFT_MAX, LIFT_RATE, LIFT_TOLERANCE, 0);
    }

    /**
     * The execute method is called repeatedly until this Command either finishes or is canceled.
     */
    @Override
    protected void execute() {
        double error = position - lift.getPosition();
        double speed;
        speed = pid.compute(error);
        lift.setSpeedPercent(speed);
        done = speed == 0;
    }

    /**
     * Called when the command ended peacefully. This is where you may want to wrap up loose ends,
     * like shutting off a motor that was being used in the command.
     */
    @Override
    protected void end() {
        lift.setSpeedPercent(0);
        lift.setBrake(true);
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
        return done;
    }

}