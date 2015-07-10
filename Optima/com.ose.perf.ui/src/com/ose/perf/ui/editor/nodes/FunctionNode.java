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

public class FunctionNode extends TreeNode
{
   private final String functionName;
   private final String path;
   private final String filename;
   private final Color color = new Color(Display.getCurrent(), 177, 220, 4);

   public FunctionNode(BinaryNode parent, String functionName, String path)
   {
      super(parent);
      this.functionName = functionName;
      this.path = path;

      if (path != null)
      {
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
      else
      {
         filename = null;
      }
   }

   protected void finalize()
   {
      color.dispose();
   }

   public String getFunctionName()
   {
      return functionName;
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
      return SharedImages.get(SharedImages.IMG_OBJ_FUNCTION);
   }

   public Color getColor()
   {
      return color;
   }

   public String toString()
   {
      if (filename != null)
      {
         return functionName + " (" + filename + ")";
      }
      else
      {
         return functionName;
      }
   }
}
