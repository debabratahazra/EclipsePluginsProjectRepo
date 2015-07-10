/* COPYRIGHT-ENEA-SRC-R2 *
 **************************************************************************
 * Copyright (C) 2010 by Enea Software AB.
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

package com.ose.fmm;

public final class Fmi
{
   // enum HostFmiStatus
   public static final int HOST_FMI_NOT_READY = -1;
   public static final int HOST_FMI_OK = 0;
   public static final int HOST_FMI_ERROR = 1;
   public static final int HOST_FMI_NOT_IMPLEMENTED = 2;
   public static final int HOST_FMI_UNSUPPORTED_KERNEL = 3;
   public static final int HOST_FMI_RESULT_TRUNCATED = 4;
   public static final int HOST_FMI_LICENSE_ERROR = 5;
   public static final int HOST_FMI_SYMBOL_LOOKUP_ERROR = 6;
   public static final int HOST_FMI_GET_MEMORY_ERROR = 7;

   // enum HostFmiFeature
   public static final int HOST_FMI_FEATURE_SEGMENT = 1;
   public static final int HOST_FMI_FEATURE_BLOCK = 2;
   public static final int HOST_FMI_FEATURE_USER_MODE = 3;
   public static final int HOST_FMI_FEATURE_REGISTERS = 4;
   public static final int HOST_FMI_FEATURE_ENVIRONMENT_VARIABLES = 5;
   public static final int HOST_FMI_FEATURE_SYSTEM_PARAMETERS = 6;
   public static final int HOST_FMI_FEATURE_LOAD_MODULE = 7;
   public static final int HOST_FMI_FEATURE_HEAP = 8;

   // enum HostFmiEvent
   public static final int HOST_FMI_EVENT_TARGET_SUSPENDED = 0;
   public static final int HOST_FMI_EVENT_TARGET_RESUMED = 1;

   // Efmi constants
   public static final int EFMI_MAX_SIGNAL_BUFFER_SIZE_COUNT = 8;
   public static final int EFMI_MAX_STACK_BUFFER_SIZE_COUNT = 8;
   public static final int EFMI_MAX_WANTED_SIGNALS = 16;
   public static final int EFMI_CURSOR_SIZE  = 24 / 4;

   // enum EfmiStatus
   public static final int EFMI_OK = 0;
   public static final int EFMI_UNAVAILABLE = 1;
   public static final int EFMI_END_OF_LIST = 2;
   public static final int EFMI_RESULT_TRUNCATED = 3;
   public static final int EFMI_POOL_CORRUPTED = 4;
   public static final int EFMI_KERNEL_CORRUPTED = 5;
   public static final int EFMI_KERNEL_UNINITIATED = 6;
   public static final int EFMI_CALL_INVALID = 7;

   // Endian value
   public static final byte[] EFMI_BIG_ENDIAN_VALUE =
      new byte[] {0x01, 0x02, 0x03, 0x04};

   public static final byte[] EFMI_LITTLE_ENDIAN_VALUE =
      new byte[] {0x04, 0x03, 0x02, 0x01};

   // enum EfmiOSType
   public static final int EFMI_OS_OSE_5 = 0;
   public static final int EFMI_OS_OSE_4 = 1;
   public static final int EFMI_OS_OSE_EPSILON = 2;
   public static final int EFMI_OS_OSE_CK = 3;
   public static final int EFMI_OS_UNKNOWN = 0x7fffffff;

   // enum EfmiCPUType
   public static final int EFMI_CPU_PPC = 0;
   public static final int EFMI_CPU_ARM = 1;
   public static final int EFMI_CPU_MIPS = 2;
   public static final int EFMI_CPU_X86 = 3;
   public static final int EFMI_CPU_SPARC = 4;
   public static final int EFMI_CPU_C64X = 0x10000000;
   public static final int EFMI_CPU_C64XP = 0x10000001;
   public static final int EFMI_CPU_SP26XX = 0x10000002;
   public static final int EFMI_CPU_SP27XX = 0x10000003;
   public static final int EFMI_CPU_TL3 = 0x10000004;
   public static final int EFMI_CPU_CEVAX = 0x10000005;
   public static final int EFMI_CPU_M8144 = 0x10000006;
   public static final int EFMI_CPU_M8156 = 0x10000007;
   public static final int EFMI_CPU_C66X = 0x10000008;
   public static final int EFMI_CPU_C674X = 0x10000009;
   public static final int EFMI_CPU_UNKNOWN = 0x7fffffff;

   // enum EfmiProcessTag
   public static final int EFMI_SIG_CNT_IN_Q = 0;
   public static final int EFMI_PROCESS_STACK_USAGE = 1;
   public static final int EFMI_SUPERVISOR_STACK_USAGE = 2;

   // enum EfmiSignalStatus
   public static final int EFMI_SS_VALID = 1;
   public static final int EFMI_SS_NO_ENDMARK = 2;
   public static final int EFMI_SS_BROKEN_SIGADM = 3;
   public static final int EFMI_SS_WILD_POINTER_INSIDE_POOL = 4;
   public static final int EFMI_SS_WILD_POINTER_OUTSIDE_POOL = 5;

   // enum EfmiConfigurationTag
   public static final int EFMI_CONF_TRACESIZE = 0;
   public static final int EFMI_CONF_SUPERV_STACK_SIZE = 1;
   public static final int EFMI_CONF_MAX_PROCS = 2;
   public static final int EFMI_CONF_MAX_ATTACHED = 3;
   public static final int EFMI_CONF_TICK_LEN = 4;
   public static final int EFMI_CONF_RESIDENT_PRI = 5;
   public static final int EFMI_CONF_ALLOW_USER_INTPROC = 6;
   public static final int EFMI_CONF_ALLOW_ALLOC_NIL = 7;
   public static final int EFMI_CONF_SKIP_STACK_ALLOCATION = 8;
   public static final int EFMI_CONF_LINKHANDLER_TIMEOUT = 9;
   public static final int EFMI_CONF_ALLOW_POOL_COPY = 10;
   public static final int EFMI_CONF_TABLE_EXTENDED = 11;
   public static final int EFMI_CONF_LDM_PRIORITY = 12;
   public static final int EFMI_CONF_HUNTD_PRIORITY = 13;
   public static final int EFMI_CONF_MAIN_PRIORITY = 14;
   public static final int EFMI_CONF_SYSD_PRIORITY = 15;
   public static final int EFMI_CONF_ENABLED_GET_PCB_REGS = 16;
   public static final int EFMI_CONF_TICKD_PRIORITY = 17;
   public static final int EFMI_CONF_BGD_TIMESLICE = 18;
   public static final int EFMI_CONF_ENABLE_REMCALLS = 19;
   public static final int EFMI_CONF_NTICK_FREQ = 20;
   
   // enum PfmiStatus
   public static final int PFMI_OK = 0;
   public static final int PFMI_UNAVAILABLE = 1;
   public static final int PFMI_END_OF_LIST = 2;
   public static final int PFMI_RESULT_TRUNCATED = 3;
   public static final int PFMI_DATA_LOCKED = 4;
   public static final int PFMI_DATA_CORRUPTED = 5;
   public static final int PFMI_EPROGPID = 6;
   public static final int PFMI_EINSTHND = 7;
   public static final int PFMI_UNKNOWN = 8;
   public static final int PFMI_CALL_INVALID = 9;
   
   // Pfmi Constants
   public static final int PM_STRING_LENGTH = 32;
   public static final int PM_INSTALL_HANDLE_LENGTH = PM_STRING_LENGTH;
   public static final int PM_FILE_FORMAT_NAME_LENGTH = PM_STRING_LENGTH;
   public static final int PM_PARENT_FILE_NAME_SIZE = 256;
   public static final int PM_SECTION_NAME_LENGTH = PM_STRING_LENGTH;
   public static final int PFMI_CURSOR_SIZE  = 16 / 4;

   // C pointer to a dynamic library instance context.
   @SuppressWarnings("all")
   private long context;

   // C pointer to a struct containing a pointer
   // to the JVM and an instance of this object.
   @SuppressWarnings("all")
   private long callbackContext;
   
   private final ITargetProxy targetProxy;
   
   private final String libraryPath;
   
   private boolean initialized;

   public Fmi(String libraryPath, ITargetProxy targetProxy)
   {
      this.targetProxy = targetProxy;
      this.libraryPath = libraryPath;
   }

   public static class HostFmiInitParams
   {
      public int interface_version;
      public int max_cache;
      public boolean use_domain;
      public byte[] endian;
      public String path;
   }

   public static class EfmiSystemInfo
   {
      public int os_type;
      public String baseline;
      public int cpu_type;
      public byte[] endian = new byte[4];
   };

   public static class EfmiSegmentInfo
   {
      public int segment;           // U32, ose.h PROCESS
      public int segid;             // U32, ose.h OSSEGMENT
   }

   public static class EfmiBlockInfo
   {
      public int block;             // U32, ose.h PROCESS
      public int user;              // U32, ose.h OSUSER
      public int segment;           // U32, ose.h PROCESS
      public int sig_pool;          // U32, ose.h PROCESS
      public int stack_pool;        // U32, ose.h PROCESS
      public int supervisor_mode;   // U32
      public int remcall_server;    // U32, ose.h PROCESS
      public int cpu_id;            // U32
   }

   public static class EfmiStackInfo
   {
      public int top;               // U32, ose.h OSADDRESS
      public int limit;             // U32, ose.h OSADDRESS
      public int no_endmark;        // U32
   }

   public static class EfmiProcessInfo
   {
      public int process;           // U32, ose.h PROCESS
      public int user;              // U32, ose.h OSUSER
      public int block;             // U32, ose.h PROCESS
      public int parent;            // U32, ose.h PROCESS
      public int entry_point;       // U32, ose.h OSADDRESS
      public int type;              // U32, ose.h enum PROCESS_TYPE
      public int priority;          // U32, ose.h OSPRIORITY
      public int status;            // U32, ose.h enum PROCESS_STATUS
      public int fsemvalue;         // S32, ose.h OSFSEMVAL
      public int sig_cnt_owned;     // U32
      public int line;              // U32
      public EfmiStackInfo process_stack;
      public EfmiStackInfo supervisor_stack;
      public int[] wanted =
         new int[EFMI_MAX_WANTED_SIGNALS]; // U32[], ose.h SIGSELECT[]
      public String file;
      public int cpu_id;            // U32
   }

   public static class EfmiProcessStatistics
   {
      public int value;             // U32
   }

   public static class EfmiBufSizeInfo
   {
      public int configured_size;   // U32
      public int allocated;         // U32
      public int free;              // U32
   }

   public static class EfmiPoolInfo
   {
      public int pool_id;           // U32, ose.h PROCESS
      public int fragment_count;    // U32
      public int never_used;        // U32
      public EfmiBufSizeInfo[] signal_buf_sizes;
      public EfmiBufSizeInfo[] stack_buf_sizes;
      public int segment;           // U32
      public int stack_alignment;   // U16
      public int stack_admin_size;  // U8
      public int stack_internal_admin_size; // U8
      public int signal_alignment;   // U16
      public int signal_admin_size;   // U8
      public int signal_internal_admin_size; // U8

      public EfmiPoolInfo()
      {
         signal_buf_sizes = 
            new EfmiBufSizeInfo[EFMI_MAX_SIGNAL_BUFFER_SIZE_COUNT];
         
         for (int i = 0; i < EFMI_MAX_SIGNAL_BUFFER_SIZE_COUNT; i++)
         {
            signal_buf_sizes[i] = new EfmiBufSizeInfo();
         }
         
         stack_buf_sizes =
            new EfmiBufSizeInfo[EFMI_MAX_STACK_BUFFER_SIZE_COUNT];
         
         for (int i = 0; i < EFMI_MAX_STACK_BUFFER_SIZE_COUNT; i++)
         {
            stack_buf_sizes[i] = new EfmiBufSizeInfo();
         }
      }
   }

   public static class EfmiFragmentInfo
   {
      public int base_address;      // U32, ose.h OSADDRESS
      public int size;              // U32
      public int used_for_signals;  // U32
      public int used_for_stacks;   // U32
   }

   public static class EfmiSignalInfo
   {
      public int signal_pointer;    // U32, ose.h OSADDRESS
      public int sig_no;            // U32, ose.h SIGSELECT
      public int owner;             // U32, ose.h PROCESS
      public int sender_pid;        // U32, ose.h PROCESS
      public int addressee_pid;     // U32, ose.h PROCESS
      public int sig_size;          // U32
      public int size_in_pool;      // U32
      public int sig_status;        // U32
   }

   public static class EfmiRegisterInfo
   {
      public int value;             // U32
   }

   public static class EfmiCurrent
   {
      public int process;           // U32, ose.h PROCESS
      public int block;             // U32, ose.h PROCESS
      public int segment;           // U32, ose.h OSSEGMENT
      public int segid;             // U32, ose.h enum PROCESS_TYPE
      public int type;              // U32, ose.h PROCESS
      public int ni_process;        // U32
      public int cpu_id;            // U32
   }

   public static class EfmiConfigurationInfo
   {
      public int value;             // U32
   }

   public static class EfmiCursor
   {
      int[] cursor = new int[EFMI_CURSOR_SIZE];
   }

   public static class EfmiInteger
   {
      public int value;

      public EfmiInteger(int value)
      {
         this.value = value;
      }
   }

   public static class EfmiString
   {
      public String string;

      public String toString()
      {
         return string;
      }
   }
   
   public static class PfmiLoadModuleInfo
   {
      public String install_handle; // char[PM_INSTALL_HANDLE_LENGTH];
      public int entrypoint; // OSADDRESS
      public int options; // uint32_t
      public int text_base; // OSADDRESS
      public int text_size; // uint32_t
      public int data_base; // OSADDRESS
      public int data_size; // uint32_t
      public int no_of_sections; // uint32_t
      public int no_of_instances; // uint32_t
      public String file_format; // char[PM_FILE_FORMAT_NAME_LENGTH];
      public String file_name; // char[PM_PARENT_FILE_NAME_SIZE];
   }
   
   public static class PfmiLoadModuleSectionInfo
   {
      public int section_base; // OSADDRESS
      public int section_size; // uint32_t
      public int section_attr; // uint32_t
      public int section_options; // uint32_t
      public String section_name; // char[PM_SECTION_NAME_LENGTH];
   }
   
   public static class PfmiProgramInfo
   {
      public String install_handle; // char[PM_INSTALL_HANDLE_LENGTH]
      public int progpid; // PROCESS
      public int domain; // OSDOMAIN
      public int segpid; // PROCESS
      public int main_block; // PROCESS
      public int main_process; // PROCESS
      public int stk_poolid; // PROCESS
      public int stk_pool_base; // PROCESS
      public int stk_pool_size; // uint32_t
      public int sig_poolid; // PROCESS
      public int sig_pool_base; // OSADDRESS
      public int sig_pool_size; // uint32_t
      public int uid; // OSUSER
      public int heap; // OSHEAP
      public int state; // uint32_t
   }
   
   public static class PfmiCursor
   {
      int[] cursor = new int[PFMI_CURSOR_SIZE];
   }
   
      
   public void initializeLibrary()
   {
      System.load(libraryPath);
      initJNI();
   }
   
   public boolean isInitialized()
   {
      return initialized;
   }
   
   public void setInitialized(boolean initialized)
   {
      this.initialized = initialized;
   }
   
   public ITargetProxy getTargetProxy()
   {
      return targetProxy;
   }
   
   public String getLibraryPath()
   {
      return libraryPath;
   }

   public static String fmiStatusToString(int status)
   {
      switch (status)
      {
         case HOST_FMI_NOT_READY:
            return "The kernel data structures are not ready yet.";
         case HOST_FMI_OK:
            return "Operation was successful.";
         case HOST_FMI_ERROR:
            return "A general error was reported.";
         case HOST_FMI_NOT_IMPLEMENTED:
            return "The operation has not been implemented.";
         case HOST_FMI_UNSUPPORTED_KERNEL:
            return "This particular kernel is not supported.";
         case HOST_FMI_RESULT_TRUNCATED:
            return "The result was truncated due to insufficient buffer space.";
         case HOST_FMI_LICENSE_ERROR:
            return "A valid license could not be obtained.";
         case HOST_FMI_SYMBOL_LOOKUP_ERROR:
            return "Symbol lookup failed unexpectedly.";
         case HOST_FMI_GET_MEMORY_ERROR:
            return "Memory retrieval failed unexpectedly.";
         default:
            return "Unknown status.";
      }
   }
   
   public static String efmiStatusToString(int status)
   {
      switch (status)
      {
         case EFMI_OK:
            return "Operation was successful.";
         case EFMI_UNAVAILABLE:
            return "Requested information not found.";
         case EFMI_END_OF_LIST:
            return "No more objects found.";
         case EFMI_RESULT_TRUNCATED:
            return "A list or string was truncated.";
         case EFMI_POOL_CORRUPTED:
            return "Error found in a non-system pool.";
         case EFMI_KERNEL_CORRUPTED:
            return "Error found in kernel data structures.";
         case EFMI_KERNEL_UNINITIATED:
            return "The kernel data structures are not yet initiated.";
         case EFMI_CALL_INVALID:
            return "Operation preconditions were not met.";
         default:
            return "Unknown status.";
      }
   }

   public static String pfmiStatusToString(int status)
   {
      switch (status)
      {
         case PFMI_OK:
            return "Operation was successful.";
         case PFMI_UNAVAILABLE:
            return "Requested information not found.";
         case PFMI_END_OF_LIST:
            return "No more objects found.";
         case PFMI_RESULT_TRUNCATED:
            return "A list or string was truncated.";
         case PFMI_DATA_LOCKED:
            return "PM data structures are locked.";
         case PFMI_DATA_CORRUPTED:
            return "PM data structures are corrupted.";
         case PFMI_EPROGPID:
            return "Illegal program PID.";
         case PFMI_EINSTHND:
            return "Illegal install handle.";
         case PFMI_UNKNOWN:
            return "The information was unknown by PM.";
         case PFMI_CALL_INVALID:
            return "Operation preconditions were not met.";
         default:
            return "Unknown status.";
      }
   }
   
   long getAddress(String symbol) throws TargetProxyException
   {
      return targetProxy.getAddress(symbol);
   }

   byte[] getMemory(long address, long domain, int length)
      throws TargetProxyException
   {
      return targetProxy.getMemory(address, length);
   }
   
   public static native void initJNI();

   // hostfmi.h
   
   public native int ose_host_fmi_init(HostFmiInitParams params);

   public native void ose_host_fmi_destroy();

   public native int ose_host_fmi_get_system_name(EfmiString name);

   public native int ose_host_fmi_is_supported(int feature);

   public native int ose_host_fmi_handle_event(int event);

   // efmi.h
   
   public native int ose_efmi_get_status();

   public native int ose_efmi_get_system_info(EfmiSystemInfo system_info);

   public native int ose_efmi_get_current(EfmiCurrent current);

   public native int ose_efmi_get_segment_info(int segment,
                                               EfmiSegmentInfo pri);

   public native int ose_efmi_get_block_info(int block_id, EfmiBlockInfo bi);

   public native int ose_efmi_get_process_info(int pid,
                                               EfmiProcessInfo proc_info);

   public native int ose_efmi_get_process_statistics(int pid, int tag,
                                              EfmiProcessStatistics statistics);

   public native int ose_efmi_get_pool_info(int pool_id,
                                            EfmiPoolInfo pool_info);

   public native int ose_efmi_get_signal_info(int signal,
                                              EfmiSignalInfo sig_info);

   public native int ose_efmi_get_process_name(int pid, EfmiString name);

   public native int ose_efmi_get_register(int pid, int register_number,
                                           EfmiRegisterInfo reg);

   public native int ose_efmi_get_config(int tag, EfmiConfigurationInfo conf);

   public native int ose_efmi_get_mem(int id, int from, byte[] to);

   public native int ose_efmi_get_smp_num_cpus(EfmiInteger num_cpus);

   public native int ose_efmi_get_first_current(EfmiCursor cc,
                                                EfmiCurrent current);

   public native int ose_efmi_get_next_current(EfmiCursor cc,
                                               EfmiCurrent current);

   public native int ose_efmi_get_first_segment(EfmiCursor prc,
                                                EfmiSegmentInfo pri);

   public native int ose_efmi_get_next_segment(EfmiCursor prc,
                                               EfmiSegmentInfo pri);

   public native int ose_efmi_get_first_block(int segment_id,
                                              EfmiCursor bc,
                                              EfmiBlockInfo bi);

   public native int ose_efmi_get_next_block(EfmiCursor bc,
                                             EfmiBlockInfo bi);

   public native int ose_efmi_get_first_process(int block_id,
                                                EfmiCursor pc,
                                                EfmiProcessInfo proc_info);

   public native int ose_efmi_get_next_process(EfmiCursor pc,
                                               EfmiProcessInfo proc_info);

   public native int ose_efmi_get_first_env_var(int pid, EfmiCursor evc,
                                                EfmiString name,
                                                EfmiString value);

   public native int ose_efmi_get_next_env_var(EfmiCursor evc,
                                               EfmiString name,
                                               EfmiString value);

   public native int ose_efmi_get_first_pool(EfmiCursor pc,
                                             EfmiPoolInfo pool_info);

   public native int ose_efmi_get_next_pool(EfmiCursor pc,
                                            EfmiPoolInfo pool_info);

   public native int ose_efmi_get_first_fragment(int pool_id,
                                                EfmiCursor fc,
                                                EfmiFragmentInfo fragment_info);

   public native int ose_efmi_get_next_fragment(EfmiCursor fc,
                                                EfmiFragmentInfo fragment_info);

   public native int ose_efmi_get_first_pool_signal(int pool_id,
                                                    EfmiCursor psc,
                                                    EfmiSignalInfo ps_info);

   public native int ose_efmi_get_next_pool_signal(EfmiCursor psc,
                                                   EfmiSignalInfo ps_info);

   public native int ose_efmi_get_first_q_signal(int pid, EfmiCursor sc,
                                                 EfmiSignalInfo sig_info);

   public native int ose_efmi_get_next_q_signal(EfmiCursor sc,
                                                EfmiSignalInfo sig_info);

   public native int ose_efmi_get_first_process_by_name(String name,
                                                        EfmiCursor nc,
                                                        EfmiInteger pid);

   public native int ose_efmi_get_next_process_by_name(String name,
                                                       EfmiCursor nc,
                                                       EfmiInteger pid);
   
   // pfmi.h
   
   public native int ose_pfmi_get_progpid(int pid, EfmiInteger progpid);
   
   public native int ose_pfmi_get_load_module_info(String install_handle,
                                                   PfmiLoadModuleInfo lmi);

   public native int ose_pfmi_get_load_module_section_info(String install_handle,
                                               int section,
                                               PfmiLoadModuleSectionInfo lmsi);
   
   public native int ose_pfmi_get_program_info(int progpid, PfmiProgramInfo pi);
      
   public native int ose_pfmi_get_first_load_module(PfmiCursor lmc,
                                                    PfmiLoadModuleInfo lmi);
   
   public native int ose_pfmi_get_next_load_module(PfmiCursor lmc,
                                                   PfmiLoadModuleInfo lmi);
   
   public native int ose_pfmi_get_first_program(PfmiCursor pc,
                                                PfmiProgramInfo pi);
   
   public native int ose_pfmi_get_next_program(PfmiCursor pc,
                                               PfmiProgramInfo pi);
}
