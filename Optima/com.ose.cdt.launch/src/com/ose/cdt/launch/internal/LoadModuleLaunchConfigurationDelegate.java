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
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Random;
import java.util.TooManyListenersException;
import com.ose.cdt.debug.mi.core.GDBDebugger;
import com.ose.cdt.debug.mi.core.IOSEMILaunchConfigurationConstants;
import com.ose.cdt.launch.internal.ui.LaunchUIPlugin;
import com.ose.httpd.Httpd;
import com.ose.httpd.LocalInetAddress;
import com.ose.httpd.ProgressEvent;
import com.ose.httpd.ProgressListener;
import com.ose.launch.IOSELaunchConfigurationConstants;
import com.ose.system.BlockInfo;
import com.ose.system.Gate;
import com.ose.system.Parameter;
import com.ose.system.ProgramInfo;
import com.ose.system.ServiceException;
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
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.debug.core.ILaunchManager;
import org.eclipse.debug.core.model.IProcess;

public class LoadModuleLaunchConfigurationDelegate extends AbstractCLaunchDelegate
{
   private static final String LAUNCH_TASK_NAME = "Launching OSE Load Module";

   private static final int LAUNCH_TASK_MAX = 100;

   private static final String TERMINATE_TASK_NAME = "Terminating OSE Load Module";

