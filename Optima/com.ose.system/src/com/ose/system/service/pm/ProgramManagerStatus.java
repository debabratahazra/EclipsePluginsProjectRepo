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

package com.ose.system.service.pm;

public class ProgramManagerStatus
{
   public static final int PM_SUCCESS                              = 0x00000000;
   public static final int PM_EFEATURE_NOT_SUPPORTED               = 0x00000001;
   public static final int PM_ENOT_IN_CORE                         = 0x00000002;
   public static final int PM_ENO_PROGRAM_MANAGER                  = 0x00000003;
   public static final int PM_EOPERATION_TIMED_OUT                 = 0x0000FFFE;
   public static final int PM_EUNKNOWN_ECODE                       = 0x0000FFFF;
   public static final int PM_EILLEGAL_PROGRAM_TYPE                = 0x00010000;
   public static final int PM_EILLEGAL_REGION_TYPE                 = 0x00010001;
   public static final int PM_EPROGRAM_TYPE_SYNTAX_ERROR           = 0x00010002;
   public static final int PM_EPROGRAM_TYPE_PARAM_NAME_CORRUPT     = 0x00010003;
   public static final int PM_EPROGRAM_TYPE_PARAM_SUPER_CORRUPT    = 0x00010004;
   public static final int PM_EPROGRAM_TYPE_PARAM_REGIONS          = 0x00010005;
   public static final int PM_EFILE_FORMAT_NOT_SUPPORTED           = 0x00020000;
   public static final int PM_EINSTALL_HANDLE                      = 0x00030000;
   public static final int PM_EINSTALL_HANDLE_ALREADY_INSTALLED    = 0x00030001;
   public static final int PM_EDOMAIN_NOT_FOUND                    = 0x00040000;
   public static final int PM_EPROGRAM_CONFIGURATION               = 0x00040001;
   public static final int PM_EOPTION_UNKNOWN                      = 0x00040002;
   public static final int PM_EBLOCK_CREATION_FAILED               = 0x00040003;
   public static final int PM_EPROCESS_CREATION_FAILED             = 0x00040004;
   public static final int PM_EMULTIPLE_PROGRAMS_IN_DOMAIN         = 0x00040005;
   public static final int PM_EINSTALL_HANDLE_IN_USE               = 0x00040006;
   public static final int PM_EOPERATION_ON_CORE                   = 0x00040007;
   public static final int PM_EPROGRAM_ALREADY_STARTED             = 0x00040008;
   public static final int PM_ECORE_PROGRAM_MUST_BE_SUPERVISOR     = 0x00040009;
   public static final int PM_EPROGRAM_CONFIGURATION_POOL_SIZE     = 0x0004000A;
   public static final int PM_EPROGPID                             = 0x00050000;
   public static final int PM_EREGION_DELETION_FAILED              = 0x00060000;
   public static final int PM_EREGION_CREATION_FAILED              = 0x00060001;
   public static final int PM_EDOMAIN_DELETION_FAILED              = 0x00060002;
   public static final int PM_EDOMAIN_CREATION_FAILED              = 0x00060003;
   public static final int PM_EUNINSTALL_PROGRAM_EXISTS            = 0x00070000;
   public static final int PM_ESECTION_INTERVAL                    = 0x00070002;
   public static final int PM_ECORRUPT_CONFIGURATION               = 0x00070003;
   public static final int PM_ENO_MAGIC                            = 0x00070004;
   public static final int PM_EPARAMS_CONF_SIZE_CONF_NO_OF_STRINGS = 0x00070005;
   public static final int PM_EPARAM_CONF_SIZE                     = 0x00070006;
   public static final int PM_EPARAM_CONF_NO_OF_STRINGS            = 0x00070007;
   public static final int PM_ENO_PROGRAM_TYPE                     = 0x00070008;
   public static final int PM_EFILE_NAME_CORRUPT                   = 0x00070009;
   public static final int PM_EBAD_CHECKSUM                        = 0x0007000A;
   public static final int PM_ENO_CHECKSUM                         = 0x0007000B;
   public static final int PM_ERTL_INTERNAL_ERROR                  = 0x00080000;
   public static final int PM_ERTL_LOST                            = 0x00080001;
   public static final int PM_ENOT_FOUND                           = 0x00080002;
   public static final int RTL_EALREADY_CONNECTED                  = 0xFFFF0000;
   public static final int RTL_EILLEGAL_CONFIGURATION              = 0xFFFF0001;
   public static final int RTL_EREAD_FAIL                          = 0xFFFF0002;
   public static final int RTL_EOPEN_FAIL                          = 0xFFFF0003;
   public static final int RTL_EUNSUPPORTED_RELOCATION             = 0xFFFF0004;
   public static final int RTL_EMEMORY_ALLOC_DENIED                = 0xFFFF0005;
   public static final int RTL_EINTERNAL_WRITE                     = 0xFFFF0006;
   public static final int RTL_EINTERFACE_MISMATCH                 = 0xFFFF0007;
   public static final int RTL_EPROGRAM_TYPE                       = 0xFFFF0008;
   public static final int RTL_ECHANGE_PERMISSION_DENIED_BY_MM     = 0xFFFF0009;
   public static final int RTL_EBACKWARD_REFERENCE                 = 0xFFFF000A;
   public static final int RTL_ENO_SU_READ_EXECUTE                 = 0xFFFF000B;
   public static final int RTL_EFLUSH_OR_CHECKSUM                  = 0xFFFF000C;
   public static final int RTL_ECORRUPT_REQUEST_SIGNAL             = 0xFFFF000D;
   public static final int RTL_EMISSING_CONFIGURATION              = 0xFFFF000E;
   public static final int RTL_ELF_ELOAD_BEFORE_PT_DYNAMIC         = 0xFFFF0100;
   public static final int RTL_ELF_EELF_HEADER_CORRUPT             = 0xFFFF0101;
   public static final int RTL_ELF_ESEGMENT_READ_FAIL              = 0xFFFF0102;
   public static final int RTL_ELF_ESECTION_READ_FAIL              = 0xFFFF0103;
   public static final int RTL_ELF_ENO_PROGRAM_HEADERS             = 0xFFFF0104;
   public static final int RTL_ELF_ENO_PT_LOAD                     = 0xFFFF0105;
   public static final int RTL_ELF_EELF_NOT_EXECUTABLE             = 0xFFFF0106;
   public static final int RTL_ELF_ENO_LM_CONF_SEGMENT             = 0xFFFF0107;
   public static final int RTL_ELF_ESEGMENT_BEFORE_HEADER          = 0xFFFF0108;
   public static final int RTL_ELF_ENO_RELOCATION_INFO             = 0xFFFF0109;
   public static final int RTL_ELF_ENO_TEXT                        = 0xFFFF010A;
   public static final int RTL_ELF_ENO_DATA                        = 0xFFFF010B;
   public static final int RTL_ELF_EHOLE_BETWEEN_DATA              = 0xFFFF010C;
   public static final int RTL_ELF_ENO_BIOSENTRY_SECTION           = 0xFFFF010D;

