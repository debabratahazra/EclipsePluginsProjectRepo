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

package com.ose.system.ui.views.heap;

import java.util.Iterator;
import java.util.List;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.IDialogSettings;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;
import com.ose.system.Heap;
import com.ose.system.HeapBufferFilter;
import com.ose.system.HeapBufferInfo;
import com.ose.system.Target;
import com.ose.system.ui.SystemBrowserPlugin;
import com.ose.system.ui.util.StringUtils;

public class HeapFilterDialog extends Dialog
{
   private static final String EXCLUDE_STR = "Not";

   private static final String SHARED_YES_STR = "Yes";
   private static final String SHARED_NO_STR = "No";

   private static final String STATUS_UNKNOWN_STR = "Unknown";
   private static final String STATUS_VALID_STR = "Valid";
   private static final String STATUS_ENDMARK_STR = "Broken Endmark";

   private List targets;
   private String targetName;
   private String heapName;

   private Target target;
   private int heapId;
   private HeapBufferFilter heapBufferFilter;

   private Combo targetCombo;
   private Text heapIdText;

   private Text ownerIdText;
   private Text ownerNameText;
   private Combo sharedCombo;
   private Text requestedSizeFromText;
   private Text requestedSizeToText;
   private Text actualSizeFromText;
   private Text actualSizeToText;
   private Text addressFromText;
   private Text addressToText;
   private Text fileNameText;
   private Text lineNumberFromText;
   private Text lineNumberToText;
   private Combo statusCombo;

   private Button ownerIdCheckbox;
   private Button ownerNameCheckbox;
   private Button sharedCheckbox;
   private Button requestedSizeCheckbox;
   private Button actualSizeCheckbox;
   private Button addressCheckbox;
   private Button fileNameCheckbox;
   private Button lineNumberCheckbox;
   private Button statusCheckbox;

   private CLabel errorMessageLabel;
   private Button clearButton;

   public HeapFilterDialog(Shell parent, List targets)
   {
      super(parent);
      if (targets == null)
      {
         throw new NullPointerException();
      }
      this.targets = targets;
   }

   public HeapFilterDialog(Shell parent, List targets, Heap heap)
   {
      this(parent, targets);
      if (heap == null)
      {
         throw new NullPointerException();
      }
      this.targetName = heap.getTarget().toString();
      this.heapName = StringUtils.toHexString(heap.getId());
   }

   protected void configureShell(Shell shell)
   {
      super.configureShell(shell);
      shell.setText("Heap Filter");
   }

   protected Control createContents(Composite parent)
   {
      Control contents = super.createContents(parent);
      initContents();
      return contents;
   }

   protected Control createDialogArea(Composite parent)
   {
      Composite comp;
      Composite subComp;
      ValidationHandler validationHandler;
      Label label;

      comp = (Composite) super.createDialogArea(parent);
      subComp = new Composite(comp, SWT.NONE);
      subComp.setLayout(new GridLayout(1, false));
      validationHandler = new ValidationHandler();

      // Create heap group.
      createHeapGroup(subComp, validationHandler);

      // Create vertical spacer.
      label = new Label(subComp, SWT.NONE);
      label.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

      // Create properties group.
      createPropsGroup(subComp, validationHandler);

      // Create error message label.
      errorMessageLabel = new CLabel(subComp, SWT.NONE);
      errorMessageLabel.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

      // Create separator line.
      label = new Label(parent, SWT.HORIZONTAL | SWT.SEPARATOR);
      label.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

      applyDialogFont(comp);
      return comp;
   }

