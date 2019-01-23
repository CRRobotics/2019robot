package org.team639;

import org.team639.lib.controls.JoystickManager;
import org.team639.lib.controls.XBoxController;

public class OI {
    public static final JoystickManager drive = new XBoxController(0); // new DoubleLogitechAttack3(); // new LogitechF310(0);
    public static final JoystickManager controller = new XBoxController(1);

    public static void mapButtons() {}
}
