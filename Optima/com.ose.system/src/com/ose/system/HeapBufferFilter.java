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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import com.ose.system.service.monitor.Monitor;
import com.ose.system.service.monitor.MonitorTag;

/**
 * This class is a filter used when filtering OSE heap buffers.
 *
 * @see com.ose.system.Heap#getFilteredHeapBuffers(IProgressMonitor, HeapBufferFilter)
 */
public class HeapBufferFilter
{
   private final List tags;

   /**
    * Create a new empty heap buffer filter object.
    */
   public HeapBufferFilter()
   {
      tags = new ArrayList();
   }

   /**
    * Add a filter criteria for the owner process ID of a private heap buffer.
    *
    * @param exclude  whether or not to invert the filter criteria.
    * @param ownerId  the owner process ID value.
    */
   public void filterOwnerId(boolean exclude, int ownerId)
   {
      if (exclude)
      {
         tags.add(new MonitorTag(Monitor.MONITOR_HEAP_BUFFER_FILTER_TAG_NOT));
      }
      tags.add(new MonitorTag(Monitor.MONITOR_HEAP_BUFFER_FILTER_TAG_OWNER_ID,
            new Integer(ownerId)));
   }

   /**
    * Add a filter criteria for the owner process name of a private heap buffer.
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
         tags.add(new MonitorTag(Monitor.MONITOR_HEAP_BUFFER_FILTER_TAG_NOT));
      }
      tags.add(new MonitorTag(Monitor.MONITOR_HEAP_BUFFER_FILTER_TAG_OWNER_NAME,
            ownerName));
   }

   /**
    * Add a filter criteria for the shared mode property of a heap buffer.
    *
    * @param exclude  whether or not to invert the filter criteria.
    * @param shared   the shared mode value.
    */
   public void filterShared(boolean exclude, boolean shared)
   {
      if (exclude)
      {
         tags.add(new MonitorTag(Monitor.MONITOR_HEAP_BUFFER_FILTER_TAG_NOT));
      }
      tags.add(new MonitorTag(Monitor.MONITOR_HEAP_BUFFER_FILTER_TAG_SHARED,
            new Integer(shared ? 1 : 0)));
   }

   /**
    * Add a filter criteria for the address of a heap buffer.
    *
    * @param exclude  whether or not to invert the filter criteria.
    * @param from     the min heap buffer address value, inclusive.
    * @param to       the max heap buffer address value, exclusive.
    */
   public void filterAddress(boolean exclude, int from, int to)
   {
      if (exclude)
      {
         tags.add(new MonitorTag(Monitor.MONITOR_HEAP_BUFFER_FILTER_TAG_NOT));
      }
      tags.add(new MonitorTag(Monitor.MONITOR_HEAP_BUFFER_FILTER_TAG_ADDRESS,
            new Integer[] {new Integer(from), new Integer(to)}));
   }

   /**
    * Add a filter criteria for the actual size of a heap buffer.
    *
    * @param exclude  whether or not to invert the filter criteria.
    * @param from     the min heap buffer actual size value, inclusive.
    * @param to       the max heap buffer actual size value, exclusive.
    */
   public void filterSize(boolean exclude, int from, int to)
   {
      if (exclude)
      {
         tags.add(new MonitorTag(Monitor.MONITOR_HEAP_BUFFER_FILTER_TAG_NOT));
      }
      tags.add(new MonitorTag(Monitor.MONITOR_HEAP_BUFFER_FILTER_TAG_SIZE,
            new Integer[] {new Integer(from), new Integer(to)}));
   }

   /**
    * Add a filter criteria for the requested size of a heap buffer.
    *
    * @param exclude  whether or not to invert the filter criteria.
    * @param from     the min heap buffer requested size value, inclusive.
    * @param to       the max heap buffer requested size value, exclusive.
    */
   public void filterRequestedSize(boolean exclude, int from, int to)
   {
      if (exclude)
      {
         tags.add(new MonitorTag(Monitor.MONITOR_HEAP_BUFFER_FILTER_TAG_NOT));
      }
      tags.add(new MonitorTag(Monitor.MONITOR_HEAP_BUFFER_FILTER_TAG_REQ_SIZE,
            new Integer[] {new Integer(from), new Integer(to)}));
   }

   /**
    * Add a filter criteria for the name of the source file where the allocation
    * of a heap buffer took place. Accepts wildcards, where a '*' matches any
    * string of zero or more characters, and a '?' matches any single character.
    *
    * @param exclude   whether or not to invert the filter criteria.
    * @param fileName  the file name value.
    */
   public void filterFileName(boolean exclude, String fileName)
   {
      if (exclude)
      {
         tags.add(new MonitorTag(Monitor.MONITOR_HEAP_BUFFER_FILTER_TAG_NOT));
      }
      tags.add(new MonitorTag(Monitor.MONITOR_HEAP_BUFFER_FILTER_TAG_FILE_NAME,
            fileName));
   }

   /**
    * Add a filter criteria for the line number in the source file where the
    * allocation of a heap buffer took place.
    *
    * @param exclude  whether or not to invert the filter criteria.
    * @param from     the min line number value, inclusive.
    * @param to       the max line number value, exclusive.
    */
   public void filterLineNumber(boolean exclude, int from, int to)
   {
      if (exclude)
      {
         tags.add(new MonitorTag(Monitor.MONITOR_HEAP_BUFFER_FILTER_TAG_NOT));
      }
      tags.add(new MonitorTag(Monitor.MONITOR_HEAP_BUFFER_FILTER_TAG_LINE_NUMBER,
            new Integer[] {new Integer(from), new Integer(to)}));
   }

