package team.unstudio.udpl.core.nms.v1_11_R1.network;

import java.util.Iterator;
import java.util.List;

import org.bukkit.craftbukkit.v1_11_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.MessageToByteEncoder;
import net.minecraft.server.v1_11_R1.EntityPlayer;
import net.minecraft.server.v1_11_R1.EnumProtocolDirection;
import net.minecraft.server.v1_11_R1.PacketDecoder;

public class NmsNetwork implements team.unstudio.udpl.nms.network.NmsNetwork{

	private static final String UDPL_DECODER = "udplDecoder";
	private static final String UDPL_ENCODER = "udplEncoder";
	
	@Override
	public void inject(Player player) {
		EntityPlayer nmsPlayer = ((CraftPlayer) player).getHandle();
		Channel channel = nmsPlayer.playerConnection.networkManager.channel;
		ChannelPipeline pipeline = channel.pipeline();
		if (pipeline.get(UDPL_DECODER) != null) 
			return;
		
		ByteToMessageDecoder decoder = (ByteToMessageDecoder) pipeline.get("decoder");
		MessageToByteEncoder encoder = (MessageToByteEncoder) pipeline.get("encoder");
		
		pipeline.addBefore("decoder", UDPL_DECODER, new ByteToMessageDecoder() {

			@Override
			protected void decode(ChannelHandlerContext context, ByteBuf buffer, List<Object> packets) throws Exception {
				
				Iterator<?> iterator = packets.iterator();
				while (iterator.hasNext()) {
					Object packet = iterator.next();
//					PlayerSendPacket event = new PlayerSendPacket(player, packet);
//					EventManager.call(event);
//					if (event.isCancelled()) iterator.remove();
				}
			}
			
		});
		
		pipeline.addAfter("encoder", UDPL_ENCODER, new MessageToByteEncoder() {

			@Override
			protected void encode(ChannelHandlerContext context, Object packet, ByteBuf buffer) throws Exception {
				if (packet == null) 
					return;
				
//				PlayerReceivePacket event = new PlayerReceivePacket(NetworkInjector.this.player, packet);
//				EventManager.call(event);
//				if (!event.isCancelled()) NetworkInjector.methodEncode.invoke(NetworkInjector.this.objectEncoder, context, packet, buffer);
			}
			
		});
	}

}