   private static final String PM_SUCCESS_MSG =
      "The operation was successful.";
   private static final String PM_EFEATURE_NOT_SUPPORTED_MSG =
      "A requested feature was not supported.";
   private static final String PM_ENOT_IN_CORE_MSG =
      "The program manager was not executing as part of the core program.";
   private static final String PM_ENO_PROGRAM_MANAGER_MSG =
      "The program manager process was not found by the functional API.";
   private static final String PM_EOPERATION_TIMED_OUT_MSG =
      "PM operation timed out.";
   private static final String PM_EUNKNOWN_ECODE_MSG =
      "Unknown error code received from PM.";
   private static final String PM_EILLEGAL_PROGRAM_TYPE_MSG =
      "The referenced program type was not found.";
   private static final String PM_EILLEGAL_REGION_TYPE_MSG =
      "The referenced program region type was not found.";
   private static final String PM_EPROGRAM_TYPE_SYNTAX_ERROR_MSG =
      "The program type has illegal syntax.";
   private static final String PM_EPROGRAM_TYPE_PARAM_NAME_CORRUPT_MSG =
      "A program type name was too long.";
   private static final String PM_EPROGRAM_TYPE_PARAM_SUPER_CORRUPT_MSG =
      "A program execution mode was not correctly set.";
   private static final String PM_EPROGRAM_TYPE_PARAM_REGIONS_MSG =
      "The number of program types in an add program request is invalid.";
   private static final String PM_EFILE_FORMAT_NOT_SUPPORTED_MSG =
      "The referenced file format was not supported.";
   private static final String PM_EINSTALL_HANDLE_MSG =
      "The install handle specified was not found.";
   private static final String PM_EINSTALL_HANDLE_ALREADY_INSTALLED_MSG =
      "The install handle was already in use, the install operation was aborted.";
   private static final String PM_EDOMAIN_NOT_FOUND_MSG =
      "The referenced domain was not found.";
   private static final String PM_EPROGRAM_CONFIGURATION_MSG =
      "The program configuration was incomplete or corrupt.";
   private static final String PM_EOPTION_UNKNOWN_MSG =
      "An option field in a PM request included an unknown or incorrect option.";
   private static final String PM_EBLOCK_CREATION_FAILED_MSG =
      "The main block creation failed during program creation.";
   private static final String PM_EPROCESS_CREATION_FAILED_MSG =
      "The main process creation failed during program creation.";
   private static final String PM_EMULTIPLE_PROGRAMS_IN_DOMAIN_MSG =
      "Multiple programs in one domain is not supported with the system's " +
      "memory configuration.";
   private static final String PM_EINSTALL_HANDLE_IN_USE_MSG =
      "The install handle was already in use by another program.";
   private static final String PM_EOPERATION_ON_CORE_MSG =
      "An unallowed operation was requested on the core program.";
   private static final String PM_EPROGRAM_ALREADY_STARTED_MSG =
      "A start program request could not be performed because the program " +
      "has already started.";
   private static final String PM_ECORE_PROGRAM_MUST_BE_SUPERVISOR_MSG =
      "Attempted to create a program with program id 0 in user mode.";
   private static final String PM_EPROGRAM_CONFIGURATION_POOL_SIZE_MSG =
      "The pool size in the configuration has an illegal value.";
   private static final String PM_EPROGPID_MSG =
      "A supplied program id was not recognized.";
   private static final String PM_EREGION_DELETION_FAILED_MSG =
      "A memory region could not be deleted.";
   private static final String PM_EREGION_CREATION_FAILED_MSG =
      "A memory region could not be created.";
   private static final String PM_EDOMAIN_DELETION_FAILED_MSG =
      "A domain could not be deleted.";
   private static final String PM_EDOMAIN_CREATION_FAILED_MSG =
      "A domain could not be created.";
   private static final String PM_EUNINSTALL_PROGRAM_EXISTS_MSG =
      "The load module cannot be uninstalled, at least one program created " +
      "from the load module exists.";
   private static final String PM_ESECTION_INTERVAL_MSG =
      "The section interval does not follow the given syntax.";
   private static final String PM_ECORRUPT_CONFIGURATION_MSG =
      "The configuration of a load module was corrupt.";
   private static final String PM_ENO_MAGIC_MSG =
      "The magic string in the configuration was missing or corrupt.";
   private static final String PM_EPARAMS_CONF_SIZE_CONF_NO_OF_STRINGS_MSG =
      "An ambiguous size of the configuration was specified.";
   private static final String PM_EPARAM_CONF_SIZE_MSG =
      "An incorrect size of the configuration was specified.";
   private static final String PM_EPARAM_CONF_NO_OF_STRINGS_MSG =
      "An incorrect number of configuration items was specified.";
   private static final String PM_ENO_PROGRAM_TYPE_MSG =
      "The configuration did not contain any program type, or there was a " +
      "syntax error in the entry.";
   private static final String PM_EFILE_NAME_CORRUPT_MSG =
      "The file name is too long or not terminated.";
   private static final String PM_EBAD_CHECKSUM_MSG =
      "The checksum of a load module was not correct.";
   private static final String PM_ENO_CHECKSUM_MSG =
      "The checkum of a load module was not calculatable (alien load module).";
   private static final String PM_ERTL_INTERNAL_ERROR_MSG =
      "The RTL used when installing a load module replied with corrupted parameters.";
   private static final String PM_ERTL_LOST_MSG =
      "A removed RTL is required for an operation.";
   private static final String PM_ENOT_FOUND_MSG =
      "The requested RTL can not be found.";
   private static final String RTL_EALREADY_CONNECTED_MSG =
      "The RTL is already connected.";
   private static final String RTL_EILLEGAL_CONFIGURATION_MSG =
      "The load module configuration or the override configuration has illegal syntax.";
   private static final String RTL_EREAD_FAIL_MSG =
      "A read error occurred while reading a load module file.";
   private static final String RTL_EOPEN_FAIL_MSG =
      "The load module file could not be opened.";
   private static final String RTL_EUNSUPPORTED_RELOCATION_MSG =
      "An unsupported relocation was encountered in the load module.";
   private static final String RTL_EMEMORY_ALLOC_DENIED_MSG =
      "A memory region allocation was not successful.";
   private static final String RTL_EINTERNAL_WRITE_MSG =
      "An internal RTL error occurred. The RTL was unable to write to a region.";
   private static final String RTL_EINTERFACE_MISMATCH_MSG =
      "The RTL signal interface did not match the PM's rtl.sig signal interface.";
   private static final String RTL_EPROGRAM_TYPE_MSG =
      "The load module configuration does not contain program type information.";
   private static final String RTL_ECHANGE_PERMISSION_DENIED_BY_MM_MSG =
      "MM denied a region permission change.";
   private static final String RTL_EBACKWARD_REFERENCE_MSG =
      "A backward reference in the file. The load module is not structured " +
      "for stream loading.";
   private static final String RTL_ENO_SU_READ_EXECUTE_MSG =
      "Illegal configuration, a 'text' region must allow read and execute in " +
      "supervisor execution mode.";
   private static final String RTL_EFLUSH_OR_CHECKSUM_MSG =
      "The instruction cache could not be flushed, or the checksum could not " +
      "be calculated.";
   private static final String RTL_ECORRUPT_REQUEST_SIGNAL_MSG =
      "The install request was corrupt. Either a string was NULL, longer " +
      "than allowed, or the option field had unknown options specified.";
   private static final String RTL_EMISSING_CONFIGURATION_MSG =
      "The configuration of a load module is missing a mandatory configuration.";
   private static final String RTL_ELF_ELOAD_BEFORE_PT_DYNAMIC_MSG =
      "The '.dynamic' section was located in the wrong place in the load module.";
   private static final String RTL_ELF_EELF_HEADER_CORRUPT_MSG =
      "The ELF header of the load module is corrupt.";
   private static final String RTL_ELF_ESEGMENT_READ_FAIL_MSG =
      "A backward reference in the load module file. The segments are not " +
      "sorted for stream loading.";
   private static final String RTL_ELF_ESECTION_READ_FAIL_MSG =
      "A backward reference in the load module file. The sections are not " +
      "sorted for stream loading.";
   private static final String RTL_ELF_ENO_PROGRAM_HEADERS_MSG =
      "The load module does not contain any program headers.";
   private static final String RTL_ELF_ENO_PT_LOAD_MSG =
      "The load module does not contain any load segments.";
   private static final String RTL_ELF_EELF_NOT_EXECUTABLE_MSG =
      "The ELF file lacks execute primitive (ET_EXEC).";
   private static final String RTL_ELF_ENO_LM_CONF_SEGMENT_MSG =
      "The load module lacks the configuration segment or the configuration " +
      "has syntax error.";
   private static final String RTL_ELF_ESEGMENT_BEFORE_HEADER_MSG =
      "The ELF segment(s) are placed before the program header(s) in the file. " +
      "This will lead to backward references in the file.";
   private static final String RTL_ELF_ENO_RELOCATION_INFO_MSG =
      "The load module lacks the dynamic relocation information segment " +
      "(non-absolute install).";
   private static final String RTL_ELF_ENO_TEXT_MSG =
      "The load module does not contain any 'text' (code or read-only data).";
   private static final String RTL_ELF_ENO_DATA_MSG =
      "The load module does not contain any 'data' (uninitiated data).";
   private static final String RTL_ELF_EHOLE_BETWEEN_DATA_MSG =
      "The ELF file has a hole between data segments, and it is being " +
      "installed absolute. This leads to unnecessary memory usage.";
   private static final String RTL_ELF_ENO_BIOSENTRY_SECTION_MSG =
      "The SFK load module lacks the BIOS entry section called " +
      "'.ose_sfk_biosentry'.";
   private static final String PM_EUNKNOWN_SPECIFIED_ECODE_MSG =
      "Unknown error code received from PM: ";

