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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import com.ose.system.service.monitor.Monitor;
import com.ose.system.service.monitor.MonitorTag;

/**
 * This class is a filter used when filtering OSE stacks.
 *
 * @see com.ose.system.Pool#getFilteredPoolStacks(IProgressMonitor, StackFilter)
 */
public class StackFilter
{
   private final List tags;

   /**
    * Create a new empty stack filter object.
    */
   public StackFilter()
   {
      tags = new ArrayList();
   }

   /**
    * Add a filter criteria for the owner process ID of a stack.
    *
    * @param exclude  whether or not to invert the filter criteria.
    * @param ownerId  the owner process ID value.
    */
   public void filterOwnerId(boolean exclude, int ownerId)
   {
      if (exclude)
      {
         tags.add(new MonitorTag(Monitor.MONITOR_STACK_FILTER_TAG_NOT));
      }
      tags.add(new MonitorTag(Monitor.MONITOR_STACK_FILTER_TAG_OWNER_ID,
            new Integer(ownerId)));
   }

   /**
    * Add a filter criteria for the owner process name of a stack.
    * Accepts wildcards, where a '*' matches any string of zero or more
    * characters, and a '?' matches any single character.
    *
    * @param exclude    whether or not to invert the filter criteria.
    * @param ownerName  the owner process name value.
    */
   public void filterOwnerName(boolean exclude, String ownerName)
   {
      if (exclude)
      {
         tags.add(new MonitorTag(Monitor.MONITOR_STACK_FILTER_TAG_NOT));
      }
      tags.add(new MonitorTag(Monitor.MONITOR_STACK_FILTER_TAG_OWNER_NAME,
            ownerName));
   }

   /**
    * Add a filter criteria for the size of a stack.
    *
    * @param exclude  whether or not to invert the filter criteria.
    * @param from     the min stack size value, inclusive.
    * @param to       the max stack size value, exclusive.
    */
   public void filterSize(boolean exclude, int from, int to)
   {
      if (exclude)
      {
         tags.add(new MonitorTag(Monitor.MONITOR_STACK_FILTER_TAG_NOT));
      }
      tags.add(new MonitorTag(Monitor.MONITOR_STACK_FILTER_TAG_SIZE,
            new Integer[] {new Integer(from), new Integer(to)}));
   }

   /**
    * Add a filter criteria for the actual buffer size of a stack.
    *
    * @param exclude  whether or not to invert the filter criteria.
    * @param from     the min actual stack buffer size value, inclusive.
    * @param to       the max actual stack buffer size value, exclusive.
    */
   public void filterBufferSize(boolean exclude, int from, int to)
   {
      if (exclude)
      {
         tags.add(new MonitorTag(Monitor.MONITOR_STACK_FILTER_TAG_NOT));
      }
      tags.add(new MonitorTag(Monitor.MONITOR_STACK_FILTER_TAG_BUFSIZE,
            new Integer[] {new Integer(from), new Integer(to)}));
   }

   /**
    * Add a filter criteria for the buffer address of a stack.
    *
    * @param exclude  whether or not to invert the filter criteria.
    * @param from     the min stack buffer address value, inclusive.
    * @param to       the max stack buffer address value, exclusive.
    */
   public void filterAddress(boolean exclude, int from, int to)
   {
      if (exclude)
      {
         tags.add(new MonitorTag(Monitor.MONITOR_STACK_FILTER_TAG_NOT));
      }
      tags.add(new MonitorTag(Monitor.MONITOR_STACK_FILTER_TAG_ADDRESS,
            new Integer[] {new Integer(from), new Integer(to)}));
   }

   /**
    * Add a filter criteria for the peak usage of a stack.
    *
    * @param exclude  whether or not to invert the filter criteria.
    * @param from     the min stack peak usage value, inclusive.
    * @param to       the max stack peak usage value, exclusive.
    */
   public void filterUsed(boolean exclude, int from, int to)
   {
      if (exclude)
      {
         tags.add(new MonitorTag(Monitor.MONITOR_STACK_FILTER_TAG_NOT));
      }
      tags.add(new MonitorTag(Monitor.MONITOR_STACK_FILTER_TAG_USED,
            new Integer[] {new Integer(from), new Integer(to)}));
   }

