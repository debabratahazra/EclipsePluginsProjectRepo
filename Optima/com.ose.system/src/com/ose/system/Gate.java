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
import com.ose.gateway.Gateway;
import com.ose.gateway.GatewayIdentifier;

/**
 * This class represents an OSE gate and is a node in the hierarchical OSE
 * system model tree structure.
 * <p>
 * A gate has targets that are accessible from it as children nodes and the OSE
 * system model singleton object as parent node. A gate can be connected to,
 * disconnected from, pinged to see if it's alive, and can have new targets
 * added to it.
 * <p>
 * A gate in this context is an OSE Gateway, but could in the future include an
 * OSE Link Handler or some other entity for transmitting OSE signals.
 *
 * @see com.ose.system.SystemModelNode
 * @see com.ose.system.SystemModel#findGates(IProgressMonitor)
 * @see com.ose.system.SystemModel#addGate(InetAddress, int)
 */
public class Gate implements SystemModelNode
{
   /* Internal field read/write lock. */
   private final Object lock = new Object();

   /* Killed status. */
   private boolean killed;

   /* Gate info. */
   private final String name;
   private final InetAddress address;
   private final int port;

   /* Targets (synchronized list). */
   private final List targets;

   /**
    * Create a new gate object. If the gate runs on the same CPU core as an OSE
    * target monitor, the corresponding target object will be automatically
    * added to the gate.
    *
    * @param name     the gate name or null if unknown.
    * @param address  the gate IP address or host name.
    * @param port     the gate port.
    */
   Gate(String name, InetAddress address, int port)
   {
      if (address == null)
      {
         throw new NullPointerException();
      }
      this.name = ((name != null) ? name : getName(address, port));
      this.address = address;
      this.port = port;
      targets = Collections.synchronizedList(new ArrayList());

      // Try to add this gate as a target.
      try
      {
         addTarget("");
      }
      catch (Throwable ignore) {}
   }

   private static String getName(InetAddress address, int port)
   {
      String name = null;

      try
      {
         name = Gateway.getName(address,
                                port,
                                SystemModel.getInstance().getPingTimeout());
      }
      catch (IOException ignore) {}

      return ((name != null) ? name : "nameless");
   }

   /**
    * Broadcast for available gates on the local network using the specified
    * broadcast port and return an array of gate identifiers for the gates
    * responding within the specified timeout period.
    *
    * @param port     the gate broadcast port.
    * @param timeout  the gate broadcast timeout period in milliseconds.
    * @return         an array of gate identifiers for the gates found, or an
    * empty array if no gates were found.
    * @throws IOException  if an I/O exception occurred.
    */
   static GatewayIdentifier[] find(int port, int timeout) throws IOException
   {
      GatewayIdentifier[] gids = Gateway.find(port, timeout);
      return gids;
   }

   /**
    * Ping the gate with the specified address and port.
    *
    * @param address  the gate IP address or host name.
    * @param port     the gate port.
    * @param timeout  the gate ping timeout period in milliseconds.
    * @return true if the specified gate responds within the specified timeout
    * period, false otherwise.
    */
   public static boolean ping(InetAddress address, int port, int timeout)
   {
      boolean result = false;
      try
      {
         result = Gateway.ping(address, port, timeout);
      }
      catch (IOException ignore) {}
      return result;
   }

   /**
    * Ping this gate.
    *
    * @param timeout  the gate ping timeout period in milliseconds.
    * @return true if this gate responds within the specified timeout period,
    * false otherwise.
    */
   public boolean ping(int timeout)
   {
      return Gate.ping(address, port, timeout);
   }

   /**
    * Add a new target to this gate that is accessible from it using the
    * specified link handler hunt path prefix. Use an empty hunt path string if
    * the requested target runs on the same CPU core as this gate.
    *
    * @param huntPath  the target link handler hunt path prefix, i.e.
    * link-name-1/link-name-2/.../link-name-n or an empty string.
    * @return the resulting target object, or null if it has already been added
    * to this gate or this gate is killed/disconnected.
    * @throws IOException  if an I/O exception occurred, e.g. the target has no
    * OSE monitor or it is not responding due to a communication error.
    */
   public Target addTarget(String huntPath) throws IOException
   {
      Target target = null;

      synchronized (targets)
      {
         if (!isKilled())
         {
            boolean exists = false;

            for (Iterator i = targets.iterator(); i.hasNext();)
            {
               Target t = (Target) i.next();
               if (t.matches(huntPath))
               {
                  exists = true;
                  break;
               }
            }

            if (!exists)
            {
               target = new Target(this, huntPath);
               targets.add(target);
               SystemModel.getInstance().fireNodesAdded(this,
                     Collections.singletonList(target));
            }
         }
      }

      return target;
   }

