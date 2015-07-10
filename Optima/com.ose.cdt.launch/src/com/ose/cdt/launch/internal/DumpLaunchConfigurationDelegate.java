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
import java.net.InetSocketAddress;
import java.text.DateFormat;
import java.text.MessageFormat;
import java.util.Date;
import com.ose.cdt.launch.internal.ui.LaunchUIPlugin;
import com.ose.gateway.server.GatewayServer;
import com.ose.launch.GatewayServerProcess;
import com.ose.launch.IOSELaunchConfigurationConstants;
import com.ose.plugin.control.LicenseException;
import com.ose.pmd.monitor.Dump;
import com.ose.pmd.monitor.DumpFileConverter;
import com.ose.pmd.monitor.MonitorGatewayService;
import com.ose.pmd.monitor.ProgramManagerGatewayService;
import com.ose.system.Gate;
import com.ose.system.SystemModel;
import org.eclipse.cdt.core.IBinaryParser.IBinaryObject;
import org.eclipse.cdt.core.model.ICProject;
import org.eclipse.cdt.debug.core.CDIDebugModel;
import org.eclipse.cdt.debug.core.ICDIDebugger2;
import org.eclipse.cdt.debug.core.ICDTLaunchConfigurationConstants;
import org.eclipse.cdt.debug.core.ICDebugConfiguration;
import org.eclipse.cdt.debug.core.cdi.CDIException;
import org.eclipse.cdt.debug.core.cdi.ICDISession;
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
import org.eclipse.debug.core.DebugException;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.ILaunch;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.debug.core.ILaunchManager;
import org.eclipse.debug.core.model.IProcess;

