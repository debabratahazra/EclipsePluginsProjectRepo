package com.odcgroup.workbench.ui.repository;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryLabelProvider;
import org.eclipse.jface.contentassist.IContentAssistSubjectControl;
import org.eclipse.jface.contentassist.ISubjectControlContentAssistProcessor;
import org.eclipse.jface.contentassist.SubjectControlContentAssistant;
import org.eclipse.jface.contentassist.SubjectControlContextInformationValidator;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.ITextViewer;
import org.eclipse.jface.text.contentassist.CompletionProposal;
import org.eclipse.jface.text.contentassist.ICompletionProposal;
import org.eclipse.jface.text.contentassist.IContextInformation;
import org.eclipse.jface.text.contentassist.IContextInformationValidator;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.contentassist.ContentAssistHandler;


public class ContentAssistProcessor implements
        ISubjectControlContentAssistProcessor {

    public static void install(Text pathText, Object[] elements,
            AdapterFactory adapterFactory) {
        SubjectControlContentAssistant assistant = new SubjectControlContentAssistant();
        assistant.enableAutoActivation(true);
        assistant.setShowEmptyList(true);
        assistant.setAutoActivationDelay(500);
        assistant.setContentAssistProcessor(new ContentAssistProcessor(
                elements, adapterFactory), IDocument.DEFAULT_CONTENT_TYPE);
        ContentAssistHandler.createHandlerForText(pathText, assistant);
    }

    private final Object[] elements;
    private final ILabelProvider labelProvider;

    ContentAssistProcessor(Object[] elements, AdapterFactory adapterFactory) {
        this.elements = elements;
        this.labelProvider = new AdapterFactoryLabelProvider(adapterFactory);
    }

    public ICompletionProposal[] computeCompletionProposals(
            IContentAssistSubjectControl control, int offset) {
        Text text = (Text) control.getControl();
        return computeCompletionProposals(text.getText(), offset);
    }

    public IContextInformation[] computeContextInformation(
            IContentAssistSubjectControl control, int offset) {
        return null;
    }

    public ICompletionProposal[] computeCompletionProposals(ITextViewer viewer,
            int offset) {
        IDocument document = viewer.getDocument();
        return computeCompletionProposals(document.get(), offset);
    }

    public IContextInformation[] computeContextInformation(ITextViewer viewer,
            int offset) {
        return null;
    }

    public char[] getCompletionProposalAutoActivationCharacters() {
        return null;
    }

    public char[] getContextInformationAutoActivationCharacters() {
        return null;
    }

    public IContextInformationValidator getContextInformationValidator() {
        return new SubjectControlContextInformationValidator(this);
    }

    public String getErrorMessage() {
        return null;
    }

    private ICompletionProposal[] computeCompletionProposals(String text,
            int offset) {
        String pname = text.substring(0, offset);
        List proposals = new ArrayList();

        // Look first for local names
        for (int i = 0; i < elements.length; i++) {
            String name = labelProvider.getText(elements[i]);

            if (name.startsWith(pname)) {
                proposals.add(new CompletionProposal(name, 0, text.length(),
                        name.length(), labelProvider.getImage(elements[i]),
                        name, null, name));
            }
        }

        return (ICompletionProposal[]) proposals.toArray(new ICompletionProposal[proposals.size()]);
    }
}
