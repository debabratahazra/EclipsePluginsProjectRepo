/* COPYRIGHT-ENEA-SRC-R2 *
 **************************************************************************
 * Copyright (C) 2005-2007 by Enea Software AB.
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

package com.ose.system.ui.charts;

public class ChartModelInput
{
   private Object input;
   private Object filter;

   public ChartModelInput(Object input, Object filter)
   {
      if (input == null)
      {
         throw new NullPointerException();
      }
      this.input = input;
      this.filter = filter;
   }

   public Object getInput()
   {
      return input;
   }

   public Object getFilter()
   {
      return filter;
   }
}
