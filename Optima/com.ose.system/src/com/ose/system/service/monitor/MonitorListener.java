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

package com.ose.system.service.monitor;

import com.ose.gateway.Signal;

public interface MonitorListener
{
   public void exceptionThrown(Throwable e);

   public void monitorKilled();

   public void getProcessInfoReply(int pid,
                                   int bid,
                                   int sid,
                                   int type,
                                   int state,
                                   int priority,
                                   int entrypoint,
                                   int properties,
                                   String name);

   public void getProcessInfoLongReply(int pid,
                                       int bid,
                                       int sid,
                                       int type,
                                       int state,
                                       int priority,
                                       int uid,
                                       int creator,
                                       boolean supervisor,
                                       int signalsInQueue,
                                       int signalsAttached,
                                       int entrypoint,
                                       int semaphoreValue,
                                       int stackSize,
                                       int supervisorStackSize,
                                       int lineNumber,
                                       int signalsOwned,
                                       int timeSlice,
                                       int vector,
                                       int errorHandler,
                                       int properties,
                                       short euId,
                                       int[] sigselect,
                                       String processName,
                                       String fileName);

   public void getProcessInfoEndmark(int status);

   public void getBlockInfoReply(int bid,
                                 int sid,
                                 int uid,
                                 boolean supervisor,
                                 int signalsAttached,
                                 int errorHandler,
                                 int maxSigSize,
                                 int sigPoolId,
                                 int stackPoolId,
                                 short euId,
                                 String name);

   public void getBlockInfoEndmark(int status);

   public void getSegmentInfoReply(int sid, int number);

   public void getSegmentInfoLongReply(int sid,
                                       int number,
                                       int signalsAttached,
                                       boolean masMapped,
                                       String name);

   public void getSegmentInfoEndmark(int status);

   public void getStackUsageReply(int pid,
                                  MonitorStackInfo main,
                                  MonitorStackInfo supervisor);

   public void getStackUsageEndmark(int status);

   public void getEnvVarsReply(int pid, String name, String value);

   public void getEnvVarsEndmark(int status);

   public void getSignalQueueReply(int pid, MonitorSignalInfo signal);

   public void getSignalQueueLongReply(int pid,
                                       MonitorSignalInfo signal,
                                       byte[] data);

   public void getSignalQueueEndmark(int status);

   public void getPoolInfoReply(int pid,
                                int sid,
                                int total,
                                int free,
                                int stackAlignment,
                                int stackAdmSize,
                                int stackInternalAdmSize,
                                int signalAlignment,
                                int signalAdmSize,
                                int signalInternalAdmSize,
                                MonitorPoolFragmentInfo[] fragments,
                                MonitorPoolBufferSizeInfo[] stackSizes,
                                MonitorPoolBufferSizeInfo[] signalSizes);
   
   public void getPoolInfo64Reply(int pid,
           						  int sid,
					              long total,
					              long free,
					              int stackAlignment,
					              int stackAdmSize,
					              int stackInternalAdmSize,
					              int signalAlignment,
					              int signalAdmSize,
					              int signalInternalAdmSize,
					              MonitorPoolFragmentInfo64[] fragments,
					              MonitorPoolBufferSizeInfo[] stackSizes,
					              MonitorPoolBufferSizeInfo[] signalSizes);

   public void getPoolInfoEndmark(int status);

   public void getPoolSignalsReply(int pid, MonitorSignalInfo[] signals);

   public void getPoolSignalsEndmark(int status);

   public void getHeapInfoReply(int id,
                                int owner,
                                int sid,
                                int size,
                                int usedSize,
                                int peakUsedSize,
                                int reqSize,
                                int largestFree,
                                int largeThreshold,
                                int priv,
                                int shared,
                                int mallocFailed,
                                MonitorHeapBufferSizeInfo[] bufferSizes);
   
   public void getHeapInfo64Reply(int id,
					              int owner,
					              int sid,
				   	              long size,
					              long usedSize,
				   	              long peakUsedSize,
					              long reqSize,
					              long largestFree,
					              long largeThreshold,
					              int priv,
					              int shared,
					              int mallocFailed,
					              MonitorHeapBufferSizeInfo64[] bufferSizes);

   public void getHeapInfoEndmark(int status);

   public void getHeapFragmentInfoReply(int id,
                                        MonitorHeapFragmentInfo[] fragments);
   
   public void getHeapFragmentInfo64Reply(int id,
                                        MonitorHeapFragmentInfo64[] fragments);

