/* COPYRIGHT-ENEA-SRC-R1 *
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

package com.ose.pmd.ui.editor;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.Platform;
import com.ose.pmd.editor.IBlockFormatter;

public class BlockFormatterManager
{
   public static final String TYPE_MEMORY = "memory";

   public static final String TYPE_SIGNAL = "signal";

   private final List blockFormatterProxies;

   public BlockFormatterManager()
   {
      blockFormatterProxies = getBlockFormatterProxies();
   }

   private static List getBlockFormatterProxies()
   {
      List blockFormatterProxies;
      IExtensionRegistry extensionRegistry;
      IExtensionPoint extensionPoint;
      IExtension[] extensions;

      blockFormatterProxies = new ArrayList();
      extensionRegistry = Platform.getExtensionRegistry();
      extensionPoint = extensionRegistry.getExtensionPoint(
         "com.ose.pmd.ui", "blockFormatters");
      extensions = extensionPoint.getExtensions();

      for (int i = 0; i < extensions.length; i++)
      {
         IConfigurationElement[] ces = extensions[i].getConfigurationElements();
         for (int j = 0; j < ces.length; j++)
         {
            IConfigurationElement ce = ces[j];
            String type = ce.getAttribute("type");
            // The type and display name attributes are
            // optional for sake of backwards-compatibility.
            if (ce.getName().equals("blockFormatter") &&
                ((type == null) || type.equals(TYPE_MEMORY) || type.equals(TYPE_SIGNAL)) &&
                (ce.getAttribute("description") != null) &&
                (ce.getAttribute("class") != null))
            {
               blockFormatterProxies.add(new BlockFormatterProxy(ce));
            }
         }
      }

      return blockFormatterProxies;
   }

   public BlockFormatterProxy[] getBlockFormatterProxies(String type,
                                                         String description)
   {
      List foundBlockFormatterProxies = new ArrayList();

      for (Iterator i = blockFormatterProxies.iterator(); i.hasNext();)
      {
         BlockFormatterProxy blockFormatterProxy = (BlockFormatterProxy) i.next();
         if (blockFormatterProxy.getType().equals(type))
         {
            String blockFormatterProxyDescription =
               blockFormatterProxy.getDescription();
            if (blockFormatterProxyDescription.equals("") ||
                blockFormatterProxyDescription.equals(description))
            {
               foundBlockFormatterProxies.add(blockFormatterProxy);
            }
         }
      }

      return (BlockFormatterProxy[])
         foundBlockFormatterProxies.toArray(new BlockFormatterProxy[0]);
   }

   public static class BlockFormatterProxy
   {
      private final IConfigurationElement ce;
      private final String type;
      private final String description;
      private final String name;
      private final String className;
      private IBlockFormatter blockFormatter;

      BlockFormatterProxy(IConfigurationElement ce)
      {
         String optType;
         String optName;

         this.ce = ce;
         optType = ce.getAttribute("type");
         type = ((optType != null) ? optType : TYPE_MEMORY);
         description = ce.getAttribute("description");
         optName = ce.getAttribute("name");
         name = ((optName != null) ? optName : "");
         className = ce.getAttribute("class");
      }

      public String getType()
      {
         return type;
      }

      public String getDescription()
      {
         return description;
      }

      public String getName()
      {
         return name;
      }

      public String getClassName()
      {
         return className;
      }

      public IBlockFormatter getBlockFormatter() throws CoreException
      {
         if (blockFormatter == null)
         {
            blockFormatter =
               (IBlockFormatter) ce.createExecutableExtension("class");
         }
         return blockFormatter;
      }
   }
}
