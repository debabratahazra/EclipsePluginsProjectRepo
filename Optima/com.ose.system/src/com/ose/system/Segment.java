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

package com.ose.system;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import com.ose.system.Target.MonitorHandler;
import com.ose.system.service.monitor.Monitor;
import com.ose.system.service.monitor.MonitorStatus;

/**
 * This class represents an OSE segment and is a node in the hierarchical OSE
 * system model tree structure.
 * <p>
 * A segment has pools, heaps, and blocks as children nodes and a target as
 * parent node. A segment can be started, stopped, and killed.
 *
 * @see com.ose.system.SystemModelNode
 * @see com.ose.system.OseObject
 */
public class Segment extends OseObject implements SystemModelNode
{
   /* Internal field read/write lock. */
   private final Object lock = new Object();

   /* Killed status. */
   private boolean killed;

   /* Parent. */
   private final Target target;

   /* Segment info. */
   private final String name;
   private final int segmentNumber;
   private int numSignalsAttached; // Not used
   private boolean masMapped;

   /* Pools (synchronized list). */
   private final List pools;

   /* Heaps (synchronized list). */
   private final List heaps;

   /* Blocks (synchronized list). */
   private final List blocks;

   /**
    * Create a new segment object.
    *
    * @param target              the target.
    * @param name                the segment name.
    * @param id                  the segment ID.
    * @param segmentNumber       the segment number.
    * @param numSignalsAttached  the number of attached signals.
    * @param masMapped           whether the segment has MAS mapped regions or not.
    */
   Segment(Target target,
           String name,
           int id,
           int segmentNumber,
           int numSignalsAttached,
           boolean masMapped)
   {
      super(id);
      if ((target == null) || (name == null))
      {
         throw new NullPointerException();
      }
      this.target = target;
      this.name = name;
      this.segmentNumber = segmentNumber;
      this.numSignalsAttached = numSignalsAttached;
      this.masMapped = masMapped;
      pools = Collections.synchronizedList(new ArrayList());
      heaps = Collections.synchronizedList(new ArrayList());
      blocks = Collections.synchronizedList(new ArrayList());
   }

   /**
    * Change the mutable properties of this segment.
    *
    * @param numSignalsAttached  the number of attached signals.
    * @param masMapped           whether the segment has MAS mapped regions or not.
    * @return true if any of the mutable properties of this segment were changed,
    * false otherwise.
    */
   boolean change(int numSignalsAttached, boolean masMapped)
   {
      synchronized (lock)
      {
         boolean changed = ((this.numSignalsAttached != numSignalsAttached) ||
                            (this.masMapped != masMapped));
         this.numSignalsAttached = numSignalsAttached;
         this.masMapped = masMapped;
         return changed;
      }
   }

   /**
    * Add a pool to this segment.
    *
    * @param pool  the pool to add.
    */
   void addPool(Pool pool)
   {
      pools.add(pool);
   }

   /**
    * Remove a pool from this segment.
    *
    * @param pool  the pool to remove.
    */
   void removePool(Pool pool)
   {
      pools.remove(pool);
   }

   /**
    * Add a heap to this segment.
    *
    * @param heap  the heap to add.
    */
   void addHeap(Heap heap)
   {
      heaps.add(heap);
   }

   /**
    * Remove a heap from this segment.
    *
    * @param heap  the heap to remove.
    */
   void removeHeap(Heap heap)
   {
      heaps.remove(heap);
   }

   /**
    * Add a block to this segment.
    *
    * @param block  the block to add.
    */
   void addBlock(Block block)
   {
      blocks.add(block);
   }

   /**
    * Remove a block from this segment.
    *
    * @param block  the block to remove.
    */
   void removeBlock(Block block)
   {
      blocks.remove(block);
   }

   /**
    * Determine whether this segment is killed or not.
    *
    * @return true if this segment is killed, false otherwise.
    * @see com.ose.system.SystemModelNode#isKilled()
    */
   public boolean isKilled()
   {
      synchronized (lock)
      {
         return killed;
      }
   }

   /**
    * Mark this segment and all its pools, heaps, and blocks as killed.
    *
    * @see com.ose.system.OseObject#setKilled()
    */
   void setKilled()
   {
      setKilled(true);
   }

