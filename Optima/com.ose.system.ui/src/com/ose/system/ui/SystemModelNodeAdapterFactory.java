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

package com.ose.system.ui;

import org.eclipse.core.runtime.IAdapterFactory;
import org.eclipse.ui.IActionFilter;
import org.eclipse.ui.views.properties.IPropertySource;
import com.ose.system.Block;
import com.ose.system.Gate;
import com.ose.system.Heap;
import com.ose.system.Pool;
import com.ose.system.Process;
import com.ose.system.Segment;
import com.ose.system.SystemModelNode;
import com.ose.system.Target;
import com.ose.system.ui.properties.BlockPropertySource;
import com.ose.system.ui.properties.GatePropertySource;
import com.ose.system.ui.properties.HeapPropertySource;
import com.ose.system.ui.properties.PoolPropertySource;
import com.ose.system.ui.properties.ProcessPropertySource;
import com.ose.system.ui.properties.SegmentPropertySource;
import com.ose.system.ui.properties.TargetPropertySource;

public class SystemModelNodeAdapterFactory implements IAdapterFactory
{
   private static final Class[] ADAPTER_LIST =
      new Class[] {IPropertySource.class, IActionFilter.class};

   private static final IActionFilter SYSTEM_MODEL_NODE_ACTION_FILTER =
      new SystemModelNodeActionFilter();

   public Object getAdapter(Object adaptableObject, Class adapterType)
   {
      if (IPropertySource.class.equals(adapterType))
      {
         if (adaptableObject instanceof Gate)
         {
            return new GatePropertySource((Gate) adaptableObject);
         }
         else if (adaptableObject instanceof Target)
         {
            return new TargetPropertySource((Target) adaptableObject);
         }
         else if (adaptableObject instanceof Segment)
         {
            return new SegmentPropertySource((Segment) adaptableObject);
         }
         else if (adaptableObject instanceof Pool)
         {
            return new PoolPropertySource((Pool) adaptableObject);
         }
         else if (adaptableObject instanceof Heap)
         {
            return new HeapPropertySource((Heap) adaptableObject);
         }
         else if (adaptableObject instanceof Block)
         {
            return new BlockPropertySource((Block) adaptableObject);
         }
         else if (adaptableObject instanceof Process)
         {
            return new ProcessPropertySource((Process) adaptableObject);
         }
         else
         {
            return null;
         }
      }
      else if (IActionFilter.class.equals(adapterType))
      {
         if (adaptableObject instanceof SystemModelNode)
         {
            return SYSTEM_MODEL_NODE_ACTION_FILTER;
         }
         else
         {
            return null;
         }
      }
      else
      {
         return null;
      }
   }

   public Class[] getAdapterList()
   {
      return ADAPTER_LIST;
   }
}
