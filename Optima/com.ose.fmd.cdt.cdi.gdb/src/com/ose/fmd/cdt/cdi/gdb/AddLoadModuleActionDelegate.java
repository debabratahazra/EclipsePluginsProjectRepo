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

package com.ose.fmd.cdt.cdi.gdb;

import java.util.ArrayList;
import java.util.List;
import org.eclipse.cdt.debug.core.cdi.event.ICDIEvent;
import org.eclipse.cdt.debug.core.cdi.model.ICDITarget;
import org.eclipse.cdt.debug.core.model.ICDebugTarget;
import org.eclipse.cdt.debug.mi.core.MIException;
import org.eclipse.cdt.debug.mi.core.MISession;
import org.eclipse.cdt.debug.mi.core.cdi.EventManager;
import org.eclipse.cdt.debug.mi.core.cdi.Session;
import org.eclipse.cdt.debug.mi.core.cdi.event.CreatedEvent;
import org.eclipse.cdt.debug.mi.core.cdi.model.SharedLibrary;
import org.eclipse.cdt.debug.mi.core.cdi.model.Target;
import org.eclipse.cdt.debug.mi.core.command.CLICommand;
import org.eclipse.cdt.debug.mi.core.command.Command;
import org.eclipse.cdt.debug.mi.core.output.MIInfo;
import org.eclipse.cdt.debug.mi.core.output.MIShared;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IViewActionDelegate;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.PlatformUI;
import com.ose.fmm.Fmi;
import com.ose.fmm.FreezeModeMonitor;

public class AddLoadModuleActionDelegate implements IViewActionDelegate
{
   private ICDebugTarget debugTarget;

   public void init(IViewPart view)
   {
      // Nothing to do.
   }

   public void selectionChanged(IAction action, ISelection selection)
   {
      Object obj = null;
      if (selection instanceof IStructuredSelection)
      {
         obj = ((IStructuredSelection) selection).getFirstElement();
      }
      debugTarget = ((obj instanceof ICDebugTarget) ? ((ICDebugTarget) obj) : null);
      action.setEnabled(isAddLoadModuleActionEnabled());
   }

   public void run(IAction action)
   {
      if ((debugTarget != null) && debugTarget.isSuspended())
      {
         Job job = new AddLoadModuleJob(debugTarget);
         job.schedule();
      }
   }

   private boolean isAddLoadModuleActionEnabled()
   {
      if ((debugTarget != null) && debugTarget.isSuspended())
      {
         FreezeModeMonitor monitor =
            DebuggerPlugin.getDefault().getMonitor(debugTarget);
         if ((monitor != null) && monitor.isOpen())
         {
            Fmi fmi = monitor.getFmi();
            if (fmi.isInitialized())
            {
               if (fmi.ose_host_fmi_is_supported(Fmi.HOST_FMI_FEATURE_LOAD_MODULE) != 0)
               {
                  return true;
               }
            }
         }
      }

      return false;
   }

   private static class AddLoadModuleJob extends Job
   {
      private final ICDebugTarget debugTarget;

      AddLoadModuleJob(ICDebugTarget debugTarget)
      {
         super("Adding load module");
         setPriority(SHORT);
         this.debugTarget = debugTarget;
      }

      protected IStatus run(IProgressMonitor progressMonitor)
      {
         try
         {
            progressMonitor.beginTask(getName(), IProgressMonitor.UNKNOWN);
            addLoadModule(progressMonitor);
            return (progressMonitor.isCanceled() ?
                    Status.CANCEL_STATUS : Status.OK_STATUS);
         }
         catch (OperationCanceledException e)
         {
            return Status.CANCEL_STATUS;
         }
         catch (Exception e)
         {
            return DebuggerPlugin.createErrorStatus("Error adding load module", e);
         }
         finally
         {
            progressMonitor.done();
         }
      }