   public void getHeapFragmentInfoEndmark(int status);

   public void getHeapBuffersReply(int id, MonitorHeapBufferInfo[] buffers);

   public void getHeapBuffersEndmark(int status);

   public void getSysParamReply(String name, String value);

   public void getSysParamEndmark(int status);

   public void setSysParamReply(int status);

   public void getMemoryReply(int pid, long address, byte[] data);

   public void getMemoryEndmark(int status);

   public void killScopeReply(int status);

   public void startScopeReply(int status);

   public void stopScopeReply(int status);

   public void setEnvVarReply(int status);

   public void signalSemaphoreReply(int status);

   public void setPriorityReply(int status);

   public void setExecutionUnitReply(int status);

   public void getRamdumpInfoReply(int dumpId, int size, int sec, int microsec);

   public void getRamdumpInfoEndmark(int status);

   public void getRamdumpReply(int dumpId, int offset, byte[] data);

   public void getRamdumpEndmark(int status);

   public void getCPUReportsEnabledReply(boolean enabled,
                                         int interval,
                                         int priority,
                                         int maxReports,
                                         int sec,
                                         int sectick,
                                         int secntick);

   public void setCPUReportsEnabledReply(int status);

   public void getCPUReportsReply(int status,
                                  boolean enabled,
                                  short euId,
                                  MonitorCPUReport[] reports);

   public void getCPUPriReportsEnabledReply(boolean enabled,
                                            int interval,
                                            int maxReports,
                                            int sec,
                                            int sectick,
                                            int secntick);

   public void setCPUPriReportsEnabledReply(int status);

   public void getCPUPriReportsReply(int status,
                                     boolean enabled,
                                     short euId,
                                     MonitorCPUPriReport[] reports);

   public void getCPUProcessReportsEnabledReply(boolean enabled,
                                                int interval,
                                                int maxReports,
                                                int maxProcsReport,
                                                int sec,
                                                int sectick,
                                                int secntick);

   public void setCPUProcessReportsEnabledReply(int status);

   public void setCPUProcessReportpointReply(int status, int id);

   public void clearCPUProcessReportpointReply(int status);

   public void getCPUProcessReportpointInfoReply(int scopeType,
                                                 int scopeId,
                                                 int id,
                                                 MonitorTag[] tags);

   public void getCPUProcessReportpointInfoEndmark(int status);

   public void getCPUProcessReportsReply(int status,
                                         boolean enabled,
                                         short euId,
                                         MonitorCPUProcessReport[] reports);

   public void getCPUBlockReportsEnabledReply(boolean enabled,
                                              int interval,
                                              int maxReports,
                                              int maxBlocksReport,
                                              int sec,
                                              int sectick,
                                              int secntick);

   public void setCPUBlockReportsEnabledReply(int status);

   public void getCPUBlockReportsReply(int status,
                                       boolean enabled,
                                       short euId,
                                       MonitorCPUBlockReport[] reports);

   public void getUserReportsEnabledReply(int reportNo,
                                          boolean enabled,
                                          int interval,
                                          int maxReports,
                                          int maxValuesReport,
                                          int sec,
                                          int sectick,
                                          int secntick);

   public void setUserReportsEnabledReply(int status);

   public void getUserReportsReply(int status,
                                   int reportNo,
                                   boolean enabled,
                                   boolean continuous,
                                   boolean maxmin,
                                   boolean multiple,
                                   MonitorUserReport[] reports);

   public void getSupportedCounterTypesReply(int counterType, String name);

   public void getSupportedCounterTypesEndmark(int status);

   public void getCounterTypeEnabledReply(int status,
                                          boolean enabled,
                                          short euId,
                                          int counterType,
                                          long counterValue,
                                          int maxReports);

   public void setCounterTypeEnabledReply(int status);

   public void getCounterReportsNotifyEnabledReply(int status,
                                                   boolean enabled,
                                                   short euId,
                                                   int counterType);

   public void setCounterReportsNotifyEnabledReply(int status);

   public void counterReportsNotify(short euId,
                                    int counterType,
                                    MonitorCounterReport[] reports);

   public void counterReportsLossNotify(short euId,
                                        int counterType,
                                        int reportsLost);

   public void attachReply(int status, int processCacheCount);

   public void detachReply(int status);

   public void interceptScopeReply(int status);

   public void resumeScopeReply(int status);

   public void setScopeReply(int status);

   public void setInterceptScopeReply(int status);

