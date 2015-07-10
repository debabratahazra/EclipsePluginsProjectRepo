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

class UnsignedArithmetics
{
   // 64 bit unsigned comparison.
   static boolean isLessThan(long n1, long n2)
   {
      // Reverse comparison if sign bits differ.
      return (n1 < n2) ^ ((n1 < 0) != (n2 < 0));
   }

   // 64 bit unsigned comparison.
   static boolean isGreaterThan(long n1, long n2)
   {
      // Reverse comparison if sign bits differ.
      return (n1 > n2) ^ ((n1 < 0) != (n2 < 0));
   }

   // 64 bit unsigned comparison.
   static boolean isLessThanOrEqualTo(long n1, long n2)
   {
      return (n1 == n2) || isLessThan(n1, n2);
   }

   // 64 bit unsigned comparison.
   static boolean isGreaterThanOrEqualTo(long n1, long n2)
   {
      return (n1 == n2) || isGreaterThan(n1, n2);
   }
}
