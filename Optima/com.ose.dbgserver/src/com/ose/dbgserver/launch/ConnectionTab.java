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

package com.ose.dbgserver.launch;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.debug.ui.AbstractLaunchConfigurationTab;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import com.ose.dbgserver.DebugServerPlugin;

class ConnectionTab extends AbstractLaunchConfigurationTab
{
   private final Image image;
   private Text addressText;
   private Text portText;

   ConnectionTab()
   {
      ImageDescriptor imageDesc = DebugServerPlugin.imageDescriptorFromPlugin(
            DebugServerPlugin.getUniqueIdentifier(),
            "icons/view16/connection_tab.gif");
      image = imageDesc.createImage();
   }

   public void createControl(Composite parent)
   {
      ModifyHandler modifyHandler;
      Composite comp;
      Label label;

      modifyHandler = new ModifyHandler();
      comp = new Composite(parent, SWT.NONE);
      comp.setLayoutData(new GridData(GridData.FILL_BOTH));
      comp.setLayout(new GridLayout(2, false));
      setControl(comp);

      createVerticalSpacer(comp, 2);

      label = new Label(comp, SWT.NONE);
      label.setText("Debug server address:");
      addressText = new Text(comp, SWT.SINGLE | SWT.BORDER);
      addressText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
      addressText.addModifyListener(modifyHandler);

      createVerticalSpacer(comp, 2);

      label = new Label(comp, SWT.NONE);
      label.setText("Debug server port:");
      portText = new Text(comp, SWT.SINGLE | SWT.BORDER);
      portText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
      portText.addModifyListener(modifyHandler);
   }

   public void dispose()
   {
      image.dispose();
   }

   public void setDefaults(ILaunchConfigurationWorkingCopy config)
   {
      config.setAttribute(ILaunchConfigurationConstants.ATTR_DEBUG_SERVER_ADDRESS,
            "");
      config.setAttribute(ILaunchConfigurationConstants.ATTR_DEBUG_SERVER_PORT,
            ILaunchConfigurationConstants.DEFAULT_VALUE_DEBUG_SERVER_PORT);
   }

   public void initializeFrom(ILaunchConfiguration config)
   {
      updateAddressFromConfig(config);
      updatePortFromConfig(config);
   }

   private void updateAddressFromConfig(ILaunchConfiguration config)
   {
      String address = "";

      try
      {
         address = config.getAttribute(
            ILaunchConfigurationConstants.ATTR_DEBUG_SERVER_ADDRESS, "");
      }
      catch (CoreException e)
      {
         DebugServerPlugin.log(e);
      }
      addressText.setText(address);
   }

   private void updatePortFromConfig(ILaunchConfiguration config)
   {
      int port = ILaunchConfigurationConstants.DEFAULT_VALUE_DEBUG_SERVER_PORT;

      try
      {
         port = config.getAttribute(
            ILaunchConfigurationConstants.ATTR_DEBUG_SERVER_PORT,
            ILaunchConfigurationConstants.DEFAULT_VALUE_DEBUG_SERVER_PORT);
      }
      catch (CoreException e)
      {
         DebugServerPlugin.log(e);
      }
      portText.setText(Integer.toString(port));
   }

   public void activated(ILaunchConfigurationWorkingCopy config)
   {
   }

   public void performApply(ILaunchConfigurationWorkingCopy config)
   {
      config.setAttribute(ILaunchConfigurationConstants.ATTR_DEBUG_SERVER_ADDRESS,
                          addressText.getText());
      try
      {
         config.setAttribute(ILaunchConfigurationConstants.ATTR_DEBUG_SERVER_PORT,
                             Integer.parseInt(portText.getText()));
      }
      catch (NumberFormatException e)
      {
         setErrorMessage("Invalid debug server port number");
      }
   }

   public boolean isValid(ILaunchConfiguration config)
   {
      String text;

      setErrorMessage(null);
      setMessage(null);

      text = addressText.getText().trim();
      if (text.length() == 0)
      {
         setErrorMessage("Debug server address not specified");
         return false;
      }

      text = portText.getText().trim();
      if (text.length() == 0)
      {
         setErrorMessage("Debug server port not specified");
         return false;
      }
      try
      {
         Integer.parseInt(text);
      }
      catch (NumberFormatException e)
      {
         setErrorMessage("Invalid debug server port number");
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
      return image;
   }

   public String getId()
   {
      return "com.ose.dbgserver.launch.connectionTab";
   }

   private class ModifyHandler implements ModifyListener
   {
      public void modifyText(ModifyEvent e)
      {
         updateLaunchConfigurationDialog();
      }
   }
}
