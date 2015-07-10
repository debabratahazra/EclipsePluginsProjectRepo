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
 * This class is a filter used when filtering OSE signals.
 *
 * @see com.ose.system.Pool#getFilteredPoolSignals(IProgressMonitor, SignalFilter)
 */
public class SignalFilter
{
   private final List tags;

   /**
    * Create a new empty signal filter object.
    */
   public SignalFilter()
   {
      tags = new ArrayList();
   }

   /**
    * Add a filter criteria for the signal number of a signal.
    *
    * @param exclude  whether or not to invert the filter criteria.
    * @param from     the min signal number value, inclusive.
    * @param to       the max signal number value, exclusive.
    */
   public void filterNumber(boolean exclude, int from, int to)
   {
      if (exclude)
      {
         tags.add(new MonitorTag(Monitor.MONITOR_SIGNAL_FILTER_TAG_NOT));
      }
      tags.add(new MonitorTag(Monitor.MONITOR_SIGNAL_FILTER_TAG_NUMBER,
            new Integer[] {new Integer(from), new Integer(to)}));
   }

   /**
    * Add a filter criteria for the owner process ID of a signal.
    *
    * @param exclude  whether or not to invert the filter criteria.
    * @param ownerId  the owner process ID value.
    */
   public void filterOwnerId(boolean exclude, int ownerId)
   {
      if (exclude)
      {
         tags.add(new MonitorTag(Monitor.MONITOR_SIGNAL_FILTER_TAG_NOT));
      }
      tags.add(new MonitorTag(Monitor.MONITOR_SIGNAL_FILTER_TAG_OWNER_ID,
            new Integer(ownerId)));
   }

   /**
    * Add a filter criteria for the owner process name of a signal.
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
         tags.add(new MonitorTag(Monitor.MONITOR_SIGNAL_FILTER_TAG_NOT));
      }
      tags.add(new MonitorTag(Monitor.MONITOR_SIGNAL_FILTER_TAG_OWNER_NAME,
            ownerName));
   }

   /**
    * Add a filter criteria for the sender process ID of a signal.
    *
    * @param exclude   whether or not to invert the filter criteria.
    * @param senderId  the sender process ID value.
    */
   public void filterSenderId(boolean exclude, int senderId)
   {
      if (exclude)
      {
         tags.add(new MonitorTag(Monitor.MONITOR_SIGNAL_FILTER_TAG_NOT));
      }
      tags.add(new MonitorTag(Monitor.MONITOR_SIGNAL_FILTER_TAG_SENDER_ID,
            new Integer(senderId)));
   }

   /**
    * Add a filter criteria for the sender process name of a signal.
    * Accepts wildcards, where a '*' matches any string of zero or more
    * characters, and a '?' matches any single character.
    *
    * @param exclude     whether or not to invert the filter criteria.
    * @param senderName  the sender process name value.
    */
   public void filterSenderName(boolean exclude, String senderName)
   {
      if (exclude)
      {
         tags.add(new MonitorTag(Monitor.MONITOR_SIGNAL_FILTER_TAG_NOT));
      }
      tags.add(new MonitorTag(Monitor.MONITOR_SIGNAL_FILTER_TAG_SENDER_NAME,
            senderName));
   }

   /**
    * Add a filter criteria for the addressee process ID of a signal.
    *
    * @param exclude      whether or not to invert the filter criteria.
    * @param addresseeId  the addressee process ID value.
    */
   public void filterAddresseeId(boolean exclude, int addresseeId)
   {
      if (exclude)
      {
         tags.add(new MonitorTag(Monitor.MONITOR_SIGNAL_FILTER_TAG_NOT));
      }
      tags.add(new MonitorTag(Monitor.MONITOR_SIGNAL_FILTER_TAG_ADDRESSEE_ID,
            new Integer(addresseeId)));
   }

