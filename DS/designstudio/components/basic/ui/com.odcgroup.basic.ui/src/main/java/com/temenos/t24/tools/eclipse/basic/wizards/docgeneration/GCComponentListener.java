package com.temenos.t24.tools.eclipse.basic.wizards.docgeneration;

import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.List;

/**
 * listener class for listening the components in Generate Comments wizard
 * 
 * @author sbharathraja
 * 
 */
class GCComponentListener {

    /** wizard page */
    private GenerateCommentsWizardPage page;
    /** list holds the available projects **/
    private List listAvailableProjects;
    /** list display the selected projects */
    private List listSelectedProjects;
    /** add button */
    private Button buttonAdd;
    /** button add all */
    private Button buttonAddAll;
    /** remove button */
    private Button buttonRemove;

    /**
     * constructor of listener
     * 
     * @param page - instance of {@link GenerateCommentsWizardPage}
     * @param listAvailableProjects
     * @param listSelectedProjects
     * 
     */
    GCComponentListener(GenerateCommentsWizardPage page, List listAvailableProjects, List listSelectedProjects) {
        this.page = page;
        this.listAvailableProjects = listAvailableProjects;
        this.listSelectedProjects = listSelectedProjects;
    }

    /**
     * listening the add button
     * 
     * @param buttonAdd
     */
    protected void listenAddButton(Button buttonAdd) {
        this.buttonAdd = buttonAdd;
        buttonAdd.addSelectionListener(new SelectionListener() {

            public void widgetDefaultSelected(SelectionEvent defaultSelectedEvent) {
            }

            public void widgetSelected(SelectionEvent event) {
                String[] selectFromAvailableList = listAvailableProjects.getSelection();
                if (selectFromAvailableList.length > 0) {
                    for (String item : selectFromAvailableList) {
                        if (!isItemAlreadyAdded(item))
                            listSelectedProjects.add(item);
                    }
                }
                page.setPageComplete(page.isPageComplete());
            }
        });
    }

    /**
     * listening the add all button
     * 
     * @param buttonAddAll
     */
    protected void listenAddAllButton(final Button buttonAddAll) {
        this.buttonAddAll = buttonAddAll;
        buttonAddAll.addSelectionListener(new SelectionListener() {

            public void widgetDefaultSelected(SelectionEvent defaultSelectedEvent) {
            }

            public void widgetSelected(SelectionEvent event) {
                // since the add all button add all available projects
                // into selected project, add button is unnecessary at that
                // time, even add all button too
                buttonAdd.setEnabled(false);
                buttonAddAll.setEnabled(false);
                String[] allAvailableProjects = listAvailableProjects.getItems();
                for (String item : allAvailableProjects) {
                    if (!isItemAlreadyAdded(item))
                        listSelectedProjects.add(item);
                }
                page.setPageComplete(page.isPageComplete());
            }
        });
    }

    /**
     * listening remove button
     * 
     * @param buttonRemove
     */
    protected void listenRemoveButton(final Button buttonRemove) {
        this.buttonRemove = buttonRemove;
        buttonRemove.addSelectionListener(new SelectionListener() {

            public void widgetDefaultSelected(SelectionEvent defaultSelectedEvent) {
            }

            public void widgetSelected(SelectionEvent event) {
                String[] selectedProject = listSelectedProjects.getSelection();
                for (String item : selectedProject)
                    listSelectedProjects.remove(item);
                // enable add button and disable the remove button
                if (listSelectedProjects.getItemCount() == 0) {
                    buttonAdd.setEnabled(true);
                    buttonAddAll.setEnabled(true);
                    buttonRemove.setEnabled(false);
                }
                page.setPageComplete(page.isPageComplete());
            }
        });
    }

    /**
     * listen the selected project's list
     */
    protected void listenSelectedList() {
        listSelectedProjects.addSelectionListener(new SelectionListener() {

            public void widgetDefaultSelected(SelectionEvent defaultSelectedEvent) {
            }

            public void widgetSelected(SelectionEvent event) {
                if (listSelectedProjects.getItemCount() > 0)
                    buttonRemove.setEnabled(true);
                else
                    buttonRemove.setEnabled(false);
            }
        });
    }

    /**
     * checks the selected item already in selected project list or not.
     * 
     * @param selectedItem - user selected item
     * @return true if already there, false otherwise
     */
    private boolean isItemAlreadyAdded(String selectedItem) {
        for (String presentedItem : listSelectedProjects.getItems()) {
            if (presentedItem.equalsIgnoreCase(selectedItem))
                return true;
        }
        return false;
    }
}
