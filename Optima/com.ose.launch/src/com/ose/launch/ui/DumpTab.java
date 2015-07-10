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

import java.io.File;
import com.ose.launch.IOSELaunchConfigurationConstants;
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
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

public class DumpTab extends AbstractLaunchConfigurationTab
{
   protected static final String EMPTY_STRING = "";

   protected Button fStartDumpMonitorButton;
   protected Group fDumpMonitorGroup;
   protected Label fFileLabel;
   protected Text fFileText;
   protected Button fBrowseButton;

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

      fStartDumpMonitorButton = new Button(comp, SWT.CHECK);
      fStartDumpMonitorButton.setText("Start new dump monitor");
      fStartDumpMonitorButton.setEnabled(getLaunchConfigurationDialog().
         getMode().equals(ILaunchManager.DEBUG_MODE));
      fStartDumpMonitorButton.addSelectionListener(new SelectionAdapter()
      {
         public void widgetSelected(SelectionEvent e)
         {
            handleStartDumpMonitorButtonSelected(
               fStartDumpMonitorButton.getSelection());
         }
      });

      fDumpMonitorGroup = new Group(comp, SWT.SHADOW_NONE);
      fDumpMonitorGroup.setText("Dump Monitor Options");
      gd = new GridData(GridData.FILL_BOTH);
      fDumpMonitorGroup.setLayoutData(gd);
      groupLayout = new GridLayout(3, false);
      fDumpMonitorGroup.setLayout(groupLayout);

      createVerticalSpacer(fDumpMonitorGroup, 3);

      fFileLabel = new Label(fDumpMonitorGroup, SWT.NONE);
      fFileLabel.setText("Dump File:");
      fFileText = new Text(fDumpMonitorGroup, SWT.SINGLE | SWT.BORDER);
      gd = new GridData(GridData.FILL_HORIZONTAL);
      fFileText.setLayoutData(gd);
      fFileText.addModifyListener(new ModifyListener()
      {
         public void modifyText(ModifyEvent e)
         {
            updateLaunchConfigurationDialog();
         }
      });
      fBrowseButton = new Button(fDumpMonitorGroup, SWT.PUSH);
      fBrowseButton.setText("Browse...");
      fBrowseButton.addSelectionListener(new BrowseButtonHandler());

      LaunchUIPlugin.setDialogShell(parent.getShell());
   }

   protected void handleStartDumpMonitorButtonSelected(boolean selected)
   {
      fDumpMonitorGroup.setEnabled(selected);
      fFileLabel.setEnabled(selected);
      fFileText.setEnabled(selected);
      fBrowseButton.setEnabled(selected);
      updateLaunchConfigurationDialog();
   }

   public void setDefaults(ILaunchConfigurationWorkingCopy config)
   {
      config.setAttribute(
         IOSELaunchConfigurationConstants.ATTR_DUMP_MONITOR_MANAGED, true);
      config.setAttribute(
         IOSELaunchConfigurationConstants.ATTR_DUMP_FILE, EMPTY_STRING);
   }

   public void initializeFrom(ILaunchConfiguration config)
   {
      updateStartDumpMonitorFromConfig(config);
      updateFileFromConfig(config);
   }

   protected void updateStartDumpMonitorFromConfig(ILaunchConfiguration config)
   {
      boolean start = true;

      if (getLaunchConfigurationDialog().getMode().equals(ILaunchManager.DEBUG_MODE))
      {
         try
         {
            start = config.getAttribute(
               IOSELaunchConfigurationConstants.ATTR_DUMP_MONITOR_MANAGED, true);
         }
         catch (CoreException e)
         {
            LaunchUIPlugin.log(e);
         }
      }

      fStartDumpMonitorButton.setSelection(start);
      // On Windows, selection event listeners are not called
      // when a button is programatically selected. We have to
      // call a button's selection event handler explicitly.
      handleStartDumpMonitorButtonSelected(start);
   }

   protected void updateFileFromConfig(ILaunchConfiguration config)
   {
      String file = EMPTY_STRING;

      try
      {
         file = config.getAttribute(
            IOSELaunchConfigurationConstants.ATTR_DUMP_FILE, EMPTY_STRING);
      }
      catch (CoreException e)
      {
         LaunchUIPlugin.log(e);
      }
      fFileText.setText(file);
   }

   public void activated(ILaunchConfigurationWorkingCopy config)
   {
   }

   public void performApply(ILaunchConfigurationWorkingCopy config)
   {
      config.setAttribute(
         IOSELaunchConfigurationConstants.ATTR_DUMP_MONITOR_MANAGED,
         fStartDumpMonitorButton.getSelection());
      config.setAttribute(
         IOSELaunchConfigurationConstants.ATTR_DUMP_FILE,
         fFileText.getText().trim());
   }

   public boolean isValid(ILaunchConfiguration config)
   {
      String text;

      setErrorMessage(null);
      setMessage(null);

      if (!fStartDumpMonitorButton.getSelection())
      {
         return true;
      }

      text = fFileText.getText().trim();
      if (text.length() == 0)
      {
         setErrorMessage("Dump file not specified");
         return false;
      }
      else if (!(new File(text)).isFile())
      {
         setErrorMessage("Dump file does not exist");
         return false;
      }

      return true;
   }

   public String getName()
   {
      return "Dump";
   }

   public Image getImage()
   {
      return LaunchImages.get(LaunchImages.IMG_VIEW_DUMP_TAB);
   }

   public String getId()
   {
      return "com.ose.launch.ui.dumpTab";
   }

   class BrowseButtonHandler extends SelectionAdapter
   {
      public void widgetSelected(SelectionEvent e)
      {
         FileDialog dialog = new FileDialog(getShell(), SWT.OPEN);
         dialog.setFileName(fFileText.getText());
         String text = dialog.open();
         if (text != null)
         {
            fFileText.setText(text);
         }
      }
   }
}
