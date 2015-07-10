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

package com.ose.system.ui.views.system;

import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;
import com.ose.system.Block;
import com.ose.system.Gate;
import com.ose.system.Heap;
import com.ose.system.Pool;
import com.ose.system.Process;
import com.ose.system.Segment;
import com.ose.system.SystemModelNode;
import com.ose.system.Target;
import com.ose.system.ui.SharedImages;

public class SystemLabelProvider extends LabelProvider
{
   public String getText(Object obj)
   {
      return obj.toString();
   }

   public Image getImage(Object obj)
   {
      if (!(obj instanceof SystemModelNode))
      {
         return null;
      }

      SystemModelNode node = (SystemModelNode) obj;
      String imageKey = null;
      if (node instanceof Gate)
      {
         imageKey = (node.isKilled() ?
                     SharedImages.IMG_OBJ_GATE_KILLED :
                     SharedImages.IMG_OBJ_GATE);
      }
      else if (node instanceof Target)
      {
         imageKey = (node.isKilled() ?
                     SharedImages.IMG_OBJ_TARGET_KILLED :
                     SharedImages.IMG_OBJ_TARGET);
      }
      else if (node instanceof Segment)
      {
         imageKey = (node.isKilled() ?
                     SharedImages.IMG_OBJ_SEGMENT_KILLED :
                     SharedImages.IMG_OBJ_SEGMENT);
      }
      else if (node instanceof Pool)
      {
         imageKey = (node.isKilled() ?
                     SharedImages.IMG_OBJ_POOL_KILLED :
                     SharedImages.IMG_OBJ_POOL);
      }
      else if (node instanceof Heap)
      {
         imageKey = (node.isKilled() ?
                     SharedImages.IMG_OBJ_HEAP_KILLED :
                     SharedImages.IMG_OBJ_HEAP);
      }
      else if (node instanceof Block)
      {
         imageKey = (node.isKilled() ?
                     SharedImages.IMG_OBJ_BLOCK_KILLED :
                     SharedImages.IMG_OBJ_BLOCK);
      }
      else if (node instanceof Process)
      {
         Process process = (Process) node;
         if (process.isKilled())
         {
            switch (process.getType())
            {
               case Process.TYPE_IDLE:
                  // Fall through.
               case Process.TYPE_BACKGROUND:
                  imageKey = SharedImages.IMG_OBJ_PROCESS_BACKGROUND_KILLED;
                  break;
               case Process.TYPE_INTERRUPT:
                  imageKey = SharedImages.IMG_OBJ_PROCESS_INTERRUPT_KILLED;
                  break;
               case Process.TYPE_KILLED:
                  imageKey = SharedImages.IMG_OBJ_PROCESS_UNKNOWN_KILLED;
                  break;
               case Process.TYPE_PHANTOM:
                  imageKey = SharedImages.IMG_OBJ_PROCESS_PHANTOM_KILLED;
                  break;
               case Process.TYPE_PRIORITIZED:
                  imageKey = SharedImages.IMG_OBJ_PROCESS_PRIORITIZED_KILLED;
                  break;
               case Process.TYPE_SIGNAL_PORT:
                  imageKey = SharedImages.IMG_OBJ_PROCESS_SIGNALPORT_KILLED;
                  break;
               case Process.TYPE_TIMER_INTERRUPT:
                  imageKey = SharedImages.IMG_OBJ_PROCESS_TIMER_KILLED;
                  break;
               case Process.TYPE_UNKNOWN:
                  // Fall through.
               default:
                  imageKey = SharedImages.IMG_OBJ_PROCESS_UNKNOWN_KILLED;
                  break;
            }
         }
         else
         {
            int state = process.getState();
            switch (process.getType())
            {
               case Process.TYPE_IDLE:
                  // Fall through.
               case Process.TYPE_BACKGROUND:
                  if (((state & Process.STATE_STOPPED) != 0) ||
                      ((state & Process.STATE_INTERCEPTED) != 0))
                  {
                     imageKey = SharedImages.IMG_OBJ_PROCESS_BACKGROUND_STOPPED;
                  }
                  else if ((state & Process.STATE_RECEIVE) != 0)
                  {
                     imageKey = SharedImages.IMG_OBJ_PROCESS_BACKGROUND_RECEIVE;
                  }
                  else if ((state & Process.STATE_DELAY) != 0)
                  {
                     imageKey = SharedImages.IMG_OBJ_PROCESS_BACKGROUND_DELAY;
                  }
                  else if (((state & Process.STATE_SEMAPHORE) != 0) ||
                           ((state & Process.STATE_FAST_SEMAPHORE) != 0) ||
                           ((state & Process.STATE_MUTEX) != 0))
                  {
                     imageKey = SharedImages.IMG_OBJ_PROCESS_BACKGROUND_SEMAPHORE;
                  }
                  else if ((state & Process.STATE_REMOTE) != 0)
                  {
                     imageKey = SharedImages.IMG_OBJ_PROCESS_BACKGROUND_REMOTE;
                  }
                  else if (((state & Process.STATE_RUNNING) != 0) ||
                           ((state & Process.STATE_READY) != 0))
                  {
                     imageKey = SharedImages.IMG_OBJ_PROCESS_BACKGROUND_READY;
                  }
                  else if ((state & Process.STATE_KILLED) != 0)
                  {
                     imageKey = SharedImages.IMG_OBJ_PROCESS_BACKGROUND_KILLED;
                  }
                  else
                  {
                     imageKey = SharedImages.IMG_OBJ_PROCESS_UNKNOWN_UNKNOWN;
                  }
                  break;
               case Process.TYPE_INTERRUPT:
                  if (((state & Process.STATE_RUNNING) != 0) ||
                      ((state & Process.STATE_READY) != 0))
                  {
                     imageKey = SharedImages.IMG_OBJ_PROCESS_INTERRUPT_READY;
                  }
                  else if ((state & Process.STATE_KILLED) != 0)
                  {
                     imageKey = SharedImages.IMG_OBJ_PROCESS_INTERRUPT_KILLED;
                  }
                  else
                  {
                     imageKey = SharedImages.IMG_OBJ_PROCESS_UNKNOWN_UNKNOWN;
                  }
                  break;
               case Process.TYPE_KILLED:
                  imageKey = SharedImages.IMG_OBJ_PROCESS_UNKNOWN_KILLED;
                  break;
               case Process.TYPE_PHANTOM:
                  if ((state & Process.STATE_READY) != 0)
                  {
                     imageKey = SharedImages.IMG_OBJ_PROCESS_PHANTOM_READY;
                  }
                  else if ((state & Process.STATE_KILLED) != 0)
                  {
                     imageKey = SharedImages.IMG_OBJ_PROCESS_PHANTOM_KILLED;
                  }
                  else
                  {
                     imageKey = SharedImages.IMG_OBJ_PROCESS_UNKNOWN_UNKNOWN;
                  }
                  break;
               case Process.TYPE_PRIORITIZED:
                  if (((state & Process.STATE_STOPPED) != 0) ||
                      ((state & Process.STATE_INTERCEPTED) != 0))
                  {
                     imageKey = SharedImages.IMG_OBJ_PROCESS_PRIORITIZED_STOPPED;
                  }
                  else if ((state & Process.STATE_RECEIVE) != 0)
                  {
                     imageKey = SharedImages.IMG_OBJ_PROCESS_PRIORITIZED_RECEIVE;
                  }
                  else if ((state & Process.STATE_DELAY) != 0)
                  {
                     imageKey = SharedImages.IMG_OBJ_PROCESS_PRIORITIZED_DELAY;
                  }
                  else if (((state & Process.STATE_SEMAPHORE) != 0) ||
                           ((state & Process.STATE_FAST_SEMAPHORE) != 0) ||
                           ((state & Process.STATE_MUTEX) != 0))
                  {
                     imageKey = SharedImages.IMG_OBJ_PROCESS_PRIORITIZED_SEMAPHORE;
                  }
                  else if ((state & Process.STATE_REMOTE) != 0)
                  {
                     imageKey = SharedImages.IMG_OBJ_PROCESS_PRIORITIZED_REMOTE;
                  }
                  else if (((state & Process.STATE_RUNNING) != 0) ||
                           ((state & Process.STATE_READY) != 0))
                  {
                     imageKey = SharedImages.IMG_OBJ_PROCESS_PRIORITIZED_READY;
                  }
                  else if ((state & Process.STATE_KILLED) != 0)
                  {
                     imageKey = SharedImages.IMG_OBJ_PROCESS_PRIORITIZED_KILLED;
                  }
                  else
                  {
                     imageKey = SharedImages.IMG_OBJ_PROCESS_UNKNOWN_UNKNOWN;
                  }
                  break;
               case Process.TYPE_SIGNAL_PORT:
                  if ((state & Process.STATE_READY) != 0)
                  {
                     imageKey = SharedImages.IMG_OBJ_PROCESS_SIGNALPORT;
                  }
                  else if ((state & Process.STATE_KILLED) != 0)
                  {
                     imageKey = SharedImages.IMG_OBJ_PROCESS_SIGNALPORT_KILLED;
                  }
                  else
                  {
                     imageKey = SharedImages.IMG_OBJ_PROCESS_UNKNOWN_UNKNOWN;
                  }
                  break;
               case Process.TYPE_TIMER_INTERRUPT:
                  if (((state & Process.STATE_STOPPED) != 0) ||
                      ((state & Process.STATE_INTERCEPTED) != 0))
                  {
                     imageKey = SharedImages.IMG_OBJ_PROCESS_TIMER_STOPPED;
                  }
                  else if (((state & Process.STATE_RUNNING) != 0) ||
                           ((state & Process.STATE_READY) != 0))
                  {
                     imageKey = SharedImages.IMG_OBJ_PROCESS_TIMER_READY;
                  }
                  else if ((state & Process.STATE_KILLED) != 0)
                  {
                     imageKey = SharedImages.IMG_OBJ_PROCESS_TIMER_KILLED;
                  }
                  else
                  {
                     imageKey = SharedImages.IMG_OBJ_PROCESS_UNKNOWN_UNKNOWN;
                  }
                  break;
               case Process.TYPE_UNKNOWN:
                  // Fall through.
               default:
                  imageKey = SharedImages.IMG_OBJ_PROCESS_UNKNOWN_UNKNOWN;
                  break;
            }
         }
      }
      return SharedImages.get(imageKey);
   }
}
