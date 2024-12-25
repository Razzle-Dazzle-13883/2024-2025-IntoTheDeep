package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous(name="AutoRight")
public class AutoRight extends LinearOpMode {
    Robot robot;

    @Override
    public void runOpMode() throws InterruptedException {
        robot = new Robot(this);
        robot.initHardware();

        waitForStart();

        /*
        claw.setPosition(CLAW_CLOSE);
        sleep(500);
        wrist.setPosition(WRIST_CLOSE);
        rotation.setPosition(ROTATION_OPEN);
        sleep(1000);
        drive(23 * TICKS_PER_INCH, 23 * TICKS_PER_INCH, 23 * TICKS_PER_INCH, 23 * TICKS_PER_INCH, 0.5); // Forward
        drive(-10 * TICKS_PER_INCH, 10 * TICKS_PER_INCH, 10 * TICKS_PER_INCH, -10 * TICKS_PER_INCH, 0.5); // Strafe Left
        sleep(1000);
        armPos = ARM_SCORE_HIGH_RUNG;
        rightArm.setTargetPosition((int)armPos);
        rightArm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightArm.setPower(armPower);
        while(rightArm.isBusy()) {}
        up((int) (6 * LS_TICKS_PER_INCH), (int) (6 * LS_TICKS_PER_INCH), 0.2);
        while(rightLS.isBusy()) {}
        sleep(2000);
        // hang specimen on high rung
        drive(4 * TICKS_PER_INCH, 4 * TICKS_PER_INCH, 4 * TICKS_PER_INCH, 4 * TICKS_PER_INCH, 0.5); // Forward
        sleep(1000);
        armPos = ARM_HANG_SPECIMEN;
        rightArm.setTargetPosition((int)armPos);
        rightArm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightArm.setPower(armPower);
        wrist.setPosition(WRIST_OPEN);
        while(rightArm.isBusy()) {}
        sleep(1000);
        claw.setPosition(CLAW_OPEN);
        down((int)(5 * LS_TICKS_PER_INCH),(int)(5 * LS_TICKS_PER_INCH), 0.2);
        while(rightLS.isBusy()) {}
        sleep(1000);
        // robot goes to park
        drive(-20 * TICKS_PER_INCH, -20 * TICKS_PER_INCH, -20 * TICKS_PER_INCH, -20 * TICKS_PER_INCH, 0.5); // Reverse
        wrist.setPosition(WRIST_CLOSE);
        sleep(2000);
        drive(45 * TICKS_PER_INCH, -45 * TICKS_PER_INCH, -45 * TICKS_PER_INCH, 45 * TICKS_PER_INCH, 0.5); // Strafe Right
        sleep(2000);
        armPos = ARM_REST;
        rightArm.setTargetPosition((int)armPos);
        rightArm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightArm.setPower(armPower);

        // Drive to position
        drive(20 * TICKS_PER_INCH, 20 * TICKS_PER_INCH, 20 * TICKS_PER_INCH, 20 * TICKS_PER_INCH, 0.6); // Forward
        drive(30 * TICKS_PER_INCH, -30 * TICKS_PER_INCH, -30 * TICKS_PER_INCH, 30 * TICKS_PER_INCH, 0.6); // Strafe Right
        drive(35 * TICKS_PER_INCH, 35 * TICKS_PER_INCH, 35 * TICKS_PER_INCH, 35 * TICKS_PER_INCH, 0.6); // Forward
        drive(40 * TICKS_PER_INCH, 40 * TICKS_PER_INCH, -40 * TICKS_PER_INCH, -40 * TICKS_PER_INCH, 0.6); // Turn Right
        // first sample
        drive(-10 * TICKS_PER_INCH, 10 * TICKS_PER_INCH, 10 * TICKS_PER_INCH, -10 * TICKS_PER_INCH, 0.6); // Strafe Left
        drive(50 * TICKS_PER_INCH, 50 * TICKS_PER_INCH, 50 * TICKS_PER_INCH, 50 * TICKS_PER_INCH, 0.5); // Forward
        drive(-65 * TICKS_PER_INCH, -65 * TICKS_PER_INCH, -65 * TICKS_PER_INCH, -65 * TICKS_PER_INCH, 0.6); // Reverse
        // second sample
        drive(-8 * TICKS_PER_INCH, 8 * TICKS_PER_INCH, 8 * TICKS_PER_INCH, -8 * TICKS_PER_INCH, 0.6); //Strafe Left
        drive(50 * TICKS_PER_INCH, 50 * TICKS_PER_INCH, 50 * TICKS_PER_INCH, 50 * TICKS_PER_INCH, 0.6); // Forward
        drive(-65 * TICKS_PER_INCH, -65 * TICKS_PER_INCH, -65 * TICKS_PER_INCH, -65 * TICKS_PER_INCH, 0.6); // Reverse
         */

        while(!isStopRequested()){

        }
    }
}
