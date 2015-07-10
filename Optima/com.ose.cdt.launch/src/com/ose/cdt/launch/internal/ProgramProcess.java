/* COPYRIGHT-ENEA-SRC-R2 *
 **************************************************************************
 * Copyright (C) 2008 by Enea Software AB.
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

package com.ose.cdt.launch.internal;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Status;
import org.eclipse.debug.core.DebugEvent;
import org.eclipse.debug.core.DebugException;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.ILaunch;
import org.eclipse.debug.core.model.IDebugTarget;
import org.eclipse.debug.core.model.IProcess;
import org.eclipse.debug.core.model.IStreamsProxy;

// TODO: Terminate the ProgramProcess if its program dies.
public class ProgramProcess implements IProcess
{
   private final ILaunch launch;
   private final String label;
   private volatile boolean killed;

   public ProgramProcess(ILaunch launch, String label)
   {
      if ((launch == null) || (label == null))
      {
         throw new NullPointerException();
      }
      this.launch = launch;
      this.label = label;
      launch.addProcess(this);
      fireCreateEvent();
   }

   public String getLabel()
   {
      return label;
   }

   public ILaunch getLaunch()
   {
      return launch;
   }

   public IStreamsProxy getStreamsProxy()
   {
      // TODO: Implement program console support.
      return null;
   }

   public String getAttribute(String key)
   {
      // Nothing to do.
      return null;
   }

   public void setAttribute(String key, String value)
   {
      // Nothing to do.
   }

   public int getExitValue() throws DebugException
   {
      if (killed)
      {
         // TODO: Get the exit value from the actual program.
         return 0;
      }
      else
      {
         throw new DebugException(new Status(
               IStatus.ERROR,
               DebugPlugin.getUniqueIdentifier(),
               DebugException.TARGET_REQUEST_FAILED,
               "Exit value not available until program terminates.",
               null));
      }
   }

   public boolean canTerminate()
   {
      return !killed;
   }

   public boolean isTerminated()
   {
      return killed;
   }

   public void terminate() throws DebugException
   {
      if (!killed)
      {
         killed = true;
         fireTerminateEvent();
      }
   }

   public Object getAdapter(Class adapter)
   {
      if (IProcess.class.equals(adapter))
      {
         return this;
      }
      else if (IDebugTarget.class.equals(adapter))
      {
         IDebugTarget[] targets = launch.getDebugTargets();
         for (int i = 0; i < targets.length; i++)
         {
            IDebugTarget target = targets[i];
            if (this.equals(target.getProcess()))
            {
               return target;
            }
         }
         return null;
      }
      else if (ILaunch.class.equals(adapter))
      {
         return launch;
      }
      else
      {
         return Platform.getAdapterManager().getAdapter(this, adapter);
      }
   }

   protected void fireEvent(DebugEvent event)
   {
      DebugPlugin manager = DebugPlugin.getDefault();
      if (manager != null)
      {
         manager.fireDebugEventSet(new DebugEvent[] {event});
      }
   }

   protected void fireCreateEvent()
   {
      fireEvent(new DebugEvent(this, DebugEvent.CREATE));
   }

   protected void fireTerminateEvent()
   {
      fireEvent(new DebugEvent(this, DebugEvent.TERMINATE));
   }
}
