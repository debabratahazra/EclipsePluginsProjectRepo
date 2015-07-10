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

package com.ose.cdt.launch.internal;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Properties;
import java.util.TooManyListenersException;
import com.ose.boot.BootLoader;
import com.ose.boot.ConsoleConnection;
import com.ose.boot.PoloBootLoader;
import com.ose.boot.TimeoutException;
import com.ose.cdt.launch.internal.ui.LaunchUIPlugin;
import com.ose.httpd.Httpd;
import com.ose.httpd.LocalInetAddress;
import com.ose.httpd.ProgressEvent;
import com.ose.httpd.ProgressListener;
import com.ose.launch.IOSELaunchConfigurationConstants;
import com.ose.system.Gate;
import com.ose.system.SystemModel;
import com.ose.system.Target;
import org.eclipse.cdt.core.IBinaryParser.IBinaryObject;
import org.eclipse.cdt.core.model.ICProject;
import org.eclipse.cdt.debug.core.CDIDebugModel;
import org.eclipse.cdt.debug.core.ICDIDebugger2;
import org.eclipse.cdt.debug.core.ICDTLaunchConfigurationConstants;
import org.eclipse.cdt.debug.core.ICDebugConfiguration;
import org.eclipse.cdt.debug.core.cdi.CDIException;
import org.eclipse.cdt.debug.core.cdi.ICDISession;
import org.eclipse.cdt.debug.core.cdi.model.ICDIRuntimeOptions;
import org.eclipse.cdt.debug.core.cdi.model.ICDITarget;
import org.eclipse.cdt.launch.AbstractCLaunchDelegate;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.MultiStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.SubProgressMonitor;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.ILaunch;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchManager;
import org.eclipse.debug.core.model.IProcess;

public class CoreModuleLaunchConfigurationDelegate extends AbstractCLaunchDelegate
{
   private static final String TASK_NAME = "Launching OSE Core Module";

   private static final int TASK_MAX = 100;

   public void launch(ILaunchConfiguration config,
                      String mode,
                      ILaunch launch,
                      IProgressMonitor monitor)
      throws CoreException
   {
      if (monitor == null)
      {
         monitor = new NullProgressMonitor();
      }

      if (mode.equals(ILaunchManager.RUN_MODE))
      {
         monitor.beginTask(TASK_NAME, TASK_MAX);
         load(config, monitor);
         updateSystemModel(config, monitor);
      }
      else // ILaunchManager.DEBUG_MODE
      {
         boolean download = config.getAttribute(
            IOSELaunchConfigurationConstants.ATTR_BOOT_DOWNLOAD, false);

         if (download)
         {
            monitor.beginTask(TASK_NAME, TASK_MAX + 10);
            load(config, monitor);
            updateSystemModel(config, monitor);
            debug(config, launch, monitor);
            monitor.worked(10);
         }
         else
         {
            monitor.beginTask(TASK_NAME, IProgressMonitor.UNKNOWN);
            debug(config, launch, monitor);
         }
      }

      monitor.done();
   }

   protected void abort(String message, Throwable exception, int code)
      throws CoreException
   {
      IStatus status = new Status(IStatus.ERROR,
                                  getPluginID(),
                                  code,
                                  message,
                                  exception);
      throw new CoreException(status);
   }

   protected void abort(String message,
                        String extraMessage,
                        Throwable exception,
                        int code)
      throws CoreException
   {
      MultiStatus status =
         new MultiStatus(getPluginID(), IStatus.ERROR, message, null);
      status.add(new Status(IStatus.ERROR,
                            getPluginID(),
                            code,
                            extraMessage,
                            exception));
      throw new CoreException(status);
   }

   protected String getPluginID()
   {
      return LaunchUIPlugin.getUniqueIdentifier();
   }

