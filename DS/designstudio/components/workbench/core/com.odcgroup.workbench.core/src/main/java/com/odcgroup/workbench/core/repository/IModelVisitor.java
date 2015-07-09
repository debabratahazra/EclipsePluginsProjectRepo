package com.odcgroup.workbench.core.repository;

/**
 * An interface used to create utility classes that allow a client to lookup the
 * model path.
 */
public interface IModelVisitor {

    /**
     * @param model the currently visited model object.
     * @return <code>true</code> if the model can be visited,
     *         <code>false</code> otherwise.
     */
    public boolean enter(Object model);
    
    public void leave(Object model);
    
    public int getScope();

}
