package com.odcgroup.mdf.editor.ui.editors;

import java.util.Collection;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.UnexecutableCommand;
import org.eclipse.emf.edit.command.CommandParameter;
import org.eclipse.emf.edit.command.CreateChildCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.edit.ui.action.StaticSelectionCommandAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IWorkbenchPart;


/**
 * @author <a href="mailto:cvedovini@odyssey-group.com">Claude Vedovini</a>
 * @version 1.0
 */
public class CreateModelAction extends StaticSelectionCommandAction {
    /** This describes the child to be created. */
    protected Object descriptor;

    /**
     * This constructs an instance of an action that creates a child specified
     * by <code>descriptor</code> for the single object in the
     * <code>selection</code>.
     */
    public CreateModelAction(
        IWorkbenchPart workbenchPart, ISelection selection, Object descriptor) {
        super(workbenchPart);
        this.descriptor = descriptor;
        configureAction(selection);
    }

    public static Command create(
        EditingDomain domain, Object owner, Object newChildDescriptor,
        Collection selection) {
        return domain.createCommand(
            CreateChildCommand.class,
            new CommandParameter(owner, null, newChildDescriptor, selection));
    }

    /**
     * This creates the command for {@link
     * StaticSelectionCommandAction#createActionCommand}.
     */
    protected Command createActionCommand(
        EditingDomain editingDomain, Collection collection) {
        if (collection.size() == 1) {
            Object owner = collection.iterator().next();
            return create(
                editingDomain, owner, descriptor, collection);
        }

        return UnexecutableCommand.INSTANCE;
    }
}
