package io.github.riesenpilz.nmsUtilities.packet.playOut;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.Player;

import com.mojang.datafixers.util.Pair;

import io.github.riesenpilz.nmsUtilities.inventory.ItemStack;
import io.github.riesenpilz.nmsUtilities.reflections.Field;
import net.minecraft.server.v1_16_R3.EnumItemSlot;
import net.minecraft.server.v1_16_R3.Packet;
import net.minecraft.server.v1_16_R3.PacketListenerPlayOut;
import net.minecraft.server.v1_16_R3.PacketPlayOutEntityEquipment;

/**
 * https://wiki.vg/Protocol#Entity_Equipment
 * <p>
 * Packet ID: 0x50<br>
 * State: Play<br>
 * Bound To: Client
 * 
 * @author Martin
 *
 */
public class PacketPlayOutEntityEquipmentEvent extends PacketPlayOutEntityEvent {

	private List<Equipment> equimpent;

	@SuppressWarnings("unchecked")
	public PacketPlayOutEntityEquipmentEvent(Player injectedPlayer, PacketPlayOutEntityEquipment packet) {
		super(injectedPlayer, packet);
		
		final List<Pair<EnumItemSlot, net.minecraft.server.v1_16_R3.ItemStack>> nms = Field.get(packet, "b", List.class);
		equimpent = new ArrayList<>();
		for (Pair<EnumItemSlot, net.minecraft.server.v1_16_R3.ItemStack> nmsEquipment : nms)
			equimpent.add(new Equipment(nmsEquipment));
	}

	public PacketPlayOutEntityEquipmentEvent(Player injectedPlayer, int entityId, List<Equipment> equimpent) {
		super(injectedPlayer, entityId);
		this.equimpent = equimpent;
	}

	public List<Equipment> getEquimpent() {
		return equimpent;
	}

	@Override
	public Packet<PacketListenerPlayOut> getNMS() {
		final List<Pair<EnumItemSlot, net.minecraft.server.v1_16_R3.ItemStack>> nms = new ArrayList<>();
		for (Equipment equipment : this.equimpent)
			nms.add(equipment.getNMS());
		return new PacketPlayOutEntityEquipment(getEntityId(), nms);
	}

	@Override
	public int getPacketID() {
		return 0x50;
	}

	@Override
	public String getProtocolURLString() {
		return "https://wiki.vg/Protocol#Entity_Equipment";
	}

	public static class Equipment {

		private ItemStack itemStack;
		private Slot slot;

		public Equipment(ItemStack itemStack, Slot slot) {
			this.itemStack = itemStack;
			this.slot = slot;
		}

		public Equipment(Pair<EnumItemSlot, net.minecraft.server.v1_16_R3.ItemStack> nms) {
			itemStack = ItemStack.getItemStackOf(nms.getSecond());
			slot = Slot.getSlot(nms.getFirst());
		}

		public ItemStack getItemStack() {
			return itemStack;
		}

		public Slot getSlot() {
			return slot;
		}

		public Pair<EnumItemSlot, net.minecraft.server.v1_16_R3.ItemStack> getNMS() {
			return Pair.of(slot.getNMS(), itemStack.getNMS());
		}
	}

	public enum Slot {
		MAINHAND(EnumItemSlot.MAINHAND), OFFHAND(EnumItemSlot.OFFHAND), FEET(EnumItemSlot.FEET),
		LEGS(EnumItemSlot.LEGS), CHEST(EnumItemSlot.CHEST), HEAD(EnumItemSlot.HEAD);

		private EnumItemSlot nms;

		private Slot(EnumItemSlot nms) {
			this.nms = nms;
		}

		public EnumItemSlot getNMS() {
			return nms;
		}

		public static Slot getSlot(EnumItemSlot nms) {
			for (Slot slot : values())
				if (slot.getNMS().equals(nms))
					return slot;
			return null;
		}
	}
}
