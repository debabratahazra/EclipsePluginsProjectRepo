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

package com.ose.event.ui.editors.action.detailparts;

import org.eclipse.ui.forms.IDetailsPage;
import org.eclipse.ui.forms.IDetailsPageProvider;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import com.ose.xmleditor.model.DocumentModel;
import com.ose.xmleditor.validation.ValidationNode;

public class EventActionDetailsPageProvider implements IDetailsPageProvider
{
   private final DocumentModel model;

   public EventActionDetailsPageProvider(DocumentModel model)
   {
      this.model = model;
   }
   
   public IDetailsPage getPage(Object key)
   {
      if(key instanceof ValidationNode)
      {
         ValidationNode validationNode = (ValidationNode) key;
         Element eventAction = (Element) validationNode.getNode();
         NodeList nodeList = eventAction.getElementsByTagName("event");
         Element event = (Element) nodeList.item(0);
         String eventType = event.getAttribute("type");
         
         nodeList = eventAction.getElementsByTagName("action");
         Element action = (Element) nodeList.item(0);
         String actionType = action.getAttribute("type");
         
         if (eventType.equalsIgnoreCase("send") || eventType.equalsIgnoreCase("receive"))
         {
            return new SignalEventActionDetailsPage(model, actionType);
         }
         else if (eventType.equalsIgnoreCase("swap") || eventType.equalsIgnoreCase("create") || eventType.equalsIgnoreCase("kill"))
         {
            return new ProcessEventActionDetailsPage(model, actionType);
         }
         else if (eventType.equalsIgnoreCase("error"))
         {
            return new ErrorEventActionDetailsPage(model, actionType);
         }
         else if (eventType.equalsIgnoreCase("bind"))
         {
            return new BindEventActionDetailsPage(model, actionType);
         }
         else if (eventType.equalsIgnoreCase("alloc") || eventType.equalsIgnoreCase("free"))
         {
            return new BufferEventActionDetailsPage(model, actionType);
         }
         else if (eventType.equalsIgnoreCase("timeout"))
         {
            return new TimeoutEventActionDetailsPage(model, actionType);
         }
         else if (eventType.equalsIgnoreCase("user"))
         {
            return new UserEventActionDetailsPage(model, actionType);
         }
      }
         
      return null;
   }

   public Object getPageKey(Object object)
   {
      return object;
   }

}
