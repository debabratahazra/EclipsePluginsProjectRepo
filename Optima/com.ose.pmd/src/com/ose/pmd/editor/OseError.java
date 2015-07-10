/* COPYRIGHT-ENEA-SRC-R2 *
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

package com.ose.pmd.editor;

/**
 * This class contains the public OSE error codes and a method for retrieving
 * the verbose error message for a specified OSE error code.
 */
public class OseError
{
   public static final int OSE_EERROR_MASK = 0x0000FFFF;
   public static final int OSE_EAPP_ERROR_MASK = 0x7FFFFFFF;
   public static final int OSE_ESUBCODE_MASK = 0x7FFF0000;
   public static final int OSE_EFATAL_MASK = 0x80000000;

   public static final int OSE_ENO_KERN_SPACE = 0x12;
   public static final int OSE_ENO_USER_SIGSPACE = 0x20;
   public static final int OSE_ENO_USER_STACKSPACE = 0x02;
   public static final int OSE_EPOOL_EXTENSION_FAILED = 0x21;
   public static final int OSE_EBUFFER_TOO_LARGE = 0x11;
   public static final int OSE_ESTACK_TOO_LARGE = 0x01;
   public static final int OSE_EBLOCK_HAS_PROCESSES = 0x22;
   public static final int OSE_EILLEGAL_PROCESS_ID = 0x32;
   public static final int OSE_EUSED_NIL_POINTER = 0x31;
   public static final int OSE_ENOT_SIG_OWNER = 0x5F;
   public static final int OSE_ECANNOT_RESTORE = 0x57;
   public static final int OSE_EUNREASONABLE_SIZES = 0x23;
   public static final int OSE_ETOO_MANY_PROCESSES = 0x03;
   public static final int OSE_ETOO_MANY_ATTACHED = 0x24;
   public static final int OSE_ECORRUPTED_KERNEL = 0x4A;
   public static final int OSE_EVECTOR_IN_USE = 0x25;
   public static final int OSE_EILLEGAL_SYSTEMCALL = 0x26;
   public static final int OSE_EINTERSEGMENT_SEND = 0x27;
   public static final int OSE_EBAD_INTLEVEL = 0x28;
   public static final int OSE_ERECURSIVE_ERROR = 0x29;
   public static final int OSE_ERECURSIVE_SYSERROR = 0x2A;
   public static final int OSE_EILLEGAL_SEM_VALUE = 0x2B;
   public static final int OSE_EILLEGAL_SEMAPHORE = 0x1F;
   public static final int OSE_EKILLED_STATIC_SEMAPHORE = 0x1D;
   public static final int OSE_EKILLED_SEMAPHORE_IN_USE = 0x1E;
   public static final int OSE_EINTERSEGMENT_SEMAPHORE_REF = 0x2C;
   public static final int OSE_EDETACHED_TWICE = 0x2D;
   public static final int OSE_EDETACHED_TOO_LATE = 0x2E;
   public static final int OSE_EDETACH_AFTER_RECEIVE = 0x2F;
   public static final int OSE_EBAD_PROCESS_TYPE = 0x30;
   public static final int OSE_ENOT_ATTREF_OWNER = 0x36;
   public static final int OSE_EILLEGAL_FLUSH_SENDER = 0x47;
   public static final int OSE_EKILLED_STATIC_PROCESS = 0x48;
   public static final int OSE_EILLEGAL_REDIRCOUNT = 0x49;
   public static final int OSE_ESPURIOUS_INTERRUPT = 0x52;
   public static final int OSE_EATTACHED_TO_CALLER = 0x53;
   public static final int OSE_EINTERSEGMENT_COPY_FAILED = 0x54;
   public static final int OSE_EINTSTACK_TOO_LARGE = 0x55;
   public static final int OSE_EKILLED_SYSTEM_DAEMON = 0x56;
   public static final int OSE_EFRAGMENT_TOO_SMALL = 0x58;
   public static final int OSE_ECONCURRENT_TICK_CALL = 0x59;
   public static final int OSE_EILLEGAL_USER_CALL = 0x5A;
   public static final int OSE_EALREADY_STARTED = 0x5B;
   public static final int OSE_ELINKHANDLER_DEADLOCK = 0x5C;
   public static final int OSE_EILLEGAL_ENVNAME = 0x5D;
   public static final int OSE_EILLEGAL_POOL = 0x5E;
   public static final int OSE_EWILD_SIG_POINTER = 0x70;
   public static final int OSE_EBAD_PARAMETER = 0x71;
   public static final int OSE_EFRAGMENT_IN_USE = 0x72;
   public static final int OSE_EILLEGAL_SUPER_MODE = 0x73;
   public static final int OSE_ESPURIOUS_SYSTEM_SIGNAL = 0x74;
   public static final int OSE_EFRAGMENT_TOO_LARGE = 0x75;
   public static final int OSE_ESTOP_OVERFLOW = 0x76;
   public static final int OSE_ESEM_OVERFLOW = 0x77;
   public static final int OSE_EBAD_HUNT_PRI = 0x78;
   public static final int OSE_EILLEGAL_PRISYSTEMCALL = 0x79;
   public static final int OSE_EILLEGAL_CONFIGURATION = 0x7A;
   public static final int OSE_ETOO_MANY_TMO_REQUESTS = 0x7B;
   public static final int OSE_ETMO_ALREADY_CANCELLED = 0x7C;
   public static final int OSE_ETMO_ALREADY_RECEIVED = 0x7D;
   public static final int OSE_ECRT_KRN_INCOMPATIBLE = 0x7E;
   public static final int OSE_ETOO_MANY_PPD_KEYS = 0x7F;
   public static final int OSE_ETOO_LONG_PPD_KEY_STRING = 0x80;
   public static final int OSE_EILLEGAL_PPDKEY = 0x81;
   public static final int OSE_EMUTEX_INVALID = 0x82;
   public static final int OSE_EMUTEX_ALREADY_INITIALIZED = 0x83;
   public static final int OSE_EMUTEX_IN_USE = 0x84;
   public static final int OSE_EMUTEX_DESTROYED = 0x85;
   public static final int OSE_EMUTEX_LOCKED = 0x86;
   public static final int OSE_EMUTEX_DEADLOCK = 0x87;
   public static final int OSE_EMUTEX_NOT_OWNER = 0x88;
   public static final int OSE_EMUTEX_OWNER_DEAD = 0x89;
   public static final int OSE_EILLEGAL_USER_MODE = 0x8A;
   public static final int OSE_EPRIVILEGED_SYSCALL = 0x8B;
   public static final int OSE_ETOO_MANY_MUTEXES = 0x8C;
   public static final int OSE_ENO_BUFFER_END_MARK = 0xA5;
   public static final int OSE_ECORRUPTED_POOL = 0xA6;
   public static final int OSE_EUSER_STACK_OVERFLOW = 0x101;
   public static final int OSE_ESUPERV_STACK_OVERFLOW = 0x102;
   public static final int OSE_EINTERRUPT_STACK_OVERFLOW = 0x103;
   public static final int OSE_EUNKNOWN_BREAKPOINT = 0x104;
   public static final int OSE_EUNKNOWN_INTERRUPT = 0x105;
   public static final int OSE_EPROCESS_ENDED = 0x107;
   public static final int OSE_ESTART_STACK_OVERFLOW = 0x108;
   public static final int OSE_EUNRECOGNIZED_CPU = 0x109;
   public static final int OSE_EINCOMPATIBLE_CPU_HAL = 0x10A;
   public static final int OSE_EUNDEFINED_SYSCALL_CODE = 0x110;
   public static final int OSE_EUNEXPECTED_EXCEPTION = 0x111;
   public static final int OSE_EPRIORITY_ERROR = 0x112;
   public static final int OSE_EUNEXPECTED_EXCEPTION_REGDUMP = 0x113;
   public static final int OSE_EEARLY_ERROR = 0x114;
   public static final int OSE_ESYSCALL_TOO_EARLY = 0x115;
   public static final int OSE_EINCOMPATIBLE_CONFIGURATION = 0x116;

