package org.team639.robot.commands.drive;

import org.team639.lib.commands.DriveCommand;
import org.team639.lib.math.PID;
import org.team639.robot.Robot;
import org.team639.robot.sensors.VisionTarget;
import org.team639.robot.subsystems.Drivetrain;

import java.util.Optional;

import static org.team639.robot.Constants.Drivetrain.*;

public class ApproachVisionTarget extends DriveCommand {
    private Drivetrain drivetrain;

    private Optional<VisionTarget> target;

    private PID anglePID;

    public ApproachVisionTarget() {
        super("ApproachVisionTarget");

        this.drivetrain = Robot.drivetrain;
        requires(this.drivetrain);
    }

    @Override
    protected void initialize() {
        target = drivetrain.getVisionTarget();

        anglePID = new PID(AC_P, AC_I, AC_D, AC_MIN, AC_MAX, AC_RATE, AC_TOLERANCE, AC_I_CAP);
    }

    @Override
    protected void execute() {
        if (target.isPresent()) {
            var t = target.get();

            var correction = anglePID.compute(t.angle - drivetrain.getRobotAngle());

            drivetrain.setSpeedsPercent(VISION_APPROACH_BASE_SPEED + correction, VISION_APPROACH_BASE_SPEED - correction);
        }
    }

    @Override
    protected void end() {

    }

    @Override
    protected void interrupted() {
        end();
    }

    @Override
    protected boolean isFinished() {
        return drivetrain.lineFollowerValue().isPresent() || target.isEmpty();
    }
}
