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

package com.ose.cdt.debug.mi.core;

import java.io.File;
import java.io.IOException;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;
import java.text.DateFormat;
import java.text.MessageFormat;
import java.util.Date;
import com.ose.cdt.debug.mi.core.command.CLIAddLoadModule;
import com.ose.cdt.debug.mi.core.command.CLIDefineMemoryRegion;
import com.ose.cdt.debug.mi.core.command.MITargetAttach;
import com.ose.launch.IOSELaunchConfigurationConstants;
import com.ose.plugin.control.LicenseException;
import com.ose.plugin.control.LicenseManager;
import com.ose.system.BlockFilter;
import com.ose.system.BlockInfo;
import com.ose.system.Gate;
import com.ose.system.ProcessFilter;
import com.ose.system.ProcessInfo;
import com.ose.system.ProgramInfo;
import com.ose.system.ServiceException;
import com.ose.system.SystemModel;
import org.eclipse.cdt.core.IBinaryParser.IBinaryObject;
import org.eclipse.cdt.debug.core.ICDIDebugger2;
import org.eclipse.cdt.debug.core.ICDTLaunchConfigurationConstants;
import org.eclipse.cdt.debug.core.cdi.ICDISession;
import org.eclipse.cdt.debug.core.cdi.model.ICDITarget;
import org.eclipse.cdt.debug.mi.core.IMILaunchConfigurationConstants;
import org.eclipse.cdt.debug.mi.core.MIException;
import org.eclipse.cdt.debug.mi.core.MIPlugin;
import org.eclipse.cdt.debug.mi.core.MISession;
import org.eclipse.cdt.debug.mi.core.cdi.Session;
import org.eclipse.cdt.debug.mi.core.cdi.model.Target;
import org.eclipse.cdt.debug.mi.core.command.Command;
import org.eclipse.cdt.debug.mi.core.command.CommandFactory;
import org.eclipse.cdt.debug.mi.core.output.MIInfo;
import org.eclipse.cdt.utils.elf.Elf;
import org.eclipse.cdt.utils.elf.Elf.PHdr;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Status;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.ILaunch;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchConfigurationType;
import org.eclipse.debug.core.model.IProcess;
import org.eclipse.osgi.service.datalocation.Location;

public class GDBDebugger implements ICDIDebugger2
{
   public ICDISession createDebuggerSession(ILaunch launch,
                                            IBinaryObject exe,
                                            IProgressMonitor monitor)
      throws CoreException
   {
      return createSession(launch, exe.getPath().toFile(), monitor);
   }

   public ICDISession createSession(ILaunch launch,
                                    File exe,
                                    IProgressMonitor monitor)
      throws CoreException
   {
      ILaunchConfiguration config;
      String debugMode;
      Session debugSession = null;

      if (monitor == null)
      {
         monitor = new NullProgressMonitor();
      }
      monitor.beginTask("Creating OSE GDB debugger session", IProgressMonitor.UNKNOWN);
      if (monitor.isCanceled())
      {
         throw new OperationCanceledException();
      }

      // Check out FLEXlm license.
      // XXX: Due to a bug in the FLEXlm Java client library on Solaris,
      // we cannot check out the FLEXlm license when creating a GDBDebugger
      // instance and check it in when disposing the GDBDebugger instance
      // without hanging the whole JVM. Instead, we have to check it out
      // only once when the first GDBDebugger instance is created.
      checkoutLicense();

      config = launch.getLaunchConfiguration();
      debugMode = config.getAttribute(
         ICDTLaunchConfigurationConstants.ATTR_DEBUGGER_START_MODE,
         ICDTLaunchConfigurationConstants.DEBUGGER_MODE_RUN);

      // FIXME: We should really use DEBUGGER_MODE_ATTACH/MISession.ATTACH
      // instead of DEBUGGER_MODE_RUN/MISession.PROGRAM. However, I don't dare
      // to do that change for now and old launch configurations would also stop
      // working.
      if (debugMode.equals(ICDTLaunchConfigurationConstants.DEBUGGER_MODE_RUN))
      {
         debugSession = createGDBSession(config, exe, MISession.PROGRAM, monitor);
      }
      else if (debugMode.equals(ICDTLaunchConfigurationConstants.DEBUGGER_MODE_CORE))
      {
         debugSession = createGDBSession(config, exe, MISession.CORE, monitor);
      }
      else if (debugMode.equals(ICDTLaunchConfigurationConstants.DEBUGGER_MODE_ATTACH))
      {
         throw newCoreException("OSE GDB debugger does not support attaching", null);
      }

      if (debugSession != null)
      {
         boolean verboseMode;
         ICDITarget[] dtargets;

         verboseMode = config.getAttribute(
               IMILaunchConfigurationConstants.ATTR_DEBUGGER_VERBOSE_MODE,
               IMILaunchConfigurationConstants.DEBUGGER_VERBOSE_MODE_DEFAULT);
         dtargets = debugSession.getTargets();
         for (int i = 0; i < dtargets.length; i++)
         {
            Target dtarget = (Target) dtargets[i];
            Process debugger = debugSession.getSessionProcess(dtarget);
            if (debugger != null)
            {
               IProcess debuggerProcess =
                  DebugPlugin.newProcess(launch, debugger, renderDebuggerProcessLabel());
               launch.addProcess(debuggerProcess);
            }
            dtarget.enableVerboseMode(verboseMode);
         }
      }

      monitor.done();
      return debugSession;
   }

