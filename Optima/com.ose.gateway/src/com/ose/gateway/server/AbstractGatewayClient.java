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
import java.nio.channels.ClosedByInterruptException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.util.Arrays;

/**
 * This abstract class represents a host gateway client.
 *
 * @see com.ose.gateway.server.AbstractGatewayServer
 */
public abstract class AbstractGatewayClient
{
   private final AbstractGatewayServer server;
   private final SocketChannel channel;
   private final CharsetDecoder decoder;
   private final GatewayClientThread thread;

   private volatile boolean open;
   private boolean authenticated;
   private boolean connected;
   private byte[] serverData;

   /**
    * Create a new gateway client object.
    *
    * @param server   the gateway server for this gateway client.
    * @param channel  the socket channel for this gateway client.
    */
   public AbstractGatewayClient(AbstractGatewayServer server,
                                SocketChannel channel)
   {
      if ((server == null) || (channel == null))
      {
         throw new NullPointerException();
      }

      this.server = server;
      this.channel = channel;
      decoder = Charset.forName("ISO-8859-1").newDecoder();
      thread = new GatewayClientThread();
   }

   /**
    * Start this gateway client.
    */
   void start()
   {
      open = true;
      thread.start();
   }

   /**
    * Return the gateway server for this gateway client.
    *
    * @return the gateway server for this gateway client.
    */
   public final AbstractGatewayServer getGatewayServer()
   {
      return server;
   }

   /**
    * Return the character set decoder for this gateway client.
    *
    * @return the character set decoder for this gateway client.
    */
   protected final CharsetDecoder getCharsetDecoder()
   {
      return decoder;
   }

   /**
    * Determine whether incoming data is available for this gateway client or
    * not.
    *
    * @return true if incoming data is available for this gateway client, false
    * otherwise.
    * @throws IOException  if an I/O exception occurred.
    */
   protected final boolean dataAvailable() throws IOException
   {
      Selector selector = null;
      int numKeys = 0;
      boolean wasBlocking = channel.isBlocking();

      try
      {
         selector = Selector.open();
         channel.configureBlocking(false);
         channel.register(selector, SelectionKey.OP_READ);
         numKeys = selector.selectNow();
      }
      finally
      {
         if (selector != null)
         {
            selector.close();
         }
         if (wasBlocking)
         {
            channel.configureBlocking(true);
         }
      }

      return (numKeys > 0);
   }

   /**
    * Determine whether this gateway client is opened or not.
    *
    * @return true if this gateway client is opened, false otherwise.
    */
   public final boolean isOpen()
   {
      return open;
   }

   /**
    * Close this gateway client.
    */
   public final void close()
   {
      synchronized (thread)
      {
         open = false;
         thread.interrupt();
      }
   }

   /**
    * A close hook to be implemented by subclasses, called when this gateway
    * client is being closed.
    */
   protected abstract void closeClientHook();

   /**
    * Strip any characters after the terminating null character from the given
    * string.
    *
    * @param str  the string to be stripped.
    * @return the stripped string.
    */
   protected static String strip(String str)
   {
      // Strip any characters after the terminating null character.
      int nullIndex = str.indexOf(0);
      if (nullIndex > 0)
      {
         str = str.substring(0, nullIndex);
      }
      return str;
   }

