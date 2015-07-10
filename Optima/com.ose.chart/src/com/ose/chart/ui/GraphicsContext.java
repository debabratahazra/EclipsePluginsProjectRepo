/* COPYRIGHT-ENEA-SRC-R1 *
 **************************************************************************
 * Copyright (C) 2007 by Enea Software AB.
 * All rights reserved.
 *
 * This Software is furnished under a software license agreement and
 * may be used only in accordance with the terms of such agreement.
 * Any other use or reproduction is prohibited. No title to and
 * ownership of the Software is hereby transferred.
 *
 * PROPRIETARY NOTICE
 * This Software consists of confidential information.
 * Trade secret law and copyright law protect this Software.
 * The above notice of copyright on this Software does not indicate
 * any actual or intended publication of such Software.
 **************************************************************************
 * COPYRIGHT-END */

package com.ose.chart.ui;

import com.ose.chart.math.Vector2;
import com.ose.chart.math.Vector3;

/**
 * An interface that provides a unified set of 3D graphics operations,
 * regardless of the underlying graphics API.
 */
public interface GraphicsContext
{
   // ------ FONT TYPES -------------------------------
   public static final int FONT_DEFAULT = 0;
   public static final int FONT_TITLE = 1;
   public static final int FONT_LABEL = 2;
   public static final int FONT_LEGEND = 3;
   public static final int FONT_BIG_LABEL = 4;
   public static final int FONT_SMALL_LABEL = 5;

   // ------ INFO -------------------------------------
   public String getInfo(String query);
   
   // ------ MODES ------------------------------------
   public void begin();
   public void end();

   // ------ TRANSFORMS -------------------------------
   public void setPerspectiveProjection(float fieldOfView, float aspectRatio, float nearPlane, float farPlane);
   public void setParallelProjection(float left, float right, float bottom, float top);
   public void pushTransform();
   public void popTransform();
   public void loadIdentityTransform();
   public void translate(Vector3 v);
   public void translate(float x, float y, float z);
   public void rotateX(float angle);
   public void rotateY(float angle);
   public void rotateZ(float angle);
   public void rotate(float angle, Vector3 axis);
   public void rotate(float angle, float axisX, float axisY, float axisZ);
   public void scale(float s);
   public void scale(Vector3 v);
   public void scale(float x, float y, float z);

   // ------ SETTINGS ----------------------------------
   public void setBlending(boolean enabled);
   public void setTexturing(boolean enabled);
   public void setLighting(boolean enabled);
   public void setDepthWriting(boolean enabled);
   public void setDepthTesting(boolean enabled);
   public void setBackfaceCulling(boolean enabled);
   public void setLineSmoothing(boolean enabled);
   public void setLineWidth(float width);
   public void setDashedLine(boolean enabled);

   // ------ COLOR -------------------------------------
   public void setForeground(ColorRGBA color);
   public void setForeground(float r, float g, float b);
   public void setForeground(float r, float g, float b, float a);

   // ------ VIEWPORT RELATED --------------------------
   public void resizeViewport(int width, int height);
   public void clear(ColorRGBA color);
   public void clear(float r, float g, float b);
   public void clear(float r, float g, float b, float a);

   // ------ TEXT --------------------------------------
   public void beginText2D(int fontType, int width, int height);
   public void drawString2D(String str, int x, int y);
   public void endText2D();

   public void beginText3D(int fontType);
   public void drawString3D(String str, float x, float y, float z, float scale);
   public void endText3D();

   public void setTextColor(ColorRGBA color);
   public void setTextColor(float r, float g, float b);
   public void setTextColor(float r, float g, float b, float a);

   public Rectangle2D getTextBounds(String str);
   public Rectangle2D getTextBounds2D(int fontType, String str);
   public Rectangle2D getTextBounds3D(int fontType, String str);

