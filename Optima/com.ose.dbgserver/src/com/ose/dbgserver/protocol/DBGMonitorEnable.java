/*
     This module was generated automatically from /vobs/ose5/core_ext/dbgserver/private/dbgserverinterface.stl.
                DO NOT EDIT THIS FILE
*/



package com.ose.dbgserver.protocol;
import java.io.*;

public class DBGMonitorEnable extends Message implements dbgserverinterfaceConstants{
   public int wantProcessInfo;


   public DBGMonitorEnable(int _wantProcessInfo) {
      wantProcessInfo = _wantProcessInfo;
   }

   public DBGMonitorEnable(DataInputStream _s) throws IOException { signalNo = 32957; read(_s);}
   public final void sendMessage(DataOutputStream _s) throws IOException { write(_s, this.wantProcessInfo);}
   public final static void write(DataOutputStream _s, int _wantProcessInfo   ) throws IOException {
         int _i;
         _s.writeInt(DBGMONITORENABLE);
         int _size=12;
         _s.writeInt(_size);
         _s.writeInt(_wantProcessInfo);
   }
   public final void read(DataInputStream _s) throws IOException {
         int _i;
         int _size=_s.readInt();
         wantProcessInfo=_s.readInt();
   }
}