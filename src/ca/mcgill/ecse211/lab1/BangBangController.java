package ca.mcgill.ecse211.lab1;

import static ca.mcgill.ecse211.lab1.Resources.*;

/**
 * This class implements the Bang-Bang controller type functionality for the robot in Lab 1 on the EV3 platform
 * @author Abhimukth Chaudhuri, Aly Elgharabawy
 */

public class BangBangController extends UltrasonicController {

  public BangBangController() {
    LEFT_MOTOR.setSpeed(MOTOR_HIGH); // Start robot moving forward
    RIGHT_MOTOR.setSpeed(MOTOR_HIGH);
    LEFT_MOTOR.forward();
    RIGHT_MOTOR.forward();
  }

  /**
   * processUSData processes the distance read by the US sensor and updates the motor speeds and hence the
   * behavior of the robot accordingly. The motor speeds are updated by a fixed constant
   * that is independent of the measured error (error = distance - BAND_CENTER where BAND_CENTER is
   * the offset from the wall). The cases covered by the method include:
   *  1) when robot is within the acceptable distance 
   * from the wall
   *  2) when the robot is too close to the wall it turns away
   *  3) when the robot is too far from the wall it turns inwards. 
   * 
   * The method also includes a fail-safe where it turns outwards
   * at a high angular speed when it is extremely close to the wall.
   * 
   * @param distance The measured value received from the US sensor. 
   */

  @Override
  public void processUSData(int distance) {

    filter(distance);

    int error = distance - BANGBANG_BAND_CENTER;
    TEXT_LCD.drawString("ERROR: " + error,0,5);

    // Robot corrects its position when it gets too too close to the wall
    if(distance < 25)
    {
      TEXT_LCD.drawString("TOO TOO CLOSE ",0,4);
      // Robot corrects its position when it gets too close to the wall
      LEFT_MOTOR.setSpeed(350); 
      RIGHT_MOTOR.setSpeed(350); 
      //Make the robot turn outwards instead of driving into the wall 
      LEFT_MOTOR.forward();
      RIGHT_MOTOR.backward(); 
    }

    //Position correction when robot drives outside acceptable bandwidth and towards the wall 
    else if(error < (-BANGBANG_BAND_WIDTH))
    {
      TEXT_LCD.drawString("CLOSE",0,4);
      //Robot movement turn right 
      LEFT_MOTOR.setSpeed(MOTOR_HIGH+BANGBANG_DELTA);
      RIGHT_MOTOR.setSpeed(MOTOR_HIGH-BANGBANG_DELTA);
      LEFT_MOTOR.forward(); // Start robot moving forward
      RIGHT_MOTOR.forward();
    } 
    
    //Position correction when robot drives outside acceptable bandwidth and away from the wall
    else if(error > BANGBANG_BAND_WIDTH)
    {
      TEXT_LCD.drawString("FAR",0,4);
      //Robot movement turn left 
      LEFT_MOTOR.setSpeed(MOTOR_HIGH-BANGBANG_DELTA);
      RIGHT_MOTOR.setSpeed(MOTOR_HIGH+BANGBANG_DELTA);
      LEFT_MOTOR.forward(); // Start robot moving forward
      RIGHT_MOTOR.forward();
    }
    
    //When robot drives within acceptable bandwidth
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
