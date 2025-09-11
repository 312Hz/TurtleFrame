package me.xiaoying.turtle.bukkit.broker;

import me.xiaoying.moebroker.api.BrokerAddress;
import me.xiaoying.turtle.api.broker.BrokerClient;

public class BClient extends BrokerClient {
    public BClient(BrokerAddress address) {
        super(address);
    }

    @Override
    public void onStart() {

    }

    @Override
    public void onOpen() {
    }

    @Override
    public void onClose() {

    }

    @Override
    public void onError(Throwable throwable) {

    }
}