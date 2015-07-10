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

package com.ose.cdt.debug.mi.internal.ui;

import java.io.File;
import java.net.URL;
import java.util.Observable;
import java.util.Observer;
import com.ose.cdt.debug.mi.core.GDBDebugger;
import com.ose.cdt.debug.mi.core.IOSEMILaunchConfigurationConstants;
import com.ose.launch.IOSELaunchConfigurationConstants;
import org.eclipse.cdt.core.CCorePlugin;
import org.eclipse.cdt.core.model.CoreModel;
import org.eclipse.cdt.core.model.IBinary;
import org.eclipse.cdt.core.model.ICElement;
import org.eclipse.cdt.core.model.ICProject;
import org.eclipse.cdt.debug.core.ICDTLaunchConfigurationConstants;
import org.eclipse.cdt.debug.mi.core.IMILaunchConfigurationConstants;
import org.eclipse.cdt.debug.ui.AbstractCDebuggerPage;
import org.eclipse.cdt.debug.ui.CDebugUIPlugin;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.Platform;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.osgi.service.datalocation.Location;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.swt.widgets.Text;

public class GDBDebuggerPage extends AbstractCDebuggerPage implements Observer
{
   protected static final String[] DEBUG_SCOPES =
   {
      IOSEMILaunchConfigurationConstants.VALUE_DEBUG_SCOPE_SEGMENT,
      IOSEMILaunchConfigurationConstants.VALUE_DEBUG_SCOPE_BLOCK,
      IOSEMILaunchConfigurationConstants.VALUE_DEBUG_SCOPE_PROCESS
   };

   protected static final String EMPTY_STRING = "";

   protected Text fGDBCommandText;
   protected Text fGDBInitText;
   protected Button fVerboseModeButton;
   protected Combo fScopeCombo;
   protected Text fSegmentText;
   protected Text fBlockText;
   protected Text fProcessText;

   protected String launchType = EMPTY_STRING;

   private boolean isInitializing = false;

   private String cpu;

   public void createControl(Composite parent)
   {
      Composite comp;
      TabFolder tabFolder;

      comp = new Composite(parent, SWT.NONE);
      comp.setLayoutData(new GridData(GridData.FILL_BOTH));
      comp.setLayout(new GridLayout());

      tabFolder = new TabFolder(comp, SWT.NONE);
      tabFolder.setLayoutData(new GridData(GridData.FILL_BOTH));
      createMainTab(tabFolder);
      createDebugTab(tabFolder);
      tabFolder.setSelection(0);

      setControl(parent);
   }