   private static void checkoutLicense() throws CoreException
   {
      try
      {
         LicenseManager lm = LicenseManager.getInstance();
         long cookie = (long) (Math.random() * Long.MAX_VALUE);
         long r = lm.registerPlugin("com.ose.cdt.launch", "1.100", cookie);
         long t = System.currentTimeMillis() / 10000;
         if ((r != (cookie ^ t)) && (r != (cookie ^ (t - 1))))
         {
            throw new LicenseException("Incorrect response value");
         }
      }
      catch (LicenseException e)
      {
         throw newCoreException("Could not check out a FLEXlm license for OSE " +
            "C/C++ Development Tools Launching Support plugin (com.ose.cdt.launch)", e);
      }
   }

   protected Session createGDBSession(ILaunchConfiguration config,
                                      File exe,
                                      int sessionType,
                                      IProgressMonitor monitor)
      throws CoreException
   {
      Session session = null;
      boolean ok = false;

      try
      {
         String gdb;
         String miVersion;
         File exeFile = null;
         File cwd;
         String gdbinit;
         CommandFactory commandFactory;
         String[] extraArgs;
         ICDITarget[] dtargets;

         gdb = config.getAttribute(
            IMILaunchConfigurationConstants.ATTR_DEBUG_NAME,
            IOSEMILaunchConfigurationConstants.VALUE_DEBUG_NAME_NATIVE);
         miVersion = config.getAttribute(
            IMILaunchConfigurationConstants.ATTR_DEBUGGER_PROTOCOL, "mi");
         commandFactory = new OSECommandFactory(miVersion);
         if (!isLoadModule(config.getType(), exe))
         {
            exeFile = exe;
         }
         cwd = getProjectPath(config).toFile();
         gdbinit = config.getAttribute(
            IMILaunchConfigurationConstants.ATTR_GDB_INIT, ".gdbinit");
         extraArgs = new String[]
            {"--cd=" + cwd.getAbsolutePath(), "--command=" + gdbinit};
         session = MIPlugin.getDefault().createSession(
               sessionType, gdb, commandFactory, exeFile, extraArgs, false, monitor);

         if (monitor.isCanceled())
         {
            throw new OperationCanceledException();
         }

         dtargets = session.getTargets();
         for (int i = 0; i < dtargets.length; i++)
         {
            Target target = (Target) dtargets[i];
            target.getMISession().setCommandFactory(commandFactory);
         }

         initGDBSession(config, exe, session, monitor);

         ok = true;
         return session;
      }
      catch (IOException e)
      {
         throw newCoreException(e);
      }
      catch (MIException e)
      {
         throw newCoreException(e);
      }
      finally
      {
         if (!ok && (session != null))
         {
            try
            {
               session.terminate();
            } catch (Exception e) {}
         }
      }
   }

