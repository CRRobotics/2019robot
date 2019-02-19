package org.team639.robot.subsystems.shuffleboard;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;

public class DrivetrainDisplay {
    private ShuffleboardTab tab = Shuffleboard.getTab("Drivetrain");
    private NetworkTableEntry leftError = tab
            .add("Left error", 0)
            .withWidget(BuiltInWidgets.kGraph)
            .getEntry();
    private NetworkTableEntry rightError = tab
            .add("Right error", 0)
            .withWidget(BuiltInWidgets.kGraph)
            .getEntry();

    public void setLeftError(double error) {
        leftError.setDouble(error);
    }

    public void setRightError(double error) {
        rightError.setDouble(error);
    }
}
