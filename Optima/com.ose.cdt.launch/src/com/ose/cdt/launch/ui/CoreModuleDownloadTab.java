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

package com.ose.cdt.launch.ui;

import com.ose.cdt.launch.internal.ui.LaunchUIPlugin;
import com.ose.launch.IOSELaunchConfigurationConstants;
import com.ose.launch.ui.LaunchImages;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.debug.core.ILaunchManager;
import org.eclipse.debug.ui.AbstractLaunchConfigurationTab;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

public class CoreModuleDownloadTab extends AbstractLaunchConfigurationTab
{
   protected static final String DEFAULT_PROMPT = "polo>";

   protected static final String[] BOOT_LOADERS =
      {IOSELaunchConfigurationConstants.VALUE_BOOT_POLO};

   protected Button fDownloadButton;
   protected Group fDownloadGroup;
   protected Label fBootLoaderLabel;
   protected Combo fBootLoaderCombo;
   protected Label fPromptLabel;
   protected Text fPromptText;

   public void createControl(Composite parent)
   {
      Composite comp;
      GridLayout topLayout;
      GridLayout groupLayout;
      GridData gd;

      comp = new Composite(parent, SWT.NONE);
      topLayout = new GridLayout();
      comp.setLayout(topLayout);
      setControl(comp);

      createVerticalSpacer(comp, 1);

      fDownloadButton = new Button(comp, SWT.CHECK);
      fDownloadButton.setText("Download");
      fDownloadButton.setEnabled(
         getLaunchConfigurationDialog().getMode().equals(ILaunchManager.DEBUG_MODE));
      fDownloadButton.addSelectionListener(new SelectionAdapter()
      {
         public void widgetSelected(SelectionEvent e)
         {
            handleDownloadButtonSelected(fDownloadButton.getSelection());
         }
      });

      fDownloadGroup = new Group(comp, SWT.SHADOW_NONE);
      fDownloadGroup.setText("Download Options");
      gd = new GridData(GridData.FILL_BOTH);
      fDownloadGroup.setLayoutData(gd);
      groupLayout = new GridLayout(2, false);
      fDownloadGroup.setLayout(groupLayout);

      createVerticalSpacer(fDownloadGroup, 2);

      fBootLoaderLabel = new Label(fDownloadGroup, SWT.NONE);
      fBootLoaderLabel.setText("Boot loader:");
      fBootLoaderCombo = new Combo(fDownloadGroup, SWT.DROP_DOWN | SWT.READ_ONLY);
      fBootLoaderCombo.setItems(BOOT_LOADERS);
      fBootLoaderCombo.setVisibleItemCount(fBootLoaderCombo.getItemCount());
      gd = new GridData(GridData.HORIZONTAL_ALIGN_FILL);
      fBootLoaderCombo.setLayoutData(gd);

      createVerticalSpacer(fDownloadGroup, 2);

      fPromptLabel = new Label(fDownloadGroup, SWT.NONE);
      fPromptLabel.setText("Prompt:");
      fPromptText = new Text(fDownloadGroup, SWT.SINGLE | SWT.BORDER);
      gd = new GridData(GridData.HORIZONTAL_ALIGN_FILL);
      fPromptText.setLayoutData(gd);
      fPromptText.addModifyListener(new ModifyListener()
      {
         public void modifyText(ModifyEvent e)
         {
            updateLaunchConfigurationDialog();
         }
      });

      LaunchUIPlugin.setDialogShell(parent.getShell());
   }

   protected void handleDownloadButtonSelected(boolean selected)
   {
      fDownloadGroup.setEnabled(selected);
      fBootLoaderLabel.setEnabled(selected);
      fBootLoaderCombo.setEnabled(selected);
      fPromptLabel.setEnabled(selected);
      fPromptText.setEnabled(selected);
      updateLaunchConfigurationDialog();
   }

   public void setDefaults(ILaunchConfigurationWorkingCopy config)
   {
      config.setAttribute(IOSELaunchConfigurationConstants.ATTR_BOOT_DOWNLOAD, true);
      config.setAttribute(IOSELaunchConfigurationConstants.ATTR_BOOT_LOADER,
                          IOSELaunchConfigurationConstants.VALUE_BOOT_POLO);
      config.setAttribute(IOSELaunchConfigurationConstants.ATTR_BOOT_PROMPT,
                          DEFAULT_PROMPT);
   }

   public void initializeFrom(ILaunchConfiguration config)
   {
      updateDownloadFromConfig(config);
      updateBootLoaderFromConfig(config);
      updatePromptFromConfig(config);
   }

   protected void updateDownloadFromConfig(ILaunchConfiguration config)
   {
      boolean download = true;

      if (getLaunchConfigurationDialog().getMode().equals(ILaunchManager.DEBUG_MODE))
      {
         try
         {
            download = config.getAttribute(
               IOSELaunchConfigurationConstants.ATTR_BOOT_DOWNLOAD, true);
         }
         catch (CoreException e)
         {
            LaunchUIPlugin.log(e);
         }
      }

      fDownloadButton.setSelection(download);
      // On Windows, selection event listeners are not called
      // when a button is programatically selected. We have to
      // call a button's selection event handler explicitly.
      handleDownloadButtonSelected(download);
   }

   protected void updateBootLoaderFromConfig(ILaunchConfiguration config)
   {
      String bootLoader = IOSELaunchConfigurationConstants.VALUE_BOOT_POLO;

      try
      {
         bootLoader = config.getAttribute(
            IOSELaunchConfigurationConstants.ATTR_BOOT_LOADER,
            IOSELaunchConfigurationConstants.VALUE_BOOT_POLO);
      }
      catch (CoreException e)
      {
         LaunchUIPlugin.log(e);
      }
      fBootLoaderCombo.setText(bootLoader);
   }

   protected void updatePromptFromConfig(ILaunchConfiguration config)
   {
      String prompt = DEFAULT_PROMPT;

      try
      {
         prompt = config.getAttribute(
            IOSELaunchConfigurationConstants.ATTR_BOOT_PROMPT, DEFAULT_PROMPT);
      }
      catch (CoreException e)
      {
         LaunchUIPlugin.log(e);
      }
      fPromptText.setText(prompt);
   }

   public void activated(ILaunchConfigurationWorkingCopy config)
   {
   }

   public void performApply(ILaunchConfigurationWorkingCopy config)
   {
      if (getLaunchConfigurationDialog().getMode().equals(ILaunchManager.DEBUG_MODE))
      {
         config.setAttribute(IOSELaunchConfigurationConstants.ATTR_BOOT_DOWNLOAD,
                             fDownloadButton.getSelection());
      }
      config.setAttribute(IOSELaunchConfigurationConstants.ATTR_BOOT_LOADER,
                          fBootLoaderCombo.getText());
      config.setAttribute(IOSELaunchConfigurationConstants.ATTR_BOOT_PROMPT,
                          fPromptText.getText());
   }

   public boolean isValid(ILaunchConfiguration config)
   {
      String text;

      setErrorMessage(null);
      setMessage(null);

      if (!fDownloadButton.getSelection())
      {
         return true;
      }

      text = fPromptText.getText().trim();
      if (text.length() == 0)
      {
         setErrorMessage("Prompt not specified");
         return false;
      }

      return true;
   }

   public String getName()
   {
      return "Download";
   }

   public Image getImage()
   {
      return LaunchImages.get(LaunchImages.IMG_VIEW_DOWNLOAD_TAB);
   }

   public String getId()
   {
      return "com.ose.cdt.launch.ui.coreModuleDownloadTab";
   }
}
