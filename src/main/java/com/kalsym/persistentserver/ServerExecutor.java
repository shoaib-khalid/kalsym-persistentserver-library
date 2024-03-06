package com.kalsym.persistentserver;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

class ServerExecutor {

    ThreadPoolExecutor executor = null;
    BlockingQueue<Runnable> worksQueue = new ArrayBlockingQueue<Runnable>(100000);
    int poolSize = 10;
    int maxPoolSize = 100;
    long keepAliveTime = 120;
    ProcessRequest requestProcesser = null;

    /**
     *
     * @param poolSize
     * @param maxPoolSize
     * @param keepAliveTime
     */
    public ServerExecutor(int poolSize, int maxPoolSize, long keepAliveTime,
            ProcessRequest request) {
        this.poolSize = poolSize;
        this.maxPoolSize = maxPoolSize;
        this.keepAliveTime = keepAliveTime;
        requestProcesser = request;
    }

    public void init() {
        executor = new ThreadPoolExecutor(poolSize, maxPoolSize, keepAliveTime, TimeUnit.SECONDS, worksQueue);
        executor.allowCoreThreadTimeOut(true);
    }

    public void runTask(byte[] message, Object channel) {
        //LogProperties.WriteLog(
//                String.format("[monitor] [%d/%d] Active: %d, Completed: %d, Task: %d, isShutdown: %s, isTerminated: %s",
//                        this.executor.getPoolSize(),
//                        this.executor.getCorePoolSize(),
//                        this.executor.getActiveCount(),
//                        this.executor.getCompletedTaskCount(),
//                        this.executor.getTaskCount(),
//                        this.executor.isShutdown(),
//                        this.executor.isTerminated()));
        try {
            this.executor.execute(new ServerWorker(message, channel, requestProcesser));

        } catch (RejectedExecutionException e) {
            //LogProperties.WriteLog("Worker Queue Full. Execution Rejected");
        } catch (Exception e) {
            //LogProperties.WriteLog("Exception while creating worker:" + e.toString());
        }

    }

    public void runTask(String message, Object channel) {
        //LogProperties.WriteLog(
//                String.format("[monitor] [%d/%d] Active: %d, Completed: %d, Task: %d, isShutdown: %s, isTerminated: %s",
//                        executor.getPoolSize(),
//                        executor.getCorePoolSize(),
//                        executor.getActiveCount(),
//                        executor.getCompletedTaskCount(),
//                        executor.getTaskCount(),
//                        executor.isShutdown(),
//                        executor.isTerminated()));
        try {
            executor.execute(new ServerWorker(message, channel, requestProcesser));
            //SmppWorker worker = new SmppWorker(message,channel,charging);
            //worker.run();
            //LogProperties.WriteLog("String work executed");
        } catch (RejectedExecutionException e) {
            //LogProperties.WriteLog("Worker Queue Full. Execution Rejected");
        } catch (Exception e) {
            //LogProperties.WriteLog("Exception while creating worker:" + e.toString());
        }
    }

    public void showException(String ex) {
        //LogProperties.WriteLog("Exception Error:" + ex);
    }

}