   /**
    * Remove a previously added target from this gate.
    *
    * @param target  the target to remove.
    */
   void removeTarget(Target target)
   {
      targets.remove(target);
   }

   /**
    * Return the target matching the specified link handler hunt path prefix, or
    * null if no such target has been added to this gate. Use an empty hunt path
    * string if the requested target runs on the same CPU core as this gate.
    *
    * @param huntPath  the target link handler hunt path prefix, i.e.
    * link-name-1/link-name-2/.../link-name-n or an empty string.
    * @return the target matching the specified link handler hunt path prefix,
    * or null if no such target has been added to this gate.
    */
   public Target getTarget(String huntPath)
   {
      Target target = null;
      synchronized (targets)
      {
         for (Iterator i = targets.iterator(); i.hasNext();)
         {
            Target t = (Target) i.next();
            if (t.matches(huntPath))
            {
               target = t;
               break;
            }
         }
      }
      return target;
   }

   /**
    * Connect to this gate if it is disconnected.
    *
    * @param progressMonitor  the progress monitor used for cancellation.
    */
   public void connect(IProgressMonitor progressMonitor)
   {
      synchronized (lock)
      {
         if (killed)
         {
            killed = !ping(SystemModel.getInstance().getPingTimeout());
            // FIXME: Throw IOException or return false if ping failed
            // in order to indicate that the connection attempt failed?
            if (!killed)
            {
               SystemModel.getInstance().fireNodesChanged(SystemModel.getInstance(),
                     Collections.singletonList(this));
               // Try to add this gate as a target.
               try
               {
                  addTarget("");
               }
               catch (Throwable ignore) {}
            }
         }
      }
   }

   /**
    * Disconnect from this gate if it is connected.
    */
   public void disconnect()
   {
      synchronized (lock)
      {
         if (killed)
         {
            return;
         }
         killed = true;
      }
      synchronized (targets)
      {
         for (Iterator i = targets.iterator(); i.hasNext();)
         {
            Target target = (Target) i.next();
            target.disconnect();
         }
      }
      SystemModel.getInstance().fireNodesChanged(SystemModel.getInstance(),
            Collections.singletonList(this));
   }

   /**
    * Determine whether this gate is connected (i.e. not killed) or not.
    *
    * @return true if this gate is connected, false otherwise.
    * @see #isKilled()
    */
   public boolean isConnected()
   {
      synchronized (lock)
      {
         return !killed;
      }
   }

   /**
    * Determine whether this gate is killed (i.e. not connected) or not.
    *
    * @return true if this gate is killed, false otherwise.
    * @see com.ose.system.SystemModelNode#isKilled()
    * @see #isConnected()
    */
   public boolean isKilled()
   {
      synchronized (lock)
      {
         return killed;
      }
   }

   /**
    * Determine whether the specified gate address and port matches the address
    * and port of this gate or not.
    *
    * @param address  a gate IP address or host name.
    * @param port     a gate port.
    * @return true if the specified gate address and port matches the address
    * and port of this gate, false otherwise.
    */
   boolean matches(InetAddress address, int port)
   {
      // Final fields, no synchronization needed.
      return (this.address.equals(address) && (this.port == port));
   }

   /**
    * Return the name of this gate.
    *
    * @return the name of this gate.
    */
   public String getName()
   {
      // Final field, no synchronization needed.
      return name;
   }

   /**
    * Return the address of this gate.
    *
    * @return the address of this gate.
    */
   public InetAddress getAddress()
   {
      // Final field, no synchronization needed.
      return address;
   }

   /**
    * Return the port of this gate.
    *
    * @return the port of this gate.
    */
   public int getPort()
   {
      // Final field, no synchronization needed.
      return port;
   }

   /**
    * Return an array of the targets of this gate (never null). A new array is
    * created and returned for each call.
    *
    * @return an array of the targets of this gate (never null).
    */
   public Target[] getTargets()
   {
      return (Target[]) targets.toArray(new Target[0]);
   }

