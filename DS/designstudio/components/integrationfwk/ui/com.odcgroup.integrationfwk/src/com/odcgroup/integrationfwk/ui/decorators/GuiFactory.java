package com.odcgroup.integrationfwk.ui.decorators;

import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;

/**
 * 
 * 
 * Factory Class to create Gui Components
 *
 */
public class GuiFactory
{
  
  private static GuiFactory instance_ = null;

  private GuiFactory()
  {
  }

  public static GuiFactory getInstance()
  {
    if (instance_ == null)
    {
      instance_ = new GuiFactory();
    }
    return instance_;
  }

	/**
   * Create a Composite
   */ 
  public Composite createComposite(Composite parent, int numColumns)
  {
    Composite composite = new Composite(parent, SWT.NULL);

    // GridLayout
    GridLayout layout = new GridLayout();
    layout.numColumns = numColumns;
    composite.setLayout(layout);

    // GridData
    GridData data = new GridData();
    data.verticalAlignment = GridData.FILL;
    data.horizontalAlignment = GridData.FILL;
    composite.setLayoutData(data);
    return composite;
  }
  
  /**
   * Create a Text Field
   */ 
  public Text createTextField(Composite parent)
  {
    Text text = new Text(parent, SWT.SINGLE | SWT.BORDER);
    GridData data = new GridData(GridData.FILL_HORIZONTAL);
    data.verticalAlignment = GridData.CENTER;
    data.grabExcessVerticalSpace = false;
    data.widthHint = IDialogConstants.ENTRY_FIELD_WIDTH;
    text.setLayoutData(data);
    return text;
  }

}
