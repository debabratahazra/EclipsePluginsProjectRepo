/* COPYRIGHT-ENEA-SRC-R2 *
 **************************************************************************
 * Copyright (C) 2005-2007 by Enea Software AB.
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

package com.ose.gateway;

/**
 * AttachSignal is the default signal used by the Gateway.attach() method
 * when invoked without an attach signal parameter.
 */
public final class AttachSignal extends EmptySignal
{
   /**
    * The OSE signal number for the default AttachSignal. It has the same
    * value as the OSE manifest constant OS_ATTACH_SIG.
    */
   public static final int SIG_NO = 252;

   /**
    * Create a new AttachSignal object.
    */
   AttachSignal()
   {
      super(SIG_NO);
   }
}
