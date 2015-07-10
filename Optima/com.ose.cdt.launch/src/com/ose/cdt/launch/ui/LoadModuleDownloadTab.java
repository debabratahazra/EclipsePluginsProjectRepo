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

import java.io.File;
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
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

public class LoadModuleDownloadTab extends AbstractLaunchConfigurationTab
{
   protected static final String EMPTY_STRING = "";

   protected Button fDownloadButton;
   protected Group fDownloadGroup;
   protected Label fFileMsgLabel;
   protected Label fFileLabel;
   protected Text fFileText;
   protected Button fFileBrowseButton;
   protected Label fHttpServerPortMsgLabel;
   protected Label fHttpServerPortLabel;
   protected Text fHttpServerPortText;
   protected Label fHttpVmHuntPathMsgLabel;
   protected Label fHttpVmHuntPathLabel;
   protected Text fHttpVmHuntPathText;
   protected Label fExecutionUnitMsgLabel;
   protected Label fExecutionUnitLabel;
   protected Text fExecutionUnitText;
   protected Button fAbsoluteButton;

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
      groupLayout = new GridLayout(3, false);
      fDownloadGroup.setLayout(groupLayout);

      createVerticalSpacer(fDownloadGroup, 3);

      fFileMsgLabel = new Label(fDownloadGroup, SWT.WRAP);
      fFileMsgLabel.setText(
            "By default, the load module file specified in the Main tab will " +
            "be downloaded. Optionally, you can specify another flavor of " +
            "that file here. For example, one without debug information to " +
            "reduce the download time.");
      gd = new GridData(GridData.FILL_HORIZONTAL);
      gd.horizontalSpan = 3;
      gd.widthHint = 200;
      fFileMsgLabel.setLayoutData(gd);

      fFileLabel = new Label(fDownloadGroup, SWT.NONE);
      fFileLabel.setText("Load Module:");
      fFileText = new Text(fDownloadGroup, SWT.SINGLE | SWT.BORDER);
      gd = new GridData(GridData.FILL_HORIZONTAL);
      fFileText.setLayoutData(gd);
      fFileText.addModifyListener(new ModifyListener()
      {
         public void modifyText(ModifyEvent e)
         {
            updateLaunchConfigurationDialog();
         }
      });
      fFileBrowseButton = new Button(fDownloadGroup, SWT.PUSH);
      fFileBrowseButton.setText("Browse...");
      fFileBrowseButton.addSelectionListener(new BrowseButtonHandler());

      createVerticalSpacer(fDownloadGroup, 3);

      fHttpServerPortMsgLabel = new Label(fDownloadGroup, SWT.WRAP);
      fHttpServerPortMsgLabel.setText(
            "The load module is loaded from a HTTP server on your host " +
            "using the specified port (0 means any free port).");
      gd = new GridData(GridData.FILL_HORIZONTAL);
      gd.horizontalSpan = 3;
      gd.widthHint = 200;
      fHttpServerPortMsgLabel.setLayoutData(gd);

      fHttpServerPortLabel = new Label(fDownloadGroup, SWT.NONE);
      fHttpServerPortLabel.setText("HTTP Server Port:");
      fHttpServerPortText = new Text(fDownloadGroup, SWT.SINGLE | SWT.BORDER);
      gd = new GridData(GridData.FILL_HORIZONTAL);
      gd.horizontalSpan = 2;
      fHttpServerPortText.setLayoutData(gd);
      fHttpServerPortText.addModifyListener(new ModifyListener()
      {
         public void modifyText(ModifyEvent e)
         {
            updateLaunchConfigurationDialog();
         }
      });

      createVerticalSpacer(fDownloadGroup, 3);

      fHttpVmHuntPathMsgLabel = new Label(fDownloadGroup, SWT.WRAP);
      fHttpVmHuntPathMsgLabel.setText(
            "If your target has no HTTP volume manager, you must specify a " +
            "hunt path from your target to another, reachable target that " +
            "has a HTTP volume manager with access to your host network.");
      gd = new GridData(GridData.FILL_HORIZONTAL);
      gd.horizontalSpan = 3;
      gd.widthHint = 200;
      fHttpVmHuntPathMsgLabel.setLayoutData(gd);

      fHttpVmHuntPathLabel = new Label(fDownloadGroup, SWT.NONE);
      fHttpVmHuntPathLabel.setText("HTTP Volume Manager Hunt Path:");
      fHttpVmHuntPathText = new Text(fDownloadGroup, SWT.SINGLE | SWT.BORDER);
      gd = new GridData(GridData.FILL_HORIZONTAL);
      gd.horizontalSpan = 2;
      fHttpVmHuntPathText.setLayoutData(gd);
      fHttpVmHuntPathText.addModifyListener(new ModifyListener()
      {
         public void modifyText(ModifyEvent e)
         {
            updateLaunchConfigurationDialog();
         }
      });

