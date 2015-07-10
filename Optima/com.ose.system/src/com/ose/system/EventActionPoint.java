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
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.util.ArrayList;
import java.util.List;
import com.ose.system.service.monitor.Monitor;
import com.ose.system.service.monitor.MonitorTag;

/**
 * This class represents an OSE event action point. An event action point
 * defines what kind of action should be taken when a certain type of event
 * occurs on an OSE target system.
 * <p>
 * EventActionPoint is the abstract super class of all concrete event action
 * points for the different types of events, e.g. SendEventActionPoint.
 * <p>
 * Note that a newly created EventActionPoint object does not take effect until
 * it has been set with Target.setEventActionPoint(), which will assign its id.
 * Changing the properties of an existing EventActionPoint object that has
 * previously been set with Target.setEventActionPoint() does not take effect
 * until it has first been removed with Target.clearEventActionPoint(), which
 * will reset its id to 0, and then set again with Target.setEventActionPoint(),
 * which will update its id.
 *
 * @see com.ose.system.Target#setEventActionPoint(IProgressMonitor, EventActionPoint)
 * @see com.ose.system.Target#getEventActionPoints(IProgressMonitor)
 */
public abstract class EventActionPoint
{
   /* Action types. */

   /** Report the event to registered event listeners. */
   public static final int ACTION_NOTIFY = Monitor.MONITOR_ACTION_NOTIFY;

   /**
    * Intercept all processes in the intercept scope and report the event to
    * registered event listeners.
    */
   public static final int ACTION_INTERCEPT = Monitor.MONITOR_ACTION_INTERCEPT;

   /** Trace the event, i.e. write it to the target's event trace buffer. */
   public static final int ACTION_TRACE = Monitor.MONITOR_ACTION_TRACE;

   /** Turn on the target's event trace facility. */
   public static final int ACTION_ENABLE_TRACE = Monitor.MONITOR_ACTION_ENABLE_TRACE;

   /** Turn off the target's event trace facility. */
   public static final int ACTION_DISABLE_TRACE = Monitor.MONITOR_ACTION_DISABLE_TRACE;

   /** Go to a new event action state. */
   public static final int ACTION_SET_STATE = Monitor.MONITOR_ACTION_SET_STATE;

   /** Not used. */
   public static final int ACTION_COUNT = Monitor.MONITOR_ACTION_COUNT;
   
   /** Defines a user action. */
   public static final int ACTION_USER = Monitor.MONITOR_ACTION_USER;

   /** Turn on the target's hardware trace facility. */
   public static final int ACTION_ENABLE_HW_TRACE = Monitor.MONITOR_ACTION_ENABLE_HW_TRACE;

   /** Turn off the target's hardware trace facility. */
   public static final int ACTION_DISABLE_HW_TRACE = Monitor.MONITOR_ACTION_DISABLE_HW_TRACE;

   /* Scope types. */

   /** Scope type for all processes in the target system. */
   public static final int SCOPE_SYSTEM = Monitor.MONITOR_SCOPE_SYSTEM;

   /** Scope type for all processes in the specified segment. */
   public static final int SCOPE_SEGMENT_ID = Monitor.MONITOR_SCOPE_SEGMENT_ID;

   /** Scope type for all processes in the specified block. */
   public static final int SCOPE_BLOCK_ID = Monitor.MONITOR_SCOPE_BLOCK_ID;

   /** Scope type for the specified process. */
   public static final int SCOPE_ID = Monitor.MONITOR_SCOPE_ID;

   /** Scope type for processes matching a specified name pattern. */
   public static final int SCOPE_NAME_PATTERN = Monitor.MONITOR_SCOPE_NAME_PATTERN;

   private final CharsetDecoder decoder = Charset.forName("ISO-8859-1").newDecoder();

   // Output properties.
   private volatile int id;
   private final int client;
   private final int count;

