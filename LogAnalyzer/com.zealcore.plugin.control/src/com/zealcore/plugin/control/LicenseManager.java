/* COPYRIGHT-ENEA-SRC-R2 *
 **************************************************************************
 * Copyright (C) 2010 by Enea Software AB.
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

package com.zealcore.plugin.control;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import flexlm.LM;
import flexlm.license;

public final class LicenseManager
{
   private static final LicenseManager LICENSE_MANAGER = new LicenseManager();

   private final Map pluginMap;

   private final Properties licenseProperties;
     
        
   private LicenseManager()
   {
      pluginMap = new HashMap();
      pluginMap.put("com.zealcore.se.core",
         new License(createKey(70, 79, 110, 111, 108, 88, 69, 113, 79, 51)));
      pluginMap.put("com.zealcore.sd.lttng.importer",
         new License(createKey(101, 52, 116, 98, 54, 99, 101, 113, 89, 69)));
      licenseProperties = new Properties();
   }

   /**
    * @return The singleton instance
    */
   public static LicenseManager getInstance()
   {
      return LICENSE_MANAGER;
   }

   public void setLicenseProperties(InputStream in) throws IOException
   {
      if (in == null)
      {
         throw new IllegalArgumentException();
      }

      licenseProperties.load(in);
   }

   /**
    * @param name The name of the plugin to register
    * @param version The version of the plugin's license
    * @param cookie The registration cookie
    * @return The response value
    * @throws LicenseException If the registration fails
    */
   public long registerPlugin(String name, String version, long cookie)
      throws LicenseException
   {
      License lic;

      if (name == null)
      {
         throw new IllegalArgumentException("Plugin name is null");
      }
      if (version == null)
      {
         throw new IllegalArgumentException("Plugin version is null");
      }

      lic = (License) pluginMap.get(name);
      if (lic == null)
      {
         throw new IllegalArgumentException(
               "Plugin '" + name + "' has no license");
      }

      if (!licenseProperties.getProperty(name, "").equals(lic.getKey()))
      {
         String var = null;

         try
         {
            var = System.getenv("LM_LICENSE_FILE");
         } catch (Throwable ignore) {}

         if (var == null)
         {
            var = System.getProperty("lm.license.file");
         }

         if ((var == null) || (var.trim().length() == 0))
         {
            throw new LicenseException(
                  "Could not find environment variable LM_LICENSE_FILE");
         }

         lic.checkout(name, version, var);
      }

      return cookie ^ (System.currentTimeMillis() / 10000);
   }

   /** @param name The name of the plugin to unregister */
   public void unregisterPlugin(String name)
   {
      License lic;

      if (name == null)
      {
         throw new IllegalArgumentException("Plugin name is null");
      }

      lic = (License) pluginMap.get(name);
      if (lic == null)
      {
         throw new IllegalArgumentException(
               "Plugin '" + name + "' has no license");
      }

      if (!licenseProperties.getProperty(name, "").equals(lic.getKey()))
      {
         lic.checkin();
      }
   }

   private static String createKey(int a, int b, int c, int d, int e,
                                   int f, int g, int h, int i, int j)
   {
      String key = null;

      try
      {
         byte[] bytes = {(byte) a, (byte) b, (byte) c, (byte) d, (byte) e,
                         (byte) f, (byte) g, (byte) h, (byte) i, (byte) j};
         key = new String(bytes, "ISO-8859-1");
      } catch (UnsupportedEncodingException ignore) {}

      return key;
   }

   private static class License
   {
      private final String key;
      private final license lic;
      private int licCount;

      License(String key)
      {
         this.key = key;
         lic = new license("osedaemon",
                           0xA65F10AB ^ 0x525C39AE,
                           0xD8C742F0 ^ 0x525C39AE,
                           0xC3223992, 0xD2F06F28,
                           0x46968E72, 0xF70AC4AA);
         licCount = 0;
      }

      public String getKey()
      {
         return key;
      }

      public synchronized void checkout(String name, String version, String var)
         throws LicenseException
      {
         if (licCount < 1)
         {
            int result = lic.checkout(LM.RESTRICTIVE, name, version, 1, var);
            if (result != 0)
            {
               throw new LicenseException(lic.get_errstring());
            }
         }
         licCount++;
      }

      public synchronized void checkin()
      {
         if (licCount > 0)
         {
            licCount--;
         }
         if (licCount == 0)
         {
            lic.checkin();
         }
      }
   }
 
}
