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
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import com.ose.gateway.Signal;

/**
 * This class implements a host gateway server to which host gateway services
 * can be registered.
 *
 * @see com.ose.gateway.server.AbstractGatewayService
 */
public final class GatewayServer extends AbstractGatewayServer
{
   private final Map objects = Collections.synchronizedMap(new HashMap());
   private final List hunters = Collections.synchronizedList(new ArrayList());
   private volatile int idSeed;

   /**
    * Create a new gateway server object with no defined name that uses the
    * default server and broadcast ports.
    *
    * @throws IOException  if an I/O exception occurred.
    */
   public GatewayServer() throws IOException
   {
      this(null,
           GatewayProtocol.DEFAULT_SERVER_PORT,
           GatewayProtocol.DEFAULT_BROADCAST_PORT);
   }

   /**
    * Create a new gateway server object.
    *
    * @param name           the name of the gateway server.
    * @param serverPort     the server port of the gateway server.
    * @param broadcastPort  the broadcast port of the gateway server.
    * @throws IOException  if an I/O exception occurred.
    */
   public GatewayServer(String name, int serverPort, int broadcastPort)
      throws IOException
   {
      super(name, serverPort, broadcastPort);
   }

   /**
    * Register the given gateway service with this gateway server.
    *
    * @param service  the gateway service to be registered.
    * @throws IllegalStateException  if this gateway server is closed.
    */
   public void registerService(AbstractGatewayService service)
      throws IllegalStateException
   {
      int id;

      if (service == null)
      {
         throw new NullPointerException();
      }

      if (!isOpen())
      {
         throw new IllegalStateException(
            "Host gateway server is closed, cannot register gateway service.");
      }

      id = createId();
      objects.put(new Integer(id), service);
      service.init(this, id);
      handleHunters(service);
   }

   /**
    * Return the maximum signal size of this gateway server. The default value
    * is 0x20000.
    *
    * @return the maximum signal size of this gateway server.
    * @see com.ose.gateway.server.AbstractGatewayServer#getMaxSignalSize()
    */
   protected int getMaxSignalSize()
   {
      return 0x20000;
   }

   /* (non-Javadoc)
    * @see com.ose.gateway.server.AbstractGatewayServer#createClient(java.nio.channels.SocketChannel)
    */
   protected AbstractGatewayClient createClient(SocketChannel channel)
   {
      return new GatewayClient(this, channel);
   }

   /* (non-Javadoc)
    * @see com.ose.gateway.server.AbstractGatewayServer#closeServerHook()
    */
   protected void closeServerHook()
   {
      closeServices();
      objects.clear();
      hunters.clear();
   }

   /**
    * Send a signal to the specified addressee from the specified sender.
    *
    * @param sender     the ID of the gateway object that sent the signal.
    * @param addressee  the ID of the gateway object that the signal should be
    * sent to.
    * @param sigSize    the size of the signal in bytes.
    * @param sigNo      the signal number of the signal.
    * @param sigData    the data of the signal.
    */
   void send(int sender, int addressee, int sigSize, int sigNo, byte[] sigData)
   {
      GatewayObject senderObject;
      GatewayObject addresseeObject;

      if (sigData == null)
      {
         throw new NullPointerException();
      }

      senderObject = (GatewayObject) objects.get(new Integer(sender));
      addresseeObject = (GatewayObject) objects.get(new Integer(addressee));
      if (senderObject == null)
      {
         log("Host Gateway Server: A signal to be sent " +
             "with invalid sender was disposed.");
      }
      else if (addresseeObject == null)
      {
         log("Host Gateway Server: A signal to be sent " +
             "with invalid addressee was disposed.");
      }
      else if (addresseeObject instanceof AbstractGatewayService)
      {
         AbstractGatewayService service =
            (AbstractGatewayService) addresseeObject;
         service.signalReceived(sender,
                                addressee,
                                sigSize,
                                sigNo,
                                sigData);
      }
      else
      {
         RawSignal signal = new RawSignal(sigNo);
         signal.initSignal(sigSize, sigData);
         signal.initTransfer(sender, addressee);
         addresseeObject.signalReceived(signal);
      }
   }

   /**
    * Send the given signal using its sender and addressee information.
    *
    * @param signal  the signal to be sent.
    */
   void send(Signal signal)
   {
      GatewayObject senderObject;
      GatewayObject addresseeObject;

      if (signal == null)
      {
         throw new NullPointerException();
      }

      senderObject =
         (GatewayObject) objects.get(new Integer(signal.getSender()));
      addresseeObject =
         (GatewayObject) objects.get(new Integer(signal.getAddressee()));
      if (senderObject == null)
      {
         log("Host Gateway Server: A signal to be sent " +
             "with invalid sender was disposed.");
      }
      else if (addresseeObject == null)
      {
         log("Host Gateway Server: A signal to be sent " +
             "with invalid addressee was disposed.");
      }
      else
      {
         addresseeObject.signalReceived(signal);
      }
   }

