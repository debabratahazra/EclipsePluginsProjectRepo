package com.zealcore.se.ui.graphics;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

/**
 * This class demonstrates a Canvas
 */
public class FillRoundedRectangleTest {

    private int iteration;

    private int i;

    /**
     * Runs the application
     */
    public void run() {
        Display display = new Display();
        Shell shell = new Shell(display);
        shell.setText("Canvas Example");
        createContents(shell);
        shell.open();
        while (!shell.isDisposed()) {
            if (!display.readAndDispatch()) {
                display.sleep();
            }
        }
        display.dispose();
    }

    /**
     * Creates the main window's contents
     * 
     * @param shell
     *                the main window
     */
    private void createContents(final Shell shell) {
        shell.setLayout(new FillLayout());

        // Create a canvas
        final Canvas canvas = new Canvas(shell, SWT.NONE);

        final Text text = new Text(shell, SWT.SHADOW_IN);

        // Create a button on the canvas
        Button button = new Button(canvas, SWT.PUSH);
        button.setBounds(10, 10, 300, 40);
        button.setText("You can place widgets on a canvas");
        button.addSelectionListener(new SelectionListener() {

            public void widgetSelected(final SelectionEvent event) {
                final IGraphics egc = Graphics.valueOf(new GC(canvas));
                int x = 100;
                int y = 100;
                int width = 100;
                int height = 100;
                int arcWidth = 10;
                int arcHeight = 10;
                for (i = 0; i < 100; i++) {
                    egc.setBackground(egc.getDevice().getSystemColor(
                    /* SWT.COLOR_GRAY+ */i % 10));
                    egc.fillRoundRectangle(x + i, y + i + iteration, width,
                            height, arcWidth, arcHeight);
                }
                iteration += 20;
                text.setText("Called fillRoundedRectangle: " + (iteration * 5)
                        + " times");

            }

            public void widgetDefaultSelected(final SelectionEvent event) {
                text.setText("No worries!");
            }
        });

        // Create a paint handler for the canvas
        canvas.addPaintListener(new PaintListener() {
            private int x;

            public void paintControl(final PaintEvent e) {
                // Do some drawing
                Rectangle rect = ((Canvas) e.widget).getBounds();
                e.gc.setForeground(e.display.getSystemColor(SWT.COLOR_RED));
                e.gc.drawFocus(5, 5, rect.width - 10, rect.height - 10);
                e.gc.drawText("You can draw text directly on a canvas", 60, 60);

                final IGraphics egc = Graphics.valueOf(e.gc);
                egc.setBackground(egc.getDevice()
                        .getSystemColor(SWT.COLOR_GRAY));
                x++;
                int y = 100;
                int width = 100;
                int height = 100;
                int arcWidth = 10;
                int arcHeight = 10;
                // for (int i = 0; i < 100; i++) {
                egc.fillRoundRectangle(x + i, y + i, width, height, arcWidth,
                        arcHeight);
                // }

            }
        });
    }

    /**
     * The application entry point
     * 
     * @param args
     *                the command line arguments
     */
    public static void main(final String[] args) {
        new FillRoundedRectangleTest().run();
    }
}
