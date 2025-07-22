package me.xiaoying.turtle.broker.processor;

import com.alipay.remoting.BizContext;
import com.alipay.remoting.rpc.protocol.SyncUserProcessor;
import me.xiaoying.turtle.api.messsage.OptionRequestMessage;
import me.xiaoying.turtle.api.messsage.OptionResponseMessage;
import me.xiaoying.turtle.api.option.Option;
import me.xiaoying.turtle.broker.TBroker;

/**
 * 处理 Option 获取请求
 */
public class OptionRequestProcessor extends SyncUserProcessor<OptionRequestMessage> {
    @Override
    public Object handleRequest(BizContext bizContext, OptionRequestMessage message) throws Exception {
        Option option = TBroker.getOptionManager().getOption(message.getClassification(), message.getKey());
        return new OptionResponseMessage(option);
    }

    @Override
    public String interest() {
        return OptionRequestMessage.class.getName();
    }
}