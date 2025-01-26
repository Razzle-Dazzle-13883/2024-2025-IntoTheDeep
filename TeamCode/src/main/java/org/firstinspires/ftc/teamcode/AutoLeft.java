package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous(name="AutoLeft")
public class AutoLeft extends LinearOpMode {
    Robot robot;

    @Override
    public void runOpMode() throws InterruptedException {
        robot = new Robot(this);
        robot.initHardware();

        waitForStart();

        // drop preload to high basket
        robot.drive(-4 * robot.TICKS_PER_INCH, 4 * robot.TICKS_PER_INCH, 4 * robot.TICKS_PER_INCH, -4 * robot.TICKS_PER_INCH, 0.5);
        robot.drive(-15 * robot.TICKS_PER_INCH, -15 * robot.TICKS_PER_INCH, -15 * robot.TICKS_PER_INCH, -15 * robot.TICKS_PER_INCH, 0.5);
        robot.intakePick();
        robot.intakeClaw.setPosition(robot.INCLAW_OPEN);
        robot.drive(-36 * robot.TICKS_PER_INCH, -36 * robot.TICKS_PER_INCH, 36 * robot.TICKS_PER_INCH, 36 * robot.TICKS_PER_INCH, 0.5);
        robot.outtakeUp();
        robot.intakeFeed();
        robot.drive(-3 * robot.TICKS_PER_INCH, -3 * robot.TICKS_PER_INCH, -3 * robot.TICKS_PER_INCH, -3 * robot.TICKS_PER_INCH, 0.5);
        sleep(200);
        robot.outtakeClaw.setPosition(robot.OUTCLAW_OPEN);

        // grab first sample
        robot.drive(6 * robot.TICKS_PER_INCH, 6 * robot.TICKS_PER_INCH, 6 * robot.TICKS_PER_INCH, 6 * robot.TICKS_PER_INCH, 0.5);
        robot.outtakeDown();
        robot.drive(3 * robot.TICKS_PER_INCH, 3 * robot.TICKS_PER_INCH, 3 * robot.TICKS_PER_INCH, 3 * robot.TICKS_PER_INCH, 0.5);
        robot.intakePick();
        sleep(200);
        robot.intakeClaw.setPosition(robot.INCLAW_OPEN);
        robot.intakeFeed();
        sleep(200);

        // drop first sample
        robot.drive(-9 * robot.TICKS_PER_INCH, -9 * robot.TICKS_PER_INCH, -9 * robot.TICKS_PER_INCH, -9 * robot.TICKS_PER_INCH, 0.5);
        robot.outtakeUp();
        sleep(200);
        robot.outtakeClaw.setPosition(robot.OUTCLAW_OPEN);

        // grab second sample
        robot.drive(6 * robot.TICKS_PER_INCH, 6 * robot.TICKS_PER_INCH, 6 * robot.TICKS_PER_INCH, 6 * robot.TICKS_PER_INCH, 0.5);
        robot.outtakeDown();
        robot.drive(4 * robot.TICKS_PER_INCH, 4 * robot.TICKS_PER_INCH, -4 * robot.TICKS_PER_INCH, -4 * robot.TICKS_PER_INCH, 0.5);
        robot.drive(-8 * robot.TICKS_PER_INCH, 8 * robot.TICKS_PER_INCH, 8 * robot.TICKS_PER_INCH, -8 * robot.TICKS_PER_INCH, 0.5);
        robot.intakePick();
        sleep(200);
        robot.intakeClaw.setPosition(robot.INCLAW_OPEN);
        robot.intakeFeed();
        sleep(200);

        // drop second sample
        robot.drive(8 * robot.TICKS_PER_INCH, -8 * robot.TICKS_PER_INCH, -8 * robot.TICKS_PER_INCH, 8 * robot.TICKS_PER_INCH, 0.5);
        robot.drive(-36 * robot.TICKS_PER_INCH, -36 * robot.TICKS_PER_INCH, 36 * robot.TICKS_PER_INCH, 36 * robot.TICKS_PER_INCH, 0.5);
        robot.drive(-9 * robot.TICKS_PER_INCH, -9 * robot.TICKS_PER_INCH, -9 * robot.TICKS_PER_INCH, -9 * robot.TICKS_PER_INCH, 0.5);
        robot.outtakeUp();
        sleep(200);
        robot.outtakeClaw.setPosition(robot.OUTCLAW_OPEN);

        // end auto
        robot.drive(6 * robot.TICKS_PER_INCH, 6 * robot.TICKS_PER_INCH, 6 * robot.TICKS_PER_INCH, 6 * robot.TICKS_PER_INCH, 0.5);
        robot.outtakeDown();

        /*
        // drive to low rung
        claw.setPosition(CLAW_CLOSE);
        sleep(500);
        wrist.setPosition(WRIST_CLOSE);
        rotation.setPosition(ROTATION_OPEN);
        sleep(1000);
        drive(23 * TICKS_PER_INCH, 23 * TICKS_PER_INCH, 23 * TICKS_PER_INCH, 23 * TICKS_PER_INCH, 0.5); // Forward
        sleep(1000);
        armPos = ARM_SCORE_HIGH_RUNG;
        rightArm.setTargetPosition((int)armPos);
        rightArm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightArm.setPower(armPower);
        while(rightArm.isBusy()) {}
        robot.up((int) (6 * LS_TICKS_PER_INCH), (int) (6 * LS_TICKS_PER_INCH), 0.2);
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
        robot.down((int)(5 * LS_TICKS_PER_INCH),(int)(5 * LS_TICKS_PER_INCH), 0.2);
        while(rightLS.isBusy()) {}
        sleep(1000);
        // robot goes to push samples into net zone
        wrist.setPosition(WRIST_CLOSE);
        drive(-12 * TICKS_PER_INCH, -12 * TICKS_PER_INCH, -12 * TICKS_PER_INCH, -12 * TICKS_PER_INCH, 0.5); // Reverse
        sleep(1000);
        armPos = ARM_REST;
        rightArm.setTargetPosition((int)armPos);
        rightArm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightArm.setPower(armPower);
        drive(-31 * TICKS_PER_INCH, 31 * TICKS_PER_INCH, 31 * TICKS_PER_INCH, -31 * TICKS_PER_INCH, 0.5); // Strafe Left
        drive(32 * TICKS_PER_INCH, 32 * TICKS_PER_INCH, 32 * TICKS_PER_INCH, 32 * TICKS_PER_INCH, 0.5); // Forward
        drive(-36 * TICKS_PER_INCH, -36 * TICKS_PER_INCH, 36 * TICKS_PER_INCH, 36 * TICKS_PER_INCH, 0.5); // Turn Left
        drive(5 * TICKS_PER_INCH, -5 * TICKS_PER_INCH, -5 * TICKS_PER_INCH, 5 * TICKS_PER_INCH, 0.6); // Strafe Right
        drive(55 * TICKS_PER_INCH, 55 * TICKS_PER_INCH, 55 * TICKS_PER_INCH, 55 * TICKS_PER_INCH, 0.5); // Forward
        drive(-58 * TICKS_PER_INCH, -58 * TICKS_PER_INCH, -58 * TICKS_PER_INCH, -58 * TICKS_PER_INCH, 0.5); // Reverse
        drive(-4 * TICKS_PER_INCH, -4 * TICKS_PER_INCH, 4 * TICKS_PER_INCH, 4 * TICKS_PER_INCH, 0.5); // Turn Right
        drive(15 * TICKS_PER_INCH, -15 * TICKS_PER_INCH, -15 * TICKS_PER_INCH, 15 * TICKS_PER_INCH, 0.6); // Strafe Right
        drive(55 * TICKS_PER_INCH, 55 * TICKS_PER_INCH, 55 * TICKS_PER_INCH, 55 * TICKS_PER_INCH, 0.5); // Forward

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
