/*
 * 
 */
package com.zealcore.se.ui.search;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.easymock.classextension.EasyMock;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import util.MockUtil;

import com.zealcore.se.core.SeCorePlugin;
import com.zealcore.se.core.SearchAdapter;
import com.zealcore.se.core.SearchCriteria;
import com.zealcore.se.core.dl.ITypeRegistry;
import com.zealcore.se.core.ifw.TestTypeRegistry;
import com.zealcore.se.core.model.ILogEvent;
import com.zealcore.se.core.model.IType;
import com.zealcore.se.core.model.LogFile;
import com.zealcore.se.core.model.ReflectiveType;
import com.zealcore.se.ui.internal.assertions.IModifyListener;
import com.zealcore.se.ui.internal.assertions.ModifyEvent;
import com.zealcore.se.ui.mock.MockActivity;
import com.zealcore.se.ui.mock.MockTypePackage;

public class SearchFilterInputTest {
    private static final int SHELL_SIZE = 500;

    @Before
    public void setUp() throws Exception {}

    @After
    public void tearDown() throws Exception {}

    @Test(expected = AssertionError.class)
    public void testSetCriteriaValueLogSessionBadAttributeName() {

        final Set<Class<?>> searchableClasses = new HashSet<Class<?>>();
        searchableClasses.add(ILogEvent.class);

        final SearchCriteria criteria = EasyMock
                .createMock(SearchCriteria.class);
        final SearchHelper helper = EasyMock.createMock(SearchHelper.class);

        org.easymock.EasyMock.expect(criteria.getAttributeClassType())
                .andReturn(LogFile.class);
        org.easymock.EasyMock.expect(criteria.getAttributeName()).andReturn(
                "failFail");

		SearchString searchString=new SearchString();
		searchString.setChecked(false);
		searchString.setText1("name1");
		searchString.setText2("name2");
		helper.set(criteria, searchString);

        MockUtil.replay(criteria, helper);

        Assert.fail("Expected assertion error");
    }

    /**
     * The main method for testing the display of the SearchFilterInput.
     * 
     * @param args
     *                the args
     */
    public static void main(final String[] args) {
        final Display display = new Display();

        final Shell shell = new Shell(display);
        shell.setSize(SearchFilterInputTest.SHELL_SIZE,
                SearchFilterInputTest.SHELL_SIZE);

        shell.setLayout(new FillLayout());

        final Composite parent = new Canvas(shell, SWT.NULL
                | SWT.DOUBLE_BUFFERED);

        parent.setBackground(display.getSystemColor(SWT.COLOR_WHITE));

        parent.setLayout(new FillLayout());

        final MockTypePackage tp = new MockTypePackage();
        final TestTypeRegistry mockTypeRegistry = new TestTypeRegistry();
        mockTypeRegistry.addPackage(tp);

        SeCorePlugin.getDefault().registerService(ITypeRegistry.class,
                mockTypeRegistry);

        final Set<IType> typeSet = new HashSet<IType>();
        final Collection<IType> types = tp.getTypes();
        typeSet.addAll(types);
        final SearchFilterInput subject = new SearchFilterInput(
                SearchFilterInput.SEARCHABLE_TYPES);

        final SearchAdapter adapter = SearchAdapter
                .createSearchAdapter(ReflectiveType.valueOf(MockActivity.class));
		adapter.getCritList().get(0).setOperator1("!=");
		adapter.getCritList().get(0).setOperand1(Integer.valueOf(1));		
		adapter.getCritList().get(0).setOperand2(Integer.valueOf(1));

        // subject.createContent(parent, adapter);
        subject.createContents(parent);

        final Button okButton = new Button(parent, SWT.PUSH);
        okButton.setText("Ok");
        okButton.setEnabled(false);

        subject.addModifyListener(new IModifyListener() {
            public void componentModified(final ModifyEvent event) {
                if (event.getErrorMessage() != null) {
                    okButton.setEnabled(false);
                } else {
                    okButton.setEnabled(true);
                }

                System.out.println(event.getErrorMessage());

            }
        });
        // COde goes here

        shell.open();
        while (!shell.isDisposed()) {
            if (!display.readAndDispatch()) {
                display.sleep();
            }
        }
        display.dispose();
    }

}
