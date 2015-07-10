/* COPYRIGHT-ENEA-SRC-R2 *
 **************************************************************************
 * Copyright (C) 2009 by Enea Software AB.
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
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import com.ose.system.Target.MonitorHandler;
import com.ose.system.service.monitor.Monitor;
import com.ose.system.service.monitor.MonitorHeapBufferSizeInfo;
import com.ose.system.service.monitor.MonitorHeapBufferSizeInfo64;
import com.ose.system.service.monitor.MonitorHeapFragmentInfo;
import com.ose.system.service.monitor.MonitorHeapFragmentInfo64;

/**
 * This class represents an OSE heap and is a node in the hierarchical OSE
 * system model tree structure.
 * <p>
 * A heap has no children nodes and a segment or target as parent node. Heap
 * buffers of interest in a heap can be found using filters.
 *
 * @see com.ose.system.SystemModelNode
 * @see com.ose.system.OseObject
 */
public class Heap extends OseObject implements SystemModelNode
{
   /* Internal field read/write lock. */
   private final Object lock = new Object();

   /* Killed status. */
   private boolean killed;

   /* Target and segment. */
   private final Target target;
   private final Segment segment;

   /* Heap info. */
   private final int owner;
   private final int sid;
   private long size;
   private long usedSize;
   private long peakUsedSize;
   private long requestedSize;
   private long largestFree;
   private long largeThreshold;
   private int priv;
   private int shared;
   private int mallocFailed;
   private final ArrayList heapBufferSizes;

   /* Heap Fragments. */
   private final ArrayList heapFragments;
   private final ArrayList tmpHeapFragments;
   private boolean heapFragmentsInitialized;

   /**
    * Create a new heap object 32bit.
    *
    * @param target          the target.
    * @param segment         the parent segment.
    * @param id              the heap ID.
    * @param owner           the heap owner ID.
    * @param sid             the parent segment ID.
    * @param size            the total size.
    * @param usedSize        the used size.
    * @param peakUsedSize    the peak used size.
    * @param reqSize         the requested size.
    * @param largestFree     the largest free heap buffer size.
    * @param largeThreshold  the large threshold value.
    * @param priv            number of private heap buffers.
    * @param shared          number of shared heap buffers.
    * @param mallocFailed    number of failed malloc attempts.
    * @param bufferSizes     the heap buffer sizes.
    */
   Heap(Target target,
        Segment segment,
        int id,
        int owner,
        int sid,
        int size,
        int usedSize,
        int peakUsedSize,
        int reqSize,
        int largestFree,
        int largeThreshold,
        int priv,
        int shared,
        int mallocFailed,
        MonitorHeapBufferSizeInfo[] bufferSizes)
   {
      super(id);
      if ((target == null) ||
          (target.hasExtendedSegmentSupport() && (segment == null)) ||
          (bufferSizes == null))
      {
         throw new NullPointerException();
      }
      this.target = target;
      this.segment = segment;
      this.owner = owner;
      this.sid = sid;
      this.size = size;
      this.usedSize = usedSize;
      this.peakUsedSize = peakUsedSize;
      this.requestedSize = reqSize;
      this.largestFree = largestFree;
      this.largeThreshold = largeThreshold;
      this.priv = priv;
      this.shared = shared;
      this.mallocFailed = mallocFailed;
      this.heapBufferSizes = new ArrayList();
      for (int i = 0; i < bufferSizes.length; i++)
      {
         HeapBufferSizeInfo bufferSize = new HeapBufferSizeInfo(bufferSizes[i]);
         this.heapBufferSizes.add(bufferSize);
      }
      this.heapFragments = new ArrayList();
      this.tmpHeapFragments = new ArrayList();
   }
   
