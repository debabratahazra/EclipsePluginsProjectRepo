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

package com.ose.prof.ui.editors.process;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import com.ose.xmleditor.validation.conditions.Condition;
import com.ose.xmleditor.validation.conditions.ConditionProvider;
import com.ose.xmleditor.validation.conditions.NullCondition;
import com.ose.xmleditor.validation.conditions.PositiveIntegerCondition;
import com.ose.xmleditor.validation.conditions.StringCondition;

public class ProcessConditionProvider implements ConditionProvider
{
   public Condition getCondition(Node node, String attributeName)
   {
      String nodeName = node.getNodeName();

      if (nodeName.equals("process"))
      {
         if (attributeName.equals("type"))
         {
            return new StringCondition();
         }
      }
      return new NullCondition();
   }

   public Condition getCondition(Node node)
   {
      String nodeName = node.getNodeName();

      // id or namePattern
      if (nodeName.equals("process"))
      {
         Element element = (Element) node;
         String type = element.getAttribute("type");

         if (type.equals("id"))
         {
            return new PositiveIntegerCondition();
         }
         else
         {
            return new StringCondition();
         }
      }

      return new NullCondition();
   }
}
