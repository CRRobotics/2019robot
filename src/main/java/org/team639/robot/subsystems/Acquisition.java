package org.team639.robot.subsystems;

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

    /**
     * @author Patrick Pfeifer
     * methods declaring minimum and maximum speeds of the roller motors (top roller and bottom roller)  in the forward and reverse directions
     */

    public void setBottomReverseSpeed(double speed) {
        bottomTalon.configPeakOutputReverse(speed);
    }

    public void setBottomForwardSpeed(double speed) {
        bottomTalon.configPeakOutputForward(speed);
    }

    public void setTopReverseSpeed(double speed) {
        topTalon.configPeakOutputReverse(speed);
    }

    public void setTopForwardSpeed(double speed) {
        topTalon.configPeakOutputForward(speed);
    }


    /**
     * setting positions of the auxiliary roller as stored or receiving to be used with motors or pistons
     */


    /**
     * method for moving roller positions inside of the drive train
     */
    public void setSpeedPercent(double topTalon, double BottomTalon) {

    }
}
