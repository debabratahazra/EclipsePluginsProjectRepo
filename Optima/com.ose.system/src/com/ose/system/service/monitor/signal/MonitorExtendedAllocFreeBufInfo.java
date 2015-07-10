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
package com.ose.system.service.monitor.signal;

import java.io.IOException;
import com.ose.gateway.SignalInputStream;
import com.ose.gateway.SignalOutputStream;

public class MonitorExtendedAllocFreeBufInfo
{
   public long signalAddress;     // U 64

   public void read(SignalInputStream in) throws IOException
   {
      signalAddress = in.readS64();
      in.readS64();     // reserved0
      in.readS64();     // reserved1
      in.readS64();     // reserved2
      in.readS64();     // reserved3
   }

   public void write(SignalOutputStream out) throws IOException
   {
      out.writeS64(signalAddress);
      out.writeS64(0);  // reserved0
      out.writeS64(0);  // reserved1
      out.writeS64(0);  // reserved2
      out.writeS64(0);  // reserved3
   }
   
   public String toString() 
   {
      String returnString = "signalAddress: " + signalAddress;
      return returnString;
   }
}