   // Input/output properties.
   private volatile int state;
   private volatile int fromScopeType;
   private volatile int fromScopeId;
   private volatile String fromNamePattern;
   private volatile int toScopeType;
   private volatile int toScopeId;
   private volatile String toNamePattern;
   private volatile int interceptScopeType;
   private volatile int interceptScopeId;
   private volatile String interceptNamePattern;
   private volatile boolean not;
   private volatile int action;
   private volatile int parameter;
   private volatile int numIgnore;
   private volatile int numRemove;

   /**
    * Create a new event action point object.
    */
   EventActionPoint()
   {
      this(0,
           SCOPE_SYSTEM,
           0,
           null,
           SCOPE_SYSTEM,
           0,
           null,
           SCOPE_SYSTEM,
           0,
           null,
           false,
           ACTION_NOTIFY,
           0,
           0,
           0);
   }

   /**
    * Create a new event action point object.
    *
    * @param state  the state this event action point is part of. Set to 0 to
    * indicate that an action is active in all event states.
    * @param fromScopeType  the from scope type of this event action point, i.e.
    * one of the EventActionPoint.SCOPE_* constants. The set of processes to
    * match as signal sender, creator, killer, swapped out, error reporter,
    * user event reporter, bound, signal allocating, signal freeing, and timed
    * out processes.
    * @param fromScopeId  the from scope id of this event action point, if
    * fromScopeType is not EventActionPoint.SCOPE_NAME_PATTERN.
    * @param fromNamePattern  the from scope name pattern of this event action
    * point, if fromScopeType is EventActionPoint.SCOPE_NAME_PATTERN.
    * @param toScopeType  the to scope type of this event action point, i.e. one
    * of the EventActionPoint.SCOPE_* constants. The set of processes to match
    * as signal receiver, created, killed, or swapped in processes.
    * @param toScopeId  the to scope id of this event action point, if
    * toScopeType is not EventActionPoint.SCOPE_NAME_PATTERN.
    * @param toNamePattern  the to scope name pattern of this event action
    * point, if toScopeType is EventActionPoint.SCOPE_NAME_PATTERN.
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
    * @param parameter  the parameter to the action.
    * For ACTION_NOTIFY, ACTION_INTERCEPT, and ACTION_TRACE actions this is a
    * bitfield where bit 0 is set to 1 if file and line should be incorporated.
    * Other bits should be set to zero. For ACTION_SET_STATE actions this is the
    * new event state to set.
    * @param numIgnore  the number of times an event is allowed to occur before
    * the action is performed.
    * @param numRemove  the number of times an event is allowed to occur before
    * the action is automatically removed.
    */
   EventActionPoint(int state,
                    int fromScopeType,
                    int fromScopeId,
                    String fromNamePattern,
                    int toScopeType,
                    int toScopeId,
                    String toNamePattern,
                    int interceptScopeType,
                    int interceptScopeId,
                    String interceptNamePattern,
                    boolean not,
                    int action,
                    int parameter,
                    int numIgnore,
                    int numRemove)
   {
      validateScopeType(fromScopeType);
      validateScopeType(toScopeType);
      validateScopeType(interceptScopeType);
      validateAction(action);
      this.id = 0;
      this.client = 0;
      this.count = 0;
      this.state = state;
      this.fromScopeType = fromScopeType;
      this.fromScopeId = fromScopeId;
      this.fromNamePattern = fromNamePattern;
      this.toScopeType = toScopeType;
      this.toScopeId = toScopeId;
      this.toNamePattern = toNamePattern;
      this.interceptScopeType = interceptScopeType;
      this.interceptScopeId = interceptScopeId;
      this.interceptNamePattern = interceptNamePattern;
      this.not = not;
      this.action = action;
      this.parameter = parameter;
      this.numIgnore = numIgnore;
      this.numRemove = numRemove;
   }

