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

package com.ose.sigdb.table;

import java.io.IOException;

/** This interface is used by SignalTable the it reads a signal table. */
public interface SignalTableReader
{

   /**
    * @return the name of the signal table that has been read by this reader.
    */
   public String getName();

   /**
    * @param suffix
    *           The name of the current suffix or null for the first suffix.
    * @return the next signal table suffix or null if no more signal tables.
    */
   public String getNextSuffix(String suffix);

   /**
    * Reads a signal table and adds signals and types to the signal table.
    * 
    * @param table
    *           The signal table to read signals into
    * @param suffix
    *           The suffix of the signal table to read or null for the first
    *           SignalTable.
    * 
    * @throws IOException
    * @return The current suffix or null if no suffixes exists.
    */
   public SignalDescriptionTable read(String suffix) throws IOException;
}
