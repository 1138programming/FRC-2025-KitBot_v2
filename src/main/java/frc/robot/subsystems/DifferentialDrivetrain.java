package frc.robot.subsystems;

import java.util.function.DoubleSupplier;

import frc.robot.Constants.DriveConstants;

import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkBase.ResetMode;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.config.SparkMaxConfig;

import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.motorcontrol.Spark;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import static frc.robot.Constants.DriveConstants.*;

//Talon drivebase
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.configs.CurrentLimitsConfigs;
import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.configs.VoltageConfigs;
import com.ctre.phoenix6.controls.DifferentialFollower;

import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.TalonSRXConfiguration;
import com.ctre.phoenix.motorcontrol.ControlMode;


public class DifferentialDrivetrain extends SubsystemBase {
    private final SparkMax leftLead;
    private final SparkMax leftFollow;
    private final SparkMax rightLead;
    private final SparkMax rightFollow;

    //Talon
    private final TalonSRX T_leftLead;
    private final TalonSRX T_leftFollow;
    private final TalonSRX T_rightLead;
    private final TalonSRX T_rightFollow;

    private TalonSRXConfiguration T_config;
    private ControlMode controlMode;


    private final DifferentialDrive drive;

    public DifferentialDrivetrain() {
        //brushed motors -> simple, less pricey. Roattes motor with input output current

        leftLead = new SparkMax(DriveConstants.KLEFT_LEADER_ID, MotorType.kBrushed);
        leftFollow = new SparkMax(DriveConstants.KLEFT_FOLLOWER_ID, MotorType.kBrushed);
        rightLead = new SparkMax(DriveConstants.KRIGHT_LEADER_ID, MotorType.kBrushed);
        rightFollow = new SparkMax(DriveConstants.KRIGHT_FOLLOWER_ID, MotorType.kBrushed);

        drive = new DifferentialDrive(leftLead, rightLead);
    

        //set timeout to not block robot operation upon construction
        leftLead.setCANTimeout(250);
        rightLead.setCANTimeout(250);
        leftLead.setCANTimeout(250);
        leftFollow.setCANTimeout(250);

        //Talon instatiation
        T_leftLead = new TalonSRX(KLEFT_LEADER_ID);
        T_leftFollow = new TalonSRX(KLEFT_FOLLOWER_ID);
        T_rightLead = new TalonSRX(KRIGHT_LEADER_ID);
        T_rightFollow = new TalonSRX(KRIGHT_FOLLOWER_ID);


        //create config for motors
        //Voltage compensation helps on differing batter volatges
        // -slightly slower at full charge
        //limit helps prevent tripping breakers

        //sparkmaxconfig
            SparkMaxConfig config = new SparkMaxConfig();
            config.voltageCompensation(12);
            config.smartCurrentLimit(DriveConstants.KDRIVE_MOTOR_CURRENT_LIMIT);

            //Set config to follow leader and have follower motor
            //follow leader
            //measures to reset on controller swap and breaker trips

            config.follow(leftLead);
            leftFollow.configure(config, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
            config.follow(rightLead);
            rightFollow.configure(config, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);

            //Remove following, then apply config to right leader
            config.disableFollowerMode();
            rightLead.configure(config, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
            //set config to inverted the apply left leader.
            //postive will drive both foward
            config.inverted(true);
            leftLead.configure(config,  ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);

    

        //talonSRX configs

            T_config = new TalonSRXConfiguration();
            T_config.voltageCompSaturation = KDRIVE_MOTOR_VOLTAGE_LiMIT;
            T_config.peakCurrentLimit = KDRIVE_MOTOR_CURRENT_LIMIT;

            //set followers
            T_leftFollow.follow(T_leftLead);
            T_rightFollow.follow(T_rightLead);

            T_leftLead.setInverted(true);

            T_leftLead.configAllSettings(T_config);
            T_leftFollow.configAllSettings(T_config);
            T_rightLead.configAllSettings(T_config);
            T_rightFollow.configAllSettings(T_config);

        //Talon control
            controlMode = ControlMode.PercentOutput;
    }

    @Override
    public void periodic() {
    }
    
    //joystick control command
    public Command driveArcade(DifferentialDrivetrain drivetrain, DoubleSupplier xSpeed, DoubleSupplier zRotation) {
        return Commands.run(() -> drive.arcadeDrive(xSpeed.getAsDouble(), zRotation.getAsDouble()), drivetrain);
    } 

    public void driveArcadeTalon(double xSpeedL, double xSpeedR) {
           T_leftLead.set(controlMode, xSpeedL);
           T_rightLead.set(controlMode, xSpeedR);   
    }

    public void stop() {
        drive.stopMotor();
    }
}
