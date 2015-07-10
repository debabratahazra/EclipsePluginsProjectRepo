/* COPYRIGHT-ENEA-SRC-R1 *
 **************************************************************************
 * Copyright (C) 2012-2013 by Enea Software AB.
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

package com.ose.pmd.tracebuffer;

import java.util.LinkedList;
import java.util.List;
import com.ose.system.service.tracepoint.signal.MonitorTracepointActionData;
import com.ose.system.service.tracepoint.signal.MonitorTracepointGetActionsReply;
import com.ose.system.service.tracepoint.signal.MonitorTracepointGetAnnotationsReply;
import com.ose.system.service.tracepoint.signal.MonitorTracepointGetTracepointsReply;

public class TracepointData
{
   
   MonitorTracepointGetTracepointsReply tracepoint;
   List<MonitorTracepointGetActionsReply> tracepointActions;
   MonitorTracepointGetAnnotationsReply tracepointAnnotations;
   int stepCount = 0; // step count of the while-stepping action of this tracepoint
   int whileSteppingId = -1;
   
   int hitCount = 0; // not the real hit count but the number of frames in the buffer for this tracepoint
   int usedBuffer = 0; // total used buffer by frames for this tracepoint
   

   public TracepointData()
   {
      super();
      tracepointActions = new LinkedList<MonitorTracepointGetActionsReply>();
   }
   
   // tracepoint
   public MonitorTracepointGetTracepointsReply getTracepoint()
   {
      return tracepoint;
   }
   
   public void setTracepoint(MonitorTracepointGetTracepointsReply tracepoint)
   {
      this.tracepoint = tracepoint;
   }
   
   
   //tracepointActions
   public List<MonitorTracepointGetActionsReply> getTracepointActions()
   {
      return tracepointActions;
   }
   
   public void addTracepointAction(MonitorTracepointGetActionsReply action)
   {
      if (action.actionType == MonitorTracepointActionData.MONITOR_TRACEPOINT_ACTION_WHILE_STEPPING)
      {
         this.stepCount = action.actionData.whileStepping.stepCount;
         this.whileSteppingId = action.actionId;
      }
      tracepointActions.add(action);
   }
   
   
   //tracepointAnnotations
   public MonitorTracepointGetAnnotationsReply getTracepointAnnotations()
   {
      return tracepointAnnotations;
   }
   
   public void setTracepointAnnotations(MonitorTracepointGetAnnotationsReply tracepointAnnotations)
   {
      this.tracepointAnnotations = tracepointAnnotations;
   }

   //stepCount
   public int getStepCount()
   {
      return stepCount;
   }

   //whileSteppingId
   public int getWhileSteppingId()
   {
      return whileSteppingId;
   }
   
   //hitCount
   public int getHitCount()
   {
      return hitCount;
   }
   
   public void incrementHitCount()
   {
      this.hitCount++;
   }

   //usedBuffer
   public int getUsedBuffer()
   {
      return usedBuffer;
   }
   
   public void addUsedBuffer(int used)
   {
      this.usedBuffer += used;
   }
   
}

