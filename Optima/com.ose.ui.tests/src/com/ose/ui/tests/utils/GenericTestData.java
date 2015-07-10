/* COPYRIGHT-ENEA-SRC-R2 *
 **************************************************************************
 * Copyright (C) 2010 by Enea Software AB.
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

public class GenericTestData
{   
   int value1;
   int value2;
   String string1;
   String string2;
   
   public GenericTestData(int value1, int value2, String string1)
   {
      this.value1 = value1;
      this.value2 = value2;
      this.string1 = string1;
   }
   
   public int getValue1()
   {
      return value1;
   }
    
   public int getValue2()
   {
      return value2;
   }
   
   public String getString1()
   {
      return string1;
   }  
}