   protected void initGDBSession(ILaunchConfiguration config,
                                 File exe,
                                 Session session,
                                 IProgressMonitor monitor)
      throws CoreException
   {
      try
      {
         String address;
         int port;
         String huntPath;
         String scope;
         String segment;
         String block;
         String process;
         boolean dumpLaunch;
         ICDITarget[] dtargets;

         address = config.getAttribute(
            IOSELaunchConfigurationConstants.ATTR_GATE_ADDRESS, "");
         port = config.getAttribute(
            IOSELaunchConfigurationConstants.ATTR_GATE_PORT,
            IOSELaunchConfigurationConstants.DEFAULT_VALUE_GATE_PORT);
         huntPath = config.getAttribute(
            IOSELaunchConfigurationConstants.ATTR_TARGET_HUNT_PATH, "");
         scope = config.getAttribute(
            IOSEMILaunchConfigurationConstants.ATTR_DEBUG_SCOPE,
            IOSEMILaunchConfigurationConstants.VALUE_DEBUG_SCOPE_SEGMENT);
         segment = config.getAttribute(
            IOSEMILaunchConfigurationConstants.ATTR_SEGMENT_ID, "");
         block = config.getAttribute(
            IOSEMILaunchConfigurationConstants.ATTR_BLOCK_ID, "");
         process = config.getAttribute(
            IOSEMILaunchConfigurationConstants.ATTR_PROCESS_ID, "");
         dumpLaunch = config.getType().getIdentifier().equals(
            IOSELaunchConfigurationConstants.ID_LAUNCH_DUMP);

         dtargets = session.getTargets();
         for (int i = 0; i < dtargets.length; i++)
         {
            Target target;
            MISession mi;
            CommandFactory factory;
            Command cmd;
            MIInfo info;
            String[] params;
            ProgramInfo program;
            String poolstart;
            String poolend;

            target = (Target) dtargets[i];
            mi = target.getMISession();
            factory = mi.getCommandFactory();

            // Enclose the address with brackets if it's an IPv6 address.
            try
            {
               InetAddress inetAddress = InetAddress.getByName(address);
               if (inetAddress instanceof Inet6Address)
               {
                  address = "[" + inetAddress.getHostAddress() + "]";
               }
            }
            catch (UnknownHostException e)
            {
               MIPlugin.log(e);
            }

            // Connect to the target.
            params = new String[3];
            params[0] = "ose";
            params[1] = address + ":" + Integer.toString(port);
            params[2] = huntPath;
            cmd = factory.createMITargetSelect(params);
            mi.postCommand(cmd);
            info = cmd.getMIInfo();
            if (info == null)
            {
               throw new MIException("No answer from MITargetSelect");
            }

            // Obtain the program (core module or load module) to be debugged.
            program = getProgram(monitor, config);

            // If debugging a load module, add it to GDB.
            if (isLoadModule(config.getType(), exe))
            {
               if (program == null)
               {
                  throw newCoreException("The program for the process to be " +
                                         "debugged cannot be found", null);
               }

               cmd = new CLIAddLoadModule(exe.getAbsolutePath(),
                                          program.getInstallHandle());
               mi.postCommand(cmd);
               try
               {
                  info = cmd.getMIInfo();
               }
               catch (MIException e)
               {
                  // Suppressed to ignore "No stack" messages. The GDB
                  // command add-ose-load-module may report "No stack"
                  // even if it succeeds but CDT treats this as an error.
               }
               if (info == null)
               {
                  throw new MIException("No answer from CLIAddLoadModule");
               }
            }

            // Attach to the segment, block, or process to be debugged.
            if (scope.equals(IOSEMILaunchConfigurationConstants.VALUE_DEBUG_SCOPE_SEGMENT))
            {
               params = new String[3];
               params[0] = "segment";
               params[1] = segment;
               params[2] = process;
            }
            else if (scope.equals(IOSEMILaunchConfigurationConstants.VALUE_DEBUG_SCOPE_BLOCK))
            {
               params = new String[3];
               params[0] = "block";
               params[1] = block;
               params[2] = process;
            }
            else // IOSEMILaunchConfigurationConstants.VALUE_DEBUG_SCOPE_PROCESS
            {
               params = new String[2];
               params[0] = "process";
               params[1] = process;
            }
            cmd = new MITargetAttach(factory.getMIVersion(), params);
            mi.postCommand(cmd);
            info = cmd.getMIInfo();
            if (info == null)
            {
               throw new MIException("No answer from MITargetAttach");
            }

            // Tell GDB that read-only sections in the ELF file really are
            // read-only. In that case, GDB can fetch values from these
            // sections out of the ELF file, rather than from the target.
            params = new String[] {"trust-readonly-sections", "on"};
            cmd = factory.createMIGDBSet(params);
            mi.postCommand(cmd);
            info = cmd.getMIInfo();
            if (info == null)
            {
               throw new MIException("No answer from MIGDBSet");
            }

            // If we are not debugging a dump or OSE 4, make GDB cache the
            // program's stack pool.
            if (!dumpLaunch && (program != null) &&
                (program.getTarget().getOsType() == com.ose.system.Target.OS_OSE_5))
            {
               poolstart = "0x" + Long.toHexString(program.getStackPoolBaseLong());
               poolend = "0x" + Long.toHexString(program.getStackPoolBaseLong() +
                     program.getStackPoolSizeLong());
               cmd = new CLIDefineMemoryRegion(poolstart, poolend, "rw cache");
               mi.postCommand(cmd);
               info = cmd.getMIInfo();
               if (info == null)
               {
                  throw new MIException("No answer from CLIDefineMemoryRegion");
               }
            }

            // We have to manually set the suspended state when we attach.
            mi.getMIInferior().setSuspended();
            mi.getMIInferior().update();
         }
      }
      catch (MIException e)
      {
         throw newCoreException(e);
      }
   }

