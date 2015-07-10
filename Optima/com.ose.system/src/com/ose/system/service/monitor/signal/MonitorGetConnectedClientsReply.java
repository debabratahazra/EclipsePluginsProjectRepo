/* COPYRIGHT-ENEA-SRC-R1 *
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

package com.ose.system.service.monitor.signal;

import java.io.IOException;
import com.ose.gateway.Signal;
import com.ose.gateway.SignalInputStream;
import com.ose.gateway.SignalOutputStream;

public class MonitorGetConnectedClientsReply extends Signal
{
   public static final int SIG_NO = 40821;
   
   public int clientPid;    // U32
   public int offspringPid; // U32
   public int reserved0;    // U32
   public int scopeType;    // U32
   public int scopeId;      // U32
   public int interceptScopeType; // U32
   public int interceptScopeId;   // U32
   public int nbrOfBreakpoints;   // U32
   public int nbrOfTracepoints;   // U32
   public int nbrOfTracepointActions;   // U32
   public int nbrOfTracepointVariables; // U32
   public long tracepointBufferSize;    // U64
   public int tracepointBufferType;     // U32
   public int reserved1;  // U32
   public int reserved2;  // U32
   public int reserved3;  // U32
   public int nameLength; // U32
   public String clientName;  // char[nameLength]
   
   public MonitorGetConnectedClientsReply()
   {
      super(SIG_NO);
   }
   
   protected void read(SignalInputStream in) throws IOException
   {
      clientPid = in.readS32();
      offspringPid = in.readS32();
      reserved0 = in.readS32();
      scopeType = in.readS32();
      scopeId = in.readS32();
      interceptScopeType = in.readS32();
      interceptScopeId = in.readS32();
      nbrOfBreakpoints = in.readS32();
      nbrOfTracepoints = in.readS32();
      nbrOfTracepointActions = in.readS32();
      nbrOfTracepointVariables = in.readS32();
      tracepointBufferSize = in.readS64();
      tracepointBufferType = in.readS32();
      reserved1 = in.readS32();
      reserved2 = in.readS32();
      reserved3 = in.readS32();
      nameLength = in.readS32();
      clientName = in.readString();
   }

   protected void write(SignalOutputStream out) throws IOException
   {
      out.writeS32(clientPid);
      out.writeS32(offspringPid);
      out.writeS32(reserved0);
      out.writeS32(scopeType);
      out.writeS32(scopeId);
      out.writeS32(interceptScopeType);
      out.writeS32(interceptScopeId);
      out.writeS32(nbrOfBreakpoints);
      out.writeS32(nbrOfTracepoints);
      out.writeS32(nbrOfTracepointActions);
      out.writeS32(nbrOfTracepointVariables);
      out.writeS64(tracepointBufferSize);
      out.writeS32(tracepointBufferType);
      out.writeS32(reserved1);
      out.writeS32(reserved2);
      out.writeS32(reserved3);
      out.writeS32(nameLength);
      out.writeString((clientName != null) ? clientName : "");
   }
}