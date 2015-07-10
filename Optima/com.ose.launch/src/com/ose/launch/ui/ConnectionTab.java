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

package com.ose.launch.ui;

import com.ose.launch.IOSELaunchConfigurationConstants;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.debug.ui.AbstractLaunchConfigurationTab;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

public class ConnectionTab extends AbstractLaunchConfigurationTab
{
   protected static final String EMPTY_STRING = "";

   protected Label fAddressLabel;
   protected Text fAddressText;
   protected Label fPortLabel;
   protected Text fPortText;
   protected Label fHuntPathLabel;
   protected Text fHuntPathText;

   public void createControl(Composite parent)
   {
      Composite comp;
      GridLayout layout;
      GridData gd;

      comp = new Composite(parent, SWT.NONE);
      gd = new GridData(GridData.FILL_BOTH);
      comp.setLayoutData(gd);
      layout = new GridLayout(2, false);
      comp.setLayout(layout);
      setControl(comp);

      createVerticalSpacer(comp, 2);

      fAddressLabel = new Label(comp, SWT.NONE);
      fAddressLabel.setText("Gate address:");
      fAddressText = new Text(comp, SWT.SINGLE | SWT.BORDER);
      gd = new GridData(GridData.FILL_HORIZONTAL);
      fAddressText.setLayoutData(gd);
      fAddressText.addModifyListener(new ModifyListener()
      {
         public void modifyText(ModifyEvent e)
         {
            updateLaunchConfigurationDialog();
         }
      });

      createVerticalSpacer(comp, 2);

      fPortLabel = new Label(comp, SWT.NONE);
      fPortLabel.setText("Gate port:");
      fPortText = new Text(comp, SWT.SINGLE | SWT.BORDER);
      gd = new GridData(GridData.FILL_HORIZONTAL);
      fPortText.setLayoutData(gd);
      fPortText.addModifyListener(new ModifyListener()
      {
         public void modifyText(ModifyEvent e)
         {
            updateLaunchConfigurationDialog();
         }
      });

      createVerticalSpacer(comp, 2);

      fHuntPathLabel = new Label(comp, SWT.NONE);
      fHuntPathLabel.setText("Target hunt path:");
      fHuntPathText = new Text(comp, SWT.SINGLE | SWT.BORDER);
      gd = new GridData(GridData.FILL_HORIZONTAL);
      fHuntPathText.setLayoutData(gd);
      fHuntPathText.addModifyListener(new ModifyListener()
      {
         public void modifyText(ModifyEvent e)
         {
            updateLaunchConfigurationDialog();
         }
      });

      LaunchUIPlugin.setDialogShell(parent.getShell());
   }

   public void setDefaults(ILaunchConfigurationWorkingCopy config)
   {
      config.setAttribute(IOSELaunchConfigurationConstants.ATTR_GATE_ADDRESS,
                          EMPTY_STRING);
      config.setAttribute(IOSELaunchConfigurationConstants.ATTR_GATE_PORT,
                          IOSELaunchConfigurationConstants.DEFAULT_VALUE_GATE_PORT);
      config.setAttribute(IOSELaunchConfigurationConstants.ATTR_TARGET_HUNT_PATH,
                          EMPTY_STRING);
   }

   public void initializeFrom(ILaunchConfiguration config)
   {
      updateAddressFromConfig(config);
      updatePortFromConfig(config);
      updateHuntPathFromConfig(config);
   }

   protected void updateAddressFromConfig(ILaunchConfiguration config)
   {
      String address = EMPTY_STRING;

      try
      {
         address = config.getAttribute(
            IOSELaunchConfigurationConstants.ATTR_GATE_ADDRESS, EMPTY_STRING);
      }
      catch (CoreException e)
      {
         LaunchUIPlugin.log(e);
      }
      fAddressText.setText(address);
   }

   protected void updatePortFromConfig(ILaunchConfiguration config)
   {
      int port = IOSELaunchConfigurationConstants.DEFAULT_VALUE_GATE_PORT;

      try
      {
         port = config.getAttribute(
            IOSELaunchConfigurationConstants.ATTR_GATE_PORT,
            IOSELaunchConfigurationConstants.DEFAULT_VALUE_GATE_PORT);
      }
      catch (CoreException e)
      {
         LaunchUIPlugin.log(e);
      }
      fPortText.setText(Integer.toString(port));
   }

   protected void updateHuntPathFromConfig(ILaunchConfiguration config)
   {
      String huntPath = EMPTY_STRING;

      try
      {
         huntPath = config.getAttribute(
            IOSELaunchConfigurationConstants.ATTR_TARGET_HUNT_PATH, EMPTY_STRING);
      }
      catch (CoreException e)
      {
         LaunchUIPlugin.log(e);
      }
      fHuntPathText.setText(huntPath);
   }

   public void activated(ILaunchConfigurationWorkingCopy config)
   {
   }

   public void performApply(ILaunchConfigurationWorkingCopy config)
   {
      config.setAttribute(IOSELaunchConfigurationConstants.ATTR_GATE_ADDRESS,
                          fAddressText.getText());
      try
      {
         config.setAttribute(IOSELaunchConfigurationConstants.ATTR_GATE_PORT,
                             Integer.parseInt(fPortText.getText()));
      }
      catch (NumberFormatException e)
      {
         setErrorMessage("Invalid gate port number");
      }
      config.setAttribute(IOSELaunchConfigurationConstants.ATTR_TARGET_HUNT_PATH,
                          fHuntPathText.getText());
   }

   public boolean isValid(ILaunchConfiguration config)
   {
      String text;

      setErrorMessage(null);
      setMessage(null);

      text = fAddressText.getText().trim();
      if (text.length() == 0)
      {
         setErrorMessage("Gate address not specified");
         return false;
      }

      text = fPortText.getText().trim();
      if (text.length() == 0)
      {
         setErrorMessage("Gate port not specified");
         return false;
      }
      try
      {
         Integer.parseInt(text);
      }
      catch (NumberFormatException e)
      {
         setErrorMessage("Invalid gate port number");
         return false;
      }

      return true;
   }

   public String getName()
   {
      return "Connection";
   }

   public Image getImage()
   {
      return LaunchImages.get(LaunchImages.IMG_VIEW_CONNECTION_TAB);
   }

   public String getId()
   {
      return "com.ose.launch.ui.connectionTab";
   }
}
