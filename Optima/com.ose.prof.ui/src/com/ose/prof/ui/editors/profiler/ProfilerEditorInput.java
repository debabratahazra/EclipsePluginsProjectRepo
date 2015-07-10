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

package com.ose.prof.ui.editors.profiler;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IPersistableElement;
import com.ose.system.Target;

public abstract class ProfilerEditorInput implements IEditorInput
{
   private final Target target;
   private final int integrationInterval;
   private final int maxReports;
   private final long timestamp;
   
   public ProfilerEditorInput(Target target,
                              int integrationInterval,
                              int maxReports,
                              long timestamp)
   {
      if (target == null)
      {
         throw new IllegalArgumentException();
      }
      this.target = target;
      this.integrationInterval = integrationInterval;
      this.maxReports = maxReports;
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

   public IPersistableElement getPersistable()
   {
      return null;
   }

   public String getToolTipText()
   {
      return target.toString();
   }

   public Object getAdapter(Class adapter)
   {
      return null;
   }

   public Target getTarget()
   {
      return target;
   }
   
   public int getIntegrationInterval()
   {
      return integrationInterval;
   }

   public int getMaxReports()
   {
      return maxReports;
   }

   public long getTimestamp()
   {
      return timestamp;
   }

   public boolean equals(Object obj)
   {
      if (obj instanceof ProfilerEditorInput)
      {
         ProfilerEditorInput that = (ProfilerEditorInput)obj;         
         
         return getTarget().equals(that.getTarget()) 
            && getIntegrationInterval() == that.getIntegrationInterval()
            && getMaxReports() == that.getMaxReports()
            && getTimestamp() == that.getTimestamp();
      }
      return false;
   }
   
   public int hashCode()
   {
      int result = getTarget().hashCode();
      
      result = 37*result + getIntegrationInterval();
      result = 37*result + getMaxReports();
      return result;
   }
}
