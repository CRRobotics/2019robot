package org.team639.robot.commands.drive;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import edu.wpi.first.wpilibj.command.Command;
import org.team639.robot.Robot;

public class SetDriveNeutralMode extends Command {
    private NeutralMode mode;

    /**
     * Creates a new command. The name of this command will be set to its class name.
     */
    public SetDriveNeutralMode(NeutralMode mode) {
        this.mode = mode;
    }

    /**
     * The initialize method is called the first time this Command is run after being started.
     */
    @Override
    protected void initialize() {
        Robot.drivetrain.setNeutralMode(mode);
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
        return true;
    }
}
