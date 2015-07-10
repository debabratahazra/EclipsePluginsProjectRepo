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

package com.ose.fmm;

import java.io.IOException;
import java.net.InetSocketAddress;
import com.ose.gateway.server.GatewayProtocol;
import com.ose.gateway.server.GatewayServer;
import com.ose.system.Gate;
import com.ose.system.SystemModel;

public class FreezeModeMonitor
{
   private final String libraryPath;

   private final ITargetProxy targetProxy;

   private final Fmi fmi;

   private final GatewayServer gatewayServer;

   public FreezeModeMonitor(String libraryPath,
                            ITargetProxy targetProxy,
                            boolean addressTranslation)
      throws IOException
   {
      if ((libraryPath == null) || (targetProxy == null))
      {
         throw new IllegalArgumentException();
      }
      this.libraryPath = libraryPath;
      this.targetProxy = targetProxy;
      
      fmi = new Fmi(libraryPath, targetProxy);
      fmi.initializeLibrary();

      // Create the gateway server and its monitor gateway
      // service and add the corresponding system model gate.
      MonitorGatewayService monitorService =
         new MonitorGatewayService(fmi, targetProxy, libraryPath, addressTranslation);
      ProgramManagerGatewayService programManagerService =
         new ProgramManagerGatewayService(fmi, targetProxy);      
      gatewayServer = new GatewayServer(
         targetProxy.getName(), 0, GatewayProtocol.DEFAULT_BROADCAST_PORT);
      gatewayServer.registerService(monitorService);
      gatewayServer.registerService(programManagerService);
   }

   public String getLibraryPath()
   {
      return libraryPath;
   }

   public ITargetProxy getTargetProxy()
   {
      return targetProxy;
   }

   public Fmi getFmi()
   {
      return fmi;
   }

   public GatewayServer getGatewayServer()
   {
      return gatewayServer;
   }

   public boolean isOpen()
   {
      return gatewayServer.isOpen();
   }

   public void close()
   {
      if (gatewayServer.isOpen())
      {
         // Disconnect the system model gate and close the
         // gateway server and its monitor gateway service.
         disconnectGate();
         gatewayServer.close();
      }
   }

   private void disconnectGate()
   {
      InetSocketAddress gwsAddress = gatewayServer.getServerAddress();
      Gate gate = SystemModel.getInstance().getGate(gwsAddress.getAddress(),
                                                    gwsAddress.getPort());
      if (gate != null)
      {
         gate.disconnect();
      }
   }
}
