/* COPYRIGHT-ENEA-SRC-R2 *
 **************************************************************************
 * Copyright (C) 2008 by Enea Software AB.
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

package com.ose.system.ui;

import java.lang.reflect.Method;
import org.eclipse.ui.IActionFilter;
import com.ose.system.Block;
import com.ose.system.Heap;
import com.ose.system.Pool;
import com.ose.system.Process;
import com.ose.system.Segment;
import com.ose.system.SystemModelNode;
import com.ose.system.Target;

/**
 * This class is an action filter for advanced visibility and enablement
 * expressions for declaratively contributed actions on system model nodes.
 * <p>
 * This action filter makes all "public boolean isXxx()" methods on the different
 * types of SystemModelNodes available and all "public boolean hasXxxSupport()"
 * methods in the Target class available for SystemModelNodes which descends
 * from a Target node.
 */
class SystemModelNodeActionFilter implements IActionFilter
{
   public boolean testAttribute(Object obj, String name, String value)
   {
      if (obj instanceof SystemModelNode)
      {
         if (name.startsWith("is") && (name.length() > 2))
         {
            try
            {
               Class cls = obj.getClass();
               Method method = cls.getMethod(name, (Class[]) null);
               Boolean result = (Boolean) method.invoke(obj, (Object[]) null);
               return (result.booleanValue() == value.equalsIgnoreCase("true"));
            }
            catch (Exception e)
            {
               return false;
            }
         }
         else if (name.startsWith("has") && name.endsWith("Support") &&
                  (name.length() > 10))
         {
            Target target = getTarget(obj);
            if (target != null)
            {
               try
               {
                  Class cls = target.getClass();
                  Method method = cls.getDeclaredMethod(name, (Class[]) null);
                  Boolean result = (Boolean) method.invoke(target, (Object[]) null);
                  return (result.booleanValue() == value.equalsIgnoreCase("true"));
               }
               catch (Exception e)
               {
                  return false;
               }
            }
         }
      }
      return false;
   }

   private static Target getTarget(Object obj)
   {
      if (obj instanceof Target)
      {
         return (Target) obj;
      }
      else if (obj instanceof Segment)
      {
         return ((Segment) obj).getTarget();
      }
      else if (obj instanceof Pool)
      {
         return ((Pool) obj).getTarget();
      }
      else if (obj instanceof Heap)
      {
         return ((Heap) obj).getTarget();
      }
      else if (obj instanceof Block)
      {
         return ((Block) obj).getTarget();
      }
      else if (obj instanceof Process)
      {
         return ((Process) obj).getTarget();
      }
      else
      {
         return null;
      }
   }
}