   /**
    * Create a new heap object 64bit.
    *
    * @param target          the target.
    * @param segment         the parent segment.
    * @param id              the heap ID.
    * @param owner           the heap owner ID.
    * @param sid             the parent segment ID.
    * @param size            the total size.
    * @param usedSize        the used size.
    * @param peakUsedSize    the peak used size.
    * @param reqSize         the requested size.
    * @param largestFree     the largest free heap buffer size.
    * @param largeThreshold  the large threshold value.
    * @param priv            number of private heap buffers.
    * @param shared          number of shared heap buffers.
    * @param mallocFailed    number of failed malloc attempts.
    * @param bufferSizes     the heap buffer sizes.
    */
   Heap(Target target,
        Segment segment,
        int id,
        int owner,
        int sid,
        long size,
        long usedSize,
        long peakUsedSize,
        long reqSize,
        long largestFree,
        long largeThreshold,
        int priv,
        int shared,
        int mallocFailed,
        MonitorHeapBufferSizeInfo64[] bufferSizes)
   {
      super(id);
      if ((target == null) ||
          (target.hasExtendedSegmentSupport() && (segment == null)) ||
          (bufferSizes == null))
      {
         throw new NullPointerException();
      }
      this.target = target;
      this.segment = segment;
      this.owner = owner;
      this.sid = sid;
      this.size = size;
      this.usedSize = usedSize;
      this.peakUsedSize = peakUsedSize;
      this.requestedSize = reqSize;
      this.largestFree = largestFree;
      this.largeThreshold = largeThreshold;
      this.priv = priv;
      this.shared = shared;
      this.mallocFailed = mallocFailed;
      this.heapBufferSizes = new ArrayList();
      for (int i = 0; i < bufferSizes.length; i++)
      {
         HeapBufferSizeInfo bufferSize = new HeapBufferSizeInfo(bufferSizes[i]);
         this.heapBufferSizes.add(bufferSize);
      }
      this.heapFragments = new ArrayList();
      this.tmpHeapFragments = new ArrayList();
   }

   /**
    * Change the mutable properties of this heap 32 bit.
    *
    * @param size            the total size.
    * @param usedSize        the used size.
    * @param peakUsedSize    the peak used size.
    * @param reqSize         the requested size.
    * @param largestFree     the largest free heap buffer size.
    * @param largeThreshold  the large threshold value.
    * @param priv            number of private heap buffers.
    * @param shared          number of shared heap buffers.
    * @param mallocFailed    number of failed malloc attempts.
    * @param bufferSizes     the heap buffer sizes.
    * @return true if any of the mutable properties of this heap were changed,
    * false otherwise.
    */
   boolean change(int size,
                  int usedSize,
                  int peakUsedSize,
                  int reqSize,
                  int largestFree,
                  int largeThreshold,
                  int priv,
                  int shared,
                  int mallocFailed,
                  MonitorHeapBufferSizeInfo[] bufferSizes)
   {
      synchronized (lock)
      {
         // Always treat the heap as being changed when it is updated.
         boolean changed = true;
         // FIXME: Determine if really changed.
         this.size = size;
         this.usedSize = usedSize;
         this.peakUsedSize = peakUsedSize;
         this.requestedSize = reqSize;
         this.largestFree = largestFree;
         this.largeThreshold = largeThreshold;
         this.priv = priv;
         this.shared = shared;
         this.mallocFailed = mallocFailed;
         this.heapBufferSizes.clear();
         for (int i = 0; i < bufferSizes.length; i++)
         {
            HeapBufferSizeInfo bufferSize =
               new HeapBufferSizeInfo(bufferSizes[i]);
            this.heapBufferSizes.add(bufferSize);
         }
         this.heapBufferSizes.trimToSize();
         return changed;
      }
   }
   