   /**
    * Run this gateway client.
    */
   private void runClient()
   {
      try
      {
         ByteBuffer header = ByteBuffer.allocate(8);
         ByteBuffer payload;
         int status;
         int type;
         int length;

         while (open)
         {
            ByteBuffer reply = null;

            // Read header (payload type and length).
            header.clear();
            while (((status = channel.read(header)) != -1) && header.hasRemaining());
            if (status == -1)
            {
               return;
            }
            header.flip();
            type = header.getInt();
            length = header.getInt();

            // Validate header.
            if (!isValidPayloadRequestType(type))
            {
               server.log("Unknown request type in Host Gateway Client");
               return;
            }
            if (!isValidPayloadRequestLength(length))
            {
               server.log("Invalid request length in Host Gateway Client");
               return;
            }

            // Read payload.
            payload = ByteBuffer.allocate(8 + length);
            payload.putInt(type);
            payload.putInt(length);
            while (((status = channel.read(payload)) != -1) && payload.hasRemaining());
            if (status == -1)
            {
               return;
            }
            payload.flip();
            payload.position(8);

            // Handle payload according to type.
            switch (type)
            {
               case GatewayProtocol.INTERFACE_REQUEST:
                  reply = handleInterfaceRequest(payload);
                  break;
               case GatewayProtocol.NAME_REQUEST:
                  reply = handleNameRequest(payload);
                  break;
               case GatewayProtocol.LOGIN_REQUEST:
                  reply = handleLoginRequest(payload);
                  break;
               case GatewayProtocol.CHALLENGE_RESPONSE:
                  reply = handleChallengeResponse(payload);
                  authenticated = (reply.getInt(8) == GatewayProtocol.OSEGW_EOK);
                  break;
               case GatewayProtocol.CREATE_REQUEST:
                  if (server.isAuthenticationRequired() && !authenticated)
                  {
                     reply = handleUnauthenticatedCreateRequest(payload);
                  }
                  else
                  {
                     reply = handleCreateRequest(payload);
                  }
                  connected = (reply.getInt(8) == GatewayProtocol.OSEGW_EOK);
                  break;
               case GatewayProtocol.DESTROY_REQUEST:
                  checkConnected();
                  reply = handleDestroyRequest(payload);
                  if (reply.getInt(8) == GatewayProtocol.OSEGW_EOK)
                  {
                     open = false;
                  }
                  break;
               case GatewayProtocol.SEND_REQUEST:
                  checkConnected();
                  // Optimization: Ignore the send reply from
                  // handleSendRequest() and instead send a send reply directly.
                  channel.write(handleSendReply(payload));
                  handleSendRequest(payload);
                  break;
               case GatewayProtocol.RECEIVE_REQUEST:
                  checkConnected();
                  reply = handleReceiveRequest(payload);
                  break;
               case GatewayProtocol.HUNT_REQUEST:
                  checkConnected();
                  reply = handleHuntRequest(payload);
                  break;
               case GatewayProtocol.ATTACH_REQUEST:
                  checkConnected();
                  reply = handleAttachRequest(payload);
                  break;
               case GatewayProtocol.DETACH_REQUEST:
                  checkConnected();
                  reply = handleDetachRequest(payload);
                  break;
               default:
                  server.log("Unknown request type in Host Gateway Client");
                  return;
            }

            // Write reply.
            if (reply != null)
            {
               channel.write(reply);
            }
         }
      }
      catch (ClosedByInterruptException e)
      {
         // OK, close requested.
      }
      catch (InterruptedIOException e)
      {
         // OK, close requested.
      }
      catch (Exception e)
      {
         server.log("Exception occurred in Host Gateway Client:", e);
      }
      finally
      {
         closeResources();
         try
         {
            closeClientHook();
         }
         catch (Exception ignore) {}
         server.clientClosed(this);
         open = false;
      }
   }

   /**
    * Determine whether the given gateway payload request type is valid or not.
    *
    * @param type  a gateway payload request type.
    * @return true if the given gateway payload request type is valid, false
    * otherwise.
    */
   private static boolean isValidPayloadRequestType(int type)
   {
      switch (type)
      {
         case GatewayProtocol.INTERFACE_REQUEST:
         case GatewayProtocol.NAME_REQUEST:
         case GatewayProtocol.LOGIN_REQUEST:
         case GatewayProtocol.CHALLENGE_RESPONSE:
         case GatewayProtocol.CREATE_REQUEST:
         case GatewayProtocol.DESTROY_REQUEST:
         case GatewayProtocol.SEND_REQUEST:
         case GatewayProtocol.RECEIVE_REQUEST:
         case GatewayProtocol.HUNT_REQUEST:
         case GatewayProtocol.ATTACH_REQUEST:
         case GatewayProtocol.DETACH_REQUEST:
            return true;
         default:
            return false;
      }
   }

   /**
    * Determine whether the given gateway payload request length is valid or
    * not.
    *
    * @param length  a gateway payload request length.
    * @return true if the given gateway payload request length is valid, false
    * otherwise.
    */
   private boolean isValidPayloadRequestLength(int length)
   {
      return ((length > 0) && (length <= server.getMaxSignalSize()));
   }

   /**
    * Check whether this gateway client is connected, throws an
    * IllegalStateException otherwise.
    *
    * @throws IllegalStateException  if this gateway client is not connected.
    */
   private void checkConnected() throws IllegalStateException
   {
      if (!connected)
      {
         throw new IllegalStateException(
               "A not yet connected Host Gateway Client received " +
               "a gateway request that requires connection.");
      }
   }

   /**
    * Return random data to be used when encoding a password.
    *
    * @return random data.
    */
   private static byte[] getRandom()
   {
      // TODO: Not implemented.
      return null;
   }

   /**
    * Encode the given password string using the given random data.
    *
    * @param password  a password string.
    * @param random    random data.
    * @return an encoded version of the given password.
    */
   private static byte[] encodePassword(String password, byte[] random)
   {
      // TODO: Not implemented.
      return null;
   }

   /**
    * Close any resources used by this gateway client.
    */
   private void closeResources()
   {
      try
      {
         channel.close();
      }
      catch (IOException ignore) {}
   }

