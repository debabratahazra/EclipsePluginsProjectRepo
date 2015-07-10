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
 * The gateway protocol receive command.
 */
class CommandReceive extends Command
{
   /* Request parameters */
   private int timeout;
   private int[] sigSelect;

   /* Reply parameters */
   int senderPid;
   int addresseePid;
   int sigSize;
   int sigNo;
   byte[] sigData;

   /*
    * Create a new receive command object.
    *
    * @param timeout  the timeout in milliseconds or -1 for an infinite timeout.
    * @param sigSelect  the signal select array.
    */
   CommandReceive(int timeout, int[] sigSelect)
   {
      this.timeout = timeout;
      this.sigSelect = sigSelect;
   }

   /*
    * @see com.ose.gateway.Command#writeRequest(java.io.DataOutputStream)
    */
   void writeRequest(DataOutputStream out) throws IOException
   {
      out.writeInt(Command.RECEIVE_REQUEST);
      out.writeInt(8 + 4 * sigSelect.length);
      out.writeInt(timeout);
      out.writeInt(sigSelect.length);
      for (int i = 0; i < sigSelect.length; i++)
      {
         out.writeInt(sigSelect[i]);
      }
      out.flush();
   }

   /*
    * @see com.ose.gateway.Command#readReply(java.io.DataInputStream)
    */
   void readReply(DataInputStream in) throws IOException
   {
      if (readHeader(in) == Command.RECEIVE_REPLY)
      {
         status = in.readInt();
         senderPid = in.readInt();
         addresseePid = in.readInt();
         sigSize = in.readInt();
         sigNo = in.readInt();
         if (sigSize > 0)
         {
            if (sigSize > 4)
            {
               sigData = new byte[sigSize - 4];
               in.readFully(sigData);
            }
            else
            {
               sigData = new byte[0];
            }
         }
         // Workaround for a Gateway server bug
         in.skipBytes(in.available());
      }
      else
      {
         in.skipBytes(length);
         status = GatewayException.OSEGW_EPROTOCOL_ERROR;
      }
   }
}
