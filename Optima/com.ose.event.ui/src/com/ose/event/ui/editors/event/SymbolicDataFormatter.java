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

package com.ose.event.ui.editors.event;

import java.io.PrintWriter;
import java.util.Iterator;
import com.ose.sigdb.Attribute;
import com.ose.sigdb.CompositeAttribute;

public class SymbolicDataFormatter
{
   private static final String INDENT = "    ";

   public void format(CompositeAttribute signal, PrintWriter out)
   {
      format(signal, out, 0);
   }

   private void format(CompositeAttribute signal, PrintWriter out, int depth)
   {
      if ((signal == null) || (out == null) || (depth < 0))
      {
         throw new IllegalArgumentException();
      }

      writeIndent(out, depth);
      out.println(signal.getName());

      writeIndent(out, depth);
      out.println("{");

      for (Iterator i = signal.getAttributes().iterator(); i.hasNext();)
      {
         Attribute attribute = (Attribute) i.next();
         if (attribute instanceof CompositeAttribute)
         {
            format((CompositeAttribute) attribute, out, depth + 1);
         }
         else
         {
            writeIndent(out, depth + 1);
            out.println(attribute.toString());
         }
      }

      writeIndent(out, depth);
      out.println("}");
   }

   private static void writeIndent(PrintWriter out, int depth)
   {
      for (int i = 0; i < depth; i++)
      {
         out.print(INDENT);
      }
   }
}
