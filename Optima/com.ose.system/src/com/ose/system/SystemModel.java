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

package com.ose.system;

import java.io.IOException;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.core.runtime.Platform;
import com.ose.gateway.GatewayIdentifier;

/**
 * This class is the OSE system model entrypoint and its singleton object is the
 * root node in the hierarchical OSE system model tree structure. The OSE system
 * model singleton object is retrieved with the static method
 * SystemModel.getInstance().
 * <p>
 * The OSE system model singleton object has gates as children nodes and no
 * parent node since it is the root node in the OSE system model. The OSE system
 * model singleton object supports finding new gates on the local network and
 * adding new gates manually.
 * <p>
 * The OSE system model provides an event listener interface to clients for
 * registering interest in and receiving events when the system model is
 * modified.
 *
 * @see com.ose.system.SystemModelNode
 */
public class SystemModel implements SystemModelNode
{
   /* System model singleton instance. */
   private static final SystemModel SYSTEM_MODEL = new SystemModel();

   /* Internal field read/write lock. */
   private final Object lock = new Object();

   /* System model event listener list. */
   private final EventListenerList listenerList;

   /* Gate parameters. */
   private int broadcastPort;
   private int broadcastTimeout;
   private int pingTimeout;

   /* Gates (synchronized list). */
   private final List gates;

   /**
    * Create the OSE system model singleton object.
    */
   private SystemModel()
   {
      listenerList = new EventListenerList();
      broadcastPort = 21768;
      broadcastTimeout = 4000;
      pingTimeout = 2000;
      gates = Collections.synchronizedList(new ArrayList());
   }

   /**
    * Add a new gate to the OSE system model singleton object.
    *
    * @param name     the gate name or null if unknown.
    * @param address  the gate IP address or host name.
    * @param port     the gate port.
    * @param ping     whether or not to ping the gate before adding it. If the
    * gate does not respond to ping, it is not added.
    * @return the resulting gate object, or null if it has already been added
    * to the OSE system model singleton object or if it cannot be found.
    */
   private Gate addGate(String name, InetAddress address, int port, boolean ping)
   {
      Gate gate = null;

      synchronized (gates)
      {
         boolean exists = false;
         boolean shouldAdd = false;

         for (Iterator i = gates.iterator(); i.hasNext();)
         {
            Gate g = (Gate) i.next();
            if (g.matches(address, port))
            {
               exists = true;
               break;
            }
         }

         if (!exists)
         {
            shouldAdd = (ping ? Gate.ping(address, port, getPingTimeout()) : true);
         }

         if (shouldAdd)
         {
            gate = new Gate(name, address, port);
            gates.add(gate);
         }
      }

      return gate;
   }

   /**
    * Remove a previously added gate from the OSE system model singleton object.
    *
    * @param gate  the gate to remove.
    */
   void removeGate(Gate gate)
   {
      gates.remove(gate);
   }

   /**
    * Return the OSE system model singleton object.
    *
    * @return the OSE system model singleton object.
    */
   public static SystemModel getInstance()
   {
      return SYSTEM_MODEL;
   }

   /**
    * Add an OSE system model listener. Has no effect if an identical listener
    * has already been added.
    *
    * @param l  the OSE system model listener to add.
    */
   public void addSystemModelListener(SystemModelListener l)
   {
      listenerList.add(l);
   }

   /**
    * Remove a previously added OSE system model listener. Has no effect if an
    * identical listener has not previously been added.
    *
    * @param l  the OSE system model listener to remove.
    */
   public void removeSystemModelListener(SystemModelListener l)
   {
      listenerList.remove(l);
   }

   /**
    * Fire an OSE system model change event to all registered OSE system model
    * listeners.
    *
    * @param parent    the parent node of the changed nodes.
    * @param children  a list of the changed nodes. The list shall contain one
    * or more sibling nodes that have been changed, and who are direct
    * descendents of the specified parent node.
    */
   void fireNodesChanged(SystemModelNode parent, List children)
   {
      SystemModelEvent event = null;
      Object[] listeners = listenerList.getListeners();
      for (int i = 0; i < listeners.length; i++)
      {
         if (event == null)
         {
            event = new SystemModelEvent(this, parent, children);
         }
         ((SystemModelListener) listeners[i]).nodesChanged(event);
      }
   }