   /**
    * Add a filter criteria for the status of a heap buffer, i.e. one of the
    * HeapBufferInfo.STATUS_* constants.
    *
    * @param exclude  whether or not to invert the filter criteria.
    * @param status   the heap buffer status value.
    * @see HeapBufferInfo#STATUS_UNKNOWN
    * @see HeapBufferInfo#STATUS_VALID
    * @see HeapBufferInfo#STATUS_ENDMARK
    */
   public void filterStatus(boolean exclude, int status)
   {
      if (exclude)
      {
         tags.add(new MonitorTag(Monitor.MONITOR_HEAP_BUFFER_FILTER_TAG_NOT));
      }
      tags.add(new MonitorTag(Monitor.MONITOR_HEAP_BUFFER_FILTER_TAG_STATUS,
            new Integer(status)));
   }

   /**
    * Return an array of the OSE monitor protocol tags constituting this heap
    * buffer filter.
    *
    * @return an array of the OSE monitor protocol tags constituting this heap
    * buffer filter.
    */
   MonitorTag[] getTags()
   {
      return (MonitorTag[]) tags.toArray(new MonitorTag[0]);
   }

   /**
    * Return a string representation of this heap buffer filter object.
    *
    * @return a string representation of this heap buffer filter object.
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
            case Monitor.MONITOR_HEAP_BUFFER_FILTER_TAG_NOT:
               break;
            case Monitor.MONITOR_HEAP_BUFFER_FILTER_TAG_OWNER_ID:
               sb.append("Owner ID:\n");
               appendHexIntArg(sb, tag, lastTag);
               break;
            case Monitor.MONITOR_HEAP_BUFFER_FILTER_TAG_OWNER_NAME:
               sb.append("Owner Name:\n");
               appendStringArg(sb, tag, lastTag);
               break;
            case Monitor.MONITOR_HEAP_BUFFER_FILTER_TAG_SHARED:
               sb.append("Shared:\n");
               appendBooleanArg(sb, tag, lastTag);
               break;
            case Monitor.MONITOR_HEAP_BUFFER_FILTER_TAG_ADDRESS:
               sb.append("Address:\n");
               appendHexIntArgs(sb, tag, lastTag);
               break;
            case Monitor.MONITOR_HEAP_BUFFER_FILTER_TAG_SIZE:
               sb.append("Size:\n");
               appendUnsignedIntArgs(sb, tag, lastTag);
               break;
            case Monitor.MONITOR_HEAP_BUFFER_FILTER_TAG_REQ_SIZE:
               sb.append("Requested Size:\n");
               appendUnsignedIntArgs(sb, tag, lastTag);
               break;
            case Monitor.MONITOR_HEAP_BUFFER_FILTER_TAG_FILE_NAME:
               sb.append("File Name:\n");
               appendStringArg(sb, tag, lastTag);
               break;
            case Monitor.MONITOR_HEAP_BUFFER_FILTER_TAG_LINE_NUMBER:
               sb.append("Line Number:\n");
               appendUnsignedIntArgs(sb, tag, lastTag);
               break;
            case Monitor.MONITOR_HEAP_BUFFER_FILTER_TAG_STATUS:
               int status = ((Integer) tag.getArgs()).intValue();
               sb.append("Status:\n");
               if (lastTag.getType() == Monitor.MONITOR_HEAP_BUFFER_FILTER_TAG_NOT)
               {
                  sb.append("NOT ");
               }
               switch (status)
               {
                  case Monitor.MONITOR_HEAP_BUFFER_STATUS_VALID:
                     sb.append("Valid\n");
                     break;
                  case Monitor.MONITOR_HEAP_BUFFER_STATUS_ENDMARK:
                     sb.append("Broken Endmark\n");
                     break;
                  case Monitor.MONITOR_HEAP_BUFFER_STATUS_UNKNOWN:
                     // Fall through.
                  default:
                     sb.append("Unknown\n");
                     break;
               }
               break;
            default:
               sb.append("<Unknown heap buffer filter tag>\n");
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
      if (lastTag.getType() == Monitor.MONITOR_HEAP_BUFFER_FILTER_TAG_NOT)
      {
         sb.append("NOT ");
      }
      sb.append(tag.getArgs());
      sb.append("\n");
   }

   private static void appendBooleanArg(StringBuffer sb,
                                        MonitorTag tag,
                                        MonitorTag lastTag)
   {
      if (lastTag.getType() == Monitor.MONITOR_HEAP_BUFFER_FILTER_TAG_NOT)
      {
         sb.append("NOT ");
      }
      sb.append(((Integer) tag.getArgs()).intValue() != 0);
      sb.append("\n");
   }

   private static void appendHexIntArg(StringBuffer sb,
                                       MonitorTag tag,
                                       MonitorTag lastTag)
   {
      if (lastTag.getType() == Monitor.MONITOR_HEAP_BUFFER_FILTER_TAG_NOT)
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

      if (lastTag.getType() == Monitor.MONITOR_HEAP_BUFFER_FILTER_TAG_NOT)
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

      if (lastTag.getType() == Monitor.MONITOR_HEAP_BUFFER_FILTER_TAG_NOT)
      {
         sb.append("NOT ");
      }
      sb.append(arg1);
      sb.append(" - ");
      sb.append(arg2);
      sb.append("\n");
   }
}
