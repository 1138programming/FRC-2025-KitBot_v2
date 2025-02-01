package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.CoralRelease;
import java.util.function.DoubleSupplier;

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
        double forward = Robot.m_robotContainer.getXBoxLeftY();
        double reverse = Robot.m_robotContainer.getXBoxRightY();

        coralRelease.runRollerTalon(forward, reverse);
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
