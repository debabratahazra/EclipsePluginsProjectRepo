package com.zealcore.se.ui.actions;

// package com.zealcore.se.ui.actions;
//
// import org.eclipse.core.resources.IProject;
// import org.eclipse.jface.action.IAction;
// import org.eclipse.jface.viewers.IStructuredSelection;
// import org.eclipse.ui.IObjectActionDelegate;
//
// import com.zealcore.se.ui.SystemExplorerNature;
//
// public class AddZealcoreNatureAction
//
// extends AbstractObjectDelegate implements IObjectActionDelegate {
//
// public AddZealcoreNatureAction() {
// // Empty constructor
// }
//
// public void run(final IAction action) {
// if (guardFail()) {
// return;
// }
//
// if (selection instanceof IStructuredSelection) {
// final IStructuredSelection struct = (IStructuredSelection) selection;
// final Object first = struct.getFirstElement();
//
// if (first instanceof IProject) {
// final IProject project = (IProject) first;
//
// SystemExplorerNature.applyTo(project);
// }
// }
//
// }
//
// }