   /**
    * Mark this segment and all its pools, heaps, and blocks as killed.
    *
    * @param self  true if a change event should be fired for this node.
    */
   void setKilled(boolean self)
   {
      synchronized (lock)
      {
         if (killed)
         {
            return;
         }
         killed = true;
      }
      target.removeId(getId());
      for (Iterator i = pools.iterator(); i.hasNext();)
      {
         Pool pool = (Pool) i.next();
         pool.setKilled(false);
      }
      SystemModel.getInstance().fireNodesChanged(this, pools);
      for (Iterator i = heaps.iterator(); i.hasNext();)
      {
         Heap heap = (Heap) i.next();
         heap.setKilled(false);
      }
      SystemModel.getInstance().fireNodesChanged(this, heaps);
      for (Iterator i = blocks.iterator(); i.hasNext();)
      {
         Block block = (Block) i.next();
         block.setKilled(false);
      }
      SystemModel.getInstance().fireNodesChanged(this, blocks);
      if (self)
      {
         SystemModel.getInstance().fireNodesChanged(target,
               Collections.singletonList(this));
      }
   }

   /**
    * Determine whether all of the given immutable segment properties are
    * identical to the ones of this segment.
    *
    * @param id             ID of a segment.
    * @param name           name of a segment.
    * @param segmentNumber  segment number of a segment.
    * @return true if all of the given immutable segment properties matches this
    * segment, false otherwise.
    */
   boolean matches(int id, String name, int segmentNumber)
   {
      // Final fields, no synchronization needed.
      boolean result = false;
      result = ((getId() == id) &&
                (this.name.equals(name)) &&
                (this.segmentNumber == segmentNumber));
      return result;
   }

   /**
    * Return the parent target object.
    *
    * @return the parent target object.
    */
   public Target getTarget()
   {
      // Final field, no synchronization needed.
      return target;
   }

   /**
    * Return the name of this segment.
    *
    * @return the name of this segment.
    */
   public String getName()
   {
      // Final field, no synchronization needed.
      return name;
   }

   /**
    * Return the segment number of this segment.
    *
    * @return the segment number of this segment.
    */
   public int getSegmentNumber()
   {
      // Final field, no synchronization needed.
      return segmentNumber;
   }

   /**
    * Return the number of signals attached to this segment.
    *
    * @return the number of signals attached to this segment.
    */
   public int getNumSignalsAttached()
   {
      synchronized (lock)
      {
         return numSignalsAttached;
      }
   }

   /**
    * Determine whether this segment has any MAS mapped memory regions or not.
    *
    * @return true if this segment has any MAS mapped memory regions, false
    * otherwise.
    */
   public boolean isMasMapped()
   {
      synchronized (lock)
      {
         return masMapped;
      }
   }

   /**
    * Return an array of the pools in this segment (never null). A new array
    * is created and returned for each call.
    *
    * @return an array of the pools in this segment (never null).
    */
   public Pool[] getPools()
   {
      return (Pool[]) pools.toArray(new Pool[0]);
   }

   /**
    * Return an array of the heaps in this segment (never null). A new array
    * is created and returned for each call.
    *
    * @return an array of the heaps in this segment (never null).
    */
   public Heap[] getHeaps()
   {
      return (Heap[]) heaps.toArray(new Heap[0]);
   }

   /**
    * Return an array of the blocks in this segment (never null). A new array
    * is created and returned for each call.
    *
    * @return an array of the blocks in this segment (never null).
    */
   public Block[] getBlocks()
   {
      return (Block[]) blocks.toArray(new Block[0]);
   }

   /**
    * Return the internal list of the pools in this segment (never null).
    *
    * @return the internal list of the pools in this segment (never null).
    * @see Pool
    */
   List getPoolList()
   {
      return pools;
   }

   /**
    * Return the internal list of the heaps in this segment (never null).
    *
    * @return the internal list of the heaps in this segment (never null).
    * @see Heap
    */
   List getHeapList()
   {
      return heaps;
   }

   /**
    * Return the internal list of the blocks in this segment (never null).
    *
    * @return the internal list of the blocks in this segment (never null).
    * @see Block
    */
   List getBlockList()
   {
      return blocks;
   }

