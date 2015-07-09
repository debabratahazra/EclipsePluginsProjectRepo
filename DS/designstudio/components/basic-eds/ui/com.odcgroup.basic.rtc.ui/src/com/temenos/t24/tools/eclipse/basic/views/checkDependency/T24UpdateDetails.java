package com.temenos.t24.tools.eclipse.basic.views.checkDependency;

/** This object represents each row in Dependency Details section in CheckDependencyView. */

public class T24UpdateDetails {

    private String updateName;
    private int dependencyCount;
    private String dependencyList;

    T24UpdateDetails(String itemName, int dependencyCount, String reserved1) {
        this.updateName = itemName;
        this.dependencyCount = dependencyCount;
        this.dependencyList = reserved1;
    }

    public String getUpdateName() {
        return updateName;
    }

    public void setUpdateName(String updateName) {
        this.updateName = updateName;
    }

    public int getDependencyCount() {
        return dependencyCount;
    }

    public void setDependencyCount(int dependencyCount) {
        this.dependencyCount = dependencyCount;
    }

    public String getDependencyList() {
        return dependencyList;
    }

    public void setDependencyList(String dependencyList) {
        this.dependencyList = dependencyList;
    }
}
