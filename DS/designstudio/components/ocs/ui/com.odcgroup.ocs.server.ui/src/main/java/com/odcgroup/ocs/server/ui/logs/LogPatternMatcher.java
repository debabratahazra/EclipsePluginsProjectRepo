package com.odcgroup.ocs.server.ui.logs;

import org.eclipse.jface.text.BadLocationException;
import org.eclipse.swt.program.Program;
import org.eclipse.ui.console.IHyperlink;
import org.eclipse.ui.console.IPatternMatchListenerDelegate;
import org.eclipse.ui.console.PatternMatchEvent;
import org.eclipse.ui.console.TextConsole;

public class LogPatternMatcher implements IPatternMatchListenerDelegate {

	private TextConsole console;
	
	@Override
	public void connect(TextConsole console) {
		this.console = console;
	}

	@Override
	public void disconnect() {
		console = null;
	}

	@Override
	public void matchFound(PatternMatchEvent event) {
        try {
            int offset = event.getOffset();
            int length = event.getLength();
            final String url = console.getDocument().get(offset, length);
            IHyperlink link = new IHyperlink() {
				@Override
				public void linkEntered() {
				}

				@Override
				public void linkExited() {
				}

				@Override
				public void linkActivated() {
					Program.launch(url);
				}
            };
            console.addHyperlink(link, offset, length);
        } catch (BadLocationException e) {
        }

	}

}
