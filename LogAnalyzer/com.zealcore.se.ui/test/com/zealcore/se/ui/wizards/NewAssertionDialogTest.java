package com.zealcore.se.ui.wizards;

import java.util.HashSet;
import java.util.Set;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.junit.After;
import org.junit.Before;

import com.zealcore.se.core.SeCorePlugin;
import com.zealcore.se.core.dl.ITypeRegistry;
import com.zealcore.se.core.ifw.TestTypeRegistry;
import com.zealcore.se.core.model.IType;
import com.zealcore.se.core.model.ReflectiveType;
import com.zealcore.se.ui.mock.MockActivity;
import com.zealcore.se.ui.mock.MockTypePackage;
import com.zealcore.se.ui.search.SearchFilterInput;

public class NewAssertionDialogTest {

    private static final int SHELL_SIZE = 500;

    @Before
    public void setUp() throws Exception {}

    @After
    public void tearDown() throws Exception {}

    public static void main(final String[] args) {
        final Display display = new Display();

        final Shell shell = new Shell(display);
        shell.setSize(NewAssertionDialogTest.SHELL_SIZE,
                NewAssertionDialogTest.SHELL_SIZE);

        shell.setLayout(new FillLayout());

        final Composite parent = new Canvas(shell, SWT.NULL
                | SWT.DOUBLE_BUFFERED);

        parent.setBackground(display.getSystemColor(SWT.COLOR_WHITE));

        final Set<IType> clazzes = new HashSet<IType>();
        clazzes.add(ReflectiveType.valueOf(MockActivity.class));

        final MockTypePackage tp = new MockTypePackage();
        final TestTypeRegistry mockTypeRegistry = new TestTypeRegistry();
        mockTypeRegistry.addPackage(tp);

        SeCorePlugin.getDefault().registerService(ITypeRegistry.class,
                mockTypeRegistry);

        final SearchFilterInput editor = new SearchFilterInput(
                SearchFilterInput.SEARCHABLE_TYPES);
        final NewAssertionDialog dlg = new NewAssertionDialog(shell, editor);

        dlg.open();
        shell.dispose();
        display.dispose();
    }
}