   /**
    * Validate the given gateway send request and return a gateway send reply.
    *
    * @param request  a gateway send request.
    * @return a gateway send reply.
    * @throws IOException  if an I/O exception occurred.
    */
   private ByteBuffer handleSendReply(ByteBuffer request) throws IOException
   {
      int sigSize;
      boolean validSigSize;
      ByteBuffer reply;
      int status;

      sigSize = request.getInt(16);
      validSigSize = ((sigSize >= 4) && (sigSize <= server.getMaxSignalSize()));
      status = (validSigSize ? GatewayProtocol.OSEGW_EOK :
         GatewayProtocol.OSEGW_EBAD_PARAMETER);

      reply = ByteBuffer.allocate(12);
      reply.putInt(GatewayProtocol.SEND_REPLY);
      reply.putInt(4);
      reply.putInt(status);
      reply.flip();
      return reply;
   }

   /**
    * Handle the given gateway interface request and return a gateway interface
    * reply.
    *
    * @param request  a gateway interface request.
    * @return a gateway interface reply.
    * @throws IOException  if an I/O exception occurred.
    */
   private ByteBuffer handleInterfaceRequest(ByteBuffer request)
      throws IOException
   {
      ByteBuffer reply;
      int serverFlags;

      // Skip reading clientVersion integer.
      // Skip reading clientFlags integer.

      serverFlags = (server.isAuthenticationRequired() ?
                     GatewayProtocol.USE_AUTH : 0);

      reply = ByteBuffer.allocate(64);
      reply.putInt(GatewayProtocol.INTERFACE_REPLY);
      reply.putInt(56);
      reply.putInt(GatewayProtocol.OSEGW_EOK);
      reply.putInt(GatewayProtocol.PROTOCOL_VERSION);
      reply.putInt(serverFlags);
      reply.putInt(10);
      reply.putInt(GatewayProtocol.INTERFACE_REQUEST);
      reply.putInt(GatewayProtocol.NAME_REQUEST);
      reply.putInt(GatewayProtocol.LOGIN_REQUEST);
      reply.putInt(GatewayProtocol.CREATE_REQUEST);
      reply.putInt(GatewayProtocol.DESTROY_REQUEST);
      reply.putInt(GatewayProtocol.SEND_REQUEST);
      reply.putInt(GatewayProtocol.RECEIVE_REQUEST);
      reply.putInt(GatewayProtocol.HUNT_REQUEST);
      reply.putInt(GatewayProtocol.ATTACH_REQUEST);
      reply.putInt(GatewayProtocol.DETACH_REQUEST);
      reply.flip();
      return reply;
   }

   /**
    * Handle the given gateway name request and return a gateway name reply.
    *
    * @param request  a gateway name request.
    * @return a gateway name reply.
    * @throws IOException  if an I/O exception occurred.
    */
   private ByteBuffer handleNameRequest(ByteBuffer request)
      throws IOException
   {
      ByteBuffer reply;
      String name;
      int nameLength;

      // Skip reading reserved integer.

      name = server.getName();
      nameLength = name.length() + 1;

      reply = ByteBuffer.allocate(16 + nameLength);
      reply.putInt(GatewayProtocol.NAME_REPLY);
      reply.putInt(8 + nameLength);
      reply.putInt(GatewayProtocol.OSEGW_EOK);
      reply.putInt(nameLength);
      reply.put(name.getBytes("ISO-8859-1"));
      reply.put((byte) 0);
      reply.flip();
      return reply;
   }

   /**
    * Handle the given gateway login request and return a gateway challenge
    * reply.
    *
    * @param request  a gateway login request.
    * @return a gateway challenge reply.
    * @throws IOException  if an I/O exception occurred.
    */
   private ByteBuffer handleLoginRequest(ByteBuffer request)
      throws IOException
   {
      int authType;
      String userName;
      ByteBuffer reply;
      int status;
      byte[] random;

      authType = request.getInt();
      userName = strip(decoder.decode(request).toString());

      if (authType == GatewayProtocol.AUTH_PLAIN_TEXT)
      {
         String password = server.getClientPassword(userName);
         if (password != null)
         {
            status = GatewayProtocol.OSEGW_EOK;
            random = getRandom();
            serverData = encodePassword(password, random);
         }
         else
         {
            status = GatewayProtocol.OSEGW_ELOGIN_FAILED;
            random = new byte[] {0};
         }
      }
      else
      {
         status = GatewayProtocol.OSEGW_EUNSUPPORTED_AUTH;
         random = new byte[] {0};
      }

      reply = ByteBuffer.allocate(20 + random.length);
      reply.putInt(GatewayProtocol.CHALLENGE_REPLY);
      reply.putInt(12 + random.length);
      reply.putInt(status);
      reply.putInt(GatewayProtocol.AUTH_PLAIN_TEXT);
      reply.putInt(random.length);
      reply.put(random);
      reply.flip();
      return reply;
   }

