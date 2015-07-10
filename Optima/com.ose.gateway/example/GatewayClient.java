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

import java.io.IOException;
import com.ose.gateway.AttachSignal;
import com.ose.gateway.Gateway;
import com.ose.gateway.GatewayIdentifier;
import com.ose.gateway.Signal;
import com.ose.gateway.SignalRegistry;
import com.ose.gateway.SignalSelect;
import com.ose.gateway.UnregisteredSignal;

/**
 * The GatewayClient class is the main class of a simple Java application
 * demonstrating how to use the OSE Gateway API for Java.
 * <p>
 * In this simple example all signal communication is done in the Java main
 * thread. In a real application, the signal communication would more likely
 * be done in the run() method of one or more dedicated Java threads.
 *
 * @author steby
 */
public class GatewayClient
{
   /**
    * A method demonstrating how to use the OSE Gateway API for Java.
    */
   public static void test() throws IOException
   {
      GatewayIdentifier[] gids;
      Gateway gateway;
      SignalRegistry registry;
      Signal signal;
      int pid;
      int attref;

      /*
       * Search for all available gateway servers.
       */
      System.out.println("Searching for available gateway servers...");
      gids = Gateway.find(2000);
      if (gids.length == 0)
      {
         System.err.println("No gateway server found. Terminating.");
         return;
      }
      else
      {
         System.out.println("Gateway servers found:");
         for (int i = 0; i < gids.length; i++)
         {
            System.out.println(gids[i]);
         }
      }

      /*
       * Connect to the first gateway server found.
       */
      System.out.println("Connecting to first gateway server found.");
      gateway = new Gateway(gids[0]);
      gateway.connect("my_gateway_client");

      /*
       * Create a signal registry and add the AttachSignal and
       * HeapInterfaceReplySignal classes to it. These are the
       * signals we are interested to receive.
       */
      registry = new SignalRegistry();
      registry.add(AttachSignal.class);
      registry.add(HeapInterfaceReplySignal.class);

      /*
       * Hunt for the native OSE process named "ose_heapd", i.e. the
       * native OSE process we want to communicate with.
       */
      System.out.println("Hunting for 'ose_heapd' process...");
      pid = gateway.hunt("ose_heapd");

      /*
       * Attach a signal to the native OSE process in
       * order to be notified if it dies unexpectedly.
       */
      System.out.println("Attaching to 'ose_heapd' process.");
      attref = gateway.attach(pid);

      /*
       * Send an HeapInterfaceRequestSignal to the native OSE process.
       */
      System.out.println("Sending an HeapInterfaceRequestSignal.");
      gateway.send(pid, new HeapInterfaceRequestSignal());

      /*
       * Receive an HeapInterfaceReplySignal from the native OSE process.
       */
      System.out.println("Waiting to receive an HeapInterfaceReplySignal...");
      signal = gateway.receive(registry);
      System.out.println("Sender: " + signal.getSender());
      System.out.println("Addressee: " + signal.getAddressee());
      switch (signal.getSigNo())
      {
      case HeapInterfaceReplySignal.SIG_NO:
         HeapInterfaceReplySignal replySignal = (HeapInterfaceReplySignal) signal;
         System.out.println("Received an HeapInterfaceReplySignal with contents:");
         System.out.println("status: " + replySignal.status);
         System.out.println("whatStr: " + replySignal.whatStr);
         System.out.println("biosHandle: " + replySignal.biosHandle);
         for (int i = 0; i < replySignal.sigs.length; i++)
         {
            System.out.println("sigs[" + i + "]: " + replySignal.sigs[i]);
         }
         break;
      case AttachSignal.SIG_NO:
         System.err.println("Native OSE process killed. Terminating.");
         return;
      default:
         System.err.println("Received an unknown signal. Terminating.");
         gateway.detach(attref);
         return;
      }

      /*
       * Erroneously try to receive a second HeapInterfaceReplySignal from the
       * native OSE process.
       */
      System.out.println("Erroneously wait to receive one more HeapInterfaceReplySignal...");
      signal = gateway.receive(registry, SignalSelect.ANY_SIGNAL, 5000);
      if (signal == null)
      {
         System.out.println("Ok, no reply within 5 seconds.");
      }
      else if (signal instanceof HeapInterfaceReplySignal)
      {
         System.err.println("Error, received an unexpected HeapInterfaceReplySignal.");
      }
      else if (signal instanceof AttachSignal)
      {
         System.err.println("Native OSE process killed. Terminating.");
         return;
      }
      else if (signal instanceof UnregisteredSignal)
      {
         System.err.println("Received an unregistered signal. Terminating.");
      }
      else
      {
         System.err.println("Received an unknown signal. Terminating.");
      }

      /*
       * Note that before exiting the application we do only detach the
       * attached signal from the native OSE process if we have not already
       * received it.
       */
      System.out.println("Detaching from 'ose_heapd' process.");
      gateway.detach(attref);

      /*
       * Disconnect from the gateway server.
       */
      System.out.println("Disconnecting from gateway server.");
      gateway.disconnect();
   }

   /**
    * The main method of the Java GatewayClient application.
    *
    * @param  args  The command-line arguments.
    */
   public static void main(String[] args)
   {
      try
      {
         test();
      }
      catch (IOException e)
      {
         e.printStackTrace();
      }
   }
}
