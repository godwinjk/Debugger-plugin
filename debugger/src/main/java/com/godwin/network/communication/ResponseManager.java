package com.godwin.network.communication;


import com.godwin.network.godwin.communication.MessageContract;

/**
 * Created by Godwin on 5/7/2018 2:51 PM for plugin.
 *
 * @author : Godwin Joseph Kurinjikattu
 */
public interface ResponseManager {

    void processResponse(MessageContract messageContract, String response);

}
