package org.team639.robot.commands.drive;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import org.team639.lib.commands.DriveCommand;
import org.team639.lib.math.PID;
import org.team639.robot.Robot;
import org.team639.robot.subsystems.Drivetrain;

public class FollowLine extends DriveCommand {
    private Drivetrain drivetrain;
    private PID pid;

    private boolean done;

    public FollowLine() {
        super("FollowLine");

        this.drivetrain = Robot.drivetrain;

        requires(drivetrain);
    }

    @Override
    protected void initialize() {
        pid = new PID(0.2, 0, 0, 0, 0.5, 1, 0.1, 0);
        done = false;

        drivetrain.setNeutralMode(NeutralMode.Coast);
    }

    @Override
    protected void execute() {
        var error = drivetrain.lineFollowerValue();

        if (error.isPresent()) {
            var output = pid.compute(error.getAsDouble());

            drivetrain.setSpeedsPercent(0.2 + output, 0.2 - output);
        } else {
            done = true;
        }
    }

    @Override
    protected void end() {
        drivetrain.setSpeedsPercent(0, 0);
//        drivetrain.setNeutralMode(NeutralMode.Brake);
    }

    @Override
    protected void interrupted() {
        end();
    }

    @Override
    protected boolean isFinished() {
        return done;
    }
}
