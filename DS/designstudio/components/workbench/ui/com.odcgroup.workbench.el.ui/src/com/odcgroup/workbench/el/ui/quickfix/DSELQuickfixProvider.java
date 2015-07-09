
package com.odcgroup.workbench.el.ui.quickfix;

import java.util.List;

import org.eclipse.xtext.ui.editor.quickfix.DefaultQuickfixProvider;
import org.eclipse.xtext.ui.editor.quickfix.IssueResolution;
import org.eclipse.xtext.validation.Issue;

import com.google.common.collect.Lists;

public class DSELQuickfixProvider extends DefaultQuickfixProvider {

//	@Fix(MyJavaValidator.INVALID_NAME)
//	public void capitalizeName(final Issue issue, IssueResolutionAcceptor acceptor) {
//		acceptor.accept(issue, "Capitalize name", "Capitalize the name.", "upcase.png", new IModification() {
//			public void apply(IModificationContext context) throws BadLocationException {
//				IXtextDocument xtextDocument = context.getXtextDocument();
//				String firstLetter = xtextDocument.get(issue.getOffset(), 1);
//				xtextDocument.replace(issue.getOffset(), 1, firstLetter.toUpperCase());
//			}
//		});
//	}

	@Override
	public List<IssueResolution> getResolutions(Issue issue) {
		return Lists.newArrayList();
	}

}
