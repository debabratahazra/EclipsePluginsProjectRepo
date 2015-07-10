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

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.net.Socket;
import java.net.URLDecoder;
import java.util.StringTokenizer;

class HttpRequest
{
   private BufferedReader in;
   private String method;
   private String requestURI;
   private String httpVersion;

   HttpRequest(Socket socket) throws IOException
   {
      in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
   }

   boolean readRequest() throws IOException
   {
      String line;
      StringTokenizer st;
      String[] request;
      int i = 0;

      // Read the request line
      line = in.readLine();
      if (line == null)
      {
         return false;
      }

      st = new StringTokenizer(line, " ");
      request = new String[st.countTokens()];
      while (st.hasMoreTokens())
      {
         request[i++] = st.nextToken();
      }

      if (request.length == 3)
      {
         method = request[0];
         requestURI = request[1];
         try
         {
            requestURI = URLDecoder.decode(requestURI, "UTF-8");
         }
         catch (IllegalArgumentException ignore) {}
         httpVersion = request[2];
      }
      else if (request.length == 2)
      {
         method = request[0];
         requestURI = "";
         httpVersion = request[1];
      }
      else
      {
         return false;
      }

      // Discard any request headers
      do
      {
         line = in.readLine();
      }
      while ((line != null) && !line.equals(""));

      return true;
   }

   String getMethod()
   {
      return method;
   }

   String getRequestURI()
   {
      return requestURI;
   }

   String getHttpVersion()
   {
      return httpVersion;
   }

   void close()
   {
      try
      {
         in.close();
      }
      catch (IOException e) {}
   }
}