   protected void createMainTab(TabFolder tabFolder)
   {
      TabItem tabItem;
      Composite comp;
      Composite subComp;
      Label label;
      Button button;
      GridData gd;

      tabItem = new TabItem(tabFolder, SWT.NONE);
      tabItem.setText("Main");

      comp = new Composite(tabFolder, SWT.NONE);
      comp.setLayout(new GridLayout(1, false));
      gd = new GridData(GridData.FILL_BOTH);
      comp.setLayoutData(gd);
      tabItem.setControl(comp);

      subComp = new Composite(comp, SWT.NONE);
      subComp.setLayout(new GridLayout(4, false));
      gd = new GridData(GridData.FILL_HORIZONTAL);
      subComp.setLayoutData(gd);

      label = new Label(subComp, SWT.NONE);
      label.setText("GDB debugger:");
      gd = new GridData();
      label.setLayoutData(gd);

      fGDBCommandText = new Text(subComp, SWT.SINGLE | SWT.BORDER);
      gd = new GridData(GridData.FILL_HORIZONTAL);
      fGDBCommandText.setLayoutData(gd);
      fGDBCommandText.addModifyListener(new ModifyListener()
      {
         public void modifyText(ModifyEvent e)
         {
            if (!isInitializing())
            {
               updateLaunchConfigurationDialog();
            }
         }
      });

      button = new Button(subComp, SWT.PUSH);
      button.setText("&Find");
      button.addSelectionListener(new SelectionAdapter()
      {
         public void widgetSelected(SelectionEvent e)
         {
            handleButtonSelected();
            updateLaunchConfigurationDialog();
         }

         private void handleButtonSelected()
         {
            setInitializing(true);
            fGDBCommandText.setText(getGDB(cpu));
            setInitializing(false);
            updateLaunchConfigurationDialog();
         }
      });

      button = new Button(subComp, SWT.PUSH);
      button.setText("&Browse...");
      button.addSelectionListener(new SelectionAdapter()
      {
         public void widgetSelected(SelectionEvent e)
         {
            handleButtonSelected();
            updateLaunchConfigurationDialog();
         }

         private void handleButtonSelected()
         {
            FileDialog dialog;
            String gdbCommand;
            int lastSeparatorIndex;
            Location installLoc;
            String toolsRoot = null;
            String result;

            dialog = new FileDialog(getShell(), SWT.NONE);
            dialog.setText("GDB debugger");

            gdbCommand = fGDBCommandText.getText().trim();
            lastSeparatorIndex = gdbCommand.lastIndexOf(File.separator);

            installLoc = Platform.getInstallLocation();
            if (installLoc != null)
            {
               URL installURL = installLoc.getURL();
               if (installURL != null)
               {
                  File installDir = new File(installURL.getPath());
                  toolsRoot = installDir.getParent();
               }
            }

            if (lastSeparatorIndex != -1)
            {
               dialog.setFilterPath(gdbCommand.substring(0, lastSeparatorIndex));
            }
            else if (toolsRoot != null)
            {
               dialog.setFilterPath(toolsRoot);
            }

            result = dialog.open();
            if (result != null)
            {
               fGDBCommandText.setText(result);
            }
         }
      });

      label = new Label(subComp, SWT.NONE);
      label.setText("GDB command file:");
      gd = new GridData();
      label.setLayoutData(gd);

      fGDBInitText = new Text(subComp, SWT.SINGLE | SWT.BORDER);
      gd = new GridData(GridData.FILL_HORIZONTAL);
      gd.horizontalSpan = 2;
      fGDBInitText.setLayoutData(gd);
      fGDBInitText.addModifyListener(new ModifyListener()
      {
         public void modifyText(ModifyEvent e)
         {
            if (!isInitializing())
            {
               updateLaunchConfigurationDialog();
            }
         }
      });

      button = new Button(subComp, SWT.PUSH);
      button.setText("&Browse...");
      button.addSelectionListener(new SelectionAdapter()
      {
         public void widgetSelected(SelectionEvent e)
         {
            handleButtonSelected();
            updateLaunchConfigurationDialog();
         }

         private void handleButtonSelected()
         {
            FileDialog dialog;
            String gdbInit;
            int lastSeparatorIndex;
            String result;

            dialog = new FileDialog(getShell(), SWT.NONE);
            dialog.setText("GDB command file");
            gdbInit = fGDBInitText.getText().trim();
            lastSeparatorIndex = gdbInit.lastIndexOf(File.separator);
            if (lastSeparatorIndex != -1)
            {
               dialog.setFilterPath(gdbInit.substring(0, lastSeparatorIndex));
            }

            result = dialog.open();
            if (result != null)
            {
               fGDBInitText.setText(result);
            }
         }
      });

      label = new Label(subComp, SWT.WRAP);
      label.setText("(Warning: Some commands in this file may interfere " +
                    "with the startup operation of the debugger.)");
      gd = new GridData(GridData.FILL_HORIZONTAL);
      gd.horizontalSpan = 4;
      gd.widthHint = 200;
      label.setLayoutData(gd);

      createVerticalSpacer(subComp, 4);

      fVerboseModeButton = new Button(subComp, SWT.CHECK);
      fVerboseModeButton.setText("Verbose console mode");
      gd = new GridData(GridData.FILL_HORIZONTAL);
      gd.horizontalSpan = 4;
      fVerboseModeButton.setLayoutData(gd);
      fVerboseModeButton.addSelectionListener(new SelectionAdapter()
      {
         public void widgetSelected(SelectionEvent e)
         {
            if (!isInitializing())
            {
               updateLaunchConfigurationDialog();
            }
         }
      });
   }

