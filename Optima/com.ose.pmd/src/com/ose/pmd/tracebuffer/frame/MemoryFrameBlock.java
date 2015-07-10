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

public class MemoryFrameBlock extends FrameBlock
{
   byte[] address; // 8 Bytes
   byte[] length;  // 2 Bytes
   byte[] data;
   
   public MemoryFrameBlock()
   {
      super.frameType = 'M';
      address = new byte[8];
      length = new byte[2];
   }

   public byte[] getAddress()
   {
      return address;
   }

   public byte[] getLength()
   {
      return length;
   }

   public byte[] getData()
   {
      return data;
   }
   
}
