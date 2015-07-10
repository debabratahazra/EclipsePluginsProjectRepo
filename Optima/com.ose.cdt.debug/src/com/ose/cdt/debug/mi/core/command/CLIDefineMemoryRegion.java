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

package com.ose.cdt.debug.mi.core.command;

import org.eclipse.cdt.debug.mi.core.command.CLICommand;

/*
 * mem <loaddr> <hiaddr> [rw|ro|wo] [8|16|32|64] [cache|nocache]
 */
public class CLIDefineMemoryRegion extends CLICommand
{
   public CLIDefineMemoryRegion(String loaddr, String hiaddr)
   {
      super("mem " + loaddr + " " + hiaddr);
   }

   public CLIDefineMemoryRegion(String loaddr, String hiaddr, String attributes)
   {
      super("mem " + loaddr + " " + hiaddr + " " + attributes);
   }
}