   public void setEventActionpointReply(int status, int id);

   public void clearEventActionpointReply(int status);

   public void getSendActionpointInfoReply(int state,
                                           int fromType,
                                           int fromId,
                                           int toType,
                                           int toId,
                                           boolean not,
                                           int action,
                                           int interceptType,
                                           int interceptId,
                                           int parameter,
                                           int client,
                                           int id,
                                           int count,
                                           MonitorTag[] tags);

   public void getReceiveActionpointInfoReply(int state,
                                              int fromType,
                                              int fromId,
                                              int toType,
                                              int toId,
                                              boolean not,
                                              int action,
                                              int interceptType,
                                              int interceptId,
                                              int parameter,
                                              int client,
                                              int id,
                                              int count,
                                              MonitorTag[] tags);

   public void getSwapActionpointInfoReply(int state,
                                           int fromType,
                                           int fromId,
                                           int toType,
                                           int toId,
                                           boolean not,
                                           int action,
                                           int interceptType,
                                           int interceptId,
                                           int parameter,
                                           int client,
                                           int id,
                                           int count,
                                           MonitorTag[] tags);

   public void getCreateActionpointInfoReply(int state,
                                             int fromType,
                                             int fromId,
                                             int toType,
                                             int toId,
                                             boolean not,
                                             int action,
                                             int interceptType,
                                             int interceptId,
                                             int parameter,
                                             int client,
                                             int id,
                                             int count,
                                             MonitorTag[] tags);

   public void getKillActionpointInfoReply(int state,
                                           int fromType,
                                           int fromId,
                                           int toType,
                                           int toId,
                                           boolean not,
                                           int action,
                                           int interceptType,
                                           int interceptId,
                                           int parameter,
                                           int client,
                                           int id,
                                           int count,
                                           MonitorTag[] tags);

   public void getErrorActionpointInfoReply(int state,
                                            int fromType,
                                            int fromId,
                                            int toType,
                                            int toId,
                                            boolean not,
                                            int action,
                                            int interceptType,
                                            int interceptId,
                                            int parameter,
                                            int client,
                                            int id,
                                            int count,
                                            MonitorTag[] tags);

   public void getEventActionpointInfoEndmark(int status);

   public void getEventActionpointsReply(int type,
                                         int state,
                                         int fromType,
                                         int fromId,
                                         int toType,
                                         int toId,
                                         boolean not,
                                         int action,
                                         int interceptType,
                                         int interceptId,
                                         int parameter,
                                         int client,
                                         int id,
                                         int count,
                                         MonitorTag[] tags);

   public void getEventActionpointsEndmark(int status);

   public void getEventStateReply(int status, int state);

   public void setEventStateReply(int status);

   public void getNotifyEnabledReply(int status, boolean enabled);

   public void setNotifyEnabledReply(int status);

   public void processNotify(int pid,
                             int bid,
                             int sid,
                             int type,
                             int entrypoint,
                             int properties,
                             String name);

   public void sendNotify(int reference,
                          int tick,
                          int ntick,
                          int sec,
                          int sectick,
                          int signalNumber,
                          int signalSender,
                          int signalAddressee,
                          int signalSize,
                          int signalAddress,
                          int signalId,
                          int lineNumber,
                          short euId,
                          String fileName,
                          byte[] signalData);

   public void receiveNotify(int reference,
                             int tick,
                             int ntick,
                             int sec,
                             int sectick,
                             int signalNumber,
                             int signalSender,
                             int signalAddressee,
                             int signalSize,
                             int signalAddress,
                             int signalId,
                             int lineNumber,
                             short euId,
                             String fileName,
                             byte[] signalData);

   public void swapNotify(int reference,
                          int tick,
                          int ntick,
                          int sec,
                          int sectick,
                          int from,
                          int to,
                          int lineNumber,
                          short euId,
                          String fileName);

   public void createNotify(int reference,
                            int tick,
                            int ntick,
                            int sec,
                            int sectick,
                            int from,
                            int to,
                            int lineNumber,
                            short euId,
                            String fileName);

   public void killNotify(int reference,
                          int tick,
                          int ntick,
                          int sec,
                          int sectick,
                          int from,
                          int to,
                          int lineNumber,
                          short euId,
                          String fileName);

   public void errorNotify(int reference,
                           int tick,
                           int ntick,
                           int sec,
                           int sectick,
                           int from,
                           boolean exec,
                           int error,
                           int extra,
                           int lineNumber,
                           short euId,
                           String fileName);