   private void createHeapGroup(Composite comp,
                                ValidationHandler validationHandler)
   {
      Group heapGroup;
      GridData gd;
      Label label;

      heapGroup = new Group(comp, SWT.SHADOW_NONE);
      heapGroup.setText("Heap");
      heapGroup.setLayout(new GridLayout(2, false));
      gd = new GridData(GridData.FILL_HORIZONTAL);
      heapGroup.setLayoutData(gd);

      label = new Label(heapGroup, SWT.NONE);
      label.setText("Target:");

      targetCombo = new Combo(heapGroup, SWT.READ_ONLY);
      gd = new GridData(GridData.FILL_HORIZONTAL);
      gd.widthHint = convertHorizontalDLUsToPixels(180);
      targetCombo.setLayoutData(gd);
      for (Iterator i = targets.iterator(); i.hasNext();)
      {
         Target target = (Target) i.next();
         targetCombo.add(target.toString());
      }
      targetCombo.addModifyListener(validationHandler);

      label = new Label(heapGroup, SWT.NONE);
      label.setText("Heap ID:");

      heapIdText = new Text(heapGroup, SWT.SINGLE | SWT.BORDER);
      gd = new GridData(GridData.FILL_HORIZONTAL);
      heapIdText.setLayoutData(gd);
      heapIdText.addModifyListener(validationHandler);
   }

