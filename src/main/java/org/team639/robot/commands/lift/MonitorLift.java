package org.team639.robot.commands.lift;
import org.team639.robot.Constants.*;

/*
 * Unimplemented
 * Class for using the lift motor as a brake instead of a solenoid
 */
public class MonitorLift {

private double motorHoldingSpeed = 1.0;
private double motorHoldingSpeedPercent = 1.0;

public double getMotorHoldingSpeed()
{
    return motorHoldingSpeed;
}

public void setMotorHoldingSpeed(double motorHoldingSpeed)
{
    this.motorHoldingSpeed = motorHoldingSpeed;
}

public double getMotorHoldingSpeedPercent()
{
    return motorHoldingSpeedPercent;
}

public void setMotorHoldingSpeedPercent(double motorHoldingSpeedPercent)
{
    this.motorHoldingSpeedPercent = motorHoldingSpeedPercent;
}

}
