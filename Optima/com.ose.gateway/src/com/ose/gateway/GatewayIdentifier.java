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

package com.ose.gateway;

import java.net.InetAddress;
import java.util.StringTokenizer;

/**
 * This class is used to identify a gateway server.
 */
public final class GatewayIdentifier
{
   private String name;
   private InetAddress address;
   private int port;
   private int protocolVersion;

   /*
    * Create a new gateway server identifier object.
    *
    * @param data  the gateway server identifier data.
    * @throws IllegalArgumentException  if invalid gateway server identifier data.
    */
   GatewayIdentifier(String data) throws IllegalArgumentException
   {
      try
      {
         StringTokenizer st = new StringTokenizer(data, "\n");

         while (st.hasMoreTokens())
         {
            String token = st.nextToken();

            if (token.startsWith("OSEGW!"))
            {
               protocolVersion = Integer.parseInt(token.substring(6).trim());
            }
            else if (token.startsWith("Gateway-name:"))
            {
               name = token.substring(13).trim();
            }
            else if (token.startsWith("Gateway-addr:"))
            {
               /* URL format: tcp://<address>:<port>/ */
               String url = token.substring(13).trim();
               int firstColon = url.indexOf(':');
               int lastColon = url.lastIndexOf(':');
               int length = url.length();

               if ((firstColon != -1) && (lastColon != -1) &&
                   (firstColon + 3 < lastColon) &&
                   (lastColon + 1 < length - 1))
               {
                  String a = url.substring(firstColon + 3, lastColon);
                  String p = url.substring(lastColon + 1, length - 1);
                  address = InetAddress.getByName(a);
                  port = Integer.parseInt(p);
               }
            }
         }
      }
      catch (Exception e)
      {
         throw new IllegalArgumentException("Invalid gateway identifier data.");
      }

      if ((name == null) || (address == null) ||
          (port == 0) || (protocolVersion == 0))
      {
         throw new IllegalArgumentException("Invalid gateway identifier data.");
      }
   }

   /**
    * Return the gateway server name.
    *
    * @return the gateway server name.
    */
   public String getName()
   {
      return name;
   }

   /**
    * Return the gateway server address.
    *
    * @return the gateway server address.
    */
   public InetAddress getAddress()
   {
      return address;
   }

   /**
    * Return the gateway server port.
    *
    * @return the gateway server port.
    */
   public int getPort()
   {
      return port;
   }

   /**
    * Return the gateway server protocol version.
    *
    * @return the gateway server protocol version.
    */
   public int getProtocolVersion()
   {
      return protocolVersion;
   }

   /*
    * @see java.lang.Object#toString()
    */
   public String toString()
   {
      return ("Name: " + name + ", Address: " + address.getHostAddress() +
              ":" + port + ", Version: " + protocolVersion);
   }
}
