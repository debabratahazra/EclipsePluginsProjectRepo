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

package com.ose.system;

import com.ose.system.service.pm.ProgramManager;

/**
 * This class contains information about an OSE load module. A LoadModuleInfo
 * object is immutable and its content is a snapshot at the time of the call of
 * the OSE system model method it was returned from.
 *
 * @see com.ose.system.Target#getLoadModuleInfo(IProgressMonitor, String)
 * @see com.ose.system.Target#getLoadModuleInfo(IProgressMonitor)
 */
public class LoadModuleInfo
{
   /* Installation options. */

   /** Load module is persistent. */
   public static final int LOAD_MODULE_PERSISTENT =
      ProgramManager.PM_LOAD_MODULE_PERSISTENT;

   /** Load module is absolute linked. */
   public static final int LOAD_MODULE_ABSOLUTE =
      ProgramManager.PM_LOAD_MODULE_ABSOLUTE;

   /* Target. */
   private final Target target;

   /* Load module info. */
   private final String installHandle;
   private final long entrypoint;
   private final int options;
   private final long textBase;
   private final long textSize;
   private final long dataBase;
   private final long dataSize;
   private final int numSections;
   private final int numInstances;
   private final String fileFormat;
   private final String fileName;

   /**
    * Create a new load module info object.
    *
    * @param target         the target.
    * @param installHandle  the install handle.
    * @param entrypoint     the entrypoint address.
    * @param options        the install options.
    * @param textBase       the text base address.
    * @param textSize       the text size.
    * @param dataBase       the data base address.
    * @param dataSize       the data size.
    * @param numSections    the number of sections.
    * @param numInstances   the number of program instances.
    * @param fileFormat     the file format.
    * @param fileName       the file name.
    */
   LoadModuleInfo(Target target,
                  String installHandle,
                  long entrypoint,
                  int options,
                  long textBase,
                  long textSize,
                  long dataBase,
                  long dataSize,
                  int numSections,
                  int numInstances,
                  String fileFormat,
                  String fileName)
   {
      if ((installHandle == null) || (fileFormat == null) || (fileName == null))
      {
         throw new NullPointerException();
      }
      this.target = target;
      this.installHandle = installHandle;
      this.entrypoint = entrypoint;
      this.options = options;
      this.textBase = textBase;
      this.textSize = textSize;
      this.dataBase = dataBase;
      this.dataSize = dataSize;
      this.numSections = numSections;
      this.numInstances = numInstances;
      this.fileFormat = fileFormat;
      this.fileName = fileName;
   }

   /**
    * Create a new load module info object for off-line usage.
    *
    * @param installHandle  the install handle.
    * @param entrypoint     the entrypoint address.
    * @param options        the install options.
    * @param textBase       the text base address.
    * @param textSize       the text size.
    * @param dataBase       the data base address.
    * @param dataSize       the data size.
    * @param numSections    the number of sections.
    * @param numInstances   the number of program instances.
    * @param fileFormat     the file format.
    * @param fileName       the file name.
    * @return a new load module info object for off-line usage.
    */
   public static LoadModuleInfo getInstance(String installHandle,
                                            long entrypoint,
                                            int options,
                                            long textBase,
                                            long textSize,
                                            long dataBase,
                                            long dataSize,
                                            int numSections,
                                            int numInstances,
                                            String fileFormat,
                                            String fileName)
   {
      return new LoadModuleInfo(null,
                                installHandle,
                                entrypoint,
                                options,
                                textBase,
                                textSize,
                                dataBase,
                                dataSize,
                                numSections,
                                numInstances,
                                fileFormat,
                                fileName);
   }

   /**
    * Return the parent target object. If this load module info object
    * originates from an OSE target system this method will return that
    * com.ose.system.Target object, otherwise it has been created for off-line
    * usage and this method will return null.
    *
    * @return the parent target object or null.
    */
   public Target getTarget()
   {
      return target;
   }

