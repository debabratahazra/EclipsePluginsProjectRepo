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

public class SymbolTest
{
   @Test
   public void testContains()
   {
      Symbol symbol = new Symbol("testSymbol", 1000L, 512L, true);

      // Check lower bound
      assertFalse(symbol.contains(999L));
      assertTrue(symbol.contains(1000L));

      // Check address in the middle of the symbol range
      assertTrue(symbol.contains(1256L));

      // Check the upper bound
      assertTrue(symbol.contains(1511L));
      assertFalse(symbol.contains(1512L));
   }

   @Test
   public void testContains64Bit()
   {
      Symbol symbol = new Symbol("testSymbol", 0xffffffff00001000L, 0x500L, true);

      // Check lower bound
      assertFalse(symbol.contains(0xffffffff00000fffL));
      assertTrue(symbol.contains(0xffffffff00001000L));

      // Check address in the middle of the symbol range
      assertTrue(symbol.contains(0xffffffff00001250L));

      // Check the upper bound
      assertTrue(symbol.contains(0xffffffff000014ffL));
      assertFalse(symbol.contains(0xffffffff00001500L));
   }

   @Test
   public void testCompareTo()
   {
      // Create two "equal" symbols
      Symbol a = new Symbol("a", 1000L, 512L, true);
      Symbol b = new Symbol("b", 1000L, 512L, true);

      // Check equality for two equal symbols
      assertEquals(0, a.compareTo(b));
      assertEquals(0, b.compareTo(a));
      assertEquals(0, a.compareTo(a));
      assertEquals(0, b.compareTo(b));

      Symbol c = new Symbol("c", 2000L, 512L, true);

      //Check inequality for two non-equal symbols
      assertEquals(-1, a.compareTo(c));
      assertEquals(1, c.compareTo(a));

      // Check equality for a symbol and an address
      assertEquals(0, a.compareTo(1250L));

      // Check inequality for a symbol and an address
      assertEquals(1, a.compareTo(999L));
      assertEquals(-1, a.compareTo(1512L));
   }

   @Test
   public void testCompareTo64Bit()
   {
      // Create two "equal" symbols
      Symbol a = new Symbol("a", 0xffffffff00001000L, 0x500L, true);
      Symbol b = new Symbol("b", 0xffffffff00001000L, 0x500L, true);

      // Check equality for two equal symbols
      assertEquals(0, a.compareTo(b));
      assertEquals(0, b.compareTo(a));
      assertEquals(0, a.compareTo(a));
      assertEquals(0, b.compareTo(b));

      Symbol c = new Symbol("c", 0x7ffffffffffff000L, 0x1500L, true);

      // Check inequality for two non-equal symbols
      assertEquals(1, a.compareTo(c));
      assertEquals(-1, c.compareTo(a));

      // Check equality for a symbol and an address
      assertEquals(0, c.compareTo(0x8000000000000250L));

      // Check inequality for a symbol and an address
      assertEquals(1, c.compareTo(0x7fffffffffffefffL));
      assertEquals(-1, c.compareTo(0x8000000000000500L));
   }
}