   /**
    * Fire an OSE system model add event to all registered OSE system model
    * listeners.
    *
    * @param parent    the parent node of the added nodes.
    * @param children  a list of the added nodes. The list shall contain one
    * or more sibling nodes that have been added, and who are direct
    * descendents of the specified parent node.
    */
   void fireNodesAdded(SystemModelNode parent, List children)
   {
      SystemModelEvent event = null;
      Object[] listeners = listenerList.getListeners();
      for (int i = 0; i < listeners.length; i++)
      {
         if (event == null)
         {
            event = new SystemModelEvent(this, parent, children);
         }
         ((SystemModelListener) listeners[i]).nodesAdded(event);
      }
   }

   /**
    * Fire an OSE system model remove event to all registered OSE system model
    * listeners.
    *
    * @param parent    the parent node of the removed nodes.
    * @param children  a list of the removed nodes. The list shall contain one
    * or more sibling nodes that have been removed, and who are direct
    * descendents of the specified parent node.
    */
   void fireNodesRemoved(SystemModelNode parent, List children)
   {
      SystemModelEvent event = null;
      Object[] listeners = listenerList.getListeners();
      for (int i = 0; i < listeners.length; i++)
      {
         if (event == null)
         {
            event = new SystemModelEvent(this, parent, children);
         }
         ((SystemModelListener) listeners[i]).nodesRemoved(event);
      }
   }

   /**
    * Broadcast for new gates on the local network using the broadcast port
    * specified by SystemModel.setBroadcastPort() and return an array of the
    * new gates responding within the timeout period specified by
    * SystemModel.setBroadcastTimeout(). All new gates found will be
    * automatically added to the OSE system model singleton object.
    *
    * @param progressMonitor  the progress monitor used for cancellation.
    * @return  an array of the new gates found, or an empty array if no new
    * gates were found.
    * @throws IOException  if an I/O exception occurred.
    */
   public Gate[] findGates(IProgressMonitor progressMonitor)
      throws IOException
   {
      List addedGates = new ArrayList();
      GatewayIdentifier[] gids = Gate.find(getBroadcastPort(), getBroadcastTimeout());
      for (int i = 0; i < gids.length; i++)
      {
         GatewayIdentifier gid = gids[i];
         Gate gate = addGate(gid.getName(), gid.getAddress(), gid.getPort(), false);
         if (gate != null)
         {
            addedGates.add(gate);
         }
         if (progressMonitor.isCanceled())
         {
            break;
         }
      }
      if (!addedGates.isEmpty())
      {
         fireNodesAdded(this, addedGates);
      }
      return (Gate[]) addedGates.toArray(new Gate[0]);
   }

   /**
    * Add a new gate with the specified address and port to the OSE system model
    * singleton object.
    *
    * @param address  the gate IP address or host name.
    * @param port     the gate port.
    * @return the resulting gate object, or null if it has already been added
    * to the OSE system model singleton object or if it cannot be found.
    */
   public Gate addGate(InetAddress address, int port)
   {
      Gate gate = addGate(null, address, port, true);
      if (gate != null)
      {
         fireNodesAdded(this, Collections.singletonList(gate));
      }
      return gate;
   }

   /**
    * Return the gate matching the specified gate address and port, or null if
    * no such gate has been added to the OSE system model singleton object.
    *
    * @param address  the gate IP address or host name.
    * @param port     the gate port.
    * @return the gate matching the specified gate address and port, or null if
    * no such gate has been added to the OSE system model singleton object.
    */
   public Gate getGate(InetAddress address, int port)
   {
      Gate gate = null;
      synchronized (gates)
      {
         for (Iterator i = gates.iterator(); i.hasNext();)
         {
            Gate g = (Gate) i.next();
            if (g.matches(address, port))
            {
               gate = g;
               break;
            }
         }
      }
      return gate;
   }

   /**
    * Determine whether this node is killed or not. The OSE system model
    * singleton object is never killed and always returns false.
    *
    * @return false since the OSE system model singleton object is never killed.
    * @see com.ose.system.SystemModelNode#isKilled()
    */
   public boolean isKilled()
   {
      return false;
   }

