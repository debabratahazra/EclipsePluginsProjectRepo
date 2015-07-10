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

package com.ose.fmd.freescale;

import java.io.File;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.debug.ui.AbstractLaunchConfigurationTab;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

public class OseTab extends AbstractLaunchConfigurationTab
{
   private final Image image;

   private Text fileText;

   public OseTab()
   {
      ImageDescriptor imageDesc = DebuggerPlugin.imageDescriptorFromPlugin(
         DebuggerPlugin.getUniqueIdentifier(), "icons/view16/ose_tab.gif");
      image = imageDesc.createImage();
   }

   public void createControl(Composite parent)
   {
      Composite comp;
      Label fileLabel;
      Button browseButton;

      comp = new Composite(parent, SWT.NONE);
      comp.setLayout(new GridLayout(3, false));
      setControl(comp);

      createVerticalSpacer(comp, 3);

      fileLabel = new Label(comp, SWT.NONE);
      fileLabel.setText("OSE Kernel Awareness Library:");

      fileText = new Text(comp, SWT.SINGLE | SWT.BORDER);
      fileText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
      fileText.addModifyListener(new FileTextModifyHandler());

      browseButton = new Button(comp, SWT.PUSH);
      browseButton.setText("Browse...");
      browseButton.addSelectionListener(new BrowseButtonHandler());
   }

   public void dispose()
   {
      image.dispose();
   }

   public void setDefaults(ILaunchConfigurationWorkingCopy config)
   {
      config.setAttribute(DebuggerPlugin.ATTR_KERNEL_AWARENESS_LIBRARY, "");
   }

   public void initializeFrom(ILaunchConfiguration config)
   {
      String file = "";
      try
      {
         file = config.getAttribute(DebuggerPlugin.ATTR_KERNEL_AWARENESS_LIBRARY, "");
      }
      catch (CoreException e)
      {
         DebuggerPlugin.log(e);
      }
      fileText.setText(file);
   }

   public void activated(ILaunchConfigurationWorkingCopy config)
   {
   }

   public void performApply(ILaunchConfigurationWorkingCopy config)
   {
      config.setAttribute(DebuggerPlugin.ATTR_KERNEL_AWARENESS_LIBRARY,
                          fileText.getText().trim());
   }

   public boolean isValid(ILaunchConfiguration config)
   {
      setErrorMessage(null);
      setMessage(null);

      String text = fileText.getText().trim();
      if ((text.length() > 0) && !(new File(text)).isFile())
      {
         setErrorMessage("Kernel awareness library does not exist");
         return false;
      }
      else
      {
         return true;
      }
   }

   public String getName()
   {
      return "OSE";
   }

   public Image getImage()
   {
      return image;
   }

   public String getId()
   {
      return "com.ose.fmd.freescale.oseTab";
   }

   private class FileTextModifyHandler implements ModifyListener
   {
      public void modifyText(ModifyEvent e)
      {
         updateLaunchConfigurationDialog();
      }
   }

   private class BrowseButtonHandler extends SelectionAdapter
   {
      public void widgetSelected(SelectionEvent e)
      {
         FileDialog dialog = new FileDialog(getShell(), SWT.OPEN);
         dialog.setFileName(fileText.getText());
         String text = dialog.open();
         if (text != null)
         {
            fileText.setText(text);
         }
      }
   }
}
