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

import javax.media.opengl.GL;
import javax.media.opengl.GLContext;
import javax.media.opengl.GLDrawableFactory;
import javax.media.opengl.glu.GLU;

import org.eclipse.swt.graphics.Rectangle;

import com.ose.chart.math.Vector2;
import com.ose.chart.math.Vector3;
import com.ose.chart.ui.ColorRGBA;
import com.ose.chart.ui.GraphicsContext;
import com.ose.chart.ui.Vertex;
import com.sun.opengl.util.j2d.TextRenderer;

/**
 * The OpenGL (JOGL) implementation of the {@link GraphicsContext} interface.
 * 
 * @see <a href="https://jogl.dev.java.net/">JOGL</a>
 * @see <a href="http://www.opengl.org/sdk/docs/man/">OpenGL 2.1 Reference Pages</a>
 */
class GraphicsContextGL implements GraphicsContext
{
   private GLContext context;
   private GLU glu = null;
   private GL gl = null;

   protected TextRenderer default2DFontRenderer;
   protected TextRenderer default3DFontRenderer;
   protected TextRenderer titleFontRenderer;
   protected TextRenderer label2DFontRenderer;
   protected TextRenderer label3DFontRenderer;
   protected TextRenderer smallLabel3DFontRenderer;
   protected TextRenderer bigLabel3DFontRenderer;
   protected TextRenderer legendFontRenderer;
   protected TextRenderer currentFontRenderer;

   private int viewportWidth;
   private int viewportHeight;
      
   public GraphicsContextGL()
   {
      context = GLDrawableFactory.getFactory().createExternalGLContext();
      context.makeCurrent();
      glu = new GLU();

      initializeRenderState();

      initializeTextRenderers();
   }

   private void setEnabled(int property, boolean enabled)
   {
      if (enabled)
      {
         gl.glEnable(property);
      }
      else
      {
         gl.glDisable(property);
      }
   }

