package ca.mcgill.ecse211.lab1;

import static ca.mcgill.ecse211.lab1.Resources.*;

public class PController extends UltrasonicController {

  private static final int MOTOR_SPEED = 225;
  int PDELTA = 0;

  public PController() {
    LEFT_MOTOR.setSpeed(MOTOR_SPEED); // Initialize motor rolling forward
    RIGHT_MOTOR.setSpeed(MOTOR_SPEED);
    LEFT_MOTOR.forward();
    RIGHT_MOTOR.forward();
  }

  @Override
  public void processUSData(int distance) {
    filter(distance);

    int error = distance - P_BAND_CENTER;
    float PDELTA = (int) (error*P_CONSTANT);
    if(PDELTA>62)
      PDELTA = 62;
    TEXT_LCD.drawString("ERROR: " + error,0,5);

    if(distance < 20)//20
    {
      TEXT_LCD.drawString("TOO TOO CLOSE ",0,4);
      // Robot corrects its position when it gets too close to the wall
      LEFT_MOTOR.setSpeed(480); 
      RIGHT_MOTOR.setSpeed(480); 
      LEFT_MOTOR.forward();
      RIGHT_MOTOR.backward(); 
    }

    else if(error < (-P_BAND_WIDTH))
    {
      TEXT_LCD.drawString("CLOSE",0,4);
      //Robot movement turn right 
      LEFT_MOTOR.setSpeed(MOTOR_SPEED + PDELTA);
      RIGHT_MOTOR.setSpeed(MOTOR_SPEED - PDELTA);
      LEFT_MOTOR.forward(); // Start robot moving forward
      RIGHT_MOTOR.forward();
    } 

    else if(error > P_BAND_WIDTH)
    {
      TEXT_LCD.drawString("FAR",0,4);
      //Robot movement turn left 
      LEFT_MOTOR.setSpeed(MOTOR_SPEED-PDELTA);
      RIGHT_MOTOR.setSpeed(MOTOR_SPEED+PDELTA);
      LEFT_MOTOR.forward(); // Start robot moving forward
      RIGHT_MOTOR.forward();
    }
    else if(Math.abs(error) <= P_BAND_WIDTH)
    {
      TEXT_LCD.drawString("ON PATH",0,4);
      LEFT_MOTOR.setSpeed(MOTOR_SPEED);
      RIGHT_MOTOR.setSpeed(MOTOR_SPEED);
      LEFT_MOTOR.forward(); // Start robot moving forward
      RIGHT_MOTOR.forward();
    }

  }


  @Override
  public int readUSDistance() {
    return this.distance;
  }

}
