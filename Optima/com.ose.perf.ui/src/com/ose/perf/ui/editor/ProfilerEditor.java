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

import java.io.IOException;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import org.eclipse.core.filesystem.EFS;
import org.eclipse.core.filesystem.IFileStore;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.debug.core.ILaunch;
import org.eclipse.debug.core.sourcelookup.ISourceLookupDirector;
import org.eclipse.debug.core.sourcelookup.containers.LocalFileStorage;
import org.eclipse.debug.ui.sourcelookup.ISourceDisplay;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.layout.TreeColumnLayout;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.viewers.ColumnWeightData;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeColumn;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.IPartListener;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.forms.widgets.Form;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.ide.FileStoreEditorInput;
import org.eclipse.ui.part.EditorPart;
import org.eclipse.ui.part.FileEditorInput;
import org.eclipse.ui.texteditor.ITextEditor;
import com.ose.perf.elf.Elf;
import com.ose.perf.elf.FileLineInfo;
import com.ose.perf.elf.Symbol;
import com.ose.perf.format.ReportReader;
import com.ose.perf.format.ReportReaderClient;
import com.ose.perf.ui.ProfilerPlugin;
import com.ose.perf.ui.editor.nodes.TreeNode;
import com.ose.perf.ui.editor.nodes.CounterTypeNode;
import com.ose.perf.ui.editor.nodes.BinaryNode;
import com.ose.perf.ui.editor.nodes.FunctionNode;
import com.ose.perf.ui.editor.nodes.LineNode;
import com.ose.perf.ui.editor.nodes.AddressNode;
import com.ose.perf.ui.launch.BinaryFileContainer;
import com.ose.system.LoadModuleInfo;
import com.ose.system.PerformanceCounterReport;
import com.ose.system.Target;

/*
 * XXX: The current implementation does not support source profiling dump
 * files containing multiple performance counter types of the same type but
 * for different execution units.
 */

public class ProfilerEditor extends EditorPart
{
   static final int COLUMN_NAME = 0;
   static final int COLUMN_BARS = 1;
   static final int COLUMN_RELATIVE = 2;
   static final int COLUMN_ABSOLUTE = 3;

   private static final String UNKNOWN_BINARY_FILE = "<Unknown>";

   private final Object editorOpenedLock = new Object();
   private boolean editorOpened;
   private volatile boolean disposed;

   private String target;
   private List binaryFiles;
   private ISourceLookupDirector sourceLookupDirector;

   private final Map typeToCounterTypeMap = new HashMap();
   private final Map nameToCounterTypeMap = new HashMap();
   private final Map loadModules = new HashMap();
   private final Map processes = new HashMap();

   private FormToolkit toolkit;
   private Combo counterTypeCombo;
   private Label headerLabel;

   private TreeViewer viewer;
   private ProfilerSorter sorter;

   public void init(IEditorSite site, IEditorInput input)
      throws PartInitException
   {
      if (!(input instanceof ProfilerEditorInput))
      {
         throw new PartInitException("Invalid editor input");
      }

      ProfilerEditorInput profInput = (ProfilerEditorInput) input;
      binaryFiles = profInput.getBinaryFiles();
      sourceLookupDirector = profInput.getSourceLookupDirector();

      setSite(site);
      setInput(input);
      setPartName(input.getName());

      String path = profInput.getResultsFile().getAbsolutePath();
      Job job = new ReportReaderJob(path);
      job.schedule();
   }

   public void createPartControl(Composite parent)
   {
      toolkit = new FormToolkit(parent.getDisplay());
      toolkit.getColors().initializeSectionToolBarColors();
      Form form = toolkit.createForm(parent);
      form.setText("Source Profiler Results");
      GridLayout layout = new GridLayout(1, false);
      layout.marginWidth = 0;
      layout.marginHeight = 0;
      form.getBody().setLayout(layout);

      Composite headerComp = toolkit.createComposite(form.getHead());
      layout = new GridLayout(3, false);
      layout.marginWidth = 0;
      layout.marginHeight = 0;
      headerComp.setLayout(layout);

      Label counterTypeLabel = toolkit.createLabel(headerComp, null);
      counterTypeLabel.setText("Performance Counter:");
      counterTypeCombo = new Combo(headerComp, SWT.READ_ONLY);
      GridData gd = new GridData();
      gd.widthHint = 120;
      counterTypeCombo.setLayoutData(gd);
      toolkit.adapt(counterTypeCombo);
      counterTypeCombo.addSelectionListener(new CounterTypeComboSelectionHandler());

      headerLabel = toolkit.createLabel(headerComp, null);
      headerLabel.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
      form.setHeadClient(headerComp);

      createUI(form.getBody());

      getSite().getPage().addPartListener(new EditorOpenedHandler());
   }

