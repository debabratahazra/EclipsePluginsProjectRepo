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
 * The gateway protocol send command.
 */
class CommandSend extends Command
{
   /* Request parameters */
   private int fromPid;
   private int toPid;
   private int sigNo;
   private byte[] sigData;

   /* No reply parameters */

   /*
    * Create a new send command object.
    *
    * @param fromPid  the ID of the sending process.
    * @param toPid    the ID of the receiving process.
    * @param sigNo    the signal number.
    * @param sigData  the signal data.
    */
   CommandSend(int fromPid, int toPid, int sigNo, byte[] sigData)
   {
      this.fromPid = fromPid;
      this.toPid = toPid;
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
      out.writeInt(Command.SEND_REQUEST);
      out.writeInt(16 + sigSize);
      out.writeInt(fromPid);
      out.writeInt(toPid);
      out.writeInt(4 + sigSize);
      out.writeInt(sigNo);
      if (sigData != null)
      {
         out.write(sigData);
      }
      out.flush();
   }

   /*
    * @see com.ose.gateway.Command#readReply(java.io.DataInputStream)
    */
   void readReply(DataInputStream in) throws IOException
   {
      if (readHeader(in) == Command.SEND_REPLY)
      {
         status = in.readInt();
      }
      else
      {
         in.skipBytes(length);
         status = GatewayException.OSEGW_EPROTOCOL_ERROR;
      }
   }
}
