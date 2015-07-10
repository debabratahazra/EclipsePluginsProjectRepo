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

package com.ose.dbgserver.util;

/** This exception is thrown when a program error is found. */
public class AssertException extends RuntimeException
{
   private static final long serialVersionUID = 1L;

   public AssertException()
   {
      super("Internal error");
   }

   public AssertException(String message)
   {
      super("Internal error: " + message);
   }

   public static void test(boolean a)
   {
      if (!a)
      {
         throw new AssertException();
      }
   }

   public static void test(boolean a, String message)
   {
      if (!a)
      {
         throw new AssertException(message);
      }
   }
}
