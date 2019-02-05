package org.team639.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;
/**
 * The acquisition subsystem.
 *
 * Responsible for manipulating hatch panels and cargo.
 * @author Sara Xin
 */

public class Acquisition extends Subsystem {

    private TalonSRX topTalon;
    private TalonSRX bottomTalon;
    private Solenoid flowerOpen;
    private Solenoid flowerForward;

    /**
     * The constructor of the Acquisition class.
     * Initializes all private instance variables.
     */
    public Acquisition() {
        topTalon = new TalonSRX(4);
        bottomTalon = new TalonSRX(3);

        flowerOpen = new Solenoid(1);
        flowerForward = new Solenoid(2);


        topTalon.configFactoryDefault();
        bottomTalon.configFactoryDefault();

    }

    @Override
    public void initDefaultCommand() {
    }

    /**
     * Sets the flower to be open.
     *
     * @param open boolean indicating whether the flower should be open or not.
     */
    public void setFlowerOpen(boolean open) {
        flowerOpen.set(open);
    }

    public void setFlowerOpen(Solenoid flowerOpen) {
        this.flowerOpen = flowerOpen;

    }

    /**
     * Sets the flower to be forward.
     *
     * @param forward boolean indicating whether the flower should be positioned forward or not.
     */
    public void setFlowerForward(boolean forward) {
        flowerForward.set(forward);
    }

    public void setFlowerForward(Solenoid flowerForward) {
        this.flowerForward = flowerForward;
    }

    /**
     * @author Patrick Pfeifer
     * method declaring minimum and maximum speeds of the roller motors (top roller and bottom roller) as percent
     */
    public void setTopRollerSpeed(double speed) {
        if (speed > 1) speed = 1;
        else if (speed < -1) speed = -1;
        topTalon.set(ControlMode.PercentOutput, speed);
    }

    public void setBottomRollerSpeed(double speed) {
        if (speed > 1) speed = 1;
        else if (speed < -1) speed = -1;
        bottomTalon.set(ControlMode.PercentOutput, speed);
    }

    /**
     * method for moving roller positions inside of the drive train using motor ticks for the auxiliary roller and bottom roller separately
     */
    public void setAuxiliaryRollerPosiition(int AuxTicks) {
        topTalon.set(ControlMode.MotionMagic, AuxTicks);
    }

    public void setBottomRollerPosition(int BottomTicks) {
        bottomTalon.set(ControlMode.MotionMagic, BottomTicks);
    }
    /**
     * gets encoder values like 2017-18 lift's code. I hope motion magic works. It looks cool.
     * @param ?
     */
    public int getAuxRollerEncoderPosition() {
        return topTalon.getSelectedSensorPosition(0);
    }
    public void zeroEncoder() {
        topTalon.getSensorCollection().setQuadraturePosition(0, 0);
    }
    public boolean isAtOpenPosition() {
        return topTalon.getSensorCollection().isRevLimitSwitchClosed();
    }
 public boolean isAtStoredPosition() {
        return topTalon.getSensorCollection() .isRevLimitSwitchClosed();
 }
}
