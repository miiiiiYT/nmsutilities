package io.github.riesenpilz.nms.packet.playOut;

import java.util.List;

import org.bukkit.entity.Player;

import io.github.riesenpilz.nms.reflections.Field;
import net.minecraft.server.v1_16_R3.Packet;
import net.minecraft.server.v1_16_R3.PacketListenerPlayOut;
import net.minecraft.server.v1_16_R3.PacketPlayOutLightUpdate;

/**
 * https://wiki.vg/Protocol#Update_Light
 * <p>
 * Updates light levels for a chunk.
 * <p>
 * Packet ID: 0x25<br>
 * State: Play<br>
 * Bound To: Client
 * 
 * @author Martin
 *
 */
public class PacketPlayOutUpdateLightEvent extends PacketPlayOutEvent {

	private int chunkX;
    private int chunkY;
    private int c;
    private int d;
    private int e;
    private int f;
    private List<byte[]> skyLight;
    private List<byte[]> blockLight;
    private boolean trustEdges;
	
	@SuppressWarnings("unchecked")
	public PacketPlayOutUpdateLightEvent(Player injectedPlayer, PacketPlayOutLightUpdate packet) {
		super(injectedPlayer);
		chunkX = Field.get(packet, "a", int.class);
		chunkY = Field.get(packet, "b", int.class);
		c = Field.get(packet, "c", int.class);
		d = Field.get(packet, "d", int.class);
		e = Field.get(packet, "e", int.class);
		f = Field.get(packet, "f", int.class);
		skyLight = Field.get(packet, "g", List.class);
		blockLight = Field.get(packet, "h", List.class);
		trustEdges = Field.get(packet, "i", boolean.class);
	}

	public PacketPlayOutUpdateLightEvent(Player injectedPlayer, int chunkX, int chunkY, int c, int d, int e, int f,
			List<byte[]> skyLight, List<byte[]> blockLight, boolean trustEdges) {
		super(injectedPlayer);
		this.chunkX = chunkX;
		this.chunkY = chunkY;
		this.c = c;
		this.d = d;
		this.e = e;
		this.f = f;
		this.skyLight = skyLight;
		this.blockLight = blockLight;
		this.trustEdges = trustEdges;
	}

	public int getChunkX() {
		return chunkX;
	}

	public int getChunkY() {
		return chunkY;
	}

	public int getC() {
		return c;
	}

	public int getD() {
		return d;
	}

	public int getE() {
		return e;
	}

	public int getF() {
		return f;
	}

	public List<byte[]> getSkyLight() {
		return skyLight;
	}

	public List<byte[]> getBlockLight() {
		return blockLight;
	}

	public boolean isTrustEdges() {
		return trustEdges;
	}

	@Override
	public Packet<PacketListenerPlayOut> getNMS() {
		final PacketPlayOutLightUpdate packet = new PacketPlayOutLightUpdate();
		Field.set(packet, "a", chunkX);
		Field.set(packet, "b", chunkY);
		Field.set(packet, "c", c);
		Field.set(packet, "d", d);
		Field.set(packet, "e", e);
		Field.set(packet, "f", f);
		Field.set(packet, "g", skyLight);
		Field.set(packet, "h", blockLight);
		Field.set(packet, "i", trustEdges);
		return packet;
	}

	@Override
	public int getPacketID() {
		return 0x25;
	}

	@Override
	public String getProtocolURLString() {
		return "https://wiki.vg/Protocol#Update_Light";
	}
}
