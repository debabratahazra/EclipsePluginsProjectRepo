/* COPYRIGHT-ENEA-SRC-R1 *
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

package com.ose.pmd.ui.editor;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;
import org.eclipse.jface.text.IFindReplaceTarget;
import org.eclipse.jface.text.IFindReplaceTargetExtension;
import org.eclipse.jface.text.IFindReplaceTargetExtension3;
import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.Region;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.LineBackgroundEvent;
import org.eclipse.swt.custom.LineBackgroundListener;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.custom.StyledTextContent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Point;

public class FindTargetHandler implements IFindReplaceTarget,
   IFindReplaceTargetExtension, IFindReplaceTargetExtension3
{
   private final StyledText styledText;
   private final FindScopeHandler findScopeHandler;
   private volatile FindHandler findHandler;

   public FindTargetHandler(StyledText styledText)
   {
      if (styledText == null)
      {
         throw new IllegalArgumentException();
      }
      this.styledText = styledText;
      findScopeHandler = new FindScopeHandler(styledText);
      update();
   }

   public void update()
   {
      StyledTextContent textContent;
      CharSequence charContent;

      textContent = styledText.getContent();
      charContent = ((textContent instanceof CharSequence) ?
            (CharSequence) textContent : styledText.getText());
      findHandler = new FindHandler(charContent);
   }

   // Methods for IFindReplaceTarget.

   public boolean canPerformFind()
   {
      return true;
   }

   public int findAndSelect(int widgetOffset,
                            String findString,
                            boolean searchForward,
                            boolean caseSensitive,
                            boolean wholeWord)
   {
      try
      {
         return findAndSelect(widgetOffset,
                              findString,
                              searchForward,
                              caseSensitive,
                              wholeWord,
                              false);
      }
      catch (PatternSyntaxException e)
      {
         return -1;
      }
   }

   public Point getSelection()
   {
      return styledText.getSelectionRange();
   }

   public String getSelectionText()
   {
      return styledText.getSelectionText();
   }

   public boolean isEditable()
   {
      return false;
   }

   public void replaceSelection(String text)
   {
      // Not supported.
   }

   // Methods for IFindReplaceTargetExtension.

   public void beginSession()
   {
      // Nothing to do.
   }

   public void endSession()
   {
      findScopeHandler.uninstallScope();
   }

   public IRegion getScope()
   {
      return findScopeHandler.getScope();
   }

   public void setScope(IRegion scope)
   {
      findScopeHandler.uninstallScope();
      if (scope != null)
      {
         findScopeHandler.installScope(scope);
      }
   }

   public Point getLineSelection()
   {
      return styledText.getSelectionRange();
   }

   public void setSelection(int offset, int length)
   {
      styledText.setSelectionRange(offset, length);
   }

   public void setScopeHighlightColor(Color color)
   {
      findScopeHandler.setHighlightColor(color);
   }

   public void setReplaceAllMode(boolean replaceAll)
   {
      // Not supported.
   }

   // Methods for IFindReplaceTargetExtension3.

   public int findAndSelect(int widgetOffset,
                            String findString,
                            boolean searchForward,
                            boolean caseSensitive,
                            boolean wholeWord,
                            boolean regExSearch)
   {
      IRegion scope;
      IRegion match;

      scope = findScopeHandler.getScope();
      if (scope != null)
      {
         if (searchForward &&
             ((widgetOffset == -1) || (widgetOffset < scope.getOffset())))
         {
            widgetOffset = scope.getOffset();
         }
         else if (!searchForward && ((widgetOffset == -1) ||
                  (widgetOffset > scope.getOffset() + scope.getLength())))
         {
            widgetOffset = scope.getOffset() + scope.getLength();
         }
      }

      match = findHandler.find(widgetOffset,
                               findString,
                               searchForward,
                               caseSensitive,
                               wholeWord,
                               regExSearch);

      if (match != null)
      {
         int matchOffset = match.getOffset();
         int matchLength = match.getLength();
         if ((scope == null) || ((matchOffset >= scope.getOffset()) &&
             (matchOffset + matchLength <= scope.getOffset() + scope.getLength())))
         {
            styledText.setSelectionRange(matchOffset, matchLength);
            styledText.showSelection();
            return match.getOffset();
         }
         else
         {
            return -1;
         }
      }
      else
      {
         return -1;
      }
   }

   public void replaceSelection(String text, boolean regExReplace)
   {
      // Not supported.
   }

   private static class FindScopeHandler implements LineBackgroundListener
   {
      private final StyledText styledText;
      private Color highlightColor;
      private IRegion scope;

      FindScopeHandler(StyledText styledText)
      {
         if (styledText == null)
         {
            throw new IllegalArgumentException();
         }
         this.styledText = styledText;
         highlightColor = styledText.getDisplay().getSystemColor(SWT.COLOR_GRAY);
         styledText.addLineBackgroundListener(this);
      }

      public IRegion getScope()
      {
         return scope;
      }

      public void installScope(IRegion scope)
      {
         int startLine;
         int endLine;
         int startOffset;
         int endOffset;
         int length;

         if (scope == null)
         {
            throw new IllegalArgumentException();
         }
         startLine = styledText.getLineAtOffset(scope.getOffset());
         endLine = styledText.getLineAtOffset(scope.getOffset() + scope.getLength() - 1);
         startOffset = styledText.getOffsetAtLine(startLine);
         endOffset = styledText.getOffsetAtLine(endLine + 1) - 1;
         length = endOffset - startOffset + 1;
         this.scope = new Region(startOffset, length);
         paint();
      }

      public void uninstallScope()
      {
         if (scope != null)
         {
            paint();
            scope = null;
         }
      }

      public void setHighlightColor(Color color)
      {
         highlightColor = color;
         paint();
      }

      public void lineGetBackground(LineBackgroundEvent event)
      {
         int offset = event.lineOffset;

         if ((scope != null) &&
             (offset >= scope.getOffset()) &&
             (offset < scope.getOffset() + scope.getLength()))
         {
            event.lineBackground = highlightColor;
         }
      }

      private void paint()
      {
         int offset = scope.getOffset();
         int length = scope.getLength();
         int count = styledText.getCharCount();

         if (offset + length >= count)
         {
            length = count - offset;
         }
         styledText.redrawRange(offset, length, true);
      }
   }

   private static class FindHandler
   {
      private final CharSequence content;
      private Matcher matcher;

      FindHandler(CharSequence content)
      {
         if (content == null)
         {
            throw new IllegalArgumentException();
         }
         this.content = content;
      }

      public IRegion find(int startOffset,
                          String findString,
                          boolean searchForward,
                          boolean caseSensitive,
                          boolean wholeWord,
                          boolean regExSearch)
         throws PatternSyntaxException
      {
         int patternFlags = 0;

         if (regExSearch && wholeWord)
         {
            throw new IllegalArgumentException();
         }

         if (startOffset == -1)
         {
            startOffset = (searchForward ? 0 : content.length() - 1);
         }

         if ((startOffset < 0) || (startOffset >= content.length()))
         {
            return null;
         }

         if ((findString == null) || (findString.length() == 0))
         {
            return null;
         }

         if (regExSearch)
         {
            patternFlags |= Pattern.MULTILINE;
         }

         if (!caseSensitive)
         {
            patternFlags |= Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE;
         }

         if (wholeWord)
         {
            findString = "\\b" + findString + "\\b";
         }
         else if (!regExSearch)
         {
            findString = asRegexPattern(findString);
         }

         if ((matcher == null) ||
             !matcher.pattern().pattern().equals(findString) ||
             (matcher.pattern().flags() != patternFlags))
         {
            Pattern pattern = Pattern.compile(findString, patternFlags);
            matcher = pattern.matcher(content);
         }

         if (searchForward) // Forward search
         {
            boolean found = matcher.find(startOffset);

            if (found && (matcher.group().length() > 0))
            {
               return new Region(matcher.start(), matcher.group().length());
            }
            else
            {
               return null;
            }
         }
         else // Backward search
         {
            boolean found = matcher.find(0);
            int index = -1;
            int length = -1;

            while (found &&
                   (matcher.start() + matcher.group().length() <= startOffset + 1))
            {
               index = matcher.start();
               length = matcher.group().length();
               found = matcher.find(index + 1);
            }

            if (index > -1)
            {
               // Set matcher to correct position.
               matcher.find(index);
               return new Region(index, length);
            }
            else
            {
               return null;
            }
         }
      }

      /* Converts a non-regex string to a pattern that can be used with the regex
       * search engine.
       */
      private static String asRegexPattern(String string)
      {
         StringBuffer sb = new StringBuffer(string.length());
         boolean quoting = false;

         for (int i = 0, length = string.length(); i < length; i++)
         {
            char ch = string.charAt(i);
            if (ch == '\\')
            {
               if (quoting)
               {
                  sb.append("\\E");
                  quoting = false;
               }
               sb.append("\\\\");
               continue;
            }
            if (!quoting)
            {
               sb.append("\\Q");
               quoting = true;
            }
            sb.append(ch);
         }

         if (quoting)
         {
            sb.append("\\E");
         }

         return sb.toString();
      }
   }
}
