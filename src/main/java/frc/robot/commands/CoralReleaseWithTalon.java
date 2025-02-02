package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.CoralRelease;
import static frc.robot.Constants.CoralReleaseConstants.*;


import frc.robot.Robot;

public class CoralReleaseWithTalon extends Command{

    CoralRelease coralRelease;

    public CoralReleaseWithTalon(CoralRelease coralRelease) {
        this.coralRelease = coralRelease;
        addRequirements(coralRelease);
    }

    @Override
    public void initialize() {
    }

    @Override
    public void execute() {
        coralRelease.runRollerTalon(0.0, KROLLER_EJECT_VALUE);
    }

    @Override
    public void end(boolean interrupted) {
        coralRelease.runRollerTalon(0.0, 0.0);
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}
