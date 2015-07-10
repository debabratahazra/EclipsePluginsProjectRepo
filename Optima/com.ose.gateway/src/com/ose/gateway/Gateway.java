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

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InterruptedIOException;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;

/**
 * This class is the main class in the OSE Gateway API for Java and it
 * represents a gateway client that can communicate with a gateway server
 * using the OSE gateway protocol.
 * <p>
 * The OSE Gateway API for Java enables Java programs to communicate with
 * remote OSE processes using the OSE message-passing model for asynchronous
 * interprocess communication. In short, this means that Java programs can
 * find OSE processes by name, supervise OSE processes, send signals to OSE
 * processes, and receive signals from OSE processes.
 * <p>
 * The following Java properties are supported:<br>
 * <ul>
 * <li>gateway.reply.timeout<br>
 * The reply timeout in milliseconds for conceptually non-blocking gateway
 * operations, i.e. all operations except receive(). A value of 0 means infinite
 * timeout. This additional feature was introduced to prevent operations like
 * send() to block potentially indefinitely because of communication issues.
 * Set to 0 to get the original behavior. Default value is 4000 milliseconds.
 * <li>gateway.trace<br>
 * If set to true all signals sent and received using this gateway client will
 * be traced on standard out. Default value is false.
 * </ul>
 */
public final class Gateway
{
   private static final int REPLY_TIMEOUT =
      Integer.getInteger("gateway.reply.timeout", 4000).intValue();
   private static final int PROTOCOL_VERSION = 100;
   private static final int STANDARD_BROADCAST_PORT = 21768;
   private static final String BROADCAST_MSG = "OSEGW? " + PROTOCOL_VERSION + "\n";
   private static final boolean TRACE = Boolean.getBoolean("gateway.trace");

   private String traceSendMsg;
   private String traceReceiveMsg;

   private volatile String clientName;
   private volatile int clientPid;

   private String name;
   private final InetAddress address;
   private final int port;
   private volatile boolean bigEndian;
   private int maxSigSize;

   private Socket socket;
   private DataInputStream in;
   private DataOutputStream out;
   private volatile boolean connected;

   /**
    * Create a new gateway client object.
    *
    * @param gid  the gateway identifier object of the gateway server that this
    * gateway client shall be connected to.
    */
   public Gateway(GatewayIdentifier gid)
   {
      this(gid.getName(), gid.getAddress(), gid.getPort());
   }

   /**
    * Create a new gateway client object.
    *
    * @param address  the address of the gateway server that this gateway client
    * shall be connected to.
    * @param port  the port of the gateway server that this gateway client shall
    * be connected to.
    */
   public Gateway(InetAddress address, int port)
   {
      this("unknown", address, port);
   }

   /**
    * Create a new gateway client object.
    *
    * @param name  the name of the gateway server that this gateway client shall
    * be connected to.
    * @param address  the address of the gateway server that this gateway client
    * shall be connected to.
    * @param port  the port of the gateway server that this gateway client shall
    * be connected to.
    */
   public Gateway(String name, InetAddress address, int port)
   {
      this.name = name;
      this.address = address;
      this.port = port;
   }

   /**
    * Return the name of the OSE process representing this gateway client,
    * if this gateway client is connected to its gateway server.
    *
    * @return the name of the OSE process representing this gateway client.
    */
   public String getClientName()
   {
      return clientName;
   }

   /**
    * Return the ID of the OSE process representing this gateway client,
    * if this gateway client is connected to its gateway server.
    *
    * @return the ID of the OSE process representing this gateway client.
    */
   public int getClientPid()
   {
      return clientPid;
   }