   /**
    * Return the parent target object.
    *
    * @return the parent target object.
    * @see com.ose.system.SystemModelNode#getParent()
    */
   public SystemModelNode getParent()
   {
      return getTarget();
   }

   /**
    * Return an array of the children pools, heaps, and blocks in this segment.
    * A new array is created and returned for each call.
    *
    * @return an array of the children pools, heaps, and blocks in this segment.
    * @see com.ose.system.SystemModelNode#getChildren()
    * @see Pool
    * @see Heap
    * @see Block
    */
   public SystemModelNode[] getChildren()
   {
      SystemModelNode[] poolsArray = getPools();
      SystemModelNode[] heapsArray = getHeaps();
      SystemModelNode[] blocksArray = getBlocks();
      int poolsLength = poolsArray.length;
      int heapsLength = heapsArray.length;
      int blocksLength = blocksArray.length;
      SystemModelNode[] children =
         new SystemModelNode[poolsLength + heapsLength + blocksLength];
      System.arraycopy(poolsArray, 0, children, 0, poolsLength);
      System.arraycopy(heapsArray, 0, children, poolsLength, heapsLength);
      System.arraycopy(blocksArray, 0, 
         children, poolsLength + heapsLength, blocksLength);
      return children;
   }

   /**
    * Determine whether this node is a leaf node or not. A segment object is not
    * a leaf node and always returns false.
    *
    * @return false since a segment object is not a leaf node.
    * @see com.ose.system.SystemModelNode#isLeaf()
    */
   public boolean isLeaf()
   {
      return false;
   }

   /**
    * This method has no effect on a segment since a segment has no lazily
    * initialized properties.
    *
    * @param progressMonitor  the progress monitor used for cancellation.
    * @throws IOException     if an I/O exception occurred.
    */
   public void initLazyProperties(IProgressMonitor progressMonitor)
      throws IOException
   {
      // Nothing to do.
   }

   /**
    * Update this segment and its pools, heaps, and blocks. If recursive is
    * true, also update the children nodes of the pool nodes, heap nodes, and
    * block nodes and so on; i.e. recursivly update the branch in the tree that
    * is rooted in this segment node.
    *
    * @param progressMonitor  the progress monitor used for cancellation.
    * @param recursive        whether or not to recursivly update the whole
    * branch in the tree that is rooted in this segment node.
    * @throws IOException  if an I/O exception occurred; parent target is
    * automatically disconnected.
    * @throws ServiceException  if the monitor service reported an error.
    * @throws OperationCanceledException  if the operation was interrupted or
    * cancelled; parent target is automatically disconnected.
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

      synchronized (target)
      {
         if (!isKilled())
         {
            MonitorHandler monitorHandler = target.getMonitorHandler();

            monitorHandler.getSegmentInfoRequest(progressMonitor, this,
                  1, Monitor.MONITOR_SCOPE_ID, getId());

            if (target.hasPoolSupport())
            {
               monitorHandler.getPoolInfoRequest(progressMonitor, this,
                     Monitor.MONITOR_SCOPE_SEGMENT_ID, getId());
            }

            if (target.hasHeapSupport())
            {
               monitorHandler.getHeapInfoRequest(progressMonitor, this,
                     Monitor.MONITOR_SCOPE_SEGMENT_ID, getId());

               for (Iterator i = heaps.iterator(); i.hasNext();)
               {
                  Heap heap = (Heap) i.next();
                  if (!heap.isKilled())
                  {
                     heap.updateLazyProperties(progressMonitor);
                  }
               }
            }

            if (target.hasBlockSupport())
            {
               monitorHandler.getBlockInfoRequest(progressMonitor, this,
                     Monitor.MONITOR_SCOPE_SEGMENT_ID, getId());

               for (Iterator i = blocks.iterator(); i.hasNext();)
               {
                  Block block = (Block) i.next();
                  if (!block.isKilled())
                  {
                     block.updateLazyProperties(progressMonitor);
                  }
               }

               if (recursive)
               {
                  if (target.hasProcessSupport())
                  {
                     monitorHandler.getProcessInfoRequest(progressMonitor, this,
                           1, Monitor.MONITOR_SCOPE_SEGMENT_ID, getId());
                  }

                  for (Iterator i = blocks.iterator(); i.hasNext();)
                  {
                     Block block = (Block) i.next();
                     if (!block.isKilled())
                     {
                        for (Iterator j = block.getProcessList().iterator(); j.hasNext();)
                        {
                           Process process = (Process) j.next();
                           if (!process.isKilled())
                           {
                              process.updateLazyProperties(progressMonitor);
                           }
                        }
                     }
                  }
               }
            }
         }
      }
   }

   /**
    * This method has no effect on a segment since a segment has no lazily
    * initialized properties.
    *
    * @param progressMonitor  the progress monitor used for cancellation.
    * @throws IOException     if an I/O exception occurred.
    */
   void updateLazyProperties(IProgressMonitor progressMonitor)
      throws IOException
   {
      // Nothing to do.
   }

