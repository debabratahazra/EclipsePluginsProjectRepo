package com.temenos.t24.tools.eclipse.basic.dialogs;

import java.io.InputStream;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;


public class T24MessageDialog extends MessageDialog
{
    
    /**
     * Create a message dialog. Note that the dialog will have no visual
     * representation (no widgets) until it is told to open.
     * 
     * @param parentShell
     *            the parent shell
     * @param dialogTitle
     *            the dialog title, or <code>null</code> if none
     * @param dialogTitleImage
     *            the dialog title image, or <code>null</code> if none
     * @param dialogMessage
     *            the dialog message
     * @param dialogImageType
     *            one of the following values:
     *            <ul>
     *            <li><code>MessageDialog.NONE</code> for a dialog with no
     *            image</li>
     *            <li><code>MessageDialog.ERROR</code> for a dialog with an
     *            error image</li>
     *            <li><code>MessageDialog.INFORMATION</code> for a dialog
     *            with an information image</li>
     *            <li><code>MessageDialog.QUESTION </code> for a dialog with a
     *            question image</li>
     *            <li><code>MessageDialog.WARNING</code> for a dialog with a
     *            warning image</li>
     *            </ul>
     */    
    public T24MessageDialog(Shell parentShell,
          String dialogTitle, String dialogMessage, int dialogImageType)
    {
        super(parentShell, dialogTitle, getTitleImage(), dialogMessage, dialogImageType,
                new String[]{"OK","Cancel"}, 1);
    }

    /**
     * Build content for the area of the dialog made visible when the
     * Details button is clicked.
     * 
     * @param parent the details area parent
     * @return the details area
     */
    protected Control createDetailsArea(Composite parent) {

       // Create the details area.
       Composite panel = new Composite(parent, SWT.NONE);
       panel.setLayoutData(new GridData(GridData.FILL_BOTH));
       GridLayout layout = new GridLayout();
       panel.setBounds(100, 100, 300, 200);
       layout.marginHeight = 0;
       layout.marginWidth = 0;
       panel.setLayout(layout);

       return panel;
    }
    
    private static Image getTitleImage(){
        String imageFile = "/icons/myt24Image.gif";
        InputStream is = T24MessageDialog.class.getResourceAsStream(imageFile);
        Image image = new Image(null, is);
        return image;
    }
 }
