package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.CoralReleaseConstants;
import java.util.function.DoubleSupplier;

import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkBase.ResetMode;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.config.SparkMaxConfig;


public class CoralRelease extends SubsystemBase {

    private final SparkMax rollerMotor;

    public CoralRelease () {
        rollerMotor = new SparkMax (CoralReleaseConstants.KROLLER_MOTOR_ID, MotorType.kBrushed);
        rollerMotor.setCANTimeout(250);

        SparkMaxConfig rollerConfig = new SparkMaxConfig();
        rollerConfig.voltageCompensation(CoralReleaseConstants.KROLLER_MOTOR_VOLTAGE_COMP);
        rollerConfig.smartCurrentLimit(CoralReleaseConstants.KROLLER_MOTOR_CURRENT_LIMIT);
        rollerMotor.configure(rollerConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);

    }

    @Override
    public void periodic() {
    }
    

    // configures motor for roller
    //limit is supposed to make sure the motors dont over heat
    public Command runRoller(CoralRelease coralRelease, DoubleSupplier forward, DoubleSupplier reverse) {
        return Commands.run(() -> rollerMotor.set(forward.getAsDouble() - reverse.getAsDouble()), coralRelease);
    }

}
