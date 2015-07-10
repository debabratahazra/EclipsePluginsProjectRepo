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
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.ActionContributionItem;
import org.eclipse.jface.action.GroupMarker;
import org.eclipse.jface.action.IMenuCreator;
import org.eclipse.jface.action.IMenuListener;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.dialogs.IDialogSettings;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.ViewerSorter;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.FocusListener;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IPartListener;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IWorkbenchActionConstants;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.actions.ActionFactory;
import org.eclipse.ui.part.IShowInTarget;
import org.eclipse.ui.part.ShowInContext;
import org.eclipse.ui.part.ViewPart;
import com.ose.plugin.control.LicenseException;
import com.ose.system.Block;
import com.ose.system.Gate;
import com.ose.system.Heap;
import com.ose.system.OseObject;
import com.ose.system.Pool;
import com.ose.system.Process;
import com.ose.system.ProcessFilter;
import com.ose.system.ProcessInfo;
import com.ose.system.Segment;
import com.ose.system.ServiceException;
import com.ose.system.SystemModel;
import com.ose.system.SystemModelNode;
import com.ose.system.Target;
import com.ose.system.debug.OseDebugContextProvider;
import com.ose.system.ui.AutoUpdateManager;
import com.ose.system.ui.IUpdatable;
import com.ose.system.ui.SharedImages;
import com.ose.system.ui.SystemBrowserPlugin;
import com.ose.system.ui.dialogs.SetEnvVarDialog;
import com.ose.system.ui.dialogs.ShowMemoryDialog;
import com.ose.system.ui.editors.SystemModelNodeEditorInput;
import com.ose.system.ui.util.AbstractShowMemoryAction;
import com.ose.system.ui.util.BackgroundImageHandler;
import com.ose.system.ui.views.block.BlockView;
import com.ose.system.ui.views.heap.HeapView;
import com.ose.system.ui.views.loadmodule.LoadModuleView;
import com.ose.system.ui.views.pool.PoolView;
import com.ose.system.ui.views.pooloptimizer.PoolOptimizerView;
import com.ose.system.ui.views.process.ProcessView;

public class SystemView extends ViewPart implements IShowInTarget
{
   private static final String SORT_CRITERIA = "SORT_CRITERIA";

   private static final String SORT_DIRECTION = "SORT_DIRECTION";

   private static final String LINK_WITH_EDITOR = "LINK_WITH_EDITOR";

   private static final int AUTO_UPDATE_PRIORITY = 1;

   private TreeViewer viewer;

   private SystemSorter sorter;

   private Text findText;

   private IDialogSettings settings;

   private IPartListener editorPartHandler;

   private Object debugContextProvider;

   private boolean linkingEnabled;

   private Action openEditorAction;

   private Action sortNameAction;

   private Action sortIdAction;

   private Action sortNoneAction;

   private Action sortDirectionAction;

   private Action toggleLinkingAction;

   private Action updateAction;

   private Action updateAllAction;

   private Action updateAutoAction;

   private Action cleanAction;

   private Action cleanAllAction;

   private Action findGatesAction;

   private Action addGateAction;

   private Action addTargetAction;

   private Action connectAction;

   private Action disconnectAction;

   private Action showLoadModulesAction;

   private Action showPoolAction;

   private Action showPoolOptimizerAction;

   private Action showHeapAction;

   private Action showBlocksAction;

   private Action showProcessesAction;

   private Action showMemoryAction;

   private Action setSysParamAction;

   private Action startScopeAction;

   private Action stopScopeAction;

   private Action killScopeAction;

   private Action setEnvVarAction;

   private Action signalSemaphoreAction;

   private Action setPriorityAction;

   private Action setExecutionUnitAction;

   private Action findAction;

   public SystemView()
   {
      IDialogSettings pluginSettings =
         SystemBrowserPlugin.getDefault().getDialogSettings();
      settings = pluginSettings.getSection("SystemView");
      if (settings == null)
      {
         settings = pluginSettings.addNewSection("SystemView");
      }

      linkingEnabled = settings.getBoolean(LINK_WITH_EDITOR);
   }

   public void createPartControl(Composite parent)
   {
      SystemContentProvider contentProvider;
      FindHandler findHandler;
      GridLayout layout;
      Composite viewerComposite;
      Composite findComposite;
      Label label;

      layout = new GridLayout(1, false);
      layout.marginWidth = 0;
      layout.marginHeight = 0;
      layout.verticalSpacing = 0;
      parent.setLayout(layout);

      viewerComposite = new Composite(parent, SWT.NONE);
      viewerComposite.setLayoutData(new GridData(GridData.FILL_BOTH));
      viewerComposite.setLayout(new FillLayout());

      contentProvider = new SystemContentProvider();
      viewer = new TreeViewer(viewerComposite, SWT.SINGLE | SWT.H_SCROLL | SWT.V_SCROLL);
      viewer.setUseHashlookup(true);
      viewer.setContentProvider(contentProvider);
      viewer.setLabelProvider(new SystemLabelProvider());
      viewer.setInput(contentProvider.getInitialInput());
      contentProvider.setBackgroundImageHandler(
            new TreeBackgroundImageHandler(viewer.getTree()));

      label = new Label(parent, SWT.HORIZONTAL | SWT.SEPARATOR);
      label.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

      findComposite = new Composite(parent, SWT.NONE);
      findComposite.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
      findComposite.setLayout(new GridLayout(2, false));

      label = new Label(findComposite, SWT.NONE);
      label.setText("Find:");
      label.setLayoutData(new GridData());

      findText = new Text(findComposite, SWT.SINGLE | SWT.BORDER);
      findText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
      findHandler = new FindHandler();
      findText.addFocusListener(findHandler);
      findText.addModifyListener(findHandler);
      findText.addKeyListener(findHandler);

      createActions();
      createContextMenu();
      hookSelectionChangedHandler();
      hookDoubleClickHandler();
      contributeToActionBars();
      getSite().setSelectionProvider(viewer);
      editorPartHandler = new EditorPartHandler();
      getSite().getPage().addPartListener(editorPartHandler);

      // Requires Eclipse 3.2 or later.
      try
      {
         debugContextProvider = new OseDebugContextProvider(this, viewer);
      }
      catch (Throwable t)
      {
         SystemBrowserPlugin.log(SystemBrowserPlugin.createErrorStatus(
            "Could not create OSE debug context provider " +
            "(requires Eclipse 3.2 or later).", t));
      }
   }

   private void createActions()
   {
      openEditorAction = new OpenEditorAction();
      sortNameAction = new SortNameAction();
      sortIdAction = new SortIdAction();
      sortNoneAction = new SortNoneAction();
      sortDirectionAction = new SortDirectionAction();
      toggleLinkingAction = new ToggleLinkingAction();
      updateAction = new UpdateAction();
      updateAllAction = new UpdateAllAction();
      updateAutoAction = new ToggleUpdateAutoAction();
      cleanAction = new CleanAction();
      cleanAllAction = new CleanAllAction();
      findGatesAction = new FindGatesAction();
      addGateAction = new AddGateAction();
      addTargetAction = new AddTargetAction();
      connectAction = new ConnectAction();
      disconnectAction = new DisconnectAction();
      showLoadModulesAction = new ShowLoadModulesAction();
      showPoolAction = new ShowPoolAction();
      showPoolOptimizerAction = new ShowPoolOptimizerAction();
      showHeapAction = new ShowHeapAction();
      showBlocksAction = new ShowBlocksAction();
      showProcessesAction = new ShowProcessesAction();
      showMemoryAction = new ShowMemoryAction();
      setSysParamAction = new SetSysParamAction();
      startScopeAction = new StartScopeAction();
      stopScopeAction = new StopScopeAction();
      killScopeAction = new KillScopeAction();
      setEnvVarAction = new SetEnvVarAction();
      signalSemaphoreAction = new SignalSemaphoreAction();
      setPriorityAction = new SetPriorityAction();
      setExecutionUnitAction = new SetExecutionUnitAction();
      findAction = new FindAction();
   }

   private void createContextMenu()
   {
      MenuManager menuMgr;
      Menu menu;

      menuMgr = new MenuManager();
      menuMgr.setRemoveAllWhenShown(true);
      menuMgr.addMenuListener(new ContextMenuHandler());
      menu = menuMgr.createContextMenu(viewer.getControl());
      viewer.getControl().setMenu(menu);
      getSite().registerContextMenu(menuMgr, viewer);
   }

   private void hookSelectionChangedHandler()
   {
      viewer.addSelectionChangedListener(new SelectionChangedHandler());
   }

   private void hookDoubleClickHandler()
   {
      viewer.addDoubleClickListener(new DoubleClickHandler());
   }

