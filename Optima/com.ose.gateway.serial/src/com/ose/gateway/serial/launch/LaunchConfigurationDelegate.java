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

package com.ose.gateway.serial.launch;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
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
import com.ose.gateway.serial.SerialGatewayPlugin;
import com.ose.gateway.serial.SerialGatewayServer;
import com.ose.gateway.server.GatewayProtocol;
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
         monitor.beginTask("Launching Serial Gateway", IProgressMonitor.UNKNOWN);
         run(config, launch, monitor);
         monitor.done();
      }
   }

   private void run(ILaunchConfiguration config,
                    ILaunch launch,
                    IProgressMonitor monitor)
      throws CoreException
   {
      SerialGatewayServer gatewayServer = null;

      try
      {
         String serialPort = config.getAttribute(
               ILaunchConfigurationConstants.ATTR_SERIAL_PORT, "");
         int baudRate = config.getAttribute(
               ILaunchConfigurationConstants.ATTR_BAUD_RATE, 9600);

         // Create serial gateway server and gateway server launch process.
         gatewayServer = new SerialGatewayServer(serialPort, 0,
               GatewayProtocol.DEFAULT_BROADCAST_PORT, serialPort, baudRate);
         new GatewayServerProcess(gatewayServer,
                                  launch,
                                  renderProcessLabel(serialPort, gatewayServer));

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
      catch (IOException e)
      {
         if (gatewayServer != null)
         {
            gatewayServer.close();
         }
         throw new CoreException(SerialGatewayPlugin.createErrorStatus(
               "Failed to start Serial Gateway", e));
      }
   }

   private static String renderProcessLabel(String serialPort,
                                            SerialGatewayServer gatewayServer)
   {
      String format = "{0} [{1}:{2}] ({3})";
      InetSocketAddress socketAddress = gatewayServer.getServerAddress();
      String address = socketAddress.getAddress().getHostAddress();
      String port = Integer.toString(socketAddress.getPort());
      String timestamp =
         DateFormat.getInstance().format(new Date(System.currentTimeMillis()));
      return MessageFormat.format(
         format, (Object[]) new String[] {serialPort, address, port, timestamp});
   }
}