   /*
    * Try to find the path
    * "<toolsroot>/gdb_<host>_<arch>_<version>/bin/<arch>-ose-gdb[.exe]"
    * given "<arch>-ose-gdb".
    */
   public static String findGDB(String gdbName)
   {
      Location eclipseLoc;
      URL eclipseURL;
      File eclipseDir;
      File toolsDir;
      String os;
      String gdbDirName;
      File[] list;
      File gdbDir = null;
      File gdbBinDir = null;
      String gdbPath = null;

      eclipseLoc = Platform.getInstallLocation();
      if (eclipseLoc == null)
      {
         return null;
      }
      eclipseURL = eclipseLoc.getURL();
      if (eclipseURL == null)
      {
         return null;
      }
      eclipseDir = new File(eclipseURL.getPath());
      toolsDir = eclipseDir.getParentFile();
      if (toolsDir == null)
      {
         return null;
      }

      os = Platform.getOS();
      if (os.equals(Platform.OS_LINUX))
      {
         gdbDirName = "gdb_linux";
      }
      else if (os.equals(Platform.OS_SOLARIS))
      {
         gdbDirName = "gdb_solaris2";
      }
      else if (os.equals(Platform.OS_WIN32))
      {
         gdbDirName = "gdb_win32";
      }
      else
      {
         return null;
      }

      if (gdbName.equals(IOSEMILaunchConfigurationConstants.VALUE_DEBUG_NAME_ARM))
      {
         gdbDirName += "_arm";
      }
      else if (gdbName.equals(IOSEMILaunchConfigurationConstants.VALUE_DEBUG_NAME_MIPS))
      {
         gdbDirName += "_mips";
      }
      else if (gdbName.equals(IOSEMILaunchConfigurationConstants.VALUE_DEBUG_NAME_POWERPC))
      {
         gdbDirName += "_powerpc";
      }
      else
      {
         return null;
      }

      list = toolsDir.listFiles();
      if ((list == null) || (list.length == 0))
      {
         return null;
      }

      for (int i = 0; i < list.length; i++)
      {
         File f = list[i];
         if (f.isDirectory() && f.getName().startsWith(gdbDirName))
         {
            gdbDir = f;
            break;
         }
      }

      if (gdbDir == null)
      {
         return null;
      }

      list = gdbDir.listFiles();
      if ((list == null) || (list.length == 0))
      {
         return null;
      }

      for (int i = 0; i < list.length; i++)
      {
         File f = list[i];
         if (f.isDirectory() && f.getName().equals("bin"))
         {
            gdbBinDir = f;
            break;
         }
      }

      if (gdbBinDir == null)
      {
         return null;
      }

      list = gdbBinDir.listFiles();
      if ((list == null) || (list.length == 0))
      {
         return null;
      }

      for (int i = 0; i < list.length; i++)
      {
         File f = list[i];
         if (f.isFile() && f.getName().startsWith(gdbName))
         {
            gdbPath = f.getAbsolutePath();
            break;
         }
      }

      return gdbPath;
   }

   public static boolean isLoadModule(ILaunchConfigurationType configType,
                                      File file)
   {
      String configTypeId;
      boolean loadModuleLaunch;
      boolean dumpLaunch = false;
      boolean loadModuleFile = false;

      if ((configType == null) || (file == null))
      {
         throw new IllegalArgumentException();
      }

      configTypeId = configType.getIdentifier();
      loadModuleLaunch = configTypeId.equals(
         IOSELaunchConfigurationConstants.ID_LAUNCH_LOAD_MODULE);
      if (!loadModuleLaunch)
      {
         dumpLaunch = configTypeId.equals(
            IOSELaunchConfigurationConstants.ID_LAUNCH_DUMP);
         try
         {
            loadModuleFile = isLoadModule(file.getAbsolutePath());
         } catch (IOException ignore) {}
      }

      return (loadModuleLaunch || (dumpLaunch && loadModuleFile));
   }

