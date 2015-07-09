package com.odcgroup.mdf.editor.ui.dialogs.mdf.assist;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.jface.fieldassist.ContentProposalAdapter;
import org.eclipse.jface.fieldassist.IContentProposal;
import org.eclipse.jface.fieldassist.IContentProposalProvider;
import org.eclipse.jface.fieldassist.TextContentAdapter;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.fieldassist.ContentAssistCommandAdapter;

import com.odcgroup.mdf.metamodel.MdfClass;
import com.odcgroup.mdf.metamodel.MdfDataset;
import com.odcgroup.mdf.metamodel.MdfEntity;
import com.odcgroup.mdf.metamodel.MdfProperty;
import com.odcgroup.mdf.utils.ModelHelper;


public class PropertyPathContentAssistProcessor implements
        IContentProposalProvider {

    private static final char[] AUTO_ACTIVATION_CHARACTERS = ".".toCharArray();

    private final MdfDataset rootDataset;
    private final MdfClass rootClass;

    public static ContentProposalAdapter install(Text pathText,
            MdfDataset rootDataset) {
        ContentAssistCommandAdapter adapter = new ContentAssistCommandAdapter(
                pathText, new TextContentAdapter(),
                new PropertyPathContentAssistProcessor(rootDataset),
                ContentAssistCommandAdapter.CONTENT_PROPOSAL_COMMAND,
                AUTO_ACTIVATION_CHARACTERS, true);
        adapter.setProposalAcceptanceStyle(ContentAssistCommandAdapter.PROPOSAL_REPLACE);
        return adapter;
    }

    /**
     * @param page
     */
    PropertyPathContentAssistProcessor(MdfDataset rootDataset) {
        this.rootDataset = rootDataset;
        this.rootClass = rootDataset.getBaseClass();
    }

    public IContentProposal[] getProposals(String contents, int position) {
        return computeCompletionProposals(contents, position);
    }

    private IContentProposal[] computeCompletionProposals(String text,
            int offset) {
        MdfEntity current = rootClass;
        String path = text.substring(0, offset);
        String prefix = "";
        String pname = null;
        
        while (true) {
            if (!(current instanceof MdfClass)) return null;

            int pos = path.indexOf('.');
            prefix = (pname == null) ? "" : prefix + "." + pname;
            
            if (pos > 0) {
                pname = path.substring(0, pos);
                path = path.substring(pos + 1);
            } else {     
                pname = path;
                break;
            }

            MdfProperty p = ((MdfClass) current).getProperty(pname);
            
            if (p == null)
                p = ModelHelper.getReverseAssociation(
                        rootDataset.getParentDomain(), (MdfClass) current,
                        pname);
            if (p == null) return null;
            current = p.getType();
        }

        List proposals = new ArrayList();
        List properties = new ArrayList();
        properties.addAll(((MdfClass) current).getProperties(true));
        properties.addAll(ModelHelper.getReverseAssociations(
                rootDataset.getParentDomain(), (MdfClass) current));
        Iterator it = properties.iterator();

        while (it.hasNext()) {
            MdfProperty p = (MdfProperty) it.next();
            String name = p.getName();
            
            if (name.startsWith(pname)) {
            	String content = null;
            	if (prefix != null && !prefix.trim().equals("")) {
            		if (prefix.startsWith(".")) {
            			prefix = prefix.substring(1);
            		}
            		content = prefix+"."+name;
            	} else {
            		content = name;
            	}
                proposals.add(new SimpleContentProposal(content,
                        name, p.getDocumentation()));
            }
        }

        return (IContentProposal[]) proposals.toArray(new IContentProposal[proposals.size()]);
    }
}
