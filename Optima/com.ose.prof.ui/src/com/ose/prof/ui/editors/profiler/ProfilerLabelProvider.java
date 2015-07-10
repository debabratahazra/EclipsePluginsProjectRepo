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

package com.ose.prof.ui.editors.profiler;

import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;
import com.ose.system.Target;

abstract class ProfilerLabelProvider extends LabelProvider
   implements ITableLabelProvider
{
   private boolean relative;
   
   ProfilerLabelProvider(boolean relative)
   {
      this.relative = relative;
   }
   
   void setRelative(boolean relative)
   {
      this.relative = relative;
   }

   boolean isRelative()
   {
      return relative;
   }
   
   public Image getColumnImage(Object element, int columnIndex)
   {
      return null; 
   }

   String toLongString(int i)
   {
      return Long.toString(0xffffffffL & i);
   }

   String toRelativeLongString(int sum, int interval)
   {
      if (isRelative())
      {
         long s = 0xFFFFFFFFL & sum;
         long i = 0xFFFFFFFFL & interval;
         double val = Math.round((1000.0 * s) / i) / 10.0;
         return Double.toString(val) + "%";
      }
      else
      {
         return toLongString(sum);
      }
   }

   String toRelativeLongString(int sum,
                               int interval,
                               int numExecutionUnits,
                               short executionUnit)
   {
      if (isRelative())
      {
         long s = 0xFFFFFFFFL & sum;
         long i = 0xFFFFFFFFL & interval;
         double val;

         if ((numExecutionUnits > 1) && (executionUnit == Target.ALL_EXECUTION_UNITS))
         {
            long e = 0xFFFFFFFFL & numExecutionUnits;
            val = Math.round((1000.0 * s) / ((double) i * e)) / 10.0;
         }
         else
         {
            val = Math.round((1000.0 * s) / i) / 10.0;
         }
         return Double.toString(val) + "%";
      }
      else
      {
         return toLongString(sum);
      }
   }

   String toLongHexString(int i)
   {
      return "0x" + Long.toHexString(0xffffffffL & i);
   }
}
