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

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/*
 * The gateway protocol attach command.
 */
class CommandAttach extends Command
{
   /* Request parameters */
   private int pid;
   private int sigNo;
   private byte[] sigData;

   /* Reply parameters */
   int attref;

   /*
    * Create a new attach command object.
    *
    * @param pid  the ID of the process to attach to.
    * @param sigNo  the attach signal number or 0 if the default attach signal
    * should be used.
    * @param sigData  the attach signal data or null if the default attach
    * signal should be used.
    */
   CommandAttach(int pid, int sigNo, byte[] sigData)
   {
      this.pid = pid;
      this.sigNo = sigNo;
      this.sigData = sigData;
   }

   /*
    * @see com.ose.gateway.Command#writeRequest(java.io.DataOutputStream)
    */
   void writeRequest(DataOutputStream out) throws IOException
   {
      int sigSize;

      sigSize = ((sigData != null) ? sigData.length : 0);
      out.writeInt(Command.ATTACH_REQUEST);
      out.writeInt(12 + sigSize);
      out.writeInt(pid);
      if (sigData != null)
      {
         out.writeInt(4 + sigSize);
         out.writeInt(sigNo);
         out.write(sigData);
      }
      else
      {
         out.writeInt(0);
         out.writeInt(0);
      }
      out.flush();
   }

   /*
    * @see com.ose.gateway.Command#readReply(java.io.DataInputStream)
    */
   void readReply(DataInputStream in) throws IOException
   {
      if (readHeader(in) == Command.ATTACH_REPLY)
      {
         status = in.readInt();
         attref = in.readInt();
      }
      else
      {
         in.skipBytes(length);
         status = GatewayException.OSEGW_EPROTOCOL_ERROR;
      }
   }
}
