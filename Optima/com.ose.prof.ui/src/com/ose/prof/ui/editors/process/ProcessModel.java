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

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import com.ose.xmleditor.model.DocumentModel;

public class ProcessModel extends DocumentModel
{
   public ProcessModel(Document document)
   {
      init(document);
   }

   public void createElement(String type, String id)
   {
      NodeList nodes = document.getElementsByTagName("processes");
      Element root = (Element) nodes.item(0);

      Element process = document.createElement("process");
      process.setAttribute("type", type);
      process.appendChild(document.createTextNode(id));
      
      root.appendChild(process);
      fireElementAdded(process);
   }

   public void createElement(String str) {}
}
