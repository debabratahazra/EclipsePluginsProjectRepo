package com.zealcore.se.ui.internal;

import java.util.Collections;
import java.util.List;

import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.IFigure;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Display;

import com.zealcore.se.core.SeCorePlugin;
import com.zealcore.se.core.model.IObject;
import com.zealcore.se.core.services.IAsserionReportListener;
import com.zealcore.se.core.services.IAssertionReport;
import com.zealcore.se.core.services.IAssertionReportEvent;
import com.zealcore.se.core.services.IAssertionReportService;
import com.zealcore.se.core.services.IServiceProvider;
import com.zealcore.se.ui.editors.ILogBrowserContribution;
import com.zealcore.se.ui.editors.ILogsetBrowser;
import com.zealcore.se.ui.editors.IRuler;
import com.zealcore.se.ui.graphics.figures.ObjectFigure;

public class AssertionGanttContribution implements ILogBrowserContribution,
        IAsserionReportListener {

    private IAssertionReport report;

    public void dispose() {

    }

    public void init(final ILogsetBrowser view) {
        final IServiceProvider services = SeCorePlugin.getDefault();
        if (services != null) {
            final IAssertionReportService reportService = services
                    .getService(IAssertionReportService.class);
            reportService.addAssertionReportListener(this);
        }
    }

    public void decorateFigures(final Graphics graphics, final IFigure figure) {
        if (report == null || graphics == null || figure == null) {
            return;
        }
        final Color oldForeground = graphics.getForegroundColor();
        final Color red = Display.getDefault().getSystemColor(SWT.COLOR_RED);
        graphics.setForegroundColor(red);

        for (final Object object : figure.getChildren()) {
            if (object instanceof ObjectFigure) {
                final ObjectFigure objectFigure = (ObjectFigure) object;
                if (objectFigure.getData() instanceof IObject) {
                    final IObject obj = (IObject) objectFigure.getData();
                    if (report.hasFailed(obj)) {
                        graphics.drawRectangle(objectFigure.getBounds());
                    }
                }
            }
        }
        graphics.setForegroundColor(oldForeground);
    }

    public void reportEvent(final IAssertionReportEvent event) {
        report = event.getReport();
    }

    public void clearEvent() {
        report = null;
    }

    @SuppressWarnings("unchecked")
    public List<IFigure> getElements(final IRuler ruler, final IFigure figure) {
        return Collections.EMPTY_LIST;
    }

}
