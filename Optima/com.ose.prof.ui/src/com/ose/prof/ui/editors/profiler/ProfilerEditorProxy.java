/* COPYRIGHT-ENEA-SRC-R2 *
 **************************************************************************
 * Copyright (C) 2009 by Enea Software AB.
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

package com.ose.prof.ui.editors.profiler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.ListenerList;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IEditorRegistry;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.IPropertyListener;
import org.eclipse.ui.IURIEditorInput;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.IWorkbenchPartSite;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.dialogs.ListDialog;
import com.ose.pmd.dump.DumpFile;
import com.ose.pmd.dump.SignalBlock;
import com.ose.prof.ui.ProfilerPlugin;
import com.ose.system.service.monitor.signal.MonitorGetCPUBlockReportsRequest;
import com.ose.system.service.monitor.signal.MonitorGetCPUPriReportsRequest;
import com.ose.system.service.monitor.signal.MonitorGetCPUProcessReportsRequest;
import com.ose.system.service.monitor.signal.MonitorGetCPUReportsRequest;
import com.ose.system.service.monitor.signal.MonitorGetUserReportsRequest;

public class ProfilerEditorProxy implements IEditorPart
{
   public static final int EDITOR_STATUS_NOT_READY = 0;

   public static final int EDITOR_STATUS_READY =
      ProfilerEditor.EDITOR_PROPERTY_READY;

   public static final int EDITOR_STATUS_FAILED =
      ProfilerEditor.EDITOR_PROPERTY_FAILED;

   private static final String EXTENSION_PMD = "pmd";

   private static final String EXTENSION_XML = "report";

   private final Map editorIdNameMap;

   private final ListenerList listenerList;

   private final Image defaultTitleImage;

   private Image titleImage;

   private IEditorPart editor;

   private int editorStatus;

   public ProfilerEditorProxy()
   {
      URL imageURL;

      editorIdNameMap = createEditorIdNameMap();
      listenerList = new ListenerList();
      imageURL = Platform.getBundle(ProfilerPlugin.getUniqueIdentifier()).
         getEntry("/icons/elcl16/prof.gif");
      defaultTitleImage = ImageDescriptor.createFromURL(imageURL).createImage();
      titleImage = defaultTitleImage;
   }

   public void init(IEditorSite site, IEditorInput input)
      throws PartInitException
   {
      if (input instanceof IURIEditorInput)
      {
         IURIEditorInput uriEditorInput;
         IPath path;
         IFile file;
         String editorId;

         uriEditorInput = (IURIEditorInput) input;
         path = new Path(uriEditorInput.getURI().getPath());
         file = ResourcesPlugin.getWorkspace().getRoot().getFileForLocation(path);
         if ((file == null) || !file.exists())
         {
            throw new PartInitException("Invalid editor input");
         }
         try
         {
            editorId = findEditorId(file);
         }
         catch (IOException e)
         {
            throw new PartInitException(
               "Error reading file " + file.getName(), e);
         }
         if (editorId == null)
         {
            throw new UnsupportedOperationException(
               "No suitable editor found for file " + file.getName());
         }
         editor = createEditor(editorId);
         if (editor == null)
         {
            throw new UnsupportedOperationException(
               "No suitable editor found for file " + file.getName());
         }
         editor.addPropertyListener(new PropertyHandler());
         editor.init(site, input);
      }
      else
      {
         throw new PartInitException("Invalid editor input");
      }
   }

   public void createPartControl(Composite parent)
   {
      editor.createPartControl(parent);
   }

   public void addPropertyListener(IPropertyListener listener)
   {
      listenerList.add(listener);
   }

   public void removePropertyListener(IPropertyListener listener)
   {
      listenerList.remove(listener);
   }

   public IEditorPart getEditor()
   {
      return editor;
   }

   public int getEditorStatus()
   {
      return editorStatus;
   }

   public IEditorInput getEditorInput()
   {
      return editor.getEditorInput();
   }

   public IEditorSite getEditorSite()
   {
      return editor.getEditorSite();
   }

   public IWorkbenchPartSite getSite()
   {
      return editor.getSite();
   }

   public String getTitle()
   {
      return (editor != null) ? editor.getTitle() : "";
   }

   public Image getTitleImage()
   {
      return titleImage;
   }

   public void setTitleImage(Image titleImage)
   {
      this.titleImage = (titleImage != null) ? titleImage : defaultTitleImage;
      firePropertyChange(IWorkbenchPart.PROP_TITLE);
   }

   public String getTitleToolTip()
   {
      return editor.getTitleToolTip();
   }

   public Object getAdapter(Class adapter)
   {
      return editor.getAdapter(adapter);
   }

   public boolean isDirty()
   {
      return editor.isDirty();
   }

   public boolean isSaveAsAllowed()
   {
      return editor.isSaveAsAllowed();
   }

   public boolean isSaveOnCloseNeeded()
   {
      return editor.isSaveOnCloseNeeded();
   }

   public void setFocus()
   {
      editor.setFocus();
   }

   public void doSave(IProgressMonitor monitor)
   {
      editor.doSave(monitor);
   }

   public void doSaveAs()
   {
      editor.doSaveAs();
   }

   public void dispose()
   {
      if (editor != null)
      {
         editor.dispose();
      }
      defaultTitleImage.dispose();
   }

   private static Map createEditorIdNameMap()
   {
      Map map;
      IEditorRegistry registry;

      map = new HashMap();
      registry = PlatformUI.getWorkbench().getEditorRegistry();
      map.put(ProfilerPlugin.CPU_PROFILER_EDITOR_ID,
         registry.findEditor(ProfilerPlugin.CPU_PROFILER_EDITOR_ID).getLabel());
      map.put(ProfilerPlugin.CPU_PRIORITY_PROFILER_EDITOR_ID,
         registry.findEditor(ProfilerPlugin.CPU_PRIORITY_PROFILER_EDITOR_ID).getLabel());
      map.put(ProfilerPlugin.CPU_PROCESS_PROFILER_EDITOR_ID,
         registry.findEditor(ProfilerPlugin.CPU_PROCESS_PROFILER_EDITOR_ID).getLabel());
      map.put(ProfilerPlugin.CPU_BLOCK_PROFILER_EDITOR_ID,
         registry.findEditor(ProfilerPlugin.CPU_BLOCK_PROFILER_EDITOR_ID).getLabel());
      map.put(ProfilerPlugin.HEAP_PROFILER_EDITOR_ID,
         registry.findEditor(ProfilerPlugin.HEAP_PROFILER_EDITOR_ID).getLabel());
      map.put(ProfilerPlugin.ACCUMULATED_HEAP_PROCESS_PROFILER_EDITOR_ID,
         registry.findEditor(ProfilerPlugin.ACCUMULATED_HEAP_PROCESS_PROFILER_EDITOR_ID).getLabel());
      map.put(ProfilerPlugin.ACCUMULATED_HEAP_PROFILER_EDITOR_ID,
         registry.findEditor(ProfilerPlugin.ACCUMULATED_HEAP_PROFILER_EDITOR_ID).getLabel());
      map.put(ProfilerPlugin.USER_PROFILER_EDITOR_ID,
         registry.findEditor(ProfilerPlugin.USER_PROFILER_EDITOR_ID).getLabel());
      return map;
   }

   private String findEditorId(IFile file) throws IOException
   {
      String[] editorIds;
      String editorId;

      editorIds = findEditorIds(file);
      if ((editorIds == null) || (editorIds.length == 0))
      {
         editorId = null;
      }
      else if (editorIds.length > 1)
      {
         editorId = getSelectedEditorId(file, editorIds);
      }
      else
      {
         editorId = editorIds[0];
      }

      return editorId;
   }

   private static String[] findEditorIds(IFile file) throws IOException
   {
      String extension = file.getFileExtension();

      if (EXTENSION_PMD.equals(extension))
      {
         return findPMDEditorIds(file);
      }
      else if (EXTENSION_XML.equals(extension))
      {
         String editorId = findXMLEditorId(file);
         return (editorId != null) ? new String[] {editorId} : null;
      }
      else
      {
         return null;
      }
   }

   private static String[] findPMDEditorIds(IFile file) throws IOException
   {
      DumpFile dumpFile = null;

      try
      {
         List signalBlocks;
         Set editorIds = new HashSet();

         dumpFile = new DumpFile(file.getLocation().toFile());
         signalBlocks = dumpFile.getSignalBlocks();

         for (Iterator i = signalBlocks.iterator(); i.hasNext();)
         {
            SignalBlock signalBlock = (SignalBlock) i.next();
            int reqSigNo = (int) signalBlock.getRequestSigNo();

            switch (reqSigNo)
            {
               case MonitorGetCPUReportsRequest.SIG_NO:
                  editorIds.add(ProfilerPlugin.CPU_PROFILER_EDITOR_ID);
                  break;
               case MonitorGetCPUPriReportsRequest.SIG_NO:
                  editorIds.add(ProfilerPlugin.CPU_PRIORITY_PROFILER_EDITOR_ID);
                  break;
               case MonitorGetCPUProcessReportsRequest.SIG_NO:
                  editorIds.add(ProfilerPlugin.CPU_PROCESS_PROFILER_EDITOR_ID);
                  break;
               case MonitorGetCPUBlockReportsRequest.SIG_NO:
                  editorIds.add(ProfilerPlugin.CPU_BLOCK_PROFILER_EDITOR_ID);
                  break;
               case MonitorGetUserReportsRequest.SIG_NO:
                  editorIds.add(ProfilerPlugin.USER_PROFILER_EDITOR_ID);
                  break;
               default:
                  break;
            }
         }

         return (String[]) editorIds.toArray(new String[0]);
      }
      finally
      {
         if (dumpFile != null)
         {
            dumpFile.close();
         }
      }
   }

   private static String findXMLEditorId(IFile file) throws IOException
   {
      BufferedReader in = null;

      try
      {
         char[] cbuf = new char[2048];
         String str;

         in = new BufferedReader(
            new InputStreamReader(file.getContents(), "ISO-8859-1"));
         in.read(cbuf);
         str = new String(cbuf);

         if (str.contains("cpuReports"))
         {
            return ProfilerPlugin.CPU_PROFILER_EDITOR_ID;
         }
         else if (str.contains("cpuPriorityReports"))
         {
            return ProfilerPlugin.CPU_PRIORITY_PROFILER_EDITOR_ID;
         }
         else if (str.contains("cpuProcessReports"))
         {
            return ProfilerPlugin.CPU_PROCESS_PROFILER_EDITOR_ID;
         }
         else if (str.contains("cpuBlockReports"))
         {
            return ProfilerPlugin.CPU_BLOCK_PROFILER_EDITOR_ID;
         }
         else if (str.contains("userReports"))
         {
            return ProfilerPlugin.USER_PROFILER_EDITOR_ID;
         }
         else
         {
            return null;
         }
      }
      catch (CoreException e)
      {
         throw new IOException(e.getMessage());
      }
      finally
      {
         if (in != null)
         {
            try
            {
               in.close();
            } catch (IOException ignore) {}
         }
      }
   }

   private String getSelectedEditorId(IFile file, String[] editorIds)
   {
      ListDialog dialog;
      Object[] result;

      dialog = createEditorSelectionDialog(file, editorIds);
      dialog.open();
      result = dialog.getResult();
      if ((result != null) && (result.length > 0))
      {
         String editorName = (String) result[0];
         Set entrySet = editorIdNameMap.entrySet();
         for (Iterator i = entrySet.iterator(); i.hasNext();)
         {
            Map.Entry entry = (Map.Entry) i.next();
            if (entry.getValue().equals(editorName))
            {
               return (String) entry.getKey();
            }
         }
         return null;
      }
      else
      {
         return null;
      }
   }

   private ListDialog createEditorSelectionDialog(IFile file, String[] editorIds)
   {
      List editorNames;
      Shell shell;
      ListDialog dialog;

      editorNames = new ArrayList();
      for (int i = 0; i < editorIds.length; i++)
      {
         String editorName = (String) editorIdNameMap.get(editorIds[i]);
         if (editorName != null)
         {
            editorNames.add(editorName);
         }
      }

      shell = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell();
      dialog = new ListDialog(shell);
      dialog.setTitle("System Profiler Editor Selection");
      dialog.setMessage("Select a profiler editor for file " + file.getName() + ":");
      dialog.setAddCancelButton(true);
      dialog.setContentProvider(new ArrayContentProvider());
      dialog.setLabelProvider(new LabelProvider());
      dialog.setInput(editorNames);
      if (editorNames.size() > 0)
      {
         dialog.setInitialSelections(new String[] {(String) editorNames.get(0)});
      }
      return dialog;
   }

   private static IEditorPart createEditor(String editorId)
   {
      if (ProfilerPlugin.CPU_PROFILER_EDITOR_ID.equals(editorId))
      {
         return new CPUProfilerEditor();
      }
      else if (ProfilerPlugin.CPU_PRIORITY_PROFILER_EDITOR_ID.equals(editorId))
      {
         return new CPUPriorityProfilerEditor();
      }
      else if (ProfilerPlugin.CPU_PROCESS_PROFILER_EDITOR_ID.equals(editorId))
      {
         return new CPUProcessProfilerEditor();
      }
      else if (ProfilerPlugin.CPU_BLOCK_PROFILER_EDITOR_ID.equals(editorId))
      {
         return new CPUBlockProfilerEditor();
      }
      else if (ProfilerPlugin.HEAP_PROFILER_EDITOR_ID.equals(editorId))
      {
         return new HeapProfilerEditor();
      }
      else if (ProfilerPlugin.ACCUMULATED_HEAP_PROCESS_PROFILER_EDITOR_ID.equals(editorId))
      {
         return new AccumulatedHeapProcessProfilerEditor();
      }
      else if (ProfilerPlugin.ACCUMULATED_HEAP_PROFILER_EDITOR_ID.equals(editorId))
      {
         return new AccumulatedHeapProfilerEditor();
      }
      else if (ProfilerPlugin.USER_PROFILER_EDITOR_ID.equals(editorId))
      {
         return new UserProfilerEditor();
      }
      else
      {
         return null;
      }
   }

   private void firePropertyChange(int propertyId)
   {
      Object[] listeners = listenerList.getListeners();
      for (int i = 0; i < listeners.length; i++)
      {
         IPropertyListener listener = (IPropertyListener) listeners[i];
         try
         {
            listener.propertyChanged(this, propertyId);
         }
         catch (RuntimeException e)
         {
            ProfilerPlugin.log(e);
         }
      }
   }

   private class PropertyHandler implements IPropertyListener
   {
      public void propertyChanged(Object source, int propertyId)
      {
         if ((propertyId == EDITOR_STATUS_READY) ||
             (propertyId == EDITOR_STATUS_FAILED))
         {
            editorStatus = propertyId;
         }
         firePropertyChange(propertyId);
      }
   }
}
