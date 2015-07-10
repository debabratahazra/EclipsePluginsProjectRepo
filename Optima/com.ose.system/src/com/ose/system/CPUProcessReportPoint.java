/* COPYRIGHT-ENEA-SRC-R1 *
 **************************************************************************
 * Copyright (C) 2006 by Enea Software AB.
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

import com.ose.system.service.monitor.Monitor;
import com.ose.system.service.monitor.MonitorTag;

/**
 * This class represents an OSE CPU process report point.
 *
 * @see com.ose.system.Target#setCPUProcessReportPoint(IProgressMonitor,
 * CPUProcessReportPoint)
 * @see com.ose.system.Target#getCPUProcessReportPoints(IProgressMonitor)
 */
public class CPUProcessReportPoint
{
   /** Scope type for a specified process. */
   public static final int SCOPE_ID = Monitor.MONITOR_SCOPE_ID;

   /** Scope type for processes matching a specified name pattern. */
   public static final int SCOPE_NAME_PATTERN = Monitor.MONITOR_SCOPE_NAME_PATTERN;

   private volatile int id;
   private final int scopeType;
   private final int scopeId;
   private final String namePattern;

   /**
    * Create a new CPU process report point object representing the process with
    * the specified process id.
    *
    * @param pid  the process id of this CPU process report point.
    */
   public CPUProcessReportPoint(int pid)
   {
      this.id = 0;
      this.scopeType = SCOPE_ID;
      this.scopeId = pid;
      this.namePattern = null;
   }

   /**
    * Create a new CPU process report point object representing the process(es)
    * matching the specified process name pattern. The process name pattern
    * accepts wildcards, where a '*' matches any string of zero or more
    * characters, and a '?' matches any single character.
    *
    * @param namePattern  the process name pattern of this CPU process report
    * point.
    */
   public CPUProcessReportPoint(String namePattern)
   {
      if (namePattern == null)
      {
         throw new NullPointerException();
      }
      this.id = 0;
      this.scopeType = SCOPE_NAME_PATTERN;
      this.scopeId = 0;
      this.namePattern = namePattern;
   }

   /**
    * Create a new CPU process report point object.
    *
    * @param id         the id of this CPU process report point.
    * @param scopeType  the scope type of this CPU process report point, i.e.
    * one of the CPUProcessReportPoint.SCOPE_* constants.
    * @param scopeId    the scope id of this CPU process report point, only
    * applicable if scope type is CPUProcessReportPoint.SCOPE_ID.
    * @param tags       the encoded process name pattern of this CPU process
    * report point, only applicable if scope type is
    * CPUProcessReportPoint.SCOPE_NAME_PATTERN.
    */
   CPUProcessReportPoint(int id, int scopeType, int scopeId, MonitorTag[] tags)
   {
      this.id = id;
      this.scopeType = scopeType;
      if (scopeType == SCOPE_ID)
      {
         this.scopeId = scopeId;
         this.namePattern = null;
      }
      else if (scopeType == SCOPE_NAME_PATTERN)
      {
         this.scopeId = 0;
         try
         {
            byte[] bytes;
            int nullIndex;

            if (tags[0].getType() != Monitor.MONITOR_SCOPE_TAG_NAME)
            {
               throw new IllegalArgumentException("Unexpected monitor tag");
            }
            bytes = (byte[]) tags[0].getArgs();
            nullIndex = bytes.length;
            for (int i = 0; i < bytes.length; i++)
            {
               if (bytes[i] == 0)
               {
                  nullIndex = i;
                  break;
               }
            }
            this.namePattern = new String(bytes, 0, nullIndex, "8859_1");
         }
         catch (Exception e)
         {
            throw new IllegalArgumentException(e.getMessage());
         }
      }
      else
      {
         throw new IllegalArgumentException();
      }
   }

   /**
    * Set the id of this CPU process report point.
    *
    * @param id  the id to be set.
    */
   void setId(int id)
   {
      this.id = id;
   }

   /**
    * Return the id of this CPU process report point.
    * Only applicable if this CPU process report point was retrieved with
    * com.ose.system.Target.getCPUProcessReportPoints() or successfully set
    * with com.ose.system.Target.setCPUProcessReportPoint().
    *
    * @return the id of this CPU process report point.
    */
   public int getId()
   {
      return id;
   }

   /**
    * Return the scope type of this CPU process report point, i.e. one of the
    * CPUProcessReportPoint.SCOPE_* constants.
    *
    * @return the scope type of this CPU process report point.
    * @see #SCOPE_ID
    * @see #SCOPE_NAME_PATTERN
    */
   public int getScopeType()
   {
      return scopeType;
   }

   /**
    * Return the scope id (i.e. process id) of this CPU process report point,
    * only applicable if scope type is CPUProcessReportPoint.SCOPE_ID.
    *
    * @return the scope id of this CPU process report point.
    */
   public int getScopeId()
   {
      return scopeId;
   }

   /**
    * Return the process name pattern of this CPU process report point, only
    * applicable if scope type is CPUProcessReportPoint.SCOPE_NAME_PATTERN.
    *
    * @return the process name pattern of this CPU process report point.
    */
   public String getNamePattern()
   {
      return namePattern;
   }

   /**
    * Return an array of the OSE monitor protocol tags constituting this CPU
    * process report point.
    *
    * @return an array of the OSE monitor protocol tags constituting this CPU
    * process report point.
    */
   MonitorTag[] getTags()
   {
      if (scopeType == SCOPE_NAME_PATTERN)
      {
         return new MonitorTag[]
            {new MonitorTag(Monitor.MONITOR_SCOPE_TAG_NAME, namePattern)};
      }
      else
      {
         return null;
      }
   }

   /**
    * Return a string representation of this CPU process report point object.
    *
    * @return a string representation of this CPU process report point object.
    * @see java.lang.Object#toString()
    */
   public String toString()
   {
      if (scopeType == SCOPE_NAME_PATTERN)
      {
         return namePattern;
      }
      else
      {
         return "0x" + Integer.toHexString(scopeId).toUpperCase();
      }
   }
}
