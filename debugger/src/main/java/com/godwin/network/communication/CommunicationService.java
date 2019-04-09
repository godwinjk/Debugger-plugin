package com.godwin.network.communication;

/**
 * Created by Godwin on 5/4/2018 6:04 PM for plugin.
 *
 * @author : Godwin Joseph Kurinjikattu
 */
public class CommunicationService {

    public static RequestManager getRequestService() {
        return new RequestManagerImpl();
    }

    public static ResponseManager getResponseService() {
        return new ResponseManagerImpl();
    }
}
