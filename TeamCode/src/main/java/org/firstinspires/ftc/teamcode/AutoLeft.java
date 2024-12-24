package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

@Autonomous(name="AutoLeft")
public class AutoLeft extends LinearOpMode {

    DcMotor frontLeftMotor;
    DcMotor backLeftMotor;
    DcMotor frontRightMotor;
    DcMotor backRightMotor;
    DcMotor rightArm;
    DcMotor rightLS;


    Servo claw;
    Servo wrist;
    Servo rotation;

    int leftFrontPos = 0;
    int leftBackPos = 0;
    int rightFrontPos = 0;
    int rightBackPos = 0;

    final double ARM_TICKS_PER_DEGREE = 28 // number of encoder ticks per rotation of the bare motor
            * 13.7 // This is the exact gear ratio of the 13.7:1 Yellow Jacket gearbox
            * 100.0 / 20.0 // This is the external gear reduction, a 20T pinion gear that drives a 100T hub-mount gear
            * 1/360.0; // we want ticks per degree, not per rotation
    double armPos; // Define arm position
    double armPower = 0.2;
    final double ARM_REST = 0;
    final double ARM_FLOOR_PICKUP = 10 * ARM_TICKS_PER_DEGREE;
    final double ARM_CLEAR_BARRIER = 15 * ARM_TICKS_PER_DEGREE;
    final double ARM_WALL_PICKUP = 55 * ARM_TICKS_PER_DEGREE;
    final double ARM_HANG = 75 * ARM_TICKS_PER_DEGREE;
    final double ARM_HANG_SPECIMEN = 70 * ARM_TICKS_PER_DEGREE;
    final double ARM_SCORE_HIGH_RUNG = 85 * ARM_TICKS_PER_DEGREE;
    final double ARM_SCORE_HIGH_BASKET = 95 * ARM_TICKS_PER_DEGREE;

    double clawPos; // Define claw position
    final double CLAW_OPEN = 1.0;
    final double CLAW_CLOSE = 0.2;

    double wristPos; // Define wrist position
    final double WRIST_OPEN = 0.2;
    final double WRIST_CLOSE = 0;

    double rotatePos; // Define rotation servo position
    final double ROTATION_OPEN = 0.25;
    final double ROTATION_CLOSE = 0;

    int rightPos;
    final double LS_TICKS_PER_INCH = 87.2079837; // 435rpm motor encoder/spool circumference 4.409 in per rev; 384.5/4.409 ticks per inc


    final int TICKS_PER_INCH = 45; // 11.87 in per rev; 537.7 ticks per rev; 537.7/11.87 ticks per inch

    @Override
    public void runOpMode() throws InterruptedException {
        // Declare our motors
        // Make sure your ID's match your configuration
        frontLeftMotor = hardwareMap.dcMotor.get("frontLeftMotor");
        backLeftMotor = hardwareMap.dcMotor.get("backLeftMotor");
        frontRightMotor = hardwareMap.dcMotor.get("frontRightMotor");
        backRightMotor = hardwareMap.dcMotor.get("backRightMotor");
        rightArm = hardwareMap.dcMotor.get("rightArm");
        rightLS = hardwareMap.dcMotor.get("rightLS");
        claw = hardwareMap.servo.get("claw");
        wrist = hardwareMap.servo.get("wrist");
        rotation = hardwareMap.servo.get("rotation");

        frontLeftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backLeftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frontRightMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backRightMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightArm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightLS.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        frontLeftMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backLeftMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        frontRightMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backRightMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightArm.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightLS.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        frontRightMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        backRightMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        rightLS.setDirection(DcMotorSimple.Direction.REVERSE);

        waitForStart();

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

        /*
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
    public void drive(int lF, int lB, int rF, int rB, double speed) {

        leftFrontPos -= lF;
        leftBackPos -= lB;
        rightFrontPos -= rF;
        rightBackPos -= rB;

        frontLeftMotor.setTargetPosition(leftFrontPos);
        backLeftMotor.setTargetPosition(leftBackPos);
        frontRightMotor.setTargetPosition(rightFrontPos);
        backRightMotor.setTargetPosition(rightBackPos);

        frontLeftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        backLeftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        frontRightMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        backRightMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        double driftFactor = 1.042;
        frontLeftMotor.setPower(speed);
        backLeftMotor.setPower(speed);
        frontRightMotor.setPower(speed);
        backRightMotor.setPower(speed);

        waitDrive();

        telemetry.addData("Running", true);
        telemetry.update();
    }
    private void waitDrive() {
        while (frontLeftMotor.isBusy() && frontRightMotor.isBusy() && backLeftMotor.isBusy() && backRightMotor.isBusy() && this.opModeIsActive()) ;
    }
    public void up (int left, int right, double speed) {
        // leftPos -= left;
        rightPos -= right;

        // leftLS.setTargetPosition(leftPos);
        rightLS.setTargetPosition(rightPos);

        // leftLS.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightLS.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        // leftLS.setPower(speed);
        rightLS.setPower(speed);
    }

    public void down ( int left, int right, double speed){
        // leftPos += left;
        rightPos += right;

        // leftLS.setTargetPosition(leftPos);
        rightLS.setTargetPosition(rightPos);

        // leftLS.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightLS.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        // leftLS.setPower(speed);
        rightLS.setPower(speed);
    }
}
