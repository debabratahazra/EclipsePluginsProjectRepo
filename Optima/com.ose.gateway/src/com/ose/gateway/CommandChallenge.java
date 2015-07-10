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
 * The gateway protocol challenge command.
 */
class CommandChallenge extends Command
{
   /* Response parameters */
   private String password;

   /* Reply parameters */
   int authType;
   String serverData;

   /*
    * Create a new challenge command object.
    *
    * @param password  the client password.
    */
   CommandChallenge(String password)
   {
      this.password = password;
   }

   /*
    * @see com.ose.gateway.Command#writeRequest(java.io.DataOutputStream)
    */
   void writeRequest(DataOutputStream out) throws IOException
   {
      String clientData;

      clientData = transform();
      out.writeInt(Command.CHALLENGE_RESPONSE);
      out.writeInt(4 + clientData.length());
      out.writeInt(clientData.length());
      out.writeBytes(clientData);
      out.flush();
   }

   /*
    * @see com.ose.gateway.Command#readReply(java.io.DataInputStream)
    */
   void readReply(DataInputStream in) throws IOException
   {
      if (readHeader(in) == Command.CHALLENGE_REPLY)
      {
         int size;
         byte[] tmp;

         status = in.readInt();
         authType = in.readInt();
         size = in.readInt();
         tmp = new byte[size];
         in.readFully(tmp);
         serverData = new String(tmp);
         // Workaround for a Gateway server bug
         in.skipBytes(in.available());
      }
      else
      {
         in.skipBytes(length);
         status = GatewayException.OSEGW_EPROTOCOL_ERROR;
      }
   }

   /*
    * Create the challenge response.
    *
    * @return the challenge response.
    */
   private String transform()
   {
      // When a non-plain text authentication scheme is available
      // create a response by using both serverData and password
      // as input.
      return password;
   }
}
