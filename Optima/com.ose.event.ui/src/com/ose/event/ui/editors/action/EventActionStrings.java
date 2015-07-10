/* COPYRIGHT-ENEA-SRC-R2 *
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

package com.ose.event.ui.editors.action;

import com.ose.xmleditor.model.DocumentChangeEvent;

public class EventActionStrings
{
   public static final int SYSTEM = 0;
   
   public static final int BLOCK = 1;
   
   public static final int PROCESS = 2;
   
   public static final int NAME_PATTERN = 3;
   
   public static final String[] SCOPE = { "System", "Block",
         "Process", "Name Pattern" };

   public static final String[] XML_SCOPE = { "system", "block",
         "process", "namePattern" };

   public static final String[] EVENTS = { "Send", "Receive", "Swap", "Create",
         "Kill", "Error", "Bind", "Alloc", "Free", "Timeout", "User" };

   public static final int NUM_EVENT_GRP = 7;

   public static final String[] EVENTS_GRP1 = { "Send", "Receive" };

   public static final String[] EVENTS_GRP2 = { "Swap", "Create", "Kill" };

   public static final String[] EVENTS_GRP3 = { "Error" };

   public static final String[] EVENTS_GRP4 = { "Bind" };

   public static final String[] EVENTS_GRP5 = { "Alloc", "Free" };

   public static final String[] EVENTS_GRP6 = { "Timeout" };

   public static final String[] EVENTS_GRP7 = { "User" };

   public static final String[] XML_EVENTS = { "send", "receive", "swap",
         "create", "kill", "error", "bind", "alloc", "free", "timeout", "user" };

   public static final String[] XML_EVENTS_GRP1 = { "send", "receive" };

   public static final String[] XML_EVENTS_GRP2 = { "swap", "create", "kill" };

   public static final String[] XML_EVENTS_GRP3 = { "error" };

   public static final String[] XML_EVENTS_GRP4 = { "bind" };

   public static final String[] XML_EVENTS_GRP5 = { "alloc", "free" };

   public static final String[] XML_EVENTS_GRP6 = { "timeout" };

   public static final String[] XML_EVENTS_GRP7 = { "user" };

   public static final String[] ACTIONS = { "Trace", "Notify", "Intercept",
         "Set State", "Enable Trace", "Disable Trace", "User",
         "Enable Hardware Trace", "Disable Hardware Trace" };

   public static final String[] XML_ACTIONS = { "trace", "notify", "intercept",
         "setState", "enableTrace", "disableTrace", "user",
         "enableHWTrace", "disableHWTrace" };

   public static final String UNDO_ADD_LABEL = "Add Event Action";

   public static final String UNDO_REMOVE_LABEL = "Remove Event Action";

   public static final String UNDO_CHANGE_LABEL = "Event Action Property Change";
   
   public static final String[] DATA_FILTER_NUMBER_SIZE = { "1", "2", "4", "8" };

   public static String getEventString(String xmlEvent)
   {
      return capitalize(xmlEvent);
   }

   public static String getActionString(String xmlAction)
   {
      if (xmlAction.equals("setState"))
      {
         return ACTIONS[3];
      }
      else if (xmlAction.equals("enableTrace"))
      {
         return ACTIONS[4];
      }
      else if (xmlAction.equals("disableTrace"))
      {
         return ACTIONS[5];
      }
      else if (xmlAction.equals("enableHWTrace"))
      {
         return ACTIONS[7];
      }
      else if (xmlAction.equals("disableHWTrace"))
      {
         return ACTIONS[8];
      }
      else
      {
         return capitalize(xmlAction);
      }
   }

   public static String getScopeString(String xmlScope)
   {
      if(xmlScope.equals("namePattern"))
      {
         return SCOPE[NAME_PATTERN];
      }
      else
      {
         return capitalize(xmlScope);
      }
   }

   public static String getUndoActionLabel(int id)
   {
      switch (id)
      {
         case DocumentChangeEvent.ADD:
            return UNDO_ADD_LABEL;
         case DocumentChangeEvent.REMOVE:
            return UNDO_REMOVE_LABEL;
         case DocumentChangeEvent.CHANGE:
            return UNDO_CHANGE_LABEL;
         case DocumentChangeEvent.REPLACE:
            return UNDO_CHANGE_LABEL;
         default:
            return "";
      }
   }

   private static String capitalize(String in)
   {
      StringBuffer out = new StringBuffer(in);
      out.setCharAt(0, Character.toUpperCase(out.charAt(0)));
      return out.toString();
   }
}