   private void contributeToActionBars()
   {
      IActionBars bars = getViewSite().getActionBars();
      fillLocalPullDown(bars.getMenuManager());
      fillLocalToolBar(bars.getToolBarManager());
      bars.setGlobalActionHandler(ActionFactory.FIND.getId(), findAction);
   }

   private void fillLocalPullDown(IMenuManager manager)
   {
      // XXX: Uncomment InternalRefreshAction when testing tree
      // viewer refresh but comment it away before release.
      //manager.add(new InternalRefreshAction());
      manager.add(new Separator());
      manager.add(new SortDropDownAction());
      manager.add(toggleLinkingAction);
   }

   private void fillLocalToolBar(IToolBarManager manager)
   {
      manager.add(new UpdateDropDownAction());
      manager.add(new CleanDropDownAction());
      manager.add(new Separator());
      manager.add(findGatesAction);
      manager.add(addGateAction);
      manager.add(addTargetAction);
      manager.add(new Separator());
      manager.add(connectAction);
      manager.add(disconnectAction);
      manager.add(new Separator());
      manager.add(toggleLinkingAction);
   }

   public void dispose()
   {
      if (debugContextProvider != null)
      {
         try
         {
            ((OseDebugContextProvider) debugContextProvider).dispose();
         }
         catch (Throwable t)
         {
            SystemBrowserPlugin.log(t);
         }
      }
      getSite().getPage().removePartListener(editorPartHandler);
      super.dispose();
   }

   public void setFocus()
   {
      viewer.getControl().setFocus();
   }

   public boolean show(ShowInContext context)
   {
      Object input = context.getInput();
      if (input instanceof SystemModelNode)
      {
         viewer.setSelection(new StructuredSelection(input), true);
         return true;
      }
      return false;
   }

   public SystemModelNode getSelectedSystemModelNode()
   {
      ISelection selection = viewer.getSelection();
      Object obj = ((IStructuredSelection) selection).getFirstElement();
      return ((obj instanceof SystemModelNode) ? ((SystemModelNode) obj) : null);
   }

   public boolean isLinkingEnabled()
   {
      return linkingEnabled;
   }

   public void setLinkingEnabled(boolean enabled)
   {
      linkingEnabled = enabled;
      settings.put(LINK_WITH_EDITOR, enabled);
      if (enabled)
      {
         IEditorPart editor = getSite().getPage().getActiveEditor();
         if (editor != null)
         {
            editorActivated(editor);
         }
      }
   }

   private void editorActivated(IEditorPart editor)
   {
      if (isLinkingEnabled())
      {
         IEditorInput input = editor.getEditorInput();
         if (input instanceof SystemModelNodeEditorInput)
         {
            SystemModelNodeEditorInput nodeInput =
               (SystemModelNodeEditorInput) input;
            ISelection newSelection =
               new StructuredSelection(nodeInput.getSystemModelNode());
            if (viewer.getSelection().equals(newSelection))
            {
               viewer.getTree().showSelection();
            }
            else
            {
               viewer.setSelection(newSelection, true);
            }
         }
      }
   }

   static class TreeBackgroundImageHandler extends BackgroundImageHandler
   {
      TreeBackgroundImageHandler(Composite comp)
      {
         super(comp);
      }

      protected void drawBackgroundImage(Image image, GC gc, int xOffset,
            int yOffset)
      {
         String str1 = "Press the ";
         String str2 = " button to find gates on the";
         String str3 = "local network or press the ";
         String str4 = " button to";
         String str5 = "add a gate explicitly. Expand a target";
         String str6 = "under a gate to connect to it.";
         Image findGateIcon = SharedImages.get(SharedImages.IMG_TOOL_GATE_FIND);
         Image addGateIcon = SharedImages.get(SharedImages.IMG_TOOL_GATE_ADD);
         Point p1 = gc.stringExtent(str1);
         Point p2 = gc.stringExtent(str2);
         Point p3 = gc.stringExtent(str3);
         Point p4 = gc.stringExtent(str4);
         Point p5 = gc.stringExtent(str5);
         Point p6 = gc.stringExtent(str6);
         Rectangle imageRect = image.getBounds();
         Rectangle findGateIconRect = findGateIcon.getBounds();
         Rectangle addGateIconRect = addGateIcon.getBounds();
         int leading = gc.getFontMetrics().getLeading();
         int w1 = p1.x + findGateIconRect.width + p2.x;
         int h1 = Math.max(findGateIconRect.height, Math.max(p1.y, p2.y) + leading);
         int w2 = p3.x + addGateIconRect.width + p4.x;
         int h2 = Math.max(addGateIconRect.height, Math.max(p3.y, p4.y) + leading);
         int w3 = p5.x;
         int h3 = p5.y + leading;
         int w4 = p6.x;
         int h4 = p6.y + leading;
         int w = Math.max(Math.max(w1, w2), Math.max(w3, w4));
         int h = h1 + h2 + h3 + h4;
         int x = xOffset + (imageRect.width - xOffset - w) / 2;
         int y = yOffset + (imageRect.height - yOffset - h) / 2;
         gc.drawString(str1, x, y);
         gc.drawImage(findGateIcon, x + p1.x, y);
         gc.drawString(str2, x + p1.x + findGateIconRect.width, y);
         gc.drawString(str3, x, y + h1);
         gc.drawImage(addGateIcon, x + p3.x, y + h1);
         gc.drawString(str4, x + p3.x + addGateIconRect.width, y + h1);
         gc.drawString(str5, x, y + h1 + h2);
         gc.drawString(str6, x, y + h1 + h2 + h3);
      }
   }

   class ContextMenuHandler implements IMenuListener
   {
      public void menuAboutToShow(IMenuManager manager)
      {
         SystemModelNode node = getSelectedSystemModelNode();
         manager.add(updateAction);
         manager.add(cleanAction);
         manager.add(new Separator());
         manager.add(findGatesAction);
         manager.add(addGateAction);
         if ((node instanceof Gate) || (node instanceof Target))
         {
            manager.add(addTargetAction);
            manager.add(new Separator());
            manager.add(connectAction);
            manager.add(disconnectAction);
         }
         if (node != null)
         {
            manager.add(new Separator());
            manager.add(openEditorAction);
         }
         if ((node instanceof Target) || (node instanceof Segment) ||
             (node instanceof Block) || (node instanceof Process))
         {
            manager.add(new Separator());
            if (node instanceof Target)
            {
               manager.add(showLoadModulesAction);
            }
            if ((node instanceof Target) || (node instanceof Segment) ||
                (node instanceof Block))
            {
               manager.add(showBlocksAction);
            }
            manager.add(showProcessesAction);
            manager.add(new GroupMarker("showViewAdditions"));
         }
         if (node instanceof Pool)
         {
            manager.add(new Separator());
            manager.add(showPoolAction);
            manager.add(showPoolOptimizerAction);
         }
         if (node instanceof Heap)
         {
            manager.add(new Separator());
            manager.add(showHeapAction);
         }
         if (node instanceof Target)
         {
            manager.add(new Separator());
            manager.add(setSysParamAction);
         }
         if ((node instanceof Segment) || (node instanceof Block) ||
             (node instanceof Process))
         {
            manager.add(new Separator());
            manager.add(showMemoryAction);
            manager.add(startScopeAction);
            manager.add(stopScopeAction);
            manager.add(killScopeAction);
            if ((node instanceof Block) || (node instanceof Process))
            {
               manager.add(setEnvVarAction);
            }
         }
         if (node instanceof Process)
         {
            manager.add(signalSemaphoreAction);
            manager.add(setPriorityAction);
         }
         if ((node instanceof Block) || (node instanceof Process))
         {
            manager.add(setExecutionUnitAction);
         }
         manager.add(new Separator(IWorkbenchActionConstants.MB_ADDITIONS));
      }
   }

