package team.unstudio.udpc.api.nms;

public interface NMSPacket {

	public Object createPacketPlayOutChat(String message,byte type) throws Exception;
	
	public Object createPacketPlayOutChat(String message) throws Exception;
}
