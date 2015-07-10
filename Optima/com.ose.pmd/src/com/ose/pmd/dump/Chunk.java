/* COPYRIGHT-ENEA-SRC-R1 *
 **************************************************************************
 * Copyright (C) 2006 by Enea Software AB.
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

package com.ose.pmd.dump;

import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * This class represents an IFF chunk.
 */
public class Chunk
{
   public static final IffId BLHD = new IffId('B', 'L', 'H', 'D');
   public static final IffId BLOC = new IffId('B', 'L', 'O', 'C');
   public static final IffId DATA = new IffId('D', 'A', 'T', 'A');
   public static final IffId DESC = new IffId('D', 'E', 'S', 'C');
   public static final IffId DPID = new IffId('D', 'P', 'I', 'D');
   public static final IffId ERBL = new IffId('E', 'R', 'B', 'L');
   public static final IffId ERIN = new IffId('E', 'R', 'I', 'N');
   public static final IffId MBL_ = new IffId('M', 'B', 'L', ' ');
   public static final IffId MHD_ = new IffId('M', 'H', 'D', ' ');
   public static final IffId PMD_ = new IffId('P', 'M', 'D', ' ');
   public static final IffId SGBL = new IffId('S', 'G', 'B', 'L');
   public static final IffId SGHD = new IffId('S', 'G', 'H', 'D');
   public static final IffId TXBL = new IffId('T', 'X', 'B', 'L');
   public static final IffId VERS = new IffId('V', 'E', 'R', 'S');
   public static final IffId ZDAT = new IffId('Z', 'D', 'A', 'T');
   public static final IffId ZDEF = new IffId('Z', 'D', 'E', 'F');
   public static final IffId ER64 = new IffId('E', 'R', '6', '4');
   public static final IffId MBL2 = new IffId('M', 'B', 'L', '2');
   public static final IffId MHD2 = new IffId('M', 'H', 'D', '2');

   private static final String INDENT_LEVEL = "  ";

   // Chunk offsets.
   private static final int ID_OFFSET = 0;
   private static final int SIZE_OFFSET = 4;
   private static final int DATA_OFFSET = 8;
   private static final int FIRST_CHUNK_OFFSET_IN_GROUP = 12;

   // Dump file.
   private DumpFile dumpFile;

   // Offset within dump file of this chunk.
   private int offset;

   protected Chunk(DumpFile dumpFile, int offset)
   {
      this.dumpFile = dumpFile;
      this.offset = offset;
   }

   // Creates a specialized chunk depending on the ID.
   public static Chunk getInstanceAt(DumpFile dumpFile, int offset)
         throws IffException
   {
      Chunk tmpChunk = new Chunk(dumpFile, offset);
      IffId id = tmpChunk.getID();

      if (id.equals(IffId.FORM))
      {
         return new FORMChunk(tmpChunk);
      }
      else if (id.equals(IffId.LIST))
      {
         return new LISTChunk(tmpChunk);
      }
      else if (id.equals(BLHD))
      {
         return new BLHDChunk(tmpChunk);
      }
      else if (id.equals(DATA))
      {
         return new DATAChunk(tmpChunk);
      }
      else if (id.equals(DESC))
      {
         return new DESCChunk(tmpChunk);
      }
      else if (id.equals(DPID))
      {
         return new DPIDChunk(tmpChunk);
      }
      else if (id.equals(ERIN))
      {
         return new ERINChunk(tmpChunk);
      }
      else if (id.equals(MHD_))
      {
         return new MHDChunk(tmpChunk);
      }
      else if (id.equals(SGHD))
      {
         return new SGHDChunk(tmpChunk);
      }
      else if (id.equals(VERS))
      {
         return new VERSChunk(tmpChunk);
      }
      else if (id.equals(ZDEF))
      {
         return new ZDEFChunk(tmpChunk);
      }
      else if (id.equals(ER64))
      {
         return new ER64Chunk(tmpChunk);
      }
      else if (id.equals(MHD2))
      {
         return new MHD2Chunk(tmpChunk);
      }
      else
      {
         return tmpChunk;
      }
   }

   public DumpFile getDumpFile()
   {
      return dumpFile;
   }

   public int getOffset()
   {
      return offset;
   }

