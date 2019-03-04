package org.team639.robot.commands.lift;

import edu.wpi.first.wpilibj.command.ConditionalCommand;
import org.team639.robot.Robot;
import org.team639.robot.subsystems.Lift;

public class MoveToTop extends ConditionalCommand {

    private Lift lift;

    public MoveToTop() {
        super(new MoveLiftToSetPosition(LiftPosition.TOP_HATCH), new MoveLiftToSetPosition(LiftPosition.TOP_BALL));
        lift = Robot.lift;
        requires(lift);
    }

    @Override
    public boolean condition() {
        return false; // TODO: actually use condition
    }
}
