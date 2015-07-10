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

package com.ose.event.ui.preferences;

import java.util.ArrayList;
import java.util.List;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.Viewer;

class SymbolDbContentProvider implements IStructuredContentProvider
{
   private final List references = new ArrayList();

   public void inputChanged(Viewer viewer, Object oldInput, Object newInput)
   {
      // Nothing to do.
   }

   public void dispose()
   {
      references.clear();
   }

   public Object[] getElements(Object inputElement)
   {
      return (SymbolDbReference[]) references.toArray(new SymbolDbReference[0]);
   }

   public void addReference(SymbolDbReference reference)
   {
      references.add(reference);
   }

   public void removeReference(SymbolDbReference reference)
   {
      references.remove(reference);
   }

   public void moveUpReference(SymbolDbReference reference)
   {
      int index = references.indexOf(reference);
      if (index > 0)
      {
         references.remove(reference);
         references.add(index - 1, reference);
      }
   }

   public void moveDownReference(SymbolDbReference reference)
   {
      int index = references.indexOf(reference);
      if ((index >= 0) && (index < (references.size() - 1)))
      {
         references.remove(reference);
         references.add(index + 1, reference);
      }
   }
}
