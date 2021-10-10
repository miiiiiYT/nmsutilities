package io.github.riesenpilz.nmsUtilities.packet.playOut;

import org.apache.commons.lang.Validate;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import io.github.riesenpilz.nmsUtilities.packet.PacketUtils;
import io.github.riesenpilz.nmsUtilities.reflections.Field;
import net.minecraft.server.v1_16_R3.Item;
import net.minecraft.server.v1_16_R3.Packet;
import net.minecraft.server.v1_16_R3.PacketListenerPlayOut;
import net.minecraft.server.v1_16_R3.PacketPlayOutSetCooldown;

/**
 * https://wiki.vg/Protocol#Set_Cooldown
 * <p>
 * Applies a cooldown period to all items with the given type. Used by the
 * Notchian server with enderpearls. This packet should be sent when the
 * cooldown starts and also when the cooldown ends (to compensate for lag),
 * although the client will end the cooldown automatically. Can be applied to
 * any item, note that interactions still get sent to the server with the item
 * but the client does not play the animation nor attempt to predict results
 * (i.e block placing).
 * <p>
 * Packet ID: 0x16<br>
 * State: Play<br>
 * Bound To: Client
 *
 * @author Martin
 *
 */
public class PacketPlayOutSetCooldownEvent extends PacketPlayOutEvent {

	private Material material;

	/**
	 * Number of ticks to apply a cooldown for, or 0 to clear the cooldown.
	 */
	private int cooldown;

	public PacketPlayOutSetCooldownEvent(Player injectedPlayer, PacketPlayOutSetCooldown packet) {
		super(injectedPlayer);

		Validate.notNull(packet);

		material = PacketUtils.toMaterial(Field.get(packet, "a", Item.class));
		cooldown = Field.get(packet, "b", int.class);
	}

	public PacketPlayOutSetCooldownEvent(Player injectedPlayer, Material material, int cooldown) {
		super(injectedPlayer);

		Validate.notNull(material);

		this.material = material;
		this.cooldown = cooldown;
	}

	public Material getMaterial() {
		return material;
	}

	public int getCooldown() {
		return cooldown;
	}

	@Override
	public Packet<PacketListenerPlayOut> getNMS() {
		return new PacketPlayOutSetCooldown(PacketUtils.toItem(material), cooldown);
	}

	@Override
	public int getPacketID() {
		return 0x16;
	}

	@Override
	public String getProtocolURLString() {
		return "https://wiki.vg/Protocol#Set_Cooldown";
	}
}
