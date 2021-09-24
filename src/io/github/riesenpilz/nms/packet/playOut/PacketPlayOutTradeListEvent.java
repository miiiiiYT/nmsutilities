package io.github.riesenpilz.nms.packet.playOut;

import org.bukkit.entity.Player;

import io.github.riesenpilz.nms.reflections.Field;
import net.minecraft.server.v1_16_R3.MerchantRecipeList;
import net.minecraft.server.v1_16_R3.Packet;
import net.minecraft.server.v1_16_R3.PacketListenerPlayOut;
import net.minecraft.server.v1_16_R3.PacketPlayOutOpenWindowMerchant;

/**
 * https://wiki.vg/Protocol#Trade_List
 * <p>
 * The list of trades a villager NPC is offering.
 * <p>
 * Packet ID: 0x28<br>
 * State: Play<br>
 * Bound To: Client
 * 
 * @author Martin
 *
 */
public class PacketPlayOutTradeListEvent extends PacketPlayOutEvent {

	private int inventoryId;
	private MerchantRecipeList tardeList;

	/**
	 * Appears on the trade GUI; meaning comes from the translation key
	 * merchant.level. + level. 1: Novice, 2: Apprentice, 3: Journeyman, 4: Expert,
	 * 5: Master.
	 */
	private int villagerLevel;

	/**
	 * Total experience for this villager (always 0 for the wandering trader).
	 */
	private int xp;

	/**
	 * True if this is a regular villager; false for the wandering trader. When
	 * false, hides the villager level and some other GUI elements.
	 */
	private boolean regularVillager;

	/**
	 * True for regular villagers and false for the wandering trader. If true, the
	 * "Villagers restock up to two times per day." message is displayed when
	 * hovering over disabled trades.
	 */
	private boolean restock;

	public PacketPlayOutTradeListEvent(Player injectedPlayer, PacketPlayOutOpenWindowMerchant packet) {
		super(injectedPlayer);

		inventoryId = Field.get(packet, "a", int.class);
		tardeList = Field.get(packet, "b", MerchantRecipeList.class);
		villagerLevel = Field.get(packet, "c", int.class);
		xp = Field.get(packet, "d", int.class);
		regularVillager = Field.get(packet, "e", boolean.class);
		restock = Field.get(packet, "f", boolean.class);
	}

	public int getInventoryId() {
		return inventoryId;
	}

	public MerchantRecipeList getTardeList() {
		return tardeList;
	}

	public int getVillagerLevel() {
		return villagerLevel;
	}

	public int getXp() {
		return xp;
	}

	public boolean isRegularVillager() {
		return regularVillager;
	}

	public boolean canRestock() {
		return restock;
	}

	@Override
	public Packet<PacketListenerPlayOut> getNMS() {
		return new PacketPlayOutOpenWindowMerchant(inventoryId, tardeList, villagerLevel, xp, regularVillager, restock);
	}

	@Override
	public int getPacketID() {
		return 0x28;
	}

	@Override
	public String getProtocolURLString() {
		return "https://wiki.vg/Protocol#Trade_List";
	}
}
