package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.DifferentialDrivetrain;

public class Autonomous {

    public static final Command moveforward(DifferentialDrivetrain drivetrain) {
        return drivetrain.driveArcade(drivetrain, () -> 0.5, () -> 0.0).withTimeout(1.0);
        //drive forward 50% speed for 1 sec example DO NOT USE
    }

    public static final Command doAbsolutelyNothing(DifferentialDrivetrain drivetrain) {
        return drivetrain.driveArcade(drivetrain, () -> 0.0, () -> 0.0).withTimeout(3.0);
    }
}
