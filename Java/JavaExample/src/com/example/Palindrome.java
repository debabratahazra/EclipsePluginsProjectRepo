package com.example;

public class Palindrome
{
   public static void main(String args[])
   {
      String original, reverse = "";
      original = "abcdcba";
 
      int length = original.length();
 
      for ( int i = length - 1; i >= 0; i-- )
         reverse = reverse + original.charAt(i);
 
      if (original.equals(reverse))
         System.out.println("Entered string is a palindrome.");
      else
         System.out.println("Entered string is not a palindrome.");
 
   }
}	