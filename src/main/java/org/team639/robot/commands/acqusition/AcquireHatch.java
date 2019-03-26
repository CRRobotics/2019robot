package org.team639.robot.commands.acqusition;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.ConditionalCommand;
import org.team639.lib.commands.ExecuteCommandAndWait;
import org.team639.robot.Robot;

import static org.team639.robot.Constants.Acquisition.HATCH_EXTEND_DELAY;
import static org.team639.robot.Constants.Acquisition.HATCH_OPEN_DELAY;

public class AcquireHatch extends CommandGroup {
    public AcquireHatch() {
        addSequential(new ConditionalCommand(new ExecuteCommandAndWait(new CloseFlower(), HATCH_OPEN_DELAY)) {
            @Override
            protected boolean condition() {
                return Robot.acquisition.isFlowerOpen();
            }
        });

        addSequential(new ExecuteCommandAndWait(new ExtendFlower(), HATCH_EXTEND_DELAY));
        addSequential(new ExecuteCommandAndWait(new OpenFlower(), HATCH_OPEN_DELAY));
    }
}
