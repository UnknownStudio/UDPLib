package team.unstudio.udpl.api.common;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.WrappedChatComponent;
import org.bukkit.entity.Player;

import java.lang.reflect.InvocationTargetException;

/**
 * Created by trychen on 17/7/11.
 */
public class ActionBar {
//    private static ActionBar instance;

    private static ProtocolManager protocolManager = ProtocolLibrary.getProtocolManager();

//    public static ActionBar getInstance() {
//        if (instance == null){
//            instance = new ActionBar();
//        }
//        return instance;
//    }

    public static void send(Player player, String text){
        PacketContainer container = protocolManager.createPacket(PacketType.Play.Server.CHAT);
        container.getChatComponents().write(0, WrappedChatComponent.fromJson("{\"text\": \"" + text + "\"}"));
        container.getBytes().write(0, (byte) 2);

        try {
            protocolManager.sendServerPacket(player, container);
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}