   public void userNotify(int reference,
                          int tick,
                          int ntick,
                          int sec,
                          int sectick,
                          int from,
                          int eventNumber,
                          int eventSize,
                          int lineNumber,
                          short euId,
                          String fileName,
                          byte[] eventData);

   public void bindNotify(int reference,
                          int tick,
                          int ntick,
                          int sec,
                          int sectick,
                          int from,
                          short fromEuId,
                          short toEuId,
                          int lineNumber,
                          String fileName);

   public void allocNotify(int reference,
                           int tick,
                           int ntick,
                           int sec,
                           int sectick,
                           int from,
                           int signalNumber,
                           int signalSize,
                           int signalAddress,
                           int signalId,
                           int lineNumber,
                           short euId,
                           String fileName);

   public void freeBufNotify(int reference,
                             int tick,
                             int ntick,
                             int sec,
                             int sectick,
                             int from,
                             int signalNumber,
                             int signalSize,
                             int signalAddress,
                             int signalId,
                             int lineNumber,
                             short euId,
                             String fileName);

   public void timeoutNotify(int reference,
                             int tick,
                             int ntick,
                             int sec,
                             int sectick,
                             int timeout,
                             int tmoSource,
                             int from,
                             int lineNumber,
                             short euId,
                             String fileName);

   public void lossNotify(int reference,
                          int tick,
                          int ntick,
                          int sec,
                          int sectick,
                          int lostSize);

   public void processInterceptNotify(int pid,
                                      int bid,
                                      int sid,
                                      int type,
                                      int entrypoint,
                                      int properties,
                                      String name);

   public void sendInterceptNotify(int reference,
                                   int tick,
                                   int ntick,
                                   int sec,
                                   int sectick,
                                   int signalNumber,
                                   int signalSender,
                                   int signalAddressee,
                                   int signalSize,
                                   int signalAddress,
                                   int signalId,
                                   int lineNumber,
                                   short euId,
                                   String fileName,
                                   byte[] signalData);

   public void receiveInterceptNotify(int reference,
                                      int tick,
                                      int ntick,
                                      int sec,
                                      int sectick,
                                      int signalNumber,
                                      int signalSender,
                                      int signalAddressee,
                                      int signalSize,
                                      int signalAddress,
                                      int signalId,
                                      int lineNumber,
                                      short euId,
                                      String fileName,
                                      byte[] signalData);

   public void swapInterceptNotify(int reference,
                                   int tick,
                                   int ntick,
                                   int sec,
                                   int sectick,
                                   int from,
                                   int to,
                                   int lineNumber,
                                   short euId,
                                   String fileName);

   public void createInterceptNotify(int reference,
                                     int tick,
                                     int ntick,
                                     int sec,
                                     int sectick,
                                     int from,
                                     int to,
                                     int lineNumber,
                                     short euId,
                                     String fileName);

   public void killInterceptNotify(int reference,
                                   int tick,
                                   int ntick,
                                   int sec,
                                   int sectick,
                                   int from,
                                   int to,
                                   int lineNumber,
                                   short euId,
                                   String fileName);

   public void errorInterceptNotify(int reference,
                                    int tick,
                                    int ntick,
                                    int sec,
                                    int sectick,
                                    int from,
                                    boolean exec,
                                    int error,
                                    int extra,
                                    int lineNumber,
                                    short euId,
                                    String fileName);

   public void userInterceptNotify(int reference,
                                   int tick,
                                   int ntick,
                                   int sec,
                                   int sectick,
                                   int from,
                                   int eventNumber,
                                   int eventSize,
                                   int lineNumber,
                                   short euId,
                                   String fileName,
                                   byte[] eventData);

   public void bindInterceptNotify(int reference,
                                   int tick,
                                   int ntick,
                                   int sec,
                                   int sectick,
                                   int from,
                                   short fromEuId,
                                   short toEuId,
                                   int lineNumber,
                                   String fileName);

   public void allocInterceptNotify(int reference,
                                    int tick,
                                    int ntick,
                                    int sec,
                                    int sectick,
                                    int from,
                                    int signalNumber,
                                    int signalSize,
                                    int signalAddress,
                                    int signalId,
                                    int lineNumber,
                                    short euId,
                                    String fileName);

   public void freeBufInterceptNotify(int reference,
                                      int tick,
                                      int ntick,
                                      int sec,
                                      int sectick,
                                      int from,
                                      int signalNumber,
                                      int signalSize,
                                      int signalAddress,
                                      int signalId,
                                      int lineNumber,
                                      short euId,
                                      String fileName);

