/* COPYRIGHT-ENEA-SRC-R2 *
 **************************************************************************
 * Copyright (C) 2011 by Enea Software AB.
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

package com.ose.perf.ui.editor.nodes;

import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;
import com.ose.perf.ui.SharedImages;

public class BinaryNode extends TreeNode
{
   private final String path;
   private final String filename;
   private final Color color = new Color(Display.getCurrent(), 146, 182, 16);

   public BinaryNode(String path)
   {
      super();
      this.path = path;

      String[] pathParts = path.split("\\\\|/");
      if (pathParts.length > 0)
      {
         filename = pathParts[pathParts.length - 1];
      }
      else
      {
         filename = path;
      }
   }

   protected void finalize()
   {
      color.dispose();
   }

   public String getPath()
   {
      return path;
   }

   public String getFilename()
   {
      return filename;
   }

   public Image getImage()
   {
      return SharedImages.get(SharedImages.IMG_OBJ_BINARY_FILE);
   }

   public Color getColor()
   {
      return color;
   }

   public String toString()
   {
      return filename;
   }
}
