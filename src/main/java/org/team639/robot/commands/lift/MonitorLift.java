package org.team639.robot.commands.lift;
import com.ctre.phoenix.motorcontrol.ControlMode;
import org.team639.robot.Constants.*;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motion.*;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import com.ctre.phoenix.motorcontrol.ControlMode.*;
import org.team639.robot.subsystems.Lift;

/*
 * Unimplemented
 * Class for using the lift motor as a brake instead of a solenoid
 */
public class MonitorLift {

    private double motorHoldingSpeed = 1.0;
    private double motorHoldingSpeedPercent = 1.0;

    private static Lift lift;

    public double getMotorHoldingSpeed()
    {
        return motorHoldingSpeed;
    }

    public void setLift(Lift lift)
    {
        this.lift = lift;
    }

    public void setHolding()
    {
        
        lift.setMotionMagicPosition(lift.getEncPos());
        lift.setcurrentControlMode(ControlMode.MotionMagic);
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