   public void timeoutInterceptNotify(int reference,
                                      int tick,
                                      int ntick,
                                      int sec,
                                      int sectick,
                                      int timeout,
                                      int tmoSource,
                                      int from,
                                      int lineNumber,
                                      short euId,
                                      String fileName);

   public void getTraceEnabledReply(int status, boolean enabled);

   public void setTraceEnabledReply(int status);

   public void clearTraceReply(int status);

   public void getProcessTraceReply(int pid,
                                    int bid,
                                    int sid,
                                    int type,
                                    int entrypoint,
                                    int properties,
                                    String name);

   public void getSendTraceReply(int reference,
                                 int tick,
                                 int ntick,
                                 int sec,
                                 int sectick,
                                 int signalNumber,
                                 int signalSender,
                                 int signalAddressee,
                                 int signalSize,
                                 int signalAddress,
                                 int signalId,
                                 int lineNumber,
                                 short euId,
                                 String fileName,
                                 byte[] signalData);

   public void getReceiveTraceReply(int reference,
                                    int tick,
                                    int ntick,
                                    int sec,
                                    int sectick,
                                    int signalNumber,
                                    int signalSender,
                                    int signalAddressee,
                                    int signalSize,
                                    int signalAddress,
                                    int signalId,
                                    int lineNumber,
                                    short euId,
                                    String fileName,
                                    byte[] signalData);

   public void getSwapTraceReply(int reference,
                                 int tick,
                                 int ntick,
                                 int sec,
                                 int sectick,
                                 int from,
                                 int to,
                                 int lineNumber,
                                 short euId,
                                 String fileName);

   public void getCreateTraceReply(int reference,
                                   int tick,
                                   int ntick,
                                   int sec,
                                   int sectick,
                                   int from,
                                   int to,
                                   int lineNumber,
                                   short euId,
                                   String fileName);

   public void getKillTraceReply(int reference,
                                 int tick,
                                 int ntick,
                                 int sec,
                                 int sectick,
                                 int from,
                                 int to,
                                 int lineNumber,
                                 short euId,
                                 String fileName);

   public void getErrorTraceReply(int reference,
                                  int tick,
                                  int ntick,
                                  int sec,
                                  int sectick,
                                  int from,
                                  boolean exec,
                                  int error,
                                  int extra,
                                  int lineNumber,
                                  short euId,
                                  String fileName);

   public void getResetTraceReply(int reference,
                                  int tick,
                                  int ntick,
                                  int sec,
                                  int sectick,
                                  boolean warm,
                                  int lineNumber,
                                  String fileName);

   public void getUserTraceReply(int reference,
                                 int tick,
                                 int ntick,
                                 int sec,
                                 int sectick,
                                 int from,
                                 int eventNumber,
                                 int eventSize,
                                 int lineNumber,
                                 short euId,
                                 String fileName,
                                 byte[] eventData);

   public void getBindTraceReply(int reference,
                                 int tick,
                                 int ntick,
                                 int sec,
                                 int sectick,
                                 int from,
                                 short fromEuId,
                                 short toEuId,
                                 int lineNumber,
                                 String fileName);

   public void getAllocTraceReply(int reference,
                                  int tick,
                                  int ntick,
                                  int sec,
                                  int sectick,
                                  int from,
                                  int signalNumber,
                                  int signalSize,
                                  int signalAddress,
                                  int signalId,
                                  int lineNumber,
                                  short euId,
                                  String fileName);

   public void getFreeBufTraceReply(int reference,
                                    int tick,
                                    int ntick,
                                    int sec,
                                    int sectick,
                                    int from,
                                    int signalNumber,
                                    int signalSize,
                                    int signalAddress,
                                    int signalId,
                                    int lineNumber,
                                    short euId,
                                    String fileName);

   public void getTimeoutTraceReply(int reference,
                                    int tick,
                                    int ntick,
                                    int sec,
                                    int sectick,
                                    int timeout,
                                    int tmoSource,
                                    int from,
                                    int lineNumber,
                                    short euId,
                                    String fileName);

   public void getLossTraceReply(int reference,
                                 int tick,
                                 int ntick,
                                 int sec,
                                 int sectick,
                                 int lostSize);

   public void getTraceEndmark(int status);

   public void getTraceMultipleReply(int status,
                                     int handle,
                                     boolean enabled,
                                     Signal[] events);
}