   protected void createDebugTab(TabFolder tabFolder)
   {
      TabItem tabItem;
      Composite comp;
      Label label;
      GridData gd;

      tabItem = new TabItem(tabFolder, SWT.NONE);
      tabItem.setText("Debug");

      comp = new Composite(tabFolder, SWT.NONE);
      comp.setLayout(new GridLayout(2, false));
      gd = new GridData(GridData.FILL_BOTH);
      comp.setLayoutData(gd);
      tabItem.setControl(comp);

      label = new Label(comp, SWT.NONE);
      label.setText("Debug scope:");

      fScopeCombo = new Combo(comp, SWT.DROP_DOWN | SWT.READ_ONLY);
      fScopeCombo.setItems(DEBUG_SCOPES);
      fScopeCombo.setVisibleItemCount(fScopeCombo.getItemCount());
      gd = new GridData(GridData.FILL_HORIZONTAL);
      fScopeCombo.setLayoutData(gd);
      fScopeCombo.addSelectionListener(new SelectionAdapter()
      {
         public void widgetSelected(SelectionEvent e)
         {
            handleComboSelected();
            updateLaunchConfigurationDialog();
         }

         private void handleComboSelected()
         {
            int index = fScopeCombo.getSelectionIndex();

            if (index != -1)
            {
               String scope = fScopeCombo.getItem(index);
               fSegmentText.setEnabled(
                  !launchType.equals(IOSELaunchConfigurationConstants.ID_LAUNCH_CORE_MODULE) &&
                  scope.equals(IOSEMILaunchConfigurationConstants.VALUE_DEBUG_SCOPE_SEGMENT));
               fBlockText.setEnabled(
                  scope.equals(IOSEMILaunchConfigurationConstants.VALUE_DEBUG_SCOPE_BLOCK));
            }
         }
      });

      label = new Label(comp, SWT.NONE);
      label.setText("Segment:");

      fSegmentText = new Text(comp, SWT.SINGLE | SWT.BORDER);
      gd = new GridData(GridData.FILL_HORIZONTAL);
      fSegmentText.setLayoutData(gd);
      fSegmentText.addModifyListener(new ModifyListener()
      {
         public void modifyText(ModifyEvent e)
         {
            if (!isInitializing())
            {
               updateLaunchConfigurationDialog();
            }
         }
      });

      label = new Label(comp, SWT.NONE);
      label.setText("Block:");

      fBlockText = new Text(comp, SWT.SINGLE | SWT.BORDER);
      gd = new GridData(GridData.FILL_HORIZONTAL);
      fBlockText.setLayoutData(gd);
      fBlockText.addModifyListener(new ModifyListener()
      {
         public void modifyText(ModifyEvent e)
         {
            if (!isInitializing())
            {
               updateLaunchConfigurationDialog();
            }
         }
      });

      label = new Label(comp, SWT.NONE);
      label.setText("Process:");

      fProcessText = new Text(comp, SWT.SINGLE | SWT.BORDER);
      gd = new GridData(GridData.FILL_HORIZONTAL);
      fProcessText.setLayoutData(gd);
      fProcessText.addModifyListener(new ModifyListener()
      {
         public void modifyText(ModifyEvent e)
         {
            if (!isInitializing())
            {
               updateLaunchConfigurationDialog();
            }
         }
      });
   }

   public void update(Observable o, Object arg)
   {
      if (!isInitializing())
      {
         updateLaunchConfigurationDialog();
      }
   }

   public void setDefaults(ILaunchConfigurationWorkingCopy config)
   {
      String launchType = EMPTY_STRING;

      try
      {
         launchType = config.getType().getIdentifier();
      }
      catch (CoreException e)
      {
         CDebugUIPlugin.log(e);
      }

      config.setAttribute(IMILaunchConfigurationConstants.ATTR_DEBUG_NAME,
         getGDB(config));
      config.setAttribute(IMILaunchConfigurationConstants.ATTR_GDB_INIT,
         EMPTY_STRING);
      config.setAttribute(IMILaunchConfigurationConstants.ATTR_DEBUGGER_VERBOSE_MODE,
         IMILaunchConfigurationConstants.DEBUGGER_VERBOSE_MODE_DEFAULT);
      if (launchType.equals(IOSELaunchConfigurationConstants.ID_LAUNCH_CORE_MODULE))
      {
         config.setAttribute(IOSEMILaunchConfigurationConstants.ATTR_DEBUG_SCOPE,
            IOSEMILaunchConfigurationConstants.VALUE_DEBUG_SCOPE_PROCESS);
      }
      else
      {
         config.setAttribute(IOSEMILaunchConfigurationConstants.ATTR_DEBUG_SCOPE,
            IOSEMILaunchConfigurationConstants.VALUE_DEBUG_SCOPE_SEGMENT);
      }
      config.setAttribute(IOSEMILaunchConfigurationConstants.ATTR_SEGMENT_ID,
         EMPTY_STRING);
      config.setAttribute(IOSEMILaunchConfigurationConstants.ATTR_BLOCK_ID,
         EMPTY_STRING);
      config.setAttribute(IOSEMILaunchConfigurationConstants.ATTR_PROCESS_ID,
         EMPTY_STRING);
   }