   private static final Random RANDOM = new Random();

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
         monitor.beginTask(LAUNCH_TASK_NAME, LAUNCH_TASK_MAX);
         load(config, mode, monitor);
         createProgramProcess(config, launch);
      }
      else // ILaunchManager.DEBUG_MODE
      {
         boolean download = config.getAttribute(
            IOSELaunchConfigurationConstants.ATTR_LM_DOWNLOAD, false);

         if (download)
         {
            monitor.beginTask(LAUNCH_TASK_NAME, LAUNCH_TASK_MAX + 10);
            load(config, mode, monitor);
            debug(config, launch, monitor);
            monitor.worked(10);
         }
         else
         {
            monitor.beginTask(LAUNCH_TASK_NAME, IProgressMonitor.UNKNOWN);
            debug(config, launch, monitor);
         }
      }

      monitor.done();
   }

   public void terminate(ILaunchConfiguration config,
                         IProgressMonitor monitor,
                         boolean uninstall)
      throws CoreException
   {
      Target target = null;
      boolean removeTarget = false;
      boolean disconnectTarget = false;

      if (monitor == null)
      {
         monitor = new NullProgressMonitor();
      }

      monitor.beginTask(TERMINATE_TASK_NAME, IProgressMonitor.UNKNOWN);

      try
      {
         String address;
         int port;
         InetAddress inetAddress = null;
         String huntPath;
         Gate gate;
         ProgramInfo program;

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
            abort("Failed Terminating Load Module",
                  "Unknown target IP address",
                  e, ICDTLaunchConfigurationConstants.ERR_INTERNAL_ERROR);
         }

         // Get target hunt path
         huntPath = config.getAttribute(
               IOSELaunchConfigurationConstants.ATTR_TARGET_HUNT_PATH, "");

         // Get requested gate
         gate = SystemModel.getInstance().getGate(inetAddress, port);
         if (gate == null)
         {
            gate = SystemModel.getInstance().addGate(inetAddress, port);
            if (gate == null)
            {
               abort("Failed Terminating Load Module",
                     "Gate not found",
                     null, ICDTLaunchConfigurationConstants.ERR_INTERNAL_ERROR);
            }
            removeTarget = true;
         }
         if (!gate.isConnected())
         {
            gate.connect(monitor);
         }

         // Check for cancellation
         if (monitor.isCanceled())
         {
            throw new OperationCanceledException();
         }

         // Connect to requested target
         target = gate.getTarget(huntPath);
         if (target == null)
         {
            target = gate.addTarget(huntPath);
            if (target == null)
            {
               abort("Failed Terminating Load Module",
                     "Target not found",
                     null, ICDTLaunchConfigurationConstants.ERR_INTERNAL_ERROR);
            }
            removeTarget = true;
         }
         if (!target.isConnected())
         {
            target.connect(monitor);
            disconnectTarget = true;
         }

         // Check for cancellation
         if (monitor.isCanceled())
         {
            throw new OperationCanceledException();
         }

         // Obtain the program to be killed.
         program = GDBDebugger.getProgram(monitor, config);
         if (program == null)
         {
            abort("Failed Terminating Load Module",
                  "Program not found",
                  null, ICDTLaunchConfigurationConstants.ERR_INTERNAL_ERROR);
         }

         // Check for cancellation
         if (monitor.isCanceled())
         {
            throw new OperationCanceledException();
         }

         // Kill program
         target.killProgram(monitor, program.getId());

         // Check for cancellation
         if (monitor.isCanceled())
         {
            throw new OperationCanceledException();
         }

         // Uninstall load module
         if (uninstall)
         {
            target.uninstallLoadModule(monitor, program.getInstallHandle());
         }

         monitor.done();
      }
      catch (UnsupportedOperationException e)
      {
         abort("Failed Terminating Load Module",
               e, ICDTLaunchConfigurationConstants.ERR_INTERNAL_ERROR);
      }
      catch (ServiceException e)
      {
         abort("Failed Terminating Load Module", e, e.getStatus());
      }
      catch (IOException e)
      {
         abort("Failed Terminating Load Module",
               e, ICDTLaunchConfigurationConstants.ERR_INTERNAL_ERROR);
      }
      finally
      {
         // Disconnect from target
         if (target != null)
         {
            if (removeTarget)
            {
               target.disconnect();
               target.clean();
            }
            else if (disconnectTarget)
            {
               target.disconnect();
            }
         }
      }
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

   private void load(ILaunchConfiguration config,
                     String mode,
                     IProgressMonitor monitor)
      throws CoreException
   {
      Httpd httpd = null;
      Target target = null;
      boolean removeTarget = false;
      boolean disconnectTarget = false;
      String fileName = "";

      if (monitor.isCanceled())
      {
         throw new OperationCanceledException();
      }

      try
      {
         File exeFile;
         String exeFileName;
         int httpPort;
         LoadProgressHandler progress;
         String address;
         int port;
         InetAddress inetAddress = null;
         String huntPath;
         Gate gate;
         String httpVmHuntPath;
         String localAddress = null;
         String installHandle;
         boolean absolute;
         Map params;
         Map env;
         String args;
         boolean stopAtMain;
         int executionUnit;
         int numExecutionUnits;
         int progid;

         // Get load module file
         exeFileName = config.getAttribute(
            IOSELaunchConfigurationConstants.ATTR_LM_FILE, "");
         if (exeFileName.trim().length() > 0)
         {
            exeFile = new File(exeFileName.trim());
            if (!exeFile.isFile())
            {
               abort("Load module file does not exist",
                     new FileNotFoundException(exeFile.getAbsolutePath() + " not found"),
                     ICDTLaunchConfigurationConstants.ERR_PROGRAM_NOT_EXIST);
            }
         }
         else
         {
            exeFile = verifyProgramPath(config).toFile();
         }
         exeFileName = exeFile.getName();

         if (Boolean.getBoolean("com.ose.httpd.port.gatewayBased"))
         {
            // Special fix for Ericsson Research ERA, see TOOLSCR-163.
            httpPort = config.getAttribute(
               IOSELaunchConfigurationConstants.ATTR_GATE_PORT,
               IOSELaunchConfigurationConstants.DEFAULT_VALUE_GATE_PORT) + 1;
         }
         else
         {
            httpPort = config.getAttribute(
               IOSELaunchConfigurationConstants.ATTR_HTTP_SERVER_PORT, 0);
         }

         // Start HTTP server
         httpd = new Httpd(exeFile.getParentFile(), httpPort);
         progress = new LoadProgressHandler(monitor, exeFileName);
         try
         {
            httpd.addProgressListener(progress);
         } catch (TooManyListenersException ignore) {}
         httpd.start();

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
            abort("Failed loading load module",
                  "Unknown target IP address",
                  e, ICDTLaunchConfigurationConstants.ERR_INTERNAL_ERROR);
         }

         // Get target hunt path
         huntPath = config.getAttribute(
               IOSELaunchConfigurationConstants.ATTR_TARGET_HUNT_PATH, "");

         // Get requested gate
         gate = SystemModel.getInstance().getGate(inetAddress, port);
         if (gate == null)
         {
            gate = SystemModel.getInstance().addGate(inetAddress, port);
            if (gate == null)
            {
               abort("Failed loading load module",
                     "Gate not found",
                     null, ICDTLaunchConfigurationConstants.ERR_INTERNAL_ERROR);
            }
            removeTarget = true;
         }
         if (!gate.isConnected())
         {
            gate.connect(monitor);
         }

         // Check for cancellation
         if (monitor.isCanceled())
         {
            throw new OperationCanceledException();
         }

         // Connect to requested target
         target = gate.getTarget(huntPath);
         if (target == null)
         {
            target = gate.addTarget(huntPath);
            if (target == null)
            {
               abort("Failed loading load module",
                     "Target not found",
                     null, ICDTLaunchConfigurationConstants.ERR_INTERNAL_ERROR);
            }
            removeTarget = true;
         }
         if (!target.isConnected())
         {
            target.connect(monitor);
            disconnectTarget = true;
         }

         // Check for cancellation
         if (monitor.isCanceled())
         {
            throw new OperationCanceledException();
         }

         // Create load module file path and install handle
         httpVmHuntPath = config.getAttribute(
            IOSELaunchConfigurationConstants.ATTR_HTTP_VM_HUNT_PATH, "");
         try
         {
            InetAddress localInetAddress =
               LocalInetAddress.getLocalHost(gate.getAddress());
            if (localInetAddress.isLoopbackAddress())
            {
               LaunchUIPlugin.log(new Status(
                     IStatus.WARNING,
                     getPluginID(),
                     IStatus.OK,
                     "Using local host's loopback address when loading load module "
                        + exeFileName,
                     null));
            }
            localAddress = LocalInetAddress.getHostAddress(localInetAddress);
         }
         catch (UnknownHostException e)
         {
            abort("Failed loading load module",
                  "Unknown local host IP address",
                  e, ICDTLaunchConfigurationConstants.ERR_INTERNAL_ERROR);
         }
         fileName = httpVmHuntPath + "/http/" + localAddress + ":" +
            httpd.getPort() + "/" + exeFileName;
         installHandle = exeFileName + "-" + RANDOM.nextInt(Integer.MAX_VALUE);
         absolute = config.getAttribute(
            IOSELaunchConfigurationConstants.ATTR_LM_ABSOLUTE, false);

         // Install load module
         target.installLoadModule(monitor,
                                  fileName,
                                  installHandle,
                                  false,
                                  absolute,
                                  null);

         // Check for cancellation
         if (monitor.isCanceled())
         {
            // Uninstall load module and return
            target.uninstallLoadModule(monitor, installHandle);
            throw new OperationCanceledException();
         }

         // Add any load module parameters
         params = new HashMap();
         env = config.getAttribute(
            ILaunchManager.ATTR_ENVIRONMENT_VARIABLES, (Map) null);
         args = getProgramArguments(config);
         stopAtMain = config.getAttribute(
            ICDTLaunchConfigurationConstants.ATTR_DEBUGGER_STOP_AT_MAIN, false);
         executionUnit = config.getAttribute(
            IOSELaunchConfigurationConstants.ATTR_LM_CPU_ID, 0);
         numExecutionUnits = target.getNumExecutionUnits() & 0xFFFF;
         if (env != null)
         {
            params.putAll(env);
         }
         if ((args != null) && (args.trim().length() > 0))
         {
            params.put(Parameter.ARGV, exeFileName + " " + args.trim());
         }
         if (stopAtMain && mode.equals(ILaunchManager.DEBUG_MODE))
         {
            params.put(Parameter.OSE_LM_INTERCEPT, "YES");
         }
         if ((executionUnit < 0) ||
             ((numExecutionUnits > 0) && (executionUnit >= numExecutionUnits)))
         {
            abort("Failed loading load module",
                  "Invalid execution unit",
                  null, ICDTLaunchConfigurationConstants.ERR_INTERNAL_ERROR);
         }
         if (executionUnit > 0)
         {
            params.put(Parameter.OSE_LM_CPU_ID, Integer.toString(executionUnit));
         }

         // Create and start program
         progid = target.createProgram(monitor, installHandle, params);

         // Get program info and store segment id, main block id,
         // and main process id in the launch configuration.
         {
            ProgramInfo programInfo = null;
            BlockInfo[] blocks;
            ILaunchConfigurationWorkingCopy configCopy;

            try
            {
               programInfo = target.getProgramInfo(monitor, progid);
            }
            catch (ServiceException e)
            {
               abort("Program died immediately", e, e.getStatus());
            }

            blocks = target.getFilteredBlocks(monitor,
                                              Target.SCOPE_ID,
                                              programInfo.getMainBid(),
                                              null);
            if (blocks.length != 1)
            {
               abort("Program died immediately", null,
                  ICDTLaunchConfigurationConstants.ERR_INTERNAL_ERROR);
            }

            configCopy = config.getWorkingCopy();
            configCopy.setAttribute(
               IOSEMILaunchConfigurationConstants.ATTR_SEGMENT_ID,
               "0x" + Integer.toHexString(blocks[0].getSid()).toUpperCase());
            configCopy.setAttribute(
               IOSEMILaunchConfigurationConstants.ATTR_BLOCK_ID,
               "0x" + Integer.toHexString(programInfo.getMainBid()).toUpperCase());
            configCopy.setAttribute(
               IOSEMILaunchConfigurationConstants.ATTR_PROCESS_ID,
               "0x" + Integer.toHexString(programInfo.getMainPid()).toUpperCase());
            configCopy.doSave();
         }

         // Remove load progress listener
         httpd.removeProgressListener(progress);

         // Make sure load progress listener is completed
         progress.finish();
      }
      catch (UnsupportedOperationException e)
      {
         abort("Failed loading load module " + fileName,
               e, ICDTLaunchConfigurationConstants.ERR_INTERNAL_ERROR);
      }
      catch (ServiceException e)
      {
         abort("Failed loading load module " + fileName, e, e.getStatus());
      }
      catch (IOException e)
      {
         abort("Failed loading load module " + fileName,
               e, ICDTLaunchConfigurationConstants.ERR_INTERNAL_ERROR);
      }
      finally
      {
         // Disconnect from target
         if (target != null)
         {
            if (removeTarget)
            {
               target.disconnect();
               target.clean();
            }
            else if (disconnectTarget)
            {
               target.disconnect();
            }
         }

         // Stop HTTP server
         if (httpd != null)
         {
            httpd.shutdown();
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
            boolean stopInMain;
            String stopSymbol = null;
            boolean download;
            ICDebugConfiguration debugConfig;
            Object debugger;
            ICDITarget[] dtargets;

            project = verifyCProject(config);
            exePath = verifyProgramPath(config);
            exeFile = verifyBinary(project, exePath);
            arguments = getProgramArgumentsArray(config);
            environment = getEnvironmentAsProperty(config);
            wd = getWorkingDirectory(config);
            stopInMain = config.getAttribute(
               ICDTLaunchConfigurationConstants.ATTR_DEBUGGER_STOP_AT_MAIN, false);
            if (stopInMain)
            {
               stopSymbol = config.getAttribute(
                  ICDTLaunchConfigurationConstants.ATTR_DEBUGGER_STOP_AT_MAIN_SYMBOL,
                  ICDTLaunchConfigurationConstants.DEBUGGER_STOP_AT_MAIN_SYMBOL_DEFAULT);
            }
            download = config.getAttribute(
               IOSELaunchConfigurationConstants.ATTR_LM_DOWNLOAD, false);

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
                  stopSymbol,
                  download && stopInMain);
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

   private void createProgramProcess(ILaunchConfiguration config, ILaunch launch)
      throws CoreException
   {
      IPath exePath = verifyProgramPath(config);
      if (exePath == null)
      {
         abort("Load module file does not exist", null,
               ICDTLaunchConfigurationConstants.ERR_PROGRAM_NOT_EXIST);
      }
      new ProgramProcess(launch, renderProcessLabel(exePath.toOSString()));
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
               monitor.subTask(LAUNCH_TASK_NAME +
                               ": Loading will be aborted when safe");
            }
         }
      }

      public void finish()
      {
         if (progress < LAUNCH_TASK_MAX)
         {
            monitor.worked(LAUNCH_TASK_MAX - progress);
         }
      }
   }
}
