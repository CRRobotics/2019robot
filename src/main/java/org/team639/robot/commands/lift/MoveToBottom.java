package org.team639.robot.commands.lift;

import edu.wpi.first.wpilibj.command.ConditionalCommand;
import org.team639.robot.Robot;
import org.team639.robot.subsystems.Lift;

public class MoveToBottom extends ConditionalCommand {

    private Lift lift;

    public MoveToBottom() {
        super(new MoveLiftToSetPosition(LiftPosition.BOTTOM_HATCH), new MoveLiftToSetPosition(LiftPosition.BOTTOM_BALL));
        lift = Robot.getLift();
        requires(lift);
    }

    @Override
    public boolean condition() {
        return false; // TODO: actually use condition
    }
}
