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

package com.ose.system.ui.editors;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IMenuListener;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IWorkbenchActionConstants;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.EditorPart;
import org.eclipse.ui.part.IShowInSource;
import org.eclipse.ui.part.IShowInTarget;
import org.eclipse.ui.part.IShowInTargetList;
import org.eclipse.ui.part.ShowInContext;
import com.ose.system.Block;
import com.ose.system.Gate;
import com.ose.system.Heap;
import com.ose.system.Pool;
import com.ose.system.Process;
import com.ose.system.Segment;
import com.ose.system.SystemModel;
import com.ose.system.SystemModelEvent;
import com.ose.system.SystemModelListener;
import com.ose.system.SystemModelNode;
import com.ose.system.Target;
import com.ose.system.ui.SystemBrowserPlugin;
import com.ose.system.ui.forms.BlockForm;
import com.ose.system.ui.forms.GateForm;
import com.ose.system.ui.forms.HeapForm;
import com.ose.system.ui.forms.IForm;
import com.ose.system.ui.forms.PoolForm;
import com.ose.system.ui.forms.ProcessForm;
import com.ose.system.ui.forms.SegmentForm;
import com.ose.system.ui.forms.TargetForm;
import com.ose.system.ui.views.system.SystemLabelProvider;

public class SystemModelNodeFormEditor extends EditorPart
   implements IShowInTargetList, IShowInSource, SystemModelListener
{
   private static final SystemLabelProvider SYSTEM_LABEL_PROVIDER =
      new SystemLabelProvider();
   private IForm form;
   private Action showInAction;

   public void init(IEditorSite site, IEditorInput input)
      throws PartInitException
   {
      if (!(input instanceof SystemModelNodeEditorInput))
      {
         throw new PartInitException("Invalid editor input");
      }

      SystemModelNode node =
         ((SystemModelNodeEditorInput) input).getSystemModelNode();
      if (!(node instanceof Gate) &&
          !(node instanceof Target) &&
          !(node instanceof Segment) &&
          !(node instanceof Pool) &&
          !(node instanceof Heap) &&
          !(node instanceof Block) &&
          !(node instanceof Process))
      {
         throw new PartInitException("Invalid editor input");
      }

      setSite(site);
      setInput(input);
      setPartName(input.getName());
      setTitleImage(SYSTEM_LABEL_PROVIDER.getImage(node));
   }

   public void createPartControl(Composite parent)
   {
      SystemModelNodeEditorInput input =
         (SystemModelNodeEditorInput) getEditorInput();
      SystemModelNode node = input.getSystemModelNode();

      if (node instanceof Gate)
      {
         form = new GateForm(this, (Gate) node);
      }
      else if (node instanceof Target)
      {
         form = new TargetForm(this, (Target) node);
      }
      else if (node instanceof Segment)
      {
         form = new SegmentForm(this, (Segment) node);
      }
      else if (node instanceof Pool)
      {
         form = new PoolForm(this, (Pool) node);
      }
      else if (node instanceof Heap)
      {
         form = new HeapForm(this, (Heap) node);
      }
      else if (node instanceof Block)
      {
         form = new BlockForm(this, (Block) node);
      }
      else if (node instanceof Process)
      {
         form = new ProcessForm(this, (Process) node);
      }
      else
      {
         // Shouldn't happen.
         throw new IllegalStateException("Invalid editor input");
      }

      form.createContents(parent);
      createActions();
      createContextMenu();
      SystemModel.getInstance().addSystemModelListener(this);
   }

   private void createActions()
   {
      showInAction = new ShowInAction();
   }

   private void createContextMenu()
   {
      MenuManager menuMgr;
      Menu menu;

      menuMgr = new MenuManager();
      menuMgr.setRemoveAllWhenShown(true);
      menuMgr.addMenuListener(new MenuHandler());
      menu = menuMgr.createContextMenu(form.getControl());
      form.getControl().setMenu(menu);
      getSite().registerContextMenu(menuMgr, getSite().getSelectionProvider());
   }

   public void dispose()
   {
      SystemModel.getInstance().removeSystemModelListener(this);
      form.dispose();
      super.dispose();
   }

   public void setFocus()
   {
      form.setFocus();
   }

   public boolean isDirty()
   {
      // Save not supported.
      return false;
   }

   public void doSave(IProgressMonitor monitor)
   {
      // Save not supported.
   }

   public void doSaveAs()
   {
      // Save not supported.
   }

   public boolean isSaveAsAllowed()
   {
      // Save not supported.
      return false;
   }

   public Object getAdapter(Class adapter)
   {
      return null;
   }

   public String[] getShowInTargetIds()
   {
      return new String[] {SystemBrowserPlugin.SYSTEM_VIEW_ID};
   }

   public ShowInContext getShowInContext()
   {
      SystemModelNodeEditorInput input =
         (SystemModelNodeEditorInput) getEditorInput();
      return new ShowInContext(input.getSystemModelNode(), null);
   }

   public void nodesChanged(SystemModelEvent event)
   {
      if (!form.getControl().isDisposed())
      {
         form.getControl().getDisplay().asyncExec(new UpdateEditorRunner(event));
      }
   }

   public void nodesAdded(SystemModelEvent event)
   {
      // Not applicable.
   }

   public void nodesRemoved(SystemModelEvent event)
   {
      if (!form.getControl().isDisposed())
      {
         form.getControl().getDisplay().asyncExec(new CloseEditorRunner(event));
      }
   }

   class MenuHandler implements IMenuListener
   {
      public void menuAboutToShow(IMenuManager manager)
      {
         manager.add(showInAction);
         manager.add(new Separator(IWorkbenchActionConstants.MB_ADDITIONS));
      }
   }

   class ShowInAction extends Action
   {
      ShowInAction()
      {
         super("Show in " + SystemBrowserPlugin.SYSTEM_VIEW_NAME);
      }

      public void run()
      {
         IWorkbenchPage page =
            PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
         if (page != null)
         {
            try
            {
               IViewPart view =
                  page.showView(SystemBrowserPlugin.SYSTEM_VIEW_ID);
               if (view instanceof IShowInTarget)
               {
                  IShowInTarget target = (IShowInTarget) view;
                  target.show(getShowInContext());
               }
            }
            catch (PartInitException e)
            {
               SystemBrowserPlugin.log(e);
            }
         }
      }
   }

   class UpdateEditorRunner implements Runnable
   {
      private SystemModelEvent event;

      UpdateEditorRunner(SystemModelEvent event)
      {
         this.event = event;
      }

      public void run()
      {
         SystemModelNodeEditorInput input =
            (SystemModelNodeEditorInput) getEditorInput();
         SystemModelNode node = input.getSystemModelNode();
         if ((event.getParent() == node.getParent()) &&
             (event.getChildren().contains(node)))
         {
            form.refresh();
            setTitleImage(SYSTEM_LABEL_PROVIDER.getImage(node));
         }
      }
   }

   class CloseEditorRunner implements Runnable
   {
      private SystemModelEvent event;

      CloseEditorRunner(SystemModelEvent event)
      {
         this.event = event;
      }

      public void run()
      {
         SystemModelNodeEditorInput input =
            (SystemModelNodeEditorInput) getEditorInput();
         SystemModelNode node = input.getSystemModelNode();
         if ((event.getParent() == node.getParent()) &&
             (event.getChildren().contains(node)))
         {
            IWorkbenchPage page =
               PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
            if (page != null)
            {
               page.closeEditor(SystemModelNodeFormEditor.this, false);
            }
         }
      }
   }
}