   /**
    * Return the name of the gateway server with the specified address and port.
    *
    * @param address  the gateway server IP address or host name.
    * @param port     the gateway server port.
    * @param timeout  the timeout period in milliseconds, where 0 represents an
    * infinite timeout.
    * @return the name of the gateway server with the specified address and port
    * if it exists and responds within the specified timeout period, null
    * otherwise.
    * @throws IOException  if an I/O exception occurred.
    * @throws GatewayException  if a gateway exception occurred.
    */
   public static String getName(InetAddress address, int port, int timeout)
      throws IOException, GatewayException
   {
      String name = null;
      Socket psocket = null;
      DataInputStream pin = null;
      DataOutputStream pout = null;

      try
      {
         CommandInterface cmdInterface;
         boolean hasCommandName = false;

         psocket = new Socket(address, port);
         psocket.setSoTimeout(timeout);
         pin = new DataInputStream(new BufferedInputStream(psocket.getInputStream()));
         pout = new DataOutputStream(new BufferedOutputStream(psocket.getOutputStream()));

         cmdInterface = new CommandInterface(PROTOCOL_VERSION, 0);
         cmdInterface.writeRequest(pout);
         cmdInterface.readReply(pin);
         cmdInterface.checkReply();

         for (int i = 0; i < cmdInterface.payloadTypes.length; i++)
         {
            if (cmdInterface.payloadTypes[i] == Command.NAME_REQUEST)
            {
               hasCommandName = true;
               break;
            }
         }

         if (hasCommandName)
         {
            CommandName cmdName = new CommandName();
            cmdName.writeRequest(pout);
            cmdName.readReply(pin);
            cmdName.checkReply();
            name = cmdName.name;
         }
      }
      catch (SocketTimeoutException e)
      {
         /* OK, timeout on read. */
      }
      finally
      {
         try
         {
            if (pin != null)
            {
               pin.close();
            }
         }
         catch (IOException e) {}
         try
         {
            if (pout != null)
            {
               pout.close();
            }
         }
         catch (IOException e) {}
         try
         {
            if (psocket != null)
            {
               psocket.close();
            }
         }
         catch (IOException e) {}
      }

      return name;
   }

   /**
    * Return the name of the gateway server that this gateway client
    * communicates with.
    *
    * @return the name of the gateway server that this gateway client
    * communicates with.
    */
   public String getName()
   {
      return name;
   }

   /**
    * Return the address of the gateway server that this gateway client
    * communicates with.
    *
    * @return the address of the gateway server that this gateway client
    * communicates with.
    */
   public InetAddress getAddress()
   {
      // Final field, no synchronization needed.
      return address;
   }

   /**
    * Return the port of the gateway server that this gateway client
    * communicates with.
    *
    * @return the port of the gateway server that this gateway client
    * communicates with.
    */
   public int getPort()
   {
      // Final field, no synchronization needed.
      return port;
   }

   /**
    * Broadcast for gateway servers on the local network using the default
    * gateway broadcast port (21768) and return an array of gateway identifiers
    * for the gateway servers responding within the specified timeout period.
    *
    * @param timeout  the broadcast timeout period in milliseconds, where 0
    * represents an infinite timeout.
    * @return an array of gateway identifiers for the gateway servers found, or
    * an empty array if no gateway servers were found.
    * @throws IOException  if an I/O exception occurred.
    */
   public static GatewayIdentifier[] find(int timeout)
      throws IOException
   {
      return find(STANDARD_BROADCAST_PORT, timeout);
   }

   /**
    * Broadcast for gateway servers on the local network using the specified
    * gateway broadcast port and return an array of gateway identifiers for the
    * gateway servers responding within the specified timeout period.
    *
    * @param port  the broadcast port.
    * @param timeout  the broadcast timeout period in milliseconds, where 0
    * represents an infinite timeout.
    * @return an array of gateway identifiers for the gateway servers found, or
    * an empty array if no gateway servers were found.
    * @throws IOException  if an I/O exception occurred.
    */
   public static GatewayIdentifier[] find(int port, int timeout)
      throws IOException
   {
      DatagramSocket broadcastSocket = null;
      List list = new ArrayList();

      try
      {
         InetAddress broadcastAddress;
         DatagramPacket outPacket;

         broadcastSocket = new DatagramSocket(null);
         broadcastSocket.setReuseAddress(true);
         broadcastSocket.setSoTimeout(timeout);
         broadcastSocket.bind(new InetSocketAddress(port));

         broadcastAddress = InetAddress.getByName("255.255.255.255");
         outPacket = new DatagramPacket(BROADCAST_MSG.getBytes(),
                                        BROADCAST_MSG.length(),
                                        broadcastAddress,
                                        port);
         broadcastSocket.send(outPacket);

         while (true)
         {
            DatagramPacket inPacket;
            String data;

            inPacket = new DatagramPacket(new byte[1500], 1500);
            broadcastSocket.receive(inPacket);
            data = new String(inPacket.getData());
            
            if (data.startsWith("OSEGW!"))
            {
               try
               {
                  GatewayIdentifier gid = new GatewayIdentifier(data);
                  list.add(gid);
               }
               catch (IllegalArgumentException e)
               {
                  System.err.println("Invalid gateway broadcast reply.");
               }
            }
         }
      }
      catch (InterruptedIOException e)
      {
         /* OK, timeout on receive. */
      }
      finally
      {
         if (broadcastSocket != null) broadcastSocket.close();
      }

      return ((GatewayIdentifier[]) list.toArray(new GatewayIdentifier[0]));
   }

