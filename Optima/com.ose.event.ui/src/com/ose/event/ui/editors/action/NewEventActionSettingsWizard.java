/* COPYRIGHT-ENEA-SRC-R2 *
 **************************************************************************
 * Copyright (C) 2007 by Enea Software AB.
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

package com.ose.event.ui.editors.action;

import java.io.FileOutputStream;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.eclipse.core.runtime.preferences.InstanceScope;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.dialogs.WizardNewFileCreationPage;
import org.eclipse.ui.part.FileEditorInput;
import org.eclipse.ui.wizards.newresource.BasicNewResourceWizard;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import com.ose.event.ui.EventPlugin;
import com.ose.xmleditor.model.DomHelper;


public class NewEventActionSettingsWizard extends BasicNewResourceWizard
{
   private WizardNewFileCreationPage mainPage;

   public NewEventActionSettingsWizard()
   {
      super();
      IEclipsePreferences node = InstanceScope.INSTANCE.getNode("com.ose.event.ui.editors.action.NewEventActionSettingsWizard");
      node.putBoolean(ResourcesPlugin.PREF_DISABLE_LINKING, true);
   }

   public void addPages()
   {
      super.addPages();
      mainPage = new WizardNewFileCreationPage("newEventActionFile",
            getSelection());
      mainPage.setTitle("Event Action Settings");
      mainPage.setDescription("Create a new Event Action Settings file");
      addPage(mainPage);
   }

   public boolean performFinish()
   {
      String fileName = mainPage.getFileName();

      if (!fileName.endsWith(".action"))
      {
         mainPage.setFileName(fileName + ".action");
      }

      IFile file = mainPage.createNewFile();

      if (file == null)
      {
         return false;
      }

      try
      {
         // Create a document and a root element
         FileOutputStream ostream = new FileOutputStream(file.getRawLocation()
               .toFile());
         Document document = DomHelper.createNewDocument(new EventActionEntityResolver());
         Element eventActions = document.createElement("eventActions");
         document.appendChild(eventActions);
         
         DomHelper.transform(document, ostream, "eventaction.dtd");
         ostream.close();

         selectAndReveal(file);

         // Open editor on new file.
         IWorkbenchWindow dw = getWorkbench().getActiveWorkbenchWindow();
         if (dw != null)
         {
            IWorkbenchPage page = dw.getActivePage();
            if (page != null)
            {
               page.openEditor(new FileEditorInput(file),
                     "com.ose.event.ui.editors.action.EventActionFormEditor");
            }
         }

         file.refreshLocal(IResource.DEPTH_ZERO, null);
      }
      catch (Exception e)
      {
         EventPlugin.errorDialog("Error", e.getMessage(), e);
      }

      return true;
   }

   public void init(IWorkbench workbench, IStructuredSelection currentSelection)
   {
      super.init(workbench, currentSelection);
      setWindowTitle("New Event Action Settings");
      setNeedsProgressMonitor(true);
   }

   protected void initializeDefaultPageImageDescriptor()
   {
   }

   public void dispose()
   {
      IEclipsePreferences node = InstanceScope.INSTANCE.getNode("com.ose.event.ui.editors.action.NewEventActionSettingsWizard");
      node.putBoolean(ResourcesPlugin.PREF_DISABLE_LINKING, false);
      
      mainPage = null;
      super.dispose();
   }
}