   /**
    * Create a new event action point object.
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
   EventActionPoint(int state,
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
      String tmpFromNamePattern = null;
      String tmpToNamePattern = null;
      String tmpInterceptNamePattern = null;
      int tmpNumIgnore = 0;
      int tmpNumRemove = 0;

      validateScopeType(fromType);
      validateScopeType(toType);
      validateScopeType(interceptType);
      validateAction(action);
      this.id = id;
      this.client = client;
      this.count = count;
      this.state = state;
      this.fromScopeType = fromType;
      this.fromScopeId = fromId;
      this.toScopeType = toType;
      this.toScopeId = toId;
      this.interceptScopeType = interceptType;
      this.interceptScopeId = interceptId;
      this.not = not;
      this.action = action;
      this.parameter = parameter;

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
               buffer.order(bigEndian ? ByteOrder.BIG_ENDIAN : ByteOrder.LITTLE_ENDIAN);
            }
            else
            {
               // Ignore and skip invalid tags.
               continue;
            }

            try
            {
               switch (type)
               {
                  case Monitor.MONITOR_SCOPE_TAG_NAME:
                     if ((fromScopeType == SCOPE_NAME_PATTERN) &&
                         (tmpFromNamePattern == null))
                     {
                        tmpFromNamePattern = bytesToString(buffer);
                     }
                     else if ((toScopeType == SCOPE_NAME_PATTERN) &&
                              (tmpToNamePattern == null))
                     {
                        tmpToNamePattern = bytesToString(buffer);
                     }
                     else if ((interceptScopeType == SCOPE_NAME_PATTERN) &&
                              (tmpInterceptNamePattern == null))
                     {
                        tmpInterceptNamePattern = bytesToString(buffer);
                     }
                     break;
                  case Monitor.MONITOR_EVENT_ACTIONPOINT_TAG_IGNORE:
                     tmpNumIgnore = buffer.getInt();
                     break;
                  case Monitor.MONITOR_EVENT_ACTIONPOINT_TAG_REMOVE:
                     tmpNumRemove = buffer.getInt();
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

      this.fromNamePattern = tmpFromNamePattern;
      this.toNamePattern = tmpToNamePattern;
      this.interceptNamePattern = tmpInterceptNamePattern;
      this.numIgnore = tmpNumIgnore;
      this.numRemove = tmpNumRemove;
   }

   /**
    * Create the parameter to the action.
    *
    * @param action  the action to take when an event occurs, i.e. one of the
    * EventActionPoint.ACTION_* constants.
    * @param fileAndLineIncluded  For ACTION_NOTIFY, ACTION_INTERCEPT, and
    * ACTION_TRACE actions, determine whether filename and line number
    * information will be included in the event or not.
    * @param newState  For ACTION_SET_STATE actions, this is the new event state
    * to set.
    * @return the parameter to the action.
    */
   static int createParameter(int action,
                              boolean fileAndLineIncluded,
                              int newState)
   {
      if (action == ACTION_SET_STATE)
      {
         return newState;
      }
      else if ((action == ACTION_NOTIFY) ||
               (action == ACTION_INTERCEPT) ||
               (action == ACTION_TRACE))
      {
         return (fileAndLineIncluded ?
                 Monitor.MONITOR_EVENT_ACTIONPOINT_PARAMETER_FILE : 0);
      }
      else
      {
         return 0;
      }
   }

   /**
    * Validate the given action, i.e. make sure that it is one of the
    * EventActionPoint.ACTION_* constants.
    *
    * @param action  the action to validate.
    * @throws IllegalArgumentException  if the given action is invalid.
    */
   private static void validateAction(int action)
      throws IllegalArgumentException
   {
      if ((action != ACTION_NOTIFY) &&
          (action != ACTION_INTERCEPT) &&
          (action != ACTION_TRACE) &&
          (action != ACTION_ENABLE_TRACE) &&
          (action != ACTION_DISABLE_TRACE) &&
          (action != ACTION_SET_STATE) &&
          (action != ACTION_COUNT) &&
          (action != ACTION_USER) &&
          (action != ACTION_ENABLE_HW_TRACE) &&
          (action != ACTION_DISABLE_HW_TRACE))
      {
         throw new IllegalArgumentException("Invalid action");
      }
   }

