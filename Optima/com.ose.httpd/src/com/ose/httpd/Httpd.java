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
import java.io.InterruptedIOException;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.TooManyListenersException;

public class Httpd extends Thread
{
   static final String SERVER_NAME = "HTTP Daemon";

   private File root;
   private ServerSocket server;
   private ProgressListener listener;
   private volatile boolean listen;

   public Httpd() throws IOException
   {
      this(new File(System.getProperty("user.dir", ".")), 80);
   }

   public Httpd(File root, int port) throws IOException
   {
      super(SERVER_NAME);
      setDaemon(true);
      setPriority(Thread.NORM_PRIORITY - 1);

      if (!(root.isDirectory() && root.canRead()))
      {
         throw new IOException("Given HTTP root directory '" +
                               root.getAbsolutePath() +
                               "' is not a directory or can't be read.");
      }

      this.root = root;
      server = new ServerSocket();
      if (port > 0)
      {
         server.setReuseAddress(true);
      }
      server.bind(new InetSocketAddress(port));
      server.setSoTimeout(3000);
      listener = null;
      listen = true;
   }

   public File getRoot()
   {
      return root;
   }

   public int getPort()
   {
      return server.getLocalPort();
   }

   public synchronized void addProgressListener(ProgressListener l)
      throws TooManyListenersException
   {
      if (listener == null)
      {
         listener = l;
      }
      else
      {
         throw new TooManyListenersException("Too many HTTP progress listeners.");
      }
   }

   public synchronized void removeProgressListener(ProgressListener l)
   {
      listener = null;
   }

   synchronized void fireProgressChanged(String requestURI, int progress)
   {
      if (listener != null)
      {
         ProgressEvent e = new ProgressEvent(this, requestURI, progress);
         listener.progressChanged(e);
      }
   }

   public void shutdown()
   {
      listen = false;
      interrupt();
   }

   public void run()
   {
      try
      {
         while (listen)
         {
            try
            {
               Socket client = server.accept();
               handleConnection(client);
            }
            catch (InterruptedIOException e) {}
         }
      }
      catch (IOException e)
      {
         System.err.println("I/O exception occurred in HTTP daemon:");
         e.printStackTrace();
      }
      finally
      {
         try
         {
            server.close();
         }
         catch (IOException e) {}
      }
   }

   private void handleConnection(Socket socket)
   {
      HttpRequest request = null;
      HttpResponse response = null;

      try
      {
         request = new HttpRequest(socket);
         response = new HttpResponse(socket);

         if (request.readRequest())
         {
            String method = request.getMethod();
            String requestURI = request.getRequestURI();

            if (method.equals("GET") || method.equals("HEAD"))
            {
               File file = new File(root, requestURI);
               boolean body = method.equals("GET");

               if (file.exists())
               {
                  if (file.canRead())
                  {
                     if (file.isFile())
                     {
                        response.writeFile(this, requestURI, file, body);
                     }
                     else
                     {
                        if (requestURI.endsWith("/"))
                        {
                           response.writeDirectory(root, requestURI, file, body);
                        }
                        else
                        {
                           response.writeHeaders(301, "Moved Permanently",
                                                 requestURI + "/");
                        }
                     }
                  }
                  else
                  {
                     response.writeHeaders(403, "Forbidden");
                  }
               }
               else
               {
                  response.writeHeaders(404, "Not Found");
               }
            }
            else
            {
               response.writeHeaders(501, "Not Implemented");
            }
         }
         else
         {
            response.writeHeaders(400, "Bad Request");
         }
      }
      catch (InterruptedIOException e)
      {
      }
      catch (IOException e)
      {
         System.err.println("I/O exception occurred in HTTP daemon:");
         e.printStackTrace();
      }
      finally
      {
         if (request != null)
         {
            request.close();
         }
         if (response != null)
         {
            response.close();
         }
         try
         {
            socket.close();
         }
         catch (IOException e) {}
      }
   }
}
