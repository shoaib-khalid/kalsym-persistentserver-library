package com.kalsym.persistentserver;

class ServerConnHandler implements ConnHandler {

    private final ServerExecutor svrExecutor;

    public ServerConnHandler(ServerExecutor parent) {
        this.svrExecutor = parent;
    }

    @Override
    public void handleMessage(byte[] msg, Object channel) {
        this.svrExecutor.runTask(msg, channel);

    }

    @Override
    public void handleMessage(String msg, Object channel) {
        this.svrExecutor.runTask(msg, channel);
    }

    @Override
    public void exceptionError(String ex) {
        this.svrExecutor.showException(ex);
    }

}
