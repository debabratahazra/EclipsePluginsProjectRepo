package samplecode.source.view;

import java.text.Collator;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IResource;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerSorter;

public class SampleViewerSorter extends ViewerSorter {

	public int category(Object element) {
		if (element instanceof IContainer) {
			return 0;
		} else {
			return 1;
		}

	}

	public int compare(Viewer viewer, Object e1, Object e2) {

		int cat1 = category(e1);
		int cat2 = category(e2);

		if (cat1 != cat2) {
			return cat1 - cat2;
		}

		if (e1 instanceof IResource && e2 instanceof IResource) {
			return Collator.getInstance().compare(((IResource) e2).getName(),
					((IResource) e1).getName());
		}
		return super.compare(viewer, e1, e2);
	}
}
