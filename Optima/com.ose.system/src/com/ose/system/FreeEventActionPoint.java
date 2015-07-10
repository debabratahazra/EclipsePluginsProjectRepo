/* COPYRIGHT-ENEA-SRC-R1 *
 **************************************************************************
 * Copyright (C) 2007 by Enea Software AB.
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

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import com.ose.system.service.monitor.Monitor;
import com.ose.system.service.monitor.MonitorTag;

/**
 * This class represents an OSE free event action point. A free event action
 * point defines what kind of action should be taken when a free event occurs.
 *
 * @see com.ose.system.EventActionPoint
 * @see com.ose.system.FreeEvent
 */
public final class FreeEventActionPoint extends EventActionPoint
{
   private volatile boolean useDataFilter;
   
   private volatile boolean filterDataRangeInverted;

   private volatile long filterDataMin;

   private volatile long filterDataMax;

   private volatile int filterDataSize;

   private volatile long filterDataOffset;

   /**
    * Create a new free event action point object.
    */
   public FreeEventActionPoint()
   {
      super();
      useDataFilter = false;
      filterDataOffset = 0;
      filterDataSize = 1;
      filterDataRangeInverted = false;
      filterDataMin = 0;
      filterDataMax = 0xFFFFFFFFFFFFFFFFL;
   }

   /**
    * Create a new free event action point object.
    *
    * @param state  the state this event action point is part of. Set to 0 to
    * indicate that an action is active in all event states.
    * @param fromScopeType  the from scope type of this event action point, i.e.
    * one of the EventActionPoint.SCOPE_* constants. The set of processes to
    * match as freeing processes.
    * @param fromScopeId  the from scope id of this event action point, if
    * fromScopeType is not EventActionPoint.SCOPE_NAME_PATTERN.
    * @param fromNamePattern  the from scope name pattern of this event action
    * point, if fromScopeType is EventActionPoint.SCOPE_NAME_PATTERN.
    * @param interceptScopeType  the intercept scope type of this event action
    * point, i.e. one of the EventActionPoint.SCOPE_* constants. Only applicable
    * for ACTION_INTERCEPT actions; defines the set of processes to intercept.
    * @param interceptScopeId  the intercept scope id of this event action
    * point, if interceptScopeType is not EventActionPoint.SCOPE_NAME_PATTERN.
    * @param interceptNamePattern  the intercept scope name pattern of this
    * event action point, if interceptScopeType is
    * EventActionPoint.SCOPE_NAME_PATTERN.
    * @param not  set to false to perform the action if an event matches or true
    * to not perform the action if an event matches, even if other events
    * indicate that this action should be performed.
    * @param action  the action to take when an event occurs, i.e. one of the
    * EventActionPoint.ACTION_* constants.
    * @param fileAndLineIncluded  For ACTION_NOTIFY, ACTION_INTERCEPT, and
    * ACTION_TRACE actions, determine whether filename and line number
    * information will be included in events or not.
    * @param newState  For ACTION_SET_STATE actions, this is the new event state
    * to set.
    * @param numIgnore  the number of times an event is allowed to occur before
    * the action is performed.
    * @param numRemove  the number of times an event is allowed to occur before
    * the action is automatically removed.
    * @param useDataFilter Use Data filter or not.
    * @param filterDataOffset  Offset of the number in the data.
    * @param filterDataSize  Size of the number.
    * @param filterDataRangeInverted  true if the number range specified with
    * filterDataMin and filterDataMax is inverted, false otherwise.
    * @param filterDataMin  the minimal number (inclusive)
    * @param filterDataMax  the maximal number (exclusive).
    */
   public FreeEventActionPoint(int state,
                               int fromScopeType,
                               int fromScopeId,
                               String fromNamePattern,
                               int interceptScopeType,
                               int interceptScopeId,
                               String interceptNamePattern,
                               boolean not,
                               int action,
                               boolean fileAndLineIncluded,
                               int newState,
                               int numIgnore,
                               int numRemove,
                               boolean useSignalFilter,
                               long filterDataOffset,
                               int filterDataSize,
                               boolean filterDataRangeInverted,
                               long filterDataMin,
                               long filterDataMax)
   {
      super(state,
            fromScopeType,
            fromScopeId,
            fromNamePattern,
            0,
            0,
            null,
            interceptScopeType,
            interceptScopeId,
            interceptNamePattern,
            not,
            action,
            createParameter(action, fileAndLineIncluded, newState),
            numIgnore,
            numRemove);
      this.useDataFilter = useSignalFilter;
      this.filterDataOffset = filterDataOffset;
      this.filterDataSize = filterDataSize;
      this.filterDataRangeInverted = filterDataRangeInverted;
      this.filterDataMin = filterDataMin;
      this.filterDataMax = filterDataMax;
   }

