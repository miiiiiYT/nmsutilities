package io.github.riesenpilz.nmsUtilities.packet.playOut;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.attribute.AttributeModifier.Operation;
import org.bukkit.entity.Player;

import io.github.riesenpilz.nmsUtilities.reflections.Field;
import net.minecraft.server.v1_16_R3.AttributeBase;
import net.minecraft.server.v1_16_R3.AttributeModifier;
import net.minecraft.server.v1_16_R3.GenericAttributes;
import net.minecraft.server.v1_16_R3.Packet;
import net.minecraft.server.v1_16_R3.PacketListenerPlayOut;
import net.minecraft.server.v1_16_R3.PacketPlayOutUpdateAttributes;
import net.minecraft.server.v1_16_R3.PacketPlayOutUpdateAttributes.AttributeSnapshot;

/**
 * https://wiki.vg/Protocol#Entity_Properties
 * <p>
 * Sets attributes on the given entity.
 * <p>
 * Packet ID: 0x63<br>
 * State: Play<br>
 * Bound To: Client
 * 
 * @author Martin
 *
 */
public class PacketPlayOutEntityAttributesEvent extends PacketPlayOutEntityEvent {

	private List<Attribute> attributes;

	@SuppressWarnings("unchecked")
	public PacketPlayOutEntityAttributesEvent(Player injectedPlayer, PacketPlayOutUpdateAttributes packet) {
		super(injectedPlayer, packet);
		List<AttributeSnapshot> nms = Field.get(packet, "b", List.class);
		attributes = new ArrayList<>();
		for (AttributeSnapshot snapshot : nms)
			attributes.add(Attribute.getAtributeOf(snapshot));
	}

	public PacketPlayOutEntityAttributesEvent(Player injectedPlayer, int entityId, List<Attribute> attributes) {
		super(injectedPlayer, entityId);
		this.attributes = attributes;
	}

	public List<Attribute> getAttributes() {
		return attributes;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Packet<PacketListenerPlayOut> getNMS() {
		final PacketPlayOutUpdateAttributes packet = new PacketPlayOutUpdateAttributes();
		Field.set(packet, "a", getEntityId());
		List<AttributeSnapshot> nms = new ArrayList<>();
		for (Attribute attribute : attributes)
			nms.add(attribute.getNMS());
		Field.get(packet, "b", List.class).addAll(attributes);
		return packet;
	}

	@Override
	public int getPacketID() {
		return 0x63;
	}

	@Override
	public String getProtocolURLString() {
		return "https://wiki.vg/Protocol#Entity_Properties";
	}

	public static class Attribute {
		private Type type;
		private double value;
		private List<Modifier> modifiers;

		public Attribute(Type type, double value, List<Modifier> modifiers) {
			this.type = type;
			this.value = value;
			this.modifiers = modifiers;
		}

		public AttributeSnapshot getNMS() {
			List<AttributeModifier> nmsModifers = new ArrayList<>();
			for (Modifier modifier : modifiers)
				nmsModifers.add(modifier.getNMS());
			return new PacketPlayOutUpdateAttributes().new AttributeSnapshot(type.getNMS(), value, nmsModifers);
		}

		public static Attribute getAtributeOf(AttributeSnapshot nms) {
			List<Modifier> modifiers = new ArrayList<>();
			for (AttributeModifier nmsModifier : nms.c())
				modifiers.add(Modifier.getModiferOf(nmsModifier));
			return new Attribute(Type.getTypeFrom(nms.a()), nms.b(), null);
		}
	}

	public static class Modifier {
		private UUID uuid;
		private double amount;
		private Operation operation;

		public Modifier(UUID uuid, double amount, Operation operation) {
			this.uuid = uuid;
			this.amount = amount;
			this.operation = operation;
		}

		public static Modifier getModiferOf(AttributeModifier nms) {
			Operation operation = null;
			switch (nms.getOperation()) {
			case ADDITION:
				operation = Operation.ADD_NUMBER;
				break;
			case MULTIPLY_BASE:
				operation = Operation.MULTIPLY_SCALAR_1;
				break;
			case MULTIPLY_TOTAL:
				operation = Operation.ADD_SCALAR;
				break;
			default:
				break;

			}
			return new Modifier(nms.getUniqueId(), nms.getAmount(), operation);
		}

		public AttributeModifier getNMS() {
			AttributeModifier.Operation nmsOp = null;
			switch (operation) {
			case ADD_NUMBER:
				nmsOp = AttributeModifier.Operation.ADDITION;
				break;
			case ADD_SCALAR:
				nmsOp = AttributeModifier.Operation.MULTIPLY_TOTAL;
				break;
			case MULTIPLY_SCALAR_1:
				nmsOp = AttributeModifier.Operation.MULTIPLY_BASE;
				break;
			default:
				break;
			}
			return new AttributeModifier(uuid, () -> {
				return null;
			}, amount, nmsOp);
		}
	}

	public enum Type {
		MAX_HEALTH(GenericAttributes.MAX_HEALTH), FOLLOW_RANGE(GenericAttributes.FOLLOW_RANGE),
		KNOCKBACK_RESISTANCE(GenericAttributes.KNOCKBACK_RESISTANCE), MOVEMENT_SPEED(GenericAttributes.MOVEMENT_SPEED),
		FLYING_SPEED(GenericAttributes.FLYING_SPEED), ATTACK_DAMAGE(GenericAttributes.ATTACK_DAMAGE),
		ATTACK_KNOCKBACK(GenericAttributes.ATTACK_KNOCKBACK), ATTACK_SPEED(GenericAttributes.ATTACK_SPEED),
		ARMOR(GenericAttributes.ARMOR), ARMOR_TOUGHNESS(GenericAttributes.ARMOR_TOUGHNESS),
		LUCK(GenericAttributes.LUCK), SPAWN_REINFORCEMENTS(GenericAttributes.SPAWN_REINFORCEMENTS),
		JUMP_STRENGTH(GenericAttributes.JUMP_STRENGTH);

		private AttributeBase nms;

		private Type(AttributeBase nms) {
			this.nms = nms;
		}

		public AttributeBase getNMS() {
			return nms;
		}

		public static Type getTypeFrom(AttributeBase nms) {
			for (Type type : values())
				if (type.nms.equals(nms))
					return type;
			return null;
		}
	}
}
