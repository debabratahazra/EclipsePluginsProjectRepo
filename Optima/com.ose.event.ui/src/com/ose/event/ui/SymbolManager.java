/* COPYRIGHT-ENEA-SRC-R2 *
 **************************************************************************
 * Copyright (C) 2007 by Enea Software AB.
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

package com.ose.event.ui;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.PropertyChangeEvent;
import com.ose.event.ui.EventPlugin;
import com.ose.sigdb.Signal;
import com.ose.sigdb.io.BinaryFile;
import com.ose.sigdb.io.ObjectFile;
import com.ose.sigdb.table.DeltaFormatReader;
import com.ose.sigdb.table.SignalDescription;
import com.ose.sigdb.table.SignalDescriptionTable;
import com.ose.sigdb.table.SignalTableReader;

public class SymbolManager implements IPropertyChangeListener
{
   private final List signalTables = new ArrayList();

   private final List eventTables = new ArrayList();

   private final Object signalLock = new Object();

   private final Object eventLock = new Object();

   SymbolManager()
   {
      populateList("signal", signalTables);
      populateList("event", eventTables);
   }

   public void propertyChange(PropertyChangeEvent event)
   {
      String property = event.getProperty();

      if (property.startsWith("signal_ref_"))
      {
         synchronized (signalLock)
         {
            signalTables.clear();
            populateList("signal", signalTables);
         }
      }
      else if (property.startsWith("event_ref_"))
      {
         synchronized (eventLock)
         {
            eventTables.clear();
            populateList("event", eventTables);
         }
      }
   }

   public Signal getSignal(int number, byte[] data, int byteOrder)
   {
      Signal signal = null;

      if (data == null)
      {
         throw new IllegalArgumentException();
      }

      synchronized (signalLock)
      {
         for (Iterator i = signalTables.iterator(); i.hasNext();)
         {
            SignalDescriptionTable table = (SignalDescriptionTable) i.next();
            SignalDescription description = table.getDescription(number);
            if (description != null)
            {
               signal = new Signal(description, data, byteOrder);
               break;
            }
         }
      }

      return signal;
   }

   public Signal getEvent(int number, byte[] data, int byteOrder)
   {
      Signal signal = null;

      if (data == null)
      {
         throw new IllegalArgumentException();
      }

      synchronized (eventLock)
      {
         for (Iterator i = eventTables.iterator(); i.hasNext();)
         {
            SignalDescriptionTable table = (SignalDescriptionTable) i.next();
            SignalDescription description = table.getDescription(number);
            if (description != null)
            {
               signal = new Signal(description, data, byteOrder);
               break;
            }
         }
      }

      return signal;
   }

   /**
    * Populate a list with object types according to the stored preferences.
    *
    * @param type  the type to populate the list with (signal or event).
    * @param list  the list to populate.
    */
   private static void populateList(String type, List list)
   {
      IPreferenceStore prefs;
      int size;

      if ((list == null) || (type == null) ||
          (!type.equals("signal") && !type.equals("event")))
      {
         throw new IllegalArgumentException();
      }

      prefs = EventPlugin.getDefault().getPreferenceStore();
      size = prefs.getInt(type + "_ref_size");

      for (int i = 0; i < size; i++)
      {
         String path = prefs.getString(type + "_ref_" + i + "_path");
         boolean active = prefs.getBoolean(type + "_ref_" + i + "_active");

         if ((path.length() > 0) && active)
         {
            try
            {
               ObjectFile objectFile;
               SignalTableReader signalTableReader;
               String suffix;
               SignalDescriptionTable signalTable;

               objectFile = new BinaryFile();
               objectFile.init(new File(path));
               signalTableReader = new DeltaFormatReader(objectFile);
               suffix = (type.equals("event") ? "evt" : null);
               signalTable = signalTableReader.read(suffix);
               if (signalTable != null)
               {
                  list.add(signalTable);
               }
            }
            catch (IOException e)
            {
               EventPlugin.errorDialog(
                     null,
                     "Error while reading " + type + " database file " + path,
                     e);
            }
         }
      }
   }
}
