package com.odcgroup.translation.ui.internal.views;
import org.eclipse.jface.viewers.ITreeSelection;
import org.eclipse.jface.viewers.TreePath;
import org.eclipse.jface.viewers.TreeSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;

import com.odcgroup.translation.core.ITranslationKind;
import com.odcgroup.translation.ui.views.ITranslationKindSelector;
import com.odcgroup.translation.ui.views.ITranslationModel;

/**
 * TODO: Document me!
 *
 * @author atr
 */
public class TranslationKindSelector implements ITranslationKindSelector {
	
	/** The translations */
	private ITranslationModel model;
	
	/** contains the different types of translation */
	private TreeViewer treeViewer;
	
	/** */
	private TranslationSelectorLabelProvider selectorLabelProvider 
		= new TranslationSelectorLabelProvider();
	
	/**
	 * 
	 */
	protected void updateSelection() {
		ITranslationKind kind = getTranslationModel().getSelectedKind();
		Tree tree = getViewer().getTree();
		tree.deselectAll();
		if (kind != null) {
			TreePath path = new TreePath(new Object[]{kind});
            getViewer().setSelection((ITreeSelection)new TreeSelection(path), true);
		}
	}
	
	/**
	 * @return
	 */
	protected final ITranslationModel getTranslationModel() {
		return this.model;
	}
	
	/**
	 * @return
	 */
	protected final TreeViewer getViewer() {
		return treeViewer;
	}
	
	/**
	 * @param parent
	 * @return
	 */
	protected TreeViewer createTreeViewer(Composite parent) {
		return new TreeViewer(parent);
	}
	
	/**
	 * @param parent
	 */
	protected void createControls(Composite parent) {
		
		treeViewer = createTreeViewer(parent);
		GridData gridData = new GridData(GridData.FILL_VERTICAL | GridData.VERTICAL_ALIGN_BEGINNING);
		treeViewer.getTree().setLayoutData(gridData);
		
		treeViewer.setLabelProvider(selectorLabelProvider);
		treeViewer.setContentProvider(new TranslationSelectorContentProvider());
    	
		treeViewer.getTree().addSelectionListener(new SelectionAdapter(){			
			public void widgetSelected(SelectionEvent event) {
				TreeItem item = ((Tree)event.widget).getSelection()[0];
            	ITranslationKind kind = (ITranslationKind)item.getData();
            	getTranslationModel().selectKind(kind);
			}    		
    	});	
    }
	
	/**
	 * @param newModel
	 */
	public void setTranslationModel(ITranslationModel newModel) {
		model = newModel;
		if (selectorLabelProvider != null) {
			selectorLabelProvider.setModel(newModel);
		}
		getViewer().setInput(model);
		getViewer().expandAll();
		updateSelection();
	}

	/**
	 * 
	 */
	public void dispose() {
	}
	
	/**
	 * @param model
	 * @param parent
	 */
	public TranslationKindSelector(Composite parent) {
		createControls(parent);
	}


}
