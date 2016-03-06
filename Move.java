import lejos.nxt.MotorPort;

public class Move
{
   private int speed;
   
   public Move(int speed)
   {
      if (speed > 100 || speed < 60)
         this.speed = 90;
      else 
         this.speed = speed;
   }
   
   public void forward() throws InterruptedException
   {
      MotorPort.A.controlMotor(speed, 1);
      MotorPort.B.controlMotor(speed, 1);
      Thread.sleep(1);
   }
   
   public void forward(int centimeters) throws InterruptedException
   {
      resetTachoCount();
      
      while (MotorPort.A.getTachoCount() < (centimeters / (5.6 * Math.PI) * 360))
         forward();
      
      stop();
      Thread.sleep(100);
   }
   
   public void backward(int centimeters) throws InterruptedException
   {
      resetTachoCount();
      
      while (MotorPort.A.getTachoCount() > (centimeters / (5.6 * Math.PI) * 360 * -1))
      {
         MotorPort.A.controlMotor(speed, 2);
         MotorPort.B.controlMotor(speed, 2);
      }
      
      stop();
      Thread.sleep(100);
   }
   
   public void turnCounterClockwise()  throws InterruptedException
   {
      MotorPort.A.controlMotor(speed, 1);
      MotorPort.B.controlMotor(speed, 2);
      Thread.sleep(1);
   }
   
   public void turnClockwise()  throws InterruptedException
   {
      MotorPort.A.controlMotor(speed, 2);
      MotorPort.B.controlMotor(speed, 1);
      Thread.sleep(1);
   }
   
   public void turnClockwise(int degrees)  throws InterruptedException
   {
      resetTachoCount();
      
      while (MotorPort.B.getTachoCount() < degrees * 5)
         turnClockwise();
      
      stop();
      Thread.sleep(100);
   }
   
   public void stop() throws InterruptedException
   {
      MotorPort.A.controlMotor(100, 3);
      MotorPort.B.controlMotor(100, 3);
      Thread.sleep(200);
   }
   
   public int getATachoCount()
   {
      return MotorPort.A.getTachoCount();
   }
   
   public void resetTachoCount()
   {
      MotorPort.A.resetTachoCount();
      MotorPort.B.resetTachoCount();
   }
}