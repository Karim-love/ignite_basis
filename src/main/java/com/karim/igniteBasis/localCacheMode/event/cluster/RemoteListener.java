package com.karim.igniteBasis.localCacheMode.event.cluster;

import org.apache.ignite.events.Event;
import org.apache.ignite.lang.IgnitePredicate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.apache.ignite.events.EventType.*;

/**
 * @author : sblim
 * @version : 1.0.0
 * @package : org.example.ignite.cluster
 * @name : ignite_karim
 * @date : 2024. 04. 30. 030 오전 10:53
 * @modifyed :
 * @description :
 **/

public class RemoteListener implements IgnitePredicate<Event> {

    private final static Logger logger = LoggerFactory.getLogger(RemoteListener.class);

    @Override
    public boolean apply(Event event) {

        // EVTS_DISCOVERY
        switch (event.type()) {

            case EVT_NODE_JOINED:

                logger.info("sblim RemoteListener node joined = {}", event.node().addresses());
                break;

            case EVT_NODE_LEFT:

                logger.info("sblim RemoteListener node left = {}", event.node().addresses());
                break;

            case EVT_CLIENT_NODE_DISCONNECTED:

                logger.info("sblim RemoteListener node disconnected = {}", event.node().addresses());
                break;
            case EVT_CLIENT_NODE_RECONNECTED:

                logger.info("sblim RemoteListener node reconnected = {}", event.node().addresses());
                break;
            default:

                break;
        }
        return true;
    }
}