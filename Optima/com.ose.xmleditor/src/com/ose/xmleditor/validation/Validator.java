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

package com.ose.xmleditor.validation;

import java.util.List;
import org.w3c.dom.Attr;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import com.ose.xmleditor.validation.conditions.Condition;
import com.ose.xmleditor.validation.conditions.ConditionProvider;

public class Validator
{
   private final ConditionProvider conditionProvider;

   public Validator(ConditionProvider conditionProvider)
   {
      this.conditionProvider = conditionProvider;
   }

   public void validate(Object[] nodes)
   {

      for (int i = 0; i < nodes.length; i++)
      {
         ValidationNode validationNode = (ValidationNode) nodes[i];
         validationNode.clearErrors();

         Node node = validationNode.getNode();
         Condition condition = conditionProvider.getCondition(node);

         condition.validate(validationNode);

         if (node.hasAttributes())
         {
            validateAttributes(validationNode);
         }

         List children = validationNode.getElements();
         validate(children.toArray());
      }
   }

   private void validateAttributes(ValidationNode validationNode)
   {
      Node node = validationNode.getNode();
      NamedNodeMap map = node.getAttributes();

      for (int i = 0; i < map.getLength(); i++)
      {
         Attr attribute = (Attr) map.item(i);
         Condition condition = conditionProvider.getCondition(node, attribute
               .getName());
         condition.validate(validationNode, attribute);
      }
   }
}
