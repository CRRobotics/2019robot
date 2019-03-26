package org.team639.robot.commands.drive;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class AutoAssist extends CommandGroup {
    public AutoAssist() {
        addSequential(new ApproachVisionTarget());
        addSequential(new FollowLine());
    }
}