      createVerticalSpacer(fDownloadGroup, 3);

      fExecutionUnitMsgLabel = new Label(fDownloadGroup, SWT.WRAP);
      fExecutionUnitMsgLabel.setText(
            "For multi-core systems, the execution unit on which the program " +
            "will start on.");
      gd = new GridData(GridData.FILL_HORIZONTAL);
      gd.horizontalSpan = 3;
      gd.widthHint = 200;
      fExecutionUnitMsgLabel.setLayoutData(gd);

      fExecutionUnitLabel = new Label(fDownloadGroup, SWT.NONE);
      fExecutionUnitLabel.setText("Execution Unit:");
      fExecutionUnitText = new Text(fDownloadGroup, SWT.SINGLE | SWT.BORDER);
      gd = new GridData(GridData.FILL_HORIZONTAL);
      gd.horizontalSpan = 2;
      fExecutionUnitText.setLayoutData(gd);
      fExecutionUnitText.addModifyListener(new ModifyListener()
      {
         public void modifyText(ModifyEvent e)
         {
            updateLaunchConfigurationDialog();
         }
      });

      createVerticalSpacer(fDownloadGroup, 3);

      fAbsoluteButton = new Button(fDownloadGroup, SWT.CHECK);
      fAbsoluteButton.setText("Absolute Linked");
      gd = new GridData();
      gd.horizontalSpan = 3;
      fAbsoluteButton.setLayoutData(gd);
      fAbsoluteButton.addSelectionListener(new SelectionAdapter()
      {
         public void widgetSelected(SelectionEvent e)
         {
            updateLaunchConfigurationDialog();
         }
      });

