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

package com.ose.gateway.server;

import java.io.IOException;
import java.io.InterruptedIOException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import com.ose.gateway.Signal;

/**
 * This class represents a host gateway client to be used with the
 * com.ose.gateway.server.GatewayServer class.
 *
 * @see com.ose.gateway.server.AbstractGatewayClient
 * @see com.ose.gateway.server.GatewayServer
 */
final class GatewayClient extends AbstractGatewayClient implements GatewayObject
{
   private static final long PEEK_TIMEOUT = 1000L;

   private final SignalQueue signalQueue = new SignalQueue();

   private int id;
   private String name;

   /**
    * Create a new gateway client object.
    *
    * @param server   the gateway server for this gateway client.
    * @param channel  the socket channel for this gateway client.
    */
   GatewayClient(GatewayServer server, SocketChannel channel)
   {
      super(server, channel);
   }

   /**
    * Return the gateway server for this gateway client.
    *
    * @return the gateway server for this gateway client.
    */
   private GatewayServer getServer()
   {
      return (GatewayServer) getGatewayServer();
   }

   /* (non-Javadoc)
    * @see com.ose.gateway.server.GatewayObject#getId()
    */
   public int getId()
   {
      return id;
   }

   /* (non-Javadoc)
    * @see com.ose.gateway.server.GatewayObject#getName()
    */
   public String getName()
   {
      return name;
   }

   /* (non-Javadoc)
    * @see com.ose.gateway.server.GatewayObject#signalReceived(com.ose.gateway.Signal)
    */
   public void signalReceived(Signal signal)
   {
      signalQueue.post(signal);
   }

   /* (non-Javadoc)
    * @see com.ose.gateway.server.AbstractGatewayClient#closeClientHook()
    */
   protected void closeClientHook()
   {
      signalQueue.clear();
      getServer().removeClient(this);
   }

   /* (non-Javadoc)
    * @see com.ose.gateway.server.AbstractGatewayClient#handleCreateRequest(java.nio.ByteBuffer)
    */
   protected ByteBuffer handleCreateRequest(ByteBuffer request)
      throws IOException
   {
      ByteBuffer reply;

      request.getInt(); // userNumber
      name = strip(getCharsetDecoder().decode(request).toString());

      id = getServer().addClient(this);

      reply = ByteBuffer.allocate(20);
      reply.putInt(GatewayProtocol.CREATE_REPLY);
      reply.putInt(12);
      reply.putInt(GatewayProtocol.OSEGW_EOK);
      reply.putInt(id);
      reply.putInt(getServer().getMaxSignalSize());
      reply.flip();
      return reply;
   }

   /* (non-Javadoc)
    * @see com.ose.gateway.server.AbstractGatewayClient#handleDestroyRequest(java.nio.ByteBuffer)
    */
   protected ByteBuffer handleDestroyRequest(ByteBuffer request)
      throws IOException
   {
      int pid;
      ByteBuffer reply;
      int status;

      pid = request.getInt();

      if (pid != id)
      {
         status = GatewayProtocol.OSEGW_EILLEGAL_PROCESS_ID;
         getServer().log("Unknown pid in host gateway client destroy request");
      }
      else
      {
         status = GatewayProtocol.OSEGW_EOK;
      }

      reply = ByteBuffer.allocate(12);
      reply.putInt(GatewayProtocol.DESTROY_REPLY);
      reply.putInt(4);
      reply.putInt(status);
      reply.flip();
      return reply;
   }

   /* (non-Javadoc)
    * @see com.ose.gateway.server.AbstractGatewayClient#handleSendRequest(java.nio.ByteBuffer)
    */
   protected ByteBuffer handleSendRequest(ByteBuffer request)
      throws IOException
   {
      int sender;
      int addressee;
      int sigSize;
      boolean validSigSize;
      int sigNo;
      byte[] sigData = null;
      ByteBuffer reply;
      int status;

      sender = request.getInt();
      sender = ((sender == 0) ? id : sender);
      addressee = request.getInt();
      sigSize = request.getInt();
      validSigSize = ((sigSize >= 4) && (sigSize <= getServer().getMaxSignalSize()));
      sigNo = request.getInt();
      if (validSigSize)
      {
         sigData = new byte[sigSize - 4];
         request.get(sigData);
      }

      if (!validSigSize)
      {
         status = GatewayProtocol.OSEGW_EBAD_PARAMETER;
      }
      else if (!getServer().isValidId(sender) || !getServer().isValidId(addressee))
      {
         status = GatewayProtocol.OSEGW_EILLEGAL_PROCESS_ID;
      }
      else
      {
         getServer().send(sender, addressee, sigSize, sigNo, sigData);
         status = GatewayProtocol.OSEGW_EOK;
      }

      reply = ByteBuffer.allocate(12);
      reply.putInt(GatewayProtocol.SEND_REPLY);
      reply.putInt(4);
      reply.putInt(status);
      reply.flip();
      return reply;
   }

