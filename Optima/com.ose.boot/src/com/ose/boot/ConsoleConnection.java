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

package com.ose.boot;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * This class provides handling of a target connection to a console.
 * The connection may be any I/O stream such as a serial port or a socket.
 */
public class ConsoleConnection
{
   private static final int DEFAULT_TIMEOUT = 10000;

   private OutputStream out;
   private ConsoleReader reader;
   private String prompt;

   public ConsoleConnection(InputStream in, OutputStream out)
   {
      this.out = out;
      this.reader = new ConsoleReader(in, 128);
      this.prompt = ">";
   }

   public void setPrompt(String prompt)
   {
      if ((prompt == null) || (prompt.length() == 0))
      {
         throw new IllegalArgumentException("Invalid console prompt.");
      }
      this.prompt = prompt;
   }

   public String waitForPrompt() throws IOException
   {
      return waitForPrompt(DEFAULT_TIMEOUT);
   }

   public String waitForPrompt(int tmo) throws IOException
   {
      out.write(13); // CR
      out.write(10); // LF
      out.flush();
      return expect("\n" + prompt, tmo);
   }

   public void sendCommand(String command) throws IOException
   {
      sendCommand(command, DEFAULT_TIMEOUT);
   }

   public void sendCommand(String command, int tmo) throws IOException
   {
      out.write(command.getBytes());
      out.write(13); // CR
      out.write(10); // LF
      out.flush();
      expect(command, tmo);
   }

   /**
    * Read from the target until the specified string appears in the output.
    *
    * @param expectation  The expected string to wait for.
    * @param tmo          The timeout in milliseconds.
    * @return  A string of what has been read from the target prior to
    *          the expected string appeared.
    * @exception TimeoutException        If the operation timed out.
    * @exception InterruptedIOException  If the operation was interrupted by
    *                                    another thread.
    * @exception IOException             If some other I/O error occurs.
    */
   public String expect(String expectation, int tmo) throws IOException
   {
      long timeout;
      long t0;
      String head;
      StringBuffer tail;
      int length;

      timeout = tmo;
      t0 = System.currentTimeMillis();
      head = "";
      tail = new StringBuffer(256);
      length = expectation.length();

      if (length == 0)
      {
         return "";
      }

      do
      {
         int c;

         if (timeout < 0)
         {
            throw new TimeoutException("Console operation timed out.");
         }
         c = reader.read((int) timeout);
         if (c < 0)
         {
            throw new IOException("Unexpected end of console input stream.");
         }
         head += (char) c;
         if (head.length() > length)
         {
            tail.append(head.substring(0, 1));
            head = head.substring(1);
         }
         timeout = tmo - (System.currentTimeMillis() - t0);
      } while (!head.equals(expectation));

      return tail.toString();
   }

   public void close()
   {
      reader.close();
      try
      {
         out.close();
      }
      catch (IOException e) {}
   }
}
