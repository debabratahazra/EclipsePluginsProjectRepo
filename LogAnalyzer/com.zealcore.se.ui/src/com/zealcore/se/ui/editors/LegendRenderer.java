package com.zealcore.se.ui.editors;

import java.util.Map;
import java.util.Map.Entry;

import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.FontMetrics;

import com.zealcore.se.core.model.IType;
import com.zealcore.se.ui.graphics.IGraphics;
import com.zealcore.se.ui.internal.SWTUtil;

public class LegendRenderer {

    private static final String MULTIPLE_EVENTS = "* = Multiple events";

    public LegendRenderer() {
        super();
    }

    private static final int LEGEND_BOX_MARGIN = 10;

    private static final int LEGEND_BOX_SIZE = 10;

    private static final int TWO = 2;

    public void drawLegend(final int midX, final IGraphics graphics,
            final Map<IType, Color> colorByTypeMap,
            final boolean multipleEventsAtSameTs) {
        final int boxSize = LEGEND_BOX_SIZE;
        final int margin = LEGEND_BOX_MARGIN;
        final Color bg = graphics.getBackground();

        final int y = 0;
        int xOffset = LEGEND_BOX_MARGIN;
        final FontMetrics fontMetrics = graphics.getFontMetrics();
        final int fontHeight = fontMetrics.getHeight();

        // Get legend width
        int legendWidth = 0;
        IType key = null;
        for (final Entry<IType, Color> entry : colorByTypeMap.entrySet()) {
            final IType clazz = entry.getKey();
            // Calculate the length of the textual representation of the legend
            int nameLength = 0;
            String simpleName = clazz.getName();
            for (int i = 0; i < simpleName.length(); i++) {
                nameLength += graphics.getAdvanceWidth(simpleName.charAt(i));
            }
            legendWidth += nameLength + boxSize + margin + margin;
        }
        if (multipleEventsAtSameTs) {
            String simpleName = MULTIPLE_EVENTS;
            for (int i = 0; i < simpleName.length(); i++) {
                legendWidth += graphics.getAdvanceWidth(simpleName.charAt(i));
            }
        }

        if (key != null) {
            colorByTypeMap.remove(key);
        }

        // Assuming box is less than fontHeight
        final int boxOffset = (fontHeight - boxSize) / TWO;

        xOffset = midX - legendWidth / TWO;

        //TreeMap<IType, Color> sorted = new TreeMap<IType, Color>();
        //for (final Entry<IType, Color> entry : colorByTypeMap.entrySet()) {
        //    sorted.put(entry.getKey(), entry.getValue());
        //}

        // legendBoxes.clear();
        for (final Entry<IType, Color> entry : colorByTypeMap.entrySet()) {

            final IType clazz = entry.getKey();
            final Color c = entry.getValue();

            // Calculate the length of the textual representation of the legend
            // entry
            final int nameLength = SWTUtil
                    .textLength(graphics, clazz.getName());

            graphics.setBackground(c);
            // Draw legend color
            // final Rectangle rect = new Rectangle(xOffset, y + boxOffset,
            // boxSize, boxSize);
            // legendBoxes.put(rect, entry);
            // SWTUtil.fillGlossRoundRect(graphics, rect, 0, true);
            graphics.fillRectangle(xOffset, y + boxOffset, boxSize, boxSize);
            graphics.drawRectangle(xOffset, y + boxOffset, boxSize, boxSize);

            // legend text at offset + width of box + margin between box
            graphics.drawString(clazz.getName(), xOffset + boxSize + margin, y,
                    true);

            // New offset
            xOffset += nameLength + boxSize + margin + margin;
        }
        if (multipleEventsAtSameTs) {
            graphics.drawString(MULTIPLE_EVENTS, xOffset + boxSize + margin, y,
                    true);
        }

        // Reset bg color
        graphics.setBackground(bg);
    }

}
