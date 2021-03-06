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
import org.usfirst.frc.team649.robot.subsystems.ArmSubsystem;
import org.usfirst.frc.team649.test.AutoTestCommand;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
*Autonomous 1:
 *****Start on right side and drive forward, turn 90 degrees and drive forward to 
 *****drop off the power cube
 *Position: Right
 */
public class DelayRightSwitch extends CommandGroup {

    public DelayRightSwitch() {
    	Robot.isMPRunning = false;
    	addSequential(new ZeroArmRoutine());
    	addSequential(new Delay(10));
    	addSequential(new ChangeRobotArmState(ArmSubsystem.ArmStateConstants.HEADING_SWITCH_FRONT));
    	addParallel(new ArmMotionProfile(ArmSubsystem.ArmEncoderConstants.SWITCH_FRONT, Robot.armState,false));
    	addSequential(new DrivetrainMotionProfileIn(AutoTest.RightSwitchVal.FIRST_DRIVE));// drive forward
      	addSequential(new GyroPID(AutoTest.RightSwitchVal.FIRST_ANGLE_TURN));// turn 90 degrees
//      addSequential(new DrivetrainMotionProfileIn(AutoTest.RightSwitchVal.SECOND_DRIVE)); // drive forward
    	addSequential(new RunIntakeForTime(1, false, 1));
    	addSequential(new DrivetrainMotionProfileIn(-10));
      	//      	addSequential(new GyroPID(AutoTest.RightSwitchVal.SECOND_ANGLE_TURN));
//      	addSequential(new DrivetrainMotionProfileIn(AutoTest.RightSwitchVal.THIRD_DRIVE));

      	// drop off block
    }
}
