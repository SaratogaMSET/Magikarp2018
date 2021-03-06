package org.usfirst.frc.team649.robot.CommandGroups;

import org.usfirst.frc.team649.robot.Robot;
import org.usfirst.frc.team649.robot.commands.MotionProfileDrive;
import org.usfirst.frc.team649.robot.commands.SwitchMPModes;
import org.usfirst.frc.team649.robot.commands.arm.ArmMotionProfile;
import org.usfirst.frc.team649.robot.commands.arm.ChangeRobotArmState;
import org.usfirst.frc.team649.robot.commands.drivetrain.DriveBackForTime;
import org.usfirst.frc.team649.robot.commands.drivetrain.DrivetrainMotionProfileIn;
import org.usfirst.frc.team649.robot.commands.intake.RunIntakeForTime;
import org.usfirst.frc.team649.robot.commands.intake.RunIntakeWheels;
import org.usfirst.frc.team649.robot.commands.intake.SetIntakePistons;
import org.usfirst.frc.team649.robot.commands.liftCommands.ChangeRobotLiftState;
import org.usfirst.frc.team649.robot.commands.liftCommands.LiftMotionProfile;
import org.usfirst.frc.team649.robot.subsystems.ArmSubsystem;
import org.usfirst.frc.team649.robot.subsystems.LiftSubsystem;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class LeftScaleDoubleScaleMP extends CommandGroup {

    public LeftScaleDoubleScaleMP() {
    	addSequential(new ChangeRobotLiftState(9));
    	addSequential(new ChangeRobotArmState(ArmSubsystem.ArmStateConstants.HEADING_HIGH_DROP_FRONT));
    	addParallel(new ArmMotionProfile(ArmSubsystem.ArmEncoderConstants.HIGH_DROP_FRONT,Robot.armState,false));
    	addParallel(new LiftMotionProfile(LiftSubsystem.LiftEncoderConstants.HIGH_SCALE_STATE,Robot.liftState,1.25));
    	addSequential(new MotionProfileDrive(false));    	
    	addParallel(new ChangeRobotLiftState(1));
    	addSequential(new RunIntakeForTime(0.6, false, 0.35));
    	addSequential(new ChangeRobotLiftState(LiftSubsystem.LiftStateConstants.HEADING_INTAKE_EXCHANGE_STORE_STATE));
    	addSequential(new ChangeRobotArmState(ArmSubsystem.ArmStateConstants.HEADING_STORE_FRONT));
    	addParallel(new LiftMotionProfile(LiftSubsystem.LiftEncoderConstants.LOW_STATE,Robot.liftState, 1.25));
    	addParallel(new ArmMotionProfile(ArmSubsystem.ArmEncoderConstants.STORE_FRONT,Robot.armState, false));
    	addSequential(new SwitchMPModes(Robot.modifierLeftBack));
    	addSequential(new MotionProfileDrive(true));
//    	addSequential(new WaitForSEc(0.5));
//    	addSequential(new SetIntakePistons(false, true));

//    	addParallel(new DriveBackForTime(-0.3,0.9));
//////    	addSequential(new SwitchMPModes(Robot.modifierSideBack));
//    	addSequential(new DownAndFlipWhenPossibleIntakeRear());
//    	addSequential(new SetIntakePistons(true,false));
//    	addParallel(new RunIntakeWheels(0.5));
//    	addSequential(new DriveBackForTime(-0.3,1));
//    	addSequential(new SetIntakePistons(false,false));
//    	addSequential(new WaitForSEc(0.75));
//    	addParallel(new RunIntakeWheels(0));
//    	addSequential(new ChangeRobotArmState(ArmSubsystem.ArmStateConstants.HEADING_HIGH_DROP_FRONT));
//    	addSequential(new ChangeRobotLiftState(LiftSubsystem.LiftStateConstants.HEADING_HIGH_SCALE_STATE));
//    	addParallel(new DrivetrainMotionProfileIn(60));
//    	addParallel(new ArmMotionProfile(ArmSubsystem.ArmEncoderConstants.HIGH_DROP_FRONT,Robot.armState,true));
//
//    	addSequential(new LiftMotionProfile(LiftSubsystem.LiftEncoderConstants.HIGH_SCALE_STATE,Robot.liftState,0.5));
//    	addSequential(new RunIntakeForTime(0.5,false));
    }
}