   protected void initializeRenderState()
   {
      begin();

      setForeground(ColorRGBA.RED);
      setDepthTesting(true);
      setDepthWriting(true);
      setLineSmoothing(true);
      setTexturing(false);

      // Set value for clearing the z-buffer
      gl.glClearDepth(1.0f);
      
      // Set default clear color
      gl.glClearColor(1.0f, 1.0f, 1.0f, 1.0f);

      // High quality perspective correction should be used
      gl.glHint(GL.GL_PERSPECTIVE_CORRECTION_HINT, GL.GL_NICEST);

      // Set alpha blending default mode
      gl.glBlendFunc(GL.GL_SRC_ALPHA, GL.GL_ONE_MINUS_SRC_ALPHA);

      // Set back face culling
      gl.glFrontFace(GL.GL_CCW);
      gl.glCullFace(GL.GL_BACK);
      setBackfaceCulling(true);

      // Setup lighting
      gl.glLightfv(GL.GL_LIGHT1, GL.GL_DIFFUSE, new float[] {1.0f, 1.0f, 1.0f, 1.0f}, 0);
      gl.glLightfv(GL.GL_LIGHT1, GL.GL_AMBIENT, new float[] {0.0f, 0.0f, 0.0f, 1.0f}, 0);
      gl.glLightfv(GL.GL_LIGHT1, GL.GL_POSITION, new float[] {-50.f, 50.0f, 100.0f, 1.0f}, 0);
      gl.glEnable(GL.GL_LIGHT1);
      gl.glShadeModel(GL.GL_FLAT);
      setLighting(true);

      // Setup material
      setColorMaterial(true);
      gl.glColorMaterial(GL.GL_FRONT, GL.GL_AMBIENT_AND_DIFFUSE);

      // Turn off auto-normalize
      setNormalize(false);

      // Turn on vertical sync
      gl.setSwapInterval(1);

      // Setup texture filtering
      gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_MAG_FILTER, GL.GL_LINEAR);
      gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_MIN_FILTER, GL.GL_LINEAR);

      end();
   }

   protected void setColorMaterial(boolean enabled)
   {
      setEnabled(GL.GL_COLOR_MATERIAL, enabled);
   }

   public void setNormalize(boolean enabled)
   {
      setEnabled(GL.GL_NORMALIZE, false);
   }

   protected void initializeTextRenderers()
   {
      titleFontRenderer = new TextRenderer(new java.awt.Font("SansSerif", java.awt.Font.BOLD, 14), true, false);
      label2DFontRenderer = new TextRenderer(new java.awt.Font("SansSerif", java.awt.Font.PLAIN, 11), true, false);
      smallLabel3DFontRenderer = new TextRenderer(new java.awt.Font("SansSerif", java.awt.Font.PLAIN, 20), true, false);
      label3DFontRenderer = new TextRenderer(new java.awt.Font("SansSerif", java.awt.Font.PLAIN, 20), true, false);
      bigLabel3DFontRenderer = new TextRenderer(new java.awt.Font("SansSerif", java.awt.Font.PLAIN, 40), true, false);
      legendFontRenderer = new TextRenderer(new java.awt.Font("SansSerif", java.awt.Font.PLAIN, 10), true, false);
      default2DFontRenderer = label2DFontRenderer;
      default3DFontRenderer = label3DFontRenderer;
   }

   // ------------- PUBLIC METHODS --------------------------
   
   public void resizeViewport(Rectangle rect)
   {
      resizeViewport(rect.width, rect.height);
   }
   
   public void resizeViewport(int width, int height)
   {
      viewportWidth = width;
      viewportHeight = height;
      
      // Make GL context current
      begin();

      // Setup the GL viewport
      gl.glViewport(0, 0, width, height);

      // Release GL context
      end();
   }

   public Rectangle getViewportSize()
   {
      return new Rectangle(0, 0, viewportWidth, viewportHeight);
   }
   
   public String getInfo(String query)
   {
      String info;
      
      // Make GL context current
      begin();
      
      if (query.equals("vendor"))
      {
         info = gl.glGetString(GL.GL_VENDOR);
      }
      else if (query.equals("renderer"))
      {
         info = gl.glGetString(GL.GL_RENDERER);         
      }
      else if (query.equals("version"))
      {
         info = gl.glGetString(GL.GL_VERSION);         
      }
      else
      {
         info = null;
      }
      
      // Release GL context
      end();

      return info;
   }
   
   // Be careful with this method. It will call makeCurrent() on the JOGL 
   // OpenGL context. Presumably you will also want to call setCurrent() on 
   // the SWT GLCanvas.
   public void begin()
   {
      if (gl == null)
      {
         context.makeCurrent();
         gl = context.getGL();
      }
   }

   public void end()
   {
      if (gl != null)
      {
         context.release();
         gl = null;
      }
   }

   public void setPerspectiveProjection(float fieldOfView, float aspectRatio, float nearPlane, float farPlane)
   {
      gl.glMatrixMode(GL.GL_PROJECTION);
      gl.glLoadIdentity();
      glu.gluPerspective(fieldOfView, aspectRatio, nearPlane, farPlane);
      gl.glMatrixMode(GL.GL_MODELVIEW);
   }
   
   public void setParallelProjection(float left, float right, float bottom, float top)
   {
      gl.glMatrixMode(GL.GL_PROJECTION);
      gl.glLoadIdentity();
      gl.glOrtho(left, right, bottom, top, -1, 1);      
      gl.glMatrixMode(GL.GL_MODELVIEW);
   }
   
   public void pushTransform()
   {
      gl.glPushMatrix();
   }

   public void popTransform()
   {
      gl.glPopMatrix();
   }

   public void loadIdentityTransform()
   {
      gl.glLoadIdentity();
   }

   public void lookAt(double eyeX, double eyeY, double eyeZ,
                      double centerX, double centerY, double centerZ)
   {
      gl.glLoadIdentity();
      glu.gluLookAt(eyeX, eyeY, eyeZ,
                    centerX, centerY, centerZ,
                    0.0f, 1.0f, 0.0f);
   }

   public void translate(Vector3 v)
   {
      translate(v.x, v.y, v.z);
   }

   public void translate(float x, float y, float z)
   {
      gl.glTranslatef(x, y, z);
   }

   public void rotateX(float angle)
   {
      rotate(angle, 1.0f, 0.0f, 0.0f);
   }

   public void rotateY(float angle)
   {
      rotate(angle, 0.0f, 1.0f, 0.0f);
   }

   public void rotateZ(float angle)
   {
      rotate(angle, 0.0f, 0.0f, 1.0f);
   }

   public void rotate(float angle, Vector3 axis)
   {
      rotate(angle, axis.x, axis.y, axis.z);
   }

   public void rotate(float angle, float axisX, float axisY, float axisZ)
   {
      gl.glRotatef(angle, axisX, axisY, axisZ);
   }

   public void scale(float s)
   {
      scale(s, s, s);
   }

   public void scale(Vector3 v)
   {
      scale(v.x, v.y, v.z);
   }

   public void scale(float x, float y, float z)
   {
      gl.glScalef(x, y, z);
   }

   public void clear(ColorRGBA color)
   {
      clear(color.getRed(),
            color.getGreen(),
            color.getBlue(),
            color.getAlpha());
   }
   
   public void clear(float r, float g, float b)
   {
      clear(r, g, b, 1.0f);
   }
   
   public void clear(float r, float g, float b, float a)
   {
      // Set background color (RGBA)
      gl.glClearColor(r, g, b, a);

      // Clear frame and depth buffers
      gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);
   }

   public void setBlending(boolean enabled)
   {
      setEnabled(GL.GL_BLEND, enabled);
   }

   public void setTexturing(boolean enabled)
   {
      setEnabled(GL.GL_TEXTURE_2D, enabled);
   }

   public void setLighting(boolean enabled)
   {
      setEnabled(GL.GL_LIGHTING, enabled);
   }

   public void setDepthWriting(boolean enabled)
   {
      gl.glDepthMask(enabled);
   }

   public void setDepthTesting(boolean enabled)
   {
      setEnabled(GL.GL_DEPTH_TEST, enabled);
   }

   public void setBackfaceCulling(boolean enabled)
   {
      setEnabled(GL.GL_CULL_FACE, enabled);
   }

   public void setLineSmoothing(boolean enabled)
   {
      setEnabled(GL.GL_LINE_SMOOTH, enabled);
   }

   public void setLineWidth(float width)
   {
      gl.glLineWidth(width);
   }

   public void setDashedLine(boolean enabled)
   {
      gl.glLineStipple(4, (short)0xAAAA);
      setEnabled(GL.GL_LINE_STIPPLE, enabled);
   }

   public void setForeground(ColorRGBA color)
   {
      setForeground(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha());
   }

   public void setForeground(float r, float g, float b)
   {
      setForeground(r, g, b, 1.0f);
   }

   public void setForeground(float r, float g, float b, float a)
   {
      gl.glColor4f(r, g, b, a);
   }

   public void clampTexture()
   {
      gl.glTexParameterf(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_WRAP_S, GL.GL_CLAMP);
      gl.glTexParameterf(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_WRAP_T, GL.GL_CLAMP);
   }

   public void drawLine(Vector3 p1, Vector3 p2)
   {
      drawLine(p1.x, p1.y, p1.z, p2.x, p2.y, p2.z);
   }

   public void drawLine(float x1, float y1, float x2, float y2)
   {
      drawLine(x1, y1, 0, x2, y2, 0);
   }

   public void drawLine(float x1, float y1, float z1, float x2, float y2, float z2)
   {
      gl.glBegin(GL.GL_LINES);
      gl.glVertex3f(x1, y1, z1);
      gl.glVertex3f(x2, y2, z2);
      gl.glEnd();
   }

   public void drawPolyline(Vector3[] points, boolean closed)
   {
      gl.glBegin(closed ? GL.GL_LINE_LOOP : GL.GL_LINE_STRIP);

      for (int i = 0; i < points.length; i++)
      {
         Vector3 v = points[i];
         gl.glVertex3f(v.x, v.y, v.z);
      }

      gl.glEnd();
   }

   public void drawPolyline(Vector2[] points, boolean closed)
   {
      gl.glBegin(closed ? GL.GL_LINE_LOOP : GL.GL_LINE_STRIP);

      for (int i = 0; i < points.length; i++)
      {
         Vector2 v = points[i];
         gl.glVertex3f(v.x, v.y, 0.0f);
      }

      gl.glEnd();
   }

   public void drawPolyline(Vector2[] points, int offset, int count, boolean closed)
   {
      gl.glBegin(closed ? GL.GL_LINE_LOOP : GL.GL_LINE_STRIP);

      for (int i = 0; i < count; i++)
      {
         Vector2 v = points[offset + i];
         gl.glVertex3f(v.x, v.y, 0.0f);
      }

      gl.glEnd();
   }

   public void beginPolyline(boolean closed)
   {
      gl.glBegin(closed ? GL.GL_LINE_LOOP : GL.GL_LINE_STRIP);
   }

   public void drawPolyline(float x, float y, float z)
   {
      gl.glVertex3f(x, y, z);
   }

   public void drawPolyline(Vector3 v)
   {
      gl.glVertex3f(v.x, v.y, v.z);
   }

   public void drawPolyline(float x, float y)
   {
      gl.glVertex3f(x, y, 0f);
   }

   public void drawPolyline(Vector2 v)
   {
      gl.glVertex3f(v.x, v.y, 0f);
   }

   public void endPolyline()
   {
      gl.glEnd();
   }

   public void drawGrid(int horizontalSteps, int verticalSteps)
   {
      float dx = 1.0f / horizontalSteps;
      float dy = 1.0f / verticalSteps;

      gl.glBegin(GL.GL_LINES);

      for (float x = 0; x <= horizontalSteps; x++)
      {
         gl.glVertex3f(dx * x, 0.0f, 0.0f);
         gl.glVertex3f(dx * x, 1.0f, 0.0f);
      }

      for (float y = 0; y <= verticalSteps; y++)
      {
         gl.glVertex3f(0.0f, dy * y, 0.0f);
         gl.glVertex3f(1.0f, dy * y, 0.0f);
      }

      gl.glEnd();
   }

   public void drawGridX(float xOffset, float dx, float xLimit, float y1)
   {
      gl.glBegin(GL.GL_LINES);

      for (float x = xOffset; x <= xLimit; x += dx)
      {
         gl.glVertex3f(x, 0.0f, 0.0f);
         gl.glVertex3f(x, y1, 0.0f);
      }

      gl.glEnd();
   }
   
   public void drawGridX(float xOffset, float dx, float xLimit, float y1, float z)
   {
      gl.glBegin(GL.GL_LINES);

      if (dx > 0.0f)
      {
         for (float x = xOffset; x <= xLimit; x += dx)
         {
            gl.glVertex3f(x, 0.0f, z);
            gl.glVertex3f(x, y1, z);
         }
      }
      else if (dx < 0.0f)
      {
         for (float x = xOffset; x >= xLimit; x += dx)
         {
            gl.glVertex3f(x, 0.0f, z);
            gl.glVertex3f(x, y1, z);
         }
      }
      else
      {
         // Do nothing
      }
      
      gl.glEnd();
   }

   public void drawGridY(float yOffset, float dy, float yLimit, float x1)
   {
      drawGridY(yOffset, dy, yLimit, x1, 0.0f);
   }

   public void drawGridY(float yOffset, float dy, float yLimit, float x1, float z)
   {
      gl.glBegin(GL.GL_LINES);


      if (dy > 0.0f)
      {
         for (float y = yOffset; y <= yLimit; y += dy)
         {
            gl.glVertex3f(0.0f, y, z);
            gl.glVertex3f(x1, y, z);
         }
      }
      else if (dy < 0.0f)
      {
         for (float y = yOffset; y >= yLimit; y += dy)
         {
            gl.glVertex3f(0.0f, y, z);
            gl.glVertex3f(x1, y, z);
         }
      }
      else
      {
         // Do nothing
      }
      
      gl.glEnd();
   }
   
   public void drawGridY(float width, float distance, int count, float z)
   {
      gl.glBegin(GL.GL_LINES);
      
      for (int i = 0; i <= count; i++)
      {
         gl.glVertex3f(0.0f, distance * (float)i, z);
         gl.glVertex3f(width, distance * (float)i, z);
      }

      gl.glEnd();
   }

   public void drawTicksX(float xOffset, float dx, float xLimit, float length, float y)
   {
      gl.glBegin(GL.GL_LINES);
      
      for (float x = xOffset; x <= xLimit; x += dx)
      {
         gl.glVertex3f(x, y, 0.0f);
         gl.glVertex3f(x, y - length, 0.0f);
      }

      gl.glEnd();
   }   
   
   public void drawTicksY(float yOffset, float dy, float yLimit)
   {
      drawTicksY(yOffset, dy, yLimit, 0.0f);
   }

   public void drawTicksY(float yOffset, float dy, float yLimit, float z)
   {
      drawTicksY(yOffset, dy, yLimit, 0.01f, 0.0f);
   }

   public void drawTicksY(float yOffset, float dy, float yLimit, float length, float z)
   {
      gl.glBegin(GL.GL_LINES);

      if (dy > 0.0f)
      {
         for (float y = yOffset; y <= yLimit; y += dy)
         {
            gl.glVertex3f(0.0f, y, z);
            gl.glVertex3f(-length, y, z);
         }
      }
      else if (dy < 0.0f)
      {
         for (float y = yOffset; y >= yLimit; y += dy)
         {
            gl.glVertex3f(0.0f, y, z);
            gl.glVertex3f(-length, y, z);
         }
      }
      else
      {
         // Do nothing
      }

      gl.glEnd();  
   }
   
   public void drawTicksY(float distance, int count)
   {
      drawTicksY(distance, count, 0f);
   }

   public void drawTicksY(float distance, int count, float z)
   {
      gl.glBegin(GL.GL_LINES);

      for (int i = 0; i <= count; i++)
      {
         gl.glVertex3f(0.0f, distance * (float)i, z);
         gl.glVertex3f(-0.01f, distance * (float)i, z);
      }

      gl.glEnd();
   }

   public void drawRectangle2D(float x0, float y0, float x1, float y1)
   {
      gl.glBegin(GL.GL_LINES);
      gl.glVertex3f(x0, y0, 0.0f);
      gl.glVertex3f(x0, y1, 0.0f);
      gl.glVertex3f(x1, y0, 0.0f);
      gl.glVertex3f(x1, y1, 0.0f);
      gl.glVertex3f(x0, y1, 0.0f);
      gl.glVertex3f(x1, y1, 0.0f);
      gl.glVertex3f(x0, y0, 0.0f);
      gl.glVertex3f(x1, y0, 0.0f);
      gl.glEnd();
   }

   public void beginFan()
   {
      gl.glBegin(GL.GL_TRIANGLE_FAN);
   }

   public void endFan()
   {
      gl.glEnd();
   }
   
   public void fillFan(Vertex[] vertices)
   {
      fillFan(0f, 0f, 0f, vertices);
   }

   public void fillFan(float x, float y, float z, Vertex[] vertices)
   {
      for (int i = 0; i < vertices.length; i++)
      {
         Vertex v = vertices[i];
         gl.glVertex3f(x + v.position.x, y + v.position.y, z + v.position.z);
      }
   }

   public void fillFan(Vertex[] vertices, ColorRGBA color)
   {
      fillFan(0f, 0f, 0f, vertices, color);
   }

   public void fillFan(float x, float y, float z, Vertex[] vertices, ColorRGBA color)
   {
      for (int i = 0; i < vertices.length; i++)
      {
         Vertex v = vertices[i];
         gl.glColor3f(color.getRed(), color.getGreen(), color.getBlue());
         gl.glVertex3f(x + v.position.x, y + v.position.y, z + v.position.z);
      }
   }
   
   public void fillFanAroundOrigin(Vertex[] vertices, int firstIndex, int count,
                                   ColorRGBA color)
   {
      fillFanAroundPoint(0f, 0f, 0f, vertices, firstIndex, count, color);
   }

   public void fillFanAroundPoint(float x, float y, float z, Vertex[] vertices,
                                  int firstIndex, int count, ColorRGBA color)
   {
      gl.glColor3f(color.getRed(), color.getGreen(), color.getBlue());
      gl.glVertex3f(x, y, z);
      for (int i = 0; i < count + 1; i++)
      {
         Vertex v = vertices[(i + firstIndex) % vertices.length];
         gl.glVertex3f(x + v.position.x, y + v.position.y, z + v.position.z);
      }      
   }
   
   public void beginStrip()
   {
      gl.glBegin(GL.GL_TRIANGLE_STRIP);
   }

   public void endStrip()
   {
      gl.glEnd();
   }

   public void fillStrip(Vertex[] vertices)
   {
      fillStrip(0f, 0f, 0f, vertices);
   }

   public void fillStrip(float x, float y, float z, Vertex[] vertices)
   {
      for (int i = 0; i < vertices.length; i++)
      {
         Vertex v = vertices[i];
         gl.glVertex3f(x + v.position.x, y + v.position.y, z + v.position.z);
      }
   }

   public void fillStrip(float x, float y, float z, float[] xs, float[] ys, int offset, int count)
   {
      for (int i = 0; i < count; i++)
      {
         gl.glVertex3f(x + xs[offset + i], y + ys[offset + i], z);
      }
   }

   public void fillStrip2D(float x, float y, float[] xs, float[] ys)
   {
      int limit = Math.min(xs.length, ys.length);
      for (int i = 0; i < limit; i++)
      {
         gl.glVertex3f(x + xs[i], y + ys[i], 0.0f);
      }
   }

   public void fillStrip2D(float x, float y)
   {
      gl.glVertex3f(x, y, 0.0f);
   }

   public void fillStrip2D(float x, float y, float[] xs, float[] ys, int offset, int count)
   {
      for (int i = 0; i < count; i++)
      {
         gl.glVertex3f(x + xs[offset + i], y + ys[offset + i], 0.0f);
      }
   }

   public void beginTriangles()
   {
      gl.glBegin(GL.GL_TRIANGLES);
   }

   public void endTriangles()
   {
      gl.glEnd();
   }

   public void fillTriangles(Vertex[] vertices)
   {
      fillTriangles(0f, 0f, 0f, vertices);
   }

   public void fillTriangles(float x, float y, float z, Vertex[] vertices)
   {
      for (int i = 0; i < vertices.length; i++)
      {
         Vertex v = vertices[i];
         gl.glVertex3f(x + v.position.x, y + v.position.y, z + v.position.z);
      }
   }

   public void beginQuads()
   {
      gl.glBegin(GL.GL_QUADS);
   }

   public void endQuads()
   {
      gl.glEnd();
   }

   public void fillQuads(Vertex[] vertices)
   {
      fillQuads(0f, 0f, 0f, vertices);
   }

   public void fillQuads(float x, float y, float z, Vertex[] vertices)
   {
      for (int i = 0; i < vertices.length; i++)
      {
         Vertex v = vertices[i];
         gl.glVertex3f(x + v.position.x, y + v.position.y, z + v.position.z);
      }
   }

   public void fillBar(float x0, float y0, float z0, float x1, float y1, float z1, ColorRGBA color, float[] colorScaling)
   {
      float scale;
      int i = 0;
      float r = color.getRed();
      float g = color.getGreen();
      float b = color.getBlue();

      if (y1 < y0)
      {
         float tmp = y1;
         y1 = y0;
         y0 = tmp;
      }
      
      // Front Face
      scale = colorScaling[i++];
      gl.glColor3f(r * scale, g * scale, b * scale);
      gl.glVertex3f(x0, y0, z1);
      gl.glVertex3f(x1, y0, z1);
      gl.glVertex3f(x1, y1, z1);
      gl.glVertex3f(x0, y1, z1);

      // Back Face
      scale = colorScaling[i++];
      gl.glColor3f(r * scale, g * scale, b * scale);
      gl.glVertex3f(x0, y0, z0);
      gl.glVertex3f(x0, y1, z0);
      gl.glVertex3f(x1, y1, z0);
      gl.glVertex3f(x1, y0, z0);

      // Top Face
      scale = colorScaling[i++];
      gl.glColor3f(r * scale, g * scale, b * scale);
      gl.glVertex3f(x0, y1, z0);
      gl.glVertex3f(x0, y1, z1);
      gl.glVertex3f(x1, y1, z1);
      gl.glVertex3f(x1, y1, z0);

      // Right face
      scale = colorScaling[i++];
      gl.glColor3f(r * scale, g * scale, b * scale);
      gl.glVertex3f(x1, y0, z0);
      gl.glVertex3f(x1, y1, z0);
      gl.glVertex3f(x1, y1, z1);
      gl.glVertex3f(x1, y0, z1);

      // Left Face
      scale = colorScaling[i++];
      gl.glColor3f(r * scale, g * scale, b * scale);
      gl.glVertex3f(x0, y0, z0);
      gl.glVertex3f(x0, y0, z1);
      gl.glVertex3f(x0, y1, z1);
      gl.glVertex3f(x0, y1, z0);
      
      // Bottom Face
      scale = colorScaling[i++];
      gl.glColor3f(r * scale, g * scale, b * scale);
      gl.glVertex3f(x0, y0, z0);
      gl.glVertex3f(x1, y0, z0);
      gl.glVertex3f(x1, y0, z1);
      gl.glVertex3f(x0, y0, z1);
   }

   public void fillSegment(float x0, float y0a, float y0b, float z0, float x1, float y1a, float y1b, float z1, ColorRGBA color, float[] colorScaling, boolean drawTop, boolean drawLeft, boolean drawRight)
   {
      float scale;
      int i = 0;
      float r = color.getRed();
      float g = color.getGreen();
      float b = color.getBlue();
      
      // Front Face
      scale = colorScaling[i++];
      gl.glColor3f(r * scale, g * scale, b * scale);
      gl.glVertex3f(x0, y0a, z1);
      gl.glVertex3f(x1, y0b, z1);
      gl.glVertex3f(x1, y1b, z1);
      gl.glVertex3f(x0, y1a, z1);

      // Back Face
      scale = colorScaling[i++];
      gl.glColor3f(r * scale, g * scale, b * scale);
      gl.glVertex3f(x0, y0a, z0);
      gl.glVertex3f(x0, y1a, z0);
      gl.glVertex3f(x1, y1b, z0);
      gl.glVertex3f(x1, y0b, z0);

      // Top Face
      scale = colorScaling[i++];
      if (drawTop)
      {
         gl.glColor3f(r * scale, g * scale, b * scale);
         gl.glVertex3f(x0, y1a, z0);
         gl.glVertex3f(x0, y1a, z1);
         gl.glVertex3f(x1, y1b, z1);
         gl.glVertex3f(x1, y1b, z0);
      }

      // Right face
      scale = colorScaling[i++];
      if (drawRight)
      {
         gl.glColor3f(r * scale, g * scale, b * scale);
         gl.glVertex3f(x1, y0b, z0);
         gl.glVertex3f(x1, y1b, z0);
         gl.glVertex3f(x1, y1b, z1);
         gl.glVertex3f(x1, y0b, z1);
      }

      // Left Face
      scale = colorScaling[i++];
      if (drawLeft)
      {
         gl.glColor3f(r * scale, g * scale, b * scale);
         gl.glVertex3f(x0, y0a, z0);
         gl.glVertex3f(x0, y0a, z1);
         gl.glVertex3f(x0, y1a, z1);
         gl.glVertex3f(x0, y1a, z0);
      }
   }


   public void fillCuboid(float x0, float y0, float z0, float x1, float y1, float z1)
   {
      gl.glBegin(GL.GL_QUADS);

      // Front Face
      gl.glNormal3f(0.0f, 0.0f, 1.0f);
      gl.glTexCoord2f(0.0f, 1.0f);
      gl.glVertex3f(x0, y0, z1);
      gl.glTexCoord2f(1.0f, 1.0f);
      gl.glVertex3f(x1, y0, z1);
      gl.glTexCoord2f(1.0f, 0.0f);
      gl.glVertex3f(x1, y1, z1);
      gl.glTexCoord2f(0.0f, 0.0f);
      gl.glVertex3f(x0, y1, z1);

      // Back Face
      gl.glNormal3f( 0.0f, 0.0f, -1.0f);
      gl.glTexCoord2f(1.0f, 1.0f);
      gl.glVertex3f(x0, y0, z0);
      gl.glTexCoord2f(1.0f, 0.0f);
      gl.glVertex3f(x0, y1, z0);
      gl.glTexCoord2f(0.0f, 0.0f);
      gl.glVertex3f(x1, y1, z0);
      gl.glTexCoord2f(0.0f, 1.0f);
      gl.glVertex3f(x1, y0, z0);

      // Top Face
      gl.glNormal3f( 0.0f, 1.0f, 0.0f);
      gl.glTexCoord2f(0.0f, 0.0f);
      gl.glVertex3f(x0, y1, z0);
      gl.glTexCoord2f(0.0f, 1.0f);
      gl.glVertex3f(x0, y1, z1);
      gl.glTexCoord2f(1.0f, 1.0f);
      gl.glVertex3f(x1, y1, z1);
      gl.glTexCoord2f(1.0f, 0.0f);
      gl.glVertex3f(x1, y1, z0);

      // Bottom Face
      gl.glNormal3f( 0.0f, -1.0f, 0.0f);
      gl.glTexCoord2f(0.0f, 1.0f);
      gl.glVertex3f(x0, y0, z0);
      gl.glTexCoord2f(1.0f, 1.0f);
      gl.glVertex3f(x1, y0, z0);
      gl.glTexCoord2f(1.0f, 0.0f);
      gl.glVertex3f(x1, y0, z1);
      gl.glTexCoord2f(0.0f, 0.0f);
      gl.glVertex3f(x0, y0, z1);

      // Right face
      gl.glNormal3f(1.0f, 0.0f, 0.0f);
      gl.glTexCoord2f(1.0f, 1.0f);
      gl.glVertex3f(x1, y0, z0);
      gl.glTexCoord2f(1.0f, 0.0f);
      gl.glVertex3f(x1, y1, z0);
      gl.glTexCoord2f(0.0f, 0.0f);
      gl.glVertex3f(x1, y1, z1);
      gl.glTexCoord2f(0.0f, 1.0f);
      gl.glVertex3f(x1, y0, z1);

      // Left Face
      gl.glNormal3f(-1.0f, 0.0f, 0.0f);
      gl.glTexCoord2f(0.0f, 1.0f);
      gl.glVertex3f(x0, y0, z0);
      gl.glTexCoord2f(1.0f, 1.0f);
      gl.glVertex3f(x0, y0, z1);
      gl.glTexCoord2f(1.0f, 0.0f);
      gl.glVertex3f(x0, y1, z1);
      gl.glTexCoord2f(0.0f, 0.0f);
      gl.glVertex3f(x0, y1, z0);

      gl.glEnd();
   }

   public void fillCuboid(float x0, float y0, float z0, float x1, float y1, float z1, ColorRGBA color, float[] colorScaling)
   {
      float scale;
      int i = 0;
      float r = color.getRed();
      float g = color.getGreen();
      float b = color.getBlue();

      gl.glBegin(GL.GL_QUADS);

      // Front Face
      scale = colorScaling[i++];
      gl.glColor3f(r * scale, g * scale, b * scale);
      gl.glNormal3f(0.0f, 0.0f, 1.0f);
      gl.glTexCoord2f(0.0f, 1.0f);
      gl.glVertex3f(x0, y0, z1);
      gl.glTexCoord2f(1.0f, 1.0f);
      gl.glVertex3f(x1, y0, z1);
      gl.glTexCoord2f(1.0f, 0.0f);
      gl.glVertex3f(x1, y1, z1);
      gl.glTexCoord2f(0.0f, 0.0f);
      gl.glVertex3f(x0, y1, z1);

      // Back Face
      scale = colorScaling[i++];
      gl.glColor3f(r * scale, g * scale, b * scale);
      gl.glNormal3f( 0.0f, 0.0f, -1.0f);
      gl.glTexCoord2f(1.0f, 1.0f);
      gl.glVertex3f(x0, y0, z0);
      gl.glTexCoord2f(1.0f, 0.0f);
      gl.glVertex3f(x0, y1, z0);
      gl.glTexCoord2f(0.0f, 0.0f);
      gl.glVertex3f(x1, y1, z0);
      gl.glTexCoord2f(0.0f, 1.0f);
      gl.glVertex3f(x1, y0, z0);

      // Top Face
      scale = colorScaling[i++];
      gl.glColor3f(r * scale, g * scale, b * scale);
      gl.glNormal3f( 0.0f, 1.0f, 0.0f);
      gl.glTexCoord2f(0.0f, 0.0f);
      gl.glVertex3f(x0, y1, z0);
      gl.glTexCoord2f(0.0f, 1.0f);
      gl.glVertex3f(x0, y1, z1);
      gl.glTexCoord2f(1.0f, 1.0f);
      gl.glVertex3f(x1, y1, z1);
      gl.glTexCoord2f(1.0f, 0.0f);
      gl.glVertex3f(x1, y1, z0);

      // Bottom Face
      scale = colorScaling[i++];
      gl.glColor3f(r * scale, g * scale, b * scale);
      gl.glNormal3f( 0.0f, -1.0f, 0.0f);
      gl.glTexCoord2f(0.0f, 1.0f);
      gl.glVertex3f(x0, y0, z0);
      gl.glTexCoord2f(1.0f, 1.0f);
      gl.glVertex3f(x1, y0, z0);
      gl.glTexCoord2f(1.0f, 0.0f);
      gl.glVertex3f(x1, y0, z1);
      gl.glTexCoord2f(0.0f, 0.0f);
      gl.glVertex3f(x0, y0, z1);

      // Right face
      scale = colorScaling[i++];
      gl.glColor3f(r * scale, g * scale, b * scale);
      gl.glNormal3f(1.0f, 0.0f, 0.0f);
      gl.glTexCoord2f(1.0f, 1.0f);
      gl.glVertex3f(x1, y0, z0);
      gl.glTexCoord2f(1.0f, 0.0f);
      gl.glVertex3f(x1, y1, z0);
      gl.glTexCoord2f(0.0f, 0.0f);
      gl.glVertex3f(x1, y1, z1);
      gl.glTexCoord2f(0.0f, 1.0f);
      gl.glVertex3f(x1, y0, z1);

      // Left Face
      scale = colorScaling[i++];
      gl.glColor3f(r * scale, g * scale, b * scale);
      gl.glNormal3f(-1.0f, 0.0f, 0.0f);
      gl.glTexCoord2f(0.0f, 1.0f);
      gl.glVertex3f(x0, y0, z0);
      gl.glTexCoord2f(1.0f, 1.0f);
      gl.glVertex3f(x0, y0, z1);
      gl.glTexCoord2f(1.0f, 0.0f);
      gl.glVertex3f(x0, y1, z1);
      gl.glTexCoord2f(0.0f, 0.0f);
      gl.glVertex3f(x0, y1, z0);

      gl.glEnd();
   }

   public void fillRectangle2D(float x0, float y0, float x1, float y1)
   {
      gl.glBegin(GL.GL_QUADS);
      gl.glTexCoord2f(0.0f, 1.0f);
      gl.glVertex3f(x0, y0, 0f);
      gl.glTexCoord2f(1.0f, 1.0f);
      gl.glVertex3f(x1, y0, 0f);
      gl.glTexCoord2f(1.0f, 0.0f);
      gl.glVertex3f(x1, y1, 0f);
      gl.glTexCoord2f(0.0f, 0.0f);
      gl.glVertex3f(x0, y1, 0f);
      gl.glEnd();
   }

   public void fillRectangleXZ(float x0, float z0, float x1, float z1)
   {
      gl.glBegin(GL.GL_QUADS);

      // Top Face
      gl.glNormal3f(0.0f, 1.0f, 0.0f);
      gl.glTexCoord2f(0.0f, 0.0f);
      gl.glVertex3f(x0, 0.0f, z0);
      gl.glTexCoord2f(0.0f, 1.0f);
      gl.glVertex3f(x0, 0.0f, z1);
      gl.glTexCoord2f(1.0f, 1.0f);
      gl.glVertex3f(x1, 0.0f, z1);
      gl.glTexCoord2f(1.0f, 0.0f);
      gl.glVertex3f(x1, 0.0f, z0);

      gl.glEnd();
   }

   public void fillRectangleXZ(float x0, float z0, float x1, float z1, float y)
   {
      gl.glBegin(GL.GL_QUADS);

      // Top Face
      gl.glNormal3f(0.0f, 1.0f, 0.0f);
      gl.glTexCoord2f(0.0f, 0.0f);
      gl.glVertex3f(x0, y, z0);
      gl.glTexCoord2f(0.0f, 1.0f);
      gl.glVertex3f(x0, y, z1);
      gl.glTexCoord2f(1.0f, 1.0f);
      gl.glVertex3f(x1, y, z1);
      gl.glTexCoord2f(1.0f, 0.0f);
      gl.glVertex3f(x1, y, z0);

      gl.glEnd();
   }

   public void fillRectangleXY(float x0, float y0, float x1, float y1)
   {
      gl.glBegin(GL.GL_QUADS);

      // Top Face
      gl.glNormal3f(0.0f, 1.0f, 0.0f);
      gl.glTexCoord2f(0.0f, 0.0f);
      gl.glVertex3f(x0, y0, 0.0f);
      gl.glTexCoord2f(0.0f, 1.0f);
      gl.glVertex3f(x0, y1, 0.0f);
      gl.glTexCoord2f(1.0f, 1.0f);
      gl.glVertex3f(x1, y1, 0.0f);
      gl.glTexCoord2f(1.0f, 0.0f);
      gl.glVertex3f(x1, y0, 0.0f);

      gl.glEnd();
   }

   public void fillRectangleXY(float x0, float y0, float x1, float y1, float z)
   {
      gl.glBegin(GL.GL_QUADS);

      // Top Face
      gl.glNormal3f(0.0f, 1.0f, 0.0f);
      gl.glTexCoord2f(0.0f, 0.0f);
      gl.glVertex3f(x0, y0, z);
      gl.glTexCoord2f(0.0f, 1.0f);
      gl.glVertex3f(x0, y1, z);
      gl.glTexCoord2f(1.0f, 1.0f);
      gl.glVertex3f(x1, y1, z);
      gl.glTexCoord2f(1.0f, 0.0f);
      gl.glVertex3f(x1, y0, z);

      gl.glEnd();
   }

   public void fillRectangleYZ(float y0, float z0, float y1, float z1)
   {
      gl.glBegin(GL.GL_QUADS);

      // Top Face
      gl.glNormal3f(0.0f, 1.0f, 0.0f);
      gl.glTexCoord2f(0.0f, 0.0f);
      gl.glVertex3f(0.0f, y0, z0);
      gl.glTexCoord2f(0.0f, 1.0f);
      gl.glVertex3f(0.0f, y0, z1);
      gl.glTexCoord2f(1.0f, 1.0f);
      gl.glVertex3f(0.0f, y1, z1);
      gl.glTexCoord2f(1.0f, 0.0f);
      gl.glVertex3f(0.0f, y1, z0);

      gl.glEnd();
   }

   // ------ TEXT --------------------------------------

   protected TextRenderer getFontRenderer2D(int fontType)
   {
      switch (fontType)
      {
      case FONT_TITLE:
         return titleFontRenderer;
      case FONT_LABEL:
         return label2DFontRenderer;
      case FONT_LEGEND:
         return legendFontRenderer;
      case FONT_DEFAULT:
      default:
         return default2DFontRenderer;
      }
   }

   public void beginText2D(int fontType, int width, int height)
   {
      currentFontRenderer = getFontRenderer2D(fontType);
      currentFontRenderer.beginRendering(width, height);
   }

   public void drawString2D(String str, int x, int y)
   {
      currentFontRenderer.draw(str, x, y);
   }

   public void endText2D()
   {
      currentFontRenderer.endRendering();
   }

   protected TextRenderer getFontRenderer3D(int fontType)
   {
      switch (fontType)
      {
      case FONT_TITLE:
         return titleFontRenderer;
      case FONT_LABEL:
         return label3DFontRenderer;
      case FONT_LEGEND:
         return legendFontRenderer;
      case FONT_SMALL_LABEL:
         return smallLabel3DFontRenderer; 
      case FONT_BIG_LABEL:
         return bigLabel3DFontRenderer;
      case FONT_DEFAULT:
      default:
         return default3DFontRenderer;
      }
   }

   public void beginText3D(int fontType)
   {
      currentFontRenderer = getFontRenderer3D(fontType);
      currentFontRenderer.begin3DRendering();
   }

   public void drawString3D(String str, float x, float y, float z, float scale)
   {
      currentFontRenderer.draw3D(str, x, y, z, scale);
   }

   public void endText3D()
   {
      currentFontRenderer.end3DRendering();
   }

   public void setTextColor(ColorRGBA color)
   {
      currentFontRenderer.setColor(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha());
   }

   public void setTextColor(float r, float g, float b)
   {
      currentFontRenderer.setColor(r, g, b, 1.0f);
   }

   public void setTextColor(float r, float g, float b, float a)
   {
      currentFontRenderer.setColor(r, g, b, a);
   }

   public Rectangle2D getTextBounds(String str)
   {
      java.awt.geom.Rectangle2D bounds = currentFontRenderer.getBounds(str);
      Rectangle2D rect = new Rectangle2D(bounds.getX(), bounds.getY(),
                                         bounds.getWidth(), bounds.getHeight());
      return rect;
   }
   
   public Rectangle2D getTextBounds2D(int fontType, String str)
   {
      java.awt.geom.Rectangle2D bounds =  getFontRenderer2D(fontType).getBounds(str);
      Rectangle2D rect = new Rectangle2D(bounds.getX(), bounds.getY(),
            bounds.getWidth(), bounds.getHeight());
      return rect;
   }

   public Rectangle2D getTextBounds3D(int fontType, String str)
   {
      java.awt.geom.Rectangle2D bounds =  getFontRenderer3D(fontType).getBounds(str);
      Rectangle2D rect = new Rectangle2D(bounds.getX(), bounds.getY(),
            bounds.getWidth(), bounds.getHeight());
      return rect;
   }
}
