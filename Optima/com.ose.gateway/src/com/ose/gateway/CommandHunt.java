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
 * The gateway protocol hunt command.
 */
class CommandHunt extends Command
{
   /* Request parameters */
   private int userNumber;
   private String procName;
   private int sigNo;
   private byte[] sigData;

   /* Reply parameters */
   int pid;

   /*
    * Create a new hunt command object.
    *
    * @param userNumber  the user number.
    * @param procName    the name of the hunted process.
    * @param sigNo       the hunt signal number or 0 if no hunt signal is used.
    * @param sigData     the hunt signal data or null if no hunt signal is used.
    */
   CommandHunt(int userNumber, String procName, int sigNo, byte[] sigData)
   {
      this.userNumber = userNumber;
      this.procName = procName;
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
      out.writeInt(Command.HUNT_REQUEST);
      out.writeInt(21 + procName.length() + sigSize);
      out.writeInt(userNumber);
      out.writeInt(0);
      if (sigData != null)
      {
         out.writeInt(procName.length() + 1);
         out.writeInt(4 + sigSize);
         out.writeInt(sigNo);
      }
      else
      {
         out.writeInt(0);
         out.writeInt(0);
         out.writeInt(0);
      }
      out.writeBytes(procName);
      out.writeByte(0);
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
      if (readHeader(in) == Command.HUNT_REPLY)
      {
         status = in.readInt();
         pid = in.readInt();
      }
      else
      {
         in.skipBytes(length);
         status = GatewayException.OSEGW_EPROTOCOL_ERROR;
      }
   }
}
