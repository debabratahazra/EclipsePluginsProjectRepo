package com.zealcore.se.ui.editors;

import org.eclipse.swt.graphics.GC;

import com.zealcore.se.core.model.ITimed;
import com.zealcore.se.ui.graphics.IGraphics;

interface IDrawableRuler extends IRuler {

    void draw(final Iterable<? extends ITimed> timed, final GC graphics,
            final int xStartClearance, final int xEndClearance);

    void draw(final Iterable<? extends ITimed> timed, final IGraphics graphics,
            final int xStartClearance, final int xEndClearance);
}
