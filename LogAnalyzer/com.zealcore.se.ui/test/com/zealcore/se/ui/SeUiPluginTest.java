package com.zealcore.se.ui;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.runtime.IAdapterManager;
import org.junit.Ignore;
import org.junit.Test;

import util.MockUtil;

import com.zealcore.se.core.SeCorePlugin;
import com.zealcore.se.core.dl.ITypeRegistry;
import com.zealcore.se.core.ifw.assertions.Assertion;
import com.zealcore.se.core.ifw.assertions.IAssertionRegistry;
import com.zealcore.se.core.ifw.assertions.IAssertionResult;
import com.zealcore.se.core.ifw.assertions.IAssertionSetResult;
import com.zealcore.se.core.model.IObject;
import com.zealcore.se.core.model.SEProperty;
import com.zealcore.se.ui.core.CaseFileManager;
import com.zealcore.se.ui.internal.assertions.EditableAssertionAdapter;
import com.zealcore.se.ui.util.PropertySourceAdapterFactory;

public class SeUiPluginTest {
    @Ignore
    @Test
    public void testRegisterAdapters() {

        final IAdapterManager manager = MockUtil.newMock(IAdapterManager.class);

        manager.registerAdapters(CaseFileManager.getInstance(), IFile.class);
        // The new Directory CaseFile can can be adapted from IFolders
        manager.registerAdapters(CaseFileManager.getInstance(), IFolder.class);
        // ICaseFiles are not adapted to property sources, aparantly
        // manager.registerAdapters(PropertySourceAdapterFactory.getInstance(),
        // ICaseFile.class);
        manager.registerAdapters(PropertySourceAdapterFactory.getInstance(),
                ITimeCluster.class);
        manager.registerAdapters(PropertySourceAdapterFactory.getInstance(),
                IObject.class);
        manager.registerAdapters(PropertySourceAdapterFactory.getInstance(),
                SEProperty.class);

        manager.registerAdapters(PropertySourceAdapterFactory.getInstance(),
                IAssertionSetResult.class);

        manager.registerAdapters(org.easymock.EasyMock
                .isA(EditableAssertionAdapter.class), Assertion.class);

        manager.registerAdapters(PropertySourceAdapterFactory.getInstance(),
                IAssertionResult.class);

        final SeCorePlugin plugin = new SeCorePlugin();

        plugin.registerService(ITypeRegistry.class, MockUtil
                .niceMock(ITypeRegistry.class));

        plugin.registerService(IAssertionRegistry.class, MockUtil
                .niceMock(IAssertionRegistry.class));

        MockUtil.replayAll();
        // SeCorePlugin mustInitThisFirst =

        final SeUiPlugin subject = new SeUiPlugin();
        subject.registerAdapters(manager);

        MockUtil.verifyAll();
    }
}
