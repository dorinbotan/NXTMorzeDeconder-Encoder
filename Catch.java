import java.util.ArrayList;
import lejos.nxt.LCD;
import lejos.nxt.SensorPort;
import lejos.nxt.LightSensor;
import lejos.nxt.UltrasonicSensor;

public class Catch
{
   private int dark;
   private int dot;
   private ArrayList<Integer> array = new ArrayList<Integer>();
   private LightSensor lightSensor = new LightSensor(SensorPort.S1);
   private UltrasonicSensor ultrasonic = new UltrasonicSensor(SensorPort.S3);
   private String[] code = {".-", "-...", "-.-.", "-..", ".", "..-.", "--.",
         "....", "..", ".---", "-.-", ".-..", "--", "-.", "---", ".--.", "--.-",
         ".-.", "...", "-", "..-", "...-", ".--", "-..-", "-.--", "--.."};
   private String message = new String();
   
   public Catch(int dark)
   {
      lightSensor.setFloodlight(false);
      this.dark = dark;
      dot = 0;
   }
   
   public Catch(int dark, int dot)
   {
      lightSensor.setFloodlight(false);
      this.dark = dark;
      this.dot = dot;
   }
   
   public ArrayList<Integer> getLightValues(int values) throws InterruptedException
   {
      int start;
      int stop;
      int time;
      boolean flag = false;
      
      while(array.size() <= values)
      {
         start = (int)System.currentTimeMillis();
         
         while (getLightValue() > dark);

         stop = (int)System.currentTimeMillis();
         time = stop - start;
         
         if (flag && time > 20)
            array.add(time);
         
         flag = true;
      }
      
      return array;
   }
   
   private boolean gotMessage()
   {
      if (message.indexOf("HELLO") != -1 && message.indexOf("GOODBYE") != -1)
         return (message.indexOf("HELLO") < message.lastIndexOf("GOODBYE"));
      
      return false;
   }
   
   private String getMessage()
   {
      int start = message.indexOf("HELLO");
      int stop = message.lastIndexOf("GOODBYE") + 7;
      return message.substring(start, stop);
   }
   
   public String getMorse() throws InterruptedException
   {
      boolean flag = true;      
      int start;
      int stop;
      int time;
      String letter = "";
      
      while (flag)
      {
         // Dark
         start = (int)System.currentTimeMillis();
         while (getLightValue() < dark);
         stop = (int)System.currentTimeMillis();
         time = stop - start;

         // End of Word/Letter
         if (time > dot * 7 && !letter.isEmpty())
         {
            for (int i = 0; i < 26; i++)
               if (letter.equals(code[i]))
               {
                  message += (char)(65 + i);
                  
                  LCD.clear(2);
                  if (message.length() > 16)
                     LCD.drawString(message.substring(message.length() - 16), 0, 2);
                  else
                     LCD.drawString(message, 0, 2);
               }
            
            letter = "";
            
            if (gotMessage())
            {
               LCD.clear();
               message = getMessage();
               flag = false;
            }
            
            message += " ";
         }
         else if (time > dot * 3)
         {
            for (int i = 0; i < 26; i++)
               if (letter.equals(code[i]))
               {
                  message += (char)(65 + i);
                  
                  LCD.clear(2);
                  if (message.length() > 16)
                     LCD.drawString(message.substring(message.length() - 16), 0, 2);
                  else
                     LCD.drawString(message, 0, 2);
               }
            
            letter = "";
         }

         // Light
         start = (int)System.currentTimeMillis();
         while (getLightValue() > dark);
         stop = (int)System.currentTimeMillis();
         time = stop - start;
         
         // Dot/dash
         if (time > dot * 3)
            letter += "-";
         else if (time > dot)
            letter += ".";
      }
      
      return message;
   }
   
   public int getLightValue()
   {
      return lightSensor.getLightValue();
   }
   
   public int getDistance()
   {
      return ultrasonic.getDistance();
   }
}