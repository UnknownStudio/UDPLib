package team.unstudio.udpl.util;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.ListeningWhitelist;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.events.PacketListener;
import org.bukkit.plugin.Plugin;
import team.unstudio.udpl.UDPLib;

import java.util.function.Consumer;

public interface ProtocolLibUtils {
    ProtocolManager PROTOCOL_MANAGER = ProtocolLibrary.getProtocolManager();

    static ProtocolManager getManager() {
        return PROTOCOL_MANAGER;
    }

    /**
     * 监听包的发送
     *
     * @param consumer 发送处理
     * @param sendingWhiteList 需要监听的包
     */
    static void listenOnPacketSending(Consumer<PacketEvent> consumer, PacketType... sendingWhiteList) {
        PROTOCOL_MANAGER.addPacketListener(new PacketListener() {
            @Override
            public void onPacketSending(PacketEvent event) {
                consumer.accept(event);
            }

            @Override
            public void onPacketReceiving(PacketEvent event) {

            }

            @Override
            public ListeningWhitelist getSendingWhitelist() {
                return ListeningWhitelist.newBuilder().types(sendingWhiteList).build();
            }

            @Override
            public ListeningWhitelist getReceivingWhitelist() {
                return ListeningWhitelist.EMPTY_WHITELIST;
            }

            @Override
            public Plugin getPlugin() {
                return UDPLib.getPlugin();
            }
        });
    }

    /**
     * 监听包的接收
     *
     * @param consumer 接收处理
     * @param receivingWhiteList 需要监听的包
     */
    static void listenOnPacketReceiving(Consumer<PacketEvent> consumer, PacketType... receivingWhiteList) {
        PROTOCOL_MANAGER.addPacketListener(new PacketListener() {
            @Override
            public void onPacketSending(PacketEvent event) {
            }

            @Override
            public void onPacketReceiving(PacketEvent event) {
                consumer.accept(event);
            }

            @Override
            public ListeningWhitelist getReceivingWhitelist() {
                return ListeningWhitelist.newBuilder().types(receivingWhiteList).build();
            }

            @Override
            public ListeningWhitelist getSendingWhitelist() {
                return ListeningWhitelist.EMPTY_WHITELIST;
            }

            @Override
            public Plugin getPlugin() {
                return UDPLib.getPlugin();
            }
        });
    }
}
