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
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.ClosedByInterruptException;
import java.nio.channels.DatagramChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * This abstract class represents a host gateway server.
 *
 * @see com.ose.gateway.server.AbstractGatewayClient
 */
public abstract class AbstractGatewayServer
{
   private static final String BROADCAST_REQUEST =
      "OSEGW? " + GatewayProtocol.PROTOCOL_VERSION + "\n";

   private final String name;
   private final InetSocketAddress serverAddress;
   private final InetSocketAddress broadcastAddress;

   private final CharsetDecoder decoder;
   private final CharsetEncoder encoder;
   private final Selector selector;
   private final ServerSocketChannel serverChannel;
   private final DatagramChannel broadcastChannel;
   private final List clients;
   private final GatewayServerThread thread;

   private volatile boolean open;

   /**
    * Create a new gateway server object.
    *
    * @param name           the name of the gateway server.
    * @param serverPort     the server port of the gateway server.
    * @param broadcastPort  the broadcast port of the gateway server.
    * @throws IOException  if an I/O exception occurred.
    */
   public AbstractGatewayServer(String name, int serverPort, int broadcastPort)
      throws IOException
   {
      Charset charset;

      this.name = ((name != null) ? name : "unknown");

      charset = Charset.forName("ISO-8859-1");
      decoder = charset.newDecoder();
      encoder = charset.newEncoder();

      selector = Selector.open();

      serverChannel = ServerSocketChannel.open();
      serverChannel.socket().setReuseAddress(false);
      serverChannel.socket().bind(new InetSocketAddress(serverPort));
      serverChannel.configureBlocking(false);
      serverChannel.register(selector, SelectionKey.OP_ACCEPT);
      serverAddress = new InetSocketAddress(
            InetAddress.getLocalHost(),
            serverChannel.socket().getLocalPort());

      broadcastChannel = DatagramChannel.open();
      broadcastChannel.socket().setReuseAddress(true);
      broadcastChannel.socket().setBroadcast(true);
      broadcastChannel.socket().bind(new InetSocketAddress(broadcastPort));
      broadcastChannel.configureBlocking(false);
      broadcastChannel.register(selector, SelectionKey.OP_READ);
      broadcastAddress = new InetSocketAddress(
            InetAddress.getByName("255.255.255.255"),
            broadcastChannel.socket().getLocalPort());

      clients = Collections.synchronizedList(new ArrayList());

      thread = new GatewayServerThread();
      open = true;
      thread.start();
   }

   /**
    * Return the name of this gateway server.
    *
    * @return the name of this gateway server.
    */
   public final String getName()
   {
      return name;
   }

   /**
    * Return the server address of this gateway server.
    *
    * @return the server address of this gateway server.
    */
   public final InetSocketAddress getServerAddress()
   {
      return serverAddress;
   }

   /**
    * Return the broadcast address of this gateway server.
    *
    * @return the broadcast address of this gateway server.
    */
   public final InetSocketAddress getBroadcastAddress()
   {
      return broadcastAddress;
   }

   /**
    * Determine whether this gateway server is opened or not.
    *
    * @return true if this gateway server is opened, false otherwise.
    */
   public final boolean isOpen()
   {
      return open;
   }

   /**
    * Close this gateway server.
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
    * Log the given message.
    * <p>
    * The default implementation writes to the standard error stream.
    *
    * @param message  the message to be logged.
    */
   protected void log(String message)
   {
      System.err.println(message);
   }

   /**
    * Log the given exception.
    * <p>
    * The default implementation writes to the standard error stream.
    *
    * @param t  the exception to be logged.
    */
   protected void log(Throwable t)
   {
      t.printStackTrace();
   }

   /**
    * Log the given message and exception.
    * <p>
    * The default implementation writes to the standard error stream.
    *
    * @param message  the message to be logged.
    * @param t        the exception to be logged.
    */
   protected void log(String message, Throwable t)
   {
      System.err.println(message);
      t.printStackTrace();
   }

   /**
    * Return the maximum signal size of this gateway server.
    *
    * @return the maximum signal size of this gateway server.
    */
   protected abstract int getMaxSignalSize();

   /**
    * Determine whether authentication is required or not when connecting to
    * this gateway server. Default is no authentication.
    *
    * @return true if authentication is required when connecting to this gateway
    * server, false otherwise.
    */
   protected boolean isAuthenticationRequired()
   {
      return false;
   }

