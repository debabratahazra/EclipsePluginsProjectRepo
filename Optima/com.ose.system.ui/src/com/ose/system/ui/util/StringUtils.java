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

package com.ose.system.ui.util;

import java.math.BigInteger;
import java.text.NumberFormat;
import java.util.Locale;
import com.ose.system.HeapBufferInfo;
import com.ose.system.LoadModuleInfo;
import com.ose.system.Process;
import com.ose.system.SignalInfo;
import com.ose.system.Target;

public class StringUtils
{
   public static final String NOT_AVAILABLE = "Not Available";

   public static final String N_A = "N/A";

   public static final String ALL = "All";

   private static final NumberFormat percent;

   static
   {
      percent = NumberFormat.getPercentInstance(Locale.US);
      percent.setMinimumFractionDigits(1);
      percent.setMaximumFractionDigits(1);
   }

   public static String toHexString(int i)
   {
      return "0x" + Integer.toHexString(i).toUpperCase();
   }

   public static String toHexString(long l)
   {
      return "0x" + Long.toHexString(l).toUpperCase();
   }

   public static String toU32String(long l)
   {
      return Long.toString(l & 0xFFFFFFFFL);
   }

   public static String toPercentString(double dividend, double divisor)
   {
      double value = ((divisor != 0) ? (dividend / divisor) : 0);
      return percent.format(value);
   }

   public static String intArrayToString(int[] array)
   {
      StringBuffer sb = new StringBuffer();
      for (int i = 0; i < array.length; i++)
      {
         sb.append(array[i] + " ");
      }
      return sb.toString();
   }

   public static String intArrayToHexString(int[] array)
   {
      StringBuffer sb = new StringBuffer();
      for (int i = 0; i < array.length; i++)
      {
         sb.append(toHexString(array[i]) + " ");
      }
      return sb.toString();
   }

   public static String toKilledString(boolean killed)
   {
      return (killed ? "Yes" : "No");
   }

   public static String toEndianString(boolean bigEndian)
   {
      return (bigEndian ? "Big Endian" : "Little Endian");
   }

   public static String toOsTypeString(int osType)
   {
      switch (osType)
      {
         case Target.OS_OSE_5:
            return "OSE 5";
         case Target.OS_OSE_4:
            return "OSE 4";
         case Target.OS_OSE_EPSILON:
            return "OSE Epsilon";
         case Target.OS_OSE_CK:
            return "OSEck";
         case Target.OS_UNKNOWN:
            // Fall through.
         default:
            return "Unknown";
      }
   }

   public static String toCpuTypeString(int cpuType)
   {
      switch (cpuType)
      {
         case Target.CPU_PPC:
            return "PowerPC";
         case Target.CPU_M68K:
            return "68000";
         case Target.CPU_ARM:
            return "ARM";
         case Target.CPU_MIPS:
            return "MIPS";
         case Target.CPU_X86:
            return "x86";
         case Target.CPU_SPARC:
            return "SPARC";
         case Target.CPU_C64X:
            return "TMS320c64x";
         case Target.CPU_C64XP:
            return "TMS320c64x+";
         case Target.CPU_SP26XX:
            return "SP26xx";
         case Target.CPU_SP27XX:
            return "SP27xx";
         case Target.CPU_TL3:
            return "Ceva-TL3";
         case Target.CPU_CEVAX:
            return "Ceva-X";
         case Target.CPU_M8144:
            return "MSC8144";
         case Target.CPU_M8156:
            return "MSC8156";
         case Target.CPU_C66X:
            return "TMS320c66x";
         case Target.CPU_C674X:
            return "TMS320c674x";
         case Target.CPU_UNKNOWN:
            // Fall through.
         default:
            return "Unknown";
      }
   }

   public static String toScopeString(int scopeType, int scopeId)
   {
      switch (scopeType)
      {
         case Target.SCOPE_SYSTEM:
            return "System";
         case Target.SCOPE_SEGMENT_ID:
            return "Segment " + toHexString(scopeId);
         case Target.SCOPE_BLOCK_ID:
            return "Block " + toHexString(scopeId);
         case Target.SCOPE_ID:
            return "Process " + toHexString(scopeId);
         default:
            return "Unknown";
      }
   }

   public static String toProcessTypeString(int type)
   {
      switch (type)
      {
         case Process.TYPE_PRIORITIZED:
            return "Prioritized";
         case Process.TYPE_BACKGROUND:
            return "Background";
         case Process.TYPE_INTERRUPT:
            return "Interrupt";
         case Process.TYPE_TIMER_INTERRUPT:
            return "Timer Interrupt";
         case Process.TYPE_PHANTOM:
            return "Phantom";
         case Process.TYPE_SIGNAL_PORT:
            return "Signal Port";
         case Process.TYPE_IDLE:
            return "Idle";
         case Process.TYPE_KILLED:
            return "Killed";
         case Process.TYPE_UNKNOWN:
            // Fall through.
         default:
            return "Unknown";
      }
   }

