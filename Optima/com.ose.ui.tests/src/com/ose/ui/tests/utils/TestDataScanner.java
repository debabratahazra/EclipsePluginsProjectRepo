/* COPYRIGHT-ENEA-SRC-R2 *
 **************************************************************************
 * Copyright (C) 2011 by Enea Software AB.
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

package com.ose.ui.tests.utils;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import org.eclipse.core.runtime.Platform;
import org.osgi.framework.Bundle;

/**
 * Utility class to perform some file reading and parsing operations.
 */
public class TestDataScanner
{
   /**
    * Returns String[] properties by reading and parsing the given file and
    * applying the filter and index conditions.
    *
    * @param fileName - Name of test data file found inside
    * com.ose.ui.tests.framework package.
    * @param propertyFilter
    * @param colIndex
    * @return
    * @throws Exception
    */
   public static List<String[]> getProperties(String fileName,
         String propertyFilter, int colIndex) throws Exception
   {
      Scanner scanner = null;
      try
      {
         List<String[]> data = new ArrayList<String[]>();
         String[] properties = null;
         InputStream in = getFileInputStream(fileName);
         if (in == null)
         {
            throw new IOException("File " + fileName + " not found.");
         }
         scanner = new Scanner(in);
         // We choose Unix style line ending LF as our sources reside on Linux
         // ClearCase machines.
         scanner.useDelimiter("\n");
         while (scanner.hasNext())
         {
            properties = parseLine(scanner.next(), propertyFilter, colIndex);
            if (properties != null)
            {
               data.add(properties);
            }
         }

         return data;
      }
      catch (Exception e)
      {
         throw e;
      }
      finally
      {
         if (scanner != null)
         {
            scanner.close();
         }
      }
   }

   /**
    * Returns a list of String[] properties matching the given filter, column
    * index conditions from a list of String[] properties.
    *
    * @param properties
    * @param propertyFilter
    * @param colIndex
    * @return
    */
   public static List<String[]> getProperties(List<String[]> properties,
         String propertyFilter, int colIndex)
   {
      List<String[]> data = new ArrayList<String[]>();

      if (propertyFilter == null)
      {
         return properties;
      }

      for (String[] propsArray : properties)
      {
         if (propertyFilter != null)
         {
            if (propsArray[colIndex].equalsIgnoreCase(propertyFilter) == true)
            {
               data.add(propsArray);
            }
         }
      }

      return data;
   }

   public static List<String[]> getProperties(List<String[]> properties,
         EntityFilter[] propertyFilters)
   {
      List<String[]> data = new ArrayList<String[]>();
      boolean flag = true;
      String value1;
      String value2;

      if (propertyFilters == null)
      {
         return properties;
      }

      for (String[] propsArray : properties)
      {
         flag = true;

         for (EntityFilter filter : propertyFilters)
         {
            if (filter.isRangeAvailable())
            {
               if (propsArray[filter.getIndex()].equals("N/A"))
               {
                  flag = false;
                  break;
               }

               value1 = filter.getValue1();
               value2 = filter.getValue2();

               if (value1.equals("") == false)
               {
                  if (value2.equals("") == false)
                  {
                     flag = flag
                           && (Long.parseLong(propsArray[filter.getIndex()]) >= Long
                                 .parseLong(value1) && (Long
                                 .parseLong(propsArray[filter.getIndex()]) < Long
                                 .parseLong(value2))) && filter.isExclude();
                  }
                  else
                  {
                     flag = flag
                           && (Long.parseLong(propsArray[filter.getIndex()]) >= Long
                                 .parseLong(value1)) && filter.isExclude();
                  }
               }
               else
               {
                  if (value2.equals("") == false)
                  {
                     flag = flag
                           && (Long.parseLong(propsArray[filter.getIndex()]) < Long
                                 .parseLong(value2)) && filter.isExclude();
                  }
               }
            }
            else
            {
               flag = flag
                     && (propsArray[filter.getIndex()].equalsIgnoreCase(filter
                           .getValue1())) && filter.isExclude();
            }
         }

         if (flag)
         {
            data.add(propsArray);
         }
      }

      return data;
   }

   private static String[] parseLine(String line, String propertyFilter,
         int colIndex)
   {
      String[] properties = null;
      properties = line.split("\\s*,\\s*");
      if (propertyFilter != null)
      {
         if (!properties[colIndex].equalsIgnoreCase(propertyFilter))
         {
            return null;
         }
      }
      return properties;
   }

   private static InputStream getFileInputStream(String path) throws IOException
   {
      InputStream in = null;

      Bundle bundle = Platform.getBundle("com.ose.ui.tests");
      if (bundle != null)
      {
         URL url = bundle.getEntry(path);
         if (url != null)
         {
            in = url.openStream();
         }
      }

      return in;
   }
}
