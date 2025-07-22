package me.xiaoying.turtleframe;

import com.alipay.remoting.exception.RemotingException;
import me.xiaoying.turtle.api.core.TCore;
import me.xiaoying.turtle.api.messsage.OptionRequestMessage;
import me.xiaoying.turtle.api.messsage.OptionResponseMessage;
import me.xiaoying.turtle.api.utils.ServerUtil;
import net.afyer.afybroker.client.Broker;
import org.bukkit.plugin.java.JavaPlugin;

public class Turtle extends JavaPlugin {
    @Override
    public void onLoad() {

    }

    @Override
    public void onEnable() {
        ServerUtil.sendMessage("&6[*] &b初始化 &e" + this.getName() + " &b.");

        TCore.setInstance(this);

        // initialize
        Turtle.initialize();

        // 测试用代码
//        try {
//            OptionResponseMessage message = Broker.invokeSync(new OptionRequestMessage("test", "name"));
//            System.out.println(message.getOption().getValue() + " - " + message.getOption().getDescription());
//        } catch (RemotingException | InterruptedException e) {
//            throw new RuntimeException(e);
//        }
        ServerUtil.sendMessage("&a[√] &b初始化完成.");
    }

    @Override
    public void onDisable() {
        Turtle.unInitialize();
        ServerUtil.sendMessage("&6[#] &e" + this.getName() + " &b已卸载.");
    }

    public static void initialize() {

    }

    public static void unInitialize() {

    }
}