package io.github.riesenpilz.nmsUtilities.packet.playOut;

import java.util.ArrayList;

import org.apache.commons.lang.Validate;
import org.bukkit.entity.Player;

import io.github.riesenpilz.nmsUtilities.reflections.Field;
import net.minecraft.server.v1_16_R3.MerchantRecipe;
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
 */
public class PacketPlayOutTradeListEvent extends PacketPlayOutInventoryEvent {

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
		super(injectedPlayer, packet);

		tardeList = Field.get(packet, "b", MerchantRecipeList.class);
		villagerLevel = Field.get(packet, "c", int.class);
		xp = Field.get(packet, "d", int.class);
		regularVillager = Field.get(packet, "e", boolean.class);
		restock = Field.get(packet, "f", boolean.class);
	}

	public PacketPlayOutTradeListEvent(Player injectedPlayer, int inventoryId, MerchantRecipeList tardeList,
			int villagerLevel, int xp, boolean regularVillager, boolean restock) {
		super(injectedPlayer, inventoryId);

		Validate.notNull(tardeList);

		this.tardeList = tardeList;
		this.villagerLevel = villagerLevel;
		this.xp = xp;
		this.regularVillager = regularVillager;
		this.restock = restock;
	}

	public MerchantRecipeList getTardeList() {
		return tardeList;
	}

	public ArrayList<MerchantRecipe> getTardeArray() {
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
		return new PacketPlayOutOpenWindowMerchant(getInventoryId(), tardeList, villagerLevel, xp, regularVillager,
				restock);
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