   /**
    * Ping the gateway server with the specified address and port.
    *
    * @param address  the gateway server IP address or host name.
    * @param port     the gateway server port.
    * @param timeout  the ping timeout period in milliseconds, where 0
    * represents an infinite timeout.
    * @return true if the specified gateway server responds within the specified
    * timeout period, false otherwise.
    * @throws IOException  if an I/O exception occurred.
    * @throws GatewayException  if a gateway exception occurred.
    */
   public static boolean ping(InetAddress address, int port, int timeout)
      throws IOException, GatewayException
   {
      boolean alive = false;
      Socket psocket = null;
      DataInputStream pin = null;
      DataOutputStream pout = null;

      try
      {
         CommandInterface cmdInterface;

         psocket = new Socket();
         psocket.connect(new InetSocketAddress(address, port), timeout);
         psocket.setSoTimeout(timeout);
         pin = new DataInputStream(new BufferedInputStream(psocket.getInputStream()));
         pout = new DataOutputStream(new BufferedOutputStream(psocket.getOutputStream()));

         cmdInterface = new CommandInterface(PROTOCOL_VERSION, 0);
         cmdInterface.writeRequest(pout);
         cmdInterface.readReply(pin);
         cmdInterface.checkReply();
         alive = true;
      }
      catch (SocketTimeoutException e)
      {
         /* OK, timeout on read. */
      }
      finally
      {
         try
         {
            if (pin != null)
            {
               pin.close();
            }
         }
         catch (IOException e) {}
         try
         {
            if (pout != null)
            {
               pout.close();
            }
         }
         catch (IOException e) {}
         try
         {
            if (psocket != null)
            {
               psocket.close();
            }
         }
         catch (IOException e) {}
      }

      return alive;
   }

   /**
    * Ping the gateway server that this gateway client communicates with.
    *
    * @param timeout  the ping timeout period in milliseconds, where 0
    * represents an infinite timeout.
    * @return true if the gateway server that this gateway client communicates
    * with responds within the specified timeout period, false otherwise.
    * @throws IOException  if an I/O exception occurred.
    * @throws GatewayException  if a gateway exception occurred.
    */
   public boolean ping(int timeout)
      throws IOException, GatewayException
   {
      return Gateway.ping(address, port, timeout);
   }

   /**
    * Connect this gateway client, if it is disconnected, to its gateway server
    * without any authentication.
    *
    * @param clientName  the name of the OSE process that will represent this
    * gateway client.
    * @throws IOException  if an I/O exception occurred.
    * @throws GatewayException  if a gateway exception occurred.
    */
   public synchronized void connect(String clientName)
      throws IOException, GatewayException
   {
      connect(clientName, null, null);
   }

   /**
    * Connect this gateway client, if it is disconnected, to its gateway server
    * using authentication.
    *
    * @param clientName  the name of the OSE process that will represent this
    * gateway client.
    * @param userName  the user name required for connecting to the gateway
    * server.
    * @param password  the password required for connecting to the gateway
    * server.
    * @throws IOException  if an I/O exception occurred.
    * @throws GatewayException  if a gateway exception occurred.
    */
   public synchronized void connect(String clientName, String userName, String password)
      throws IOException, GatewayException
   {
      if (connected)
      {
         return;
      }

      try
      {
         CommandInterface cmdInterface;
         boolean hasCommandName = false;
         CommandLogin cmdLogin;
         CommandChallenge cmdChallenge;
         CommandCreate cmdCreate;

         socket = new Socket(address, port);
         in = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
         out = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));

