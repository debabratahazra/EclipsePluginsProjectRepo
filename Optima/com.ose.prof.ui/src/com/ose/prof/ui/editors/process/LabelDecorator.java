/* COPYRIGHT-ENEA-SRC-R2 *
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

package com.ose.prof.ui.editors.process;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.ILabelDecorator;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.LabelProviderChangedEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.ui.IDecoratorManager;
import com.ose.prof.ui.ProfilerPlugin;

public class LabelDecorator extends LabelProvider implements ILabelDecorator
{
   public static final Point SIZE = new Point(16, 16);

   private ImageCache imageCache;

   public LabelDecorator()
   {
      imageCache = new ImageCache();
   }

   public static LabelDecorator getLabelDecorator()
   {
      IDecoratorManager decoratorManager =
         ProfilerPlugin.getDefault().getWorkbench().getDecoratorManager();
      ILabelDecorator decorator =
         decoratorManager.getLabelDecorator(ProfilerPlugin.PROCESS_DECORATOR_ID);
      return (LabelDecorator) decorator;
   }

   public Image decorateImage(Image image, Object element)
   {
      if (element instanceof IResource)
      {
         IResource resource = (IResource) element;
         
         IProject project = resource.getProject();
         if(!project.isOpen())
         {
            return null;
         }
         
         try
         {
            IMarker[] markers = resource.findMarkers(IMarker.PROBLEM, false,
                  IResource.DEPTH_INFINITE);

            if (markers.length > 0 && resource.exists())
            {
               ImageDescriptor descriptor = new ErrorDecoratedImageDescriptor(image);
               return imageCache.getImage(descriptor);
            }
         }
         catch (CoreException e)
         {
            ProfilerPlugin.errorDialog("Error", e.getMessage(), e);
         }
      }

      return null;
   }

   public String decorateText(String text, Object element)
   {
      return null;
   }

   public void refresh(IResource[] resources)
   {
      ILabelDecorator decorator = getLabelDecorator();
      if (decorator == null)
      {
         return;
      }

      LabelProviderChangedEvent event = new LabelProviderChangedEvent(
            decorator, resources);
      fireLabelProviderChanged(event);
   }

   public void dispose()
   {
      imageCache.dispose();
   }
}