   private void createPropsGroup(Composite comp,
                                 ValidationHandler validationHandler)
   {
      Group propsGroup;
      GridData gd;
      Label label;

      propsGroup = new Group(comp, SWT.SHADOW_NONE);
      propsGroup.setText("Properties");
      propsGroup.setLayout(new GridLayout(4, false));
      gd = new GridData(GridData.FILL_HORIZONTAL);
      propsGroup.setLayoutData(gd);

      label = new Label(propsGroup, SWT.NONE);
      label.setText("Owner ID:");

      ownerIdText = new Text(propsGroup, SWT.SINGLE | SWT.BORDER);
      gd = new GridData(GridData.FILL_HORIZONTAL);
      gd.horizontalSpan = 2;
      ownerIdText.setLayoutData(gd);
      ownerIdText.addModifyListener(validationHandler);

      ownerIdCheckbox = new Button(propsGroup, SWT.CHECK);
      ownerIdCheckbox.setText(EXCLUDE_STR);
      new CheckboxHandler(ownerIdText, ownerIdCheckbox);

      label = new Label(propsGroup, SWT.NONE);
      label.setText("Owner Name:");

      ownerNameText = new Text(propsGroup, SWT.SINGLE | SWT.BORDER);
      gd = new GridData(GridData.FILL_HORIZONTAL);
      gd.horizontalSpan = 2;
      ownerNameText.setLayoutData(gd);
      ownerNameText.addModifyListener(validationHandler);

      ownerNameCheckbox = new Button(propsGroup, SWT.CHECK);
      ownerNameCheckbox.setText(EXCLUDE_STR);
      new CheckboxHandler(ownerNameText, ownerNameCheckbox);

      label = new Label(propsGroup, SWT.NONE);
      label.setText("Shared:");

      sharedCombo = new Combo(propsGroup, SWT.READ_ONLY);
      gd = new GridData(GridData.FILL_HORIZONTAL);
      gd.horizontalSpan = 2;
      sharedCombo.setLayoutData(gd);
      sharedCombo.add("");
      sharedCombo.add(SHARED_YES_STR);
      sharedCombo.add(SHARED_NO_STR);
      sharedCombo.setVisibleItemCount(sharedCombo.getItemCount());

      sharedCheckbox = new Button(propsGroup, SWT.CHECK);
      sharedCheckbox.setText(EXCLUDE_STR);
      new CheckboxHandler(sharedCombo, sharedCheckbox);

      label = new Label(propsGroup, SWT.NONE);
      label.setText("Requested Size:");

      requestedSizeFromText = new Text(propsGroup, SWT.SINGLE | SWT.BORDER);
      gd = new GridData(GridData.FILL_HORIZONTAL);
      requestedSizeFromText.setLayoutData(gd);
      requestedSizeFromText.addModifyListener(validationHandler);
      requestedSizeToText = new Text(propsGroup, SWT.SINGLE | SWT.BORDER);
      gd = new GridData(GridData.FILL_HORIZONTAL);
      requestedSizeToText.setLayoutData(gd);
      requestedSizeToText.addModifyListener(validationHandler);

      requestedSizeCheckbox = new Button(propsGroup, SWT.CHECK);
      requestedSizeCheckbox.setText(EXCLUDE_STR);
      new CheckboxHandler(requestedSizeFromText,
                          requestedSizeToText,
                          requestedSizeCheckbox);

      label = new Label(propsGroup, SWT.NONE);
      label.setText("Actual Size:");

      actualSizeFromText = new Text(propsGroup, SWT.SINGLE | SWT.BORDER);
      gd = new GridData(GridData.FILL_HORIZONTAL);
      actualSizeFromText.setLayoutData(gd);
      actualSizeFromText.addModifyListener(validationHandler);
      actualSizeToText = new Text(propsGroup, SWT.SINGLE | SWT.BORDER);
      gd = new GridData(GridData.FILL_HORIZONTAL);
      actualSizeToText.setLayoutData(gd);
      actualSizeToText.addModifyListener(validationHandler);

      actualSizeCheckbox = new Button(propsGroup, SWT.CHECK);
      actualSizeCheckbox.setText(EXCLUDE_STR);
      new CheckboxHandler(actualSizeFromText,
                          actualSizeToText,
                          actualSizeCheckbox);

      label = new Label(propsGroup, SWT.NONE);
      label.setText("Address:");

      addressFromText = new Text(propsGroup, SWT.SINGLE | SWT.BORDER);
      gd = new GridData(GridData.FILL_HORIZONTAL);
      addressFromText.setLayoutData(gd);
      addressFromText.addModifyListener(validationHandler);
      addressToText = new Text(propsGroup, SWT.SINGLE | SWT.BORDER);
      gd = new GridData(GridData.FILL_HORIZONTAL);
      addressToText.setLayoutData(gd);
      addressToText.addModifyListener(validationHandler);

      addressCheckbox = new Button(propsGroup, SWT.CHECK);
      addressCheckbox.setText(EXCLUDE_STR);
      new CheckboxHandler(addressFromText, addressToText, addressCheckbox);

      label = new Label(propsGroup, SWT.NONE);
      label.setText("Filename:");

      fileNameText = new Text(propsGroup, SWT.SINGLE | SWT.BORDER);
      gd = new GridData(GridData.FILL_HORIZONTAL);
      gd.horizontalSpan = 2;
      fileNameText.setLayoutData(gd);
      fileNameText.addModifyListener(validationHandler);

      fileNameCheckbox = new Button(propsGroup, SWT.CHECK);
      fileNameCheckbox.setText(EXCLUDE_STR);
      new CheckboxHandler(fileNameText, fileNameCheckbox);

      label = new Label(propsGroup, SWT.NONE);
      label.setText("Line Number:");

      lineNumberFromText = new Text(propsGroup, SWT.SINGLE | SWT.BORDER);
      gd = new GridData(GridData.FILL_HORIZONTAL);
      lineNumberFromText.setLayoutData(gd);
      lineNumberFromText.addModifyListener(validationHandler);
      lineNumberToText = new Text(propsGroup, SWT.SINGLE | SWT.BORDER);
      gd = new GridData(GridData.FILL_HORIZONTAL);
      lineNumberToText.setLayoutData(gd);
      lineNumberToText.addModifyListener(validationHandler);

      lineNumberCheckbox = new Button(propsGroup, SWT.CHECK);
      lineNumberCheckbox.setText(EXCLUDE_STR);
      new CheckboxHandler(lineNumberFromText,
                          lineNumberToText,
                          lineNumberCheckbox);

      label = new Label(propsGroup, SWT.NONE);
      label.setText("Status:");

      statusCombo = new Combo(propsGroup, SWT.READ_ONLY);
      gd = new GridData(GridData.FILL_HORIZONTAL);
      gd.horizontalSpan = 2;
      statusCombo.setLayoutData(gd);
      statusCombo.add("");
      statusCombo.add(STATUS_VALID_STR);
      statusCombo.add(STATUS_ENDMARK_STR);
      statusCombo.add(STATUS_UNKNOWN_STR);
      statusCombo.setVisibleItemCount(statusCombo.getItemCount());

      statusCheckbox = new Button(propsGroup, SWT.CHECK);
      statusCheckbox.setText(EXCLUDE_STR);
      new CheckboxHandler(statusCombo, statusCheckbox);
   }

