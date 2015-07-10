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

package com.ose.system.ui.views.system;

import java.io.IOException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.IJobChangeEvent;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.core.runtime.jobs.JobChangeAdapter;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.ui.progress.WorkbenchJob;
import com.ose.plugin.control.LicenseException;
import com.ose.plugin.control.LicenseManager;
import com.ose.system.ServiceException;
import com.ose.system.SystemModel;
import com.ose.system.SystemModelEvent;
import com.ose.system.SystemModelListener;
import com.ose.system.SystemModelNode;
import com.ose.system.Target;
import com.ose.system.ui.SystemBrowserPlugin;
import com.ose.system.ui.util.BackgroundImageHandler;

class SystemContentProvider
   implements ITreeContentProvider, SystemModelListener
{
   private static final SystemModelNode[] EMPTY_ARRAY = new SystemModelNode[0];

   private SystemModel systemModel = SystemModel.getInstance();

   private TreeViewer viewer;

   private BackgroundImageHandler backgroundImageHandler;

   public SystemModel getInitialInput()
   {
      return systemModel;
   }

   public void dispose()
   {
      // Nothing to be done.
   }

   public void inputChanged(Viewer viewer, Object oldInput, Object newInput)
   {
      this.viewer = (TreeViewer) viewer;
      if (oldInput instanceof SystemModel)
      {
         ((SystemModel) oldInput).removeSystemModelListener(this);
      }
      if (newInput instanceof SystemModel)
      {
         ((SystemModel) newInput).addSystemModelListener(this);
      }
   }

   public Object[] getElements(Object element)
   {
      return getChildren(element);
   }

   public Object[] getChildren(Object element)
   {
      if (element instanceof SystemModelNode)
      {
         SystemModelNode parent = (SystemModelNode) element;
         SystemModelNode[] children = parent.getChildren();
         if (children.length > 0)
         {
            return children;
         }
         else
         {
            Object placeholder = new PendingUpdatePlaceholder();
            Job job = new UpdateJob(parent);
            job.addJobChangeListener(new UpdateJobDoneHandler(placeholder));
            job.schedule();
            return (new Object[] {placeholder});
         }
      }
      else
      {
         return EMPTY_ARRAY;
      }
   }

   public Object getParent(Object element)
   {
      if (element instanceof SystemModelNode)
      {
         SystemModelNode child = (SystemModelNode) element;
         return child.getParent();
      }
      else
      {
         return null;
      }
   }

   public boolean hasChildren(Object element)
   {
      // Always return true if non-leaf node.
      if (element instanceof SystemModelNode)
      {
         SystemModelNode node = (SystemModelNode) element;
         return !node.isLeaf();
      }
      else
      {
         return false;
      }
   }

   public void nodesChanged(SystemModelEvent event)
   {
      viewer.getTree().getDisplay().asyncExec(
            new UpdateTreeViewerRunner(event, false));
   }

   public void nodesAdded(SystemModelEvent event)
   {
      viewer.getTree().getDisplay().asyncExec(
            new UpdateTreeViewerRunner(event, true));
   }

   public void nodesRemoved(SystemModelEvent event)
   {
      viewer.getTree().getDisplay().asyncExec(
            new UpdateTreeViewerRunner(event, true));
   }

   void setBackgroundImageHandler(BackgroundImageHandler backgroundImageHandler)
   {
      this.backgroundImageHandler = backgroundImageHandler;
   }

   static void checkoutLicense(Target target) throws LicenseException
   {
      String name;
      String version;
      long cookie;
      long r;
      long t;

      if (target.getOsType() == Target.OS_OSE_CK)
      {
         name = "com.ose.ck.system.ui";
         version = "1.000";
      }
      else
      {
         name = "com.ose.system.ui";
         version = "1.100";
      }
      cookie = (long) (Math.random() * Long.MAX_VALUE);
      r = LicenseManager.getInstance().registerPlugin(name, version, cookie);
      t = System.currentTimeMillis() / 10000;
      if ((r != (cookie ^ t)) && (r != (cookie ^ (t - 1))))
      {
         throw new LicenseException("Incorrect response value");
      }
   }

   static class UpdateJob extends Job
   {
      private SystemModelNode node;

      UpdateJob(SystemModelNode node)
      {
         super("Updating " + node);
         setPriority(SHORT);
         this.node = node;
      }

      protected IStatus run(IProgressMonitor monitor)
      {
         try
         {
            monitor.beginTask(getName(), IProgressMonitor.UNKNOWN);
            if (node instanceof Target)
            {
               Target target = (Target) node;
               checkoutLicense(target);
               target.connect(monitor);
               if (monitor.isCanceled())
               {
                  return Status.CANCEL_STATUS;
               }
            }
            node.update(monitor, false);
            return (monitor.isCanceled() ?
                    Status.CANCEL_STATUS : Status.OK_STATUS);
         }
         catch (OperationCanceledException e)
         {
            return Status.CANCEL_STATUS;
         }
         catch (LicenseException e)
         {
            return SystemBrowserPlugin.createErrorStatus(
               "Could not check out a FLEXlm license for the " +
               SystemBrowserPlugin.PLUGIN_NAME +
               " for the selected target type.", e);
         }
         catch (ServiceException e)
         {
            return SystemBrowserPlugin.createErrorStatus(
               "Error reported from target when updating " + node, e);
         }
         catch (IOException e)
         {
            return SystemBrowserPlugin.createErrorStatus(
               "Error communicating with target when updating " + node, e);
         }
         catch (Exception e)
         {
            return SystemBrowserPlugin.createErrorStatus(
               "Error when updating " + node, e);
         }
         finally
         {
            monitor.done();
         }
      }
   }

   static class PendingUpdatePlaceholder
   {
      public String toString()
      {
         return " Updating...";
      }
   }

   class UpdateJobDoneHandler extends JobChangeAdapter
   {
      private Object placeholder;

      UpdateJobDoneHandler(Object placeholder)
      {
         this.placeholder = placeholder;
      }

      public void done(IJobChangeEvent event)
      {
         // Remove the placeholder if it is still there.
         Job job = new RemovePlaceholderJob(placeholder);
         job.schedule();
      }
   }

   class RemovePlaceholderJob extends WorkbenchJob
   {
      private Object placeholder;

      RemovePlaceholderJob(Object placeholder)
      {
         super("Remove pending update placeholder");
         setSystem(true);
         this.placeholder = placeholder;
      }

      public IStatus runInUIThread(IProgressMonitor monitor)
      {
         if (viewer.getControl().isDisposed())
         {
            return Status.CANCEL_STATUS;
         }
         else
         {
            viewer.remove(placeholder);
            return Status.OK_STATUS;
         }
      }
   }

   class UpdateTreeViewerRunner implements Runnable
   {
      private SystemModelEvent event;
      private boolean refresh;

      UpdateTreeViewerRunner(SystemModelEvent event, boolean refresh)
      {
         this.event = event;
         this.refresh = refresh;
      }

      public void run()
      {
         if (backgroundImageHandler != null)
         {
            backgroundImageHandler.dispose();
            backgroundImageHandler = null;
         }

         if (refresh)
         {
            viewer.refresh(event.getParent(), false);
         }
         else
         {
            viewer.update(event.getChildren().toArray(), null);
         }

         // Reselect the selected item to force an update of the properties view.
         ISelection selection = viewer.getSelection();
         if (selection != null)
         {
            viewer.setSelection(selection);
         }
      }
   }
}