   public static String toProcessStateString(int state)
   {
      String str = "";

      if ((state & Process.STATE_INTERCEPTED) != 0)
      {
         str += "Intercepted ";
      }
      if ((state & Process.STATE_STOPPED) != 0)
      {
         str += "Stopped ";
      }
      if ((state & Process.STATE_RECEIVE) != 0)
      {
         str += "Receive ";
      }
      if ((state & Process.STATE_DELAY) != 0)
      {
         str += "Delay ";
      }
      if ((state & Process.STATE_SEMAPHORE) != 0)
      {
         str += "Semaphore ";
      }
      if ((state & Process.STATE_FAST_SEMAPHORE) != 0)
      {
         str += "Fast Semaphore ";
      }
      if ((state & Process.STATE_REMOTE) != 0)
      {
         str += "Remote ";
      }
      if ((state & Process.STATE_MUTEX) != 0)
      {
         str += "Mutex ";
      }
      if ((state & Process.STATE_RUNNING) != 0)
      {
         str += "Running ";
      }
      if ((state & Process.STATE_READY) != 0 ||
          (state & ~(Process.STATE_INTERCEPTED | Process.STATE_STOPPED)) == 0)
      {
         str += "Ready";
      }
      if (state == Process.STATE_KILLED)
      {
         str = "Killed";
      }
      if (str.length() == 0)
      {
         str = "Unknown";
      }

      return str.trim();
   }

   public static String toSignalStatusString(int status)
   {
      switch (status)
      {
         case SignalInfo.STATUS_VALID:
            return "Valid";
         case SignalInfo.STATUS_ENDMARK:
            return "Broken Endmark";
         case SignalInfo.STATUS_ADMINISTRATION:
            return "Broken Admin Block";
         case SignalInfo.STATUS_NOT_A_SIGNAL:
            return "Not a Signal";
         case SignalInfo.STATUS_NOT_A_POOL:
            return "Not in a Pool";
         case SignalInfo.STATUS_FREE:
            return "Free";
         case SignalInfo.STATUS_UNKNOWN:
            // Fall through.
         default:
            return "Unknown";
      }
   }

   public static String toHeapBufferStatusString(int status)
   {
      switch (status)
      {
         case HeapBufferInfo.STATUS_VALID:
            return "Valid";
         case HeapBufferInfo.STATUS_ENDMARK:
            return "Broken Endmark";
         case HeapBufferInfo.STATUS_UNKNOWN:
            // Fall through.
         default:
            return "Unknown";
      }
   }

   public static String toLoadModuleOptionsString(int options)
   {
      String str = "";

      if ((options & LoadModuleInfo.LOAD_MODULE_PERSISTENT) != 0)
      {
         str += "Persistent";
      }
      if ((options & LoadModuleInfo.LOAD_MODULE_ABSOLUTE) != 0)
      {
         if (str.length() > 0)
         {
            str += ", ";
         }
         str += "Absolute Linked";
      }

      return str;
   }

   public static String removeNetworkPathPrefix(String path)
   {
      String filename = path;
      int minPrefixLength = 6;
      if (path.startsWith("/http") || path.startsWith("/tftp"))
      {
         int lastSlash = path.lastIndexOf('/');
         if ((lastSlash > minPrefixLength) && ((lastSlash + 1) < path.length()))
         {
            filename = path.substring(lastSlash + 1);
         }
      }
      return filename;
   }

   public static short parseS16(String s) throws NumberFormatException
   {
      if (s == null)
      {
         throw new NullPointerException();
      }

      return Short.decode(s.trim()).shortValue();
   }

   public static short parseU16(String s) throws NumberFormatException
   {
      int value;

      if (s == null)
      {
         throw new NullPointerException();
      }

      value = Integer.decode(s.trim()).intValue();
      if ((value < 0) || (value > 0xFFFF))
      {
         throw new NumberFormatException();
      }
      return (short) (0xFFFF & value);
   }

   public static int parseS32(String s) throws NumberFormatException
   {
      if (s == null)
      {
         throw new NullPointerException();
      }

      return Integer.decode(s.trim()).intValue();
   }

   public static int parseU32(String s) throws NumberFormatException
   {
      long value;

      if (s == null)
      {
         throw new NullPointerException();
      }

      value = Long.decode(s.trim()).longValue();
      if ((value < 0L) || (value > 0xFFFFFFFFL))
      {
         throw new NumberFormatException();
      }
      return (int) (0xFFFFFFFFL & value);
   }

   public static long parseS64(String s) throws NumberFormatException
   {
      if (s == null)
      {
         throw new NullPointerException();
      }

      return Long.decode(s.trim()).longValue();
   }

   public static long parseU64(String s) throws NumberFormatException
   {
      BigInteger value;
      int radix = 10;
      int index = 0;

      if (s == null)
      {
         throw new NullPointerException();
      }

      s = s.trim();

      if (s.startsWith("-"))
      {
         throw new NumberFormatException("Negative number");
      }

      if (s.startsWith("0x") || s.startsWith("0X"))
      {
         index += 2;
         radix = 16;
      }
      else if (s.startsWith("#"))
      {
         index++;
         radix = 16;
      }
      else if (s.startsWith("0") && (s.length() > 1))
      {
         index++;
         radix = 8;
      }

      value = new BigInteger(s.substring(index), radix);
      if (value.bitLength() > 64)
      {
         throw new NumberFormatException(s + " is too big");
      }
      return value.longValue();
   }
}