   class SelectionChangedHandler implements ISelectionChangedListener
   {
      public void selectionChanged(SelectionChangedEvent event)
      {
         ISelection selection;
         Object obj;
         Target target;
         boolean isAlive;
         boolean isGate;
         boolean isTarget;
         boolean isGateOrTarget;
         boolean isPool;
         boolean isHeap;
         boolean isTargetOrSegmentOrBlock;
         boolean isTargetOrSegmentOrBlockOrProcess;
         boolean isSegmentOrBlockOrProcess;
         boolean isBlockOrProcess;
         boolean isProcess;
         boolean isPrioritizedProcess;
         boolean hasLoadModuleSupport = false;
         boolean hasPoolSupport = false;
         boolean hasHeapSupport = false;
         boolean hasBlockSupport = false;
         boolean hasProcessSupport = false;
         boolean hasMemorySupport = false;
         boolean hasSetSysParamSupport = false;
         boolean hasStartScopeSupport = false;
         boolean hasStopScopeSupport = false;
         boolean hasKillScopeSupport = false;
         boolean hasSetEnvVarSupport = false;
         boolean hasSignalSemaphoreSupport = false;
         boolean hasSetPrioritySupport = false;
         boolean hasSetExecutionUnitSupport = false;

         selection = event.getSelection();
         obj = ((IStructuredSelection) selection).getFirstElement();
         target = getTarget(obj);
         isAlive = ((obj instanceof SystemModelNode) ?
                    !((SystemModelNode) obj).isKilled() : false);
         isGate = (obj instanceof Gate);
         isTarget = (obj instanceof Target);
         isGateOrTarget = isGate || isTarget;
         isPool = (obj instanceof Pool);
         isHeap = (obj instanceof Heap);
         isTargetOrSegmentOrBlock = ((obj instanceof Target) ||
                                     (obj instanceof Segment) ||
                                     (obj instanceof Block));
         isTargetOrSegmentOrBlockOrProcess = ((obj instanceof Target) ||
                                              (obj instanceof Segment) ||
                                              (obj instanceof Block) ||
                                              (obj instanceof Process));
         isSegmentOrBlockOrProcess = ((obj instanceof Segment) ||
                                      (obj instanceof Block) ||
                                      (obj instanceof Process));
         isBlockOrProcess = ((obj instanceof Block) || (obj instanceof Process));
         isProcess = (obj instanceof Process);
         isPrioritizedProcess = isProcess &&
               (((Process) obj).getType() == Process.TYPE_PRIORITIZED);
         if (target != null)
         {
            hasLoadModuleSupport = target.hasLoadModuleSupport();
            hasPoolSupport = target.hasPoolSupport();
            hasHeapSupport = target.hasHeapSupport();
            hasBlockSupport = target.hasBlockSupport();
            hasProcessSupport = target.hasProcessSupport();
            hasMemorySupport = target.hasMemorySupport();
            hasSetSysParamSupport = target.hasSetSysParamSupport();
            hasStartScopeSupport = target.hasStartScopeSupport();
            hasStopScopeSupport = target.hasStopScopeSupport();
            hasKillScopeSupport = target.hasKillScopeSupport();
            hasSetEnvVarSupport = target.hasSetEnvVarSupport();
            hasSignalSemaphoreSupport = target.hasSignalSemaphoreSupport();
            hasSetPrioritySupport = target.hasSetPrioritySupport();
            hasSetExecutionUnitSupport = target.hasSetExecutionUnitSupport();
         }

         updateAction.setEnabled(isGateOrTarget || isAlive);
         openEditorAction.setEnabled(obj != null);
         addTargetAction.setEnabled(isGateOrTarget && isAlive);
         connectAction.setEnabled(isGateOrTarget);
         disconnectAction.setEnabled(isGateOrTarget);
         showLoadModulesAction.setEnabled(isTarget && isAlive && hasLoadModuleSupport);
         showPoolAction.setEnabled(isPool && isAlive && hasPoolSupport);
         showPoolOptimizerAction.setEnabled(isPool && isAlive && hasPoolSupport);
         showHeapAction.setEnabled(isHeap && isAlive && hasHeapSupport);
         showBlocksAction.setEnabled(isTargetOrSegmentOrBlock && isAlive && hasBlockSupport);
         showProcessesAction.setEnabled(isTargetOrSegmentOrBlockOrProcess && isAlive && hasProcessSupport);
         showMemoryAction.setEnabled(isSegmentOrBlockOrProcess && isAlive && hasMemorySupport);
         setSysParamAction.setEnabled(isTarget && isAlive && hasSetSysParamSupport);
         startScopeAction.setEnabled(isSegmentOrBlockOrProcess && isAlive && hasStartScopeSupport);
         stopScopeAction.setEnabled(isSegmentOrBlockOrProcess && isAlive && hasStopScopeSupport);
         killScopeAction.setEnabled(isSegmentOrBlockOrProcess && isAlive && hasKillScopeSupport);
         setEnvVarAction.setEnabled(isBlockOrProcess && isAlive && hasSetEnvVarSupport);
         signalSemaphoreAction.setEnabled(isProcess && isAlive && hasSignalSemaphoreSupport);
         setPriorityAction.setEnabled(isPrioritizedProcess && isAlive && hasSetPrioritySupport);
         setExecutionUnitAction.setEnabled(isBlockOrProcess && isAlive && hasSetExecutionUnitSupport);
      }

      private Target getTarget(Object obj)
      {
         if (obj instanceof Target)
         {
            return (Target) obj;
         }
         else if (obj instanceof Segment)
         {
            return ((Segment) obj).getTarget();
         }
         else if (obj instanceof Pool)
         {
            return ((Pool) obj).getTarget();
         }
         else if (obj instanceof Heap)
         {
            return ((Heap) obj).getTarget();
         }
         else if (obj instanceof Block)
         {
            return ((Block) obj).getTarget();
         }
         else if (obj instanceof Process)
         {
            return ((Process) obj).getTarget();
         }
         else
         {
            return null;
         }
      }
   }

   class DoubleClickHandler implements IDoubleClickListener
   {
      public void doubleClick(DoubleClickEvent event)
      {
         openEditorAction.run();
      }
   }

   class EditorPartHandler implements IPartListener
   {
      public void partActivated(IWorkbenchPart part)
      {
         if (part instanceof IEditorPart)
         {
            editorActivated((IEditorPart) part);
         }
      }

      public void partBroughtToTop(IWorkbenchPart part) {}

      public void partClosed(IWorkbenchPart part) {}

      public void partDeactivated(IWorkbenchPart part) {}

      public void partOpened(IWorkbenchPart part) {}
   }

   class FindHandler implements FocusListener, ModifyListener, KeyListener
   {
      private final Color hitColor;
      private final Color missColor;

      private List nodes;
      private List result;
      private int next;

      FindHandler()
      {
         hitColor = findText.getBackground();
         missColor = findText.getDisplay().getSystemColor(SWT.COLOR_RED);
      }

      public void focusGained(FocusEvent event)
      {
         nodes = getNodes();
         result = new ArrayList();
         next = 0;
         findText.setText(findText.getText());
      }

      public void focusLost(FocusEvent event)
      {
         nodes = null;
         result = null;
         next = 0;
      }

      public void modifyText(ModifyEvent event)
      {
         String findString = findText.getText().toLowerCase();
         boolean hasFindString = (findString.length() > 0);
         result.clear();
         next = 0;

         if (hasFindString)
         {
            for (Iterator i = nodes.iterator(); i.hasNext();)
            {
               SystemModelNode node = (SystemModelNode) i.next();
               if (node.toString().toLowerCase().indexOf(findString) != -1)
               {
                  result.add(node);
               }
            }
         }

         if (result.isEmpty())
         {
            selectNode(SystemModel.getInstance());
            findText.setBackground(hasFindString ? missColor : hitColor);
         }
         else
         {
            selectNode(result.get(0));
            findText.setBackground(hitColor);
         }
      }

      public void keyPressed(KeyEvent event)
      {
         if (event.character == SWT.CR)
         {
            if (event.stateMask != SWT.SHIFT)
            {
               if (!result.isEmpty())
               {
                  if ((next >= 0) && (next < (result.size() - 1)))
                  {
                     next++;
                  }
                  else
                  {
                     next = 0;
                     findText.getDisplay().beep();
                  }
                  selectNode(result.get(next));
               }
            }
            else
            {
               if (!result.isEmpty())
               {
                  if ((next > 0) && (next < result.size()))
                  {
                     next--;
                  }
                  else
                  {
                     next = result.size() - 1;
                     findText.getDisplay().beep();
                  }
                  selectNode(result.get(next));
               }
            }
         }
         else if (event.character == SWT.ESC)
         {
            // Give back focus to the tree viewer.
            setFocus();
         }
      }

      public void keyReleased(KeyEvent event) {}

      private List getNodes()
      {
         return getNodes(SystemModel.getInstance().getChildren());
      }

      private List getNodes(SystemModelNode[] nodes)
      {
         List result = new ArrayList();
         SystemModelNode[] sortedNodes = sortNodes(nodes);

         for (int i = 0; i < sortedNodes.length; i++)
         {
            SystemModelNode node = sortedNodes[i];
            result.add(node);
            if (!node.isLeaf())
            {
               result.addAll(getNodes(node.getChildren()));
            }
         }

         return result;
      }

      // If a sorter is set in the tree viewer, sort the nodes correspondingly.
      private SystemModelNode[] sortNodes(SystemModelNode[] nodes)
      {
         ViewerSorter viewerSorter = viewer.getSorter();
         if (viewerSorter != null)
         {
            viewerSorter.sort(viewer, nodes);
         }
         return nodes;
      }

      private void selectNode(Object node)
      {
         viewer.setSelection(new StructuredSelection(node), true);
      }
   }

   class InternalRefreshAction extends Action
   {
      InternalRefreshAction()
      {
         super("Internal Refresh");
      }

