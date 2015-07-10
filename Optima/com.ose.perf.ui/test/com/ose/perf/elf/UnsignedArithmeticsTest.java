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

package com.ose.perf.elf;

import static org.junit.Assert.*;
import org.junit.Test;

public class UnsignedArithmeticsTest
{
   @Test
   public void testIsLessThan()
   {
      long a = 0x7ffffffffffff000L; 
      long b = 0x8000000000000500L;
      long c = 0x7ffffffffffff050L;
      
      assertTrue(UnsignedArithmetics.isLessThan(a, b));
      assertFalse(UnsignedArithmetics.isLessThan(b, a));
      assertTrue(UnsignedArithmetics.isLessThan(a, c));
      assertFalse(UnsignedArithmetics.isLessThan(c, a));      
   }

   @Test
   public void testIsGreaterThan()
   {
      long a = 0x7ffffffffffff000L; 
      long b = 0x8000000000000500L;
      long c = 0x7ffffffffffff050L;
      
      assertTrue(UnsignedArithmetics.isGreaterThan(b, a));
      assertFalse(UnsignedArithmetics.isGreaterThan(a, b));
      assertTrue(UnsignedArithmetics.isGreaterThan(c, a));
      assertFalse(UnsignedArithmetics.isGreaterThan(a, c));      
   }

   @Test
   public void testIsLessThanOrEqualTo()
   {
      long a = 0x7ffffffffffff000L; 
      long b = 0x8000000000000500L;
      long c = 0x7ffffffffffff050L;
      
      assertTrue(UnsignedArithmetics.isLessThanOrEqualTo(a, b));
      assertFalse(UnsignedArithmetics.isLessThanOrEqualTo(b, a));
      assertTrue(UnsignedArithmetics.isLessThanOrEqualTo(a, c));
      assertFalse(UnsignedArithmetics.isLessThanOrEqualTo(c, a));
      assertTrue(UnsignedArithmetics.isLessThanOrEqualTo(a, a));
      assertTrue(UnsignedArithmetics.isLessThanOrEqualTo(b, b));
   }
}