   /**
    * Return the gate broadcast port. Used by SystemModel.findGates().
    *
    * @return the gate broadcast port.
    * @see #setBroadcastPort(int)
    */
   public int getBroadcastPort()
   {
      synchronized (lock)
      {
         return broadcastPort;
      }
   }

   /**
    * Set the gate broadcast port. If not set, the default value 21768 is used.
    *
    * @param broadcastPort  the gate broadcast port to set.
    * @see #getBroadcastPort()
    */
   public void setBroadcastPort(int broadcastPort)
   {
      if ((broadcastPort < 0) || (broadcastPort > 65535))
      {
         throw new IllegalArgumentException();
      }
      synchronized (lock)
      {
         this.broadcastPort = broadcastPort;
      }
   }

   /**
    * Return the gate broadcast timeout period in milliseconds. Used by
    * SystemModel.findGates().
    *
    * @return the gate broadcast timeout period in milliseconds.
    * @see #setBroadcastTimeout(int)
    */
   public int getBroadcastTimeout()
   {
      synchronized (lock)
      {
         return broadcastTimeout;
      }
   }

   /**
    * Set the gate broadcast timeout period in milliseconds. If not set, the
    * default value 4000 milliseconds is used.
    *
    * @param broadcastTimeout  the gate broadcast timeout period to set.
    * @see #getBroadcastTimeout()
    */
   public void setBroadcastTimeout(int broadcastTimeout)
   {
      if (broadcastTimeout < 0)
      {
         throw new IllegalArgumentException();
      }
      synchronized (lock)
      {
         this.broadcastTimeout = broadcastTimeout;
      }
   }

   /**
    * Return the gate ping timeout period in milliseconds. Used by
    * SystemModel.addGate() and SystemModel.update().
    *
    * @return the gate ping timeout period in milliseconds.
    * @see #setPingTimeout(int)
    */
   public int getPingTimeout()
   {
      synchronized (lock)
      {
         return pingTimeout;
      }
   }

   /**
    * Set the gate ping timeout period in milliseconds. If not set, the default
    * value 2000 milliseconds is used.
    *
    * @param pingTimeout  the gate ping timeout period to set.
    * @see #getPingTimeout()
    */
   public void setPingTimeout(int pingTimeout)
   {
      if (pingTimeout < 0)
      {
         throw new IllegalArgumentException();
      }
      synchronized (lock)
      {
         this.pingTimeout = pingTimeout;
      }
   }

   /**
    * Return an array of the gates of the OSE system model singleton object
    * (never null). A new array is created and returned for each call.
    *
    * @return an array of the gates of the OSE system model singleton object
    * (never null).
    */
   public Gate[] getGates()
   {
      return (Gate[]) gates.toArray(new Gate[0]);
   }

   /**
    * Return the parent node. Since the OSE system model singleton object is the
    * root node in the hierarchical OSE system model tree structure, this method
    * always returns null.
    *
    * @return null since the OSE system model singleton object is the root node
    * in the hierarchical OSE system model tree structure.
    * @see com.ose.system.SystemModelNode#getParent()
    */
   public SystemModelNode getParent()
   {
      return null;
   }

   /**
    * Return an array of the children gates of the OSE system model singleton
    * object. A new array is created and returned for each call.
    *
    * @return an array of the children gates of the OSE system model singleton
    * object.
    * @see com.ose.system.SystemModelNode#getChildren()
    * @see Gate
    */
   public SystemModelNode[] getChildren()
   {
      return getGates();
   }

   /**
    * Determine whether this node is a leaf node or not. The OSE system model
    * singleton object is not a leaf node and always returns false.
    *
    * @return false since the OSE system model singleton object is not a leaf
    * node.
    * @see com.ose.system.SystemModelNode#isLeaf()
    */
   public boolean isLeaf()
   {
      return false;
   }

