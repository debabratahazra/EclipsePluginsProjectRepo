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
 * This class represents an OSE error event action point. An error event action
 * point defines what kind of action should be taken when an error event occurs.
 *
 * @see com.ose.system.EventActionPoint
 * @see com.ose.system.ErrorEvent
 */
public final class ErrorEventActionPoint extends EventActionPoint
{
   private volatile boolean executive;
   private volatile boolean errorRangeInverted;
   private volatile int errorMin;
   private volatile int errorMax;
   private volatile boolean extraRangeInverted;
   private volatile int extraMin;
   private volatile int extraMax;

   /**
    * Create a new error event action point object.
    */
   public ErrorEventActionPoint()
   {
      super();
      this.executive = false;
      this.errorRangeInverted = false;
      this.errorMin = 0;
      this.errorMax = 0xFFFFFFFF;
      this.extraRangeInverted = false;
      this.extraMin = 0;
      this.extraMax = 0xFFFFFFFF;
   }

   /**
    * Create a new error event action point object.
    *
    * @param state  the state this event action point is part of. Set to 0 to
    * indicate that an action is active in all event states.
    * @param fromScopeType  the from scope type of this event action point, i.e.
    * one of the EventActionPoint.SCOPE_* constants. The set of processes to
    * match as error reporter processes.
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
    * @param executive  true if errors invoked by the executive should be
    * reported, false if errors invoked by user programs should be reported.
    * @param errorRangeInverted  true if the error code range specified with
    * errorMin and errorMax is inverted, false otherwise.
    * @param errorMin  the minimal error code (inclusive)
    * @param errorMax  the maximal error code (exclusive).
    * @param extraRangeInverted  true if the error code extra parameter range
    * specified with extraMin and extraMax is inverted, false otherwise.
    * @param extraMin  the minimal error code extra parameter (inclusive).
    * @param extraMax  the maximal error code extra parameter (exclusive).
    * @param numIgnore  the number of times an event is allowed to occur before
    * the action is performed.
    * @param numRemove  the number of times an event is allowed to occur before
    * the action is automatically removed.
    */
   public ErrorEventActionPoint(int state,
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
                                boolean executive,
                                boolean errorRangeInverted,
                                int errorMin,
                                int errorMax,
                                boolean extraRangeInverted,
                                int extraMin,
                                int extraMax,
                                int numIgnore,
                                int numRemove)
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
      this.executive = executive;
      this.errorRangeInverted = errorRangeInverted;
      this.errorMin = errorMin;
      this.errorMax = errorMax;
      this.extraRangeInverted = extraRangeInverted;
      this.extraMin = extraMin;
      this.extraMax = extraMax;
   }

   /**
    * Create a new error event action point object.
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
   ErrorEventActionPoint(int state,
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

      boolean tmpExecutive = false;
      boolean tmpErrorRangeInverted = false;
      int tmpErrorMin = 0;
      int tmpErrorMax = 0xFFFFFFFF;
      boolean tmpExtraRangeInverted = false;
      int tmpExtraMin = 0;
      int tmpExtraMax = 0xFFFFFFFF;

      if (tags != null)
      {
         boolean notTag = false;

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
               buffer.order(bigEndian ? ByteOrder.BIG_ENDIAN : ByteOrder.LITTLE_ENDIAN);
            }
            else if ((type != Monitor.MONITOR_ERROR_FILTER_TAG_NOT) &&
                     (type != Monitor.MONITOR_ERROR_FILTER_TAG_EXEC))
            {
               // Ignore and skip invalid tags.
               continue;
            }

            try
            {
               switch (type)
               {
                  case Monitor.MONITOR_ERROR_FILTER_TAG_NOT:
                     break;
                  case Monitor.MONITOR_ERROR_FILTER_TAG_EXEC:
                     tmpExecutive = !notTag;
                     break;
                  case Monitor.MONITOR_ERROR_FILTER_TAG_ERROR:
                     tmpExtraRangeInverted = notTag;
                     tmpErrorMin = buffer.getInt();
                     tmpErrorMax = buffer.getInt();
                     break;
                  case Monitor.MONITOR_ERROR_FILTER_TAG_EXTRA:
                     tmpExtraRangeInverted = notTag;
                     tmpExtraMin = buffer.getInt();
                     tmpExtraMax = buffer.getInt();
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

            notTag = (type == Monitor.MONITOR_ERROR_FILTER_TAG_NOT);
         }
      }

      executive = tmpExecutive;
      errorRangeInverted = tmpErrorRangeInverted;
      errorMin = tmpErrorMin;
      errorMax = tmpErrorMax;
      extraRangeInverted = tmpExtraRangeInverted;
      extraMin = tmpExtraMin;
      extraMax = tmpExtraMax;
   }

   /**
    * Determine whether to report errors invoked by the executive or not.
    *
    * @return true if errors invoked by the executive should be reported, false
    * if errors invoked by user programs should be reported.
    */
   public boolean isExecutive()
   {
      return executive;
   }

   /**
    * Set whether to report errors invoked by the executive or not.
    *
    * @param executive  true if errors invoked by the executive should be
    * reported, false if errors invoked by user programs should be reported.
    */
   public void setExecutive(boolean executive)
   {
      this.executive = executive;
   }

   /**
    * Determine whether the error code range is inverted or not.
    *
    * @return true if the error code range is inverted, false otherwise.
    */
   public boolean isErrorRangeInverted()
   {
      return errorRangeInverted;
   }

   /**
    * Set whether the error code range is inverted or not.
    *
    * @param errorRangeInverted  true if the error code range is inverted, false
    * otherwise.
    */
   public void setErrorRangeInverted(boolean errorRangeInverted)
   {
      this.errorRangeInverted = errorRangeInverted;
   }

   /**
    * Return the minimal error code (inclusive).
    * Only error codes within the getErrorMin() and getErrorMax() range are
    * reported.
    *
    * @return the minimal error code (inclusive).
    */
   public int getErrorMin()
   {
      return errorMin;
   }

   /**
    * Set the minimal error code (inclusive).
    * Only error codes within the getErrorMin() and getErrorMax() range are
    * reported.
    *
    * @param errorMin  the minimal error code (inclusive).
    */
   public void setErrorMin(int errorMin)
   {
      this.errorMin = errorMin;
   }

   /**
    * Return the maximal error code (exclusive).
    * Only error codes within the getErrorMin() and getErrorMax() range are
    * reported.
    *
    * @return the maximal error code (exclusive).
    */
   public int getErrorMax()
   {
      return errorMax;
   }

   /**
    * Set the maximal error code (exclusive).
    * Only error codes within the getErrorMin() and getErrorMax() range are
    * reported.
    *
    * @param errorMax  the maximal error code (exclusive).
    */
   public void setErrorMax(int errorMax)
   {
      this.errorMax = errorMax;
   }

   /**
    * Determine whether the error code extra parameter range is inverted or not.
    *
    * @return true if the error code extra parameter range is inverted, false
    * otherwise.
    */
   public boolean isExtraRangeInverted()
   {
      return extraRangeInverted;
   }

   /**
    * Set whether the error code extra parameter range is inverted or not.
    *
    * @param extraRangeInverted  true if the error code extra parameter range is
    * inverted, false otherwise.
    */
   public void setExtraRangeInverted(boolean extraRangeInverted)
   {
      this.extraRangeInverted = extraRangeInverted;
   }

   /**
    * Return the minimal error code extra parameter (inclusive).
    * Only error codes with extra parameters within the getExtraMin() and
    * getExtraMax() range are reported.
    *
    * @return the minimal error code extra parameter (inclusive).
    */
   public int getExtraMin()
   {
      return extraMin;
   }

   /**
    * Set the minimal error code extra parameter (inclusive).
    * Only error codes with extra parameters within the getExtraMin() and
    * getExtraMax() range are reported.
    *
    * @param extraMin  the minimal error code extra parameter (inclusive).
    */
   public void setExtraMin(int extraMin)
   {
      this.extraMin = extraMin;
   }

   /**
    * Return the maximal error code extra parameter (exclusive).
    * Only error codes with extra parameters within the getExtraMin() and
    * getExtraMax() range are reported.
    *
    * @return the maximal error code extra parameter (exclusive).
    */
   public int getExtraMax()
   {
      return extraMax;
   }

   /**
    * Set the maximal error code extra parameter (exclusive).
    * Only error codes with extra parameters within the getExtraMin() and
    * getExtraMax() range are reported.
    *
    * @param extraMax  the maximal error code extra parameter (exclusive).
    */
   public void setExtraMax(int extraMax)
   {
      this.extraMax = extraMax;
   }

   /**
    * Return an array of the OSE monitor protocol tags for this error event
    * action point.
    *
    * @return an array of the OSE monitor protocol tags for this error event
    * action point.
    */
   MonitorTag[] getTags()
   {
      List tags = new ArrayList(Arrays.asList(super.getTags()));

      if (executive)
      {
         tags.add(new MonitorTag(Monitor.MONITOR_ERROR_FILTER_TAG_EXEC));
      }

      if (errorRangeInverted)
      {
         tags.add(new MonitorTag(Monitor.MONITOR_ERROR_FILTER_TAG_NOT));
      }
      tags.add(new MonitorTag(Monitor.MONITOR_ERROR_FILTER_TAG_ERROR,
            new Integer[] {new Integer(errorMin), new Integer(errorMax)}));

      if (extraRangeInverted)
      {
         tags.add(new MonitorTag(Monitor.MONITOR_ERROR_FILTER_TAG_NOT));
      }
      tags.add(new MonitorTag(Monitor.MONITOR_ERROR_FILTER_TAG_EXTRA,
            new Integer[] {new Integer(extraMin), new Integer(extraMax)}));

      return (MonitorTag[]) tags.toArray(new MonitorTag[0]);
   }

   /**
    * Return a string representation of this error event action point object.
    *
    * @return a string representation of this error event action point object.
    * @see java.lang.Object#toString()
    */
   public String toString()
   {
      return (super.toString() +
              ", Invoked by executive: " + executive +
              ", Error code range inverted: " + errorRangeInverted +
              ", Min error code: " + errorMin +
              ", Max error code: " + errorMax +
              ", Extra parameter range inverted: " + extraRangeInverted +
              ", Min extra parameter: " + extraMin +
              ", Max extra parameter: " + extraMax);
   }
}
