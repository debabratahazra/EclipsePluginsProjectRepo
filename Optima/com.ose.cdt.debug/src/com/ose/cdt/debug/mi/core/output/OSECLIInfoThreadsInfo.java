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

package com.ose.cdt.debug.mi.core.output;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.eclipse.cdt.debug.mi.core.output.CLIInfoThreadsInfo;
import org.eclipse.cdt.debug.mi.core.output.MIConsoleStreamOutput;
import org.eclipse.cdt.debug.mi.core.output.MIOOBRecord;
import org.eclipse.cdt.debug.mi.core.output.MIOutput;
import org.eclipse.cdt.debug.mi.core.output.MIStreamRecord;

public class OSECLIInfoThreadsInfo extends CLIInfoThreadsInfo
{
   protected String[] threadNames;

   public OSECLIInfoThreadsInfo(MIOutput out)
   {
      super(out);
   }

   public String[] getThreadNames()
   {
      return threadNames;
   }

   protected void parse()
   {
      List list = new ArrayList();
      boolean noNames = false;

      if (isDone())
      {
         MIOOBRecord[] oobs = getMIOutput().getMIOOBRecords();

         for (int i = 0; i < oobs.length; i++)
         {
            if (oobs[i] instanceof MIConsoleStreamOutput)
            {
               MIStreamRecord cons = (MIStreamRecord) oobs[i];
               parseThreadInfo(cons.getString().trim(), list);
            }
         }
      }

      Collections.sort(list);
      threadIds = new int[list.size()];
      threadNames = new String[list.size()];
      for (int i = 0; i < list.size(); i++)
      {
         ThreadInfo info = (ThreadInfo) list.get(i);
         threadIds[i] = info.id;
         threadNames[i] = info.name;
         if (info.name == null)
         {
            noNames = true;
         }
      }

      if (noNames)
      {
         threadNames = null;
      }
   }

   protected void parseThreadInfo(String str, List list)
   {
      if (str.length() > 0)
      {
         boolean isCurrentThread = false;

         // Discover the current thread
         if (str.charAt(0) == '*')
         {
            isCurrentThread = true;
            str = str.substring(1).trim();
         }

         // Fetch the thread info
         if ((str.length() > 0) && Character.isDigit(str.charAt(0)))
         {
            int id = -1;
            String name = null;

            // Fetch the GDB thread id
            int i = 1;
            while ((i < str.length()) && Character.isDigit(str.charAt(i)))
            {
               i++;
            }

            try
            {
               id = Integer.parseInt(str.substring(0, i));
               if (isCurrentThread)
               {
                  currentThreadId = id;
               }
            }
            catch (NumberFormatException e) {}

            // Fetch the OSE process id and name
            int start = str.indexOf("(OSE procname=");
            int middle = str.indexOf(" pid=", start);
            int end = str.indexOf(")", middle);

            if ((start != -1) && (middle != -1) && (end != -1))
            {
               name = str.substring(middle + 5, end) + " " +
                      str.substring(start + 14, middle);
            }

            // Add the thread info if valid
            if (id != -1)
            {
               list.add(new ThreadInfo(id, name));
            }
         }
      }
   }

   static class ThreadInfo implements Comparable
   {
      final int id;
      final String name;

      public ThreadInfo(int id, String name)
      {
         this.id = id;
         this.name = name;
      }

      public int compareTo(Object o)
      {
         ThreadInfo info = (ThreadInfo) o;
         return ((id < info.id) ? -1 : ((id == info.id) ? 0 : 1));
      }
   }
}
