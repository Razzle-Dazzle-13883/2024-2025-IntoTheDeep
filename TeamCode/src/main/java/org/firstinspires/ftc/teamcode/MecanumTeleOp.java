package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;

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
            double y = gamepad1.left_stick_y; // Remember, Y stick value is reversed
            double x = -gamepad1.left_stick_x * 1.1; // Counteract imperfect strafing
            double rx = -gamepad1.right_stick_x;

            // This button choice was made so that it is hard to hit on accident,
            // it can be freely changed based on preference.
            // The equivalent button is start on Xbox-style controllers.
            if (gamepad1.options) {
                robot.imu.resetYaw();
            }

            double botHeading = robot.imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.RADIANS);

            // Rotate the movement direction counter to the bot's rotation
            double rotX = x * Math.cos(-botHeading) - y * Math.sin(-botHeading);
            double rotY = x * Math.sin(-botHeading) + y * Math.cos(-botHeading);

            rotX = rotX * 1.1;  // Counteract imperfect strafing

            // Denominator is the largest motor power (absolute value) or 1
            // This ensures all the powers maintain the same ratio,
            // but only if at least one is out of the range [-1, 1]
            double denominator = Math.max(Math.abs(rotY) + Math.abs(rotX) + Math.abs(rx), 1);
            double frontLeftPower = (rotY + rotX + rx) / denominator;
            double backLeftPower = (rotY - rotX + rx) / denominator;
            double frontRightPower = (rotY - rotX - rx) / denominator;
            double backRightPower = (rotY + rotX - rx) / denominator;

            /* Denominator is the largest motor power (absolute value) or 1
            // This ensures all the powers maintain the same ratio,
            // but only if at least one is out of the range [-1, 1]
            double denominator = Math.max(Math.abs(y) + Math.abs(x) + Math.abs(rx), 1);
            double frontLeftPower = (y + x + rx) / denominator * speedFactor;
            double backLeftPower = (y - x + rx) / denominator * speedFactor;
            double frontRightPower = (y - x - rx) / denominator * speedFactor;
            double backRightPower = (y + x - rx) / denominator * speedFactor;
             */

            robot.robotMove(frontLeftPower, backLeftPower, frontRightPower, backRightPower);

            if (gamepad1.left_stick_y == 0) {
                robot.robotBrakeBehavior();
            } else if (gamepad1.right_stick_x == 0) {
                robot.robotBrakeBehavior();
            }
            if (gamepad2.x) {
                robot.intakeClaw.setPosition(robot.INCLAW_OPEN);
            }
            if (gamepad2.y) {
                robot.intakeClaw.setPosition(robot.INCLAW_CLOSE);
            }
            if (gamepad2.a) {
                robot.intakePick();
            }
            if (gamepad2.b) {
                robot.intakeFeed();
            }
            if (gamepad2.dpad_up) {
                robot.outtakeUp();
            }
            if (gamepad2.dpad_down) {
                robot.outtakeDown();
            }
            if (gamepad2.dpad_left) {
                robot.outtakeClaw.setPosition(robot.OUTCLAW_OPEN);
            }

            /*
            if (gamepad2.dpad_up) { // linear slide up
                if (Math.abs(robot.rightPos) >= 20 * robot.LS_TICKS_PER_INCH) {
                    // safety up
                } else {
                    robot.up((int) (0.1 * robot.LS_TICKS_PER_INCH), (int) (0.1 * robot.LS_TICKS_PER_INCH), 10);
                }
            }
            if (gamepad2.dpad_down) { // linear slide down
                if (Math.abs(robot.rightPos) <= 2 * robot.LS_TICKS_PER_INCH) {
                    // safety down
                } else {
                    robot.down((int) (0.1 * robot.LS_TICKS_PER_INCH), (int) (0.1 * robot.LS_TICKS_PER_INCH), 0.2);
                }
                // i left my water bottle here
            }
             */

            telemetry.addData("leftintakeslide: ", robot.leftIntakeSlide.getPosition());
            telemetry.addData("rightouttakearm: ", robot.rightOuttakeArm.getPosition());
            telemetry.addData("outtakewrist: ", robot.outtakeWrist.getPosition());
            telemetry.addData("intakewrist: ", robot.intakeWrist.getPosition());
            telemetry.addData("leftLS: ", robot.leftLS.getCurrentPosition());
            telemetry.addData("rightLS: ", robot.rightLS.getCurrentPosition());
            telemetry.update();
        }
    }
}