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

package com.ose.event.ui.editors.event;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IPersistableElement;
import com.ose.system.Target;

public class EventEditorInput implements IEditorInput
{
   private final Target target;

   private final String scope;

   private final long timestamp;

   public EventEditorInput(Target target, String scope, long timestamp)
   {
      if ((target == null) || (scope == null))
      {
         throw new IllegalArgumentException();
      }
      this.target = target;
      this.scope = scope;
      this.timestamp = timestamp;
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
      return "Events";
   }

   public IPersistableElement getPersistable()
   {
      return null;
   }

   public String getToolTipText()
   {
      return target.toString();
   }

   public Target getTarget()
   {
      return target;
   }

   public String getScope()
   {
      return scope;
   }

   public long getTimestamp()
   {
      return timestamp;
   }

   public Object getAdapter(Class adapter)
   {
      return null;
   }

   public boolean equals(Object obj)
   {
      boolean result = false;
      if (obj instanceof EventEditorInput)
      {
         EventEditorInput other = (EventEditorInput) obj;
         result = (target.equals(other.target) &&
                   scope.equals(other.scope) &&
                   (timestamp == other.timestamp));
      }
      return result;
   }
}
