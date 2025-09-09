package me.xiaoying.turtle.broker.processor;

import me.xiaoying.moebroker.api.RemoteClient;
import me.xiaoying.moebroker.api.processor.SyncProcessor;
import me.xiaoying.turtle.api.messsage.OptionRequestMessage;
import me.xiaoying.turtle.api.messsage.OptionResponseMessage;
import me.xiaoying.turtle.api.option.Option;
import me.xiaoying.turtle.broker.TBroker;

/**
 * 处理 Option 获取请求
 */
public class OptionRequestProcessor extends SyncProcessor<OptionRequestMessage> {
    @Override
    public Object syncRequest(RemoteClient remoteClient, OptionRequestMessage message) throws Exception {
        Option option = TBroker.getOptionManager().getOption(message.getClassification(), message.getKey());
        return new OptionResponseMessage(option);
    }
}