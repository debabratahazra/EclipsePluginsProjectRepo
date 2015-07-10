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

import org.eclipse.jface.viewers.ViewerSorter;

class ProfilerSorter extends ViewerSorter
{
   static final int ASCENDING = 1;
   static final int DESCENDING = -1;

   private int column;
   private int direction;

   ProfilerSorter()
   {
      super();
      reset();
   }

   public void reset()
   {
      column = ProfilerEditor.COLUMN_TICK;
      direction = ASCENDING;
   }

   public void setColumn(int column)
   {
      if (this.column == column)
      {
         // Same column, toggle sort direction.
         direction *= DESCENDING;
      }
      else
      {
         // New column, reset sort direction.
         this.column = column;
         direction = ASCENDING;
      }
   }

   int getColumn()
   {
      return column;
   }
   
   void setDirection(int direction)
   {
      this.direction = direction;
   }

   int getDirection()
   {
      return direction;
   }

   int compareUnsignedInts(int i1, int i2)
   {
      int result;
      long l1 = 0xFFFFFFFFL & i1;
      long l2 = 0xFFFFFFFFL & i2;

      result = (l1 < l2) ? -1 : ((l1 > l2) ? 1 : 0);
      if (direction == DESCENDING)
      {
         result *= DESCENDING;
      }
      return result;
   }

   int compareUnsignedIntsPair(int i11, int i12, int i21, int i22)
   {
      int result;
      long l11 = 0xFFFFFFFFL & i11;
      long l12 = 0xFFFFFFFFL & i12;
      long l21 = 0xFFFFFFFFL & i21;
      long l22 = 0xFFFFFFFFL & i22;

      if (l11 < l21)
      {
         result = -1;
      }
      else if (l11 > l21)
      {
         result = 1;
      }
      else
      {
         result = (l12 < l22) ? -1 : ((l12 > l22) ? 1 : 0);
      }

      if (direction == DESCENDING)
      {
         result *= DESCENDING;
      }
      return result;
   }
}
