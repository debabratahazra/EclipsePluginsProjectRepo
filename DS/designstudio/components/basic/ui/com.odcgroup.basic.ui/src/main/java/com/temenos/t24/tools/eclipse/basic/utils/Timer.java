package com.temenos.t24.tools.eclipse.basic.utils;

public class Timer implements Runnable {
    private static Long durationMillis; 
    private static ITimable obj;
    private Thread timerThread = null;;
    private boolean stopRequest = false;
    
    /**
     * Creates a timer thread. 
     * @param obj - instance of the client. The client will implement ITimable. This
     * allows the timer thread to invoke the timeout() method, once the thread finished. 
     * @param durationMillis - timer duration in milliseconds.
     */
    public void startTimer(ITimable obj, Long durationMillis){
        
        Timer timer = new Timer();
        timerThread = new Thread(timer);
        timerThread.start();
    }
    
    /**
     * Stops the current timer thread
     */
    public void stopTimer(){
        stopRequest = true;
    }
    
    public void run(){
        long start = System.currentTimeMillis();
        try{
            while(!stopRequest && ((start+durationMillis)>System.currentTimeMillis())){
                // Check every 10 milliseconds
                Thread.sleep(10);
            }
            // wake up and inform the client object of this event
            obj.timeout();
        } catch(InterruptedException e){}
    }
}
