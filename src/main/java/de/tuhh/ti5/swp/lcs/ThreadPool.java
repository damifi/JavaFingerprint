package de.tuhh.ti5.swp.lcs;

import java.util.LinkedList;

/**
 * This is the ThreadPool class represents a thread pool with specified number of threads.
 * 
 * @author  Rustam Magomedov.
 * @version 1.0.
 * @since   2016-12-12.
 */

public class ThreadPool {
	
	/**
	 * @param 
	 */
	
    private final Worker[] threads;
    private final LinkedList<Runnable> queue;
 
	/**
	 * The ThreadPool constructor.
	 * @param size 
	 * 		number of worker threads thread pool requires.
	 */
    
    ThreadPool(int size) {
    	queue = new LinkedList<>();
    	threads = new Worker[size];
    	
    	for (int i = 0; i < size; i++) {
    		threads[i] = new Worker();
    		threads[i].start();
        }
    }
 
	/**
	 * This is execute method, which adds tasks to the working queue.
	 * 
	 * @param task
	 * 		task to be added to the queue. 
	 */
    
    public void execute(Runnable task) {
        synchronized (queue) {
            queue.addLast(task);
            queue.notifyAll();
        }
    }

	/**
	 * This is awaitTermination method, puts the main user thread on hold 
	 * in order to wait for results of worker threads, which are daemons.
	 */
        
    public void awaitTermination() {
        synchronized (queue) {
            while (!(queue.isEmpty() && allFinished())) { //  && threads are busy
                try {
                    queue.wait();
                } catch (InterruptedException ignored) {
                	// should be ignored
                }
            }
        }            
    }
    
    /**
     * This is the Worker inner class of a class ThreadPool, which extends thread.
     */
    
    private class Worker extends Thread {
    	private boolean busy;
    	
    	/**
    	 * The Worker constructor, the main purpose of which is to set thread as daemon.
    	 */

    	Worker() {
    		setDaemon(true);
    	}
    	
    	@Override
        public void run() {
        	Runnable task;
            while (true) {
                synchronized (queue) {
                    while (queue.isEmpty()) {
                        try {
                            queue.wait();
                        } catch (InterruptedException ignored) {
                        	// should be ignored
                        }
                    }
                    busy = true;
                    task = (Runnable) queue.removeFirst();
                }                
                try {
                	task.run();
                	busy = false;
                	synchronized (queue) {
                        if (queue.isEmpty()) {
                        	queue.notifyAll();
                        }
                	}
                } catch (RuntimeException e) {
                	System.out.println("RuntimeException inside a Worker!");
                	e.printStackTrace();
                }
            }
        }
    	
    	/**
    	 * This method checks if worker is currently executing.
    	 * true is returned if execution is not finished. 
    	 * @return boolean
    	 */
    	
    	public boolean isBusy() {
    		return busy;
    	}
    }
	
	/**
	 * This method check if all threads are finished with their execution.
	 * true is returned if they are finished and else returned false.
	 * @return boolean
	 */
	
	public boolean allFinished() {
        for (Worker thread : threads) {
        	if (thread.isBusy()) {
        		return false;
        	}
        }
        return true;
	}
}