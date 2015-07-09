package com.odcgroup.page.ui.properties;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.jface.viewers.DialogCellEditor;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

import com.odcgroup.mdf.ecore.util.DomainRepository;
import com.odcgroup.mdf.editor.ui.dialogs.mdf.assist.IContentAssistProvider;
import com.odcgroup.mdf.editor.ui.dialogs.mdf.assist.MdfEntitySelectionDialog;
import com.odcgroup.mdf.metamodel.MdfDataset;
import com.odcgroup.mdf.metamodel.MdfDomain;
import com.odcgroup.mdf.metamodel.MdfEntity;
import com.odcgroup.mdf.metamodel.MdfModelElement;
import com.odcgroup.mdf.metamodel.MdfName;
import com.odcgroup.mdf.model.util.ModelVisitor;
import com.odcgroup.mdf.model.util.ModelWalker;
import com.odcgroup.page.model.Property;
import com.odcgroup.page.ui.util.EclipseUtils;


/**
 * Defines the context cell editor for the property context
 * 
 * @author Alexandre Jaquet
 * @author Gary Hayes
 */
public class DomainEntityCellEditor extends DialogCellEditor {
	
	/** The Property*/
	private Property property;
	
	/**
	 * Constructor
	 * 
	 * @param parent 
	 * 			The parent component
	 * @param property
	 * 			The selected property
	 */
    public DomainEntityCellEditor(Composite parent, Property property) {
        super(parent);
        this.property = property;
    }
    
	/**
	 * @return the domain object provider
	 */
	public IContentAssistProvider getDomainObjectProvider() {
		final DomainRepository repository = DomainRepository.getInstance(EclipseUtils.findCurrentProject());
		final IContentAssistProvider contentAssistProvider = new IContentAssistProvider() {
			public MdfModelElement[] getCandidates() {
				final List<MdfDataset> allDatasets = new ArrayList<MdfDataset>();
				Iterator<MdfDomain> it = repository.getDomains().iterator();
				while (it.hasNext()) {
					new ModelWalker(new ModelVisitor() {
						public boolean accept(MdfModelElement model) {
							if (model instanceof MdfDataset) {
								allDatasets.add((MdfDataset) model);
							}
							return true;
						}
					}).visit(it.next());
				}
				return allDatasets.toArray(new MdfModelElement[] {});
			}

			public String getDefaultDomainName() {
				return "";
			}
		};
		return contentAssistProvider;
	}    

    /**
     * Open dialog window
     * 
     * @param cellEditorWindow 
     * 			The cell editor window
     * @return Object
     */
    protected Object openDialogBox(Control cellEditorWindow) {
    	final String[] result = {property.getValue()};
        MdfEntitySelectionDialog dialog =
            MdfEntitySelectionDialog.createDialog(cellEditorWindow.getShell(), getDomainObjectProvider());
        dialog.setSelection((MdfName)null);
        if (dialog.open() == Window.OK) {
        	MdfEntity entity = (MdfEntity)dialog.getFirstResult();
        	if (entity != null) {
        		result[0] = entity.getQualifiedName().getQualifiedName();
			}
        }
	    
	    return result[0];

    }	
}
