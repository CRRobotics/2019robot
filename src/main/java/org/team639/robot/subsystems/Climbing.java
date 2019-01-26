package org.team639.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.ControlMode;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Solenoid;
import org.team639.robot.commands.JoystickControlledClimb;

public class Climbing extends Subsystem {

    private TalonSRX climbMotor1;
    private TalonSRX climbMotor2;
    private DigitalInput highlimitswitch;
    private DigitalInput lowlimitswitch;
    //We might only use 1 piston. This is still very much under construction
    private Solenoid piston1;
    private Solenoid piston2;

    public void Climbing() {
        //Device numbers/channels at the moment are placeholders
        //Need to be changed in order to test code
        climbMotor1 = new TalonSRX(0);
        climbMotor2 = new TalonSRX(1);
        highlimitswitch = new DigitalInput(2);
        lowlimitswitch = new DigitalInput(3);
        piston1 = new Solenoid(4);
        piston2 = new Solenoid(5);
    }

    @Override
    public void initDefaultCommand()
    {
        setDefaultCommand(new JoystickControlledClimb());
    }

    /* Moves the climbing motors based on a parameter amount
     * between 1 and -1
     * Assumptions:
     * Values < 0 move the system down
     * Values > 0 move the system up
     * This might need to be reversed based on which way the motors
     * actually run.
     */
    public void moveSystem(double amount)
    {
        /* This checks the limit switches and makes sure that
           that the motors to dot run above them.
           This might need to be reversed depending on which way
           the motors actually run when it's tested
        */
        if ((highlimitswitch.get() && amount > 1)
                || (lowlimitswitch.get() && amount < 1))
        {
            //Prevents the motors from running past the limit switches
            climbMotor1.set(ControlMode.PercentOutput, 0);
            climbMotor2.set(ControlMode.PercentOutput, 0);
        }
        else
        {
            climbMotor1.set(ControlMode.PercentOutput, amount);
            climbMotor2.set(ControlMode.PercentOutput, amount);
        }
    }

    //Sets pistons 1 and 2 to a specified value
    public void setPistons(boolean open) {
        piston1.set(open);
        piston2.set(open);
    }

    //Accessor methods for components start here
    public TalonSRX getClimbMotor1() {
        return climbMotor1;
    }

    public TalonSRX getClimbMotor2() {
        return climbMotor2;
    }

    public DigitalInput getHighlimitswitch() {
        return highlimitswitch;
    }

    public DigitalInput getLowlimitswitch() {
        return lowlimitswitch;
    }

    public Solenoid getPiston1() {
        return piston1;
    }

    public Solenoid getPiston2() {
        return piston2;
    }
}