   /**
    * Search for a gateway object (i.e. a gateway service or a gateway client)
    * by name.
    * <p>
    * If specified, the given hunt signal will be sent back to the specified
    * hunter when a gateway object with a matching name is found.
    *
    * @param hunter   the hunting gateway object.
    * @param name     the name of the gateway object to search for.
    * @param sigSize  the size of the hunt signal in bytes or 0 if no hunt
    * signal is used.
    * @param sigNo    if a hunt signal is used, the signal number of that hunt
    * signal.
    * @param sigData  if a hunt signal is used, the data of that hunt signal.
    * @return the ID of the found gateway object, or 0 if the named gateway
    * object does not exist at the time of the call.
    */
   int hunt(GatewayObject hunter, String name, int sigSize, int sigNo, byte[] sigData)
   {
      GatewayObject huntedObject;

      if ((hunter == null) || (name == null))
      {
         throw new NullPointerException();
      }

      huntedObject = findObject(name);
      if (huntedObject != null)
      {
         if (sigSize > 0)
         {
            RawSignal huntSignal = new RawSignal(sigNo);
            huntSignal.initSignal(sigSize, sigData);
            huntSignal.initTransfer(huntedObject.getId(), hunter.getId());
            hunter.signalReceived(huntSignal);
         }
         return huntedObject.getId();
      }
      else
      {
         if (sigSize > 0)
         {
            RawSignal huntSignal = new RawSignal(sigNo);
            huntSignal.initSignal(sigSize, sigData);
            hunters.add(new HuntEntry(hunter, name, huntSignal));
         }
         return 0;
      }
   }

   /**
    * Add the given gateway client to this gateway server and associate it with
    * a unique ID.
    *
    * @param client  the gateway client to be added.
    * @return the designated ID of the added gateway client.
    */
   int addClient(GatewayClient client)
   {
      if (client == null)
      {
         throw new NullPointerException();
      }

      int id = createId();
      objects.put(new Integer(id), client);
      handleHunters(client);
      return id;
   }

   /**
    * Remove the given gateway client from this gateway server.
    *
    * @param client  the gateway client to be removed.
    */
   void removeClient(GatewayClient client)
   {
      if (client == null)
      {
         throw new NullPointerException();
      }
      objects.remove(new Integer(client.getId()));
   }

   /**
    * Determine whether the given gateway object ID is valid or not.
    *
    * @param id  a gateway object ID.
    * @return true if the given gateway object ID is valid, false otherwise.
    */
   boolean isValidId(int id)
   {
      synchronized (objects)
      {
         for (Iterator i = objects.values().iterator(); i.hasNext();)
         {
            GatewayObject object = (GatewayObject) i.next();
            if (id == object.getId())
            {
               return true;
            }
         }
      }
      return false;
   }

   /**
    * Create and return a unique gateway object ID.
    *
    * @return a unique gateway object ID.
    */
   private int createId()
   {
      return ++idSeed;
   }

   /**
    * Find the named gateway object (i.e. a gateway service or a gateway
    * client).
    *
    * @param name  the name of the gateway object to find.
    * @return the found gateway object or null if the named gateway object does
    * not exist at the time of the call.
    */
   private GatewayObject findObject(String name)
   {
      synchronized (objects)
      {
         for (Iterator i = objects.values().iterator(); i.hasNext();)
         {
            GatewayObject object = (GatewayObject) i.next();
            if (name.equals(object.getName()))
            {
               return object;
            }
         }
      }
      return null;
   }

   /**
    * Handle all outstanding hunt requests for the given gateway object.
    *
    * @param object  a gateway object.
    */
   private void handleHunters(GatewayObject object)
   {
      synchronized (hunters)
      {
         for (Iterator i = hunters.iterator(); i.hasNext();)
         {
            HuntEntry huntEntry = (HuntEntry) i.next();
            if (object.getName().equals(huntEntry.getHuntName()))
            {
               RawSignal huntSignal = huntEntry.getHuntSignal();
               huntSignal.initTransfer(object.getId(),
                                       huntEntry.getHunter().getId());
               huntEntry.getHunter().signalReceived(huntSignal);
               i.remove();
            }
         }
      }
   }

   /**
    * Close all gateway services registered to this gateway server.
    */
   private void closeServices()
   {
      synchronized (objects)
      {
         for (Iterator i = objects.values().iterator(); i.hasNext();)
         {
            GatewayObject object = (GatewayObject) i.next();
            if (object instanceof AbstractGatewayService)
            {
               try
               {
                  AbstractGatewayService service =
                     (AbstractGatewayService) object;
                  service.close();
               }
               catch (Exception ignore) {}
            }
         }
      }
   }

   /**
    * This class represents an outstanding hunt request.
    */
   private class HuntEntry
   {
      private final GatewayObject hunter;
      private final String huntName;
      private final RawSignal huntSignal;

      /**
       * Create a new hunt entry object.
       *
       * @param hunter      the hunting gateway object.
       * @param huntName    the name of the hunted gateway object.
       * @param huntSignal  the hunt signal.
       */
      private HuntEntry(GatewayObject hunter, String huntName, RawSignal huntSignal)
      {
         this.hunter = hunter;
         this.huntName = huntName;
         this.huntSignal = huntSignal;
      }

      /**
       * Return the hunting gateway object.
       *
       * @return the hunting gateway object.
       */
      public GatewayObject getHunter()
      {
         return hunter;
      }

      /**
       * Return the name of the hunted gateway object.
       *
       * @return the name of the hunted gateway object.
       */
      public String getHuntName()
      {
         return huntName;
      }

      /**
       * Return the hunt signal.
       *
       * @return the hunt signal.
       */
      public RawSignal getHuntSignal()
      {
         return huntSignal;
      }
   }
}