   public static boolean isLoadModule(String file) throws IOException
   {
      boolean loadModule = false;
      Elf elf = null;

      if (file == null)
      {
         throw new IllegalArgumentException();
      }

      try
      {
         PHdr[] headers;

         elf = new Elf(file);
         headers = elf.getPHdrs();
         for (int i = 0; i < headers.length; i++)
         {
            PHdr header = headers[i];
            if (header.p_type == PHdr.PT_DYNAMIC)
            {
               loadModule = true;
               break;
            }
         }
      }
      finally
      {
         if (elf != null)
         {
            elf.dispose();
         }
      }

      return loadModule;
   }

   protected static IPath getProjectPath(ILaunchConfiguration config)
      throws CoreException
   {
      String projectName = config.getAttribute(
         ICDTLaunchConfigurationConstants.ATTR_PROJECT_NAME, (String) null);
      if (projectName != null)
      {
         projectName = projectName.trim();
         if (projectName.length() > 0)
         {
            IProject project =
               ResourcesPlugin.getWorkspace().getRoot().getProject(projectName);
            IPath path = project.getLocation();
            if (path != null)
            {
               return path;
            }
         }
      }
      return Path.EMPTY;
   }

   public static ProgramInfo getProgram(IProgressMonitor monitor,
                                        ILaunchConfiguration config)
      throws CoreException
   {
      String address;
      int port;
      String huntPath;
      String scope;
      String segment;
      String block;
      String process;
      InetAddress inetAddress;
      com.ose.system.Target target = null;
      boolean removeTarget = false;
      boolean disconnectTarget = false;

      address = config.getAttribute(
            IOSELaunchConfigurationConstants.ATTR_GATE_ADDRESS, "");
      port = config.getAttribute(
            IOSELaunchConfigurationConstants.ATTR_GATE_PORT,
            IOSELaunchConfigurationConstants.DEFAULT_VALUE_GATE_PORT);
      huntPath = config.getAttribute(
            IOSELaunchConfigurationConstants.ATTR_TARGET_HUNT_PATH, "");
      scope = config.getAttribute(
            IOSEMILaunchConfigurationConstants.ATTR_DEBUG_SCOPE,
            IOSEMILaunchConfigurationConstants.VALUE_DEBUG_SCOPE_SEGMENT);
      segment = config.getAttribute(
            IOSEMILaunchConfigurationConstants.ATTR_SEGMENT_ID, "");
      block = config.getAttribute(
            IOSEMILaunchConfigurationConstants.ATTR_BLOCK_ID, "");
      process = config.getAttribute(
            IOSEMILaunchConfigurationConstants.ATTR_PROCESS_ID, "");

      try
      {
         inetAddress = InetAddress.getByName(address);
      }
      catch (UnknownHostException e)
      {
         throw newCoreException("Unknown gate IP address", e);
      }

      try
      {
         SystemModel sm;
         Gate gate;
         int sid;
         int bid;
         int pid;
         int progid;

         sm = SystemModel.getInstance();
         gate = sm.getGate(inetAddress, port);
         if (gate == null)
         {
            gate = sm.addGate(inetAddress, port);
            if (gate == null)
            {
               throw newCoreException("Gate cannot be found", null);
            }
         }
         if (!gate.isConnected())
         {
            gate.connect(monitor);
         }

         if (monitor.isCanceled())
         {
            throw new OperationCanceledException();
         }

         target = gate.getTarget(huntPath);
         if (target == null)
         {
            target = gate.addTarget(huntPath);
            if (target == null)
            {
               throw newCoreException("Gate is disconnected", null);
            }
            removeTarget = true;
         }
         if (!target.isConnected())
         {
            target.connect(monitor);
            disconnectTarget = true;
         }

         if (!target.hasProgramSupport())
         {
            return null;
         }

         if (monitor.isCanceled())
         {
            throw new OperationCanceledException();
         }

         if (scope.equals(IOSEMILaunchConfigurationConstants.VALUE_DEBUG_SCOPE_SEGMENT))
         {
            sid = getSegmentId(segment);
            if (target.isMonitorObsolete())
            {
               /* Workaround for obsolete OSE monitors (i.e. OSE 5.1.1, where
                * program ID is equal to segment ID) that doesn't support
                * filtering processes within in a segment.
                */
               return target.getProgramInfo(monitor, sid);
            }
            else
            {
               pid = getProcessId(monitor,
                                  target,
                                  com.ose.system.Target.SCOPE_SEGMENT_ID,
                                  sid,
                                  process);
            }
         }
         else if (scope.equals(IOSEMILaunchConfigurationConstants.VALUE_DEBUG_SCOPE_BLOCK))
         {
            bid = getBlockId(monitor, target, block);
            pid = getProcessId(monitor,
                               target,
                               com.ose.system.Target.SCOPE_BLOCK_ID,
                               bid,
                               process);
         }
         else if (scope.equals(IOSEMILaunchConfigurationConstants.VALUE_DEBUG_SCOPE_PROCESS))
         {
            pid = getProcessId(monitor,
                               target,
                               com.ose.system.Target.SCOPE_SYSTEM,
                               0,
                               process);
         }
         else
         {
            throw newCoreException("Invalid debug scope", null);
         }

         if (monitor.isCanceled())
         {
            throw new OperationCanceledException();
         }

         progid = target.getProgramId(monitor, pid);

         if (monitor.isCanceled())
         {
            throw new OperationCanceledException();
         }

         return target.getProgramInfo(monitor, progid);
      }
      catch (ServiceException e)
      {
         throw newCoreException(
            "Error reported from target when retrieving program information", e);
      }
      catch (IOException e)
      {
         throw newCoreException(
            "Error communicating with target when retrieving program information", e);
      }
      catch (Exception e)
      {
         throw newCoreException("Error when retrieving program information", e);
      }
      finally
      {
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

   private static int getSegmentId(String segment)
      throws CoreException
   {
      try
      {
         return Integer.decode(segment.trim()).intValue();
      }
      catch (NumberFormatException e)
      {
         throw newCoreException("Invalid segment ID: " + segment, null);
      }
   }

   private static int getBlockId(IProgressMonitor monitor,
                                 com.ose.system.Target target,
                                 String block)
      throws CoreException, IOException
   {
      BlockFilter filter;
      BlockInfo[] blocks;

      try
      {
         return Integer.decode(block.trim()).intValue();
      }
      catch (NumberFormatException ignore) {}

      filter = new BlockFilter();
      filter.filterName(false, block);
      blocks = target.getFilteredBlocks(monitor,
                                        com.ose.system.Target.SCOPE_SYSTEM,
                                        0,
                                        filter);
      if (blocks.length > 0)
      {
         return blocks[0].getId();
      }
      else
      {
         throw newCoreException("Block not found: " + block, null);
      }
   }

   private static int getProcessId(IProgressMonitor monitor,
                                   com.ose.system.Target target,
                                   int scopeType,
                                   int scopeId,
                                   String process)
      throws CoreException, IOException
   {
      ProcessFilter filter;
      ProcessInfo[] processes;

      try
      {
         return Integer.decode(process.trim()).intValue();
      }
      catch (NumberFormatException ignore) {}

      filter = new ProcessFilter();
      filter.filterName(false, process);
      processes = target.getFilteredProcesses(monitor,
                                              scopeType,
                                              scopeId,
                                              filter);
      if (processes.length > 0)
      {
         return processes[0].getId();
      }
      else
      {
         throw newCoreException("Process not found: " + process, null);
      }
   }

   protected static String renderDebuggerProcessLabel()
   {
      String format = "{0} ({1})";
      String message = "Debugger Process";
      String timestamp = DateFormat.getInstance().format(new Date());
      return MessageFormat.format(format, (Object[]) new String[]{message, timestamp});
   }

   protected static CoreException newCoreException(Throwable exception)
   {
      return new CoreException(new Status(
            IStatus.ERROR,
            MIPlugin.getUniqueIdentifier(),
            ICDTLaunchConfigurationConstants.ERR_INTERNAL_ERROR,
            "Error creating debug session",
            exception));
   }

   protected static CoreException newCoreException(String message, Throwable exception)
   {
      return new CoreException(new Status(
            IStatus.ERROR,
            MIPlugin.getUniqueIdentifier(),
            ICDTLaunchConfigurationConstants.ERR_INTERNAL_ERROR,
            message,
            exception));
   }
}
