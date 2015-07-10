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
 * A vertex represents a corner point of a polygon in 3D space. The Vertex class
 * groups a number of properties of this point, such as color, texture
 * coordinates and surface normal. Operations on a vertex need to be as direct
 * as possible which is why its fields are all public.
 * 
 * @see <a href="http://en.wikipedia.org/wiki/Vertex_%28geometry%29">Vertex (geometry)</a>
 */
class Vertex
{
   public static final int POSITION = 1;
   public static final int NORMAL = 2;
   public static final int TEXTURE = 4;
   public static final int COLOR = 8;

   public int fields;
   public Vector3 position;
   public Vector3 normal;
   public Vector2 texture;
   public ColorRGBA color;

   public Vertex()
   {
      this(POSITION);
   }

   public Vertex(int fields)
   {
      this.fields = fields;
      position = new Vector3();
      normal = new Vector3();
      texture = new Vector2();
      color = new ColorRGBA();
   }
}