   /**
    * Create a new free event action point object.
    *
    * @param state          the event state.
    * @param fromType       the from scope type.
    * @param fromId         the from scope id.
    * @param toType         the to scope type.
    * @param toId           the to scope id.
    * @param not            the not flag.
    * @param action         the action.
    * @param interceptType  the intercept scope type.
    * @param interceptId    the intercept scope id
    * @param parameter      the action parameter.
    * @param client         the client pid.
    * @param id             the event action point id.
    * @param count          the event count.
    * @param tags           the OSE monitor protocol tags for this event action
    * point.
    * @param bigEndian      the target byte order.
    */
   FreeEventActionPoint(int state,
                        int fromType,
                        int fromId,
                        int toType,
                        int toId,
                        boolean not,
                        int action,
                        int interceptType,
                        int interceptId,
                        int parameter,
                        int client,
                        int id,
                        int count,
                        MonitorTag[] tags,
                        boolean bigEndian)
   {
      super(state,
            fromType,
            fromId,
            toType,
            toId,
            not,
            action,
            interceptType,
            interceptId,
            parameter,
            client,
            id,
            count,
            tags,
            bigEndian);
      boolean tmpUseSignalFilter = false;
      long tmpFilterDataOffset = 0;
      int tmpFilterDataSize = 1;
      boolean tmpFilterDataRangeInverted = false;
      long tmpFilterDataMin = 0;
      long tmpFilterDataMax = 0xFFFFFFFFFFFFFFFFL;

      if (tags != null)
      {
         for (int i = 0; i < tags.length; i++)
         {
            MonitorTag tag;
            short type;
            byte[] args;
            ByteBuffer buffer = null;

            tag = tags[i];
            type = tag.getType();
            args = (byte[]) tag.getArgs();

            if (args != null)
            {
               buffer = ByteBuffer.wrap(args);
               buffer.order(bigEndian ? ByteOrder.BIG_ENDIAN
                     : ByteOrder.LITTLE_ENDIAN);
            }
            else if (type != Monitor.MONITOR_SIGNAL_FILTER_TAG_NOT)
            {
               // Ignore and skip invalid tags.
               continue;
            }

            try
            {
               switch (type)
               {
                  case Monitor.MONITOR_EVENT_ACTIONPOINT_TAG_DATA_RANGE_NOT:
                     tmpUseSignalFilter = true;
                     tmpFilterDataRangeInverted = true;
                     tmpFilterDataOffset = buffer.getLong();
                     tmpFilterDataSize = buffer.getInt();
                     tmpFilterDataMin = buffer.getLong();
                     tmpFilterDataMax = buffer.getLong();
                     break;
                  case Monitor.MONITOR_EVENT_ACTIONPOINT_TAG_DATA_RANGE:
                     tmpUseSignalFilter = true;
                     tmpFilterDataOffset = buffer.getLong();
                     tmpFilterDataSize = buffer.getInt();
                     tmpFilterDataMin = buffer.getLong();
                     tmpFilterDataMax = buffer.getLong();
                     break;
                  default:
                     // Ignore and skip invalid tags.
                     break;
               }
            }
            catch (Exception e)
            {
               throw new IllegalArgumentException(e.getMessage());
            }
         }
      }

      useDataFilter = tmpUseSignalFilter;
      filterDataOffset = tmpFilterDataOffset;
      filterDataSize = tmpFilterDataSize;
      filterDataRangeInverted = tmpFilterDataRangeInverted;
      filterDataMin = tmpFilterDataMin;
      filterDataMax = tmpFilterDataMax;
   }

   /**
    * Determine whether use the data filter or not.
    * 
    * @return true if usage of the data filter data , false otherwise.
    */
   public boolean isUseSignalFilter()
   {
      return useDataFilter;
   }

   /**
    * Set to use the data filter  or not.
    * 
    * @param useSignalFilter
    *           true if usage of the data filter , false otherwise.
    */
   public void setUseSignalFilter(boolean useSignalFilter)
   {
      this.useDataFilter = useSignalFilter;
   }

   /**
    * Determine whether the filter data number range is inverted or not.
    * 
    * @return true if the filter data number range is inverted, false otherwise.
    */
   public boolean isFilterDataRangeInverted()
   {
      return filterDataRangeInverted;
   }