   /**
    * Change the mutable properties of this heap 64 bit.
    *
    * @param size            the total size.
    * @param usedSize        the used size.
    * @param peakUsedSize    the peak used size.
    * @param reqSize         the requested size.
    * @param largestFree     the largest free heap buffer size.
    * @param largeThreshold  the large threshold value.
    * @param priv            number of private heap buffers.
    * @param shared          number of shared heap buffers.
    * @param mallocFailed    number of failed malloc attempts.
    * @param bufferSizes     the heap buffer sizes.
    * @return true if any of the mutable properties of this heap were changed,
    * false otherwise.
    */
   boolean change(long size,
		   	      long usedSize,
		          long peakUsedSize,
                  long reqSize,
                  long largestFree,
                  long largeThreshold,
                  int priv,
                  int shared,
                  int mallocFailed,
                  MonitorHeapBufferSizeInfo64[] bufferSizes)
   {
      synchronized (lock)
      {
         // Always treat the heap as being changed when it is updated.
         boolean changed = true;
         // FIXME: Determine if really changed.
         this.size = size;
         this.usedSize = usedSize;
         this.peakUsedSize = peakUsedSize;
         this.requestedSize = reqSize;
         this.largestFree = largestFree;
         this.largeThreshold = largeThreshold;
         this.priv = priv;
         this.shared = shared;
         this.mallocFailed = mallocFailed;
         this.heapBufferSizes.clear();
         for (int i = 0; i < bufferSizes.length; i++)
         {
            HeapBufferSizeInfo bufferSize =
               new HeapBufferSizeInfo(bufferSizes[i]);
            this.heapBufferSizes.add(bufferSize);
         }
         this.heapBufferSizes.trimToSize();
         return changed;
      }
   }

   /**
    * Add heap fragments to this heap 32 bit.
    *
    * @param fragments  the heap fragments to add.
    * @see #commitHeapFragments()
    */
   void addHeapFragments(MonitorHeapFragmentInfo[] fragments)
   {
      synchronized (lock)
      {
         for (int i = 0; i < fragments.length; i++)
         {
            HeapFragmentInfo fragment = new HeapFragmentInfo(fragments[i]);
            tmpHeapFragments.add(fragment);
         }
      }
   }
   
   /**
    * Add heap fragments to this heap 64 bit.
    *
    * @param fragments  the heap fragments to add.
    * @see #commitHeapFragments()
    */
   void addHeapFragments(MonitorHeapFragmentInfo64[] fragments)
   {
      synchronized (lock)
      {
         for (int i = 0; i < fragments.length; i++)
         {
            HeapFragmentInfo fragment = new HeapFragmentInfo(fragments[i]);
            tmpHeapFragments.add(fragment);
         }
      }
   }

   /**
    * Commit all heap fragments added to this heap with the addHeapFragments()
    * method.
    *
    * @return true if any heap fragment was changed, added, or removed; false
    * otherwise.
    * @see #addHeapFragments(MonitorHeapFragmentInfo[])
    */
   boolean commitHeapFragments()
   {
      synchronized (lock)
      {
         boolean changed = !heapFragments.equals(tmpHeapFragments);
         if (changed)
         {
            heapFragments.clear();
            heapFragments.addAll(tmpHeapFragments);
            heapFragments.trimToSize();
         }
         tmpHeapFragments.clear();
         tmpHeapFragments.trimToSize();
         return changed;
      }
   }

   /**
    * Determine whether this heap is killed or not.
    *
    * @return true if this heap is killed, false otherwise.
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
    * Mark this heap as killed.
    *
    * @see com.ose.system.OseObject#setKilled()
    */
   void setKilled()
   {
      setKilled(true);
   }

