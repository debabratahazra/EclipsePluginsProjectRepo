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

/**
 * A class for formatting numbers that follows printf conventions.
 * Also implements C-like atoi and atof functions
 * @version 1.01 15 Feb 1996 
 * @author Cay Horstmann
 */

package com.ose.sigdb.util;

public class Format
{
   /**
    * Formats the number following printf conventions. Main limitation: Can only
    * handle one format parameter at a time Use multiple Format objects to
    * format more than one number
    * 
    * @param s
    *           the format string following printf conventions The string has a
    *           prefix, a format code and a suffix. The prefix and suffix become
    *           part of the formatted output. The format code directs the
    *           formatting of the (single) parameter to be formatted. The code
    *           has the following structure
    *           <ul>
    *           <li> a % (required)
    *           <li> a modifier (optional)
    *           <dl>
    *           <dt> +
    *           <dd> forces display of + for positive numbers
    *           <dt> 0
    *           <dd> show leading zeroes
    *           <dt> -
    *           <dd> align left in the field
    *           <dt> space
    *           <dd> prepend a space in front of positive numbers
    *           <dt> #
    *           <dd> use "alternate" format. Add 0 or 0x for octal or
    *           hexadecimal numbers. Don't suppress trailing zeroes in general
    *           floating point format.
    *           </dl>
    *           <li> an integer denoting field width (optional)
    *           <li> a period followed by an integer denoting precision
    *           (optional)
    *           <li> a format descriptor (required)
    *           <dl>
    *           <dt>f
    *           <dd> floating point number in fixed format
    *           <dt>e, E
    *           <dd> floating point number in exponential notation (scientific
    *           format). The E format results in an uppercase E for the exponent
    *           (1.14130E+003), the e format in a lowercase e.
    *           <dt>g, G
    *           <dd> floating point number in general format (fixed format for
    *           small numbers, exponential format for large numbers). Trailing
    *           zeroes are suppressed. The G format results in an uppercase E
    *           for the exponent (if any), the g format in a lowercase e.
    *           <dt>d, i
    *           <dd> integer in decimal
    *           <dt>x
    *           <dd> integer in hexadecimal
    *           <dt>o
    *           <dd> integer in octal
    *           <dt>s
    *           <dd> string
    *           <dt>c
    *           <dd> character
    *           </dl>
    *           </ul>
    * @exception IllegalArgumentException
    *               if bad format
    */

   private static final int PARSE_STATE_PREFIX = 0;

   private static final int PARSE_STATE_FLAGS = 1;

   private static final int PARSE_STATE_WIDTH = 2;

   private static final int PARSE_STATE_PRECISION = 3;

   private static final int PARSE_STATE_SIZE = 4;

   private static final int PARSE_STATE_SIZE2 = 5;

   private static final int PARSE_STATE_FORMAT = 6;

   private static final int PARSE_STATE_END = 7;

   // FIXME Size specifications in format strings are ignored!

   private static String convert(long x, int n, int m, String d)
   {
      if (x == 0)
         return "0";
      String r = "";
      do
      {
         r = d.charAt((int) (x & m)) + r;
         x = x >>> n;
      } while (x != 0);
      return r;
   }

   /**
    * prints a formatted number following printf conventions
    * 
    * @param s
    *           a PrintStream
    * @param fmt
    *           the format string
    * @param x
    *           the character to
    */

   public static void print(java.io.PrintStream s, String fmt, char x)
   {
      s.print(new Format(fmt).form(x));
   }

   /**
    * prints a formatted number following printf conventions
    * 
    * @param s
    *           a PrintStream
    * @param fmt
    *           the format string
    * @param x
    *           the double to print
    */

   public static void print(java.io.PrintStream s, String fmt, double x)
   {
      s.print(new Format(fmt).form(x));
   }

   /**
    * prints a formatted number following printf conventions
    * 
    * @param s
    *           a PrintStream
    * @param fmt
    *           the format string
    * @param x
    *           the long to print
    */
   public static void print(java.io.PrintStream s, String fmt, long x)
   {
      s.print(new Format(fmt).form(x));
   }

   /**
    * prints a formatted number following printf conventions
    * 
    * @param s
    *           a PrintStream, fmt the format string
    * @param x
    *           a string that represents the digits to print
    */

   public static void print(java.io.PrintStream s, String fmt, String x)
   {
      s.print(new Format(fmt).form(x));
   }

   private static String repeat(char c, int n)
   {
      if (n <= 0)
         return "";
      StringBuffer s = new StringBuffer(n);
      for (int i = 0; i < n; i++)
         s.append(c);
      return s.toString();
   }

   private int width;

   private int precision;

   private String pre;

   private String post;

   private boolean leading_zeroes;

   private boolean show_plus;

   private boolean alternate;

   private boolean show_space;

   private boolean left_align;

   private String size_specifier; // one of ll, l, L, h, hh, j, z

   private char fmt; // one of cdeEfgGiosxXos

