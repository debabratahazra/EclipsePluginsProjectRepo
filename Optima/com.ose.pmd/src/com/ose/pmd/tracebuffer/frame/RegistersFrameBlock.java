/* COPYRIGHT-ENEA-SRC-R1 *
 **************************************************************************
 * Copyright (C) 2012-2013 by Enea Software AB.
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

package com.ose.pmd.tracebuffer.frame;

import com.ose.pmd.tracebuffer.TracebufferUtil;

public class RegistersFrameBlock extends FrameBlock
{
   /*
    * OSE Registers:
    * r0 ... r 31
    * cr
    * xer
    * lr
    * ctr
    * esr
    * pc
    * msr
    */
   
   byte oseRegisters[];
   byte gdbRegisters[];
   
   public RegistersFrameBlock()
   {
      super.frameType = 'R';
      oseRegisters = new byte[TracebufferUtil.oseRegisters * 4];
   }

   public byte[] getGdbRegisters()
   {
      return gdbRegisters;
   }
   
}