      LaunchUIPlugin.setDialogShell(parent.getShell());
   }

   protected void handleDownloadButtonSelected(boolean selected)
   {
      fDownloadGroup.setEnabled(selected);
      fFileMsgLabel.setEnabled(selected);
      fFileLabel.setEnabled(selected);
      fFileText.setEnabled(selected);
      fFileBrowseButton.setEnabled(selected);
      fHttpServerPortMsgLabel.setEnabled(selected);
      fHttpServerPortLabel.setEnabled(selected);
      fHttpServerPortText.setEnabled(selected);
      fHttpVmHuntPathMsgLabel.setEnabled(selected);
      fHttpVmHuntPathLabel.setEnabled(selected);
      fHttpVmHuntPathText.setEnabled(selected);
      fExecutionUnitMsgLabel.setEnabled(selected);
      fExecutionUnitLabel.setEnabled(selected);
      fExecutionUnitText.setEnabled(selected);
      fAbsoluteButton.setEnabled(selected);
      updateLaunchConfigurationDialog();
   }

   public void setDefaults(ILaunchConfigurationWorkingCopy config)
   {
      config.setAttribute(IOSELaunchConfigurationConstants.ATTR_LM_DOWNLOAD,
                          true);
      config.setAttribute(IOSELaunchConfigurationConstants.ATTR_LM_FILE,
                          EMPTY_STRING);
      config.setAttribute(IOSELaunchConfigurationConstants.ATTR_HTTP_SERVER_PORT,
                          0);
      config.setAttribute(IOSELaunchConfigurationConstants.ATTR_HTTP_VM_HUNT_PATH,
                          EMPTY_STRING);
      config.setAttribute(IOSELaunchConfigurationConstants.ATTR_LM_CPU_ID,
                          0);
      config.setAttribute(IOSELaunchConfigurationConstants.ATTR_LM_ABSOLUTE,
                          false);
   }

   public void initializeFrom(ILaunchConfiguration config)
   {
      updateDownloadFromConfig(config);
      updateFileFromConfig(config);
      updateHttpServerPortFromConfig(config);
      updateHttpVmHuntPathFromConfig(config);
      updateExecutionUnitFromConfig(config);
      updateAbsoluteFromConfig(config);
   }

   protected void updateDownloadFromConfig(ILaunchConfiguration config)
   {
      boolean download = true;

      if (getLaunchConfigurationDialog().getMode().equals(ILaunchManager.DEBUG_MODE))
      {
         try
         {
            download = config.getAttribute(
               IOSELaunchConfigurationConstants.ATTR_LM_DOWNLOAD, true);
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

   protected void updateFileFromConfig(ILaunchConfiguration config)
   {
      String file = EMPTY_STRING;

      try
      {
         file = config.getAttribute(
            IOSELaunchConfigurationConstants.ATTR_LM_FILE, EMPTY_STRING);
      }
      catch (CoreException e)
      {
         LaunchUIPlugin.log(e);
      }
      fFileText.setText(file);
   }

   protected void updateHttpServerPortFromConfig(ILaunchConfiguration config)
   {
      int httpServerPort = 0;

      try
      {
         httpServerPort = config.getAttribute(
            IOSELaunchConfigurationConstants.ATTR_HTTP_SERVER_PORT, 0);
      }
      catch (CoreException e)
      {
         LaunchUIPlugin.log(e);
      }
      fHttpServerPortText.setText(Integer.toString(httpServerPort));
   }

   protected void updateHttpVmHuntPathFromConfig(ILaunchConfiguration config)
   {
      String httpVmHuntPath = EMPTY_STRING;

      try
      {
         httpVmHuntPath = config.getAttribute(
            IOSELaunchConfigurationConstants.ATTR_HTTP_VM_HUNT_PATH, EMPTY_STRING);
      }
      catch (CoreException e)
      {
         LaunchUIPlugin.log(e);
      }
      fHttpVmHuntPathText.setText(httpVmHuntPath);
   }

   protected void updateExecutionUnitFromConfig(ILaunchConfiguration config)
   {
      int executionUnit = 0;

      try
      {
         executionUnit = config.getAttribute(
            IOSELaunchConfigurationConstants.ATTR_LM_CPU_ID, 0);
      }
      catch (CoreException e)
      {
         LaunchUIPlugin.log(e);
      }
      fExecutionUnitText.setText(Integer.toString(executionUnit));
   }

   protected void updateAbsoluteFromConfig(ILaunchConfiguration config)
   {
      boolean absolute = false;

      try
      {
         absolute = config.getAttribute(
            IOSELaunchConfigurationConstants.ATTR_LM_ABSOLUTE, false);
      }
      catch (CoreException e)
      {
         LaunchUIPlugin.log(e);
      }
      fAbsoluteButton.setSelection(absolute);
   }

   public void activated(ILaunchConfigurationWorkingCopy config)
   {
   }

   public void performApply(ILaunchConfigurationWorkingCopy config)
   {
      if (getLaunchConfigurationDialog().getMode().equals(ILaunchManager.DEBUG_MODE))
      {
         config.setAttribute(IOSELaunchConfigurationConstants.ATTR_LM_DOWNLOAD,
                             fDownloadButton.getSelection());
      }
      config.setAttribute(IOSELaunchConfigurationConstants.ATTR_LM_FILE,
                          fFileText.getText().trim());
      try
      {
         config.setAttribute(IOSELaunchConfigurationConstants.ATTR_HTTP_SERVER_PORT,
                             Integer.parseInt(fHttpServerPortText.getText().trim()));
      }
      catch (NumberFormatException e)
      {
         setErrorMessage("Invalid HTTP server port number");
      }
      config.setAttribute(IOSELaunchConfigurationConstants.ATTR_HTTP_VM_HUNT_PATH,
                          fHttpVmHuntPathText.getText().trim());
      try
      {
         String executionUnitStr = fExecutionUnitText.getText().trim();
         int executionUnit = (executionUnitStr.length() > 0) ?
               Integer.parseInt(executionUnitStr) : 0;
         config.setAttribute(IOSELaunchConfigurationConstants.ATTR_LM_CPU_ID,
                             executionUnit);
      }
      catch (NumberFormatException e)
      {
         setErrorMessage("Invalid execution unit");
      }
      config.setAttribute(IOSELaunchConfigurationConstants.ATTR_LM_ABSOLUTE,
                          fAbsoluteButton.getSelection());
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

      text = fFileText.getText().trim();
      if ((text.length() > 0) && !(new File(text)).isFile())
      {
         setErrorMessage("Load module does not exist");
         return false;
      }

      text = fHttpServerPortText.getText().trim();
      if (text.length() == 0)
      {
         setErrorMessage("HTTP server port not specified");
         return false;
      }
      try
      {
         Integer.parseInt(text);
      }
      catch (NumberFormatException e)
      {
         setErrorMessage("Invalid HTTP server port number");
         return false;
      }

      text = fExecutionUnitText.getText().trim();
      if (text.length() > 0)
      {
         try
         {
            Integer.parseInt(text);
         }
         catch (NumberFormatException e)
         {
            setErrorMessage("Invalid execution unit");
            return false;
         }
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
      return "com.ose.cdt.launch.ui.loadModuleDownloadTab";
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