         try
         {
            socket.setSoTimeout(REPLY_TIMEOUT);
         }
         catch (SocketException ignore) {}

         cmdInterface = new CommandInterface(PROTOCOL_VERSION, 0);
         cmdInterface.writeRequest(out);
         cmdInterface.readReply(in);
         cmdInterface.checkReply();
         // TODO: What to do with cmdInterface.serverVersion?
         bigEndian = ((cmdInterface.serverFlags & Command.LITTLE_ENDIAN) == 0);

         for (int i = 0; i < cmdInterface.payloadTypes.length; i++)
         {
            if (cmdInterface.payloadTypes[i] == Command.NAME_REQUEST)
            {
               hasCommandName = true;
               break;
            }
         }

         if (hasCommandName)
         {
            CommandName cmdName = new CommandName();
            cmdName.writeRequest(out);
            cmdName.readReply(in);
            cmdName.checkReply();
            name = cmdName.name;
         }

         {
            String id = name + " " + address.getHostAddress() + ":" + port;
            traceSendMsg = "Gateway " + id + " sent:";
            traceReceiveMsg = "Gateway " + id + " received:";
         }

         if ((cmdInterface.serverFlags & Command.USE_AUTH) != 0)
         {
            if ((userName == null) || (password == null))
            {
               throw new GatewayException(GatewayException.OSEGW_ELOGIN_FAILED);
            }
            cmdLogin = new CommandLogin(Command.AUTH_PLAIN_TEXT, userName);
            cmdLogin.writeRequest(out);
            cmdChallenge = new CommandChallenge(password);
            cmdChallenge.readReply(in);
            cmdChallenge.checkReply();
            if (cmdChallenge.authType != Command.AUTH_PLAIN_TEXT)
            {
               throw new GatewayException(GatewayException.OSEGW_EUNSUPPORTED_AUTH);
            }
            cmdChallenge.writeRequest(out);
            cmdLogin.readReply(in);
            cmdLogin.checkReply();
         }

