package ca.mcgill.ecse211.lab1;

import static ca.mcgill.ecse211.lab1.Resources.*;
/**
 * This class implements the P-controller type (Proportional control) functionality for the robot 
 * in Lab 1 on the EV3 platform.
 * @author Abhimukth Chaudhuri, Aly Elgharabawy
 */
public class PController extends UltrasonicController {

  private static final int MOTOR_SPEED = 225;
  int PDELTA = 0;

  public PController() {
    LEFT_MOTOR.setSpeed(MOTOR_SPEED); // Initialize motor rolling forward
    RIGHT_MOTOR.setSpeed(MOTOR_SPEED);
    LEFT_MOTOR.forward();
    RIGHT_MOTOR.forward();
  }
  /**
   * processUSData processes the distance read by the US sensor and updates the motor speeds and hence the
   * behavior of the robot accordingly. The motor speeds are updated by a value
   * that is dependent on the measured error (error = distance - BAND_CENTER where BAND_CENTER is
   * the offset from the wall). The latter value is determined by multiplying a proportionality constant
   * by the measured error. 
   *  
   * The cases covered by the method include:
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

    int error = distance - P_BAND_CENTER;
    float PDELTA = (int) (error*P_CONSTANT);
    if(PDELTA>62)
      PDELTA = 62;
    TEXT_LCD.drawString("ERROR: " + error,0,5);

    // Robot corrects its position when it gets too too close to the wall
    if(distance < 25)
    {
      TEXT_LCD.drawString("TOO TOO CLOSE ",0,4);
      LEFT_MOTOR.setSpeed(480); 
      RIGHT_MOTOR.setSpeed(480); 
      //Make the robot turn outwards instead of driving into the wall 
      LEFT_MOTOR.forward(); 
      RIGHT_MOTOR.backward(); 
    }
    
    //Position correction when robot drives outside acceptable bandwidth and towards the wall 
    else if(error < (-P_BAND_WIDTH))
    {
      TEXT_LCD.drawString("CLOSE",0,4);
      //Robot movement turn right 
      LEFT_MOTOR.setSpeed(MOTOR_SPEED + PDELTA);
      RIGHT_MOTOR.setSpeed(MOTOR_SPEED - PDELTA);
      LEFT_MOTOR.forward(); // Start robot moving forward
      RIGHT_MOTOR.forward();
    } 

    //Position correction when robot drives outside acceptable bandwidth and away from the wall
    else if(error > P_BAND_WIDTH)
    {
      TEXT_LCD.drawString("FAR",0,4);
      //Robot movement turn left 
      LEFT_MOTOR.setSpeed(MOTOR_SPEED-PDELTA);
      RIGHT_MOTOR.setSpeed(MOTOR_SPEED+PDELTA);
      LEFT_MOTOR.forward(); // Start robot moving forward
      RIGHT_MOTOR.forward();
    }
    
    //When robot drives within acceptable bandwidth
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
