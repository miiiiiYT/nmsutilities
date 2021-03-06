package io.github.riesenpilz.nmsUtilities.packet.loginOut;

import org.bukkit.entity.Player;

import io.github.riesenpilz.nmsUtilities.reflections.Field;
import net.minecraft.server.v1_16_R3.Packet;
import net.minecraft.server.v1_16_R3.PacketLoginOutEncryptionBegin;
import net.minecraft.server.v1_16_R3.PacketLoginOutListener;

/**
 * https://wiki.vg/Protocol#Encryption_Request
 * <p>
 * See Protocol Encryption for details.
 * <p>
 * Packet ID: 0x01<br>
 * State: Login<br>
 * Bound To: Client
 * 
 * @author Martin
 *
 */
public class PacketLoginOutEncryptionRequestEvent extends PacketLoginOutEvent {

	/**
	 * Appears to be empty. (20 chars max)
	 */
	private String serverID;

	/**
	 * The server's public key in bytes.
	 */
	private byte[] publicKey;

	/**
	 * A sequence of random bytes generated by the server.
	 */
	private byte[] verifyToken;

	public PacketLoginOutEncryptionRequestEvent(Player injectedPlayer, String serverID, byte[] publicKey,
			byte[] verifyToken) {
		super(injectedPlayer);
		this.serverID = serverID;
		this.publicKey = publicKey;
		this.verifyToken = verifyToken;
	}

	public PacketLoginOutEncryptionRequestEvent(Player injectedPlayer, PacketLoginOutEncryptionBegin packet) {
		super(injectedPlayer);
		serverID = Field.get(packet, "a", String.class);
		publicKey = Field.get(packet, "b", byte[].class);
		verifyToken = Field.get(packet, "c", byte[].class);
	}

	public String getServerID() {
		return serverID;
	}

	public byte[] getPublicKey() {
		return publicKey;
	}

	public byte[] getVerifyToken() {
		return verifyToken;
	}

	@Override
	public Packet<PacketLoginOutListener> getNMS() {
		return new PacketLoginOutEncryptionBegin(serverID, publicKey, verifyToken);
	}

	@Override
	public int getPacketID() {
		return 0x01;
	}

	@Override
	public String getProtocolURLString() {
		return "https://wiki.vg/Protocol#Encryption_Request";
	}

}
