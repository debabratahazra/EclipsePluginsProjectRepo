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

package com.ose.chart.math;

/**
 * Represents a coordinate vector in 2-dimensional Euclidean space.
 * 
 * @see <a href="http://en.wikipedia.org/wiki/Coordinate_vector">Coordinate vector</a>
 * @see <a href="http://en.wikipedia.org/wiki/Euclidean_space">Euclidean space</a>
 */
public class Vector2
{
   public static final Vector2 ZERO = new Vector2(0f, 0f);

   public float x;
   public float y;

   public Vector2()
   {
      set(0f, 0f);
   }

   public Vector2(Vector2 v)
   {
      set(v);
   }

   public Vector2(float x, float y)
   {
      set(x, y);
   }

   public Vector2 set(Vector2 v)
   {
      x = v.x;
      y = v.y;
      return this;
   }

   public Vector2 set(float x, float y)
   {
      this.x = x;
      this.y = y;
      return this;
   }

   public Vector2 add(Vector2 v)
   {
      return add(v, this);
   }

   public Vector2 add(Vector2 v, Vector2 out)
   {
      return out.set(x + v.x, y + v.y);
   }

   public Vector2 add(float scalar)
   {
      return add(scalar, this);
   }

   public Vector2 add(float scalar, Vector2 out)
   {
      return out.set(x + scalar, y + scalar);
   }
   
   public Vector2 add(float x, float y)
   {
      return add(x, y, this);
   }
   
   public Vector2 add(float x, float y, Vector2 out)
   {
      return out.set(this.x + x, this.y + y);
   }

   public float cross(Vector2 v)
   {
      return cross(v, this);
   }

   public float cross(Vector2 v, Vector2 out)
   {
      return x * v.y - y * v.x;
   }

   public float distance(Vector2 v)
   {
      return (float) Math.sqrt(distanceSquared(v));
   }

   public float distanceSquared(Vector2 v)
   {
      float dx = v.x - x;
      float dy = v.y - y;

      return dx * dx + dy * dy;
   }

   public Vector2 divide(Vector2 v)
   {
      return divide(v, this);
   }

   public Vector2 divide(Vector2 v, Vector2 out)
   {
      return out.set(x / v.x, y / v.y);
   }

   public Vector2 divide(float scalar)
   {
      return divide(scalar, this);
   }

   public Vector2 divide(float scalar, Vector2 out)
   {
      return out.set(x / scalar, y / scalar);
   }

   public float dot(Vector2 v)
   {
      return x * v.x + y * v.y;
   }

   public boolean equals(Vector2 v)
   {
      return x == v.x && y == v.y;
   }

   public float length()
   {
      return (float) Math.sqrt(lengthSquared());
   }

   public float lengthSquared()
   {
      return x * x + y * y;
   }

   public Vector2 multiply(Vector2 v)
   {
      return multiply(v, this);
   }

   public Vector2 multiply(Vector2 v, Vector2 out)
   {
      return out.set(x * v.x, y * v.y);
   }

   public Vector2 multiply(float scalar)
   {
      return multiply(scalar, this);
   }

   public Vector2 multiply(float scalar, Vector2 out)
   {
      return out.set(x * scalar, y * scalar);
   }

   public Vector2 negate()
   {
      return negate(this);
   }

   public Vector2 negate(Vector2 out)
   {
      return out.set(-x, -y);
   }

   public Vector2 normalize()
   {
      return normalize(this);
   }

   public Vector2 normalize(Vector2 out)
   {
      float inverseLength = 1.0f / length();
      return out.set(x * inverseLength, y * inverseLength);
   }

   public Vector2 subtract(Vector2 v)
   {
      return subtract(v, this);
   }

   public Vector2 subtract(Vector2 v, Vector2 out)
   {
      return out.set(x - v.x, y - v.y);
   }

   public Vector2 subtract(float scalar)
   {
      return subtract(scalar, this);
   }

   public Vector2 subtract(float scalar, Vector2 out)
   {
      return out.set(x - scalar, y - scalar);
   }
   
   public Vector2 subtract(float x, float y)
   {
      return subtract(x, y, this);
   }
   
   public Vector2 subtract(float x, float y, Vector2 out)
   {
      return out.set(this.x - x, this.y - y);
   }
}
