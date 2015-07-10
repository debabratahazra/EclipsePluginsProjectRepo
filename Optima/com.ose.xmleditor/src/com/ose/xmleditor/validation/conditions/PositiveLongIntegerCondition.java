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

package com.ose.xmleditor.validation.conditions;

import java.math.BigInteger;

public class PositiveLongIntegerCondition extends Condition
{
   private static final String ERROR = "Not a valid positive long integer";

   protected boolean doValidation(String value)
   {
      BigInteger bi = null;
      if (value == null)
      {
         throw new IllegalArgumentException();
      }

      try
      {
         if (value.startsWith("0x") || value.startsWith("0X"))
         {
            bi = new BigInteger(value.substring(2, value.length()), 16);
         }
         else
         {
            bi = new BigInteger(value);
         }
      }
      catch (NumberFormatException e)
      {
         return false;
      }
      BigInteger max = new BigInteger("FFFFFFFFFFFFFFFF", 16);
      BigInteger min = new BigInteger("0", 16);
      if ((bi.compareTo(min) < 0) || (bi.compareTo(max) > 0))
      {
         return false;
      }
      return true;
   }

   protected String getErrorMessage()
   {
      return ERROR;
   }
}
