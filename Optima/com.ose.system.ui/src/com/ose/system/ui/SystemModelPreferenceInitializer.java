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

package com.ose.system.ui;

import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;
import org.eclipse.jface.preference.IPreferenceStore;

public class SystemModelPreferenceInitializer extends AbstractPreferenceInitializer
{
   private static final String DEFAULT_GATE_BROADCAST_PORT = "21768";
   private static final String DEFAULT_GATE_BROADCAST_TIMEOUT = "4000";
   private static final String DEFAULT_GATE_PING_TIMEOUT = "2000";
   private static final String DEFAULT_AUTOMATIC_UPDATE = "10000";

   public void initializeDefaultPreferences()
   {
      IPreferenceStore prefs =
         SystemBrowserPlugin.getDefault().getPreferenceStore();

      prefs.setDefault(SystemModelPreferencePage.PREF_GATE_BROADCAST_PORT,
            DEFAULT_GATE_BROADCAST_PORT);
      prefs.setDefault(SystemModelPreferencePage.PREF_GATE_BROADCAST_TIMEOUT,
            DEFAULT_GATE_BROADCAST_TIMEOUT);
      prefs.setDefault(SystemModelPreferencePage.PREF_GATE_PING_TIMEOUT,
            DEFAULT_GATE_PING_TIMEOUT);
      prefs.setDefault(SystemModelPreferencePage.PREF_AUTOMATIC_UPDATE,
            DEFAULT_AUTOMATIC_UPDATE);
   }
}
