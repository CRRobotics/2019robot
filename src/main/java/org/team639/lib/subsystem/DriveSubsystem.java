package org.team639.lib.subsystem;

import edu.wpi.first.wpilibj.command.Subsystem;

public abstract class DriveSubsystem extends Subsystem {
    protected DriveSubsystem() {
        super("Drivetrain");
    }

    public abstract void threadConstant();
}
