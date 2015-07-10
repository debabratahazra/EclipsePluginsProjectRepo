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

import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import com.ose.xmleditor.validation.ValidationNode;

public class MasterLabelProvider extends LabelProvider implements
      ITableLabelProvider
{
   public String getColumnText(Object element, int columnIndex)
   {
      if (element instanceof ValidationNode)
      {
         ValidationNode validationNode = (ValidationNode) element;

         return getElementText(columnIndex, validationNode);
      }
      return null;
   }

   public static String getElementText(int columnIndex,
         ValidationNode validationNode)
   {
      Element eventAction = (Element) validationNode.getNode();
      switch (columnIndex)
      {
         case EventActionMasterDetailsBlock.STATE:
            return eventAction.getAttribute("state");
         case EventActionMasterDetailsBlock.EVENT:
            StringBuffer eventBuffer;
            NodeList events;
            Element event;
            NodeList numberRanges;
            Element numberRange;
            NodeList fromScopes;
            Element fromScope;
            NodeList toScopes;
            Element toScope;

            eventBuffer = new StringBuffer();

            events = eventAction.getElementsByTagName("event");
            event = (Element) events.item(0);
            eventBuffer.append(EventActionStrings.getEventString(event
                  .getAttribute("type")));
            eventBuffer.append(" ");

            numberRanges = event.getElementsByTagName("numberRange");
            numberRange = (Element) numberRanges.item(0);
            if (numberRange != null)
            {
               eventBuffer.append(numberRange.getAttribute("min"));
               eventBuffer.append(" - ");
               eventBuffer.append(numberRange.getAttribute("max"));
               eventBuffer.append(" ");
               numberRange.getAttribute("not");
            }

            fromScopes = event.getElementsByTagName("fromScope");
            fromScope = (Element) fromScopes.item(0);
            if (fromScope != null)
            {
               String scopeType = fromScope.getAttribute("type");
               eventBuffer.append("from ");
               eventBuffer.append(EventActionStrings.getScopeString(scopeType));
               if (!scopeType
                     .equals(EventActionStrings.XML_SCOPE[EventActionStrings.SYSTEM]))
               {
                  eventBuffer.append(" (");
                  eventBuffer.append(fromScope.getAttribute("value"));
                  eventBuffer.append(")");
               }
               eventBuffer.append(" ");
            }

            toScopes = event.getElementsByTagName("toScope");
            toScope = (Element) toScopes.item(0);
            if (toScope != null)
            {
               String scopeType = toScope.getAttribute("type");
               eventBuffer.append("to ");
               eventBuffer.append(EventActionStrings.getScopeString(scopeType));
               if (!scopeType
                     .equals(EventActionStrings.XML_SCOPE[EventActionStrings.SYSTEM]))
               {
                  eventBuffer.append(" (");
                  eventBuffer.append(toScope.getAttribute("value"));
                  eventBuffer.append(") ");
               }
            }

            return eventBuffer.toString();
         case EventActionMasterDetailsBlock.ACTION:
            NodeList actions = eventAction.getElementsByTagName("action");
            Element action = (Element) actions.item(0);
            return EventActionStrings.getActionString(action
                  .getAttribute("type"));
      }
      return null;
   }

   public Image getColumnImage(Object element, int columnIndex)
   {
      Image image = null;

      if ((columnIndex == 0) && (element instanceof ValidationNode))
      {
         ValidationNode node = (ValidationNode) element;
         if (node.hasErrors())
         {
            image = PlatformUI.getWorkbench().getSharedImages()
                  .getImage(ISharedImages.IMG_OBJS_ERROR_TSK);
         }
      }

      return image;
   }
}
