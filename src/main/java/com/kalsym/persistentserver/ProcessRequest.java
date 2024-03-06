package com.kalsym.persistentserver;

/**
 *
 * @author zeeshan
 */
public interface ProcessRequest {

    /**
     * Process Incoming message
     *
     * @param requestMessage
     * @return
     */
    public String doProcess(String requestMessage);
}
