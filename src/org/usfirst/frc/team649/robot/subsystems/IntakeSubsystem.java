package org.usfirst.frc.team649.robot.subsystems;

import org.usfirst.frc.team649.robot.Robot;
import org.usfirst.frc.team649.robot.RobotMap;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class IntakeSubsystem extends Subsystem {

    public TalonSRX leftIntake, rightIntake;
    public DoubleSolenoid intakeSol;
    public boolean isIntakeRunning;

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
    public IntakeSubsystem(){
    	intakeSol = new DoubleSolenoid(RobotMap.Intake.INTAKE_SOL[0],RobotMap.Intake.INTAKE_SOL[1],RobotMap.Intake.INTAKE_SOL[2]);
    	leftIntake = new TalonSRX(RobotMap.Intake.LEFT_INTAKE_MOTOR);
    	rightIntake = new TalonSRX(RobotMap.Intake.RIGHT_INTAKE_MOTOR);
    	leftIntake.configNominalOutputForward(0, Robot.timeoutMs);
    	leftIntake.configNominalOutputReverse(0, Robot.timeoutMs);
    	leftIntake.configPeakOutputForward(1, Robot.timeoutMs);
    	leftIntake.configPeakOutputReverse(-1, Robot.timeoutMs);
    	rightIntake.configNominalOutputForward(0, Robot.timeoutMs);
    	rightIntake.configNominalOutputReverse(0, Robot.timeoutMs);
    	rightIntake.configPeakOutputForward(1, Robot.timeoutMs);
    	rightIntake.configPeakOutputReverse(-1, Robot.timeoutMs);
    	isIntakeRunning = false;
    }
    public void setIntakeMotors(double right, double left){
    	leftIntake.set(ControlMode.PercentOutput, left);
    	rightIntake.set(ControlMode.PercentOutput, right);
    	if(right >= 0 && left >=0 && left<=0 && right<=0)
    	{
    		isIntakeRunning = true;
    	}
    }
    public void setIntakePiston(boolean isOut){
		intakeSol.set(isOut ? DoubleSolenoid.Value.kForward : DoubleSolenoid.Value.kReverse);
    }
    public void setIntakePulse(double time, double period,boolean isForward){
    	double powerLeft = Math.abs(Math.sin(2*Math.PI*(time/period)));
    	double powerRight = Math.abs(Math.cos(2*Math.PI*(time/period)));
    	if(isForward){
        	setIntakeMotors(powerLeft,powerRight);
    	}else{
        	setIntakeMotors(-powerLeft,-powerRight);
    	}
    	
    }
    public boolean isRunning()
    {
    	return isIntakeRunning;
    }
    
}