   public IffId getID() throws IffException
   {
      return new IffId(readIffU32(ID_OFFSET));
   }

   public long getDataSize() throws IffException
   {
      return readIffU32(SIZE_OFFSET);
   }

   public long getSize() throws IffException
   {
      return getDataSize() + DATA_OFFSET;
   }

   public boolean isGroupChunk() throws IffException
   {
      IffId id = getID();

      if (id.equals(IffId.FORM) ||
          id.equals(IffId.CAT_) ||
          id.equals(IffId.LIST) ||
          id.equals(IffId.PROP))
      {
         long dsize = getDataSize();
         if (dsize >= 4)
         {
            return true;
         }
         else
         {
            throw new IffException(
                  "Data size of chunk at offset " + offset + " is less than 4");
         }
      }

      return false;
   }

   public IffId getFormType() throws IffException
   {
      IffId formType;

      if (isGroupChunk())
      {
         try
         {
            formType = new IffId(dumpFile.readIffU32(offset + DATA_OFFSET));
         }
         catch (IOException e)
         {
            throw new IffException(
                  "Couldn't read form type in chunk at offset " + offset);
         }
      }
      else
      {
         throw new IffException(
               "Trying to get form type from a non group chunk");
      }

      return formType;
   }

   public List getChunks() throws IffException
   {
      List list = new ArrayList();
      long chsize = getSize();
      int off = FIRST_CHUNK_OFFSET_IN_GROUP;

      while (off < chsize)
      {
         /* Get a chunk. */
         if ((chsize - off) >= DATA_OFFSET)
         {
            Chunk ch = Chunk.getInstanceAt(dumpFile, offset + off);
            list.add(ch);
            off += ch.getSize();
         }
         else
         {
            break;
         }
      }

      if (off != chsize)
      {
         throw new IffException(
               "Mismatch of contained sizes in chunk at offset " + offset);
      }

      return list;
   }

   public List getMatchingChunks(IffId[] spec) throws IffException
   {
      return getMatchingChunks(this, spec, 0);
   }

   private static List getMatchingChunks(Chunk ch, IffId[] spec, int pos)
         throws IffException
   {
      List list = new ArrayList();

      if (ch.isGroupChunk())
      {
         if ((pos <= spec.length - 2) &&
             (spec[pos] != null) &&
             spec[pos].equals(ch.getID()) &&
             (spec[pos + 1] != null) &&
             spec[pos + 1].equals(ch.getFormType()))
         {
            int newPos = pos + 2;

            if (newPos == spec.length)
            {
               // Match ends here
               list.add(ch);
            }
            else
            {
               // Traverse contained chunks
               List chunks = ch.getChunks();
               for (Iterator i = chunks.iterator(); i.hasNext();)
               {
                  Chunk c = (Chunk) i.next();
                  list.addAll(getMatchingChunks(c, spec, newPos));
               }
            }
         }
      }
      else
      {
         if ((pos == spec.length - 1) &&
             (spec[pos] != null) &&
             spec[pos].equals(ch.getID()))
         {
            // Match
            list.add(ch);
         }
      }

      return list;
   }

   public void validate() throws IffException
   {
      if (isGroupChunk())
      {
         long dSizeSum = FIRST_CHUNK_OFFSET_IN_GROUP;

         List chunks = getChunks();
         for (Iterator i = chunks.iterator(); i.hasNext();)
         {
            Chunk ch = (Chunk) i.next();

            dSizeSum += ch.getSize();

            // Verify substructure, depth-first
            ch.validate();
         }

         if (dSizeSum != getSize())
         {
            throw new IffException("The chunk at offset " + getOffset()
                  + " has non-consistent contained chunk sizes");
         }
      }
   }

   public int read(byte[] b, int off, int len) throws IffException
   {
      int readBytes = 0;

      try
      {
         readBytes = dumpFile.read(b, offset + off, len);
         if (readBytes != len)
         {
            throw new IffException(
                  "Couldn't read data of chunk at offset " + offset + off);
         }
      }
      catch (IOException e)
      {
         throw new IffException(e.getMessage());
      }

      return readBytes;
   }

   public long readIffU32(int off) throws IffException
   {
      try
      {
         return dumpFile.readIffU32(offset + off);
      }
      catch (IOException e)
      {
         throw new IffException(
               "Couldn't read U32 of chunk at offset " + offset + off);
      }
   }

