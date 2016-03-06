import java.util.ArrayList;

public class SecondStep
{
   private ArrayList<Integer> array;
   private Catch ctch;
   private Move move;
   
   public SecondStep(int darkValue, int speed)
   {
      ctch = new Catch(darkValue);
      move = new Move(speed);
   }
   
   private void findLightSource() throws InterruptedException
   {
      array = new ArrayList<Integer>();
      
      for (int i = 0; i < 1800; i++)
      {
         move.turnClockwise();
         array.add(ctch.getLightValue());
      }
      
      move.stop();
   }
   
   private void turnToLightSource() throws InterruptedException
   {
      int max = getMaxIndex();     
      
      if (max < 900)
         for (int i = 0; i < max; i++)
            move.turnClockwise();
      else
         for (int i = 0; i < (1800 - max); i++)
            move.turnCounterClockwise();
      
      move.stop();
   }
   
   public int getMaxIndex()
   {
      int max = array.get(0);
      int index = 0;
      
      for (int i = 1; i < array.size(); i++)
         if (max < array.get(i))
         {
            max = array.get(i);
            index = i;
         }
      
      return index;
   }
   
   public void execute() throws InterruptedException
   {
      findLightSource();
      turnToLightSource();
      
      while (ctch.getDistance() > 15)
      {
         move.resetTachoCount();
         
         while ((move.getATachoCount() < 360) && (ctch.getDistance() > 15))
            move.forward();
         
         move.stop();
         
         if (ctch.getDistance() > 15)
         {
            findLightSource();
            turnToLightSource();
         }
      }
   }
}