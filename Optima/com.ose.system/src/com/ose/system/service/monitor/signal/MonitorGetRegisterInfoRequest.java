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

package com.ose.system.service.monitor.signal;

import java.io.IOException;
import com.ose.gateway.Signal;
import com.ose.gateway.SignalInputStream;
import com.ose.gateway.SignalOutputStream;

public class MonitorGetRegisterInfoRequest extends Signal
{
   public static final int SIG_NO = 39121;

   public int scopeType;   // U32
   public int scopeId;     // U32
   public int[] registers; // U32 (array of register ids)

   public MonitorGetRegisterInfoRequest()
   {
      super(SIG_NO);
   }

   public MonitorGetRegisterInfoRequest(int scopeType,
                                        int scopeId,
                                        int[] registers)
   {
      super(SIG_NO);
      this.scopeType = scopeType;
      this.scopeId = scopeId;
      this.registers = registers;
   }

   protected void read(SignalInputStream in) throws IOException
   {
      int registersCount;

      in.readS32(); // Unused U32 reserved0
      scopeType = in.readS32();
      scopeId = in.readS32();
      in.readS32(); // Unused U32 reserved1
      registersCount = in.readS32();
      registers = in.readS32Array(registersCount);
   }

   protected void write(SignalOutputStream out) throws IOException
   {
      out.writeS32(0); // Unused U32 reserved0
      out.writeS32(scopeType);
      out.writeS32(scopeId);
      out.writeS32(0); // Unused U32 reserved1
      out.writeS32(registers.length);
      out.writeS32Array(registers);
   }
}
