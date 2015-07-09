package com.odcgroup.page.ui.properties;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.eclipse.jface.viewers.DialogCellEditor;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

import com.odcgroup.mdf.ecore.util.DomainRepository;
import com.odcgroup.mdf.editor.ui.dialogs.mdf.assist.IContentAssistProvider;
import com.odcgroup.mdf.editor.ui.dialogs.mdf.assist.MdfEntitySelectionDialog;
import com.odcgroup.mdf.ext.aaa.AAAAspect;
import com.odcgroup.mdf.metamodel.MdfClass;
import com.odcgroup.mdf.metamodel.MdfDataset;
import com.odcgroup.mdf.metamodel.MdfDomain;
import com.odcgroup.mdf.metamodel.MdfEntity;
import com.odcgroup.mdf.metamodel.MdfModelElement;
import com.odcgroup.mdf.metamodel.MdfName;
import com.odcgroup.mdf.metamodel.MdfNameFactory;
import com.odcgroup.mdf.model.util.ModelVisitor;
import com.odcgroup.mdf.model.util.ModelWalker;
import com.odcgroup.page.metamodel.PropertyTypeConstants;
import com.odcgroup.page.model.Property;
import com.odcgroup.page.model.Widget;
import com.odcgroup.workbench.core.IOfsProject;
import com.odcgroup.workbench.core.repository.OfsResourceHelper;

/**
 * @author phanikumark
 *
 */
public class EditableDatasetCellEditor extends DialogCellEditor {
	

	/** The Property*/
	private Property property;

	/**
	 * @param parent
	 * @param property
	 */
	public EditableDatasetCellEditor(Composite parent, Property property) {
		super(parent);
		this.property = property;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.viewers.DialogCellEditor#openDialogBox(org.eclipse.swt.widgets.Control)
	 */
	@Override
	protected Object openDialogBox(Control cellEditorWindow) {
    	final String[] result = {property.getValue()};
		Widget rw = property.getWidget().getRootWidget();
    	Property entity = rw.findProperty(PropertyTypeConstants.DOMAIN_ENTITY);
    	if (entity == null) {
    		return result[0];
    	}
		IOfsProject project = OfsResourceHelper.getOfsProject(property.eResource());
		DomainRepository repository = DomainRepository.getInstance(project);
		MdfName qname = MdfNameFactory.createMdfName(entity.getValue());
		MdfDataset ds = repository.getDataset(qname);
		if (ds != null) {
			IContentAssistProvider provider = getDomainObjectProvider(repository, ds);
			MdfEntitySelectionDialog dialog = MdfEntitySelectionDialog.createDialog(cellEditorWindow.getShell(), provider);
	        dialog.setSelection((MdfName)null);
	        if (dialog.open() == Window.OK) {
	        	MdfEntity sent = (MdfEntity)dialog.getFirstResult();
	        	if (entity != null) {
	        		result[0] = sent.getQualifiedName().getQualifiedName();
				}
	        }
		}
	    
	    return result[0];
	}
	
	/**
	 * @return the domain object provider
	 */
	@SuppressWarnings("unchecked")
	public IContentAssistProvider getDomainObjectProvider(final DomainRepository repository, final MdfDataset dataset) {
		final IContentAssistProvider contentAssistProvider = new IContentAssistProvider() {
			public MdfModelElement[] getCandidates() {
				final List<MdfDataset> allDatasets = new ArrayList<MdfDataset>();
				final MdfClass baseClass = dataset.getBaseClass();
				if (baseClass != null) {
					String sqlName = AAAAspect.getTripleAEntitySQLName(baseClass);
					if (!StringUtils.isEmpty(sqlName)) {					
						MdfDomain entDom = repository.getDomain("AAAEntities");
						if (entDom != null) {
							List<MdfClass> classes = entDom.getClasses();
							final MdfClass klass = getEntityWithSQLName(sqlName, classes);
							Iterator<MdfDomain> it = repository.getDomains().iterator();
							while (it.hasNext()) {
								new ModelWalker(new ModelVisitor() {
									public boolean accept(MdfModelElement model) {
										if (model instanceof MdfDataset) {
											MdfDataset ds = (MdfDataset) model;
											if (klass.equals(ds.getBaseClass())) {
												allDatasets.add((MdfDataset) model);
											}
										}
										return true;
									}
								}).visit(it.next());
							}
						}
					}
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
	 * @param sqlName
	 * @param classes
	 * @return
	 */
	private MdfClass getEntityWithSQLName(String sqlName, List<MdfClass> classes) {
		for (MdfClass mdfClass : classes) {
			String name = AAAAspect.getTripleAEntitySQLName(mdfClass);
			if (StringUtils.isNotEmpty(name) && sqlName.equals(name)) {
				return mdfClass;
			}
		}
		return null;
	}

}
