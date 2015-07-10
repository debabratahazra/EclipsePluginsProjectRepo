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

package com.ose.sigdb.table;

public interface SymDescConstants
{
   public final int OSE_SYMDESC_SIZE = 44;

   public final int OS_MAJVER = 0;

   public final int OS_MINVER = 2;

   public final int OS_SIGDESC_OFFSET = 4;

   public final int OS_VARDESC_OFFSET = 8;

   public final int OS_ENUMDESC_OFFSET = 12;

   public final int OS_STRING_POOL_OFFSET = 16;

   public final int OS_INDEX_POOL_OFFSET = 20;

   public final int OS_SIGDESC_SIZE = 24;

   public final int OS_VARDESC_SIZE = 28;

   public final int OS_ENUMDESC_SIZE = 32;

   public final int OS_STRING_POOL_SIZE = 36;

   public final int OS_INDEX_POOL_SIZE = 40;

   // Byte position in table ose_symbols when 64-bit pointers are used.

   public final int OSE_SYMDESC_SIZE_64 = 68;

   public final int OS_MAJVER_64 = 0;

   public final int OS_MINVER_64 = 2;

   public final int OS_SIGDESC_OFFSET_64 = 8;

   public final int OS_VARDESC_OFFSET_64 = 16;

   public final int OS_ENUMDESC_OFFSET_64 = 24;

   public final int OS_STRING_POOL_OFFSET_64 = 32;

   public final int OS_INDEX_POOL_OFFSET_64 = 40;

   public final int OS_SIGDESC_SIZE_64 = 48;

   public final int OS_VARDESC_SIZE_64 = 52;

   public final int OS_ENUMDESC_SIZE_64 = 56;

   public final int OS_STRING_POOL_SIZE_64 = 60;

   public final int OS_INDEX_POOL_SIZE_64 = 64;
}
