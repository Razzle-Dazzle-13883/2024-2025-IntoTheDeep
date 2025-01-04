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
            // telemetry.addData("claw: ", robot.rotation.getPosition());
            // telemetry.addData("rotation: ", robot.rotation.getPosition());
            // telemetry.addData("rightArm: ", robot.rightArm.getCurrentPosition());
            telemetry.update();
        }
    }
}