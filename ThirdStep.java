import lejos.nxt.LCD;
import lejos.nxt.Sound;

public class ThirdStep
{
   private Catch ctch;
   private Move move;
   private String message;
   private int dot;
   
   public ThirdStep(int darkValue, int speed, int dot)
   {
      this.dot = dot;
      ctch = new Catch(darkValue, dot);
      move = new Move(speed);
   }
   
   private void showCharacters() throws InterruptedException
   {
      char letter;
      int x = 0;
      int y = 2;
      
      char[] alphabet = {'A', 'B', 'C', 'D', 'E', 'F', 'G',
            'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q',
            'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', ' '};
      String[] code = {".-", "-...", "-.-.", "-..", ".", "..-.", "--.",
            "....", "..", ".---", "-.-", ".-..", "--", "-.", "---", ".--.", "--.-",
            ".-.", "...", "-", "..-", "...-", ".--", "-..-", "-.--", "--..", "#"};
   
      for (int i = 0; i < message.length(); i++)
      {
         letter = message.charAt(i);
         LCD.drawChar(letter, x, y);
         
         for (int j = 0; j < alphabet.length; j++)
         {
            if (alphabet[j] == letter)
               sing(code[j]);
         }
         
         if (x == 15)
         {
            x = 0;
            y++;
         } 
         else
            x++;
      }
   }
   
   private void sing(String letter) throws InterruptedException
   {
      for(int i = 0; i < letter.length(); i++)
      {
         if (letter.charAt(i) == '-')
         {
            Sound.playTone(500, dot * 3);
            Thread.sleep(dot * 3);
         }
         else if (letter.charAt(i) == '.')
         {
            Sound.playTone(500, dot);
            Thread.sleep(dot);
         }
         else
         {
            Sound.playTone(0, dot);
            Thread.sleep(dot * 4);
         }
         
         Sound.playTone(0, dot);
         Thread.sleep(dot);
      }
      
      Sound.playTone(0, dot);
      Thread.sleep(dot * 2);
   }
   
   public void execute() throws InterruptedException
   {
      message = ctch.getMorse();      
      move.backward(50);
      showCharacters();
   }
}