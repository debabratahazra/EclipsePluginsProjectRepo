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
import java.util.ListIterator;

import com.ose.pmd.tracebuffer.frame.TraceframeFormatter;
import com.ose.system.service.monitor.signal.MonitorGetConnectedClientsReply;
import com.ose.system.service.pm.signal.PmLoadModuleSectionInfoReply;
import com.ose.system.service.pm.signal.PmProgramInfoReply;
import com.ose.system.service.tracepoint.signal.MonitorTracepointGetActionsReply;
import com.ose.system.service.tracepoint.signal.MonitorTracepointGetAnnotationsReply;
import com.ose.system.service.tracepoint.signal.MonitorTracepointGetStatusReply;
import com.ose.system.service.tracepoint.signal.MonitorTracepointGetTracepointsReply;
import com.ose.system.service.tracepoint.signal.MonitorTracepointGetVariablesReply;


/* Encapsulates all the tracepoint experiment data */

public class ExperimentData {
   
   int cpuType;
   List<MonitorGetConnectedClientsReply> connectedClients;
   PmProgramInfoReply programInfo;
   PmLoadModuleSectionInfoReply sectionInfos;
   
   List<MonitorTracepointGetVariablesReply> traceVariables;
   List<TracepointData> tracepointData;
   
   MonitorTracepointGetStatusReply experimentStatus;
      
   int lastFrameTracepointId; // Needed for the status entry
   String stopErrorMessage; // Error messages are not defined so we use a default string and the error number
   
   public ExperimentData()
   {
      super();
      connectedClients = new LinkedList<MonitorGetConnectedClientsReply>();
      traceVariables = new LinkedList<MonitorTracepointGetVariablesReply>();
      tracepointData = new LinkedList<TracepointData>();
   }
   
   
   // cpuType
   public int getCpuType()
   {
      return cpuType;
   }
   
   public void setCpuType(int cpuType)
   {
      this.cpuType = cpuType;
   }

   // lastFrameTracepointId
   public int getLastFrameTracepointId()
   {
      return lastFrameTracepointId;
   }

   // stopErrorMessage
   public String getStopErrorMessage()
   {
      return stopErrorMessage;
   }
   
   // connectedClients
   public List<MonitorGetConnectedClientsReply> getConnectedClients()
   {
      return connectedClients;
   }

   public void addConnectedClient(MonitorGetConnectedClientsReply client)
   {
      this.connectedClients.add(client);
   }

   // programInfo
   public PmProgramInfoReply getProgramInfo()
   {
      return programInfo;
   }
   
   public void setProgramInfo(PmProgramInfoReply programInfo)
   {
      this.programInfo = programInfo;
   }
   
   // sectionInfos
   public PmLoadModuleSectionInfoReply getSectionInfos()
   {
      return sectionInfos;
   }
   
   public void addSectionInfos(PmLoadModuleSectionInfoReply sectionInfo)
   {
      this.sectionInfos = sectionInfo;
   }
   
   //traceVariables
   public List<MonitorTracepointGetVariablesReply> getTraceVariables()
   {
      return traceVariables;
   }

   public void addTraceVariable(MonitorTracepointGetVariablesReply traceVariable)
   {
      this.traceVariables.add(traceVariable);
   }
      
   // experimentStatus
   public MonitorTracepointGetStatusReply getExperimentStatus()
   {
      return experimentStatus;
   }
   
   public void setExperimentStatus(MonitorTracepointGetStatusReply experimentStatus)
   {
      this.experimentStatus = experimentStatus;
   }
      
   public void computeTracebufferEntrie(TraceframeFormatter tracebufferEntrie, boolean bigEndian)
   {
      TracepointData tpData = this.getTracepointData(tracebufferEntrie.tracepointId);
      tpData.incrementHitCount();
      
      /*
       * Calculate the actual size in the OSE tracebuffer
       */
      tpData.addUsedBuffer(tracebufferEntrie.computeSizeInOseFramebuffer(bigEndian));
      
      lastFrameTracepointId = tracebufferEntrie.tracepointId;
      if (tracebufferEntrie.errorCode != 0)
      {
         stopErrorMessage = TracebufferUtil.TRACEPOINT_ERROR_DEFAULT_MESSAGE + tracebufferEntrie.errorCode;
      }
   }
      
   // tracepointData
   public List<TracepointData> getTracepointData()
   {
      return tracepointData;
   }
   
   public TracepointData getTracepointData(int tracepointId)
   {
      TracepointData tpData = new TracepointData();
      ListIterator<TracepointData> tracepointDataIterator = (ListIterator<TracepointData>)tracepointData.iterator();
      
      while (tracepointDataIterator.hasNext())
      {
         tpData = tracepointDataIterator.next();
         if (tpData.getTracepoint().id == tracepointId)
         {
            return tpData;
         }
      }
      
      return null;
   }
   
   public void addTracepoint(MonitorTracepointGetTracepointsReply tracepoint)
   {
      if (getTracepointData(tracepoint.id) != null)
         return;
      
      TracepointData tpData = new TracepointData();
      tpData.setTracepoint(tracepoint);
      tracepointData.add(tpData);
   }
   
   public boolean addTracepointAction(int tracepointId, MonitorTracepointGetActionsReply action)
   {
      TracepointData tpData = this.getTracepointData(tracepointId);
      if (tpData != null)
      {
         tpData.addTracepointAction(action);
         return true;
      }
      return false;
   }
   
   public boolean addTracepointAnnotation(int tracepointId, MonitorTracepointGetAnnotationsReply annotations)
   {
      TracepointData tpData = this.getTracepointData(tracepointId);
      if (tpData != null)
      {
         tpData.setTracepointAnnotations(annotations);
         return true;
      }
      return false;
   }
   
}
