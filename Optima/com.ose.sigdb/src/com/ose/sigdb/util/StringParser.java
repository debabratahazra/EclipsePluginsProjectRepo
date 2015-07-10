/* COPYRIGHT-ENEA-SRC-R2 *
 **************************************************************************
 * Copyright (C) 2007 by Enea Software AB.
 * All rights reserved.
 *
 * This Software is furnished under a software license agreement and
 * may be used only in accordance with the terms of such agreement.
 * Any other use or reproduction is prohibited. No title to and
 * ownership of the Software is hereby transferred.
 *
 * PROPRIETARY NOTICE
 * This Software consists of confidential information.
 * Trade secret law and copyright law protect this Software.
 * The above notice of copyright on this Software does not indicate
 * any actual or intended publication of such Software.
 **************************************************************************
 * COPYRIGHT-END */

package com.ose.sigdb.util;

public final class StringParser
{
   public static String ascii2String(byte[] data, int offset, int maxLength)
   {
      int strLength;

      for (strLength = 0; strLength <= maxLength
            && (offset + strLength) < data.length; strLength++)
         if (data[offset + strLength] == 0)
            break;

      char[] carr = new char[strLength];

      for (int i = 0; i < strLength; i++)
      {
         if (data[offset + i] < 0)
            carr[i] = (char) (-1 * data[offset + i] + 128);
         else
            carr[i] = (char) data[offset + i];
      }
      return new String(carr);
   }

   public final static String pad(boolean leftAlign, String s, int w)
   {
      String spaces = "                    ";
      String result;
      if (s.length() < w)
      {
         int pads = w - s.length();
         String padString = "";
         for (; pads > spaces.length(); pads -= spaces.length())
            padString += spaces;
         padString += spaces.substring(0, pads);
         if (leftAlign)
            result = s + padString;
         else
            result = padString + s;
      }
      else
         result = s;

      return result;
   }

   public final static String pad(String s, int w)
   {
      return pad(true, s, w);
   }

   /**
    * Parse an option line and return a statement.
    * 
    * @return If the string is blank or a comment null is returned otherwise a
    *         statement is returned.
    */
   public static Statement parseStatement(String s)
         throws java.text.ParseException
   {
      String ts = s.trim();
      if (ts.startsWith("#") || (ts.length() == 0)) // Check for comment or
                                                      // blank line
         return null;
      else if (ts.indexOf('=') > 0)
      { // Check if assignment.
         int idx = ts.indexOf('=');
         String variable = ts.substring(0, idx).trim();
         String value = ts.substring(idx + 1).trim();
         if (variable.length() > 0)
         {
            if (value.startsWith("\""))
            { // Check if value is a string.
               if (value.endsWith("\""))
                  return new Statement(variable, value.substring(1, value
                        .length() - 1));
               else
                  throw new java.text.ParseException(
                        "String value without ending \"", 0);
            }
            else if (value.equalsIgnoreCase("true")
                  || value.equalsIgnoreCase("false"))
               return new Statement(variable, new Boolean(value));
            else
            {
               try
               { // Check if an integer
                  Long lvalue = new Long(value);
                  return new Statement(variable, lvalue);
               }
               catch (NumberFormatException nolong)
               {
                  try
                  { // Check if a double
                     Double dvalue = new Double(value);
                     return new Statement(variable, dvalue);
                  }
                  catch (NumberFormatException nodouble)
                  {
                     throw new java.text.ParseException(
                           "No valid value found in assignment", 0);
                  }
               }
            }
         }
         else
            throw new java.text.ParseException(
                  "Assignment without variable name", 0);
      }
      else if (ts.endsWith("{"))
      { // Check if complex statement
         if (ts.length() > 1)
            return new Statement(ts.substring(0, ts.length() - 1).trim(), true);
         else
            throw new java.text.ParseException(
                  "Open bracket without corresponding keyword", 0);
      }
      else if (ts.equals("}"))
         return new Statement();
      else
         return new Statement(ts, false);
   }

   public static String[] split(String s, String separator, boolean trim)
   {
      int numberOfParts = 1;

      int i = s.indexOf(separator);
      while (i > -1)
      {
         if (separator.length() + i < s.length())
         {
            numberOfParts++;
            i = s.indexOf(separator, i + separator.length());
         }
         else
            break;
      }
      String[] parts = new String[numberOfParts];
      i = s.indexOf(separator);
      if (i > 0)
      {
         parts[0] = s.substring(0, i);
         if (trim)
            parts[0].trim();
      }
      else if (i == 0)
         parts[0] = "";
      else
         parts[0] = s;

      int oldi = i;
      for (int n = 1; n < numberOfParts; n++)
      {
         i = s.indexOf(separator, oldi + 1);
         if (i - 1 > oldi + 1)
         {
            parts[n] = s.substring(oldi + 1, i);
            if (trim)
               parts[n].trim();
         }
         else if (i == -1)
         {
            parts[n] = s.substring(oldi + 1, s.length());
            if (trim)
               parts[n].trim();
         }
         else
            parts[n] = "";
         oldi = i;
      }
      return parts;
   }

   private String s;

   public StringParser(String _s)
   {
      s = _s;
   }

   public String[] split(String separator)
   {
      return split(separator, false);
   }

   public String[] split(String separator, boolean trim)
   {
      return split(s, separator, trim);
   }

   public String toString()
   {
      return s;
   }
}
