package com.zealcore.se.core;

import java.util.Collection;

import com.zealcore.se.core.model.IObject;

public interface IQueryFilter {

    Collection<IFilter<IObject>> getFilter();
    Collection<IFilter<IObject>> getFilterSelections();
    
    void setFilter(Collection<IFilter<IObject>> filter);
}
