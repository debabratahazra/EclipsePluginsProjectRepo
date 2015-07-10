/* COPYRIGHT-ENEA-SRC-R2 *
 **************************************************************************
 * Copyright (C) 2005-2007 by Enea Software AB.
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

package com.ose.system.ui.editors;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IPersistableElement;
import com.ose.system.SystemModelNode;

public class SystemModelNodeEditorInput implements IEditorInput
{
   private SystemModelNode node;

   public SystemModelNodeEditorInput(SystemModelNode node)
   {
      if (node == null)
      {
         throw new NullPointerException();
      }
      this.node = node;
   }

   public boolean exists()
   {
      return true;
   }

   public ImageDescriptor getImageDescriptor()
   {
      return null;
   }

   public String getName()
   {
      return node.toString();
   }

   public IPersistableElement getPersistable()
   {
      return null;
   }

   public String getToolTipText()
   {
      return getName();
   }

   public Object getAdapter(Class adapter)
   {
      return null;
   }

   public SystemModelNode getSystemModelNode()
   {
      return node;
   }

   public boolean equals(Object obj)
   {
      boolean result = false;
      if (obj instanceof SystemModelNodeEditorInput)
      {
         SystemModelNodeEditorInput other = (SystemModelNodeEditorInput) obj;
         result = getSystemModelNode().equals(other.getSystemModelNode());
      }
      return result;
   }
}