      public void run()
      {
         SystemModelNode node = getSelectedSystemModelNode();
         if (node != null)
         {
            viewer.refresh(node, true);
         }
         else
         {
            viewer.refresh(true);
         }
      }
   }

   class OpenEditorAction extends Action
   {
      OpenEditorAction()
      {
         super("Open");
      }

      public void run()
      {
         SystemModelNode node = getSelectedSystemModelNode();
         if (node != null)
         {
            IWorkbenchPage page = PlatformUI.getWorkbench()
               .getActiveWorkbenchWindow().getActivePage();
            if (page != null)
            {
               try
               {
                  page.openEditor(new SystemModelNodeEditorInput(node),
                        SystemBrowserPlugin.SYSTEM_MODEL_NODE_EDITOR_ID);
                  // Make sure any lazily initialized properties
                  // of an OSE object are initialized.
                  if (node instanceof OseObject)
                  {
                     Job job = new InitLazyPropertiesJob(node);
                     job.schedule();
                  }
               }
               catch (PartInitException e)
               {
                  SystemBrowserPlugin.log(e);
               }
            }
         }
      }
   }

   class SortDropDownAction extends Action implements IMenuCreator
   {
      private Menu menu;

      SortDropDownAction()
      {
         int criteria = 0;
         int direction = SystemSorter.DIRECTION_ASCENDING;

         setText("Sort By");
         setMenuCreator(this);

         try
         {
            criteria = settings.getInt(SORT_CRITERIA);
            direction = settings.getInt(SORT_DIRECTION);
         } catch (NumberFormatException ignore) {}

         switch (criteria)
         {
            case SystemSorter.CRITERIA_NAME:
               sortNameAction.setChecked(true);
               break;
            case SystemSorter.CRITERIA_ID:
               sortIdAction.setChecked(true);
               break;
            default:
               sortNoneAction.setChecked(true);
         }
         sortDirectionAction.setChecked(
            direction != SystemSorter.DIRECTION_DESCENDING);
         sortDirectionAction.setEnabled(criteria != 0);
         sorter = new SystemSorter();
         sorter.setDirection(direction);
         if (criteria != 0)
         {
            sorter.setCriteria(criteria);
            viewer.setSorter(sorter);
         }
      }

      public void dispose()
      {
         if (menu != null)
         {
            menu.dispose();
            menu = null;
         }
      }

      public Menu getMenu(Menu parent)
      {
         ActionContributionItem item;

         if (menu != null)
         {
            menu.dispose();
         }
         menu = new Menu(parent);

         item = new ActionContributionItem(sortNameAction);
         item.fill(menu, -1);

         item = new ActionContributionItem(sortIdAction);
         item.fill(menu, -1);

         item = new ActionContributionItem(sortNoneAction);
         item.fill(menu, -1);

         new MenuItem(menu, SWT.SEPARATOR);

         item = new ActionContributionItem(sortDirectionAction);
         item.fill(menu, -1);

         return menu;
      }

      public Menu getMenu(Control parent)
      {
         return null;
      }
   }

   class SortNameAction extends Action
   {
      SortNameAction()
      {
         super("Name", Action.AS_RADIO_BUTTON);
      }

      public void run()
      {
         sorter.setCriteria(SystemSorter.CRITERIA_NAME);
         if (viewer.getSorter() == null)
         {
            sortDirectionAction.setEnabled(true);
            viewer.setSorter(sorter);
         }
         viewer.refresh();
         // Steal focus from the find handler to provoke
         // sorting of its nodes when it gets focus again.
         setFocus();
         settings.put(SORT_CRITERIA, SystemSorter.CRITERIA_NAME);
      }
   }

   class SortIdAction extends Action
   {
      SortIdAction()
      {
         super("ID", Action.AS_RADIO_BUTTON);
      }

      public void run()
      {
         sorter.setCriteria(SystemSorter.CRITERIA_ID);
         if (viewer.getSorter() == null)
         {
            sortDirectionAction.setEnabled(true);
            viewer.setSorter(sorter);
         }
         viewer.refresh();
         // Steal focus from the find handler to provoke
         // sorting of its nodes when it gets focus again.
         setFocus();
         settings.put(SORT_CRITERIA, SystemSorter.CRITERIA_ID);
      }
   }

   class SortNoneAction extends Action
   {
      SortNoneAction()
      {
         super("None", Action.AS_RADIO_BUTTON);
      }

      public void run()
      {
         sortDirectionAction.setEnabled(false);
         viewer.setSorter(null);
         viewer.refresh();
         // Steal focus from the find handler to provoke no
         // sorting of its nodes when it gets focus again.
         setFocus();
         settings.put(SORT_CRITERIA, 0);
      }
   }

   class SortDirectionAction extends Action
   {
      SortDirectionAction()
      {
         super("Ascending", Action.AS_CHECK_BOX);
      }

      public void run()
      {
         int direction = isChecked() ? SystemSorter.DIRECTION_ASCENDING :
            SystemSorter.DIRECTION_DESCENDING;
         sorter.setDirection(direction);
         if (viewer.getSorter() != null)
         {
            viewer.refresh();
            // Steal focus from the find handler to provoke
            // sorting of its nodes when it gets focus again.
            setFocus();
         }
         settings.put(SORT_DIRECTION, direction);
      }
   }

   class ToggleLinkingAction extends Action
   {
      ToggleLinkingAction()
      {
         super("Link with Editor");
         setToolTipText("Link with Editor");
         setImageDescriptor(SharedImages.DESC_TOOL_LINK_EDITOR);
         setChecked(isLinkingEnabled());
      }

      public void run()
      {
         setLinkingEnabled(isChecked());
      }
   }

   class UpdateDropDownAction extends Action implements IMenuCreator
   {
      private Menu menu;

      UpdateDropDownAction()
      {
         super("Update");
         setToolTipText("Update");
         setImageDescriptor(SharedImages.DESC_TOOL_UPDATE);
         setMenuCreator(this);
      }

      public void dispose()
      {
         if (menu != null)
         {
            menu.dispose();
            menu = null;
         }
      }

      public Menu getMenu(Control parent)
      {
         ActionContributionItem item;

         if (menu != null)
         {
            menu.dispose();
         }
         menu = new Menu(parent);

         item = new ActionContributionItem(updateAction);
         item.fill(menu, -1);

         item = new ActionContributionItem(updateAllAction);
         item.fill(menu, -1);

         item = new ActionContributionItem(updateAutoAction);
         item.fill(menu, -1);

         return menu;
      }

      public Menu getMenu(Menu parent)
      {
         return null;
      }

      public void run()
      {
         if (updateAction.isEnabled())
         {
            updateAction.run();
         }
      }
   }

   class UpdateAction extends Action
   {
      UpdateAction()
      {
         super("Update");
         setToolTipText("Update");
         setImageDescriptor(SharedImages.DESC_TOOL_UPDATE);
      }

      public void run()
      {
         SystemModelNode node = getSelectedSystemModelNode();
         if (node != null)
         {
            if (!node.isKilled())
            {
               Job job = new UpdateJob(node);
               job.schedule();
            }
            else if ((node instanceof Gate) || (node instanceof Target))
            {
               Job job = new ConnectJob(node);
               job.schedule();
            }
         }
      }
   }

   class UpdateAllAction extends Action
   {
      UpdateAllAction()
      {
         super("Update All");
         setToolTipText("Update All");
         setImageDescriptor(SharedImages.DESC_TOOL_UPDATE_ALL);
      }

      public void run()
      {
         Job job = new UpdateJob(SystemModel.getInstance());
         job.schedule();
      }
   }

   class ToggleUpdateAutoAction extends Action
   {
      private final IUpdatable updatable = new Updatable();

      ToggleUpdateAutoAction()
      {
         super("Update Automatically", AS_CHECK_BOX);
         setToolTipText("Update Automatically");
         setImageDescriptor(SharedImages.DESC_TOOL_UPDATE_AUTO);
      }

      public void run()
      {
         if (isChecked())
         {
            AutoUpdateManager.getInstance().addUpdatable(updatable);
         }
         else
         {
            AutoUpdateManager.getInstance().removeUpdatable(updatable.getId());
         }
      }

      private class Updatable implements IUpdatable
      {
         public String getId()
         {
            return SystemBrowserPlugin.SYSTEM_VIEW_ID;
         }

         public int getPriority()
         {
            return SystemView.AUTO_UPDATE_PRIORITY;
         }

