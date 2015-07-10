/* COPYRIGHT-ENEA-SRC-R2 *
 **************************************************************************
 * Copyright (C) 2009 by Enea Software AB.
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

package com.ose.prof.ui.editors.profiler;

import java.net.URL;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.resource.CompositeImageDescriptor;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.BaseLabelProvider;
import org.eclipse.jface.viewers.IDecoration;
import org.eclipse.jface.viewers.ILightweightLabelDecorator;
import org.eclipse.jface.viewers.LabelProviderChangedEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IDecoratorManager;
import org.eclipse.ui.PlatformUI;
import org.osgi.framework.Bundle;
import com.ose.prof.ui.ProfilerPlugin;

public class SynchronizedScrollPartDecorator extends BaseLabelProvider
   implements ILightweightLabelDecorator
{
   private final ImageDescriptor baseImageDesc;

   private final ImageDescriptor overlayImageDesc;

   private final Image decoratedImage;

   public SynchronizedScrollPartDecorator()
   {
      Bundle bundle;
      URL imageURL;

      bundle = Platform.getBundle(ProfilerPlugin.getUniqueIdentifier());
      imageURL = bundle.getEntry("/icons/elcl16/prof.gif");
      baseImageDesc = ImageDescriptor.createFromURL(imageURL);
      imageURL = bundle.getEntry("/icons/obj16/sync_scroll_part.gif");
      overlayImageDesc = ImageDescriptor.createFromURL(imageURL);
      decoratedImage = (new DecoratedImageDescriptor()).createImage();
   }

   public static SynchronizedScrollPartDecorator getManagedInstance()
   {
      IDecoratorManager decoratorManager =
         PlatformUI.getWorkbench().getDecoratorManager();
      return (SynchronizedScrollPartDecorator) decoratorManager
         .getBaseLabelProvider(ProfilerPlugin.SYNCHRONIZED_EDITOR_DECORATOR_ID);
   }

   public Image getDecoratedImage()
   {
      return decoratedImage;
   }

   public void refresh(IFile file)
   {
      Display.getDefault().asyncExec(new UpdateDecoratorRunner(this, file));
   }

   public boolean isLabelProperty(Object element, String property)
   {
      return false;
   }

   public void decorate(Object element, IDecoration decoration)
   {
      if (element instanceof IFile)
      {
         IFile file = (IFile) element;
         if (SynchronizedScrollPartController.getInstance().isFileSynchronized(file))
         {
            decoration.addOverlay(overlayImageDesc, IDecoration.TOP_RIGHT);
         }
      }
   }

   public void dispose()
   {
      super.dispose();
      decoratedImage.dispose();
   }

   private static class UpdateDecoratorRunner implements Runnable
   {
      private final SynchronizedScrollPartDecorator decorator;

      private final IFile file;

      UpdateDecoratorRunner(SynchronizedScrollPartDecorator decorator,
                            IFile file)
      {
         this.decorator = decorator;
         this.file = file;
      }

      public void run()
      {
         LabelProviderChangedEvent event =
            new LabelProviderChangedEvent(decorator, file);
         decorator.fireLabelProviderChanged(event);
      }
   }

   private class DecoratedImageDescriptor extends CompositeImageDescriptor
   {
      protected void drawCompositeImage(int width, int height)
      {
         ImageData base = baseImageDesc.getImageData();
         ImageData overlay = overlayImageDesc.getImageData();
         drawImage(base, 0, 0);
         drawImage(overlay, base.width - overlay.width, 0);
      }

      protected Point getSize()
      {
         return new Point(16, 16);
      }
   }
}
