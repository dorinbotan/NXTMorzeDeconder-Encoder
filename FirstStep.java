import java.util.ArrayList;

public class FirstStep
{
   private ArrayList<Integer> array = new ArrayList<Integer>();
   private Catch ctch;
   private Move move;
   
   public FirstStep(int darkValue, int speed)
   {
      ctch = new Catch(darkValue);
      move = new Move(speed);
   }
   
   private int getMax()
   {
      int max = array.get(0);

      for (int i = 1; i < array.size(); i++)
         if (max < array.get(i))
            max = array.get(i);
      
      return max;
   }
   
   private int getSecondMax()
   {
      int secondMax = 0;
      
      for (int i = 0; i < array.size(); i++)
         if (secondMax < array.get(i) && secondMax != getMax())
            secondMax = array.get(i);
      
      return secondMax;
   }
   
   public void execute() throws InterruptedException
   {  
      array = ctch.getLightValues(15);
      
      move.turnClockwise(getMax());
      move.forward((int)getSecondMax());
      move.stop();
   }
}