   public void initializeFrom(ILaunchConfiguration config)
   {
      boolean lmDownload = false;
      String gdbCommand = IOSEMILaunchConfigurationConstants.VALUE_DEBUG_NAME_NATIVE;
      String gdbInit = EMPTY_STRING;
      boolean verboseMode = IMILaunchConfigurationConstants.DEBUGGER_VERBOSE_MODE_DEFAULT;
      String scope = IOSEMILaunchConfigurationConstants.VALUE_DEBUG_SCOPE_SEGMENT;
      String segment = EMPTY_STRING;
      String block = EMPTY_STRING;
      String process = EMPTY_STRING;

      setInitializing(true);
      cpu = getCPU(config);

      try
      {
         launchType = config.getType().getIdentifier();
         lmDownload = config.getAttribute(
            IOSELaunchConfigurationConstants.ATTR_LM_DOWNLOAD, false);
         gdbCommand = config.getAttribute(
            IMILaunchConfigurationConstants.ATTR_DEBUG_NAME,
            IOSEMILaunchConfigurationConstants.VALUE_DEBUG_NAME_NATIVE);
         gdbInit = config.getAttribute(
            IMILaunchConfigurationConstants.ATTR_GDB_INIT, EMPTY_STRING);
         verboseMode = config.getAttribute(
            IMILaunchConfigurationConstants.ATTR_DEBUGGER_VERBOSE_MODE,
            IMILaunchConfigurationConstants.DEBUGGER_VERBOSE_MODE_DEFAULT);
         scope = config.getAttribute(
            IOSEMILaunchConfigurationConstants.ATTR_DEBUG_SCOPE,
            IOSEMILaunchConfigurationConstants.VALUE_DEBUG_SCOPE_SEGMENT);
         segment = config.getAttribute(
            IOSEMILaunchConfigurationConstants.ATTR_SEGMENT_ID, EMPTY_STRING);
         block = config.getAttribute(
            IOSEMILaunchConfigurationConstants.ATTR_BLOCK_ID, EMPTY_STRING);
         process = config.getAttribute(
            IOSEMILaunchConfigurationConstants.ATTR_PROCESS_ID, EMPTY_STRING);
      }
      catch (CoreException e)
      {
         CDebugUIPlugin.log(e);
      }

      fGDBCommandText.setText(gdbCommand);
      fGDBInitText.setText(gdbInit);
      fVerboseModeButton.setSelection(verboseMode);

      if (launchType.equals(IOSELaunchConfigurationConstants.ID_LAUNCH_CORE_MODULE))
      {
         // Core module
         fScopeCombo.setEnabled(true);
         fSegmentText.setEnabled(false);
         fBlockText.setEnabled(
            scope.equals(IOSEMILaunchConfigurationConstants.VALUE_DEBUG_SCOPE_BLOCK));
         fProcessText.setEnabled(true);
         fScopeCombo.setText(scope);
         fSegmentText.setText("0");
         fBlockText.setText(block);
         fProcessText.setText(process);
      }
      else if (lmDownload)
      {
         // Load module to be downloaded
         fScopeCombo.setEnabled(false);
         fSegmentText.setEnabled(false);
         fBlockText.setEnabled(false);
         fProcessText.setEnabled(false);
         fScopeCombo.setText(IOSEMILaunchConfigurationConstants.VALUE_DEBUG_SCOPE_SEGMENT);
         fSegmentText.setText(EMPTY_STRING);
         fBlockText.setText(EMPTY_STRING);
         fProcessText.setText(EMPTY_STRING);
      }
      else
      {
         // Existing load module or dump
         fScopeCombo.setEnabled(true);
         fSegmentText.setEnabled(
            scope.equals(IOSEMILaunchConfigurationConstants.VALUE_DEBUG_SCOPE_SEGMENT));
         fBlockText.setEnabled(
            scope.equals(IOSEMILaunchConfigurationConstants.VALUE_DEBUG_SCOPE_BLOCK));
         fProcessText.setEnabled(true);
         fScopeCombo.setText(scope);
         fSegmentText.setText(segment);
         fBlockText.setText(block);
         fProcessText.setText(process);
      }

      setInitializing(false);

      // Update since the load module download option may have changed.
      updateLaunchConfigurationDialog();
   }

