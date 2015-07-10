package com.zealcore.se.core;

import java.util.List;

import com.zealcore.se.core.model.IObject;
import com.zealcore.se.core.model.IType;

public interface ISearchAdapter extends IFilter<IObject> {

    /**
     * Returns the search criterias.
     * 
     * @return a List of the search criterias in this adapter.
     */
    List<SearchCriteria> getCritList();

    String getName();
    
    IType getSearchType();
}