   /**
    * Return the installation handle of this load module.
    *
    * @return the installation handle of this load module.
    */
   public String getInstallHandle()
   {
      return installHandle;
   }

   /**
    * Return the entrypoint address of this load module.
    *
    * @return the entrypoint address of this load module.
    */
   public long getEntrypointLong()
   {
      return entrypoint;
   }
   
   /**
    * Return the entrypoint address of this load module.
    *
    * @return the entrypoint address of this load module.
    * @deprecated As of Optima 2.8, use {@link #getEntrypointLong()} instead
    */
   @Deprecated  
   public int getEntrypoint()
   {
      return (int)entrypoint;
   }

   /**
    * Return the installation options of this load module, i.e. one of the
    * LoadModuleInfo.LOAD_MODULE_PERSISTENT or
    * LoadModuleInfo.LOAD_MODULE_ABSOLUTE constants.
    *
    * @return the installation options of this load module.
    * @see #LOAD_MODULE_PERSISTENT
    * @see #LOAD_MODULE_ABSOLUTE
    */
   public int getOptions()
   {
      return options;
   }

   /**
    * Return the base address of the text section of this load module.
    *
    * @return the base address of the text section of this load module.
    */
   public long getTextBaseLong()
   {
      return textBase;
   }
   
   /**
    * Return the base address of the text section of this load module.
    *
    * @return the base address of the text section of this load module.
    * @deprecated As of Optima 2.8, use {@link #getTextBaseLong()} instead
    */
   public int getTextBase()
   {
      return (int)textBase;
   }

   /**
    * Return the size, in bytes, of the text section of this load module.
    *
    * @return the size, in bytes, of the text section of this load module.
    */
   public long getTextSizeLong()
   {
      return textSize;
   }
   
   /**
    * Return the size, in bytes, of the text section of this load module.
    *
    * @return the size, in bytes, of the text section of this load module.
    * @deprecated As of Optima 2.8, use {@link #getTextSizeLong()} instead
    */
   @Deprecated
   public int getTextSize()
   {
      return (int)textSize;
   }

   /**
    * Return the base address of the data section of this load module.
    *
    * @return the base address of the data section of this load module.
    */
   public long getDataBaseLong()
   {
      return dataBase;
   }
   
   /**
    * Return the base address of the data section of this load module.
    *
    * @return the base address of the data section of this load module.
    * @deprecated As of Optima 2.8, use {@link #getDataBaseLong()} instead
    */
   @Deprecated
   public int getDataBase()
   {
      return (int)dataBase;
   }

   /**
    * Return the size, in bytes, of the data section of this load module.
    *
    * @return the size, in bytes, of the data section of this load module.
    */
   public long getDataSizeLong()
   {
      return dataSize;
   }
   
   /**
    * Return the size, in bytes, of the data section of this load module.
    *
    * @return the size, in bytes, of the data section of this load module.
    * @deprecated As of Optima 2.8, use {@link #getDataSizeLong()} instead
    */
   @Deprecated
   public int getDataSize()
   {
      return (int)dataSize;
   }

   /**
    * Return the number of sections in the file of this load module.
    *
    * @return the number of sections in the file of this load module.
    */
   public int getNumSections()
   {
      return numSections;
   }

   /**
    * Return the number of program instances created from this load module.
    *
    * @return the number of program instances created from this load module.
    */
   public int getNumInstances()
   {
      return numInstances;
   }

   /**
    * Return the file format of this load module.
    *
    * @return the file format of this load module.
    */
   public String getFileFormat()
   {
      return fileFormat;
   }

   /**
    * Return the file name of this load module.
    *
    * @return the file name of this load module.
    */
   public String getFileName()
   {
      return fileName;
   }

   /**
    * Return a string representation of this load module info object.
    * The returned string is of the form "&lt;install-handle&gt;".
    *
    * @return a string representation of this load module info object.
    * @see java.lang.Object#toString()
    */
   public String toString()
   {
      return installHandle;
   }
}
