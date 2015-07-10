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

package com.ose.gateway.serial;

import gnu.io.CommPortIdentifier;
import gnu.io.NoSuchPortException;
import gnu.io.PortInUseException;
import gnu.io.SerialPort;
import gnu.io.UnsupportedCommOperationException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.channels.SocketChannel;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeoutException;
import com.ose.gateway.server.AbstractGatewayClient;
import com.ose.gateway.server.AbstractGatewayServer;

public class SerialGatewayServer extends AbstractGatewayServer
{
   private final SerialPort serialPort;

   private final OutputStream out;

   private final Object writeLock;

   private final Map<Integer, SerialGatewayClient> clientMap =
      Collections.synchronizedMap(new HashMap<Integer, SerialGatewayClient>());

   private int id;

   public SerialGatewayServer(String name,
                              int serverPort,
                              int broadcastPort,
                              String serialPortName,
                              int baudRate)
         throws IOException
   {
      super(name, serverPort, broadcastPort);
      serialPort = connect(serialPortName, baudRate);
      out = serialPort.getOutputStream();
      writeLock = new Object();
   }

   private SerialPort connect(String serialPortName, int baudRate)
      throws IOException
   {
      SerialPort serial = null;
      boolean success = false;

      try
      {
         CommPortIdentifier portId =
            CommPortIdentifier.getPortIdentifier(serialPortName);
         serial = (SerialPort)
            portId.open(SerialGatewayPlugin.getUniqueIdentifier(), 5000);
         serial.setSerialPortParams(baudRate,
                                    SerialPort.DATABITS_8,
                                    SerialPort.STOPBITS_1,
                                    SerialPort.PARITY_NONE);
         serial.setFlowControlMode(SerialPort.FLOWCONTROL_NONE);
         // FIXME: Should we use serial.setLowLatency()?

         ReaderThread readerThread = new ReaderThread(serial.getInputStream());
         readerThread.start();

         success = true;
         return serial;
      }
      catch (NoSuchPortException e)
      {
         throw new IOException("Invalid serial port " + serialPortName);
      }
      catch (PortInUseException e)
      {
         throw new IOException("Serial port " + serialPortName +
               " is already in use by another application");
      }
      catch (UnsupportedCommOperationException e)
      {
         throw new IOException("Unsupported serial port operation: " +
               e.getMessage());
      }
      finally
      {
         if (!success && (serial != null))
         {
            serial.close();
         }
      }
   }

   @Override
   protected int getMaxSignalSize()
   {
      return 0x20000;
   }

   @Override
   protected AbstractGatewayClient createClient(SocketChannel channel)
   {
      int id = createId();
      SerialGatewayClient client = new SerialGatewayClient(this, channel, id);
      clientMap.put(id, client);
      return client;
   }

   @Override
   protected void closeServerHook()
   {
      if (serialPort != null)
      {
         serialPort.close();
      }
      clientMap.clear();
   }

   void writeData(byte[] data) throws IOException
   {
      synchronized (writeLock)
      {
         out.write(data);
         out.flush();
      }
   }

   void removeClient(SerialGatewayClient client)
   {
      clientMap.remove(client.getId());
   }

   private int createId()
   {
      id++;
      id = PacketFactory.adjustId(id);
      return id;
   }

   private class ReaderThread extends Thread
   {
      private final InputStream in;

      ReaderThread(InputStream in)
      {
         super("Serial Gateway Reader");
         this.in = in;
      }

      public void run()
      {
         while (isOpen())
         {
            try
            {
               Packet packet = readPacket();
               dispatchPacket(packet);
            }
            catch (Exception e)
            {
               log(e);
            }
         }
      }

      private void waitAvailable(int numBytes, long endTime) throws IOException,
            TimeoutException
      {
         while (isOpen() && (in.available() < numBytes))
         {
            if (System.currentTimeMillis() > endTime)
            {
               throw new TimeoutException();
            }
            try
            {
               sleep(1);
            } catch (InterruptedException ignore) {}
         }
      }

      private byte readUnquotedByte(long endTime) throws IOException,
            TimeoutException
      {
         int b;

         waitAvailable(1, endTime);
         b = in.read();
         if (b < 0)
         {
            throw new IOException("Unexpected read() result");
         }
         if (b == Packet.START_BYTE)
         {
            waitAvailable(1, endTime);
            b = in.read();
            if (b != Packet.START_BYTE)
            {
               throw new IOException("Protocol error, unexpected start byte");
            }
         }

         return (byte) b;
      }

      private Packet readPacket() throws IOException
      {
         Packet p = new Packet();

         try
         {
            long endTime;
            byte[] data = new byte[2];
            byte[] id = new byte[4];
            byte[] payload;

            do
            {
               waitAvailable(1, Long.MAX_VALUE);
               if (in.read(data, 0, 1) != 1)
               {
                  throw new IOException("Unexpected read() result");
               }
            } while (data[0] != Packet.START_BYTE);

            do
            {
               endTime = System.currentTimeMillis() + Packet.PACKET_READ_TMO;
               waitAvailable(1, endTime);
               if (in.read(data, 1, 1) != 1)
               {
                  throw new IOException("Unexpected read() result");
               }
            } while (data[1] == Packet.START_BYTE);

            p.setType(data[1]);
            p.setPayloadSize(readUnquotedByte(endTime));
            p.setChecksum(readUnquotedByte(endTime));

            id[0] = readUnquotedByte(endTime);
            id[1] = readUnquotedByte(endTime);
            id[2] = readUnquotedByte(endTime);
            id[3] = readUnquotedByte(endTime);
            p.setId(id);

            payload = new byte[p.getPayloadSize() & 0xFF];
            for (int i = 0; i < payload.length; i++)
            {
               payload[i] = readUnquotedByte(endTime);
            }
            p.setPayload(payload);

            p.checkChecksum();
         }
         catch (TimeoutException e)
         {
            p.setTimeoutStatus();
         }

         return p;
      }

      private void dispatchPacket(Packet packet)
      {
         SerialGatewayClient client = clientMap.get(packet.getId());
         if (client != null)
         {
            client.notifyPacketAvailable(packet);
         }
         else
         {
            throw new RuntimeException("No Gateway client found for ID "
                  + packet.getId());
         }
      }
   }
}
