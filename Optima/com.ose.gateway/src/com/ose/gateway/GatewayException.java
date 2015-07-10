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

package com.ose.gateway;

import java.io.IOException;

/**
 * Thrown when a gateway error occurs.
 */
public class GatewayException extends IOException
{
   private static final long serialVersionUID = 1L;

   /* The gateway error codes. */
   public static final int OSEGW_EOK                   = 0x0;
   public static final int OSEGW_EPROTOCOL_ERROR       = 0x201;
   public static final int OSEGW_EUNKNOWN_ECODE        = 0x202;
   public static final int OSEGW_ELOGIN_FAILED         = 0x206;
   public static final int OSEGW_EUNSUPPORTED_AUTH     = 0x207;
   public static final int OSEGW_EBUFFER_TOO_LARGE     = 0x11;
   public static final int OSEGW_ENO_USER_SIGSPACE     = 0x20;
   public static final int OSEGW_EUSED_NIL_POINTER     = 0x31;
   public static final int OSEGW_EILLEGAL_PROCESS_ID   = 0x32;
   public static final int OSEGW_ENOT_SIG_OWNER        = 0x5F;
   public static final int OSEGW_EBAD_PARAMETER        = 0x71;
   public static final int OSEGW_ENO_BUFFER_END_MARK   = 0xA5;
   public static final int OSEGW_ETOO_MANY_ATTACHED    = 0x24;
   public static final int OSEGW_EDETACHED_TWICE       = 0x2D;
   public static final int OSEGW_EDETACH_AFTER_RECEIVE = 0x2F;
   public static final int OSEGW_EATTACHED_TO_CALLER   = 0x53;

   /* The gateway error messages. */
   private static final String OSEGW_EOK_MSG =
      "Gateway operation successful.";
   private static final String OSEGW_EPROTOCOL_ERROR_MSG =
      "The gateway client got some response data which it did not expect.";
   private static final String OSEGW_EUNKNOWN_ECODE_MSG =
      "The gateway server has responded to a request with an unknown error code.";
   private static final String OSEGW_ELOGIN_FAILED_MSG =
      "The gateway client was unable to login to the specified gateway server.";
   private static final String OSEGW_EUNSUPPORTED_AUTH_MSG =
      "The gateway client and gateway server were unable to negotiate upon " +
      "any authentication scheme.";
   private static final String OSEGW_EBUFFER_TOO_LARGE_MSG =
      "Too large signal buffer requested.";
   private static final String OSEGW_ENO_USER_SIGSPACE_MSG =
      "Out of space when trying to allocate a signal buffer.";
   private static final String OSEGW_EUSED_NIL_POINTER_MSG =
      "The gateway client tried to operate on an invalid signal buffer pointer.";
   private static final String OSEGW_EILLEGAL_PROCESS_ID_MSG =
      "An illegal block or process ID was presented to the kernel.";
   private static final String OSEGW_ENOT_SIG_OWNER_MSG =
      "The gateway client is not the owner of the specified signal buffer.";
   private static final String OSEGW_EBAD_PARAMETER_MSG =
      "An invalid parameter was used in a kernel system call.";
   private static final String OSEGW_ENO_BUFFER_END_MARK_MSG =
      "A valid end mark could not be found in the signal buffer presented " +
      "to the kernel.";
   private static final String OSEGW_ETOO_MANY_ATTACHED_MSG =
      "Too many signals were attached.";
   private static final String OSEGW_EDETACHED_TWICE_MSG =
      "An attempt was made to detach from an already detached process.";
   private static final String OSEGW_EDETACH_AFTER_RECEIVE_MSG =
      "An attempt was made to detach from a process when the attached signal " +
      "has already been received.";
   private static final String OSEGW_EATTACHED_TO_CALLER_MSG =
      "An attempt was made to attach to the process ID representing the " +
      "gateway client.";
   private static final String OSEGW_EUNKNOWN_SPECIFIED_ECODE_MSG =
      "The gateway server has responded to a request with an unknown error code: ";

   /* The gateway error code. */
   private int errorCode;

   /**
    * Create a new gateway exception object.
    *
    * @param errorCode  the gateway error code.
    */
   public GatewayException(int errorCode)
   {
      super(getErrorMessage(errorCode));
      this.errorCode = errorCode;
   }

   /**
    * Return the gateway error code.
    *
    * @return the gateway error code.
    */
   public int getErrorCode()
   {
      return errorCode;
   }

   /*
    * Return the gateway error message for the specified gateway error code.
    *
    * @param errorCode  a gateway error code.
    * @return the gateway error message for the specified gateway error code.
    */
   private static String getErrorMessage(int errorCode)
   {
      switch (errorCode)
      {
      case OSEGW_EOK:
         return OSEGW_EOK_MSG;
      case OSEGW_EPROTOCOL_ERROR:
         return OSEGW_EPROTOCOL_ERROR_MSG;
      case OSEGW_EUNKNOWN_ECODE:
         return OSEGW_EUNKNOWN_ECODE_MSG;
      case OSEGW_ELOGIN_FAILED:
         return OSEGW_ELOGIN_FAILED_MSG;
      case OSEGW_EUNSUPPORTED_AUTH:
         return OSEGW_EUNSUPPORTED_AUTH_MSG;
      case OSEGW_EBUFFER_TOO_LARGE:
         return OSEGW_EBUFFER_TOO_LARGE_MSG;
      case OSEGW_ENO_USER_SIGSPACE:
         return OSEGW_ENO_USER_SIGSPACE_MSG;
      case OSEGW_EUSED_NIL_POINTER:
         return OSEGW_EUSED_NIL_POINTER_MSG;
      case OSEGW_EILLEGAL_PROCESS_ID:
         return OSEGW_EILLEGAL_PROCESS_ID_MSG;
      case OSEGW_ENOT_SIG_OWNER:
         return OSEGW_ENOT_SIG_OWNER_MSG;
      case OSEGW_EBAD_PARAMETER:
         return OSEGW_EBAD_PARAMETER_MSG;
      case OSEGW_ENO_BUFFER_END_MARK:
         return OSEGW_ENO_BUFFER_END_MARK_MSG;
      case OSEGW_ETOO_MANY_ATTACHED:
         return OSEGW_ETOO_MANY_ATTACHED_MSG;
      case OSEGW_EDETACHED_TWICE:
         return OSEGW_EDETACHED_TWICE_MSG;
      case OSEGW_EDETACH_AFTER_RECEIVE:
         return OSEGW_EDETACH_AFTER_RECEIVE_MSG;
      case OSEGW_EATTACHED_TO_CALLER:
         return OSEGW_EATTACHED_TO_CALLER_MSG;
      default:
         return OSEGW_EUNKNOWN_SPECIFIED_ECODE_MSG + errorCode;
      }
   }
}