   public Format(String s)
   {
      width = 0;
      precision = -1;
      pre = "";
      post = "";
      leading_zeroes = false;
      show_plus = false;
      alternate = false;
      show_space = false;
      left_align = false;
      size_specifier = "";
      fmt = ' ';

      int length = s.length();
      int parse_state = PARSE_STATE_PREFIX;
      int i = 0;

      while (parse_state == PARSE_STATE_PREFIX)
      {
         if (i >= length)
            parse_state = PARSE_STATE_END;
         else if (s.charAt(i) == '%')
         {
            if (i < length - 1)
            {
               if (s.charAt(i + 1) == '%')
               {
                  pre = pre + '%';
                  i++;
               }
               else
                  parse_state = PARSE_STATE_FLAGS;
            }
            else
               throw new java.lang.IllegalArgumentException();
         }
         else
            pre = pre + s.charAt(i);
         i++;
      }
      while (parse_state == PARSE_STATE_FLAGS)
      {
         if (i >= length)
            parse_state = PARSE_STATE_END;
         else if (s.charAt(i) == ' ')
            show_space = true;
         else if (s.charAt(i) == '-')
            left_align = true;
         else if (s.charAt(i) == '+')
            show_plus = true;
         else if (s.charAt(i) == '0')
            leading_zeroes = true;
         else if (s.charAt(i) == '#')
            alternate = true;
         else
         {
            parse_state = PARSE_STATE_WIDTH;
            i--;
         }
         i++;
      }
      while (parse_state == PARSE_STATE_WIDTH)
      {
         if (i >= length)
            parse_state = PARSE_STATE_END;
         else if ('0' <= s.charAt(i) && s.charAt(i) <= '9')
         {
            width = width * 10 + s.charAt(i) - '0';
            i++;
         }
         else if (s.charAt(i) == '.')
         {
            parse_state = PARSE_STATE_PRECISION;
            precision = 0;
            i++;
         }
         else
            parse_state = PARSE_STATE_SIZE;
      }
      while (parse_state == PARSE_STATE_PRECISION)
      {
         if (i >= length)
            parse_state = PARSE_STATE_FORMAT;
         else if ('0' <= s.charAt(i) && s.charAt(i) <= '9')
         {
            precision = precision * 10 + s.charAt(i) - '0';
            i++;
         }
         else
            parse_state = PARSE_STATE_SIZE;
      }
      boolean got_size_l = false;
      boolean got_size_h = false;
      while (parse_state == PARSE_STATE_SIZE)
      {
         if (i >= length)
         {
            parse_state = PARSE_STATE_FORMAT;
         }
         else if ('l' == s.charAt(i))
         {
            got_size_l = true;
            size_specifier = "l";
            parse_state = PARSE_STATE_SIZE2;
            i++;
         }
         else if ('L' == s.charAt(i))
         {
            size_specifier = "L";
            i++;
            parse_state = PARSE_STATE_FORMAT;
         }
         else if ('h' == s.charAt(i))
         {
            got_size_h = true;
            size_specifier = "h";
            parse_state = PARSE_STATE_SIZE2;
            i++;
         }
         else if ('j' == s.charAt(i))
         {
            size_specifier = "j";
            i++;
            parse_state = PARSE_STATE_FORMAT;
         }
         else if ('z' == s.charAt(i))
         {
            size_specifier = "z";
            i++;
            parse_state = PARSE_STATE_FORMAT;
         }
         else
            parse_state = PARSE_STATE_FORMAT;
      }
      while (parse_state == PARSE_STATE_SIZE2)
      {
         if (i >= length)
         {
            parse_state = PARSE_STATE_FORMAT;
         }
         else if ('l' == s.charAt(i))
         {
            if (got_size_h)
               throw new IllegalArgumentException(
                     "Unexpected size specifier: hl");
            size_specifier = "ll";
            i++;
            parse_state = PARSE_STATE_FORMAT;
         }
         else if ('h' == s.charAt(i))
         {
            if (got_size_l)
               throw new IllegalArgumentException(
                     "Unexpected size specifier: lh");
            size_specifier = "hh";
            i++;
            parse_state = PARSE_STATE_FORMAT;
         }
         else
         {
            parse_state = PARSE_STATE_FORMAT;
         }
      }
      if (parse_state == PARSE_STATE_FORMAT)
      {
         if (i >= length)
            parse_state = PARSE_STATE_END;
         else
         {
            fmt = s.charAt(i);
            i++;
         }
      }
      // PARSE_STATE_END
      if (i < length)
         post = s.substring(i, length);
   }

   private String exp_format(double d)
   {
      String f = "";
      int e = 0;
      double dd = d;
      double factor = 1;
      while (dd > 10)
      {
         e++;
         factor /= 10;
         dd = dd / 10;
      }
      while (dd < 1)
      {
         e--;
         factor *= 10;
         dd = dd * 10;
      }
      if ((fmt == 'g' || fmt == 'G') && e >= -4 && e < precision)
         return fixed_format(d);

      d = d * factor;
      f = f + fixed_format(d);

      if (fmt == 'e' || fmt == 'g')
         f = f + "e";
      else
         f = f + "E";

      String p = "000";
      if (e >= 0)
      {
         f = f + "+";
         p = p + e;
      }
      else
      {
         f = f + "-";
         p = p + (-e);
      }

      return f + p.substring(p.length() - 3, p.length());
   }

