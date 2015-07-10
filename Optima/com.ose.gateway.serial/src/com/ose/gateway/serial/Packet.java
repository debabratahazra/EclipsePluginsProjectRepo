/* COPYRIGHT-ENEA-SRC-R2 *
 **************************************************************************
 * Copyright (C) 2010 by Enea Software AB.
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

package com.ose.gateway.serial;

import java.io.IOException;

class Packet
{
   /** Timeout for packet acknowledge (ACK/NACK response) in milliseconds. */
   public static final long PACKET_REPLY_TMO = 10000;

   /** Timeout for reading a packet in milliseconds. */
   public static final long PACKET_READ_TMO = 5000;

   public static final byte START_BYTE = -121; // 0x87

   public static final byte TYPE_PAYLOAD = 0x01;

   public static final byte TYPE_OK = 0x02;

   public static final byte TYPE_NOK = 0x03;

   public static final byte TYPE_DISCONNECT = 0x04;

   public static final byte TYPE_LAST_PAYLOAD = -127; // 0x81

   public static final int PACKET_STATUS_OK = 0;

   public static final int PACKET_STATUS_CHK_ERROR = 1;

   public static final int PACKET_STATUS_TMO_ERROR = 2;

   private byte type;

   private byte payloadSize;

   private byte checksum;

   private int id;

   private byte[] payload;

   private int status = PACKET_STATUS_OK;

   public byte getType()
   {
      return type;
   }

   public void setType(byte type)
   {
      this.type = type;
   }

   public byte getPayloadSize()
   {
      return payloadSize;
   }

   public void setPayloadSize(byte payloadSize)
   {
      this.payloadSize = payloadSize;
   }

   public byte getChecksum()
   {
      return checksum;
   }

   public void setChecksum(byte checksum)
   {
      this.checksum = checksum;
   }

   public int getId()
   {
      return id;
   }

   public void setId(int id)
   {
      this.id = id;
   }

   public void setId(byte[] id)
   {
      this.id = ((id[0] & 0xFF) << 24) + ((id[1] & 0xFF) << 16) +
                ((id[2] & 0xFF) <<  8) + ((id[3] & 0xFF) <<  0);
   }

   public byte[] getPayload()
   {
      return payload;
   }

   public void setPayload(byte[] payload)
   {
      this.payload = payload;
   }

   public void checkChecksum()
   {
      if (checksum != PacketFactory.calcChecksum(type,
            (byte) (payload.length & 0xFF),
            PacketFactory.intToByteArray(id),
            payload))
      {
         status = PACKET_STATUS_CHK_ERROR;
      }
   }

   public void setTimeoutStatus()
   {
      status = PACKET_STATUS_TMO_ERROR;
   }

   public void verifyStatus() throws IOException
   {
      switch (status)
      {
         case PACKET_STATUS_OK:
            break;
         case PACKET_STATUS_CHK_ERROR:
            throw new IOException(
               "Gateway transport protocol packet received with incorrect checksum");
         case PACKET_STATUS_TMO_ERROR:
            throw new IOException(
               "Gateway transport protocol packet read timeout");
         default:
            throw new IOException(
               "Unknown gateway transport protocol packet status");
      }
   }
}
