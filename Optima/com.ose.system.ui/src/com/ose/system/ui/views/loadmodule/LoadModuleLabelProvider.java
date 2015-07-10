/* COPYRIGHT-ENEA-SRC-R2 *
 **************************************************************************
 * Copyright (C) 2005-2007 by Enea Software AB.
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

package com.ose.system.ui.views.loadmodule;

import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;
import com.ose.system.LoadModuleInfo;
import com.ose.system.ui.SharedImages;
import com.ose.system.ui.util.StringUtils;

public class LoadModuleLabelProvider extends LabelProvider
   implements ITableLabelProvider
{
   public String getColumnText(Object obj, int index)
   {
      if (!(obj instanceof LoadModuleInfo))
      {
         return null;
      }

      LoadModuleInfo loadModule = (LoadModuleInfo) obj;
      switch (index)
      {
         case LoadModuleView.COLUMN_INSTALL_HANDLE:
            return loadModule.getInstallHandle();
         case LoadModuleView.COLUMN_ENTRYPOINT:
            return StringUtils.toHexString(loadModule.getEntrypointLong());
         case LoadModuleView.COLUMN_OPTIONS:
            return StringUtils.toLoadModuleOptionsString(loadModule.getOptions());
         case LoadModuleView.COLUMN_TEXT_BASE:
            return StringUtils.toHexString(loadModule.getTextBaseLong());
         case LoadModuleView.COLUMN_TEXT_SIZE:
            return Long.toString(loadModule.getTextSizeLong());
         case LoadModuleView.COLUMN_DATA_BASE:
            return StringUtils.toHexString(loadModule.getDataBaseLong());
         case LoadModuleView.COLUMN_DATA_SIZE:
            return Long.toString(loadModule.getDataSizeLong());
         case LoadModuleView.COLUMN_NUM_SECTIONS:
            return Integer.toString(loadModule.getNumSections());
         case LoadModuleView.COLUMN_NUM_INSTANCES:
            return Integer.toString(loadModule.getNumInstances());
         case LoadModuleView.COLUMN_FILE_FORMAT:
            return loadModule.getFileFormat();
         case LoadModuleView.COLUMN_FILE_NAME:
            return StringUtils.removeNetworkPathPrefix(loadModule.getFileName());
         default:
            return null;
      }
   }

   public Image getColumnImage(Object obj, int index)
   {
      if ((obj instanceof LoadModuleInfo) &&
          (index == LoadModuleView.COLUMN_INSTALL_HANDLE))
      {
         return SharedImages.get(SharedImages.IMG_OBJ_LOAD_MODULE);
      }
      else
      {
         return null;
      }
   }
}