   protected void createButtonsForButtonBar(Composite parent)
   {
      Label spacer;
      GridLayout layout;

      parent.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

      clearButton =
         createButton(parent, IDialogConstants.CLIENT_ID + 1, "Clear", false);
      clearButton.addSelectionListener(new ClearHandler());

      spacer = new Label(parent, SWT.NONE);
      spacer.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
      layout = (GridLayout) parent.getLayout();
      layout.numColumns++;
      layout.makeColumnsEqualWidth = false;

      super.createButtonsForButtonBar(parent);
   }

   private void initContents()
   {
      if ((targetName != null) && (heapName == null))
      {
         clearDialog();
         targetCombo.setText(targetName);
         heapIdText.setText("");
      }
      else if ((targetName != null) && (heapName != null))
      {
         clearDialog();
         targetCombo.setText(targetName);
         heapIdText.setText(heapName);
      }
      else
      {
         loadDialogSettings();
      }
   }

   private void loadDialogSettings()
   {
      IDialogSettings settings;
      IDialogSettings section;

      settings = SystemBrowserPlugin.getDefault().getDialogSettings();
      section = settings.getSection("HeapFilterDialog");
      if (section == null)
      {
         clearDialog();
         return;
      }

      targetCombo.setText(section.get("target"));
      heapIdText.setText(section.get("heapId"));

      ownerIdText.setText(section.get("ownerId"));
      ownerNameText.setText(section.get("ownerName"));
      sharedCombo.setText(section.get("shared"));
      requestedSizeFromText.setText(section.get("requestedSizeFrom"));
      requestedSizeToText.setText(section.get("requestedSizeTo"));
      actualSizeFromText.setText(section.get("actualSizeFrom"));
      actualSizeToText.setText(section.get("actualSizeTo"));
      addressFromText.setText(section.get("addressFrom"));
      addressToText.setText(section.get("addressTo"));
      fileNameText.setText(section.get("fileName"));
      lineNumberFromText.setText(section.get("lineNumberFrom"));
      lineNumberToText.setText(section.get("lineNumberTo"));
      statusCombo.setText(section.get("status"));

      ownerIdCheckbox.setSelection(section.getBoolean("ownerIdExclude"));
      ownerNameCheckbox.setSelection(section.getBoolean("ownerNameExclude"));
      sharedCheckbox.setSelection(section.getBoolean("sharedExclude"));
      requestedSizeCheckbox.setSelection(section.getBoolean("requestedSizeExclude"));
      actualSizeCheckbox.setSelection(section.getBoolean("actualSizeExclude"));
      addressCheckbox.setSelection(section.getBoolean("addressExclude"));
      fileNameCheckbox.setSelection(section.getBoolean("fileNameExclude"));
      lineNumberCheckbox.setSelection(section.getBoolean("lineNumberExclude"));
      statusCheckbox.setSelection(section.getBoolean("statusExclude"));
   }