public class DumpLaunchConfigurationDelegate extends AbstractCLaunchDelegate
{
   private static final String LAUNCH_TASK_NAME = "Launching OSE Postmortem Dump";

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
         monitor.beginTask(LAUNCH_TASK_NAME, IProgressMonitor.UNKNOWN);
         run(config, launch, monitor);
      }
      else // ILaunchManager.DEBUG_MODE
      {
         boolean ok = false;
         boolean startDumpMonitor = config.getAttribute(
            IOSELaunchConfigurationConstants.ATTR_DUMP_MONITOR_MANAGED, false);

         monitor.beginTask(LAUNCH_TASK_NAME, IProgressMonitor.UNKNOWN);
         if (startDumpMonitor)
         {
            run(config, launch, monitor);
         }
         try
         {
            debug(config, launch, monitor);
            ok = true;
         }
         finally
         {
            if (!ok && startDumpMonitor)
            {
               try
               {
                  launch.terminate();
               } catch (DebugException ignore) {}
            }
         }
      }

      monitor.done();
   }

   public void terminate(ILaunch launch) throws CoreException
   {
      ILaunchConfiguration config = launch.getLaunchConfiguration();
      if (config != null)
      {
         boolean dumpMonitorManaged = config.getAttribute(
            IOSELaunchConfigurationConstants.ATTR_DUMP_MONITOR_MANAGED, false);
         if (dumpMonitorManaged)
         {
            IProcess[] processes = launch.getProcesses();
            for (int i = 0; i < processes.length; i++)
            {
               IProcess process = processes[i];
               if (process instanceof GatewayServerProcess)
               {
                  GatewayServerProcess gatewayServerProcess =
                     (GatewayServerProcess) process;
                  gatewayServerProcess.terminate();
                  break;
               }
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

   private void run(ILaunchConfiguration config,
                    ILaunch launch,
                    IProgressMonitor monitor)
      throws CoreException
   {
      File tempFile = null;
      Dump dump = null;
      GatewayServer gatewayServer = null;
      boolean gatewaySuccess = false;

      if (monitor.isCanceled())
      {
         throw new OperationCanceledException();
      }

      try
      {
         int port;
         String fileName;
         File file;
         MonitorGatewayService monitorGatewayService;
         ProgramManagerGatewayService pmGatewayService;
         InetSocketAddress serverAddress;
         InetAddress gateAddress;
         int gatePort;

         // Get gate port.
         port = config.getAttribute(
            IOSELaunchConfigurationConstants.ATTR_GATE_PORT,
            IOSELaunchConfigurationConstants.DEFAULT_VALUE_GATE_PORT);

         // Get dump file.
         fileName = config.getAttribute(
            IOSELaunchConfigurationConstants.ATTR_DUMP_FILE, "");
         file = new File(fileName.trim());
         if (!file.isFile())
         {
            abort("Dump file does not exist",
                  new FileNotFoundException(file.getAbsolutePath() + " not found"),
                  ICDTLaunchConfigurationConstants.ERR_PROGRAM_NOT_EXIST);
         }

         // Create temporary uncompressed dump file (if needed), dump handler,
         // monitor gateway service, program manager gateway service, host
         // gateway server, and host gateway server launch process.
         tempFile = createTemporaryDumpFile();
         try
         {
            DumpFileConverter dumpFileConverter = new DumpFileConverter();
            if (!dumpFileConverter.convert(file, tempFile, false))
            {
               tempFile = null;
            }
         }
         catch (IOException e)
         {
            tempFile = null;
         }
         dump = new Dump((tempFile != null) ? tempFile : file);
         monitorGatewayService = new MonitorGatewayService(dump);
         pmGatewayService = new ProgramManagerGatewayService(dump);
         gatewayServer = new GatewayServer(file.getName(), port,
               SystemModel.getInstance().getBroadcastPort());
         gatewayServer.registerService(monitorGatewayService);
         gatewayServer.registerService(pmGatewayService);
         new GatewayServerProcess(gatewayServer,
                                  dump,
                                  tempFile,
                                  launch,
                                  renderDumpLabel(file, gatewayServer));
         gatewaySuccess = true;

         // Get the actual gate address and port, which may be different from
         // the specified values if they are dynamically bound in the host
         // gateway server.
         serverAddress = gatewayServer.getServerAddress();
         gateAddress = serverAddress.getAddress();
         gatePort = serverAddress.getPort();

         // Update gate address and port in the launch configuration.
         {
            ILaunchConfigurationWorkingCopy configCopy;

            configCopy = config.getWorkingCopy();
            configCopy.setAttribute(
                  IOSELaunchConfigurationConstants.ATTR_GATE_ADDRESS,
                  gateAddress.getHostAddress());
            configCopy.setAttribute(
                  IOSELaunchConfigurationConstants.ATTR_GATE_PORT,
                  gatePort);
            configCopy.doSave();
         }

         // Add the corresponding gate to the system model.
         if (gatewayServer.isOpen())
         {
            SystemModel sm;
            Gate gate;

            sm = SystemModel.getInstance();
            sm.addGate(gateAddress, gatePort);
            gate = sm.getGate(gateAddress, gatePort);
            if ((gate != null) && !gate.isConnected())
            {
               gate.connect(monitor);
            }
         }
      }
      catch (LicenseException e)
      {
         abort("License Checkout Error",
               "Could not check out a FLEXlm license for " +
               "PMD Support plugin (com.ose.pmd)",
               e, ICDTLaunchConfigurationConstants.ERR_INTERNAL_ERROR);
      }
      catch (IOException e)
      {
         abort("Failed Starting Dump Monitor",
               e, ICDTLaunchConfigurationConstants.ERR_INTERNAL_ERROR);
      }
      finally
      {
         if (!gatewaySuccess)
         {
            if (dump != null)
            {
               dump.close();
            }
            if (gatewayServer != null)
            {
               gatewayServer.close();
            }
            if (tempFile != null)
            {
               tempFile.delete();
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

         if (debugMode.equals(ICDTLaunchConfigurationConstants.DEBUGGER_MODE_CORE))
         {
            ICProject project;
            IPath exePath;
            IBinaryObject exeFile;
            ICDebugConfiguration debugConfig;
            Object debugger;
            ICDITarget[] dtargets;

            project = verifyCProject(config);
            exePath = verifyProgramPath(config);
            exeFile = verifyBinary(project, exePath);

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
               Process process;
               IProcess debuggeeProcess = null;

               process = dtargets[i].getProcess();
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
            } catch (CDIException ignore) {}
         }
         throw e;
      }
   }

   private static File createTemporaryDumpFile()
   {
      File pluginStateDir;
      String fileName;

      pluginStateDir = LaunchUIPlugin.getDefault().getStateLocation().toFile();
      fileName = "dump-" + System.currentTimeMillis() + ".pmd";
      return new File(pluginStateDir, fileName);
   }

   private static String renderDumpLabel(File file, GatewayServer gatewayServer)
   {
      String format = "{0} [{1}:{2}] ({3})";
      String fileName;
      InetSocketAddress socketAddress;
      String address;
      String port;
      String timestamp;

      fileName = file.getAbsolutePath();
      socketAddress = gatewayServer.getServerAddress();
      address = socketAddress.getAddress().getHostAddress();
      port = Integer.toString(socketAddress.getPort());
      timestamp = DateFormat.getInstance().format(new Date(System.currentTimeMillis()));
      return MessageFormat.format(
         format, (Object[]) new String[] {fileName, address, port, timestamp});
   }
}
