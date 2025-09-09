package me.xiaoying.turtle.broker.processor;
import me.xiaoying.moebroker.api.RemoteClient;
import me.xiaoying.moebroker.api.processor.SyncProcessor;
import me.xiaoying.turtle.api.messsage.OptionClassificationRequestMessage;
import me.xiaoying.turtle.api.messsage.OptionClassificationResponseMessage;
import me.xiaoying.turtle.broker.TBroker;

public class OptionClassificationRequestProcessor extends SyncProcessor<OptionClassificationRequestMessage> {
    @Override
    public Object syncRequest(RemoteClient remoteClient, OptionClassificationRequestMessage message) throws Exception {
        return new OptionClassificationResponseMessage(TBroker.getOptionManager().getOptions(message.getClassification()));
    }
}