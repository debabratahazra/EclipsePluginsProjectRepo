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

import org.eclipse.cdt.debug.mi.core.command.MICommand;

/*
 * attach system <pid|pname>
 * attach segment <sid> <pid|pname>
 * attach block <bid|bname> <pid|pname>
 * attach process <pid|pname>
 */
public class MITargetAttach extends MICommand
{
   public MITargetAttach(String miVersion, String[] params)
   {
      super(miVersion, "attach", params);
   }
}