   public static final int OSE_HEAP_EBASE = 0x820000;
   public static final int OSE_HEAP_EBIOS_INSTALL_FAILED = 0x820000;
   public static final int OSE_HEAP_EHUNT_HEAPD_FAILED = 0x820001;
   public static final int OSE_HEAP_EBIOS_OPEN_FAILED = 0x820002;
   public static final int OSE_HEAP_EUNKNOWN_SIGNAL = 0x820003;
   public static final int OSE_HEAP_EFREED_BUFFER_OVERWRITTEN = 0x820007;
   public static final int OSE_HEAP_ESIZE_TOO_LARGE = 0x820100;
   public static final int OSE_HEAP_EPTR_INVALID = 0x820101;
   public static final int OSE_HEAP_EBUFFER_FREE = 0x820102;
   public static final int OSE_HEAP_EPROCESS_NOT_OWNER = 0x820103;
   public static final int OSE_HEAP_EENDMARK_OVERWRITTEN = 0x820104;
   public static final int OSE_HEAP_EHEAP_EXHAUSTED = 0x820105;
   public static final int OSE_HEAP_ESYSMEM_ALLOC_FAILED = 0x820110;
   public static final int OSE_HEAP_EDYNAMIC_LINK_FAILED = 0x820107;
   public static final int OSE_HEAP_EMODULE_TOO_OLD = 0x820108;
   public static final int OSE_HEAP_EHEAP_INCONSISTENT = 0x820109;
   public static final int OSE_HEAP_EILLEGAL_EXTENSION = 0x820111;
   public static final int OSE_HEAP_EHEAP_CORRUPTED = 0x820112;
   public static final int OSE_HEAP_EILLEGAL_USER = 0x820113;

   public static final int OSE_EFS_EABORT_CALLED = 0x900004;

   public static final int OSE_EASSERTION_FAILED = 0xFFFFFFFF;
   public static final int OSE_EUNKNOWN_SIGNAL = 0xFFFFFFFE;

