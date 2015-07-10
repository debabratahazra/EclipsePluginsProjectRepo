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

/**
 * This class contains information about an OSE block. A BlockInfo object is
 * immutable and its content is a snapshot at the time of the call of the OSE
 * system model method it was returned from.
 *
 * @see com.ose.system.Target#getFilteredBlocks(IProgressMonitor, int, int, BlockFilter)
 */
public class BlockInfo
{
   /* Target. */
   private final Target target;

   /* Block info. */
   private final String name;
   private final int id;
   private final int sid;
   private final int uid;
   private final boolean supervisor; // Not used
   private final int maxSignalSize;
   private final int signalPoolId;
   private final int stackPoolId;
   private final short executionUnit;
   private final int numSignalsAttached; // Not used
   private final int errorHandler; // Not used

   /**
    * Create a new block info object.
    *
    * @param target              the target.
    * @param name                the block name.
    * @param id                  the block ID.
    * @param sid                 the parent segment ID.
    * @param uid                 the user number.
    * @param supervisor          supervisor mode or not.
    * @param maxSignalSize       the max signal size.
    * @param signalPoolId        the signal pool ID.
    * @param stackPoolId         the stack pool ID.
    * @param executionUnit       the execution unit.
    * @param numSignalsAttached  the number of attached signals.
    * @param errorHandler        the error handler address.
    */
   BlockInfo(Target target,
             String name,
             int id,
             int sid,
             int uid,
             boolean supervisor,
             int maxSignalSize,
             int signalPoolId,
             int stackPoolId,
             short executionUnit,
             int numSignalsAttached,
             int errorHandler)
   {
      if ((target == null) || (name == null))
      {
         throw new NullPointerException();
      }
      this.target = target;
      this.name = name;
      this.id = id;
      this.sid = sid;
      this.uid = uid;
      this.supervisor = supervisor;
      this.maxSignalSize = maxSignalSize;
      this.signalPoolId = signalPoolId;
      this.stackPoolId = stackPoolId;
      this.executionUnit = executionUnit;
      this.numSignalsAttached = numSignalsAttached;
      this.errorHandler = errorHandler;
   }

   /**
    * Return the parent target object.
    *
    * @return the parent target object.
    */
   public Target getTarget()
   {
      return target;
   }

   /**
    * Return the name of this block.
    *
    * @return the name of this block.
    */
   public String getName()
   {
      return name;
   }

   /**
    * Return the ID of this block.
    *
    * @return the ID of this block.
    */
   public int getId()
   {
      return id;
   }

   /**
    * Return the parent segment ID of this block.
    *
    * @return the parent segment ID of this block.
    */
   public int getSid()
   {
      return sid;
   }

   /**
    * Return the user number of this block.
    *
    * @return the user number of this block.
    */
   public int getUid()
   {
      return uid;
   }

   /**
    * Determine whether this block is a supervisor mode block or not.
    *
    * @return true if this block is a supervisor mode block, false othetwise.
    */
   public boolean isSupervisor()
   {
      return supervisor;
   }

   /**
    * Return the maximum signal size, in bytes, that can be allocated by a
    * process in this block.
    *
    * @return the maximum signal size, in bytes, that can be allocated by a
    * process in this block.
    */
   public int getMaxSignalSize()
   {
      return maxSignalSize;
   }

   /**
    * Return the ID of the pool used as signal pool by this block.
    *
    * @return the ID of the pool used as signal pool by this block.
    */
   public int getSignalPoolId()
   {
      return signalPoolId;
   }

   /**
    * Return the ID of the pool used as stack pool by this block.
    *
    * @return the ID of the pool used as stack pool by this block.
    */
   public int getStackPoolId()
   {
      return stackPoolId;
   }

   /**
    * Return the execution unit this block is bound to.
    *
    * @return the execution unit this block is bound to.
    */
   public short getExecutionUnit()
   {
      return executionUnit;
   }

   /**
    * Return the number of signals attached to this block.
    *
    * @return the number of signals attached to this block.
    */
   public int getNumSignalsAttached()
   {
      return numSignalsAttached;
   }

   /**
    * Return the error handler address of this block.
    *
    * @return the error handler address of this block.
    */
   public int getErrorHandler()
   {
      return errorHandler;
   }

   /**
    * Return a string representation of this block info object. The returned
    * string is of the form "&lt;block-name&gt; (&lt;block-id&gt;)", where
    * block-id is in hexadecimal form.
    *
    * @return a string representation of this block info object.
    * @see java.lang.Object#toString()
    */
   public String toString()
   {
      return (name + " (0x" + Integer.toHexString(id).toUpperCase() + ")");
   }
}
