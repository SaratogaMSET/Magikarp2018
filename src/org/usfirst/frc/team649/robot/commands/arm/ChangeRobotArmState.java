package org.usfirst.frc.team649.robot.commands.arm;

import org.usfirst.frc.team649.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class ChangeRobotArmState extends Command {
	int changeTo;
    public ChangeRobotArmState(int armState) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	changeTo = armState;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	Robot.armState = changeTo;

    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return true;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
