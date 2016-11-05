package team.unstudio.udpc.api.nms;

public interface NMSPacket {

	/**
	 * 创建一个PacketPlayOutChat
	 * @param message
	 * @param type
	 * @return
	 * @throws Exception
	 */
	public Object createPacketPlayOutChat(String message,byte type) throws Exception;
	
	/**
	 * 创建一个PacketPlayOutChat
	 * @param message
	 * @param type
	 * @return
	 * @throws Exception
	 */
	public Object createPacketPlayOutChat(String message) throws Exception;
}
