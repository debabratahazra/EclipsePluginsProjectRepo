/**
 * 
 */
package com.zealcore.se.core.ifw;

import java.util.ArrayList;
import java.util.List;

import org.easymock.EasyMock;
import org.eclipse.ui.IMemento;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.zealcore.se.core.IChangeListener;
import com.zealcore.se.core.dl.ITypeRegistry;
import com.zealcore.se.core.ifw.assertions.IAssertion;
import com.zealcore.se.core.ifw.assertions.IAssertionRegistry;
import com.zealcore.se.core.ifw.assertions.IAssertionSet;
import com.zealcore.se.core.ifw.assertions.IAssertionSetResult;
import com.zealcore.se.core.model.IObject;
import com.zealcore.se.core.services.IMemento2;

/**
 * @author cafa
 * 
 */
public class ImporterExporterTest {
  

    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {

    }

    /**
     * @throws java.lang.Exception
     */
    @After
    public void tearDown() throws Exception {}

    /**
     * Test method for
     * {@link com.zealcore.se.core.ifw.ImporterExporter#exportAssertions(org.eclipse.ui.IMemento, com.zealcore.se.core.ifw.assertions.IAssertionRegistry)}.
     */
    @Test
    public void testAssertionExport() {

        final IAssertionRegistry registry = EasyMock
                .createMock(IAssertionRegistry.class);
        final IAssertionSet setOne = EasyMock.createMock(IAssertionSet.class);
        final IAssertionSet setTwo = EasyMock.createMock(IAssertionSet.class);

        final IMemento2 rootNode = EasyMock.createMock(IMemento2.class);
        final IMemento memOne = EasyMock.createMock(IMemento.class);
        final IMemento memTwo = EasyMock.createMock(IMemento.class);

        final List<IAssertionSet> sets = new ArrayList<IAssertionSet>();
        sets.add(setOne);
        sets.add(setTwo);

        EasyMock.expect(registry.getAssertionSets()).andReturn(sets);

        EasyMock.expect(
                rootNode.createChild(ImporterExporter.ASSERTION_SET_NODE))
                .andReturn(memOne);
        setOne.saveSate(memOne);
        memOne.putString(ImporterExporter.IMPL_NODE, setOne.getClass()
                .getName());

        EasyMock.expect(
                rootNode.createChild(ImporterExporter.ASSERTION_SET_NODE))
                .andReturn(memTwo);
        setTwo.saveSate(memTwo);
        memTwo.putString(ImporterExporter.IMPL_NODE, setTwo.getClass()
                .getName());

        // Teaching of Mocks complete - begin actual code
        EasyMock.replay(registry, setOne, setTwo, rootNode, memOne, memTwo);

        ImporterExporter.exportAssertions(rootNode, registry);

        // Actual code done - verify that all methods have behaved as taught
        EasyMock.verify(registry, setOne, setTwo, rootNode, memOne, memTwo);
    }

    @Test
    public void testAssertionImport() {
        final IMemento2 rootNode = EasyMock.createMock(IMemento2.class);

        final IAssertionRegistry importRegistry = EasyMock
                .createMock(IAssertionRegistry.class);
        final ITypeRegistry typeRegistry = EasyMock
                .createMock(ITypeRegistry.class);

        final IMemento memOne = EasyMock.createMock(IMemento.class);
        final IMemento memTwo = EasyMock.createMock(IMemento.class);
        final IMemento[] mementos = new IMemento[] { memOne, memTwo };

        EasyMock.expect(
                rootNode.getChild(ImporterExporter.ASSERTION_REGISTRY_NODE))
                .andReturn(rootNode);
        EasyMock.expect(
                rootNode.getChildren(ImporterExporter.ASSERTION_SET_NODE))
                .andReturn(mementos);
        EasyMock.expect(
                rootNode.getChildren(ImporterExporter.ASSERTION_SET_NODE))
                .andReturn(mementos);
        EasyMock.expect(memOne.getString(ImporterExporter.IMPL_NODE))
                .andReturn(MyImplAssertions.class.getName());
        EasyMock.expect(memTwo.getString(ImporterExporter.IMPL_NODE))
                .andReturn(MyImplAssertions.class.getName());

        final IAssertionSet setOne = EasyMock.createMock(IAssertionSet.class);
        final IAssertionSet setTwo = EasyMock.createMock(IAssertionSet.class);

        setOne.init(memOne, typeRegistry);
        setTwo.init(memTwo, typeRegistry);

        importRegistry.addAssertionSet(EasyMock.isA(MyImplAssertions.class));
        importRegistry.addAssertionSet(EasyMock.isA(MyImplAssertions.class));

        MyImplAssertions.mocksDelegate.add(setOne);
        MyImplAssertions.mocksDelegate.add(setTwo);

        EasyMock.replay(rootNode, importRegistry, typeRegistry, memOne, memTwo,
                setOne, setTwo);

        ImporterExporter.importAssertions(rootNode, importRegistry,
                typeRegistry);

        EasyMock.verify(rootNode, importRegistry, typeRegistry, memOne, memTwo,
                setOne, setTwo);
    }

    public static final class MyImplAssertions extends AbstractQuery implements IAssertionSet {

        private static int index;

        private final int myindex;

        private static List<IAssertionSet> mocksDelegate = new ArrayList<IAssertionSet>();

        private static List<MyImplAssertions> implList = new ArrayList<MyImplAssertions>();

        public MyImplAssertions() {
            this.myindex = MyImplAssertions.index;
            MyImplAssertions.index++;
            MyImplAssertions.implList.add(this);
        }

        public void addAssertion(final IAssertion assertion) {
            MyImplAssertions.mocksDelegate.get(this.myindex).addAssertion(
                    assertion);
        }

        public void addChangeListener(final IChangeListener listener) {
            MyImplAssertions.mocksDelegate.get(this.myindex).addChangeListener(
                    listener);
        }

        public Iterable<IAssertion> getAssertions() {
            return getDelegate().getAssertions();
        }

        private IAssertionSet getDelegate() {
            return MyImplAssertions.mocksDelegate.get(this.myindex);
        }

        public String getDescription() {
            return getDelegate().getDescription();
        }

        public String getName() {
            return null;
        }

        public void init(final IMemento memento, final ITypeRegistry typeService) {
            getDelegate().init(memento, typeService);
        }

        public void removeAssertion(final IAssertion assertion) {

        }

        public void removeChangeListener(final IChangeListener listener) {

        }

        public void saveSate(final IMemento memento) {

        }

        public boolean visitBegin(final Reason r) {
            throw new UnsupportedOperationException();
        }

        public void visitEnd(final boolean e) {
            throw new UnsupportedOperationException();
        }

        public boolean visit(final IObject abstractLogFileItem) {
            throw new UnsupportedOperationException();
        }

        public IAssertionSetResult getResult() {
            throw new UnsupportedOperationException();
        }

        public void initialize(final IDataSource data) {
        }

		public int getHitCount() {
			return 0;
		}

		public void resetHitCount() {
		}

		public void setSaveResults(boolean save) {
		}
    }

    @Test
    public void testImportSearches() {

    }
}
