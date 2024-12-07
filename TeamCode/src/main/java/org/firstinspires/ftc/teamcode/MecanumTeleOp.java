package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
@TeleOp(name="MyTeleOpMode")
public class MecanumTeleOp extends LinearOpMode {

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
    double speedFactor = 0.7;

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
    final double ARM_HANG_SPECIMEN = 80 * ARM_TICKS_PER_DEGREE;
    final double ARM_SCORE_LOW_RUNG = 85 * ARM_TICKS_PER_DEGREE;
    final double ARM_SCORE_HIGH_BASKET = 95 * ARM_TICKS_PER_DEGREE;
    double armManual = 0.0;

    double clawPos; // Define claw position
    final double CLAW_OPEN = 0.5;
    final double CLAW_CLOSE = 0;

    double wristPos; // Define wrist position
    final double WRIST_OPEN = 0.2;
    final double WRIST_CLOSE = 0;

    double rotatePos; // Define rotation servo position
    final double ROTATION_OPEN = 0.25;
    final double ROTATION_CLOSE = 0;

    @Override
    public void runOpMode() throws InterruptedException {
        // Declare our motors
        // Make sure your ID's match your configuration
        frontLeftMotor = hardwareMap.dcMotor.get("frontLeftMotor");
        backLeftMotor = hardwareMap.dcMotor.get("backLeftMotor");
        frontRightMotor = hardwareMap.dcMotor.get("frontRightMotor");
        backRightMotor = hardwareMap.dcMotor.get("backRightMotor");
        rightArm = hardwareMap.dcMotor.get("rightArm");
        // leftArm = hardwareMap.dcMotor.get("leftArm");
        rightLS = hardwareMap.dcMotor.get("rightLS");
        // leftLS = hardwareMap.dcMotor.get("leftLS");
        claw = hardwareMap.servo.get("claw");
        wrist = hardwareMap.servo.get("wrist");
        rotation = hardwareMap.servo.get("rotation");

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

        waitForStart();

        if (isStopRequested()) return;

        while (opModeIsActive()) {
            double y = gamepad1.left_stick_y; // Remember, Y stick value is reversed
            double x = -gamepad1.left_stick_x * 1.1; // Counteract imperfect strafing
            double rx = -gamepad1.right_stick_x;

            // Denominator is the largest motor power (absolute value) or 1
            // This ensures all the powers maintain the same ratio,
            // but only if at least one is out of the range [-1, 1]
            double denominator = Math.max(Math.abs(y) + Math.abs(x) + Math.abs(rx), 1);
            double frontLeftPower = (y + x + rx) / denominator * speedFactor;
            double backLeftPower = (y - x + rx) / denominator * speedFactor;
            double frontRightPower = (y - x - rx) / denominator * speedFactor;
            double backRightPower = (y + x - rx) / denominator * speedFactor;

            frontLeftMotor.setPower(frontLeftPower);
            backLeftMotor.setPower(backLeftPower);
            frontRightMotor.setPower(frontRightPower);
            backRightMotor.setPower(backRightPower);

            if (gamepad1.left_stick_y == 0) {
                frontLeftMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
                backLeftMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
                frontRightMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
                backRightMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            } else if (gamepad1.right_stick_x == 0) {
                frontLeftMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
                backLeftMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
                frontRightMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
                backRightMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            }

            if (gamepad2.dpad_up) { // linear slide up
                if (Math.abs(rightPos) >= 10 * LS_TICKS_PER_INCH) {
                    // safety
                } else {
                    up((int) (0.1 * LS_TICKS_PER_INCH), (int) (0.1 * LS_TICKS_PER_INCH), 0.2);
                }
            }
            if (gamepad2.dpad_down) { // linear slide down
                down((int)(0.1 * LS_TICKS_PER_INCH),(int)(0.1 * LS_TICKS_PER_INCH), 0.2);
            }

            // setting arm positions
            if (gamepad1.a) {
                wristPos = WRIST_CLOSE;
                rotatePos = ROTATION_CLOSE;
                armPos = ARM_CLEAR_BARRIER;
            }
            if (gamepad1.b) {
                wristPos = WRIST_CLOSE;
                rotatePos = ROTATION_CLOSE;
                armPos = ARM_FLOOR_PICKUP;
            }
            if (gamepad1.x) {
                armPos = ARM_WALL_PICKUP;
            }
            if (gamepad1.left_bumper) {
                armPos = ARM_SCORE_HIGH_BASKET;
            }
            if (gamepad1.right_bumper) {
                armPos = ARM_SCORE_LOW_RUNG;
            }
            if (gamepad1.dpad_up) {
                armPos = ARM_HANG;
            }
            if (gamepad1.dpad_down) {
                wristPos = WRIST_CLOSE;
                rotatePos = ROTATION_CLOSE;
                armPos = ARM_REST;
            }
            if (gamepad1.y) {
                armPos = ARM_HANG_SPECIMEN;
            }

            if (gamepad2.x) {
                clawPos = CLAW_OPEN;
            }
            if (gamepad2.y) {
                clawPos = CLAW_CLOSE;
            }

            if (gamepad2.b) {
                wristPos = WRIST_CLOSE;
            }
            if (gamepad2.a) {
                wristPos = WRIST_OPEN;
            }

            if (gamepad2.left_bumper) {
                rotatePos = ROTATION_CLOSE;
            }
            if (gamepad2.right_bumper) {
                rotatePos = ROTATION_OPEN;
            }

            claw.setPosition(clawPos);
            wrist.setPosition(wristPos);
            rotation.setPosition(rotatePos);
            // leftArm.setTargetPosition((int)armPos);
            rightArm.setTargetPosition((int) armPos);
            // leftArm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            rightArm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            rightArm.setPower(armPower);
/*
            armManual = gamepad2.right_stick_y * 0.5;
            if (gamepad2.right_stick_y > 0.05 || gamepad2.right_stick_y < -0.05) {
                rightArm.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                rightArm.setPower(armManual);
                armPos = rightArm.getCurrentPosition();
            } else {
                rightArm.setTargetPosition((int)armPos);
                rightArm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                rightArm.setPower(armManual);
            }
 */


            telemetry.addData("rotation: ", rotation.getPosition());
            telemetry.addData("rightArm: ", rightArm.getCurrentPosition());
            telemetry.update();
        }
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