   public void performApply(ILaunchConfigurationWorkingCopy config)
   {
      config.setAttribute(IMILaunchConfigurationConstants.ATTR_DEBUG_NAME,
                          fGDBCommandText.getText().trim());
      config.setAttribute(IMILaunchConfigurationConstants.ATTR_GDB_INIT,
                          fGDBInitText.getText().trim());
      config.setAttribute(IMILaunchConfigurationConstants.ATTR_DEBUGGER_VERBOSE_MODE,
                          fVerboseModeButton.getSelection());
      config.setAttribute(IOSEMILaunchConfigurationConstants.ATTR_DEBUG_SCOPE,
                          fScopeCombo.getText());
      config.setAttribute(IOSEMILaunchConfigurationConstants.ATTR_SEGMENT_ID,
                          fSegmentText.getText().trim());
      config.setAttribute(IOSEMILaunchConfigurationConstants.ATTR_BLOCK_ID,
                          fBlockText.getText().trim());
      config.setAttribute(IOSEMILaunchConfigurationConstants.ATTR_PROCESS_ID,
                          fProcessText.getText().trim());
   }

   public boolean isValid(ILaunchConfiguration config)
   {
      String text;

      setErrorMessage(null);
      setMessage(null);

      text = fGDBCommandText.getText().trim();
      if (text.length() == 0)
      {
         setErrorMessage("Debugger executable not specified");
         return false;
      }

      if (fSegmentText.isEnabled())
      {
         text = fSegmentText.getText().trim();
         if (text.length() == 0)
         {
            setErrorMessage("Segment id not specified");
            return false;
         }
      }

      if (fBlockText.isEnabled())
      {
         text = fBlockText.getText().trim();
         if (text.length() == 0)
         {
            setErrorMessage("Block id not specified");
            return false;
         }
      }

      if (fProcessText.isEnabled())
      {
         text = fProcessText.getText().trim();
         if (text.length() == 0)
         {
            setErrorMessage("Process id not specified");
            return false;
         }
      }

      return true;
   }

   public String getName()
   {
      return "OSE GDB Debugger Options";
   }

   protected boolean isInitializing()
   {
      return isInitializing;
   }

   private void setInitializing(boolean isInitializing)
   {
      this.isInitializing = isInitializing;
   }

   protected static String getGDB(String cpu)
   {
      String gdbName;
      String gdbPath;

      if (cpu == null)
      {
         return IOSEMILaunchConfigurationConstants.VALUE_DEBUG_NAME_NATIVE;
      }
      else if (cpu.equals("arm"))
      {
         gdbName = IOSEMILaunchConfigurationConstants.VALUE_DEBUG_NAME_ARM;
      }
      else if (cpu.equals("mips"))
      {
         gdbName = IOSEMILaunchConfigurationConstants.VALUE_DEBUG_NAME_MIPS;
      }
      else if (cpu.equals("ppc"))
      {
         gdbName = IOSEMILaunchConfigurationConstants.VALUE_DEBUG_NAME_POWERPC;
      }
      else
      {
         return IOSEMILaunchConfigurationConstants.VALUE_DEBUG_NAME_NATIVE;
      }

      gdbPath = GDBDebugger.findGDB(gdbName);
      return ((gdbPath != null) ? gdbPath : gdbName);
   }

   protected static String getGDB(ILaunchConfiguration config)
   {
      return getGDB(getCPU(config));
   }

   protected static String getCPU(ILaunchConfiguration config)
   {
      String cpu = null;
      String projectName = null;
      String programName = null;
      CoreModel model;
      ICProject project;
      IFile programFile;
      ICElement element;

      try
      {
         projectName = config.getAttribute(
            ICDTLaunchConfigurationConstants.ATTR_PROJECT_NAME, (String) null);

         programName = config.getAttribute(
            ICDTLaunchConfigurationConstants.ATTR_PROGRAM_NAME, (String) null);
      }
      catch (CoreException e)
      {
         CDebugUIPlugin.log(e);
      }

      if ((projectName == null) ||
          (programName == null) ||
          (projectName.trim().length() == 0) ||
          (programName.trim().length() == 0))
      {
         return null;
      }

      model = CCorePlugin.getDefault().getCoreModel();

      project = model.getCModel().getCProject(projectName);
      if (project == null)
      {
         return null;
      }

      programFile = project.getProject().getFile(programName);
      if (programFile == null)
      {
         return null;
      }

      element = model.create(programFile);

      if (element instanceof IBinary)
      {
         IBinary bin = (IBinary) element;
         cpu = bin.getCPU();
      }

      return cpu;
   }
}
