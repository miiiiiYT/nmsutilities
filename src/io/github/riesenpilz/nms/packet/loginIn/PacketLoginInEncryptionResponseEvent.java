package io.github.riesenpilz.nms.packet.loginIn;

import org.bukkit.entity.Player;

import io.github.riesenpilz.nms.reflections.Field;
import net.minecraft.server.v1_16_R3.Packet;
import net.minecraft.server.v1_16_R3.PacketLoginInEncryptionBegin;
import net.minecraft.server.v1_16_R3.PacketLoginInListener;

/**
 * https://wiki.vg/Protocol#Encryption_Response<br>
 * <br>
 * See Protocol Encryption for details.<br>
 * <br>
 * Packet ID: 0x01<br>
 * State: Login<br>
 * Bound To: Server
 * 
 * @author Martin
 *
 */
public class PacketLoginInEncryptionResponseEvent extends PacketLoginInEvent {
	/**
	 * Shared Secret value, encrypted with the server's public key.
	 */
	private byte[] sharedSecret;

	/**
	 * Verify Token value, encrypted with the same public key as the shared secret.
	 */
	private byte[] verifyToken;

	public PacketLoginInEncryptionResponseEvent(Player injectedPlayer, byte[] sharedSecret, byte[] verifyToken) {
		super(injectedPlayer);
		this.sharedSecret = sharedSecret;
		this.verifyToken = verifyToken;
	}

	public PacketLoginInEncryptionResponseEvent(Player injectedPlayer, PacketLoginInEncryptionBegin packet) {
		super(injectedPlayer);
		sharedSecret = (byte[]) new Field(PacketLoginInEncryptionBegin.class, "a").get(packet);
		verifyToken = (byte[]) new Field(PacketLoginInEncryptionBegin.class, "b").get(packet);
	}

	public byte[] getSharedSecret() {
		return sharedSecret;
	}

	public byte[] getVerifyToken() {
		return verifyToken;
	}

	@Override
	public Packet<PacketLoginInListener> getNMS() {
		final PacketLoginInEncryptionBegin packet = new PacketLoginInEncryptionBegin();
		new Field(PacketLoginInEncryptionBegin.class, "a").set(packet, sharedSecret);
		new Field(PacketLoginInEncryptionBegin.class, "b").set(packet, verifyToken);
		return packet;
	}

	@Override
	public int getPacketID() {
		return 1;
	}

	@Override
	public String getProtocolURLString() {
		return "https://wiki.vg/Protocol#Encryption_Response";
	}

}