   /**
    * Clean this segment, i.e. recursivly remove all nodes marked as killed in
    * the branch in the tree that is rooted in this segment node.
    *
    * @see com.ose.system.SystemModelNode#clean()
    */
   public void clean()
   {
      synchronized (target)
      {
         if (isKilled())
         {
            target.removeSegment(this);
            SystemModel.getInstance().fireNodesRemoved(target,
                  Collections.singletonList(this));
         }

         cleanChildren();
      }
   }

   /**
    * Clean the pools, heaps, and blocks of this segment recursivly.
    */
   void cleanChildren()
   {
      synchronized (target)
      {
         List removedChildren = new ArrayList();
         List originalBlocks = new ArrayList(blocks);

         for (Iterator i = pools.iterator(); i.hasNext();)
         {
            Pool pool = (Pool) i.next();
            if (pool.isKilled())
            {
               i.remove();
               removedChildren.add(pool);
            }
         }

         for (Iterator i = heaps.iterator(); i.hasNext();)
         {
            Heap heap = (Heap) i.next();
            if (heap.isKilled())
            {
               i.remove();
               removedChildren.add(heap);
            }
         }

         for (Iterator i = blocks.iterator(); i.hasNext();)
         {
            Block block = (Block) i.next();
            if (block.isKilled())
            {
               i.remove();
               removedChildren.add(block);
            }
         }

         if (!removedChildren.isEmpty())
         {
            SystemModel.getInstance().fireNodesRemoved(this, removedChildren);
         }

         for (Iterator i = originalBlocks.iterator(); i.hasNext();)
         {
            Block block = (Block) i.next();
            block.cleanChildren();
         }
      }
   }

   /**
    * Start this segment if possible. If successful, this segment is recursivly
    * updated so that itself and its pools, heaps, and blocks are up-to-date.
    *
    * @param progressMonitor  the progress monitor used for cancellation.
    * @throws IOException  if an I/O exception occurred; parent target is
    * automatically disconnected.
    * @throws ServiceException  if the monitor service reported an error.
    * @throws OperationCanceledException  if the operation was interrupted or
    * cancelled; parent target is automatically disconnected.
    */
   public void start(IProgressMonitor progressMonitor) throws IOException
   {
      if (progressMonitor == null)
      {
         throw new NullPointerException();
      }

      synchronized (target)
      {
         update(progressMonitor, true);
         if (!isKilled())
         {
            if (isStartable())
            {
               target.getMonitorHandler().startScopeRequest(progressMonitor,
                     Monitor.MONITOR_SCOPE_SEGMENT_ID, getId());
               update(progressMonitor, true);
            }
            else
            {
               // Compensate for a missing condition check in the monitor.
               throw new ServiceException(Target.SERVICE_MONITOR_ID,
                  MonitorStatus.MONITOR_STATUS_CANCELLED,
                  "An attempt was made to start a segment that is not stopped.");
            }
         }
      }
   }

   /**
    * Determine whether this segment can be started or not.
    *
    * @return true if this segment can be started, false otherwise.
    */
   boolean isStartable()
   {
      synchronized (blocks)
      {
         for (Iterator i = blocks.iterator(); i.hasNext();)
         {
            Block block = (Block) i.next();
            if (!block.isKilled() && !block.isStartable())
            {
               return false;
            }
         }
      }

      return true;
   }

