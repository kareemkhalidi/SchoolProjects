package base;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Implementation of an abstract RobustThread for simplified starting and stopping of loop threads.
 *
 * @author John Gauci
 */

public abstract class RobustThread implements Runnable {

    private static final AtomicInteger threadCount = new AtomicInteger(0);
    private String name = "Unnamed";
    private Thread thread;

    private boolean stopped;

    /**
     * Sets the name of this RobustThread
     *
     * @param name thread name
     * @author John Gauci
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Starts the RobustThread
     *
     * @author John Gauci
     */
    public void start() {
        thread = new Thread(this, name + " RobustThread-" + threadCount.getAndIncrement());
        thread.start();
    }

    /**
     * Code to be run in thread loop.
     *
     * @author John Gauci
     */
    public abstract void loopExecute();

    /**
     * Stops the thread, successful if loop is not blocked
     *
     * @author John Gauci
     */
    public void stop() {
        if (!stopped) {
            threadCount.decrementAndGet();
            stopped = true;
        }
    }

    /**
     * Stops the thread, successful regardless whether the loop is blocked
     *
     * @author John Gauci
     */
    public void forceStop() {
        if (!stopped) {
            threadCount.decrementAndGet();
            stopped = true;
            thread.interrupt();
        }
    }

    /**
     * Runs the code implemented in loop execute in a loop
     *
     * @author John Gauci
     */
    @Override
    public final void run() {
        stopped = false;
        while (!stopped) {
            loopExecute();
        }
    }

}