   private void saveDialogSettings()
   {
      IDialogSettings settings;
      IDialogSettings section;

      settings = SystemBrowserPlugin.getDefault().getDialogSettings();
      section = settings.addNewSection("HeapFilterDialog");

      section.put("target", targetCombo.getText());
      section.put("heapId", heapIdText.getText().trim());

      section.put("ownerId", ownerIdText.getText().trim());
      section.put("ownerName", ownerNameText.getText().trim());
      section.put("shared", sharedCombo.getText());
      section.put("requestedSizeFrom", requestedSizeFromText.getText().trim());
      section.put("requestedSizeTo", requestedSizeToText.getText().trim());
      section.put("actualSizeFrom", actualSizeFromText.getText().trim());
      section.put("actualSizeTo", actualSizeToText.getText().trim());
      section.put("addressFrom", addressFromText.getText().trim());
      section.put("addressTo", addressToText.getText().trim());
      section.put("fileName", fileNameText.getText().trim());
      section.put("lineNumberFrom", lineNumberFromText.getText().trim());
      section.put("lineNumberTo", lineNumberToText.getText().trim());
      section.put("status", statusCombo.getText());

      section.put("ownerIdExclude", ownerIdCheckbox.getSelection());
      section.put("ownerNameExclude", ownerNameCheckbox.getSelection());
      section.put("sharedExclude", sharedCheckbox.getSelection());
      section.put("requestedSizeExclude", requestedSizeCheckbox.getSelection());
      section.put("actualSizeExclude", actualSizeCheckbox.getSelection());
      section.put("addressExclude", addressCheckbox.getSelection());
      section.put("fileNameExclude", fileNameCheckbox.getSelection());
      section.put("lineNumberExclude", lineNumberCheckbox.getSelection());
      section.put("statusExclude", statusCheckbox.getSelection());
   }

   private void clearDialog()
   {
      heapIdText.setText("");

      ownerIdText.setText("");
      ownerNameText.setText("");
      sharedCombo.select(0);
      requestedSizeFromText.setText("");
      requestedSizeToText.setText("");
      actualSizeFromText.setText("");
      actualSizeToText.setText("");
      addressFromText.setText("");
      addressToText.setText("");
      fileNameText.setText("");
      lineNumberFromText.setText("");
      lineNumberToText.setText("");
      statusCombo.select(0);

      ownerIdCheckbox.setSelection(false);
      ownerNameCheckbox.setSelection(false);
      sharedCheckbox.setSelection(false);
      requestedSizeCheckbox.setSelection(false);
      actualSizeCheckbox.setSelection(false);
      addressCheckbox.setSelection(false);
      fileNameCheckbox.setSelection(false);
      lineNumberCheckbox.setSelection(false);
      statusCheckbox.setSelection(false);
   }

   private void validateDialog()
   {
      String errorMessage = null;

      if (targetCombo.getText().length() == 0)
      {
         errorMessage = "Target not specified";
      }
      else if (heapIdText.getText().trim().length() == 0)
      {
         errorMessage = "Heap ID not specified";
      }
      else
      {
         errorMessage = isValidU32(heapIdText.getText(), "Invalid heap ID");
      }
      if (errorMessage == null)
      {
         errorMessage = isValidU32(ownerIdText.getText(),
               "Invalid heap buffer owner ID");
      }
      if (errorMessage == null)
      {
         errorMessage = isValidU32(requestedSizeFromText.getText(),
               "Invalid requested heap buffer size min value");
      }
      if (errorMessage == null)
      {
         errorMessage = isValidU32(requestedSizeToText.getText(),
               "Invalid requested heap buffer size max value");
      }
      if (errorMessage == null)
      {
         errorMessage = isValidU32(actualSizeFromText.getText(),
               "Invalid actual heap buffer size min value");
      }
      if (errorMessage == null)
      {
         errorMessage = isValidU32(actualSizeToText.getText(),
               "Invalid actual heap buffer size max value");
      }
      if (errorMessage == null)
      {
         errorMessage = isValidU32(addressFromText.getText(),
               "Invalid heap buffer address min value");
      }
      if (errorMessage == null)
      {
         errorMessage = isValidU32(addressToText.getText(),
               "Invalid heap buffer address max value");
      }
      if (errorMessage == null)
      {
         errorMessage = isValidU32(lineNumberFromText.getText(),
               "Invalid line number min value");
      }
      if (errorMessage == null)
      {
         errorMessage = isValidU32(lineNumberToText.getText(),
               "Invalid line number max value");
      }
      setErrorMessage(errorMessage);
   }

