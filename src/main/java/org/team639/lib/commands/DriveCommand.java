package org.team639.lib.commands;

import edu.wpi.first.wpilibj.command.Command;

/**
 * A command subclass for use with {@link DriveThread} via {@link ThreadedDriveCommand}.
 * @see ThreadedDriveCommand
 * @see DriveThread
 */
public abstract class DriveCommand extends Command {
    public DriveCommand(String name) {
        super(name);
    }

    @Override
    protected abstract void initialize();

    @Override
    protected abstract void execute();

    @Override
    protected abstract void end();

    @Override
    protected abstract void interrupted();

    @Override
    protected abstract boolean isFinished();

    @Override
    public synchronized void start() throws IllegalCallerException {
        throw new IllegalCallerException("This command should only be started through a ThreadedDriveCommand wrapper to run on a separate thread.");
    }
}
