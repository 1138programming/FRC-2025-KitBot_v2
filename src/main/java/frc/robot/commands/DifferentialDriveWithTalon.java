package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.DifferentialDrivetrain;
import java.util.function.DoubleSupplier;

public class DifferentialDriveWithTalon extends Command {

    DoubleSupplier leftSpeed;
    DoubleSupplier rightSpeed;
    DifferentialDrivetrain drivetrain;

    public DifferentialDriveWithTalon(DifferentialDrivetrain drivetrain, DoubleSupplier leftSpeed, DoubleSupplier rightSpeed) {
        this.leftSpeed = leftSpeed;
        this.rightSpeed = rightSpeed;
        this.drivetrain = drivetrain;
        addRequirements(drivetrain);
    }

    @Override
    public void initialize() {
    }

    @Override
    public void execute() {
        drivetrain.driveArcadeTalon(rightSpeed, leftSpeed);
    }

    @Override
    public void end(boolean interrupted) {
        drivetrain.driveArcadeTalon(() -> 0.0, () -> 0.0);
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}
