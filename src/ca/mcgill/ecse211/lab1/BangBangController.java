package ca.mcgill.ecse211.lab1;

import static ca.mcgill.ecse211.lab1.Resources.*;

public class BangBangController extends UltrasonicController {
  double dCos45 = 0;


  public BangBangController() {
    LEFT_MOTOR.setSpeed(MOTOR_HIGH); // Start robot moving forward
    RIGHT_MOTOR.setSpeed(MOTOR_HIGH);
    LEFT_MOTOR.forward();
    RIGHT_MOTOR.forward();
  }

  @Override
  public void processUSData(int distance) {

    filter(distance);
    dCos45 = distance*BANGBANG_COS_B;

    if(dCos45 < (BANGBANG_BAND_CENTER/1.50))
    {
      // Robot corrects its position when it gets too close to the wall
      LEFT_MOTOR.setSpeed((int) (MOTOR_HIGH+(2*BANGBANG_DELTA))); 
      RIGHT_MOTOR.setSpeed((int) (MOTOR_HIGH+(2*BANGBANG_DELTA))); 
      LEFT_MOTOR.forward();
      RIGHT_MOTOR.backward(); 
    }
    else if(Math.abs(dCos45-BANGBANG_BAND_CENTER) < BANGBANG_BAND_WIDTH)
    {
      LEFT_MOTOR.setSpeed(MOTOR_HIGH);
      RIGHT_MOTOR.setSpeed(MOTOR_HIGH);
      LEFT_MOTOR.forward(); // Start robot moving forward
      RIGHT_MOTOR.forward();
    }
    else if(dCos45 > BANGBANG_BAND_CENTER+BANGBANG_BAND_WIDTH)
    {
      //Robot movement turn left 
      LEFT_MOTOR.setSpeed(MOTOR_HIGH-BANGBANG_DELTA);
      RIGHT_MOTOR.setSpeed(MOTOR_HIGH+BANGBANG_DELTA);
      LEFT_MOTOR.forward(); // Start robot moving forward
      RIGHT_MOTOR.forward();
    }
    else if(dCos45 < BANGBANG_BAND_CENTER-BANGBANG_BAND_WIDTH)
    {
      //Robot movement turn right 
      LEFT_MOTOR.setSpeed(MOTOR_HIGH+BANGBANG_DELTA);
      RIGHT_MOTOR.setSpeed(MOTOR_HIGH-BANGBANG_DELTA);
      LEFT_MOTOR.forward(); // Start robot moving forward
      RIGHT_MOTOR.forward();
    } 

  }

  @Override
  public int readUSDistance() {
    return this.distance;
  }
}
