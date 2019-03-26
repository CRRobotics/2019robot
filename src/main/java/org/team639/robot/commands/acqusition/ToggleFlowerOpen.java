package org.team639.robot.commands.acqusition;

import edu.wpi.first.wpilibj.command.ConditionalCommand;
import org.team639.robot.Robot;

public class ToggleFlowerOpen extends ConditionalCommand {

    public ToggleFlowerOpen() {
        super(new CloseFlower(), new OpenFlower());
    }
    /**
     * The Condition to test to determine which Command to run.
     *
     * @return true if m_onTrue should be run or false if m_onFalse should be run.
     */
    @Override
    protected boolean condition() {
        return Robot.acquisition.isFlowerOpen();
    }
}