   /**
    * Validate the given scope type, i.e. make sure that it is one of the
    * EventActionPoint.SCOPE_* constants.
    *
    * @param scopeType  the scope type to validate.
    * @throws IllegalArgumentException  if the given scope type is invalid.
    */
   private static void validateScopeType(int scopeType)
      throws IllegalArgumentException
   {
      if ((scopeType != SCOPE_SYSTEM) &&
          (scopeType != SCOPE_SEGMENT_ID) &&
          (scopeType != SCOPE_BLOCK_ID) &&
          (scopeType != SCOPE_ID) &&
          (scopeType != SCOPE_NAME_PATTERN))
      {
         throw new IllegalArgumentException("Invalid scope type");
      }
   }

   /**
    * Convert a byte buffer to an ISO-8859-1 string. Strip terminating null
    * character and any characters after it.
    *
    * @param buffer  the byte buffer to be converted.
    * @return the resulting string.
    */
   private String bytesToString(ByteBuffer buffer)
   {
      String str = null;

      try
      {
         int nullIndex;

         str = decoder.decode(buffer).toString();
         nullIndex = str.indexOf(0);
         if (nullIndex >= 0)
         {
            str = str.substring(0, nullIndex);
         }
      } catch (CharacterCodingException ignore) {}

      return str;
   }

   /**
    * Return the id of this event action point.
    * Only applicable if this event action point was retrieved with
    * com.ose.system.Target.getEventActionPoints() or successfully
    * set with com.ose.system.Target.setEventActionPoint().
    *
    * @return the id of this event action point.
    */
   public int getId()
   {
      return id;
   }

   /**
    * Set the id of this event action point.
    *
    * @param id  the id to be set.
    */
   void setId(int id)
   {
      this.id = id;
   }

   /**
    * Return the id of the client process who set this event action point.
    * Only applicable if this event action point was retrieved with
    * com.ose.system.Target.getEventActionPoints().
    *
    * @return the id of the client process who set this event action point.
    */
   public int getClient()
   {
      return client;
   }

   /**
    * Return the number of times this event has occured.
    * Only applicable if this event action point was retrieved with
    * com.ose.system.Target.getEventActionPoints().
    *
    * @return the number of times this event has occured.
    */
   public int getCount()
   {
      return count;
   }

   /**
    * Return the event state this event action point is part of.
    * 0 indicates that an action is active in all event states.
    *
    * @return the event state this event action point is part of.
    */
   public int getState()
   {
      return state;
   }

   /**
    * Set the event state this event action point is part of.
    * 0 indicates that an action is active in all event states.
    *
    * @param state  the event state this event action point is part of.
    */
   public void setState(int state)
   {
      this.state = state;
   }

   /**
    * Return the from scope type of this event action point, i.e. one of the
    * EventActionPoint.SCOPE_* constants.
    * <p>
    * The set of processes to match as signal sender, creator, killer, swapped
    * out, error reporter, user event reporter, bound, signal allocating,
    * signal freeing, and timed out processes.
    *
    * @return the from scope type of this event action point.
    * @see #SCOPE_SYSTEM
    * @see #SCOPE_SEGMENT_ID
    * @see #SCOPE_BLOCK_ID
    * @see #SCOPE_ID
    * @see #SCOPE_NAME_PATTERN
    */
   public int getFromScopeType()
   {
      return fromScopeType;
   }

