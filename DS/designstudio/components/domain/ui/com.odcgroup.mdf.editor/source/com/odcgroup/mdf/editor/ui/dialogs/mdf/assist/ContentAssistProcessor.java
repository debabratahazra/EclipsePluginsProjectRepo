package com.odcgroup.mdf.editor.ui.dialogs.mdf.assist;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.fieldassist.ContentProposalAdapter;
import org.eclipse.jface.fieldassist.IContentProposal;
import org.eclipse.jface.fieldassist.IContentProposalProvider;
import org.eclipse.jface.fieldassist.TextContentAdapter;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.fieldassist.ContentAssistCommandAdapter;

import com.odcgroup.mdf.metamodel.MdfModelElement;


public class ContentAssistProcessor implements IContentProposalProvider {

    private static final char[] AUTO_ACTIVATION_CHARACTERS = ":".toCharArray();

    private final IContentAssistProvider provider;

    public static ContentProposalAdapter install(Text pathText,
            IContentAssistProvider provider) {
        ContentAssistCommandAdapter adapter = new ContentAssistCommandAdapter(
                pathText, new TextContentAdapter(), new ContentAssistProcessor(
                        provider),
                ContentAssistCommandAdapter.CONTENT_PROPOSAL_COMMAND,
                AUTO_ACTIVATION_CHARACTERS, true);
        adapter.setProposalAcceptanceStyle(ContentAssistCommandAdapter.PROPOSAL_REPLACE);
        return adapter;
    }

    /**
     * @param page
     */
    ContentAssistProcessor(IContentAssistProvider provider) {
        this.provider = provider;
    }

    public IContentProposal[] getProposals(String contents, int position) {
        return computeCompletionProposals(contents, position);
    }

    private IContentProposal[] computeCompletionProposals(String text,
            int offset) {
        String pname = text.substring(0, offset);
        List proposals = new ArrayList();
        String defaultDomain = provider.getDefaultDomainName();
        MdfModelElement[] elements = provider.getCandidates();

        // Look first for local names
        for (int i = 0; i < elements.length; i++) {
            MdfModelElement p = elements[i];

            if (defaultDomain.equals(p.getQualifiedName().getDomain())) {
                String name = p.getName();

                if (name.startsWith(pname)) {
                    proposals.add(new SimpleContentProposal(name, name,
                            p.getDocumentation()));
                }
            }
        }

        // Then for imports
        for (int i = 0; i < elements.length; i++) {
            MdfModelElement p = elements[i];

            if (!defaultDomain.equals(p.getQualifiedName().getDomain())) {
                String name = p.getQualifiedName().toString();

                if (name.startsWith(pname)) {
                    proposals.add(new SimpleContentProposal(name, name,
                            p.getDocumentation()));
                }
            }
        }

        return (IContentProposal[]) proposals.toArray(new IContentProposal[proposals.size()]);
    }
}
