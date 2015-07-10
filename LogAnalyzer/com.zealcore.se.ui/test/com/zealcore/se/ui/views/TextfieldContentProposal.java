package com.zealcore.se.ui.views;

import org.eclipse.jface.bindings.keys.KeyStroke;
import org.eclipse.jface.bindings.keys.ParseException;
import org.eclipse.jface.fieldassist.ContentProposalAdapter;
import org.eclipse.jface.fieldassist.SimpleContentProposalProvider;
import org.eclipse.jface.fieldassist.TextContentAdapter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

public final class TextfieldContentProposal {

    private TextfieldContentProposal() {

    }

    private static final int SHELL_SIZE = 500;

    public static void main(final String[] args) throws ParseException {
        final Display display = new Display();

        final Shell shell = new Shell(display);
        shell.setSize(TextfieldContentProposal.SHELL_SIZE,
                TextfieldContentProposal.SHELL_SIZE);

        shell.setLayout(new FillLayout());

        final Composite parent = new Composite(shell, SWT.NULL
                | SWT.DOUBLE_BUFFERED);

        parent.setLayout(new FillLayout());

        final Text widget = new Text(parent, SWT.BORDER);
        widget.setText("");

        final ContentProposalAdapter proposals = new ContentProposalAdapter(
                widget, new TextContentAdapter(),
                new SimpleContentProposalProvider(new String[] { "{foobar}" }),
                KeyStroke.getInstance("ctrl+space"), new char[] { '$' });
        System.out.println(proposals);

        shell.open();
        while (!shell.isDisposed()) {
            if (!display.readAndDispatch()) {
                display.sleep();
            }
        }
        display.dispose();
    }

}