   /**
    * Set whether the filter data number range is inverted or not.
    * 
    * @param filterDataRangeInverted
    *           true if the filter data number range is inverted, false
    *           otherwise.
    */
   public void setFilterDataRangeInverted(boolean filterDataRangeInverted)
   {
      this.filterDataRangeInverted = filterDataRangeInverted;
   }

   /**
    * Return the minimal filter data range number (inclusive). Only signals with
    * number in the data within the getFilterDataMin() and getFilterDataMax()
    * range are reported.
    * 
    * @return the minimal filter data range number (inclusive).
    */
   public long getFilterDataMin()
   {
      return filterDataMin;
   }

   /**
    * Set the minimal filter data range number (inclusive). Only signals with
    * number in the data within the getFilterDataMin() and getFilterDataMax()
    * range are reported.
    * 
    * @param filterDataMin
    *           the minimal filter data range number (inclusive).
    */
   public void setFilterDataMin(long filterDataMin)
   {
      this.filterDataMin = filterDataMin;
   }

   /**
    * Return the maximal filter data range number (exclusive). Only signals with
    * number in the data within the getFilterDataMin() and getFilterDataMax()
    * range are reported.
    * 
    * @return the maximal filter data range number (exclusive).
    */
   public long getFilterDataMax()
   {
      return filterDataMax;
   }

   /**
    * Set the maximal filter data range number (exclusive). Only signals with
    * number in the data within the getFilterDataMin() and getFilterDataMax()
    * range are reported.
    * 
    * @param filterDataMax
    *           the maximal filter data range number (exclusive).
    */
   public void setFilterDataMax(long filterDataMax)
   {
      this.filterDataMax = filterDataMax;
   }

   /**
    * Return the size of the number for signal data filter.
    * 
    * @return the size of number of the signal data filter.
    */
   public int getFilterDataSize()
   {
      return filterDataSize;
   }

   /**
    * Set the size of the number for signal data filter.
    * 
    * @param filterDataSize
    *           the size of the number for signal data filter.
    */
   public void setFilterDataSize(int filterDataSize)
   {
      this.filterDataSize = filterDataSize;
   }

   /**
    * Return the offset of the number within the signal data.
    * 
    * @return the offset of the number within the signal data.
    */
   public long getFilterDataOffset()
   {
      return filterDataOffset;
   }

   /**
    * Set the offset of the number within the signal data.
    * 
    * @param filterDataOffset
    *           the offset of the number within the signal data.
    */
   public void setFilterDataOffset(long filterDataOffset)
   {
      this.filterDataOffset = filterDataOffset;
   }

   /**
    * Return an array of the OSE monitor protocol tags for this receive event
    * action point.
    * 
    * @param  bigEndian      the target byte order.
    * 
    * @return an array of the OSE monitor protocol tags for this receive event
    *         action point.
    */
   MonitorTag[] getTags(boolean bigEndian)
   {
      List tags = new ArrayList(Arrays.asList(super.getTags()));
      
      if (useDataFilter)
      {
         List<Integer> values = new ArrayList<Integer>();
         values.addAll(Arrays.asList(convertLongToIntegers(filterDataOffset,
               bigEndian)));
         values.add(new Integer(filterDataSize));
         values.addAll(Arrays.asList(convertLongToIntegers(filterDataMin,
               bigEndian)));
         values.addAll(Arrays.asList(convertLongToIntegers(filterDataMax,
               bigEndian)));
         if (filterDataRangeInverted)
         {
            tags.add(new MonitorTag(
                  Monitor.MONITOR_EVENT_ACTIONPOINT_TAG_DATA_RANGE_NOT, values
                        .toArray(new Integer[0])));
         }
         else
         {
            tags.add(new MonitorTag(
                  Monitor.MONITOR_EVENT_ACTIONPOINT_TAG_DATA_RANGE, values
                        .toArray(new Integer[0])));
         }
      }
      return (MonitorTag[]) tags.toArray(new MonitorTag[0]);
   }

   /**
    * Return a string representation of this receive event action point object.
    * 
    * @return a string representation of this receive event action point object.
    * @see java.lang.Object#toString()
    */
   public String toString()
   {
      return (super.toString() +
            ", Use Data Filter:" + useDataFilter +
            ", Filter data offset: " + filterDataOffset +
            ", Filter data size: " + filterDataSize +
            ", Signal filter data range inverted: " + filterDataRangeInverted + 
            ", Min filter data number: " + filterDataMin + 
            ", Max filter data number: " + filterDataMax);
   }
}
