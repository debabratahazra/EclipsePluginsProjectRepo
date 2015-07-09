package com.temenos.t24.tools.eclipse.basic.utils;


public class TimerTest implements ITimable{

    private boolean timeout = false;
    
    public void runTest(){
        Timer timer = new Timer();
        timer.startTimer(this, 1000L);
     
        try{Thread.sleep(500L);} catch(Exception e){}
        //timer.stopTimer();
        
        while(!timeout){}
        System.out.println("finally timedout");
        
    }
    
    public static void main(String[] args) {
        TimerTest tt = new TimerTest();
        tt.runTest();
    }

    public void timeout() {
        this.timeout = true;
    }
}
