package ca.mcgill.ecse211.lab1;

import static ca.mcgill.ecse211.lab1.Resources.*;

public class PController extends UltrasonicController {

  private static final int MOTOR_SPEED = 225;
  double dCos45 = 0;
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
    dCos45 = distance*COS_B;
    PDELTA = (int) ((dCos45-BAND_CENTER)*P_CONSTANT);
      if(PDELTA > MAX_PDELTA)
        PDELTA = MAX_PDELTA;

    if(dCos45 < (BAND_CENTER/2.5))
    {
      // Robot corrects its position when it gets too close to the wall
      LEFT_MOTOR.setSpeed((MOTOR_SPEED+DELTA)); 
      RIGHT_MOTOR.setSpeed((MOTOR_SPEED+DELTA)); 
      LEFT_MOTOR.forward();
      RIGHT_MOTOR.backward(); 
    }
    else if(Math.abs(dCos45-BAND_CENTER) < BAND_WIDTH)
    {
      LEFT_MOTOR.setSpeed(MOTOR_SPEED);
      RIGHT_MOTOR.setSpeed(MOTOR_SPEED);
      LEFT_MOTOR.forward(); // Start robot moving forward
      RIGHT_MOTOR.forward();
    }
    else if(dCos45 > BAND_CENTER+BAND_WIDTH)
    {
      //Robot movement turn left 
      LEFT_MOTOR.setSpeed(MOTOR_SPEED-PDELTA);
      RIGHT_MOTOR.setSpeed(MOTOR_SPEED+PDELTA);
      LEFT_MOTOR.forward(); // Start robot moving forward
      RIGHT_MOTOR.forward();
    }
    else if(dCos45 < BAND_CENTER-BAND_WIDTH)
    {
      //Robot movement turn right 
      LEFT_MOTOR.setSpeed(MOTOR_SPEED+PDELTA);
      RIGHT_MOTOR.setSpeed(MOTOR_SPEED-PDELTA);
      LEFT_MOTOR.forward(); // Start robot moving forward
      RIGHT_MOTOR.forward();
    } 
  }


  @Override
  public int readUSDistance() {
    return this.distance;
  }

}