   /**
    * Stop this segment if possible. If successful, this segment is recursivly
    * updated so that itself and its pools, heaps, and blocks are up-to-date.
    *
    * @param progressMonitor  the progress monitor used for cancellation.
    * @throws IOException  if an I/O exception occurred; parent target is
    * automatically disconnected.
    * @throws ServiceException  if the monitor service reported an error.
    * @throws OperationCanceledException  if the operation was interrupted or
    * cancelled; parent target is automatically disconnected.
    */
   public void stop(IProgressMonitor progressMonitor) throws IOException
   {
      if (progressMonitor == null)
      {
         throw new NullPointerException();
      }

      synchronized (target)
      {
         if (!isKilled())
         {
            target.getMonitorHandler().stopScopeRequest(progressMonitor,
                  Monitor.MONITOR_SCOPE_SEGMENT_ID, getId());
            update(progressMonitor, true);
         }
      }
   }

   /**
    * Kill this segment if possible. If successful, this segment is recursivly
    * updated so that itself and its pools, heaps, and blocks are up-to-date.
    *
    * @param progressMonitor  the progress monitor used for cancellation.
    * @throws IOException  if an I/O exception occurred; parent target is
    * automatically disconnected.
    * @throws ServiceException  if the monitor service reported an error.
    * @throws OperationCanceledException  if the operation was interrupted or
    * cancelled; parent target is automatically disconnected.
    */
   public void kill(IProgressMonitor progressMonitor) throws IOException
   {
      if (progressMonitor == null)
      {
         throw new NullPointerException();
      }

      synchronized (target)
      {
         if (!isKilled())
         {
            target.getMonitorHandler().killScopeRequest(progressMonitor,
                  Monitor.MONITOR_SCOPE_SEGMENT_ID, getId());
            update(progressMonitor, true);
         }
      }
   }

   /**
    * Check the state of this segment object. If recursive is true, recursivly
    * check the state of all nodes in the branch in the tree that is rooted in
    * this segment node.
    *
    * @param recursive  whether or not to recursivly check the state of all
    * nodes in the branch in the tree that is rooted in this segment node.
    * @throws IllegalStateException  if this segment object is not in a
    * consistent state or, if recursive is true, if any node in the branch in
    * the tree that is rooted in this segment node is not in a consistent state.
    */
   public void checkState(boolean recursive) throws IllegalStateException
   {
      // Final fields, no synchronization needed.

      if (target == null)
      {
         throw new IllegalStateException("Target is null.");
      }
      if (name == null)
      {
         throw new IllegalStateException("Name is null.");
      }
      // segmentNumber can be anything.
      // numSignalsAttached can be anything.
      // masMapped can be anything.
      if (pools == null)
      {
         throw new IllegalStateException("Pools is null.");
      }
      if (heaps == null)
      {
         throw new IllegalStateException("Heaps is null.");
      }
      if (blocks == null)
      {
         throw new IllegalStateException("Blocks is null.");
      }

      if (recursive)
      {
         synchronized (pools)
         {
            for (Iterator i = pools.iterator(); i.hasNext();)
            {
               Pool pool = (Pool) i.next();
               pool.checkState(recursive);
            }
         }
         synchronized (heaps)
         {
            for (Iterator i = heaps.iterator(); i.hasNext();)
            {
               Heap heap = (Heap) i.next();
               heap.checkState(recursive);
            }
         }
         synchronized (blocks)
         {
            for (Iterator i = blocks.iterator(); i.hasNext();)
            {
               Block block = (Block) i.next();
               block.checkState(recursive);
            }
         }
      }
   }

   /**
    * Return a string representation of this segment object. The returned string
    * is of the form "&lt;segment-name&gt; (&lt;segment-id&gt;)", where
    * segment-id is in hexadecimal form.
    *
    * @return a string representation of this segment object.
    * @see java.lang.Object#toString()
    */
   public String toString()
   {
      // Final fields, no synchronization needed.
      return (name + " (0x" + Integer.toHexString(getId()).toUpperCase() + ")");
   }
}
