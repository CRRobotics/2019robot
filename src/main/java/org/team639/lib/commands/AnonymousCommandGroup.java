package org.team639.lib.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 * A simple wrapper to construct commands groups anonymously.
 */
public abstract class AnonymousCommandGroup extends CommandGroup {
    public AnonymousCommandGroup() {
        init();
    }

    /**
     * Called in the anonymous command group's constructor. Put normal command group items here.
     */
    protected abstract void init();
}