   public void dispose()
   {
      super.dispose();
      terminateLaunch();
      toolkit.dispose();
      disposed = true;
   }

   public boolean isDisposed()
   {
      return disposed;
   }

   public void setFocus()
   {
      viewer.getControl().setFocus();
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

   private void createUI(Composite parent)
   {
      Composite comp = toolkit.createComposite(parent);
      GridLayout layout = new GridLayout(1, false);
      layout.marginWidth = 2;
      layout.marginHeight = 2;
      comp.setLayout(layout);
      comp.setLayoutData(new GridData(GridData.FILL_BOTH));

      createTreeTableViewer(comp);
      updateHeaderLabel();
   }

   private void createTreeTableViewer(Composite parent)
   {
      Composite comp = new Composite(parent, SWT.NONE);
      TreeColumnLayout layout = new TreeColumnLayout();
      comp.setLayout(layout);
      comp.setLayoutData(new GridData(GridData.FILL_BOTH));

      Tree tree = new Tree(comp, SWT.SINGLE | SWT.BORDER | SWT.H_SCROLL
            | SWT.V_SCROLL | SWT.FULL_SELECTION | SWT.VIRTUAL);
      tree.setHeaderVisible(true);

      TreeColumn nameColumn = new TreeColumn(tree, SWT.LEFT);
      nameColumn.setText("Name");
      nameColumn.addSelectionListener(new ColumnSelectionHandler(COLUMN_NAME));
      layout.setColumnData(nameColumn, new ColumnWeightData(30, 260));

      TreeColumn barColumn = new TreeColumn(tree, SWT.LEFT);
      barColumn.setText("");
      barColumn.setMoveable(true);
      barColumn.addSelectionListener(new ColumnSelectionHandler(COLUMN_BARS));
      layout.setColumnData(barColumn, new ColumnWeightData(15, 160));

      TreeColumn relativeValueColumn = new TreeColumn(tree, SWT.RIGHT);
      relativeValueColumn.setText("Relative Value");
      relativeValueColumn.setMoveable(true);
      relativeValueColumn.addSelectionListener(new ColumnSelectionHandler(COLUMN_RELATIVE));
      layout.setColumnData(relativeValueColumn, new ColumnWeightData(10, 100));

      TreeColumn absoluteValueColumn = new TreeColumn(tree, SWT.RIGHT);
      absoluteValueColumn.setText("Absolute Value");
      absoluteValueColumn.setMoveable(true);
      absoluteValueColumn.addSelectionListener(new ColumnSelectionHandler(COLUMN_ABSOLUTE));
      layout.setColumnData(absoluteValueColumn, new ColumnWeightData(10, 100));

      sorter = new ProfilerSorter();
      viewer = new TreeViewer(tree);
      viewer.setUseHashlookup(true);
      viewer.setContentProvider(new ProfilerContentProvider());
      viewer.setLabelProvider(new ProfilerLabelProvider());
      viewer.setSorter(sorter);
      viewer.setAutoExpandLevel(2);

      tree.addListener(SWT.EraseItem, new DrawBarHandler());
      tree.addSelectionListener(new TreeNodeSelectionHandler());
   }

   /*
    * Using the given hit counters, build the tree of counter type nodes,
    * binary nodes, function nodes, line nodes and address nodes.
    */
   private boolean buildTree(Map collatedReports)
   {
      boolean binaryErrors = false;
      List unknownBinaryNodes = new LinkedList();

      // Add load module information to each user specified binary file that is
      // a non-absolute linked load module.
      for (Iterator i = binaryFiles.iterator(); i.hasNext();)
      {
         BinaryFileContainer binaryFile = (BinaryFileContainer) i.next();
         addLoadModuleInfo(binaryFile);
      }

      // Add a binary node for each user specified binary file to
      // each counter type root node.
      for (Iterator typeIter = typeToCounterTypeMap.values().iterator();
            typeIter.hasNext();)
      {
         TreeNode rootNode = (TreeNode) typeIter.next();

         for (Iterator binIter = binaryFiles.iterator(); binIter.hasNext();)
         {
            BinaryFileContainer binaryFile = (BinaryFileContainer) binIter.next();
            BinaryNode binaryNode = new BinaryNode(binaryFile.getPath());
            rootNode.addChild(binaryNode);
         }

         // Also add a special binary node for all PC addresses that could not
         // be mapped to a function in one of the binary files.
         BinaryNode unknownBinaryNode = new BinaryNode(UNKNOWN_BINARY_FILE);
         rootNode.addChild(unknownBinaryNode);
         unknownBinaryNodes.add(unknownBinaryNode);
      }

      // Iterate over all hit counters and, if available, add symbolic
      // information to them. Then build the tree by passing each hit
      // counter to the addToTree() method.
      for (Iterator iter = collatedReports.keySet().iterator(); iter.hasNext();)
      {
         Integer type = (Integer) iter.next();
         Map hitCounters = (Map) collatedReports.get(type);

         for (Iterator hitIter = hitCounters.values().iterator(); hitIter.hasNext();)
         {
            HitCounter hitCounter = (HitCounter) hitIter.next();
            String binaryPath = UNKNOWN_BINARY_FILE;
            Symbol symbol = null;
            FileLineInfo fileLineInfo = null;

            // Look up symbol, file and line in each binary file until
            // one that matches is found.
            for (Iterator binIter = binaryFiles.iterator(); binIter.hasNext();)
            {
               try
               {
                  BinaryFileContainer binaryFile = (BinaryFileContainer) binIter.next();
                  Elf elf = binaryFile.getElf();
                  long address = hitCounter.getAddress();

                  // Look up the symbol from the DWARF2 debug info if available,
                  // otherwise from the symbol table.
                  symbol = elf.getDebugSymbol(address);
                  if (symbol == null)
                  {
                     symbol = elf.getSymbol(address);
                  }

                  if (symbol != null)
                  {
                     binaryPath = elf.getPath();
                     fileLineInfo = elf.getFileLineInfo(address);
                     break;
                  }
               }
               catch (IOException e)
               {
                  binaryErrors = true;
                  ProfilerPlugin.log(e);
               }
            }

            hitCounter.setBinaryFile(binaryPath);
            hitCounter.setSymbol(symbol);
            hitCounter.setFileLineInfo(fileLineInfo);

            addToTree(hitCounter);
         }
      }

      // Remove empty binary nodes from the tree and the list.
      removeEmptyBinaryNodes(unknownBinaryNodes);

      return  binaryErrors;
   }

   /*
    * Add load module information to the given binary file if it is a
    * non-absolute linked load module and we have load module information
    * for it.
    */
   private void addLoadModuleInfo(BinaryFileContainer binaryFile)
   {
      String installHandle = binaryFile.getInstallHandle();
      if (!installHandle.equals(""))
      {
         LoadModuleInfo loadModule =
               (LoadModuleInfo) loadModules.get(installHandle);
         if ((loadModule != null) &&
             ((loadModule.getOptions() & LoadModuleInfo.LOAD_MODULE_ABSOLUTE) == 0))
         {
            binaryFile.setLoadModule(loadModule);
         }
      }
   }

   /*
    * Add the given hit counter to the tree by finding its binary node and
    * creating a corresponding address node for it. If the hit counter contains
    * symbolic information, its address node is added to the corresponding
    * function node or line node (which are both created if needed).
    */
   private void addToTree(HitCounter hitCounter)
   {
      CounterTypeNode rootNode = (CounterTypeNode)
         typeToCounterTypeMap.get(hitCounter.getCounterType());

      // Iterate over all the top level binary nodes.
      for (Iterator i = rootNode.getChildrenIterator(); i.hasNext();)
      {
         Object nextNode = i.next();
         if (!(nextNode instanceof BinaryNode))
         {
            continue;
         }

         BinaryNode binaryNode = (BinaryNode) nextNode;
         if (hitCounter.getBinaryFile().equals(binaryNode.getPath()))
         {
            if (hitCounter.getFunction() != null)
            {
               FunctionNode functionNode =
                  findFunctionNode(binaryNode, hitCounter.getFunction());
               if (functionNode == null)
               {
                  functionNode = new FunctionNode(binaryNode,
                        hitCounter.getFunction(), hitCounter.getFullPath());
               }

               if (hitCounter.getLineNumber() >= 0)
               {
                  LineNode lineNode =
                     findLineNode(functionNode, hitCounter.getLineNumber());
                  if (lineNode == null)
                  {
                     lineNode =
                        new LineNode(functionNode, hitCounter.getLineNumber());
                  }

                  new AddressNode(lineNode, hitCounter.getAddress(),
                        hitCounter.getHitCount(), rootNode.getReportCount());
               }
               else
               {
                 new AddressNode(functionNode, hitCounter.getAddress(),
                       hitCounter.getHitCount(), rootNode.getReportCount());
               }
            }
            else
            {
               new AddressNode(binaryNode, hitCounter.getAddress(),
                     hitCounter.getHitCount(), rootNode.getReportCount());
            }

            // Hit counter info added to a binary node, we're done.
            break;
         }
      }
   }

   private static FunctionNode findFunctionNode(BinaryNode binaryNode,
                                                String function)
   {
      FunctionNode functionNode = null;

      for (Iterator i = binaryNode.getChildrenIterator(); i.hasNext();)
      {
         Object nextNode = i.next();
         if (!(nextNode instanceof FunctionNode))
         {
            continue;
         }

         functionNode = (FunctionNode) nextNode;
         if (functionNode.getFunctionName().equals(function))
         {
            break;
         }
         else
         {
            functionNode = null;
         }
      }

      return functionNode;
   }

   private static LineNode findLineNode(FunctionNode functionNode,
                                        long lineNumber)
   {
      LineNode lineNode = null;

      for (Iterator i = functionNode.getChildrenIterator(); i.hasNext();)
      {
         Object nextNode = i.next();
         if (!(nextNode instanceof LineNode))
         {
            continue;
         }

         lineNode = (LineNode) nextNode;
         if (lineNode.getLineNumber() == lineNumber)
         {
            break;
         }
         else
         {
            lineNode = null;
         }
      }

      return lineNode;
   }

   /*
    * Remove empty binary nodes from the tree and the given list of binary
    * nodes.
    */
   private void removeEmptyBinaryNodes(List binaryNodes)
   {
      for (Iterator i = typeToCounterTypeMap.values().iterator(); i.hasNext();)
      {
         TreeNode rootNode = (TreeNode) i.next();
         if (rootNode instanceof CounterTypeNode)
         {
            for (Iterator j = rootNode.getChildrenIterator(); j.hasNext();)
            {
               TreeNode node = (TreeNode) j.next();
               if ((node instanceof BinaryNode) && !node.hasChildren())
               {
                  j.remove();
                  binaryNodes.remove(node);
               }
            }
         }
      }
   }

   private void setTree(boolean binaryErrors)
   {
      // Set the default viewer input to be the tree with the
      // lowest counter type number.
      List sortedTypes = new ArrayList(typeToCounterTypeMap.keySet());
      Collections.sort(sortedTypes);
      setCurrentCounterType((Integer) sortedTypes.get(0));

      // The unknown binary nodes should be collapsed, unlike the others.
      for (Iterator i = typeToCounterTypeMap.values().iterator(); i.hasNext();)
      {
         TreeNode rootNode = (TreeNode) i.next();
         if (rootNode instanceof CounterTypeNode)
         {
            for (Iterator j = rootNode.getChildrenIterator(); j.hasNext();)
            {
               TreeNode node = (TreeNode) j.next();
               if (node instanceof BinaryNode)
               {
                  BinaryNode binaryNode = (BinaryNode) node;
                  if (binaryNode.getPath().equals(UNKNOWN_BINARY_FILE))
                  {
                     viewer.collapseToLevel(binaryNode, 1);
                  }
               }
            }
         }
      }

      // Update the user interface.
      viewer.refresh();
      populateCounterTypeCombo();
      updateHeaderLabel();

      // Report any binary errors.
      if (binaryErrors)
      {
         MessageDialog.openWarning(getSite().getShell(), "Warning",
            "Errors occurred while parsing the binary file(s) for symbolic " +
            "information.\nSee the Eclipse error log for more information.");
      }
   }

   private void setCurrentCounterType(int type)
   {
      CounterTypeNode typeNode =
         (CounterTypeNode) typeToCounterTypeMap.get(type);
      viewer.setInput(typeNode);
      updateHeaderLabel();
   }

   private void setCurrentCounterType(String name)
   {
      CounterTypeNode typeNode =
         (CounterTypeNode) nameToCounterTypeMap.get(name);
      viewer.setInput(typeNode);
      updateHeaderLabel();
   }

   private CounterTypeNode getCurrentCounterTypeNode()
   {
      return (CounterTypeNode) viewer.getInput();
   }

   private void populateCounterTypeCombo()
   {
      CounterTypeNode typeNode;
      List types = new ArrayList(typeToCounterTypeMap.keySet());
      Collections.sort(types);

      counterTypeCombo.removeAll();

      for (Iterator i = types.iterator(); i.hasNext();)
      {
         Integer type = (Integer) i.next();
         typeNode = (CounterTypeNode) typeToCounterTypeMap.get(type);
         counterTypeCombo.add(typeNode.getName());
      }

      int firstType = ((Integer) types.get(0)).intValue();
      typeNode = (CounterTypeNode) typeToCounterTypeMap.get(firstType);
      counterTypeCombo.setText(typeNode.getName());
   }

   private String getHeaderString()
   {
      short executionUnit = 0;
      long triggerValue = 0;
      int totalReportCount = 0;
      int totalReportLossCount = 0;

      CounterTypeNode typeNode = getCurrentCounterTypeNode();
      if (typeNode != null)
      {
         executionUnit = typeNode.getExecutionUnit();
         triggerValue = typeNode.getTriggerValue();
         totalReportCount = typeNode.getReportCount();
         totalReportLossCount = typeNode.getReportLossCount();
      }

      return "   Trigger Value: " + triggerValue +
             "   Target: " + target +
             "   Execution Unit: " + toExecutionUnitString(executionUnit) +
             "   Reports: " + toU32String(totalReportCount) +
             "   Lost Reports: " + toU32String(totalReportLossCount);
   }

   private void updateHeaderLabel()
   {
      headerLabel.setText(getHeaderString());
   }

   private IStatus openSourceCodeEditor(String path, long lineNumber, boolean initialAttempt)
   {
      try
      {
         Object element = sourceLookupDirector.getSourceElement(path);
         if ((element == null) && initialAttempt)
         {
            IWorkbenchWindow window =
               PlatformUI.getWorkbench().getActiveWorkbenchWindow();
            IWorkbenchPage page = window.getActivePage();
            SourceCodeEditorOpener editorOpener = new SourceCodeEditorOpener(path, lineNumber);
            ILaunch launch = ((ProfilerEditorInput) getEditorInput()).getLaunch();
            SourceNotFoundEditorInput notFoundEditorInput =
               new SourceNotFoundEditorInput(editorOpener, launch, path);
            page.openEditor(notFoundEditorInput,
                  "com.ose.perf.ui.editor.SourceNotFoundEditor");
            return Status.OK_STATUS;
         }
         else if ((element == null) && !initialAttempt)
         {
            return ProfilerPlugin.createErrorStatus(
                  "Error when opening the source code file " + path,
                  new IOException("File " + path + " not found."));
         }

         IEditorInput editorInput;
         if (element instanceof LocalFileStorage)
         {
            LocalFileStorage localFileStorage = (LocalFileStorage) element;
            IFileStore fileStore = EFS.getStore(localFileStorage.getFile().toURI());
            editorInput = new FileStoreEditorInput(fileStore);
         }
         else if (element instanceof IFile)
         {
            IFile file = (IFile) element;
            editorInput = new FileEditorInput(file);
         }
         else
         {
            return ProfilerPlugin.createErrorStatus(
                  "Error when opening the source code file " + path,
                  new IOException("Unknown file provider: " +
                        element.getClass().getName()));
         }

         IWorkbenchWindow window =
            PlatformUI.getWorkbench().getActiveWorkbenchWindow();
         IWorkbenchPage page = window.getActivePage();
         IEditorPart sourceEditor = page.openEditor(editorInput,
            "org.eclipse.cdt.ui.editor.CEditor");
         goToLine(sourceEditor, lineNumber);
         return Status.OK_STATUS;
      }
      catch (Exception e)
      {
         return ProfilerPlugin.createErrorStatus(
               "Error when opening the source code file " + path, e);
      }
   }

   private void goToLine(IEditorPart editorPart, long lineNumber)
   {
      if (!(editorPart instanceof ITextEditor) || (lineNumber <= 0))
      {
         return;
      }

      ITextEditor editor = (ITextEditor) editorPart;
      IDocument document = editor.getDocumentProvider().getDocument(
            editor.getEditorInput());

      if (document != null)
      {
         IRegion lineInfo = null;
         try
         {
            lineInfo = document.getLineInformation((int)(lineNumber - 1));
         }
         catch (BadLocationException e)
         {
            ProfilerPlugin.log(ProfilerPlugin.createErrorStatus(
               "A line number from the debug info in a binary file was " +
               "greater than the number of lines in the corresponding source " +
               "code file.", e));
         }

         if (lineInfo != null)
         {
            editor.selectAndReveal(lineInfo.getOffset(), lineInfo.getLength());
         }
      }
   }

   private void terminateLaunch()
   {
      try
      {
         ProfilerEditorInput editorInput = (ProfilerEditorInput) getEditorInput();
         ILaunch launch = editorInput.getLaunch();
         if (!launch.isTerminated())
         {
            launch.terminate();
         }
      } catch (Exception ignore) {}
   }

   private static void asyncExec(Runnable runnable)
   {
      Display display = PlatformUI.getWorkbench().getDisplay();
      if (!display.isDisposed())
      {
         display.asyncExec(runnable);
      }
   }

   private static String toExecutionUnitString(short executionUnit)
   {
      return ((executionUnit == Target.ALL_EXECUTION_UNITS) ?
            "All" : Integer.toString(executionUnit & 0xFFFF));
   }

   private static String toU32String(int i)
   {
      return Long.toString(i & 0xFFFFFFFFL);
   }

   private class EditorOpenedHandler implements IPartListener
   {
      public void partOpened(IWorkbenchPart part)
      {
         synchronized (editorOpenedLock)
         {
            getSite().getPage().removePartListener(this);
            editorOpened = true;
            editorOpenedLock.notifyAll();
         }
      }

      public void partActivated(IWorkbenchPart part) {}

      public void partBroughtToTop(IWorkbenchPart part) {}

      public void partDeactivated(IWorkbenchPart part) {}

      public void partClosed(IWorkbenchPart part) {}
   }

   private class CounterTypeComboSelectionHandler extends SelectionAdapter
   {
      public void widgetSelected(SelectionEvent event)
      {
         int selectedIndex = counterTypeCombo.getSelectionIndex();
         if (selectedIndex >= 0)
         {
            String selectedType = counterTypeCombo.getItem(selectedIndex);
            setCurrentCounterType(selectedType);
            viewer.refresh();
         }
      }
   }

   private class ColumnSelectionHandler extends SelectionAdapter
   {
      private final int column;

      ColumnSelectionHandler(int column)
      {
         this.column = column;
      }

      public void widgetSelected(SelectionEvent event)
      {
         // XXX: Why is the explicit expansion necessary?
         Object[] expandedElements = viewer.getExpandedElements();
         sorter.setColumn(column);
         viewer.refresh();
         viewer.setExpandedElements(expandedElements);
      }
   }

   private static class DrawBarHandler implements Listener
   {
      public void handleEvent(Event event)
      {
         // Only interested in the bars column.
         if ((event.type == SWT.EraseItem) && (event.index == COLUMN_BARS))
         {
            // Get the entity that corresponds to the current table item.
            TreeItem item = (TreeItem) event.item;
            TreeNode node = (TreeNode) item.getData();

            if (node != null)
            {
               int width = (int) Math.round(((double)event.width - 4.0)
                     * node.getRelativeHitCount());
               int height = event.height - 4;
               int xOffset = 2;
               int yOffset = 2;

               event.gc.setBackground(node.getColor());
               event.gc.fillRectangle(event.x + xOffset, event.y + yOffset,
                                      width, height);

               // Make sure no foreground, selection or hover
               // graphics are drawn, by masking them out.
               event.detail &= ~(SWT.FOREGROUND | SWT.SELECTED | SWT.HOT);
            }
         }
      }
   }

   private class TreeNodeSelectionHandler extends SelectionAdapter
   {
      public void widgetDefaultSelected(SelectionEvent event)
      {
         String path = null;
         long line = -1;
         Object nodeData = ((TreeItem) event.item).getData();

         if (nodeData instanceof LineNode)
         {
            LineNode lineNode = (LineNode) nodeData;
            line = lineNode.getLineNumber();
            FunctionNode functionNode = (FunctionNode) lineNode.getParent();
            path = functionNode.getPath();
         }
         else if (nodeData instanceof AddressNode)
         {
            AddressNode addressNode = (AddressNode) nodeData;
            if (addressNode.getParent() instanceof LineNode)
            {
               LineNode lineNode = (LineNode) addressNode.getParent();
               line = lineNode.getLineNumber();
               FunctionNode functionNode = (FunctionNode) lineNode.getParent();
               path = functionNode.getPath();
            }
         }
         else if (nodeData instanceof FunctionNode)
         {
            FunctionNode functionNode = (FunctionNode) nodeData;
            if (functionNode.hasChildren())
            {
               TreeNode childNode = (TreeNode) functionNode.getChildren()[0];
               if (childNode instanceof LineNode)
               {
                  LineNode lineNode = (LineNode) childNode;
                  line = lineNode.getLineNumber();
                  path = functionNode.getPath();
               }
            }
         }

         if ((path != null) && (line > 0))
         {
            IStatus status = openSourceCodeEditor(path, line, true);
            if (!status.isOK())
            {
               ProfilerPlugin.errorDialog(null, null, status);
            }
         }
      }
   }

   private class SourceCodeEditorOpener implements ISourceDisplay, IAdaptable
   {
      private final String path;
      private final long lineNumber;

      SourceCodeEditorOpener(String path, long lineNumber)
      {
         this.path = path;
         this.lineNumber = lineNumber;
      }

      public void displaySource(Object element, IWorkbenchPage page,
            boolean forceSourceLookup)
      {
         IStatus status = openSourceCodeEditor(path, lineNumber, false);
         if (!status.isOK())
         {
            ProfilerPlugin.errorDialog(null, null, status);
         }
      }

      public Object getAdapter(Class adapterClass)
      {
         if (adapterClass == ISourceDisplay.class)
         {
            return this;
         }
         return null;
      }
   }

   /*
    * This class represents a PC address from a profiling session for a certain
    * counter type and execution unit and contains, if available, symbolic
    * information derived from the PC address and the number of hits for this
    * particular PC address in the profiling data.
    */
   private static class HitCounter
   {
      private final int counterType;
      private final short executionUnit;
      private final long address;
      private final int pid;
      private long hitCount;
      private String binaryFile;
      private Symbol symbol;
      private FileLineInfo fileLineInfo;

      HitCounter(int counterType, short executionUnit, long address, int pid)
      {
         this.counterType = counterType;
         this.executionUnit = executionUnit;
         this.address = address;
         this.pid = pid;
      }

      public int getCounterType()
      {
         return counterType;
      }

      public short getExecutionUnit()
      {
         return executionUnit;
      }

      public long getAddress()
      {
         return address;
      }

      public int getPid()
      {
         return pid;
      }

      public long getHitCount()
      {
         return hitCount;
      }

      void addHit()
      {
         hitCount++;
      }

      public String getBinaryFile()
      {
         return binaryFile;
      }

      void setBinaryFile(String binaryFile)
      {
         this.binaryFile = binaryFile;
      }

      public String getFunction()
      {
         if (symbol == null)
         {
            return null;
         }
         return symbol.getName();
      }

      void setSymbol(Symbol symbol)
      {
         this.symbol = symbol;
      }

      public String getFilename()
      {
         if (fileLineInfo == null)
         {
            return null;
         }
         return fileLineInfo.getFilename();
      }

      public String getPath()
      {
         if (fileLineInfo == null)
         {
            return null;
         }
         return fileLineInfo.getPath();
      }

      public String getFullPath()
      {
         if (fileLineInfo == null)
         {
            return null;
         }
         return fileLineInfo.getFullPath();
      }

      public long getLineNumber()
      {
         if (fileLineInfo == null)
         {
            return -1;
         }
         return fileLineInfo.getLine();
      }

      void setFileLineInfo(FileLineInfo fileLineInfo)
      {
         this.fileLineInfo = fileLineInfo;
      }
   }

   private class ReportReaderHandler implements ReportReaderClient
   {
      private final IProgressMonitor monitor;

      private final Map collatedReports = new HashMap();

      ReportReaderHandler(IProgressMonitor monitor)
      {
         this.monitor = monitor;
      }

      Map getCollatedReports()
      {
         return collatedReports;
      }

      public void targetSettingsRead(String target,
                                     boolean bigEndian,
                                     int osType,
                                     int cpuType,
                                     int numExecutionUnits,
                                     int tickLength,
                                     int microTickFrequency)
      {
         ProfilerEditor.this.target = target;
      }

      public void loadModuleRead(LoadModuleInfo loadModule)
      {
         loadModules.put(loadModule.getInstallHandle(), loadModule);
      }

      public void processRead(int id, String name)
      {
         processes.put(id, name);
      }

      public void performanceCounterRead(int type, String name)
      {
         if (!typeToCounterTypeMap.containsKey(type))
         {
            CounterTypeNode typeNode = new CounterTypeNode(type);
            typeNode.setName(name);
            typeToCounterTypeMap.put(type, typeNode);
            nameToCounterTypeMap.put(name, typeNode);
         }
         else
         {
            CounterTypeNode typeNode =
               (CounterTypeNode) typeToCounterTypeMap.get(type);
            if (!nameToCounterTypeMap.containsKey(name))
            {
               typeNode.setName(name);
               nameToCounterTypeMap.put(name, typeNode);
            }
         }
      }

      public void performanceCounterEnablementRead(short executionUnit,
                                                   int type,
                                                   long value,
                                                   int maxReports)
      {
         CounterTypeNode typeNode;

         if (!typeToCounterTypeMap.containsKey(type))
         {
            typeNode = new CounterTypeNode(type);
            typeToCounterTypeMap.put(type, typeNode);
         }
         else
         {
            typeNode = (CounterTypeNode) typeToCounterTypeMap.get(type);
         }

         typeNode.setExecutionUnit(executionUnit);
         typeNode.setTriggerValue(value);
         typeNode.setMaxReports(maxReports);
      }

      public void reportsRead(short executionUnit,
                              int type,
                              PerformanceCounterReport[] reports)
      {
         CounterTypeNode typeNode;

         if (monitor.isCanceled() || isDisposed())
         {
            throw new OperationCanceledException("Cancelled reading report file");
         }

         // Find or create the root node of the tree for this counter type,
         // and then increase the report count for this counter type.
         if (!typeToCounterTypeMap.containsKey(type))
         {
            typeNode = new CounterTypeNode(type);
            typeToCounterTypeMap.put(type, typeNode);
         }
         else
         {
            typeNode = (CounterTypeNode) typeToCounterTypeMap.get(type);
         }
         typeNode.addReportCount(reports.length);

         // Find the hit counters for this particular counter type.
         Map hitCounters = (Map) collatedReports.get(type);
         if (hitCounters == null)
         {
            // There was no hit counter map for this counter type,
            // so create one.
            hitCounters = new HashMap();
            collatedReports.put(type, hitCounters);
         }

         // Iterate over all reports and count the number of occurrences
         // for each PC address.
         for (int i = 0; i < reports.length; i++)
         {
            PerformanceCounterReport report = reports[i];
            long pcAddress = report.getPC();
            HitCounter hitCounter = (HitCounter) hitCounters.get(pcAddress);
            if (hitCounter == null)
            {
               hitCounter = new HitCounter(type, executionUnit, pcAddress, report.getPid());
               hitCounters.put(pcAddress, hitCounter);
            }
            hitCounter.addHit();
         }
      }

      public void reportsLossRead(short executionUnit,
                                  int type,
                                  int numReportsLost)
      {
         CounterTypeNode typeNode;

         if (monitor.isCanceled() || isDisposed())
         {
            throw new OperationCanceledException("Cancelled reading report file");
         }

         // Find or create the root node of the tree for this counter type.
         if (!typeToCounterTypeMap.containsKey(type))
         {
            typeNode = new CounterTypeNode(type);
            typeToCounterTypeMap.put(type, typeNode);
         }
         else
         {
            typeNode = (CounterTypeNode) typeToCounterTypeMap.get(type);
         }

         // Increase the report loss count for this counter type.
         typeNode.addReportLossCount(numReportsLost);
      }
   }

   private class ReportReaderJob extends Job
   {
      private final String path;

      ReportReaderJob(String path)
      {
         super("Reading and processing report dump file");
         setPriority(SHORT);
         setUser(true);
         this.path = path;
      }

      protected IStatus run(IProgressMonitor monitor)
      {
         try
         {
            ReportReaderHandler reportReaderHandler;
            ReportReader reportReader;
            boolean binaryErrors;

            monitor.beginTask(getName(), IProgressMonitor.UNKNOWN);
            waitForEditorToOpen();
            reportReaderHandler = new ReportReaderHandler(monitor);
            reportReader = new ReportReader(reportReaderHandler);
            reportReader.read(new File(path));
            binaryErrors = buildTree(reportReaderHandler.getCollatedReports());
            asyncExec(new UpdateUIRunner(binaryErrors));
            return (monitor.isCanceled() ? Status.CANCEL_STATUS : Status.OK_STATUS);
         }
         catch (OperationCanceledException e)
         {
            return Status.CANCEL_STATUS;
         }
         catch (Exception e)
         {
            return ProfilerPlugin.createErrorStatus(
               "Error when reading the report dump file", e);
         }
         finally
         {
            monitor.done();
         }
      }

      private void waitForEditorToOpen() throws InterruptedException
      {
         synchronized (editorOpenedLock)
         {
            while (!editorOpened)
            {
               editorOpenedLock.wait();
            }
         }
      }

      private class UpdateUIRunner implements Runnable
      {
         private final boolean binaryErrors;

         UpdateUIRunner(boolean binaryErrors)
         {
            this.binaryErrors = binaryErrors;
         }

         public void run()
         {
            if (!isDisposed())
            {
               setTree(binaryErrors);
            }
         }
      }
   }
}