   /**
    * Update the gates of the OSE system model singleton object. If recursive is
    * true, also update the children nodes of the gate nodes and so on; i.e.
    * recursivly update the whole OSE system model tree structure.
    *
    * @param progressMonitor  the progress monitor used for cancellation.
    * @param recursive        whether or not to recursivly update the whole OSE
    * system model tree structure.
    * @throws IOException  if an I/O exception occurred.
    * @throws ServiceException  if a target's monitor service reported an error.
    * @throws OperationCanceledException  if the operation was interrupted or
    * cancelled.
    * @see com.ose.system.SystemModelNode#update(org.eclipse.core.runtime.IProgressMonitor,
    * boolean)
    */
   public void update(IProgressMonitor progressMonitor, boolean recursive)
      throws IOException
   {
      if (progressMonitor == null)
      {
         throw new NullPointerException();
      }

      // Mark killed gates if non-recursive update.
      if (!recursive)
      {
         synchronized (gates)
         {
            for (Iterator i = gates.iterator(); i.hasNext();)
            {
               Gate gate = (Gate) i.next();
               if (!gate.isKilled() && !gate.ping(getPingTimeout()))
               {
                  gate.disconnect();
               }
               if (progressMonitor.isCanceled())
               {
                  break;
               }
            }
         }
      }

      // Check cancellation status.
      if (progressMonitor.isCanceled())
      {
         return;
      }

      // Update all gates if recursive update.
      if (recursive)
      {
         synchronized (gates)
         {
            for (Iterator i = gates.iterator(); i.hasNext();)
            {
               Gate gate = (Gate) i.next();
               if (!gate.isKilled())
               {
                  gate.update(progressMonitor, recursive);
               }
            }
         }
      }
   }

   /**
    * Clean the OSE system model singleton object, i.e. recursivly remove all
    * nodes marked as killed in the OSE system model tree structure.
    *
    * @see com.ose.system.SystemModelNode#clean()
    */
   public void clean()
   {
      synchronized (gates)
      {
         for (Iterator i = gates.iterator(); i.hasNext();)
         {
            Gate gate = (Gate) i.next();
            if (gate.isKilled())
            {
               i.remove();
               fireNodesRemoved(this, Collections.singletonList(gate));
            }
            gate.cleanChildren();
         }
      }
   }

   /**
    * Return an object which is an instance of the given adapter class
    * associated with this object. Return null if no such object can be found.
    *
    * @param adapter  the adapter class to look up.
    * @return an object castable to the given adapter class, or null if this
    * object does not have an adapter for the given adapter class.
    * @see org.eclipse.core.runtime.IAdaptable#getAdapter(java.lang.Class)
    */
   public Object getAdapter(Class adapter)
   {
      return Platform.getAdapterManager().getAdapter(this, adapter);
   }

   /**
    * Check the state of the OSE system model singleton object. If recursive is
    * true, recursivly check the state of all nodes in the OSE system model tree
    * structure.
    *
    * @param recursive  whether or not to recursivly check the state of all
    * nodes in the OSE system model tree structure.
    * @throws IllegalStateException  if the OSE system model singleton object is
    * not in a consistent state or, if recursive is true, if any node in the OSE
    * system model tree structure is not in a consistent state.
    */
   public void checkState(boolean recursive) throws IllegalStateException
   {
      synchronized (lock)
      {
         if (SYSTEM_MODEL == null)
         {
            throw new IllegalStateException("System model singleton is null.");
         }
         if (listenerList == null)
         {
            throw new IllegalStateException("Event listener list is null.");
         }
         if ((broadcastPort < 0) || (broadcastPort > 65535))
         {
            throw new IllegalStateException("Invalid broadcast port.");
         }
         if (broadcastTimeout < 0)
         {
            throw new IllegalStateException("Broadcast timeout is negative.");
         }
         if (pingTimeout < 0)
         {
            throw new IllegalStateException("Ping timeout is negative.");
         }
         if (gates == null)
         {
            throw new IllegalStateException("Gates is null.");
         }
      }

      if (recursive)
      {
         synchronized (gates)
         {
            for (Iterator i = gates.iterator(); i.hasNext();)
            {
               Gate gate = (Gate) i.next();
               gate.checkState(recursive);
            }
         }
      }
   }

   /**
    * Return a string representation of the OSE system model singleton object.
    *
    * @return a string representation of the OSE system model singleton object.
    * @see java.lang.Object#toString()
    */
   public String toString()
   {
      return "OSE System Model";
   }
}