   /**
    * Mark this heap as killed.
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
      target.removeHeapId(getId());
      if (self)
      {
         SystemModel.getInstance().fireNodesChanged(getParent(),
               Collections.singletonList(this));
      }
   }

   /**
    * Determine whether all of the given immutable heap properties are identical
    * to the ones of this heap.
    *
    * @param id     ID of a heap.
    * @param owner  owner ID of a heap.
    * @param sid    parent segment ID of a heap.
    * @return true if all of the given immutable heap properties matches this
    * heap, false otherwise.
    */
   boolean matches(int id, int owner, int sid)
   {
      synchronized (lock)
      {
         boolean result = false;
         result = (getId() == id) && (this.owner == owner) && (this.sid == sid);
         return result;
      }
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
    * Return the parent segment object or null if this target does not support
    * segments.
    *
    * @return the parent segment object or null if this target does not support
    * segments.
    */
   public Segment getSegment()
   {
      // Final field, no synchronization needed.
      return segment;
   }

   /**
    * Return the owner ID of this heap.
    *
    * @return the owner ID of this heap.
    */
   public int getOwner()
   {
      // Final field, no synchronization needed.
      return owner;
   }

   /**
    * Return the parent segment ID of this heap or 0 if not available.
    *
    * @return the parent segment ID of this heap or 0 if not available.
    */
   public int getSid()
   {
      // Final field, no synchronization needed.
      return sid;
   }

   /**
    * Return the total size of this heap in bytes.
    *
    * @return the total size of this heap in bytes.
    */
   public long getSize()
   {
      synchronized (lock)
      {
         return size;
      }
   }

   /**
    * Return the total free size of this heap in bytes.
    *
    * @return the total free size of this heap in bytes.
    */
   public long getFreeSize()
   {
      synchronized (lock)
      {
    	 return (size-usedSize);
      }
   }

   /**
    * Return the total actually used size of this heap in bytes.
    *
    * @return the total actually used size of this heap in bytes.
    */
   public long getUsedSize()
   {
      synchronized (lock)
      {
         return usedSize;
      }
   }

   /**
    * Return the peak actually used size of this heap in bytes or 0 if not
    * available.
    *
    * @return the peak actually used size of this heap in bytes or 0 if not
    * available.
    */
   public long getPeakUsedSize()
   {
      synchronized (lock)
      {
         return peakUsedSize;
      }
   }

   /**
    * Return the total requested size of this heap in bytes.
    *
    * @return the total requested size of this heap in bytes.
    */
   public long getRequestedSize()
   {
      synchronized (lock)
      {
         return requestedSize;
      }
   }

   /**
    * Return the size, in bytes, of the largest free heap buffer in this heap.
    *
    * @return the size, in bytes, of the largest free heap buffer in this heap.
    */
   public long getLargestFree()
   {
      synchronized (lock)
      {
         return largestFree;
      }
   }

   /**
    * Return the large threshold value of this heap in bytes or 0 if not
    * applicable. Heap buffers larger than this value are allocated from
    * another memory manager.
    *
    * @return the large threshold value of this heap in bytes or 0 if not
    * applicable.
    */
   public long getLargeThreshold()
   {
      synchronized (lock)
      {
         return largeThreshold;
      }
   }

   /**
    * Return the number of private heap buffers in this heap.
    *
    * @return the number of private heap buffers in this heap.
    */
   public int getPrivate()
   {
      synchronized (lock)
      {
         return priv;
      }
   }

   /**
    * Return the number of shared heap buffers in this heap.
    *
    * @return the number of shared heap buffers in this heap.
    */
   public int getShared()
   {
      synchronized (lock)
      {
         return shared;
      }
   }

   /**
    * Return the number of failed malloc() attempts in this heap.
    *
    * @return the number of failed malloc() attempts in this heap.
    */
   public int getMallocFailed()
   {
      synchronized (lock)
      {
         return mallocFailed;
      }
   }

   /**
    * Return an array of the available heap buffer sizes of this heap. A new
    * array is created and returned for each call.
    *
    * @return an array of the available heap buffer sizes of this heap.
    */
   public HeapBufferSizeInfo[] getHeapBufferSizes()
   {
      synchronized (lock)
      {
         return (HeapBufferSizeInfo[])
            heapBufferSizes.toArray(new HeapBufferSizeInfo[0]);
      }
   }

   /**
    * Return an array of the heap fragments of this heap. A new array is created
    * and returned for each call.
    *
    * @return an array of the heap fragments of this heap.
    */
   public HeapFragmentInfo[] getHeapFragments()
   {
      synchronized (lock)
      {
         return (HeapFragmentInfo[])
            heapFragments.toArray(new HeapFragmentInfo[0]);
      }
   }

   /**
    * Return an array of the heap buffers in this heap that match the given heap
    * buffer filter. The result is a snapshot of the heap at the time of the
    * call.
    *
    * @param progressMonitor  the progress monitor used for cancellation.
    * @param filter           the heap buffer filter, or null if all heap
    * buffers in the heap should be returned.
    * @return an array of the heap buffers in this heap that match the given
    * heap buffer filter.
    * @throws IOException  if an I/O exception occurred; parent target is
    * automatically disconnected.
    * @throws ServiceException  if the monitor service reported an error.
    * @throws OperationCanceledException  if the operation was interrupted or
    * cancelled; parent target is automatically disconnected.
    */
   public HeapBufferInfo[] getFilteredHeapBuffers(
         IProgressMonitor progressMonitor,
         HeapBufferFilter filter)
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
            return target.getMonitorHandler().getFilteredHeapBuffersRequest(
                  progressMonitor, Monitor.MONITOR_SCOPE_ID, getId(),
                  (filter != null ? filter.getTags() : null));
         }
         else
         {
            return new HeapBufferInfo[0];
         }
      }
   }

   /**
    * Return the parent object. If this target supports segments, the parent
    * object is a segment, otherwise it is a target object.
    *
    * @return the parent object.
    * @see com.ose.system.SystemModelNode#getParent()
    */
   public SystemModelNode getParent()
   {
      return (target.hasExtendedSegmentSupport() ?
            getSegment() : (SystemModelNode) getTarget());
   }

   /**
    * A heap object has no children and always returns an empty children array.
    *
    * @return an empty array.
    * @see com.ose.system.SystemModelNode#getChildren()
    */
   public SystemModelNode[] getChildren()
   {
      return new SystemModelNode[0];
   }

   /**
    * Determine whether this node is a leaf node or not. A heap object is a leaf
    * node and always returns true.
    *
    * @return true since a heap object is a leaf node.
    * @see com.ose.system.SystemModelNode#isLeaf()
    */
   public boolean isLeaf()
   {
      return true;
   }

   /**
    * If the lazily initialized heap fragments of this heap have not been
    * initialized, initialize them and update the other properties of this
    * heap for consistency.
    *
    * @param progressMonitor  the progress monitor used for cancellation.
    * @throws IOException     if an I/O exception occurred.
    * @see com.ose.system.OseObject#initLazyProperties(org.eclipse.core.runtime.IProgressMonitor)
    */
   public void initLazyProperties(IProgressMonitor progressMonitor)
      throws IOException
   {
      if (progressMonitor == null)
      {
         throw new NullPointerException();
      }

      synchronized (target)
      {
         if (!isKilled() && target.hasHeapFragmentSupport() &&
             !heapFragmentsInitialized)
         {
            MonitorHandler monitorHandler = target.getMonitorHandler();
            monitorHandler.getHeapInfoRequest(progressMonitor, this,
                  Monitor.MONITOR_SCOPE_ID, getId());
            monitorHandler.getHeapFragmentInfoRequest(progressMonitor, this,
                  Monitor.MONITOR_SCOPE_ID, getId());
            heapFragmentsInitialized = true;
         }
      }
   }

   /**
    * Update this heap.
    *
    * @param progressMonitor  the progress monitor used for cancellation.
    * @param recursive        not applicable.
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
            monitorHandler.getHeapInfoRequest(progressMonitor, this,
                  Monitor.MONITOR_SCOPE_ID, getId());
            if (target.hasHeapFragmentSupport() && heapFragmentsInitialized)
            {
               monitorHandler.getHeapFragmentInfoRequest(progressMonitor, this,
                     Monitor.MONITOR_SCOPE_ID, getId());
            }
         }
      }
   }

   /**
    * If the lazily initialized heap fragments of this heap have been
    * initialized, update them.
    *
    * @param progressMonitor  the progress monitor used for cancellation.
    * @throws IOException     if an I/O exception occurred.
    * @see com.ose.system.OseObject#updateLazyProperties(org.eclipse.core.runtime.IProgressMonitor)
    */
   void updateLazyProperties(IProgressMonitor progressMonitor)
      throws IOException
   {
      if (progressMonitor == null)
      {
         throw new NullPointerException();
      }

      synchronized (target)
      {
         if (!isKilled() && target.hasHeapFragmentSupport() &&
             heapFragmentsInitialized)
         {
            MonitorHandler monitorHandler = target.getMonitorHandler();
            monitorHandler.getHeapFragmentInfoRequest(progressMonitor, this,
                  Monitor.MONITOR_SCOPE_ID, getId());
         }
      }
   }

   /**
    * Clean this heap, i.e. remove it if it is marked as killed.
    *
    * @see com.ose.system.SystemModelNode#clean()
    */
   public void clean()
   {
      synchronized (target)
      {
         if (isKilled())
         {
            if (target.hasExtendedSegmentSupport())
            {
               segment.removeHeap(this);
               SystemModel.getInstance().fireNodesRemoved(segment,
                     Collections.singletonList(this));
            }
            else
            {
               target.removeHeap(this);
               SystemModel.getInstance().fireNodesRemoved(target,
                     Collections.singletonList(this));
            }
         }
      }
   }

   /**
    * Check the state of this heap object.
    *
    * @param recursive               not applicable.
    * @throws IllegalStateException  if this heap object is not in a consistent
    * state.
    * @see com.ose.system.SystemModelNode#checkState(boolean)
    */
   public void checkState(boolean recursive) throws IllegalStateException
   {
      synchronized (lock)
      {
         if (target == null)
         {
            throw new IllegalStateException("Target is null.");
         }
         if (target.hasExtendedSegmentSupport() && (segment == null))
         {
            throw new IllegalStateException("Segment is null.");
         }
         // owner can be anything.
         if (target.hasExtendedSegmentSupport() && (sid != segment.getId()))
         {
            throw new IllegalStateException("Inconsistent segment id.");
         }
         // size can be anything.
         if (usedSize > size)
         {
            throw new IllegalStateException("Inconsistent total / used size.");
         }
         if (peakUsedSize > size)
         {
            throw new IllegalStateException("Inconsistent total / peak used size.");
         }
         if (usedSize > peakUsedSize)
         {
            throw new IllegalStateException("Inconsistent peak used / used size.");
         }
         if (requestedSize > size)
         {
            throw new IllegalStateException("Inconsistent total / requested size.");
         }
         if (requestedSize > usedSize)
         {
            throw new IllegalStateException("Inconsistent used / requested size.");
         }
         if (largestFree > size)
         {
            throw new IllegalStateException(
               "Inconsistent total / largest free heap buffer size.");
         }
         // largeThreshold can be anything.
         // priv can be anything.
         // shared can be anything.
         // mallocFailed can be anything.
         if (heapBufferSizes == null)
         {
            throw new IllegalStateException("Heap buffer sizes is null.");
         }
         if (heapFragments == null)
         {
            throw new IllegalStateException("Heap fragments is null.");
         }
      }
   }

   /**
    * Return a string representation of this heap object. The returned string
    * is of the form "heap (&lt;heap-id&gt;)", where heap-id is in hexadecimal
    * form.
    *
    * @return a string representation of this heap object.
    * @see java.lang.Object#toString()
    */
   public String toString()
   {
      // Final field, no synchronization needed.
      return ("heap (0x" + Integer.toHexString(getId()).toUpperCase() + ")");
   }
}
