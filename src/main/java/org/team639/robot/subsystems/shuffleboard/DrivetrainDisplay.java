package org.team639.robot.subsystems.shuffleboard;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import org.team639.robot.Constants;

public class DrivetrainDisplay {
    private ShuffleboardTab tab = Shuffleboard.getTab("Drivetrain");
    private NetworkTableEntry leftError = tab
            .add("Left error", 0)
            .withWidget(BuiltInWidgets.kGraph)
            .withPosition(0, 0)
            .withSize(3, 3)
            .getEntry();
    private NetworkTableEntry rightError = tab
            .add("Right error", 0)
            .withWidget(BuiltInWidgets.kGraph)
            .withPosition(4, 0)
            .withSize(3, 3)
            .getEntry();

    private NetworkTableEntry driveP = tab
            .add("Drive p", Constants.Drivetrain.DRIVE_P)
            .withPosition(0, 3)
            .withWidget(BuiltInWidgets.kTextView)
            .getEntry();
    private NetworkTableEntry driveI = tab
            .add("Drive i", Constants.Drivetrain.DRIVE_I)
            .withPosition(1, 3)
            .withWidget(BuiltInWidgets.kTextView)
            .getEntry();
    private NetworkTableEntry driveD = tab
            .add("Drive d", Constants.Drivetrain.DRIVE_D)
            .withPosition(2, 3)
            .withWidget(BuiltInWidgets.kTextView)
            .getEntry();

    private DrivetrainDisplay() {

    }

    private static DrivetrainDisplay instance;

    public static synchronized DrivetrainDisplay getInstance() {
        if (instance == null) {
            instance = new DrivetrainDisplay();
        }

        return instance;
    }

    public void setLeftError(double error) {
        leftError.setDouble(error);
    }

    public void setRightError(double error) {
        rightError.setDouble(error);
    }

    public double getDriveP() {
        return driveP.getDouble(Constants.Drivetrain.DRIVE_P);
    }

    public double getDriveI() {
        return driveI.getDouble(Constants.Drivetrain.DRIVE_I);
    }

    public double getDriveD() {
        return driveD.getDouble(Constants.Drivetrain.DRIVE_D);
    }
}