   /**
    * Set the from scope type of this event action point, i.e. one of the
    * EventActionPoint.SCOPE_* constants.
    * <p>
    * The set of processes to match as signal sender, creator, killer, swapped
    * out, error reporter, user event reporter, bound, signal allocating,
    * signal freeing, and timed out processes.
    *
    * @param fromScopeType  the from scope type of this event action point.
    * @see #SCOPE_SYSTEM
    * @see #SCOPE_SEGMENT_ID
    * @see #SCOPE_BLOCK_ID
    * @see #SCOPE_ID
    * @see #SCOPE_NAME_PATTERN
    */
   public void setFromScopeType(int fromScopeType)
   {
      validateScopeType(fromScopeType);
      this.fromScopeType = fromScopeType;
   }

   /**
    * Return the from scope id of this event action point.
    * Only applicable if from scope type is not SCOPE_NAME_PATTERN.
    *
    * @return the from scope id of this event action point.
    */
   public int getFromScopeId()
   {
      return fromScopeId;
   }

   /**
    * Set the from scope id of this event action point.
    * Only applicable if from scope type is not SCOPE_NAME_PATTERN.
    *
    * @param fromScopeId  the from scope id of this event action point.
    */
   public void setFromScopeId(int fromScopeId)
   {
      this.fromScopeId = fromScopeId;
   }

   /**
    * Return the from process name pattern of this event action point.
    * Only applicable if from scope type is SCOPE_NAME_PATTERN.
    *
    * @return the from process name pattern of this event action point.
    */
   public String getFromNamePattern()
   {
      return fromNamePattern;
   }

   /**
    * Set the from process name pattern of this event action point.
    * Only applicable if from scope type is SCOPE_NAME_PATTERN.
    *
    * @param fromNamePattern  the from process name pattern of this event action
    * point.
    */
   public void setFromNamePattern(String fromNamePattern)
   {
      this.fromNamePattern = fromNamePattern;
   }

   /**
    * Return the to scope type of this event action point, i.e. one of the
    * EventActionPoint.SCOPE_* constants.
    * <p>
    * The set of processes to match as signal receiver, created, killed, or
    * swapped in processes.
    *
    * @return the to scope type of this event action point.
    * @see #SCOPE_SYSTEM
    * @see #SCOPE_SEGMENT_ID
    * @see #SCOPE_BLOCK_ID
    * @see #SCOPE_ID
    * @see #SCOPE_NAME_PATTERN
    */
   public int getToScopeType()
   {
      return toScopeType;
   }

   /**
    * Set the to scope type of this event action point, i.e. one of the
    * EventActionPoint.SCOPE_* constants.
    * <p>
    * The set of processes to match as signal receiver, created, killed, or
    * swapped in processes.
    *
    * @param toScopeType  the to scope type of this event action point.
    * @see #SCOPE_SYSTEM
    * @see #SCOPE_SEGMENT_ID
    * @see #SCOPE_BLOCK_ID
    * @see #SCOPE_ID
    * @see #SCOPE_NAME_PATTERN
    */
   public void setToScopeType(int toScopeType)
   {
      validateScopeType(toScopeType);
      this.toScopeType = toScopeType;
   }

   /**
    * Return the to scope id of this event action point.
    * Only applicable if to scope type is not SCOPE_NAME_PATTERN.
    *
    * @return the to scope id of this event action point.
    */
   public int getToScopeId()
   {
      return toScopeId;
   }

   /**
    * Set the to scope id of this event action point.
    * Only applicable if to scope type is not SCOPE_NAME_PATTERN.
    *
    * @param toScopeId  the to scope id of this event action point.
    */
   public void setToScopeId(int toScopeId)
   {
      this.toScopeId = toScopeId;
   }

   /**
    * Return the to process name pattern of this event action point.
    * Only applicable if to scope type is SCOPE_NAME_PATTERN.
    *
    * @return the to process name pattern of this event action point.
    */
   public String getToNamePattern()
   {
      return toNamePattern;
   }

   /**
    * Set the to process name pattern of this event action point.
    * Only applicable if to scope type is SCOPE_NAME_PATTERN.
    *
    * @param toNamePattern  the to process name pattern of this event action
    * point.
    */
   public void setToNamePattern(String toNamePattern)
   {
      this.toNamePattern = toNamePattern;
   }

