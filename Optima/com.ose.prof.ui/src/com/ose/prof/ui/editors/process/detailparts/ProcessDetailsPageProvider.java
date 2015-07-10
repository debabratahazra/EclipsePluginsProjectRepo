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

package com.ose.prof.ui.editors.process.detailparts;

import org.eclipse.ui.forms.IDetailsPage;
import org.eclipse.ui.forms.IDetailsPageProvider;
import com.ose.xmleditor.model.DocumentModel;
import com.ose.xmleditor.validation.ValidationNode;

public class ProcessDetailsPageProvider implements IDetailsPageProvider
{
   private final DocumentModel model;

   public ProcessDetailsPageProvider(DocumentModel model)
   {
      this.model = model;
   }
   
   public IDetailsPage getPage(Object key)
   {
      if(key instanceof ValidationNode)
      {
         return new ProcessDetailsPage(model);
      }
         
      return null;
   }

   public Object getPageKey(Object object)
   {
      return object;
   }
}
