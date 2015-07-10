/* COPYRIGHT-ENEA-SRC-R1 *
 **************************************************************************
 * Copyright (C) 2006 by Enea Software AB.
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

package com.ose.pmd.monitor;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.BindException;
import java.net.InetSocketAddress;
import com.ose.gateway.server.GatewayProtocol;
import com.ose.gateway.server.GatewayServer;
import com.ose.plugin.control.LicenseException;
import com.ose.plugin.control.LicenseManager;

public class MonitorCommand
{
   public static void main(String[] args)
   {
      String name;
      int serverPort;
      int broadcastPort;
      File file;
      String licenseProperties;
      File tempFile = null;
      Dump dump = null;
      GatewayServer server = null;

      if ((args.length == 1) && !args[0].equals("-help"))
      {
         serverPort = GatewayProtocol.DEFAULT_SERVER_PORT;
         broadcastPort = GatewayProtocol.DEFAULT_BROADCAST_PORT;
         file = new File(args[0]);
         name = file.getName();
      }
      else if (args.length == 4)
      {
         name = args[0];
         try
         {
            serverPort = Integer.parseInt(args[1]);
         }
         catch (NumberFormatException e)
         {
            System.err.println("Invalid server port number.");
            return;
         }
         try
         {
            broadcastPort = Integer.parseInt(args[2]);
         }
         catch (NumberFormatException e)
         {
            System.err.println("Invalid broadcast port number.");
            return;
         }
         file = new File(args[3]);
      }
      else
      {
         System.err.println("Usage: com.ose.pmd.monitor.MonitorCommand " +
                            "[<name> <server-port> <broadcast-port>] <file>");
         return;
      }

      if (!file.isFile())
      {
         System.err.println("File '" + file.getAbsolutePath() +
                            "' does not exist.");
         return;
      }

      licenseProperties = System.getProperty("license.properties");
      if (licenseProperties != null)
      {
         FileInputStream in = null;

         try
         {
            in = new FileInputStream(licenseProperties);
            LicenseManager.getInstance().setLicenseProperties(in);
         }
         catch (FileNotFoundException e)
         {
            System.err.println("File '" + licenseProperties + "' does not exist.");
            return;
         }
         catch (IOException e)
         {
            System.err.println("Error reading file '" + licenseProperties + "'.");
            e.printStackTrace();
            return;
         }
         finally
         {
            if (in != null)
            {
               try
               {
                  in.close();
               } catch (IOException ignore) {}
            }
         }
      }

      try
      {
         DumpFileConverter dumpFileConverter = new DumpFileConverter();
         tempFile = File.createTempFile("dump-", ".pmd");
         if (!dumpFileConverter.convert(file, tempFile, false))
         {
            tempFile.delete();
            tempFile = null;
         }
      }
      catch (IOException e)
      {
         System.err.println("Failed creating temporary uncompressed PMD " +
                            "file; using original PMD file instead.");
         if (tempFile != null)
         {
            tempFile.delete();
            tempFile = null;
         }
      }

      try
      {
         MonitorGatewayService monitorService;
         ProgramManagerGatewayService pmService;
         InetSocketAddress serverAddress;

         dump = new Dump((tempFile != null) ? tempFile : file);
         monitorService = new MonitorGatewayService(dump);
         pmService = new ProgramManagerGatewayService(dump);
         server = new GatewayServer(name, serverPort, broadcastPort);
         server.registerService(monitorService);
         server.registerService(pmService);

         serverAddress = server.getServerAddress();
         System.out.println("Starting PMD monitor using PMD file '" +
               file.getAbsolutePath() + "' in host gateway server '" +
               server.getName() + "' on " +
               serverAddress.getAddress().getHostAddress() + ":" +
               serverAddress.getPort() + " with broadcast port " +
               server.getBroadcastAddress().getPort() + ".");
      }
      catch (LicenseException e)
      {
         System.err.println("Could not check out a FLEXlm license for " +
                            "PMD monitor (com.ose.pmd).");
         System.err.println(e.getMessage());
      }
      catch (BindException e)
      {
         System.err.println("Error starting PMD monitor. " +
                            "Port number " + serverPort + " is in use.");
      }
      catch (IOException e)
      {
         System.err.println("Error starting PMD monitor.");
         e.printStackTrace();
      }
      finally
      {
         Runtime.getRuntime().addShutdownHook(new ShutdownThread(dump, tempFile));
      }
   }

   private static class ShutdownThread extends Thread
   {
      private final Dump dump;
      private final File tempFile;

      ShutdownThread(Dump dump, File tempFile)
      {
         this.dump = dump;
         this.tempFile = tempFile;
      }

      public void run()
      {
         if (dump != null)
         {
            dump.close();
         }
         if (tempFile != null)
         {
            tempFile.delete();
         }
      }
   }
}