   /**
    * Return the intercept scope type of this event action point, i.e. one of
    * the EventActionPoint.SCOPE_* constants.
    * <p>
    * Only applicable if getAction() returns ACTION_INTERCEPT; defines the set
    * of processes to intercept.
    *
    * @return the intercept scope type of this event action point.
    * @see #SCOPE_SYSTEM
    * @see #SCOPE_SEGMENT_ID
    * @see #SCOPE_BLOCK_ID
    * @see #SCOPE_ID
    * @see #SCOPE_NAME_PATTERN
    */
   public int getInterceptScopeType()
   {
      return interceptScopeType;
   }

   /**
    * Set the intercept scope type of this event action point, i.e. one of
    * the EventActionPoint.SCOPE_* constants.
    * <p>
    * Only applicable if getAction() returns ACTION_INTERCEPT; defines the set
    * of processes to intercept.
    *
    * @param interceptScopeType  the intercept scope type of this event action
    * point.
    * @see #SCOPE_SYSTEM
    * @see #SCOPE_SEGMENT_ID
    * @see #SCOPE_BLOCK_ID
    * @see #SCOPE_ID
    * @see #SCOPE_NAME_PATTERN
    */
   public void setInterceptScopeType(int interceptScopeType)
   {
      validateScopeType(interceptScopeType);
      this.interceptScopeType = interceptScopeType;
   }

   /**
    * Return the intercept scope id of this event action point.
    * Only applicable if intercept scope type is not SCOPE_NAME_PATTERN
    * and getAction() returns ACTION_INTERCEPT.
    *
    * @return the intercept scope id of this event action point.
    */
   public int getInterceptScopeId()
   {
      return interceptScopeId;
   }

   /**
    * Set the intercept scope id of this event action point.
    * Only applicable if intercept scope type is not SCOPE_NAME_PATTERN
    * and getAction() returns ACTION_INTERCEPT.
    *
    * @param interceptScopeId  the intercept scope id of this event action
    * point.
    */
   public void setInterceptScopeId(int interceptScopeId)
   {
      this.interceptScopeId = interceptScopeId;
   }

   /**
    * Return the intercept process name pattern of this event action point.
    * Only applicable if intercept scope type is SCOPE_NAME_PATTERN
    * and getAction() returns ACTION_INTERCEPT.
    *
    * @return the intercept process name pattern of this event action point.
    */
   public String getInterceptNamePattern()
   {
      return interceptNamePattern;
   }

   /**
    * Set the intercept process name pattern of this event action point.
    * Only applicable if intercept scope type is SCOPE_NAME_PATTERN
    * and getAction() returns ACTION_INTERCEPT.
    *
    * @param interceptNamePattern  the intercept process name pattern of this
    * event action point.
    */
   public void setInterceptNamePattern(String interceptNamePattern)
   {
      this.interceptNamePattern = interceptNamePattern;
   }

   /**
    * Determine whether to perform the action if an event matches or not.
    *
    * @return true if not to perform the action if an event matches (even if
    * other events indicate that this action should be performed), false
    * otherwise.
    */
   public boolean isNot()
   {
      return not;
   }

   /**
    * Set whether to perform the action if an event matches or not.
    *
    * @param not  true if not to perform the action if an event matches (even
    * if other events indicate that this action should be performed), false
    * otherwise.
    */
   public void setNot(boolean not)
   {
      this.not = not;
   }

   /**
    * Return the action to take when an event occurs, i.e. one of the
    * EventActionPoint.ACTION_* constants.
    *
    * @return the action to take when an event occurs.
    * @see #ACTION_NOTIFY
    * @see #ACTION_INTERCEPT
    * @see #ACTION_TRACE
    * @see #ACTION_ENABLE_TRACE
    * @see #ACTION_DISABLE_TRACE
    * @see #ACTION_SET_STATE
    * @see #ACTION_COUNT
    * @see #ACTION_USER
    * @see #ACTION_ENABLE_HW_TRACE
    * @see #ACTION_DISABLE_HW_TRACE
    */
   public int getAction()
   {
      return action;
   }

