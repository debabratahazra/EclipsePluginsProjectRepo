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

package com.ose.sigdb.util;

/**
 * A simple statement used while saving information in illuminator.
 * 
 * @see StringParser#parseStatement
 */
public class Statement
{
   private String keyword;

   private Object value;

   private boolean isComplexStatement;

   private boolean isEndBracket;

   Statement()
   {
      this.keyword = null;
      value = null;
      isComplexStatement = false;
      isEndBracket = true;
   }

   Statement(String keyword, boolean isComplexStatement)
   {
      this.keyword = keyword;
      this.isComplexStatement = isComplexStatement;
      this.isEndBracket = false;
      value = null;
   }

   Statement(String variable, Object value)
   {
      keyword = variable;
      if (value != null)
         this.value = value;
      else
         throw new NullPointerException();
      isComplexStatement = false;
   }

   public boolean getBooleanValue() throws java.text.ParseException
   {
      if (value instanceof Boolean)
         return ((Boolean) value).booleanValue();
      else
         throw new java.text.ParseException("Expected a boolean value.", 0);
   }

   public double getDoubleValue() throws java.text.ParseException
   {
      if (value instanceof Double)
         return ((Double) value).doubleValue();
      else if (value instanceof Long)
         return ((Long) value).doubleValue();
      else
         throw new java.text.ParseException("Expected a number value.", 0);
   }

   public String getKeyword()
   {
      return keyword;
   }

   public long getLongValue() throws java.text.ParseException
   {
      if (value instanceof Long)
         return ((Long) value).longValue();
      else
         throw new java.text.ParseException("Expected an integer value.", 0);
   }

   public String getStringValue() throws java.text.ParseException
   {
      if (value instanceof String)
         return (String) value;
      else
         throw new java.text.ParseException("Expected a string value.", 0);
   }

   public Object getValue()
   {
      if (value != null)
         return value;
      else
         throw new Error("This statement is not an assignment.");
   }

   public boolean isAssgignment()
   {
      return value != null;
   }

   public boolean isBlockStatement()
   {
      return isComplexStatement;
   }

   public boolean isClosingBracket()
   {
      return isEndBracket;
   }

   public boolean isKeyword(String keyword)
   {
      if (this.keyword != null)
         return this.keyword.equalsIgnoreCase(keyword);
      else
         return false;
   }
}
