package com.zealcore.se.ui.actions;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.IInputValidator;
import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.window.Window;

import com.zealcore.se.ui.ITimeCluster;

public class GotoAction extends AbstractObjectDelegate {

    private static final String BETWEEN_TOKEN = "...";

    public GotoAction() {}

    @Override
    public void runSafe(final IAction action) {
        if (guardFail()) {
            return;
        }
        if (!(getSelection() instanceof IStructuredSelection)) {
            return;
        }
        final IStructuredSelection struct = (IStructuredSelection) getSelection();
        final Object o = struct.getFirstElement();
        if (!(o instanceof ITimeCluster)) {
            return;
        }
        final ITimeCluster cluster = (ITimeCluster) o;
        final long min = cluster.getMin();
        final long max = cluster.getMax();
        final IInputValidator validator = new IInputValidator() {
            private Pattern p = Pattern.compile("([0-9]{1,})");

            public String isValid(final String newText) {
                Matcher m = this.p.matcher(newText);
                if (!m.matches()) {
                    return "The input must be numerical";
                }
                long value = Long.parseLong(newText);
                if (value > max || value < min) {
                    return "The input must be within bounds [" + min
                            + GotoAction.BETWEEN_TOKEN + max + ']';
                }
                return null;
            }
        };
        final InputDialog dlg = new InputDialog(null, "Go to: "
                + cluster.getParent().getLabel(null), "Enter time [" + min
                + GotoAction.BETWEEN_TOKEN + max + "]: ", Long.toString(cluster
                .getCurrentTime()), validator);
        if (dlg.open() == Window.OK) {
            final String value = dlg.getValue();
            final long time = Long.parseLong(value);
            cluster.setCurrentTime(time);
        }
    }
}
