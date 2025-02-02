package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.DifferentialDrivetrain;
import java.util.function.DoubleSupplier;
import static frc.robot.Constants.DriveConstants.*;

import frc.robot.Robot;

public class DifferentialDriveWithTalon extends Command {

    DoubleSupplier leftSpeed;
    DoubleSupplier rightSpeed;
    DifferentialDrivetrain drivetrain;

    public DifferentialDriveWithTalon(DifferentialDrivetrain drivetrain) {
        this.drivetrain = drivetrain;
        addRequirements(drivetrain);
    }

    @Override
    public void initialize() {
    }

    @Override
    public void execute() {
        double lspeed = Robot.m_robotContainer.getXBoxLeftY();
        double rspeed = Robot.m_robotContainer.getXBoxRightY();

        drivetrain.driveArcadeTalon(lspeed * KDRIVE_PERCENT_LIMIT, rspeed * KDRIVE_PERCENT_LIMIT);
    }

    @Override
    public void end(boolean interrupted) {
        drivetrain.driveArcadeTalon(0.0, 0.0);
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}
