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

package com.ose.gateway.server;

/**
 * This interface contains various constants used in the OSE gateway protocol.
 * See the OSE header file ose_gwp.h for the actual definition of the OSE
 * gateway protocol.
 */
public interface GatewayProtocol
{
   /* Protocol version */
   public static final int PROTOCOL_VERSION = 100;

   /* Default server port */
   public static final int DEFAULT_SERVER_PORT = 21768;

   /* Default broadcast port */
   public static final int DEFAULT_BROADCAST_PORT = 21768;

   /* Payload types */
   public static final int INTERFACE_REQUEST   =  1;
   public static final int INTERFACE_REPLY     =  2;
   public static final int LOGIN_REQUEST       =  3;
   public static final int CHALLENGE_RESPONSE  =  4;
   public static final int CHALLENGE_REPLY     =  5;
   public static final int LOGIN_REPLY         =  6;
   public static final int CREATE_REQUEST      =  7;
   public static final int CREATE_REPLY        =  8;
   public static final int DESTROY_REQUEST     =  9;
   public static final int DESTROY_REPLY       = 10;
   public static final int SEND_REQUEST        = 11;
   public static final int SEND_REPLY          = 12;
   public static final int RECEIVE_REQUEST     = 13;
   public static final int RECEIVE_REPLY       = 14;
   public static final int HUNT_REQUEST        = 15;
   public static final int HUNT_REPLY          = 16;
   public static final int ATTACH_REQUEST      = 17;
   public static final int ATTACH_REPLY        = 18;
   public static final int DETACH_REQUEST      = 19;
   public static final int DETACH_REPLY        = 20;
   public static final int NAME_REQUEST        = 21;
   public static final int NAME_REPLY          = 22;

   /* Authentication schemes */
   public static final int AUTH_NO_PASSWORD = 0;
   public static final int AUTH_PLAIN_TEXT  = 1;

   /* Server interface flags */
   public static final int LITTLE_ENDIAN = 0x00000001;
   public static final int USE_AUTH      = 0x00000002;

   /* Error codes */
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
}
