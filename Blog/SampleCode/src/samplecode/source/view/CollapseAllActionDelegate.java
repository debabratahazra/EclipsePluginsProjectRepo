package samplecode.source.view;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IViewActionDelegate;
import org.eclipse.ui.IViewPart;

import samplecode.source.SampleView;

public class CollapseAllActionDelegate implements IViewActionDelegate {
	
	private SampleView sampleView;

	@Override
	public void run(IAction action) {
		sampleView.gettViewer().collapseAll();
	}

	@Override
	public void selectionChanged(IAction action, ISelection selection) {
	}

	@Override
	public void init(IViewPart view) {
		sampleView = (SampleView)view;
	}

}
