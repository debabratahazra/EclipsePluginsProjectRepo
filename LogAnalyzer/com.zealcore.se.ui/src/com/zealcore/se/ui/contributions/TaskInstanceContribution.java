package com.zealcore.se.ui.contributions;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.IFigure;

import com.zealcore.se.core.model.AbstractTaskInstance;
import com.zealcore.se.core.model.IArtifact;
import com.zealcore.se.core.model.IArtifactID;
import com.zealcore.se.ui.ITimeCluster;
import com.zealcore.se.ui.editors.ILogBrowserContribution;
import com.zealcore.se.ui.editors.ILogsetBrowser;
import com.zealcore.se.ui.editors.IRuler;
import com.zealcore.se.ui.graphics.figures.TaskExecution;

public class TaskInstanceContribution implements ILogBrowserContribution {

    private TaskInstanceQuery query;

    private ILogsetBrowser view;

    public TaskInstanceContribution() {}

    public void decorateFigures(final Graphics graphics, final IFigure figure) {}

    public void dispose() {
        view.getInput().getLogset().removeQuery(query);
    }

    public List<IFigure> getElements(final IRuler ruler, final IFigure figure) {

        final List<TaskExecution> executions = new ArrayList<TaskExecution>();
        long b = Long.MAX_VALUE;
        long e = Long.MIN_VALUE;
        for (final Object object : figure.getChildren()) {
            if (object instanceof TaskExecution) {
                final TaskExecution exe = (TaskExecution) object;
                executions.add(exe);
                b = Math.min(b, exe.getDuration().getStartTime());
                e = Math.max(e, exe.getDuration().getStartTime()
                        + exe.getDuration().getDurationTime());
            }
        }

        final List<AbstractTaskInstance> data;
        if (b == Long.MAX_VALUE && e == Long.MIN_VALUE) {
            data = Collections.emptyList();
        } else {
            data = query.getBetween(b, e);
        }
        return toFigures(ruler, data, executions);

    }

    private List<IFigure> toFigures(final IRuler ruler,
            final List<AbstractTaskInstance> data,
            final List<TaskExecution> executions) {

        if (executions.size() < 1) {
            return Collections.emptyList();
        }

        final Map<IArtifactID, Integer> yByArtifact = new HashMap<IArtifactID, Integer>();
        for (final TaskExecution execution : executions) {
            final IArtifact o = execution.getDuration().getOwner();
            yByArtifact.put(o, execution.getBounds().y);
        }

        final List<IFigure> figures = new ArrayList<IFigure>();
        for (final AbstractTaskInstance ins : data) {

            if (yByArtifact.containsKey(ins.getOwner())) {
                final int y = yByArtifact.get(ins.getOwner());
                final TaskInstanceFigure fig = new TaskInstanceFigure(ruler, y,
                        ins);
                figures.add(fig);
            }
        }
        return figures;
    }

    public void init(final ILogsetBrowser view) {
        this.view = view;
        final ITimeCluster tc = view.getInput().getTimeCluster();
        query = new TaskInstanceQuery(tc);
        view.getInput().getLogset().addQuery(query);
        view.getInput().getLogset().removeQuery(query);
    }

}
