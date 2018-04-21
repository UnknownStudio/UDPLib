package team.unstudio.udpl.util;

import java.lang.reflect.InvocationTargetException;
import java.util.Set;
import java.util.UUID;

import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.ListeningWhitelist;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.events.PacketListener;
import com.comphenix.protocol.wrappers.EnumWrappers.NativeGameMode;
import com.comphenix.protocol.wrappers.EnumWrappers.PlayerInfoAction;
import com.comphenix.protocol.wrappers.PlayerInfoData;
import com.comphenix.protocol.wrappers.WrappedChatComponent;
import com.comphenix.protocol.wrappers.WrappedGameProfile;
import com.google.common.base.Strings;
import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.google.common.collect.Sets;

import team.unstudio.udpl.UDPLib;

public final class Tab {
	
	private static final ProtocolManager protocolManager = ProtocolLibrary.getProtocolManager();
	
	private int size;
	private String header;
	private String footer;
	private final Set<Player> receivers;
	private final BiMap<Integer,TabItem> items;
	
	public Tab(int size){
		this.receivers = Sets.newHashSet();
		this.items = HashBiMap.create();
		this.size = size;

		ProtocolLibUtils.listenOnPacketSending(event -> {
			PacketContainer container = event.getPacket();
			if(container.getPlayerInfoAction().read(0)!=PlayerInfoAction.ADD_PLAYER)
				return;
		}, PacketType.Play.Server.PLAYER_INFO);
	}
	
	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public String getHeader() {
		return header;
	}

	public void setHeader(String header) {
		this.header = header;
	}

	public String getFooter() {
		return footer;
	}

	public void setFooter(String footer) {
		this.footer = footer;
	}
	
	protected void sendAddPlayerPackage(UUID uuid, String name, int index, int ping, String displayName) {

	}

	protected void sendUpdateLatency(UUID uuid, int ping) {

	}

	protected void sendUpdateDisplayName(UUID uuid, String displayName) {

	}

	protected void sendRemovePlayer(UUID uuid) {

	}

	protected void sendHeaderAndFooter(String header, String footer) {
		validateReceivers();
		
		PacketContainer container = protocolManager.createPacket(PacketType.Play.Server.PLAYER_LIST_HEADER_FOOTER);
		container.getChatComponents().write(0, Strings.isNullOrEmpty(header)?WrappedChatComponent.fromJson("{\"translate\":\"\"}"):WrappedChatComponent.fromText(header))
									 .write(1, Strings.isNullOrEmpty(footer)?WrappedChatComponent.fromJson("{\"translate\":\"\"}"):WrappedChatComponent.fromText(footer));
		
		receivers.forEach(player->{
		       try {
		            protocolManager.sendServerPacket(player, container);
		        } catch (InvocationTargetException e) {
		        	UDPLib.debug(e);
		        }
		});
	}
	
	protected void validateReceivers(){
		receivers.removeIf(player->!player.isOnline());
	}
	
	public static class TabItem {
		private final Tab tab;
		private final UUID uuid;
		
		private int ping;
		private String text;
		
		public TabItem(Tab tab) {
			this.tab = tab;
			uuid = UUID.randomUUID();
		}
		
		public Tab getTab() {
			return tab;
		}

		public UUID getUuid() {
			return uuid;
		}

		public int getPing() {
			return ping;
		}

		public void setPing(int ping) {
			this.ping = ping;
		}

		public String getText() {
			return text;
		}

		public void setText(String text) {
			this.text = text;
		}
		
		public PlayerInfoData toPlayerInfoData(){
			WrappedGameProfile gameProfile = new WrappedGameProfile(uuid, "TabItem");
			return new PlayerInfoData(gameProfile, ping, NativeGameMode.SPECTATOR, WrappedChatComponent.fromText(text));
		}
		
		@Override
		public int hashCode() {
			return uuid.hashCode();
		}
		
		@Override
		public boolean equals(Object obj) {
			if(obj == null)
				return false;
			if(!(obj instanceof TabItem))
				return false;
			
			TabItem item = (TabItem) obj;
			
			if(!getUuid().equals(item.getUuid()))
				return false;
			
			return true;
		}
	}
}
