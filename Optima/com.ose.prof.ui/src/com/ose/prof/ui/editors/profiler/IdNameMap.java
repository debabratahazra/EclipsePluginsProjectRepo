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

package com.ose.prof.ui.editors.profiler;

import java.util.Map;

class IdNameMap
{
   private final Map idToNameMap;

   IdNameMap(Map idToNameMap)
   {
      this.idToNameMap = idToNameMap;
   }

   public boolean isEmpty()
   {
      return (idToNameMap == null);
   }

   public String getName(int id)
   {
      if ((idToNameMap != null) && !idToNameMap.isEmpty())
      {
         String name = (String) idToNameMap.get(new Integer(id));
         if (name != null)
         {
            return name + " (" + toHexString(id) + ")";
         }
         else
         {
            return toHexString(id);
         }
      }
      else
      {
         return toHexString(id);
      }
   }

   private static String toHexString(int i)
   {
      return "0x" + Integer.toHexString(i).toUpperCase();
   }
}