   /**
    * Return the gateway client password for the given user name or null if the
    * user name is unknown.
    *
    * @param username  a user name.
    * @return the gateway client password for the given user name or null if
    * the user name is unknown.
    */
   protected String getClientPassword(String username)
   {
      return "";
   }

   /**
    * Create a gateway client to this gateway server using the given socket
    * channel.
    *
    * @param channel  the socket channel for the gateway client.
    * @return a gateway client.
    */
   protected abstract AbstractGatewayClient createClient(SocketChannel channel);

   /**
    * A close hook to be implemented by subclasses, called when this gateway
    * server is being closed.
    */
   protected abstract void closeServerHook();

   /**
    * A notification method that is called by a gateway client when it has been
    * closed.
    *
    * @param client  the gateway client that has been closed.
    */
   void clientClosed(AbstractGatewayClient client)
   {
      clients.remove(client);
   }

   /**
    * Run this gateway server.
    */
   private void runServer()
   {
      try
      {
         while (open)
         {
            selector.select();

            for (Iterator i = selector.selectedKeys().iterator(); i.hasNext();)
            {
               SelectionKey key = (SelectionKey) i.next();
               i.remove();

               if (key.isAcceptable())
               {
                  SocketChannel client = serverChannel.accept();
                  client.configureBlocking(true);
                  handleConnection(client);
               }
               else if (key.isReadable())
               {
                  handleBroadcast();
               }
            }
         }
      }
      catch (ClosedByInterruptException e)
      {
         // OK, close requested.
      }
      catch (Exception e)
      {
         log("Exception occurred in Host Gateway Server:", e);
      }
      finally
      {
         closeClients();
         closeResources();
         try
         {
            closeServerHook();
         }
         catch (Exception ignore) {}
         open = false;
      }
   }

   /**
    * Handle a gateway client connection.
    *
    * @param channel  the socket channel for the gateway client.
    */
   private void handleConnection(SocketChannel channel)
   {
      try
      {
         AbstractGatewayClient client = createClient(channel);
         clients.add(client);
         client.start();
      }
      catch (Exception e)
      {
         log("Exception occurred in Host Gateway Server when creating a client:", e);
      }
   }

   /**
    * Handle a gateway broadcast request.
    *
    * @throws IOException  if an I/O exception occurred.
    */
   private void handleBroadcast() throws IOException
   {
      ByteBuffer inBuffer;
      SocketAddress clientAddress;
      String request;

      inBuffer = ByteBuffer.allocate(32);
      clientAddress = broadcastChannel.receive(inBuffer);
      inBuffer.flip();
      request = decoder.decode(inBuffer).toString();

      if ((clientAddress != null) && request.startsWith(BROADCAST_REQUEST))
      {
         String reply;
         ByteBuffer outBuffer;

         reply = "OSEGW! " + GatewayProtocol.PROTOCOL_VERSION + "\n" +
                 "Gateway-addr: tcp://" +
                 serverAddress.getAddress().getHostAddress() +
                 ":" + serverAddress.getPort() + "/\n" +
                 "Gateway-name: " + name + "\n";
         outBuffer = encoder.encode(CharBuffer.wrap(reply));
         broadcastChannel.send(outBuffer, broadcastAddress);
      }
   }

   /**
    * Close any resources used by this gateway server.
    */
   private void closeResources()
   {
      try
      {
         selector.close();
      }
      catch (IOException ignore) {}
      try
      {
         serverChannel.close();
      }
      catch (IOException ignore) {}
      try
      {
         broadcastChannel.close();
      }
      catch (IOException ignore) {}
   }

   /**
    * Close the gateway clients of this gateway server.
    */
   private void closeClients()
   {
      List clientsCopy;
      synchronized (clients)
      {
         clientsCopy = new ArrayList(clients);
      }
      for (Iterator i = clientsCopy.iterator(); i.hasNext();)
      {
         AbstractGatewayClient client = (AbstractGatewayClient) i.next();
         try
         {
            client.close();
         }
         catch (Exception ignore) {}
         i.remove();
      }
   }

   /**
    * The thread that runs the gateway server.
    */
   private class GatewayServerThread extends Thread
   {
      /**
       * Create a new gateway server thread object.
       */
      private GatewayServerThread()
      {
         super("Host Gateway Server");
         setPriority(Thread.NORM_PRIORITY - 1);
      }

      /**
       * Run this gateway server thread.
       *
       * @see java.lang.Thread#run()
       */
      public void run()
      {
         runServer();
      }
   }
}