   public static String getMessage(int status)
   {
      switch (status)
      {
      case PM_SUCCESS:
         return PM_SUCCESS_MSG;
      case PM_EFEATURE_NOT_SUPPORTED:
         return PM_EFEATURE_NOT_SUPPORTED_MSG;
      case PM_ENOT_IN_CORE:
         return PM_ENOT_IN_CORE_MSG;
      case PM_ENO_PROGRAM_MANAGER:
         return PM_ENO_PROGRAM_MANAGER_MSG;
      case PM_EOPERATION_TIMED_OUT:
         return PM_EOPERATION_TIMED_OUT_MSG;
      case PM_EUNKNOWN_ECODE:
         return PM_EUNKNOWN_ECODE_MSG;
      case PM_EILLEGAL_PROGRAM_TYPE:
         return PM_EILLEGAL_PROGRAM_TYPE_MSG;
      case PM_EILLEGAL_REGION_TYPE:
         return PM_EILLEGAL_REGION_TYPE_MSG;
      case PM_EPROGRAM_TYPE_SYNTAX_ERROR:
         return PM_EPROGRAM_TYPE_SYNTAX_ERROR_MSG;
      case PM_EPROGRAM_TYPE_PARAM_NAME_CORRUPT:
         return PM_EPROGRAM_TYPE_PARAM_NAME_CORRUPT_MSG;
      case PM_EPROGRAM_TYPE_PARAM_SUPER_CORRUPT:
         return PM_EPROGRAM_TYPE_PARAM_SUPER_CORRUPT_MSG;
      case PM_EPROGRAM_TYPE_PARAM_REGIONS:
         return PM_EPROGRAM_TYPE_PARAM_REGIONS_MSG;
      case PM_EFILE_FORMAT_NOT_SUPPORTED:
         return PM_EFILE_FORMAT_NOT_SUPPORTED_MSG;
      case PM_EINSTALL_HANDLE:
         return PM_EINSTALL_HANDLE_MSG;
      case PM_EINSTALL_HANDLE_ALREADY_INSTALLED:
         return PM_EINSTALL_HANDLE_ALREADY_INSTALLED_MSG;
      case PM_EDOMAIN_NOT_FOUND:
         return PM_EDOMAIN_NOT_FOUND_MSG;
      case PM_EPROGRAM_CONFIGURATION:
         return PM_EPROGRAM_CONFIGURATION_MSG;
      case PM_EOPTION_UNKNOWN:
         return PM_EOPTION_UNKNOWN_MSG;
      case PM_EBLOCK_CREATION_FAILED:
         return PM_EBLOCK_CREATION_FAILED_MSG;
      case PM_EPROCESS_CREATION_FAILED:
         return PM_EPROCESS_CREATION_FAILED_MSG;
      case PM_EMULTIPLE_PROGRAMS_IN_DOMAIN:
         return PM_EMULTIPLE_PROGRAMS_IN_DOMAIN_MSG;
      case PM_EINSTALL_HANDLE_IN_USE:
         return PM_EINSTALL_HANDLE_IN_USE_MSG;
      case PM_EOPERATION_ON_CORE:
         return PM_EOPERATION_ON_CORE_MSG;
      case PM_EPROGRAM_ALREADY_STARTED:
         return PM_EPROGRAM_ALREADY_STARTED_MSG;
      case PM_ECORE_PROGRAM_MUST_BE_SUPERVISOR:
         return PM_ECORE_PROGRAM_MUST_BE_SUPERVISOR_MSG;
      case PM_EPROGRAM_CONFIGURATION_POOL_SIZE:
         return PM_EPROGRAM_CONFIGURATION_POOL_SIZE_MSG;
      case PM_EPROGPID:
         return PM_EPROGPID_MSG;
      case PM_EREGION_DELETION_FAILED:
         return PM_EREGION_DELETION_FAILED_MSG;
      case PM_EREGION_CREATION_FAILED:
         return PM_EREGION_CREATION_FAILED_MSG;
      case PM_EDOMAIN_DELETION_FAILED:
         return PM_EDOMAIN_DELETION_FAILED_MSG;
      case PM_EDOMAIN_CREATION_FAILED:
         return PM_EDOMAIN_CREATION_FAILED_MSG;
      case PM_EUNINSTALL_PROGRAM_EXISTS:
         return PM_EUNINSTALL_PROGRAM_EXISTS_MSG;
      case PM_ESECTION_INTERVAL:
         return PM_ESECTION_INTERVAL_MSG;
      case PM_ECORRUPT_CONFIGURATION:
         return PM_ECORRUPT_CONFIGURATION_MSG;
      case PM_ENO_MAGIC:
         return PM_ENO_MAGIC_MSG;
      case PM_EPARAMS_CONF_SIZE_CONF_NO_OF_STRINGS:
         return PM_EPARAMS_CONF_SIZE_CONF_NO_OF_STRINGS_MSG;
      case PM_EPARAM_CONF_SIZE:
         return PM_EPARAM_CONF_SIZE_MSG;
      case PM_EPARAM_CONF_NO_OF_STRINGS:
         return PM_EPARAM_CONF_NO_OF_STRINGS_MSG;
      case PM_ENO_PROGRAM_TYPE:
         return PM_ENO_PROGRAM_TYPE_MSG;
      case PM_EFILE_NAME_CORRUPT:
         return PM_EFILE_NAME_CORRUPT_MSG;
      case PM_EBAD_CHECKSUM:
         return PM_EBAD_CHECKSUM_MSG;
      case PM_ENO_CHECKSUM:
         return PM_ENO_CHECKSUM_MSG;
      case PM_ERTL_INTERNAL_ERROR:
         return PM_ERTL_INTERNAL_ERROR_MSG;
      case PM_ERTL_LOST:
         return PM_ERTL_LOST_MSG;
      case PM_ENOT_FOUND:
         return PM_ENOT_FOUND_MSG;
      case RTL_EALREADY_CONNECTED:
         return RTL_EALREADY_CONNECTED_MSG;
      case RTL_EILLEGAL_CONFIGURATION:
         return RTL_EILLEGAL_CONFIGURATION_MSG;
      case RTL_EREAD_FAIL:
         return RTL_EREAD_FAIL_MSG;
      case RTL_EOPEN_FAIL:
         return RTL_EOPEN_FAIL_MSG;
      case RTL_EUNSUPPORTED_RELOCATION:
         return RTL_EUNSUPPORTED_RELOCATION_MSG;
      case RTL_EMEMORY_ALLOC_DENIED:
         return RTL_EMEMORY_ALLOC_DENIED_MSG;
      case RTL_EINTERNAL_WRITE:
         return RTL_EINTERNAL_WRITE_MSG;
      case RTL_EINTERFACE_MISMATCH:
         return RTL_EINTERFACE_MISMATCH_MSG;
      case RTL_EPROGRAM_TYPE:
         return RTL_EPROGRAM_TYPE_MSG;
      case RTL_ECHANGE_PERMISSION_DENIED_BY_MM:
         return RTL_ECHANGE_PERMISSION_DENIED_BY_MM_MSG;
      case RTL_EBACKWARD_REFERENCE:
         return RTL_EBACKWARD_REFERENCE_MSG;
      case RTL_ENO_SU_READ_EXECUTE:
         return RTL_ENO_SU_READ_EXECUTE_MSG;
      case RTL_EFLUSH_OR_CHECKSUM:
         return RTL_EFLUSH_OR_CHECKSUM_MSG;
      case RTL_ECORRUPT_REQUEST_SIGNAL:
         return RTL_ECORRUPT_REQUEST_SIGNAL_MSG;
      case RTL_EMISSING_CONFIGURATION:
         return RTL_EMISSING_CONFIGURATION_MSG;
      case RTL_ELF_ELOAD_BEFORE_PT_DYNAMIC:
         return RTL_ELF_ELOAD_BEFORE_PT_DYNAMIC_MSG;
      case RTL_ELF_EELF_HEADER_CORRUPT:
         return RTL_ELF_EELF_HEADER_CORRUPT_MSG;
      case RTL_ELF_ESEGMENT_READ_FAIL:
         return RTL_ELF_ESEGMENT_READ_FAIL_MSG;
      case RTL_ELF_ESECTION_READ_FAIL:
         return RTL_ELF_ESECTION_READ_FAIL_MSG;
      case RTL_ELF_ENO_PROGRAM_HEADERS:
         return RTL_ELF_ENO_PROGRAM_HEADERS_MSG;
      case RTL_ELF_ENO_PT_LOAD:
         return RTL_ELF_ENO_PT_LOAD_MSG;
      case RTL_ELF_EELF_NOT_EXECUTABLE:
         return RTL_ELF_EELF_NOT_EXECUTABLE_MSG;
      case RTL_ELF_ENO_LM_CONF_SEGMENT:
         return RTL_ELF_ENO_LM_CONF_SEGMENT_MSG;
      case RTL_ELF_ESEGMENT_BEFORE_HEADER:
         return RTL_ELF_ESEGMENT_BEFORE_HEADER_MSG;
      case RTL_ELF_ENO_RELOCATION_INFO:
         return RTL_ELF_ENO_RELOCATION_INFO_MSG;
      case RTL_ELF_ENO_TEXT:
         return RTL_ELF_ENO_TEXT_MSG;
      case RTL_ELF_ENO_DATA:
         return RTL_ELF_ENO_DATA_MSG;
      case RTL_ELF_EHOLE_BETWEEN_DATA:
         return RTL_ELF_EHOLE_BETWEEN_DATA_MSG;
      case RTL_ELF_ENO_BIOSENTRY_SECTION:
         return RTL_ELF_ENO_BIOSENTRY_SECTION_MSG;
      default:
         return PM_EUNKNOWN_SPECIFIED_ECODE_MSG + status;
      }
   }
}
