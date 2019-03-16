package org.team639.robot.commands.acqusition;

import edu.wpi.first.wpilibj.command.Command;
import org.team639.lib.controls.XBoxController;
import org.team639.robot.OI;
import org.team639.robot.Robot;
import org.team639.robot.subsystems.Acquisition;

import static org.team639.robot.Constants.Acquisition.MINIMUM_EXTENSION_SPEED;

public class ExtendLowerRollerWithTrigger extends Command {
    private Acquisition acquisition = Robot.acquisition;

    public ExtendLowerRollerWithTrigger() {
        requires(acquisition);
    }

    /**
     * The initialize method is called the first time this Command is run after being started.
     */
    @Override
    protected void initialize() {

    }

    /**
     * The execute method is called repeatedly until this Command either finishes or is canceled.
     */
    @Override
    protected void execute() {
        var right = OI.controller.getControllerAxis(XBoxController.ControllerAxis.RightTrigger);

        if (right < MINIMUM_EXTENSION_SPEED) {
            right = 0;
        }

        var out = -1 * right;

        if (OI.controller.getButtonPressed(XBoxController.Buttons.B)) {
            out *= -1;
        }

        acquisition.setLowerRollerExtensionSpeed(out);


    }

    /**
     * Called when the command ended peacefully. This is where you may want to wrap up loose ends,
     * like shutting off a motor that was being used in the command.
     */
    @Override
    protected void end() {
        acquisition.setLowerRollerExtensionSpeed(0);
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
        return false;
    }


}