   /**
    * Set the action to take when an event occurs, i.e. one of the
    * EventActionPoint.ACTION_* constants.
    *
    * @param action  the action to take when an event occurs.
    * @see #ACTION_NOTIFY
    * @see #ACTION_INTERCEPT
    * @see #ACTION_TRACE
    * @see #ACTION_ENABLE_TRACE
    * @see #ACTION_DISABLE_TRACE
    * @see #ACTION_SET_STATE
    * @see #ACTION_COUNT
    * @see #ACTION_USER
    * @see #ACTION_ENABLE_HW_TRACE
    * @see #ACTION_DISABLE_HW_TRACE
    */
   public void setAction(int action)
   {
      validateAction(action);
      this.action = action;
   }

   /**
    * Return the parameter to the action.
    * <p>
    * For ACTION_NOTIFY, ACTION_INTERCEPT, and ACTION_TRACE actions this is a
    * bitfield where bit 0 is set to 1 if file and line should be incorporated
    * in events. Other bits should be set to zero.
    * For ACTION_SET_STATE actions this is the new event state to set.
    *
    * @return the parameter to the action.
    */
   int getParameter()
   {
      return parameter;
   }

   /**
    * Set the parameter to the action.
    * <p>
    * For ACTION_NOTIFY, ACTION_INTERCEPT, and ACTION_TRACE actions this is a
    * bitfield where bit 0 is set to 1 if file and line should be incorporated
    * in events. Other bits should be set to zero.
    * For ACTION_SET_STATE actions this is the new event state to set.
    *
    * @param parameter  the parameter to the action.
    */
   void setParameter(int parameter)
   {
      this.parameter = parameter;
   }

   /**
    * Determine whether filename and line number information will be included in
    * events or not.
    * <p>
    * Only applicable if getAction() returns ACTION_NOTIFY, ACTION_INTERCEPT, or
    * ACTION_TRACE.
    *
    * @return true if filename and line number information will be included in
    * events, false otherwise.
    */
   public boolean isFileAndLineIncluded()
   {
      return ((parameter & Monitor.MONITOR_EVENT_ACTIONPOINT_PARAMETER_FILE) != 0);
   }

   /**
    * Set whether filename and line number information will be included in
    * events or not.
    * <p>
    * Only applicable if getAction() returns ACTION_NOTIFY, ACTION_INTERCEPT, or
    * ACTION_TRACE.
    *
    * @param fileAndLineIncluded  true if filename and line number information
    * will be included in events, false otherwise.
    */
   public void setFileAndLineIncluded(boolean fileAndLineIncluded)
   {
      if (fileAndLineIncluded)
      {
         parameter |= Monitor.MONITOR_EVENT_ACTIONPOINT_PARAMETER_FILE;
      }
      else
      {
         parameter &= ~Monitor.MONITOR_EVENT_ACTIONPOINT_PARAMETER_FILE;
      }
   }

   /**
    * Return the new event state to be set when the event occurs if the action
    * is ACTION_SET_STATE.
    * <p>
    * Only applicable if getAction() returns ACTION_SET_STATE.
    *
    * @return the new event state to be set when the event occurs if the action
    * is ACTION_SET_STATE.
    */
   public int getNewState()
   {
      return parameter;
   }

   /**
    * Set the new event state to be set when the event occurs if the action is
    * ACTION_SET_STATE.
    * <p>
    * Only applicable if getAction() returns ACTION_SET_STATE.
    *
    * @param state  the new event state to be set when the event occurs if the
    * action is ACTION_SET_STATE.
    */
   public void setNewState(int state)
   {
      parameter = state;
   }

