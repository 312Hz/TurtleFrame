package me.xiaoying.turtle.api.broker;

import me.xiaoying.moebroker.api.BrokerAddress;

public abstract class BrokerClient extends me.xiaoying.moebroker.client.BrokerClient {
    public BrokerClient(BrokerAddress address) {
        super(address);
    }
}