   private static final String OSE_ENO_KERN_SPACE_MSG =
      "System pool exhausted.";
   private static final String OSE_ENO_USER_SIGSPACE_MSG =
      "User signal pool %x exhausted.";
   private static final String OSE_ENO_USER_STACKSPACE_MSG =
      "User stack pool %x exhausted.";
   private static final String OSE_EPOOL_EXTENSION_FAILED_MSG =
      "Pool extension failed (initial error code %x).";
   private static final String OSE_EBUFFER_TOO_LARGE_MSG =
      "Too large signal buffer of size %u requested.";
   private static final String OSE_ESTACK_TOO_LARGE_MSG =
      "Too large stack buffer of size %u requested.";
   private static final String OSE_EBLOCK_HAS_PROCESSES_MSG =
      "Cannot create pool, specified block has processes.";
   private static final String OSE_EILLEGAL_PROCESS_ID_MSG =
      "Illegal block or process ID %x encountered.";
   private static final String OSE_EUSED_NIL_POINTER_MSG =
      "Used NIL signal pointer (*%x == NIL).";
   private static final String OSE_ENOT_SIG_OWNER_MSG =
      "Calling process not owner of signal buffer at address %x.";
   private static final String OSE_ECANNOT_RESTORE_MSG =
      "Cannot restore signal buffer at address %x.";
   private static final String OSE_EUNREASONABLE_SIZES_MSG =
      "Unreasonable pool signal/stack sizes.";
   private static final String OSE_ETOO_MANY_PROCESSES_MSG =
      "Too many processes, blocks, pools, or segments created (max is %u).";
   private static final String OSE_ETOO_MANY_ATTACHED_MSG =
      "Too many signals attached (max is %u).";
   private static final String OSE_ECORRUPTED_KERNEL_MSG =
      "Kernel data corrupted (%x).";
   private static final String OSE_EVECTOR_IN_USE_MSG =
      "Vector %u already in use.";
   private static final String OSE_EILLEGAL_SYSTEMCALL_MSG =
      "Illegal system call from interrupt or timer interrupt process.";
   private static final String OSE_EINTERSEGMENT_SEND_MSG =
      "Illegal intersegment send() system call.";
   private static final String OSE_EBAD_INTLEVEL_MSG =
      "Illegal wakeup priority %u for interrupt process.";
   private static final String OSE_ERECURSIVE_ERROR_MSG =
      "Recursive error %x reported by an application when in an error handler.";
   private static final String OSE_ERECURSIVE_SYSERROR_MSG =
      "Recursive error %x detected by the kernel when in an error handler.";
   private static final String OSE_EILLEGAL_SEM_VALUE_MSG =
      "Negative initial value %d for semaphore.";
   private static final String OSE_EILLEGAL_SEMAPHORE_MSG =
      "Illegal semaphore pointer specified.";
   private static final String OSE_EKILLED_STATIC_SEMAPHORE_MSG =
      "Attempt to kill a static semaphore.";
   private static final String OSE_EKILLED_SEMAPHORE_IN_USE_MSG =
      "Attempt to kill a semaphore in use by process %x.";
   private static final String OSE_EINTERSEGMENT_SEMAPHORE_REF_MSG =
      "Intersegment reference from process %x to a dying semaphore.";
   private static final String OSE_EDETACHED_TWICE_MSG =
      "Attempt to detach from an already detached process.";
   private static final String OSE_EDETACHED_TOO_LATE_MSG =
      "Attempt to detach from a process using a too old attach reference.";
   private static final String OSE_EDETACH_AFTER_RECEIVE_MSG =
      "Attempt to detach from a process when the attached signal has already " +
      "been received.";
   private static final String OSE_EBAD_PROCESS_TYPE_MSG =
      "Invalid process type specified.";
   private static final String OSE_ENOT_ATTREF_OWNER_MSG =
      "Calling process is not owner of attach reference (real owner is " +
      "process %x).";
   private static final String OSE_EILLEGAL_FLUSH_SENDER_MSG =
      "Illegal process ID in flush() system call.";
   private static final String OSE_EKILLED_STATIC_PROCESS_MSG =
      "Static process killed.";
   private static final String OSE_EILLEGAL_REDIRCOUNT_MSG =
      "Invalid redirection table specified.";
   private static final String OSE_ESPURIOUS_INTERRUPT_MSG =
      "Spurious interrupt (from unused vector) on priority %u.";
   private static final String OSE_EATTACHED_TO_CALLER_MSG =
      "Attach to calling process.";
   private static final String OSE_EINTERSEGMENT_COPY_FAILED_MSG =
      "Intersegment signal buffer copy failed, signal buffer of size %u too " +
      "large.";
   private static final String OSE_EINTSTACK_TOO_LARGE_MSG =
      "Accumulated interrupt stack of size %u too large.";
   private static final String OSE_EKILLED_SYSTEM_DAEMON_MSG =
      "Attempt to kill system daemon process %x.";
   private static final String OSE_EFRAGMENT_TOO_SMALL_MSG =
      "Pool fragment of size %u too small.";
   private static final String OSE_ECONCURRENT_TICK_CALL_MSG =
      "Non-reentrant system call tick() concurrently called.";
   private static final String OSE_EILLEGAL_USER_CALL_MSG =
      "Illegal system call from non-superuser process.";
   private static final String OSE_EALREADY_STARTED_MSG =
      "Starting non-stopped process/block %x.";
   private static final String OSE_ELINKHANDLER_DEADLOCK_MSG =
      "Linkhandler %x deadlocked.";
   private static final String OSE_EILLEGAL_ENVNAME_MSG =
      "Illegal (NULL) name of environment variable.";
   private static final String OSE_EILLEGAL_POOL_MSG =
      "Illegal pool ID in extend_pool() system call.";
   private static final String OSE_EWILD_SIG_POINTER_MSG =
      "Wild signal pointer referencing address %x.";
   private static final String OSE_EBAD_PARAMETER_MSG =
      "Bad system call parameter %x.";
   private static final String OSE_EFRAGMENT_IN_USE_MSG =
      "Pool fragment at address %x already in use.";
   private static final String OSE_EILLEGAL_SUPER_MODE_MSG =
      "Illegal create supervisor mode block (block name is at address %x) " +
      "from user mode process.";
   private static final String OSE_ESPURIOUS_SYSTEM_SIGNAL_MSG =
      "System daemon received spurious signal from process %x.";
   private static final String OSE_EFRAGMENT_TOO_LARGE_MSG =
      "Pool fragment of size %u too large.";
   private static final String OSE_ESTOP_OVERFLOW_MSG =
      "Stop counter overflow for process %x.";
   private static final String OSE_ESEM_OVERFLOW_MSG =
      "Semaphore counter overflow/underflow (semaphore address or owner " +
      "process is %x).";
   private static final String OSE_EBAD_HUNT_PRI_MSG =
      "Illegal priority for hunt daemon.";
   private static final String OSE_EILLEGAL_PRISYSTEMCALL_MSG =
      "Illegal system call for non-prioritized process.";
   private static final String OSE_EILLEGAL_CONFIGURATION_MSG =
      "Illegal kernel configuration (value %u).";
   private static final String OSE_ETOO_MANY_TMO_REQUESTS_MSG =
      "More than %u concurrent timeout signal requests.";
   private static final String OSE_ETMO_ALREADY_CANCELLED_MSG =
      "Timeout signal request already cancelled.";
   private static final String OSE_ETMO_ALREADY_RECEIVED_MSG =
      "Timeout signal request %x already received.";
   private static final String OSE_ECRT_KRN_INCOMPATIBLE_MSG =
      "C runtime library is incompatible with the kernel, versions %x " +
      "(kernel version upper 16 bits, CRT version lower 16 bits).";
   private static final String OSE_ETOO_MANY_PPD_KEYS_MSG =
      "Too many per process data keys were created (max is %u).";
   private static final String OSE_ETOO_LONG_PPD_KEY_STRING_MSG =
      "Too long per process data key string requested (max length is %u).";
   private static final String OSE_EILLEGAL_PPDKEY_MSG =
      "An illegal per process data key (%x) was presented to the kernel.";
   private static final String OSE_EMUTEX_INVALID_MSG =
      "A pointer to an uninitialized mutex (at address %x) was presented to " +
      "the kernel.";
   private static final String OSE_EMUTEX_ALREADY_INITIALIZED_MSG =
      "Attempt to reinitialize a mutex (at address %x).";
   private static final String OSE_EMUTEX_IN_USE_MSG =
      "Attempt to destroy a mutex with waiting processes. " +
      "First waiting process is %x.";
   private static final String OSE_EMUTEX_DESTROYED_MSG =
      "Attempt to use a mutex (at address %x) that has been destroyed.";
   private static final String OSE_EMUTEX_LOCKED_MSG =
      "Attempt to destroy a mutex that was locked by process %x.";
   private static final String OSE_EMUTEX_DEADLOCK_MSG =
      "Attempt to lock a mutex (at address %x) that is already locked by the " +
      "caller.";
   private static final String OSE_EMUTEX_NOT_OWNER_MSG =
      "Attempt to unlock a mutex (at address %x) that was not locked by the " +
      "caller.";
   private static final String OSE_EMUTEX_OWNER_DEAD_MSG =
      "Attempt to operate on a mutex that is still locked, but the owner " +
      "process %x is dead.";
   private static final String OSE_EILLEGAL_USER_MODE_MSG =
      "Illegal create user mode block (block name is at address %x) from " +
      "supervisor mode block.";
   private static final String OSE_EPRIVILEGED_SYSCALL_MSG =
      "Privileged system call, requires system parameter " +
      "krn/relax_error_checks mask %x.";
   private static final String OSE_ETOO_MANY_MUTEXES_MSG =
      "Too many mutexes in use.";
   private static final String OSE_ENO_BUFFER_END_MARK_MSG =
      "Buffer endmark for signal buffer at address %x overwritten.";
   private static final String OSE_ECORRUPTED_POOL_MSG =
      "Pool corrupted, extra information available at address %x.";
   private static final String OSE_EUSER_STACK_OVERFLOW_MSG =
      "User stack overflow at address %x.";
   private static final String OSE_ESUPERV_STACK_OVERFLOW_MSG =
      "Supervisor stack overflow at address %x.";
   private static final String OSE_EINTERRUPT_STACK_OVERFLOW_MSG =
      "Interrupt stack overflow at address %x.";
   private static final String OSE_EUNKNOWN_BREAKPOINT_MSG =
      "Unknown breakpoint encountered at address %x.";
   private static final String OSE_EUNKNOWN_INTERRUPT_MSG =
      "Unknown interrupt vector %u encountered.";
   private static final String OSE_EPROCESS_ENDED_MSG =
      "Process %x returned.";
   private static final String OSE_ESTART_STACK_OVERFLOW_MSG =
      "Startup stack overflow at address %x.";
   private static final String OSE_EUNRECOGNIZED_CPU_MSG =
      "Unrecognized CPU model %u configured.";
   private static final String OSE_EINCOMPATIBLE_CPU_HAL_MSG =
      "Kernel configuration refers to at least one CPU HAL with an interface " +
      "version that is not compatible with the kernel. The configured CPU " +
      "descriptor structure is at address %x.";
   private static final String OSE_EUNDEFINED_SYSCALL_CODE_MSG =
      "Undefined system call with function code %u.";
   private static final String OSE_EUNEXPECTED_EXCEPTION_MSG =
      "An unexpected exception occurred at address %x.";
   private static final String OSE_EPRIORITY_ERROR_MSG =
      "Interrupt priority error, illegal priorities %x (old priority upper " +
      "16 bits, new priority lower 16 bits).";
   private static final String OSE_EUNEXPECTED_EXCEPTION_REGDUMP_MSG =
      "An unexpected exception occurred, register dump available at address %x.";
   private static final String OSE_EEARLY_ERROR_MSG =
      "An error was reported before the kernel had started, " +
      "extra information available at address %x.";
   private static final String OSE_ESYSCALL_TOO_EARLY_MSG =
      "A system call was issued from a start handler, return address is %x.";
   private static final String OSE_EINCOMPATIBLE_CONFIGURATION_MSG =
      "Kernel configuration file (krncon.c) is incompatible with the kernel " +
      "library.";