   /**
    * Return the number of times an event is allowed to occur before the action
    * is performed.
    *
    * @return the number of times an event is allowed to occur before the action
    * is performed.
    */
   public int getNumIgnore()
   {
      return numIgnore;
   }

   /**
    * Set the number of times an event is allowed to occur before the action is
    * performed.
    *
    * @param numIgnore  the number of times an event is allowed to occur before
    * the action is performed.
    */
   public void setNumIgnore(int numIgnore)
   {
      this.numIgnore = numIgnore;
   }

   /**
    * Return the number of times an event is allowed to occur before the action
    * is automatically removed.
    *
    * @return the number of times an event is allowed to occur before the action
    * is automatically removed.
    */
   public int getNumRemove()
   {
      return numRemove;
   }

   /**
    * Set the number of times an event is allowed to occur before the action is
    * automatically removed.
    *
    * @param numRemove  the number of times an event is allowed to occur before
    * the action is automatically removed.
    */
   public void setNumRemove(int numRemove)
   {
      this.numRemove = numRemove;
   }

   /**
    * Return an array of the OSE monitor protocol tags for this event action
    * point.
    *
    * @return an array of the OSE monitor protocol tags for this event action
    * point.
    */
   MonitorTag[] getTags()
   {
      List tags = new ArrayList();

      if (fromScopeType == SCOPE_NAME_PATTERN)
      {
         tags.add(new MonitorTag(Monitor.MONITOR_SCOPE_TAG_NAME,
                                 fromNamePattern));
      }
      if (toScopeType == SCOPE_NAME_PATTERN)
      {
         tags.add(new MonitorTag(Monitor.MONITOR_SCOPE_TAG_NAME,
                                 toNamePattern));
      }
      if (interceptScopeType == SCOPE_NAME_PATTERN)
      {
         tags.add(new MonitorTag(Monitor.MONITOR_SCOPE_TAG_NAME,
                                 interceptNamePattern));
      }
      tags.add(new MonitorTag(
            Monitor.MONITOR_EVENT_ACTIONPOINT_TAG_IGNORE,
            new Integer(numIgnore)));
      tags.add(new MonitorTag(
            Monitor.MONITOR_EVENT_ACTIONPOINT_TAG_REMOVE,
            new Integer(numRemove)));

      return (MonitorTag[]) tags.toArray(new MonitorTag[0]);
   }

   /**
    * Return an array of the OSE monitor protocol tags for this event action
    * point.
    *
    *@param bigEndian      the target byte order.
    *
    * @return an array of the OSE monitor protocol tags for this event action
    * point.
    */
   MonitorTag[] getTags(boolean bigEndian)
   {
      return null;
   }

   
   public Integer[] convertLongToIntegers(long value, boolean bigEndian)
   {
      if (bigEndian)
      {
         return new Integer[] {new Integer((int) (value >> 32)),
               new Integer((int) value)};
      }
      else
      {
         return new Integer[] {new Integer((int) value),
               new Integer((int) (value >> 32))};
      }
   }
   
   /**
    * Return a string representation of this event action point object.
    *
    * @return a string representation of this event action point object.
    * @see java.lang.Object#toString()
    */
   public String toString()
   {
      return ("Event action point ID: " + id +
              ", Client pid: " + client +
              ", Event count: " + count +
              ", Event state: " + state +
              ", From scope type: " + fromScopeType +
              ", From scope ID: " + fromScopeId +
              ", From scope name pattern: " + fromNamePattern +
              ", To scope type: " + toScopeType +
              ", To scope ID: " + toScopeId +
              ", To scope name pattern: " + toNamePattern +
              ", Intercept scope type: " + interceptScopeType +
              ", Intercept scope ID: " + interceptScopeId +
              ", Intercept scope name pattern: " + interceptNamePattern +
              ", Not: " + not +
              ", Action: " + action +
              ", Action parameter: " + parameter +
              ", Num ignore: " + numIgnore +
              ", Num remove: " + numRemove);
   }
}
