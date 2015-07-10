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

package com.ose.chart.ui;

/**
 * Represents the margin of a 2D area.
 */
public class Margin
{
   private int left;
   private int top;
   private int right;
   private int bottom;

   public Margin()
   {
      set(0, 0, 0, 0);
   }
   
   public Margin(int left, int top, int right, int bottom)
   {
      set(left, top, right, bottom);
   }

   public Margin(Margin margin)
   {
      set(margin);
   }

   public void set(int left, int top, int right, int bottom)
   {
      setLeft(left);
      setTop(top);
      setRight(right);
      setBottom(bottom);
   }

   public void set(Margin margin)
   {
      set(margin.left, margin.top, margin.right, margin.bottom);
   }

   public int getLeft()
   {
      return left;
   }

   public void setLeft(int left)
   {
      this.left = left;
   }

   public int getRight()
   {
      return right;
   }

   public void setRight(int right)
   {
      this.right = right;
   }

   public int getTop()
   {
      return top;
   }

   public void setTop(int top)
   {
      this.top = top;
   }

   public int getBottom()
   {
      return bottom;
   }

   public void setBottom(int bottom)
   {
      this.bottom = bottom;
   }
}
