package org.team639.robot.commands.lift;

import edu.wpi.first.wpilibj.command.Command;
import org.team639.lib.controls.JoystickManager;
import org.team639.lib.math.PID;
import org.team639.robot.OI;
import org.team639.robot.Robot;
import org.team639.robot.subsystems.Lift;

import static org.team639.robot.Constants.*;

/**
 * Facilitates changing the position of the lift with a joystick.
 */
public class MoveLiftWithJoystick extends Command {

    private Lift lift;
    private long brakeFire;

    /**
     * Moves the lift with a joystick.
     */
    public MoveLiftWithJoystick() {
        lift = Robot.lift;
        requires(lift);
    }

    /**
     * The initialize method is called the first time this Command is run after being started.
     */
    @Override
    protected void initialize() {
        lift.setBrake(false);
        brakeFire = System.currentTimeMillis();
    }

    /**
     * The execute method is called repeatedly until this Command either finishes or is canceled.
     */
    @Override
    protected void execute() {
        if ((System.currentTimeMillis() - brakeFire < 50)) return;
//        System.out.println(lift.encoderPresent());
        var val = OI.controller.getLeftStickY();
        if (Math.abs(val) > LIFT_JOYSTICK_DEADZONE) {
            if (lift.isBraking()) {
                lift.setBrake(false);
                brakeFire = System.currentTimeMillis();
            } else {
                lift.setSpeedPercent(val);
            }
        } else {
            lift.setSpeedPercent(0);
            lift.setBrake(true);
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
        return false;
    }

}
