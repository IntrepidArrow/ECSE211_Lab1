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
    dCos45 = distance*COS_B;

//    if(dCos45<BAND_CENTER/2.5)
//    {LEFT_MOTOR.setSpeed((MOTOR_HIGH-DELTA)); 
//    RIGHT_MOTOR.setSpeed((MOTOR_HIGH-DELTA)); 
//    LEFT_MOTOR.backward();
//    RIGHT_MOTOR.backward();
//     
//    }
    if(Math.abs(dCos45-BAND_CENTER)<BAND_WIDTH)
    {
      
      LEFT_MOTOR.setSpeed(MOTOR_HIGH);
      RIGHT_MOTOR.setSpeed(MOTOR_HIGH);
      LEFT_MOTOR.forward(); // Start robot moving forward
      RIGHT_MOTOR.forward();
    }
    else if(dCos45>BAND_CENTER+BAND_WIDTH)
    {
      
      LEFT_MOTOR.setSpeed(MOTOR_HIGH-DELTA);
      RIGHT_MOTOR.setSpeed(MOTOR_HIGH+DELTA);
      LEFT_MOTOR.forward(); // Start robot moving forward
      RIGHT_MOTOR.forward();
    }
    else if(dCos45<BAND_CENTER-BAND_WIDTH)
    {
      
      LEFT_MOTOR.setSpeed(MOTOR_HIGH+DELTA);
      RIGHT_MOTOR.setSpeed(MOTOR_HIGH-DELTA);
      LEFT_MOTOR.forward(); // Start robot moving forward
      RIGHT_MOTOR.forward();
    }
  }

  @Override
  public int readUSDistance() {
    return this.distance;
  }
}
