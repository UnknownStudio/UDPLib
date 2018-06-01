package team.unstudio.udpl.util;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.EnumWrappers.TitleAction;
import com.comphenix.protocol.wrappers.WrappedChatComponent;
import com.google.common.base.Strings;
import org.bukkit.entity.Player;

import javax.annotation.Nullable;
import java.lang.reflect.InvocationTargetException;

/**
 * An util helps you sending a title (
 * <a href="https://minecraft.gamepedia.com/Commands/title">Official
 * Minecraft Wiki</a>). If you want to send a title with subtitle
 * to a player, you should use {@link TitleUtils#title(Player, String)}
 * at first. Then, use {@link TitleUtils#subTitle(Player, String)} to
 * send the subtitle. If you also want to custom display time or
 * other setting, use {@link TitleUtils#setTimeAndDisplay(Player, int, int, int)}
 * before using {@link TitleUtils#title(Player, String)}.
 */
public interface TitleUtils {
    ProtocolManager PROTOCOL_MANAGER = ProtocolLibrary.getProtocolManager();

    static Result title(Player player, @Nullable String title, @Nullable String subTitle, int fadeIn, int stay, int fadeOut) {
        Result result = setTimeAndDisplay(player, fadeIn, stay, fadeOut);
        if (result.isFailure())
            return result;

        if (!Strings.isNullOrEmpty(title)) {
            result = title(player, title);
            if (result.isFailure()) return result;
        }

        if (!Strings.isNullOrEmpty(subTitle)) {
            result = subTitle(player, subTitle);
            if (result.isFailure()) return result;
        }

        return Result.success();
    }

    /**
     * Add a title to the title screen and display the title screen
     */
    static Result title(Player player, String title) {
        PacketContainer container = PROTOCOL_MANAGER.createPacket(PacketType.Play.Server.TITLE);
        container.getTitleActions().write(0, TitleAction.TITLE);
        container.getChatComponents().write(0, WrappedChatComponent.fromText(title));
        return ProtocolLibUtils.send(player, container);
    }

    /**
     * Add a subtitle to the next title screen
     * (The title screen will not display when this command is run).
     */
    static Result subTitle(Player player, String subTitle) {
        PacketContainer container = PROTOCOL_MANAGER.createPacket(PacketType.Play.Server.TITLE);
        container.getTitleActions().write(0, TitleAction.SUBTITLE);
        container.getChatComponents().write(0, WrappedChatComponent.fromText(subTitle));
        return ProtocolLibUtils.send(player, container);
    }

    /**
     * Set the fade-in, stay and fade-out times for the title screen
     */
    static Result setTimeAndDisplay(Player player, int fadeIn, int stay, int fadeOut) {
        PacketContainer container = PROTOCOL_MANAGER.createPacket(PacketType.Play.Server.TITLE);
        container.getTitleActions().write(0, TitleAction.TIMES);
        container.getIntegers().write(0, fadeIn).write(1, stay).write(2, fadeOut);
        return ProtocolLibUtils.send(player, container);
    }

    /**
     * Remove the title screen from the screen.
     */
    static Result hide(Player player) {
        PacketContainer container = PROTOCOL_MANAGER.createPacket(PacketType.Play.Server.TITLE);
        container.getTitleActions().write(0, TitleAction.CLEAR);
        return ProtocolLibUtils.send(player, container);
    }

    /**
     * Reset the title screen to the default settings and options
     */
    static Result reset(Player player) {
        PacketContainer container = PROTOCOL_MANAGER.createPacket(PacketType.Play.Server.TITLE);
        container.getTitleActions().write(0, TitleAction.RESET);
        return ProtocolLibUtils.send(player, container);
    }
}
