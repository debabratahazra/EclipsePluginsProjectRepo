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
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.net.FileNameMap;
import java.net.Socket;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

class HttpResponse
{
   private static final String CRLF = "\r\n";

   private PrintStream out;
   private SimpleDateFormat fmt;

   HttpResponse(Socket socket) throws IOException
   {
      out = new PrintStream(socket.getOutputStream());

      fmt = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss z", Locale.UK);
      fmt.setTimeZone(TimeZone.getTimeZone("GMT"));
   }

   void writeHeaders(int status, String message)
   {
      // Response status line
      out.print("HTTP/1.0 " + status + " " + message + CRLF);

      // Response headers
      out.print("Date: " + fmt.format(new Date()) + CRLF);
      out.print("Server: " + Httpd.SERVER_NAME + CRLF);
      out.print(CRLF);
      out.flush();
   }

   void writeHeaders(int status, String message, String location)
   {
      // Response status line
      out.print("HTTP/1.0 " + status + " " + message + CRLF);

      // Response headers
      out.print("Date: " + fmt.format(new Date()) + CRLF);
      out.print("Server: " + Httpd.SERVER_NAME + CRLF);
      out.print("Location: " + location + CRLF);
      out.print(CRLF);
      out.flush();
   }

   void writeFile(Httpd httpd, String requestURI, File file, boolean body)
      throws IOException
   {
      FileInputStream in = null;

      try
      {
         long length;
         Date modDate;
         String rfc822date;
         FileNameMap map;
         String mimeType;

         in = new FileInputStream(file);
         length = file.length();
         modDate = new Date(file.lastModified());
         rfc822date = fmt.format(modDate);
         map = URLConnection.getFileNameMap();
         mimeType = map.getContentTypeFor(file.getName());
         if (mimeType == null)
         {
            mimeType = "application/octet-stream";
         }

         // Response status line
         out.print("HTTP/1.0 200 OK" + CRLF);

         // Response headers
         out.print("Date: " + fmt.format(new Date()) + CRLF);
         out.print("Server: " + Httpd.SERVER_NAME + CRLF);
         out.print("Last-Modified: " + rfc822date + CRLF);
         out.print("Content-Type: " + mimeType + CRLF);
         out.print("Content-Length: " + length + CRLF);
         out.print(CRLF);
         out.flush();

         // Response message body, i.e. the requested file
         if (body)
         {
            byte[] buffer = new byte[4096];
            long total = 0;
            int n;

            httpd.fireProgressChanged(requestURI, 0);

            while ((n = in.read(buffer)) != -1)
            {
               if (out.checkError())
               {
                  break;
               }
               else
               {
                  out.write(buffer, 0, n);
                  out.flush();
                  total += n;
                  httpd.fireProgressChanged(requestURI,
                                            (int) (100 * total / length));
               }
            }

            if (total < length)
            {
               httpd.fireProgressChanged(requestURI, 100);
            }
         }
      }
      finally
      {
         if (in != null)
         {
            try
            {
               in.close();
            }
            catch (IOException e) {}
         }
      }
   }

   void writeDirectory(File root, String requestURI, File dir, boolean body)
   {
      String title;
      File[] files;

      // Response status line
      out.print("HTTP/1.0 200 OK" + CRLF);

      // Response headers
      out.print("Date: " + fmt.format(new Date()) + CRLF);
      out.print("Server: " + Httpd.SERVER_NAME + CRLF);
      if (body)
      {
         out.print("Content-Type: text/html; charset=iso-8859-1" + CRLF);
      }
      out.print(CRLF);
      out.flush();

      // Response message body, i.e. the requested directory listing
      if (body)
      {
         // HTML header
         title = "Index of " + requestURI;
         out.println("<html>");
         out.println("<head><title>" + title + "</title></head>");
         out.println("<body><h1>" + title + "</h1><p>");

         // Link to parent directory
         if (!dir.equals(root))
         {
            out.println("<a href=\"..\">Parent directory</a><br>");
         }

         // Link to each file or directory
         files = dir.listFiles();
         Arrays.sort(files);
         for (int i = 0; i < files.length; i++)
         {
            String fileName = files[i].getName();
            String encFileName = fileName;
            try
            {
               encFileName = URLEncoder.encode(fileName, "UTF-8");
            }
            catch (UnsupportedEncodingException ignore) {}

            if (files[i].isDirectory())
            {
               out.println("<a href=\"" + encFileName + "/\"><em>" +
                           fileName + "</em></a><br>");
            }
            else
            {
               out.println("<a href=\"" + encFileName + "\">" +
                           fileName + "</a><br>");
            }
         }

         // HTML footer
         out.println("</body>");
         out.println("</html>");
         out.flush();
      }
   }

   void close()
   {
      out.close();
   }
}
