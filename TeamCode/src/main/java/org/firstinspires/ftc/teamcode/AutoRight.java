package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous(name="AutoRight")
public class AutoRight extends LinearOpMode {
    Robot robot;

    final double DRIVE_SPEED = 0.8;
    final double LAST_DITCH = 1.0;
    @Override
    public void runOpMode() throws InterruptedException {
        robot = new Robot(this);
        robot.initHardware();

        waitForStart();

        robot.drive(5 * robot.TICKS_PER_INCH, 5 * robot.TICKS_PER_INCH, 5 * robot.TICKS_PER_INCH, 5 * robot.TICKS_PER_INCH, DRIVE_SPEED);
        robot.drive(40 * robot.TICKS_PER_INCH, -40 * robot.TICKS_PER_INCH, -40 * robot.TICKS_PER_INCH, 40 * robot.TICKS_PER_INCH, 0.6); // Strafe Right

        /*
        // hang preload specimen
        robot.outtakeClaw.setPosition(robot.OUTCLAW_CLOSE);
        robot.outtakeUp();
        robot.drive(-20 * robot.TICKS_PER_INCH, -20 * robot.TICKS_PER_INCH, -20 * robot.TICKS_PER_INCH, -20 * robot.TICKS_PER_INCH, DRIVE_SPEED);
        robot.outtakeDown();

        // robot push samples into observation zone
        robot.drive(8 * robot.TICKS_PER_INCH, 8 * robot.TICKS_PER_INCH, 8 * robot.TICKS_PER_INCH, 8 * robot.TICKS_PER_INCH, DRIVE_SPEED);
        robot.outtakeWrist.setPosition(robot.OUTWRIST_FEED);
        robot.rightOuttakeArm.setPosition(robot.OUTARM_RESET);
        robot.drive(-10 * robot.TICKS_PER_INCH, 10 * robot.TICKS_PER_INCH, 10 * robot.TICKS_PER_INCH, -10 * robot.TICKS_PER_INCH, DRIVE_SPEED);
        robot.drive(-35 * robot.TICKS_PER_INCH, -35 * robot.TICKS_PER_INCH, -35 * robot.TICKS_PER_INCH, -20 * robot.TICKS_PER_INCH, DRIVE_SPEED);
        robot.drive(-8 * robot.TICKS_PER_INCH, 8 * robot.TICKS_PER_INCH, 8 * robot.TICKS_PER_INCH, -8 * robot.TICKS_PER_INCH, DRIVE_SPEED);

        // first sample push
        robot.drive(45 * robot.TICKS_PER_INCH, 45 * robot.TICKS_PER_INCH, 45 * robot.TICKS_PER_INCH, 45 * robot.TICKS_PER_INCH, DRIVE_SPEED);
        robot.drive(-45 * robot.TICKS_PER_INCH, -45 * robot.TICKS_PER_INCH, -45 * robot.TICKS_PER_INCH, -45 * robot.TICKS_PER_INCH, DRIVE_SPEED);

        // second sample push
        robot.drive(-8 * robot.TICKS_PER_INCH, 8 * robot.TICKS_PER_INCH, 8 * robot.TICKS_PER_INCH, -8 * robot.TICKS_PER_INCH, DRIVE_SPEED);
        robot.drive(45 * robot.TICKS_PER_INCH, 45 * robot.TICKS_PER_INCH, 45 * robot.TICKS_PER_INCH, 45 * robot.TICKS_PER_INCH, DRIVE_SPEED);

        // pickup first specimen
        robot.drive(-5 * robot.TICKS_PER_INCH, -5 * robot.TICKS_PER_INCH, -5 * robot.TICKS_PER_INCH, -5 * robot.TICKS_PER_INCH, DRIVE_SPEED);
        robot.drive(8 * robot.TICKS_PER_INCH, -8 * robot.TICKS_PER_INCH, -8 * robot.TICKS_PER_INCH, 8 * robot.TICKS_PER_INCH, DRIVE_SPEED);
        robot.intakePick();
        sleep(200);
        robot.intakeFeed();

        // hang first specimen
        robot.drive(12 * robot.TICKS_PER_INCH, -12 * robot.TICKS_PER_INCH, -12 * robot.TICKS_PER_INCH, 12 * robot.TICKS_PER_INCH, DRIVE_SPEED);
        robot.drive(-15 * robot.TICKS_PER_INCH, -15 * robot.TICKS_PER_INCH, -15 * robot.TICKS_PER_INCH, -15 * robot.TICKS_PER_INCH, DRIVE_SPEED);
        robot.outtakeUp();
        robot.drive(-3 * robot.TICKS_PER_INCH, -3 * robot.TICKS_PER_INCH, -3 * robot.TICKS_PER_INCH, -3 * robot.TICKS_PER_INCH, DRIVE_SPEED);
        robot.outtakeDown();
        robot.drive(15 * robot.TICKS_PER_INCH, 15 * robot.TICKS_PER_INCH, 15 * robot.TICKS_PER_INCH, 15 * robot.TICKS_PER_INCH, DRIVE_SPEED);
        robot.drive(-12 * robot.TICKS_PER_INCH, 12 * robot.TICKS_PER_INCH, 12 * robot.TICKS_PER_INCH, -12 * robot.TICKS_PER_INCH, DRIVE_SPEED);

        // pickup second specimen
        robot.intakePick();
        sleep(200);
        robot.intakeFeed();

        // hang second specimen
        robot.drive(12 * robot.TICKS_PER_INCH, -12 * robot.TICKS_PER_INCH, -12 * robot.TICKS_PER_INCH, 12 * robot.TICKS_PER_INCH, DRIVE_SPEED);
        robot.drive(-15 * robot.TICKS_PER_INCH, -15 * robot.TICKS_PER_INCH, -15 * robot.TICKS_PER_INCH, -15 * robot.TICKS_PER_INCH, DRIVE_SPEED);
        robot.outtakeUp();
        robot.drive(-3 * robot.TICKS_PER_INCH, -3 * robot.TICKS_PER_INCH, -3 * robot.TICKS_PER_INCH, -3 * robot.TICKS_PER_INCH, DRIVE_SPEED);
        robot.outtakeDown();

        // park robot
        robot.drive(15 * robot.TICKS_PER_INCH, 15 * robot.TICKS_PER_INCH, 15 * robot.TICKS_PER_INCH, 15 * robot.TICKS_PER_INCH, LAST_DITCH);
        robot.drive(-12 * robot.TICKS_PER_INCH, 12 * robot.TICKS_PER_INCH, 12 * robot.TICKS_PER_INCH, -12 * robot.TICKS_PER_INCH, LAST_DITCH);









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