   /**
    * Handle the given gateway challenge response and return a gateway login
    * reply.
    *
    * @param request  a gateway challenge response.
    * @return a gateway login reply.
    * @throws IOException  if an I/O exception occurred.
    */
   private ByteBuffer handleChallengeResponse(ByteBuffer request)
      throws IOException
   {
      int clientDataLength;
      byte[] clientData;
      ByteBuffer reply;
      int status;

      clientDataLength = request.getInt();
      clientData = new byte[clientDataLength];
      request.get(clientData);

      status = (Arrays.equals(clientData, serverData) ?
                GatewayProtocol.OSEGW_EOK :
                GatewayProtocol.OSEGW_ELOGIN_FAILED);
      serverData = null;

      reply = ByteBuffer.allocate(12);
      reply.putInt(GatewayProtocol.LOGIN_REPLY);
      reply.putInt(4);
      reply.putInt(status);
      reply.flip();
      return reply;
   }

   /**
    * Handle the given unauthenticated gateway create request and return a
    * negative gateway create reply.
    *
    * @param request  an unauthenticated gateway create request.
    * @return a negative gateway create reply.
    * @throws IOException  if an I/O exception occurred.
    */
   private ByteBuffer handleUnauthenticatedCreateRequest(ByteBuffer request)
      throws IOException
   {
      ByteBuffer reply = ByteBuffer.allocate(20);
      reply.putInt(GatewayProtocol.CREATE_REPLY);
      reply.putInt(12);
      reply.putInt(GatewayProtocol.OSEGW_ELOGIN_FAILED);
      reply.putInt(0);
      reply.putInt(server.getMaxSignalSize());
      reply.flip();
      return reply;
   }

   /**
    * Handle the given gateway create request and return a gateway create reply.
    * <p>
    * This method should be implemented by subclasses.
    *
    * @param request  a gateway create request.
    * @return a gateway create reply.
    * @throws IOException  if an I/O exception occurred.
    */
   protected abstract ByteBuffer handleCreateRequest(ByteBuffer request)
      throws IOException;

   /**
    * Handle the given gateway destroy request and return a gateway destroy
    * reply.
    * <p>
    * This method should be implemented by subclasses.
    *
    * @param request  a gateway destroy request.
    * @return a gateway destroy reply.
    * @throws IOException  if an I/O exception occurred.
    */
   protected abstract ByteBuffer handleDestroyRequest(ByteBuffer request)
      throws IOException;

   /**
    * Handle the given gateway send request and return a gateway send reply.
    * <p>
    * This method should be implemented by subclasses.
    *
    * @param request  a gateway send request.
    * @return a gateway send reply.
    * @throws IOException  if an I/O exception occurred.
    */
   protected abstract ByteBuffer handleSendRequest(ByteBuffer request)
      throws IOException;

   /**
    * Handle the given gateway receive request and return a gateway receive
    * reply.
    * <p>
    * This method should be implemented by subclasses.
    *
    * @param request  a gateway receive request.
    * @return a gateway receive reply.
    * @throws IOException  if an I/O exception occurred.
    */
   protected abstract ByteBuffer handleReceiveRequest(ByteBuffer request)
      throws IOException;

   /**
    * Handle the given gateway hunt request and return a gateway hunt reply.
    * <p>
    * This method should be implemented by subclasses.
    *
    * @param request  a gateway hunt request.
    * @return a gateway hunt reply.
    * @throws IOException  if an I/O exception occurred.
    */
   protected abstract ByteBuffer handleHuntRequest(ByteBuffer request)
      throws IOException;

   /**
    * Handle the given gateway attach request and return a gateway attach reply.
    * <p>
    * This method should be implemented by subclasses.
    *
    * @param request  a gateway attach request.
    * @return a gateway attach reply.
    * @throws IOException  if an I/O exception occurred.
    */
   protected abstract ByteBuffer handleAttachRequest(ByteBuffer request)
      throws IOException;

   /**
    * Handle the given gateway detach request and return a gateway detach reply.
    * <p>
    * This method should be implemented by subclasses.
    *
    * @param request  a gateway detach request.
    * @return a gateway detach reply.
    * @throws IOException  if an I/O exception occurred.
    */
   protected abstract ByteBuffer handleDetachRequest(ByteBuffer request)
      throws IOException;

   /**
    * The thread that runs the gateway client.
    */
   private class GatewayClientThread extends Thread
   {
      /**
       * Create a new gateway client thread object.
       */
      private GatewayClientThread()
      {
         super("Host Gateway Client");
         setDaemon(true);
         setPriority(Thread.NORM_PRIORITY - 1);
      }

      /**
       * Run this gateway client thread.
       *
       * @see java.lang.Thread#run()
       */
      public void run()
      {
         runClient();
      }
   }
}
