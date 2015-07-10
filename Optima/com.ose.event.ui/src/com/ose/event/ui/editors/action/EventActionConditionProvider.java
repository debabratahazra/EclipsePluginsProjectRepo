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

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import com.ose.xmleditor.validation.conditions.BooleanStringCondition;
import com.ose.xmleditor.validation.conditions.Condition;
import com.ose.xmleditor.validation.conditions.ConditionProvider;
import com.ose.xmleditor.validation.conditions.NullCondition;
import com.ose.xmleditor.validation.conditions.PositiveIntegerCondition;
import com.ose.xmleditor.validation.conditions.PositiveLongIntegerCondition;
import com.ose.xmleditor.validation.conditions.StringCondition;

public class EventActionConditionProvider implements ConditionProvider
{
   private final Condition nullCondition;

   private final Condition integerCondition;
   
   private final Condition longIntegerCondition;

   private final Condition booleanCondition;

   private final Condition stringCondition;

   public EventActionConditionProvider()
   {
      nullCondition = new NullCondition();
      integerCondition = new PositiveIntegerCondition();
      booleanCondition = new BooleanStringCondition();
      stringCondition = new StringCondition();
      longIntegerCondition = new PositiveLongIntegerCondition();
   }

   public Condition getCondition(Node node, String attributeName)
   {
      String nodeName = node.getNodeName();

      if (nodeName.equals("eventActions"))
      {
         if (attributeName.equals("initState"))
         {
            return integerCondition;
         }
      }
      else if (nodeName.equals("eventAction"))
      {
         if (attributeName.equals("state"))
         {
            return integerCondition;
         }
      }
      else if (nodeName.equals("event"))
      {
         if (attributeName.equals("type"))
         {
            return stringCondition;
         }
      }
      else if (nodeName.equals("fromScope") || nodeName.equals("toScope"))
      {
         if (attributeName.equals("type"))
         {
            return stringCondition;
         }
         else if (attributeName.equals("value"))
         {
            Element element = (Element) node;
            String type = element.getAttribute("type");
            if (type.equals("namePattern"))
            {
               return stringCondition;
            }
            else
            {
               return integerCondition;
            }
         }

      }
      else if (nodeName.equals("dataFilter"))
      {
         if ((attributeName.equals("offset") || attributeName.equals("min"))
               || attributeName.equals("max"))
         {
            return longIntegerCondition;
         }
         else if (attributeName.equals("not"))
         {
            return booleanCondition;
         }
         return nullCondition;
      }
      else if (nodeName.equals("numberRange") || nodeName.equals("errorRange")
            || nodeName.equals("extraRange"))
      {
         if (attributeName.equals("min") || attributeName.equals("max"))
         {
            return integerCondition;
         }
         else if (attributeName.equals("not"))
         {
            return booleanCondition;
         }
      }
      else if (nodeName.equals("action"))
      {
         if (attributeName.equals("type"))
         {
            return stringCondition;
         }
         else if (attributeName.equals("not")
               || attributeName.equals("fileLineIncluded")
               || attributeName.equals("dataIncluded"))
         {
            return booleanCondition;
         }
      }

      return nullCondition;
   }

   public Condition getCondition(Node node)
   {
      String nodeName = node.getNodeName();

      if (nodeName.equals("ignoreCount") || nodeName.equals("newState"))
      {
         return integerCondition;
      }
      else if (nodeName.equals("onlyKernelErrors"))
      {
         return booleanCondition;
      }
      return nullCondition;
   }

}
