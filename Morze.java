import lejos.nxt.LCD;

public class Morze
{
   public static void main(String[] args) throws InterruptedException
   {
      int darkLevel = 50; //Everything higher than this will be considered a signal
      int speed = 90;
      int dot = 40; //Dot length in milliseconds
      
      FirstStep firstStep = new FirstStep(darkLevel, speed);
      SecondStep secondStep = new SecondStep(darkLevel, speed);
      ThirdStep thirdStep = new ThirdStep(darkLevel, speed, dot);      
      
      LCD.drawString("First step", 0, 0);
      firstStep.execute();
      LCD.clear();
      
      LCD.drawString("Second step", 0, 0);
      secondStep.execute();
      LCD.clear();
      
      LCD.drawString("Third step", 0, 0);
      thirdStep.execute();
      LCD.clear();
   }
}