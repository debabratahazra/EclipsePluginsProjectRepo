package com.odcgroup.mdf.editor.ui.dialogs.mdf.assist;

import com.odcgroup.mdf.metamodel.MdfModelElement;

public interface IContentAssistProvider {

    public String getDefaultDomainName();
    
    public MdfModelElement[] getCandidates();
}
