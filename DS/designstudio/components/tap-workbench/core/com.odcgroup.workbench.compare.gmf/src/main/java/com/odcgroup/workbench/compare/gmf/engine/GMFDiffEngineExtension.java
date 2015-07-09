package com.odcgroup.workbench.compare.gmf.engine;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.compare.diff.metamodel.DiffElement;
import org.eclipse.emf.compare.diff.metamodel.DiffGroup;
import org.eclipse.emf.compare.diff.metamodel.DiffModel;
import org.eclipse.emf.compare.diff.metamodel.UpdateAttribute;
import org.eclipse.emf.compare.diff.metamodel.impl.AbstractDiffExtensionImpl;
import org.eclipse.gmf.runtime.notation.Diagram;

public abstract class GMFDiffEngineExtension extends AbstractDiffExtensionImpl {
	
	/* (non-Javadoc)
	 * @see org.eclipse.emf.compare.diff.metamodel.impl.AbstractDiffExtensionImpl#visit(org.eclipse.emf.compare.diff.metamodel.DiffModel)
	 */
	public void visit(DiffModel diffModel) {
		if(diffModel.getOwnedElements().size()==1) {
			DiffElement diagramDiffs = null;
			DiffElement rootDiff = (DiffElement) diffModel.getOwnedElements().get(0);
			for(Object diff : rootDiff.getSubDiffElements()) {
				if(diff instanceof DiffGroup) {
					DiffGroup group = (DiffGroup) diff;
					if(group.getRightParent() instanceof Diagram) {
						diagramDiffs = group;
					}
					List<DiffElement> list = new ArrayList<DiffElement>();
					list.addAll(group.getSubDiffElements());
					for (DiffElement subDiff : group.getSubDiffElements()) {
						if (subDiff instanceof UpdateAttribute) {
							UpdateAttribute attribute = (UpdateAttribute) subDiff;
							// filter version
							for(String attrbName : getAttributesToFilter()) {
								if(attribute.getAttribute().getName().equals(attrbName)) {
									list.remove(subDiff);
								}
							}							
						}
					}
					group.getSubDiffElements().clear();
					group.getSubDiffElements().addAll(list);
				}
			}
			if(diagramDiffs!=null) {
				rootDiff.getSubDiffElements().remove(diagramDiffs);
				diffModel.getOwnedElements().clear();
				diffModel.getOwnedElements().addAll(rootDiff.getSubDiffElements());
			}
		}
		super.visit(diffModel);
	}
	
	/**
	 * attribute names that needs to be filtered from the compare list
	 * 
	 * @return
	 */
	public abstract String[] getAttributesToFilter();
}
