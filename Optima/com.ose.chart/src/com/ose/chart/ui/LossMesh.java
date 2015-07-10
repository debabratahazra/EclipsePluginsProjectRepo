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

/**
 * Represents a 3D mesh used to indicate the loss of data, or that no
 * data is available for a particular item.
 */
class LossMesh implements Drawable
{
   private Vertex[] vertices;
   private ColorRGBA color;
   
   public LossMesh(float width, float depth)
   {
      this(width, depth, new ColorRGBA(0.86f, 0.1f, 0.22f));
   }
   
   public LossMesh(float width, float depth, ColorRGBA color)
   {
      setColor(color);
      
      vertices = new Vertex[18];
      
      for (int i = 0; i < vertices.length; i++)
      {
         vertices[i] = new Vertex(Vertex.POSITION);
      }

      vertices[0].position.set(width * 0.5f, 0.0f, depth * 0.5f);
      vertices[1].position.set(width * 0.5f, 0.0f, depth * 0.75f);
      vertices[2].position.set(width * 0.75f, 0.0f, depth * 1.0f);
      vertices[3].position.set(width * 1.0f, 0.0f, depth * 1.0f);
      vertices[4].position.set(width * 1.0f, 0.0f, depth * 0.75f);
      vertices[5].position.set(width * 0.75f, 0.0f, depth * 0.5f);
      vertices[6].position.set(width * 1.0f, 0.0f, depth * 0.25f);
      vertices[7].position.set(width * 1.0f, 0.0f, depth * 0.0f);
      vertices[8].position.set(width * 0.75f, 0.0f, depth * 0.0f);
      vertices[9].position.set(width * 0.5f, 0.0f, depth * 0.25f);
      vertices[10].position.set(width * 0.25f, 0.0f, depth * 0.0f);
      vertices[11].position.set(width * 0.0f, 0.0f, depth * 0.0f);
      vertices[12].position.set(width * 0.0f, 0.0f, depth * 0.25f);
      vertices[13].position.set(width * 0.25f, 0.0f, depth * 0.5f);
      vertices[14].position.set(width * 0.0f, 0.0f, depth * 0.75f);
      vertices[15].position.set(width * 0.0f, 0.0f, depth * 1.0f);
      vertices[16].position.set(width * 0.25f, 0.0f, depth * 1.0f);
      vertices[17] = vertices[1];
   }
   
   public void setColor(ColorRGBA color)
   {
      this.color = color;
   }
   
   public ColorRGBA getColor()
   {
      return color;
   }
   
   public void draw(GraphicsContext g)
   {
      drawAt(g, 0, 0, 0);
   }

   public void drawAt(GraphicsContext g, float x, float y, float z)
   {
      g.beginFan();
      g.fillFan(x, y, z, vertices, color);
      g.endFan();      
   }  
}
