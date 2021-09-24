package io.github.riesenpilz.nms.packet.playOut;

import org.bukkit.entity.Player;

import io.github.riesenpilz.nms.reflections.Field;
import net.minecraft.server.v1_16_R3.IChatBaseComponent;
import net.minecraft.server.v1_16_R3.Packet;
import net.minecraft.server.v1_16_R3.PacketListenerPlayOut;
import net.minecraft.server.v1_16_R3.PacketPlayOutPlayerListHeaderFooter;

/**
 * https://wiki.vg/Protocol#Player_List_Header_And_Footer
 * <p>
 * This packet may be used by custom servers to display additional information
 * above/below the player list. It is never sent by the Notchian server
 * <p>
 * Packet ID: 0x5E<br>
 * State: Play<br>
 * Bound To: Client
 * 
 * @author Martin
 *
 */
public class PacketPlayOutTabListHeaderAndFooterEvent extends PacketPlayOutEvent {

	public IChatBaseComponent header;
    public IChatBaseComponent footer;
	
	public PacketPlayOutTabListHeaderAndFooterEvent(Player injectedPlayer, PacketPlayOutPlayerListHeaderFooter packet) {
		super(injectedPlayer);
		
		header = Field.get(packet, "a", IChatBaseComponent.class);
		footer = Field.get(packet, "b", IChatBaseComponent.class);
	}

	public PacketPlayOutTabListHeaderAndFooterEvent(Player injectedPlayer, IChatBaseComponent header,
			IChatBaseComponent footer) {
		super(injectedPlayer);
		this.header = header;
		this.footer = footer;
	}

	public IChatBaseComponent getHeader() {
		return header;
	}

	public IChatBaseComponent getFooter() {
		return footer;
	}

	@Override
	public Packet<PacketListenerPlayOut> getNMS() {
		final PacketPlayOutPlayerListHeaderFooter packet = new PacketPlayOutPlayerListHeaderFooter();
		Field.set(packet, "a", header);
		Field.set(packet, "b", footer);
		return packet;
	}

	@Override
	public int getPacketID() {
		return 0x5E;
	}

	@Override
	public String getProtocolURLString() {
		return "https://wiki.vg/Protocol#Player_List_Header_And_Footer";
	}
}
