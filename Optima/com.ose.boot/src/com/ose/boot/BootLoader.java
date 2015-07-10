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

package com.ose.boot;

import java.io.IOException;

public interface BootLoader
{
   /**
    * Reset the target.
    *
    * @exception TimeoutException        If the operation timed out.
    * @exception InterruptedIOException  If the operation was interrupted by
    *                                    another thread.
    * @exception IOException             If some other I/O error occurs.
    */
   public void reset() throws IOException;

   /**
    * Boot the target with the specified file located on the specified file
    * server. The type of file server is dependent on the particular boot
    * loader, e.g. one boot loader may use a HTTP server while another may
    * use a TFTP server.
    *
    * @param host  The address of the host where the file server resides.
    * @param port  The port number of the file server.
    * @param file  The path to the file on the file server in a format specified
    *              by the file server.
    * @exception TimeoutException        If the operation timed out.
    * @exception InterruptedIOException  If the operation was interrupted by
    *                                    another thread.
    * @exception IOException             If some other I/O error occurs.
    */
   public void boot(String host, int port, String file) throws IOException;

   /**
    * Get the ramlog contents from the target.
    *
    * @param all  True if the whole ramlog should be retrieved, false if only
    *             the previous session should be retrieved.
    * @return  The ramlog contents or null if not supported.
    * @exception TimeoutException        If the operation timed out.
    * @exception InterruptedIOException  If the operation was interrupted by
    *                                    another thread.
    * @exception IOException             If some other I/O error occurs.
    */
   public String getRamlog(boolean all) throws IOException;
}