   // ------ DRAWING -----------------------------------
   public void drawLine(Vector3 p1, Vector3 p2);
   public void drawLine(float x1, float y1, float x2, float y2);
   public void drawLine(float x1, float y1, float z1, float x2, float y2, float z2);

   public void drawPolyline(Vector3[] points, boolean closed);
   public void drawPolyline(Vector2[] points, boolean closed);
   public void drawPolyline(Vector2[] points, int offset, int count, boolean closed);

   public void beginPolyline(boolean closed);
   public void drawPolyline(float x, float y, float z);
   public void drawPolyline(Vector3 v);
   public void drawPolyline(float x, float y);
   public void drawPolyline(Vector2 v);
   public void endPolyline();

   public void drawGrid(int horizontalSteps, int verticalSteps);
   public void drawGridX(float xOffset, float dx, float xLimit, float y1);
   public void drawGridX(float xOffset, float dx, float xLimit, float y1, float z);
   public void drawGridY(float yOffset, float dy, float yLimit, float x1);
   public void drawGridY(float width, float distance, int count, float z);
   public void drawGridY(float yOffset, float dy, float yLimit, float x1, float z);

   public void drawTicksX(float xOffset, float dx, float xLimit, float length, float y);
   public void drawTicksY(float yOffset, float dy, float yLimit, float length, float z);
   public void drawTicksY(float distance, int count);
   public void drawTicksY(float distance, int count, float z);

   public void drawRectangle2D(float x0, float y0, float x1, float y1);

   public void beginFan();
   public void fillFan(Vertex[] vertices);
   public void fillFan(float x, float y, float z, Vertex[] vertices);
   public void fillFan(Vertex[] vertices, ColorRGBA color);
   public void fillFan(float x, float y, float z, Vertex[] vertices, ColorRGBA color);
   public void fillFanAroundOrigin(Vertex[] vertices, int firstIndex, int count, ColorRGBA color);
   public void fillFanAroundPoint(float x, float y, float z, Vertex[] vertices, int firstIndex, int count, ColorRGBA color);
   public void endFan();

   public void beginStrip();
   public void fillStrip(Vertex[] vertices);
   public void fillStrip(float x, float y, float z, Vertex[] vertices);
   public void fillStrip(float x, float y, float z, float[] xs, float[] ys, int offset, int count);
   public void fillStrip2D(float x, float y, float[] xs, float[] ys);
   public void fillStrip2D(float x, float y);
   public void fillStrip2D(float x, float y, float[] xs, float[] ys, int offset, int count);
   public void endStrip();

   public void beginTriangles();
   public void fillTriangles(Vertex[] vertices);
   public void fillTriangles(float x, float y, float z, Vertex[] vertices);
   public void endTriangles();

   public void beginQuads();
   public void fillQuads(Vertex[] vertices);
   public void fillQuads(float x, float y, float z, Vertex[] vertices);
   public void endQuads();

   public void fillBar(float x0, float y0, float z0, float x1, float y1, float z1, ColorRGBA color, float[] colorScaling);
   public void fillSegment(float x0, float y0a, float y0b, float z0, float x1, float y1a, float y1b, float z1, ColorRGBA color, float[] colorScaling, boolean drawTop, boolean drawLeft, boolean drawRight);

   public void fillCuboid(float x0, float y0, float z0, float x1, float y1, float z1);
   public void fillCuboid(float x0, float y0, float z0, float x1, float y1, float z1, ColorRGBA color, float[] colorScaling);

   public void fillRectangle2D(float x0, float y0, float x1, float y1);
   public void fillRectangleXZ(float x0, float z0, float x1, float z1);
   public void fillRectangleXZ(float x0, float z0, float x1, float z1, float y);
   public void fillRectangleXY(float x0, float y0, float x1, float y1);
   public void fillRectangleXY(float x0, float y0, float x1, float y1, float z);
   public void fillRectangleYZ(float y0, float z0, float y1, float z1);
}
