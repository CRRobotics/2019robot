package org.team639.robot.subsystems;



import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.wpi.first.wpilibj.command.Subsystem;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.StatusFrameEnhanced;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;
import org.team639.robot.Constants;


public abstract class Lift extends Subsystem {

    private final int lift_max_height;
    private TalonSRX mainTalon;
    private TalonSRX followerTalon;

    private Solenoid brake;

    private ControlMode currentControlMode;

    private double kP;
    private double kI;
    private double kD;
    private double kF;

    public Lift() {
        lift_max_height = 185;

        mainTalon = new TalonSRX(6);
        followerTalon = new TalonSRX(7);

        followerTalon.follow(mainTalon);

        mainTalon.setInverted(true);
        followerTalon.setInverted(true);

        mainTalon.configReverseSoftLimitEnable(false, 0);

        mainTalon.configForwardSoftLimitEnable(true, 0);
        mainTalon.configForwardSoftLimitThreshold(Constants.LIFT_MAX_HEIGHT, 0);
    }

        public void setMotionMagicPosition(int tickCount) {
            mainTalon.set(ControlMode.MotionMagic, tickCount);

        }

        public double getEncPos(){
            return mainTalon.getSelectedSensorPosition(0);


}

        public void zeroEncoder(){
            mainTalon.getSensorCollection().setQuadraturePosition(0,0);
        }

        public boolean isAtLowerLimit() {
            return mainTalon.getSensorCollection().isRevLimitSwitchClosed();
        }

            public void setBrake ( boolean locked){
                brake.set(!locked);
            }

            public boolean isBraking () {
                return !brake.get();

            }

            public ControlMode getCurrentControlMode () {
                return currentControlMode;

            }


            public void setcurrentControlMode (ControlMode controlMode) {
                this.currentControlMode = controlMode;
            }

}