   private void load(ILaunchConfiguration config, IProgressMonitor monitor)
      throws CoreException
   {
      String boot;
      Httpd httpd = null;
      ConsoleConnection console = null;

      // FIXME: Remove this when support for loading core modules is revived
      if (true)
      {
         throw new UnsupportedOperationException(
            "Loading core modules is no longer supported");
      }

      if (monitor.isCanceled())
      {
         throw new OperationCanceledException();
      }

      boot = config.getAttribute(
         IOSELaunchConfigurationConstants.ATTR_BOOT_LOADER, "");
      if (!boot.equals(IOSELaunchConfigurationConstants.VALUE_BOOT_POLO))
      {
         return;
      }

      try
      {
         File exeFile;
         String exeFileName;
         LoadProgressHandler progress;
         String prompt;
         InputStream in;
         OutputStream out;
         BootLoader bootLoader;
         String localAddress = null;

         // Get core module file
         exeFile = verifyProgramPath(config).toFile();
         exeFileName = exeFile.getName();

         // Start HTTP server
         httpd = new Httpd(exeFile.getParentFile(), 0);
         progress = new LoadProgressHandler(monitor, exeFileName);
         try
         {
            httpd.addProgressListener(progress);
         } catch (TooManyListenersException e) {}
         httpd.start();

         // FIXME: Obtain input/output streams for the target console connection
         in = null;
         out = null;

         // Check for cancellation
         if (monitor.isCanceled())
         {
            throw new OperationCanceledException();
         }

         // Open console connection to target
         console = new ConsoleConnection(in, out);
         prompt = config.getAttribute(
            IOSELaunchConfigurationConstants.ATTR_BOOT_PROMPT, "polo>");
         console.setPrompt(prompt);
         bootLoader = new PoloBootLoader(console);

         // Reset target
         bootLoader.reset();

         // Check for cancellation
         if (monitor.isCanceled())
         {
            throw new OperationCanceledException();
         }

         // Boot core module file
         {
            InetAddress remoteInetAddress = null;
            InetAddress localInetAddress = null;

            try
            {
               String remoteAddress = config.getAttribute(
                  IOSELaunchConfigurationConstants.ATTR_GATE_ADDRESS, (String) null);
               if (remoteAddress != null)
               {
                  remoteInetAddress = InetAddress.getByName(remoteAddress);
               }
            }
            catch (UnknownHostException ignore) {}

            try
            {
               localInetAddress = LocalInetAddress.getLocalHost(remoteInetAddress);
            }
            catch (UnknownHostException e)
            {
               abort("Failed Loading Core Module",
                     "Unknown local host IP address",
                     e, ICDTLaunchConfigurationConstants.ERR_INTERNAL_ERROR);
            }
            if (localInetAddress.isLoopbackAddress())
            {
               abort("Failed Loading Core Module",
                     "Unknown local host IP address, only loopback address found",
                     null, ICDTLaunchConfigurationConstants.ERR_INTERNAL_ERROR);
            }
            localAddress = LocalInetAddress.getHostAddress(localInetAddress);
         }
         bootLoader.boot(localAddress, httpd.getPort(), exeFileName);

         // Remove load progress listener
         httpd.removeProgressListener(progress);

         // Make sure load progress listener is completed
         progress.finish();
      }
      catch (TimeoutException e)
      {
         abort("Failed Loading Core Module",
               "Boot operation timed out",
               e, ICDTLaunchConfigurationConstants.ERR_INTERNAL_ERROR);
      }
      catch (IOException e)
      {
         LaunchUIPlugin.log(e);
         abort("Failed Loading Core Module",
               "I/O error",
               e, ICDTLaunchConfigurationConstants.ERR_INTERNAL_ERROR);
      }
      finally
      {
         // Close console connection to target
         if (console != null)
         {
            console.close();
         }

         // Stop HTTP server
         if (httpd != null)
         {
            httpd.shutdown();
         }
      }
   }

   private void updateSystemModel(ILaunchConfiguration config,
                                  IProgressMonitor monitor)
      throws CoreException
   {
      String address;
      int port;
      InetAddress inetAddress = null;
      String huntPath;
      Gate gate;

      if (monitor.isCanceled())
      {
         throw new OperationCanceledException();
      }

      // Get gate address and port
      address = config.getAttribute(
         IOSELaunchConfigurationConstants.ATTR_GATE_ADDRESS, "");
      port = config.getAttribute(
         IOSELaunchConfigurationConstants.ATTR_GATE_PORT,
         IOSELaunchConfigurationConstants.DEFAULT_VALUE_GATE_PORT);
      try
      {
         inetAddress = InetAddress.getByName(address);
      }
      catch (UnknownHostException e)
      {
         abort("Failed Launching Core Module",
               "Unknown target IP address",
               e, ICDTLaunchConfigurationConstants.ERR_INTERNAL_ERROR);
      }

      // Get target hunt path
      huntPath = config.getAttribute(
         IOSELaunchConfigurationConstants.ATTR_TARGET_HUNT_PATH, "");

      // Reconnect the target if it is in the system model and is connected
      gate = SystemModel.getInstance().getGate(inetAddress, port);
      if ((gate != null) && gate.isConnected())
      {
         Target target = gate.getTarget(huntPath);
         if ((target != null) && target.isConnected())
         {
            target.disconnect();
            try
            {
               target.connect(monitor);
            }
            catch (IOException e)
            {
               abort("Failed Launching Core Module",
                     e, ICDTLaunchConfigurationConstants.ERR_INTERNAL_ERROR);
            }
         }
      }
   }

