/* COPYRIGHT-ENEA-SRC-R2 *
 **************************************************************************
 * Copyright (C) 2009 by Enea Software AB.
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

package com.ose.prof.ui.editors.profiler;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

class TimeFormatter
{
   private static final DateFormat DATE_FORMAT;

   static
   {
      DATE_FORMAT = new SimpleDateFormat("HH:mm:ss.");
      DATE_FORMAT.setTimeZone(TimeZone.getTimeZone("GMT"));
   }

   public static String format(long time)
   {
      long seconds = time / 1000000000L;
      String micros = Long.toString((time % 1000000000L) / 1000L);
      return DATE_FORMAT.format(new Date(seconds * 1000L))
             + padWithLeadingZeroes(micros, 6);
   }

   private static String padWithLeadingZeroes(String str, int fieldWidth)
   {
      int zeroCount = fieldWidth - str.length();

      if (zeroCount > 0)
      {
         StringBuffer buf = new StringBuffer();

         for (int i = 0; i < zeroCount; i++)
         {
            buf.append('0');
         }

         buf.append(str);
         return buf.toString();
      }
      else
      {
         return str;
      }
   }
}
