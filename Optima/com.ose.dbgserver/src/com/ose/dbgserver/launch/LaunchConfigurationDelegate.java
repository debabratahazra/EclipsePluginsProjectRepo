/* COPYRIGHT-ENEA-SRC-R2 *
 **************************************************************************
 * Copyright (C) 2011 by Enea Software AB.
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

package com.ose.dbgserver.launch;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import java.text.DateFormat;
import java.text.MessageFormat;
import java.util.Date;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.debug.core.ILaunch;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchManager;
import org.eclipse.debug.core.model.ILaunchConfigurationDelegate;
import com.ose.dbgserver.DebugServerPlugin;
import com.ose.dbgserver.monitor.MonitorGatewayService;
import com.ose.dbgserver.monitor.ProgramManagerGatewayService;
import com.ose.gateway.server.GatewayProtocol;
import com.ose.gateway.server.GatewayServer;
import com.ose.plugin.control.LicenseException;
import com.ose.system.Gate;
import com.ose.system.SystemModel;

public class LaunchConfigurationDelegate implements ILaunchConfigurationDelegate
{
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
         monitor.beginTask("Launching OSE Debug Server", IProgressMonitor.UNKNOWN);
         run(config, launch, monitor);
         monitor.done();
      }
   }

   private void run(ILaunchConfiguration config,
                    ILaunch launch,
                    IProgressMonitor monitor)
      throws CoreException
   {
      GatewayServer gatewayServer = null;

      try
      {
         String address;
         int port;
         InetAddress inetAddress;
         String name;
         MonitorGatewayService monitorGatewayService;
         ProgramManagerGatewayService pmGatewayService;

         // Get the debug server target address and port.
         address = config.getAttribute(
               ILaunchConfigurationConstants.ATTR_DEBUG_SERVER_ADDRESS, "");
         port = config.getAttribute(
               ILaunchConfigurationConstants.ATTR_DEBUG_SERVER_PORT,
               ILaunchConfigurationConstants.DEFAULT_VALUE_DEBUG_SERVER_PORT);
         try
         {
            inetAddress = InetAddress.getByName(address);
         }
         catch (UnknownHostException e)
         {
            throw new CoreException(DebugServerPlugin.createErrorStatus(
                  "Unknown debug server target IP address", e));
         }
         name = inetAddress.getHostAddress() + ":" + port;

         // Create monitor gateway service, program manager gateway service,
         // host gateway server, and host gateway server launch process.
         monitorGatewayService = new MonitorGatewayService(inetAddress, port);
         pmGatewayService = new ProgramManagerGatewayService(inetAddress, port);
         gatewayServer = new GatewayServer(name,
               0, GatewayProtocol.DEFAULT_BROADCAST_PORT);
         gatewayServer.registerService(monitorGatewayService);
         gatewayServer.registerService(pmGatewayService);
         new GatewayServerProcess(
               gatewayServer,
               launch,
               renderProcessLabel(name, gatewayServer));

         // Add the corresponding gate to the system model.
         if (gatewayServer.isOpen())
         {
            InetSocketAddress serverAddress = gatewayServer.getServerAddress();
            InetAddress gateAddress = serverAddress.getAddress();
            int gatePort = serverAddress.getPort();
            SystemModel sm = SystemModel.getInstance();
            sm.addGate(gateAddress, gatePort);
            Gate gate = sm.getGate(gateAddress, gatePort);
            if ((gate != null) && !gate.isConnected())
            {
               gate.connect(monitor);
            }
         }
      }
      catch (LicenseException e)
      {
         throw new CoreException(DebugServerPlugin.createErrorStatus(
               "Could not check out a FLEXlm license for the " +
               "OSE Debug Server plugin (com.ose.dbgserver)",
               e));
      }
      catch (IOException e)
      {
         if (gatewayServer != null)
         {
            gatewayServer.close();
         }
         throw new CoreException(DebugServerPlugin.createErrorStatus(
               "Failed to start the OSE Debug Server", e));
      }
   }

   private static String renderProcessLabel(String name,
                                            GatewayServer gatewayServer)
   {
      String format = "{0} [{1}:{2}] ({3})";
      InetSocketAddress socketAddress = gatewayServer.getServerAddress();
      String address = socketAddress.getAddress().getHostAddress();
      String port = Integer.toString(socketAddress.getPort());
      String timestamp =
         DateFormat.getInstance().format(new Date(System.currentTimeMillis()));
      return MessageFormat.format(
         format, (Object[]) new String[] {name, address, port, timestamp});
   }
}
