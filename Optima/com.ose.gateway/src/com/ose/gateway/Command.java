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
 * This class is the base class for the gateway protocol commands.
 */
abstract class Command
{
   /* Payload types */
   static final int GENERIC_ERROR_REPLY =  0;
   static final int INTERFACE_REQUEST   =  1;
   static final int INTERFACE_REPLY     =  2;
   static final int LOGIN_REQUEST       =  3;
   static final int CHALLENGE_RESPONSE  =  4;
   static final int CHALLENGE_REPLY     =  5;
   static final int LOGIN_REPLY         =  6;
   static final int CREATE_REQUEST      =  7;
   static final int CREATE_REPLY        =  8;
   static final int DESTROY_REQUEST     =  9;
   static final int DESTROY_REPLY       = 10;
   static final int SEND_REQUEST        = 11;
   static final int SEND_REPLY          = 12;
   static final int RECEIVE_REQUEST     = 13;
   static final int RECEIVE_REPLY       = 14;
   static final int HUNT_REQUEST        = 15;
   static final int HUNT_REPLY          = 16;
   static final int ATTACH_REQUEST      = 17;
   static final int ATTACH_REPLY        = 18;
   static final int DETACH_REQUEST      = 19;
   static final int DETACH_REPLY        = 20;
   static final int NAME_REQUEST        = 21;
   static final int NAME_REPLY          = 22;

   /* Authentication schemes */
   static final int AUTH_NO_PASSWORD = 0;
   static final int AUTH_PLAIN_TEXT  = 1;

   /* Server interface flags */
   static final int LITTLE_ENDIAN = 0x00000001;
   static final int USE_AUTH      = 0x00000002;

   /* Payload type */
   int type;

   /* Payload length */
   int length;

   /* Status of reply */
   int status;

   /*
    * Write this gateway protocol command request to the specified data output
    * stream.
    *
    * @param out  the data output stream.
    * @throws IOException  if an I/O exception occurred.
    */
   abstract void writeRequest(DataOutputStream out) throws IOException;

   /*
    * Read the gateway protocol transport header (payload type and length) for
    * this gateway protocol command reply and return the payload type.
    *
    * @param in  the data input stream.
    * @return the payload type.
    * @throws IOException  if an I/O exception occurred.
    */
   final int readHeader(DataInputStream in) throws IOException
   {
      type = in.readInt();
      length = in.readInt();
      return type;
   }

   /*
    * Read this gateway protocol command reply from the specified data input
    * stream.
    *
    * @param in  the data input stream.
    * @throws IOException  if an I/O exception occurred.
    */
   abstract void readReply(DataInputStream in) throws IOException;

   /*
    * Check the status of this gateway protocol command reply.
    *
    * @throws GatewayException  if the reply status is not OK.
    */
   final void checkReply() throws GatewayException
   {
      if (status != GatewayException.OSEGW_EOK)
      {
         throw new GatewayException(status);
      }
   }
}
