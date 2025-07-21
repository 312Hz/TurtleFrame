package me.xiaoying.turtle.broker.processor;

import com.alipay.remoting.BizContext;
import com.alipay.remoting.rpc.protocol.SyncUserProcessor;
import me.xiaoying.turtle.api.messsage.OptionRequestMessage;

/**
 * 处理 Option 获取请求
 */
public class OptionRequestProcessor extends SyncUserProcessor<OptionRequestMessage> {
    @Override
    public Object handleRequest(BizContext bizContext, OptionRequestMessage optionRequestMessage) throws Exception {

        return null;
    }

    @Override
    public String interest() {
        return OptionRequestMessage.class.getName();
    }
}