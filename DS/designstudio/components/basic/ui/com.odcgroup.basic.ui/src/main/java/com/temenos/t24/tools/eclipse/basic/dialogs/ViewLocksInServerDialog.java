package com.temenos.t24.tools.eclipse.basic.dialogs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.TableEditor;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;

import com.temenos.t24.tools.eclipse.basic.utils.MementoUtil;
import com.temenos.t24.tools.eclipse.basic.utils.MementoUtilFactory;
import com.temenos.t24.tools.eclipse.basic.utils.StringUtil;


public class ViewLocksInServerDialog extends Dialog
{
    private final int DIALOG_WIDTH   = 350;  // Dialog width pixels
    private final int DIALOG_HEIGHT  = 350;  // Dialog height pixels
    private final int SPACING        = 10;   // space between elements
    private final int LABEL_LENGTH   = 14;   // Labels = name, prompts, body
    
    // Memento class, used for getting, saving the server directory
    // from plugin persistence file.
    private MementoUtil mu;         
    
   // BASIC modules. e.g. ACCT.STMT.DATE, ACCOUNT, etc 
   private ArrayList<String> lockedFilesAL;
   private Map<String, Boolean> filesMap = new HashMap<String, Boolean>();
   
   // Fields used to capture the values
   private Table filesTable = null;
   private Text channelField; 
   
   /**
    * Creates a dialog instance. Note that the window will have no
    * visual representation (no widgets) until it is told to open. By
    * default, <code>open</code> blocks for dialogs.
    * 
    * @param parentShell the parent shell
    */
   public ViewLocksInServerDialog(Shell parentShell, ArrayList<String> lockedFiles) {
      super(parentShell);

      setShellStyle(getShellStyle() | SWT.RESIZE | SWT.MAX);
      mu  = MementoUtilFactory.getMementoUtil();
      this.lockedFilesAL = lockedFiles;
      for(int i=0; i<lockedFilesAL.size(); i++){
          filesMap.put(lockedFilesAL.get(i), true);
      }      
   }

   /**
    * Creates and returns the contents of the upper part of this
    * dialog (above the button bar). 
    * @param parent the parent composite to contain the dialog area
    * @return the dialog area control
    */
   protected Control createDialogArea(Composite parent) {
      StringUtil su = new StringUtil();
      Composite container = (Composite) super.createDialogArea(parent);
      FormLayout formLayout = new FormLayout();
      formLayout.marginWidth = SPACING;
      formLayout.marginHeight = SPACING;
      container.setLayout(formLayout);
      
      // *************************************************************
      // Channel directory
      final Label channelLabel= new Label(container, SWT.NONE);
      channelLabel.setText(su.pad("Channel:", LABEL_LENGTH," "));
      FormData formData = new FormData();
      formData.left   = new FormAttachment(0, SPACING);
      formData.top    = new FormAttachment(0, SPACING);
      channelLabel.setLayoutData(formData);      
   
      channelField = new Text(container, SWT.BORDER | SWT.READ_ONLY);
      // Set initial value 
      channelField.setText(mu.getProperty("t24.remote.channel.name"));
      formData = new FormData();
      formData.left   = new FormAttachment(channelLabel, SPACING);
      formData.top    = new FormAttachment(0, SPACING);
      formData.right  = new FormAttachment(100, -SPACING);
      channelField.setLayoutData(formData);      
            
      // *************************************************************
      // Files List
      final Label filenameLabel= new Label(container, SWT.NONE);
      filenameLabel.setText(su.pad("Files List:", LABEL_LENGTH," "));
      formData = new FormData();
      formData.left = new FormAttachment(0, SPACING);
      formData.top  = new FormAttachment(channelLabel, SPACING);
      filenameLabel.setLayoutData(formData);
      
      // Put the files names in an String[]
      final String[] files = new String[filesMap.size()];
      Iterator<String> it = filesMap.keySet().iterator();
      int idx =0;
      while(it.hasNext()){
          files[idx++] = (String)it.next();
      }
      Arrays.sort(files);
      
      // Build table
      filesTable = new Table(container, SWT.MULTI | SWT.BORDER | SWT.FULL_SELECTION);
      formData = new FormData();
      formData.left   = new FormAttachment(filenameLabel, SPACING);
      formData.right  = new FormAttachment(100, -SPACING);
      formData.top    = new FormAttachment(channelField, SPACING);
      formData.bottom = new FormAttachment(100, -SPACING);
      filesTable.setLayoutData(formData);
      
      filesTable.setHeaderVisible(false);
      filesTable.setLinesVisible(false);
      
      // Add columns + properties
      TableColumn column1 = new TableColumn(filesTable, SWT.NULL);
      column1.setWidth(30);
      TableColumn column2 = new TableColumn(filesTable, SWT.NULL);
      column2.setWidth(DIALOG_WIDTH);
      
      // Add items (rows) to the table
      for(int i=0; i<filesMap.size(); i++){
          new TableItem(filesTable, SWT.NONE);
      }
      TableItem[] tabItems = filesTable.getItems();

      // Iterate through all the items, adding text and check buttoms
      for(int i=0; i<tabItems.length; i++){
          final int j = i;
          TableEditor editor = new TableEditor(filesTable);
          final Button button = new Button (filesTable, SWT.CHECK);
          button.pack ();
          button.setSelection(true);
          editor.minimumWidth = button.getSize ().x;
          editor.horizontalAlignment = SWT.CENTER;
          editor.setEditor (button, tabItems[i], 0);
          
          button.addSelectionListener(new SelectionAdapter(){
              public void widgetSelected(SelectionEvent event){
                  if(button.getSelection()){
                      filesMap.put(files[j], true);
                  } else {
                      filesMap.put(files[j], false);
                  }
              }
          }); 
          
          editor = new TableEditor (filesTable);
          Text text = new Text (filesTable, SWT.NONE);
          text.setText(files[i]);
          editor.grabHorizontal = true;
          editor.setEditor(text, tabItems[i], 1);
      }
      
      // **************************************************************
      // Setup listeners for the fields
      setupListeners();

      return container;
   }
   

   /**
    * Overwritten.
    */
   protected void createButtonsForButtonBar(Composite parent) {
       super.createButtonsForButtonBar(parent);
       getButton(IDialogConstants.OK_ID).setText("Unlock");
   }
   
   /**
     * Sets up listeners for the text fields, so whenever they are modified the
     * relevant variables are updated.
     */
    private void setupListeners() {
    }
   
   /**
    * Configures the given shell in preparation for opening this window in it.
    * In this case, we set the dialog title.
    */
   protected void configureShell(Shell newShell) {
      super.configureShell(newShell);
      newShell.setText("View Locks Dialog");
   }

   /**
    * Returns the initial size to use for the shell.
    * @return the initial size of the shell
    */
   protected Point getInitialSize() {
       // Answer the size from the previous incarnation.
       Rectangle b1 = getShell().getDisplay().getBounds();
       Rectangle b2 = new Rectangle(0,0,DIALOG_WIDTH,DIALOG_HEIGHT);
       return new Point(
         b1.width < b2.width ? b1.width : b2.width,
         b1.height < b1.height ? b2.height : b2.height);
    }      

    public Map<String, Boolean> getFilesSelected(){
        return this.filesMap;
    }
}
