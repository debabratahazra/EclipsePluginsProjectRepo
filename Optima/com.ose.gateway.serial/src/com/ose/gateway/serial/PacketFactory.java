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

import java.util.List;

class PacketFactory
{
   public static byte[][] splitDataIntoPayloads(byte[] data)
   {
      int numRows = data.length / 256 + 1;
      byte[][] payloads = new byte[numRows][];
      int i = 255;

      while (i < data.length)
      {
         payloads[i / 255 - 1] = new byte[255];
         System.arraycopy(data, i - 255, payloads[i / 255 - 1], 0, 255);
         i += 255;
      }
      payloads[i / 255 - 1] = new byte[data.length - (i - 255)];

      System.arraycopy(data, i - 255, payloads[i / 255 - 1], 0, data.length - (i - 255));
      return payloads;
   }

   public static byte[] assemblePayloads(List<byte[]> payloads)
   {
      int totalPayloadLength = 0;
      for (int i = 0; i < payloads.size(); i++)
      {
         totalPayloadLength += payloads.get(i).length;
      }

      byte[] totalPayload = new byte[totalPayloadLength];
      for (int i = 0, destPos = 0; i < payloads.size(); i++)
      {
         byte[] payload = payloads.get(i);
         System.arraycopy(payload, 0, totalPayload, destPos, payload.length);
         destPos += payload.length;
      }

      return totalPayload;
   }

   public static byte[] packData(byte packetType, int id, byte[] payload)
   {
      byte payloadSize = (byte) (payload.length & 0xFF);
      byte[] idarray = intToByteArray(id);
      byte checksum = calcChecksum(packetType, payloadSize, idarray, payload);
      int numQuotedBytes = 0;

      if (payloadSize == Packet.START_BYTE)
      {
         numQuotedBytes++;
      }

      for (int i = 0; i < idarray.length; i++)
      {
         if (idarray[i] == Packet.START_BYTE)
         {
            numQuotedBytes++;
         }
      }

      for (int i = 0; i < payload.length; i++)
      {
         if (payload[i] == Packet.START_BYTE)
         {
            numQuotedBytes++;
         }
      }

      if (checksum == Packet.START_BYTE)
      {
         numQuotedBytes++;
      }

      byte[] packet = new byte[1 + 1 + 1 + 1 + 4 + payload.length + numQuotedBytes];
      int packetIndex = 0;

      packet[packetIndex++] = Packet.START_BYTE;
      packet[packetIndex++] = packetType;

      packet[packetIndex++] = payloadSize;
      if (payloadSize == Packet.START_BYTE)
      {
         packet[packetIndex++] = payloadSize;
      }

      packet[packetIndex++] = checksum;
      if (checksum == Packet.START_BYTE)
      {
         packet[packetIndex++] = checksum;
      }

      for (int i = 0; i < idarray.length; i++)
      {
         packet[packetIndex++] = idarray[i];
         if (idarray[i] == Packet.START_BYTE)
         {
            packet[packetIndex++] = idarray[i];
         }
      }

      for (int i = 0; i < payload.length; i++)
      {
         packet[packetIndex++] = payload[i];
         if (payload[i] == Packet.START_BYTE)
         {
            packet[packetIndex++] = payload[i];
         }
      }

      return packet;
   }

   public static byte[] packData(byte packetType, int id)
   {
      byte[] idarray = intToByteArray(id);
      byte checksum = calcChecksum(packetType, (byte) 0, idarray, null);
      int numQuotedBytes = 0;

      for (int i = 0; i < idarray.length; i++)
      {
         if (idarray[i] == Packet.START_BYTE)
         {
            numQuotedBytes++;
         }
      }

      if (checksum == Packet.START_BYTE)
      {
         numQuotedBytes++;
      }

      byte[] packet = new byte[8 + numQuotedBytes];
      int packetIndex = 0;

      packet[packetIndex++] = Packet.START_BYTE;
      packet[packetIndex++] = packetType;
      packet[packetIndex++] = 0;

      packet[packetIndex++] = checksum;
      if (checksum == Packet.START_BYTE)
      {
         packet[packetIndex++] = checksum;
      }

      for (int i = 0; i < idarray.length; i++)
      {
         packet[packetIndex++] = idarray[i];
         if (idarray[i] == Packet.START_BYTE)
         {
            packet[packetIndex++] = idarray[i];
         }
      }

      return packet;
   }

   public static int adjustId(int id)
   {
      final int startByte = Packet.START_BYTE & 0xFF;
      int byte1 = (id >>> 24) & 0xFF;
      int byte2 = (id >>> 16) & 0xFF;
      int byte3 = (id >>>  8) & 0xFF;
      int byte4 = (id >>>  0) & 0xFF;

      if (byte1 == startByte)
      {
         byte1++;
      }
      if (byte2 == startByte)
      {
         byte2++;
      }
      if (byte3 == startByte)
      {
         byte3++;
      }
      if (byte4 == startByte)
      {
         byte4++;
      }

      return (byte1 << 24) + (byte2 << 16) + (byte3 << 8) + (byte4 << 0);
   }

   public static byte calcChecksum(byte packetType, byte payloadSize,
         byte[] id, byte[] payload)
   {
      byte[] checksumData;

      if (payload == null)
      {
         checksumData = new byte[6];
      }
      else
      {
         checksumData = new byte[6 + (payloadSize & 0xFF)];
      }

      checksumData[0] = packetType;
      checksumData[1] = payloadSize;
      for (int i = 0; i < id.length; i++)
      {
         checksumData[i + 2] = id[i];
      }
      if (payload != null)
      {
         System.arraycopy(payload, 0, checksumData, 6, payloadSize & 0xFF);
      }

      int checksum = 0;
      int size = checksumData.length;

      for (int i = 0; i < size; i++)
      {
         checksum += (i ^ checksumData[i]);
      }

      return (byte) checksum;
   }

   public static byte[] intToByteArray(int value)
   {
      return new byte[] {(byte) (value >>> 24), (byte) (value >>> 16),
            (byte) (value >>> 8), (byte) (value >>> 0)};
   }
}
