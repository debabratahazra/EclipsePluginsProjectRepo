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

/**
 * This class represents a name-value-pair parameter, e.g. an environment
 * variable, a system parameter, or a program paramater. A Parameter object is
 * immutable.
 *
 * @see com.ose.system.Target#getSysParams()
 * @see com.ose.system.Block#getEnvVars()
 * @see com.ose.system.Process#getEnvVars()
 */
public class Parameter
{
   /** Argument vector parameter name. */
   public static final String ARGV = "ARGV";

   /* Program manager load module configuration parameter names. */
   public static final String OSE_LM_PROGRAM_TYPE = "OSE_LM_PROGRAM_TYPE";
   public static final String OSE_LM_POOL_SIZE = "OSE_LM_POOL_SIZE";
   public static final String OSE_LM_STACK_SIZES = "OSE_LM_STACK_SIZES";
   public static final String OSE_LM_SIGNAL_SIZES = "OSE_LM_SIGNAL_SIZES";
   public static final String OSE_LM_DATA_INIT = "OSE_LM_DATA_INIT";
   public static final String OSE_LM_BSS_INIT = "OSE_LM_BSS_INIT";
   public static final String OSE_LM_EXEC_CONTEXT = "OSE_LM_EXEC_CONTEXT";
   public static final String OSE_LM_REENTRANT = "OSE_LM_REENTRANT";
   public static final String OSE_LM_EXEC_MODEL = "OSE_LM_EXEC_MODEL";
   public static final String OSE_LM_INTERCEPT = "OSE_LM_INTERCEPT";
   public static final String OSE_LM_MAIN_NAME = "OSE_LM_MAIN_NAME";
   public static final String OSE_LM_MAIN_PRIORITY = "OSE_LM_MAIN_PRIORITY";
   public static final String OSE_LM_MAIN_STACK_SIZE = "OSE_LM_MAIN_STACK_SIZE";
   public static final String OSE_LM_USER_ID = "OSE_LM_USER_ID";
   public static final String OSE_LM_CPU_ID = "OSE_LM_CPU_ID";
   public static final String OSE_LM_LOCKED_ON_CPU = "OSE_LM_LOCKED_ON_CPU";
   public static final String OSE_LM_PROGRAM_NAME = "OSE_LM_PROGRAM_NAME";
   public static final String OSE_LM_POOL_EXT_SIZE = "OSE_LM_POOL_EXT_SIZE";
   public static final String OSE_LM_POOL_MAX_SIZE = "OSE_LM_POOL_MAX_SIZE";
   public static final String OSE_LM_POOL_FILL = "OSE_LM_POOL_FILL";
   public static final String OSE_LM_TEXT_SEARCH_BASE = "OSE_LM_TEXT_SEARCH_BASE";
   public static final String OSE_LM_DATA_SEARCH_BASE = "OSE_LM_DATA_SEARCH_BASE";

   private final String name;
   private final String value;

   /**
    * Create a new parameter object.
    *
    * @param name   the parameter name.
    * @param value  the parameter value.
    */
   Parameter(String name, String value)
   {
      if ((name == null) || (value == null))
      {
         throw new NullPointerException();
      }
      this.name = name;
      this.value = value;
   }

   /**
    * Return the name of this parameter.
    *
    * @return the name of this parameter.
    */
   public String getName()
   {
      return name;
   }

   /**
    * Return the value of this parameter.
    *
    * @return the value of this parameter.
    */
   public String getValue()
   {
      return value;
   }

   /**
    * @see java.lang.Object#hashCode()
    */
   public int hashCode()
   {
      return (name + value).hashCode();
   }

   /**
    * @see java.lang.Object#equals(java.lang.Object)
    */
   public boolean equals(Object obj)
   {
      boolean result = false;
      if (obj instanceof Parameter)
      {
         Parameter other = (Parameter) obj;
         result = (name.equals(other.name) && value.equals(other.value));
      }
      return result;
   }

   /**
    * Return a string representation of this parameter object. The returned
    * string is of the form "&lt;name&gt;=&lt;value&gt;".
    *
    * @return a string representation of this parameter object.
    * @see java.lang.Object#toString()
    */
   public String toString()
   {
      return (name + "=" + value);
   }
}
