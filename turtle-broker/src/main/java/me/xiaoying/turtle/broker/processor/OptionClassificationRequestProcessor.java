package me.xiaoying.turtle.broker.processor;

import com.alipay.remoting.BizContext;
import com.alipay.remoting.rpc.protocol.SyncUserProcessor;
import me.xiaoying.turtle.api.messsage.OptionClassificationRequestMessage;
import me.xiaoying.turtle.api.messsage.OptionClassificationResponseMessage;
import me.xiaoying.turtle.broker.TBroker;

public class OptionClassificationRequestProcessor extends SyncUserProcessor<OptionClassificationRequestMessage> {
    @Override
    public Object handleRequest(BizContext bizContext, OptionClassificationRequestMessage message) throws Exception {
        return new OptionClassificationResponseMessage(TBroker.getOptionManager().getOptions(message.getClassification()));
    }

    @Override
    public String interest() {
        return OptionClassificationRequestMessage.class.getName();
    }
}