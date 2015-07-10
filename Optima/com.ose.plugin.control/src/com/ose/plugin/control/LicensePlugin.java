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

package com.ose.plugin.control;

import java.io.InputStream;
import java.net.URL;
import org.eclipse.core.runtime.Plugin;
import org.osgi.framework.BundleContext;

public class LicensePlugin extends Plugin
{
   public void start(BundleContext context) throws Exception
   {
      URL url;

      super.start(context);
      url = getBundle().getEntry("/license.properties");
      if (url != null)
      {
         InputStream in = url.openStream();
         try
         {
            LicenseManager.getInstance().setLicenseProperties(in);
         }
         finally
         {
            in.close();
         }
      }
   }

   public void stop(BundleContext context) throws Exception
   {
      super.stop(context);
   }
}
