package com.odcgroup.mdf.editor.ui.editors.providers;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.UnexecutableCommand;
import org.eclipse.emf.common.notify.impl.AdapterImpl;
import org.eclipse.emf.common.util.ResourceLocator;
import org.eclipse.emf.edit.command.CommandParameter;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.edit.provider.IEditingDomainItemProvider;
import org.eclipse.emf.edit.provider.IItemLabelProvider;
import org.eclipse.emf.edit.provider.IStructuredItemContentProvider;
import org.eclipse.emf.edit.provider.ITreeItemContentProvider;

import com.odcgroup.mdf.ecore.MdfDomainImpl;
import com.odcgroup.mdf.editor.MdfPlugin;


public class DomainItemProvider extends AdapterImpl implements
        IEditingDomainItemProvider, IStructuredItemContentProvider,
        ITreeItemContentProvider, IItemLabelProvider {

    private final MdfDomainImpl domain;

    public DomainItemProvider(MdfDomainImpl parent) {
        super();
        this.domain = parent;
    }

    public Object getParent(Object object) {
        return null;
    }

    public String getText(Object object) {
        return null;
    }

    public Object getImage(Object object) {
        return null;
    }

    public Collection getChildren(Object object) {
// removed the imported domain list as this is now handled automatically
//        return Arrays.asList(new Object[] {
//                new ImportedDomainsListItemProvider(domain), domain });
      return Arrays.asList(new Object[] { domain });
    }

    public Command createCommand(Object object, EditingDomain editingDomain,
            Class<? extends Command> commandClass,
            CommandParameter commandParameter) {
        return UnexecutableCommand.INSTANCE;
    }

    public Collection<?> getNewChildDescriptors(Object object,
            EditingDomain editingDomain, Object sibling) {
        return Collections.emptyList();
    }

    public Collection<?> getElements(Object object) {
        return getChildren(object);
    }

    public boolean hasChildren(Object object) {
        return true;
    }

    /**
     * Return the resource locator for this item provider's resources.
     */
    protected ResourceLocator getResourceLocator() {
        return MdfPlugin.getDefault().getResourceLocator();
    }

}