         public void update(IProgressMonitor monitor)
         {
            try
            {
               SystemModel.getInstance().update(monitor, true);
            }
            catch (OperationCanceledException e)
            {
               // Do nothing.
            }
            catch (ServiceException e)
            {
               SystemBrowserPlugin.log(SystemBrowserPlugin.createErrorStatus(
                  "Error reported from target when auto updating", e));
            }
            catch (IOException e)
            {
               SystemBrowserPlugin.log(SystemBrowserPlugin.createErrorStatus(
                  "Error communicating with target when auto updating", e));
            }
            catch (Exception e)
            {
               SystemBrowserPlugin.log(SystemBrowserPlugin.createErrorStatus(
                  "Error when auto updating", e));
            }

            for (Gate gate : SystemModel.getInstance().getGates())
            {
               for (Target target : gate.getTargets())
               {
                  if (target.isConnected())
                  {
                     target.clean();
                  }
               }
            }
         }
      }
   }

   class CleanDropDownAction extends Action implements IMenuCreator
   {
      private Menu menu;

      CleanDropDownAction()
      {
         super("Clean");
         setToolTipText("Clean");
         setImageDescriptor(PlatformUI.getWorkbench().getSharedImages()
               .getImageDescriptor(ISharedImages.IMG_TOOL_DELETE));
         setMenuCreator(this);
      }

      public void dispose()
      {
         if (menu != null)
         {
            menu.dispose();
            menu = null;
         }
      }

      public Menu getMenu(Control parent)
      {
         ActionContributionItem item;

         if (menu != null)
         {
            menu.dispose();
         }
         menu = new Menu(parent);

         item = new ActionContributionItem(cleanAction);
         item.fill(menu, -1);

         item = new ActionContributionItem(cleanAllAction);
         item.fill(menu, -1);

         return menu;
      }

      public Menu getMenu(Menu parent)
      {
         return null;
      }

      public void run()
      {
         if (cleanAction.isEnabled())
         {
            cleanAction.run();
         }
      }
   }

   class CleanAction extends Action
   {
      CleanAction()
      {
         super("Clean");
         setToolTipText("Clean");
         setImageDescriptor(PlatformUI.getWorkbench().getSharedImages()
               .getImageDescriptor(ISharedImages.IMG_TOOL_DELETE));
      }

      public void run()
      {
         SystemModelNode node = getSelectedSystemModelNode();
         if (node != null)
         {
            Job job = new CleanJob(node);
            job.schedule();
         }
      }
   }

   class CleanAllAction extends Action
   {
      CleanAllAction()
      {
         super("Clean All");
         setToolTipText("Clean All");
         setImageDescriptor(SharedImages.DESC_TOOL_CLEAN_ALL);
      }

      public void run()
      {
         Job job = new CleanJob(SystemModel.getInstance());
         job.schedule();
      }
   }

   class FindGatesAction extends Action
   {
      FindGatesAction()
      {
         super("Find Gates");
         setToolTipText("Find Gates");
         setImageDescriptor(SharedImages.DESC_TOOL_GATE_FIND);
      }

      public void run()
      {
         Job job = new FindGatesJob(SystemModel.getInstance());
         job.schedule();
      }
   }

   class AddGateAction extends Action
   {
      AddGateAction()
      {
         super("Add Gate...");
         setToolTipText("Add New Gate");
         setImageDescriptor(SharedImages.DESC_TOOL_GATE_ADD);
      }

      public void run()
      {
         AddGateDialog dialog =
            new AddGateDialog(viewer.getControl().getShell());
         int result = dialog.open();
         if (result == Window.OK)
         {
            try
            {
               InetAddress address = InetAddress.getByName(dialog.getAddress());
               Job job = new AddGateJob(SystemModel.getInstance(), address,
                     dialog.getPort());
               job.schedule();
            }
            catch (UnknownHostException e)
            {
               ErrorDialog.openError(viewer.getControl().getShell(),
                                     "Error adding gate",
                                     "Unknown or invalid address.",
                                     SystemBrowserPlugin.createErrorStatus(e));
            }
         }
      }
   }

   class AddTargetAction extends Action
   {
      AddTargetAction()
      {
         super("Add Target...");
         setToolTipText("Add New Target");
         setImageDescriptor(SharedImages.DESC_TOOL_TARGET_ADD);
      }

      public void run()
      {
         SystemModelNode node = getSelectedSystemModelNode();
         if ((node instanceof Gate) || (node instanceof Target))
         {
            String huntPath = (node instanceof Gate) ? null : "*/";
            AddTargetDialog dialog =
               new AddTargetDialog(viewer.getControl().getShell(), huntPath);
            int result = dialog.open();
            if (result == Window.OK)
            {
               Job job = new AddTargetJob(node, dialog.getHuntPath());
               job.schedule();
            }
         }
      }
   }

   class ConnectAction extends Action
   {
      ConnectAction()
      {
         super("Connect");
         setToolTipText("Connect");
         setImageDescriptor(SharedImages.DESC_TOOL_CONNECT);
      }

      public void run()
      {
         SystemModelNode node = getSelectedSystemModelNode();
         if ((node instanceof Gate) || (node instanceof Target))
         {
            Job job = new ConnectJob(node);
            job.schedule();
         }
      }
   }

   class DisconnectAction extends Action
   {
      DisconnectAction()
      {
         super("Disconnect");
         setToolTipText("Disconnect");
         setImageDescriptor(SharedImages.DESC_TOOL_DISCONNECT);
      }

      public void run()
      {
         SystemModelNode node = getSelectedSystemModelNode();
         if ((node instanceof Gate) || (node instanceof Target))
         {
            Job job = new DisconnectJob(node);
            job.schedule();
         }
      }
   }

   class ShowLoadModulesAction extends Action
   {
      ShowLoadModulesAction()
      {
         super("Show in " + SystemBrowserPlugin.LOAD_MODULE_VIEW_NAME);
      }

      public void run()
      {
         SystemModelNode node = getSelectedSystemModelNode();
         if (node instanceof Target)
         {
            IWorkbenchPage page =
               PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
            if (page != null)
            {
               try
               {
                  IViewPart view = page.showView(SystemBrowserPlugin.LOAD_MODULE_VIEW_ID);
                  if (view instanceof LoadModuleView)
                  {
                     LoadModuleView loadModuleView = (LoadModuleView) view;
                     loadModuleView.show((Target) node);
                  }
               }
               catch (PartInitException e)
               {
                  SystemBrowserPlugin.log(e);
               }
            }
         }
      }
   }

   class ShowPoolAction extends Action
   {
      ShowPoolAction()
      {
         super("Show in " + SystemBrowserPlugin.POOL_VIEW_NAME);
      }

      public void run()
      {
         SystemModelNode node = getSelectedSystemModelNode();
         if (node instanceof Pool)
         {
            IWorkbenchPage page =
               PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
            if (page != null)
            {
               try
               {
                  IViewPart view = page.showView(SystemBrowserPlugin.POOL_VIEW_ID);
                  if (view instanceof PoolView)
                  {
                     PoolView poolView = (PoolView) view;
                     poolView.show((Pool) node);
                  }
               }
               catch (PartInitException e)
               {
                  SystemBrowserPlugin.log(e);
               }
            }
         }
      }
   }

   class ShowPoolOptimizerAction extends Action
   {
      ShowPoolOptimizerAction()
      {
         super("Show in " + SystemBrowserPlugin.POOL_OPTIMIZER_VIEW_NAME);
      }

      public void run()
      {
         SystemModelNode node = getSelectedSystemModelNode();
         if (node instanceof Pool)
         {
            IWorkbenchPage page =
               PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
            if (page != null)
            {
               try
               {
                  IViewPart view =
                     page.showView(SystemBrowserPlugin.POOL_OPTIMIZER_VIEW_ID);
                  if (view instanceof PoolOptimizerView)
                  {
                     PoolOptimizerView poolOptimizerView =
                        (PoolOptimizerView) view;
                     poolOptimizerView.show((Pool) node);
                  }
               }
               catch (PartInitException e)
               {
                  SystemBrowserPlugin.log(e);
               }
            }
         }
      }
   }

   class ShowHeapAction extends Action
   {
      ShowHeapAction()
      {
         super("Show in " + SystemBrowserPlugin.HEAP_VIEW_NAME);
      }

      public void run()
      {
         SystemModelNode node = getSelectedSystemModelNode();
         if (node instanceof Heap)
         {
            IWorkbenchPage page =
               PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
            if (page != null)
            {
               try
               {
                  IViewPart view = page.showView(SystemBrowserPlugin.HEAP_VIEW_ID);
                  if (view instanceof HeapView)
                  {
                     HeapView heapView = (HeapView) view;
                     heapView.show((Heap) node);
                  }
               }
               catch (PartInitException e)
               {
                  SystemBrowserPlugin.log(e);
               }
            }
         }
      }
   }

   class ShowBlocksAction extends Action
   {
      ShowBlocksAction()
      {
         super("Show in " + SystemBrowserPlugin.BLOCK_VIEW_NAME);
      }

