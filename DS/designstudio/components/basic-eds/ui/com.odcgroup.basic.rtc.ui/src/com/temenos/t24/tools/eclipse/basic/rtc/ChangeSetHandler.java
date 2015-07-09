package com.temenos.t24.tools.eclipse.basic.rtc;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.eclipse.core.runtime.IProgressMonitor;

import com.ibm.team.filesystem.common.IFileContent;
import com.ibm.team.filesystem.common.IFileItem;
import com.ibm.team.filesystem.common.IFileItemHandle;
import com.ibm.team.links.client.ILinkManager;
import com.ibm.team.links.common.ILink;
import com.ibm.team.links.common.ILinkCollection;
import com.ibm.team.links.common.ILinkQueryPage;
import com.ibm.team.links.common.IReference;
import com.ibm.team.links.common.factory.IReferenceFactory;
import com.ibm.team.repository.client.IItemManager;
import com.ibm.team.repository.client.ITeamRepository;
import com.ibm.team.repository.common.IItemHandle;
import com.ibm.team.repository.common.TeamRepositoryException;
import com.ibm.team.scm.client.IWorkspaceManager;
import com.ibm.team.scm.client.SCMPlatform;
import com.ibm.team.scm.common.IChange;
import com.ibm.team.scm.common.IChangeSet;
import com.ibm.team.workitem.common.model.IWorkItem;
import com.temenos.t24.tools.eclipse.basic.wizards.rtc.install.T24ChangeSet;

public class ChangeSetHandler {

    private ITeamRepository repository;
    private IWorkItem workItem;
    private IProgressMonitor monitor = new SysoutProgressMonitor();
    private IWorkspaceManager workSpaceManager;

    public ChangeSetHandler(ITeamRepository repository, IWorkItem workItem) {
        this.repository = repository;
        this.workItem = workItem;
        getWorkSpaceRepostoryManager();
    }

    @SuppressWarnings("unchecked")
	public List<IChangeSet> getChangeSets() throws TeamRepositoryException {
        List<IChangeSet> changeSets = new ArrayList<IChangeSet>();
        ILinkManager fLinkManager = (ILinkManager) repository.getClientLibrary(ILinkManager.class);
        IReferenceFactory fReferenceFactory = fLinkManager.referenceFactory();
        IReference reference = fReferenceFactory.createReferenceToItem(workItem.getItemHandle());
        ILinkQueryPage page;
        page = fLinkManager.findLinksByTarget("com.ibm.team.filesystem.workitems.change_set", reference, monitor);
        ILinkCollection linkCollection = page.getLinks();
        Collection<ILink> links = linkCollection.getLinksById("com.ibm.team.filesystem.workitems.change_set");
        Iterator<ILink> iter = links.iterator();
        while (iter.hasNext()) {
            ILink link = iter.next();
            IChangeSet changeSet = getChangeSet(link);
            if (changeSet != null) {
                changeSets.add(changeSet);
            }
        }
        return changeSets;
    }

    @SuppressWarnings("unchecked")
	public List<T24ChangeSet> getChangedItems() throws TeamRepositoryException {
        List<IChangeSet> changeSets = getChangeSets();
        List<T24ChangeSet> t24Changes = new ArrayList<T24ChangeSet>();
        int csCounter = 0;
        String csName = "ChangeSet";
        T24ChangeSet t24ChangeSet = null;
        T24SourceItem t24SourceItem = null;
        for (IChangeSet changeSet : changeSets) {
            csCounter++;
            List<IChange> changeItems = changeSet.changes();
            t24ChangeSet = new T24ChangeSet(csName + csCounter, workItem.getId());
            for (IChange change : changeItems) {
                t24SourceItem = getT24SourceItem(change);
                if (!(t24SourceItem instanceof T24SourceItem)) {
                    continue;
                }
                t24ChangeSet.addSourceItem(t24SourceItem);
            }
            t24Changes.add(t24ChangeSet);
        }
        return t24Changes;
    }

    private IChangeSet getChangeSet(ILink link) {
        Object source = link.getSourceRef().resolve();
        if (source instanceof IItemHandle) {
            IItemHandle csh = (IItemHandle) source;
            try {
                final IChangeSet cs = (IChangeSet) repository.itemManager().fetchCompleteItem(csh, IItemManager.REFRESH, monitor);
                return cs;
            } catch (TeamRepositoryException e) {
                return null;
            }
        }
        return null;
    }

    private T24SourceItem getT24SourceItem(IChange change) {
        if (change.afterState() instanceof IFileItemHandle) {
            IFileItemHandle fileItemHandle = (IFileItemHandle) change.afterState();
            IFileItem fileItem;
            try {
                fileItem = (IFileItem) workSpaceManager.versionableManager().fetchCompleteState(fileItemHandle, monitor);
                IFileContent fileContent = (IFileContent) fileItem.getContent();
                return (new T24SourceItem(fileItem.getName(), fileItemHandle, fileContent));
            } catch (TeamRepositoryException e) {
                return null;
            }
        }
        return null;
    }

    private void getWorkSpaceRepostoryManager() {
        workSpaceManager = SCMPlatform.getWorkspaceManager(repository);
    }
}
