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

public class MonitorStatus
{
   public static final int MONITOR_STATUS_OK = 0;
   public static final int MONITOR_STATUS_ID_INVALID = 1;
   public static final int MONITOR_STATUS_SCOPE_STATE_INVALID = 2;
   public static final int MONITOR_STATUS_SCOPE_INVALID = 3;
   public static final int MONITOR_STATUS_REQUEST_INVALID = 4;
   public static final int MONITOR_STATUS_EPM_ERROR = 5;
   public static final int MONITOR_STATUS_KERNEL_ERROR = 6;
   public static final int MONITOR_STATUS_MEMORY_GET_ERROR = 7;
   public static final int MONITOR_STATUS_MEMORY_SET_ERROR = 8;
   public static final int MONITOR_STATUS_SCOPE_OVERLAP = 9;
   public static final int MONITOR_STATUS_SCOPE_EMPTY = 10;
   public static final int MONITOR_STATUS_MEMORY_EXHAUSTED = 11;
   public static final int MONITOR_STATUS_INTERNAL_ERROR = 12;
   public static final int MONITOR_STATUS_CANCELLED = 13;
   public static final int MONITOR_STATUS_IN_USE = 14;
   public static final int MONITOR_STATUS_SYSTEM_PARAM_ERROR = 15;
   public static final int MONITOR_STATUS_RESOURCE_EXHAUSTED = 16;
   public static final int MONITOR_STATUS_CPU_INACTIVE = 17;
   public static final int MONITOR_STATUS_MEMORY_LIMIT = 18;
   public static final int MONITOR_STATUS_CLIENT_KILLED = 19;
   public static final int MONITOR_STATUS_BIND_FAILED = 20;
   public static final int MONITOR_STATUS_KILLED = 21;
   public static final int MONITOR_STATUS_INVALID_EU = 22;

   private static final String MONITOR_STATUS_OK_MSG =
      "Monitor operation successful.";
   private static final String MONITOR_STATUS_ID_INVALID_MSG =
      "Invalid segment/pool/heap/block/process id.";
   private static final String MONITOR_STATUS_SCOPE_STATE_INVALID_MSG =
      "Invalid monitor scope state.";
   private static final String MONITOR_STATUS_SCOPE_INVALID_MSG =
      "Invalid monitor scope.";
   private static final String MONITOR_STATUS_REQUEST_INVALID_MSG =
      "Invalid monitor request.";
   private static final String MONITOR_STATUS_EPM_ERROR_MSG =
      "EPM error.";
   private static final String MONITOR_STATUS_KERNEL_ERROR_MSG =
      "Kernel error.";
   private static final String MONITOR_STATUS_MEMORY_GET_ERROR_MSG =
      "Error reading memory.";
   private static final String MONITOR_STATUS_MEMORY_SET_ERROR_MSG =
      "Error writing memory.";
   private static final String MONITOR_STATUS_SCOPE_OVERLAP_MSG =
      "The requested attach scope overlaps with an existing attach scope.";
   private static final String MONITOR_STATUS_SCOPE_EMPTY_MSG =
      "Empty monitor scope.";
   private static final String MONITOR_STATUS_MEMORY_EXHAUSTED_MSG =
      "Target memory exhausted.";
   private static final String MONITOR_STATUS_INTERNAL_ERROR_MSG =
      "Internal monitor error.";
   private static final String MONITOR_STATUS_CANCELLED_MSG =
      "Operation cancelled.";
   private static final String MONITOR_STATUS_IN_USE_MSG =
      "Requested resource is in use.";
   private static final String MONITOR_STATUS_SYSTEM_PARAM_ERROR_MSG =
      "System parameter operation failed.";
   private static final String MONITOR_STATUS_UNKNOWN_ERROR_MSG =
      "Unknown monitor error code: ";
   private static final String MONITOR_STATUS_RESOURCE_EXHAUSTED_MSG =
      "A target resource has been exhausted.";
   private static final String MONITOR_STATUS_CPU_INACTIVE_MSG =
      "Requested execution unit runs another operating system than OSE.";
   private static final String MONITOR_STATUS_MEMORY_LIMIT_MSG =
      "Total memory used for profiling buffers is larger than the memory limit.";
   private static final String MONITOR_STATUS_CLIENT_KILLED_MSG =
      "Request failed due to a monitor client no longer exists.";
   private static final String MONITOR_STATUS_BIND_FAILED_MSG =
      "Bind operation failed.";
   private static final String MONITOR_STATUS_KILLED_MSG =
      "The referred segment, block, or process was killed.";
   private static final String MONITOR_STATUS_INVALID_EU_MSG =
      "Invalid execution unit id.";

