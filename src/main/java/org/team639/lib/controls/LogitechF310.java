package org.team639.lib.controls;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.command.Command;

import java.util.HashMap;
import java.util.Map;

public class LogitechF310 extends JoystickManager {

    private Joystick stick;
    private Map<Buttons, Button> btns;

    public enum Buttons implements ButtonType {
        X,
        Y,
        A,
        B,
        LB,
        RB,
        LeftJoyPress,
        RightJoyPress,
        POVUp,
        POVDown,
        POVRight,
        POVLeft,
        RightStickR,
        RightStickL,
        RightStickU,
        RightStickD
    }

    public enum ControllerAxis implements ControllerAxisType {
        LeftTrigger,
        RightTrigger
    }

    /**
     * Constructs a new Logitech F310 using port 0
     */
    public LogitechF310() {
        stick = new Joystick(0);
        initBtns();
    }


    /**
     * Constructs a new Logitech F310 using the specified port
     * @param port The USB port of the gamepad
     */
    public LogitechF310(int port) {
        stick = new Joystick(port);
        initBtns();
    }

    private void initBtns() {
        btns = new HashMap<>();
        btns.put(Buttons.A, new JoystickButton(stick, 1));
        btns.put(Buttons.B, new JoystickButton(stick, 2));
        btns.put(Buttons.X, new JoystickButton(stick, 3));
        btns.put(Buttons.Y, new JoystickButton(stick, 4));
        btns.put(Buttons.LB, new JoystickButton(stick, 5));
        btns.put(Buttons.RB, new JoystickButton(stick, 6));
        btns.put(Buttons.LeftJoyPress, new JoystickButton(stick, 9));
        btns.put(Buttons.RightJoyPress, new JoystickButton(stick, 10));
        btns.put(Buttons.POVUp, new Button() {
            @Override
            public boolean get() {
                return stick.getPOV() == 0;
            }
        });
        btns.put(Buttons.POVRight, new Button() {
            @Override
            public boolean get() {
                return stick.getPOV() == 90;
            }
        });
        btns.put(Buttons.POVLeft, new Button() {
            @Override
            public boolean get() {
                return stick.getPOV() == 270;
            }
        });
        btns.put(Buttons.POVDown, new Button() {
            @Override
            public boolean get() {
                return stick.getPOV() == 180;
            }
        });
        btns.put(Buttons.RightStickL, new Button() {
            @Override
            public boolean get() {
                return LogitechF310.this.getRightStickX() < -.9;
            }
        });
        btns.put(Buttons.RightStickR, new Button() {
            @Override
            public boolean get() {
                return LogitechF310.this.getRightStickX() > .9;
            }
        });
        btns.put(Buttons.RightStickD, new Button() {
            @Override
            public boolean get() {
                return LogitechF310.this.getRightStickY() < -.9;
            }
        });
        btns.put(Buttons.RightStickU, new Button() {
            @Override
            public boolean get() {
                return LogitechF310.this.getRightStickY() > .9;
            }
        });
    }


    /**
     * Returns the Y axis value of the left drive Joystick with forward being 1 and backward being -1
     *
     * @return The Y axis value of the left drive Joystick
     */
    @Override
    public double getLeftStickY() {
        return -1 * stick.getRawAxis(1);
    }

    /**
     * Returns the Y axis value of the right drive Joystick with forward being 1 and backward being -1
     *
     * @return The Y axis value of the right drive Joystick
     */
    @Override
    public double getRightStickY() {
        return -1 * stick.getRawAxis(5);
    }

    /**
     * Returns the X axis value of the left driveJoystick with to the right being 1 and to the left being -1
     *
     * @return The X axis value of the left drive Joystick
     */
    @Override
    public double getLeftStickX() {
        return stick.getRawAxis(0);
    }

    /**
     * Returns the X axis value of the left drive Joystick with to the right being 1 and to the left being -1
     *
     * @return The X axis value of the left drive Joystick
     */
    @Override
    public double getRightStickX() {
        return stick.getRawAxis(4);
    }

    /**
     * Maps the specified command to the specified button
     *
     * @param btn The location of the button
     * @param cmd The command to map
     * @param type The type of mapping
     */
    @Override
    public void mapButton(ButtonType btn, Command cmd, MappingType type) {
        if (!(btn instanceof Buttons)) {
            System.out.println("Missing button on LogitechF310. Are you sure you're using the correct enum?");
            return;
        }
        Button b = btns.get(btn);
        switch (type) {
            case WhenPressed:
                b.whenPressed(cmd);
                break;
            case WhenReleased:
                b.whenReleased(cmd);
                break;
            case WhileHeld:
                b.whileHeld(cmd);
                break;
            case CancelWhenPressed:
                b.cancelWhenPressed(cmd);
                break;
            case ToggleWhenPressed:
                b.toggleWhenPressed(cmd);
                break;
        }
    }



    /**
     * Returns whether a button is pressed
     * @param btn The button to retrieve
     * @return Whether or not the button is pressed.
     */
    @Override
    public boolean getButtonPressed(ButtonType btn) {
        if (!(btn instanceof Buttons)) {
            System.out.println("Missing button on LogitechF310. Are you sure you're using the correct enum?");
            return false;
        } else {
            return btns.get(btn).get();
        }
    }

    /**
     * Returns the value from the specified controller axis from -1 to 1
     *
     * @param axis The controller axis to return
     * @return the value from the specified controller axis
     */
    @Override
    public double getControllerAxis(ControllerAxisType axis) {
        if (!(axis instanceof ControllerAxis)) {
            System.out.println("Invalid controller axis requested");
            return 0;
        }
        ControllerAxis a = (ControllerAxis)axis;
        double val = 0;
        switch (a) {
            case LeftTrigger:
                val = stick.getRawAxis(2);
                break;
            case RightTrigger:
                val = stick.getRawAxis(3);
                break;
        }
        return val;
    }

    @Override
    public void setRightRumble(double rumble) {

    }

    @Override
    public void setLeftRumble(double rumble) {

    }

    @Override
    public void setAllRumble(double rumble) {

    }
}