   private static final String OSE_HEAP_EBIOS_INSTALL_FAILED_MSG =
      "Heap module could not install trap handler, extra information " +
      "available at address %x.";
   private static final String OSE_HEAP_EHUNT_HEAPD_FAILED_MSG =
      "Heap module could not find ose_heapd.";
   private static final String OSE_HEAP_EBIOS_OPEN_FAILED_MSG =
      "Heap module could not open heap BIOS module.";
   private static final String OSE_HEAP_EUNKNOWN_SIGNAL_MSG =
      "Heap daemon received unknown signal, extra information available at " +
      "address %x.";
   private static final String OSE_HEAP_EFREED_BUFFER_OVERWRITTEN_MSG =
      "Freed heap buffer corrupted, extra information available at address %x.";
   private static final String OSE_HEAP_ESIZE_TOO_LARGE_MSG =
      "Too large heap buffer size requested, extra information available at " +
      "address %x.";
   private static final String OSE_HEAP_EPTR_INVALID_MSG =
      "Invalid heap buffer pointer, extra information available at address %x.";
   private static final String OSE_HEAP_EBUFFER_FREE_MSG =
      "Operation on a freed heap buffer, extra information available at " +
      "address %x.";
   private static final String OSE_HEAP_EPROCESS_NOT_OWNER_MSG =
      "Heap buffer not owned by process, extra information available at " +
      "address %x.";
   private static final String OSE_HEAP_EENDMARK_OVERWRITTEN_MSG =
      "Heap buffer endmark corrupted, extra information available at " +
      "address %x.";
   private static final String OSE_HEAP_EHEAP_EXHAUSTED_MSG =
      "Heap exhausted, extra information available at address %x.";
   private static final String OSE_HEAP_ESYSMEM_ALLOC_FAILED_MSG =
      "Heap extension failed (failed to allocate memory from OSE memory " +
      "manager), extra information available at address %x.";
   private static final String OSE_HEAP_EDYNAMIC_LINK_FAILED_MSG =
      "Failed to link to a heap module.";
   private static final String OSE_HEAP_EMODULE_TOO_OLD_MSG =
      "The heap module link interface is too old or incompatible, versions " +
      "%x (found version upper 16 bits, expected version lower 16 bits).";
   private static final String OSE_HEAP_EHEAP_INCONSISTENT_MSG =
      "Heap inconsistent.";
   private static final String OSE_HEAP_EILLEGAL_EXTENSION_MSG =
      "Illegal heap extension or too large heap buffer allocation.";
   private static final String OSE_HEAP_EHEAP_CORRUPTED_MSG =
      "Heap corrupted.";
   private static final String OSE_HEAP_EILLEGAL_USER_MSG =
      "The first user of a heap was an interrupt process.";

