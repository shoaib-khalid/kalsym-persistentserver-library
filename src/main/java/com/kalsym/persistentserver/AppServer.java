package com.kalsym.persistentserver;

interface AppServer {

    public void start(int port, ConnHandler handler);
}
