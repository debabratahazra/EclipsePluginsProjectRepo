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
 * The gateway protocol destroy command.
 */
class CommandDestroy extends Command
{
   /* Request parameters */
   private int pid;

   /* No reply parameters */

   /*
    * Create a new destroy command object.
    *
    * @param pid  the ID of the client process.
    */
   CommandDestroy(int pid)
   {
      this.pid = pid;
   }

   /*
    * @see com.ose.gateway.Command#writeRequest(java.io.DataOutputStream)
    */
   void writeRequest(DataOutputStream out) throws IOException
   {
      out.writeInt(Command.DESTROY_REQUEST);
      out.writeInt(4);
      out.writeInt(pid);
      out.flush();
   }

   /*
    * @see com.ose.gateway.Command#readReply(java.io.DataInputStream)
    */
   void readReply(DataInputStream in) throws IOException
   {
      if (readHeader(in) == Command.DESTROY_REPLY)
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
