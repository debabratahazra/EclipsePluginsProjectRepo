package com.zealcore.se.ui.graphics.figures;

import com.zealcore.se.core.model.IArtifact;

public class TaskLaneEntry extends ObjectFigure {
    private static final int HALF = 2;

    private int lane;

    private final IArtifact user;

    public TaskLaneEntry(final IArtifact user) {
        super();
        this.user = user;
        setData(user);
    }

    @Override
    protected void paintFigure(final org.eclipse.draw2d.Graphics graphics) {
        if (!getBackgroundColor().isDisposed()) {
            graphics.setBackgroundColor(getBackgroundColor());
        }
        final int boxWidth = getBounds().height / HALF;
        final int laneYStart = getBounds().y;
        final int y = laneYStart + boxWidth / HALF;
        graphics.fillRectangle(3, y, boxWidth, boxWidth);
        graphics.drawRectangle(3, y, boxWidth, boxWidth);

        final int textXOffset = getBounds().height;
        graphics.drawString(user.getName(), textXOffset, laneYStart);
    }

    @Override
    public IArtifact getData() {
        return this.user;
    }

    public int getLane() {
        return lane;
    }

    public IArtifact getUser() {
        return user;
    }

    public void setLane(final int lane) {
        this.lane = lane;
    }

    @Override
    public void setData(final Object data) {
        if (data instanceof IArtifact) {
            super.setData(data);
        } else {
            throw new IllegalArgumentException("Data must be an IArtifact");
        }
    }
}
