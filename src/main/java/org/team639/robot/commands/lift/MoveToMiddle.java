package org.team639.robot.commands.lift;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.ConditionalCommand;
import org.team639.robot.OI;
import org.team639.robot.Robot;
import org.team639.robot.subsystems.Lift;

public class MoveToMiddle extends ConditionalCommand {

    private Lift lift;

    public MoveToMiddle()
    {
        super(new MoveLiftToSetPosition(LiftPosition.MIDDLE_HATCH), new MoveLiftToSetPosition(LiftPosition.MIDDLE_BALL));
        lift = Robot.getLift();
        requires(lift);
    }

    @Override
    public boolean condition()
    {
        return OI.isHatch();
    }
}