      public void run()
      {
         SystemModelNode node = getSelectedSystemModelNode();
         if ((node instanceof Target) ||
             (node instanceof Segment) ||
             (node instanceof Block))
         {
            IWorkbenchPage page =
               PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
            if (page != null)
            {
               try
               {
                  IViewPart view = page.showView(SystemBrowserPlugin.BLOCK_VIEW_ID);
                  if (view instanceof BlockView)
                  {
                     BlockView blockView = (BlockView) view;
                     if (node instanceof Target)
                     {
                        blockView.show((Target) node, null, null);
                     }
                     else if (node instanceof Segment)
                     {
                        Segment segment = (Segment) node;
                        blockView.show(segment.getTarget(), segment, null);
                     }
                     else if (node instanceof Block)
                     {
                        Block block = (Block) node;
                        blockView.show(block.getTarget(), null, block);
                     }
                  }
               }
               catch (PartInitException e)
               {
                  SystemBrowserPlugin.log(e);
               }
            }
         }
      }
   }

   class ShowProcessesAction extends Action
   {
      ShowProcessesAction()
      {
         super("Show in " + SystemBrowserPlugin.PROCESS_VIEW_NAME);
      }

      public void run()
      {
         SystemModelNode node = getSelectedSystemModelNode();
         if ((node instanceof Target) || (node instanceof Segment) ||
             (node instanceof Block) || (node instanceof Process))
         {
            IWorkbenchPage page =
               PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
            if (page != null)
            {
               try
               {
                  IViewPart view = page.showView(SystemBrowserPlugin.PROCESS_VIEW_ID);
                  if (view instanceof ProcessView)
                  {
                     ProcessView processView = (ProcessView) view;
                     if (node instanceof Target)
                     {
                        processView.show((Target) node, null, null, null);
                     }
                     else if (node instanceof Segment)
                     {
                        Segment segment = (Segment) node;
                        processView.show(segment.getTarget(), segment, null, null);
                     }
                     else if (node instanceof Block)
                     {
                        Block block = (Block) node;
                        processView.show(block.getTarget(), null, block, null);
                     }
                     else if (node instanceof Process)
                     {
                        Process process = (Process) node;
                        processView.show(process.getTarget(), null, null, process);
                     }
                  }
               }
               catch (PartInitException e)
               {
                  SystemBrowserPlugin.log(e);
               }
            }
         }
      }
   }

   class ShowMemoryAction extends AbstractShowMemoryAction
   {
      ShowMemoryAction()
      {
         super("Show Memory");
      }

      public void run()
      {
         SystemModelNode node = getSelectedSystemModelNode();
         if ((node instanceof Segment) ||
             (node instanceof Block) ||
             (node instanceof Process))
         {
            ShowMemoryDialog dialog =
               new ShowMemoryDialog(viewer.getControl().getShell());
            int result = dialog.open();
            if (result == Window.OK)
            {
               Target target = null;

               if (node instanceof Segment)
               {
                  target = ((Segment) node).getTarget();
               }
               else if (node instanceof Block)
               {
                  target = ((Block) node).getTarget();
               }
               else if (node instanceof Process)
               {
                  target = ((Process) node).getTarget();
               }
               try
               {
                  // FIXME: Run as a job?
                  showMemory(target,
                             ((OseObject) node).getId(),
                             dialog.getAddress(),
                             dialog.getLength());
               }
               catch (CoreException e)
               {
                  SystemBrowserPlugin.log(e);
               }
            }
         }
      }
   }

   class SetSysParamAction extends Action
   {
      SetSysParamAction()
      {
         super("Set System Parameter...");
      }

      public void run()
      {
         SystemModelNode node = getSelectedSystemModelNode();
         if (node instanceof Target)
         {
            SetSysParamDialog dialog =
               new SetSysParamDialog(viewer.getControl().getShell());
            int result = dialog.open();
            if (result == Window.OK)
            {
               Job job = new SetSysParamJob(node,
                                            dialog.getName(),
                                            dialog.getValue(),
                                            dialog.isReconfigure());
               job.schedule();
            }
         }
      }
   }

   class StartScopeAction extends Action
   {
      StartScopeAction()
      {
         super("Start");
      }

      public void run()
      {
         SystemModelNode node = getSelectedSystemModelNode();
         if ((node instanceof Segment) ||
             (node instanceof Block) ||
             (node instanceof Process))
         {
            Job job = new StartScopeJob(node);
            job.schedule();
         }
      }
   }

   class StopScopeAction extends Action
   {
      StopScopeAction()
      {
         super("Stop");
      }

      public void run()
      {
         SystemModelNode node = getSelectedSystemModelNode();
         if ((node instanceof Segment) ||
             (node instanceof Block) ||
             (node instanceof Process))
         {
            Job job = new StopScopeJob(node);
            job.schedule();
         }
      }
   }

   class KillScopeAction extends Action
   {
      KillScopeAction()
      {
         super("Kill");
      }

      public void run()
      {
         SystemModelNode node = getSelectedSystemModelNode();
         if ((node instanceof Segment) ||
             (node instanceof Block) ||
             (node instanceof Process))
         {
            Job job = new KillScopeJob(node);
            job.schedule();
         }
      }
   }

   class SetEnvVarAction extends Action
   {
      SetEnvVarAction()
      {
         super("Set Environment Variable...");
      }

      public void run()
      {
         SystemModelNode node = getSelectedSystemModelNode();
         if ((node instanceof Block) || (node instanceof Process))
         {
            SetEnvVarDialog dialog = new SetEnvVarDialog(
                  viewer.getControl().getShell(), "Set Environment Variable");
            int result = dialog.open();
            if (result == Window.OK)
            {
               Job job = new SetEnvVarJob(node, dialog.getName(), dialog.getValue());
               job.schedule();
            }
         }
      }
   }

   class SignalSemaphoreAction extends Action
   {
      SignalSemaphoreAction()
      {
         super("Signal Fast Semaphore");
      }

      public void run()
      {
         SystemModelNode node = getSelectedSystemModelNode();
         if (node instanceof Process)
         {
            Job job = new SignalSemaphoreJob(node);
            job.schedule();
         }
      }
   }

   class SetPriorityAction extends Action
   {
      SetPriorityAction()
      {
         super("Set Priority...");
      }

      public void run()
      {
         SystemModelNode node = getSelectedSystemModelNode();
         if (node instanceof Process)
         {
            SetPriorityDialog dialog = new SetPriorityDialog(
               viewer.getControl().getShell(), ((Process) node).getPriority());
            int result = dialog.open();
            if (result == Window.OK)
            {
               Job job = new SetPriorityJob(node, dialog.getPriority());
               job.schedule();
            }
         }
      }
   }

   class SetExecutionUnitAction extends Action
   {
      SetExecutionUnitAction()
      {
         super("Set Execution Unit...");
      }

      public void run()
      {
         SystemModelNode node = getSelectedSystemModelNode();
         if ((node instanceof Block) || (node instanceof Process))
         {
            short numExecutionUnits = 1;
            short executionUnit = 0;
            if (node instanceof Block)
            {
               Block block = (Block) node;
               numExecutionUnits = block.getTarget().getNumExecutionUnits();
               executionUnit = block.getExecutionUnit();
            }
            else if (node instanceof Process)
            {
               Process process = (Process) node;
               numExecutionUnits = process.getTarget().getNumExecutionUnits();
               executionUnit = process.getExecutionUnit();
            }
            SetExecutionUnitDialog dialog = new SetExecutionUnitDialog(
               viewer.getControl().getShell(), numExecutionUnits, executionUnit);
            int result = dialog.open();
            if (result == Window.OK)
            {
               Job job = new SetExecutionUnitJob(node, dialog.getExecutionUnit());
               job.schedule();
            }
         }
      }
   }

   class FindAction extends Action
   {
      FindAction()
      {
         super("Find");
         setToolTipText("Find");
      }

      public void run()
      {
         findText.forceFocus();
         findText.selectAll();
      }
   }

   static abstract class AbstractJob extends Job
   {
      SystemModelNode node;

      AbstractJob(String name, SystemModelNode node)
      {
         super(name);
         setPriority(SHORT);
         this.node = node;
      }
   }

   static class InitLazyPropertiesJob extends AbstractJob
   {
      InitLazyPropertiesJob(SystemModelNode node)
      {
         super("Initializing lazy properties for " + node, node);
      }

      protected IStatus run(IProgressMonitor monitor)
      {
         try
         {
            monitor.beginTask(getName(), IProgressMonitor.UNKNOWN);
            if (node instanceof OseObject)
            {
               ((OseObject) node).initLazyProperties(monitor);
            }
            return (monitor.isCanceled() ?
                    Status.CANCEL_STATUS : Status.OK_STATUS);
         }
         catch (OperationCanceledException e)
         {
            return Status.CANCEL_STATUS;
         }
         catch (ServiceException e)
         {
            return SystemBrowserPlugin.createErrorStatus(
               "Error reported from target when initializing lazy properties for " + node, e);
         }
         catch (IOException e)
         {
            return SystemBrowserPlugin.createErrorStatus(
               "Error communicating with target when initializing lazy properties for " + node, e);
         }
         catch (Exception e)
         {
            return SystemBrowserPlugin.createErrorStatus(
               "Error when initializing lazy properties for " + node, e);
         }
         finally
         {
            monitor.done();
         }
      }
   }

