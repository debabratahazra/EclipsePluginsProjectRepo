/* COPYRIGHT-ENEA-SRC-R1 *
 **************************************************************************
 * Copyright (C) 2006 by Enea Software AB.
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

package com.ose.pmd.ui.editor;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.ResourceBundle;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IMenuListener;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.layout.TableColumnLayout;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jface.viewers.ColumnWeightData;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.custom.StyledTextContent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.actions.ActionFactory;
import org.eclipse.ui.forms.IFormColors;
import org.eclipse.ui.forms.widgets.Form;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.Section;
import org.eclipse.ui.texteditor.FindReplaceAction;
import com.ose.pmd.dump.AbstractBlock;
import com.ose.pmd.dump.DumpFile;
import com.ose.pmd.dump.ErrorBlock;
import com.ose.pmd.dump.MemoryBlock;
import com.ose.pmd.dump.SignalBlock;
import com.ose.pmd.dump.TextBlock;
import com.ose.pmd.editor.HexBlockFormatter;
import com.ose.pmd.editor.IBlockFormatter;
import com.ose.pmd.editor.OseError;
import com.ose.pmd.editor.SignalBlockFormatter;
import com.ose.pmd.ui.DumpPlugin;
import com.ose.pmd.ui.editor.BlockFormatterManager.BlockFormatterProxy;
import com.ose.system.ui.SharedImages;
import com.ose.system.ui.forms.IForm;
import com.ose.system.ui.util.StringUtils;
import com.ose.system.ui.util.TableCopyAction;
import com.ose.system.ui.util.TableSelectAllAction;

public class DumpForm implements IForm
{
   public static final int COLUMN_BLOCK_STATUS = 1;
   public static final int COLUMN_BLOCK_NUMBER = 2;
   public static final int COLUMN_BLOCK_TYPE = 3;
   public static final int COLUMN_BLOCK_ADDRESS = 4;
   public static final int COLUMN_BLOCK_SIZE = 5;
   public static final int COLUMN_BLOCK_SIZE_IN_FILE = 6;
   public static final int COLUMN_BLOCK_DESCRIPTION = 7;

   private static final String[] BLOCK_TABLE_HEADERS =
      {"", "No.", "Type", "Address", "Size", "Size in file", "Description"};

   private static final int BLOCK_FILE_SIZE_THRESHOLD = 50000;

   private static final Random RANDOM = new Random();

   private static final DateFormat DATE_FORMAT = DateFormat.getDateTimeInstance();

   private static final FontData[] MONOSPACE_FONT_DATAS;

   private final DumpFile dumpFile;
   private final AbstractBlock[] blocks;
   private final BlockFormatterManager blockFormatterManager;
   private final List blockFiles;

   private IEditorPart editor;
   private FormToolkit toolkit;
   private Form form;

   private Label dumpIdLabel;
   private Label dumpSizeLabel;
   private Label dumpVersionLabel;
   private Label dumpEndianLabel;
   private Label dumpTimestampLabel;

   private Text descriptionText;
   private Label timestampLabel;
   private CTabFolder tabFolder;
   private StyledText blockText;
   private StyledTextContent defaultTextContent;
   private StyledTextContent currentTextContent;
   private FindTargetHandler findTargetHandler;
   private Action findAction;

   private Label errorProcessLabel;
   private Label errorDetectorLabel;
   private Label errorCodeLabel;
   private Label errorExtraLabel;
   private Text errorMessageText;

   private TableViewer blockViewer;
   private BlockSorter blockSorter;
   private BlockSaveAction blockSaveAction;
   private Action copyAction;
   private Action selectAllAction;

   private Font boldFont;
   private Font monospaceFont;

   static
   {
      if (Platform.WS_WIN32.equals(Platform.getWS()))
      {
         MONOSPACE_FONT_DATAS = new FontData[]
         {
            new FontData("Lucida Sans Typewriter", 8, SWT.NORMAL)
         };
      }
      else
      {
         MONOSPACE_FONT_DATAS = new FontData[]
         {
            new FontData("Lucida Sans Typewriter", 8, SWT.NORMAL),
            new FontData("Bitstream Vera Sans Mono", 8, SWT.NORMAL),
            new FontData("Luxi Mono", 8, SWT.NORMAL),
            new FontData("Lucida Console", 8, SWT.NORMAL),
            new FontData("Courier New", 8, SWT.NORMAL)
         };
      }
   }

   public DumpForm(IEditorPart editor, DumpFile dumpFile, AbstractBlock[] blocks)
   {
      if ((editor == null) || (dumpFile == null) || (blocks == null))
      {
         throw new IllegalArgumentException();
      }
      this.editor = editor;
      this.dumpFile = dumpFile;
      this.blocks = blocks;
      blockFormatterManager = new BlockFormatterManager();
      blockFiles = new ArrayList();
   }

   public void createContents(Composite parent)
   {
      Display display;
      FontData[] fontDatas;
      SashForm sashForm;
      Composite leftComp;
      Composite rightComp;

      display = parent.getDisplay();
      toolkit = new FormToolkit(display);
      toolkit.getColors().initializeSectionToolBarColors();
      form = toolkit.createForm(parent);
      form.getBody().setLayout(new FillLayout());

      fontDatas = parent.getFont().getFontData();
      for (int i = 0; i < fontDatas.length; i++)
      {
         fontDatas[i].setStyle(fontDatas[i].getStyle() | SWT.BOLD);
      }
      boldFont = new Font(display, fontDatas);

      fontDatas = JFaceResources.getFontRegistry().
         filterData(MONOSPACE_FONT_DATAS, display);
      monospaceFont = new Font(display, fontDatas[0]);

      sashForm = new SashForm(form.getBody(), SWT.HORIZONTAL);
      toolkit.adapt(sashForm, false, false);
      sashForm.setBackground(toolkit.getColors().getColor(IFormColors.TB_BG));
      sashForm.setLayout(new FillLayout());

      leftComp = toolkit.createComposite(sashForm);
      leftComp.setLayout(new GridLayout(1, false));
      toolkit.paintBordersFor(leftComp);

      rightComp = toolkit.createComposite(sashForm);
      rightComp.setLayout(new GridLayout(1, false));
      toolkit.paintBordersFor(rightComp);

      sashForm.setWeights(new int[] {50, 50});

      createInfoSection(leftComp);
      createErrorSection(leftComp);
      createBlockSection(leftComp);
      createDataSection(rightComp);
      refresh();
   }

   public void dispose()
   {
      boldFont.dispose();
      monospaceFont.dispose();
      toolkit.dispose();

      if (currentTextContent instanceof BlockFileStyledTextContent)
      {
         ((BlockFileStyledTextContent) currentTextContent).dispose();
      }

      for (Iterator i = blockFiles.iterator(); i.hasNext();)
      {
         ((File) i.next()).delete();
      }
   }

   public void setFocus()
   {
      form.setFocus();
      updateActionBars();
   }

   public void refresh()
   {
      refreshInfoSection();
      refreshErrorSection();
      refreshBlockSection();
      refreshDataSection();
   }

   public Control getControl()
   {
      return form;
   }

   private void createInfoSection(Composite parent)
   {
      Section section;
      Composite client;
      Label label;
      GridLayout layout;

      section = toolkit.createSection(parent,
            Section.TITLE_BAR | Section.TWISTIE | Section.EXPANDED);
      section.setText("Dump");
      section.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
      section.setLayout(new GridLayout());

      client = toolkit.createComposite(section);
      layout = new GridLayout(2, false);
      client.setLayout(layout);
      toolkit.paintBordersFor(client);
      section.setClient(client);

      label = toolkit.createLabel(client, "ID:");
      label.setFont(boldFont);
      dumpIdLabel = toolkit.createLabel(client, "");

      label = toolkit.createLabel(client, "Size:");
      label.setFont(boldFont);
      dumpSizeLabel = toolkit.createLabel(client, "");

      label = toolkit.createLabel(client, "Version:");
      label.setFont(boldFont);
      dumpVersionLabel = toolkit.createLabel(client, "");

      label = toolkit.createLabel(client, "Byte order:");
      label.setFont(boldFont);
      dumpEndianLabel = toolkit.createLabel(client, "");

      label = toolkit.createLabel(client, "Timestamp:");
      label.setFont(boldFont);
      dumpTimestampLabel = toolkit.createLabel(client, "");
   }

   private void createDataSection(Composite parent)
   {
      Section section;
      Composite client;
      Label label;
      GridLayout layout;
      GridData gd;

      section = toolkit.createSection(parent, Section.TITLE_BAR);
      section.setText("Block Data");
      section.setLayoutData(new GridData(GridData.FILL_BOTH));
      section.setLayout(new GridLayout());

      client = toolkit.createComposite(section);
      client.setLayoutData(new GridData(GridData.FILL_BOTH));
      layout = new GridLayout(2, false);
      layout.marginWidth = 0;
      layout.marginHeight = 0;
      client.setLayout(layout);
      toolkit.paintBordersFor(client);
      section.setClient(client);

      descriptionText = toolkit.createText(client, "", SWT.READ_ONLY);
      gd = new GridData(GridData.FILL_HORIZONTAL);
      gd.horizontalSpan = 2;
      descriptionText.setLayoutData(gd);

      label = toolkit.createLabel(client, "Timestamp:");
      label.setFont(boldFont);
      label.setLayoutData(new GridData());

      timestampLabel = toolkit.createLabel(client, "");
      timestampLabel.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

      tabFolder = new CTabFolder(client, SWT.TOP | SWT.FLAT);
      toolkit.adapt(tabFolder);
      gd = new GridData(GridData.FILL_BOTH);
      gd.horizontalSpan = 2;
      tabFolder.setLayoutData(gd);
      tabFolder.setSelectionBackground(new Color[]
         {toolkit.getColors().getColor(IFormColors.TB_BG),
          toolkit.getColors().getBackground()}, new int[] {100}, true);
      tabFolder.addSelectionListener(new TabFolderSelectionHandler());

      blockText = new StyledText(tabFolder, SWT.READ_ONLY | SWT.MULTI |
            SWT.V_SCROLL | SWT.H_SCROLL | SWT.FLAT | SWT.BORDER);
      toolkit.adapt(blockText);
      blockText.setFont(monospaceFont);
      defaultTextContent = blockText.getContent();
      findTargetHandler = new FindTargetHandler(blockText);
      findAction = new FindAction(editor);

      createTabs(null);
   }

   private void createTabs(BlockFormatterProxy[] blockFormatterProxies)
   {
      // Remove the old tab(s).
      CTabItem[] tabItems = tabFolder.getItems();
      for (int i = 0; i < tabItems.length; i++)
      {
         CTabItem tabItem = tabItems[i];
         tabItem.setControl(null);
         tabItem.dispose();
      }

      // Create the new tab(s).
      if ((blockFormatterProxies == null) || (blockFormatterProxies.length < 2))
      {
         new CTabItem(tabFolder, SWT.NONE);
         tabFolder.setSingle(true);
         tabFolder.setTabHeight(0);
      }
      else
      {
         for (int i = 0; i < blockFormatterProxies.length; i++)
         {
            CTabItem tabItem = new CTabItem(tabFolder, SWT.NONE);
            tabItem.setText(blockFormatterProxies[i].getName());
         }
         tabFolder.setSingle(false);
         tabFolder.setTabHeight(SWT.DEFAULT);
      }

      // Select the first tab.
      tabFolder.setSelection(0);
      handleTabSelected();
   }

   private void updateTabs()
   {
      CTabItem[] tabItems;
      CTabItem tabItem;

      // Associate the block text widget with the currently selected tab.
      tabItems = tabFolder.getItems();
      for (int i = 0; i < tabItems.length; i++)
      {
         tabItems[i].setControl(null);
      }
      tabItem = tabFolder.getSelection();
      if (tabItem != null)
      {
         tabItem.setControl(blockText);
      }
   }

   private void handleTabSelected()
   {
      updateTabs();
      refreshDataSection();
      findTargetHandler.update();
   }

   private void createErrorSection(Composite parent)
   {
      Section section;
      Composite client;
      Label label;
      GridLayout layout;
      GridData gd;

      section = toolkit.createSection(parent,
            Section.TITLE_BAR | Section.TWISTIE | Section.EXPANDED);
      section.setText("Last Error");
      section.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
      section.setLayout(new GridLayout());

      client = toolkit.createComposite(section);
      client.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
      layout = new GridLayout(4, false);
      layout.horizontalSpacing = 20;
      client.setLayout(layout);
      toolkit.paintBordersFor(client);
      section.setClient(client);

      label = toolkit.createLabel(client, "Process:");
      label.setFont(boldFont);
      errorProcessLabel = toolkit.createLabel(client, "");

      label = toolkit.createLabel(client, "Detected by:");
      label.setFont(boldFont);
      errorDetectorLabel = toolkit.createLabel(client, "");

      label = toolkit.createLabel(client, "Error code:");
      label.setFont(boldFont);
      errorCodeLabel = toolkit.createLabel(client, "");

      label = toolkit.createLabel(client, "Extra parameter:");
      label.setFont(boldFont);
      errorExtraLabel = toolkit.createLabel(client, "");

      errorMessageText = toolkit.createText(client, "",
            SWT.READ_ONLY | SWT.MULTI | SWT.WRAP | SWT.V_SCROLL);
      errorMessageText.setForeground(
            form.getDisplay().getSystemColor(SWT.COLOR_RED));
      gd = new GridData(GridData.FILL_BOTH);
      gd.horizontalSpan = 4;
      gd.widthHint = 200;
      gd.heightHint = errorMessageText.getLineHeight() * 3;
      errorMessageText.setLayoutData(gd);
   }

   private void createBlockSection(Composite parent)
   {
      Section section;
      Composite client;
      GridLayout layout;

      section = toolkit.createSection(parent,
            Section.TITLE_BAR | Section.TWISTIE | Section.EXPANDED);
      section.setText("Blocks");
      section.setLayoutData(new GridData(GridData.FILL_BOTH));

      client = toolkit.createComposite(section);
      client.setLayoutData(new GridData(GridData.FILL_BOTH));
      layout = new GridLayout();
      layout.marginWidth = 0;
      layout.marginHeight = 0;
      client.setLayout(layout);
      toolkit.paintBordersFor(client);
      section.setClient(client);

      blockViewer = new TableViewer(createBlockTable(client));
      blockViewer.setContentProvider(new BlockContentProvider());
      blockViewer.setLabelProvider(new BlockLabelProvider());
      blockViewer.addSelectionChangedListener(new SelectionChangedHandler());
      blockSorter = new BlockSorter();
      blockViewer.setSorter(blockSorter);
      blockSaveAction = new BlockSaveAction();
      copyAction = new TableCopyAction(blockViewer);
      selectAllAction = new TableSelectAllAction(blockViewer);
      createContextMenu();
   }

   private Table createBlockTable(Composite parent)
   {
      Composite comp;
      TableColumnLayout layout;
      Table table;
      TableColumn column;

      comp = new Composite(parent, SWT.NONE);
      comp.setLayoutData(new GridData(GridData.FILL_BOTH));
      layout = new TableColumnLayout();
      comp.setLayout(layout);
      table = toolkit.createTable(comp,
            SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL | SWT.FULL_SELECTION);
      table.setHeaderVisible(true);
      table.setLinesVisible(true);

      // Dummy first column to get real first column center-aligned on Windows.
      column = new TableColumn(table, SWT.NONE);
      column.setText("");
      layout.setColumnData(column, new ColumnWeightData(0, 0, false));

      column = new TableColumn(table, SWT.CENTER);
      column.setImage(SharedImages.get(SharedImages.IMG_OBJ_EXCLAMATION));
      column.setMoveable(true);
      column.addSelectionListener(new SelectionHandler(COLUMN_BLOCK_STATUS));
      layout.setColumnData(column, new ColumnWeightData(1, 16));

      column = new TableColumn(table, SWT.RIGHT);
      column.setText(BLOCK_TABLE_HEADERS[1]);
      column.setMoveable(true);
      column.addSelectionListener(new SelectionHandler(COLUMN_BLOCK_NUMBER));
      layout.setColumnData(column, new ColumnWeightData(4, 40));

      column = new TableColumn(table, SWT.LEFT);
      column.setText(BLOCK_TABLE_HEADERS[2]);
      column.setMoveable(true);
      column.addSelectionListener(new SelectionHandler(COLUMN_BLOCK_TYPE));
      layout.setColumnData(column, new ColumnWeightData(5, 50));

      column = new TableColumn(table, SWT.RIGHT);
      column.setText(BLOCK_TABLE_HEADERS[3]);
      column.setMoveable(true);
      column.addSelectionListener(new SelectionHandler(COLUMN_BLOCK_ADDRESS));
      layout.setColumnData(column, new ColumnWeightData(7, 75));

      column = new TableColumn(table, SWT.RIGHT);
      column.setText(BLOCK_TABLE_HEADERS[4]);
      column.setMoveable(true);
      column.addSelectionListener(new SelectionHandler(COLUMN_BLOCK_SIZE));
      layout.setColumnData(column, new ColumnWeightData(6, 65));

      column = new TableColumn(table, SWT.RIGHT);
      column.setText(BLOCK_TABLE_HEADERS[5]);
      column.setMoveable(true);
      column.addSelectionListener(new SelectionHandler(COLUMN_BLOCK_SIZE_IN_FILE));
      layout.setColumnData(column, new ColumnWeightData(6, 65));

      column = new TableColumn(table, SWT.LEFT);
      column.setText(BLOCK_TABLE_HEADERS[6]);
      column.setMoveable(true);
      column.addSelectionListener(new SelectionHandler(COLUMN_BLOCK_DESCRIPTION));
      layout.setColumnData(column, new ColumnWeightData(20, 135));

      return table;
   }

   private void createContextMenu()
   {
      MenuManager menuMgr;
      Menu menu;

      menuMgr = new MenuManager();
      menuMgr.setRemoveAllWhenShown(true);
      menuMgr.addMenuListener(new ContextMenuHandler());
      menu = menuMgr.createContextMenu(blockViewer.getControl());
      blockViewer.getControl().setMenu(menu);
   }

   private void refreshInfoSection()
   {
      dumpIdLabel.setText(StringUtils.toHexString(dumpFile.getId()));
      dumpSizeLabel.setText(dumpFile.getSize() + " bytes");
      dumpVersionLabel.setText(StringUtils.toHexString(dumpFile.getVersion()));
      dumpEndianLabel.setText(dumpFile.isBigEndian() ?
                              "Big endian" : "Little endian");
      dumpTimestampLabel.setText(getTimestamp(dumpFile.getSeconds(),
                                              dumpFile.getMicroSeconds()));
   }

   private void refreshDataSection()
   {
      AbstractBlock block = getSelectedBlock();

      if (block instanceof ErrorBlock)
      {
         formatErrorBlock((ErrorBlock) block);
      }
      else if (block instanceof TextBlock)
      {
         formatTextBlock((TextBlock) block);
      }
      else if (block instanceof MemoryBlock)
      {
         try
         {
            formatMemoryBlock((MemoryBlock) block, getSelectedBlockFormatter(block));
         }
         catch (Exception e)
         {
            DumpPlugin.errorDialog(null,
               "Error formatting memory block number " + block.getBlockNo(), e);
         }
      }
      else if (block instanceof SignalBlock)
      {
         try
         {
            formatSignalBlock((SignalBlock) block, getSelectedBlockFormatter(block));
         }
         catch (Exception e)
         {
            DumpPlugin.errorDialog(null,
               "Error formatting signal block number " + block.getBlockNo(), e);
         }
      }
      else
      {
         clearDataSection();
         return;
      }

      descriptionText.setText(getDescription(block));
      timestampLabel.setText(getTimestamp(block));
   }

   private void refreshErrorSection()
   {
      ErrorBlock eb = null;

      for (int i = blocks.length - 1; i >= 0; i--)
      {
         AbstractBlock block = blocks[i];
         if (block instanceof ErrorBlock)
         {
            eb = (ErrorBlock) block;
            break;
         }
      }

      if (eb != null)
      {
         errorProcessLabel.setText(StringUtils.toHexString(eb.getCurrentProcess()));
         errorDetectorLabel.setText((eb.getUserCalled() != 0) ? "User" : "Kernel");
         errorCodeLabel.setText(StringUtils.toHexString(eb.getErrorCode()));
         errorExtraLabel.setText(StringUtils.toHexString(eb.getExtra()));
         errorMessageText.setText(getErrorMessage(eb));
      }
   }

   private void refreshBlockSection()
   {
      blockViewer.setInput(blocks);
      blockViewer.refresh();
   }

   private void clearDataSection()
   {
      blockText.setWordWrap(true);
      blockText.setContent(defaultTextContent);
      blockText.setText("");
      descriptionText.setText("");
      timestampLabel.setText("");
   }

   private void updateActionBars()
   {
      IActionBars bars = editor.getEditorSite().getActionBars();
      bars.setGlobalActionHandler(ActionFactory.COPY.getId(), copyAction);
      bars.setGlobalActionHandler(ActionFactory.SELECT_ALL.getId(), selectAllAction);
      bars.setGlobalActionHandler(ActionFactory.FIND.getId(), findAction);
      bars.updateActionBars();
   }

   private void formatErrorBlock(ErrorBlock eb)
   {
      String[] descriptions;

      blockText.setRedraw(false);
      blockText.setWordWrap(true);
      blockText.setContent(defaultTextContent);
      blockText.setText("");
      descriptions = eb.getDescriptions();
      for (int i = 0; i < descriptions.length; i++)
      {
         blockText.append(descriptions[i]);
         blockText.append("\n");
      }
      if (descriptions.length > 0)
      {
         blockText.append("\n");
      }
      blockText.append(getExtendedErrorMessage(eb));
      blockText.setRedraw(true);
   }

   private void formatTextBlock(TextBlock tb)
   {
      String[] descriptions;

      blockText.setRedraw(false);
      blockText.setWordWrap(true);
      blockText.setContent(defaultTextContent);
      blockText.setText("");
      descriptions = tb.getDescriptions();
      for (int i = 0; i < descriptions.length; i++)
      {
         blockText.append(descriptions[i]);
         blockText.append("\n");
      }
      blockText.setRedraw(true);
   }

   private void formatMemoryBlock(MemoryBlock mb, IBlockFormatter blockFormatter)
      throws IOException
   {
      InputStream in = null;
      PrintWriter out = null;

      try
      {
         boolean useFile;
         File file = null;
         StringWriter stringWriter = null;

         in = mb.getInputStream();
         useFile = ((mb.getLength() > BLOCK_FILE_SIZE_THRESHOLD) &&
                    (blockFormatter instanceof HexBlockFormatter));
         if (useFile)
         {
            file = createBlockFile(mb);
            out = new PrintWriter(new BufferedOutputStream(new FileOutputStream(file)));
         }
         else
         {
            stringWriter = new StringWriter((int) mb.getLength());
            out = new PrintWriter(stringWriter);
         }
         blockFormatter.format(
            in, out, mb.getStartAddress(), mb.getLength(), dumpFile.isBigEndian());
         out.flush();

         blockText.setWordWrap(false);
         if (useFile)
         {
            if (currentTextContent instanceof BlockFileStyledTextContent)
            {
               ((BlockFileStyledTextContent) currentTextContent).dispose();
            }
            currentTextContent = new BlockFileStyledTextContent(file);
            blockText.setContent(currentTextContent);
         }
         else
         {
            blockText.setContent(defaultTextContent);
            blockText.setText(stringWriter.toString());
         }
      }
      finally
      {
         if (in != null)
         {
            try
            {
               in.close();
            }
            catch (IOException ignore) {}
         }
         if (out != null)
         {
            out.close();
         }
      }
   }

   private void formatSignalBlock(SignalBlock sb, IBlockFormatter blockFormatter)
      throws IOException
   {
      InputStream in = null;
      PrintWriter out = null;

      try
      {
         boolean useFile;
         File file = null;
         StringWriter stringWriter = null;

         in = sb.getInputStream();
         useFile = ((sb.getLength() > BLOCK_FILE_SIZE_THRESHOLD) &&
                    (blockFormatter instanceof HexBlockFormatter));
         if (useFile)
         {
            file = createBlockFile(sb);
            out = new PrintWriter(new BufferedOutputStream(new FileOutputStream(file)));
         }
         else
         {
            stringWriter = new StringWriter((int) sb.getLength());
            out = new PrintWriter(stringWriter);
         }
         blockFormatter.format(in, out, 0, sb.getLength(), dumpFile.isBigEndian());
         out.flush();

         blockText.setWordWrap(false);
         if (useFile)
         {
            if (currentTextContent instanceof BlockFileStyledTextContent)
            {
               ((BlockFileStyledTextContent) currentTextContent).dispose();
            }
            currentTextContent = new BlockFileStyledTextContent(file);
            blockText.setContent(currentTextContent);
         }
         else
         {
            blockText.setContent(defaultTextContent);
            blockText.setText(stringWriter.toString());
         }
      }
      finally
      {
         if (in != null)
         {
            try
            {
               in.close();
            }
            catch (IOException ignore) {}
         }
         if (out != null)
         {
            out.close();
         }
      }
   }

   private File createBlockFile(AbstractBlock block)
   {
      File dir;
      String name;
      File file;

      dir = DumpPlugin.getDefault().getStateLocation().toFile();
      name = "pmd_" + Long.toHexString(block.getDumpId()) + "_" +
         block.getBlockNo() + "_" + RANDOM.nextInt(Integer.MAX_VALUE) + ".txt";
      file = new File(dir, name);
      blockFiles.add(file);
      return file;
   }

   private AbstractBlock getSelectedBlock()
   {
      IStructuredSelection selection =
         (IStructuredSelection) blockViewer.getSelection();
      Object obj = ((selection.size() == 1) ? selection.getFirstElement() : null);
      return ((obj instanceof AbstractBlock) ? ((AbstractBlock) obj) : null);
   }

   private BlockFormatterProxy[] getBlockFormatterProxies(AbstractBlock block)
   {
      if (block instanceof MemoryBlock)
      {
         String[] descriptions;
         String description;

         descriptions = ((MemoryBlock) block).getDescriptions();
         description = ((descriptions.length > 0) ? descriptions[0].trim() : "");
         return blockFormatterManager.
            getBlockFormatterProxies(BlockFormatterManager.TYPE_MEMORY, description);
      }
      else if (block instanceof SignalBlock)
      {
         String description;

         description = Long.toString(((SignalBlock) block).getRequestSigNo());
         return blockFormatterManager.
            getBlockFormatterProxies(BlockFormatterManager.TYPE_SIGNAL, description);
      }
      else
      {
         return null;
      }
   }

   private IBlockFormatter getSelectedBlockFormatter(AbstractBlock block)
      throws CoreException
   {
      BlockFormatterProxy[] blockFormatterProxies = getBlockFormatterProxies(block);

      if ((blockFormatterProxies != null) && (blockFormatterProxies.length > 0))
      {
         int selectedTabIndex = tabFolder.getSelectionIndex();
         if (selectedTabIndex == -1)
         {
            selectedTabIndex = 0;
         }
         return blockFormatterProxies[selectedTabIndex].getBlockFormatter();
      }
      else
      {
         return null;
      }
   }

   private static String getTimestamp(long seconds, long microSeconds)
   {
      Date date = new Date(seconds * 1000);
      return DATE_FORMAT.format(date) + " " + microSeconds + " \u00B5s";
   }

   private static String getTimestamp(AbstractBlock block)
   {
      return getTimestamp(block.getSeconds(), block.getMicroSeconds());
   }

   private static String getDescription(AbstractBlock block)
   {
      String[] descriptions;
      StringBuffer descriptionsBuffer;
      String description;

      if (block instanceof ErrorBlock)
      {
         ErrorBlock eb = (ErrorBlock) block;
         descriptions = eb.getDescriptions();
         descriptionsBuffer = new StringBuffer(160);
         for (int i = 0; i < descriptions.length; i++)
         {
            descriptionsBuffer.append(descriptions[i]);
         }
         description = descriptionsBuffer.toString();
      }
      else if (block instanceof TextBlock)
      {
         TextBlock tb = (TextBlock) block;
         descriptions = tb.getDescriptions();
         description = (descriptions.length > 0) ? descriptions[0] : "";
         if (description.length() > 160)
         {
            description = description.substring(0, 160) + "...";
         }
      }
      else if (block instanceof MemoryBlock)
      {
         MemoryBlock mb = (MemoryBlock) block;
         descriptions = mb.getDescriptions();
         descriptionsBuffer = new StringBuffer(160);
         for (int i = 0; i < descriptions.length; i++)
         {
            descriptionsBuffer.append(descriptions[i]);
         }
         description = descriptionsBuffer.toString();
      }
      else if (block instanceof SignalBlock)
      {
         SignalBlock sb = (SignalBlock) block;
         long reqSigNo = sb.getRequestSigNo();
         long status = sb.getStatus();
         String signalName = SignalBlockFormatter.getSignalName((int) reqSigNo);
         description = ((signalName != null) ? signalName + " " : "") +
            StringUtils.toU32String(reqSigNo) +
            ((status != 0) ? " Status: " + StringUtils.toHexString(status) : "");
      }
      else
      {
         throw new IllegalArgumentException();
      }

      return description;
   }

   private static String getErrorMessage(ErrorBlock eb)
   {
      String errorMessage;

      errorMessage = OseError.getErrorMessage((eb.getUserCalled() != 0),
                                              eb.getErrorCode(),
                                              eb.getExtra());
      if (errorMessage == null)
      {
         errorMessage = "";
      }
      return errorMessage;
   }

   private static String getExtendedErrorMessage(ErrorBlock eb)
   {
      StringBuffer sb;
      String errorMessage;

      sb = new StringBuffer(200);
      sb.append("Process: ");
      sb.append(StringUtils.toHexString(eb.getCurrentProcess()));
      sb.append("\n\n");
      sb.append("Detected by: ");
      sb.append((eb.getUserCalled() != 0) ? "User" : "Kernel");
      sb.append("\n\n");
      sb.append("Error code: ");
      sb.append(StringUtils.toHexString(eb.getErrorCode()));
      sb.append("\n\n");
      sb.append("Extra parameter: ");
      sb.append(StringUtils.toHexString(eb.getExtra()));
      errorMessage = OseError.getErrorMessage((eb.getUserCalled() != 0),
                                              eb.getErrorCode(),
                                              eb.getExtra());
      if (errorMessage != null)
      {
         sb.append("\n\n");
         sb.append(errorMessage);
      }

      return sb.toString();
   }

   FindTargetHandler getFindTargetHandler()
   {
      return findTargetHandler;
   }

   private class SelectionChangedHandler implements ISelectionChangedListener
   {
      public void selectionChanged(SelectionChangedEvent event)
      {
         AbstractBlock block = getSelectedBlock();
         blockSaveAction.setEnabled(block != null);
         createTabs(getBlockFormatterProxies(block));
      }
   }

   private class SelectionHandler extends SelectionAdapter
   {
      private final int column;

      SelectionHandler(int column)
      {
         this.column = column;
      }

      public void widgetSelected(SelectionEvent event)
      {
         blockSorter.sortByColumn(column);
         blockViewer.refresh();
      }
   }

   private class ContextMenuHandler implements IMenuListener
   {
      public void menuAboutToShow(IMenuManager manager)
      {
         manager.add(blockSaveAction);
      }
   }

   private class TabFolderSelectionHandler extends SelectionAdapter
   {
      public void widgetSelected(SelectionEvent event)
      {
         handleTabSelected();
      }
   }

   private class FindAction extends FindReplaceAction
   {
      FindAction(IWorkbenchPart workbenchPart)
      {
         super(ResourceBundle.getBundle(
               "org.eclipse.ui.texteditor.ConstructedEditorMessages"),
               "Editor.FindReplace.",
               workbenchPart);
      }
   }

   private class BlockSaveAction extends Action
   {
      BlockSaveAction()
      {
         super("Save Block");
      }

      public void run()
      {
         AbstractBlock block = getSelectedBlock();
         if (block != null)
         {
            String fileName = getFileName();
            if (fileName != null)
            {
               Job job = new BlockSaveJob(block, fileName);
               job.schedule();
            }
         }
      }

      private String getFileName()
      {
         Shell shell;
         FileDialog dialog;
         boolean done = false;
         String fileName = null;

         shell = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell();
         dialog = new FileDialog(shell, SWT.SAVE | SWT.APPLICATION_MODAL);

         while (!done)
         {
            fileName = dialog.open();

            if (fileName == null)
            {
               done = true;
            }
            else if (new File(fileName).exists())
            {
               MessageBox mb = new MessageBox(shell,
                  SWT.ICON_WARNING | SWT.YES | SWT.NO | SWT.APPLICATION_MODAL);
               mb.setText("Save As");
               mb.setMessage("File " + fileName +
                     " already exists.\n Do you want to replace it?");
               done = (mb.open() == SWT.YES);
            }
            else
            {
               done = true;
            }
         }

         return fileName;
      }
   }

   private static class BlockSaveJob extends Job
   {
      private final AbstractBlock block;
      private final String file;

      BlockSaveJob(AbstractBlock block, String file)
      {
         super("Saving Block #" + block.getBlockNo());
         setPriority(SHORT);
         this.block = block;
         this.file = file;
      }

      protected IStatus run(IProgressMonitor monitor)
      {
         InputStream in = null;
         FileOutputStream out = null;
         FileWriter writer = null;

         try
         {
            if (block instanceof ErrorBlock)
            {
               ErrorBlock eb = (ErrorBlock) block;
               String[] descriptions = eb.getDescriptions();

               monitor.beginTask(getName(), 2);
               writer = new FileWriter(file);
               for (int i = 0; i < descriptions.length; i++)
               {
                  writer.write(descriptions[i]);
                  writer.write("\n");
               }
               if (descriptions.length > 0)
               {
                  writer.write("\n");
               }
               monitor.worked(1);
               writer.write(getExtendedErrorMessage(eb));
               monitor.worked(1);
               writer.flush();
            }
            else if (block instanceof TextBlock)
            {
               TextBlock tb = (TextBlock) block;
               String[] descriptions = tb.getDescriptions();

               monitor.beginTask(getName(), (int) tb.getLength());
               writer = new FileWriter(file);
               for (int i = 0; i < descriptions.length; i++)
               {
                  String description = descriptions[i];
                  writer.write(description);
                  writer.write("\n");
                  monitor.worked(description.length());
               }
               writer.flush();
            }
            else if (block instanceof MemoryBlock)
            {
               MemoryBlock mb = (MemoryBlock) block;
               byte[] buffer = new byte[8192];
               int length;

               monitor.beginTask(getName(), (int) mb.getLength());
               in = mb.getInputStream();
               out = new FileOutputStream(file);
               while ((length = in.read(buffer)) != -1)
               {
                  out.write(buffer, 0, length);
                  monitor.worked(length);
               }
            }
            else if (block instanceof SignalBlock)
            {
               SignalBlock sb = (SignalBlock) block;
               byte[] buffer = new byte[8192];
               int length;

               monitor.beginTask(getName(), (int) sb.getLength());
               in = sb.getInputStream();
               out = new FileOutputStream(file);
               while ((length = in.read(buffer)) != -1)
               {
                  out.write(buffer, 0, length);
                  monitor.worked(length);
               }
            }
            return Status.OK_STATUS;
         }
         catch (Exception e)
         {
            return DumpPlugin.createErrorStatus("Failed Saving Block", e);
         }
         finally
         {
            monitor.done();
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
            if (writer != null)
            {
               try
               {
                  writer.close();
               } catch (IOException ignore) {}
            }
            try
            {
               refreshWorkspaceFile(monitor, new File(file));
            } catch (IOException ignore) {}
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
   }
}
