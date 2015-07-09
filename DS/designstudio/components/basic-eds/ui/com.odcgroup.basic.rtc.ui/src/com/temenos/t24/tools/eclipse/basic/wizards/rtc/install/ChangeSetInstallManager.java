package com.temenos.t24.tools.eclipse.basic.wizards.rtc.install;

import java.util.ArrayList;
import java.util.List;

import com.ibm.team.repository.client.ITeamRepository;
import com.ibm.team.repository.client.TeamPlatform;
import com.ibm.team.repository.common.TeamRepositoryException;
import com.ibm.team.workitem.common.model.IWorkItem;
import com.temenos.t24.tools.eclipse.basic.protocols.ftp.RemoteOperationsLog;
import com.temenos.t24.tools.eclipse.basic.remote.data.InstallableItemCollection;
import com.temenos.t24.tools.eclipse.basic.rtc.ChangeSetHandler;
import com.temenos.t24.tools.eclipse.basic.rtc.RepositoryFileContentHandler;
import com.temenos.t24.tools.eclipse.basic.rtc.RepositoryManager;
import com.temenos.t24.tools.eclipse.basic.rtc.WorkItemHandler;

public class ChangeSetInstallManager {

    private String repositoryUri;
    private int workItemRef;
    private IWorkItem workItem;
    private ITeamRepository repository;
    private WorkItemHandler workItemHandler;

    public ChangeSetInstallManager(String repositoryUri, int workItemRef) {
        this.repositoryUri = repositoryUri;
        this.workItemRef = workItemRef;
        initHandlers();
    }

    public static ITeamRepository[] getRepositories() {
        List<ITeamRepository> teamRepositoryList = RepositoryManager.getInstance().getRepositories();
        List<ITeamRepository> loggedRepositoryList = new ArrayList<ITeamRepository>();
        for (ITeamRepository repository : teamRepositoryList) {
            if (repository.loggedIn()) {
                loggedRepositoryList.add(repository);
            }
        }
        ITeamRepository[] repositories = new ITeamRepository[loggedRepositoryList.size()];
        loggedRepositoryList.toArray(repositories);
        return repositories;
    }

    public List<T24ChangeSet> getDirectChangeSets() {
        if (workItem == null) {
            return new ArrayList<T24ChangeSet>();
        }
        ChangeSetHandler changeSetHandler = new ChangeSetHandler(repository, workItem);
        List<T24ChangeSet> changeSets = new ArrayList<T24ChangeSet>();
        try {
            changeSets = changeSetHandler.getChangedItems();
        } catch (TeamRepositoryException e) {
            RemoteOperationsLog.error("Unable to retrieve Change Sets. " + e.getMessage());
        }
        return changeSets;
    }

    public List<T24ChangeSet> getAllChangeSets() {
        if (workItem == null) {
            return new ArrayList<T24ChangeSet>();
        }
        List<IWorkItem> allWorkItems = new ArrayList<IWorkItem>();
        allWorkItems.add(workItem);
        allWorkItems.addAll(workItemHandler.getAllChildren(workItem));
        List<T24ChangeSet> allChangeSets = new ArrayList<T24ChangeSet>();
        ChangeSetHandler changeSetHandler = null;
        for (IWorkItem child : allWorkItems) {
            changeSetHandler = new ChangeSetHandler(repository, child);
            try {
                List<T24ChangeSet> changeSets = changeSetHandler.getChangedItems();
                allChangeSets.addAll(changeSets);
            } catch (TeamRepositoryException e) {
                RemoteOperationsLog.error("Unable to retrieve Change Sets. " + e.getMessage());
                return allChangeSets;
            }
        }
        return allChangeSets;
    }

    public InstallableItemCollection retriveItems(List<T24ChangeSet> changeSets) {
        RepositoryFileContentHandler fileHandler = new RepositoryFileContentHandler(repository);
        InstallableItemCollection itemCollection = fileHandler.retrieveContent(changeSets);
        return itemCollection;
    }

    private void initHandlers() {
        repository = TeamPlatform.getTeamRepositoryService().getTeamRepository(repositoryUri);
        workItemHandler = new WorkItemHandler(repository);
        workItem = workItemHandler.getWorkItem(workItemRef);
    }
}