   private String fixed_format(double d)
   {
      String f = "";

      if (d > 0x7FFFFFFFFFFFFFFFL)
         return exp_format(d);

      long l = (long) (precision == 0 ? d + 0.5 : d);
      f = f + l;

      double fr = d - l; // fractional part
      if (fr >= 1 || fr < 0)
         return exp_format(d);

      return f + frac_part(fr);
   }

   /**
    * Formats a character into a string (like sprintf in C)
    * 
    * @param x
    *           the value to format
    * @return the formatted string
    */

   public String form(char c)
   {
      StringBuffer buffer = new StringBuffer();
      
      if (fmt != 'c')
         throw new java.lang.IllegalArgumentException();

      if(c >= 0x20 && c < 0x7f)
      {
         buffer.append(c);
      }
      else
      {
         buffer.append(".");
      }
      
      return pad(buffer.toString());
   }

   /**
    * Formats a double into a string (like sprintf in C)
    * 
    * @param x
    *           the number to format
    * @return the formatted string
    * @exception IllegalArgumentException
    *               if bad argument
    */

   public String form(double x)
   {
      String r;
      if (precision < 0)
         precision = 6;
      int s = 1;
      if (x < 0)
      {
         x = -x;
         s = -1;
      }
      if (fmt == 'f')
         r = fixed_format(x);
      else if (fmt == 'e' || fmt == 'E' || fmt == 'g' || fmt == 'G')
      {
         java.text.NumberFormat format = java.text.NumberFormat.getInstance();
         r = format.format(x);
      }
      else
         throw new java.lang.IllegalArgumentException();

      return pad(sign(s, r));
   }

   /**
    * Formats a long integer into a string (like sprintf in C)
    * 
    * @param x
    *           the number to format
    * @return the formatted string
    */

   public String form(long x)
   {
      String r;
      int s = 0;
      if (fmt == 'X')
         r = convert(x, 4, 15, "0123456789ABCDEF");
      else if (fmt == 'x')
         r = convert(x, 4, 15, "0123456789abcdef");
      else if (fmt == 'd' || fmt == 'i')
      {
         s = 1;
         if (x < 0)
         {
            x = -x;
            s = -1;
         }
         r = "" + x;
      }
      else if (fmt == 'o')
         r = convert(x, 3, 7, "01234567");
      else
         throw new java.lang.IllegalArgumentException(
               "illegal format string for integers: " + fmt);

      return pad(sign(s, r));
   }

   /**
    * Formats a string into a larger string (like sprintf in C)
    * 
    * @param x
    *           the value to format
    * @return the formatted string
    */

   public String form(String s)
   {
      if (fmt != 's')
         throw new java.lang.IllegalArgumentException();
      if (precision >= 0)
         s = s.substring(0, precision);
      return pad(s);
   }

   private String frac_part(double fr)
   // precondition: 0 <= fr < 1
   {
      String z = "";
      if (precision > 0)
      {
         double factor = 1;
         String leading_zeroes = "";
         for (int i = 1; i <= precision && factor <= 0x7FFFFFFFFFFFFFFFL; i++)
         {
            factor *= 10;
            leading_zeroes = leading_zeroes + "0";
         }
         long l = (long) (factor * fr + 0.5);

         z = leading_zeroes + l;
         z = z.substring(z.length() - precision, z.length());
      }

      if (precision > 0 || alternate)
         z = "." + z;
      if ((fmt == 'G' || fmt == 'g') && !alternate)
      // remove trailing zeroes and decimal point
      {
         int t = z.length() - 1;
         while (t >= 0 && z.charAt(t) == '0')
            t--;
         if (t >= 0 && z.charAt(t) == '.')
            t--;
         z = z.substring(0, t + 1);
      }
      return z;
   }

   public char getFormatChar()
   {
      return fmt;
   }

   private String pad(String r)
   {
      String p = repeat(' ', width - r.length());
      if (left_align)
         return pre + r + p + post;
      else
         return pre + p + r + post;
   }

   public char setFormatChar(char _fmt)
   {
      return fmt = _fmt;
   }

   private String sign(int s, String r)
   {
      String p = "";
      if (s < 0)
         p = "-";
      else if (s > 0)
      {
         if (show_plus)
            p = "+";
         else if (show_space)
            p = " ";
      }
      else
      {
         if (fmt == 'o' && alternate && r.length() > 0 && r.charAt(0) != '0')
            p = "0";
         else if (fmt == 'x' && alternate)
            p = "0x";
         else if (fmt == 'X' && alternate)
            p = "0X";
      }
      int w = 0;
      if (leading_zeroes)
         w = width;
      else if ((fmt == 'd' || fmt == 'i' || fmt == 'x' || fmt == 'X' || fmt == 'o')
            && precision > 0)
         w = precision;

      return p + repeat('0', w - p.length() - r.length()) + r;
   }
}
