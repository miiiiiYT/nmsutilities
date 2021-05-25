package io.github.riesenpilz.nms.packet.loginIn;

import org.bukkit.entity.Player;

import io.github.riesenpilz.nms.reflections.Field;
import net.minecraft.server.v1_16_R3.Packet;
import net.minecraft.server.v1_16_R3.PacketLoginInEncryptionBegin;
import net.minecraft.server.v1_16_R3.PacketLoginInListener;

/**
 * https://wiki.vg/Protocol#Encryption_Response
 * <p>
 * See Protocol Encryption for details.
 * <p>
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
		sharedSecret = Field.get(packet, "a", byte[].class);
		verifyToken = Field.get(packet, "b", byte[].class);
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
		Field.set(packet, "a", sharedSecret);
		Field.set(packet, "b", verifyToken);
		return packet;
	}

	@Override
	public int getPacketID() {
		return 0x01;
	}

	@Override
	public String getProtocolURLString() {
		return "https://wiki.vg/Protocol#Encryption_Response";
	}

}
