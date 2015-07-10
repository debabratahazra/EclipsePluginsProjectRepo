/* COPYRIGHT-ENEA-SRC-R1 *
 **************************************************************************
 * Copyright (C) 2006 by Enea Software AB.
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

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;

/**
 * This interface defines a PMD block formatter.
 */
public interface IBlockFormatter
{
   /**
    * Format the block, whose binary content is in the given input stream, on
    * the given output print writer.
    * <p>
    * It is the responsibility of the caller to close the input stream and
    * output print writer afterwards.
    *
    * @param in  the input stream containing the binary content of the block.
    * @param out  the output print writer where to write the formatted block.
    * @param address  if applicable, the base address of the block.
    * @param size  the size in bytes of the binary content of the block
    * contained in the input stream.
    * @param bigEndian  true if the binary content of the block contained in the
    * input stream is in big endian byte order, false if it is in little endian
    * byte order.
    * @throws IffException  if an IFF PMD exception occurred.
    * @throws IOException  if an I/O exception occurred.
    */
   public void format(InputStream in, PrintWriter out, long address, long size, boolean bigEndian)
      throws IOException;
}
