package org.team639.lib.commands;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;

/**
 * Executes a command and then waits after it finishes.
 */
public class ExecuteCommandAndWait extends CommandGroup {
    public ExecuteCommandAndWait(Command cmd, double delayInSeconds) {
        addSequential(cmd);
        addSequential(new WaitCommand(delayInSeconds));
    }
}
