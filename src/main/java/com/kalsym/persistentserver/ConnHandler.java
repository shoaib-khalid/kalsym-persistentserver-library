package com.kalsym.persistentserver;

interface ConnHandler {

    public void handleMessage(byte[] msg, Object channel);

    public void handleMessage(String url, Object Channel);

    public void exceptionError(String exception);
}