         this.clientName = clientName;
         cmdCreate = new CommandCreate(0, clientName);
         cmdCreate.writeRequest(out);
         cmdCreate.readReply(in);
         cmdCreate.checkReply();
         clientPid = cmdCreate.pid;
         maxSigSize = cmdCreate.maxSigSize;
         connected = true;
      }
      catch (IOException e)
      {
         dispose();
         throw e;
      }
   }

   /**
    * Determine whether the gateway server that this gateway client is connected
    * to has big endian byte order or not.
    *
    * @return true if the gateway server that this gateway client is connected
    * to has big endian byte order, false if it has little endian byte order.
    */
   public boolean isBigEndian()
   {
      return bigEndian;
   }

   /**
    * Set the byte order of the gateway server that this gateway client is
    * connected to. If the gateway server runs on another node than the target
    * node and has a different byte order than the target node, this method must
    * be used to change the perceived byte order of the gateway server to the
    * target byte order immediately after connection to the gateway server.
    *
    * @param bigEndian  true if big endian byte order, false if little endian
    * byte order.
    */
   public synchronized void setBigEndian(boolean bigEndian)
   {
      this.bigEndian = bigEndian;
   }

   /**
    * Gracefully disconnect this gateway client, if it is connected, from its
    * gateway server by first waiting for any ongoing gateway operation to
    * complete and finally by waiting for disconnection acknowledgment.
    *
    * @throws IOException  if an I/O exception occurred.
    * @throws GatewayException  if a gateway exception occurred.
    */
   public synchronized void disconnect()
      throws IOException, GatewayException
   {
      if (connected)
      {
         try
         {
            CommandDestroy cmd;

            cmd = new CommandDestroy(clientPid);
            cmd.writeRequest(out);
            cmd.readReply(in);
            cmd.checkReply();
         }
         finally
         {
            dispose();
            connected = false;
         }
      }
   }

   /**
    * Forcefully disconnect this gateway client, if it is connected, from its
    * gateway server without waiting for any ongoing gateway operation to
    * complete and without waiting for disconnection acknowledgment.
    */
   public void forcedDisconnect()
   {
      if (connected)
      {
         try
         {
            CommandDestroy cmd;

            cmd = new CommandDestroy(clientPid);
            cmd.writeRequest(out);
            // Don't wait for a reply.
         }
         catch (Exception e)
         {
            // Ignore exceptions.
         }
         finally
         {
            dispose();
            connected = false;
         }
      }
   }

   /**
    * Determine whether this gateway client is connected to its gateway server
    * or not.
    *
    * @return true if this gateway client is connected to its gateway server,
    * false otherwise.
    */
   public boolean isConnected()
   {
      return connected;
   }

   /*
    * Check the state of this gateway client object.
    *
    * @throws IOException  if this gateway client object is not connected.
    */
   private void checkState() throws IOException
   {
      if (!connected)
      {
         throw new IOException("Gateway not connected.");
      }
   }

   /**
    * Send a signal to the specified OSE process.
    *
    * @param to  the ID of the OSE process to send to.
    * @param sig  the signal to send.
    * @throws IOException  if an I/O exception occurred.
    * @throws GatewayException  if a gateway exception occurred.
    */
   public synchronized void send(int to, Signal sig)
      throws IOException, GatewayException
   {
      byte[] data;
      CommandSend cmd;

      checkState();
      data = sig.javaToOse(bigEndian);
      if ((data != null) && (data.length > maxSigSize))
      {
         throw new GatewayException(GatewayException.OSEGW_EBUFFER_TOO_LARGE);
      }
      cmd = new CommandSend(clientPid, to, sig.getSigNo(), data);
      cmd.writeRequest(out);
      cmd.readReply(in);
      cmd.checkReply();

      if (TRACE)
      {
         System.out.println(traceSendMsg);
         System.out.println(sig);
      }
   }

   /**
    * Send a signal to the specified OSE process with a specified OSE process
    * registered as the sender.
    *
    * @param from  the ID of the OSE process that should be registered as the
    * sender.
    * @param to  the ID of the OSE process to send to.
    * @param sig  the signal to send.
    * @throws IOException  if an I/O exception occurred.
    * @throws GatewayException  if a gateway exception occurred.
    */
   public synchronized void send(int from, int to, Signal sig)
      throws IOException, GatewayException
   {
      byte[] data;
      CommandSend cmd;

      checkState();
      data = sig.javaToOse(bigEndian);
      if ((data != null) && (data.length > maxSigSize))
      {
         throw new GatewayException(GatewayException.OSEGW_EBUFFER_TOO_LARGE);
      }
      cmd = new CommandSend(from, to, sig.getSigNo(), data);
      cmd.writeRequest(out);
      cmd.readReply(in);
      cmd.checkReply();

      if (TRACE)
      {
         System.out.println(traceSendMsg);
         System.out.println(sig);
      }
   }

   /**
    * Receive the first wanted signal from the signal queue of this gateway
    * client.
    * <p>
    * The call returns immediately if a wanted signal can be found in the signal
    * queue. Otherwise it waits until a wanted signal arrives in the signal
    * queue. Unwanted signals are always left in the signal queue. There is no
    * timeout; the call will wait indefinitely if no wanted signal arrives.
    * <p>
    * Only signals registered in the signal registry are considered wanted.
    *
    * @param registry  the signal registry that shall be used to translate OSE
    * signal numbers to Java signal classes. Also defines which signals are
    * wanted.
    * @return the first matching signal from the signal queue of the OSE process
    * representing this gateway client.
    * @throws IOException  if an I/O exception occurred.
    * @throws GatewayException  if a gateway exception occurred.
    */
   public synchronized Signal receive(SignalRegistry registry)
      throws IOException, GatewayException
   {
      CommandReceive cmd;
      Signal sig;

      checkState();
      try
      {
         try
         {
            socket.setSoTimeout(0);
         }
         catch (SocketException ignore) {}
         cmd = new CommandReceive(-1, registry.getSigSelectList());
         cmd.writeRequest(out);
         cmd.readReply(in);
         cmd.checkReply();
      }
      finally
      {
         try
         {
            socket.setSoTimeout(REPLY_TIMEOUT);
         }
         catch (SocketException ignore) {}
      }
      sig = createJavaSignal(cmd.sigNo, cmd.sigData,
                             cmd.senderPid, cmd.addresseePid,
                             cmd.sigSize, registry);
      if (TRACE)
      {
         System.out.println(traceReceiveMsg);
         System.out.println(sig);
      }
      return sig;
   }

   /**
    * Receive the first wanted signal from the signal queue of this gateway
    * client.
    * <p>
    * The call returns immediately if a wanted signal can be found in the signal
    * queue. Otherwise it waits until a wanted signal arrives in the signal
    * queue. Unwanted signals are always left in the signal queue. There is no
    * timeout; the call will wait indefinitely if no wanted signal arrives.
    * <p>
    * Only signals matching the wanted parameter are considered wanted.
    *
    * @param registry  the signal registry that shall be used to translate OSE
    * signal numbers to Java signal classes.
    * @param wanted  a SignalSelect object defining which signals are wanted.
    * @return the first matching signal from the signal queue of the OSE process
    * representing this gateway client.
    * @throws IOException  if an I/O exception occurred.
    * @throws GatewayException  if a gateway exception occurred.
    */
   public synchronized Signal receive(SignalRegistry registry,
                                      SignalSelect wanted)
      throws IOException, GatewayException
   {
      return receive(registry, wanted, -1);
   }

   /**
    * Receive the first wanted signal from the signal queue of this gateway
    * client.
    * <p>
    * The call returns immediately if a wanted signal can be found in the signal
    * queue. Otherwise it waits until a wanted signal arrives in the signal
    * queue or the timeout expires. Unwanted signals are always left in the
    * signal queue.
    * <p>
    * Only signals registered in the signal registry are considered wanted.
    *
    * @param registry  the signal registry that shall be used to translate OSE
    * signal numbers to Java signal classes. Also defines which signals are
    * wanted.
    * @param timeout  the number of milliseconds to wait for a wanted signal.
    * The receive will be aborted if no wanted signal arrives in the signal
    * queue before the timeout expires. A timeout value of 0 can be used to
    * check if a wanted signal is available now.
    * @return the first matching signal from the signal queue of the OSE process
    * representing this gateway client, or null if no matching signal was
    * received before timeout.
    * @throws IOException  if an I/O exception occurred.
    * @throws GatewayException  if a gateway exception occurred.
    */
   public synchronized Signal receive(SignalRegistry registry, int timeout)
      throws IOException, GatewayException
   {
      CommandReceive cmd;
      Signal sig;

      checkState();
      try
      {
         try
         {
            socket.setSoTimeout(0);
         }
         catch (SocketException ignore) {}
         cmd = new CommandReceive(timeout, registry.getSigSelectList());
         cmd.writeRequest(out);
         cmd.readReply(in);
         cmd.checkReply();
      }
      finally
      {
         try
         {
            socket.setSoTimeout(REPLY_TIMEOUT);
         }
         catch (SocketException ignore) {}
      }
      sig = createJavaSignal(cmd.sigNo, cmd.sigData,
                             cmd.senderPid, cmd.addresseePid,
                             cmd.sigSize, registry);
      if (TRACE)
      {
         System.out.println(traceReceiveMsg);
         System.out.println(sig);
      }
      return sig;
   }

   /**
    * Receive the first wanted signal from the signal queue of this gateway
    * client.
    * <p>
    * The call returns immediately if a wanted signal can be found in the signal
    * queue. Otherwise it waits until a wanted signal arrives in the signal
    * queue or the timeout expires. Unwanted signals are always left in the
    * signal queue.
    * <p>
    * Only signals matching the wanted parameter are considered wanted.
    *
    * @param registry  the signal registry that shall be used to translate OSE
    * signal numbers to Java signal classes.
    * @param wanted  a SignalSelect object defining which signals are wanted.
    * @param timeout  the number of milliseconds to wait for a wanted signal.
    * The receive will be aborted if no wanted signal arrives in the signal
    * queue before the timeout expires. A timeout value of 0 can be used to
    * check if a wanted signal is available now.
    * @return the first matching signal from the signal queue of the OSE process
    * representing this gateway client, or null if no matching signal was
    * received before timeout.
    * @throws IOException  if an I/O exception occurred.
    * @throws GatewayException  if a gateway exception occurred.
    */
   public synchronized Signal receive(SignalRegistry registry,
                                      SignalSelect wanted,
                                      int timeout)
      throws IOException, GatewayException
   {
      CommandReceive cmd;
      Signal sig;

      checkState();
      try
      {
         try
         {
            socket.setSoTimeout(0);
         }
         catch (SocketException ignore) {}
         cmd = new CommandReceive(timeout, wanted.sigSelectList);
         cmd.writeRequest(out);
         cmd.readReply(in);
         cmd.checkReply();
      }
      finally
      {
         try
         {
            socket.setSoTimeout(REPLY_TIMEOUT);
         }
         catch (SocketException ignore) {}
      }
      sig = createJavaSignal(cmd.sigNo, cmd.sigData,
                             cmd.senderPid, cmd.addresseePid,
                             cmd.sigSize, registry);
      if (TRACE)
      {
         System.out.println(traceReceiveMsg);
         System.out.println(sig);
      }
      return sig;
   }

   /**
    * Search for an OSE process by name. This method returns immediately.
    *
    * @param name  the name of the OSE process to search for.
    * @return the ID of the found OSE process, or 0 if the named OSE process
    * does not exist at the time of the call.
    * @throws IOException  if an I/O exception occurred.
    * @throws GatewayException  if a gateway exception occurred.
    */
   public synchronized int hunt(String name)
      throws IOException, GatewayException
   {
      CommandHunt cmd;

      checkState();
      cmd = new CommandHunt(0, name, 0, null);
      cmd.writeRequest(out);
      cmd.readReply(in);
      cmd.checkReply();
      return cmd.pid;
   }

   /**
    * Search for an OSE process by name using a hunt signal. This method returns
    * immediately.
    * <p>
    * The specified hunt signal will be sent back to the OSE process
    * representing this gateway client when an OSE process with a matching name
    * is found. Use Signal.getSender() to get the ID of the found OSE process
    * when the hunt signal has been received.
    *
    * @param name  the name of the OSE process to search for.
    * @param sig   the hunt signal that shall be sent back when the named OSE
    * process has been found.
    * @throws IOException  if an I/O exception occurred.
    * @throws GatewayException  if a gateway exception occurred.
    */
   public synchronized void hunt(String name, Signal sig)
      throws IOException, GatewayException
   {
      byte[] data;
      CommandHunt cmd;

      checkState();
      data = sig.javaToOse(bigEndian);
      if ((data != null) && (data.length > maxSigSize))
      {
         throw new GatewayException(GatewayException.OSEGW_EBUFFER_TOO_LARGE);
      }
      cmd = new CommandHunt(0, name, sig.getSigNo(), data);
      cmd.writeRequest(out);
      cmd.readReply(in);
      cmd.checkReply();
   }

   /**
    * Attach a default signal of type AttachSignal to the specified OSE process.
    * The signal is sent back to the OSE process representing this gateway
    * client if the attached OSE process is killed. The signal is sent back
    * immediately if the OSE process is already dead when issuing the attach.
    *
    * @param pid  the ID of the OSE process to attach to.
    * @return an attach ID that may be used in a subsequent call to detach().
    * @throws IOException  if an I/O exception occurred.
    * @throws GatewayException  if a gateway exception occurred.
    */
   public synchronized int attach(int pid)
      throws IOException, GatewayException
   {
      CommandAttach cmd;

      checkState();
      cmd = new CommandAttach(pid, 0, null);
      cmd.writeRequest(out);
      cmd.readReply(in);
      cmd.checkReply();
      return cmd.attref;
   }

   /**
    * Attach a signal to the specified OSE process. The signal is sent back to
    * the OSE process representing this gateway client if the attached OSE
    * process is killed. The signal is sent back immediately if the OSE process
    * is already dead when issuing the attach.
    *
    * @param pid  the ID of the OSE process to attach to.
    * @param sig  the signal to be attached.
    * @return an attach ID that may be used in a subsequent call to detach().
    * @throws IOException  if an I/O exception occurred.
    * @throws GatewayException  if a gateway exception occurred.
    */
   public synchronized int attach(int pid, Signal sig)
      throws IOException, GatewayException
   {
      byte[] data;
      CommandAttach cmd;

      checkState();
      data = sig.javaToOse(bigEndian);
      if ((data != null) && (data.length > maxSigSize))
      {
         throw new GatewayException(GatewayException.OSEGW_EBUFFER_TOO_LARGE);
      }
      cmd = new CommandAttach(pid, sig.getSigNo(), data);
      cmd.writeRequest(out);
      cmd.readReply(in);
      cmd.checkReply();
      return cmd.attref;
   }

   /**
    * Detach a signal from an OSE process previously attached by the OSE process
    * representing this gateway client.
    * <p>
    * It is illegal to detach a signal that has already been received.
    * <p>
    * It is illegal for a gateway client to use an attach ID obtained by another
    * gateway client, i.e. corresponding attach() and detach() calls must be
    * made by the same gateway client.
    *
    * @param attref  the attach ID of a previously attached signal.
    * @throws IOException  if an I/O exception occurred.
    * @throws GatewayException  if a gateway exception occurred.
    */
   public synchronized void detach(int attref)
      throws IOException, GatewayException
   {
      CommandDetach cmd;

      checkState();
      cmd = new CommandDetach(attref);
      cmd.writeRequest(out);
      cmd.readReply(in);
      cmd.checkReply();
   }

   /*
    * Create a Java signal from an OSE signal.
    *
    * @param sigNo  the signal number.
    * @param sigData  the signal data.
    * @param sender  the sender of the signal.
    * @param addressee  the addressee of the signal.
    * @param sigSize  the size of the signal.
    * @param registry  the signal registry to be used.
    * @return the corresponding Java signal.
    * @throws IOException  if an I/O exception occurred.
    */
   private Signal createJavaSignal(int sigNo,
                                   byte[] sigData,
                                   int sender,
                                   int addressee,
                                   int sigSize,
                                   SignalRegistry registry)
      throws IOException
   {
      Signal sig = null;
      Class sigClass;

      if (sigData == null)
      {
         return null;
      }

      // Find the signal class in the signal registry.
      sigClass = registry.getSignalClass(sigNo);

      if (sigClass != null)
      {
         // Create an object of the found signal class.
         try
         {
            sig = (Signal) sigClass.newInstance();
         }
         catch (InstantiationException e)
         {
            // The signal class did not have a default constructor.
            throw new RuntimeException("Default constructor missing in " +
                                       sigClass.getName());
         }
         catch (IllegalAccessException e)
         {
            // The signal class did not have a public default constructor.
            throw new RuntimeException("Default constructor not public in " +
                                       sigClass.getName());
         }
         catch (ClassCastException e)
         {
            // The signal class did not extend Signal.
            throw new RuntimeException(sigClass.getName() +
                                       " do not extend com.ose.gateway.Signal");
         }
         catch (Error e)
         {
            // The signal class constructor did throw an error.
            e.printStackTrace();
            throw e;
         }
      }
      else
      {
         // Did not find a registered signal class.
         sig = new UnregisteredSignal(sigNo);
      }

      // Extract common data from the native signal to the Java signal.
      sig.init(sender, addressee, sigSize);

      // Extract user data from the native signal to the Java signal.
      sig.oseToJava(sigData, bigEndian);

      return sig;
   }

   /*
    * Dispose this gateway client object and release any resources associated
    * with it.
    */
   private void dispose()
   {
      clientName = null;
      clientPid = 0;

      try
      {
         if (in != null)
         {
            in.close();
            in = null;
         }
      }
      catch (IOException e) {}
      try
      {
         if (out != null)
         {
            out.close();
            out = null;
         }
      }
      catch (IOException e) {}
      try
      {
         if (socket != null)
         {
            socket.close();
            socket = null;
         }
      }
      catch (IOException e) {}
   }
}
