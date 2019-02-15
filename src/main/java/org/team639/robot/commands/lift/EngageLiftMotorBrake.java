package org.team639.robot.commands.lift;
import com.ctre.phoenix.motorcontrol.ControlMode;
import edu.wpi.first.wpilibj.command.Command;
import org.team639.lib.math.PID;
import org.team639.robot.Constants.*;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motion.*;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import com.ctre.phoenix.motorcontrol.ControlMode.*;
import org.team639.robot.Robot;
import org.team639.robot.subsystems.Lift;

import static org.team639.robot.Constants.*;

/*
 * Unimplemented
 * Class for using the lift motor as a brake instead of a solenoid
 */
public class EngageLiftMotorBrake extends Command {

    private Lift lift;
    private LiftPosition position;
    private boolean running;
    private PID pid;

    public EngageLiftMotorBrake(LiftPosition position) {
        this.position = position;
        lift = Robot.getLift();
        requires(lift);
    }

    /**
     * The initialize method is called the first time this Command is run after being started.
     */
    @Override
    protected void initialize() {
        running = true;
        if (!lift.encoderPresent()) running = false;
        pid = new PID(LIFT_POS_P, LIFT_POS_I, LIFT_POS_D, LIFT_MIN, LIFT_MAX, LIFT_RATE, LIFT_TOLERANCE, 0);
    }

    /**
     * The execute method is called repeatedly until this Command either finishes or is canceled.
     */
    @Override
    protected void execute() {
        int error = position.getEncTicks() - lift.getEncPos();
        double speed;
        speed = pid.compute(error);
        lift.setSpeedPercent(speed);
    }

    /**
     * Called when the command ended peacefully. Should be called when the motor break is disengaged
     */
    @Override
    protected void end() {

    }

    /**
     * Called when the command ends because somebody called {@link Command#cancel() cancel()} or
     * another command shared the same requirements as this one, and booted it out.
     * <p>
     * <p>This is where you may want to wrap up loose ends, like shutting off a motor that was being
     * used in the command.
     * <p>
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
     * <p>
     * <p>It may be useful for a team to reference the {@link Command#isTimedOut() isTimedOut()}
     * method for time-sensitive commands.
     * @return whether this command is finished.
     * @see Command#isTimedOut() isTimedOut()
     */
    @Override
    protected boolean isFinished() {
        return !running;
    }
}
