/* COPYRIGHT-ENEA-SRC-R2 *
 **************************************************************************
 * Copyright (C) 2010 by Enea Software AB.
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

package com.ose.ui.tests.utils;

import org.eclipse.swt.graphics.Image;

public class TreeData
{   
   String id;
   String name;
   Image img;
   int index;
   
   public TreeData(String id, String name, Image img, int index)
   {
      this.id = id;
      this.name = name;
      this.img = img;
      this.index = index;
   }
   
   public String getName()
   {
      return name;
   }  
   
   public String getId()
   {
      return id;
   }
   
   public void setId(String id)
   {
      this.id = id;
   }
   
   public void setName(String name)
   {
      this.name = name;
   }
   
   public void setImg(Image img)
   {
      this.img = img;
   }
   
   public Image getImg()
   {
      return img;
   }
   
   public int getIndex()
   {
      return index;
   }
}