      private void addLoadModule(IProgressMonitor progressMonitor)
         throws MIException
      {
         if (debugTarget.isSuspended())
         {
            FreezeModeMonitor monitor =
               DebuggerPlugin.getDefault().getMonitor(debugTarget);
            if ((monitor != null) && monitor.isOpen())
            {
               Fmi fmi = monitor.getFmi();
               if (fmi.isInitialized())
               {
                  // Bail out if the target does not support load modules.
                  if (fmi.ose_host_fmi_is_supported(Fmi.HOST_FMI_FEATURE_LOAD_MODULE) == 0)
                  {
                     asyncExec(new MessageDialogRunner(
                        "The target does not support load modules."));
                     return;
                  }

                  // Get info about the installed load modules on the target.
                  Fmi.PfmiLoadModuleInfo[] loadModules =
                     getLoadModules(progressMonitor, fmi);

                  // Bail out if there are no installed load modules on the target.
                  if (loadModules.length <= 1)
                  {
                     asyncExec(new MessageDialogRunner(
                        "There are no installed load modules on the target."));
                     return;
                  }

                  // Open the add load module dialog.
                  AddLoadModuleDialogRunner dialogRunner =
                     new AddLoadModuleDialogRunner(loadModules);
                  syncExec(dialogRunner);
                  String file = dialogRunner.getFile();
                  Fmi.PfmiLoadModuleInfo loadModule = dialogRunner.getLoadModule();

                  // Add and relocate the selected load module file.
                  if ((file != null) && (loadModule != null))
                  {
                     //Locate the loadable sections
                     Fmi.PfmiLoadModuleSectionInfo[] sectionInfos = 
                        getSections(progressMonitor, fmi, loadModule); 
                     ICDITarget cdiTarget =
                        (ICDITarget) debugTarget.getAdapter(ICDITarget.class);
                     if (cdiTarget instanceof Target)
                     {
                        Target miTarget = (Target) cdiTarget;
                        MISession miSession = miTarget.getMISession();
                        if (miSession != null)
                        {
                           Command cmd = null;
                       	   String relocations = "";
                           for (int i = 0; i < sectionInfos.length; i++)
                           {
                               relocations += " -s " + 
                                  sectionInfos[i].section_name + " " + 
                                  toHexString(sectionInfos[i].section_base);
                    	   }
                           cmd = new CLIAddSymbolFile(
                       	            (new Path(file)).toString(),
                       	            loadModule.text_base,
                       	            loadModule.data_base,
                       	            relocations);    
                           miSession.postCommand(cmd);
                           MIInfo info = cmd.getMIInfo();
                           if (info == null)
                           {
                              throw new MIException("No answer from CLIAddSymbolFile");
                           }
                           fireSharedLibraryLoadedEvent(file, loadModule, miTarget);
                        }
                     }
                  }
               }
            }
         }
      }

      private static Fmi.PfmiLoadModuleInfo[] getLoadModules(
            IProgressMonitor progressMonitor, Fmi fmi)
      {
         List loadModules = new ArrayList();
         Fmi.PfmiCursor cursor = new Fmi.PfmiCursor();
         Fmi.PfmiLoadModuleInfo loadModule = new Fmi.PfmiLoadModuleInfo();
         int status;

         status = fmi.ose_pfmi_get_first_load_module(cursor, loadModule);

         while (status == Fmi.EFMI_OK)
         {
            loadModules.add(loadModule);
            if (progressMonitor.isCanceled())
            {
               throw new OperationCanceledException();
            }
            loadModule = new Fmi.PfmiLoadModuleInfo();
            status = fmi.ose_pfmi_get_next_load_module(cursor, loadModule);
         }

         return (Fmi.PfmiLoadModuleInfo[])
            loadModules.toArray(new Fmi.PfmiLoadModuleInfo[0]);
      }
      
