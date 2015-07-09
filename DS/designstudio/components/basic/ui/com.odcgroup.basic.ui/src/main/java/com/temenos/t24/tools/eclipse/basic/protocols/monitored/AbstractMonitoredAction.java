package com.temenos.t24.tools.eclipse.basic.protocols.monitored;

import java.lang.reflect.InvocationTargetException;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;

import com.temenos.t24.tools.eclipse.basic.protocols.Response;

/**
 * Abstract class meant to be implemented by all monitored actions.
 * Its role is to create a separate thread where the action will run,
 * and then wrap that thread with within a progress monitor that will
 * be shown on the Eclipse IDE and will allow the user to stop the thread
 * at any time.
 * 
 * @author lfernandez
 *
 */
public abstract class AbstractMonitoredAction implements MonitoredAction {

    private boolean processFinished = false;
    private Response response;
    
    protected Response execute(final String[] params) {
        IWorkbenchWindow window = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
        
        this.processFinished= false;
        try {
            /**
             * A progress monitor is started with the following API. 
             * Note that an instance of IRunnableProgress is created
             * and passed as argument. It has a run method, inside which
             * the compilation thread is kicked off. 
             */
            window.run(true, true, new IRunnableWithProgress() {
                public void run(IProgressMonitor monitor) 
                    throws InvocationTargetException, InterruptedException {
                    
                    // Create a separate thread to run the process. i.e. there are two threads; 
                    // one running the progress monitor, and another one running the process.
                    MonitoredRunnable execProcess= getMonitoredThread(params);
                    Thread thread = new Thread(execProcess);
                    thread.start();                    

                    // Actions within the progress monitor:
                    // We don't know a priori how long the compilation will take
                    int totalUnitsOfWork = IProgressMonitor.UNKNOWN; 
                    String name = "Status bar progress:";
                    monitor.beginTask(name, totalUnitsOfWork);
                    while(!processFinished){                        
                        Thread.sleep(1000); // wait one second
                        monitor.worked(1);  // add one slot to the progress monitor
                        if(monitor.isCanceled()){
                            // This point is reached when the user has clicked
                            // to cancel the monitor.
                            response = execProcess.stopProcess();
                            thread.stop();
                            break;
                        }
                    }
                    monitor.done();
                }
            });
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        return response;
    }

    
    /** From MonitoredAction */
    public void setResponse(Response response) {
        this.response = response;
    }

    /** From MonitoredAction */
    public void setProcessFinished(boolean processFinished) {
        this.processFinished = processFinished;
    }

    protected abstract MonitoredRunnable getMonitoredThread(String[] params);
}
