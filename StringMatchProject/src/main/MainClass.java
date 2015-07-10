package main;

import matcher.StringMatch;

public class MainClass
{

  /**
   * @param args
   */
  public static void main(String[] args)
  {
    // TODO Auto-generated method stub
      String string = "hi, I'm Debabrata from Bangalore";
      String pattern = "*Debabr?ta*Banga????";
      
      boolean match = StringMatch.match(string, pattern);
      System.out.println(match);
  }

}