   private static final String OSE_EFS_EABORT_CALLED_MSG =
      "Abort called, extra information available at address %x.";

   private static final String OSE_EASSERTION_FAILED_MSG =
      "Failed assertion, extra information available at address %x.";
   private static final String OSE_EUNKNOWN_SIGNAL_MSG =
      "Unknown signal received (signal buffer address %x).";

   private static final String[] OSE_SYSTEM_CALL_NAMES =
   {
      null,
      "addressee",
      "alloc",
      "assign_linkhandler",
      "attach",
      "attach_segment",
      "clear_bp",
      "create_block",
      "create_error_handler",
      "create_pool",
      "create_process",
      "create_sem",
      "current_process",
      "delay",
      "detach",
      "error",
      "flush",
      "free_buf",
      "get_bid",
      "get_bid_list",
      "get_cpu",
      "get_env",
      "get_env_list",
      "get_fsem",
      "get_mem",
      "get_pcb",
      "get_pid_list",
      "get_pri",
      "get_ptype",
      "get_sem",
      "get_signal",
      "get_systime",
      "get_ticks",
      "get_uid",
      "hunt",
      "hunt_from",
      "intercept",
      "kill_proc",
      "kill_sem",
      "power_fail",
      "receive",
      "receive_from",
      "receive_w_tmo",
      "restore",
      "resume",
      "send",
      "sender",
      "send_w_s",
      "set_bp",
      "set_env",
      "set_fsem",
      "set_mem",
      "set_pcb",
      "set_pri",
      "set_segment",
      "sigsize",
      "signal_fsem",
      "signal_sem",
      "start",
      "start_ose",
      "stop",
      "system_tick",
      "tick",
      "wait_fsem",
      "wait_sem",
      "wake_up",
      "extend_pool",
      "receive_with",
      "set_pri_for",
      "request_tmo_sig",
      "request_tmo",
      "cancel_tmo_sig",
      "cancel_tmo",
      "restart_tmo",
      "receive_sport",
      "set_redirection",
      "get_stk_poolid",
      "get_sig_poolid",
      "ose_mutex_init",
      "ose_mutex_destroy",
      "ose_mutex_lock",
      "ose_mutex_trylock",
      "ose_mutex_unlock",
      "ose_nano_delay"
   };

   /**
    * Return the verbose error message for the specified OSE error.
    *
    * @param userCalled  true if the error was user called, false if it was
    * kernel called.
    * @param error  the error code.
    * @param extra  the extra parameter to the error code.
    * @return the verbose error message for the specified OSE error or null if
    * unknown.
    */
   public static String getErrorMessage(boolean userCalled, long error, long extra)
   {
      String msg = null;

      if (userCalled)
      {
         msg = getApplicationErrorMessage(error);
      }
      else
      {
         msg = getKernelErrorMessage(error);
      }

      if (msg == null)
      {
         msg = getSpecialErrorMessage((int) error);
      }

      if (msg != null)
      {
         boolean fatal;
         StringBuffer sb;

         fatal = (error & OSE_EFATAL_MASK) != 0;
         sb = new StringBuffer(160);
         sb.append(fatal ? "Fatal error: " : "Error: ");
         sb.append(formatErrorMessage(msg, extra));
         if (!userCalled)
         {
            String systemCallName = getSystemCallName(error);
            if (systemCallName != null)
            {
               sb.append(System.getProperty("line.separator", "\n"));
               sb.append("System call: ");
               sb.append(systemCallName);
               sb.append("()");
            }
         }
         msg = sb.toString();
      }

      return msg;
   }