   public static String getMessage(int status)
   {
      switch (status)
      {
      case MONITOR_STATUS_OK:
         return MONITOR_STATUS_OK_MSG;
      case MONITOR_STATUS_ID_INVALID:
         return MONITOR_STATUS_ID_INVALID_MSG;
      case MONITOR_STATUS_SCOPE_STATE_INVALID:
         return MONITOR_STATUS_SCOPE_STATE_INVALID_MSG;
      case MONITOR_STATUS_SCOPE_INVALID:
         return MONITOR_STATUS_SCOPE_INVALID_MSG;
      case MONITOR_STATUS_REQUEST_INVALID:
         return MONITOR_STATUS_REQUEST_INVALID_MSG;
      case MONITOR_STATUS_EPM_ERROR:
         return MONITOR_STATUS_EPM_ERROR_MSG;
      case MONITOR_STATUS_KERNEL_ERROR:
         return MONITOR_STATUS_KERNEL_ERROR_MSG;
      case MONITOR_STATUS_MEMORY_GET_ERROR:
         return MONITOR_STATUS_MEMORY_GET_ERROR_MSG;
      case MONITOR_STATUS_MEMORY_SET_ERROR:
         return MONITOR_STATUS_MEMORY_SET_ERROR_MSG;
      case MONITOR_STATUS_SCOPE_OVERLAP:
         return MONITOR_STATUS_SCOPE_OVERLAP_MSG;
      case MONITOR_STATUS_SCOPE_EMPTY:
         return MONITOR_STATUS_SCOPE_EMPTY_MSG;
      case MONITOR_STATUS_MEMORY_EXHAUSTED:
         return MONITOR_STATUS_MEMORY_EXHAUSTED_MSG;
      case MONITOR_STATUS_INTERNAL_ERROR:
         return MONITOR_STATUS_INTERNAL_ERROR_MSG;
      case MONITOR_STATUS_CANCELLED:
         return MONITOR_STATUS_CANCELLED_MSG;
      case MONITOR_STATUS_IN_USE:
         return MONITOR_STATUS_IN_USE_MSG;
      case MONITOR_STATUS_SYSTEM_PARAM_ERROR:
         return MONITOR_STATUS_SYSTEM_PARAM_ERROR_MSG;
      case MONITOR_STATUS_RESOURCE_EXHAUSTED:
         return MONITOR_STATUS_RESOURCE_EXHAUSTED_MSG;
      case MONITOR_STATUS_CPU_INACTIVE:
         return MONITOR_STATUS_CPU_INACTIVE_MSG;
      case MONITOR_STATUS_MEMORY_LIMIT:
         return MONITOR_STATUS_MEMORY_LIMIT_MSG;
      case MONITOR_STATUS_CLIENT_KILLED:
         return MONITOR_STATUS_CLIENT_KILLED_MSG;
      case MONITOR_STATUS_BIND_FAILED:
         return MONITOR_STATUS_BIND_FAILED_MSG;
      case MONITOR_STATUS_KILLED:
         return MONITOR_STATUS_KILLED_MSG;
      case MONITOR_STATUS_INVALID_EU:
         return MONITOR_STATUS_INVALID_EU_MSG;
      default:
         return MONITOR_STATUS_UNKNOWN_ERROR_MSG + status;
      }
   }
}
