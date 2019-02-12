package org.team639.lib.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

public abstract class AnonymousCommandGroup extends CommandGroup {
    public AnonymousCommandGroup() {
        init();
    }

    protected abstract void init();
}