   private void debug(ILaunchConfiguration config,
                      ILaunch launch,
                      IProgressMonitor monitor)
      throws CoreException
   {
      ICDISession debugSession = null;

      if (monitor.isCanceled())
      {
         throw new OperationCanceledException();
      }

      setDefaultSourceLocator(launch, config);

      try
      {
         String debugMode = config.getAttribute(
            ICDTLaunchConfigurationConstants.ATTR_DEBUGGER_START_MODE,
            ICDTLaunchConfigurationConstants.DEBUGGER_MODE_RUN);

         if (debugMode.equals(ICDTLaunchConfigurationConstants.DEBUGGER_MODE_RUN))
         {
            ICProject project;
            IPath exePath;
            IBinaryObject exeFile;
            String[] arguments;
            Properties environment;
            File wd;
            ICDebugConfiguration debugConfig;
            Object debugger;
            ICDITarget[] dtargets;

            project = verifyCProject(config);
            exePath = verifyProgramPath(config);
            exeFile = verifyBinary(project, exePath);
            arguments = getProgramArgumentsArray(config);
            environment = getEnvironmentAsProperty(config);
            wd = getWorkingDirectory(config);

            debugConfig = getDebugConfig(config);
            debugger = debugConfig.createDebugger();
            if (debugger instanceof ICDIDebugger2)
            {
               debugSession = ((ICDIDebugger2) debugger).createSession(
                  launch, exePath.toFile(), new SubProgressMonitor(monitor, 0));
            }
            else
            {
               abort("Unsupported Debugger Type",
                     "The selected debugger type is not supported",
                     null, ICDTLaunchConfigurationConstants.ERR_INTERNAL_ERROR);
            }

            dtargets = debugSession.getTargets();
            for (int i = 0; i < dtargets.length; i++)
            {
               try
               {
                  ICDIRuntimeOptions options = dtargets[i].getRuntimeOptions();
                  options.setArguments(arguments);
                  options.setEnvironment(environment);
                  if (wd != null)
                  {
                     options.setWorkingDirectory(wd.getAbsolutePath());
                  }
               }
               catch (CDIException e)
               {
                  abort("Failed to set program arguments, environment or working directory.",
                        e, ICDTLaunchConfigurationConstants.ERR_INTERNAL_ERROR);
               }

               Process process = dtargets[i].getProcess();
               IProcess debuggeeProcess = null;
               if (process != null)
               {
                  debuggeeProcess = DebugPlugin.newProcess(
                     launch, process, renderProcessLabel(exePath.toOSString()));
               }

               CDIDebugModel.newDebugTarget(
                  launch,
                  project.getProject(),
                  dtargets[i],
                  renderTargetLabel(debugConfig),
                  debuggeeProcess,
                  exeFile,
                  true,
                  false,
                  null,
                  false);
            }
         }
      }
      catch (CoreException e)
      {
         if (debugSession != null)
         {
            try
            {
               debugSession.terminate();
            } catch (CDIException ce) {}
         }
         throw e;
      }
   }

   static class LoadProgressHandler implements ProgressListener
   {
      IProgressMonitor monitor;
      String file;
      int progress = 0;

      public LoadProgressHandler(IProgressMonitor monitor, String file)
      {
         this.monitor = monitor;
         this.file = file;
      }

      public void progressChanged(ProgressEvent e)
      {
         if (e.getFile().endsWith(file))
         {
            int work = e.getProgress() - progress;
            progress += work;
            monitor.worked(work);
            if (monitor.isCanceled())
            {
               monitor.subTask(TASK_NAME + ": Loading will be aborted when safe");
            }
         }
      }

      public void finish()
      {
         if (progress < TASK_MAX)
         {
            monitor.worked(TASK_MAX - progress);
         }
      }
   }
}
