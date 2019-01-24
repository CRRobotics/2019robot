package org.team639.robot.commands.drive;

import org.team639.lib.math.AngleMath;
import org.team639.robot.subsystems.Drivetrain;

import static org.team639.robot.Constants.Drivetrain.TICKS_PER_INCH;

/**
 * A class that approximates the position of the robot by monitoring the encoders and gyro.
 */
public class DriveTracker {
    private Drivetrain drivetrain;

    private double x;
    private double y;

    private double lastRightDist;
    private double lastLeftDist;

    private double lastAngle;

    public DriveTracker(double startX, double startY, Drivetrain drivetrain) {
        this.drivetrain = drivetrain;
        reset(startX, startY);
    }

    /**
     * Uses current information to update approximated position. Should be called as often as possible.
     */
    public void collect() {
        double l = drivetrain.getLeftEncPos() / TICKS_PER_INCH;
        double r = drivetrain.getRightEncPos() / TICKS_PER_INCH;
        double a = drivetrain.getRobotAngle();

        double angle = a + AngleMath.shortestAngle(a, lastAngle) / 2;
        angle = AngleMath.constrainTo360(angle);
        lastAngle = a;

        double avg = ((l - lastLeftDist) + (r - lastRightDist)) / 2;

        lastRightDist = r;
        lastLeftDist = l;

        x += Math.cos(Math.toRadians(angle)) * avg;
        y += Math.sin(Math.toRadians(angle)) * avg;
    }

    /**
     * Resets the approximated position to the specified point.
     * @param x The x value to reset to.
     * @param y The y value to reset to.
     */
    public void reset(double x, double y) {
        this.x = x;
        this.y = y;

        lastRightDist = drivetrain.getRightEncPos() / TICKS_PER_INCH;
        lastLeftDist = drivetrain.getLeftEncPos() / TICKS_PER_INCH;

        lastAngle = drivetrain.getRobotAngle();
    }

    /**
     * Returns the approximated current x position.
     * @return The approximated current x position.
     */
    public double getX() {
        return x;
    }

    /**
     * Returns the approximated current y position.
     * @return The approximated current y position.
     */
    public double getY() {
        return y;
    }
}