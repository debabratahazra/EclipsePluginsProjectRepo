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
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Platform;

/**
 * This abstract class represents an OSE object with an ID. An OSE object is
 * also a node in the hierarchical OSE system model tree structure.
 *
 * @see com.ose.system.SystemModelNode
 */
public abstract class OseObject implements SystemModelNode
{
   private final int id;
   private long timeStamp;

   /**
    * Create a new OSE object with an ID.
    *
    * @param id  the OSE object ID.
    */
   OseObject(int id)
   {
      this.id = id;
   }

   /**
    * Return the ID of this OSE object.
    *
    * @return the ID of this OSE object.
    */
   public final int getId()
   {
      // Final field, no synchronization needed.
      return id;
   }

   /**
    * If this OSE object has lazily initialized properties and they have not
    * been initialized, initialize those lazily initialized properties and
    * update any non-lazily initialized properties of this OSE object for
    * consistency.
    *
    * @param progressMonitor  the progress monitor used for cancellation.
    * @throws IOException     if an I/O exception occurred.
    */
   public abstract void initLazyProperties(IProgressMonitor progressMonitor)
      throws IOException;

   /**
    * If this OSE object has lazily initialized properties and they have been
    * initialized, update those lazily initialized properties only.
    *
    * @param progressMonitor  the progress monitor used for cancellation.
    * @throws IOException     if an I/O exception occurred.
    */
   abstract void updateLazyProperties(IProgressMonitor progressMonitor)
      throws IOException;

   /**
    * Mark this node and all nodes in the branch in the tree that is rooted in
    * this node as killed.
    */
   abstract void setKilled();

   /**
    * Return the time stamp of this OSE object.
    *
    * @return the time stamp of this OSE object.
    */
   final long getTimeStamp()
   {
      return timeStamp;
   }

   /**
    * Set the time stamp of this OSE object.
    *
    * @param timeStamp  the time stamp to set.
    */
   final void setTimeStamp(long timeStamp)
   {
      this.timeStamp = timeStamp;
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
}