   private static String getKernelErrorMessage(long error)
   {
      int cause = (int) (error & OSE_EERROR_MASK);
      switch (cause)
      {
         case OSE_ENO_KERN_SPACE:
            return OSE_ENO_KERN_SPACE_MSG;
         case OSE_ENO_USER_SIGSPACE:
            return OSE_ENO_USER_SIGSPACE_MSG;
         case OSE_ENO_USER_STACKSPACE:
            return OSE_ENO_USER_STACKSPACE_MSG;
         case OSE_EPOOL_EXTENSION_FAILED:
            return OSE_EPOOL_EXTENSION_FAILED_MSG;
         case OSE_EBUFFER_TOO_LARGE:
            return OSE_EBUFFER_TOO_LARGE_MSG;
         case OSE_ESTACK_TOO_LARGE:
            return OSE_ESTACK_TOO_LARGE_MSG;
         case OSE_EBLOCK_HAS_PROCESSES:
            return OSE_EBLOCK_HAS_PROCESSES_MSG;
         case OSE_EILLEGAL_PROCESS_ID:
            return OSE_EILLEGAL_PROCESS_ID_MSG;
         case OSE_EUSED_NIL_POINTER:
            return OSE_EUSED_NIL_POINTER_MSG;
         case OSE_ENOT_SIG_OWNER:
            return OSE_ENOT_SIG_OWNER_MSG;
         case OSE_ECANNOT_RESTORE:
            return OSE_ECANNOT_RESTORE_MSG;
         case OSE_EUNREASONABLE_SIZES:
            return OSE_EUNREASONABLE_SIZES_MSG;
         case OSE_ETOO_MANY_PROCESSES:
            return OSE_ETOO_MANY_PROCESSES_MSG;
         case OSE_ETOO_MANY_ATTACHED:
            return OSE_ETOO_MANY_ATTACHED_MSG;
         case OSE_ECORRUPTED_KERNEL:
            return OSE_ECORRUPTED_KERNEL_MSG;
         case OSE_EVECTOR_IN_USE:
            return OSE_EVECTOR_IN_USE_MSG;
         case OSE_EILLEGAL_SYSTEMCALL:
            return OSE_EILLEGAL_SYSTEMCALL_MSG;
         case OSE_EINTERSEGMENT_SEND:
            return OSE_EINTERSEGMENT_SEND_MSG;
         case OSE_EBAD_INTLEVEL:
            return OSE_EBAD_INTLEVEL_MSG;
         case OSE_ERECURSIVE_ERROR:
            return OSE_ERECURSIVE_ERROR_MSG;
         case OSE_ERECURSIVE_SYSERROR:
            return OSE_ERECURSIVE_SYSERROR_MSG;
         case OSE_EILLEGAL_SEM_VALUE:
            return OSE_EILLEGAL_SEM_VALUE_MSG;
         case OSE_EILLEGAL_SEMAPHORE:
            return OSE_EILLEGAL_SEMAPHORE_MSG;
         case OSE_EKILLED_STATIC_SEMAPHORE:
            return OSE_EKILLED_STATIC_SEMAPHORE_MSG;
         case OSE_EKILLED_SEMAPHORE_IN_USE:
            return OSE_EKILLED_SEMAPHORE_IN_USE_MSG;
         case OSE_EINTERSEGMENT_SEMAPHORE_REF:
            return OSE_EINTERSEGMENT_SEMAPHORE_REF_MSG;
         case OSE_EDETACHED_TWICE:
            return OSE_EDETACHED_TWICE_MSG;
         case OSE_EDETACHED_TOO_LATE:
            return OSE_EDETACHED_TOO_LATE_MSG;
         case OSE_EDETACH_AFTER_RECEIVE:
            return OSE_EDETACH_AFTER_RECEIVE_MSG;
         case OSE_EBAD_PROCESS_TYPE:
            return OSE_EBAD_PROCESS_TYPE_MSG;
         case OSE_ENOT_ATTREF_OWNER:
            return OSE_ENOT_ATTREF_OWNER_MSG;
         case OSE_EILLEGAL_FLUSH_SENDER:
            return OSE_EILLEGAL_FLUSH_SENDER_MSG;
         case OSE_EKILLED_STATIC_PROCESS:
            return OSE_EKILLED_STATIC_PROCESS_MSG;
         case OSE_EILLEGAL_REDIRCOUNT:
            return OSE_EILLEGAL_REDIRCOUNT_MSG;
         case OSE_ESPURIOUS_INTERRUPT:
            return OSE_ESPURIOUS_INTERRUPT_MSG;
         case OSE_EATTACHED_TO_CALLER:
            return OSE_EATTACHED_TO_CALLER_MSG;
         case OSE_EINTERSEGMENT_COPY_FAILED:
            return OSE_EINTERSEGMENT_COPY_FAILED_MSG;
         case OSE_EINTSTACK_TOO_LARGE:
            return OSE_EINTSTACK_TOO_LARGE_MSG;
         case OSE_EKILLED_SYSTEM_DAEMON:
            return OSE_EKILLED_SYSTEM_DAEMON_MSG;
         case OSE_EFRAGMENT_TOO_SMALL:
            return OSE_EFRAGMENT_TOO_SMALL_MSG;
         case OSE_ECONCURRENT_TICK_CALL:
            return OSE_ECONCURRENT_TICK_CALL_MSG;
         case OSE_EILLEGAL_USER_CALL:
            return OSE_EILLEGAL_USER_CALL_MSG;
         case OSE_EALREADY_STARTED:
            return OSE_EALREADY_STARTED_MSG;
         case OSE_ELINKHANDLER_DEADLOCK:
            return OSE_ELINKHANDLER_DEADLOCK_MSG;
         case OSE_EILLEGAL_ENVNAME:
            return OSE_EILLEGAL_ENVNAME_MSG;
         case OSE_EILLEGAL_POOL:
            return OSE_EILLEGAL_POOL_MSG;
         case OSE_EWILD_SIG_POINTER:
            return OSE_EWILD_SIG_POINTER_MSG;
         case OSE_EBAD_PARAMETER:
            return OSE_EBAD_PARAMETER_MSG;
         case OSE_EFRAGMENT_IN_USE:
            return OSE_EFRAGMENT_IN_USE_MSG;
         case OSE_EILLEGAL_SUPER_MODE:
            return OSE_EILLEGAL_SUPER_MODE_MSG;
         case OSE_ESPURIOUS_SYSTEM_SIGNAL:
            return OSE_ESPURIOUS_SYSTEM_SIGNAL_MSG;
         case OSE_EFRAGMENT_TOO_LARGE:
            return OSE_EFRAGMENT_TOO_LARGE_MSG;
         case OSE_ESTOP_OVERFLOW:
            return OSE_ESTOP_OVERFLOW_MSG;
         case OSE_ESEM_OVERFLOW:
            return OSE_ESEM_OVERFLOW_MSG;
         case OSE_EBAD_HUNT_PRI:
            return OSE_EBAD_HUNT_PRI_MSG;
         case OSE_EILLEGAL_PRISYSTEMCALL:
            return OSE_EILLEGAL_PRISYSTEMCALL_MSG;
         case OSE_EILLEGAL_CONFIGURATION:
            return OSE_EILLEGAL_CONFIGURATION_MSG;
         case OSE_ETOO_MANY_TMO_REQUESTS:
            return OSE_ETOO_MANY_TMO_REQUESTS_MSG;
         case OSE_ETMO_ALREADY_CANCELLED:
            return OSE_ETMO_ALREADY_CANCELLED_MSG;
         case OSE_ETMO_ALREADY_RECEIVED:
            return OSE_ETMO_ALREADY_RECEIVED_MSG;
         case OSE_ECRT_KRN_INCOMPATIBLE:
            return OSE_ECRT_KRN_INCOMPATIBLE_MSG;
         case OSE_ETOO_MANY_PPD_KEYS:
            return OSE_ETOO_MANY_PPD_KEYS_MSG;
         case OSE_ETOO_LONG_PPD_KEY_STRING:
            return OSE_ETOO_LONG_PPD_KEY_STRING_MSG;
         case OSE_EILLEGAL_PPDKEY:
            return OSE_EILLEGAL_PPDKEY_MSG;
         case OSE_EMUTEX_INVALID:
            return OSE_EMUTEX_INVALID_MSG;
         case OSE_EMUTEX_ALREADY_INITIALIZED:
            return OSE_EMUTEX_ALREADY_INITIALIZED_MSG;
         case OSE_EMUTEX_IN_USE:
            return OSE_EMUTEX_IN_USE_MSG;
         case OSE_EMUTEX_DESTROYED:
            return OSE_EMUTEX_DESTROYED_MSG;
         case OSE_EMUTEX_LOCKED:
            return OSE_EMUTEX_LOCKED_MSG;
         case OSE_EMUTEX_DEADLOCK:
            return OSE_EMUTEX_DEADLOCK_MSG;
         case OSE_EMUTEX_NOT_OWNER:
            return OSE_EMUTEX_NOT_OWNER_MSG;
         case OSE_EMUTEX_OWNER_DEAD:
            return OSE_EMUTEX_OWNER_DEAD_MSG;
         case OSE_EILLEGAL_USER_MODE:
            return OSE_EILLEGAL_USER_MODE_MSG;
         case OSE_EPRIVILEGED_SYSCALL:
            return OSE_EPRIVILEGED_SYSCALL_MSG;
         case OSE_ETOO_MANY_MUTEXES:
            return OSE_ETOO_MANY_MUTEXES_MSG;
         case OSE_ENO_BUFFER_END_MARK:
            return OSE_ENO_BUFFER_END_MARK_MSG;
         case OSE_ECORRUPTED_POOL:
            return OSE_ECORRUPTED_POOL_MSG;
         case OSE_EUSER_STACK_OVERFLOW:
            return OSE_EUSER_STACK_OVERFLOW_MSG;
         case OSE_ESUPERV_STACK_OVERFLOW:
            return OSE_ESUPERV_STACK_OVERFLOW_MSG;
         case OSE_EINTERRUPT_STACK_OVERFLOW:
            return OSE_EINTERRUPT_STACK_OVERFLOW_MSG;
         case OSE_EUNKNOWN_BREAKPOINT:
            return OSE_EUNKNOWN_BREAKPOINT_MSG;
         case OSE_EUNKNOWN_INTERRUPT:
            return OSE_EUNKNOWN_INTERRUPT_MSG;
         case OSE_EPROCESS_ENDED:
            return OSE_EPROCESS_ENDED_MSG;
         case OSE_ESTART_STACK_OVERFLOW:
            return OSE_ESTART_STACK_OVERFLOW_MSG;
         case OSE_EUNRECOGNIZED_CPU:
            return OSE_EUNRECOGNIZED_CPU_MSG;
         case OSE_EINCOMPATIBLE_CPU_HAL:
            return OSE_EINCOMPATIBLE_CPU_HAL_MSG;
         case OSE_EUNDEFINED_SYSCALL_CODE:
            return OSE_EUNDEFINED_SYSCALL_CODE_MSG;
         case OSE_EUNEXPECTED_EXCEPTION:
            return OSE_EUNEXPECTED_EXCEPTION_MSG;
         case OSE_EPRIORITY_ERROR:
            return OSE_EPRIORITY_ERROR_MSG;
         case OSE_EUNEXPECTED_EXCEPTION_REGDUMP:
            return OSE_EUNEXPECTED_EXCEPTION_REGDUMP_MSG;
         case OSE_EEARLY_ERROR:
            return OSE_EEARLY_ERROR_MSG;
         case OSE_ESYSCALL_TOO_EARLY:
            return OSE_ESYSCALL_TOO_EARLY_MSG;
         case OSE_EINCOMPATIBLE_CONFIGURATION:
            return OSE_EINCOMPATIBLE_CONFIGURATION_MSG;
         default:
            return null;
      }
   }