   /**
    * Add a filter criteria for the unused size (size - used) of a stack.
    *
    * @param exclude  whether or not to invert the filter criteria.
    * @param from     the min unused stack size value, inclusive.
    * @param to       the max unused stack size value, exclusive.
    */
   public void filterUnused(boolean exclude, int from, int to)
   {
      if (exclude)
      {
         tags.add(new MonitorTag(Monitor.MONITOR_STACK_FILTER_TAG_NOT));
      }
      tags.add(new MonitorTag(Monitor.MONITOR_STACK_FILTER_TAG_UNUSED,
            new Integer[] {new Integer(from), new Integer(to)}));
   }

   /**
    * Add a filter criteria for the size of a supervisor stack.
    *
    * @param exclude  whether or not to invert the filter criteria.
    * @param from     the min supervisor stack size value, inclusive.
    * @param to       the max supervisor stack size value, exclusive.
    */
   public void filterSupervisorSize(boolean exclude, int from, int to)
   {
      if (exclude)
      {
         tags.add(new MonitorTag(Monitor.MONITOR_STACK_FILTER_TAG_NOT));
      }
      tags.add(new MonitorTag(Monitor.MONITOR_STACK_FILTER_TAG_SUPERVISOR_SIZE,
            new Integer[] {new Integer(from), new Integer(to)}));
   }

   /**
    * Add a filter criteria for the actual buffer size of a supervisor stack.
    *
    * @param exclude  whether or not to invert the filter criteria.
    * @param from     the min actual supervisor stack buffer size value, inclusive.
    * @param to       the max actual supervisor stack buffer size value, exclusive.
    */
   public void filterSupervisorBufferSize(boolean exclude, int from, int to)
   {
      if (exclude)
      {
         tags.add(new MonitorTag(Monitor.MONITOR_STACK_FILTER_TAG_NOT));
      }
      tags.add(new MonitorTag(Monitor.MONITOR_STACK_FILTER_TAG_SUPERVISOR_BUFSIZE,
            new Integer[] {new Integer(from), new Integer(to)}));
   }

   /**
    * Add a filter criteria for the buffer address of a supervisor stack.
    *
    * @param exclude  whether or not to invert the filter criteria.
    * @param from     the min supervisor stack buffer address value, inclusive.
    * @param to       the max supervisor stack buffer address value, exclusive.
    */
   public void filterSupervisorAddress(boolean exclude, int from, int to)
   {
      if (exclude)
      {
         tags.add(new MonitorTag(Monitor.MONITOR_STACK_FILTER_TAG_NOT));
      }
      tags.add(new MonitorTag(Monitor.MONITOR_STACK_FILTER_TAG_SUPERVISOR_ADDRESS,
            new Integer[] {new Integer(from), new Integer(to)}));
   }

   /**
    * Add a filter criteria for the peak usage of a supervisor stack.
    *
    * @param exclude  whether or not to invert the filter criteria.
    * @param from     the min supervisor stack peak usage value, inclusive.
    * @param to       the max supervisor stack peak usage value, exclusive.
    */
   public void filterSupervisorUsed(boolean exclude, int from, int to)
   {
      if (exclude)
      {
         tags.add(new MonitorTag(Monitor.MONITOR_STACK_FILTER_TAG_NOT));
      }
      tags.add(new MonitorTag(Monitor.MONITOR_STACK_FILTER_TAG_SUPERVISOR_USED,
            new Integer[] {new Integer(from), new Integer(to)}));
   }

   /**
    * Add a filter criteria for the unused size (size - used) of a supervisor
    * stack.
    *
    * @param exclude  whether or not to invert the filter criteria.
    * @param from     the min unused supervisor stack size value, inclusive.
    * @param to       the max unused supervisor stack size value, exclusive.
    */
   public void filterSupervisorUnused(boolean exclude, int from, int to)
   {
      if (exclude)
      {
         tags.add(new MonitorTag(Monitor.MONITOR_STACK_FILTER_TAG_NOT));
      }
      tags.add(new MonitorTag(Monitor.MONITOR_STACK_FILTER_TAG_SUPERVISOR_UNUSED,
            new Integer[] {new Integer(from), new Integer(to)}));
   }

   /**
    * Return an array of the OSE monitor protocol tags constituting this stack
    * filter.
    *
    * @return an array of the OSE monitor protocol tags constituting this stack
    * filter.
    */
   MonitorTag[] getTags()
   {
      return (MonitorTag[]) tags.toArray(new MonitorTag[0]);
   }

