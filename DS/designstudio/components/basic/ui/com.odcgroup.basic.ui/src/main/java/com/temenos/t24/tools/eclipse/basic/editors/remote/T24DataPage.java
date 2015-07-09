package com.temenos.t24.tools.eclipse.basic.editors.remote;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.editor.FormEditor;
import org.eclipse.ui.forms.editor.FormPage;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.ScrolledForm;
import org.eclipse.ui.forms.widgets.Section;
import org.eclipse.ui.forms.widgets.TableWrapData;
import org.eclipse.ui.forms.widgets.TableWrapLayout;

import com.temenos.t24.tools.eclipse.basic.protocols.ftp.RemoteOperationsLog;

/**
 * First page in the {@link T24DataFileEditor}.
 * 
 * @author ssethupathi
 * 
 */
public class T24DataPage extends FormPage {

    private T24DataFileModel model;
    private Text sourceText;
    private Section sourceSection;
    private Composite parent;

    /**
     * Construct the T24 data file page.
     * 
     * @param editor
     * @param model
     */
    public T24DataPage(FormEditor editor, T24DataFileModel model) {
        super(editor, model.getFileName(), model.getDescription());
        this.model = model;
    }

    protected void createFormContent(IManagedForm managedForm) {
        final ScrolledForm form = managedForm.getForm();
        final FormToolkit toolkit = managedForm.getToolkit();
        TableWrapLayout layout1 = new TableWrapLayout();
        parent = form.getBody();
        layout1.numColumns = 1;
        parent.setLayout(layout1);
        TableWrapData trData = new TableWrapData();
        final Button button1 = toolkit.createButton(parent, "Get from T24", SWT.PUSH);
        final Button button2 = toolkit.createButton(parent, "Send to T24", SWT.PUSH);
        button1.setEnabled(canEnableGetButton());
        button2.setEnabled(canEnableSendButton());
        
        button1.addSelectionListener(new SelectionAdapter() {

            public void widgetSelected(SelectionEvent e) {
                button1.setEnabled(false);
                boolean received = model.getFromT24();
                if (received) {
                    sourceSection.dispose();
                    createSourceArea(form, toolkit);
                    RemoteOperationsLog.info("File " + model.getFileName() + " received from T24 Server");
                }
                button1.setEnabled(true);
                button2.setEnabled(canEnableSendButton());
            }
        });
        button1.setLayoutData(trData);
        
       
        button2.addSelectionListener(new SelectionAdapter() {

            public void widgetSelected(SelectionEvent e) {
                button2.setEnabled(false);
                boolean sent = model.sendToT24();
                if (sent) {
                  RemoteOperationsLog.info("File " + model.getFileName() + " sent to T24 Server");
                }
                button2.setEnabled(canEnableSendButton());
            }
        });
        createSourceArea(form, toolkit);
    }
    
    private boolean canEnableSendButton(){
        if(model==null || model.getFileContent().length()==0 || !model.isLocalFile() ){
          return false;
      }
      return true;
  }
    
    private boolean canEnableGetButton(){
        if (model==null || !model.isLocalFile()) {
            return false;
        }
        return true;
        
    }

   private void createSourceArea(ScrolledForm form, FormToolkit toolkit) {
        TableWrapLayout layout = new TableWrapLayout();
        parent.setLayout(layout);
        layout.numColumns = 1;
        sourceSection = toolkit.createSection(form.getBody(), Section.DESCRIPTION);
        TableWrapData td = new TableWrapData();
        td.align = TableWrapData.FILL;
        td.grabHorizontal = true;
        sourceSection.setLayoutData(td);
        sourceSection.setText("Content");
        toolkit.createCompositeSeparator(sourceSection);
        Composite client = toolkit.createComposite(sourceSection);
        client.setData(FormToolkit.KEY_DRAW_BORDER, FormToolkit.TEXT_BORDER);
        toolkit.paintBordersFor(client);
        TableWrapLayout gisLayout = new TableWrapLayout();
        client.setLayout(gisLayout);
        gisLayout.numColumns = 1;
        td = new TableWrapData(TableWrapData.FILL_GRAB);
        client.layout();
        sourceText = toolkit.createText(client, "Loading...", SWT.MULTI);
        String fileContent = model.getFileContent();
        sourceText.setText(fileContent);
        sourceText.setEditable(false);
        TableWrapData gd = new TableWrapData(TableWrapData.FILL_GRAB);
        gd.grabVertical = true;
        sourceText.setLayoutData(gd);
        sourceSection.setClient(client);
        form.reflow(true);
    }
}
