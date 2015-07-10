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

import com.ose.chart.math.Vector3;

/**
 * A camera that orbits a pivot point in 3D space.
 */
public class OrbitCamera implements Camera
{
   private Vector3 rotation;
   private Vector3 translation;
   private Vector3 pivot;
   private Vector3 negativePivot;
   
   // 45 degrees field of view.
   private float fieldOfView;
   
   // NEAR AND FAR CLIPPING PLANES
   // ----------------------------
   // Keep in mind that the distance between the clipping planes
   // effectively determines the accuracy of the depth buffer. Some
   // old GPUs may only support 16 bit depth buffers in windowed mode
   // and if the distance between the planes is too big, the accuracy
   // will suffer severely.

   // Distance to near clipping plane.
   private float nearPlane;

   // Distance to far clipping plane.
   private float farPlane;
   
   public OrbitCamera()
   {
      // Defaults
      this(45.0f, 1.0f, 800.0f);
   }
   
   public OrbitCamera(float fieldOfView, float nearPlane, float farPlane)
   {
      this.fieldOfView = fieldOfView;
      this.nearPlane = nearPlane;
      this.farPlane = farPlane;
      
      rotation = new Vector3(0.0f, 0.0f, 0.0f);
      translation = new Vector3(0.0f, 0.0f, 0.0f);
      pivot = new Vector3(0.0f, 0.0f, 0.0f);
      negativePivot = new Vector3(0.0f, 0.0f, 0.0f);   
   }
   
   public void setPivotPoint(Vector3 v)
   {
      pivot.set(v);
      negativePivot.set(v).negate();
   }

   public void setPivotPoint(float x, float y, float z)
   {
      pivot.set(x, y, z);
      negativePivot.set(x, y, z).negate();
   }

   public void getPivotPoint(Vector3 output)
   {
      output.set(pivot);
   }
   
   public void setTranslation(Vector3 v)
   {
      translation.set(v);
   }
   
   public void setTranslation(float x, float y, float z)
   {
      translation.set(x, y, z);
   }
   
   public void getTranslation(Vector3 output)
   {
      output.set(translation);
   }

   public void setRotation(float x, float y)
   {
      rotation.set(x, y, 0);
      constrainOrbit();
   }
   
   public void getRotation(Vector3 output)
   {
      output.set(rotation);
   }
   
   public void pan(float dx, float dy)
   {
      translation.add(dx, dy, 0);
   }
   
   public void orbit(float dx, float dy)
   {
      rotation.add(dx, dy, 0);
      constrainOrbit();
   }
   
   public void zoom(float dz)
   {
      translation.add(0, 0, dz);
   }
   
   public void applyProjectionTransform(GraphicsContext g, int width, int height)
   {
      // Calculate the aspect ratio
      float aspectRatio = (float)width / (float)height;

      g.setPerspectiveProjection(fieldOfView, aspectRatio, nearPlane, farPlane);
   }

   public void applyWorldViewTransform(GraphicsContext g, int width, int height)
   {
      g.translate(translation);
      g.rotate(rotation.x, 1.0f, 0.0f, 0.0f);
      g.rotate(rotation.y, 0.0f, 1.0f, 0.0f);
      g.translate(negativePivot);
   }
   
   private void constrainOrbit()
   {
      if (rotation.x < -90.0f)
      {
         rotation.x = -90.0f;
      }
      if (rotation.x > 90.0f)
      {
         rotation.x = 90.0f;
      }
   }
}