   /**
    * Add a filter criteria for the addressee process name of a signal.
    * Accepts wildcards, where a '*' matches any string of zero or more
    * characters, and a '?' matches any single character.
    *
    * @param exclude        whether or not to invert the filter criteria.
    * @param addresseeName  the addressee process name value.
    */
   public void filterAddresseeName(boolean exclude, String addresseeName)
   {
      if (exclude)
      {
         tags.add(new MonitorTag(Monitor.MONITOR_SIGNAL_FILTER_TAG_NOT));
      }
      tags.add(new MonitorTag(Monitor.MONITOR_SIGNAL_FILTER_TAG_ADDRESSEE_NAME,
            addresseeName));
   }

   /**
    * Add a filter criteria for the size of a signal.
    *
    * @param exclude  whether or not to invert the filter criteria.
    * @param from     the min signal size value, inclusive.
    * @param to       the max signal size value, exclusive.
    */
   public void filterSize(boolean exclude, int from, int to)
   {
      if (exclude)
      {
         tags.add(new MonitorTag(Monitor.MONITOR_SIGNAL_FILTER_TAG_NOT));
      }
      tags.add(new MonitorTag(Monitor.MONITOR_SIGNAL_FILTER_TAG_SIZE,
            new Integer[] {new Integer(from), new Integer(to)}));
   }

   /**
    * Add a filter criteria for the actual buffer size of a signal.
    *
    * @param exclude  whether or not to invert the filter criteria.
    * @param from     the min actual signal buffer size value, inclusive.
    * @param to       the max actual signal buffer size value, exclusive.
    */
   public void filterBufferSize(boolean exclude, int from, int to)
   {
      if (exclude)
      {
         tags.add(new MonitorTag(Monitor.MONITOR_SIGNAL_FILTER_TAG_NOT));
      }
      tags.add(new MonitorTag(Monitor.MONITOR_SIGNAL_FILTER_TAG_BUFSIZE,
            new Integer[] {new Integer(from), new Integer(to)}));
   }

   /**
    * Add a filter criteria for the buffer address of a signal.
    *
    * @param exclude  whether or not to invert the filter criteria.
    * @param from     the min signal buffer address value, inclusive.
    * @param to       the max signal buffer address value, exclusive.
    */
   public void filterAddress(boolean exclude, int from, int to)
   {
      if (exclude)
      {
         tags.add(new MonitorTag(Monitor.MONITOR_SIGNAL_FILTER_TAG_NOT));
      }
      tags.add(new MonitorTag(Monitor.MONITOR_SIGNAL_FILTER_TAG_ADDRESS,
            new Integer[] {new Integer(from), new Integer(to)}));
   }

   /**
    * Add a filter criteria for the status of a signal, i.e. one of the
    * SignalInfo.STATUS_* constants.
    *
    * @param exclude  whether or not to invert the filter criteria.
    * @param status   the signal status value.
    * @see SignalInfo#STATUS_UNKNOWN
    * @see SignalInfo#STATUS_VALID
    * @see SignalInfo#STATUS_ENDMARK
    * @see SignalInfo#STATUS_ADMINISTRATION
    * @see SignalInfo#STATUS_NOT_A_SIGNAL
    * @see SignalInfo#STATUS_NOT_A_POOL
    * @see SignalInfo#STATUS_FREE
    */
   public void filterStatus(boolean exclude, int status)
   {
      if (exclude)
      {
         tags.add(new MonitorTag(Monitor.MONITOR_SIGNAL_FILTER_TAG_NOT));
      }
      tags.add(new MonitorTag(Monitor.MONITOR_SIGNAL_FILTER_TAG_STATUS,
            new Integer(status)));
   }

   /**
    * Return an array of the OSE monitor protocol tags constituting this signal
    * filter.
    *
    * @return an array of the OSE monitor protocol tags constituting this signal
    * filter.
    */
   MonitorTag[] getTags()
   {
      return (MonitorTag[]) tags.toArray(new MonitorTag[0]);
   }

