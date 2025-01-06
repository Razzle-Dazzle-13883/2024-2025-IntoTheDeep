package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp(name="MyTeleOpMode")
public class MecanumTeleOp extends LinearOpMode {
    Robot robot;

    double speedFactor = 1.0;

    @Override
    public void runOpMode() throws InterruptedException {
        robot = new Robot(this);
        robot.initHardware();

        waitForStart();

        if (isStopRequested()) return;

        while (opModeIsActive()) {
            double y = -gamepad1.left_stick_y; // Remember, Y stick value is reversed
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

            robot.robotMove(frontLeftPower, backLeftPower, frontRightPower, backRightPower);

            if (gamepad1.left_stick_y == 0) {
                robot.robotBrakeBehavior();
            } else if (gamepad1.right_stick_x == 0) {
                robot.robotBrakeBehavior();
            }
            if (gamepad2.x) {
                robot.inClawPos = robot.INCLAW_OPEN;
            }
            if (gamepad2.y) {
                robot.inClawPos = robot.INCLAW_CLOSE;
            }
            if (gamepad2.a) {
                robot.outClawPos = robot.OUTCLAW_OPEN;
                robot.inSlidePos = robot.INSLIDE_OUT;
                robot.inWristPos = robot.INWRIST_PICKUP;
                robot.inClawPos = robot.INCLAW_OPEN;
            }
            if (gamepad2.b) {
                robot.inWristPos = robot.INWRIST_FEED;
                robot.inSlidePos = robot.INSLIDE_IN;
                robot.outClawPos = robot.OUTCLAW_CLOSE;
                robot.inClawPos = robot.INCLAW_OPEN;
            }
            if (gamepad1.dpad_up) {
                robot.up((int)robot.LS_HIGHBASKET, (int)robot.LS_HIGHBASKET, 0.5);
                robot.outArmPos = robot.OUTARM_BASKET;
                robot.outWristPos = robot.OUTWRIST_DROP;
            }
            if (gamepad2.dpad_down) {
                robot.outClawPos = robot.OUTCLAW_CLOSE;
                robot.outWristPos = robot.OUTWRIST_FEED;
                robot.outArmPos = robot.OUTARM_RESET;
                robot.down((int)robot.LS_RESETDOWN, (int)robot.LS_RESETDOWN, 0.25);
            }
            if (gamepad2.dpad_left) {
                robot.outClawPos = robot. OUTCLAW_OPEN;
            }

            robot.intakeClaw.setPosition(robot.inClawPos);
            robot.intakeWrist.setPosition(robot.inWristPos);
            robot.leftIntakeSlide.setPosition(robot.inSlidePos);
            robot.rightIntakeSlide.setPosition(robot.inSlidePos);
            robot.outtakeClaw.setPosition(robot.outClawPos);
            robot.outtakeWrist.setPosition(robot.outWristPos);
            robot.leftOuttakeArm.setPosition(robot.outArmPos);
            robot.rightOuttakeArm.setPosition(robot.outArmPos);

/*
            if (gamepad2.dpad_up) { // linear slide up
                if (Math.abs(robot.rightPos) >= 20 * robot.LS_TICKS_PER_INCH) {
                    // safety
                } else {
                    robot.up((int) (0.1 * robot.LS_TICKS_PER_INCH), (int) (0.1 * robot.LS_TICKS_PER_INCH), 10);
                }
            }
            if (gamepad2.dpad_down) { // linear slide down
                robot.down((int)(0.1 * robot.LS_TICKS_PER_INCH),(int)(0.1 * robot.LS_TICKS_PER_INCH), 0.2);
            }

 */
            telemetry.addData("leftintakeslide: ", robot.leftIntakeSlide.getPosition());
            telemetry.addData("rightintakeslide: ", robot.rightIntakeSlide.getPosition());
            telemetry.addData("leftouttakearm: ", robot.leftOuttakeArm.getPosition());
            telemetry.addData("rightouttakearm: ", robot.rightOuttakeArm.getPosition());
            telemetry.addData("intakewrist: ", robot.intakeWrist.getPosition());
            telemetry.addData("leftLS: ", robot.leftLS.getCurrentPosition());
            telemetry.addData("rightLS: ", robot.rightLS.getCurrentPosition());
            telemetry.update();
        }
    }
}