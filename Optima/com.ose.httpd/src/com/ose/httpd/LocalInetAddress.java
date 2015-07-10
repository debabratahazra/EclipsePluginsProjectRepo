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

import java.io.IOException;
import java.net.DatagramSocket;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.Inet4Address;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Enumeration;

/**
 * Utility class for retrieving the IP address of the local host.
 */
public final class LocalInetAddress
{
   /**
    * Returns an InetAddress, representing the address of the local host, that
    * is reachable from the given destination address.
    *
    * @param destination  the destination address from which the returned local
    * host address should be reachable
    * @return the address of the local host
    * @throws UnknownHostException if no address for the local host could be
    * found
    */
   public static InetAddress getLocalHost(InetAddress destination)
      throws UnknownHostException
   {
      InetAddress localHost = null;

      if (destination == null)
      {
         throw new IllegalArgumentException();
      }

      // First try the InetAddress.isReachable() version.
      localHost = getReachableLocalHost1(destination);

      // If that fails then try the datagram socket version.
      if (localHost == null)
      {
         localHost = getReachableLocalHost2(destination);
      }

      if (localHost == null)
      {
         throw new UnknownHostException();
      }

      return localHost;
   }

   /**
    * Returns the first non-loopback address found for this host that is
    * reachable from the given destination address or null if none found.
    * <p>
    * The local address lookup is done by calling
    * InetAddress.isReachable(NetworkInterface ...) on the destination address
    * for each network interface of the host and, if that succeeds, retrieving
    * the local address of that network interface.
    *
    * @param destination  the destination address from which the returned local
    * host address should be reachable
    * @return the first non-loopback address found for this host that is
    * reachable from the given destination address or null if none found
    */
   private static InetAddress getReachableLocalHost1(InetAddress destination)
   {
      Enumeration netfaces = null;

      try
      {
         netfaces = NetworkInterface.getNetworkInterfaces();
      } catch (SocketException ignore) {}

      if (netfaces == null)
      {
         // None found.
         return null;
      }

      while (netfaces.hasMoreElements())
      {
         NetworkInterface netface = (NetworkInterface) netfaces.nextElement();
         try
         {
            if (destination.isReachable(netface, 0, 2000))
            {
               for (Enumeration addresses = netface.getInetAddresses(); addresses.hasMoreElements();)
               {
                  InetAddress address = (InetAddress) addresses.nextElement();
                  // Return the first IPv4/IPv6-compatible non-loopback
                  // address for this network interface.
                  if (!address.isLoopbackAddress())
                  {
                     if ((destination instanceof Inet6Address) &&
                         (address instanceof Inet6Address))
                     {
                        return address;
                     }
                     else if ((destination instanceof Inet4Address) &&
                              (address instanceof Inet4Address))
                     {
                        return address;
                     }
                  }
               }
            }
         } catch (IOException ignore) {}
      }

      // None found.
      return null;
   }

   /**
    * Returns an address for this host that is reachable from the given
    * destination address or null if none found.
    * <p>
    * The local address lookup is done by connecting a datagram socket to the
    * destination address and, if that succeeds, retrieving the local address
    * that the socket was bound to.
    *
    * @param destination  the destination address from which the returned local
    * host address should be reachable
    * @return an address for this host that is reachable from the given
    * destination address or null if none found
    */
   private static InetAddress getReachableLocalHost2(InetAddress destination)
   {
      DatagramSocket socket = null;
      InetAddress localHost = null;

      try
      {
         socket = new DatagramSocket();
         socket.connect(destination, 0);
         localHost = socket.getLocalAddress();
      }
      catch (Throwable ignore) {}
      finally
      {
         if (socket != null)
         {
            socket.disconnect();
            socket.close();
         }
      }

      return localHost;
   }

   public static String getHostAddress(InetAddress address)
   {
      String hostAddress = address.getHostAddress();
      if (address instanceof Inet6Address)
      {
         hostAddress = "[" + hostAddress + "]";
      }
      return hostAddress;
   }
}