      private static Fmi.PfmiLoadModuleSectionInfo[] getSections(
            IProgressMonitor progressMonitor, Fmi fmi, 
            Fmi.PfmiLoadModuleInfo loadModule)
      {
         List sectionInfos = new ArrayList();
         int status;
   	  
         for (int section_no = 0; section_no < loadModule.no_of_sections; section_no++)
         {
            Fmi.PfmiLoadModuleSectionInfo sectionInfo = 
               new Fmi.PfmiLoadModuleSectionInfo();       
            status = fmi.ose_pfmi_get_load_module_section_info(
                        loadModule.install_handle, 
                        section_no, sectionInfo);
            // Check section_options against PM_SECTION_LOAD (value is 1)
            if (status == Fmi.EFMI_OK && 
                sectionInfo.section_options == 1 &&
                sectionInfo.section_name.compareToIgnoreCase(".text") != 0 &&
                sectionInfo.section_name.compareToIgnoreCase(".data") != 0)
            {
               sectionInfos.add(sectionInfo);
            }
         }
    	  
         return (Fmi.PfmiLoadModuleSectionInfo[])
            sectionInfos.toArray(new Fmi.PfmiLoadModuleSectionInfo[0]);
      }

      private static void fireSharedLibraryLoadedEvent(
            String file,
            Fmi.PfmiLoadModuleInfo loadModule,
            Target target)
      {
         String from = toHexString(loadModule.text_base);
         String to = toHexString(loadModule.text_base + loadModule.text_size);
         MIShared shared = new MIShared(from, to, true, file);
         SharedLibrary library = new SharedLibrary(target, shared);
         Session session = (Session) target.getSession();
         ICDIEvent event = new CreatedEvent(session, library);
         EventManager eventManager = (EventManager) session.getEventManager();
         eventManager.fireEvents(new ICDIEvent[] {event});
      }

      private static void asyncExec(Runnable runnable)
      {
         Display display = PlatformUI.getWorkbench().getDisplay();
         if (!display.isDisposed())
         {
            display.asyncExec(runnable);
         }
      }

      private static void syncExec(Runnable runnable)
      {
         Display display = PlatformUI.getWorkbench().getDisplay();
         if (!display.isDisposed())
         {
            display.syncExec(runnable);
         }
      }

      private static String toHexString(int i)
      {
         return "0x" + Integer.toHexString(i).toUpperCase();
      }

      private static class MessageDialogRunner implements Runnable
      {
         private final String message;

         MessageDialogRunner(String message)
         {
            this.message = message;
         }

         public void run()
         {
            MessageDialog.openInformation(null, "Information", message);
         }
      }

      private static class AddLoadModuleDialogRunner implements Runnable
      {
         private final Fmi.PfmiLoadModuleInfo[] loadModules;
         private String file;
         private Fmi.PfmiLoadModuleInfo loadModule;

         AddLoadModuleDialogRunner(Fmi.PfmiLoadModuleInfo[] loadModules)
         {
            this.loadModules = loadModules;
         }

         public void run()
         {
            AddLoadModuleDialog dialog =
               new AddLoadModuleDialog(null, loadModules);
            int result = dialog.open();
            if (result == Window.OK)
            {
               synchronized (this)
               {
                  file = dialog.getFile();
                  loadModule = dialog.getLoadModule();
               }
            }
         }

         public synchronized String getFile()
         {
            return file;
         }

         public synchronized Fmi.PfmiLoadModuleInfo getLoadModule()
         {
            return loadModule;
         }
      }

      /*
       * add-symbol-file <file> <text-address> [-readnow]
       * [-s <section> <section-address>] ...
       */
      private static class CLIAddSymbolFile extends CLICommand
      {
         CLIAddSymbolFile(String file, int textAddress, 
            int dataAddress, String relocations)
         {
       	    super("add-symbol-file " + file + " " + toHexString(textAddress) +
                  " -s .data " + toHexString(dataAddress) +
                  relocations + " -readnow");
         }
      }
   }
}