   /* (non-Javadoc)
    * @see com.ose.gateway.server.AbstractGatewayClient#handleReceiveRequest(java.nio.ByteBuffer)
    */
   protected ByteBuffer handleReceiveRequest(ByteBuffer request)
      throws IOException
   {
      long timeout;
      int sigSelectLength;
      int[] sigSelect = null;
      ByteBuffer reply;
      int status;
      int sender = 0;
      int addressee = 0;
      int sigSize = 0;
      int sigNo = 0;
      byte[] sigData = new byte[0];

      timeout = 0xFFFFFFFFL & request.getInt();
      sigSelectLength = request.getInt();
      if ((sigSelectLength > 0) && (sigSelectLength <= 4096))
      {
         sigSelect = new int[sigSelectLength];
         request.asIntBuffer().get(sigSelect);
      }

      try
      {
         Signal signal = null;

         if (timeout == 0xFFFFFFFFL)
         {
            while ((signal = signalQueue.receive(sigSelect, PEEK_TIMEOUT)) == null)
            {
               if (dataAvailable())
               {
                  // If there is socket data available at this point, it means
                  // that the client has either issued an asynchronous destroy
                  // request or sent erroneous data in violation of the protocol.
                  // In either case, we should shut down.
                  throw new InterruptedIOException();
               }
            }
         }
         else
         {
            long remaining = timeout;
            long peekTimeout = (timeout < PEEK_TIMEOUT) ? timeout : PEEK_TIMEOUT;
            while ((signal = signalQueue.receive(sigSelect, peekTimeout)) == null)
            {
               remaining -= peekTimeout;
               if (remaining <= 0)
               {
                  break;
               }
               if (dataAvailable())
               {
                  // See comment above.
                  throw new InterruptedIOException();
               }
               if (remaining < peekTimeout)
               {
                  peekTimeout = remaining;
               }
            }
         }

         status = GatewayProtocol.OSEGW_EOK;
         if (signal != null)
         {
            sender = signal.getSender();
            addressee = signal.getAddressee();
            sigNo = signal.getSigNo();
            sigData = signal.getData();
            sigSize = sigData.length + 4;
         }
      }
      catch (RuntimeException e)
      {
         status = GatewayProtocol.OSEGW_EBAD_PARAMETER;
         sender = 0;
         addressee = 0;
         sigSize = 0;
         sigNo = 0;
         sigData = new byte[0];
      }

      reply = ByteBuffer.allocate(28 + sigData.length);
      reply.putInt(GatewayProtocol.RECEIVE_REPLY);
      reply.putInt(20 + sigData.length);
      reply.putInt(status);
      reply.putInt(sender);
      reply.putInt(addressee);
      reply.putInt(sigSize);
      reply.putInt(sigNo);
      reply.put(sigData);
      reply.flip();
      return reply;
   }

   /* (non-Javadoc)
    * @see com.ose.gateway.server.AbstractGatewayClient#handleHuntRequest(java.nio.ByteBuffer)
    */
   protected ByteBuffer handleHuntRequest(ByteBuffer request)
      throws IOException
   {
      int nameIndex;
      int sigDataIndex;
      int sigSize;
      int sigNo;
      boolean validInput;
      String name = null;
      byte[] sigData = null;
      ByteBuffer reply;
      int status;
      int pid;

      request.getInt(); // userNumber
      nameIndex = request.getInt();
      sigDataIndex = request.getInt();
      sigSize = request.getInt();
      sigNo = request.getInt();
      validInput = ((nameIndex >= 0) &&
                    (nameIndex <= 4096) &&
                    (sigDataIndex > nameIndex) &&
                    (sigDataIndex <= sigDataIndex + sigSize) &&
                    (sigSize >= 0) &&
                    (sigSize <= getServer().getMaxSignalSize()));
      if (validInput)
      {
         request.limit(request.position() + sigDataIndex);
         request.position(request.position() + nameIndex);
         name = strip(getCharsetDecoder().decode(request).toString());
         request.limit(request.capacity());
         if (sigSize > 4)
         {
            sigData = new byte[sigSize - 4];
            request.get(sigData);
         }
         else
         {
            sigData = new byte[0];
         }
      }

      if (validInput)
      {
         pid = getServer().hunt(this, name, sigSize, sigNo, sigData);
         status = GatewayProtocol.OSEGW_EOK;
      }
      else
      {
         pid = 0;
         status = GatewayProtocol.OSEGW_EBAD_PARAMETER;
      }

      reply = ByteBuffer.allocate(16);
      reply.putInt(GatewayProtocol.HUNT_REPLY);
      reply.putInt(8);
      reply.putInt(status);
      reply.putInt(pid);
      reply.flip();
      return reply;
   }

   /* (non-Javadoc)
    * @see com.ose.gateway.server.AbstractGatewayClient#handleAttachRequest(java.nio.ByteBuffer)
    */
   // XXX: Attach is NOT implemented.
   protected ByteBuffer handleAttachRequest(ByteBuffer request)
      throws IOException
   {
      int pid;
      ByteBuffer reply;
      int status;

      pid = request.getInt();
      // Skip reading sigSize integer.
      // Skip reading sigNo integer.
      // Skip reading sigData byte[].

      if (pid == id)
      {
         status = GatewayProtocol.OSEGW_EATTACHED_TO_CALLER;
      }
      else
      {
         status = GatewayProtocol.OSEGW_EOK;
      }

      reply = ByteBuffer.allocate(16);
      reply.putInt(GatewayProtocol.ATTACH_REPLY);
      reply.putInt(8);
      reply.putInt(status);
      reply.putInt(0); // attref
      reply.flip();
      return reply;
   }

   /* (non-Javadoc)
    * @see com.ose.gateway.server.AbstractGatewayClient#handleDetachRequest(java.nio.ByteBuffer)
    */
   // XXX: Detach is NOT implemented.
   protected ByteBuffer handleDetachRequest(ByteBuffer request)
      throws IOException
   {
      ByteBuffer reply;

      // Skip reading attref integer.

      reply = ByteBuffer.allocate(12);
      reply.putInt(GatewayProtocol.DETACH_REPLY);
      reply.putInt(4);
      reply.putInt(GatewayProtocol.OSEGW_EOK);
      reply.flip();
      return reply;
   }
}
