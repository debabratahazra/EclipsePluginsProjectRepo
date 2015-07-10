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

package com.ose.system.ui.views.pooloptimizer;

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
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Scale;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;
import com.ose.system.Pool;
import com.ose.system.Target;
import com.ose.system.ui.SystemBrowserPlugin;
import com.ose.system.ui.util.StringUtils;

public class PoolOptimizerDialog extends Dialog
{
   public static final int LEVELS_MIN = 0;

   public static final int LEVELS_MAX = 100;

   public static final int LEVELS_DEFAULT = 30;

   private List targets;

   private String targetName;

   private String poolName;

   private Target target;

   private int poolId;

   private int levels;

   private Combo targetCombo;

   private Text poolIdText;

   private Scale levelsScale;

   private CLabel errorMessageLabel;

   private Button clearButton;

   public PoolOptimizerDialog(Shell parent, List targets)
   {
      super(parent);
      if (targets == null)
      {
         throw new NullPointerException();
      }
      this.targets = targets;
   }

   public PoolOptimizerDialog(Shell parent, List targets, Target target, Pool pool)
   {
      this(parent, targets);

      if (target == null)
      {
         throw new NullPointerException();
      }

      this.targetName = target.toString();

      if (pool != null)
      {
         this.poolName = StringUtils.toHexString(pool.getId());
      }
   }

   protected void configureShell(Shell shell)
   {
      super.configureShell(shell);
      shell.setText("Pool Selection");
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
      GridData gd;
      ValidationHandler validationHandler;
      Label label;

      comp = (Composite) super.createDialogArea(parent);
      subComp = new Composite(comp, SWT.NONE);
      subComp.setLayout(new GridLayout(2, false));
      validationHandler = new ValidationHandler();

      label = new Label(subComp, SWT.NONE);
      label.setText("Target:");

      targetCombo = new Combo(subComp, SWT.READ_ONLY);
      gd = new GridData(GridData.FILL_HORIZONTAL);
      gd.widthHint = convertHorizontalDLUsToPixels(180);
      targetCombo.setLayoutData(gd);
      for (Iterator i = targets.iterator(); i.hasNext();)
      {
         Target target = (Target) i.next();
         targetCombo.add(target.toString());
      }
      targetCombo.addModifyListener(validationHandler);

      label = new Label(subComp, SWT.NONE);
      label.setText("Pool ID:");

      poolIdText = new Text(subComp, SWT.SINGLE | SWT.BORDER);
      poolIdText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
      poolIdText.addModifyListener(validationHandler);

      label = new Label(subComp, SWT.NONE);
      gd = new GridData();
      gd.horizontalSpan = 2;
      label.setLayoutData(gd);

      label = new Label(subComp, SWT.NONE);
      label.setText("Algorithm Thoroughness:");
      gd = new GridData();
      gd.horizontalSpan = 2;
      label.setLayoutData(gd);

      levelsScale = new Scale(subComp, SWT.HORIZONTAL);
      levelsScale.setMinimum(LEVELS_MIN);
      levelsScale.setMaximum(LEVELS_MAX);
      levelsScale.setSelection(LEVELS_DEFAULT);
      levelsScale.setIncrement(5);
      levelsScale.setPageIncrement(10);
      gd = new GridData(GridData.FILL_HORIZONTAL);
      gd.horizontalSpan = 2;
      levelsScale.setLayoutData(gd);

      label = new Label(subComp, SWT.LEFT);
      label.setText("Less");
      label.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

      label = new Label(subComp, SWT.RIGHT);
      label.setText("More");
      label.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

      // Create error message label.
      errorMessageLabel = new CLabel(subComp, SWT.NONE);
      gd = new GridData(GridData.FILL_HORIZONTAL);
      gd.horizontalSpan = 2;
      errorMessageLabel.setLayoutData(gd);

      // Create separator line.
      label = new Label(parent, SWT.HORIZONTAL | SWT.SEPARATOR);
      gd = new GridData(GridData.FILL_HORIZONTAL);
      gd.horizontalSpan = 2;
      label.setLayoutData(gd);

      applyDialogFont(comp);
      return comp;
   }

   protected void createButtonsForButtonBar(Composite parent)
   {
      Label spacer;
      GridLayout layout;

      parent.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

      clearButton = createButton(parent, IDialogConstants.CLIENT_ID + 1,
            "Clear", false);
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
      if (targetName != null)
      {
         clearDialog();
         targetCombo.setText(targetName);
         poolIdText.setText((poolName != null) ? poolName : "");
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
      int levs;

      settings = SystemBrowserPlugin.getDefault().getDialogSettings();
      section = settings.getSection("PoolOptimizerDialog");
      if (section == null)
      {
         clearDialog();
         return;
      }

      targetCombo.setText(section.get("target"));
      poolIdText.setText(section.get("poolId"));
      levs = section.getInt("levels");
      if ((levs >= LEVELS_MIN) && (levs <= LEVELS_MAX))
      {
         levelsScale.setSelection(levs);
      }
      else
      {
         levelsScale.setSelection(LEVELS_DEFAULT);
      }
   }

   private void saveDialogSettings()
   {
      IDialogSettings settings;
      IDialogSettings section;

      settings = SystemBrowserPlugin.getDefault().getDialogSettings();
      section = settings.addNewSection("PoolOptimizerDialog");

      section.put("target", targetCombo.getText());
      section.put("poolId", poolIdText.getText().trim());
      section.put("levels", levelsScale.getSelection());
   }

   private void clearDialog()
   {
      poolIdText.setText("");
   }

   private void validateDialog()
   {
      String errorMessage = null;

      if (targetCombo.getText().length() == 0)
      {
         errorMessage = "Target not specified";
      }
      else if (poolIdText.getText().trim().length() == 0)
      {
         errorMessage = "Pool ID not specified";
      }
      else
      {
         errorMessage = isValidU32(poolIdText.getText(), "Invalid pool ID");
      }
      setErrorMessage(errorMessage);
   }

   private void setErrorMessage(String errorMessage)
   {
      errorMessageLabel.setText((errorMessage == null) ? "" : errorMessage);
      errorMessageLabel.setImage((errorMessage == null) ? null :
         PlatformUI.getWorkbench().getSharedImages().getImage(
            ISharedImages.IMG_OBJS_ERROR_TSK));
      getButton(IDialogConstants.OK_ID).setEnabled(errorMessage == null);
      getShell().update();
   }

   protected void okPressed()
   {
      target = createTarget();
      try
      {
         poolId = createPoolId();
      } catch (NumberFormatException ignore) {}
      levels = createLevels();
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

   private int createPoolId() throws NumberFormatException
   {
      String str = poolIdText.getText().trim();
      int result = (str.length() > 0) ? StringUtils.parseU32(str) : 0;
      return result;
   }

   private int createLevels()
   {
      return levelsScale.getSelection();
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

   public int getPoolId()
   {
      return poolId;
   }

   public int getLevels()
   {
      return levels;
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
         validateDialog();
         saveDialogSettings();
      }
   }
}
