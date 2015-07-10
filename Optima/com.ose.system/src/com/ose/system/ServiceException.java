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

package com.ose.system;

// FIXME: Should really be a checked exception instead.

/**
 * Thrown when a request to an OSE target service used by an operation in the
 * OSE system model failed in a controlled manner. The error reported by the
 * target service is described with an error status and an error message.
 */
public class ServiceException extends RuntimeException
{
   private static final long serialVersionUID = 1L;

   private final String service;
   private final int status;

   /**
    * Create a new service exception object.
    *
    * @param service  the reporting service.
    * @param status   the error status.
    * @param message  the error message.
    */
   ServiceException(String service, int status, String message)
   {
      super(message);
      this.service = service;
      this.status = status;
   }

   /**
    * Return the identity of the service reporting the error.
    *
    * @return the identity of the service reporting the error.
    */
   public String getService()
   {
      return service;
   }

   /**
    * Return the error status reported by the service.
    *
    * @return the error status reported by the service.
    */
   public int getStatus()
   {
      return status;
   }
}
