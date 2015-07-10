/*
     This module was generated automatically from /vobs/ose5/core_ext/dbgserver/private/dbgserverinterface.stl.
                DO NOT EDIT THIS FILE
*/



package com.ose.dbgserver.protocol;
import java.io.*;

public class DBGReadMemoryReply extends Message implements dbgserverinterfaceConstants{
   public byte status;
   public byte unused1;
   public byte unused2;
   public byte unused3;
   public byte bytes[];


   public DBGReadMemoryReply(byte _status, byte _unused1, byte _unused2, byte _unused3, byte _bytes[]) {
      status = _status;
      unused1 = _unused1;
      unused2 = _unused2;
      unused3 = _unused3;
      bytes = _bytes;
   }

   public DBGReadMemoryReply(DataInputStream _s) throws IOException { signalNo = 32995; read(_s);}
   public final void sendMessage(DataOutputStream _s) throws IOException { write(_s, this.status, this.unused1, this.unused2, this.unused3, this.bytes);}
   public final static void write(DataOutputStream _s, byte _status, byte _unused1, byte _unused2, byte _unused3, byte _bytes[]   ) throws IOException {
         int _i;
         _s.writeInt(DBGREADMEMORYREPLY);
         int _size=12
            +4+_bytes.length
            ;
         _s.writeInt(_size);
         _s.writeByte(_status);
         _s.writeByte(_unused1);
         _s.writeByte(_unused2);
         _s.writeByte(_unused3);
         int _dynSize=12;
         int _bytes2Skip;

         // write bytes
         _bytes2Skip=4-_dynSize&3;
         for(_i=0;_i<_bytes2Skip;_i++) _s.writeByte(0);
         _dynSize+=_bytes2Skip;
         _s.writeInt(_bytes.length);
         _dynSize+=_bytes.length;
         _s.write(_bytes);
   }
   public final void read(DataInputStream _s) throws IOException {
         int _i;
         int _size=_s.readInt();
         status=_s.readByte();
         unused1=_s.readByte();
         unused2=_s.readByte();
         unused3=_s.readByte();
         int _dynSize=12;
         int _bytes2Skip;

         // read bytes
         _bytes2Skip=4-_dynSize&3;
         if(_bytes2Skip!=0) {_s.skipBytes(_bytes2Skip);_dynSize+=_bytes2Skip;}
         _size=_s.readInt();
         _dynSize+=_size;
         bytes=new byte[_size];
         _s.readFully(bytes,0,_size);
   }
}