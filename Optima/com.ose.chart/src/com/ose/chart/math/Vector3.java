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
 * Represents a coordinate vector in 3-dimensional Euclidean space.
 *
 * @see <a href="http://en.wikipedia.org/wiki/Coordinate_vector">Coordinate vector</a>
 * @see <a href="http://en.wikipedia.org/wiki/Euclidean_space">Euclidean space</a>
 */
public class Vector3
{
   public static final Vector3 ZERO = new Vector3(0f, 0f, 0f);

   public float x;
   public float y;
   public float z;

   public Vector3()
   {
      set(0f, 0f, 0f);
   }

   public Vector3(Vector3 v)
   {
      set(v);
   }

   public Vector3(float x, float y, float z)
   {
      set(x, y, z);
   }

   public Vector3 set(Vector3 v)
   {
      x = v.x;
      y = v.y;
      z = v.z;
      return this;
   }

   public Vector3 set(float x, float y, float z)
   {
      this.x = x;
      this.y = y;
      this.z = z;
      return this;
   }

   public Vector3 add(Vector3 v)
   {
      return add(v, this);
   }

   public Vector3 add(Vector3 v, Vector3 out)
   {
      return out.set(x + v.x, y + v.y, z + v.z);
   }

   public Vector3 add(float scalar)
   {
      return add(scalar, this);
   }

   public Vector3 add(float scalar, Vector3 out)
   {
      return out.set(x + scalar, y + scalar, z + scalar);
   }

   public Vector3 add(float x, float y, float z)
   {
      return add(x, y, z, this);
   }

   public Vector3 add(float x, float y, float z, Vector3 out)
   {
      return out.set(this.x + x, this.y + y, this.z + z);
   }   
   
   public Vector3 cross(Vector3 v)
   {
      return cross(v, this);
   }

   public Vector3 cross(Vector3 v, Vector3 out)
   {
      return out.set(y * v.z - z * v.y, z * v.x - x * v.z, x * v.y - y * v.x);
   }

   public float distance(Vector3 v)
   {
      return (float) Math.sqrt(distanceSquared(v));
   }

   public float distanceSquared(Vector3 v)
   {
      float dx = v.x - x;
      float dy = v.y - y;
      float dz = v.z - z;

      return dx * dx + dy * dy + dz * dz;
   }

   public Vector3 divide(Vector3 v)
   {
      return divide(v, this);
   }

   public Vector3 divide(Vector3 v, Vector3 out)
   {
      return out.set(x / v.x, y / v.y, z / v.z);
   }

   public Vector3 divide(float scalar)
   {
      return divide(scalar, this);
   }

   public Vector3 divide(float scalar, Vector3 out)
   {
      return out.set(x / scalar, y / scalar, z / scalar);
   }

   public float dot(Vector3 v)
   {
      return x * v.x + y * v.y + z * v.z;
   }

   public boolean equals(Vector3 v)
   {
      return x == v.x && y == v.y && z == v.z;
   }

   public float length()
   {
      return (float) Math.sqrt(lengthSquared());
   }

   public float lengthSquared()
   {
      return x * x + y * y + z * z;
   }

   public Vector3 multiply(Vector3 v)
   {
      return multiply(v, this);
   }

   public Vector3 multiply(Vector3 v, Vector3 out)
   {
      return out.set(x * v.x, y * v.y, z * v.z);
   }

   public Vector3 multiply(float scalar)
   {
      return multiply(scalar, this);
   }

   public Vector3 multiply(float scalar, Vector3 out)
   {
      return out.set(x * scalar, y * scalar, z * scalar);
   }

   public Vector3 negate()
   {
      return negate(this);
   }

   public Vector3 negate(Vector3 out)
   {
      return out.set(-x, -y, -z);
   }

   public Vector3 normalize()
   {
      return normalize(this);
   }

   public Vector3 normalize(Vector3 out)
   {
      float inverseLength = 1.0f / length();
      return out.set(x * inverseLength, y * inverseLength, z * inverseLength);
   }

   public Vector3 subtract(Vector3 v)
   {
      return subtract(v, this);
   }

   public Vector3 subtract(Vector3 v, Vector3 out)
   {
      return out.set(x - v.x, y - v.y, z - v.z);
   }

   public Vector3 subtract(float scalar)
   {
      return subtract(scalar, this);
   }

   public Vector3 subtract(float scalar, Vector3 out)
   {
      return out.set(x - scalar, y - scalar, z - scalar);
   }
   
   public Vector3 subtract(float x, float y, float z)
   {
      return subtract(x, y, z, this);
   }

   public Vector3 subtract(float x, float y, float z, Vector3 out)
   {
      return out.add(this.x - x, this.y - y, this.z - z);
   }
}