   /**
    * Return a string representation of this signal filter object.
    *
    * @return a string representation of this signal filter object.
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
            case Monitor.MONITOR_SIGNAL_FILTER_TAG_NOT:
               break;
            case Monitor.MONITOR_SIGNAL_FILTER_TAG_NUMBER:
               sb.append("Signal Number:\n");
               appendHexIntArgs(sb, tag, lastTag);
               break;
            case Monitor.MONITOR_SIGNAL_FILTER_TAG_OWNER_ID:
               sb.append("Owner ID:\n");
               appendHexIntArg(sb, tag, lastTag);
               break;
            case Monitor.MONITOR_SIGNAL_FILTER_TAG_OWNER_NAME:
               sb.append("Owner Name:\n");
               appendStringArg(sb, tag, lastTag);
               break;
            case Monitor.MONITOR_SIGNAL_FILTER_TAG_SENDER_ID:
               sb.append("Sender ID:\n");
               appendHexIntArg(sb, tag, lastTag);
               break;
            case Monitor.MONITOR_SIGNAL_FILTER_TAG_SENDER_NAME:
               sb.append("Sender Name:\n");
               appendStringArg(sb, tag, lastTag);
               break;
            case Monitor.MONITOR_SIGNAL_FILTER_TAG_ADDRESSEE_ID:
               sb.append("Addressee ID:\n");
               appendHexIntArg(sb, tag, lastTag);
               break;
            case Monitor.MONITOR_SIGNAL_FILTER_TAG_ADDRESSEE_NAME:
               sb.append("Addressee Name:\n");
               appendStringArg(sb, tag, lastTag);
               break;
            case Monitor.MONITOR_SIGNAL_FILTER_TAG_SIZE:
               sb.append("Size:\n");
               appendUnsignedIntArgs(sb, tag, lastTag);
               break;
            case Monitor.MONITOR_SIGNAL_FILTER_TAG_BUFSIZE:
               sb.append("Buffer Size:\n");
               appendUnsignedIntArgs(sb, tag, lastTag);
               break;
            case Monitor.MONITOR_SIGNAL_FILTER_TAG_ADDRESS:
               sb.append("Address:\n");
               appendHexIntArgs(sb, tag, lastTag);
               break;
            case Monitor.MONITOR_SIGNAL_FILTER_TAG_STATUS:
               int status = ((Integer) tag.getArgs()).intValue();
               sb.append("Status:\n");
               if (lastTag.getType() == Monitor.MONITOR_SIGNAL_FILTER_TAG_NOT)
               {
                  sb.append("NOT ");
               }
               switch (status)
               {
                  case Monitor.MONITOR_SIGNAL_STATUS_VALID:
                     sb.append("Valid\n");
                     break;
                  case Monitor.MONITOR_SIGNAL_STATUS_ENDMARK:
                     sb.append("Broken Endmark\n");
                     break;
                  case Monitor.MONITOR_SIGNAL_STATUS_ADMINISTRATION:
                     sb.append("Broken Admin Block\n");
                     break;
                  case Monitor.MONITOR_SIGNAL_STATUS_NOT_A_SIGNAL:
                     sb.append("Not a Signal\n");
                     break;
                  case Monitor.MONITOR_SIGNAL_STATUS_NOT_A_POOL:
                     sb.append("Not in a Pool\n");
                     break;
                  case Monitor.MONITOR_SIGNAL_STATUS_FREE:
                     sb.append("Free\n");
                     break;
                  case Monitor.MONITOR_SIGNAL_STATUS_UNKNOWN:
                     // Fall through.
                  default:
                     sb.append("Unknown\n");
                     break;
               }
               break;
            default:
               sb.append("<Unknown signal filter tag>\n");
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
      if (lastTag.getType() == Monitor.MONITOR_SIGNAL_FILTER_TAG_NOT)
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
      if (lastTag.getType() == Monitor.MONITOR_SIGNAL_FILTER_TAG_NOT)
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

      if (lastTag.getType() == Monitor.MONITOR_SIGNAL_FILTER_TAG_NOT)
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

      if (lastTag.getType() == Monitor.MONITOR_SIGNAL_FILTER_TAG_NOT)
      {
         sb.append("NOT ");
      }
      sb.append(arg1);
      sb.append(" - ");
      sb.append(arg2);
      sb.append("\n");
   }
}
