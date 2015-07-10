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
import java.io.InterruptedIOException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import com.ose.gateway.server.AbstractGatewayClient;
import com.ose.gateway.server.AbstractGatewayServer;

class SerialGatewayClient extends AbstractGatewayClient
{
   private final int id;

   private final Queue<Packet> packets;

   SerialGatewayClient(AbstractGatewayServer server,
                       SocketChannel channel,
                       int id)
   {
      super(server, channel);
      this.id = id;
      packets = new ConcurrentLinkedQueue<Packet>();
   }

   private SerialGatewayServer getServer()
   {
      return (SerialGatewayServer) getGatewayServer();
   }

   public int getId()
   {
      return id;
   }

   @Override
   protected void closeClientHook()
   {
      packets.clear();
      getServer().removeClient(this);
   }

   @Override
   protected ByteBuffer handleCreateRequest(ByteBuffer request)
         throws IOException
   {
      return handleRequest(request);
   }

   @Override
   protected ByteBuffer handleDestroyRequest(ByteBuffer request)
         throws IOException
   {
      return handleRequest(request);
   }

   @Override
   protected ByteBuffer handleSendRequest(ByteBuffer request)
         throws IOException
   {
      return handleRequest(request);
   }

   @Override
   protected ByteBuffer handleReceiveRequest(ByteBuffer request)
         throws IOException
   {
      return handleRequest(request);
   }

   @Override
   protected ByteBuffer handleHuntRequest(ByteBuffer request)
         throws IOException
   {
      return handleRequest(request);
   }

   @Override
   protected ByteBuffer handleAttachRequest(ByteBuffer request)
         throws IOException
   {
      return handleRequest(request);
   }

   @Override
   protected ByteBuffer handleDetachRequest(ByteBuffer request)
         throws IOException
   {
      return handleRequest(request);
   }

   private ByteBuffer handleRequest(ByteBuffer request) throws IOException
   {
      byte[][] requestPayloads;
      int i = 0;
      Packet replyPacket;
      List<byte[]> replyPayloads = new ArrayList<byte[]>();

      // Split the request into one or more payload packets.
      requestPayloads = PacketFactory.splitDataIntoPayloads(request.array());

      // Send the first payload packets of the request.
      while (i < (requestPayloads.length - 1))
      {
         sendPayload(requestPayloads[i++]);
         replyPacket = waitForPacket(true);
         if (replyPacket.getType() != Packet.TYPE_OK)
         {
            sendDisconnect();
            throw new IOException("NOK was sent by target.");
         }
      }

      // Send the last payload packet of the request.
      sendLastPayload(requestPayloads[i]);
      replyPacket = waitForPacket(true);
      if (replyPacket.getType() != Packet.TYPE_OK)
      {
         sendDisconnect();
         throw new IOException("NOK was sent by target.");
      }

      // Receive the first payload packets of the reply.
      replyPacket = waitForPacket(false);
      while (replyPacket.getType() == Packet.TYPE_PAYLOAD)
      {
         try
         {
            replyPayloads.add(replyPacket.getPayload());
            sendOk();
            replyPacket = waitForPacket(false);
         }
         catch (IOException e)
         {
            sendNok();
            replyPacket = waitForPacket(false);
            if (replyPacket.getType() != Packet.TYPE_DISCONNECT)
            {
               sendDisconnect();
               throw new IOException("NOK was ignored by target.");
            }
            throw new IOException("Disconnect was sent by target.");
         }
      }

      // Receive the last payload packet of the reply.
      if (replyPacket.getType() == Packet.TYPE_LAST_PAYLOAD)
      {
         try
         {
            replyPayloads.add(replyPacket.getPayload());
            sendOk();
         }
         catch (IOException e)
         {
            sendNok();
            replyPacket = waitForPacket(false);
            if (replyPacket.getType() != Packet.TYPE_DISCONNECT)
            {
               sendDisconnect();
               throw new IOException("NOK was ignored by target.");
            }
            throw new IOException("Disconnect was sent by target.");
         }
      }
      else
      {
         sendNok();
         replyPacket = waitForPacket(false);
         if (replyPacket.getType() != Packet.TYPE_DISCONNECT)
         {
            sendDisconnect();
            throw new IOException("NOK was ignored by target.");
         }
         throw new IOException("Disconnect was sent by target.");
      }

      // Assemble the reply from its payload packets.
      byte[] replyPayload = PacketFactory.assemblePayloads(replyPayloads);
      ByteBuffer reply = ByteBuffer.allocate(replyPayload.length);
      reply.put(replyPayload);
      reply.flip();
      return reply;
   }

   private void sendPayload(byte[] payload) throws IOException
   {
      byte[] packet = PacketFactory.packData(Packet.TYPE_PAYLOAD, id, payload);
      getServer().writeData(packet);
   }

   private void sendLastPayload(byte[] payload) throws IOException
   {
      byte[] packet = PacketFactory.packData(Packet.TYPE_LAST_PAYLOAD, id, payload);
      getServer().writeData(packet);
   }

   private void sendOk() throws IOException
   {
      byte[] packet = PacketFactory.packData(Packet.TYPE_OK, id);
      getServer().writeData(packet);
   }

   private void sendNok() throws IOException
   {
      byte[] packet = PacketFactory.packData(Packet.TYPE_NOK, id);
      getServer().writeData(packet);
   }

   private void sendDisconnect() throws IOException
   {
      byte[] packet = PacketFactory.packData(Packet.TYPE_DISCONNECT, id);
      getServer().writeData(packet);
   }

   private synchronized Packet waitForPacket(boolean ackReply)
         throws IOException
   {
      while (isOpen() && packets.isEmpty())
      {
         try
         {
            if (ackReply)
            {
               wait(Packet.PACKET_REPLY_TMO);
               if (isOpen() && packets.isEmpty())
               {
                  throw new IOException(
                     "Serial gateway client timed out while waiting for packet.");
               }
            }
            else
            {
               wait();
            }
         }
         catch (InterruptedException e)
         {
            throw new InterruptedIOException(
               "Serial gateway client was interrupted while waiting for data.");
         }
      }

      Packet packet = packets.remove();
      packet.verifyStatus();
      return packet;
   }

   public synchronized void notifyPacketAvailable(Packet packet)
   {
      packets.add(packet);
      notify();
   }
}
