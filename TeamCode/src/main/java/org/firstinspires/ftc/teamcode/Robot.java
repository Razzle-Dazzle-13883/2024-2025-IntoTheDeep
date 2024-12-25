package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

public class Robot {
    DcMotor frontLeftMotor;
    DcMotor backLeftMotor;
    DcMotor frontRightMotor;
    DcMotor backRightMotor;
    DcMotor rightArm;
    // DcMotor leftArm;
    DcMotor rightLS;
    // DcMotor leftLS;

    Servo claw;
    Servo wrist;
    Servo rotation;

    // int leftPos; // Define left LS position
    int rightPos; // Define right LS position
    final int TICKS_PER_INCH = 45; // 11.87 in per rev; 537.7 ticks per rev; 537.7/11.87 ticks per inch
    final double LS_TICKS_PER_INCH = 87.2079837; // 435rpm motor encoder/spool circumference 4.409 in per rev; 384.5/4.409 ticks per inch
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
    double armManual = 0.0;

    double clawPos; // Define claw position
    final double CLAW_OPEN = 1.0;
    final double CLAW_CLOSE = 0.2;

    double wristPos; // Define wrist position
    final double WRIST_OPEN = 0.2;
    final double WRIST_CLOSE = 0;

    double rotatePos; // Define rotation servo position
    final double ROTATION_OPEN = 0.25;
    final double ROTATION_CLOSE = 0;

    int leftFrontPos = 0;
    int leftBackPos = 0;
    int rightFrontPos = 0;
    int rightBackPos = 0;

    private LinearOpMode myOpMode;
    public Robot(LinearOpMode opMode){
        this.myOpMode = opMode;
    }

    public void initHardware(){
        // Declare our motors
        // Make sure your ID's match your configuration
        frontLeftMotor = myOpMode.hardwareMap.dcMotor.get("frontLeftMotor");
        backLeftMotor = myOpMode.hardwareMap.dcMotor.get("backLeftMotor");
        frontRightMotor = myOpMode.hardwareMap.dcMotor.get("frontRightMotor");
        backRightMotor = myOpMode.hardwareMap.dcMotor.get("backRightMotor");
        rightArm = myOpMode.hardwareMap.dcMotor.get("rightArm");
        // leftArm = myOpMode.hardwareMap.dcMotor.get("leftArm");
        rightLS = myOpMode.hardwareMap.dcMotor.get("rightLS");
        // leftLS = myOpMode.hardwareMap.dcMotor.get("leftLS");
        claw = myOpMode.hardwareMap.servo.get("claw");
        wrist = myOpMode.hardwareMap.servo.get("wrist");
        rotation = myOpMode.hardwareMap.servo.get("rotation");

        frontLeftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backLeftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frontRightMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backRightMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightArm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        // leftArm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightLS.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        // leftLS.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        frontLeftMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backLeftMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        frontRightMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backRightMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightArm.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        // leftArm.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightLS.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        // leftLS.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        frontLeftMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backLeftMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        frontRightMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backRightMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightArm.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        // leftArm.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightLS.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        // leftLS.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        // Reverse the right side motors. This may be wrong for your setup.
        // If your robot moves backwards when commanded to go forwards,
        // reverse the left side instead.
        frontRightMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        backRightMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        // rightArm.setDirection(DcMotorSimple.Direction.REVERSE);
        rightLS.setDirection(DcMotorSimple.Direction.REVERSE);
    }

    public void robotMove(double frontLeftPower, double backLeftPower, double frontRightPower, double backRightPower){
        frontLeftMotor.setPower(frontLeftPower);
        backLeftMotor.setPower(backLeftPower);
        frontRightMotor.setPower(frontRightPower);
        backRightMotor.setPower(backRightPower);
    }

    public void robotBrakeBehavior(){
        frontLeftMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backLeftMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        frontRightMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backRightMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
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
    }
    private void waitDrive() {
        while (frontLeftMotor.isBusy() && frontRightMotor.isBusy() && backLeftMotor.isBusy() && backRightMotor.isBusy() && myOpMode.opModeIsActive()) ;
    }
}
