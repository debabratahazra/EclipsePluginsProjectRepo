/* COPYRIGHT-ENEA-SRC-R2 *
 **************************************************************************
 * Copyright (C) 2011 by Enea Software AB.
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

package com.ose.ui.tests.utils;

public class EntityFilter
{
   private boolean include;

   private String value1;
   
   private String value2;

   private int index;
   
   private boolean range;
   
   public EntityFilter()
   {
      include = true;
      value1 = "";
      value2 = "";
      index = -1;
      range = false;
   }
   
   public EntityFilter(int columnIndex, String value, boolean include)
   {
      this.index = columnIndex;
      this.value1 = value;
      this.include = include;
      this.range = false;
   }
   
   public EntityFilter(int columnIndex, String value1, String value2, boolean include)
   {
      this.index = columnIndex;
      this.value1 = value1;
      this.value2 = value2;
      this.include = include;
      this.range = true;
   }

   public boolean isExclude()
   {
      return include;
   }

   public void setExclude(boolean exclude)
   {
      this.include = exclude;
   }

   public String getValue1()
   {
      return value1;
   }

   public void setValue1(String value)
   {
      this.value1 = value;
   }
   
   public String getValue2()
   {
      return value2;
   }

   public void setValue2(String value)
   {
      this.value2 = value;
   }

   public int getIndex()
   {
      return index;
   }

   public void setIndex(int index)
   {
      this.index = index;
   }

   public boolean isRangeAvailable()
   {
      return range;
   }
}
