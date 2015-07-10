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

public class TracebufferUtil
{
   public static String[] architectures = {"E500", "E500MC", "E600"};
   public static final int numRegsE500 = 292;   // this is the registers set size (numRegs * 4)
   public static final int numRegsE500MC = 412; // this is the registers set size (numRegs * 4)
   public static final int numRegsE600 = 932;   // this is the registers set size (numRegs * 4)
   
   public static final int pcOffsetE500 = 256;    // 4 * 32 + 4 * 32
   public static final int pcOffsetE500MC = 384;  // 4 * 32 + 8 * 32
   public static final int pcOffsetE600 = 384;    // 4 * 32 + 8 * 32
   
   
   public static final String REGISTER_MASK_E500 = "0FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFF";
   public static final String REGISTER_MASK_E500MC = "0FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFF";
   public static final String REGISTER_MASK_E600 = "0FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFF";
   
   //PVR values -> MonitorConnectReply->cpuType & 0xFFFF0000
   public static final int POWERPC_E500V1 = 0x80200000;
   public static final int POWERPC_E500V2 = 0x80210000;
   public static final int POWERPC_E500MC = 0x80230000;
   public static final int POWERPC_E600 = 0x80040000;
   
   
   public static final int TRACEPOINT_RUNNING = 0;
   public static final int TRACEPOINT_NOTRUN = 1;
   public static final int TRACEPOINT_STOP = 2;
   public static final int TRACEPOINT_FULL = 3;
   public static final int TRACEPOINT_PASSCOUNT = 4;
   public static final int TRACEPOINT_ERROR = 5;
   
   public static final String TRACEPOINT_RUNNING_GDB_STRING = "tunknown";
   public static final String TRACEPOINT_NOTRUN_GDB_STRING = "tnorrun";
   public static final String TRACEPOINT_STOP_GDB_STRING = "tstop";
   public static final String TRACEPOINT_FULL_GDB_STRING = "tfull";
   public static final String TRACEPOINT_PASSCOUNT_GDB_STRING = "tpasscount";
   public static final String TRACEPOINT_ERROR_GDB_STRING = "terror";
   
   public static final String TRACEPOINT_ERROR_DEFAULT_MESSAGE = "Tracepoint error: ";
   
   // We define this since we don't store variable names in OSE
   public static final String TRACE_STATE_VARIABLES_DEFAULT_PREFIX = "v";
   
   // Tracepoint constants
   public static final String TRACEPOINT_ENABLED = "E";
   public static final String TRACEPOINT_DISABLED = "D";
   
   public static final int MONITOR_TRACEPOINT_TRAP_BASED_TRACEPOINT = 0;
   public static final int MONITOR_TRACEPOINT_FAST_TRACEPOINT = 1;
   
   // Number of bytes displaced by the fast tracepoint
   public static final int FAST_TRACEPOINT_DISPLACEMENT = 4;
   
   public static final long MEMORY_IS_REGISTER_DEFAULT = 0;
   public static final long MEMORY_IS_NOT_REGISTER_DEFAULT = 1;
   
   public static final int MONITOR_TRACEPOINT_BUFFER_LINEAR = 1;
   public static final int MONITOR_TRACEPOINT_BUFFER_CIRCULAR = 2;
   
   //File header
   public static final String header = "TRACE0\n";
   
   // Number of OSE registers
   public static final int oseRegisters = 156; // this is the registers set size (numRegs * 4)
}
