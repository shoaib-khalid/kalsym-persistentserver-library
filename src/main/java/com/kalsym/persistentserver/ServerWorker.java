package com.kalsym.persistentserver;

import org.jboss.netty.channel.Channel;

class ServerWorker implements Runnable {

    private byte[] msgByte = null;
    private String msgString = null;
    private Object channel = null;
    private boolean processString = false;
    ProcessRequest processRequest = null;

    public ServerWorker(byte[] msg, Object channel, ProcessRequest request) {
        this.msgByte = msg;
        this.channel = channel;
        processRequest = request;
    }

    public ServerWorker(String msg, Object channel, ProcessRequest request) {
        this.msgString = msg;
        this.channel = channel;
        processString = true;
        processRequest = request;
    }

    @Override
    public void run() {
        String refId = extractRefId(msgString);
        //LogProperties.WriteLog("[" + refId + "] REQ receive :" + msgString);
//TODO: handle exception in doProcess method
        String resp = refId + "|" + processRequest.doProcess(extractRequestMsg(msgString));
        ((Channel) channel).write(resp + "\n");
        //LogProperties.WriteLog("[" + refId + "] RES:" + resp);
    }

    public String bytesToHexString(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(String.format("%02x", b & 0xff));
        }
        return sb.toString();
    }

    /**
     * Extracts refId from requestMsg, request Msg must be in format: refId|Msg
     *
     * @param requestMsg
     * @return
     */
    private String extractRefId(String requestMsg) {
        try {
            //TODO: Remove relying on exception here handle the cases that can cuase exception, this can slow down the server performance if continous expception occur
            return requestMsg.substring(0, requestMsg.indexOf("|"));
        } catch (Exception exp) {
            //LogProperties.WriteLog("Couldn't find refId in : " + requestMsg);
        }
        return "";
    }

    /**
     * Extracts message from requestMsg, request Msg must be in format:
     * refId|Msg
     *
     * @param requestMsg
     * @return
     */
    private String extractRequestMsg(String requestMsg) {
        try {
            return requestMsg.substring(requestMsg.indexOf("|") + 1);
        } catch (Exception exp) {
            //LogProperties.WriteLog("Couldn't find message in : " + requestMsg);
        }
        return "";
    }
}
