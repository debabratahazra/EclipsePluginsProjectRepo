/* COPYRIGHT-ENEA-SRC-R1 *
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
package com.ose.system.service.tracepoint.signal;

import java.io.IOException;

import com.ose.gateway.Signal;
import com.ose.gateway.SignalInputStream;
import com.ose.gateway.SignalOutputStream;

public class MonitorTracepointGetActionsReply extends Signal
{

   public static final int SIG_NO = 40745;

   public int tracepointId; // U32
   public int actionId; // U32
   public int whileSteppingId; // U32
   public int actionType; // U32
   public MonitorTracepointActionData actionData;

   public MonitorTracepointGetActionsReply()
   {
      super(SIG_NO);
   }

   protected void read(SignalInputStream in) throws IOException
   {
      tracepointId = in.readS32();
      actionId = in.readS32();
      whileSteppingId = in.readS32();
      in.readS32(); // Unused U32 reserved0
      in.readS32(); // Unused U32 reserved1
      actionType = in.readS32();
      in.readS32(); // Unused U32 reserved2
      actionData = new MonitorTracepointActionData();
      if (actionType == MonitorTracepointActionData.MONITOR_TRACEPOINT_ACTION_WHILE_STEPPING)
      {
         actionData.whileStepping = actionData.new MonitorTracepointActionWhileStepping();
         actionData.whileStepping.stepCount = in.readS32();
      }
      else if (actionType == MonitorTracepointActionData.MONITOR_TRACEPOINT_ACTION_SAVE_MEMORY)
      {
         actionData.memory = actionData.new MonitorTracepointActionSaveMemory();
         actionData.memory.isRegister = in.readS32();
         actionData.memory.length = in.readS32();
         actionData.memory.base = in.readS64();
      }
      else if (actionType == MonitorTracepointActionData.MONITOR_TRACEPOINT_ACTION_EXPRESSION)
      {
         actionData.expression = actionData.new MonitorTracepointActionExpression();
         actionData.expression.byteCodeLength = in.readS32();
         actionData.expression.byteCode = in
               .readS8Array(actionData.expression.byteCodeLength);
      }

   }

   protected void write(SignalOutputStream out) throws IOException
   {
      out.writeS32(tracepointId);
      out.writeS32(actionId);
      out.writeS32(whileSteppingId);
      out.writeS32(0); // Unused U32 reserved0
      out.writeS32(0); // Unused U32 reserved1
      out.writeS32(actionType);
      out.writeS32(0); // Unused U32 reserved2
      if (actionType == MonitorTracepointActionData.MONITOR_TRACEPOINT_ACTION_WHILE_STEPPING)
      {
         out.writeS32(actionData.whileStepping.stepCount);
      }
      else if (actionType == MonitorTracepointActionData.MONITOR_TRACEPOINT_ACTION_SAVE_MEMORY)
      {
         out.writeS32(actionData.memory.isRegister);
         out.writeS32(actionData.memory.length);
         out.writeS64(actionData.memory.base);
      }
      else if (actionType == MonitorTracepointActionData.MONITOR_TRACEPOINT_ACTION_EXPRESSION)
      {
         out.writeS32(actionData.expression.byteCodeLength);
         out.writeS8Array(actionData.expression.byteCode);
      }
   }

}