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

import com.ose.system.service.monitor.MonitorTag;

/**
 * This class represents an OSE swap event action point. A swap event action
 * point defines what kind of action should be taken when a swap event occurs.
 *
 * @see com.ose.system.EventActionPoint
 * @see com.ose.system.SwapEvent
 */
public final class SwapEventActionPoint extends EventActionPoint
{
   /**
    * Create a new swap event action point object.
    */
   public SwapEventActionPoint()
   {
      super();
   }

   /**
    * Create a new swap event action point object.
    *
    * @param state  the state this event action point is part of. Set to 0 to
    * indicate that an action is active in all event states.
    * @param fromScopeType  the from scope type of this event action point, i.e.
    * one of the EventActionPoint.SCOPE_* constants. The set of processes to
    * match as swapped out processes.
    * @param fromScopeId  the from scope id of this event action point, if
    * fromScopeType is not EventActionPoint.SCOPE_NAME_PATTERN.
    * @param fromNamePattern  the from scope name pattern of this event action
    * point, if fromScopeType is EventActionPoint.SCOPE_NAME_PATTERN.
    * @param toScopeType  the to scope type of this event action point, i.e. one
    * of the EventActionPoint.SCOPE_* constants. The set of processes to match
    * as swapped in processes.
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
    * @param fileAndLineIncluded  For ACTION_NOTIFY, ACTION_INTERCEPT, and
    * ACTION_TRACE actions, determine whether filename and line number
    * information will be included in events or not.
    * @param newState  For ACTION_SET_STATE actions, this is the new event state
    * to set.
    * @param numIgnore  the number of times an event is allowed to occur before
    * the action is performed.
    * @param numRemove  the number of times an event is allowed to occur before
    * the action is automatically removed.
    */
   public SwapEventActionPoint(int state,
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
                               boolean fileAndLineIncluded,
                               int newState,
                               int numIgnore,
                               int numRemove)
   {
      super(state,
            fromScopeType,
            fromScopeId,
            fromNamePattern,
            toScopeType,
            toScopeId,
            toNamePattern,
            interceptScopeType,
            interceptScopeId,
            interceptNamePattern,
            not,
            action,
            createParameter(action, fileAndLineIncluded, newState),
            numIgnore,
            numRemove);
   }

   /**
    * Create a new swap event action point object.
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
   SwapEventActionPoint(int state,
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
   }
}
