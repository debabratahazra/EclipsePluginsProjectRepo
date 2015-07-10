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

import com.ose.system.service.pm.ProgramManager;

/**
 * This class contains information about an OSE program. A ProgramInfo object is
 * immutable and its content is a snapshot at the time of the call of the OSE
 * system model method it was returned from.
 *
 * @see com.ose.system.Target#getProgramInfo(IProgressMonitor, int)
 */
public class ProgramInfo
{
   /* Program states. */

   /** Program is created but not started. */
   public static final int PROGRAM_CREATED = ProgramManager.PM_PROGRAM_CREATED;

   /** Program is created and started. */
   public static final int PROGRAM_STARTED = ProgramManager.PM_PROGRAM_STARTED;

   /* Target. */
   private final Target target;

   /* Program info. */
   private final String installHandle;
   private final int id;
   private final int domain;
   private final int mainBid;
   private final int mainPid;
   private final long stackPoolBase;
   private final long stackPoolSize;
   private final int uid;
   private final int state;
   // Only available in OSE 5.2 and later:
   private final boolean extendedInfo;
   private final int sid;
   private final int stackPoolId;
   private final int signalPoolId;
   private final long signalPoolBase;
   private final long signalPoolSize;
   private final int heap;

   /**
    * Create a new program info object.
    *
    * @param target         the target.
    * @param installHandle  the install handle.
    * @param id             the program ID.
    * @param domain         the domain.
    * @param mainBid        the main block ID.
    * @param mainPid        the main process ID.
    * @param stackPoolBase  the stack pool base address.
    * @param stackPoolSize  the stack pool size.
    * @param uid            the user number.
    * @param state          the program state.
    * @param extendedInfo   true if the following parameters are valid.
    * @param sid            the segment ID.
    * @param stackPoolId    the stack pool ID.
    * @param signalPoolId   the signal pool ID.
    * @param signalPoolBase the signal pool base address.
    * @param signalPoolSize the signal pool size.
    * @param heap           the heap reference.
    */
   ProgramInfo(Target target,
               String installHandle,
               int id,
               int domain,
               int mainBid,
               int mainPid,
               long stackPoolBase,
               long stackPoolSize,
               int uid,
               int state,
               boolean extendedInfo,
               int sid,
               int stackPoolId,
               int signalPoolId,
               long signalPoolBase,
               long signalPoolSize,
               int heap)
   {
      if ((target == null) || (installHandle == null))
      {
         throw new NullPointerException();
      }
      this.target = target;
      this.installHandle = installHandle;
      this.id = id;
      this.domain = domain;
      this.mainBid = mainBid;
      this.mainPid = mainPid;
      this.stackPoolBase = stackPoolBase;
      this.stackPoolSize = stackPoolSize;
      this.uid = uid;
      this.state = state;
      this.extendedInfo = extendedInfo;
      this.sid = sid;
      this.stackPoolId = stackPoolId;
      this.signalPoolId = signalPoolId;
      this.signalPoolBase = signalPoolBase;
      this.signalPoolSize = signalPoolSize;
      this.heap = heap;
   }

