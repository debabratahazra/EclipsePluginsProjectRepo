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

package com.ose.perf.ui.editor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import org.eclipse.cdt.debug.core.sourcelookup.MappingSourceContainer;
import org.eclipse.cdt.debug.internal.core.sourcelookup.MapEntrySourceContainer;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.debug.core.ILaunchManager;
import org.eclipse.debug.core.model.ISourceLocator;
import org.eclipse.debug.core.sourcelookup.ISourceContainer;
import org.eclipse.debug.core.sourcelookup.ISourceLookupDirector;
import org.eclipse.debug.core.sourcelookup.containers.DirectorySourceContainer;
import org.eclipse.debug.ui.sourcelookup.CommonSourceNotFoundEditor;
import org.eclipse.debug.ui.sourcelookup.ISourceDisplay;
import org.eclipse.debug.ui.sourcelookup.SourceLookupDialog;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.ui.IEditorSite;

public class SourceNotFoundEditor extends CommonSourceNotFoundEditor
{
   private static final String FOUND_MAPPINGS_CONTAINER_NAME = "Found Mappings";

   protected void createButtons(Composite parent)
   {
      Button button = new Button(parent, SWT.PUSH);
      button.setText("Locate File...");
      button.addSelectionListener(new SelectionAdapter()
      {
         public void widgetSelected(SelectionEvent event)
         {
            locateFile();
         }
      });

      super.createButtons(parent);
   }

   protected String getText()
   {
      return "Can't find a source file at \"" + getEditorInput().getName() + "\".\n"
         + "Locate the file or edit the source lookup path to include its location.";
   }

   /**
    * Edits the source lookup path associated with the active launch.
    * After the path is edited, source lookup is performed again and
    * this editor is closed.
    */
   protected void editSourceLookupPath()
   {
      SourceNotFoundEditorInput editorInput =
         (SourceNotFoundEditorInput) getEditorInput();
      ISourceLocator sourceLocator = editorInput.getLaunch().getSourceLocator();
      Object artifact = getArtifact();

      if ((sourceLocator instanceof ISourceLookupDirector) &&
          (artifact instanceof ISourceDisplay))
      {
         ISourceLookupDirector sourceLookupDirector =
            (ISourceLookupDirector) sourceLocator;
         ISourceDisplay sourceDisplay = (ISourceDisplay) artifact;
         IEditorSite editorSite = getEditorSite();

         SourceLookupDialog dialog = new SourceLookupDialog(
               editorSite.getShell(), sourceLookupDirector);
         if (dialog.open() == Window.OK)
         {
            sourceDisplay.displaySource(artifact, editorSite.getPage(), true);
            closeEditor();
         }
      }
   }

   /**
    * Locate the missing source file, add a source lookup path for it, and show
    * it.
    */
   private void locateFile()
   {
      FileDialog dialog = new FileDialog(getEditorSite().getShell(), SWT.NONE);
      Path missingPath = new Path(getEditorInput().getName());
      dialog.setFilterNames(new String[] {"Missing Source File"});
      dialog.setFilterExtensions(new String[]
         {"*." + missingPath.getFileExtension()});
      String res = dialog.open();

      if (res != null)
      {
         Path newPath = new Path(res);

         if (newPath.lastSegment().equalsIgnoreCase(missingPath.lastSegment()))
         {
            // Add a source lookup path if possible.
            if (missingPath.segmentCount() > 1)
            {
               // Add a path mapping.
               IPath compPath = missingPath.removeLastSegments(1);
               IPath newSourcePath = newPath.removeLastSegments(1);
               try
               {
                  addSourceLookupPathToLaunch(compPath, newSourcePath, true);
               } catch (CoreException ignore) {}
            }
            else if (missingPath.segmentCount() > 0)
            {
               // Add a file system directory.
               IPath newSourcePath = newPath.removeLastSegments(1);
               try
               {
                  addSourceLookupPathToLaunch(null, newSourcePath, false);
               } catch (CoreException ignore) {}
            }

            // Show the located source file and close this editor.
            ISourceDisplay sourceDisplay = (ISourceDisplay) getArtifact();
            sourceDisplay.displaySource(sourceDisplay, getEditorSite().getPage(), true);
            closeEditor();
         }
      }
   }

