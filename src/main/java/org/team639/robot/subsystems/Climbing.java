package org.team639.robot.subsystems;

import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import edu.wpi.first.wpilibj.command.Subsystem;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.ControlMode;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Solenoid;
import org.team639.robot.commands.climbing.JoystickControlledClimb;

/*
 * Controls the functions of the climbing subsystem. This system has pneumatics that moves its
 * pivot points and motors that move it up and down.
 */
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
        climbMotor1.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 0);
        climbMotor2.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 0);
        //May set to inverted depending on how the motors run
        //climbMotor1.setInverted(true);
        //climbMotor1.setInverted(true);
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
     * @param speed The speed at which the motors are run, which will be scaled down 50%
     */
    public void moveSystem(double speed)
    {
        if ((highlimitswitch.get() && speed > 1)
                || (lowlimitswitch.get() && speed < 1))
        {
            //Prevents the motors from running past the limit switches
            climbMotor1.set(ControlMode.PercentOutput, 0);
            climbMotor2.set(ControlMode.PercentOutput, 0);
        }
        else
        {
            climbMotor1.set(ControlMode.PercentOutput, speed);
            climbMotor2.set(ControlMode.PercentOutput, speed);
        }
    }

    /*
     * Sets pistons 1 and 2 to a specified value
     * @param open If set to true, will open the pistons, if set to false it will close them.
     */
    public void setPistons(boolean open) {
        piston1.set(open);
        piston2.set(open);
    }

    /*
     * Returns the encoder position of a specified motor, assuming the encoder has index 0.
     * @param The motor to return an encoder position of
     */
    public int getEncoderPositions(TalonSRX motor)
    {
        return climbMotor1.getSelectedSensorPosition(0);
    }

}
