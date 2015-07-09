package com.odcgroup.mdf.editor.ui.editors.commands;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.UnexecutableCommand;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.edit.command.ChangeCommand;

import com.odcgroup.mdf.editor.ui.dialogs.EditionSupport;
import com.odcgroup.mdf.editor.ui.dialogs.EditionSupportFactory;
import com.odcgroup.mdf.metamodel.MdfModelElement;
import com.odcgroup.workbench.core.repository.OfsResourceHelper;


public class RemoveOrDeprecateCommand extends ChangeCommand {

    private final Collection collection;
    private final EObject owner;

    protected RemoveOrDeprecateCommand(Collection notifiers,
            EObject owner, Collection collection) {
        super(notifiers);
        this.owner = owner;
        this.collection = collection;
    }

    public static final Command create(Object owner,
            Collection collection) {
        List notifiers = new ArrayList(collection.size() + 1);
        notifiers.addAll(collection);
        notifiers.add(owner);
        Resource resource = ((EObject) owner).eResource();
        if (OfsResourceHelper.isResourceReadOnly(resource)) {
        	return UnexecutableCommand.INSTANCE;
        }
        return new RemoveOrDeprecateCommand(notifiers, (EObject)owner, collection);
    }

    public void doExecute() {
        // DS-1349
        EditionSupport support = EditionSupportFactory.INSTANCE();
        support.setSave(false);
        
        if (collection.size()==1) {
        	support.delete((EObject) collection.iterator().next());
        }

        if (collection.size()>1) {
        	List<MdfModelElement> list = new ArrayList<MdfModelElement>();
        	list.addAll(collection);
        	support.delete(owner, list);
        }
    }
    
}
