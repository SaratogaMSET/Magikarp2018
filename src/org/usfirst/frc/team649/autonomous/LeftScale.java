package org.usfirst.frc.team649.autonomous;

import org.usfirst.frc.team649.robot.Robot;
import org.usfirst.frc.team649.robot.CommandGroups.DeployWithWheelsAndIntake;
import org.usfirst.frc.team649.robot.commands.Delay;
import org.usfirst.frc.team649.robot.commands.arm.ArmMotionProfile;
import org.usfirst.frc.team649.robot.commands.arm.ChangeRobotArmState;
import org.usfirst.frc.team649.robot.commands.arm.ZeroArmRoutine;
import org.usfirst.frc.team649.robot.commands.drivetrain.DrivetrainMotionProfileIn;
import org.usfirst.frc.team649.robot.commands.drivetrain.GyroPID;
import org.usfirst.frc.team649.robot.commands.intake.RunIntakeForTime;
import org.usfirst.frc.team649.robot.commands.intake.RunIntakeWheels;
import org.usfirst.frc.team649.robot.commands.liftCommands.ChangeRobotLiftState;
import org.usfirst.frc.team649.robot.commands.liftCommands.LiftMotionProfile;
import org.usfirst.frc.team649.robot.subsystems.ArmSubsystem;
import org.usfirst.frc.team649.robot.subsystems.LiftSubsystem;
import org.usfirst.frc.team649.test.AutoTestCommand;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *Autonomous 2:
 ***** Drive straight and turn then drop off block
 *Position: Left
 */
public class LeftScale extends CommandGroup {

    public LeftScale() {
    	addSequential(new ZeroArmRoutine());
    	addSequential(new DrivetrainMotionProfileIn(AutoTest.LeftScaleVal.FIRST_DRIVE)); // drive straight
    	addSequential(new GyroPID(AutoTest.LeftScaleFarVal.FIRST_ANGLE_TURN)); // turn ~45 degrees
        addSequential(new DrivetrainMotionProfileIn(AutoTest.LeftScaleVal.SECOND_DRIVE));// drive straight
        addSequential(new ChangeRobotLiftState(LiftSubsystem.LiftStateConstants.HIGH_SCALE_STATE));
    	addSequential(new ChangeRobotArmState(ArmSubsystem.ArmStateConstants.HEADING_HIGH_DROP_FRONT));
        addParallel(new LiftMotionProfile(LiftSubsystem.LiftEncoderConstants.HIGH_SCALE_STATE,Robot.liftState,1.25));
    	addParallel(new ArmMotionProfile(ArmSubsystem.ArmEncoderConstants.HIGH_DROP_FRONT,Robot.armState,false));
        addSequential(new GyroPID(AutoTest.LeftScaleVal.SECOND_ANGLE_TURN));
        addSequential(new DrivetrainMotionProfileIn(AutoTest.LeftScaleVal.THIRD_DRIVE));
        addSequential(new Delay(0.5));// drive straight
    	addSequential(new RunIntakeForTime(1, false, 1));
    	addSequential(new DrivetrainMotionProfileIn(-10));// deploy
    }
}