   private void setErrorMessage(String errorMessage)
   {
      errorMessageLabel.setText((errorMessage == null) ? "" : errorMessage);
      errorMessageLabel.setImage((errorMessage == null) ? null :
         PlatformUI.getWorkbench().getSharedImages()
         .getImage(ISharedImages.IMG_OBJS_ERROR_TSK));
      getButton(IDialogConstants.OK_ID).setEnabled(errorMessage == null);
      getShell().update();
   }

   protected void okPressed()
   {
      target = createTarget();
      try
      {
         heapId = createHeapId();
         heapBufferFilter = createHeapBufferFilter();
      }
      catch (NumberFormatException ignore) {}
      saveDialogSettings();
      super.okPressed();
   }

   private Target createTarget()
   {
      Target result = null;
      int selection = targetCombo.getSelectionIndex();
      if (selection > -1)
      {
         result = (Target) targets.get(selection);
      }
      return result;
   }

   private int createHeapId() throws NumberFormatException
   {
      String str = heapIdText.getText().trim();
      int result = (str.length() > 0) ? StringUtils.parseU32(str) : 0;
      return result;
   }

   private HeapBufferFilter createHeapBufferFilter() throws NumberFormatException
   {
      HeapBufferFilter filter;
      boolean exclude;
      String str;
      String strFrom;
      String strTo;
      int num;
      int from;
      int to;

      filter = new HeapBufferFilter();

      str = ownerIdText.getText().trim();
      if (str.length() > 0)
      {
         exclude = ownerIdCheckbox.getSelection();
         num = StringUtils.parseU32(str);
         filter.filterOwnerId(exclude, num);
      }

      str = ownerNameText.getText().trim();
      if (str.length() > 0)
      {
         exclude = ownerNameCheckbox.getSelection();
         filter.filterOwnerName(exclude, str);
      }

      str = sharedCombo.getText();
      if (str.length() > 0)
      {
         exclude = sharedCheckbox.getSelection();
         filter.filterShared(exclude, str.equals(SHARED_YES_STR));
      }

      strFrom = requestedSizeFromText.getText().trim();
      strTo = requestedSizeToText.getText().trim();
      if ((strFrom.length() > 0) || (strTo.length() > 0))
      {
         exclude = requestedSizeCheckbox.getSelection();
         from = (strFrom.length() > 0) ? StringUtils.parseU32(strFrom) : 0;
         to = (strTo.length() > 0) ? StringUtils.parseU32(strTo) : 0xFFFFFFFF;
         filter.filterRequestedSize(exclude, from, to);
      }

      strFrom = actualSizeFromText.getText().trim();
      strTo = actualSizeToText.getText().trim();
      if ((strFrom.length() > 0) || (strTo.length() > 0))
      {
         exclude = actualSizeCheckbox.getSelection();
         from = (strFrom.length() > 0) ? StringUtils.parseU32(strFrom) : 0;
         to = (strTo.length() > 0) ? StringUtils.parseU32(strTo) : 0xFFFFFFFF;
         filter.filterSize(exclude, from, to);
      }

      strFrom = addressFromText.getText().trim();
      strTo = addressToText.getText().trim();
      if ((strFrom.length() > 0) || (strTo.length() > 0))
      {
         exclude = addressCheckbox.getSelection();
         from = (strFrom.length() > 0) ? StringUtils.parseU32(strFrom) : 0;
         to = (strTo.length() > 0) ? StringUtils.parseU32(strTo) : 0xFFFFFFFF;
         filter.filterAddress(exclude, from, to);
      }

      str = fileNameText.getText().trim();
      if (str.length() > 0)
      {
         exclude = fileNameCheckbox.getSelection();
         filter.filterFileName(exclude, str);
      }

      strFrom = lineNumberFromText.getText().trim();
      strTo = lineNumberToText.getText().trim();
      if ((strFrom.length() > 0) || (strTo.length() > 0))
      {
         exclude = lineNumberCheckbox.getSelection();
         from = (strFrom.length() > 0) ? StringUtils.parseU32(strFrom) : 0;
         to = (strTo.length() > 0) ? StringUtils.parseU32(strTo) : 0xFFFFFFFF;
         filter.filterLineNumber(exclude, from, to);
      }

      str = statusCombo.getText();
      if (str.length() > 0)
      {
         exclude = statusCheckbox.getSelection();
         if (str.equals(STATUS_VALID_STR))
         {
            num = HeapBufferInfo.STATUS_VALID;
         }
         else if (str.equals(STATUS_ENDMARK_STR))
         {
            num = HeapBufferInfo.STATUS_ENDMARK;
         }
         else
         {
            num = HeapBufferInfo.STATUS_UNKNOWN;
         }
         filter.filterStatus(exclude, num);
      }

      return filter;
   }

