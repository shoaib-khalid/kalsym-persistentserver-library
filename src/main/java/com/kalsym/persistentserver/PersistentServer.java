package com.kalsym.persistentserver;

/**
 *
 * @author zeeshan
 */
public class PersistentServer {

    /*public static final int ER_NOBALANCE=90001;
     public static final int ER_SERVERDOWN=90002;
     public static final int ER_NOLINK=90003;
     public static final int ER_NOTBIND=90004;
     public static final int ER_BLACKLIST=90005;
     */
    AppServer svConnector = null;
    ServerExecutor executor = null;

    int poolSize = 10;
    int maxPoolSize = 100;
    long keepAliveTime = 120;
    ProcessRequest requestProcesser = null;
    int port = -1;

    private PersistentServer persistentServer = null;

    /**
     * Default values: poolsize = 10, maxPoolSize = 100, keepAliveTime = 120
     * (seconds)
     *
     * @param poolSize
     * @param maxPoolSize
     * @param keepAliveTime
     * @param request
     */
    public PersistentServer(int port, int poolSize, int maxPoolSize,
            long keepAliveTime, ProcessRequest request) {
        this.poolSize = poolSize;
        this.maxPoolSize = maxPoolSize;
        this.keepAliveTime = keepAliveTime;
        requestProcesser = request;
        this.port = port;
    }

    public void start() {
        //init executor
        executor = new ServerExecutor(poolSize, maxPoolSize, keepAliveTime, requestProcesser);
        executor.init();
        //LogProperties.WriteLog("Worker Thread Pool Initialized");

        //start Persistent server listener
        svConnector = new NettyTcpServer();
        svConnector.start(port, new ServerConnHandler(executor));
        //LogProperties.WriteLog("[Persistent Server] Listening to Client at " + port);
    }
}