   /**
    * Return the parent system model singleton object.
    *
    * @return the parent system model singleton object.
    * @see com.ose.system.SystemModelNode#getParent()
    */
   public SystemModelNode getParent()
   {
      return SystemModel.getInstance();
   }

   /**
    * Return an array of the children targets of this gate. A new array is
    * created and returned for each call.
    *
    * @return an array of the children targets of this gate.
    * @see com.ose.system.SystemModelNode#getChildren()
    * @see Target
    */
   public SystemModelNode[] getChildren()
   {
      return getTargets();
   }

   /**
    * Determine whether this node is a leaf node or not. A gate object is not a
    * leaf node and always returns false.
    *
    * @return false since a gate object is not a leaf node.
    * @see com.ose.system.SystemModelNode#isLeaf()
    */
   public boolean isLeaf()
   {
      return false;
   }

   /**
    * Update this gate and its targets. If recursive is true, also update the
    * children nodes of the target nodes and so on; i.e. recursivly update the
    * branch in the tree that is rooted in this gate node.
    *
    * @param progressMonitor  the progress monitor used for cancellation.
    * @param recursive        whether or not to recursivly update the whole
    * branch in the tree that is rooted in this gate node.
    * @throws IOException  if an I/O exception occurred.
    * @throws ServiceException  if a children target's monitor service reported
    * an error.
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

      if (!isKilled())
      {
         if (!ping(SystemModel.getInstance().getPingTimeout()))
         {
            disconnect();
            return;
         }
         synchronized (targets)
         {
            for (Iterator i = targets.iterator(); i.hasNext();)
            {
               Target target = (Target) i.next();
               if (!target.isKilled())
               {
                  target.update(progressMonitor, recursive);
               }
            }
         }
      }
   }

   /**
    * Clean this gate, i.e. recursivly remove all nodes marked as killed in the
    * branch in the tree that is rooted in this gate node.
    *
    * @see com.ose.system.SystemModelNode#clean()
    */
   public void clean()
   {
      if (isKilled())
      {
         SystemModel systemModel = SystemModel.getInstance();
         systemModel.removeGate(this);
         systemModel.fireNodesRemoved(systemModel, Collections.singletonList(this));
      }

      cleanChildren();
   }

   /**
    * Clean the targets of this gate recursivly.
    */
   void cleanChildren()
   {
      synchronized (targets)
      {
         for (Iterator i = targets.iterator(); i.hasNext();)
         {
            Target target = (Target) i.next();
            if (target.isKilled())
            {
               i.remove();
               SystemModel.getInstance().fireNodesRemoved(this,
                     Collections.singletonList(target));
            }
            target.cleanChildren();
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
    * Check the state of this gate object. If recursive is true, recursivly
    * check the state of all nodes in the branch in the tree that is rooted in
    * this gate node.
    *
    * @param recursive  whether or not to recursivly check the state of all
    * nodes in the branch in the tree that is rooted in this gate node.
    * @throws IllegalStateException  if this gate object is not in a consistent
    * state or, if recursive is true, if any node in the branch in the tree that
    * is rooted in this gate node is not in a consistent state.
    */
   public void checkState(boolean recursive) throws IllegalStateException
   {
      // Final fields, no synchronization needed.
      if (name == null)
      {
         throw new IllegalStateException("Name is null.");
      }
      if (address == null)
      {
         throw new IllegalStateException("Address is null.");
      }
      if ((port < 0) || (port > 65535))
      {
         throw new IllegalStateException("Invalid port.");
      }
      if (targets == null)
      {
         throw new IllegalStateException("Targets is null.");
      }

      if (recursive)
      {
         synchronized (targets)
         {
            for (Iterator i = targets.iterator(); i.hasNext();)
            {
               Target target = (Target) i.next();
               target.checkState(recursive);
            }
         }
      }
   }

   /**
    * Return a string representation of this gate object. The returned string
    * is of the form
    * "&lt;gate-name&gt; (&lt;gate-IP-address&gt;:&lt;gate-port&gt;)".
    *
    * @return a string representation of this gate object.
    * @see java.lang.Object#toString()
    */
   public String toString()
   {
      // Final fields, no synchronization needed.
      return (name + " (" + address.getHostAddress() + ":" + port + ")");
   }
}