   private static String getApplicationErrorMessage(long error)
   {
      int cause = (int) (error & OSE_EAPP_ERROR_MASK);
      switch (cause)
      {
         case OSE_HEAP_EBIOS_INSTALL_FAILED:
            return OSE_HEAP_EBIOS_INSTALL_FAILED_MSG;
         case OSE_HEAP_EHUNT_HEAPD_FAILED:
            return OSE_HEAP_EHUNT_HEAPD_FAILED_MSG;
         case OSE_HEAP_EBIOS_OPEN_FAILED:
            return OSE_HEAP_EBIOS_OPEN_FAILED_MSG;
         case OSE_HEAP_EUNKNOWN_SIGNAL:
            return OSE_HEAP_EUNKNOWN_SIGNAL_MSG;
         case OSE_HEAP_EFREED_BUFFER_OVERWRITTEN:
            return OSE_HEAP_EFREED_BUFFER_OVERWRITTEN_MSG;
         case OSE_HEAP_ESIZE_TOO_LARGE:
            return OSE_HEAP_ESIZE_TOO_LARGE_MSG;
         case OSE_HEAP_EPTR_INVALID:
            return OSE_HEAP_EPTR_INVALID_MSG;
         case OSE_HEAP_EBUFFER_FREE:
            return OSE_HEAP_EBUFFER_FREE_MSG;
         case OSE_HEAP_EPROCESS_NOT_OWNER:
            return OSE_HEAP_EPROCESS_NOT_OWNER_MSG;
         case OSE_HEAP_EENDMARK_OVERWRITTEN:
            return OSE_HEAP_EENDMARK_OVERWRITTEN_MSG;
         case OSE_HEAP_EHEAP_EXHAUSTED:
            return OSE_HEAP_EHEAP_EXHAUSTED_MSG;
         case OSE_HEAP_ESYSMEM_ALLOC_FAILED:
            return OSE_HEAP_ESYSMEM_ALLOC_FAILED_MSG;
         case OSE_HEAP_EDYNAMIC_LINK_FAILED:
            return OSE_HEAP_EDYNAMIC_LINK_FAILED_MSG;
         case OSE_HEAP_EMODULE_TOO_OLD:
            return OSE_HEAP_EMODULE_TOO_OLD_MSG;
         case OSE_HEAP_EHEAP_INCONSISTENT:
            return OSE_HEAP_EHEAP_INCONSISTENT_MSG;
         case OSE_HEAP_EILLEGAL_EXTENSION:
            return OSE_HEAP_EILLEGAL_EXTENSION_MSG;
         case OSE_HEAP_EHEAP_CORRUPTED:
            return OSE_HEAP_EHEAP_CORRUPTED_MSG;
         case OSE_HEAP_EILLEGAL_USER:
            return OSE_HEAP_EILLEGAL_USER_MSG;
         case OSE_EFS_EABORT_CALLED:
            return OSE_EFS_EABORT_CALLED_MSG;
         default:
            return null;
      }
   }