   /**
    * Determine whether this program has extended info or not.
    * The methods getSid(), getStackPoolId(), getSignalPoolId(),
    * getSignalPoolBase(), getSignalPoolSize(), and getHeap()
    * are only valid if a program has extended info.
    *
    * @return true if this program has extended info, false otherwise.
    */
   public boolean hasExtendedInfo()
   {
      return extendedInfo;
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
    * Return the installation handle of the load module that this program was
    * created from.
    *
    * @return the installation handle of the load module that this program was
    * created from.
    */
   public String getInstallHandle()
   {
      return installHandle;
   }

   /**
    * Return the ID of this program.
    *
    * @return the ID of this program.
    */
   public int getId()
   {
      return id;
   }

   /**
    * Return the domain of this program.
    *
    * @return the domain of this program.
    */
   public int getDomain()
   {
      return domain;
   }

   /**
    * Return the ID of the segment of this program.
    * Only available in OSE 5.2 and later,
    * i.e. if hasExtendedInfo() returns true.
    *
    * @return the ID of the segment of this program.
    */
   public int getSid()
   {
      return sid;
   }

   /**
    * Return the ID of the main block of this program.
    *
    * @return the ID of the main block of this program.
    */
   public int getMainBid()
   {
      return mainBid;
   }

   /**
    * Return the ID of the main process of this program.
    *
    * @return the ID of the main process of this program.
    */
   public int getMainPid()
   {
      return mainPid;
   }

   /**
    * Return the ID of the stack pool of this program.
    * Only available in OSE 5.2 and later,
    * i.e. if hasExtendedInfo() returns true.
    *
    * @return the ID of the stack pool of this program.
    */
   public int getStackPoolId()
   {
      return stackPoolId;
   }

   /**
    * Return the base address of the stack pool of this program.
    *
    * @return the base address of the stack pool of this program.
    */
   public long getStackPoolBaseLong()
   {
      return stackPoolBase;
   }

   /**
    * Return the base address of the stack pool of this program.
    *
    * @return the base address of the stack pool of this program.
    * @deprecated As of Optima 2.8, use {@link #getStackPoolBaseLong()} instead
    */
   @Deprecated
   public int getStackPoolBase()
   {
      return (int)stackPoolBase;
   }

   /**
    * Return the size, in bytes, of the stack pool of this program.
    *
    * @return the size, in bytes, of the stack pool of this program.
    */
   public long getStackPoolSizeLong()
   {
      return stackPoolSize;
   }
   
   /**
    * Return the size, in bytes, of the stack pool of this program.
    *
    * @return the size, in bytes, of the stack pool of this program.
    * @deprecated As of Optima 2.8, use {@link #getStackPoolSizeLong()} instead
    */
   @Deprecated
   public int getStackPoolSize()
   {
      return (int)stackPoolSize;
   }

   /**
    * Return the ID of the signal pool of this program.
    * Only available in OSE 5.2 and later,
    * i.e. if hasExtendedInfo() returns true.
    *
    * @return the ID of the signal pool of this program.
    */
   public int getSignalPoolId()
   {
      return signalPoolId;
   }

   /**
    * Return the base address of the signal pool of this program.
    * Only available in OSE 5.2 and later,
    * i.e. if hasExtendedInfo() returns true.
    *
    * @return the base address of the signal pool of this program.
    */
   public long getSignalPoolBaseLong()
   {
      return signalPoolBase;
   }

   /**
    * Return the base address of the signal pool of this program.
    * Only available in OSE 5.2 and later,
    * i.e. if hasExtendedInfo() returns true.
    *
    * @return the base address of the signal pool of this program.
    * @deprecated As of Optima 2.8, use {@link #getSignalPoolBaseLong()} instead
    */
   @Deprecated
   public int getSignalPoolBase()
   {
      return (int)signalPoolBase;
   }
   
   /**
    * Return the size, in bytes, of the signal pool of this program.
    * Only available in OSE 5.2 and later,
    * i.e. if hasExtendedInfo() returns true.
    *
    * @return the size, in bytes, of the signal pool of this program.
    */
   public long getSignalPoolSizeLong()
   {
      return signalPoolSize;
   }
   
   /**
    * Return the size, in bytes, of the signal pool of this program.
    * Only available in OSE 5.2 and later,
    * i.e. if hasExtendedInfo() returns true.
    *
    * @return the size, in bytes, of the signal pool of this program.
    * @deprecated As of Optima 2.8, use {@link #getSignalPoolSizeLong()} instead
    */
   @Deprecated
   public int getSignalPoolSize()
   {
      return (int)signalPoolSize;
   }

   /**
    * Return the heap reference of this program.
    * Only available in OSE 5.2 and later,
    * i.e. if hasExtendedInfo() returns true.
    *
    * @return the heap reference of this program.
    */
   public int getHeap()
   {
      return heap;
   }

   /**
    * Return the user number of this program.
    *
    * @return the user number of this program.
    */
   public int getUid()
   {
      return uid;
   }

   /**
    * Return the state of this program, i.e. one of the
    * ProgramInfo.PROGRAM_CREATED or ProgramInfo.PROGRAM_STARTED constants.
    *
    * @return the state of this program.
    * @see #PROGRAM_CREATED
    * @see #PROGRAM_STARTED
    */
   public int getState()
   {
      return state;
   }

   /**
    * Return a string representation of this program info object. The returned
    * string is of the form "&lt;install-handle&gt; (&lt;program-id&gt;)", where
    * program-id is in hexadecimal form.
    *
    * @return a string representation of this program info object.
    * @see java.lang.Object#toString()
    */
   public String toString()
   {
      return (installHandle + " (0x" + Integer.toHexString(id).toUpperCase() + ")");
   }
}
