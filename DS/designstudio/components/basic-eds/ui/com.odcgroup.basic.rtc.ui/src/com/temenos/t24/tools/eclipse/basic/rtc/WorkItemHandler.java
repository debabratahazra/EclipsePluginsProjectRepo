package com.temenos.t24.tools.eclipse.basic.rtc;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.eclipse.core.runtime.IProgressMonitor;

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
import com.ibm.team.workitem.client.IWorkItemClient;
import com.ibm.team.workitem.common.model.IWorkItem;
import com.temenos.t24.tools.eclipse.basic.protocols.ftp.RemoteOperationsLog;

public class WorkItemHandler {

    private ITeamRepository teamRepository;
    private IProgressMonitor monitor;
    private ILinkManager linkManager;
    private IReferenceFactory referenceFactory;

    public WorkItemHandler(ITeamRepository teamRepository) {
        this.teamRepository = teamRepository;
        monitor = new SysoutProgressMonitor();
        linkManager = (ILinkManager) teamRepository.getClientLibrary(ILinkManager.class);
        referenceFactory = linkManager.referenceFactory();
    }

    public IWorkItem getWorkItem(int workItemRef) {
        IWorkItemClient workItemClient = (IWorkItemClient) teamRepository.getClientLibrary(IWorkItemClient.class);
        try {
            IWorkItem workItem = workItemClient.findWorkItemById(workItemRef, IWorkItem.FULL_PROFILE, monitor);
            return workItem;
        } catch (TeamRepositoryException e) {
            RemoteOperationsLog.error("Unable to resolve Work item " + workItemRef + ". " + e.getMessage());
            return null;
        }
    }

    public List<IWorkItem> getAllChildren(IWorkItem parentWorkItem) {
        List<IWorkItem> children = new ArrayList<IWorkItem>();
        Collection<ILink> links = null;
        try {
            links = getWorkItemLinks(parentWorkItem);
        } catch (TeamRepositoryException e) {
            RemoteOperationsLog.error("Unable to resolve child Work items of " + parentWorkItem.getId() + ". " + e.getMessage());
            return children;
        }
        Iterator<ILink> iter = links.iterator();
        ILink link = null;
        IWorkItem workItem = null;
        while (iter.hasNext()) {
            link = iter.next();
            try {
                workItem = getWorkItem(link);
            } catch (TeamRepositoryException e) {
                RemoteOperationsLog.error("Unable to resolve a child Work item of " + parentWorkItem.getId() + ". "
                        + e.getMessage());
            }
            if (workItem instanceof IWorkItem) {
                children.add(workItem);
                List<IWorkItem> children2 = getAllChildren(workItem);
                children.addAll(children2);
            }
        }
        return children;
    }

    @SuppressWarnings("unchecked")
	private Collection<ILink> getWorkItemLinks(IWorkItem workItem) throws TeamRepositoryException {
        IReference reference = referenceFactory.createReferenceToItem(workItem.getItemHandle());
        ILinkQueryPage page;
        page = linkManager.findLinksByTarget("com.ibm.team.workitem.linktype.parentworkitem", reference, monitor);
        ILinkCollection linkCollection = page.getLinks();
        return linkCollection.getLinksById("com.ibm.team.workitem.linktype.parentworkitem");
    }

    public List<Object> getAllWorkItems() {
        throw new UnsupportedOperationException("Not available yet!");
    }

    public List<Object> getAllWorkItemsByOwner(String ownerName) {
        throw new UnsupportedOperationException("Not available yet!");
    }

    public List<Object> getAllWorkItemsByType(String type) {
        throw new UnsupportedOperationException("Not available yet!");
    }

    private IWorkItem getWorkItem(ILink link) throws TeamRepositoryException {
        Object source = link.getSourceRef().resolve();
        if (source instanceof IItemHandle) {
            IItemHandle wih = (IItemHandle) source;
            final IWorkItem wi = (IWorkItem) teamRepository.itemManager().fetchCompleteItem(wih, IItemManager.REFRESH, monitor);
            return wi;
        }
        return null;
    }
    // TODO: Only as part of a POC.
    // private class QueryHandler {
    //
    // private IQueryableAttributeFactory factory =
    // QueryableAttributes.getFactory(IWorkItem.ITEM_TYPE);
    //
    // public IQueryResult<IResolvedResult<IWorkItem>> queryByOwner(String
    // owner) {
    // IQueryResult<IResolvedResult<IWorkItem>> results = null;
    // try {
    // IQueryableAttribute projectAreaAttribute =
    // factory.findAttribute(projectArea, IWorkItem.PROJECT_AREA_PROPERTY,
    // getAuditableClient(), monitor);
    // IQueryableAttribute ownerAttribute = factory.findAttribute(projectArea,
    // IWorkItem.OWNER_PROPERTY,
    // getAuditableClient(), monitor);
    // AttributeExpression projectAreaExpression = new
    // AttributeExpression(projectAreaAttribute,
    // AttributeOperation.EQUALS, projectArea);
    // AttributeExpression ownerExpression = new
    // AttributeExpression(ownerAttribute, AttributeOperation.EQUALS,
    // getOwner(owner));
    // Term userRefInProjectTerm = new Term(Operator.AND);
    // userRefInProjectTerm.add(projectAreaExpression);
    // userRefInProjectTerm.add(ownerExpression);
    // IQueryClient queryClient = (IQueryClient)
    // teamRepository.getClientLibrary(IQueryClient.class);
    // results = queryClient.getResolvedExpressionResults(projectArea,
    // userRefInProjectTerm, IWorkItem.FULL_PROFILE);
    // } catch (TeamRepositoryException e) {
    // monitor.subTask("Unable to query by owner. " + e.getMessage());
    // }
    // return results;
    // }
    //        
    // private IAuditableClient getAuditableClient() {
    // return (IAuditableClient) teamRepository
    // .getClientLibrary(IAuditableClient.class);
    // }
    //        
    // }
}
