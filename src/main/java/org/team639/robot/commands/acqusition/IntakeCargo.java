package org.team639.robot.commands.acqusition;

import edu.wpi.first.wpilibj.command.Command;
import org.team639.robot.Robot;
import org.team639.robot.subsystems.Acquisition;

import static org.team639.robot.Constants.Acquisition.CARGO_INTAKE_SPEED;
import static org.team639.robot.Constants.Acquisition.CARGO_INTAKE_STALL;

/**
 * Intakes cargo, changing to a stall speed when a cargo has been acquired.
 */
public class IntakeCargo extends Command {
    private Acquisition acquisition;
    private boolean done = false;

    public IntakeCargo() {
        this.acquisition = Robot.acquisition;

        requires(this.acquisition);
    }

    /**
     * The initialize method is called the first time this Command is run after being started.
     */
    @Override
    protected void initialize() {
        if (!done) {
            acquisition.setLowerRollerSpeed(CARGO_INTAKE_SPEED);
            acquisition.setUpperRollerSpeed(CARGO_INTAKE_SPEED);

            acquisition.setFlowerForward(false);
            acquisition.setFlowerOpen(false);
        }
    }

    /**
     * The execute method is called repeatedly until this Command either finishes or is canceled.
     */
    @Override
    protected void execute() {
        if (acquisition.isCargoDetected()) {
            acquisition.setLowerRollerSpeed(CARGO_INTAKE_STALL);
            acquisition.setUpperRollerSpeed(CARGO_INTAKE_STALL);
        } else {
            acquisition.setLowerRollerSpeed(CARGO_INTAKE_SPEED);
            acquisition.setUpperRollerSpeed(CARGO_INTAKE_SPEED);
        }
    }

    /**
     * Returns whether this command is finished. If it is, then the command will be removed and {@link
     * Command#end() end()} will be called.
     *
     * <p>It may be useful for a team to reference the {@link Command#isTimedOut() isTimedOut()}
     * method for time-sensitive commands.
     *
     * for this.
     *
     * @return whether this command is finished.
     * @see Command#isTimedOut() isTimedOut()
     */
    @Override
    protected boolean isFinished() {
        return done;
    }

    /**
     * Called when the command ended peacefully. This is where you may want to wrap up loose ends,
     * like shutting off a motor that was being used in the command.
     */
    @Override
    protected void end() {
        acquisition.setLowerRollerSpeed(CARGO_INTAKE_STALL);
        acquisition.setUpperRollerSpeed(CARGO_INTAKE_STALL);
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
}
