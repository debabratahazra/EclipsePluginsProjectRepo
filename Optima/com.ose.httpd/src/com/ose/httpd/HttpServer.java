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

package com.ose.httpd;

import java.io.File;
import java.io.IOException;
import java.util.TooManyListenersException;

public class HttpServer
{
   public HttpServer(File root, int port)
   {
      Httpd httpd = null;
      ProgressHandler progress = null;

      try
      {
         httpd = new Httpd(root, port);
         progress = new ProgressHandler();
         httpd.addProgressListener(progress);
         httpd.start();
         System.out.println("Starting local HTTP server on port " +
               httpd.getPort() + " with root directory '" +
               httpd.getRoot().getAbsolutePath() + "'.");
         httpd.join();
      }
      catch (IOException e)
      {
         e.printStackTrace();
      }
      catch (TooManyListenersException e)
      {
         e.printStackTrace();
      }
      catch (InterruptedException e)
      {
         // HTTP server interrupted.
      }
      finally
      {
         if (httpd != null)
         {
            System.out.println("Stopping HTTP server.");
            httpd.removeProgressListener(progress);
            httpd.shutdown();
         }
      }
   }

   public static void main(String[] args)
   {
      File root;
      int port;

      if (args.length == 0)
      {
         root = new File(System.getProperty("user.dir", "."));
         port = 80;
      }
      else if (args.length == 2)
      {
         root = new File(args[0]);
         try
         {
            port = Integer.parseInt(args[1]);
         }
         catch (NumberFormatException e)
         {
            System.err.println("Invalid port number.");
            return;
         }
      }
      else
      {
         System.err.println("Usage: com.ose.httpd.HttpServer [<root> <port>]");
         return;
      }

      new HttpServer(root, port);
   }

   class ProgressHandler implements ProgressListener
   {
      public void progressChanged(ProgressEvent e)
      {
         System.out.println(e.getFile() + " " + e.getProgress() + "% completed.");
      }
   }
}
