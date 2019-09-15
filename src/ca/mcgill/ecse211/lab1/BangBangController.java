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

    int error = distance - BANGBANG_BAND_CENTER;
    TEXT_LCD.drawString("ERROR: " + error,0,5);

    if(distance < 20)
    {
      TEXT_LCD.drawString("TOO TOO CLOSE ",0,4);
      // Robot corrects its position when it gets too close to the wall
      LEFT_MOTOR.setSpeed(350); 
      RIGHT_MOTOR.setSpeed(350); 
      LEFT_MOTOR.forward();
      RIGHT_MOTOR.backward(); 
    }

    else if(error < (-BANGBANG_BAND_WIDTH))
    {
      TEXT_LCD.drawString("CLOSE",0,4);
      //Robot movement turn right 
      LEFT_MOTOR.setSpeed(MOTOR_HIGH+BANGBANG_DELTA);
      RIGHT_MOTOR.setSpeed(MOTOR_HIGH-BANGBANG_DELTA);
      LEFT_MOTOR.forward(); // Start robot moving forward
      RIGHT_MOTOR.forward();
    } 

    else if(error > BANGBANG_BAND_WIDTH)
    {
      TEXT_LCD.drawString("FAR",0,4);
      //Robot movement turn left 
      LEFT_MOTOR.setSpeed(MOTOR_HIGH-BANGBANG_DELTA);
      RIGHT_MOTOR.setSpeed(MOTOR_HIGH+BANGBANG_DELTA);
      LEFT_MOTOR.forward(); // Start robot moving forward
      RIGHT_MOTOR.forward();
    }
    else if(Math.abs(error) <= BANGBANG_BAND_WIDTH)
    {
      TEXT_LCD.drawString("ON PATH",0,4);
      LEFT_MOTOR.setSpeed(MOTOR_HIGH);
      RIGHT_MOTOR.setSpeed(MOTOR_HIGH);
      LEFT_MOTOR.forward(); // Start robot moving forward
      RIGHT_MOTOR.forward();
    }

  }

  @Override
  public int readUSDistance() {
    return this.distance;
  }
}