   static class UpdateJob extends AbstractJob
   {
      UpdateJob(SystemModelNode node)
      {
         super("Updating " + node, node);
      }

      protected IStatus run(IProgressMonitor monitor)
      {
         try
         {
            monitor.beginTask(getName(), IProgressMonitor.UNKNOWN);
            node.update(monitor, true);
            return (monitor.isCanceled() ?
                    Status.CANCEL_STATUS : Status.OK_STATUS);
         }
         catch (OperationCanceledException e)
         {
            return Status.CANCEL_STATUS;
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

   static class CleanJob extends AbstractJob
   {
      CleanJob(SystemModelNode node)
      {
         super("Cleaning " + node, node);
      }

      protected IStatus run(IProgressMonitor monitor)
      {
         try
         {
            monitor.beginTask(getName(), IProgressMonitor.UNKNOWN);
            node.clean();
            return (monitor.isCanceled() ?
                    Status.CANCEL_STATUS : Status.OK_STATUS);
         }
         finally
         {
            monitor.done();
         }
      }
   }

   static class FindGatesJob extends AbstractJob
   {
      FindGatesJob(SystemModelNode node)
      {
         super("Finding gates", node);
      }

      protected IStatus run(IProgressMonitor monitor)
      {
         try
         {
            monitor.beginTask(getName(), IProgressMonitor.UNKNOWN);
            if (node instanceof SystemModel)
            {
               ((SystemModel) node).findGates(monitor);
            }
            return (monitor.isCanceled() ?
                    Status.CANCEL_STATUS : Status.OK_STATUS);
         }
         catch (OperationCanceledException e)
         {
            return Status.CANCEL_STATUS;
         }
         catch (Exception e)
         {
            return SystemBrowserPlugin.createErrorStatus(
               "Error when finding the gates", e);
         }
         finally
         {
            monitor.done();
         }
      }
   }

   static class AddGateJob extends AbstractJob
   {
      private InetAddress address;
      private int port;

      AddGateJob(SystemModelNode node, InetAddress address, int port)
      {
         super("Adding gate " + address.getHostAddress() + ":" + port, node);
         this.address = address;
         this.port = port;
      }

      protected IStatus run(IProgressMonitor monitor)
      {
         try
         {
            monitor.beginTask(getName(), IProgressMonitor.UNKNOWN);
            if (node instanceof SystemModel)
            {
               SystemModel sm = (SystemModel) node;
               Gate gate = sm.getGate(address, port);
               if (gate == null)
               {
                  gate = sm.addGate(address, port);
                  if (gate == null)
                  {
                     return SystemBrowserPlugin.createErrorStatus(
                           "Specified gate is not responding.");
                  }
               }
               else
               {
                  return SystemBrowserPlugin.createErrorStatus(
                        "Specified gate already added.");
               }
            }
            return (monitor.isCanceled() ?
                    Status.CANCEL_STATUS : Status.OK_STATUS);
         }
         finally
         {
            monitor.done();
         }
      }
   }

   static class AddTargetJob extends AbstractJob
   {
      private String huntPath;

      AddTargetJob(SystemModelNode node, String huntPath)
      {
         super("Adding target " + huntPath, node);
         this.huntPath = huntPath;
      }

      protected IStatus run(IProgressMonitor monitor)
      {
         try
         {
            monitor.beginTask(getName(), IProgressMonitor.UNKNOWN);
            if (node instanceof Gate)
            {
               Target target = ((Gate) node).addTarget(huntPath);
               if (target == null)
               {
                  return SystemBrowserPlugin.createErrorStatus(
                        "Specified target already added.");
               }
            }
            else if (node instanceof Target)
            {
               Target target = (Target) node;
               ProcessFilter filter = new ProcessFilter();
               filter.filterName(false, huntPath);
               filter.filterType(false, ProcessInfo.TYPE_PHANTOM);
               ProcessInfo[] processes = target.getFilteredProcesses(monitor,
                     Target.SCOPE_SYSTEM, 0, filter);
               Gate gate = target.getGate();
               int numAddedTargets = 0;
               for (int i = 0; i < processes.length; i++)
               {
                  try
                  {
                     gate.addTarget(processes[i].getName());
                     numAddedTargets++;
                  } catch (Exception ignore) {}
                  if (monitor.isCanceled())
                  {
                     return Status.CANCEL_STATUS;
                  }
               }
               if (numAddedTargets == 0)
               {
                  return SystemBrowserPlugin.createErrorStatus(
                        "No matching targets found.");
               }
            }
            return (monitor.isCanceled() ?
                    Status.CANCEL_STATUS : Status.OK_STATUS);
         }
         catch (OperationCanceledException e)
         {
            return Status.CANCEL_STATUS;
         }
         catch (ServiceException e)
         {
            return SystemBrowserPlugin.createErrorStatus(
               "Error reported from target when adding the target " + huntPath, e);
         }
         catch (IOException e)
         {
            return SystemBrowserPlugin.createErrorStatus(
               "Error communicating with target when adding the target " + huntPath, e);
         }
         catch (Exception e)
         {
            return SystemBrowserPlugin.createErrorStatus(
               "Error when adding the target " + huntPath, e);
         }
         finally
         {
            monitor.done();
         }
      }
   }

   static class ConnectJob extends AbstractJob
   {
      ConnectJob(SystemModelNode node)
      {
         super("Connecting to " + node, node);
      }

      protected IStatus run(IProgressMonitor monitor)
      {
         try
         {
            monitor.beginTask(getName(), IProgressMonitor.UNKNOWN);
            if (node instanceof Gate)
            {
               ((Gate) node).connect(monitor);
            }
            else if (node instanceof Target)
            {
               Target target = (Target) node;
               SystemContentProvider.checkoutLicense(target);
               target.connect(monitor);
            }
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
         catch (IOException e)
         {
            return SystemBrowserPlugin.createErrorStatus(
               "Error communicating with target when connecting to " + node, e);
         }
         catch (Exception e)
         {
            return SystemBrowserPlugin.createErrorStatus(
               "Error when connecting to " + node, e);
         }
         finally
         {
            monitor.done();
         }
      }
   }

   static class DisconnectJob extends AbstractJob
   {
      DisconnectJob(SystemModelNode node)
      {
         super("Disconnecting from " + node, node);
      }

      protected IStatus run(IProgressMonitor monitor)
      {
         try
         {
            monitor.beginTask(getName(), IProgressMonitor.UNKNOWN);
            if (node instanceof Gate)
            {
               ((Gate) node).disconnect();
            }
            else if (node instanceof Target)
            {
               ((Target) node).disconnect();
            }
            return (monitor.isCanceled() ?
                    Status.CANCEL_STATUS : Status.OK_STATUS);
         }
         finally
         {
            monitor.done();
         }
      }
   }

   static class SetSysParamJob extends AbstractJob
   {
      private String name;
      private String value;
      private boolean reconfigure;

      SetSysParamJob(SystemModelNode node,
                     String name,
                     String value,
                     boolean reconfigure)
      {
         super("Setting system parameter for " + node, node);
         this.name = name;
         this.value = (((value != null) && (value.length() > 0)) ? value : null);
         this.reconfigure = ((this.value != null) ? reconfigure : false);
      }

      protected IStatus run(IProgressMonitor monitor)
      {
         try
         {
            monitor.beginTask(getName(), IProgressMonitor.UNKNOWN);
            if (node instanceof Target)
            {
               ((Target) node).setSysParam(monitor, reconfigure, name, value);
            }
            return (monitor.isCanceled() ?
                    Status.CANCEL_STATUS : Status.OK_STATUS);
         }
         catch (OperationCanceledException e)
         {
            return Status.CANCEL_STATUS;
         }
         catch (ServiceException e)
         {
            return SystemBrowserPlugin.createErrorStatus(
               "Error reported from target when setting system parameter for " + node, e);
         }
         catch (IOException e)
         {
            return SystemBrowserPlugin.createErrorStatus(
               "Error communicating with target when setting system parameter for " + node, e);
         }
         catch (Exception e)
         {
            return SystemBrowserPlugin.createErrorStatus(
               "Error when setting system parameter for " + node, e);
         }
         finally
         {
            monitor.done();
         }
      }
   }

   static class StartScopeJob extends AbstractJob
   {
      StartScopeJob(SystemModelNode node)
      {
         super("Starting " + node, node);
      }

      protected IStatus run(IProgressMonitor monitor)
      {
         try
         {
            monitor.beginTask(getName(), IProgressMonitor.UNKNOWN);
            if (node instanceof Segment)
            {
               ((Segment) node).start(monitor);
            }
            else if (node instanceof Block)
            {
               ((Block) node).start(monitor);
            }
            else if (node instanceof Process)
            {
               ((Process) node).start(monitor);
            }
            return (monitor.isCanceled() ?
                    Status.CANCEL_STATUS : Status.OK_STATUS);
         }
         catch (OperationCanceledException e)
         {
            return Status.CANCEL_STATUS;
         }
         catch (ServiceException e)
         {
            return SystemBrowserPlugin.createErrorStatus(
               "Error reported from target when starting " + node, e);
         }
         catch (IOException e)
         {
            return SystemBrowserPlugin.createErrorStatus(
               "Error communicating with target when starting " + node, e);
         }
         catch (Exception e)
         {
            return SystemBrowserPlugin.createErrorStatus(
               "Error when starting " + node, e);
         }
         finally
         {
            monitor.done();
         }
      }
   }

   static class StopScopeJob extends AbstractJob
   {
      StopScopeJob(SystemModelNode node)
      {
         super("Stopping " + node, node);
      }

      protected IStatus run(IProgressMonitor monitor)
      {
         try
         {
            monitor.beginTask(getName(), IProgressMonitor.UNKNOWN);
            if (node instanceof Segment)
            {
               ((Segment) node).stop(monitor);
            }
            else if (node instanceof Block)
            {
               ((Block) node).stop(monitor);
            }
            else if (node instanceof Process)
            {
               ((Process) node).stop(monitor);
            }
            return (monitor.isCanceled() ?
                    Status.CANCEL_STATUS : Status.OK_STATUS);
         }
         catch (OperationCanceledException e)
         {
            return Status.CANCEL_STATUS;
         }
         catch (ServiceException e)
         {
            return SystemBrowserPlugin.createErrorStatus(
               "Error reported from target when stopping " + node, e);
         }
         catch (IOException e)
         {
            return SystemBrowserPlugin.createErrorStatus(
               "Error communicating with target when stopping " + node, e);
         }
         catch (Exception e)
         {
            return SystemBrowserPlugin.createErrorStatus(
               "Error when stopping " + node, e);
         }
         finally
         {
            monitor.done();
         }
      }
   }

   static class KillScopeJob extends AbstractJob
   {
      KillScopeJob(SystemModelNode node)
      {
         super("Killing " + node, node);
      }

      protected IStatus run(IProgressMonitor monitor)
      {
         try
         {
            monitor.beginTask(getName(), IProgressMonitor.UNKNOWN);
            if (node instanceof Segment)
            {
               ((Segment) node).kill(monitor);
            }
            else if (node instanceof Block)
            {
               ((Block) node).kill(monitor);
            }
            else if (node instanceof Process)
            {
               ((Process) node).kill(monitor);
            }
            return (monitor.isCanceled() ?
                    Status.CANCEL_STATUS : Status.OK_STATUS);
         }
         catch (OperationCanceledException e)
         {
            return Status.CANCEL_STATUS;
         }
         catch (ServiceException e)
         {
            return SystemBrowserPlugin.createErrorStatus(
               "Error reported from target when killing " + node, e);
         }
         catch (IOException e)
         {
            return SystemBrowserPlugin.createErrorStatus(
               "Error communicating with target when killing " + node, e);
         }
         catch (Exception e)
         {
            return SystemBrowserPlugin.createErrorStatus(
               "Error when killing " + node, e);
         }
         finally
         {
            monitor.done();
         }
      }
   }

   static class SetEnvVarJob extends AbstractJob
   {
      private String name;
      private String value;

      SetEnvVarJob(SystemModelNode node, String name, String value)
      {
         super("Setting environment variable for " + node, node);
         this.name = name;
         this.value = (((value != null) && (value.length() > 0)) ? value : null);
      }

      protected IStatus run(IProgressMonitor monitor)
      {
         try
         {
            monitor.beginTask(getName(), IProgressMonitor.UNKNOWN);
            if (node instanceof Block)
            {
               ((Block) node).setEnvVar(monitor, name, value);
            }
            else if (node instanceof Process)
            {
               ((Process) node).setEnvVar(monitor, name, value);
            }
            return (monitor.isCanceled() ?
                    Status.CANCEL_STATUS : Status.OK_STATUS);
         }
         catch (OperationCanceledException e)
         {
            return Status.CANCEL_STATUS;
         }
         catch (ServiceException e)
         {
            return SystemBrowserPlugin.createErrorStatus(
               "Error reported from target when setting environment variable for " + node, e);
         }
         catch (IOException e)
         {
            return SystemBrowserPlugin.createErrorStatus(
               "Error communicating with target when setting environment variable for " + node, e);
         }
         catch (Exception e)
         {
            return SystemBrowserPlugin.createErrorStatus(
               "Error when setting environment variable for " + node, e);
         }
         finally
         {
            monitor.done();
         }
      }
   }

   static class SignalSemaphoreJob extends AbstractJob
   {
      SignalSemaphoreJob(SystemModelNode node)
      {
         super("Signaling fast semaphore on " + node, node);
      }

      protected IStatus run(IProgressMonitor monitor)
      {
         try
         {
            monitor.beginTask(getName(), IProgressMonitor.UNKNOWN);
            if (node instanceof Process)
            {
               ((Process) node).signalSemaphore(monitor);
            }
            return (monitor.isCanceled() ?
                    Status.CANCEL_STATUS : Status.OK_STATUS);
         }
         catch (OperationCanceledException e)
         {
            return Status.CANCEL_STATUS;
         }
         catch (ServiceException e)
         {
            return SystemBrowserPlugin.createErrorStatus(
               "Error reported from target when signaling the fast semaphore on " + node, e);
         }
         catch (IOException e)
         {
            return SystemBrowserPlugin.createErrorStatus(
               "Error communicating with target when signaling the fast semaphore on " + node, e);
         }
         catch (Exception e)
         {
            return SystemBrowserPlugin.createErrorStatus(
               "Error when signaling the fast semaphore on " + node, e);
         }
         finally
         {
            monitor.done();
         }
      }
   }

   static class SetPriorityJob extends AbstractJob
   {
      private int priority;

      SetPriorityJob(SystemModelNode node, int priority)
      {
         super("Setting priority for " + node, node);
         this.priority = priority;
      }

      protected IStatus run(IProgressMonitor monitor)
      {
         try
         {
            monitor.beginTask(getName(), IProgressMonitor.UNKNOWN);
            if (node instanceof Process)
            {
               ((Process) node).setPriority(monitor, priority);
            }
            return (monitor.isCanceled() ?
                    Status.CANCEL_STATUS : Status.OK_STATUS);
         }
         catch (OperationCanceledException e)
         {
            return Status.CANCEL_STATUS;
         }
         catch (ServiceException e)
         {
            return SystemBrowserPlugin.createErrorStatus(
               "Error reported from target when setting the priority for " + node, e);
         }
         catch (IOException e)
         {
            return SystemBrowserPlugin.createErrorStatus(
               "Error communicating with target when setting the priority for " + node, e);
         }
         catch (Exception e)
         {
            return SystemBrowserPlugin.createErrorStatus(
               "Error when setting the priority for " + node, e);
         }
         finally
         {
            monitor.done();
         }
      }
   }

   static class SetExecutionUnitJob extends AbstractJob
   {
      private short executionUnit;

      SetExecutionUnitJob(SystemModelNode node, short executionUnit)
      {
         super("Setting execution unit for " + node, node);
         this.executionUnit = executionUnit;
      }

      protected IStatus run(IProgressMonitor monitor)
      {
         try
         {
            monitor.beginTask(getName(), IProgressMonitor.UNKNOWN);
            if (node instanceof Block)
            {
               ((Block) node).setExecutionUnit(monitor, executionUnit);
            }
            else if (node instanceof Process)
            {
               ((Process) node).setExecutionUnit(monitor, executionUnit);
            }
            return (monitor.isCanceled() ?
                    Status.CANCEL_STATUS : Status.OK_STATUS);
         }
         catch (OperationCanceledException e)
         {
            return Status.CANCEL_STATUS;
         }
         catch (ServiceException e)
         {
            return SystemBrowserPlugin.createErrorStatus(
               "Error reported from target when setting execution unit for " + node, e);
         }
         catch (IOException e)
         {
            return SystemBrowserPlugin.createErrorStatus(
               "Error communicating with target when setting execution unit for " + node, e);
         }
         catch (Exception e)
         {
            return SystemBrowserPlugin.createErrorStatus(
               "Error when setting execution unit for " + node, e);
         }
         finally
         {
            monitor.done();
         }
      }
   }
}
