package com.odcgroup.mdf.editor.ui.dialogs.mdf.assist;

import org.eclipse.jface.fieldassist.IContentProposal;

class SimpleContentProposal implements IContentProposal {

    private final String content;
    private final String label;
    private final String description;

    public SimpleContentProposal(String content, String label,
            String description) {
        this.content = content;
        this.label = label;
        this.description = description;
    }

    public String getContent() {
        return content;
    }

    public int getCursorPosition() {
        return content.length();
    }

    public String getDescription() {
        return description;
    }

    public String getLabel() {
        return label;
    }

}