   public long readU32(int off) throws IffException
   {
      try
      {
         return dumpFile.readU32(offset + off);
      }
      catch (IOException e)
      {
         throw new IffException(
               "Couldn't read U32 of chunk at offset " + offset + off);
      }
   }

   public long readU64(int off) throws IffException
   {
      try
      {
         return dumpFile.readU64(offset + off);
      }
      catch (IOException e)
      {
         throw new IffException("Couldn't read U64 of chunk at offset "
               + offset + off);
      }
   }

   public String[] readChunkDataAsText() throws IffException
   {
      long dsize = getDataSize();
      List strings = new ArrayList();
      byte[] bytes = new byte[(int) dsize];

      // Read string data
      read(bytes, DATA_OFFSET, (int) dsize);

      /*
       * Read characters up to null character, nl or ff, then start a new
       * string.
       */

      int index = 0;

      while (index < dsize)
      {
         StringBuffer sb = new StringBuffer();

         // Build string
         while (index < dsize)
         {
            char c = (char) ((bytes[index++] + 256) % 256);

            if (c == 0 || c == '\n' || c == '\f')
            {
               break;
            }
            else if (Character.isISOControl(c))
            {
               sb.append("\\0x" + Integer.toHexString(c));
            }
            else
            {
               sb.append(c);
            }
         }

         // Ignore successive null characters
         String s = sb.toString();
         if (s.equals(""))
         {
            continue;
         }
         else
         {
            strings.add(s);
         }
      }

      // Return an array
      return (String[]) strings.toArray(new String[0]);
   }

   public void describeContents(PrintStream out)
   {
      describeContents(out, "");
   }

   protected void describeContents(PrintStream out, String indent)
   {
      try
      {
         IffId id = getID();
         long dsize = getDataSize();
         IffId formType = null;
         List chunks = new ArrayList();
         boolean groupChunk = isGroupChunk();

         if (groupChunk)
         {
            formType = getFormType();
            chunks = getChunks();
         }

         out.println(indent + "{");
         out.println(indent + "  ID             : " + id.toString());
         out.println(indent + "  Chunk Data Size: " + dsize);

         if (groupChunk)
         {
            if (id.equals(IffId.FORM) || id.equals(IffId.PROP))
            {
               out.println(indent + "  Form type      : "
                           + formType.toString());
            }
            else if (id.equals(IffId.CAT_) || id.equals(IffId.LIST))
            {
               out.println(indent + "  Cont.type      : "
                           + formType.toString());
            }
            else
            {
               out.println(indent + "  Unknown group id: " + id.toString());
            }

            // Recursively describe contained chunks
            int n = chunks.size();

            for (int i = 0; i < n; i++)
            {
               Chunk c = (Chunk) chunks.get(i);
               c.describeContents(out, indent + INDENT_LEVEL);
            }
         }
         else
         {
            out.println();
            // Try to interpret the chunk data
            describeChunkData(out, indent + INDENT_LEVEL);
         }
         out.println(indent + "}");
      }
      catch (IffException e)
      {
         out.println(indent + "Invalid chunk at offset " + offset + " ("
               + e.getMessage() + ")");
      }
   }

   protected void describeChunkData(PrintStream out, String indent)
         throws IffException
   {
      out.println(indent + "(No interpretation)");
   }

   protected static String formatU32ToHex(long v)
   {
      return "0x" + Long.toHexString(v).toUpperCase();
   }
   
   public static byte[] longToByteArray(long value)
   {
      byte[] bytes = new byte[8];
      bytes[0] = (byte) ((int) (value >>> 56) & 0xFF);
      bytes[1] = (byte) ((int) (value >>> 48) & 0xFF);
      bytes[2] = (byte) ((int) (value >>> 40) & 0xFF);
      bytes[3] = (byte) ((int) (value >>> 32) & 0xFF);
      bytes[4] = (byte) ((int) (value >>> 24) & 0xFF);
      bytes[5] = (byte) ((int) (value >>> 16) & 0xFF);
      bytes[6] = (byte) ((int) (value >>> 8) & 0xFF);
      bytes[7] = (byte) ((int) (value >>> 0) & 0xFF);
      return bytes;
   }
}
