/*
     This module was generated automatically from /vobs/ose5/core_ext/dbgserver/private/dbgserverinterface.stl.
                DO NOT EDIT THIS FILE
*/



package com.ose.dbgserver.protocol;
import java.io.*;

public class DBGGetProcLoadReportsReply extends Message implements dbgserverinterfaceConstants{
   public int measurementStatus;
   public int currentTimeTick;
   public int currentTimeUTick;
   public int reports[];


   public DBGGetProcLoadReportsReply(int _measurementStatus, int _currentTimeTick, int _currentTimeUTick, int _reports[]) {
      measurementStatus = _measurementStatus;
      currentTimeTick = _currentTimeTick;
      currentTimeUTick = _currentTimeUTick;
      reports = _reports;
   }

   public DBGGetProcLoadReportsReply(DataInputStream _s) throws IOException { signalNo = 33013; read(_s);}
   public final void sendMessage(DataOutputStream _s) throws IOException { write(_s, this.measurementStatus, this.currentTimeTick, this.currentTimeUTick, this.reports);}
   public final static void write(DataOutputStream _s, int _measurementStatus, int _currentTimeTick, int _currentTimeUTick, int _reports[]   ) throws IOException {
         int _i;
         _s.writeInt(DBGGETPROCLOADREPORTSREPLY);
         int _size=20
            +4+(_reports.length*4)
            ;
         _s.writeInt(_size);
         _s.writeInt(_measurementStatus);
         _s.writeInt(_currentTimeTick);
         _s.writeInt(_currentTimeUTick);
         int _dynSize=20;
         int _bytes2Skip;

         // write reports
         _bytes2Skip=4-_dynSize&3;
         for(_i=0;_i<_bytes2Skip;_i++) _s.writeByte(0);
         _dynSize+=_bytes2Skip;
         _s.writeInt((_reports.length*4));
         _dynSize+=(_reports.length*4);
         for(_i = 0 ; _i < _reports.length ; _i++) 
            _s.writeInt(_reports[_i]);
   }
   public final void read(DataInputStream _s) throws IOException {
         int _i;
         int _size=_s.readInt();
         measurementStatus=_s.readInt();
         currentTimeTick=_s.readInt();
         currentTimeUTick=_s.readInt();
         int _dynSize=20;
         int _bytes2Skip;

         // read reports
         _bytes2Skip=4-_dynSize&3;
         if(_bytes2Skip!=0) {_s.skipBytes(_bytes2Skip);_dynSize+=_bytes2Skip;}
         _size=_s.readInt();
         _dynSize+=_size;
         _size/=4;
         reports=new int[_size];
         for(_i = 0 ; _i < _size ; _i++) 
            reports[_i]=_s.readInt();
   }
}