   /**
    * Return a string representation of this stack filter object.
    *
    * @return a string representation of this stack filter object.
    * @see java.lang.Object#toString()
    */
   public String toString()
   {
      StringBuffer sb = new StringBuffer();
      MonitorTag lastTag = null;

      for (Iterator i = tags.iterator(); i.hasNext();)
      {
         MonitorTag tag = (MonitorTag) i.next();

         if (lastTag == null)
         {
            lastTag = tag;
         }

         switch (tag.getType())
         {
            case Monitor.MONITOR_STACK_FILTER_TAG_NOT:
               break;
            case Monitor.MONITOR_STACK_FILTER_TAG_OWNER_ID:
               sb.append("Owner ID:\n");
               appendHexIntArg(sb, tag, lastTag);
               break;
            case Monitor.MONITOR_STACK_FILTER_TAG_OWNER_NAME:
               sb.append("Owner Name:\n");
               appendStringArg(sb, tag, lastTag);
               break;
            case Monitor.MONITOR_STACK_FILTER_TAG_SIZE:
               sb.append("Size:\n");
               appendUnsignedIntArgs(sb, tag, lastTag);
               break;
            case Monitor.MONITOR_STACK_FILTER_TAG_BUFSIZE:
               sb.append("Buffer Size:\n");
               appendUnsignedIntArgs(sb, tag, lastTag);
               break;
            case Monitor.MONITOR_STACK_FILTER_TAG_ADDRESS:
               sb.append("Address:\n");
               appendHexIntArgs(sb, tag, lastTag);
               break;
            case Monitor.MONITOR_STACK_FILTER_TAG_USED:
               sb.append("Used:\n");
               appendUnsignedIntArgs(sb, tag, lastTag);
               break;
            case Monitor.MONITOR_STACK_FILTER_TAG_UNUSED:
               sb.append("Unused:\n");
               appendUnsignedIntArgs(sb, tag, lastTag);
               break;
            default:
               sb.append("<Unknown stack filter tag>\n");
               break;
         }

         lastTag = tag;
      }

      return sb.toString();
   }

   private static void appendStringArg(StringBuffer sb,
                                       MonitorTag tag,
                                       MonitorTag lastTag)
   {
      if (lastTag.getType() == Monitor.MONITOR_STACK_FILTER_TAG_NOT)
      {
         sb.append("NOT ");
      }
      sb.append(tag.getArgs());
      sb.append("\n");
   }

   private static void appendHexIntArg(StringBuffer sb,
                                       MonitorTag tag,
                                       MonitorTag lastTag)
   {
      if (lastTag.getType() == Monitor.MONITOR_STACK_FILTER_TAG_NOT)
      {
         sb.append("NOT ");
      }
      sb.append("0x");
      sb.append(Integer.toHexString(((Integer) tag.getArgs()).intValue()).toUpperCase());
      sb.append("\n");
   }

   private static void appendHexIntArgs(StringBuffer sb,
                                        MonitorTag tag,
                                        MonitorTag lastTag)
   {
      Object[] args = (Object[]) tag.getArgs();
      String arg1 = Integer.toHexString(((Integer) args[0]).intValue()).toUpperCase();
      String arg2 = Integer.toHexString(((Integer) args[1]).intValue()).toUpperCase();

      if (lastTag.getType() == Monitor.MONITOR_STACK_FILTER_TAG_NOT)
      {
         sb.append("NOT ");
      }
      sb.append("0x");
      sb.append(arg1);
      sb.append(" - ");
      sb.append("0x");
      sb.append(arg2);
      sb.append("\n");
   }

   private static void appendUnsignedIntArgs(StringBuffer sb,
                                             MonitorTag tag,
                                             MonitorTag lastTag)
   {
      Object[] args = (Object[]) tag.getArgs();
      long arg1 = 0xFFFFFFFFL & ((Integer) args[0]).intValue();
      long arg2 = 0xFFFFFFFFL & ((Integer) args[1]).intValue();

      if (lastTag.getType() == Monitor.MONITOR_STACK_FILTER_TAG_NOT)
      {
         sb.append("NOT ");
      }
      sb.append(arg1);
      sb.append(" - ");
      sb.append(arg2);
      sb.append("\n");
   }
}
