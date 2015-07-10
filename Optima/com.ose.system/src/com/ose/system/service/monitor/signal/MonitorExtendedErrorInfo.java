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

public class MonitorExtendedErrorInfo
{
   public long error;    // U64
   public long extra;   //  U64

   public void write(SignalOutputStream out) throws IOException
   {
      out.writeS64(error);
      out.writeS64(extra);
      out.writeS64(0);      // reserved0 unused
      out.writeS64(0);      // reserved1 unused
      out.writeS64(0);      // reserved2 unused
      out.writeS64(0);      // reserved3 unused
   }

   public void read(SignalInputStream in) throws IOException
   {
      error = in.readS64();
      extra = in.readS64();
      in.readS64();             // reserved0 unused
      in.readS64();             // reserved1 unused
      in.readS64();             // reserved2 unused
      in.readS64();             // reserved3 unused
   }
   
   public String toString()
   {
      String returnString = "error: " + error;
      returnString += ", ";
      returnString += "extra: " + extra;
      return returnString;
   }
}
