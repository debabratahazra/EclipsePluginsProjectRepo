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

package com.ose.sigdb.util;

/**
 * Constants for target endian reprensentation.
 * 
 * @see Target#getEndian
 */
public interface EndianConstants
{

   /** Constant for big endian representation. */
   public static final int BIG_ENDIAN = 0x1020304;

   /** Constant for little endian representation. */
   public static final int LITTLE_ENDIAN = 0x4030201;
}
