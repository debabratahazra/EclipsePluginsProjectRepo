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
import com.ose.gateway.Signal;
import com.ose.gateway.SignalRegistry;
import com.ose.gateway.UnregisteredSignal;

/**
 * This abstract class represents a host gateway service to be used with the
 * com.ose.gateway.server.GatewayServer class.
 *
 * @see com.ose.gateway.server.GatewayServer#registerService(AbstractGatewayService)
 */
public abstract class AbstractGatewayService implements GatewayObject
{
   private GatewayServer server;
   private int id;

   /**
    * Initialize a new gateway service object.
    *
    * @param server  the gateway server hosting this gateway service.
    * @param id      the ID of this gateway service.
    */
   void init(GatewayServer server, int id)
   {
      this.server = server;
      this.id = id;
   }

   /**
    * Return the gateway server hosting this gateway service.
    *
    * @return the gateway server hosting this gateway service.
    */
   public final GatewayServer getGatewayServer()
   {
      return server;
   }

   /**
    * Return the ID of this gateway service.
    *
    * @return the ID of this gateway service.
    * @see com.ose.gateway.server.GatewayObject#getId()
    */
   public final int getId()
   {
      return id;
   }

   /**
    * Return the name of this gateway service.
    *
    * @return the name of this gateway service.
    * @see com.ose.gateway.server.GatewayObject#getName()
    */
   public abstract String getName();

   /**
    * Called by the hosting gateway server when a signal has been sent to this
    * gateway service.
    *
    * @param signal  the signal that has been received.
    * @see com.ose.gateway.server.GatewayObject#signalReceived(com.ose.gateway.Signal)
    */
   public abstract void signalReceived(Signal signal);

   /**
    * Called by the hosting gateway server when a signal has been sent to this
    * gateway service.
    *
    * @param sender     the ID of the gateway object that sent the signal.
    * @param addressee  the ID of the gateway object that the signal was sent to.
    * @param sigSize    the size of the signal in bytes.
    * @param sigNo      the signal number of the signal.
    * @param sigData    the data of the signal.
    */
   public abstract void signalReceived(int sender,
                                       int addressee,
                                       int sigSize,
                                       int sigNo,
                                       byte[] sigData);

   /**
    * Called by this gateway service when it wants to send a signal to a
    * gateway object.
    *
    * @param to      the ID of the gateway object to send to.
    * @param signal  the signal to send.
    */
   // XXX: Signal is assumed to have correct byte order.
   protected final void send(int to, Signal signal)
   {
      if (signal == null)
      {
         throw new NullPointerException();
      }

      // FIXME: Set up signal size and signal data.
      signal.init(getId(), to, signal.getSigSize());
      server.send(signal);
   }

   /**
    * Called by this gateway service when it wants to send a signal to a gateway
    * object with a specified gateway object registered as the sender.
    *
    * @param from    the ID of the gateway object that should be registered as
    * the sender.
    * @param to      the ID of the gateway object to send to.
    * @param signal  the signal to send.
    */
   // XXX: Signal is assumed to have correct byte order.
   protected final void send(int from, int to, Signal signal)
   {
      if (signal == null)
      {
         throw new NullPointerException();
      }

      // FIXME: Set up signal size and signal data.
      signal.init(from, to, signal.getSigSize());
      server.send(signal);
   }

   /**
    * Called by this gateway service when it wants to search for a gateway
    * object by name.
    *
    * @param name  the name of the gateway object to search for.
    * @return the ID of the found gateway object, or 0 if the named gateway
    * object does not exist at the time of the call.
    */
   protected final int hunt(String name)
   {
      return server.hunt(this, name, 0, 0, null);
   }

   /**
    * Called by this gateway service when it wants to search for a gateway
    * object by name using a hunt signal.
    * <p>
    * The specified hunt signal will be sent back to this gateway service when a
    * gateway object with a matching name is found. Use Signal.getSender() to get
    * the ID of the found gateway object when the hunt signal has been received.
    *
    * @param name    the name of the gateway object to search for.
    * @param signal  the hunt signal that shall be sent back when the named
    * gateway object has been found.
    * @throws IOException  if an I/O exception occurred.
    */
   // XXX: Signal is assumed to have correct byte order.
   protected final void hunt(String name, Signal signal) throws IOException
   {
      if (signal == null)
      {
         throw new NullPointerException();
      }

      // FIXME: Set up signal size and signal data.
      server.hunt(this, name, signal.getSigSize(), signal.getSigNo(), signal.getData());
   }

   // FIXME: Is attach/detach functionality needed by gateway services?

   /**
    * Create a signal.
    *
    * @param sender     the sender of the signal.
    * @param addressee  the addressee of the signal.
    * @param sigSize    the size of the signal.
    * @param sigNo      the signal number.
    * @param sigData    the signal data.
    * @param registry   the signal registry to be used.
    * @param bigEndian  true if the signal data is in big endian byte order,
    * false if it is in little endian byte order.
    * @return the corresponding signal.
    * @throws IOException  if an I/O exception occurred.
    */
   protected static Signal createSignal(int sender,
                                        int addressee,
                                        int sigSize,
                                        int sigNo,
                                        byte[] sigData,
                                        SignalRegistry registry,
                                        boolean bigEndian)
      throws IOException
   {
      Class sigClass;
      Signal sig;

      if ((sigSize == 0) || (sigData == null) || (registry == null))
      {
         throw new IllegalArgumentException();
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

   /**
    * Close this gateway service.
    */
   protected abstract void close();

   /**
    * Log the given message in the hosting gateway server.
    *
    * @param message  the message to be logged.
    */
   protected void log(String message)
   {
      if (server != null)
      {
         server.log("Host gateway service '" + getName() + "' log entry:");
         server.log(message);
      }
   }

   /**
    * Log the given exception in the hosting gateway server.
    *
    * @param t  the exception to be logged.
    */
   protected void log(Throwable t)
   {
      if (server != null)
      {
         server.log("Host gateway service '" + getName() + "' log entry:");
         server.log(t);
      }
   }

   /**
    * Log the given message and exception in the hosting gateway server.
    *
    * @param message  the message to be logged.
    * @param t        the exception to be logged.
    */
   protected void log(String message, Throwable t)
   {
      if (server != null)
      {
         server.log("Host gateway service '" + getName() + "' log entry:");
         server.log(message, t);
      }
   }
}