   private static String isValidU32(String text, String errorMessage)
   {
      String msg = null;
      if (text.trim().length() > 0)
      {
         try
         {
            StringUtils.parseU32(text);
         }
         catch (NumberFormatException e)
         {
            msg = errorMessage;
         }
      }
      return msg;
   }

   public Target getTarget()
   {
      return target;
   }

   public int getHeapId()
   {
      return heapId;
   }

   public HeapBufferFilter getHeapBufferFilter()
   {
      return heapBufferFilter;
   }

   class CheckboxHandler implements ModifyListener
   {
      private Control control1;
      private Control control2;
      private Button checkbox;

      CheckboxHandler(Control control, Button checkbox)
      {
         this(control, null, checkbox);
      }

      CheckboxHandler(Control control1, Control control2, Button checkbox)
      {
         if (control1 instanceof Text)
         {
            ((Text) control1).addModifyListener(this);
         }
         else if (control1 instanceof Combo)
         {
            ((Combo) control1).addModifyListener(this);
         }
         else
         {
            throw new IllegalArgumentException();
         }

         if (control2 instanceof Text)
         {
            ((Text) control2).addModifyListener(this);
         }
         else if (control2 instanceof Combo)
         {
            ((Combo) control2).addModifyListener(this);
         }
         else if (control2 != null)
         {
            throw new IllegalArgumentException();
         }

         this.control1 = control1;
         this.control2 = control2;
         this.checkbox = checkbox;
      }

      public void modifyText(ModifyEvent event)
      {
         boolean filledIn = isFilledIn();
         if (!filledIn)
         {
            checkbox.setSelection(false);
         }
         checkbox.setEnabled(filledIn);
      }

      private boolean isFilledIn()
      {
         String text1 = "";
         String text2 = "";

         if (control1 instanceof Text)
         {
            text1 = ((Text) control1).getText();
         }
         else if (control1 instanceof Combo)
         {
            text1 = ((Combo) control1).getText();
         }

         if (control2 instanceof Text)
         {
            text2 = ((Text) control2).getText();
         }
         else if (control2 instanceof Combo)
         {
            text2 = ((Combo) control2).getText();
         }

         if (control2 == null)
         {
            return (text1.length() > 0);
         }
         else
         {
            return ((text1.length() > 0) || (text2.length() > 0));
         }
      }
   }

   class ValidationHandler implements ModifyListener
   {
      public void modifyText(ModifyEvent event)
      {
         validateDialog();
      }
   }

   class ClearHandler extends SelectionAdapter
   {
      public void widgetSelected(SelectionEvent event)
      {
         clearDialog();
         saveDialogSettings();
      }
   }
}
