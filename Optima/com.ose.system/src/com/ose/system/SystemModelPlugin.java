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

package com.ose.system;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Plugin;
import org.eclipse.core.runtime.Status;
import org.osgi.framework.BundleContext;

/**
 * This is the OSE system model plugin class. It is only used internally for
 * logging and creating error status objects in the OSE system model.
 * <p>
 * This class is not intended to be used by clients to the OSE system model.
 */
public class SystemModelPlugin extends Plugin
{
   /** OSE system model plugin identifier. */
   public static final String PLUGIN_ID = "com.ose.system";

   /* OSE system model singleton plugin object. */
   private static SystemModelPlugin plugin;

   /**
    * Create the OSE system model singleton plugin object. This constructor is
    * automatically called by the Eclipse platform in the course of plugin
    * activation and should never be called explicitly.
    */
   public SystemModelPlugin()
   {
      super();
      plugin = this;
   }

   /**
    * Return the OSE system model singleton plugin object.
    *
    * @return the OSE system model singleton plugin object.
    */
   public static SystemModelPlugin getDefault()
   {
      return plugin;
   }

   /**
    * Return the plugin identifier for the OSE system model plugin.
    *
    * @return the plugin identifier for the OSE system model plugin.
    */
   public static String getUniqueIdentifier()
   {
      if (getDefault() == null)
      {
         // If the default instance is not yet initialized,
         // return a static identifier. This identifier must
         // match the plugin id defined in plugin.xml.
         return PLUGIN_ID;
      }
      else
      {
         return getDefault().getBundle().getSymbolicName();
      }
   }

   /**
    * Create an error status object using the specified exception.
    *
    * @param t  an exception.
    * @return an error status object using the specified exception.
    */
   public static IStatus createErrorStatus(Throwable t)
   {
      return new Status(IStatus.ERROR,
            getUniqueIdentifier(),
            IStatus.ERROR,
            (((t != null) && (t.getMessage() != null)) ? t.getMessage() : "Error"),
            t);
   }

   /**
    * Create an error status object using the specified message.
    *
    * @param message  a message.
    * @return an error status object using the specified message.
    */
   public static IStatus createErrorStatus(String message)
   {
      return new Status(IStatus.ERROR,
            getUniqueIdentifier(),
            IStatus.ERROR,
            message,
            null);
   }

   /**
    * Create an error status object using the specified message and exception.
    *
    * @param message  a message.
    * @param t        an exception.
    * @return an error status object using the specified message and exception.
    */
   public static IStatus createErrorStatus(String message, Throwable t)
   {
      return new Status(IStatus.ERROR,
            getUniqueIdentifier(),
            IStatus.ERROR,
            message,
            t);
   }

   /**
    * Create an error status object using the specified status code, message,
    * and exception.
    *
    * @param code     a status code.
    * @param message  a message.
    * @param t        an exception.
    * @return an error status object using the specified status code, message,
    * and exception.
    */
   public static IStatus createErrorStatus(int code, String message, Throwable t)
   {
      return new Status(IStatus.ERROR,
            getUniqueIdentifier(),
            code,
            message,
            t);
   }

   /**
    * Log the specified status object.
    *
    * @param status  a status object.
    */
   public static void log(IStatus status)
   {
      getDefault().getLog().log(status);
   }

   /**
    * Log the specified exception.
    *
    * @param t  an exception.
    */
   public static void log(Throwable t)
   {
      log(createErrorStatus(t));
   }

   /**
    * Log the specified message.
    *
    * @param message  a message.
    */
   public static void log(String message)
   {
      log(createErrorStatus(message));
   }

   /**
    * @see org.osgi.framework.BundleActivator#start(org.osgi.framework.BundleContext)
    */
   public void start(BundleContext context) throws Exception
   {
      super.start(context);
   }

   /**
    * @see org.osgi.framework.BundleActivator#stop(org.osgi.framework.BundleContext)
    */
   public void stop(BundleContext context) throws Exception
   {
      super.stop(context);
   }
}
