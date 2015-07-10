package com.zealcore.se.ui.actions;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.widgets.ColorDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IViewActionDelegate;
import org.eclipse.ui.IViewPart;

import com.zealcore.se.core.model.ILogEvent;
import com.zealcore.se.ui.core.IEventColorProvider;
import com.zealcore.se.ui.graphics.ColorAdapter;
import com.zealcore.se.ui.graphics.IColor;

public class SetEventTypeColorAction extends AbstractObjectDelegate implements
        IViewActionDelegate {

    public SetEventTypeColorAction() {}

    public void init(final IViewPart view) {
        setPart(view);

    }

    @Override
    public void runSafe(final IAction action) {
        if (guardFail()) {
            return;
        }
        final ISelection selection = getSelection();
        if (!(selection instanceof IStructuredSelection)) {
            return;
        }
        final IStructuredSelection struct = (IStructuredSelection) selection;
        final Object o = struct.getFirstElement();
        if (!(o instanceof ILogEvent)) {
            return;
        }
        // ok we have a log event which we can use to set a color
        // in the color provider
        final ColorDialog dlg = new ColorDialog(Display.getDefault()
                .getActiveShell());
        final RGB color = dlg.open();
        if (color == null) {
            return;
        }
        final ILogEvent event = (ILogEvent) o;
        final IEventColorProvider colorProvider = getServiceProvider()
                .getService(IEventColorProvider.class);
        final IColor clr = ColorAdapter.valueOf(color);
        colorProvider.setColor(event.getType(), clr);

    }

    @Override
    public void selectionChanged(final IAction action,
            final ISelection selection) {
        if (selection == null) {
            action.setEnabled(false);
        }
        if (selection instanceof IStructuredSelection) {
            final IStructuredSelection strct = (IStructuredSelection) selection;
            if (strct.getFirstElement() == null) {
                action.setEnabled(false);
            }
            if (strct.getFirstElement() instanceof ILogEvent) {
                action.setEnabled(true);

            }
        }
        super.selectionChanged(action, selection);
    }
}
