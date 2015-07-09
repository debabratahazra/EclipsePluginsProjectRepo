package com.odcgroup.workbench.editors.properties.controls;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetWidgetFactory;

import com.odcgroup.workbench.editors.properties.OFSPropertyPlugIn;

public class PropertyMessageWidget {
	

    private CLabel errorMessageLabel;
    private CLabel warnMessageLabel;
    private static final Image smartErrorImage = OFSPropertyPlugIn.getBundledImageDescriptor("icons/obj16/error.gif").createImage();
    private static final Image smartWarningImage = OFSPropertyPlugIn.getBundledImageDescriptor("icons/obj16/warning.gif").createImage();
//    private static final Image smartInfoImage = OFSPropertyPlugIn.getBundledImageDescriptor("icons/obj16/info.gif").createImage();    
    private Color gradWarning[];
//    private Color gradInfo[];
    private Color gradError[];
    private Composite parent;
    
    /**
	 * @param mbody
	 */
	public PropertyMessageWidget(Composite parent, TabbedPropertySheetWidgetFactory factory) {
		this.parent = parent;
		
		Composite body = factory.createComposite(parent);  

		GridLayout msgLayout = new GridLayout();
		msgLayout.numColumns=1;
		msgLayout.makeColumnsEqualWidth=false;
		msgLayout.marginHeight = 0;
		msgLayout.marginWidth = 0;
		msgLayout.verticalSpacing = 1;
		body.setLayout(msgLayout);
		
        errorMessageLabel = new CLabel(body, SWT.WRAP);
        warnMessageLabel = new CLabel(body, SWT.WRAP);
        
        gradError = getColorGradient(factory, 3, 85, body);
        // gradInfo = getColorGradient(factory, 9, 85, parent);
        gradWarning = getColorGradient(factory, 7, 65, body);  
	}
	

	/**
	 * @param factory
	 * @param defCol
	 * @param alpha
	 * @return
	 */
	private Color[] getColorGradient(TabbedPropertySheetWidgetFactory factory, int defCol, int alpha, Composite mbody) {
        return (new Color[] {
            getColor(factory, defCol, alpha, mbody), mbody.getBackground()
        });
    }

    /**
     * @param factory
     * @param defCol
     * @param alpha
     * @return
     */
    private Color getColor(TabbedPropertySheetWidgetFactory factory, int defCol, int alpha, Composite mbody) {
        Color color = factory.getColors().getColor("ErrorMessageSection.Color" + defCol);
        if(color != null) {
            return color;
        } else {
            RGB tbBg = getSystemColor(defCol, mbody);
            RGB formBackground = mbody.getBackground().getRGB();
            tbBg = blend(formBackground, tbBg, alpha);
            color = factory.getColors().createColor("ErrorMessageSection.Color" + defCol, tbBg);
            return color;
        }
    }
    
    public void setErrorMessage(String errorMessage){
    	errorMessageLabel.setVisible(true);            
    	errorMessageLabel.setImage(smartErrorImage);
        errorMessageLabel.setBackground(gradError, new int[] {100}, false);            
        GridData gd = new GridData(SWT.FILL, SWT.FILL, true, true);
        gd.heightHint = 16;            
        errorMessageLabel.setText(errorMessage);
        errorMessageLabel.setLayoutData(gd);
        errorMessageLabel.getParent().layout(true);
    }
    
    public void setWarnMessage(String warnMessage) { 
    	warnMessageLabel.setVisible(true);                  
        GridData gd = new GridData(SWT.FILL, SWT.FILL, true, true);
        gd.heightHint = 16;             
        warnMessageLabel.setImage(smartWarningImage);
        warnMessageLabel.setBackground(gradWarning, new int[] {100}, false);            
        warnMessageLabel.setText(warnMessage);
        warnMessageLabel.setLayoutData(gd);
        warnMessageLabel.getParent().layout(true);
    }
    
    public void resetErrorLabel() {                
        GridData gd = new GridData();
        gd.heightHint = 0;      
        errorMessageLabel.setVisible(false); 
        errorMessageLabel.setLayoutData(gd);
        errorMessageLabel.getParent().layout(true);
    }
    
    public void resetWarnLabel() {                  
        GridData gd = new GridData();
        gd.heightHint = 0;                      
    	warnMessageLabel.setVisible(false); 
        warnMessageLabel.setLayoutData(gd);
        warnMessageLabel.getParent().layout(true);
    }

    /**
     * @param code
     * @return
     */
    public RGB getSystemColor(int code, Composite mbody) {
        return mbody.getDisplay().getSystemColor(code).getRGB();
    }

    /**
     * @param c1
     * @param c2
     * @param ratio
     * @return
     */
    private RGB blend(RGB c1, RGB c2, int ratio){
        int r = blend(c1.red, c2.red, ratio);
        int g = blend(c1.green, c2.green, ratio);
        int b = blend(c1.blue, c2.blue, ratio);
        return new RGB(r, g, b);
    }

    /**
     * @param v1
     * @param v2
     * @param ratio
     * @return
     */
    private int blend(int v1, int v2, int ratio){
        return (ratio * v1 + (100 - ratio) * v2) / 100;
    }


	/**
	 * @return
	 */
	public CLabel getErrorMessageLabel() {
		return errorMessageLabel;
	}


	/**
	 * @return
	 */
	public CLabel getWarnMessageLabel() {
		return warnMessageLabel;
	}


	public Composite getParent() {
		return parent;
	}

}
