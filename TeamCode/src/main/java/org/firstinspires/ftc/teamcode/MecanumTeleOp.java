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
    // DcMotor armMotor;

    // Servo claw;
    // Servo wrist;
    int armPos; // Define arm position
    final int TICKS_PER_INCH = 45; // 11.87 in per rev; 537.7 ticks per rev; 537.7/11.87 ticks per inch
    double speedFactor = 1.0;

    @Override
    public void runOpMode() throws InterruptedException {
        // Declare our motors
        // Make sure your ID's match your configuration
        frontLeftMotor = hardwareMap.dcMotor.get("frontLeftMotor");
        backLeftMotor = hardwareMap.dcMotor.get("backLeftMotor");
        frontRightMotor = hardwareMap.dcMotor.get("frontRightMotor");
        backRightMotor = hardwareMap.dcMotor.get("backRightMotor");
        // armMotor = hardwareMap.dcMotor.get("armMotor");
        // claw = hardwareMap.servo.get("claw");
        // wrist = hardwareMap.servo.get("wrist");

        frontLeftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backLeftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frontRightMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backRightMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        // armMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        frontLeftMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backLeftMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        frontRightMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backRightMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        // armMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        // armMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        // Reverse the right side motors. This may be wrong for your setup.
        // If your robot moves backwards when commanded to go forwards,
        // reverse the left side instead.
        // See the note about this earlier on this page.
        frontRightMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        backRightMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        // armMotor.setDirection(DcMotorSimple.Direction.REVERSE);

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

            if (gamepad1.dpad_up) {
                speedFactor = 1.0;
            } else if (gamepad1.dpad_down) {
                speedFactor = 0.5;
            }

            /*
            if (gamepad2.dpad_up) { // drop position; lower basket
                armPos = -125;
                armMotor.setTargetPosition(armPos);
                armMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                // ((DcMotorEx)armMotor).setVelocity(100);
                armMotor.setPower(0.7);
            }
            */

            /* if (gamepad2.left_bumper) { // pickup position
                armPos = -22;
                armMotor.setTargetPosition(armPos);
                armMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                armMotor.setPower(0.1);
            }


            /* if (gamepad2.dpad_down) { // starting position
                wrist.setPosition(0);

                armPos = -3;
                armMotor.setTargetPosition(armPos);
                armMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                armMotor.setPower(0.1);

                sleep(500);
                armMotor.setPower(0);
            }

             */
/*
            if (gamepad2.x) {
                claw.setPosition(0.6); // Opens claw
            }

            if (gamepad2.y) {
                claw.setPosition(0.4); // Closes claw
            }

            if (gamepad2.b) {
                wrist.setPosition(0.4); // Down wrist
            }

            if (gamepad2.a) {
                wrist.setPosition(0); // Up wrist
            }
            */
            // telemetry.addData("Arm Pos: ", armMotor.getCurrentPosition());
            // telemetry.addData("Claw Pos: ", claw.getPosition());
            // telemetry.addData("Wrist Pos: ", wrist.getPosition());
            telemetry.update();
        }
    }
}