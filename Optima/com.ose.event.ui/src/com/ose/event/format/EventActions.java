/* COPYRIGHT-ENEA-SRC-R1 *
 **************************************************************************
 * Copyright (C) 2008 by Enea Software AB.
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

package com.ose.event.format;

import java.util.List;

/**
 * This class is a place-holder for a set of event actions and their initial
 * event action state.
 */
public class EventActions
{
   private final List eventActions;

   private final int initState;

   private final boolean hasSignalDataFilterTag;
   
   /**
    * Create a new event actions place-holder object.
    *
    * @param eventActions  the event actions.
    * @param initState  the initial event action state.
    */
   public EventActions(List eventActions, int initState)
   {
      if (eventActions == null)
      {
         throw new IllegalArgumentException();
      }
      this.eventActions = eventActions;
      this.initState = initState;
      this.hasSignalDataFilterTag = false;
   }

   /**
    * Create a new event actions place-holder object.
    *
    * @param eventActions  the event actions.
    * @param initState  the initial event action state.
    * @param hasSignalDataFilterTag The data filer tag(s) is available or not.
    */
   public EventActions(List eventActions, int initState,
         boolean hasSignalDataFilterTag)
   {
      if (eventActions == null)
      {
         throw new IllegalArgumentException();
      }
      this.eventActions = eventActions;
      this.initState = initState;
      this.hasSignalDataFilterTag = hasSignalDataFilterTag;
   }

   /**
    * Return the event actions.
    *
    * @return  the event actions.
    */
   public List getEventActions()
   {
      return eventActions;
   }

   /**
    * Return the initial event action state.
    *
    * @return  the initial event action state.
    */
   public int getInitState()
   {
      return initState;
   }

   /**
    * 
    * @return true if the data filer tag(s) is available, false otherwise.
    */
   public boolean isHasSignalDataFilterTag()
   {
      return hasSignalDataFilterTag;
   }
   
   
}