   private void addSourceLookupPathToLaunch(IPath missingPath,
                                            IPath newSourcePath,
                                            boolean mapping)
      throws CoreException
   {
      SourceNotFoundEditorInput editorInput =
         (SourceNotFoundEditorInput) getEditorInput();
      ILaunchConfiguration launch = editorInput.getLaunch().getLaunchConfiguration();
      ILaunchConfigurationWorkingCopy configuration = launch.getWorkingCopy();
      String memento = configuration.getAttribute(
         ILaunchConfiguration.ATTR_SOURCE_LOCATOR_MEMENTO, (String) null);
      String type = configuration.getAttribute(
         ILaunchConfiguration.ATTR_SOURCE_LOCATOR_ID, (String) null);
      if (type == null)
      {
         type = configuration.getType().getSourceLocatorId();
      }

      // Create our source locator object.
      ILaunchManager launchManager = DebugPlugin.getDefault().getLaunchManager();
      ISourceLocator locator = launchManager.newSourceLocator(type);
      if (locator instanceof ISourceLookupDirector)
      {
         ISourceLookupDirector director = (ISourceLookupDirector) locator;
         if (memento == null)
         {
            director.initializeDefaults(configuration);
         }
         else
         {
            director.initializeFromMemento(memento, configuration);
         }

         // Add a source lookup path to our source locator.
         if (mapping)
         {
            // Add a path mapping.
            addSourceMappingToDirector(missingPath, newSourcePath, director);
         }
         else
         {
            // Add a file system directory.
            addSourceDirectoryToDirector(newSourcePath, director);
         }

         // Persist the added source lookup path in our launch configuration.
         configuration.setAttribute(
            ILaunchConfiguration.ATTR_SOURCE_LOCATOR_MEMENTO, director.getMemento());
         configuration.setAttribute(
            ILaunchConfiguration.ATTR_SOURCE_LOCATOR_ID, director.getId());
         configuration.doSave();
      }
   }

   private void addSourceMappingToDirector(IPath missingPath,
                                           IPath newSourcePath,
                                           ISourceLookupDirector director)
   {
      List containers = new ArrayList(Arrays.asList(director.getSourceContainers()));
      MappingSourceContainer foundMappings = null;

      // If already created, find our path mapping source container.
      for (Iterator i = containers.iterator(); i.hasNext();)
      {
         ISourceContainer container = (ISourceContainer) i.next();
         if ((container instanceof MappingSourceContainer) &&
             container.getName().equals(FOUND_MAPPINGS_CONTAINER_NAME))
         {
            foundMappings = (MappingSourceContainer) container;
            break;
         }
      }

      // If not found, create our path mapping source container and add it to
      // the list of source containers.
      if (foundMappings == null)
      {
         foundMappings = new MappingSourceContainer(FOUND_MAPPINGS_CONTAINER_NAME);
         foundMappings.init(director);
         containers.add(foundMappings);
      }

      // Add a new path mapping entry to our path mapping source container
      // and set the updated list of source containers.
      // FIXME: MapEntrySourceContainer is internal API!
      foundMappings.addMapEntry(
         new MapEntrySourceContainer(missingPath, newSourcePath));
      director.setSourceContainers((ISourceContainer[])
         containers.toArray(new ISourceContainer[containers.size()]));
   }

   private void addSourceDirectoryToDirector(IPath sourcePath,
                                             ISourceLookupDirector director)
   {
      List containers = new ArrayList(Arrays.asList(director.getSourceContainers()));
      DirectorySourceContainer container =
            new DirectorySourceContainer(sourcePath, false);
      container.init(director);
      containers.add(container);
      director.setSourceContainers((ISourceContainer[])
            containers.toArray(new ISourceContainer[containers.size()]));
   }
}
