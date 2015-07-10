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
 * The gateway protocol interface command.
 */
class CommandInterface extends Command
{
   /* Request parameters */
   private int clientVersion;
   private int clientFlags;

   /* Reply parameters */
   int serverVersion;
   int serverFlags;
   int[] payloadTypes;

   /*
    * Create a new interface command object.
    *
    * @param clientVersion  the gateway protocol version of the client.
    * @param clientFlags    the client flags.
    */
   CommandInterface(int clientVersion, int clientFlags)
   {
      this.clientVersion = clientVersion;
      this.clientFlags = clientFlags;
   }

   /*
    * @see com.ose.gateway.Command#writeRequest(java.io.DataOutputStream)
    */
   void writeRequest(DataOutputStream out) throws IOException
   {
      out.writeInt(Command.INTERFACE_REQUEST);
      out.writeInt(8);
      out.writeInt(clientVersion);
      out.writeInt(clientFlags);
      out.flush();
   }

   /*
    * @see com.ose.gateway.Command#readReply(java.io.DataInputStream)
    */
   void readReply(DataInputStream in) throws IOException
   {
      if (readHeader(in) == Command.INTERFACE_REPLY)
      {
         int numTypes;

         status = in.readInt();
         serverVersion = in.readInt();
         serverFlags = in.readInt();
         numTypes = in.readInt();
         payloadTypes = new int[numTypes];
         for (int i = 0; i < numTypes; i++)
         {
            payloadTypes[i] = in.readInt();
         }
      }
      else
      {
         in.skipBytes(length);
         status = GatewayException.OSEGW_EPROTOCOL_ERROR;
      }
   }
}
