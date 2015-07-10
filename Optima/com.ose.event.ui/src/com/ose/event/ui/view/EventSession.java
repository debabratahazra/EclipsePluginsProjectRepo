/* COPYRIGHT-ENEA-SRC-R1 *
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

package com.ose.event.ui.view;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Observable;
import java.util.Set;
import javax.xml.parsers.ParserConfigurationException;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.xml.sax.SAXException;
import com.ose.event.format.EventActions;
import com.ose.event.format.EventActionXMLReader;
import com.ose.event.format.EventActionXMLWriter;
import com.ose.event.format.EventDumpXMLConverter;
import com.ose.event.ui.EventPlugin;
import com.ose.event.ui.editors.event.EventEditor;
import com.ose.event.ui.editors.event.EventEditorInput;
import com.ose.system.AllocEvent;
import com.ose.system.AllocEventActionPoint;
import com.ose.system.BindEvent;
import com.ose.system.BindEventActionPoint;
import com.ose.system.CreateEvent;
import com.ose.system.CreateEventActionPoint;
import com.ose.system.ErrorEvent;
import com.ose.system.ErrorEventActionPoint;
import com.ose.system.EventActionPoint;
import com.ose.system.EventTraceHandle;
import com.ose.system.FreeEvent;
import com.ose.system.FreeEventActionPoint;
import com.ose.system.KillEvent;
import com.ose.system.KillEventActionPoint;
import com.ose.system.LossEvent;
import com.ose.system.ReceiveEvent;
import com.ose.system.ReceiveEventActionPoint;
import com.ose.system.ResetEvent;
import com.ose.system.SendEvent;
import com.ose.system.SendEventActionPoint;
import com.ose.system.ServiceException;
import com.ose.system.SwapEvent;
import com.ose.system.SwapEventActionPoint;
import com.ose.system.SystemModel;
import com.ose.system.SystemModelDumpFileWriter;
import com.ose.system.SystemModelEvent;
import com.ose.system.SystemModelListener;
import com.ose.system.Target;
import com.ose.system.TargetEvent;
import com.ose.system.TargetEvents;
import com.ose.system.TargetListener;
import com.ose.system.TimeoutEvent;
import com.ose.system.TimeoutEventActionPoint;
import com.ose.system.UserEvent;
import com.ose.system.UserEventActionPoint;
import com.ose.system.ui.util.StringUtils;

class EventSession extends Observable
{
   private static final int EVENT_TRACE_TIMEOUT = 500;

   private final Target target;
   private final int scopeType;
   private final int scopeId;
   private final long timestamp;
   private final EventEditorInput eventEditorInput;
   private final SystemModelListener systemModelHandler;
   private final File eventTraceDumpFile;
   private final File eventNotifyDumpFile;
   private final Object openLock;
   private final Object eventTraceLock;
   private final Object eventNotifyLock;

   private boolean open;
   // The event editor reference must only be accessed from the UI thread.
   private EventEditor eventEditor;
   private volatile String eventActions;
   private volatile boolean eventTraceEnabled;
   private volatile boolean eventTraceStarted;
   private volatile boolean eventNotifyEnabled;
   private EventTraceJob eventTraceJob;
   private EventNotifyHandler eventNotifyHandler;

   EventSession(Target target,
                int scopeType,
                int scopeId,
                boolean persistentTraceEventActions)
      throws IOException
   {
      if (target == null)
      {
         throw new IllegalArgumentException();
      }
      this.target = target;
      this.scopeType = scopeType;
      this.scopeId = scopeId;
      timestamp = System.currentTimeMillis();
      eventEditorInput = new EventEditorInput(
         target, StringUtils.toScopeString(scopeType, scopeId), timestamp);
      systemModelHandler = new SystemModelHandler();
      eventTraceDumpFile = createEventDumpFile("event-trace", timestamp);
      eventNotifyDumpFile = createEventDumpFile("event-notify", timestamp);
      openLock = new Object();
      eventTraceLock = new Object();
      eventNotifyLock = new Object();
      eventActions = "Unknown";
      target.attach(new NullProgressMonitor(),
                    scopeType,
                    scopeId,
                    scopeType,
                    scopeId,
                    persistentTraceEventActions);
      if (hasGetEventActionsSupport())
      {
         EventActionPoint[] eventActionPoints =
            target.getEventActionPoints(new NullProgressMonitor());
         if (eventActionPoints.length == 0)
         {
            eventActions = "None";
         }
      }
      if (target.hasEventTraceEnabledSupport() &&
          target.isEventTraceEnabled(new NullProgressMonitor()))
      {
         setEventTraceEnabled(true);
      }
      SystemModel.getInstance().addSystemModelListener(systemModelHandler);
      open = true;
      if (target.isKilled() || !target.isAttached())
      {
         close(true);
         throw new IOException("Target is disconnected or detached");
      }
      openEventEditor();
   }

   public boolean isOpen()
   {
      synchronized (openLock)
      {
         return open;
      }
   }

   public void close(boolean deleteDumpFiles)
   {
      synchronized (openLock)
      {
         if (open)
         {
            SystemModel.getInstance().removeSystemModelListener(systemModelHandler);
            stopEventTrace();
            try
            {
               setEventNotifyEnabled(new NullProgressMonitor(), false);
            } catch (Exception ignore) {}
            try
            {
               target.detach(new NullProgressMonitor(), true);
            } catch (Exception ignore) {}
            open = false;
            eventTraceEnabled = false;
            eventTraceStarted = false;
            eventNotifyEnabled = false;
            eventTraceJob = null;
            eventNotifyHandler = null;
            setChanged();
            notifyObservers();
            deleteObservers();
         }
         if (deleteDumpFiles)
         {
            eventTraceDumpFile.delete();
            eventNotifyDumpFile.delete();
         }
      }
   }

   public Target getTarget()
   {
      return target;
   }

   public int getScopeType()
   {
      return scopeType;
   }

   public int getScopeId()
   {
      return scopeId;
   }

   public long getTimestamp()
   {
      return timestamp;
   }

   public EventEditorInput getEventEditorInput()
   {
      return eventEditorInput;
   }

   public boolean isEventTraceEnabled()
   {
      return eventTraceEnabled;
   }

   public boolean isEventTraceStarted()
   {
      return eventTraceStarted;
   }

   public boolean isEventNotifyEnabled()
   {
      return eventNotifyEnabled;
   }

   public boolean eventTraceDumpFileExists()
   {
      return eventTraceDumpFile.isFile();
   }

   public boolean eventNotifyDumpFileExists()
   {
      return eventNotifyDumpFile.isFile();
   }

   private void setEventTraceEnabled(boolean enabled)
   {
      this.eventTraceEnabled = enabled;
      setChanged();
      notifyObservers();
   }

   private void setEventTraceStarted(boolean started)
   {
      this.eventTraceStarted = started;
      setChanged();
      notifyObservers();
   }

   private void setEventNotifyEnabled(boolean enabled)
   {
      this.eventNotifyEnabled = enabled;
      setChanged();
      notifyObservers();
   }

   public void openEventEditor()
   {
      asyncExec(new OpenEventEditorRunner());
   }

   public void activateEventEditor()
   {
      asyncExec(new ActivateEventEditorRunner());
   }

   public void closeEventEditor()
   {
      asyncExec(new CloseEventEditorRunner());
   }

   public boolean hasSetEventActionsSupport()
   {
      return (target.hasSetSendEventActionPointSupport() ||
              target.hasSetReceiveEventActionPointSupport() ||
              target.hasSetSwapEventActionPointSupport() ||
              target.hasSetCreateEventActionPointSupport() ||
              target.hasSetKillEventActionPointSupport() ||
              target.hasSetErrorEventActionPointSupport() ||
              target.hasSetUserEventActionPointSupport() ||
              target.hasSetBindEventActionPointSupport() ||
              target.hasSetAllocEventActionPointSupport() ||
              target.hasSetFreeEventActionPointSupport() ||
              target.hasSetTimeoutEventActionPointSupport()) &&
             target.hasClearEventActionPointSupport() &&
             (target.hasClearAllEventActionPointsSupport() ||
              target.hasGetEventActionPointsSupport() ||
              target.hasDeprecatedGetEventActionPointsSupport());
   }

   public boolean hasGetEventActionsSupport()
   {
      return target.hasGetEventActionPointsSupport() ||
             target.hasDeprecatedGetEventActionPointsSupport();
   }

   public boolean hasClearEventActionsSupport()
   {
      return target.hasClearEventActionPointSupport() &&
             (target.hasClearAllEventActionPointsSupport() ||
              target.hasGetEventActionPointsSupport() ||
              target.hasDeprecatedGetEventActionPointsSupport());
   }

   public boolean hasEventTraceEnabledSupport()
   {
      return target.hasEventTraceEnabledSupport();
   }

   public boolean hasGetEventTraceSupport()
   {
      return target.hasGetEventTraceSupport();
   }

   public boolean hasStartStopEventTraceSupport()
   {
      return target.hasGetEventTraceMultipleSupport();
   }

   public boolean hasClearEventTraceSupport()
   {
      return target.hasClearEventTraceSupport();
   }

   public boolean hasEventNotifyEnabledSupport()
   {
      return target.hasEventNotifyEnabledSupport();
   }

   public boolean hasInterceptResumeSupport()
   {
      return target.hasInterceptResumeSupport();
   }

   public void setEventActions(IProgressMonitor monitor, String file)
      throws IOException, ParserConfigurationException, SAXException
   {
      if (isOpen())
      {
         File xmlFile;
         EventActionXMLReader eventActionXMLReader;
         EventActions newEventActions;
         List newEventActionPoints;
         Set unsupportedEventActionPointNames;

         xmlFile = new File(file);
         eventActionXMLReader = new EventActionXMLReader();
         newEventActions = eventActionXMLReader.read(xmlFile);
         newEventActionPoints = newEventActions.getEventActions();

         if (!target.hasSignalDataFilterFeature() 
               &&  newEventActions.isHasSignalDataFilterTag())
         {
            // Cancel the operation.
            asyncExec(new ShowErrorDialogRunner("Target is not supporting " +
                  "the usage of signal filters. Some of the event actions " + 
                  "are using the signal filter. Uncheck the " +
                  " \"Use Data Filter\" checkbox for the event actions " +
                  "using the signal filter and set the event action settings " +
                  "to target. The operation will be aborted.", null));
            return;
         }
         unsupportedEventActionPointNames =
            filterEventActionPoints(newEventActionPoints);
         if (!unsupportedEventActionPointNames.isEmpty())
         {
            String[] unsupportedEventActions;
            UnsupportedEventActionsDialogRunner dialogRunner;

            if (newEventActionPoints.isEmpty())
            {
               // Cancel the operation.
               asyncExec(new ShowErrorDialogRunner("None of the requested " +
                     "event actions are supported by this target. The " +
                     "operation will be aborted.", null));
               return;
            }

            unsupportedEventActions = (String[])
               unsupportedEventActionPointNames.toArray(new String[0]);
            dialogRunner =
               new UnsupportedEventActionsDialogRunner(unsupportedEventActions);
            syncExec(dialogRunner);
            if (!dialogRunner.isOk())
            {
               // Cancel the operation.
               return;
            }
         }

         if (target.hasClearAllEventActionPointsSupport())
         {
            target.clearEventActionPoint(monitor, null);
         }
         else
         {
            EventActionPoint[] oldEventActionPoints =
               target.getEventActionPoints(monitor);
            for (int i = 0; i < oldEventActionPoints.length; i++)
            {
               target.clearEventActionPoint(monitor, oldEventActionPoints[i]);
            }
         }

         if (target.hasEventStateSupport())
         {
            target.setEventState(monitor, newEventActions.getInitState());
         }

         try
         {
            for (Iterator i = newEventActionPoints.iterator(); i.hasNext();)
            {
               target.setEventActionPoint(monitor, (EventActionPoint) i.next());
            }

            eventActions = xmlFile.getName();
            asyncExec(new SetEventActionsRunner(eventActions));
         }
         catch (ServiceException e)
         {
            clearEventActions(monitor);
            throw e;
         }
      }
   }

   public void getEventActions(IProgressMonitor monitor, String file)
      throws IOException
   {
      if (isOpen())
      {
         EventActionPoint[] eventActionPoints;
         File newFile;
         EventActionXMLWriter eventActionXMLWriter;

         eventActionPoints = target.getEventActionPoints(monitor);
         newFile = new File(file);
         eventActionXMLWriter = new EventActionXMLWriter(newFile);
         try
         {
            for (int i = 0; i < eventActionPoints.length; i++)
            {
               eventActionXMLWriter.write(eventActionPoints[i]);
            }
         }
         finally
         {
            eventActionXMLWriter.close();
         }

         refreshWorkspaceFile(monitor, newFile);
         eventActions = newFile.getName();
         asyncExec(new SetEventActionsRunner(eventActions));
      }
   }

   public void clearEventActions(IProgressMonitor monitor) throws IOException
   {
      if (isOpen())
      {
         if (target.hasClearAllEventActionPointsSupport())
         {
            target.clearEventActionPoint(monitor, null);
         }
         else
         {
            EventActionPoint[] eventActionPoints =
               target.getEventActionPoints(monitor);
            for (int i = 0; i < eventActionPoints.length; i++)
            {
               target.clearEventActionPoint(monitor, eventActionPoints[i]);
            }
         }

         eventActions = "None";
         asyncExec(new SetEventActionsRunner(eventActions));
      }
   }

   public void setEventTraceEnabled(IProgressMonitor monitor, boolean enabled)
      throws IOException
   {
      if (isOpen())
      {
         target.setEventTraceEnabled(monitor, enabled);
         setEventTraceEnabled(enabled);
      }
   }

   public void getEventTrace(IProgressMonitor monitor, int from, int to)
      throws IOException
   {
      if (isOpen())
      {
         SystemModelDumpFileWriter eventTraceDumpFileWriter;
         TargetEvent[] events;
         boolean enabled = false;

         eventTraceDumpFileWriter =
            createEventDumpFileWriter(eventTraceDumpFile, true);

         try
         {
            events = target.getEventTrace(monitor, from, to);

            for (int i = 0; i < events.length; i++)
            {
               eventTraceDumpFileWriter.writeEvent(events[i]);
            }
         }
         finally
         {
            closeEventDumpFileWriter(eventTraceDumpFileWriter, eventTraceDumpFile);
         }

         asyncExec(new SetTraceEventsRunner(events));
         if (hasEventTraceEnabledSupport())
         {
            enabled = target.isEventTraceEnabled(monitor);
         }
         setEventTraceEnabled(enabled);
      }
   }

   public void startEventTrace()
   {
      if (isOpen())
      {
         synchronized (eventTraceLock)
         {
            if ((eventTraceJob == null) || eventTraceJob.isKilled())
            {
               eventTraceJob = new EventTraceJob(EVENT_TRACE_TIMEOUT);
               eventTraceJob.schedule();
            }
         }
      }
   }

   public void stopEventTrace()
   {
      synchronized (eventTraceLock)
      {
         if ((eventTraceJob != null) && !eventTraceJob.isKilled())
         {
            eventTraceJob.stop();
            eventTraceJob = null;
         }
      }
   }

   public void saveEventTrace(IProgressMonitor monitor, String file)
      throws IOException
   {
      saveFile(monitor, eventTraceDumpFile, new File(file), true);
   }

   public void clearEventTrace(IProgressMonitor monitor) throws IOException
   {
      if (isOpen())
      {
         target.clearEventTrace(monitor);
      }
   }

   public void setEventNotifyEnabled(IProgressMonitor monitor, boolean enabled)
      throws IOException
   {
      synchronized (eventNotifyLock)
      {
         if (enabled)
         {
            if (isOpen() && (eventNotifyHandler == null))
            {
               eventNotifyHandler = new EventNotifyHandler(monitor);
            }
         }
         else
         {
            if (eventNotifyHandler != null)
            {
               eventNotifyHandler.close();
               eventNotifyHandler = null;
            }
         }
      }
   }

   public void saveEventNotify(IProgressMonitor monitor, String file)
      throws IOException
   {
      saveFile(monitor, eventNotifyDumpFile, new File(file), false);
   }

   public void intercept(IProgressMonitor monitor) throws IOException
   {
      if (isOpen())
      {
         target.intercept(monitor, scopeType, scopeId);
      }
   }

   public void resume(IProgressMonitor monitor) throws IOException
   {
      if (isOpen())
      {
         target.resume(monitor, scopeType, scopeId);
      }
   }

   private Set filterEventActionPoints(List eventActionPoints)
   {
      Set unsupportedEventActionPointNames = new HashSet();

      for (Iterator i = eventActionPoints.iterator(); i.hasNext();)
      {
         EventActionPoint eventActionPoint = (EventActionPoint) i.next();

         if (eventActionPoint instanceof SendEventActionPoint)
         {
            if (!target.hasSetSendEventActionPointSupport())
            {
               i.remove();
               unsupportedEventActionPointNames.add("Send");
            }
         }
         else if (eventActionPoint instanceof ReceiveEventActionPoint)
         {
            if (!target.hasSetReceiveEventActionPointSupport())
            {
               i.remove();
               unsupportedEventActionPointNames.add("Receive");
            }
         }
         else if (eventActionPoint instanceof SwapEventActionPoint)
         {
            if (!target.hasSetSwapEventActionPointSupport())
            {
               i.remove();
               unsupportedEventActionPointNames.add("Swap");
            }
            else if (eventActionPoint.getAction() == EventActionPoint.ACTION_NOTIFY)
            {
               i.remove();
               unsupportedEventActionPointNames.add("Swap/Notify");
            }
            else if (eventActionPoint.getAction() == EventActionPoint.ACTION_INTERCEPT)
            {
               i.remove();
               unsupportedEventActionPointNames.add("Swap/Intercept");
            }
         }
         else if (eventActionPoint instanceof CreateEventActionPoint)
         {
            if (!target.hasSetCreateEventActionPointSupport())
            {
               i.remove();
               unsupportedEventActionPointNames.add("Create");
            }
         }
         else if (eventActionPoint instanceof KillEventActionPoint)
         {
            if (!target.hasSetKillEventActionPointSupport())
            {
               i.remove();
               unsupportedEventActionPointNames.add("Kill");
            }
         }
         else if (eventActionPoint instanceof ErrorEventActionPoint)
         {
            if (!target.hasSetErrorEventActionPointSupport())
            {
               i.remove();
               unsupportedEventActionPointNames.add("Error");
            }
         }
         else if (eventActionPoint instanceof BindEventActionPoint)
         {
            if (!target.hasSetBindEventActionPointSupport())
            {
               i.remove();
               unsupportedEventActionPointNames.add("Bind");
            }
         }
         else if (eventActionPoint instanceof AllocEventActionPoint)
         {
            if (!target.hasSetAllocEventActionPointSupport())
            {
               i.remove();
               unsupportedEventActionPointNames.add("Alloc");
            }
         }
         else if (eventActionPoint instanceof FreeEventActionPoint)
         {
            if (!target.hasSetFreeEventActionPointSupport())
            {
               i.remove();
               unsupportedEventActionPointNames.add("Free");
            }
         }
         else if (eventActionPoint instanceof TimeoutEventActionPoint)
         {
            if (!target.hasSetTimeoutEventActionPointSupport())
            {
               i.remove();
               unsupportedEventActionPointNames.add("Timeout");
            }
         }
         else if (eventActionPoint instanceof UserEventActionPoint)
         {
            if (!target.hasSetUserEventActionPointSupport())
            {
               i.remove();
               unsupportedEventActionPointNames.add("User");
            }
         }
         else
         {
            i.remove();
            unsupportedEventActionPointNames.add("Unknown");
         }
      }

      return unsupportedEventActionPointNames;
   }

   private SystemModelDumpFileWriter createEventDumpFileWriter(File file,
                                                               boolean trace)
      throws IOException
   {
      SystemModelDumpFileWriter eventDumpFileWriter = null;

      if (file == null)
      {
         throw new IllegalArgumentException();
      }

      try
      {
         int dumpId;

         dumpId = (int) (timestamp / 1000L);
         eventDumpFileWriter = new SystemModelDumpFileWriter(file,
                                                             target.isBigEndian(),
                                                             dumpId);
         eventDumpFileWriter.writeSystemInfo(target.getOsType(),
                                             target.getCpuType(),
                                             target.getName(),
                                             target.getNumExecutionUnits(),
                                             target.getTickLength(),
                                             target.getNTickFrequency(),
                                             true);
         eventDumpFileWriter.startWritingEvents(
               target.toString(),
               StringUtils.toScopeString(scopeType, scopeId),
               eventActions,
               trace,
               true);
         return eventDumpFileWriter;
      }
      catch (IOException e)
      {
         if (eventDumpFileWriter != null)
         {
            eventDumpFileWriter.close();
         }
         file.delete();
         throw e;
      }
   }

   private static void closeEventDumpFileWriter(
         SystemModelDumpFileWriter eventDumpFileWriter,
         File file)
      throws IOException
   {
      boolean success = false;

      if ((eventDumpFileWriter == null) || (file == null))
      {
         throw new IllegalArgumentException();
      }

      try
      {
         eventDumpFileWriter.endWritingEvents(0);
         success = true;
      }
      finally
      {
         eventDumpFileWriter.close();
         if (!success)
         {
            file.delete();
         }
      }
   }

   private static File createEventDumpFile(String prefix, long id)
   {
      File pluginStateDir;
      String fileName;

      if (prefix == null)
      {
         throw new IllegalArgumentException();
      }

      pluginStateDir = EventPlugin.getDefault().getStateLocation().toFile();
      fileName = prefix + "-" + id + ".pmd";
      return new File(pluginStateDir, fileName);
   }

   private static void saveFile(IProgressMonitor monitor,
                                File from,
                                File to,
                                boolean trace)
      throws IOException
   {
      if ((monitor == null) || (from == null) || (to == null))
      {
         throw new IllegalArgumentException();
      }

      if (to.getName().toLowerCase().endsWith(".event"))
      {
         EventDumpXMLConverter eventDumpXMLConverter =
               new EventDumpXMLConverter();
         eventDumpXMLConverter.convert(monitor, from, to, trace);
      }
      else
      {
         copyFile(from, to);
      }

      refreshWorkspaceFile(monitor, to);
   }

   private static void copyFile(File from, File to) throws IOException
   {
      FileInputStream in = null;
      FileOutputStream out = null;

      if ((from == null) || (to == null))
      {
         throw new IllegalArgumentException();
      }

      try
      {
         byte[] buffer = new byte[8192];
         int length;

         in = new FileInputStream(from);
         out = new FileOutputStream(to);

         while ((length = in.read(buffer)) != -1)
         {
            out.write(buffer, 0, length);
         }
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
         if (out != null)
         {
            try
            {
               out.close();
            } catch (IOException ignore) {}
         }
      }
   }

   private static void refreshWorkspaceFile(IProgressMonitor monitor, File file)
      throws IOException
   {
      if (file == null)
      {
         throw new IllegalArgumentException();
      }

      if (file.isFile())
      {
         IFile workspaceFile = ResourcesPlugin.getWorkspace().getRoot().
            getFileForLocation(Path.fromOSString(file.getAbsolutePath()));
         if (workspaceFile != null)
         {
            try
            {
               workspaceFile.refreshLocal(IResource.DEPTH_ZERO, monitor);
            }
            catch (CoreException e)
            {
               throw new IOException(e.getMessage());
            }
         }
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

   private static void asyncExec(Runnable runnable)
   {
      Display display = PlatformUI.getWorkbench().getDisplay();
      if (!display.isDisposed())
      {
         display.asyncExec(runnable);
      }
   }

   private class SystemModelHandler implements SystemModelListener
   {
      public void nodesChanged(SystemModelEvent event)
      {
         if ((event.getParent() == target.getParent()) &&
             (event.getChildren().contains(target)))
         {
            if (target.isKilled() || !target.isAttached())
            {
               close(false);
            }
         }
      }

      public void nodesAdded(SystemModelEvent event)
      {
         // Nothing to do.
      }

      public void nodesRemoved(SystemModelEvent event)
      {
         // Nothing to do.
      }
   }

   private class EventNotifyHandler implements TargetListener
   {
      private final SystemModelDumpFileWriter eventNotifyDumpFileWriter;

      EventNotifyHandler(IProgressMonitor monitor) throws IOException
      {
         eventNotifyDumpFileWriter =
            createEventDumpFileWriter(eventNotifyDumpFile, false);

         try
         {
            syncExec(new SetNotifySortingEnablementRunner(false));
            syncExec(new ClearNotifyEventsRunner());
            target.addTargetListener(this);
            target.setEventNotifyEnabled(monitor, true);
            setEventNotifyEnabled(true);
         }
         catch (IOException e)
         {
            target.removeTargetListener(this);
            asyncExec(new SetNotifySortingEnablementRunner(true));
            try
            {
               closeEventDumpFileWriter(eventNotifyDumpFileWriter, eventNotifyDumpFile);
            } catch (IOException ignore) {}
            throw e;
         }
      }

      public void close() throws IOException
      {
         try
         {
            target.removeTargetListener(this);
            target.setEventNotifyEnabled(new NullProgressMonitor(), false);
         }
         finally
         {
            setEventNotifyEnabled(false);
            asyncExec(new SetNotifySortingEnablementRunner(true));
            closeEventDumpFileWriter(eventNotifyDumpFileWriter, eventNotifyDumpFile);
         }
      }

      public void signalSent(SendEvent event)
      {
         handleEvent(event);
      }

      public void signalReceived(ReceiveEvent event)
      {
         handleEvent(event);
      }

      public void processSwapped(SwapEvent event)
      {
         handleEvent(event);
      }

      public void processCreated(CreateEvent event)
      {
         handleEvent(event);
      }

      public void processKilled(KillEvent event)
      {
         handleEvent(event);
      }

      public void errorCalled(ErrorEvent event)
      {
         handleEvent(event);
      }

      public void targetReset(ResetEvent event)
      {
         handleEvent(event);
      }

      public void userReported(UserEvent event)
      {
         handleEvent(event);
      }

      public void processBound(BindEvent event)
      {
         handleEvent(event);
      }

      public void signalAllocated(AllocEvent event)
      {
         handleEvent(event);
      }

      public void signalFreed(FreeEvent event)
      {
         handleEvent(event);
      }

      public void systemCallTimedout(TimeoutEvent event)
      {
         handleEvent(event);
      }

      public void eventsLost(LossEvent event)
      {
         handleEvent(event);
      }

      public void scopeIntercepted(SendEvent event)
      {
         handleEvent(event);
      }

      public void scopeIntercepted(ReceiveEvent event)
      {
         handleEvent(event);
      }

      public void scopeIntercepted(SwapEvent event)
      {
         handleEvent(event);
      }

      public void scopeIntercepted(CreateEvent event)
      {
         handleEvent(event);
      }

      public void scopeIntercepted(KillEvent event)
      {
         handleEvent(event);
      }

      public void scopeIntercepted(ErrorEvent event)
      {
         handleEvent(event);
      }

      public void scopeIntercepted(UserEvent event)
      {
         handleEvent(event);
      }

      public void scopeIntercepted(BindEvent event)
      {
         handleEvent(event);
      }

      public void scopeIntercepted(AllocEvent event)
      {
         handleEvent(event);
      }

      public void scopeIntercepted(FreeEvent event)
      {
         handleEvent(event);
      }

      public void scopeIntercepted(TimeoutEvent event)
      {
         handleEvent(event);
      }

      private void handleEvent(TargetEvent event)
      {
         try
         {
            eventNotifyDumpFileWriter.writeEvent(event);
            syncExec(new AddNotifyEventsRunner(new TargetEvent[] {event}));
         }
         catch (IOException e)
         {
            asyncExec(new ShowErrorDialogRunner("Error storing event notifications", e));
            try
            {
               close();
            } catch  (IOException ignore) {}
            eventNotifyHandler = null;
         }
      }
   }

   private class EventTraceJob extends Job
   {
      private static final int STOP_TIMEOUT = 10000;

      private final Object stopLock = new Object();
      private final int timeout;
      private volatile boolean stopped;
      private volatile boolean killed;

      EventTraceJob(int timeout)
      {
         super("Reading events from target");
         setPriority(LONG);
         this.timeout = timeout;
      }

      boolean isKilled()
      {
         return killed;
      }

      protected IStatus run(IProgressMonitor monitor)
      {
         try
         {
            monitor.beginTask(getName(), IProgressMonitor.UNKNOWN);
            setEventTraceStarted(true);
            syncExec(new SetTraceSortingEnablementRunner(false));
            syncExec(new ClearTraceEventsRunner());
            retrieveEvents(monitor);
            return (monitor.isCanceled() ?
                    Status.CANCEL_STATUS : Status.OK_STATUS);
         }
         catch (OperationCanceledException e)
         {
            return Status.CANCEL_STATUS;
         }
         catch (ServiceException e)
         {
            return EventPlugin.createErrorStatus(
               "Error reported from target when reading events from the target", e);
         }
         catch (IOException e)
         {
            return EventPlugin.createErrorStatus(
               "Error communicating with target when reading events from the target", e);
         }
         catch (Exception e)
         {
            return EventPlugin.createErrorStatus(
               "Error when reading events from the target", e);
         }
         finally
         {
            setEventTraceStarted(false);
            asyncExec(new SetTraceSortingEnablementRunner(true));
            monitor.done();
            synchronized (stopLock)
            {
               killed = true;
               stopLock.notifyAll();
            }
         }
      }

      private void retrieveEvents(IProgressMonitor monitor) throws IOException
      {
         SystemModelDumpFileWriter eventTraceDumpFileWriter;
         EventTraceHandle handle = new EventTraceHandle();

         eventTraceDumpFileWriter =
            createEventDumpFileWriter(eventTraceDumpFile, true);

         try
         {
            while (!stopped && !monitor.isCanceled())
            {
               TargetEvents eventInfo;

               eventInfo = target.getEventTraceMultiple(monitor, timeout, handle);
               if (eventInfo != null)
               {
                  TargetEvent[] events = eventInfo.getEvents();
   
                  if (events.length > 0)
                  {
                     for (int i = 0; i < events.length; i++)
                     {
                        eventTraceDumpFileWriter.writeEvent(events[i]);
                     }
                     syncExec(new AddTraceEventsRunner(events));
                  }
                  if (eventInfo.isEnabled() != isEventTraceEnabled())
                  {
                     setEventTraceEnabled(eventInfo.isEnabled());
                  }
               }
               else
               {
                  monitor.setCanceled(true);
               }
               // On Solaris, the event trace thread may starve other threads.
               // Yield to allow other threads to execute.
               Thread.yield();
            }
         }
         finally
         {
            closeEventDumpFileWriter(eventTraceDumpFileWriter, eventTraceDumpFile);
         }
      }

      void stop()
      {
         stopped = true;

         if (!killed)
         {
            synchronized (stopLock)
            {
               try
               {
                  stopLock.wait(STOP_TIMEOUT);
               } catch (InterruptedException e) {}
            }

            if (!killed)
            {
               cancel();
            }
         }
      }
   }

   private class ShowErrorDialogRunner implements Runnable
   {
      private final String message;
      private final Throwable t;

      ShowErrorDialogRunner(String message, Throwable t)
      {
         if (message == null)
         {
            throw new IllegalArgumentException();
         }
         this.message = message;
         this.t = t;
      }

      public void run()
      {
         if (t != null)
         {
            EventPlugin.errorDialog(null, message, t);
         }
         else
         {
            MessageDialog.openError(null, "Error", message);
         }
      }
   }

   private class UnsupportedEventActionsDialogRunner implements Runnable
   {
      private final String[] unsupportedEventActions;
      private volatile boolean ok;

      UnsupportedEventActionsDialogRunner(String[] unsupportedEventActions)
      {
         if (unsupportedEventActions == null)
         {
            throw new IllegalArgumentException();
         }
         this.unsupportedEventActions = unsupportedEventActions;
      }

      public void run()
      {
         Shell shell;
         Dialog dialog;
         int status;

         shell = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell();
         dialog = new UnsupportedEventActionsDialog(shell,
                                                    unsupportedEventActions);
         status = dialog.open();
         ok = (status == Dialog.OK);
      }

      public boolean isOk()
      {
         return ok;
      }
   }

   private abstract class EventEditorRunner implements Runnable
   {
      protected boolean isEventEditorOpen()
      {
         return ((eventEditor != null) && !eventEditor.isDisposed());
      }
   }

   private class OpenEventEditorRunner extends EventEditorRunner
   {
      public void run()
      {
         if (!isEventEditorOpen())
         {
            IWorkbenchPage page = PlatformUI.getWorkbench()
               .getActiveWorkbenchWindow().getActivePage();
            if (page != null)
            {
               try
               {
                  eventEditor = (EventEditor) page.openEditor(
                        eventEditorInput, EventPlugin.EVENT_EDITOR_ID);
                  eventEditor.setEventActions(eventActions);
               }
               catch (PartInitException e)
               {
                  EventPlugin.log(e);
               }
            }
         }
      }
   }

   private class ActivateEventEditorRunner extends EventEditorRunner
   {
      public void run()
      {
         if (isEventEditorOpen())
         {
            IWorkbenchPage page = PlatformUI.getWorkbench()
               .getActiveWorkbenchWindow().getActivePage();
            if (page != null)
            {
               page.bringToTop(eventEditor);
            }
         }
      }
   }

   private class CloseEventEditorRunner extends EventEditorRunner
   {
      public void run()
      {
         if (isEventEditorOpen())
         {
            IWorkbenchWindow workbenchWindow =
               PlatformUI.getWorkbench().getActiveWorkbenchWindow();
            if (workbenchWindow != null)
            {
               IWorkbenchPage page = workbenchWindow.getActivePage();
               if (page != null)
               {
                  page.closeEditor(eventEditor, false);
                  eventEditor = null;
               }
            }
         }
      }
   }

   private class SetEventActionsRunner extends EventEditorRunner
   {
      private final String eventActions;

      SetEventActionsRunner(String eventActions)
      {
         if (eventActions == null)
         {
            throw new IllegalArgumentException();
         }
         this.eventActions = eventActions;
      }

      public void run()
      {
         if (isEventEditorOpen())
         {
            eventEditor.setEventActions(eventActions);
         }
      }
   }

   private class SetTraceEventsRunner extends EventEditorRunner
   {
      private final TargetEvent[] events;

      SetTraceEventsRunner(TargetEvent[] events)
      {
         if (events == null)
         {
            throw new IllegalArgumentException();
         }
         this.events = events;
      }

      public void run()
      {
         if (isEventEditorOpen())
         {
            eventEditor.setTraceSortingEnabled(false);
            eventEditor.clearTraceEvents();
            eventEditor.addTraceEvents(events);
            eventEditor.setTraceSortingEnabled(true);
         }
      }
   }

   private class SetTraceSortingEnablementRunner extends EventEditorRunner
   {
      private final boolean enabled;

      SetTraceSortingEnablementRunner(boolean enabled)
      {
         this.enabled = enabled;
      }

      public void run()
      {
         if (isEventEditorOpen())
         {
            eventEditor.setTraceSortingEnabled(enabled);
         }
      }
   }

   private class ClearTraceEventsRunner extends EventEditorRunner
   {
      public void run()
      {
         if (isEventEditorOpen())
         {
            eventEditor.clearTraceEvents();
         }
      }
   }

   private class AddTraceEventsRunner extends EventEditorRunner
   {
      private final TargetEvent[] events;

      AddTraceEventsRunner(TargetEvent[] events)
      {
         if (events == null)
         {
            throw new IllegalArgumentException();
         }
         this.events = events;
      }

      public void run()
      {
         if (isEventEditorOpen())
         {
            eventEditor.addTraceEvents(events);
         }
      }
   }

   private class SetNotifySortingEnablementRunner extends EventEditorRunner
   {
      private final boolean enabled;

      SetNotifySortingEnablementRunner(boolean enabled)
      {
         this.enabled = enabled;
      }

      public void run()
      {
         if (isEventEditorOpen())
         {
            eventEditor.setNotifySortingEnabled(enabled);
         }
      }
   }

   private class ClearNotifyEventsRunner extends EventEditorRunner
   {
      public void run()
      {
         if (isEventEditorOpen())
         {
            eventEditor.clearNotifyEvents();
         }
      }
   }

   private class AddNotifyEventsRunner extends EventEditorRunner
   {
      private final TargetEvent[] events;

      AddNotifyEventsRunner(TargetEvent[] events)
      {
         if (events == null)
         {
            throw new IllegalArgumentException();
         }
         this.events = events;
      }

      public void run()
      {
         if (isEventEditorOpen())
         {
            eventEditor.addNotifyEvents(events);
         }
      }
   }
}