   private static String getSpecialErrorMessage(int error)
   {
      switch (error)
      {
         case OSE_EASSERTION_FAILED:
            return OSE_EASSERTION_FAILED_MSG;
         case OSE_EUNKNOWN_SIGNAL:
            return OSE_EUNKNOWN_SIGNAL_MSG;
         default:
            return null;
      }
   }

   private static String getSystemCallName(long error)
   {
      String systemCallName = null;
      int cause;

      cause = (int) (error & OSE_EERROR_MASK);
      if (cause < 0x100)
      {
         int subcode = (int) ((error & OSE_ESUBCODE_MASK) >> 16);
         if ((subcode >= 0) && (subcode < OSE_SYSTEM_CALL_NAMES.length))
         {
            systemCallName = OSE_SYSTEM_CALL_NAMES[subcode];
         }
      }

      return systemCallName;
   }

   private static String formatErrorMessage(String msg, long extra)
   {
      String d;
      String u;
      String x;

      // Supports %d (signed decimal integer), %u (unsigned decimal integer),
      // and %x (unsigned hexadecimal integer).

      d = Long.toString(extra);
      u = Long.toString(0xFFFFFFFFL & extra);
      x = "0x" + Long.toHexString(extra).toUpperCase();
      msg = msg.replaceAll("%d", d);
      msg = msg.replaceAll("%u", u);
      msg = msg.replaceAll("%x", x);
      return msg;
   }
}
