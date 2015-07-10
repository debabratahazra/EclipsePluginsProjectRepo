/* COPYRIGHT-ENEA-SRC-R1 *
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

package com.ose.chart.model;

/**
 * Represents a mutable integer range as an offset and a size (count). 
 */
public class MutableRange implements Range
{
   private int offset;
   private int count;

   public MutableRange()
   {
      set(0, 0);
   }

   public MutableRange(int offset, int count)
   {
      set(offset, count);
   }

   public MutableRange(Range range)
   {
      set(range.getOffset(), range.getCount());
   }

   public void set(int offset, int count)
   {
      setOffset(offset);
      setCount(count);
   }

   public void set(Range window)
   {
      set(window.getOffset(), window.getCount());
   }

   public void constrainPosition(Range outerRange)
   {
      offset = Math.max(0, Math.min(offset + count, 
                   outerRange.getOffset() + outerRange.getCount()) - count);
      offset = Math.max(offset, outerRange.getOffset());
   }
      
   public void constrain(Range outerRange)
   {
      int outerOffset = outerRange.getOffset();
      int outerCount = outerRange.getCount();
      
      constrainPosition(outerRange);
      if (offset + count > outerOffset + outerCount)
      {
         count = outerOffset + outerCount - offset;
      }      
   }

   public void move(int delta)
   {
      setOffset(delta + getOffset());
   }
   
   public int getOffset()
   {
      return offset;
   }

   public void setOffset(int offset)
   {
      this.offset = offset;
   }
   
   public int getCount()
   {
      return count;
   }
   
   public void setCount(int count)
   {
      if (count < 0)
      {
         throw new IndexOutOfBoundsException("Negative range count.");
      }
      this.count = count;
   }

   public boolean contains(int index)
   {
      return (index >= offset && index < offset + count);
   }

   public boolean contains(Range range)
   {
      return contains(range.getOffset(), range.getCount());
   }
   
   public boolean contains(int offset, int count)
   {
      int endIndex = offset + count - (count > 0 ? 1 : 0);
            
      return contains(offset) && contains(endIndex);
   }
   
   public boolean isContainedIn(int offset, int count)
   {
      int endIndex = this.offset + this.count - (this.count > 0 ? 1 : 0);
      return (this.offset >= offset && this.offset < offset + count)
             && (endIndex >= offset && endIndex < offset + count);
   }
   
   public boolean equals(Range range)
   {
      return (range != null
              && range.getOffset() == getOffset())
              && (range.getCount() == getCount());
   }

   public boolean equals(int offset, int count)
   {
      return (offset == getOffset()) && (count == getCount());
   }

}
