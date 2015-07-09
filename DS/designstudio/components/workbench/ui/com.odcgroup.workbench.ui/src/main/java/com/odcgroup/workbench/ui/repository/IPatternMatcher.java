package com.odcgroup.workbench.ui.repository;

public interface IPatternMatcher {

    public void setPattern(String pattern, boolean ignoreCase,
            boolean ignoreWildCards);

    public boolean match(Object element);

}
