package org.usfirst.frc.team649.autonomous;

import org.usfirst.frc.team649.robot.Robot;
import org.usfirst.frc.team649.robot.commands.AutoTestCommand;
import org.usfirst.frc.team649.robot.commands.DrivetrainMotionProfileIn;
import org.usfirst.frc.team649.robot.commands.DrivetrainPIDCommand;
import org.usfirst.frc.team649.robot.commands.GyroPID;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *Autonomous 2:
 ***** Drive straight and turn then drop off block
 *Position: Right
 */
public class RightScale extends CommandGroup {

    public RightScale() {
    	if(Robot.isTestingAuto) {
    		addSequential(new DrivetrainMotionProfileIn(AutoTest.RightScaleVal.FIRST_DRIVE)); // drive straight
    		addSequential(new GyroPID(AutoTest.RightScaleVal.FIRST_ANGLE_TURN)); // turn ~45 degrees
        	addSequential(new DrivetrainMotionProfileIn(AutoTest.RightScaleVal.SECOND_DRIVE));// drive straight
    		addSequential(new GyroPID(AutoTest.RightScaleVal.SECOND_ANGLE_TURN));
    		addSequential(new AutoTestCommand());
        	// deploy
    	} else {
    		addSequential(new DrivetrainMotionProfileIn(260-Robot.robotLength)); // drive straight
    		addSequential(new GyroPID(-45)); // turn ~45 degrees
        	addSequential(new DrivetrainMotionProfileIn(42.41-Robot.robotLength/2));// drive straight
    		addSequential(new GyroPID(45));
        	// deploy
    	}
    }
}
