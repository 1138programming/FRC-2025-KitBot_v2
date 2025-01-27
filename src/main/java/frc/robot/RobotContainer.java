//Functions of a robot container

/* - Define subsystems and commands 
 * - Binds commands to events such as button presses
 * - Select autnoumous when running
 * - Generates code for opertor interface and links them to commands
 * - defines commands for subsysytems when no other commands are running
 * -bluk of robot declared here
 *  -Subsystems, commands, trigger mappings
 */

package frc.robot;

import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.Trigger;
 
import static frc.robot.Constants.OperatorConstants;

import frc.robot.Constants.CoralReleaseConstants;
import frc.robot.Constants.OperatorConstants;
import frc.robot.commands.Autonomous;
 
import frc.robot.subsystems.DifferentialDrivetrain;
import frc.robot.subsystems.CoralRelease;
import frc.robot.commands.DifferentialDriveWithTalon;
import frc.robot.commands.CoralReleaseWithTalon;

 
 
 public class RobotContainer {
     
     //subsystems
     private final DifferentialDrivetrain drivetrain = new DifferentialDrivetrain();
     private final CoralRelease coralRelease = new CoralRelease();
     private final DifferentialDriveWithTalon driveWithTalon = new DifferentialDriveWithTalon(drivetrain);
    private final CoralReleaseWithTalon coralReleaseWithTalon = new CoralReleaseWithTalon(coralRelease);
 
     //controller: Driver
     private final CommandXboxController driverController = new CommandXboxController(
         OperatorConstants.KDRIVER_CONTROLLER_PORT
     );
 
     //controller: operator
     private final CommandXboxController operatorController= new CommandXboxController(
         OperatorConstants.KOPERATOR_CONTROLLER_PORT
     );
 
     //Auton chooser
     private final SendableChooser<Command> autoChooser = new SendableChooser<>();
 
     //Container: Subsystems, OI Devices, and commands
 
     public RobotContainer() {
         configureBindings();
 
         //set auton options on dashboard: TODO: add other autons with autoChooser.addOption
         autoChooser.setDefaultOption("exampleAuto", Autonomous.moveforward(drivetrain));
         autoChooser.addOption("Do Nothing", Autonomous.doAbsolutelyNothing(drivetrain));
     }
 
     //bind triggers to actions
     private void configureBindings() {
         //ex: Set A button to 
         operatorController.a().whileTrue(coralRelease.runRollerTalon(0.5, 0));
 
         //Set default commad fro drivetrain to Command provided by factory
         //with joystick axes on driver controller
         //Y axis is inverted so pushing towards driver moves it forward
         //TODO: ask driver what they prefer
 
         drivetrain.setDefaultCommand(drivetrain.driveArcadeTalon(getXBoxRightY(), getXBoxLeftY()));
 
         //Set default for coral deposit with Oerator controller
         coralRelease.setDefaultCommand(
             coralRelease.runRoller(coralRelease, () -> operatorController.getRightTriggerAxis(), () -> operatorController.getLeftTriggerAxis()));
     }
 
     //Used to pass auton commands to the main {@link Robot} class.
     //return command to run auton
     public Command getAutonomousCommand() {
         return autoChooser.getSelected();
     }
     
     public double getXBoxLeftY() {
        double Y = driverController.getLeftY();
         if (Y > OperatorConstants.KDEAD_ZONE || Y < -OperatorConstants.KDEAD_ZONE) {
             return Y;
         } else {
             return 0;
         }
     }

     //get xbox left Y Axis 
    public double getXBoxRightY() {
        double Y = driverController.getRightY();
        if (Y > OperatorConstants.KDEAD_ZONE || Y < -OperatorConstants.KDEAD_ZONE) {
            return Y;
        } else {
            return 0;
        }
    }
 
 }
