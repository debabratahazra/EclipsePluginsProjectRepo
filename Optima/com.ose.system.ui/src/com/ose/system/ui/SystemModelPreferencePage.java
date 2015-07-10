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

import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.preference.PreferencePage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;
import com.ose.system.ui.util.StringUtils;

public class SystemModelPreferencePage extends PreferencePage
   implements IWorkbenchPreferencePage
{
   public static final String PREF_GATE_BROADCAST_PORT = "pref_gate_broadcast_port";
   public static final String PREF_GATE_BROADCAST_TIMEOUT = "pref_gate_broadcast_timeout";
   public static final String PREF_GATE_PING_TIMEOUT = "pref_gate_ping_timeout";
   public static final String PREF_AUTOMATIC_UPDATE = "pref_automatic_update";

   private Text gateBroadcastPortText;
   private Text gateBroadcastTimeoutText;
   private Text gatePingTimeoutText;

   private Text automaticUpdateText;

   public SystemModelPreferencePage()
   {
      super();
      setPreferenceStore(SystemBrowserPlugin.getDefault().getPreferenceStore());
      setDescription("General Optima preferences:");
   }

   protected Control createContents(Composite parent)
   {
      Composite composite;
      GridLayout layout;
      GridData gd;
      Label label;
      Group group;
      IPreferenceStore prefs;
      ValidationHandler validationHandler;

      composite = new Composite(parent, SWT.NONE);
      layout = new GridLayout(1, false);
      layout.marginWidth = 0;
      layout.marginHeight = 0;
      composite.setLayout(layout);
      gd = new GridData(GridData.FILL_BOTH);
      composite.setLayoutData(gd);

      label = new Label(composite, SWT.NONE);
      gd = new GridData(GridData.FILL_HORIZONTAL);
      label.setLayoutData(gd);

      group = new Group(composite, SWT.SHADOW_NONE);
      group.setText("Gate Settings");
      group.setLayout(new GridLayout(2, false));
      gd = new GridData(GridData.FILL_HORIZONTAL);
      group.setLayoutData(gd);

      label = new Label(group, SWT.NONE);
      label.setText("Gate broadcast port:");
      gateBroadcastPortText = new Text(group, SWT.SINGLE | SWT.BORDER);
      gd = new GridData(GridData.FILL_HORIZONTAL);
      gateBroadcastPortText.setLayoutData(gd);

      label = new Label(group, SWT.NONE);
      label.setText("Gate broadcast timeout (ms):");
      gateBroadcastTimeoutText = new Text(group, SWT.SINGLE | SWT.BORDER);
      gd = new GridData(GridData.FILL_HORIZONTAL);
      gateBroadcastTimeoutText.setLayoutData(gd);

      label = new Label(group, SWT.NONE);
      label.setText("Gate ping timeout (ms):");
      gatePingTimeoutText = new Text(group, SWT.SINGLE | SWT.BORDER);
      gd = new GridData(GridData.FILL_HORIZONTAL);
      gatePingTimeoutText.setLayoutData(gd);

      group = new Group(composite, SWT.SHADOW_NONE);
      group.setText("Automatic Update Settings");
      group.setLayout(new GridLayout(2, false));
      gd = new GridData(GridData.FILL_HORIZONTAL);
      group.setLayoutData(gd);

      label = new Label(group, SWT.NONE);
      label.setText("Automatic update interval (ms):");
      automaticUpdateText = new Text(group, SWT.SINGLE | SWT.BORDER);
      gd = new GridData(GridData.FILL_HORIZONTAL);
      automaticUpdateText.setLayoutData(gd);

      prefs = getPreferenceStore();
      gateBroadcastPortText.setText(prefs.getString(PREF_GATE_BROADCAST_PORT));
      gateBroadcastTimeoutText.setText(prefs.getString(PREF_GATE_BROADCAST_TIMEOUT));
      gatePingTimeoutText.setText(prefs.getString(PREF_GATE_PING_TIMEOUT));
      automaticUpdateText.setText(prefs.getString(PREF_AUTOMATIC_UPDATE));

      validationHandler = new ValidationHandler();
      gateBroadcastPortText.addModifyListener(validationHandler);
      gateBroadcastTimeoutText.addModifyListener(validationHandler);
      gatePingTimeoutText.addModifyListener(validationHandler);
      automaticUpdateText.addModifyListener(validationHandler);

      return composite;
   }

   public void init(IWorkbench workbench)
   {
      // Nothing to do.
   }

   public boolean isValid()
   {
      String errorMessage = null;
      String text;

      text = gateBroadcastPortText.getText().trim();
      if (text.length() == 0)
      {
         errorMessage = "Gate broadcast port not specified";
      }
      else
      {
         try
         {
            int port = Integer.parseInt(text);
            if ((port < 0) || (port > 65535))
            {
               errorMessage = "Gate broadcast port must be between 0 and 65535";
            }
         }
         catch (NumberFormatException e)
         {
            errorMessage = "Invalid gate broadcast port";
         }
      }

      if (errorMessage == null)
      {
         text = gateBroadcastTimeoutText.getText().trim();
         if (text.length() == 0)
         {
            errorMessage = "Gate broadcast timeout not specified";
         }
         else
         {
            errorMessage = isValidU32(text, "Invalid gate broadcast timeout");
         }
      }

      if (errorMessage == null)
      {
         text = gatePingTimeoutText.getText().trim();
         if (text.length() == 0)
         {
            errorMessage = "Gate ping timeout not specified";
         }
         else
         {
            errorMessage = isValidU32(text, "Invalid gate ping timeout");
         }
      }

      if (errorMessage == null)
      {
         text = automaticUpdateText.getText().trim();
         if (text.length() == 0)
         {
            errorMessage = "Automatic update interval not specified";
         }
         else
         {
            errorMessage = isValidU32(text, "Invalid automatic update interval");
         }
      }

      setErrorMessage(errorMessage);
      return (errorMessage == null);
   }

   protected void performDefaults()
   {
      IPreferenceStore prefs = getPreferenceStore();
      gateBroadcastPortText.setText(
            prefs.getDefaultString(PREF_GATE_BROADCAST_PORT));
      gateBroadcastTimeoutText.setText(
            prefs.getDefaultString(PREF_GATE_BROADCAST_TIMEOUT));
      gatePingTimeoutText.setText(
            prefs.getDefaultString(PREF_GATE_PING_TIMEOUT));
      automaticUpdateText.setText(
            prefs.getDefaultString(PREF_AUTOMATIC_UPDATE));
      super.performDefaults();
   }

   public boolean performOk()
   {
      String gateBroadcastPortValue;
      String gateBroadcastTimeoutValue;
      String gatePingTimeoutValue;
      String automaticUpdateValue;
      IPreferenceStore prefs;

      gateBroadcastPortValue = gateBroadcastPortText.getText().trim();
      gateBroadcastTimeoutValue = gateBroadcastTimeoutText.getText().trim();
      gatePingTimeoutValue = gatePingTimeoutText.getText().trim();
      automaticUpdateValue = automaticUpdateText.getText().trim();

      prefs = getPreferenceStore();
      prefs.setValue(PREF_GATE_BROADCAST_PORT, gateBroadcastPortValue);
      prefs.setValue(PREF_GATE_BROADCAST_TIMEOUT, gateBroadcastTimeoutValue);
      prefs.setValue(PREF_GATE_PING_TIMEOUT, gatePingTimeoutValue);
      prefs.setValue(PREF_AUTOMATIC_UPDATE, automaticUpdateValue);
      SystemBrowserPlugin.getDefault().savePluginPreferences();
      return true;
   }

   private static String isValidU32(String text, String errorMessage)
   {
      String msg = null;

      if (text.trim().length() > 0)
      {
         try
         {
            StringUtils.parseU32(text);
         }
         catch (NumberFormatException e)
         {
            msg = errorMessage;
         }
      }
      return msg;
   }

   class ValidationHandler implements ModifyListener
   {
      public void modifyText(ModifyEvent event)
      {
         getContainer().updateButtons();
         updateApplyButton();
      }
   }
}
