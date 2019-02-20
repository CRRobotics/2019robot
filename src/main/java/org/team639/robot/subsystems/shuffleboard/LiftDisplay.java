package org.team639.robot.subsystems.shuffleboard;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import org.team639.robot.Constants;

public class LiftDisplay {
    private ShuffleboardTab tab = Shuffleboard.getTab("Lift");
    private NetworkTableEntry encoderPresent = tab
            .add("Encoder present", false)
            .withWidget(BuiltInWidgets.kBooleanBox)
            .withPosition(0, 0)
            .getEntry();

    private NetworkTableEntry encoderPosition = tab
            .add("Encoder position", 0)
            .withPosition(1, 0)
            .getEntry();

    private NetworkTableEntry limitSwitchStatus = tab
            .add("Limit switch status", false)
            .withWidget(BuiltInWidgets.kBooleanBox)
            .withPosition(2, 0)
            .getEntry();

    private NetworkTableEntry speedError = tab
            .add("Speed error", 0)
            .getEntry();

    private NetworkTableEntry speedP = tab
            .add("Drive p", Constants.Drivetrain.DRIVE_P)
            .withPosition(0, 3)
            .getEntry();
    private NetworkTableEntry driveI = tab
            .add("Drive i", Constants.Drivetrain.DRIVE_I)
            .withPosition(1, 3)
            .getEntry();
    private NetworkTableEntry driveD = tab
            .add("Drive d", Constants.Drivetrain.DRIVE_D)
            .withPosition(2, 3)
            .getEntry();

    public void setEncoderPresent(boolean present) {
        encoderPresent.setBoolean(present);
    }

    public void setEncoderPosition(double position) {
        encoderPosition.setDouble(position);
    }

    public void setLimitSwitchStatus(boolean status) {
        limitSwitchStatus.setBoolean(status);
    }
}
