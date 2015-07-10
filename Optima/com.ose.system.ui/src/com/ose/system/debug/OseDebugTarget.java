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

package com.ose.system.debug;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.eclipse.core.resources.IMarkerDelta;
import org.eclipse.debug.core.DebugException;
import org.eclipse.debug.core.ILaunch;
import org.eclipse.debug.core.model.DebugElement;
import org.eclipse.debug.core.model.IBreakpoint;
import org.eclipse.debug.core.model.IDebugTarget;
import org.eclipse.debug.core.model.IMemoryBlock;
import org.eclipse.debug.core.model.IMemoryBlockExtension;
import org.eclipse.debug.core.model.IMemoryBlockRetrievalExtension;
import org.eclipse.debug.core.model.IProcess;
import org.eclipse.debug.core.model.IThread;
import org.eclipse.debug.ui.actions.IAddMemoryBlocksTarget;
import com.ose.system.OseObject;
import com.ose.system.SystemModel;
import com.ose.system.SystemModelEvent;
import com.ose.system.SystemModelListener;
import com.ose.system.Target;
import com.ose.system.ui.SystemBrowserPlugin;

public class OseDebugTarget extends DebugElement
   implements IDebugTarget, IMemoryBlockRetrievalExtension, SystemModelListener
{
   private static Map debugTargetMap = new HashMap();

   private final Object debugContextProvider;
   private final Target target;
   private final IAddMemoryBlocksTarget addMemoryBlocksTarget;
   private volatile boolean terminated;

   protected OseDebugTarget(Object debugContextProvider, Target target)
   {
      super(null);
      this.debugContextProvider = debugContextProvider;
      this.target = target;
      this.addMemoryBlocksTarget = new OseAddMemoryBlocksTarget(this);
   }

   public static OseDebugTarget createDebugTarget(Target target)
   {
      return createDebugTarget(null, target);
   }

   public static OseDebugTarget createDebugTarget(Object debugContextProvider,
                                                  Target target)
   {
      OseDebugTarget debugTarget = null;

      if (target == null)
      {
         throw new NullPointerException();
      }

      synchronized (debugTargetMap)
      {
         debugTarget = (OseDebugTarget) debugTargetMap.get(target);
         if ((debugTarget == null) && !target.isKilled())
         {
            debugTarget = new OseDebugTarget(debugContextProvider, target);
            debugTargetMap.put(target, debugTarget);
            SystemModel.getInstance().addSystemModelListener(debugTarget);
         }
      }

      return debugTarget;
   }

   public Object getDebugContextProvider()
   {
      return debugContextProvider;
   }

   public Target getTarget()
   {
      return target;
   }

   public IProcess getProcess()
   {
      return null;
   }

   public IThread[] getThreads() throws DebugException
   {
      return new IThread[0];
   }

   public boolean hasThreads() throws DebugException
   {
      return false;
   }

   public String getName() throws DebugException
   {
      return target.toString();
   }

   public boolean supportsBreakpoint(IBreakpoint breakpoint)
   {
      return false;
   }

   public String getModelIdentifier()
   {
      return SystemBrowserPlugin.getUniqueIdentifier();
   }

   public IDebugTarget getDebugTarget()
   {
      return this;
   }

   public ILaunch getLaunch()
   {
      return null;
   }

   public boolean canTerminate()
   {
      return true;
   }

   public synchronized boolean isTerminated()
   {
      return terminated;
   }

   public synchronized void terminate() throws DebugException
   {
      if (!terminated)
      {
         terminated = true;
         SystemModel.getInstance().removeSystemModelListener(this);
         synchronized (debugTargetMap)
         {
            debugTargetMap.remove(target);
         }
         fireTerminateEvent();
      }
   }

   static void terminateAll()
   {
      List targets;

      synchronized (debugTargetMap)
      {
         targets = new ArrayList(debugTargetMap.values());
      }

      for (Iterator i = targets.iterator(); i.hasNext();)
      {
         OseDebugTarget target = (OseDebugTarget) i.next();
         try
         {
            target.terminate();
         }
         catch (DebugException ignore) {}
      }
   }

   public boolean canResume()
   {
      return false;
   }

   public boolean canSuspend()
   {
      return false;
   }

   public boolean isSuspended()
   {
      return false;
   }

   public void resume() throws DebugException
   {
   }

   public void suspend() throws DebugException
   {
   }

   public void breakpointAdded(IBreakpoint breakpoint)
   {
   }

   public void breakpointRemoved(IBreakpoint breakpoint, IMarkerDelta delta)
   {
   }

   public void breakpointChanged(IBreakpoint breakpoint, IMarkerDelta delta)
   {
   }

   public boolean canDisconnect()
   {
      return false;
   }

   public void disconnect() throws DebugException
   {
   }

   public boolean isDisconnected()
   {
      return !target.isConnected();
   }

   public boolean supportsStorageRetrieval()
   {
      return true;
   }

   public IMemoryBlock getMemoryBlock(long startAddress, long length)
         throws DebugException
   {
      return new OseMemoryBlock(this, 0, startAddress, length);
   }

   public IMemoryBlockExtension getExtendedMemoryBlock(String expression,
                                                       Object context)
         throws DebugException
   {
      int pid = 0;
      long address = 0;

      // Requires Eclipse 3.2 or later.
      if (debugContextProvider != null)
      {
         if (debugContextProvider instanceof OseDebugContextProvider)
         {
            OseObject object =
               ((OseDebugContextProvider) debugContextProvider).getActiveObject();
            if (object != null)
            {
               pid = object.getId();
            }
         }
      }

      try
      {
         address = Long.decode(expression.trim()).longValue();
      }
      catch (NumberFormatException e)
      {
         requestFailed("Invalid address", e);
      }

      return new OseMemoryBlock(this, pid, address);
   }

   public IMemoryBlockExtension getExtendedMemoryBlock(int pid,
                                                       long address,
                                                       long length)
         throws DebugException
   {
      return new OseMemoryBlock(this, pid, address, length);
   }

   public Object getAdapter(Class adapter)
   {
      if (ILaunch.class.equals(adapter))
      {
         return getLaunch();
      }
      else if (IDebugTarget.class.equals(adapter))
      {
         return this;
      }
      else if (IAddMemoryBlocksTarget.class.equals(adapter))
      {
         return addMemoryBlocksTarget;
      }
      else
      {
         return super.getAdapter(adapter);
      }
   }

   public void nodesChanged(SystemModelEvent event)
   {
   }

   public void nodesAdded(SystemModelEvent event)
   {
   }

   public void nodesRemoved(SystemModelEvent event)
   {
      if ((event.getParent() == target.getParent()) &&
          (event.getChildren().contains(target)))
      {
         try
         {
            terminate();
         }
         catch (DebugException ignore) {}
      }
   }
}
