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

package com.ose.gateway.serial.launch;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;
import gnu.io.CommPortIdentifier;
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
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import com.ose.gateway.serial.SerialGatewayPlugin;

class ConnectionTab extends AbstractLaunchConfigurationTab
{
   private static final String[] SERIAL_PORTS;

   private static final String[] BAUD_RATES =
      {
         "2400", "4800", "9600", "14400", "19200",
         "38400", "57600", "115200", "128000"
      };

   private static final int DEFAULT_BAUD_RATE = 57600;

   private final Image image;
   private Combo serialPortCombo;
   private Combo baudRateCombo;

   static
   {
      SERIAL_PORTS = getSerialPortNames();
   }

   ConnectionTab()
   {
      ImageDescriptor imageDesc = SerialGatewayPlugin.imageDescriptorFromPlugin(
            SerialGatewayPlugin.getUniqueIdentifier(),
            "icons/view16/connection_tab.gif");
      image = imageDesc.createImage();
   }

   public void createControl(Composite parent)
   {
      Composite comp;
      Label label;

      comp = new Composite(parent, SWT.NONE);
      comp.setLayoutData(new GridData(GridData.FILL_BOTH));
      comp.setLayout(new GridLayout(2, false));
      setControl(comp);

      createVerticalSpacer(comp, 2);

      label = new Label(comp, SWT.NONE);
      label.setText("Serial port:");
      serialPortCombo = new Combo(comp, SWT.DROP_DOWN | SWT.READ_ONLY);
      serialPortCombo.setItems(SERIAL_PORTS);
      serialPortCombo.setVisibleItemCount(serialPortCombo.getItemCount());
      serialPortCombo.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

      createVerticalSpacer(comp, 2);

      label = new Label(comp, SWT.NONE);
      label.setText("Baud rate:");
      baudRateCombo = new Combo(comp, SWT.DROP_DOWN);
      baudRateCombo.setItems(BAUD_RATES);
      baudRateCombo.setVisibleItemCount(baudRateCombo.getItemCount());
      baudRateCombo.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
      baudRateCombo.addModifyListener(new ModifyHandler());
   }

   public void dispose()
   {
      image.dispose();
   }

   public void setDefaults(ILaunchConfigurationWorkingCopy config)
   {
      config.setAttribute(ILaunchConfigurationConstants.ATTR_SERIAL_PORT,
            ((SERIAL_PORTS.length > 0) ? SERIAL_PORTS[0] : ""));
      config.setAttribute(ILaunchConfigurationConstants.ATTR_BAUD_RATE,
            DEFAULT_BAUD_RATE);
   }

   public void initializeFrom(ILaunchConfiguration config)
   {
      updateSerialPortFromConfig(config);
      updateBaudRateFromConfig(config);
   }

   private void updateSerialPortFromConfig(ILaunchConfiguration config)
   {
      String serialPort = "";

      try
      {
         serialPort = config.getAttribute(
               ILaunchConfigurationConstants.ATTR_SERIAL_PORT, "");
      }
      catch (CoreException e)
      {
         SerialGatewayPlugin.log(e);
      }
      serialPortCombo.setText(serialPort);
   }

   private void updateBaudRateFromConfig(ILaunchConfiguration config)
   {
      int baudRate = DEFAULT_BAUD_RATE;

      try
      {
         baudRate = config.getAttribute(
               ILaunchConfigurationConstants.ATTR_BAUD_RATE,
               DEFAULT_BAUD_RATE);
      }
      catch (CoreException e)
      {
         SerialGatewayPlugin.log(e);
      }
      baudRateCombo.setText(Integer.toString(baudRate));
   }

   public void activated(ILaunchConfigurationWorkingCopy config)
   {
   }

   public void performApply(ILaunchConfigurationWorkingCopy config)
   {
      config.setAttribute(ILaunchConfigurationConstants.ATTR_SERIAL_PORT,
            serialPortCombo.getText());
      try
      {
         config.setAttribute(ILaunchConfigurationConstants.ATTR_BAUD_RATE,
               Integer.parseInt(baudRateCombo.getText()));
      }
      catch (NumberFormatException e)
      {
         setErrorMessage("Invalid baud rate");
      }
   }

   public boolean isValid(ILaunchConfiguration config)
   {
      String text;

      setErrorMessage(null);
      setMessage(null);

      text = serialPortCombo.getText();
      if (text.length() == 0)
      {
         setErrorMessage("Serial port not specified");
         return false;
      }

      text = baudRateCombo.getText().trim();
      if (text.length() == 0)
      {
         setErrorMessage("Baud rate not specified");
         return false;
      }
      try
      {
         Integer.parseInt(text);
      }
      catch (NumberFormatException e)
      {
         setErrorMessage("Invalid baud rate");
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
      return "com.ose.gateway.serial.launch.connectionTab";
   }

   private static String[] getSerialPortNames()
   {
      Enumeration portIds = CommPortIdentifier.getPortIdentifiers();
      List list = new ArrayList();

      while (portIds.hasMoreElements())
      {
         CommPortIdentifier portId = (CommPortIdentifier) portIds.nextElement();
         if (portId.getPortType() == CommPortIdentifier.PORT_SERIAL)
         {
            list.add(portId.getName());
         }
      }

      String[] array = (String[]) list.toArray(new String[0]);
      Arrays.sort(array);
      return array;
   }

   private class ModifyHandler implements ModifyListener
   {
      public void modifyText(ModifyEvent e)
      {
         updateLaunchConfigurationDialog();
      }
   }
}
