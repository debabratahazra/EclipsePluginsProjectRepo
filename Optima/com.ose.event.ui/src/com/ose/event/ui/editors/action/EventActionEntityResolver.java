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

package com.ose.event.ui.editors.action;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.URL;
import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import com.ose.event.ui.EventPlugin;

public class EventActionEntityResolver implements EntityResolver
{
   public InputSource resolveEntity(String publicId, String systemId)
         throws SAXException, IOException
   {
      if (systemId.endsWith("eventaction.dtd"))
      {
         URL url = EventPlugin.getDefault().getBundle().getEntry("eventaction.dtd");
         return new InputSource(new BufferedInputStream(url.openStream()));
      }
      else
      {
         return null;
      }
   }
}
