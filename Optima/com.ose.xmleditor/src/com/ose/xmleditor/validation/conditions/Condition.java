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

package com.ose.xmleditor.validation.conditions;

import org.w3c.dom.Attr;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import com.ose.xmleditor.validation.ValidationNode;
import com.ose.xmleditor.validation.errors.AttributeError;
import com.ose.xmleditor.validation.errors.NodeError;

public abstract class Condition
{
   /**
    * Validates an attribute.
    * 
    * @param node
    *           the node where the attribute is located.
    * @param attribute
    *           the attribute to validate.
    * @return true if valid.
    */
   public boolean validate(ValidationNode node, Attr attribute)
   {
      boolean valid = doValidation(attribute.getValue());
      if (!valid)
      {
         node.addError(new AttributeError(getErrorMessage(), attribute
               .getName()));
         return false;
      }
      else
      {
         return true;
      }
   }

   /**
    * Validates a nodes children.
    * 
    * @param node
    *           the node to validate.
    * @return true if all children are valid.
    */
   public boolean validate(ValidationNode node)
   {
      boolean ok = true;

      NodeList children = node.getNode().getChildNodes();
      if (children.getLength() == 0)
      {
         // Check nodes that should have a non-null value.
         if (!doValidation(null))
         {
            node.addError(new NodeError(getErrorMessage(), 0));
            ok = false;
         }
      }
      else
      {
         for (int i = 0; i < children.getLength(); i++)
         {
            Node child = children.item(i);
            if (!doValidation(child.getNodeValue()))
            {
               node.addError(new NodeError(getErrorMessage(), i));
               ok = false;
            }
         }
      }
      return ok;
   }

   protected abstract boolean doValidation(String value);

   protected abstract String getErrorMessage();
}
