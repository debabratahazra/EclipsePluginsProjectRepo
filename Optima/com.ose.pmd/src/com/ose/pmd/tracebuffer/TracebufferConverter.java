/* COPYRIGHT-ENEA-SRC-R1 *
 **************************************************************************
 * Copyright (C) 2012-2013 by Enea Software AB.
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

package com.ose.pmd.tracebuffer;

import java.io.DataOutputStream;
import java.io.IOException;
import com.ose.gateway.Signal;
import com.ose.gateway.SignalInputStream;
import com.ose.pmd.dump.DumpFile;
import com.ose.pmd.dump.SignalBlock;
import com.ose.pmd.dump.IffException;
import com.ose.pmd.tracebuffer.frame.TraceframeFormatter;
import com.ose.system.service.tracepoint.signal.MonitorTracepointGetTracebufferEndmark;
import com.ose.system.service.tracepoint.signal.MonitorTracepointGetTracebufferRequest;



public class TracebufferConverter {

   TracebufferReader tracebufferReader;
   TracebufferWriter tracebufferWriter;
   

   public void convertTracebuffer(String input_file, String output_file)
   {
      ExperimentData experimentData = new ExperimentData();
      DataOutputStream outputStream = null;
      SignalInputStream in = null;
      
      experimentData = tracebufferReader.parseInputFile(input_file);
      if (experimentData != null)
      {
         tracebufferWriter.setBigEndian(tracebufferReader.isBigEndian());
         outputStream = tracebufferWriter.generateHeaderAndTextSections(output_file, experimentData);
         
         // For the binary section read GetTracebufferReply signals one by one and write them
         if (outputStream != null)
         {
            DumpFile dumpFile = tracebufferReader.getDumpFile(input_file);
            // Get the MonitorTracepointGetTracebufferRequest signal block
            SignalBlock tracebufferFramesBlock = tracebufferReader.getTracebufferFramesBlock(dumpFile);
            
            
            // read, convert and write tracebuffer entries from the signal input stream 
            try
            {
               in = new SignalInputStream(tracebufferFramesBlock.getInputStream(), tracebufferReader.isBigEndian());
               Signal signal = tracebufferReader.readSignal(in);
               boolean endmark = false;
               
               if (signal instanceof MonitorTracepointGetTracebufferRequest)
               {
                  do
                  {
                     signal = tracebufferReader.readSignal(in);
                     
                     if (signal instanceof TraceframeFormatter)
                     {
                        tracebufferWriter.writeTracebufferEntrie(outputStream, (TraceframeFormatter)signal);
                     }
                     else if (signal instanceof MonitorTracepointGetTracebufferEndmark)
                     {
                        endmark = true;
                     }
                     else
                     {
                        System.err.println("Invalid reply signal in MonitorTracepointGetTracebufferRequest signal block");
                     }
                     
                  } while ((in.available() > 0) && !endmark);
               }
               else
               {
                  System.err.println("Invalid request signal in MonitorTracepointGetTracebufferRequest signal block");
               }
            }
            catch (IffException e)
            {
               System.err.println("Invalid MonitorTracepointGetTracebufferRequest signal block");
               e.printStackTrace();
            }
            catch (IOException e)
            {
               e.printStackTrace();
            }
            finally
            {
               if (in != null)
               {
                  try
                  {
                     in.close();
                  }
                  catch (IOException ignore)
                  {
                  }
               }
            }
            
            
            if (dumpFile != null && dumpFile.isOpen())
            {
               dumpFile.close();
            }
            
            // Write end of data
            tracebufferWriter.writeEndOfData(outputStream);
         }
         else
         {
            System.err.println("Error, generateHeaderAndTextSection failed to return the outputStream");
            return;
         }
      }
      else
      {
         System.err.println("Error parsing the input file");
         return;
      }
       
      System.out.println("Conversion successful. Experiment written to : " + output_file);
   }
    
   
   public static void main(String[] args)
   {
      TracebufferConverter converter = new TracebufferConverter();
      
      boolean help = false;
      boolean incorrect_args = false;
      boolean incorrect_arch = false;
      String input_file = "";
      String output_file = "";
      String arch = "";

      converter.tracebufferReader = new TracebufferReader();
      converter.tracebufferWriter = new TracebufferWriter();

      
      // There has to be at least one argument
      if (args.length == 0)
      {
         incorrect_args = true;
      }
      else 
      {
         // Check for help
         if (args[0].compareTo("-help") == 0 && args.length != 1)
         {
            incorrect_args = true;
         }
         else if (args[0].compareTo("-help") == 0)
         {
            help = true;
         }      
         // First argument is the input string
         else
         {
            input_file = new String(args[0]);
         }
      }
            
      // Parse the optional arguments
      for (int i = 1; i < args.length && !incorrect_args && !help; i++)
      {
         // Ouput file argument
         if (args[i].compareTo("-o") == 0)
         {
            i++;
            if (i < args.length)
            {
               output_file = new String(args[i]);
            }
            else
            {
               incorrect_args = true;
            }
         }
         // Architecture argument
         else if (args[i].compareTo("-a") == 0)
         {
            i++;
            if (i < args.length)
            {
               arch = new String(args[i]);
               incorrect_arch = true;
               for (int j = 0; j < TracebufferUtil.architectures.length && incorrect_arch; j++)
               {
                  if (arch.compareTo(TracebufferUtil.architectures[j]) == 0)
                  {
                     incorrect_arch = false;
                  }
               }
            }
            else
            {
               incorrect_args = true;
            }
         }
      }
      
      
      // Check for incorrect arguments or help before running converter.
      if (incorrect_args)
      {
         System.err.println("Error parsing command arguments");
         System.err.println("Usage: pmd2gdb <input_file> [-o <output_file] [-a E500|E500MC|E600]");
      }
      else if (incorrect_arch)
      {
         System.err.println("Error parsing command arguments");
         System.err.println("Architecture must be one of: E500, E500MC or E600");
         System.err.println("Usage: pmd2gdb <input_file> [-o <output_file] [-a E500|E500MC|E600]");
      }
      else if (help)
      {
         System.out.println("Usage: pmd2gdb <input_file> [-o <output_file] [-a E500|E500MC|E600]");
      }
      else
      {
         if (output_file.isEmpty())
         {
            output_file = new String(input_file.concat(".out"));
         }
         if (!arch.isEmpty())
         {
            converter.tracebufferWriter.setArchitecture(arch);
         }
         converter.convertTracebuffer(input_file, output_file);
      }
   }
}
